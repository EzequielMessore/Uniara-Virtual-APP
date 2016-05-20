package br.com.uniaravirtual.model.persistence.files;

import java.util.ArrayList;
import java.util.List;

import br.com.uniaravirtual.model.entity.FileData;

/**
 * Created by Ezequiel Messore on 29/04/2016.
 * ezequielmessore.developer@gmail.com
 */
public class FileDataProvider {

    final ArrayList mFileDataList = new ArrayList();

    private FileDataProvider() {
        super();
    }

    public static FileDataProvider getInstance() {
        return LazyHolder.sInstance;
    }

    public void saveOrUpdate(final List<FileData> list) {
        mFileDataList.clear();
        mFileDataList.addAll(list);
        save(list);
    }

    public void deleteAll() {
        innerDeleteAll();
    }

    private void save(final List<FileData> list) {
        innerDeleteAll();
        innerSaveAll(list);
    }

    private void innerSaveAll(List<FileData> list) {
        for (FileData FileData : list) {
            FileDataDAO.getInstance().save(FileData);
        }
    }

    private void innerDeleteAll() {
        FileDataDAO.getInstance().deleteAll();
    }

    public List<FileData> getAll() {
        final ArrayList<FileData> list = new ArrayList<>();
        if (mFileDataList.isEmpty()) {
            mFileDataList.addAll(FileDataDAO.getInstance().getAll());
        }
        list.addAll(mFileDataList);
        return list;
    }

    public List<FileData> getById(int id) {
        return FileDataDAO.getInstance().getById(id);
    }


    private static class LazyHolder {
        private static final FileDataProvider sInstance = new FileDataProvider();
    }
}
