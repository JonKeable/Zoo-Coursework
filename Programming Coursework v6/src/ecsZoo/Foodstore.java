package ecsZoo;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Foodstore {

	private Hashtable<String, Integer> contents = new Hashtable<String, Integer>();
	
	//This is how much food will be in the store on initialisation;
	private int startingAmount = 0;
	
	public Foodstore() {
		initStore();
	}
	
	/*
	 * Initialises the store with 0 of each of the food types specified
	 */
	private void initStore() {

		contents.put("hay", startingAmount);
		contents.put("steak", startingAmount);
		contents.put("fruit", startingAmount);
		contents.put("celery", startingAmount);
		contents.put("fish", startingAmount);
		contents.put("ice cream", startingAmount);
	}

	/*
	 * If the amount argument is at least 1 and the food type is valid, increments the value accordingly.
	 * Otherwise, gives a relevant error.
	 */
	public void addFood(String name, int amount) {
		try {
			if (amount > 0) {
				if(contents.containsKey(name)) {
					contents.put(name, contents.get(name) + amount);
				}
				else {
					System.err.println("Invalid food type.");
				}
			}
			else {
				System.err.println("Amount must be greater than 0");
			}
		}
		catch(Exception e) {
			System.err.println("Something went wrong trying to add the specified food");
		}
		
		
	}
	
	/*
	 * If the store contains at least 1 of the specified food, remove 1 of it from the store and returns true.
	 * If the store does not contain any of the specified food, or if it is not a type of food it can store, returns false.
	 */
	public boolean takeFood(String food) {
		return this.takeFood(food, 1);
	}
	
	//Used by zookeepers not animals to move food.
	public boolean takeFood(String food, int amount) {

		Integer foodAmount = contents.get(food);
		
		//All valid foods have an entry with value 0, sand invalid foods which do not have an entry return null
		
		
		if(foodAmount == null) {
			System.err.println("invalid food type");
			return false;
		}
		else {
			//If there is enough food in the foodstore, remove it.
			if(contents.get(food) >= amount) {
				contents.put(food, contents.get(food) - 1);
				return true;
			}
			else{
				System.err.println("There is not enough " + food + " in the store");
				return false;
			}
		}
	}

	//Prints out a table of contents of this store
	public void printStore() {
		System.out.printf("%-20.20s  %-20.20s%n", "Food", "Amount");
		Set<String> items = contents.keySet();
		
		for(String food : items) {
			System.out.printf("%-20.20s  %-20.20s%n", food, contents.get(food));	
		}
	}
	
	public Iterator<Map.Entry<String, Integer>> getContentsIt() {
		return contents.entrySet().iterator();
	}
	
	public int getFoodAmount(String food) {
		return contents.get(food);
	}


}
