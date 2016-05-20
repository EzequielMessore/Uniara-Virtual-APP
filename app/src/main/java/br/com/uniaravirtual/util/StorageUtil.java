package br.com.uniaravirtual.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import java.io.File;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.model.enums.ExtensionMimeType;

public final class StorageUtil {

    private static final int NOTIFICATION_ID = 1;
    private static final String DIRECTORY_NAME = "/Uniara Virtual";
    private static NotificationManager mNotificationManager;

    public static String getExtension(String name){
        String[] extension = name.split("\\.");
        ExtensionMimeType extensionMimeType = ExtensionMimeType.get(extension[extension.length - 1]);
        return extensionMimeType != null ? extensionMimeType.getValue() : "";
    }

    public static Intent openFile(final String name) {
        File temp_file = new File(getPublicDataFolder() + "/" + name);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(temp_file), getExtension(name));
        mNotificationManager.cancel(NOTIFICATION_ID);
        return intent;
    }

    public static void sendNotification(Context context, final String msg, final String name) {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_file_download)
                .setContentTitle(StringsUtils.getStringFromResource(context,R.string.msg_download_complete))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg).setAutoCancel(true);

        openAndCancelNotification(context, name, mBuilder);

    }

    private static void openAndCancelNotification(Context context, String name, NotificationCompat.Builder mBuilder) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, openFile(name), 0);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public static String getPublicDataFolder() {
        StringBuilder publicFolder = new StringBuilder();
        publicFolder.append(Environment.getExternalStorageDirectory()).append(DIRECTORY_NAME);
        File new_dir = new File(publicFolder.toString());
        if (!new_dir.exists()) {
            new_dir.mkdirs();
        }
        return publicFolder.toString();
    }

    public static boolean isExternalSDStorage() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) && Environment.isExternalStorageRemovable();
    }

}
