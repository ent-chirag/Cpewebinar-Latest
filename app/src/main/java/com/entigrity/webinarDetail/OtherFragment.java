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
import com.entigrity.databinding.FragmentOtherBinding;
import com.entigrity.model.webinar_details_new.WebinarDetail;
import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class OtherFragment extends Fragment {

    FragmentOtherBinding binding;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, null, false);


        if (!WebinarDetailsActivity.getInstance().faq.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvFaq.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().faq, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvFaq.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().faq));
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvFaq.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvRefundCancelationPolicy.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.irsDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.nasbaDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.ctecDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }


        if (!WebinarDetailsActivity.getInstance().refund_and_cancelation.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvRefundCancelationPolicy.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().refund_and_cancelation, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvRefundCancelationPolicy.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().refund_and_cancelation));
            }

        }


        if (WebinarDetailsActivity.getInstance().getwebinar_type.equalsIgnoreCase("CPE/CE")) {

            binding.relNasba.setVisibility(View.VISIBLE);
            binding.relNasbaDesc.setVisibility(View.VISIBLE);

            binding.relIrs.setVisibility(View.VISIBLE);
            binding.relIrsDesc.setVisibility(View.VISIBLE);


            if (!WebinarDetailsActivity.getInstance().nasba_address.equalsIgnoreCase("")) {


                binding.nasbaAddress.setText(WebinarDetailsActivity.getInstance().nasba_address);
            }

            if (!WebinarDetailsActivity.getInstance().nasba_description.equalsIgnoreCase("")) {


                binding.nasbaDescription.setText(WebinarDetailsActivity.getInstance().nasba_description);
            }


            if (!WebinarDetailsActivity.getInstance().ea_address.equalsIgnoreCase("")) {


                binding.irsAddress.setText(WebinarDetailsActivity.getInstance().ea_address);
            }

            if (!WebinarDetailsActivity.getInstance().ea_description.equalsIgnoreCase("")) {


                binding.irsDescription.setText(WebinarDetailsActivity.getInstance().ea_description);
            }

            if (!WebinarDetailsActivity.getInstance().nasba_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().nasba_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivNasbaProfile));
            }

            if (!WebinarDetailsActivity.getInstance().ea_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().ea_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivIrsProfile));
            }


            ShowCtecApproved();


        } else if (WebinarDetailsActivity.getInstance().getwebinar_type.equalsIgnoreCase("CPE")) {

            binding.relNasba.setVisibility(View.VISIBLE);
            binding.relNasbaDesc.setVisibility(View.VISIBLE);

            binding.relIrs.setVisibility(View.GONE);
            binding.relIrsDesc.setVisibility(View.GONE);


            if (!WebinarDetailsActivity.getInstance().nasba_address.equalsIgnoreCase("")) {

                binding.nasbaAddress.setText(WebinarDetailsActivity.getInstance().nasba_address);
            }

            if (!WebinarDetailsActivity.getInstance().nasba_description.equalsIgnoreCase("")) {

                binding.nasbaDescription.setText(WebinarDetailsActivity.getInstance().nasba_description);
            }

            if (!WebinarDetailsActivity.getInstance().nasba_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().nasba_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivNasbaProfile));
            }

            ShowCtecApproved();


        } else if (WebinarDetailsActivity.getInstance().getwebinar_type.equalsIgnoreCase("CE")) {

            binding.relNasba.setVisibility(View.GONE);
            binding.relNasbaDesc.setVisibility(View.GONE);

            binding.relIrs.setVisibility(View.VISIBLE);
            binding.relIrsDesc.setVisibility(View.VISIBLE);

            if (!WebinarDetailsActivity.getInstance().ea_address.equalsIgnoreCase("")) {

                binding.irsAddress.setText(WebinarDetailsActivity.getInstance().ea_address);
            }

            if (!WebinarDetailsActivity.getInstance().ea_description.equalsIgnoreCase("")) {

                binding.irsDescription.setText(WebinarDetailsActivity.getInstance().ea_description);
            }
            if (!WebinarDetailsActivity.getInstance().ea_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().ea_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivIrsProfile));
            }

            ShowCtecApproved();


        }


        return view = binding.getRoot();
    }


    public void ShowCtecApproved() {

        if (!WebinarDetailsActivity.getInstance().ctec_course_id.equalsIgnoreCase("")) {
            binding.relCtec.setVisibility(View.VISIBLE);
            binding.relCtecDesc.setVisibility(View.VISIBLE);
        } else {
            binding.relCtec.setVisibility(View.GONE);
            binding.relCtecDesc.setVisibility(View.GONE);
        }


        if (!WebinarDetailsActivity.getInstance().ctec_address.equalsIgnoreCase("")) {

            binding.ctecAddress.setText(WebinarDetailsActivity.getInstance().ctec_address);
        }

        if (!WebinarDetailsActivity.getInstance().ctec_description.equalsIgnoreCase("")) {

            binding.ctecDescription.setText(WebinarDetailsActivity.getInstance().ctec_description);
        }
        if (!WebinarDetailsActivity.getInstance().ctec_profile_pic.equalsIgnoreCase("")) {
            Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().ctec_profile_pic)
                    .placeholder(R.mipmap.webinar_placeholder)
                    .into((binding.ivCtecProfile));
        }


    }
}
