package com.beadando.petshop;

import com.beadando.petshop.Model.Product;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public final class ProductDatabaseAccessor
{
    private static List<Product> products = new ArrayList<>();

    public static List<Product> getProducts()
    {
        return products;
    }

    public static void getProductsWithProvider(final ProductListProvider productListProvider)
    {
        DatabaseAccessor.getCollectionData(new DataCollectionProvider()
        {
            @Override
            public void ProvideCollection(Iterable<DataSnapshot> data)
            {
                List<Product> productList = ProductDatabaseAccessor.products;
                productList.clear();

                for (DataSnapshot dataSnapshot : data)
                {
                    productList.add(dataSnapshot.getValue(Product.class));
                }

                productListProvider.ProvideProductList(productList);
            }
        }, new String[] {"Products"});
    }
}
