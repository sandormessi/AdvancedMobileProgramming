package com.beadando.petshop.Animal;

import android.content.Intent;
import android.os.Bundle;

import com.beadando.petshop.R;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

public class AnimalDetailActivity extends AppCompatActivity
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
            arguments.putString(AnimalDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(AnimalDetailFragment.ARG_ITEM_ID));
            AnimalDetailFragment fragment = new AnimalDetailFragment();
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
            navigateUpTo(new Intent(this, AnimalListActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
