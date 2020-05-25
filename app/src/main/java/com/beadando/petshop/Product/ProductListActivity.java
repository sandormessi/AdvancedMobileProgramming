package com.beadando.petshop.Product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.beadando.petshop.AnimalDatabaseAccessor;
import com.beadando.petshop.DatabaseAccessor;
import com.beadando.petshop.BinaryFileProvider;
import com.beadando.petshop.GlobalConstants;
import com.beadando.petshop.ItemOrderUtility;
import com.beadando.petshop.Model.Product;
import com.beadando.petshop.ProductDatabaseAccessor;
import com.beadando.petshop.ProductListProvider;
import com.beadando.petshop.R;

import com.beadando.petshop.ShoppingCartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductListActivity extends AppCompatActivity
{
    private boolean mTwoPane;
    private boolean isAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Intent intent = getIntent();

        isAnimal = intent.getBooleanExtra(GlobalConstants.IsAnimalListArgument, false);

        View view = findViewById(R.id.shopping_cart_button);
        final FloatingActionButton shoppingCartButton = view.findViewById(R.id.shopping_cart_button2);

        final TextView header = findViewById(R.id.header_text);

        shoppingCartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ProductListActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        if (findViewById(R.id.product_detail_container) != null)
        {
            mTwoPane = true;
        }

        final RecyclerView recyclerView = findViewById(R.id.product_list);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        if(isAnimal)
        {
            header.setText("Örökbefogadható állatok");

            AnimalDatabaseAccessor.getAnimalsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    recyclerView.setAdapter(new ProductListAdapter(ProductListActivity.this, products, isAnimal, mTwoPane));
                }
            }, true);
        }
        else
        {
            header.setText("Háziállat kapcsolatos termékek");

            ProductDatabaseAccessor.getProductsWithProvider(new ProductListProvider()
            {
                @Override
                public void ProvideProductList(List<Product> products)
                {
                    recyclerView.setAdapter(new ProductListAdapter(ProductListActivity.this, products, isAnimal, mTwoPane));
                }
            }, true);
        }
    }

    public static class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder>
    {
        private final ProductListActivity mParentActivity;
        private final boolean mTwoPane;
        private final List<Product> products;
        private final boolean isAnimalList;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Product item = (Product) view.getTag();
                if (mTwoPane)
                {
                    Bundle arguments = new Bundle();
                    arguments.putString(ProductDetailFragment.ARG_ITEM_ID, item.getId());
                    arguments.putBoolean(GlobalConstants.IsAnimalListArgument, isAnimalList);

                    ProductDetailFragment fragment = new ProductDetailFragment();
                    fragment.setArguments(arguments);

                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.product_detail_container, fragment)
                            .commit();
                }
                else
                {
                    Context context = view.getContext();

                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(ProductDetailFragment.ARG_ITEM_ID, item.getId());
                    intent.putExtra(GlobalConstants.IsAnimalListArgument, isAnimalList);

                    context.startActivity(intent);
                }
            }
        };

        ProductListAdapter(ProductListActivity parent, List<Product> products, boolean isAnimalList, boolean twoPane)
        {
            this.isAnimalList = isAnimalList;
            this.products = products;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_content, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            final Product product = products.get(position);

            holder.idTextView.setText(product.getId());
            holder.stockTextView.setText("Készlet: " + String.valueOf(product.getStock()));
            holder.itemView.setTag(product);
            holder.itemView.setOnClickListener(mOnClickListener);

            DatabaseAccessor.getFileAtPath(new BinaryFileProvider()
            {
                @Override
                public void ProvideFile(byte[] fileAsByteArray)
                {
                    Bitmap image = BitmapFactory.decodeByteArray(fileAsByteArray,0, fileAsByteArray.length);
                    holder.imageImageView.setImageBitmap(image);
                }
            }, product.getImage());

            holder.addToCartButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ItemOrderUtility.OrderItems(mParentActivity, product, holder.stockTextView, 1, isAnimalList, true);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return products.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder
        {
            final TextView idTextView;
            final ImageView imageImageView;
            final Button addToCartButton;
            final TextView stockTextView;

            ViewHolder(View view)
            {
                super(view);
                idTextView = view.findViewById(R.id.id_cartitem_text);
                imageImageView = view.findViewById(R.id.animal_image);
                addToCartButton = view.findViewById(R.id.add_to_cart_button);
                stockTextView = view.findViewById(R.id.stock_textview);
            }
        }
    }
}
