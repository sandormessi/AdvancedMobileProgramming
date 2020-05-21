package com.beadando.petshop;

import com.beadando.petshop.Model.Animal;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public final class AnimalDatabaseAccessor
{
    private static List<Animal> products = new ArrayList<>();

    public static List<Animal> getAnimals()
    {
        return products;
    }

    public static void getAnimalsWithProvider(final AnimalListProvider animalListProvider)
    {
        DatabaseAccessor.getCollectionData(new DataCollectionProvider()
        {
            @Override
            public void ProvideCollection(Iterable<DataSnapshot> data)
            {
                List<Animal> productList = AnimalDatabaseAccessor.products;
                productList.clear();

                for (DataSnapshot dataSnapshot : data)
                {
                    productList.add(dataSnapshot.getValue(Animal.class));
                }

                animalListProvider.ProvideAnimalList(productList);
            }
        }, new String[] {"Animals"});
    }
}
