package cellsociety_team16.Wator;

public class Fish extends Animal {

	private boolean myIsEaten;
	
	public Fish(int breedDays) {
		super(breedDays);
		myIsEaten = false;
	}
	
	public void eatenByShark() {
		myIsEaten = true;
	}
	
	public boolean isEaten() {
		return myIsEaten;
	}
	
}
