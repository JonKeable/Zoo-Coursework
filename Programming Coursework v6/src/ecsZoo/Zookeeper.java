package ecsZoo;

import java.util.ArrayList;
import java.util.Iterator;

public class Zookeeper {

	String name;
	
	//I have implemented the ability to assign a zookeeper multiple enclosures.
	ArrayList<Enclosure> myExhibits = new ArrayList<Enclosure>();

	//This is the store that the zookeeper will take food from to refill enclosures
	Foodstore zooStore;
	
	//This limits how many enclosures each zookeeper can handle.
	int maxExhibits = 3;
	
	// how much waste and food a Zookeeper can carry each month
	int wasteCapacity = 20; 
	int foodCapacity = 20;
	
	//This stores a String array denoting what treats the zookeeper can perform.
	ArrayList<String> treatsAvailable = new ArrayList<String>();
	
	public Zookeeper(Foodstore fs, ArrayList<Enclosure> exhbs) {
		for(Enclosure enc : exhbs) {
			myExhibits.add(enc);
		}
		zooStore = fs;
		treatsAvailable.add("stroke");
		treatsAvailable.add("hug");
	}
	
	public Zookeeper(Foodstore fs, Enclosure enc) {
		myExhibits.add(enc);
		zooStore = fs;
		treatsAvailable.add("stroke");
		treatsAvailable.add("hug");
	}
	
	public Zookeeper(Foodstore fs) {
		this(fs, new ArrayList<Enclosure>());
	}
	
	public Zookeeper() {
		this(null);
	}
	
	/*
	 * Each month the zookeeper can take certain actions:
	 * - Remove up to 20 units of waste from enclosures.
	 * - Move up to 20 units of food from the Zoo's main foodstore to stores in different enclosures.
	 * - treat up to 2 animals that the keeper is trained for.
	 */
	public void aMonthPasses() {
		
		// These values keep track of the amounts of times the zookeeper has performed each action.
		int wasteRemoved = 0;
		int foodMoved = 0;
		int animalsTreated = 0;
		
		/*
		 *  Iterates over each enclosure, performing the 3 specified task where necessary and if the zookeeper 
		 *  has not already performed the task as many times as possible.
		 */
		for(Enclosure enc : myExhibits) {
			
			if(wasteRemoved < wasteCapacity) {
				wasteRemoved += removeWaste(enc,wasteCapacity - wasteRemoved);
			}
			
			if(foodMoved < foodCapacity) {
				foodMoved += moveFood(enc, foodCapacity - foodMoved);
			}
			
			Iterator<Animal> it = enc.getResidentsIt();
			while(it.hasNext() && animalsTreated <= 2) {
				Animal a = it.next();
				if(!a.isHealthy()) {
					if(treat(a)) animalsTreated += 1;
				}
			}
		}
		
		System.out.println("A keeper removed " + wasteRemoved + " waste, moved " + foodMoved + " food into enclosures, and treated " + animalsTreated + " animals");
	}
	
	/*
	 * Moves food of each type to an enclosure if it is running out of that type
	 */
	private int moveFood(Enclosure enc, int space) {
		
		int origSpace= space;
		Iterator<String> it = enc.getResFoodsIt();

		// for all foods that animals in the enclosure eat
		while(it.hasNext() && space > 0) {
			
			String foodName = it.next();
			int foodAmount = enc.getFoodAmount(foodName);
			
			//How much food less than 20 is there? (assumes 20 is a probably a good amount of food to have)
			int defecit = 20 - foodAmount;
			
			// If the amount of food is less than 10... (and that less than 10 is not)
			if(defecit > 10) {
				
				//and there is space to carry enough to refill to 20 do so
				if(space > defecit) {
					enc.getFoodstore().addFood(foodName, defecit);
					zooStore.takeFood(foodName, defecit);
					
					// space to carry food is decremented accordingly
					space -= defecit;
				}
				//otherwise refill as much as possible
				else {
					enc.getFoodstore().addFood(foodName, space);
					zooStore.takeFood(foodName, space);
					space = 0;
				}
				
			}
		}
		
		//returns how much food was taken
		return origSpace - space;
	}

	/*
	 * removes as much waste as possible from the enclosure.
	 */
	private int removeWaste(Enclosure enc, int space) {
		int wasteAmount = enc.getWasteSize();

		// If there is more waste than can be carried, just remove as much as possible.
		if(wasteAmount > space) {
			enc.removeWaste(space);
			return space;
		}
		
		// Otherwise remove all the waste from the enclosure
		else {
			enc.removeWaste(wasteAmount);
			return wasteAmount;
		}
	}
	
	public boolean assignExhibit(Enclosure enc) {
		if(myExhibits.size() < maxExhibits) {
			myExhibits.add(enc);
			return true;
		}
		else {
			System.err.println("Keeper cannot be assigned to any more exhibits.");
			return false;
		}
	}
	
	/*
	 * Attempts to treat an animal, returning true if successful, and false if unsuccessful
	 */
	private boolean treat(Animal a) {
		if(treatsAvailable.contains(a.getTreatType())) {
			a.treat();
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void addTreatAvailable(String treat) {
		treatsAvailable.add(treat);
	}

}
