package com.beadando.petshop;

import com.beadando.petshop.Model.Product;

import java.util.List;

public interface ProductListProvider
{
    void ProvideProductList(List<Product> products);
}
