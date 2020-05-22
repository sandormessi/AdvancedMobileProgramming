package com.beadando.petshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.beadando.petshop.Model.ShoppingCartItem;

import java.util.List;

public class ShoppingCartItemListAdapter extends ArrayAdapter<ShoppingCartItem>
{
    List<ShoppingCartItem> cartItems;
    public ShoppingCartItemListAdapter(@NonNull Context context, List<ShoppingCartItem> cartItems)
    {
        super(context, 0, cartItems);
        this.cartItems = cartItems;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        // Get the data item for this position
        ShoppingCartItem account = cartItems.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.shopping_cart_item_details, parent, false);
        }

        // Lookup view for data population
        TextView id = view.findViewById(R.id.id_text);
        TextView name = view.findViewById(R.id.product_name);
        TextView description = view.findViewById(R.id.product_description);
        TextView price = view.findViewById(R.id.product_price);
        EditText quantity = view.findViewById(R.id.quantity);

        id.setText(account.getId());
        name.setText(account.getName());
        description.setText(account.getDescription());
        price.setText(String.valueOf(account.getPrice()));
        quantity.setText(String.valueOf(account.getQuantity()));

        // Return the completed view to render on screen
        return view;
    }
}
