package ecsZoo;

import java.util.Random;

public class Giraffe extends Ungulate{
	
	public Giraffe(char gender, int age, int health) {
		super(gender, age, 28, health, "massage");
	}
	
	public Giraffe() {
		this('f', 0, 10);
	}

	//This overrides the superclass Animal, and is called by the zookeepers.
	public void treat() {
		this.neckMassage();
	}
	
	//This calls the overloaded method in the Animal superclass, which increments the health accordingly
	private void neckMassage() {
		this.treat(4);
	}
	
	//Implements the abstract method from animal. When this method is called a baby giraffe, with a 50/50 chance of being male/female is born.
	protected void giveBirth() {
		char gender;
		Random random = new Random();
		
		if(random.nextBoolean()) {
			gender = 'f';	
		}
		else {
			gender = 'm';
		}
		
		this.getEnclosure().addAnimal(new Giraffe(gender, 0, 4));
		System.out.println("A baby giraffe was born");
	}
	
	protected void printDeathMsg() {
		System.out.println("A giraffe has died.");
	}
}
