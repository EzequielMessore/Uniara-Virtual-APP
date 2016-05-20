package br.com.uniaravirtual.model.persistence.files;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.FileData;
import br.com.uniaravirtual.model.persistence.Columns;
import br.com.uniaravirtual.model.persistence.CursorUtil;
import br.com.uniaravirtual.model.persistence.NameBuilder;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class FileDataContract {

    public static final String TABLE_NAME = NameBuilder.getInstance().buildName("__tb_file_data__");
    public static final FileDataColumns COLUMNS = new FileDataColumns();
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
        sql.append(COLUMNS.LINK).append(" TEXT NULL, ");
        sql.append(COLUMNS.ID_STUDENT_FILES).append(" INTEGER NULL ");
        sql.append(" );");
        return sql.toString();
    }

    public static ContentValues getContentValues(FileData fileData) {
        ContentValues content = new ContentValues();
        content.put(COLUMNS.ID, fileData.getId());
        content.put(COLUMNS.NAME, fileData.getName());
        content.put(COLUMNS.LINK, fileData.getLink());
        content.put(COLUMNS.ID_STUDENT_FILES, fileData.getIdStudentFile());
        return content;
    }

    public static FileData bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            final FileData FileData = new FileData();
            FileData.setId(CursorUtil.readInt(cursor, COLUMNS.ID));
            FileData.setName(CursorUtil.readString(cursor, COLUMNS.NAME));
            FileData.setLink(CursorUtil.readString(cursor, COLUMNS.LINK));
            FileData.setIdStudentFile(CursorUtil.readInt(cursor, COLUMNS.ID_STUDENT_FILES));
            return FileData;
        }
        return null;
    }

    public static List<FileData> bindFileData(Cursor cursor) {
        final List<FileData> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(bind(cursor));
        }
        return list;
    }

    static class FileDataColumns implements Columns {

        public final String ID = buildName("__ID_c__");
        public final String NAME = buildName("__NAME_c__");
        public final String LINK = buildName("__LINK_c__");
        public final String ID_STUDENT_FILES = buildName("__ID_STUDENT_FILES_c__");

        @Override
        public String[] toStringArray() {
            return new String[]{ID, NAME, LINK, ID_STUDENT_FILES};
        }

        @Override
        public String getInsertColumnHack() {
            return ID + ", " + NAME + ", " + LINK + ", " + ID_STUDENT_FILES;
        }
    }
}
