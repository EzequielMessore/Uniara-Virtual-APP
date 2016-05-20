package br.com.uniaravirtual.app.controller.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.app.adapter.MainSectionedAdapter;
import br.com.uniaravirtual.model.entity.FileData;
import br.com.uniaravirtual.model.entity.StudentFiles;
import br.com.uniaravirtual.model.enums.BroadcastMessageType;
import br.com.uniaravirtual.model.service.UpdateFilesService;
import br.com.uniaravirtual.task.GetFilesTask;
import br.com.uniaravirtual.task.OnResultListener;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.BundleFactory;
import br.com.uniaravirtual.util.StringsUtils;

public class FilesFragment extends BaseFragment {

    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private MainSectionedAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureBroadcast();
        setTitle(StringsUtils.getStringFromResource(getActivity(), R.string.txt_files));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mBroadcastReceiver, mIntentFilter);
        initTask();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    private void initTask() {
        new GetFilesTask(getActivity(), new OnResultListener<List<StudentFiles>>() {
            @Override
            public void onSuccess(final List<StudentFiles> studentFiles) {
                if (studentFiles != null) {
                    if (!studentFiles.isEmpty()) {
                        Collections.sort(studentFiles, new Comparator<StudentFiles>() {
                            @Override
                            public int compare(StudentFiles entity1, StudentFiles entity2) {
                                return entity1.getGrade().compareTo(entity2.getGrade());
                            }
                        });

                        mAdapter = new MainSectionedAdapter(
                                listHeader(studentFiles),
                                listItem(studentFiles),
                                mapFiles(studentFiles)
                        );
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        mTextInformation.setVisibility(View.VISIBLE);
                        mTextInformation.setText(
                                StringsUtils.getStringFromResource(getActivity(), R.string
                                        .txt_information_absences));
                    }
                }
            }

        }).execute();
    }

    private Map<Integer, List<FileData>> mapFiles(List<StudentFiles> studentFiles) {
        Map<Integer, List<FileData>> map = new LinkedHashMap<>();
        for (int i = 0; i < studentFiles.size(); i++) {
            StudentFiles files = studentFiles.get(i);
            for (int j = 0; j < files.getFiles().size(); j++) {
                map.put(i, files.getFiles());
            }
        }
        return map;
    }

    private List<FileData> listItem(List<StudentFiles> studentFiles) {
        List<FileData> fileData = new ArrayList<>();
        for (int i = 0; i < studentFiles.size(); i++) {
            fileData.addAll(studentFiles.get(i).getFiles());
        }
        return fileData;
    }

    private List<String> listHeader(List<StudentFiles> studentFiles) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < studentFiles.size(); i++) {
            stringList.add(studentFiles.get(i).getGrade());
        }
        return stringList;
    }

    private void configureBroadcast() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BundleFactory.Keys.UPDATE_STUDENT_FILES.toString());
        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                final Bundle bundle = intent.getExtras();
                final BroadcastMessageType messageType = BundleFactory.getBroadcastMessageType(bundle);
                switch (messageType) {
                    case SUCCESS:
                        final List<StudentFiles> gradesList = BundleFactory.getListStudentFilesFromBundle(bundle);
                        orderList(gradesList);
                        if (!gradesList.isEmpty()) {
                            mAdapter.setData(listItem(gradesList));
                        }
                        break;
                    case ERROR:
                        final String message = BundleFactory.getBroadcastMessage(bundle);
                        Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
                        break;
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    private void startServiceUpdate() {
        final Context context = AppUtil.CONTEXT_APPLICATION;
        final Intent intent = new Intent(context, UpdateFilesService.class);
        intent.putExtras(BundleFactory.makeBundleForAction(BundleFactory.Keys.UPDATE_STUDENT_FILES.toString()));
        context.startService(intent);
    }

    private void orderList(List<StudentFiles> list) {
        Collections.sort(list, new Comparator<StudentFiles>() {
            @Override
            public int compare(StudentFiles entity1, StudentFiles entity2) {
                return entity1.getGrade().compareTo(entity2.getGrade());
            }
        });
    }

    @Override
    protected void updateList() {
        startServiceUpdate();
    }
}
