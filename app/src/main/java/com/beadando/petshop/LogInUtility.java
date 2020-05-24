package com.beadando.petshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beadando.petshop.Model.Account;
import com.beadando.petshop.Prevalent.Prevalent;

import io.paperdb.Paper;

public final class LogInUtility
{
    private ProgressDialog loadingBar;
    private CheckBox rememberMeCheckBox;
    private AppCompatActivity context;

    public LogInUtility(ProgressDialog loadingBar, CheckBox rememberMeCheckBox, AppCompatActivity context)
    {
        this.loadingBar = loadingBar;
        this.rememberMeCheckBox = rememberMeCheckBox;
        this.context = context;

        Paper.init(context);
    }

    public void LogIn(final String username, final String password)
    {
        if(rememberMeCheckBox != null && rememberMeCheckBox.isChecked())
        {
            Paper.book().write(Prevalent.UserNameKey, username);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        AccountDatabaseAccessor.getUserWithName(new AccountProvider()
        {
            @Override
            public void ProvideAccount(Account account)
            {
                boolean successfulAuthentication = false;
                if (account != null)
                {
                    if (account.getPassword().equals(password))
                    {
                        successfulAuthentication = true;
                        Prevalent.CurrentOnlineAccount = account;

                        if (account.getAdmin() != 0)
                        {
                            Toast.makeText(context, "Sikeres rendszergazda bejelentkezés!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(context, AdminCategoryActivity.class);
                            context.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(context, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(context, HomeActivity.class);
                            context.startActivity(intent);
                        }
                    }
                }

                if (!successfulAuthentication)
                {
                    Toast.makeText(context, "Rossz felhasználónév vagy jelszó, vagy '" +username + "' felhasználó nem létezik!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        }, username);
    }
}
