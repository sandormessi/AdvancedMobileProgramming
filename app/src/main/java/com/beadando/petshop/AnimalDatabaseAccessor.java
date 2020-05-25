package com.beadando.petshop;

import androidx.annotation.NonNull;

import com.beadando.petshop.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public static void addOrUpdateAnimalWithListener(final Product product)
    {
        DatabaseAccessor.addOrUpdateDataWithListener(new OnCompleteListener()
        {
            @Override
            public void onComplete(@NonNull Task task)
            {
                // Nothing to do here
            }
        }, product,"/Animals/" + product.getId());
    }
}
