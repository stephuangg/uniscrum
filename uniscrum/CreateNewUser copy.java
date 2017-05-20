package com.example.stephaniehuang.unishare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by StephanieHuang on 4/24/17.
 */

public class CreateNewUser extends Login {
    String username;
    String password;
    String firstname, lastname;
    EditText createUsername;
    EditText createPassword;
    EditText et_firstname;
    EditText et_lastname;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewuser);

        createUsername = (EditText) findViewById(R.id.editText_createUsername);
        createPassword = (EditText) findViewById(R.id.editText_createPassword);
        et_firstname = (EditText) findViewById(R.id.editText_firstname);
        et_lastname = (EditText) findViewById(R.id.editText_lastname);


        //when clicked, done button will create basic directories and files
        done = (Button) findViewById(R.id.button_done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the string values from each of the edittext
                username = createUsername.getText().toString();
                password = createPassword.getText().toString();
                firstname = et_firstname.getText().toString();
                lastname = et_lastname.getText().toString();

                //open the User file to store new user
                if (USERS.exists()){
                    Log.d("USER file exists", "nothing created");

                    try {
                        FileOutputStream fos = new FileOutputStream(USERS);
                        OutputStreamWriter oos = new OutputStreamWriter(fos);
                        oos.write("\n" + username);
                        oos.flush();
                        oos.close();
                        fos.flush();
                        fos.close();
                        Log.d("USER added: ", username);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        Log.d("!!!!!!", "!!!USER WASN'T ADDED IN THE USER LIST");
                    }
                }
                else {
                    Log.d("!!!!!", "!!!!!!USERS LIST NOT FOUND");
                }


                //create user directory based on user email
                USERDIRECTORY = new File (getFilesDir() + File.separator + username);
                USERDIRECTORY.mkdirs();
                Log.d("made directory", username);

                //create file for user info
                 userinfo = new File (USERDIRECTORY.getAbsolutePath() + File.separator + "USERINFO.TXT");
                try {
                    userinfo.createNewFile();
                    FileOutputStream fos = new FileOutputStream(userinfo);
                    OutputStreamWriter oos = new OutputStreamWriter(fos);
                    oos.write("FN: " + firstname + "\n");
                    oos.write(" LN: " + lastname + "\n");
                    oos.flush();
                    oos.close();
                    fos.flush();
                    fos.close();
                    Log.d("userinfo recorded: ", firstname + lastname);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("!!!!!!!!", "!!!!!USER INFO TEXT FILE NOT CREATED");
                }


                //create currentsprints text file
                currentsprints = new File (USERDIRECTORY.getAbsolutePath() + File.separator + "CURRENT_SPRINTS.TXT");
                   try {
                       currentsprints.createNewFile();
                   }
                   catch (IOException e){
                       e.printStackTrace();
                       Log.d("!!!!!!!!", "!!!!!CURRENT SPRINTS TEXT FILE NOT CREATED");
                   }

                //create folderlist text file
                folderlist = new File (USERDIRECTORY.getAbsolutePath() + File.separator + "LIST_OF_FOLDER_NAMES.TXT");
                try {
                    folderlist.createNewFile();
                    FileOutputStream fos = new FileOutputStream(folderlist);
                    OutputStreamWriter oos = new OutputStreamWriter(fos);
                    oos.write("Uncategorized\n");
                    oos.flush();
                    oos.close();
                    fos.flush();
                    fos.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                    Log.d("!!!!!!!!", "!!!!!LIST OF FOLDER NAME TEXT FILE NOT CREATED");
                }

                //create Sprint directory
                SPRINTDIRECTORY = new File (USERDIRECTORY.getAbsoluteFile() + File.separator + "SPRINTDIRECTORY");
                SPRINTDIRECTORY.mkdirs();
                Log.d("made sprint directory", "yay");




                //go back to Loginscreen
                Intent intent = new Intent (CreateNewUser.this, Login.class);
                startActivity(intent);

            }
        });


    }

}