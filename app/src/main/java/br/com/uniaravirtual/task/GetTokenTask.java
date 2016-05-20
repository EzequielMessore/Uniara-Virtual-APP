package br.com.uniaravirtual.task;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.model.entity.Student;
import br.com.uniaravirtual.model.service.UniaraClient;
import br.com.uniaravirtual.model.service.UniaraServiceGenerator;
import br.com.uniaravirtual.util.StringsUtils;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetTokenTask extends BaseTask<Student, Void, String> {

    public GetTokenTask(Activity activity, OnResultListener onNoteDoneListener) {
        super(activity, onNoteDoneListener);
    }

    @Override
    public String onBackground(Student... params) throws InvalidLoginException,
            NotAvailableServer, IOException, NotAvailableNetwork {
        String token = "";
        try {
            Response response = UniaraServiceGenerator.createService(UniaraClient.class)
                    .postLogin(params[0]);

            if (response.getStatus() != HttpURLConnection.HTTP_CREATED) {
                exceptionHandling(response.getStatus());
            }

            InputStream inputStream = response.getBody().in();
            byte[] buffer = new byte[inputStream.available()];
            int readBytes;

            while ((readBytes = inputStream.read(buffer)) > 0) {
                token = new String(buffer, 0, readBytes);
            }
            inputStream.close();

        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                exceptionHandling(error.getResponse().getStatus());
            } else if (error.getKind().toString().equals("NETWORK")) {
                throw new NotAvailableNetwork();
            }
        } catch (IOException unexpectedException) {
            throw new IOException();
        }

        return StringsUtils.escapeQuotes(token);
    }

    private void exceptionHandling(int status) throws NotAvailableServer, InvalidLoginException {
        if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new InvalidLoginException();
        } else if (status == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            throw new NotAvailableServer();
        }
    }

}