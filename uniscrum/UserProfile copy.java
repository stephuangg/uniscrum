package com.example.stephaniehuang.unishare;

import android.content.Intent;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by StephanieHuang on 4/25/17.
 */

public class UserProfile extends Login {
    TextView arrowDown;
    EditText inputSelfDescription;
    String selfDescription;
    Button save;
    TextView getUsername;
    TextView arrowUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_userprofile);

        //setting the view of user profile page
        arrowDown = (TextView) findViewById(R.id.textView_arrowDown);
        arrowDown.setVisibility(View.INVISIBLE);
        save = (Button) findViewById(R.id.button_save);
        getUsername = (TextView) findViewById(R.id.textView_getUsername);
        getUsername.setText(USERNAME);

        //check if file exists and read from file
        inputSelfDescription = (EditText) findViewById(R.id.editText_selfDescription);
        if (userdescription.exists()) {
            try {
                InputStream inputStream = new BufferedInputStream(new FileInputStream(userdescription));
                BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder total = new StringBuilder();
                String line;
                int linecount = 0;
                while ((line = r.readLine()) != null) {
                    Log.d("reading the file", line);
                    linecount++;
                    total.append(line);
                }
                r.close();
                inputStream.close();
                inputSelfDescription.setText(total);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        inputSelfDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save.setVisibility(View.VISIBLE);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selfDescription = inputSelfDescription.getText().toString();
                Log.d("selfdescription", selfDescription);

            try {
                Log.d("file exists", "nothing created");
                String filepath = userdescription.getAbsolutePath();
                userdescription.delete();

                File newfile = new File (filepath);
                newfile.createNewFile();

                FileOutputStream fos = new FileOutputStream(newfile);
                OutputStreamWriter oos = new OutputStreamWriter(fos);
                oos.write(selfDescription);
                oos.flush();
                oos.close();
                fos.flush();
                fos.close();
                Log.d("newfilepath: ", filepath);
                Log.d("contentis: ", selfDescription);

            }
            catch(IOException e) {
                e.printStackTrace();
            }


            }
        });

        arrowUp = (TextView) findViewById(R.id.textView_arrowUp);
        arrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent (UserProfile.this, ProjectDisplay.class);
                startActivity(intent);
            }
        });

    }


}
