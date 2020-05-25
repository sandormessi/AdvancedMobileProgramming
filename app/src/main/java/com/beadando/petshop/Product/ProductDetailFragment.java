package com.beadando.petshop.Product;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.beadando.petshop.AnimalDatabaseAccessor;
import com.beadando.petshop.BinaryFileProvider;
import com.beadando.petshop.DatabaseAccessor;
import com.beadando.petshop.GlobalConstants;
import com.beadando.petshop.ItemOrderUtility;
import com.beadando.petshop.Model.Product;
import com.beadando.petshop.ProductDatabaseAccessor;
import com.beadando.petshop.R;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductDetailFragment extends Fragment
{
    public static final String ARG_ITEM_ID = "item_id";
    private Product mItem;
    private boolean isAnimalList;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            isAnimalList = getArguments().getBoolean(GlobalConstants.IsAnimalListArgument);
            mItem = getProduct(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null)
            {
                appBarLayout.setTitle(mItem.getDescription());
            }
        }
    }

    private Product getProduct(String id)
    {
        List<Product> list;
        if (isAnimalList)
        {
            list = AnimalDatabaseAccessor.getAnimals();
        }
        else
        {
            list = ProductDatabaseAccessor.getProducts();
        }

        for (Product product : list)
        {
            if (product.getId().equals(id))
            {
                return product;
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
            final TextView detailsTextView = rootView.findViewById(R.id.product_name_textview);
            final ImageView productImage = rootView.findViewById(R.id.animal_image);
            final TextView stockTextView = rootView.findViewById(R.id.stock_textview);
            final Button orderButton = rootView.findViewById(R.id.add_to_cart_button);

            detailsTextView.setText(mItem.getDescription());

            DatabaseAccessor.getFileAtPath(new BinaryFileProvider()
            {
                @Override
                public void ProvideFile(byte[] fileAsByteArray)
                {
                    Bitmap image = BitmapFactory.decodeByteArray(fileAsByteArray,0, fileAsByteArray.length);
                    productImage.setImageBitmap(image);
                }
            }, mItem.getImage());

            stockTextView.setText(String.valueOf(mItem.getStock()));

            orderButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ItemOrderUtility.OrderItems(ProductDetailFragment.this.getActivity(), mItem, stockTextView,1, isAnimalList, true);
                }
            });
        }

        return rootView;
    }
}
