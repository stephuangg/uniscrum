package com.example.stephaniehuang.unishare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by StephanieHuang on 5/1/17.
 */

public class ProjectDisplay extends Login {

    //    Thread runHeader = new Thread (new Runnable () {
//        public void run () {
//            Header h1 = new Header ();
//
//        }
//    });
    //Header
    TextView getUsername;
    TextView arrowDown;
    Button b_sidePanel;
    SurfaceView sv_sidePanelExpanded;
    TextView tv_newProject;

    //Project infos
    EditText setProjectName, setProjectStartDate, setProjectEndDate, setProjectDescription;
    String projectname, projectstartdate, projectenddate, projectdescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectdisplay);

        /*************************************************************
         HEADER PORTION
         **************************************************************/
        tv_newProject = (TextView) findViewById(R.id.textView_startNewProject);

        getUsername = (TextView) findViewById(R.id.textView_getUsername);
        getUsername.setText(USERNAME);

        sv_sidePanelExpanded = (SurfaceView) findViewById(R.id.surfaceView_sidePanelExpanded);
        sv_sidePanelExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_sidePanelExpanded.setVisibility(View.INVISIBLE);
                tv_newProject.setVisibility(View.INVISIBLE);
                b_sidePanel.setVisibility(View.VISIBLE);
            }
        });


        b_sidePanel = (Button) findViewById(R.id.button_sidePanel);
        b_sidePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_sidePanel.setVisibility(View.INVISIBLE);
                sv_sidePanelExpanded.setVisibility(View.VISIBLE);
                tv_newProject.setVisibility(View.VISIBLE);
            }
        });

        arrowDown = (TextView) findViewById(R.id.textView_arrowDown);
        arrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open user profile page
                Intent intent = new Intent(ProjectDisplay.this, UserProfile.class);
                startActivity(intent);
            }
        });

        tv_newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectDisplay.this, NewProject.class);
                startActivity(intent);
            }
        });


        /*************************************************************
         PROJECT PORTION
         **************************************************************/
//        setProjectName = (EditText) findViewById(R.id.editText_projectName);
//        setProjectStartDate = (EditText) findViewById(R.id.editText_projectStartDate);
//        setProjectEndDate = (EditText) findViewById(R.id.editText_projectEndDate);
//        setProjectDescription = (EditText) findViewById(R.id.editText_projectDescription);
//
//
//        //Reading files of projects to display
//        if (PROJECTDIRECTORY.exists()) {
//            File[] listofprojects = PROJECTDIRECTORY.listFiles();
//            Arrays.sort(listofprojects);
//
//            for (int i = 0; i < listofprojects.length; i++) {
//                //open the project description files
//                File descriptors = new File(listofprojects[i].getAbsolutePath() + File.separator + "description.txt");
//                try {
//                    InputStream inputStream = new BufferedInputStream(new FileInputStream(descriptors));
//                    BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuilder total = new StringBuilder();
//                    String line;
//                    while ((line = r.readLine()) != null) {
//                        if (line.charAt(0) == 'N' && line.charAt(4) == ':') {
//                            projectname = line.substring(6, line.length());
//                        }
//
//                        if (line.charAt(0) == 'S' && line.charAt(9) == ':') {
//                            projectstartdate = line.substring(11, line.length());
//                        }
//
//                        if (line.charAt(0) == 'E' && line.charAt(7) == ':') {
//                            projectenddate = line.substring(9, line.length());
//                        }
//
//                        if (line.charAt(0) == 'D' && line.charAt(11) == ':') {
//                            projectenddate = line.substring(13, line.length());
//                        }
//
//                    }
//
//                    setProjectDescription.setText(projectname);
//                    setProjectStartDate.setText(projectstartdate);
//                    setProjectEndDate.setText(projectenddate);
//                    setProjectDescription.setText(projectdescription);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }


            }


        }
    //}
//}
