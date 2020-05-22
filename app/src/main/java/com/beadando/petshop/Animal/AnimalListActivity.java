package com.beadando.petshop.Animal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.beadando.petshop.AnimalDatabaseAccessor;
import com.beadando.petshop.AnimalListProvider;
import com.beadando.petshop.DatabaseAccessor;
import com.beadando.petshop.Model.Animal;
import com.beadando.petshop.R;

import com.beadando.petshop.ShoppingCartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AnimalListActivity extends AppCompatActivity
{
    private boolean mTwoPane;
    private final DatabaseAccessor dbAccessor = new DatabaseAccessor();
    private final ArrayList<Animal> list = new ArrayList<>();
    private FloatingActionButton shoppingCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        View view = findViewById(R.id.shopping_cart_button);
        shoppingCartButton = view.findViewById(R.id.shopping_cart_button2);

        shoppingCartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AnimalListActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });

        if (findViewById(R.id.product_detail_container) != null)
        {
            mTwoPane = true;
        }

        final RecyclerView recyclerView = findViewById(R.id.product_list);
        assert recyclerView != null;
        AnimalDatabaseAccessor.getAnimalsWithProvider(new AnimalListProvider()
        {
            @Override
            public void ProvideAnimalList(List<Animal> products)
            {
                recyclerView.setAdapter(new AnimalListAdapter(AnimalListActivity.this, products, mTwoPane));
            }
        });
    }

    public static class AnimalListAdapter extends RecyclerView.Adapter<AnimalListAdapter.ViewHolder>
    {
        private final AnimalListActivity mParentActivity;
        private final boolean mTwoPane;
        private final List<Animal> animals;

        private final View.OnClickListener mOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Animal item = (Animal) view.getTag();
                if (mTwoPane)
                {
                    Bundle arguments = new Bundle();
                    arguments.putString(AnimalDetailFragment.ARG_ITEM_ID, item.getId());

                    AnimalDetailFragment fragment = new AnimalDetailFragment();
                    fragment.setArguments(arguments);

                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.product_detail_container, fragment)
                            .commit();
                }
                else
                {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, AnimalDetailActivity.class);
                    intent.putExtra(AnimalDetailFragment.ARG_ITEM_ID, item.getId());

                    context.startActivity(intent);
                }
            }
        };

        public AnimalListAdapter(AnimalListActivity parent, List<Animal> animals, boolean twoPane)
        {
            this.animals = animals;
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
            holder.mIdView.setText(animals.get(position).getId());
            holder.mContentView.setText(animals.get(position).getDescription());

            holder.itemView.setTag(animals.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount()
        {
            return animals.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder
        {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view)
            {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }
}
