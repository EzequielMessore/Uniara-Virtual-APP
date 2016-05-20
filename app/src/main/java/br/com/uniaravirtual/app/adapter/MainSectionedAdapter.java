package br.com.uniaravirtual.app.adapter;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.model.entity.FileData;
import br.com.uniaravirtual.task.DownloadFileTask;

public class MainSectionedAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private List<?> mListItem;
    private final List<?> mListHeader;
    private final Map<?, ?> mMapItem;

    public MainSectionedAdapter(List<?> listHeader, List<?> listItem, Map<?, ?> mapItem) {
        mListHeader = listHeader;
        mListItem = listItem;
        mMapItem = mapItem;
    }

    public void setData(List<? extends Parcelable> data) {
        mListItem.clear();
        mListItem = data;
        notifyDataSetChanged();
    }

    @Override
    public int getSectionCount() {
        return mListHeader != null ? mListHeader.size() : 0;
    }

    @Override
    public int getItemCount(int section) {
        return ((List<FileData>) mMapItem.get(section)).size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, final int section,
                                       final int position) {
        final HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
        if (section < mListHeader.size()) {
            Object item = mListHeader.get(section);
            holder.title.setText(item.toString());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int section, int position) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        int index = (position - 1) - section;
        LinearLayout view = holder.item;
        if (index < mListItem.size()) {
            final FileData fileData = (FileData) mListItem.get(index);
            holder.title.setText(fileData.getName());
            view.setOnClickListener(new ClickListener(fileData.getLink()));
        }
    }


    @Override
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        return super.getItemViewType(section, relativePosition, absolutePosition);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == VIEW_TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header_file, parent, false);
            viewHolder = new HeaderViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_file, parent, false);
            viewHolder = new ItemViewHolder(view);
        }
        return viewHolder;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {

        final TextView title;
        final LinearLayout header;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            header = (LinearLayout) itemView.findViewById(R.id.ll_header);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final LinearLayout item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_name_file);
            item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }

    private class ClickListener implements View.OnClickListener {

        private final String mLink;

        public ClickListener(String link) {
            mLink = link;
        }

        @Override
        public void onClick(View v) {
            new DownloadFileTask((Activity) v.getContext(), null).execute(mLink);
        }
    }
}