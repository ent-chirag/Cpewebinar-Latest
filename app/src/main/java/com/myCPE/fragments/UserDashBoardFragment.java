package com.myCPE.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myCPE.MainActivity;
import com.myCPE.R;
import com.myCPE.activity.NotificationActivity;
import com.myCPE.databinding.FragmentDashboardBinding;
import com.myCPE.utility.AppSettings;

import java.util.ArrayList;
import java.util.List;

import static com.myCPE.utility.Constant.checkmywebinardotstatusset;

public class UserDashBoardFragment extends Fragment {
    View view;
    private FragmentDashboardBinding binding;
    public Context context;
    private static UserDashBoardFragment instance;
    private static final String TAG = UserDashBoardFragment.class.getName();
    TextView tabadddot;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, null, false);
        context = getActivity();
        instance = UserDashBoardFragment.this;

        setupViewPager(binding.viewpager);
        binding.homeparenttabs.setupWithViewPager(binding.viewpager);


        MainActivity.getInstance().rel_top_bottom.setVisibility(View.VISIBLE);


        if (MainActivity.getInstance().setselectedtab == 1) {
            selectPage(1);
        } else {
            selectPage(0);
        }

       /* binding.ivfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!AppSettings.get_login_token(context).isEmpty()) {
                    arraylistselectedvalue.clear();
                    Intent i = new Intent(getActivity(), TopicsofInterestSignUpActivity.class);
                    i.putExtra(getResources().getString(R.string.str_get_key_screen_key), getResources().getString(R.string.from_home_screen));
                    startActivity(i);
                } else {
                    MainActivity.getInstance().ShowPopUp();
                }


            }
        });*/

        binding.ivnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AppSettings.get_login_token(context).isEmpty()) {
                    Intent i = new Intent(getActivity(), NotificationActivity.class);
                    startActivity(i);

                } else {
                    MainActivity.getInstance().ShowPopUp();
                }


            }
        });


        binding.getRoot().setFocusableInTouchMode(true);
        binding.getRoot().requestFocus();
        binding.getRoot().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                    ConfirmationPopup();
                    return true;
                }
                return false;
            }
        });


        return view = binding.getRoot();

    }

    public static UserDashBoardFragment getInstance() {
        return instance;

    }


    public void setupTabIcons(boolean isprogress) {
        tabadddot = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabadddot.setText(getActivity().getResources().getString(R.string.str_my_webinar));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 0);
        tabadddot.setLayoutParams(params);
        tabadddot.setPadding(0, 0, 0, 0);
        if (isprogress) {
            if (!checkmywebinardotstatusset) {
                binding.homeparenttabs.getTabAt(1).setCustomView(tabadddot);
                checkmywebinardotstatusset = true;
            }
        } else {
            checkmywebinardotstatusset = false;
            binding.homeparenttabs.getTabAt(1).setCustomView(null);
        }

    }


    public void selectPage(int pageIndex) {
        binding.homeparenttabs.setScrollPosition(pageIndex, 0f, true);
        binding.viewpager.setCurrentItem(pageIndex);
    }


    public void ConfirmationPopup() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage(getResources().getString(R.string.exit_validation));


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                // Write your code here to invoke YES event
                dialog.cancel();
                getActivity().finish();
                System.exit(0);


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HomeAllFragment(), getActivity().getResources().getString(R.string.str_all));
        adapter.addFragment(new MyWebinarFragment(), getActivity().getResources().getString(R.string.str_my_webinar));
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
