package ecsZoo;

import java.util.Hashtable;

public abstract class Animal {
	
	// 'm' = male , 'f' = female
	private char gender;
	
	// Age of animal in whole months. 
	private int age;
	
	// This array of strings represents what food the animal can eat.
	private String[]  diet;
	
	// Used if no diet is specified
	private static String[] defaultDiet = {"ice cream"};
	
	// A value between 0 and 10. 10 is very health, 0 is dead.
	private int health;
	
	// The average age in months the animal will live for.
	private int lifeExpectancy;
	
	// The enclosure in which the animal lives.
	private Enclosure home;
	
	// The type of treat required for this animal
	private String treatType;
	
	//Used to model pregnancy
	private boolean pregnant = false;
	private int pregTimer = -1;
	
	private Hashtable<String, FoodValues> foodTable;
	
	
	public Animal(char gender, int age, int lifeExpectancy, int health, String[] eats, String treat) {
		
		/*
		 * Populates a hashtable with foods as the keys, and their healing and waste attributes as the values,
		 * contained in a foodValues object.
		 */
		foodTable = new Hashtable<String, FoodValues>();
		foodTable.put("hay", new FoodValues(1,4));
		foodTable.put("steak", new FoodValues(3,4));
		foodTable.put("fruit", new FoodValues(2,3));
		foodTable.put("celery", new FoodValues(0,1));
		foodTable.put("fish", new FoodValues(3,2));
		foodTable.put("ice cream", new FoodValues(1,3));
		
		// Set global variables based on the arguments.
		this.gender = gender;
		this.age =  age;
		this.health = health;
		this.lifeExpectancy = lifeExpectancy;
		this.diet = eats;
		this.treatType = treat;
	}
	
	// Creates a defualt animal with certain parameters.
	public Animal(String[] diet, String treat) {
		this('m', 0, 10, 120, diet, treat);
	}
	
	/*
	 * The default constructor theoretically should not be needed, however this constructor gives some 
	 * arbitrary values for the diet and treat type.
	 */
	public Animal() {
		this(defaultDiet, "stroke");
	}
	
	//Returns true if the food specified is part of the animal's diet
	public boolean canEat(String food) {
		boolean contains = false;
		for(String f : diet) {
			if(f.equals(food)) {
				contains = true;
			}
		}
		return contains;
	}
	
	protected void incrementAge() {
		age++;
	}
	
	//Used to cause death from causes other than starvation
	protected void die() {
		health = 0;
	}
	
	public String[] getDiet() {
		return diet;
	}
	
	/*
	 *  The eat method checks the animal's enclosure's foodstore for food the animal can eat. Once it has found some, it will eat it,
	 *  but it will not eat any more
	 */
	public void eat() {
		Foodstore myStore = this.getEnclosure().getFoodstore();
		for(String food : diet) {
			if(myStore.takeFood(food)) {
				FoodValues fVals = foodTable.get(food);
				health = health + fVals.getHealing();
				if (health > 10) {
					health = 10;
				}
				System.out.println("An animal ate " + food);
				this.getEnclosure().addWaste(fVals.getWaste());
				return;
			}
		}
	}
	
	//Each month an animals health will go down by 2
	public void decreaseHealth() {
		health = health - 2;
	}
	
	//Default treat
	public void treat() {
		this.treat(4);
	}
	
	//Increases an animals health according to the argument when treated
	public void treat(int health) {
		this.health += health;
		if (health > 10) {
			health = 10;
		}
	}
	
	public abstract boolean aMonthPasses();
	
	public int getAge() {
		return age;
	}
	
	public char getGender() {
		return gender;
	}
	
	public int getLifeExpectancy() {
		return lifeExpectancy;
	}
	
	public String getTreatType(){
		return treatType;
	}
	
	public void setEnclosure(Enclosure encl) {
		home = encl;
	}
	
	public Enclosure getEnclosure() {
		return home;
	}
	
	//if the animal has 0 or less health, this returns true meaning it is dead
	protected boolean isDead() {
		if(health <= 0) return true;
		else return false;
	}
	
	//returns true if the animal is at full health(10)
	public boolean isHealthy() {
		if(health == 10) return true;
		else return false;
	}
	
	protected boolean isPregnant() {
		return pregnant;
	}
	
	private void togglePregnant() {
		pregnant = !pregnant;
	}
	
	//Gestation period of 6 months by default
	protected void makePregnant() {
		this.makePregnant(6);
	}
	
	protected void makePregnant(int gestation) {
		if(gestation > 0) {
			pregTimer = gestation;
			this.togglePregnant();
			System.out.println("An animal became pregnant");
		}
		else {
			System.err.println("Gestation period must be at least 1 month.");
			this.makePregnant();
		}
	}
	
	//returns true if the pregnancy is complete, so animal can give birth
	protected boolean gestate() {
		pregTimer--;
		if(pregTimer <= 0) {
			this.togglePregnant();
			pregTimer = -1;
			return true;
		}
		else {
			return false;
		}
	}
	
	protected abstract void giveBirth();

}
	
