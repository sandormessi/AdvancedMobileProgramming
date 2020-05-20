package com.beadando.petshop;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

public final class DatabaseAccessor implements Serializable
{
    private static final DatabaseReference DATABASE_REFERENCE = FirebaseDatabase.getInstance().getReference();

    public static void getCollectionData(final DataCollectionProvider dataProvider, final String[] path)
    {
        DATABASE_REFERENCE.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                DataSnapshot pathElement = dataSnapshot.child(path[0]);

                for (int i = 1; i < path.length; i++)
                {
                    pathElement = pathElement.child(path[i]);
                }

                dataProvider.ProvideCollection(pathElement.getChildren());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public static void getData(final DataProvider dataProvider, final String[] path)
    {
        DATABASE_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                DataSnapshot pathElement = dataSnapshot.child(path[0]);

                for (int i = 1; i < path.length; i++)
                {
                    pathElement = pathElement.child(path[i]);
                }

                dataProvider.ProvideData(pathElement);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public static void addDataWithListener(@NonNull final OnCompleteListener listener, final HashMap<String, Object> data, final String[] path)
    {
        DATABASE_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
               DatabaseReference child = DATABASE_REFERENCE.child(path[0]);

                for (int i = 1; i < path.length; i++)
                {
                    child = child.child(path[i]);
                }

                child.updateChildren(data).addOnCompleteListener(listener);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
