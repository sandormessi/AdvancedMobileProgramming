package com.beadando.petshop.Model;

public class ShoppingCartItem
{
    private String name;
    private String description;
    private String id;
    private int price;
    private int quantity;
    private int ordered;
    private int isAnimal;

    public int getIsAnimal()
    {
        return isAnimal;
    }
    public void setIsAnimal(int isAnimal)
    {
        this.isAnimal = isAnimal;
    }

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

    public int getOrdered()
    {
        return ordered;
    }
    public void setOrdered(int ordered)
    {
        this.ordered = ordered;
    }
}
