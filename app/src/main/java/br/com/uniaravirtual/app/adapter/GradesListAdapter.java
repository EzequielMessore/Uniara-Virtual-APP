package br.com.uniaravirtual.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import br.com.uniaravirtual.R;
import br.com.uniaravirtual.model.entity.Grades;
import br.com.uniaravirtual.util.AppUtil;
import br.com.uniaravirtual.util.StringsUtils;

public class GradesListAdapter extends BaseAdapter {

    private int TEXT_COLOR;

    public GradesListAdapter(final Context context, final List<Grades> gradesList) {
        super(context, gradesList);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.grades_item_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final Grades grades = (Grades) mList.get(position);
        final GradesListAdapter.ViewHolder holder = (GradesListAdapter.ViewHolder)
                viewHolder;
        holder.mName.setText(grades.getName());
        TEXT_COLOR = holder.mName.getCurrentTextColor();

        String firstQuarter = StringsUtils.checkString(grades.getFirstBimester());
        verifyAndSetAttributes(firstQuarter, holder.mFirstQuarter, R.string.txt_first_quarter);

        String secondQuarter = StringsUtils.checkString(grades.getSecondBimester());
        verifyAndSetAttributes(secondQuarter, holder.mSecondQuarter, R.string.txt_second_quarter);

        String thirdQuarter = StringsUtils.checkString(grades.getThirdBimester());
        verifyAndSetAttributes(thirdQuarter, holder.mThirdQuarter, R.string.txt_third_quarter);

        String fourthQuarter = StringsUtils.checkString(grades.getFourthBimester());
        verifyAndSetAttributes(fourthQuarter, holder.mFourQuarter, R.string.txt_fourth_quarter);

        String replacement = StringsUtils.checkString(grades.getReplacement());
        verifyAndSetAttributes(replacement, holder.mReplacement, R.string.txt_replacement);

        String recovery = StringsUtils.checkString(grades.getRecovery());
        verifyAndSetAttributes(recovery, holder.mRecovery, R.string.txt_recovery);

        double sumAverage = calculateAverage(
                AppUtil.convertStringToDouble(firstQuarter),
                AppUtil.convertStringToDouble(secondQuarter),
                AppUtil.convertStringToDouble(thirdQuarter),
                AppUtil.convertStringToDouble(fourthQuarter),
                AppUtil.convertStringToDouble(replacement));
        if (!(sumAverage <= 0)) {
            NumberFormat format = new DecimalFormat("#0.0");
            holder.mPartialAverage.setText(
                    StringsUtils.getStringFromResourceWithText
                            (mContext, R.string.txt_partial_average, String
                                    .valueOf(format.format(sumAverage))));
        } else {
            holder.mPartialAverage.setText("");
        }
        TextDrawable drawable1 = get(grades);
        holder.mImageView.setImageDrawable(drawable1);
    }

    private double calculateAverage(final double... notes) {
        double sum = 0;
        int count = 0;
        for (double note : notes) {
            if (note != 0) {
                sum += note;
                count++;
            }
        }
        if (count % 2 != 0 && count != 1) ++count;
        return (sum > 1 && count > 1) ? sum / count : 0;
    }

    private void verifyAndSetAttributes(final String note, final TextView textView, final int
            text) {
        if (!note.equals("")) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(StringsUtils.getStringFromResourceWithText(mContext, text, note));
            isNoteRed(note, textView);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void isNoteRed(final String note, final TextView textView) {
        double parseDouble = AppUtil.convertStringToDouble(note);
        if (parseDouble < 6) {
            textView.setTextColor(Color.RED);
        } else {
            textView.setTextColor(TEXT_COLOR);
        }
    }

    private TextDrawable get(Grades grades) {
        String[] split = grades.getName().split(" ");
        String text = "";
        for (int i = 0; i < split.length; i++) {
            if (split[i] != null && split[i].length() > 2) {
                if (i < 2) {
                    text += split[i].substring(0, 1);
                }
            }
        }
        return TextDrawable.builder().buildRound(text, mContext.getResources().getColor(R.color.material_light_blue_700));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mName;
        private final TextView mFirstQuarter;
        private final TextView mSecondQuarter;
        private final TextView mThirdQuarter;
        private final TextView mFourQuarter;
        private final TextView mPartialAverage;
        private final TextView mRecovery;
        private final TextView mReplacement;
        private final ImageView mImageView;

        public ViewHolder(View view) {
            super(view);
            mName = AppUtil.get(view.findViewById(R.id.tvName));
            mFirstQuarter = AppUtil.get(view.findViewById(R.id.tvFirstBimester));
            mSecondQuarter = AppUtil.get(view.findViewById(R.id.tvSecondBimester));
            mThirdQuarter = AppUtil.get(view.findViewById(R.id.tvThirdBimester));
            mFourQuarter = AppUtil.get(view.findViewById(R.id.tvFourBimester));
            mPartialAverage = AppUtil.get(view.findViewById(R.id.tvPartialAverage));
            mRecovery = AppUtil.get(view.findViewById(R.id.tvRecovery));
            mReplacement = AppUtil.get(view.findViewById(R.id.tvReplacement));
            mImageView = AppUtil.get(view.findViewById(R.id.image_view_radius));
        }
    }
}
