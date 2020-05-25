package com.beadando.petshop.Product;

import android.content.Intent;
import android.os.Bundle;

import com.beadando.petshop.GlobalConstants;
import com.beadando.petshop.R;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

public class ProductDetailActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar =  findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null)
        {
            Bundle arguments = new Bundle();
            arguments.putString(ProductDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(ProductDetailFragment.ARG_ITEM_ID));
            arguments.putBoolean(GlobalConstants.IsAnimalListArgument, getIntent().getBooleanExtra(GlobalConstants.IsAnimalListArgument,false));

            ProductDetailFragment fragment = new ProductDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.product_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            navigateUpTo(new Intent(this, ProductListActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
