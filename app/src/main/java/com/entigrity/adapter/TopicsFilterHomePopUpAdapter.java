package com.entigrity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entigrity.R;
import com.entigrity.model.Proffesional_Credential.Model_proffesional_Credential;
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

        viewHolder.cbselection.setEnabled(false);


        /*Boolean isChecked = Constant.hashmap_professional_credential.get(model_proffesional_credential.name);


        if (isChecked) {
            viewHolder.cbselection.setChecked(true);
        } else {
            viewHolder.cbselection.setChecked(false);
        }*/


        if (model_subject_area.isChecked()) {
            viewHolder.cbselection.setChecked(true);
        } else {
            viewHolder.cbselection.setChecked(false);
        }


        viewHolder.rel_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* if (model_proffesional_credential.isChecked()) {
                    model_proffesional_credential.setChecked(false);

                    for (int i = 0; i < arraylistModelProffesioanlCredential.size(); i++) {
                        if (model_proffesional_credential.getId() == arraylistModelProffesioanlCredential.get(i).getId()) {
                            arraylistModelProffesioanlCredential.set(i, model_proffesional_credential);
                        }
                    }

                    for (int k = 0; k < Constant.arraylistselectedproffesionalcredentialID.size(); k++) {
                        if (model_proffesional_credential.getId() == Constant.arraylistselectedproffesionalcredentialID.get(k)) {
                            Constant.arraylistselectedproffesionalcredentialID.remove(k);
                            Constant.arraylistselectedproffesionalcredential.remove(k);
                        }
                    }

                    Constant.hashmap_professional_credential.put(arraylistModelProffesioanlCredential.get(position).name
                            , false);

                    viewHolder.cbselection.setChecked(false);


                } else {
                    model_proffesional_credential.setChecked(true);

                    for (int i = 0; i < arraylistModelProffesioanlCredential.size(); i++) {
                        if (model_proffesional_credential.getId() == arraylistModelProffesioanlCredential.get(i).getId()) {
                            arraylistModelProffesioanlCredential.set(i, model_proffesional_credential);
                            Constant.arraylistselectedproffesionalcredentialID.add(model_proffesional_credential.getId());
                            Constant.arraylistselectedproffesionalcredential.add(model_proffesional_credential.getName());
                        }
                    }

                    Constant.hashmap_professional_credential.put(arraylistModelProffesioanlCredential.get(position).name
                            , true);
                    viewHolder.cbselection.setChecked(true);
                }*/


            }
        });


    }


    @Override
    public int getItemCount() {
        return arraylistModelSubjectArea.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox cbselection;
        private TextView tv_professional_credential;
        private RelativeLayout rel_topics;


        private ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

            cbselection = (CheckBox) itemView.findViewById(R.id.cbselection);
            tv_professional_credential = (TextView) itemView.findViewById(R.id.tv_professional_credential);

            rel_topics = (RelativeLayout) itemView.findViewById(R.id.rel_topics);


        }
    }
}
