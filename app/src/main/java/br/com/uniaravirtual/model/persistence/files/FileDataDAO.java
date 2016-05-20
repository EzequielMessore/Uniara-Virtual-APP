package br.com.uniaravirtual.model.persistence.files;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.uniaravirtual.model.entity.FileData;
import br.com.uniaravirtual.model.persistence.DataBaseHelper;
import br.com.uniaravirtual.util.AppUtil;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class FileDataDAO {

    private FileDataDAO() {
        super();
    }

    public static FileDataDAO getInstance() {
        return LazyHolder.sInstance;
    }

    public List<FileData> getAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(FileDataContract.TABLE_NAME, FileDataContract.COLUMNS
                .toStringArray(), null, null, null, null, FileDataContract.COLUMNS.NAME);
        List<FileData> fileDataList = FileDataContract.bindFileData(cursor);
        db.close();
        helper.close();
        return fileDataList;
    }

    public List<FileData> getById(int id) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getReadableDatabase();
        String where = FileDataContract.COLUMNS.ID_STUDENT_FILES + " = ?";
        String[] args = {String.valueOf(id)};
        Cursor cursor = db.query(
                FileDataContract.TABLE_NAME, FileDataContract.COLUMNS.toStringArray(), where, args, null, null, null);
        List<FileData> list = FileDataContract.bindFileData(cursor);
        db.close();
        helper.close();
        return list;
    }

    public void save(FileData FileData) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(FileDataContract.TABLE_NAME, null, FileDataContract.getContentValues(FileData));
        db.close();
        helper.close();
    }

    public void deleteAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(FileDataContract.TABLE_NAME, null, null);
        db.close();
        helper.close();
    }

    private static class LazyHolder {
        private static final FileDataDAO sInstance = new FileDataDAO();
    }
}
