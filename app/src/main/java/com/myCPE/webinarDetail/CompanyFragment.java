package com.myCPE.webinarDetail;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.myCPE.R;
import com.myCPE.activity.CompanyProfileActivity;
import com.myCPE.activity.SpeakerProfileActivity;
import com.myCPE.activity.WebinarDetailsActivity;
import com.myCPE.databinding.FragmentCompanyDetailBinding;
import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class CompanyFragment extends Fragment {

    FragmentCompanyDetailBinding binding;

    private int company_id = 0;
    private int speaker_id = 0;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_detail, null, false);


        if (!WebinarDetailsActivity.getInstance().aboutpresenterCompanyName.equalsIgnoreCase("")) {
            binding.tvCompanyName.setText(WebinarDetailsActivity.getInstance().aboutpresenterCompanyName);
        }

        if (WebinarDetailsActivity.getInstance().company_id != 0) {
            company_id = WebinarDetailsActivity.getInstance().company_id;
        }

        if (WebinarDetailsActivity.getInstance().speaker_id != 0) {
            speaker_id = WebinarDetailsActivity.getInstance().speaker_id;
        }

        if (!WebinarDetailsActivity.getInstance().company_logo.equalsIgnoreCase("")) {
            Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().company_logo)
                    .placeholder(R.drawable.profile_place_holder)
                    .fit()
                    .into((binding.ivprofilepicture));
        } else {
            binding.ivprofilepicture.setImageResource(R.drawable.profile_place_holder);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvCompanyDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }


        if (!WebinarDetailsActivity.getInstance().aboutpresenterCompanyWebsite.equalsIgnoreCase("")) {
            binding.tvCompanyWebsite.setText(WebinarDetailsActivity.getInstance().aboutpresenterCompanyWebsite);
        }

        binding.relCompanyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speaker_id != 0) {
                    Intent intent = new Intent(getActivity(), CompanyProfileActivity.class);
                    intent.putExtra("speaker_id", speaker_id);
                    intent.putExtra("company_id", company_id);
                    startActivity(intent);
                } else {
                    Snackbar.make(binding.relCompanyDetails, getResources().getString(R.string.str_company_id_not_found), Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        if (!WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc.equalsIgnoreCase("")) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvCompanyDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc, Html.FROM_HTML_MODE_COMPACT));
//                binding.tvCompanyDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc, Html.FROM_HTML_MODE_LEGACY));
//                binding.tvCompanyDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc.toString()));
            } else {
                binding.tvCompanyDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc));
            }

        }


        return view = binding.getRoot();
    }


}
