package com.beadando.petshop;

import com.google.firebase.database.DataSnapshot;

public interface DataCollectionProvider
{
    void ProvideCollection(Iterable<DataSnapshot> data);
}
