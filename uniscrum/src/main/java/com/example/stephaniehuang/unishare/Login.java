package com.example.stephaniehuang.unishare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Vector;

/**
 * Created by StephanieHuang on 4/24/17.
 *
 *
 * This class is the first screen of the app.
 * Users log in with their email address and password.
 * If the user is found, the user's basic directories and files are set.
 * If the user is not found, error message shows.
 * User has option to  'Create New' account.
 *
 *  /(user_email_address)/USERINFO.TXT
 *      stores user's first name and last name and password info of the user
 *
 *   /(user_email_address)/CURRENT_SPRINTS.TXT
 *      stores filepaths to all the current sprint directories
 *
 *   /(user_email_address)/LIST_OF_FOLDER_NAMES.TXT
 *      is a file containing all of the folders to categorize the sprints
 *
 *   /(user_email_address)/SPRINTDIRECTORY
 *     is directory containing all of the individual sprint directrories
 *
 *  /(user_email_address)/SPRINTDIRECTORY/Uncategorized
 *      is the directory containing sprints under the 'Uncategorized' folder
 */


public class Login extends AppCompatActivity {

    Button createnew;
    Button login;
    EditText input_username, input_password;
    TextView loginError;
    static String USERNAME;
    static String firstname, lastname;

    //BASIC DIRECTORIES
    static File USERDIRECTORY;
    static File SPRINTDIRECTORY;
    static File Uncategorized;

    //BASIC FILES
    static File USERS;
    static File userinfo;
    static File currentsprints;
    static File folderlist;

    //storing all the folder names from the file, 'folderlist'
    static Vector<String> foldernames = new Vector<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        input_username = (EditText) findViewById(R.id.editText_username);
        input_password = (EditText) findViewById(R.id.editText_password);
        loginError = (TextView) findViewById(R.id.textView_error);

        //brings you to the create new user screen
        createnew = (Button) findViewById(R.id.button_createNew);
        createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Login.this, CreateNewUser.class);
                startActivity(intent);
            }
        });

        //**************** /APP/USERS.TXT *******************
        //creates the file with all users of the app.
        USERS = new File (getFilesDir() + File.separator + "USERS.TXT");
        if (!USERS.exists()){
            try {
                USERS.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //list of users file found
        else {
            //read the user names
            try {
                InputStream inputStream = new BufferedInputStream(new FileInputStream(USERS));
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder total = new StringBuilder();
                String line;
                int linecount = 0;
                while ((line = r.readLine()) != null) {
                    Log.d("reading list of users", line);
                }
                r.close();
                inputStream.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        //********** LOGGING IN ****************
        //user logins with email and password
        login = (Button) findViewById(R.id.button_Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //username is the email address
                USERNAME = input_username.getText().toString();

                //Open USERDIRECTORY based on username
                USERDIRECTORY = new File (getFilesDir() + File.separator + USERNAME);

                //user not found
                if (!USERDIRECTORY.exists()){
                    loginError.setVisibility(View.VISIBLE);
                }
                //user found
                else {

                    //********* /USERDIRECTORY/USERINFO.TXT ********
                    userinfo=new File (USERDIRECTORY.getAbsolutePath()+File.separator+"USERINFO.TXT");

                    //read the information in the user info file
                    try {
                        InputStream inputStream = new BufferedInputStream(new FileInputStream(userinfo));
                        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder total = new StringBuilder();
                        String line;
                        int linecount = 0;
                        while ((line = r.readLine()) != null) {

                            //get the user's first name
                           if (line.charAt(0)=='F') {
                               int lastelementindex = line.length();
                               firstname = line.substring(4, lastelementindex);
                               Log.d("first name", firstname);
                           }

                            //get the user's last name
                            if (line.charAt(0) == 'L'){
                                int lastelementindex = line.length();
                                lastname = line.substring(4, lastelementindex);
                                Log.d("last name", lastname);
                            }
                        }
                        r.close();
                        inputStream.close();
                    }

                    catch (IOException e){
                        e.printStackTrace();
                    }


                    //********* /USERDIRECTORY/CURRENT_SPRINTS.TXT ********
                    currentsprints = new File(USERDIRECTORY.getAbsolutePath() + File.separator + "CURRENT_SPRINTS.TXT");

                    //********* /USERDIRECTORY/LIST_OF_FOLDER_NAMES.TXT ********
                    folderlist = new File(USERDIRECTORY. getAbsolutePath() + File.separator + "LIST_OF_FOLDER_NAMES.TXT");
                    try {

                        //reading the folder names line by line
                        InputStream inputStream = new BufferedInputStream(new FileInputStream(folderlist));
                        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder total = new StringBuilder();
                        String line;

                        while ((line = r.readLine()) != null) {
                            Log.d("reading list of folders", line);

                            //add each folder into a vector<string> foldernames
                            foldernames.add(line);
                        }
                        r.close();
                        inputStream.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }

                    //********* /USERDIRECTORY/SPRINTDIRECTORY ********
                    SPRINTDIRECTORY = new File(USERDIRECTORY.getAbsolutePath() + File.separator + "SPRINTDIRECTORY");

                    //********* /USERDIRECTORY/SPRINTDIRECTORY/Uncategorized ********
                    Uncategorized = new File(SPRINTDIRECTORY.getAbsolutePath()+File.separator + "Uncategorized");


                    //open the project display page
                    Intent intent = new Intent (Login.this, ProjectDisplay.class);
                    startActivity(intent);
                }



            }
        });


    }
}