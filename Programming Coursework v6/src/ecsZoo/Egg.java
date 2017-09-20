package ecsZoo;

import java.util.Random;

class Egg extends Penguin{

	int eggTimer;
	
	protected Egg() {
		eggTimer = 3;
	}
	
	public boolean aMonthPasses() {
		eggTimer--;
		if(eggTimer <= 0) {
			this.hatch();
			return false;
		}
		else return true;
	}

	// When this method is called a baby penguin, with a 50/50 chance of being male/female,hatches.
	protected void hatch() {
		char gender;
		Random random = new Random();
			
		if(random.nextBoolean()) {
			gender = 'f';	
		}
		else {
			gender = 'm';
		}
			
		this.getEnclosure().addAnimal(new Penguin(gender, 0, 4));
		System.out.println("A baby penguin hatched");
		
	}
}


