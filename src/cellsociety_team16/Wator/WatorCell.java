package cellsociety_team16.Wator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cellsociety_team16.Cell.BaseCell;
import cellsociety_team16.Cell.Cell;
import cellsociety_team16.Cell.CellState;
import cellsociety_team16.Cell.UnknownCellStateException;
import cellsociety_team16.Manager.DataManager;
import cellsociety_team16.Util.RandUtil;

class WatorCell extends BaseCell {

	private Animal myAnimal;
	private Animal myNextAnimal;
	
	protected WatorCell(CellState state, Animal animal) {
		super(state);
		if (state.equals(WatorState.FISH)) {
			assert(animal instanceof Fish);
		} else if (state.equals(WatorState.SHARK)) {
			assert(animal instanceof Shark);
		} else {
			assert(animal == null);
		}
		myAnimal = animal;
	}
	
	@Override
	public void step(List<Cell> neighbors) {
		if (getState().equals(WatorState.SHARK)) {
			assert(myAnimal instanceof Shark);
			actAsShark(neighbors);
		} else if (getState().equals(WatorState.FISH)){
			assert(myAnimal instanceof Fish);
			actAsFish(neighbors);
		} else if (getState().equals(WatorState.EMPTY)){
			assert(myAnimal == null);
			actAsEmptyCell();
		} else {
			throw new UnknownCellStateException("WatorCell", getState());
		}
	}
	
	private void actAsShark(List<Cell> neighbors) {
		/* Get two lists of fish neighbors and empty neighbors respectively */
		List<Cell> fishNbrs = new ArrayList<>();
		List<Cell> emptyNbrs = new ArrayList<>();
		for (Cell nbr : neighbors) {
			if (nbr.getState().equals(WatorState.FISH)) {
				fishNbrs.add(nbr);
			} else if (
				nbr.getState().equals(WatorState.EMPTY)
				&& (
					nbr.peepNextState() == null
					|| nbr.peepNextState().equals(WatorState.EMPTY)
				)
			) {
				emptyNbrs.add(nbr);
			}
		}
		Collections.shuffle(emptyNbrs);
		
		/* Get the shark residing in the cell */
		Shark shark = (Shark) myAnimal;
		
		/* Breeding logic */
		if (shark.isReadyToBreed() && emptyNbrs.size() > 0) {
			/* Choose the last cell in the shuffled list as the place to breed*/
			shark.breed();
			WatorCell emptyCellToBreed = (WatorCell) emptyNbrs.get(emptyNbrs.size() - 1);
			emptyNbrs.remove(emptyNbrs.size() - 1);
			int sharkBreedDays = DataManager.get().wator().getSharkBreed();
			int sharkStarveDays = DataManager.get().wator().getSharkStarve();
			emptyCellToBreed.setNextState(WatorState.SHARK);
			emptyCellToBreed.setNextAnimal(new Shark(sharkBreedDays, sharkStarveDays));
		}
		
		/* Eating logic */
		if (fishNbrs.size() > 0) {
			/* eat a fish and stays */
			WatorCell fishCellToEat = (WatorCell) fishNbrs.get(RandUtil.nextInt(fishNbrs.size()));
			((Fish) fishCellToEat.myAnimal).eatenByShark();
			fishCellToEat.setNextState(WatorState.EMPTY);
			fishCellToEat.setNextAnimal(null);
			shark.eat();
			setNextState(WatorState.SHARK);
			setNextAnimal(shark);
		} else {
			/* nothing to eat, either die, move or stay */
			if (shark.isDeadStarving()) {
				/* die */
				setNextState(WatorState.EMPTY);
				setNextAnimal(null);
			} else if (emptyNbrs.size() > 0) {
				/* move */
				WatorCell emptyCellToMove = (WatorCell) emptyNbrs.get(emptyNbrs.size() - 1);
				emptyCellToMove.setNextState(WatorState.SHARK);
				emptyCellToMove.setNextAnimal(shark);
				setNextState(WatorState.EMPTY);
				setNextAnimal(null);
			} else {
				/* stay still */
				setNextState(WatorState.SHARK);
				setNextAnimal(shark);
			}
		}
		
		/* Shark steps */
		shark.step();
	}
	
	private void actAsFish(List<Cell> neighbors) {
		Fish fish = (Fish) myAnimal;
		
		/* Get a list of empty neighbor cells */
		List<Cell> emptyNbrs = new ArrayList<>();
		for (Cell nbr : neighbors) {
			if (
				nbr.getState().equals(WatorState.EMPTY)
				&& (
					nbr.peepNextState() == null
					|| nbr.peepNextState().equals(WatorState.EMPTY)
				)
			) {
				emptyNbrs.add(nbr);
			}
		}
		Collections.shuffle(emptyNbrs);
		
		/* Breeding logic */
		if (fish.isReadyToBreed() && emptyNbrs.size() > 0) {
			/* Choose the last cell in the shuffled list as the place to breed*/
			fish.breed();
			WatorCell emptyCellToBreed = (WatorCell) emptyNbrs.get(emptyNbrs.size() - 1);
			emptyNbrs.remove(emptyNbrs.size() - 1);
			int fishBreedDays = DataManager.get().wator().getFishBreed();
			emptyCellToBreed.setNextState(WatorState.FISH);
			emptyCellToBreed.setNextAnimal(new Fish(fishBreedDays));
		}
		
		/* Die, move or stay */
		if (fish.isEaten()) {
			/* Die */
			setNextState(WatorState.EMPTY);
			setNextAnimal(null);
		} else if (emptyNbrs.size() > 0) {
			/* move */
			WatorCell emptyCellToMove = (WatorCell) emptyNbrs.get(emptyNbrs.size() - 1);
			emptyCellToMove.setNextState(WatorState.FISH);
			emptyCellToMove.setNextAnimal(fish);
			setNextState(WatorState.EMPTY);
			setNextAnimal(null);
		} else {
			/* stay */
			setNextState(WatorState.FISH);
			setNextAnimal(fish);
		}
		
		/* Fish steps */
		fish.step();
	}
	
	private void actAsEmptyCell() {
		if (peepNextState() == null) {
			setNextState(WatorState.EMPTY);
			setNextAnimal(null);
		}
	}
	
	private void setNextAnimal(Animal animal) {
		myNextAnimal = animal;
	}
	
	@Override
	public void setCurrentState(CellState currentState) {
		super.setCurrentState(currentState);
		if (currentState.equals(WatorState.EMPTY)) {
			myAnimal = null;
		} else if (currentState.equals(WatorState.FISH)) {
			int fishBreedDays = DataManager.get().wator().getFishBreed();
			myAnimal = new Fish(fishBreedDays);
		} else if (currentState.equals(WatorState.SHARK)) {
			int sharkBreedDays = DataManager.get().wator().getSharkBreed();
			int sharkStarveDays = DataManager.get().wator().getSharkStarve();
			myAnimal = new Shark(sharkBreedDays, sharkStarveDays);
		} else {
			throw new UnknownCellStateException("WatorCell", currentState);
		}
	}

	
	@Override
	public void evolve() {
		super.evolve();
		myAnimal = myNextAnimal;
		myNextAnimal = null;
	}
	
	public void setAnimal(Animal animal) {
		myAnimal = animal;
	}
}
