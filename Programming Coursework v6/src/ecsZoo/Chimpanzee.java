package ecsZoo;

import java.util.Random;

public class Chimpanzee extends Ape{

	public Chimpanzee(char gender, int age, int health) {
		super(gender, age, 24, health, "chase");
	}
	
	public Chimpanzee() {
		this('m', 0, 10);
	}

	//This overrides the superclass Animal, and is called by the zookeepers.
	public void treat() {
		this.playChase();
	}
	
	//This calls the overloaded method in the Animal superclass, which increments the health accordingly
	private void playChase() {
		this.treat(4);
	}
	
	//Implements the abstract method from animal. When this method is called a baby chimpanzee, with a 50/50 chance of being male/female is born.
	protected void giveBirth() {
		char gender;
		Random random = new Random();
		
		if(random.nextBoolean()) {
			gender = 'f';	
		}
		else {
			gender = 'm';
		}
		
		this.getEnclosure().addAnimal(new Chimpanzee(gender, 0, 4));
		System.out.println("A baby chimp was born");
	}
	
	protected void printDeathMsg() {
		System.out.println("A chimpanzee has died.");
	}
}
