package br.com.uniaravirtual.model.service;

import java.util.List;

import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.model.persistence.absence.AbsenceProvider;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class AbsenceService {

    private AbsenceService() {
        super();
    }

    public static AbsenceService getInstance() {
        return LazyHolder.sInstance;
    }

    public List<Absence> getAll() {
        List<Absence> absences = AbsenceProvider.getInstance().getAll();
        if (absences.isEmpty()) {
            absences = UniaraServiceGenerator.createService(UniaraClient.class)
                    .getAbsences();
            AbsenceProvider.getInstance().saveOrUpdate(absences);
        }
        return absences;
    }

    private static class LazyHolder {
        private static final AbsenceService sInstance = new AbsenceService();
    }

}
