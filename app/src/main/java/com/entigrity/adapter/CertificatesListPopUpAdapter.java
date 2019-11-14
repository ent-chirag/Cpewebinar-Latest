package com.entigrity.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.entigrity.R;
import com.entigrity.model.MyCreditNew.ModelMyCertificateLinksItem;
import com.entigrity.model.MyCreditNew.MyCertificateLinksItem;

import java.util.ArrayList;

public class CertificatesListPopUpAdapter extends RecyclerView.Adapter<CertificatesListPopUpAdapter.ViewHolder> {

    private Context mContext;

//    private ArrayList<MyCreditsItem> arraylistMyCreditsItem = new ArrayList<>();
//    private ArrayList<MyCertificateLinksItem> arraylistMyCreditsItem = new ArrayList<>();
    private ArrayList<ModelMyCertificateLinksItem> arraylistMyCreditsItem = new ArrayList<>();

    LayoutInflater mInflater;


//    public CertificatesListPopUpAdapter(Context mContext, ArrayList<MyCreditsItem> mList) {
//    public CertificatesListPopUpAdapter(Context mContext, ArrayList<MyCertificateLinksItem> mList) {
    public CertificatesListPopUpAdapter(Context mContext, ArrayList<ModelMyCertificateLinksItem> mList) {
        this.mContext = mContext;
        this.arraylistMyCreditsItem = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_row_certificate_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

//        final MyCreditsItem model_proffesional_credential = arraylistModelProffesioanlCredential.get(position);
//        final MyCertificateLinksItem myCreditsItem = arraylistMyCreditsItem.get(position);
        final ModelMyCertificateLinksItem myCreditsItem = arraylistMyCreditsItem.get(position);

        viewHolder.tvCertificateType.setText(myCreditsItem.getCertificateType());

        /*if (model_proffesional_credential.isChecked()) {
//            viewHolder.cbselection.setChecked(true);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
        } else {
//            viewHolder.cbselection.setChecked(false);
            viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_not_select));
        }*/

        /*viewHolder.tv_professional_credential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("*+*+*","Clicked on the TextView Position : "+position+ "Checked Status : " +viewHolder.isCheckedS);

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
                    viewHolder.relChecked.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.blue_select));
                }
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return arraylistMyCreditsItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
//        private CheckBox cbselection;
        private TextView tvCertificateType;
        private RelativeLayout relChecked;
        private Boolean isCheckedS = false;


        private ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);

//            cbselection = (CheckBox) itemView.findViewById(R.id.cbselection);
            tvCertificateType = (TextView) itemView.findViewById(R.id.tvCertificateType);
            relChecked = (RelativeLayout) itemView.findViewById(R.id.relChecked);
        }
    }
}
