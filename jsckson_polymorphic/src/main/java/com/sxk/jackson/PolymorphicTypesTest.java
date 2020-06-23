package com.sxk.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sxk.jackson.model.Animals;
import com.sxk.jackson.model.clazz.Cat;
import com.sxk.jackson.model.clazz.Dog;
import com.sxk.jackson.model.clazz.Pig;

import java.io.IOException;

public class PolymorphicTypesTest {

	public static void main(String[] args) throws IOException {
		Cat cat = new Cat();
		cat.setAge("13");
		cat.setName("huahua");
		Dog dog = new Dog();
		dog.setName("wangwang");
		dog.setSex("xiongxing");
		Pig pig = new Pig();
		pig.setWeight("180kg");
		pig.setName("tongtong");
		Animals animals = new Animals();
		animals.addAnimal(cat);
		animals.addAnimal(dog);
		animals.addAnimal(pig);

		ObjectMapper om = new ObjectMapper();

		String string = om.writeValueAsString(animals);

		System.out.println(string);

		Animals target = om.readValue(string, Animals.class);

		System.out.println(target);

	}

}
