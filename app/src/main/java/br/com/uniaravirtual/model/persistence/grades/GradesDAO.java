package br.com.uniaravirtual.model.persistence.grades;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.List;

import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.persistence.DataBaseHelper;
import br.com.uniaravirtual.util.AppUtil;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class GradesDAO {

    private GradesDAO() {
        super();
    }

    public static GradesDAO getInstance() {
        return LazyHolder.sInstance;
    }

    public List<Grades> getAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            Cursor cursor = db.query(GradesContract.TABLE_NAME, GradesContract.COLUMNS.toStringArray(), null, null, null, null, GradesContract.COLUMNS.NAME);
            List<Grades> grades = GradesContract.bindGrades(cursor);
            db.close();
            helper.close();
            return grades;
        }catch (SQLiteException e){
            e.getMessage();
        }
        return null;
    }

    public void save(Grades grades) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(GradesContract.TABLE_NAME, null, GradesContract.getContentValues(grades));
        db.close();
        helper.close();
    }

    public void deleteAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(GradesContract.TABLE_NAME, null, null);
        db.close();
        helper.close();
    }

    private static class LazyHolder {
        private static final GradesDAO sInstance = new GradesDAO();
    }
}
