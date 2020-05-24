package com.beadando.petshop;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.beadando.petshop.Model.Product;
import com.beadando.petshop.Model.ShoppingCartItem;
import com.beadando.petshop.Prevalent.Prevalent;

import java.util.List;

public final class ItemOrderUtility
{
    public static void OrderItems(Context mParentActivity, Product product, TextView stockTextView, int quantityToBeOrdered, boolean isAnimalList, boolean writeStock)
    {
        if (product.getStock() == 0)
        {
            Toast.makeText(mParentActivity, "Nincs több raktáron.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (quantityToBeOrdered > 1 && product.getStock() - quantityToBeOrdered <= 0)
        {
            Toast.makeText(mParentActivity, "A kívánt mennyiség nem áll rendelkezésre.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<ShoppingCartItem> itemsInCart = Prevalent.CurrentOnlineAccount.getCartItems();
        ShoppingCartItem item = getItemById(product.getId(), itemsInCart);

        if (item == null)
        {
            item = new ShoppingCartItem();

            item.setId(product.getId());
            item.setOrdered(0);
            item.setDescription(product.getDescription());
            item.setQuantity(1);
            item.setPrice(product.getPrice());

            if (isAnimalList)
            {
                item.setIsAnimal(1);
            }
            else
            {
                item.setIsAnimal(0);
            }

            itemsInCart.add(item);
        }
        else
        {
            item.setQuantity(item.getQuantity() + quantityToBeOrdered);
        }

        product.setStock(product.getStock() - quantityToBeOrdered);

        AccountDatabaseAccessor.updateShoppingCartItems(Prevalent.CurrentOnlineAccount, item);

        if (writeStock)
        {
            stockTextView.setText("Készlet: " + product.getStock());
        }

        if (isAnimalList)
        {
            AnimalDatabaseAccessor.addOrUpdateAnimalWithListener(product);
        }
        else
        {
            ProductDatabaseAccessor.updateProduct(product);
        }
    }

    public static void RemoveOrderedItem(Product product, TextView stockTextView, boolean isAnimalList, boolean writeStock)
    {
        List<ShoppingCartItem> itemsInCart = Prevalent.CurrentOnlineAccount.getCartItems();
        ShoppingCartItem item = getItemById(product.getId(), itemsInCart);

        product.setStock(product.getStock() + item.getQuantity());

        AccountDatabaseAccessor.removeShoppingCartItems(Prevalent.CurrentOnlineAccount, item);

        if (writeStock)
        {
            stockTextView.setText("Készlet: " + String.valueOf(product.getStock()));
        }

        if (isAnimalList)
        {
            AnimalDatabaseAccessor.addOrUpdateAnimalWithListener(product);
        }
        else
        {
            ProductDatabaseAccessor.updateProduct(product);
        }
    }

    public static void UnOrderItems(Product product, TextView stockTextView, int quantityToBeOrdered, boolean isAnimalList, boolean writeStock)
    {
        List<ShoppingCartItem> itemsInCart = Prevalent.CurrentOnlineAccount.getCartItems();
        ShoppingCartItem item = getItemById(product.getId(), itemsInCart);

        if (item != null)
        {
            if (item.getQuantity() - quantityToBeOrdered < 0)
            {
                return;
            }

            item.setQuantity(item.getQuantity() - quantityToBeOrdered);
        }

        product.setStock(product.getStock() + quantityToBeOrdered);

        AccountDatabaseAccessor.updateShoppingCartItems(Prevalent.CurrentOnlineAccount, item);

        if (writeStock)
        {
            stockTextView.setText("Készlet: " + String.valueOf(product.getStock()));
        }

        if (isAnimalList)
        {
            AnimalDatabaseAccessor.addOrUpdateAnimalWithListener(product);
        }
        else
        {
            ProductDatabaseAccessor.updateProduct(product);
        }
    }

    private static ShoppingCartItem getItemById(String id, List<ShoppingCartItem> itemsInCart)
    {
        for (ShoppingCartItem item: itemsInCart)
        {
            if (item.getId().equals(id))
            {
                return item;
            }
        }
        return null;
    }
}
