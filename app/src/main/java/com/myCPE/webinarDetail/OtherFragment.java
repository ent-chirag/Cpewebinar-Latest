package com.myCPE.webinarDetail;

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

import com.myCPE.R;
import com.myCPE.activity.WebinarDetailsActivity;
import com.myCPE.databinding.FragmentOtherBinding;
import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class OtherFragment extends Fragment {

    FragmentOtherBinding binding;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_other, null, false);

        // For Commit..
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
            binding.eaDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
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

            // Previously we are showing the ea approved data there in CPE/CE and only CE conditions and not showing this in CPE type only
            // Now instead of showing data for EA Approved we have to show IRS Data there only.. So have to Hide EA Approved Data
            binding.relEa.setVisibility(View.GONE);
            binding.relEaDesc.setVisibility(View.GONE);
            binding.relIrs.setVisibility(View.VISIBLE);
            binding.relIrsDesc.setVisibility(View.VISIBLE);


            if (!WebinarDetailsActivity.getInstance().nasba_address.equalsIgnoreCase("")) {


                binding.nasbaAddress.setText(WebinarDetailsActivity.getInstance().nasba_address);
            }

            if (!WebinarDetailsActivity.getInstance().nasba_description.equalsIgnoreCase("")) {


                binding.nasbaDescription.setText(WebinarDetailsActivity.getInstance().nasba_description);
            }


            if (!WebinarDetailsActivity.getInstance().ea_address.equalsIgnoreCase("")) {


                binding.eaAddress.setText(WebinarDetailsActivity.getInstance().ea_address);
            }

            if (!WebinarDetailsActivity.getInstance().ea_description.equalsIgnoreCase("")) {


                binding.eaDescription.setText(WebinarDetailsActivity.getInstance().ea_description);
            }

            if (!WebinarDetailsActivity.getInstance().nasba_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().nasba_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivNasbaProfile));
            }

            if (!WebinarDetailsActivity.getInstance().nasba_profile_pic_qas.equalsIgnoreCase("")) {
                binding.ivNasbaProfileQas.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().nasba_profile_pic_qas)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivNasbaProfileQas));
            } else {
                binding.ivNasbaProfileQas.setVisibility(View.GONE);
            }

            if (!WebinarDetailsActivity.getInstance().ea_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().ea_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivEaProfile));
            }


            ShowCtecApproved();
            showIrsApproved();


        } else if (WebinarDetailsActivity.getInstance().getwebinar_type.equalsIgnoreCase("CPE")) {

            binding.relNasba.setVisibility(View.VISIBLE);
            binding.relNasbaDesc.setVisibility(View.VISIBLE);

            binding.relEa.setVisibility(View.GONE);
            binding.relEaDesc.setVisibility(View.GONE);
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

            if (!WebinarDetailsActivity.getInstance().nasba_profile_pic_qas.equalsIgnoreCase("")) {
                binding.ivNasbaProfileQas.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().nasba_profile_pic_qas)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivNasbaProfileQas));
            } else {
                binding.ivNasbaProfileQas.setVisibility(View.GONE);
            }


            ShowCtecApproved();
            showIrsApproved();


        } else if (WebinarDetailsActivity.getInstance().getwebinar_type.equalsIgnoreCase("CE")) {

            binding.relNasba.setVisibility(View.GONE);
            binding.relNasbaDesc.setVisibility(View.GONE);

            // Previously we are showing the ea approved data there in CPE/CE and only CE conditions and not showing this in CPE type only
            // Now instead of showing data for EA Approved we have to show IRS Data there only.. So have to Hide EA Approved Data
            binding.relEa.setVisibility(View.GONE);
            binding.relEaDesc.setVisibility(View.GONE);
            binding.relIrs.setVisibility(View.VISIBLE);
            binding.relIrsDesc.setVisibility(View.VISIBLE);

            if (!WebinarDetailsActivity.getInstance().ea_address.equalsIgnoreCase("")) {

                binding.eaAddress.setText(WebinarDetailsActivity.getInstance().ea_address);
            }

            if (!WebinarDetailsActivity.getInstance().ea_description.equalsIgnoreCase("")) {

                binding.eaDescription.setText(WebinarDetailsActivity.getInstance().ea_description);
            }
            if (!WebinarDetailsActivity.getInstance().ea_profile_pic.equalsIgnoreCase("")) {
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().ea_profile_pic)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivEaProfile));
            }

            if (!WebinarDetailsActivity.getInstance().nasba_profile_pic_qas.equalsIgnoreCase("")) {
                binding.ivNasbaProfileQas.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().nasba_profile_pic_qas)
                        .placeholder(R.mipmap.webinar_placeholder)
                        .into((binding.ivNasbaProfileQas));
            } else {
                binding.ivNasbaProfileQas.setVisibility(View.GONE);
            }


            ShowCtecApproved();
            showIrsApproved();


        }


        return view = binding.getRoot();
    }

    private void showIrsApproved() {

        if (!WebinarDetailsActivity.getInstance().irs_address.equalsIgnoreCase("")) {
            binding.irsAddress.setText(WebinarDetailsActivity.getInstance().irs_address);
        }

        if (!WebinarDetailsActivity.getInstance().irs_description.equalsIgnoreCase("")) {
            binding.irsDescription.setText(WebinarDetailsActivity.getInstance().irs_description);
        }

        if (!WebinarDetailsActivity.getInstance().irs_profile_pic.equalsIgnoreCase("")) {
            Picasso.with(getActivity()).load(WebinarDetailsActivity.getInstance().irs_profile_pic)
                    .placeholder(R.mipmap.webinar_placeholder)
                    .into((binding.ivIrsProfile));
        }

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
