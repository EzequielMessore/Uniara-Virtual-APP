package br.com.uniaravirtual.model.persistence;

import android.database.Cursor;

import java.util.Date;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public final class CursorUtil {

    private CursorUtil() {
        super();
    }

    public static Date readDate(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) || columnIndex < 0 ? null : new Date(cursor.getLong(columnIndex));
    }

    public static byte[] readByte(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) ? null : cursor.getBlob(columnIndex);
    }

    public static Integer readInt(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) ? Integer.valueOf(0) : cursor.getInt(columnIndex);
    }

    public static Long readLong(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) ? Long.valueOf(0) : cursor.getLong(columnIndex);
    }

    public static Float readFloat(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) ? Float.valueOf(0) : cursor.getFloat(columnIndex);
    }

    public static Double readDouble(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) ? Double.valueOf(0) : cursor.getDouble(columnIndex);
    }

    public static String readString(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) ? String.valueOf("") : cursor.getString(columnIndex);
    }

    public static Boolean readBoolean(Cursor cursor, String columName) {
        int columnIndex = cursor.getColumnIndex(columName);
        return cursor.isNull(columnIndex) ? Boolean.FALSE : cursor.getInt(columnIndex) == 1;
    }

    public static long getSafeTime(final Date date) {
        if (date == null) {
            return -1;
        }
        return date.getTime();
    }

}
