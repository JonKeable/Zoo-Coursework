package ecsZoo;

import java.util.Iterator;
import java.util.Random;

public class Bear extends Animal{

	static String[] tempDiet = {"fish", "steak"};
	
	public Bear(char gender, int age, int health) {
		super(gender, age, 18, health, tempDiet, "hug");
	}
	
	public Bear() {
		this('m', 0, 10);
	}
	
	public boolean aMonthPasses() {
		//takes damage specified in superclass every month
		this.decreaseHealth(); 
		
		//tries to eat and produces waste if applicable
		this.eat(); 
		
		//each month the animal gets 1 month older
		this.incrementAge();
		
		//If the animal is a female of age greater than 8 months, try to become pregnant, or progress a current pregnancy.
		if(this.getGender() == 'f' && this.getAge() >= 8) {
			if(!this.isPregnant()) {
				this.tryToBreed();
			}
			else if(this.gestate()) {
				this.giveBirth();
			}
		}
		
		//once animals reach life expectancy, they die (health := 0), unless they are pregnant in which case they will live long enough to give birth.
		if(this.getAge() >= this.getLifeExpectancy() && !this.isPregnant()) {
			this.die();
		}
		
		//If the animal has less than 1 health, this returns false(can be used to model death)
		if(this.isDead()) {
			System.out.println("A bear has died!");
			return false; //dead
		}
		else return true; //alive
	}
	
	//This overrides the superclass Animal, and is called by the zookeepers.
	public void treat() {
		this.hug();
	}
	
	//This calls the overloaded method in the Animal superclass, which increments the health accordingly
	private void hug() {
		this.treat(3);
	}
	
	//Implements the abstract method from animal. When this method is called a baby bear, with a 50/50 chance of being male/female is born.
		protected void giveBirth() {
			char gender;
			Random random = new Random();
			
			if(random.nextBoolean()) {
				gender = 'f';	
			}
			else {
				gender = 'm';
			}
			
			this.getEnclosure().addAnimal(new Bear(gender, 0, 4));
			System.out.println("A baby bear was born");
		}
		
	
	//If there is male of the same species, try to breed with chance of 1 in 5. Can only try to mate with one individual per month.
	private void tryToBreed() {
		Iterator<Animal> it = this.getEnclosure().getResidentsIt();
		while(it.hasNext()) {
			Animal a = it.next();
			if(a.getClass().equals(this.getClass()) && a.getGender() == 'm') {
				Random random = new Random();
				if(random.nextInt(5) == 4) {
					this.makePregnant(8);
				}
				return;
			}
		}
	}
	
	
}
