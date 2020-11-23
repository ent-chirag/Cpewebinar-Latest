package com.myCPE.webinarDetail;

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

import com.myCPE.R;
import com.myCPE.activity.WebinarDetailsActivity;
import com.myCPE.databinding.FragmentOverviewoftopicsBinding;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class OverviewOfTopicsFragment extends Fragment {

    FragmentOverviewoftopicsBinding binding;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overviewoftopics, null, false);


        if (!WebinarDetailsActivity.getInstance().overviewoftopic.equalsIgnoreCase("")) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                binding.tvOverviewOfTopics.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvOverviewOfTopics.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().overviewoftopic, Html.FROM_HTML_MODE_COMPACT));
            } else {
                binding.tvOverviewOfTopics.setText(Html.fromHtml(WebinarDetailsActivity.getInstance().overviewoftopic));
            }

        }

        return view = binding.getRoot();
    }
}
