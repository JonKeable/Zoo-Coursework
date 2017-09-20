package ecsZoo;

import java.util.Random;

public class Tiger extends BigCat{
	
	public Tiger(char gender, int age, int health) {
		super(gender, age, 24, health, "stroke");
	}
	
	public Tiger() {
		this('f', 0, 10);
	}

	//This overrides the superclass Animal, and is called by the zookeepers.
	public void treat() {
		this.stroked();
	}
	
	//This calls the overloaded method in the Animal superclass, which increments the health accordingly.
	public void stroked() {
		this.treat(3);
	}
	
	
	//Implements the abstract method from animal. When this method is called a baby tiger, with a 50/50 chance of being male/female is born.
	protected void giveBirth() {
		char gender;
		Random random = new Random();
		
		if(random.nextBoolean()) {
			gender = 'f';	
		}
		else {
			gender = 'm';
		}
		
		this.getEnclosure().addAnimal(new Tiger(gender, 0, 4));
		System.out.println("A baby tiger was born");
	}
	
	protected void printDeathMsg() {
		System.out.println("A tiger has died.");
	}
}
