package com.beadando.petshop;

import com.beadando.petshop.Model.Account;
import com.beadando.petshop.Model.ShoppingCartItem;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
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

    public static void getUsersWithProvider(final AccountListProvider accountListProvider)
    {
        DatabaseAccessor.getCollectionData(new DataCollectionProvider()
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
                    DataSnapshot orders = dataSnapshot.child("Orders");
                    Iterable<DataSnapshot> shoppingCartItems = orders.getChildren();
                    List<ShoppingCartItem> cartItems = newAccount.getCartItems();

                    for (DataSnapshot item : shoppingCartItems)
                    {
                        cartItems.add(item.getValue(ShoppingCartItem.class));
                    }
                }

                accountListProvider.ProvideAccountList(accountList);
            }
        }, new String[] {"Users"});
    }

    public static void getAdminsWithProvider(final AccountListProvider accountListProvider)
    {
        DatabaseAccessor.getCollectionData(new DataCollectionProvider()
        {
            @Override
            public void ProvideCollection(Iterable<DataSnapshot> data)
            {
                List<Account> accountList = AccountDatabaseAccessor.admins;
                accountList.clear();

                for (DataSnapshot dataSnapshot : data)
                {
                    accountList.add((Account)dataSnapshot.getValue());
                }

                accountListProvider.ProvideAccountList(accountList);
            }
        }, new String[] {"Admins"});
    }
}
