package com.beadando.petshop.Model;

import java.util.ArrayList;
import java.util.List;

public class Account
{
    private String name, password, username;
    private List<ShoppingCartItem> cartItems = new ArrayList<>();
    private boolean isAdmin;

    public Account() { }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public List<ShoppingCartItem> getCartItems()
    {
        return cartItems;
    }

    public boolean isAdmin()
{
    return isAdmin;
}
    public void setAdmin(boolean admin)
    {
        isAdmin = admin;
    }
}
