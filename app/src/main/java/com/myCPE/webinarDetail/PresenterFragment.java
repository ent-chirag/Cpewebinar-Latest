package com.myCPE.webinarDetail;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myCPE.R;
import com.myCPE.activity.SpeakerProfileActivity;
import com.myCPE.activity.WebinarDetailsActivity;
import com.myCPE.databinding.FragmentPresenterBinding;
import com.myCPE.model.webinar_details_new.WebinarDetail;
import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PresenterFragment extends Fragment {

    FragmentPresenterBinding binding;
    View view;
    private String email_id = "";
    private int speaker_id = 0;
    private int company_id = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_presenter, null, false);


        if (!WebinarDetailsActivity.getInstance().aboutpresentername.equalsIgnoreCase("")) {
            binding.tvPresenterName.setText(WebinarDetailsActivity.getInstance().aboutpresentername +
                    " " + WebinarDetailsActivity.getInstance().aboutpresenternameQualification);
        }

        if (WebinarDetailsActivity.getInstance().company_id != 0) {
            company_id = WebinarDetailsActivity.getInstance().company_id;
        }

        if (WebinarDetailsActivity.getInstance().speaker_id != 0) {
            speaker_id = WebinarDetailsActivity.getInstance().speaker_id;
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


        binding.relSpeakerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speaker_id != 0) {
                    Intent intent = new Intent(getActivity(), SpeakerProfileActivity.class);
                    intent.putExtra("speaker_id", speaker_id);
                    intent.putExtra("company_id", company_id);
                    startActivity(intent);
                } else {
                    Snackbar.make(binding.relSpeakerDetails, getResources().getString(R.string.str_speaker_id_not_found), Snackbar.LENGTH_SHORT).show();
                }
            }
        });


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
