package ecsZoo;

import java.util.ArrayList;
import java.util.Iterator;

public class Zoo {

	//Here I use an ArrayList over a primitive array to make it easier to add enclosures 1 by 1 in the configuration file (also zoos often build and demolish enclosures)
	private ArrayList<Enclosure> exhibits = new ArrayList<Enclosure>();
	private ArrayList<Zookeeper> keepers = new ArrayList<Zookeeper>();
	private Foodstore zooStore = new Foodstore();
	private int month = 0;
	
	//How many months the simulation will run for
	private int simTime = 360;

	public Zoo() {
		this(0);
	}
	
	public Zoo(int noOfExhibits) {
		this(noOfExhibits, noOfExhibits);
	}
	
	public Zoo(int noOfExhibits, int noOfKeepers) {
		
		for(int i = 0; i < noOfExhibits; i++) {
			exhibits.add(new Enclosure());
		}
		
		for(int i = 0; i < noOfKeepers; i++) {
			keepers.add(new Zookeeper(zooStore));
		}
		
	}
	
	public void aMonthPasses() {
		
		System.out.println();
		
		for(Enclosure e : exhibits) {
			e.aMonthPasses();
		}
		
		System.out.println();
		
		for(Zookeeper z : keepers) {
			z.aMonthPasses();
		}
		
		stockStore();
		
		System.out.println();
	}

	/*
	 * used to re-stock the store each month
	 */
	private void stockStore() {
		zooStore.addFood("hay", 10);
		zooStore.addFood("steak", 10);
		zooStore.addFood("fruit", 10);
		zooStore.addFood("celery", 5);
		zooStore.addFood("fish", 5);
		zooStore.addFood("ice cream", 5);
		
		System.out.println();
		printFoodstore();
	}

	//The default simulation will execute this to fill the store
	public void fillStore() {
		
		System.out.println("Filling main store.");
		
		zooStore.addFood("hay", 1000);
		zooStore.addFood("steak", 1000);
		zooStore.addFood("fruit", 1000);
		zooStore.addFood("celery", 500);
		zooStore.addFood("fish", 500);
		zooStore.addFood("ice cream", 500);
		
		printFoodstore();

		
	}
	
	// Prints out a table containing the values of the main foodstore.
	private void printFoodstore() {
		System.out.println("--------- Main Store --------");
		zooStore.printStore();
	}

	public void addKeeper(Zookeeper z) {
		keepers.add(z);
	}
	
	public void addEnclosure(Enclosure e) {
		exhibits.add(e);
	}
	
	public Enclosure getLastEnc() {
		return exhibits.get(exhibits.size()-1);
	}
	
	// Gets the reference to an enclosure based on its index in the ArrayList
	public Enclosure getEnclosure(int encNum) {
		
		//If the enclosure index specified is in the array, return it
		if(encNum < exhibits.size()) {
			return exhibits.get(encNum);
		}
		
		//Othewise, return null to denote failure
		else return null;
	}
	
	public Foodstore getStore() {
		return zooStore;
	}
	
	/*
	 * Once a zoo is created, keepers are assigned enclosures which they will look after.
	 * If there are more keepers than enclosures, some will be left unassigned, or if there are more
	 * exhibits than keepers, some keepers will look after multiple enclosures.
	 */
	private void assignKeepers() {
		
		int noOfEncs = exhibits.size();
		int noOfKeepers = keepers.size();
		
		if(noOfKeepers == noOfEncs) {
			for(int i = 0; i < exhibits.size(); i++) {
				keepers.get(i).assignExhibit(exhibits.get(i));
				
			}
			System.out.println("Keepers assigned. No spare keepers.");
		}
		
		//If there are more enclosure then keepers, some will be spare
		if(noOfKeepers > noOfEncs) {
			for(int i = 0; i < noOfEncs; i++) {
				keepers.get(i).assignExhibit(exhibits.get(i));
				
			}
			System.out.println("Keepers assigned. There are spare keepers.");
		}
		
		if(noOfEncs > noOfKeepers) {
			
			//This is because in my code keepers can only manage up to 3 enclosures each, this could be modified
			if ((noOfEncs/noOfKeepers) > 3) {
				System.err.println("Not enough keepers!");
			}
			
			//Distributes enclosures evenly amongst keepers
			else {
				
				Iterator<Enclosure> it = exhibits.iterator();
				
				int j = 0;
				while(it.hasNext()) {
					
					keepers.get(j).assignExhibit(it.next());
					j++;
					
					//If there are no more keepers, go back to the start of the list of keepers
					if(j >= noOfKeepers) j = 0;
				}
				
				System.out.println("Keepers assinged to multiple enclosures.");
			}
		}
	}
	

	public void go() {
		
		
		//Prints out some information about the initial zoo
		System.out.println("------------ Zoo ------------");
		System.out.println("There are " + this.exhibits.size() + " enclosures in the zoo");
		System.out.println("There are " + this.keepers.size() + " keepers in the zoo");
		assignKeepers();
		printFoodstore();
		
		//For a number of months run the zoo by calling the aMonthPasses method
		while(month < simTime) {
			System.out.println("---------- Month " + month + " ----------");
			aMonthPasses();
			month++;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.err.println("Error: " + e);
			}
		}
	}






}



