package com.entigrity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entigrity.R;
import com.entigrity.model.subjects_store.Model_Subject_Area;
import com.entigrity.utility.Constant;

import java.util.ArrayList;

public class TopicsFilterHomePopUpAdapter extends RecyclerView.Adapter<TopicsFilterHomePopUpAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Model_Subject_Area> arraylistModelSubjectArea = new ArrayList<>();
    LayoutInflater mInflater;


    public TopicsFilterHomePopUpAdapter(Context mContext, ArrayList<Model_Subject_Area> mList) {
        this.mContext = mContext;
        this.arraylistModelSubjectArea = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_row_proffesional_credential, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        final Model_Subject_Area model_subject_area = arraylistModelSubjectArea.get(position);

        if (!model_subject_area.getName().equalsIgnoreCase("")) {
            viewHolder.tv_professional_credential.setText(model_subject_area.getName());
        }

//        viewHolder.cbselection.setEnabled(false);


       // Boolean isChecked = Constant.hashmap_subject_home_area.get(model_subject_area.name);


        /*if (isChecked) {
            viewHolder.cbselection.setChecked(true);
        } else {
            viewHolder.cbselection.setChecked(false);
        }*/


        if (model_subject_area.isChecked()) {
//            viewHolder.cbselection.setChecked(true);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
        } else {
//            viewHolder.cbselection.setChecked(false);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));
        }


        viewHolder.tv_professional_credential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model_subject_area.isChecked()) {
                    model_subject_area.setChecked(false);

                    for (int i = 0; i < arraylistModelSubjectArea.size(); i++) {
                        if (model_subject_area.getId() == arraylistModelSubjectArea.get(i).getId()) {
                            arraylistModelSubjectArea.set(i, model_subject_area);
                        }
                    }

                    for (int k = 0; k < Constant.arraylistselectedsubjectareahomeID.size(); k++) {
                        if (model_subject_area.getId() == Constant.arraylistselectedsubjectareahomeID.get(k)) {
                            Constant.arraylistselectedsubjectareahomeID.remove(k);
                        }
                    }

                    Constant.hashmap_subject_home_area.put(arraylistModelSubjectArea.get(position).name
                            , false);

//                    viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));


                } else {
                    model_subject_area.setChecked(true);

                    for (int i = 0; i < arraylistModelSubjectArea.size(); i++) {
                        if (model_subject_area.getId() == arraylistModelSubjectArea.get(i).getId()) {
                            arraylistModelSubjectArea.set(i, model_subject_area);
                            Constant.arraylistselectedsubjectareahomeID.add(model_subject_area.getId());

                        }
                    }

                    Constant.hashmap_subject_home_area.put(arraylistModelSubjectArea.get(position).name
                            , true);
//                    viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                }


            }
        });



        viewHolder.rel_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (model_subject_area.isChecked()) {
                    model_subject_area.setChecked(false);

                    for (int i = 0; i < arraylistModelSubjectArea.size(); i++) {
                        if (model_subject_area.getId() == arraylistModelSubjectArea.get(i).getId()) {
                            arraylistModelSubjectArea.set(i, model_subject_area);
                        }
                    }

                    for (int k = 0; k < Constant.arraylistselectedsubjectareahomeID.size(); k++) {
                        if (model_subject_area.getId() == Constant.arraylistselectedsubjectareahomeID.get(k)) {
                            Constant.arraylistselectedsubjectareahomeID.remove(k);
                        }
                    }

                    Constant.hashmap_subject_home_area.put(arraylistModelSubjectArea.get(position).name
                            , false);

//                    viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));


                } else {
                    model_subject_area.setChecked(true);

                    for (int i = 0; i < arraylistModelSubjectArea.size(); i++) {
                        if (model_subject_area.getId() == arraylistModelSubjectArea.get(i).getId()) {
                            arraylistModelSubjectArea.set(i, model_subject_area);
                            Constant.arraylistselectedsubjectareahomeID.add(model_subject_area.getId());

                        }
                    }

                    Constant.hashmap_subject_home_area.put(arraylistModelSubjectArea.get(position).name
                            , true);
//                    viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                }


            }
        });





    }


    @Override
    public int getItemCount() {
        return arraylistModelSubjectArea.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private CheckBox cbselection;
        private TextView tv_professional_credential;
        private RelativeLayout rel_topics, relChecked;


        private ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

//            cbselection = (CheckBox) itemView.findViewById(R.id.cbselection);
            tv_professional_credential = (TextView) itemView.findViewById(R.id.tv_professional_credential);

            rel_topics = (RelativeLayout) itemView.findViewById(R.id.rel_topics);
            relChecked = (RelativeLayout) itemView.findViewById(R.id.relChecked);

        }
    }
}
