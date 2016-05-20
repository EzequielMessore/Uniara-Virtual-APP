package br.com.uniaravirtual.model.enums;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.uniaravirtual.util.AppUtil;

public enum SharedPreferencesValues {

    TOKEN("TOKEN"),
    USER_NAME("USER_NAME");

    private static final String NAME_PREFERENCES = "SharedPreferences";
    private String mKey;

    SharedPreferencesValues(String mKey) {
        this.mKey = mKey;
    }

    private static SharedPreferences getPrivatePreferences() {
        Context context = AppUtil.CONTEXT_APPLICATION;
        return context.getSharedPreferences(NAME_PREFERENCES, Context.MODE_PRIVATE);
    }

    public String getString() {
        return getPrivatePreferences().getString(mKey, String.valueOf(""));
    }

    public void putString(String value) {
        getPrivatePreferences().edit().putString(mKey, value).commit();
    }

    public Boolean getBoolean() {
        return getPrivatePreferences().getBoolean(mKey, false);
    }

    public void putBoolean(boolean value) {
        getPrivatePreferences().edit().putBoolean(mKey, value).commit();
    }

    public static void removeAll() {
        getPrivatePreferences().edit().clear().commit();
    }

    public void remove() {
        getPrivatePreferences().edit().remove(mKey).commit();
    }
}
