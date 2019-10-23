package com.entigrity.webinarDetail;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.entigrity.R;
import com.entigrity.activity.WebinarDetailsActivity;
import com.entigrity.databinding.FragmentCompanyDetailBinding;
import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class CompanyFragment extends Fragment {

    FragmentCompanyDetailBinding binding;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_company_detail, null, false);


        if (!WebinarDetailsActivity.getInstance().aboutpresenterCompanyName.equalsIgnoreCase("")) {
            binding.tvCompanyName.setText(WebinarDetailsActivity.getInstance().aboutpresenterCompanyName);
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


        if (!WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc.equalsIgnoreCase("")) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvCompanyDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvCompanyDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenterCompanyDesc));
            }

        }


        return view = binding.getRoot();
    }


}
