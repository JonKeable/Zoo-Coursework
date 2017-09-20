package ecsZoo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Enclosure {
	
	private ArrayList<Animal> residents = new ArrayList<Animal>();
	private Foodstore store = new Foodstore();
	private Set<String> residentFoods = new HashSet<String>();
	
	//The amount of waste in the enclosure
	private int waste;
	
	public Enclosure() {
		waste = 0;
	}
	
	public Enclosure(int waste) {
		this.addWaste(waste);
	}
	
	/*
	 * Adds an animal to the enclosure, and sets that animals enclosure to this one.
	 * Also updates what foods animals in this enclosure eat.
	 */
	public void addAnimal(Animal resident) {
		residents.add(resident);
		resident.setEnclosure(this);
		String[] diet = resident.getDiet();
		for(int i = 0; i < diet.length; i++) {
			residentFoods.add(diet[i]);
		}
	}
	
	public void removeAnimal(Animal resident) {
		residents.remove(resident);
	}
	
	public Iterator<Animal> getResidentsIt() {
		return this.residents.iterator();
	}
	
	public void removeWaste(int amount) {
		waste = waste - amount;
		if(waste < 0) {
			waste = 0;
		}
	}
	
	public void addWaste(int amount) {
		waste = waste + amount;
	}
	
	/*
	 * Returns the amount of waste actually in the enclosure. 
	 * (Not how much waste the enclosure can take, which is not modelled.)
	 */
	public int getWasteSize() {
		return waste;
	}
	
	public Foodstore getFoodstore() {
		return store;
	}
	
	public int size() {
		return residents.size();
	}
	
	//The returned iterator is used by zookeepers when restocking the foodstore.
	public Iterator<String> getResFoodsIt() {
		return residentFoods.iterator();
	}
	
	public int getFoodAmount(String food) {
		return this.getFoodstore().getFoodAmount(food);
	}
	
	//Processes the monthly activity of an enclosure.
	public void aMonthPasses() {
		for(int i = 0; i < residents.size(); i++) {
			 /*
			  * If the animal's health reaches 0, remove it (it dies).
			  * I used a standard for loop so that i can add/remove elements to the list whilst iterating over it.
			  * (Such as when an animal gives birth or died)
			  */
			if(!residents.get(i).aMonthPasses()) {
				residents.remove(i);
				//System.out.println("An animal has died!");
			}
		}
	}
}
