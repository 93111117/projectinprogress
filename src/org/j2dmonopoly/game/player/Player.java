package org.j2dmonopoly.game.player;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.j2dmonopoly.game.J2DMonopoly;
import org.j2dmonopoly.game.bank.Bank;
import org.j2dmonopoly.game.bank.Bill;

public class Player
{

	// 500x2
	// 100x2
	// 50x2
	// 20x6
	// 10x5
	// 5x5
	// 5x1
	// All remaining money goes to the Bank.

	public static final int STARTING_AMOUNT = 1500;
	private final String name;
	private final ArrayList<Bill> bills;
	private int cash;

    private int height=30;
	private final BufferedImage token;
	private int x;
	private int y;
	private final J2DMonopoly game;
	private final int tokenID;
	private boolean isController;
	private boolean spaceEventFired;
	private boolean playerTurn;
	private boolean spacePressed, spaceReleased;

	public Player(final J2DMonopoly game, final String name, final int tokenID)
	{
		this.tokenID = tokenID;
		this.game = game;
		this.name = name;
		token = Tokens.getToken(tokenID);
		bills = new ArrayList<Bill>();
	}
    public void setheight(int h){
        this.height=h;
    }
    public int getheight(){
        return this.height;
    }


	public void update()
	{
		final boolean rollRequested = spaceReleased && !spaceEventFired;
		if (rollRequested && !game.getGameBoard().diceRolling)
		{

			J2DMonopoly.print("Player -> Spacebar pressed/released");
			spaceEventFired = true;
			// execute dice roll for this player.
			// (space bar has been released @ this point)
			game.getGameBoard().diceRollStart = System.currentTimeMillis();
			game.getGameBoard().diceRolling = true;
		}
	}

	public void keyPressed(final int keyCode)
	{
		// J2DMonopoly.print("KeyPressed: " + keyCode);
		switch (keyCode)
		{
			case 32: // space
				if (!game.getGameBoard().diceRolling)
				{
					spacePressed = true;
					spaceEventFired = false;
					if (spaceReleased)
					{
						spaceReleased = false;
					}
				}
				break;
			default:
				break;
		}
	}

	public void keyReleased(final int keyCode)
	{
		// J2DMonopoly.print("KeyReleased: " + keyCode);
		switch (keyCode)
		{
			case 32: // space
				if (!game.getGameBoard().diceRolling)
				{
					spacePressed = false;
					spaceReleased = true;
				}
				break;
			default:
				break;
		}
	}

	public void keyTyped(final int keyCode)
	{
	}

	/**
	 * Used to set players starting cash. (bills)
	 * 
	 * @param bank
	 *            The bank in the game.
	 * @param playerBills
	 *            The bills to add to this player.
	 */
	public void setStartingBills(final Bank bank, final ArrayList<Bill> playerBills)
	{

		// Add the 'playerBills' to our bills.
		for (final Bill bill : playerBills)
		{

			// J2DMonopoly.print("Bank -> Giving $" + bill.getType() * bill.getAmount() + " to '" + name + "' ($" + bill.getType() + "x"+ bill.getAmount() + ")");
			bills.add(bill);

			// Remove the bills added to this player from the bank.
			bank.removeBill(bill.getType(), bill.getAmount());
			// System.out.println(name+" -> Removed a ("+bill.getType()+") bill from bank (x"+bill.getAmount()+")");

		}

		// Determine out cash amount.
		for (final Bill bill : bills)
		{
			cash += bill.getType() * bill.getAmount();
		}
		// J2DMonopoly.print("Player -> " + name + " cash: $" + CashFormatter.format(cash));
	}

	public String getName()
	{
		return name;
	}

	public int getCash()
	{
		return cash;
	}

	public ArrayList<Bill> getBills()
	{
		return bills;
	}

	public BufferedImage getToken()
	{
		return token;
	}

	public void setX(final int x)
	{
		this.x = x;
	}

	public void setY(final int y)
	{
		this.y = y;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public void setController(final boolean isController)
	{
		this.isController = isController;
	}

	public boolean isController()
	{
		return isController;
	}

	public boolean isPlayerTurn()
	{
		return playerTurn;
	}

	public void setPlayerTurn(final boolean playerTurn)
	{
		this.playerTurn = playerTurn;
	}

	public String getTokenName()
	{
		return Tokens.getName(tokenID);
	}
}
