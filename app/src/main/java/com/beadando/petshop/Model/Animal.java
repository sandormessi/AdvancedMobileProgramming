package com.beadando.petshop.Model;

public class Animal
{
    private String name;
    private String image;
    private String category;
    private int price;
    private String description;
    private String id;

    public Animal(){}

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }
    public void setImage(String image)
    {
        this.image = image;
    }

    public String getCategory()
    {
        return category;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public int getPrice()
    {
        return price;
    }
    public void setPrice(int price)
    {
        this.price = price;
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
}
