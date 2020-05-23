package com.beadando.petshop;

import androidx.annotation.NonNull;

import com.beadando.petshop.Model.Account;
import com.beadando.petshop.Model.ShoppingCartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class AccountDatabaseAccessor
{
    private static List<Account> users = new ArrayList<>();
    private static List<Account> admins = new ArrayList<>();

    public static List<Account> getAdmins()
    {
        return admins;
    }
    public static List<Account> getUsers()
    {
        return users;
    }

    public static void getUsersWithProvider(final AccountListProvider accountListProvider, final boolean detachEventListenerAfterFirstOccurrence)
    {
        DatabaseAccessor.getCollectionDataAtPath(new DataCollectionProvider()
        {
            @Override
            public void ProvideCollection(Iterable<DataSnapshot> data)
            {
                List<Account> accountList = AccountDatabaseAccessor.users;
                accountList.clear();

                for (DataSnapshot dataSnapshot : data)
                {
                    Account newAccount = dataSnapshot.getValue(Account.class);
                    accountList.add(newAccount);

                    GetShoppingCartItemsForUser(dataSnapshot, newAccount);
                }

                accountListProvider.ProvideAccountList(accountList);
            }
        }, "/Users", detachEventListenerAfterFirstOccurrence);
    }

    public static void getAdminsWithProvider(final AccountListProvider accountListProvider, final boolean detachEventListenerAfterFirstOccurrence)
    {
        DatabaseAccessor.getCollectionDataAtPath(new DataCollectionProvider()
        {
            @Override
            public void ProvideCollection(Iterable<DataSnapshot> data)
            {
                List<Account> accountList = AccountDatabaseAccessor.admins;
                accountList.clear();

                for (DataSnapshot dataSnapshot : data)
                {
                    Account newAccount = dataSnapshot.getValue(Account.class);
                    if (newAccount.getAdmin() != 0)
                    {
                        accountList.add(newAccount);
                    }
                }

                accountListProvider.ProvideAccountList(accountList);
            }
        }, "/Users", detachEventListenerAfterFirstOccurrence);
    }

    public static void addOrUpdateUserDataWithListener(@NonNull final OnCompleteListener listener, final String userName, final HashMap<String, Object> data)
    {
       DatabaseAccessor.addOrUpdateCollectionDataWithListener(listener, data, "/Users/" + userName);
    }

    private static void GetShoppingCartItemsForUser(final DataSnapshot dataSnapshot, final Account newAccount)
    {
        DataSnapshot orders = dataSnapshot.child("Orders");

        Iterable<DataSnapshot> shoppingCartItems = orders.getChildren();
        List<ShoppingCartItem> cartItems = newAccount.getCartItems();

        for (DataSnapshot item : shoppingCartItems)
        {
            cartItems.add(item.getValue(ShoppingCartItem.class));
        }
    }

    public static void getUserWithName(final AccountProvider accountProvider, final String userName)
    {
        getUsersWithProvider(new AccountListProvider()
        {
            @Override
            public void ProvideAccountList(List<Account> accounts)
            {
                Account accountToBeFound = findAccountByName(accounts, userName);
                accountProvider.ProvideAccount(accountToBeFound);
            }
        }, true);
    }

    private static Account findAccountByName(final List<Account> accounts, final String name)
    {
        for (Account account : accounts)
        {
            if (account.getUsername().equals(name))
            {
                return account;
            }
        }

        return null;
    }

    public static void updateShoppingCartItems(Account account, ShoppingCartItem item)
    {
        final String path = "/Users/" + account.getUsername() + "/Orders/" + item.getId();
        DatabaseAccessor.addOrUpdateDataWithListener(new OnCompleteListener()
        {
            @Override
            public void onComplete(@NonNull Task task)
            {

            }
        }, item, path);
    }
}
