package com.myCPE.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myCPE.R;
import com.myCPE.model.additional_qualification.Model_additional_qualification;
import com.myCPE.utility.Constant;

import java.util.ArrayList;

public class AdditionalQualificationEditPopUpAdapter extends RecyclerView.Adapter<AdditionalQualificationEditPopUpAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<Model_additional_qualification> arraylistModeladditionalcredential = new ArrayList<>();
    LayoutInflater mInflater;


    public AdditionalQualificationEditPopUpAdapter(Context mContext, ArrayList<Model_additional_qualification> mList) {
        this.mContext = mContext;
        this.arraylistModeladditionalcredential = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_row_proffesional_credential, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        final Model_additional_qualification model_additional_qualification = arraylistModeladditionalcredential.get(position);

        if (!model_additional_qualification.getName().equalsIgnoreCase("")) {
            viewHolder.tv_additional_qualification.setText(model_additional_qualification.getName());
        }
        Boolean isChecked = Constant.hashmap_additional_qualification.get(model_additional_qualification.name);
//        if (model_additional_qualification.isChecked()) {
        if (isChecked) {
//            viewHolder.cbselection.setChecked(true);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_checked));
            viewHolder.rel_topics.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_background_yellow_selected));
        } else {
//            viewHolder.cbselection.setChecked(false);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
            viewHolder.rel_topics.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_background_blue_white));
        }

        viewHolder.tv_additional_qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model_additional_qualification.isChecked()) {
                    model_additional_qualification.setChecked(false);

                    for (int i = 0; i < arraylistModeladditionalcredential.size(); i++) {
                        if (model_additional_qualification.getId() == arraylistModeladditionalcredential.get(i).getId()) {
                            arraylistModeladditionalcredential.set(i, model_additional_qualification);
                        }
                    }

                    for (int k = 0; k < Constant.arraylistselectedadditionalqualificationID.size(); k++) {
                        if (model_additional_qualification.getId() == Constant.arraylistselectedadditionalqualificationID.get(k)) {
                            Constant.arraylistselectedadditionalqualificationID.remove(k);
                            Constant.arraylistselectedadditionalqualification.remove(k);
                        }
                    }

                    Constant.hashmap_additional_qualification.put(arraylistModeladditionalcredential.get(position).name
                            , false);

//                    viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                    viewHolder.rel_topics.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_background_blue_white));


                } else {
                    model_additional_qualification.setChecked(true);

                    for (int i = 0; i < arraylistModeladditionalcredential.size(); i++) {
                        if (model_additional_qualification.getId() == arraylistModeladditionalcredential.get(i).getId()) {
                            arraylistModeladditionalcredential.set(i, model_additional_qualification);
                            Constant.arraylistselectedadditionalqualificationID.add(model_additional_qualification.getId());
                            Constant.arraylistselectedadditionalqualification.add(model_additional_qualification.getName());
                        }
                    }

                    Constant.hashmap_additional_qualification.put(arraylistModeladditionalcredential.get(position).name
                            , true);
//                    viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_checked));
                    viewHolder.rel_topics.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_background_yellow_selected));
                }


            }
        });

        viewHolder.rel_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model_additional_qualification.isChecked()) {
                    model_additional_qualification.setChecked(false);

                    for (int i = 0; i < arraylistModeladditionalcredential.size(); i++) {
                        if (model_additional_qualification.getId() == arraylistModeladditionalcredential.get(i).getId()) {
                            arraylistModeladditionalcredential.set(i, model_additional_qualification);
                        }
                    }

                    for (int k = 0; k < Constant.arraylistselectedadditionalqualificationID.size(); k++) {
                        if (model_additional_qualification.getId() == Constant.arraylistselectedadditionalqualificationID.get(k)) {
                            Constant.arraylistselectedadditionalqualificationID.remove(k);
                            Constant.arraylistselectedadditionalqualification.remove(k);
                        }
                    }

                    Constant.hashmap_additional_qualification.put(arraylistModeladditionalcredential.get(position).name
                            , false);

//                    viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_unchecked));
                    viewHolder.rel_topics.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_background_blue_white));


                } else {
                    model_additional_qualification.setChecked(true);

                    for (int i = 0; i < arraylistModeladditionalcredential.size(); i++) {
                        if (model_additional_qualification.getId() == arraylistModeladditionalcredential.get(i).getId()) {
                            arraylistModeladditionalcredential.set(i, model_additional_qualification);
                            Constant.arraylistselectedadditionalqualificationID.add(model_additional_qualification.getId());
                            Constant.arraylistselectedadditionalqualification.add(model_additional_qualification.getName());
                        }
                    }

                    Constant.hashmap_additional_qualification.put(arraylistModeladditionalcredential.get(position).name
                            , true);
//                    viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.ic_checkbox_checked));
                    viewHolder.rel_topics.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_background_yellow_selected));
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return arraylistModeladditionalcredential.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private CheckBox cbselection;
        private TextView tv_additional_qualification;
        private RelativeLayout rel_topics, relChecked;


        private ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

//            cbselection = (CheckBox) itemView.findViewById(R.id.cbselection);
            tv_additional_qualification = (TextView) itemView.findViewById(R.id.tv_professional_credential);

            rel_topics = (RelativeLayout) itemView.findViewById(R.id.rel_topics);
            relChecked = (RelativeLayout) itemView.findViewById(R.id.relChecked);



        }
    }
}
