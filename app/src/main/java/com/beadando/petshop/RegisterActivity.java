package com.beadando.petshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button RegisterButton;
    private EditText InputName, InputPW, InputUserName;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterButton = (Button) findViewById(R.id.register_button);
        InputName = (EditText) findViewById(R.id.register_inputname);
        InputPW = (EditText) findViewById(R.id.register_inputpassw);
        InputUserName = (EditText) findViewById(R.id.register_inputusername);

        loadingBar = new ProgressDialog(this);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });

    }

    private void CreateAccount()
    {
        String Name = InputName.getText().toString();
        String Passw = InputPW.getText().toString();
        String Username = InputUserName.getText().toString();

        if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(this, "Nevet kötelező megadni!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Passw))
        {
            Toast.makeText(this, "Jelszót kötelező megadni!", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Username))
        {
            Toast.makeText(this, "Felhasználónevet kötelező megadni!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Fiók készítése");
            loadingBar.setMessage("Adatok ellenőrzése");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateEmail(Name, Passw, Username);
        }
    }

    private void ValidateEmail(final String name, final String passw, final String username)
    {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(username).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("username", username);
                    userdataMap.put("name", name);
                    userdataMap.put("password", passw);

                    RootRef.child("Users").child(username).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "A felhasználói fiók elkészült!", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Nem sikerült kapcsolódni a szerverhez, próbáld újra később!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "Ezzel a felhasználónévvel már létezik fiók!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Próbálj másik felhasználónévvel regisztrálni.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

    }

}
