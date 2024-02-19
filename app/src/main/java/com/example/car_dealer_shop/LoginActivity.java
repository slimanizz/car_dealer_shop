package com.example.car_dealer_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    Button login,signup;
    EditText username,password;
    CheckBox rememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.gotoSignUpFromLogin);
        username = findViewById(R.id.userNameTextLogin);
        password = findViewById(R.id.passwordTextLogin);
        rememberMe =  findViewById(R.id.rememberMeCheckBox);
        SharedPreferences sharedPreferences = getSharedPreferences("MyData",
                Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("email","");
        username.setText(name);
        if(username.getText().toString().length()!=0)rememberMe.setChecked(true);
        signup.setOnClickListener(v -> {

            Intent myIntent=new Intent(LoginActivity.this,
                    SignUp.class);
            LoginActivity.this.startActivity(myIntent);
        });

        login.setOnClickListener(v -> {
            if(username.getText().toString().equals("admin")&&password.getText().toString().equals("admin")){
                System.out.println("ADMIN");
                Intent myIntent=new Intent(LoginActivity.this,
                        AdminMain.class);
                LoginActivity.this.startActivity(myIntent);
            }
            else{
                DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginActivity.this);
                Cursor cursor = dataBaseHelper.getUserByEmail(username.getText().toString());
                String errorText = "";
                if(cursor.getCount() == 0)errorText+="E-mail doesn't Exist \n";
                else{
                    cursor.moveToNext();
                    String hashedInputPassword = getHashedPassword(password.getText().toString());
                    String hashedFromDB = cursor.getString(5);
                    System.out.println("FROM DB -> "+hashedFromDB);
                    if(!hashedInputPassword.equals(hashedFromDB))errorText+="Incorrect Password!";
                    else{
                        SharedPreferences sharedPreferences1 = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences1.edit();
                        if(rememberMe.isChecked()){
                            editor.putString("email",username.getText().toString());
                            editor.commit();
                        }
                        editor.putString("loggedIn",username.getText().toString());
                        editor.commit();
                        System.out.println("remembering ->>>> "+ sharedPreferences1.getString("email","none"));
                        System.out.println("logged in ->>>> "+ sharedPreferences1.getString("loggedIn","none"));


                    }
                }
                if(errorText.length()!=0) Toast.makeText(LoginActivity.this,errorText,Toast.LENGTH_LONG).show();
                else{
                    if(cursor.getString(2).equals("1")){
                        Intent myIntent=new Intent(LoginActivity.this,
                                AdminMain.class);
                        LoginActivity.this.startActivity(myIntent);
                    }
                    else{
                        Intent myIntent=new Intent(LoginActivity.this,
                                CustomerMain.class);
                        LoginActivity.this.startActivity(myIntent);
                    }

                }
            }



        });
    }

    public  String getHashedPassword(String pw){
        String passwordToHash = pw;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
