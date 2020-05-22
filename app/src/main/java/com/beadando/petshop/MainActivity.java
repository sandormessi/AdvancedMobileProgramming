package com.beadando.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.beadando.petshop.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{
    private Button loginButton, signUpButton;
    private ProgressDialog loadingBar;
    private LogInUtility logInUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUpButton = findViewById(R.id.mainView_register);
        loginButton = findViewById(R.id.mainView_login);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        logInUtility = new LogInUtility(loadingBar, null,this);

        String UserNameKey = Paper.book().read(Prevalent.UserNameKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserNameKey != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserNameKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                logInUtility.LogIn(UserNameKey, UserPasswordKey);

                loadingBar.setTitle("Automaikusan bejelentkezés...");
                loadingBar.setMessage("Adatok ellenőrzése...");

                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }
}
