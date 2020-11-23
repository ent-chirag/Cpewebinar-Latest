package com.myCPE.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.myCPE.R;
import com.myCPE.model.Proffesional_Credential.Model_proffesional_Credential;
import com.myCPE.utility.Constant;

import java.util.ArrayList;

public class EditProffesionalCredentialPopUpAdapter extends RecyclerView.Adapter<EditProffesionalCredentialPopUpAdapter.ViewHolder> {

    private Context mContext;

    private ArrayList<Model_proffesional_Credential> arraylistModelProffesioanlCredential = new ArrayList<>();


    LayoutInflater mInflater;


    public EditProffesionalCredentialPopUpAdapter(Context mContext, ArrayList<Model_proffesional_Credential> mList) {
        this.mContext = mContext;
        this.arraylistModelProffesioanlCredential = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_row_proffesional_credential, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        final Model_proffesional_Credential model_proffesional_credential = arraylistModelProffesioanlCredential.get(position);

        if (!model_proffesional_credential.getName().equalsIgnoreCase("")) {
            viewHolder.tv_professional_credential.setText(model_proffesional_credential.getName());
        }

//        viewHolder.cbselection.setEnabled(false);


        Boolean isChecked = Constant.hashmap_professional_credential.get(model_proffesional_credential.name);

        if (isChecked) {
//            viewHolder.cbselection.setChecked(true);
//            viewHolder.cbselection.setChecked(true);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
            model_proffesional_credential.setChecked(true);

        } else {
//            viewHolder.cbselection.setChecked(false);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));
        }

        if (model_proffesional_credential.isChecked()) {
//            viewHolder.cbselection.setChecked(true);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
        } else {
//            viewHolder.cbselection.setChecked(false);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));
        }



        viewHolder.tv_professional_credential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("*+*+*", "Clicked on the TextView Position : " + position + "Checked Status : " + viewHolder.isCheckedS);
                /*if (!viewHolder.isCheckedS) {
//            viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                    viewHolder.isCheckedS = true;
                    model_proffesional_credential.setChecked(true);
                } else {
//            viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));
                    viewHolder.isCheckedS = false;
                    model_proffesional_credential.setChecked(false);
                }*/

                if (model_proffesional_credential.isChecked()) {
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

//                    viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));

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
//                    viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                }
            }
        });

        /*viewHolder.cbselection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("*+*+*","Clicked on the Checkbox for the Position : "+position);
            }
        });*/

        viewHolder.rel_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("*+*+*", "Clicked on the rel_topics Position : " + position + "Checked Status : " + viewHolder.isCheckedS);
                /*if (!viewHolder.isCheckedS) {
//            viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                    viewHolder.isCheckedS = true;
                    model_proffesional_credential.setChecked(true);
                } else {
//            viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));
                    viewHolder.isCheckedS = false;
                    model_proffesional_credential.setChecked(false);
                }*/

                // Chirag Logic..
                if (model_proffesional_credential.isChecked()) {
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

//                    viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));

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
//                    viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                }
            }
        });

        /*viewHolder.relChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("*+*+*","Clicked on the relChecked Position : "+position+ "Checked Status : " +viewHolder.isCheckedS);
                if (!viewHolder.isCheckedS) {
//            viewHolder.cbselection.setChecked(true);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                    viewHolder.isCheckedS = true;
                } else {
//            viewHolder.cbselection.setChecked(false);
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));
                    viewHolder.isCheckedS = false;
                }
            }
        });*/

        /*viewHolder.rel_topics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("*+*+*","Clicked on Position : "+position);
                if (model_proffesional_credential.isChecked()) {
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
                }


            }
        });*/


    }


    @Override
    public int getItemCount() {
        return arraylistModelProffesioanlCredential.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        private CheckBox cbselection;
        private TextView tv_professional_credential;
        private RelativeLayout rel_topics, relChecked;
        private Boolean isCheckedS = false;


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
