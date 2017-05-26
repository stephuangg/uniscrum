package com.example.stephaniehuang.unishare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
 *
 * This class collects information for new sprints,
 * including sprint name, sprint start date, sprint contributors and sprint folder.
 */

public class NewProject extends Login {
    EditText getSprintName, getSprintSDate, getSprintContributors;
    Button saveNewSprint;
    String sprintname, sprintstartdate, sprintfolder, sprintcontributors;
    Spinner folder_spinner;

    //HEADER ELEMENTS
    TextView getUsername;
    TextView arrowDown;
    Button b_sidePanel;
    SurfaceView sv_sidePanelExpanded;
    TextView tv_newProject;
    TextView tv_createFolder;
    EditText et_createFolder;


    public void create_basic_files_for_sprint (String filepath) {
        File infoFile = new File(filepath + File.separator + "info.txt");
        File taskAssignmentFile = new File(filepath + File.separator + "tasks.txt");
        try {
            infoFile.createNewFile();
            taskAssignmentFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(infoFile);
            OutputStreamWriter oos = new OutputStreamWriter(fos);
            oos.write("NAME: " + sprintname + "\n");
            oos.write("START: " + sprintstartdate + "\n");
            oos.write("CONTRIBUTORS: " + sprintcontributors + "\n");
            oos.flush();
            oos.close();
            fos.flush();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("!!!!", "THE INFO OR TASK TEXT FILE FOR SPRINT NOT CREATED");
        }
    }

    public void record_sprint_to_current_file (String filepath) {
        try {
            FileOutputStream fos = new FileOutputStream(currentsprints);
            OutputStreamWriter oos = new OutputStreamWriter(fos);
            oos.write(filepath + "\n");
            oos.flush();
            oos.close();
            fos.flush();
            fos.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);

        getSprintName = (EditText) findViewById(R.id.editText_getsprintName);
        getSprintSDate = (EditText) findViewById(R.id.editText_getsprintStartDate);
        //getSprintFolder = (EditText) findViewById(R.id.editText_getsprintFolder);
        folder_spinner = (Spinner) findViewById(R.id.spinner_folder);
        getSprintContributors = (EditText) findViewById(R.id.editText_getsprintContributors);
        saveNewSprint=(Button) findViewById(R.id.button_saveNewSprint);

        //set the sprint starting date to today
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        String theday = formatter.format(today);
        getSprintSDate.setText(theday);

        //load the spinner with folder options from the vector foldernames
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, foldernames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        folder_spinner.setAdapter(adapter);
        folder_spinner.setPrompt("Folder");


        saveNewSprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET THE CONTENT INPUTTED BY THE USER
                sprintname = getSprintName.getText().toString();
                sprintstartdate = getSprintSDate.getText().toString();
                sprintfolder = folder_spinner.getSelectedItem().toString();
                sprintcontributors = getSprintContributors.getText().toString();


                //if sprint folder not specified, create the sprint in the uncategroized directory
                if (sprintfolder.equals("Uncategorized")) {
                    File newsprint = new File(Uncategorized.getAbsolutePath() + File.separator + sprintname);
                    newsprint.mkdirs();
                    String new_sprint_folder_filepath = newsprint.getAbsolutePath();
                    create_basic_files_for_sprint(new_sprint_folder_filepath);
                    record_sprint_to_current_file(new_sprint_folder_filepath);
                }
                else {
                    //else create the sprint in the specified folder
                    File newsprint = new File(SPRINTDIRECTORY.getAbsolutePath() + File.separator + sprintfolder + File.separator + sprintname);
                    newsprint.mkdirs();
                    String new_sprint_folder_filepath = newsprint.getAbsolutePath();
                    create_basic_files_for_sprint(new_sprint_folder_filepath);
                    record_sprint_to_current_file(new_sprint_folder_filepath);

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
        tv_createFolder = (TextView) findViewById(R.id.textView_createFolder);
        et_createFolder = (EditText) findViewById(R.id.editText_createFolder);

        getUsername = (TextView) findViewById(R.id.textView_getUsername);
        getUsername.setText(firstname + " " + lastname);

        sv_sidePanelExpanded = (SurfaceView) findViewById(R.id.surfaceView_sidePanelExpanded);
        sv_sidePanelExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_sidePanelExpanded.setVisibility(View.INVISIBLE);
                tv_newProject.setVisibility(View.INVISIBLE);
                b_sidePanel.setVisibility(View.VISIBLE);
                tv_createFolder.setVisibility(View.INVISIBLE);
                et_createFolder.setVisibility(View.INVISIBLE);
            }
        });


        b_sidePanel = (Button) findViewById(R.id.button_sidePanel);
        b_sidePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_sidePanel.setVisibility(View.INVISIBLE);
                sv_sidePanelExpanded.setVisibility(View.VISIBLE);
                tv_newProject.setVisibility(View.VISIBLE);
                tv_createFolder.setVisibility(View.VISIBLE);
            }
        });

//        arrowDown = (TextView) findViewById(R.id.textView_arrowDown);
//        arrowDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //open user profile page
//                Intent intent = new Intent(ProjectDisplay.this, UserProfile.class);
//                startActivity(intent);
//            }
//        });

        tv_newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewProject.this, NewProject.class);
                startActivity(intent);
            }
        });

        tv_createFolder.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_createFolder.setVisibility(View.INVISIBLE);
                et_createFolder.setVisibility(View.VISIBLE);
                String new_folder_name = et_createFolder.getText().toString();
                write_to_folder_list_file(new_folder_name);
                create_new_folder_directory(new_folder_name);

            }
        }));


    }

    //HEADER FUNCTIONS
    public void write_to_folder_list_file (String new_foldername){
        try {
            FileOutputStream fos = new FileOutputStream(folderlist);
            OutputStreamWriter oos = new OutputStreamWriter(fos);
            oos.write(new_foldername + "\n");
            oos.flush();
            oos.close();
            fos.flush();
            fos.close();
            Log.d("folderadded2list", new_foldername);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void create_new_folder_directory (String new_foldername) {
        File folder_directory = new File (SPRINTDIRECTORY.getAbsolutePath() + File.separator + new_foldername);
        folder_directory.mkdirs();
        Log.d("folder created", new_foldername);
        et_createFolder.setVisibility(View.INVISIBLE);

    }
}
