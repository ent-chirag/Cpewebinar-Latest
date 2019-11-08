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
import com.entigrity.databinding.FragmentWhyyoushouldattendBinding;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class WhyYouShouldAttend extends Fragment {
    FragmentWhyyoushouldattendBinding binding;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_whyyoushouldattend, null, false);


        if (!WebinarDetailsActivity.getInstance().whyshouldattend.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.tvWhyYouShouldAttend.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvWhyYouShouldAttend.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().whyshouldattend, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvWhyYouShouldAttend.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().whyshouldattend));
            }

        }


        return view = binding.getRoot();
    }
}
