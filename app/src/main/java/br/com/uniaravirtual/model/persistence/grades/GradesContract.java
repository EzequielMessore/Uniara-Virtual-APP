package br.com.uniaravirtual.model.persistence.grades;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.persistence.Columns;
import br.com.uniaravirtual.model.persistence.CursorUtil;
import br.com.uniaravirtual.model.persistence.NameBuilder;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class GradesContract {

    public static final String TABLE_NAME = NameBuilder.getInstance().buildName("__tb_grades__");
    public static final GradesColumns COLUMNS = new GradesColumns();
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
        sql.append(COLUMNS.FIRST_BIMESTER).append(" TEXT NULL, ");
        sql.append(COLUMNS.SECOND_BIMESTER).append(" TEXT NULL, ");
        sql.append(COLUMNS.THIRD_BIMESTER).append(" TEXT NULL, ");
        sql.append(COLUMNS.FOURTH_BIMESTER).append(" TEXT NULL, ");
        sql.append(COLUMNS.REPLACEMENT).append(" TEXT NULL, ");
        sql.append(COLUMNS.AVERAGE).append(" TEXT NULL, ");
        sql.append(COLUMNS.RECOVERY).append(" TEXT NULL, ");
        sql.append(COLUMNS.STATUS).append(" TEXT NULL ");
        sql.append(" );");
        return sql.toString();
    }

    public static ContentValues getContentValues(Grades grades) {
        ContentValues content = new ContentValues();
        content.put(COLUMNS.ID, grades.getId());
        content.put(COLUMNS.NAME, grades.getName());
        content.put(COLUMNS.FIRST_BIMESTER, grades.getFirstBimester());
        content.put(COLUMNS.SECOND_BIMESTER, grades.getSecondBimester());
        content.put(COLUMNS.THIRD_BIMESTER, grades.getThirdBimester());
        content.put(COLUMNS.FOURTH_BIMESTER, grades.getFourthBimester());
        content.put(COLUMNS.REPLACEMENT, grades.getReplacement());
        content.put(COLUMNS.AVERAGE, grades.getAverage());
        content.put(COLUMNS.RECOVERY, grades.getRecovery());
        content.put(COLUMNS.STATUS, grades.getStatus());
        return content;
    }

    public static Grades bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            final Grades grades = new Grades();
            grades.setId(CursorUtil.readInt(cursor, COLUMNS.ID));
            grades.setName(CursorUtil.readString(cursor, COLUMNS.NAME));
            grades.setFirstBimester(CursorUtil.readString(cursor, COLUMNS.FIRST_BIMESTER));
            grades.setSecondBimester(CursorUtil.readString(cursor, COLUMNS.SECOND_BIMESTER));
            grades.setThirdBimester(CursorUtil.readString(cursor, COLUMNS.THIRD_BIMESTER));
            grades.setFourthBimester(CursorUtil.readString(cursor, COLUMNS.FOURTH_BIMESTER));
            grades.setReplacement(CursorUtil.readString(cursor, COLUMNS.REPLACEMENT));
            grades.setAverage(CursorUtil.readString(cursor, COLUMNS.AVERAGE));
            grades.setRecovery(CursorUtil.readString(cursor, COLUMNS.RECOVERY));
            grades.setStatus(CursorUtil.readString(cursor, COLUMNS.STATUS));
            return grades;
        }
        return null;
    }

    public static List<Grades> bindGrades(Cursor cursor) {
        final List<Grades> grades = new ArrayList<>();
        while (cursor.moveToNext()) {
            grades.add(bind(cursor));
        }
        return grades;
    }

    static class GradesColumns implements Columns {

        public final String ID = buildName("__ID_c__");
        public final String NAME = buildName("__NAME_c__");
        public final String FIRST_BIMESTER = buildName("__FIRST_BIMESTER_c__");
        public final String SECOND_BIMESTER = buildName("__SECOND_BIMESTER_c__");
        public final String THIRD_BIMESTER = buildName("__THIRD_BIMESTER_c__");
        public final String FOURTH_BIMESTER = buildName("__FOURTH_BIMESTER_c__");
        public final String REPLACEMENT = buildName("__REPLACEMENT_c__");
        public final String AVERAGE = buildName("__AVERAGE_c__");
        public final String RECOVERY = buildName("__RECOVERY_c__");
        public final String STATUS = buildName("__STATUS_c__");


        @Override
        public String[] toStringArray() {
            return new String[]{ID, NAME, FIRST_BIMESTER, SECOND_BIMESTER, THIRD_BIMESTER,
                    FOURTH_BIMESTER, REPLACEMENT, AVERAGE, RECOVERY, STATUS};
        }

        @Override
        public String getInsertColumnHack() {
            return ID + ", " + NAME + ", " + FIRST_BIMESTER + ", " + SECOND_BIMESTER + ", "
                    + THIRD_BIMESTER + ", " + FOURTH_BIMESTER + ", " + REPLACEMENT + ", "
                    + AVERAGE + ", " + RECOVERY + ", " + STATUS;
        }
    }
}
