package com.example.stephaniehuang.unishare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
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
import java.util.Arrays;

/**
 * Created by StephanieHuang on 5/1/17.
 *
 * This class is the basic home page of the user (displayed after user logs in).
 * The current sprints the user is working on is displayed.
 * When the side panel is opened, users can create new sprints or folders.
 */

public class ProjectDisplay extends Login {

    //****** HEADER ELEMENTS ******
    TextView getUsername;
    TextView arrowDown;
    Button b_sidePanel;
    SurfaceView sv_sidePanelExpanded;
    TextView tv_newProject;
    TextView tv_createFolder;
    EditText et_createFolder;

    //Project info
    EditText setProjectName, setProjectStartDate, setProjectEndDate, setProjectDescription;
    String projectname, projectstartdate, projectenddate, projectdescription;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectdisplay);

        /*************************************************************
         HEADER PORTION
         **************************************************************/

        //matching everything to the XML elements
        tv_newProject = (TextView) findViewById(R.id.textView_startNewProject);
        tv_createFolder = (TextView) findViewById(R.id.textView_createFolder);
        et_createFolder = (EditText) findViewById(R.id.editText_createFolder);
        getUsername = (TextView) findViewById(R.id.textView_getUsername);
        b_sidePanel = (Button) findViewById(R.id.button_sidePanel);
        sv_sidePanelExpanded = (SurfaceView) findViewById(R.id.surfaceView_sidePanelExpanded);


        //Display user first and last name at the top
        getUsername.setText(firstname + " " + lastname);

        //If button to open side panel is clicked
        //make the expanded panel, option to create new sprint and folder visibile
        b_sidePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_sidePanel.setVisibility(View.INVISIBLE);
                sv_sidePanelExpanded.setVisibility(View.VISIBLE);
                tv_newProject.setVisibility(View.VISIBLE);
                tv_createFolder.setVisibility(View.VISIBLE);
            }
        });

        //If the expanded side panel is clicked,
        //hide the expanded panel elements
        sv_sidePanelExpanded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_sidePanelExpanded.setVisibility(View.INVISIBLE);
                tv_newProject.setVisibility(View.INVISIBLE);
                tv_createFolder.setVisibility(View.INVISIBLE);
                et_createFolder.setVisibility(View.INVISIBLE);
                b_sidePanel.setVisibility(View.VISIBLE);

            }
        });

        //If user wants to create a new sprint
        // open the NewProject class
        tv_newProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectDisplay.this, NewProject.class);
                startActivity(intent);
            }
        });

        //If user wants to create a new folder
        tv_createFolder.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_createFolder.setVisibility(View.INVISIBLE);
                et_createFolder.setVisibility(View.VISIBLE);
                et_createFolder.setGravity(10);
                String new_folder_name = et_createFolder.getText().toString();
                write_to_folder_list_file(new_folder_name);
                create_new_folder_directory(new_folder_name);

            }
        }));


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


        /*************************************************************
         PROJECT PORTION
         **************************************************************/

        if (currentsprints.exists()) {
            try {
                InputStream inputStream = new BufferedInputStream(new FileInputStream(currentsprints));
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder total = new StringBuilder();
                String line;
                int linecount = 0;
                while ((line = r.readLine()) != null) {
                    Log.d("reading the file", line);
                }
                r.close();
                inputStream.close();

            }
            catch (IOException e){
                e.printStackTrace();
            }
        }





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
