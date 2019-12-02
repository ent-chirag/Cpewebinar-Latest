package com.myCPE.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.myCPE.R;
import com.myCPE.activity.PdfViewActivity;
import com.myCPE.model.homewebinarnew.MyCertificateLinksItem;


import java.util.ArrayList;
import java.util.List;

public class CertificatesListHomeMyWebinarPopUpAdapter extends RecyclerView.Adapter<CertificatesListHomeMyWebinarPopUpAdapter.ViewHolder> {

    private Context mContext;
    private List<MyCertificateLinksItem> arraylistMyCreditsCertificateItem = new ArrayList<>();

    LayoutInflater mInflater;


    public CertificatesListHomeMyWebinarPopUpAdapter(Context mContext, List<MyCertificateLinksItem> mList) {
        this.mContext = mContext;
        this.arraylistMyCreditsCertificateItem = mList;
        mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_row_certificate_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

        if (!arraylistMyCreditsCertificateItem.get(position).getCertificateType().equalsIgnoreCase("")) {

            viewHolder.tvCertificateType.setText(arraylistMyCreditsCertificateItem.get(position).getCertificateType());

        }

        viewHolder.btn_certification_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, PdfViewActivity.class);
                i.putExtra(mContext.getResources().getString(R.string.str_document_link), arraylistMyCreditsCertificateItem
                        .get(position).getCertificateLink());
                i.putExtra(mContext.getResources().getString(R.string.str_pdf_view_titile), mContext.getString(R.string.str_certificate));
                i.putExtra(mContext.getString(R.string.pass_webinar_type),"");
                mContext.startActivity(i);

            }
        });


    }


    @Override
    public int getItemCount() {
        return arraylistMyCreditsCertificateItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvCertificateType;
        public Button btn_certification_download;


        private ViewHolder(View itemView) {
            super(itemView);

            tvCertificateType = (TextView) itemView.findViewById(R.id.tvCertificateType);
            btn_certification_download = (Button) itemView.findViewById(R.id.btn_certification_download);


        }
    }
}
