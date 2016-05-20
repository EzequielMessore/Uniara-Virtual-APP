package br.com.uniaravirtual.app.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected final Context mContext;
    protected List<? extends Parcelable> mList;

    public BaseAdapter(Context context, List<? extends Parcelable> list) {
        mContext = context;
        mList = list;
    }

    public void setData(List<? extends Parcelable> data) {
        if (data != null && !data.isEmpty()) {
            mList = data;
            notifyDataSetChanged();
        }
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
