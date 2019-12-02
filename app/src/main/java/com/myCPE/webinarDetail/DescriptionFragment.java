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
import com.myCPE.databinding.FragmentDescriptionBinding;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class DescriptionFragment extends Fragment {

    FragmentDescriptionBinding binding;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_description, null, false);


        if (!WebinarDetailsActivity.getInstance().programDescription.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.tvDescription.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().programDescription, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvDescription.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().programDescription));
            }


        }


        if (!WebinarDetailsActivity.getInstance().learningObjective.equalsIgnoreCase("")) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.tvLearningObjective.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvLearningObjective.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().learningObjective, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvLearningObjective.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().learningObjective));
            }


        }


        return view = binding.getRoot();
    }

}
