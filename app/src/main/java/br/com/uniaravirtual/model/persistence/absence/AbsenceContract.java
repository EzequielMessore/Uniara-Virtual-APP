package br.com.uniaravirtual.model.persistence.absence;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.model.persistence.Columns;
import br.com.uniaravirtual.model.persistence.CursorUtil;
import br.com.uniaravirtual.model.persistence.NameBuilder;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class AbsenceContract {

    public static final String TABLE_NAME =  NameBuilder.getInstance().buildName("__tb_absence__");
    public static final AbsenceColumns COLUMNS = new AbsenceColumns();
    private static final String CREATE_TABLE_IF_NOT_EXISTS = " CREATE TABLE IF NOT EXISTS ";

    private static String buildName(String str) {
        return NameBuilder.getInstance().buildName(str);
    }

    public static String createTable() {
        final StringBuilder sql = new StringBuilder();
        sql.append(CREATE_TABLE_IF_NOT_EXISTS);
        sql.append(TABLE_NAME);
        sql.append(" ( ");
        sql.append(COLUMNS.ID).append(" INTEGER PRIMARY KEY , ");
        sql.append(COLUMNS.NAME).append(" TEXT NULL, ");
        sql.append(COLUMNS.FREQUENCY).append(" TEXT NULL, ");
        sql.append(COLUMNS.QUANTITY).append(" TEXT NULL ");
        sql.append(" );");
        return sql.toString();
    }

    public static ContentValues getContentValues(Absence absence) {
        ContentValues content = new ContentValues();
        content.put(COLUMNS.ID, absence.getId());
        content.put(COLUMNS.NAME, absence.getName());
        content.put(COLUMNS.FREQUENCY, absence.getFrequency());
        content.put(COLUMNS.QUANTITY, absence.getQuantity());
        return content;
    }

    public static Absence bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            final Absence absence = new Absence();
            absence.setId(CursorUtil.readInt(cursor, COLUMNS.ID));
            absence.setName(CursorUtil.readString(cursor, COLUMNS.NAME));
            absence.setFrequency(CursorUtil.readString(cursor, COLUMNS.FREQUENCY));
            absence.setQuantity(CursorUtil.readString(cursor, COLUMNS.QUANTITY));
            return absence;
        }
        return null;
    }

    public static List<Absence> bindAbsences(Cursor cursor) {
        final List<Absence> absences = new ArrayList<>();
        while (cursor.moveToNext()) {
            absences.add(bind(cursor));
        }
        return absences;
    }

    static class AbsenceColumns implements Columns {

        public final String ID = buildName("__ID_c__");
        public final String NAME = buildName("__NAME_c__");
        public final String FREQUENCY = buildName("__FREQUENCY_c__");
        public final String QUANTITY = buildName("__QUANTITY_c__");

        @Override
        public String[] toStringArray() {
            return new String[]{ID, NAME, FREQUENCY, QUANTITY};
        }

        @Override
        public String getInsertColumnHack() {
            return ID + ", " + NAME + ", " + FREQUENCY + ", " + QUANTITY;
        }
    }
}
