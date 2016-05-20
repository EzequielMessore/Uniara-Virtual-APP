package br.com.uniaravirtual.model.service;

import java.util.List;

import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.persistence.grades.GradesProvider;

/**
 * Created by Ezequiel Messore on 25/04/16.
 * ezequielmessore.developer@gmail.com
 */
public class GradesService {

    private GradesService() {
        super();
    }

    public static GradesService getInstance() {
        return LazyHolder.sInstance;
    }

    public List<Grades> getAll() {
        List<Grades> gradesList = GradesProvider.getInstance().getAll();
        if (gradesList.isEmpty()) {
            gradesList = UniaraServiceGenerator.createService(UniaraClient.class).getGrades();
            GradesProvider.getInstance().deleteAndSave(gradesList);
        }
        return gradesList;
    }

    private static class LazyHolder {
        private static final GradesService sInstance = new GradesService();
    }
}
