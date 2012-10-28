package org.nzdis.tictactoe;

import org.nzdis.fragme.factory.FactoryObject;
import org.nzdis.fragme.factory.FragMeFactory;
import org.nzdis.fragme.objects.FMeObject;

public class TicTacToeModel extends FMeObject {

	public String[] positions = { "*", "1", "2", "3", "4", "5", "6", "7", "8",
			"9" }; // "board" positions
	private boolean new_game;
	public String lastMove="";

	public TicTacToeModel() {
	}

	public boolean hasWon(String anXorO) {
		// Answer whether string anXorO is in a winning position
		// String anXorO is expected to be either an "X" or an "O"
		// check the rows for XXX or OOO
		if ((positions[1].equals(anXorO)) && (positions[2].equals(anXorO))
				&& positions[3].equals(anXorO))
			return true;
		else if ((positions[4].equals(anXorO)) && (positions[5].equals(anXorO))
				&& positions[6].equals(anXorO))
			return true;
		else if ((positions[7].equals(anXorO)) && (positions[8].equals(anXorO))
				&& positions[9].equals(anXorO))
			return true;
		// check the columns for XXX or OOO
		else if ((positions[1].equals(anXorO)) && (positions[4].equals(anXorO))
				&& positions[7].equals(anXorO))
			return true;
		else if ((positions[2].equals(anXorO)) && (positions[5].equals(anXorO))
				&& positions[8].equals(anXorO))
			return true;
		else if ((positions[3].equals(anXorO)) && (positions[6].equals(anXorO))
				&& positions[9].equals(anXorO))
			return true;
		// check the diagonals for XXX or OOO
		else if ((positions[1].equals(anXorO)) && (positions[5].equals(anXorO))
				&& positions[9].equals(anXorO))
			return true;
		else if ((positions[3].equals(anXorO)) && (positions[5].equals(anXorO))
				&& positions[7].equals(anXorO))
			return true;
		return false;
	}

	public boolean isGameOver() {
		// Answer whether the game is over
		// The game is over if either "X" or "O" have a winning
		// position or
		// there are no more free positions
		if (!hasFreePositions()){
			lastMove="";
			return true;
		}
		if (hasWon("X")){
			lastMove="";
			return true;
		}
		if (hasWon("O")){
			lastMove="";
			return true;
		}
			
		return false;
	}

	private boolean hasFreePositions() {
		// Answer whether there are still free places to play on
		boolean hasFreePlacesToPlay = false;
		for (int i = 1; i < positions.length; i++)
			if (isFree(i))
				hasFreePlacesToPlay = true;
		return hasFreePlacesToPlay;
	}

	// private int numberOfFreePositions(){
	// //Answer the number of free places left to play on
	// int numberFree = 0;
	// for(int i= 1; i<positions.length; i++)
	// if(isFree(i)) numberFree++;
	//
	// return numberFree;
	// }
	private boolean isFree(int aPosition) {
		// Answer whether position aPosition is free to play on
		// It is not free if there is an "X" or "O" on it
		if ((aPosition < 1) || (aPosition > 9))
			return false; // positions are out of range
		return !((positions[aPosition].equals("X")) || (positions[aPosition]
				.equals("O")));
	}

	// private boolean isX(int aPosition){
	// //Answer whether position aPosition has an "X" on it
	//
	// if((aPosition < 1)|| (aPosition > 9)) return false; //positions are
	// out
	// of range
	// return positions[aPosition].equals("X");
	// }
	//
	// private boolean isO(int aPosition){
	// //Answer whether position aPosition has an "O" on it
	//
	// if((aPosition < 1)|| (aPosition > 9)) return false; //positions are
	// out
	// of range
	// return positions[aPosition].equals("O");
	// }
	public boolean XPlays(int aPosition) {
		// Make a move for the challenger X
		// Mark position aPosition with an X if it's free
		// if the position is not free return false indicating the move
		// did not
		// succeed
		if (isFree(aPosition)&&(!lastMove.equals("X"))) {
			positions[aPosition] = "X";
			return true;
		}
		return false;
	}

	public boolean OPlays(int aPosition) {
		// Make a move for the defender O
		// Mark position aPosition with an "O" if it's free
		// if the position is not free return false indicating the move
		// did not
		// succeed
		if (isFree(aPosition)&&(!lastMove.equals("O"))) {
			positions[aPosition] = "O";
			return true;
		}
		return false;
	}

	public void restart() {
		positions[0] = "*";
		for (int i = 1; i <= 9; i++) {
			positions[i] = String.valueOf(i);
		}
		lastMove="";
	}

	public void changedObject() {
		System.out.println("Received a change notification!");
		this.setChanged();
		this.notifyObservers();
	}

	public void deletedObject() {
		System.out.println("Received a delete notification!");
		this.setChanged();
		this.notifyObservers();
	}

	public void deserialize(FMeObject serObject) {
		this.positions = ((TicTacToeModel) serObject).getPositions();
		this.lastMove=((TicTacToeModel) serObject).getLastMove();
	}

	private static class Factory extends FragMeFactory {
		protected FactoryObject create() {
			return new TicTacToeModel();
		}
	}

	static {
		FragMeFactory.addFactory(new Factory(), TicTacToeModel.class);
	}

	public String[] getPositions() {
		return positions;
	}

	public void setPositions(String[] positions) {
		this.positions = positions;
	}
	
	public String getLastMove(){
		return lastMove;
	}
	
	public void setLastMove(String lastMove){
		this.lastMove=lastMove;
	}

	public boolean isNew_game() {
		return this.new_game;
	}

	public void setNew_game(boolean new_game) {
		this.new_game = new_game;
	}
}