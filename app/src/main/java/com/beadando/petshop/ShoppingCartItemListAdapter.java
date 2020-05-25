package com.beadando.petshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.beadando.petshop.Model.Product;
import com.beadando.petshop.Model.ShoppingCartItem;

import java.util.List;

public class ShoppingCartItemListAdapter extends ArrayAdapter<ShoppingCartItem>
{
    private final List<ShoppingCartItem> cartItems;
    public ShoppingCartItemListAdapter(@NonNull Context context, List<ShoppingCartItem> cartItems)
    {
        super(context, 0, cartItems);
        this.cartItems = cartItems;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        final ShoppingCartItem cartItem = cartItems.get(position);

        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.shopping_cart_item_details, parent, false);
        }

        final TextView id = view.findViewById(R.id.id_cartitem_text);
        final TextView name = view.findViewById(R.id.product_name_textview);
        final TextView price = view.findViewById(R.id.product_price);
        final TextView stock = view.findViewById(R.id.product_stock);

        final LinearLayout quantityContainer = view.findViewById(R.id.quantity_container);
        final TextView quantityTextView = quantityContainer.findViewById(R.id.quantity);
        final Button increaseQuantity = quantityContainer.findViewById(R.id.increase_quantity);
        final Button decreaseQuantity = quantityContainer.findViewById(R.id.decrease_quantity);
        final Button removeItem = view.findViewById(R.id.remove_item);

        id.setText(cartItem.getId());
        name.setText(cartItem.getName());
        price.setText(String.valueOf(cartItem.getPrice()));
        quantityTextView.setText(String.valueOf(cartItem.getQuantity()));

        increaseQuantity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OrderItem(cartItem, quantityTextView, stock);
                notifyDataSetChanged();
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UnOrderItem(cartItem, quantityTextView, stock);
                notifyDataSetChanged();
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RemoveOrderItem(cartItem, stock);
                cartItems.remove(position);

            }
        });

        SetStock(stock, cartItem);

        return view;
    }

    private void SetStock(final TextView quantityTextView, final ShoppingCartItem cartItem)
    {
        if (cartItem.getIsAnimal() == 0)
        {
            ProductDatabaseAccessor.getProductsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    Product product = getProductById(cartItem.getId(), products);
                    quantityTextView.setText("Készlet: " + product.getStock());
                }
            }, true);
        }
        else
        {
            AnimalDatabaseAccessor.getAnimalsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    Product product = getProductById(cartItem.getId(), products);
                    quantityTextView.setText("Készlet: " + product.getStock());
                }
            }, true);
        }
    }

    private void OrderItem(final ShoppingCartItem cartItem, final TextView textView, final TextView stock)
    {
        if (cartItem.getIsAnimal() == 0)
        {
            ProductDatabaseAccessor.getProductsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    Product product = getProductById(cartItem.getId(), products);
                    ItemOrderUtility.OrderItems(getContext(), product, stock, 1, cartItem.getIsAnimal() != 0, true);
                    notifyDataSetChanged();
                    //textView.setText(String.valueOf(cartItem.getQuantity()));
                }
            }, true);
        }
        else
        {
            AnimalDatabaseAccessor.getAnimalsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    Product product = getProductById(cartItem.getId(), products);
                    ItemOrderUtility.OrderItems(getContext(), product, stock, 1, cartItem.getIsAnimal() != 0, true);
                    notifyDataSetChanged();
                    //textView.setText(String.valueOf(cartItem.getQuantity()));
                }
            }, true);
        }
    }

    private void UnOrderItem(final ShoppingCartItem cartItem, final TextView textView, final TextView stock)
    {
        if (cartItem.getIsAnimal() == 0)
        {
            ProductDatabaseAccessor.getProductsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products) {
                    Product product = getProductById(cartItem.getId(), products);
                    ItemOrderUtility.UnOrderItems(product, stock, 1, cartItem.getIsAnimal() != 0, true);
                    notifyDataSetChanged();
                    //textView.setText(String.valueOf(cartItem.getQuantity()));
                }
            }, true);
        }
        else
        {
            AnimalDatabaseAccessor.getAnimalsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products) {
                    Product product = getProductById(cartItem.getId(), products);
                    ItemOrderUtility.UnOrderItems(product, stock, 1, cartItem.getIsAnimal() != 0, true);
                    notifyDataSetChanged();
                    //textView.setText(String.valueOf(cartItem.getQuantity()));
                }
            }, true);
        }
    }

    private void RemoveOrderItem(final ShoppingCartItem cartItem, final TextView stock)
    {
        if (cartItem.getIsAnimal() == 0)
        {
            ProductDatabaseAccessor.getProductsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    Product product = getProductById(cartItem.getId(), products);
                    ItemOrderUtility.RemoveOrderedItem(product, stock,cartItem.getIsAnimal() != 0, true);
                    notifyDataSetChanged();
                }
            }, true);
        }
        else
        {
            AnimalDatabaseAccessor.getAnimalsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    Product product = getProductById(cartItem.getId(), products);
                    ItemOrderUtility.RemoveOrderedItem(product, stock,cartItem.getIsAnimal() != 0, true);
                    notifyDataSetChanged();
                }
            }, true);
        }
    }

    private static Product getProductById(String id, List<Product> products)
    {
        for (Product product : products)
        {
            if (product.getId().equals(id))
            {
                return product;
            }
        }

        return null;
    }
}
