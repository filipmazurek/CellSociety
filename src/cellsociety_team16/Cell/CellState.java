// This entire file is part of my masterpiece.
// Jay Wang zw48
/*
 * I chose this file as one of my code masterpieces is because
 * instead of using int primitive to represent the cell state, 
 * I made it a class and override its hashCode() and equals() methods.
 * It is essentially a wrapper class for an integer, but the abstraction 
 * is a good practice in coding - we don't wanna expose our data structure
 * directly to the outside world as primitives, and furthermore, if we want 
 * to add some extra information to a Cell State in the future, having a class would be
 * much easier to refactor.
 */
package cellsociety_team16.Cell;

/**
 * A wrapper class of integer that will be used to represent
 * a Cell's state
 * @author Jay
 */
public class CellState {
	private int myValue;
	
	/**
	 * Constructs a CellState.
	 * @param value the int value of the state.
	 */
    public CellState(int value) {
        myValue = value;
    }
    
    /**
     * Get the actual value.
     * @return state value.
     */
    public int getStateValue() {
        return myValue;
    }

    @Override
    public int hashCode() {
        return myValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CellState)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        CellState otherState = (CellState) obj;
        return this.myValue == otherState.getStateValue();
    }
}
