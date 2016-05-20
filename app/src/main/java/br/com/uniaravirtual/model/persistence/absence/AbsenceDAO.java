package br.com.uniaravirtual.model.persistence.absence;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.model.persistence.DataBaseHelper;
import br.com.uniaravirtual.util.AppUtil;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class AbsenceDAO {

    private AbsenceDAO() {
        super();
    }

    public static AbsenceDAO getInstance() {
        return LazyHolder.sInstance;
    }

    public List<Absence> getAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(AbsenceContract.TABLE_NAME, AbsenceContract.COLUMNS
                .toStringArray(), null, null, null, null, AbsenceContract.COLUMNS.NAME);
        List<Absence> absences = AbsenceContract.bindAbsences(cursor);
        db.close();
        helper.close();
        return absences;
    }

    public void save(Absence absence) {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.insert(AbsenceContract.TABLE_NAME, null, AbsenceContract.getContentValues(absence));
        db.close();
        helper.close();
    }

    public void deleteAll() {
        DataBaseHelper helper = new DataBaseHelper(AppUtil.CONTEXT_APPLICATION);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(AbsenceContract.TABLE_NAME, null, null);
        db.close();
        helper.close();
    }

    private static class LazyHolder {
        private static final AbsenceDAO sInstance = new AbsenceDAO();
    }
}
