package com.beadando.petshop.Animal;

import android.app.Activity;
import android.os.Bundle;

import com.beadando.petshop.AnimalDatabaseAccessor;
import com.beadando.petshop.DatabaseAccessor;
import com.beadando.petshop.Model.Animal;
import com.beadando.petshop.R;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AnimalDetailFragment extends Fragment
{
    public static final String ARG_ITEM_ID = "item_id";
    Animal mItem;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            mItem = getProduct(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.getDescription());
            }
        }
    }

    private Animal getProduct(String id)
    {
        for (Animal animal : AnimalDatabaseAccessor.getAnimals())
        {
            if (animal.getId().equals(id))
            {
                return animal;
            }
        }

        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.product_detail, container, false);

        if (mItem != null)
        {
            ((TextView) rootView.findViewById(R.id.product_name)).setText(mItem.getDescription());
        }

        return rootView;
    }
}
