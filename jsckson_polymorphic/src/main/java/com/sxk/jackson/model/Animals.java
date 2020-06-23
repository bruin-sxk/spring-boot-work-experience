package com.sxk.jackson.model;

import com.sxk.jackson.model.clazz.Animal;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Animals {

//	@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class", include = JsonTypeInfo.As.PROPERTY)
	private List<Animal> animals = new ArrayList<>();

	public void addAnimal(Animal animal){
		animals.add(animal);
	}
}
