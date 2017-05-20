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

    //storing all the folder names
    static Vector<String> foldernames = new Vector<String>;

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

        login = (Button) findViewById(R.id.button_Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                USERNAME = input_username.getText().toString();

                //Open user directory based on username
                USERDIRECTORY = new File (getFilesDir() + File.separator + USERNAME);
                if (!USERDIRECTORY.exists()){
                    loginError.setVisibility(View.VISIBLE);
                }
                else {
                    //userdirectory found, open user info text file
                    userinfo=new File (USERDIRECTORY.getAbsolutePath()+File.separator+"USERINFO.TXT");

                    //read the information in the user info file
                    try {
                        InputStream inputStream = new BufferedInputStream(new FileInputStream(userinfo));
                        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder total = new StringBuilder();
                        String line;
                        int linecount = 0;
                        while ((line = r.readLine()) != null) {
                           if (line.charAt(0)=='F') {
                               int lastelementindex = line.length()-1;
                               firstname = line.substring(5, lastelementindex);
                           }
                            if (line.charAt(0) == 'L'){
                                int lastelementindex = line.length()-1;
                                lastname = line.substring(5, lastelementindex);
                            }
                        }
                        r.close();
                        inputStream.close();
                    }

                    catch (IOException e){
                        e.printStackTrace();
                    }


                    //set the current text file and open
                    currentsprints = new File(USERDIRECTORY.getAbsolutePath() + File.separator + "CURRENT_SPRINTS.TXT");

                    //set the folder text file, open and store list of folder names in vector
                    folderlist = new File(USERDIRECTORY. getAbsolutePath() + File.separator + "LIST_OF_FOLDER_NAMES.TXT");
                    try {
                        InputStream inputStream = new BufferedInputStream(new FileInputStream(folderlist));
                        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder total = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            Log.d("reading list of folders", line);
                            foldernames.add(line);
                        }
                        r.close();
                        inputStream.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }


                    //create the uncategorized directory
                    Uncategorized = new File (SPRINTDIRECTORY.getAbsolutePath() + File.separator + "Uncategorized");
                    Uncategorized.mkdirs();


                    //open the header page
                    Intent intent = new Intent (Login.this, ProjectDisplay.class);
                    startActivity(intent);
                }



            }
        });


    }
}