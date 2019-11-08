package com.entigrity.webinarDetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.entigrity.R;
import com.entigrity.activity.TestimonialActivity;
import com.entigrity.activity.WebinarDetailsActivity;
import com.entigrity.databinding.FragmentTestimonialBinding;
import com.entigrity.utility.Constant;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class TestimonialFragment extends Fragment {

    FragmentTestimonialBinding binding;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_testimonial, null, false);


        binding.tvViewMoreTestimonial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TestimonialActivity.class);
                i.putExtra(getResources().getString(R.string.pass_webinar_id), WebinarDetailsActivity
                        .getInstance().webinarid);
                startActivity(i);

            }
        });

        Constant.Log("size", "testimonial_size" + WebinarDetailsActivity.getInstance().webinartestimonial.size());


        if (WebinarDetailsActivity.getInstance().webinartestimonial.size() > 0) {

            if (WebinarDetailsActivity.getInstance().webinartestimonial.size() >= 2) {
                binding.tvViewMoreTestimonial.setVisibility(View.VISIBLE);
            } else {
                binding.tvViewMoreTestimonial.setVisibility(View.GONE);
            }


            binding.lvTetimonial.setVisibility(View.VISIBLE);


            for (int i = 0; i < WebinarDetailsActivity.getInstance().webinartestimonial.size(); i++) {


                LayoutInflater inflate = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View _itemRow = inflate.inflate(R.layout.row_tetimonial, null);


                final TextView tv_username_name = (TextView) _itemRow.findViewById(R.id.tv_username_name);
                final ImageView iv_testimonial_star = (ImageView) _itemRow.findViewById(R.id.iv_testimonial_star);
                final TextView tv_review_decription = (TextView) _itemRow.findViewById(R.id.tv_review_decription);
                final TextView tv_date = (TextView) _itemRow.findViewById(R.id.tv_date);
                final View viewBlack = (View) _itemRow.findViewById(R.id.viewBlack);

                if(i == 0){
                    if(WebinarDetailsActivity.getInstance().webinartestimonial.size()>1){
                        viewBlack.setVisibility(View.VISIBLE);
                    } else {
                        viewBlack.setVisibility(View.GONE);
                    }
                } else {
                    viewBlack.setVisibility(View.GONE);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tv_username_name.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tv_review_decription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                }


                if (!WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getFirstName().equalsIgnoreCase("")) {

                    tv_username_name.setText(WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getFirstName()
                            + " " + WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getLastName()
                            + " " + WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getDesignation());

                }


                if (!WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getDate().equalsIgnoreCase("")) {
                    tv_date.setText(WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getDate());
                }


                if (!WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getReview().equalsIgnoreCase("")) {
                    tv_review_decription.setText(WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getReview());
                }
                if (!WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getRate()
                        .equalsIgnoreCase("")) {

                    if (WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getRate().equalsIgnoreCase("0")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_zero);

                    } else if (WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getRate().equalsIgnoreCase("1")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_one);
                    } else if (WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getRate().equalsIgnoreCase("2")) {
                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_two);

                    } else if (WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getRate().equalsIgnoreCase("3")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_three);

                    } else if (WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getRate().equalsIgnoreCase("4")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_four);

                    } else if (WebinarDetailsActivity.getInstance().webinartestimonial.get(i).getRate().equalsIgnoreCase("5")) {

                        iv_testimonial_star.setImageResource(R.mipmap.rev_star_five);
                    }


                }


                binding.lvTestimonialSet.addView(_itemRow);


            }
        } else {
            binding.lvTetimonial.setVisibility(View.GONE);

        }


        return view = binding.getRoot();
    }

}
