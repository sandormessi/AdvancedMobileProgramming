package com.beadando.petshop.Model;

public class ShoppingCartItem
{
    private String name;
    private String description;
    private String id;
    private int price;
    private int quantity;

    public String getName()
    {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription()

    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }

    public int getPrice()
    {
        return price;
    }
    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getQuantity()
    {
        return quantity;
    }
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
}
