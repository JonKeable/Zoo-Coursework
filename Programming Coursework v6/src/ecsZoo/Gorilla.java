package ecsZoo;

import java.util.Random;

public class Gorilla extends Ape{

	public Gorilla(char gender, int age, int health) {
		super(gender, age, 32, health, "paint");
	}
	
	public Gorilla() {
		this('m', 0, 10);
	}

	//This overrides the superclass Animal, and is called by the zookeepers.
	public void treat() {
		this.painting();
	}
	
	//This calls the overloaded method in the Animal superclass, which increments the health accordingly
	private void painting() {
		this.treat(4);
	}
	
	//Implements the abstract method from animal. When this method is called a baby gorilla, with a 50/50 chance of being male/female is born.
	protected void giveBirth() {
		char gender;
		Random random = new Random();
			
		if(random.nextBoolean()) {
			gender = 'f';	
		}
		else {
			gender = 'm';
		}
		
		this.getEnclosure().addAnimal(new Gorilla(gender, 0, 4));
		System.out.println("A baby gorilla was born");	
	}
	
	protected void printDeathMsg() {
		System.out.println("A gorilla has died.");
	}
}
