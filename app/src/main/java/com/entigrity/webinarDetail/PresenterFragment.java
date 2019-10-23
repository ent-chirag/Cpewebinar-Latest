package com.entigrity.webinarDetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
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
import com.entigrity.databinding.FragmentPresenterBinding;
import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PresenterFragment extends Fragment {

    FragmentPresenterBinding binding;
    View view;
    private String email_id = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_presenter, null, false);


        if (!WebinarDetailsActivity.getInstance().aboutpresentername.equalsIgnoreCase("")) {
            binding.tvPresenterName.setText(WebinarDetailsActivity.getInstance().aboutpresentername +
                    " " + WebinarDetailsActivity.getInstance().aboutpresenternameQualification);
        }

        if (!WebinarDetailsActivity.getInstance().presenter_image.equalsIgnoreCase("")) {
            Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().presenter_image)
                    .placeholder(R.drawable.profile_place_holder)
                    .fit()
                    .into((binding.ivprofilepicture));
        } else {
            binding.ivprofilepicture.setImageResource(R.drawable.profile_place_holder);
        }


        if (!WebinarDetailsActivity.getInstance().aboutpresenterDesgnination.equalsIgnoreCase("")) {
            binding.tvDesignationCompany.setText(WebinarDetailsActivity.getInstance().aboutpresenterDesgnination
                    + ", " + WebinarDetailsActivity.getInstance().aboutpresenterCompanyName);
        }


        if (!WebinarDetailsActivity.getInstance().aboutpresenterEmailId.equalsIgnoreCase("")) {
            email_id = WebinarDetailsActivity.getInstance().aboutpresenterEmailId;
            binding.tvPresenterEmailid.setText(WebinarDetailsActivity.getInstance().aboutpresenterEmailId);
        }


        if (!WebinarDetailsActivity.getInstance().aboutpresenternameSpeakerDesc.equalsIgnoreCase("")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.tvAboutPresenter.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvAboutPresenter.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenternameSpeakerDesc, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvAboutPresenter.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().aboutpresenternameSpeakerDesc));
            }

        }

        binding.tvPresenterEmailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!email_id.equalsIgnoreCase("")) {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", email_id, null));
                    startActivity(Intent.createChooser(emailIntent, "mail"));

                }


            }
        });


        return view = binding.getRoot();
    }


}
