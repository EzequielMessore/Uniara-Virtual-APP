package br.com.uniaravirtual.task;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.model.service.UniaraClient;
import br.com.uniaravirtual.model.service.UniaraServiceGenerator;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.StorageUtil;
import br.com.uniaravirtual.util.StringsUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

public class DownloadFileTask extends BaseTask<String, String, Void> {

    public DownloadFileTask(Activity activity, OnResultListener onNoteDoneListener) {
        super(activity, onNoteDoneListener);
    }

    @Override
    protected void onPreExecute() {
        if (getProgress().isShowing()) {
            getProgress().dismiss();
        }
    }

    @Override
    public Void onBackground(String... params) throws InvalidLoginException, NotAvailableServer, IOException, NotAvailableNetwork {
        final View view = getActivity().findViewById(android.R.id.content);
        showMessagePrepare(view);
        try {
            Response response = UniaraServiceGenerator.
                    createService(UniaraClient.class).download(StringsUtils.getIdByPath(params[0]));
            TypedInput responseBody = response.getBody();

            FileOutputStream f;
            String nameFile = getNameFile(response);
            f = new FileOutputStream(new File(StorageUtil.getPublicDataFolder(), nameFile));
            InputStream inputStream = responseBody.in();
            int lengthOfFile = (int) responseBody.length();
            byte[] buffer = new byte[4];
            int readerBytes;
            long total = 0;
            while ((readerBytes = inputStream.read(buffer)) > 0) {
                total += readerBytes;
                publishProgress(String.valueOf((int) ((total * 100) / lengthOfFile)));
                f.write(buffer, 0, readerBytes);
            }
            f.close();
            inputStream.close();
            showMessageSuccess(view);
            StorageUtil.sendNotification(getActivity(), StringsUtils.getStringFromResource(getActivity(), R
                    .string.msg_information_download), nameFile);
        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                AppUtil.exceptionHandling(error.getResponse().getStatus());
            } else if (error.getKind().toString().equals("NETWORK")) {
                throw new NotAvailableNetwork();
            }
        }
        return null;
    }

    private void showMessagePrepare(View view) {
        final String msg = StringsUtils.getStringFromResource(getActivity(), R.string.msg_download_prepare);
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    private void showMessageSuccess(View view) {
        final String msg = StringsUtils.getStringFromResource(getActivity(), R.string.msg_download_complete);
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

    private String getNameFile(Response response) {
        String name = AppUtil.getHeaderValue(response.getHeaders(), "Content-Disposition");
        return StringsUtils.escapeQuotes(name.replace("attachment; filename=\"", ""));
    }

}
