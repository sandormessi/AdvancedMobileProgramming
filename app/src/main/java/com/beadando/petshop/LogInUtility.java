package com.beadando.petshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beadando.petshop.Model.Account;
import com.beadando.petshop.Prevalent.Prevalent;
import com.beadando.petshop.main.HomeActivity;

import java.util.List;

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

        AccountDatabaseAccessor.getUsersWithProvider(new AccountListProvider()
        {
            @Override
            public void ProvideAccountList(List<Account> accounts)
            {
                boolean accountFound = false;
                for (int i = 0; i < accounts.size(); i++)
                {
                    Account account = accounts.get(i);
                    if (account.getName().equals(username) )
                    {
                        accountFound = true;
                        if (account.getPassword().equals(password))
                        {
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
                        else
                        {
                            Toast.makeText(context, "Rossz felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }

                        break;
                    }
                    else
                    {
                        Toast.makeText(context, "Rossz felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }

                if (!accountFound)
                {
                    Toast.makeText(context, username + " felhasználó nem létezik!", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}
