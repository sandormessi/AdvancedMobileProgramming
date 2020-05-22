package com.beadando.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity
{
    private boolean userLogin;
    private EditText InputUserName, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView Admin;
    private CheckBox RememberMe;
    private LogInUtility logInUtility;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = findViewById(R.id.login_button);
        InputUserName = findViewById(R.id.login_inputusern);
        InputPassword = findViewById(R.id.login_inputpassw);
        Admin = findViewById(R.id.admin_panel_link);

        loadingBar = new ProgressDialog(this);

        RememberMe = findViewById(R.id.emlekezzram);
        Paper.init(this);

        logInUtility = new LogInUtility(loadingBar, RememberMe,this);

        LoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginUser();
            }
        });

        Admin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(userLogin)
                {
                    LoginButton.setText("Belépés");
                    Admin.setText("Felhasználói belépés");
                    RememberMe.setVisibility(View.VISIBLE);
                }
                else
                {
                    LoginButton.setText("Admin belépés!");
                    Admin.setText("Admin belépés");
                    RememberMe.setVisibility(View.INVISIBLE);
                }

                userLogin = !userLogin;
            }
        });
    }

    private void LoginUser()
    {
        String Passw = InputPassword.getText().toString();
        String Username = InputUserName.getText().toString();

        if (TextUtils.isEmpty(Passw))
        {
                Toast.makeText(this, "Jelszót kötelező megadni!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Username))
        {
                Toast.makeText(this, "Felhasználónevet kötelező megadni!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Bejelentkezés");
            loadingBar.setMessage("Adatok ellenőrzése...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            logInUtility.LogIn(Username, Passw);
        }
    }
}
