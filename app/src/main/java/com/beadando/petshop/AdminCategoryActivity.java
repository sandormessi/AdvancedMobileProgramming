package com.beadando.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity
{
    private ImageView Dogs;
    private ImageView Cats;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        Dogs = (ImageView) findViewById(R.id.dogs);
        Cats = (ImageView) findViewById(R.id.cats);



        Dogs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewPetActivity.class);
                intent.putExtra("category", "Dogs");
                startActivity(intent);
            }
        });

        Cats.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewPetActivity.class);
                intent.putExtra("category", "Cats");
                startActivity(intent);
            }
        });
    }
}
