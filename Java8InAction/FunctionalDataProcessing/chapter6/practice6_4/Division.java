package chapter6.practice6_4;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

import common.Dish;

public class Division {

	static List<Dish> menu = Arrays.asList(
		new Dish("pork", false, 800, Dish.Type.MEAT),
		new Dish("beef", false, 700, Dish.Type.MEAT),
		new Dish("chicken", false, 400, Dish.Type.MEAT),
		new Dish("french fries", true, 530, Dish.Type.OTHER),
		new Dish("rice", true, 350, Dish.Type.OTHER),
		new Dish("season fruit", true, 120, Dish.Type.OTHER),
		new Dish("pizza", true, 550, Dish.Type.OTHER),
		new Dish("prawns", false, 300, Dish.Type.FISH),
		new Dish("salmon", false, 450, Dish.Type.FISH)
	);
	
	public static void main(String[] args) {
		Map<Boolean, List<Dish>> partitionedMenu = 
				menu.stream().collect(partitioningBy(Dish::isVegetarian));
		System.out.println(partitionedMenu);
	}
	
}
