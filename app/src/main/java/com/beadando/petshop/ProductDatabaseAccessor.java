package com.beadando.petshop;

import androidx.annotation.NonNull;

import com.beadando.petshop.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public static void getProductsWithProvider(final ProductListProvider productListProvider, final boolean detachEventListenerAfterFirstOccurrence)
    {
        DatabaseAccessor.getCollectionDataAtPath(new DataCollectionProvider()
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
        }, "/Products", detachEventListenerAfterFirstOccurrence);
    }

    public static void updateProduct(final Product product)
    {
        DatabaseAccessor.addOrUpdateDataWithListener(new OnCompleteListener()
        {
            @Override
            public void onComplete(@NonNull Task task)
            {
                // Nothing to do here
            }
        }, product,"/Products/" + product.getId());
    }
}
