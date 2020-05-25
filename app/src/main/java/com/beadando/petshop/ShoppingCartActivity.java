package com.beadando.petshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.beadando.petshop.Model.ShoppingCartItem;
import com.beadando.petshop.Prevalent.Prevalent;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

public class ShoppingCartActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        final GridView shoppingCartItemListContainer = findViewById(R.id.shopping_cart_list);

        final List<ShoppingCartItem> cartItems = Prevalent.CurrentOnlineAccount.getNotOrderedCartItems();
        final ShoppingCartItemListAdapter adapter = new ShoppingCartItemListAdapter(this, cartItems);
        final Button orderButton = findViewById(R.id.take_order_button);

        orderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (cartItems.size() == 0)
                {
                    Toast.makeText(ShoppingCartActivity.this,"A kosarad üres.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);

                builder.setMessage("Biztosan meg akarod rendelni kosarad tartalmát? \r\n A 0 mennyiségű termékek törölve lesznek.")
                        .setTitle("Rendelés megerősítése");

                builder.setPositiveButton("Igen", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        for (ShoppingCartItem item : cartItems)
                        {
                            if (item.getQuantity() == 0)
                            {
                                AccountDatabaseAccessor.removeShoppingCartItems(Prevalent.CurrentOnlineAccount, item);
                            }
                            else
                            {
                                item.setOrdered(1);
                                AccountDatabaseAccessor.updateShoppingCartItems(Prevalent.CurrentOnlineAccount, item);
                            }
                        }

                        cartItems.clear();
                        adapter.notifyDataSetChanged();

                        Toast.makeText(ShoppingCartActivity.this,"Rendelés sikeresen rögzítve.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Nem", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // Nothing to do here
                    }
                });

                AlertDialog dialog = builder.create();

                dialog.show();
            }
        });

        shoppingCartItemListContainer.setAdapter(adapter);
    }
}
