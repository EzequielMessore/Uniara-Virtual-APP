package br.com.uniaravirtual.app.controller.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.util.AppUtil;

public abstract class BaseFragment extends Fragment {

    protected View mView;
    protected RecyclerView mRecyclerView;
    protected TextView mTextInformation;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_base, container, false);
        setHasOptionsMenu(true);
        bindRecyclerView();
        bindInformationListNull();
        bindSwipeRefresh();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                mSwipeRefreshLayout.setRefreshing(true);
                updateList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindSwipeRefresh() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.material_light_blue_500, R.color.material_light_blue_700, R.color.material_light_blue_900);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                updateList();
            }
        });
    }


    protected abstract void updateList();

    protected void setTitle(String title) {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(title);
    }

    private void bindInformationListNull() {
        mTextInformation = AppUtil.get(mView.findViewById(R.id.tv_information_list));
    }

    private void bindRecyclerView() {
        mRecyclerView = AppUtil.get(mView.findViewById(R.id.recyclerView));
    }
}
