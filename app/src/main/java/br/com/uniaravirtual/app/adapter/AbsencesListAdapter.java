package br.com.uniaravirtual.app.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.model.entity.Absence;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.StringsUtils;

public class AbsencesListAdapter extends BaseAdapter {

    public AbsencesListAdapter(Context context, List<? extends Parcelable> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.absences_item_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final AbsencesListAdapter.ViewHolder holder = (AbsencesListAdapter.ViewHolder) viewHolder;
        Absence absence = (Absence) mList.get(position);
        String checkQuantity = StringsUtils.checkString(absence.getQuantity());
        holder.mNameAbsence.setText(absence.getName());
        holder.mFrequency.setText(StringsUtils.getStringFromResourceWithText(mContext, R.string
                .txt_frequency, absence
                .getFrequency()));
        String quantity = !checkQuantity.equals("") ? checkQuantity : String.valueOf(0);
        holder.mQuantity.setText(
                StringsUtils.getStringFromResourceWithText(mContext, R.string.txt_quantity,
                        quantity));
        TextDrawable drawable1 = get(absence);
        holder.mImageView.setImageDrawable(drawable1);

    }

    private TextDrawable get(Absence absence) {
        String[] split = absence.getName().split(" ");
        String text = "";
        for (int i = 0; i < split.length; i++) {
            if (split[i] != null && split[i].length() > 2) {
                if (i <= 2) {
                    text += split[i].substring(0, 1);
                    continue;
                }
                break;
            }
        }
        return TextDrawable.builder().buildRound(text, mContext.getResources().getColor(R.color
                .material_light_blue_700));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mNameAbsence;
        private final TextView mFrequency;
        private final TextView mQuantity;
        private final ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mNameAbsence = AppUtil.get(view.findViewById(R.id.tv_name_absence));
            mFrequency = AppUtil.get(view.findViewById(R.id.tv_frequency));
            mQuantity = AppUtil.get(view.findViewById(R.id.tv_quantity));
            mImageView = AppUtil.get(view.findViewById(R.id.image_view_radius));
        }
    }

}
