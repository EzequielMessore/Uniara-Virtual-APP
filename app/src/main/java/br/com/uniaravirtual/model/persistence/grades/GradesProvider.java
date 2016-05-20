package br.com.uniaravirtual.model.persistence.grades;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.Grades;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class GradesProvider {
    final ArrayList mGrades = new ArrayList();

    private GradesProvider() {
        super();
    }

    public static GradesProvider getInstance() {
        return LazyHolder.sInstance;
    }

    public void deleteAndSave(final List<Grades> grades) {
        synchronized (mGrades) {
            mGrades.clear();
            mGrades.addAll(grades);
        }
        asyncSave(grades);
    }

    public void deleteAll() {
        synchronized (mGrades) {
            if (!mGrades.isEmpty()) {
                innerDeleteAll();
            }
        }
    }

    private void asyncSave(final List<Grades> grades) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                innerDeleteAll();
                innerSaveAll(grades);
            }
        }).start();
    }

    private void innerSaveAll(List<Grades> grades) {
        for (Grades absence : grades) {
            GradesDAO.getInstance().save(absence);
        }
    }

    private void innerDeleteAll() {
        GradesDAO.getInstance().deleteAll();
    }

    public List<Grades> getAll() {
        final ArrayList<Grades> grades = new ArrayList<>();
        synchronized (mGrades) {
            if (mGrades.isEmpty()) {
                mGrades.addAll(GradesDAO.getInstance().getAll());
            }
            grades.addAll(mGrades);
        }
        return grades;
    }

    private static class LazyHolder {
        private static final GradesProvider sInstance = new GradesProvider();
    }
}
