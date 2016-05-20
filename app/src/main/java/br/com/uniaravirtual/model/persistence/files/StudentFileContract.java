package br.com.uniaravirtual.model.persistence.files;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.FileData;
import br.com.uniaravirtual.model.entity.StudentFiles;
import br.com.uniaravirtual.model.persistence.Columns;
import br.com.uniaravirtual.model.persistence.CursorUtil;
import br.com.uniaravirtual.model.persistence.NameBuilder;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class StudentFileContract {

    public static final String TABLE_NAME = NameBuilder.getInstance().buildName("__tb_student_file__");
    public static final StudentFileColumns COLUMNS = new StudentFileColumns();
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
        sql.append(COLUMNS.GRADE).append(" TEXT NULL ");
        sql.append(" );");
        return sql.toString();
    }

    public static ContentValues getContentValues(StudentFiles studentFile) {
        ContentValues content = new ContentValues();
        content.put(COLUMNS.ID, studentFile.getId());
        content.put(COLUMNS.GRADE, studentFile.getGrade());
        return content;
    }

    public static StudentFiles bind(Cursor cursor) {
        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            final StudentFiles studentFiles = new StudentFiles();
            studentFiles.setId(CursorUtil.readInt(cursor, COLUMNS.ID));
            studentFiles.setGrade(CursorUtil.readString(cursor, COLUMNS.GRADE));
            List<FileData> fileDataList = FileDataProvider.getInstance().getById(studentFiles.getId());
            studentFiles.setFiles(fileDataList);
            return studentFiles;
        }
        return null;
    }

    public static List<StudentFiles> bindStudentFiles(Cursor cursor) {
        final List<StudentFiles> StudentFiles = new ArrayList<>();
        while (cursor.moveToNext()) {
            StudentFiles.add(bind(cursor));
        }
        return StudentFiles;
    }

    static class StudentFileColumns implements Columns {

        public final String ID = buildName("__ID_c__");
        public final String GRADE = buildName("__GRADE_c__");

        @Override
        public String[] toStringArray() {
            return new String[]{ID, GRADE};
        }

        @Override
        public String getInsertColumnHack() {
            return ID + ", " + GRADE;
        }
    }
}
