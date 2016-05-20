package br.com.uniaravirtual.task;

import android.app.Activity;

import java.util.List;

import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.service.GradesService;
import br.com.uniaravirtual.util.AppUtil;
import retrofit.RetrofitError;

public class GetDisciplineTask extends BaseTask<String, Void, List<Grades>> {

    public GetDisciplineTask(Activity activity, OnResultListener onNoteDoneListener) {
        super(activity, onNoteDoneListener);
    }

    @Override
    public List<Grades> onBackground(String... params) throws InvalidLoginException, NotAvailableServer,
            NotAvailableNetwork {
        List<Grades> grades = null;
        try {
            grades = GradesService.getInstance().getAll();
        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                AppUtil.exceptionHandling(error.getResponse().getStatus());
            } else if (error.getKind().toString().equals("NETWORK")) {
                throw new NotAvailableNetwork();
            }
        }
        return grades;
    }

}