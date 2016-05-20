package br.com.uniaravirtual.model.service;

import android.app.IntentService;
import android.content.Intent;

import java.net.HttpURLConnection;
import java.util.List;

import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableNetwork;
import br.com.uniaravirtual.exception.NotAvailableServer;
import br.com.uniaravirtual.model.entity.StudentFiles;
import br.com.uniaravirtual.model.enums.BroadcastMessageType;
import br.com.uniaravirtual.model.persistence.files.StudentFileProvider;
import br.com.uniaravirtual.util.BundleFactory;
import retrofit.RetrofitError;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class UpdateFilesService extends IntentService {

    public UpdateFilesService() {
        super(UpdateFilesService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String message = "";
        BroadcastMessageType messageType = BroadcastMessageType.SUCCESS;
        List<StudentFiles> studentFiles = null;
        try {
            studentFiles = UniaraServiceGenerator.createService(UniaraClient.class)
                    .getFiles();
            if(!studentFiles.isEmpty()){
                StudentFileProvider.getInstance().deleteAndSave(studentFiles);
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
            intentBroadcast.putExtras(BundleFactory.makeBundleForListStudentFiles(studentFiles));
            sendBroadcast(intentBroadcast);
        }
    }
}
