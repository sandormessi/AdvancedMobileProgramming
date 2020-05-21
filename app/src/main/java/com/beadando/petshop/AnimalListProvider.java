package com.beadando.petshop;

import com.beadando.petshop.Model.Animal;

import java.util.List;

public interface AnimalListProvider
{
    void ProvideAnimalList(List<Animal> animals);
}
