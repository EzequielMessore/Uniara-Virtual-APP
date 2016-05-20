package br.com.uniaravirtual.model.service;

import java.util.List;

import br.com.uniaravirtual.model.entity.StudentFiles;
import br.com.uniaravirtual.model.persistence.files.StudentFileProvider;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class StudentFilesService {

    private StudentFilesService() {
        super();
    }

    public static StudentFilesService getInstance() {
        return LazyHolder.sInstance;
    }

    public List<StudentFiles> getAll() {
        List<StudentFiles> studentFiles = StudentFileProvider.getInstance().getAll();
        if (studentFiles.isEmpty()) {
            studentFiles = UniaraServiceGenerator.createService(UniaraClient.class).getFiles();
            StudentFileProvider.getInstance().deleteAndSave(studentFiles);
        }
        return studentFiles;
    }

    private static class LazyHolder {
        private static final StudentFilesService sInstance = new StudentFilesService();
    }
}
