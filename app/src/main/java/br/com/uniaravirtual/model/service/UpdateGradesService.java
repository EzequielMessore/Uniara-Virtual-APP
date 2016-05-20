package br.com.uniaravirtual.model.service;

import android.app.IntentService;
import android.content.Intent;

import java.net.HttpURLConnection;
import java.util.List;

import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.enums.BroadcastMessageType;
import br.com.uniaravirtual.model.persistence.grades.GradesProvider;
import br.com.uniaravirtual.util.BundleFactory;
import retrofit.RetrofitError;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class UpdateGradesService extends IntentService {

    public UpdateGradesService() {
        super(UpdateGradesService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = "";
        BroadcastMessageType messageType = BroadcastMessageType.SUCCESS;
        List<Grades> grades = null;
        try {
            grades = UniaraServiceGenerator.createService(UniaraClient.class)
                    .getGrades();
            if (!grades.isEmpty()) {
                GradesProvider.getInstance().deleteAndSave(grades);
            }
        } catch (RetrofitError error) {
            messageType = BroadcastMessageType.ERROR;
            if (error.getResponse() != null) {
                final int status = error.getResponse().getStatus();
                if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
                    message = new InvalidLoginException().getMessage();
                } else if (status == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    message = new NotAvailableServer().getMessage();
                }
            }
            if (error.getKind() != null) {
                message = new NotAvailableNetwork().getMessage();
            }
        } finally {
            final Intent intentBroadcast = new Intent();
            intentBroadcast.setAction(BundleFactory.getBroadcastActionBundle(intent.getExtras()));
            intentBroadcast.putExtras(BundleFactory.makeBundleForBroadcast(message, messageType));
            intentBroadcast.putExtras(BundleFactory.makeBundleForListGrades(grades));
            sendBroadcast(intentBroadcast);
        }
    }
}
