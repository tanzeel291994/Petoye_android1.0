package com.startup.projectfinal.peyoye;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_screen);

        ReportScreenFirstFragment reportScreenFirstFragment=new ReportScreenFirstFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.report_fragment_container, reportScreenFirstFragment);
        ft.addToBackStack(null); // it will manage back stack of fragments.
        ft.commit();
    }
    /**
     * A ReportScreenFirstFragment containing a List of reasons for reporting
     */
    public static class ReportScreenFirstFragment extends Fragment{

        Activity thisActivity=this.getActivity();

        public ReportScreenFirstFragment() {}

        public static ReportScreenFirstFragment newInstance() {
            ReportScreenFirstFragment reportScreenFirstFragment = new ReportScreenFirstFragment();
            return reportScreenFirstFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_first_report_screen, container, false);

            //set listener for cancel button
            Button cancel_button=(Button)rootView.findViewById(R.id.cancel_button);
            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            //custom function to set listeners for reason buttons
            setListenersForReasonButton(rootView);

            return rootView;
        }

        public void setListenersForReasonButton(View rootView){

            View.OnClickListener onClickListener = new View.OnClickListener() {
                ReportScreenSecondFragment reportScreenSecondFragment = new ReportScreenSecondFragment();  //this is your new fragment.
                Bundle args=new Bundle();

                @Override
                public void onClick(View v) {
                    switch(v.getId()){
                        case R.id.reason1:
                            args.putString("reason","Spam");
                            break;
                        case R.id.reason2:
                            args.putString("reason","Vulgar Image");
                            break;
                        case R.id.reason3:
                            args.putString("reason","Hate Speech");
                            break;
                        case R.id.reason4:
                            args.putString("reason","Did not Like it");
                            break;
                    }

                    reportScreenSecondFragment.setArguments(args);

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                    ft.replace(R.id.report_fragment_container, reportScreenSecondFragment);
                    ft.addToBackStack(null); // it will manage back stack of fragments.
                    ft.commit();
                }

            };

            Button btnreason1=(Button)rootView.findViewById(R.id.reason1);
            Button btnreason2=(Button)rootView.findViewById(R.id.reason2);
            Button btnreason3=(Button)rootView.findViewById(R.id.reason3);
            Button btnreason4=(Button)rootView.findViewById(R.id.reason4);

            btnreason1.setOnClickListener(onClickListener);
            btnreason2.setOnClickListener(onClickListener);
            btnreason3.setOnClickListener(onClickListener);
            btnreason4.setOnClickListener(onClickListener);

        }
    }

    // confirmation Screen.
    public static class ReportScreenSecondFragment extends Fragment{

        public ReportScreenSecondFragment() {}

        public static ReportScreenSecondFragment newInstance() {
            ReportScreenSecondFragment reportScreenSecondFragment = new ReportScreenSecondFragment();
            return reportScreenSecondFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_second_report_screen, container, false);

            // set text for confirmation
            TextView report_msg=(TextView)rootView.findViewById(R.id.report_msg);
            report_msg.setText("Report as "+getArguments().getString("reason")+" ?");

            // set listener for report button
            Button btnreport=(Button)rootView.findViewById(R.id.report_button);
            btnreport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"Post has been Reported",Toast.LENGTH_SHORT).show();
                }
            });

            //set listener for cancel button
            Button cancel_button=(Button)rootView.findViewById(R.id.cancel_button);
            cancel_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

            //set listener for back button
            ImageButton btngoback=(ImageButton)rootView.findViewById(R.id.back_button);
            btngoback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });

            return rootView;
        }
    }
}
