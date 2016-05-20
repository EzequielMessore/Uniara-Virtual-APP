package br.com.uniaravirtual.app.controller.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.app.adapter.AbsencesListAdapter;
import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.model.enums.BroadcastMessageType;
import br.com.uniaravirtual.model.enums.SharedPreferencesValues;
import br.com.uniaravirtual.model.service.UpdateAbsenceService;
import br.com.uniaravirtual.task.GetAbsencesTask;
import br.com.uniaravirtual.task.OnResultListener;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.BundleFactory;
import br.com.uniaravirtual.util.StringsUtils;

public class AbsencesFragment extends BaseFragment {

    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private AbsencesListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureBroadcast();
        setTitle(StringsUtils.getStringFromResource(getActivity(), R.string.txt_absences));
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
        new GetAbsencesTask(getActivity(), new OnResultListener<List<Absence>>() {
            @Override
            public void onSuccess(List<Absence> list) {
                if (list != null) {
                    List<Absence> absenceList = cloneAbsenceList(list);
                    if (!absenceList.isEmpty()) {
                        mAdapter = new AbsencesListAdapter(getActivity(),
                                absenceList);

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

    private List<Absence> cloneAbsenceList(List<Absence> list) {
        List<Absence> absenceList = new ArrayList<>();
        for (Absence absence : list) {
            if (!StringsUtils.checkString(absence.getQuantity()).equals("")) {
                absenceList.add(absence);
            }
        }
        Collections.sort(absenceList, new Comparator<Absence>() {
            @Override
            public int compare(Absence entity1, Absence entity2) {
                return entity1.getName().compareTo(entity2.getName());
            }
        });
        return absenceList;
    }

    private void configureBroadcast() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BundleFactory.Keys.UPDATE_ABSENCES.toString());
        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                final Bundle bundle = intent.getExtras();
                final BroadcastMessageType messageType = BundleFactory.getBroadcastMessageType(bundle);
                switch (messageType) {
                    case SUCCESS:
                        final List<Absence> absenceList = BundleFactory.getListAbsencesFromBundle(bundle);
                        List<Absence> absences = cloneAbsenceList(absenceList);
                        if (mAdapter != null)
                            mAdapter.setData(absences);
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
        final Intent intent = new Intent(context, UpdateAbsenceService.class);
        intent.putExtras(BundleFactory.makeBundleForAction(BundleFactory.Keys.UPDATE_ABSENCES.toString()));
        context.startService(intent);
    }

    @Override
    protected void updateList() {
        startServiceUpdate();
    }
}
