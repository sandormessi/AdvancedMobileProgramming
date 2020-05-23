package com.beadando.petshop;

import com.beadando.petshop.Model.Product;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public final class AnimalDatabaseAccessor
{
    private static List<Product> products = new ArrayList<>();

    public static List<Product> getAnimals()
    {
        return products;
    }

    public static void getAnimalsWithProvider(final ProductListProvider animalListProvider, final boolean detachEventListenerAfterFirstOccurrence)
    {
        DatabaseAccessor.getCollectionDataAtPath(new DataCollectionProvider()
        {
            @Override
            public void ProvideCollection(Iterable<DataSnapshot> data)
            {
                List<Product> productList = AnimalDatabaseAccessor.products;
                productList.clear();

                for (DataSnapshot dataSnapshot : data)
                {
                    productList.add(dataSnapshot.getValue(Product.class));
                }

                animalListProvider.ProvideProductList(productList);
            }
        }, "/Animals", detachEventListenerAfterFirstOccurrence);
    }
}
