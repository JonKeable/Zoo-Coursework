package ecsZoo;

import java.util.Random;

public class Elephant extends Ungulate{

	public Elephant(char gender, int age, int health) {
		super(gender, age, 36, health, "bath");
	}
	
	public Elephant() {
		this('f', 0, 10);
	}

	//This overrides the superclass Animal, and is called by the zookeepers.
	public void treat() {
		this.bath();
	}
	
	//This calls the overloaded method in the Animal superclass, which increments the health accordingly
	private void bath() {
		this.treat(5);
	}
	
	//Implements the abstract method from animal. When this method is called a baby elephant, with a 50/50 chance of being male/female is born.
	protected void giveBirth() {
		char gender;
		Random random = new Random();
		
		if(random.nextBoolean()) {
			gender = 'f';	
		}
		else {
			gender = 'm';
		}
		
		this.getEnclosure().addAnimal(new Elephant(gender, 0, 4));
		System.out.println("A baby elephant was born");
	}
	
	protected void printDeathMsg() {
		System.out.println("An elephant has died.");
	}
	
}
