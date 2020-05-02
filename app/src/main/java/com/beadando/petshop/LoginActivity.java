package com.beadando.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beadando.petshop.Model.Users;
import com.beadando.petshop.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity
{
    private EditText InputUserName, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView Admin, NotAdmin;

    private String parentDbName = "Users";

    private CheckBox RememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_button);
        InputUserName = (EditText) findViewById(R.id.login_inputusern);
        InputPassword = (EditText) findViewById(R.id.login_inputpassw);
        Admin = (TextView) findViewById(R.id.admin_panel_link);
        NotAdmin = (TextView) findViewById(R.id.notadmin_panel_link);

        loadingBar = new ProgressDialog(this);

        RememberMe = (CheckBox) findViewById(R.id.emlekezzram);
        Paper.init(this);


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
                LoginButton.setText("Admin belépés!");
                Admin.setVisibility(View.INVISIBLE);
                NotAdmin.setVisibility((View.VISIBLE));
                parentDbName ="Admins";
                RememberMe.setVisibility(View.INVISIBLE);


            }
        });

        NotAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginButton.setText("Belépés");
                Admin.setVisibility(View.VISIBLE);
                NotAdmin.setVisibility((View.INVISIBLE));
                parentDbName ="Users";
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

                AccessToAccount(Username, Passw);

            }


        }

    private void AccessToAccount(final String username, final String password)
    {
        if(RememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserNameKey, username);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }



        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child(parentDbName).child(username).exists())
                {
                    Users usersData = dataSnapshot.child(parentDbName).child(username).getValue(Users.class);
                    if (usersData.getUsername().equals(username))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "Sikeres rendszergazda bejelentkezés!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Rossz jelszó!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }


                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, username + " felhasználó nem létezik!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


    }
}
