package br.com.uniaravirtual.task;

import android.app.Activity;

import java.util.List;

import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.model.service.AbsenceService;
import br.com.uniaravirtual.util.AppUtil;
import retrofit.RetrofitError;

public class GetAbsencesTask extends BaseTask<String, Void, List<Absence>> {

    public GetAbsencesTask(Activity activity, OnResultListener onNoteDoneListener) {
        super(activity, onNoteDoneListener);
    }

    @Override
    public List<Absence> onBackground(String... params) throws InvalidLoginException,
            NotAvailableServer, NotAvailableNetwork {
        List<Absence> absences = null;
        try {
            absences = AbsenceService.getInstance().getAll();
        } catch (RetrofitError error) {
            if (error.getResponse() != null) {
                AppUtil.exceptionHandling(error.getResponse().getStatus());
            } else if (error.getKind().toString().equals("NETWORK")) {
                throw new NotAvailableNetwork();
            }
        }
        return absences;
    }
}
