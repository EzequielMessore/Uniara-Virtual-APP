package br.com.uniaravirtual.task;

import android.app.Activity;

import java.util.List;

import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.model.entity.StudentFiles;
import br.com.uniaravirtual.model.service.StudentFilesService;
import br.com.uniaravirtual.util.AppUtil;
import retrofit.RetrofitError;

public class GetFilesTask extends BaseTask<String, Void, List<StudentFiles>> {

    public GetFilesTask(Activity activity, OnResultListener onNoteDoneListener) {
        super(activity, onNoteDoneListener);
    }

    @Override
    public List<StudentFiles> onBackground(String... params) throws InvalidLoginException,
            NotAvailableServer, NotAvailableNetwork {
        List<StudentFiles> studentFiles = null;
        try {
            studentFiles = StudentFilesService.getInstance().getAll();
        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                AppUtil.exceptionHandling(error.getResponse().getStatus());
            } else if (error.getKind().toString().equals("NETWORK")) {
                throw new NotAvailableNetwork();
            }
        }
        return studentFiles;
    }

}
