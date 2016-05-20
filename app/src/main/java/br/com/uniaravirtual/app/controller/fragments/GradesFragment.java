package br.com.uniaravirtual.app.controller.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.app.adapter.GradesListAdapter;
import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.model.enums.BroadcastMessageType;
import br.com.uniaravirtual.model.service.UpdateGradesService;
import br.com.uniaravirtual.task.GetDisciplineTask;
import br.com.uniaravirtual.task.OnResultListener;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.BundleFactory;
import br.com.uniaravirtual.util.StringsUtils;

public class GradesFragment extends BaseFragment {

    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private GradesListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureBroadcast();
        setTitle(StringsUtils.getStringFromResource(getActivity(), R.string.txt_grades));
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
        new GetDisciplineTask(getActivity(), new OnResultListener<List<Grades>>() {
            @Override
            public void onSuccess(List<Grades> list) {
                if (list != null) {
                    List<Grades> gradesList = cloneListDiscipline(list);
                    if (!gradesList.isEmpty()) {
                        mAdapter = new GradesListAdapter(mView.getContext(),
                                gradesList);
                        mRecyclerView.setAdapter(mAdapter);
                    } else {
                        mTextInformation.setVisibility(View.VISIBLE);
                        mTextInformation.setText(
                                StringsUtils.getStringFromResource(getActivity(), R.string
                                        .txt_information_grades));
                    }
                }
            }
        }).execute();
    }

    private List<Grades> cloneListDiscipline(List<Grades> list) {
        List<Grades> gradesList = new ArrayList<>();
        for (Grades grades : list) {
            if (!StringsUtils.checkString(grades.getFirstBimester()).equals("")) {
                gradesList.add(grades);
            }
        }
        Collections.sort(gradesList, new Comparator<Grades>() {
            @Override
            public int compare(Grades entity1, Grades entity2) {
                return entity1.getName().compareTo(entity2.getName());
            }
        });
        return gradesList;
    }

    private void configureBroadcast() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BundleFactory.Keys.UPDATE_GRADES.toString());
        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                final Bundle bundle = intent.getExtras();
                final BroadcastMessageType messageType = BundleFactory.getBroadcastMessageType(bundle);
                switch (messageType) {
                    case SUCCESS:
                        final List<Grades> gradesList = BundleFactory.getListGradesFromBundle(bundle);
                        List<Grades> grades = cloneListDiscipline(gradesList);
                        mAdapter.setData(grades);
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
        final Intent intent = new Intent(context, UpdateGradesService.class);
        intent.putExtras(BundleFactory.makeBundleForAction(BundleFactory.Keys.UPDATE_GRADES.toString()));
        context.startService(intent);
    }


    @Override
    protected void updateList() {
        startServiceUpdate();
    }
}
