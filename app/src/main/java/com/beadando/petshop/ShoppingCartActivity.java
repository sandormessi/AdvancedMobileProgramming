package com.beadando.petshop;

import android.os.Bundle;

import com.beadando.petshop.Model.ShoppingCartItem;
import com.beadando.petshop.Prevalent.Prevalent;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.GridView;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity
{
    private GridView shoppingCartItemListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        shoppingCartItemListContainer = findViewById(R.id.shopping_cart_list);
        List<ShoppingCartItem> cartItems = Prevalent.CurrentOnlineAccount.getNotOrderedCartItems();

        shoppingCartItemListContainer.setAdapter(new ShoppingCartItemListAdapter(this, cartItems));
    }
}
