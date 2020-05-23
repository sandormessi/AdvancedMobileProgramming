package com.beadando.petshop.Model;

public class Product
{
    private String Name;
    private String Image;
    private String Category;
    private int Price;
    private String Description;
    private String Id;
    private int Stock;

    public String getName()
    {
        return Name;
    }
    public void setName(String name)
    {
        Name = name;
    }

    public String getImage()
    {
        return Image;
    }
    public void setImage(String image)
    {
        Image = image;
    }

    public String getCategory()
    {
        return Category;
    }
    public void setCategory(String category)
    {
        Category = category;
    }

    public int getPrice()
    {
        return Price;
    }
    public void setPrice(int price)
    {
        Price = price;
    }

    public String getDescription()
    {
        return Description;
    }
    public void setDescription(String description)
    {
        Description = description;
    }

    public String getId()
    {
        return Id;
    }
    public void setId(String id)
    {
        Id = id;
    }

    public int getStock()
    {
        return Stock;
    }
    public void setStock(int stock)
    {
        Stock = stock;
    }
}
