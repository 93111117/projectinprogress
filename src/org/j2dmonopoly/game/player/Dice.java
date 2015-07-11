package org.j2dmonopoly.game.player;

import org.j2dmonopoly.game.board.GameBoard;
import org.j2dmonopoly.util.RandomInt;

public class Dice
{
	private int number;
	private final RandomInt randomInt;

	public Dice(final int min, final int max)
	{
		randomInt = new RandomInt(min, max); // 1-6
	}

	public void roll()
	{
		number = randomInt.generateRandom();

	}

	public int getNumber()
	{
		return number;
	}

}