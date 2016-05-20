package br.com.uniaravirtual.model.persistence.absence;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.Absence;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class AbsenceProvider {
    final ArrayList mAbsences = new ArrayList();

    private AbsenceProvider() {
        super();
    }

    public static AbsenceProvider getInstance() {
        return LazyHolder.sInstance;
    }

    public void saveOrUpdate(final List<Absence> absences) {
        synchronized (mAbsences) {
            mAbsences.clear();
            mAbsences.addAll(absences);
        }
        asyncSave(absences);
    }

    public void deleteAll() {
        synchronized (mAbsences) {
            if (!mAbsences.isEmpty()) {
                innerDeleteAll();
            }
        }
    }

    private void asyncSave(final List<Absence> absences) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                innerDeleteAll();
                innerSaveAll(absences);
            }
        }).start();
    }

    private void innerSaveAll(List<Absence> absences) {
        for (Absence absence : absences) {
            AbsenceDAO.getInstance().save(absence);
        }
    }

    private void innerDeleteAll() {
        AbsenceDAO.getInstance().deleteAll();
    }

    public List<Absence> getAll() {
        final ArrayList<Absence> absences = new ArrayList<>();
        synchronized (mAbsences) {
            if (mAbsences.isEmpty()) {
                mAbsences.addAll(AbsenceDAO.getInstance().getAll());
            }
            absences.addAll(mAbsences);
        }
        return absences;
    }

    private static class LazyHolder {
        private static final AbsenceProvider sInstance = new AbsenceProvider();
    }

}
