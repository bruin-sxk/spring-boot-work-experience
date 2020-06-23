package com.sxk.jackson.model;

import com.sxk.jackson.model.interfacez.Animal;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IAnimals {

	private List<Animal> ianimals = new ArrayList<>();

	public void addIAnimal(Animal animal){
		ianimals.add(animal);
	}

}
