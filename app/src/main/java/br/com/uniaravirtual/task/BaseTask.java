package br.com.uniaravirtual.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

import java.io.IOException;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.util.StringsUtils;
import retrofit.RetrofitError;

public abstract class BaseTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private final Activity mActivity;
    private final OnResultListener mOnNoteDoneListener;
    private ProgressDialog mProgress;

    public BaseTask(Activity activity, OnResultListener onNoteDoneListener) {
        mActivity = activity;
        mOnNoteDoneListener = onNoteDoneListener;
        mProgress = new ProgressDialog(mActivity);
        mProgress.setTitle(StringsUtils.getStringFromResource(mActivity, R.string.msg_waiting));
        mProgress.setMessage(StringsUtils.getStringFromResource(mActivity, R.string
                .txt_searching_data));
        mProgress.setCancelable(true);
        mProgress.setIndeterminate(true);
    }

    public Activity getActivity() {
        return mActivity;
    }

    public ProgressDialog getProgress() {
        return mProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgress.show();
    }

    @Override
    protected Result doInBackground(Params... params) {
        try {
            return onBackground(params);
        } catch (RetrofitError | NotAvailableServer | InvalidLoginException |
                NotAvailableNetwork unexpectedException) {
            onError(unexpectedException);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPreExecute();
        if (mOnNoteDoneListener != null) {
            mOnNoteDoneListener.onSuccess(result);
        }
        mProgress.dismiss();
    }

    public void onError(Exception error) {
        Snackbar.make(mActivity.findViewById(android.R.id.content), error.getMessage(),
                Snackbar.LENGTH_LONG).show();
    }

    public abstract Result onBackground(Params... params) throws InvalidLoginException,
            NotAvailableServer, IOException, NotAvailableNetwork;
}
