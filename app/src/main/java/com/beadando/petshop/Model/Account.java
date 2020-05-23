package com.beadando.petshop.Model;

import java.util.ArrayList;
import java.util.List;

public class Account
{
    private String name, password, username, address;
    private List<ShoppingCartItem> cartItems = new ArrayList<>();
    private int admin;

    public Account() { }

    public String getAddress()
    {
        return address;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

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

    public List<ShoppingCartItem> getNotOrderedCartItems()
    {
        ArrayList<ShoppingCartItem> notOrdered = new ArrayList<>();
        for (ShoppingCartItem item : cartItems)
        {
            if (item.getOrdered() == 0)
            {
                notOrdered.add(item);
            }
        }

        return notOrdered;
    }

    public List<ShoppingCartItem> getOrderedCartItems()
    {
        ArrayList<ShoppingCartItem> notOrdered = new ArrayList<>();
        for (ShoppingCartItem item : cartItems)
        {
            if (item.getOrdered() != 0)
            {
                notOrdered.add(item);
            }
        }

        return notOrdered;
    }

    public int getAdmin()
    {
        return admin;
    }
    public void setAdmin(int admin)
    {
        this.admin = admin;
    }
}
