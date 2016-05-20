package br.com.uniaravirtual.util;

import android.content.Context;

public final class StringsUtils {
    private final static String path = "/files/";

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.length() == 0;
    }

    public static boolean isNC(String str) {
        return str.equalsIgnoreCase("N.C.");
    }

    public static String replaceToPoint(String str) {
        return str.replaceAll(",", ".");
    }

    public static String escapeQuotes(String str) {
        return str.replace("\"", "");
    }

    public static String getIdByPath(String str) {
        return str.replace(path, "");
    }

    public static String checkString(String str) {
        return isEmpty(str) || str.equalsIgnoreCase("--") ? "" : str;
    }

    public static String getStringFromResource(Context context, int idResource) {
        return context.getResources().getString(idResource);
    }

    public static String getStringFromResourceWithText(Context context, int idResource, String
            text) {
        return context.getResources().getString(idResource,text);
    }
}
