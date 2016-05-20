package br.com.uniaravirtual.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.uniaravirtual.model.persistence.absence.AbsenceContract;
import br.com.uniaravirtual.model.persistence.files.FileDataContract;
import br.com.uniaravirtual.model.persistence.files.StudentFileContract;
import br.com.uniaravirtual.model.persistence.grades.GradesContract;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATA_BASE = NameBuilder.getInstance().buildName("UNIARA_VIRTUAL_DB");
    private static int VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATA_BASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AbsenceContract.createTable());
        db.execSQL(GradesContract.createTable());
        db.execSQL(FileDataContract.createTable());
        db.execSQL(StudentFileContract.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
