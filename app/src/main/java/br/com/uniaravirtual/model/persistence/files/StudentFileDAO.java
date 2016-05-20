package br.com.uniaravirtual.model.persistence.files;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.uniaravirtual.model.entity.FileData;
import br.com.uniaravirtual.model.entity.StudentFiles;
import br.com.uniaravirtual.model.persistence.DataBaseHelper;
import br.com.uniaravirtual.util.AppUtil;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class StudentFileDAO {

    private StudentFileDAO() {
        super();
    }

    public static StudentFileDAO getInstance() {
        return LazyHolder.sInstance;
    }

    public List<StudentFiles> getAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getReadableDatabase();
        List<StudentFiles> studentFiles = null;
        try {
            Cursor cursor = db.query(StudentFileContract.TABLE_NAME, StudentFileContract.COLUMNS.toStringArray(), null, null, null, null, null);
            studentFiles = StudentFileContract.bindStudentFiles(cursor);
        } catch (SQLException e) {
            e.getMessage();
        }
        db.close();
        helper.close();
        return studentFiles;
    }

    public void save(StudentFiles studentFiles) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();

        long insert = db.insert(StudentFileContract.TABLE_NAME, null, StudentFileContract.getContentValues(studentFiles));
        for (FileData data : studentFiles.getFiles()) {
            data.setIdStudentFile(insert);
            db.insert(FileDataContract.TABLE_NAME, null, FileDataContract.getContentValues(data));
        }
        db.close();
        helper.close();
    }

    public void deleteAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(StudentFileContract.TABLE_NAME, null, null);
        db.close();
        helper.close();
    }

    private static class LazyHolder {
        private static final StudentFileDAO sInstance = new StudentFileDAO();
    }
}
