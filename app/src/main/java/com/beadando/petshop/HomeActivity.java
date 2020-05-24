package com.beadando.petshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beadando.petshop.Product.ProductListActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity
{
    private Button LogoutButton;
    private Button animalCategoryButton, productCategoryButton;
    private FloatingActionButton shoppingCartButton;

    private final DatabaseReference RootRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        LogoutButton = findViewById(R.id.Logout_button);

        LogoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        animalCategoryButton = findViewById(R.id.animals_category_button);
        productCategoryButton = findViewById(R.id.products_category_button);

        animalCategoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
                intent.putExtra(GlobalConstants.IsAnimalListArgument, true);

                startActivity(intent);
            }
        });

        productCategoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, ProductListActivity.class);
                intent.putExtra(GlobalConstants.IsAnimalListArgument, false);

                startActivity(intent);
            }
        });

        View view = findViewById(R.id.shopping_cart_button);
        shoppingCartButton = view.findViewById(R.id.shopping_cart_button2);

        shoppingCartButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HomeActivity.this, ShoppingCartActivity.class);
                startActivity(intent);
            }
        });
    }
}
