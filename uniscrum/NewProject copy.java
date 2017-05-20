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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by StephanieHuang on 5/1/17.
 */

public class NewProject extends Login {
    EditText getSprintName, getSprintSDate, getSprintFolder, getSprintContributors;
    Button saveNewSprint;
    String sprintname, sprintstartdate, sprintfolder, sprintcontributors;
    File newProjectdirectory;

    //Header elements
    TextView getUsername;
    TextView arrowDown;
    Button b_sidePanel;
    SurfaceView sv_sidePanelExpanded;
    TextView tv_newProject;

    Boolean folderfound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);

        getSprintName = (EditText) findViewById(R.id.editText_getsprintName);
        getSprintSDate = (EditText) findViewById(R.id.editText_getsprintStartDate);
        getSprintFolder = (EditText) findViewById(R.id.editText_getsprintFolder);
        getSprintContributors = (EditText) findViewById(R.id.editText_getsprintContributors);
        saveNewSprint=(Button) findViewById(R.id.button_saveNewSprint);

        //set the sprint starting date to today
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String theday = formatter.format(today);
        getSprintSDate.setText(theday);

        saveNewSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET THE CONTENT INPUTTED BY THE USER
                sprintname = getSprintName.getText().toString();
                sprintstartdate = getSprintSDate.getText().toString();
                sprintfolder = getSprintFolder.getText().toString();
                if (sprintfolder.equals(null)) {
                    sprintfolder = "Uncategorized";
                }
                sprintcontributors = getSprintContributors.getText().toString();

                //find if folder exists in the file already
                for (int i=0; i<foldernames.size(); i++){
                    if (sprintfolder.equals(foldernames.elementAt(i))) {
                        folderfound = true;
                    }
                }

                if (folderfound) {
                    //if sprint folder not specified, create the sprint in the uncategroized directory
                    if (sprintfolder.equals("Uncategorized")) {
                        File newsprint = new File(Uncategorized.getAbsolutePath() + File.separator + sprintname);
                        //add the sprint into the current sprints file
                        String new_sprint_filepath = newsprint.getAbsolutePath();
                        try {
                            FileOutputStream fos = new FileOutputStream(currentsprints);
                            OutputStreamWriter oos = new OutputStreamWriter(fos);
                            oos.write(new_sprint_filepath + "\n");
                            oos.flush();
                            oos.close();
                            fos.flush();
                            fos.close();
                            Log.d("added to current sprint", new_sprint_filepath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                else {
                      //create a new folder directory
                    }


                //CREATING THE NEW PROJECT DIRECTORY
                if (PROJECTDIRECTORY.exists()){
                      newProjectdirectory = new File(PROJECTDIRECTORY.getAbsolutePath() + File.separator + projectname);
                    newProjectdirectory.mkdirs();
                }
                else {
                    Log.d("PROJECTDIRECTORY", "not found");
                }

                //CREATING THE DESCRIPTION FILE
                if (newProjectdirectory.exists()){
                    File description = new File (newProjectdirectory.getAbsolutePath() + File.separator + "description.txt");
                    try {
                        description.createNewFile();
                        FileOutputStream fos = new FileOutputStream(description);
                        OutputStreamWriter oos = new OutputStreamWriter(fos);
                        oos.write("NAME: " + projectname + "\n");
                        oos.write("STARTDATE: " + projectstartdate + "\n");
                        oos.write("ENDDATE: " + projectenddate + "\n");
                        oos.write("DESCRIPTION: " + projectdescription + "\n");
                        oos.flush();
                        oos.close();
                        fos.flush();
                        fos.close();
                        Log.d("createdfile ", projectdescription);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                //GO TO PROJECT DISPLAY PAGE
                Intent intent = new Intent (NewProject.this, ProjectDisplay.class);
                startActivity(intent);

            }
        });




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
                Intent intent = new Intent(NewProject.this, UserProfile.class);
                startActivity(intent);
            }
        });





    }

}
