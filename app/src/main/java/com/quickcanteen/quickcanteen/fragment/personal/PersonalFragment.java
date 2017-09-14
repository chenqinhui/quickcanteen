package com.quickcanteen.quickcanteen.fragment.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.quickcanteen.quickcanteen.R;
import com.quickcanteen.quickcanteen.actions.user.IUserAction;
import com.quickcanteen.quickcanteen.actions.user.impl.UserActionImpl;
import com.quickcanteen.quickcanteen.activities.userinformation.UserInformation;
import com.quickcanteen.quickcanteen.bean.UserInfoBean;
import com.quickcanteen.quickcanteen.utils.BaseJson;


public class PersonalFragment extends Fragment {

    private UserInfoBean userInfo;
    private static Handler handler = new Handler();
    private Button button_set_information;
    private Button button_to_help, button_to_advice;
    private TextView points;
    private IUserAction userAction = new UserActionImpl(getActivity());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal_page, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userAction = new UserActionImpl(getActivity());
        button_set_information = (Button) getActivity().findViewById(R.id.userName);
        points = (TextView) getActivity().findViewById(R.id.points);
        button_set_information.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), UserInformation.class);
                startActivity(intent);

            }
        });

        /*Button button_to_assess=(Button)findViewById(R.id.assess_jump);
        button_to_assess.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                .intent.setClass(PersonalFragment.this,Assess.class);
                startActivity(intent);
            }
        });

        Button button_to_collect=(Button)findViewById(R.id.collect_jump);
        button_to_collect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(PersonalFragment.this,Collect.class);
                startActivity(intent);

            }
        });
*/
        /*button_to_help=(Button)getActivity().findViewById(R.id.help_jump);
        button_to_help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),Help.class);
                startActivity(intent);

            }
        });

        button_to_advice=(Button)getActivity().findViewById(R.id.advice_jump);
        button_to_advice.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),Advice.class);
                startActivity(intent);

            }
        });*/

        new Thread(new MyThread()).start();
    }

    public class MyThread implements Runnable {
        @Override
        public void run() {
            try {
                BaseJson baseJson = userAction.getCurrentUserInfo();

                userInfo = new UserInfoBean(baseJson.getJSONObject());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        button_set_information.setText(userInfo.getRealName());
                        //points.setText(userInfo.getPoints().toString()+"分");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

}
