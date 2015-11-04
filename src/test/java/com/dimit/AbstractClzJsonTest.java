package com.dimit;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dimit.bean.Animal;
import com.dimit.bean.Elephant;
import com.dimit.bean.Lion;
import com.dimit.bean.Zoo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractClzJsonTest {

	@Test
	public void test() {
		 // let start creating the zoo
        Zoo zoo = new Zoo("Samba Wild Park", "Paz");
        Lion lion = new Lion("Simba");
        Elephant elephant = new Elephant("Manny");
        List<Animal> animals = new ArrayList<>();
        animals.add(lion);
        animals.add(elephant);
        zoo.setAnimals(animals);
 
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(writer, zoo);
			System.out.println(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
