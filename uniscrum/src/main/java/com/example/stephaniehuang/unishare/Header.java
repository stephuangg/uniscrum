package com.example.stephaniehuang.unishare;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by StephanieHuang on 4/24/17.
 */

public class Header extends Login {

    TextView getUsername;
    TextView arrowDown;
    Button b_sidePanel;
    SurfaceView sv_sidePanelExpanded;
    TextView tv_startProect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

//    public Header() {

        getUsername = (TextView) findViewById(R.id.textView_getUsername);
        getUsername.setText(USERNAME);

        sv_sidePanelExpanded = (SurfaceView) findViewById(R.id.surfaceView_sidePanelExpanded);
        sv_sidePanelExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_sidePanelExpanded.setVisibility(View.INVISIBLE);
                tv_startProect.setVisibility(View.INVISIBLE);
                b_sidePanel.setVisibility(View.VISIBLE);
            }
        });
        tv_startProect = (TextView) findViewById(R.id.textView_startNewProject);


        b_sidePanel = (Button) findViewById(R.id.button_sidePanel);
        b_sidePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_sidePanel.setVisibility(View.INVISIBLE);
                sv_sidePanelExpanded.setVisibility(View.VISIBLE);
                tv_startProect.setVisibility(View.VISIBLE);
            }
        });

//        arrowDown = (TextView) findViewById(R.id.textView_arrowDown);
//        arrowDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //open user profile page
//                Intent intent = new Intent (Header.this, UserProfile.class);
//                startActivity(intent);
//            }
//        });



    }

}
