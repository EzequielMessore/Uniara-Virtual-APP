package br.com.uniaravirtual.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import java.net.HttpURLConnection;
import java.util.List;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.exception.InvalidLoginException;
import br.com.uniaravirtual.exception.NotAvailableServer;
import retrofit.client.Header;

public final class AppUtil {

    public static Context CONTEXT_APPLICATION;

    private AppUtil() {
        super();
    }

    public static <T> T get(Object element) {
        return (T) element;
    }

    public static double convertStringToDouble(String str) {
        double parseDouble = 0;
        if (!StringsUtils.isEmpty(str) && !StringsUtils.isNC(str)) {
            parseDouble = Double.parseDouble(StringsUtils.replaceToPoint(str));
        }
        return parseDouble;
    }

    public static boolean validForm(List<? extends View> fields, Context context) {
        boolean isValid = true;

        for (View v : fields) {
            if (v instanceof EditText) {
                EditText txt = (EditText) v;
                if (StringsUtils.isEmpty(txt.getText().toString())) {
                    txt.setError(context.getResources().getString(R.string.txt_fields_required));
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    public static String getHeaderValue(List<Header> headers, String key) {
        for (Header header : headers) {
            if (header.getName().equals(key)) {
                return header.getValue();
            }
        }
        return "";
    }

    public static Drawable getDrawable(Context context, int drawableRes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            return context.getDrawable(drawableRes);
        } else {
            return context.getResources().getDrawable(drawableRes);
        }
    }

    public static void startActivityAndFinishPrevious(Context context, Class<? extends Activity>
            activity) {
        Intent intent = new Intent(context, activity).
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void exceptionHandling(int status) throws NotAvailableServer,
            InvalidLoginException {
        if (status == HttpURLConnection.HTTP_BAD_REQUEST) {
            throw new InvalidLoginException();
        } else if (status == HttpURLConnection.HTTP_INTERNAL_ERROR) {
            throw new NotAvailableServer();
        }
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(CONTEXT_APPLICATION.getContentResolver(), Settings.Secure.ANDROID_ID);
    }
}
