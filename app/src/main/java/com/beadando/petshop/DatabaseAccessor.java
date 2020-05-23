package com.beadando.petshop;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;
import java.util.HashMap;

public final class DatabaseAccessor implements Serializable
{
    private static final FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();
    private static final StorageReference FIREBASE_STORAGE = FirebaseStorage.getInstance().getReference();

    public static void getCollectionDataAtPath(final DataCollectionProvider dataProvider, final String path, final boolean detachEventHandlerAfterFistOccurrence)
    {
        final DatabaseReference refAtPath = getReferenceAtPath(path);
        final ValueEventListener eventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                dataProvider.ProvideCollection(dataSnapshot.getChildren());

                if (detachEventHandlerAfterFistOccurrence)
                {
                    refAtPath.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        refAtPath.addValueEventListener(eventListener);
    }

    public static void getDataAtPath(final DataProvider dataProvider, final String path, final boolean detachEventHandlerAfterFistOccurrence)
    {
        final DatabaseReference refAtPath = getReferenceAtPath(path);
        final ValueEventListener eventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                dataProvider.ProvideData(dataSnapshot);

                if(detachEventHandlerAfterFistOccurrence)
                {
                    refAtPath.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        };

        refAtPath.addValueEventListener(eventListener);
    }

    public static void addOrUpdateDataWithListener(@NonNull final OnCompleteListener listener, final Object data, final String path)
    {
        final DatabaseReference refAtPath = getReferenceAtPath(path);
        refAtPath.setValue(data).addOnCompleteListener(listener);
    }

    public static void addOrUpdateCollectionDataWithListener(@NonNull final OnCompleteListener listener, final HashMap<String, Object> data, final String path)
    {
        final DatabaseReference refAtPath = getReferenceAtPath(path);
        refAtPath.updateChildren(data).addOnCompleteListener(listener);
    }

    public static void addOrUpdateFileWithListener(@NonNull final OnCompleteListener listener, final Uri filePath, final String storageName)
    {
        final StorageReference refAtPath = FIREBASE_STORAGE.child(storageName);
        refAtPath.putFile(filePath).addOnCompleteListener(listener);
    }

    public static void getFileAtPath(final BinaryFileProvider binaryFileProvider, final String fileDbPath)
    {
        StorageReference pathReference =
                FIREBASE_STORAGE
                .child("Product Images")
                .child(fileDbPath);
        pathReference
            .getBytes(2 * 1024 * 1024)
            .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes)
                {
                    binaryFileProvider.ProvideFile(bytes);
                }
            });
    }

    private static DatabaseReference getReferenceAtPath(final String path)
    {
        return FIREBASE_DATABASE.getReference(path);
    }
}
