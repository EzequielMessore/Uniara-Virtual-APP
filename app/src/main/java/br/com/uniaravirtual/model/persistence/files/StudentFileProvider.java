package br.com.uniaravirtual.model.persistence.files;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.StudentFiles;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class StudentFileProvider {

    final ArrayList mStudentFiles = new ArrayList();

    private StudentFileProvider() {
        super();
    }

    public static StudentFileProvider getInstance() {
        return LazyHolder.sInstance;
    }

    public void deleteAndSave(final List<StudentFiles> studentFiles) {
        mStudentFiles.clear();
        mStudentFiles.addAll(studentFiles);
        deleteAll();
        innerSaveAll(studentFiles);
    }

    private void innerSaveAll(List<StudentFiles> studentFiles) {
        for (StudentFiles StudentFiles : studentFiles) {
            StudentFileDAO.getInstance().save(StudentFiles);
        }
    }

    public void deleteAll() {
        StudentFileDAO.getInstance().deleteAll();
        FileDataProvider.getInstance().deleteAll();
    }

    public List<StudentFiles> getAll() {
        final ArrayList<StudentFiles> studentFiles = new ArrayList<>();
        if (mStudentFiles.isEmpty()) {
            mStudentFiles.addAll(StudentFileDAO.getInstance().getAll());
        }
        studentFiles.addAll(mStudentFiles);
        return studentFiles;
    }

    private static class LazyHolder {
        private static final StudentFileProvider sInstance = new StudentFileProvider();
    }
}
