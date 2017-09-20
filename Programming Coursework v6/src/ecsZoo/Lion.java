package ecsZoo;

import java.util.Random;

public class Lion extends BigCat{

	public Lion(char gender, int age, int health) {
		super(gender, age, 24, health, "stroke");
	}
	
	public Lion() {
		this('f', 0, 10);
	}

	//This overrides the superclass Animal, and is called by the zookeepers.
	public void treat() {
		this.stroked();
	}
	
	//This calls the overloaded method in the Animal superclass, which increments the health accordingly.
	public void stroked() {
		this.treat(2);
	}
	
	
	//Implements the abstract method from animal. When this method is called a baby lion, with a 50/50 chance of being male/female is born.
	protected void giveBirth() {
		char gender;
		Random random = new Random();
		
		if(random.nextBoolean()) {
			gender = 'f';	
		}
		else {
			gender = 'm';
		}
		
		this.getEnclosure().addAnimal(new Lion(gender, 0, 4));
		System.out.println("A baby lion was born");
	}
	
	protected void printDeathMsg() {
		System.out.println("A lion has died.");
	}
}
