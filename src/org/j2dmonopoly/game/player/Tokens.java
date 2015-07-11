package org.j2dmonopoly.game.player;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.j2dmonopoly.game.J2DMonopoly;
import org.j2dmonopoly.util.Transparency;

public class Tokens
{

	public static final int TOP_HAT = 1;
	public static BufferedImage TOP_HAT_IMG;

	public static final int IRON = 2;
	public static BufferedImage IRON_IMG;

	public static final int RACE_CAR = 3;
	public static BufferedImage RACE_CAR_IMG;

	public static final int DOG = 4;
	public static BufferedImage DOG_IMG;

	public static final int BOOT = 5;
	public static BufferedImage BOOT_IMG;

	public static final int THIMBLE = 6;
	public static BufferedImage THIMBLE_IMG;

	public static final int WHEEL_BARROW = 7;
	public static BufferedImage WHEEL_BARROW_IMG;

	public static final int BATTLE_SHIP = 8;
	public static BufferedImage BATTLE_SHIP_IMG;

	public static void loadTokens()
	{

		J2DMonopoly.print("Loading tokens...");
		final long start = System.currentTimeMillis();
		final String tokenDir = "assets/sprites/tokens/";
		try
		{
			TOP_HAT_IMG = Transparency.makeTransparent(tokenDir + "hat.png");
			IRON_IMG = Transparency.makeTransparent(tokenDir + "iron.png");
			RACE_CAR_IMG = Transparency.makeTransparent(tokenDir + "car.png");
			DOG_IMG = Transparency.makeTransparent(tokenDir + "dog.png");
			BOOT_IMG = Transparency.makeTransparent(tokenDir + "boot.png");
			THIMBLE_IMG = Transparency.makeTransparent(tokenDir + "thimble.png");
			WHEEL_BARROW_IMG = Transparency.makeTransparent(tokenDir + "barrow.png");
			BATTLE_SHIP_IMG = Transparency.makeTransparent(tokenDir + "ship.png");
			final long end = System.currentTimeMillis() - start;
			J2DMonopoly.print("Tokens loaded! (took: " + end + "ms)");
		} catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static BufferedImage getToken(final int tokenID)
	{
		BufferedImage toReturn = null;
		switch (tokenID)
		{
			case Tokens.DOG:
				toReturn = Tokens.DOG_IMG;
				break;

			case Tokens.BOOT:
				toReturn = Tokens.BOOT_IMG;
				break;

			case Tokens.RACE_CAR:
				toReturn = Tokens.RACE_CAR_IMG;
				break;

			case Tokens.THIMBLE:
				toReturn = Tokens.THIMBLE_IMG;
				break;

			case Tokens.TOP_HAT:
				toReturn = Tokens.TOP_HAT_IMG;
				break;

			case Tokens.WHEEL_BARROW:
				toReturn = Tokens.WHEEL_BARROW_IMG;
				break;

			case Tokens.BATTLE_SHIP:
				toReturn = Tokens.BATTLE_SHIP_IMG;
				break;

			case Tokens.IRON:
				toReturn = Tokens.IRON_IMG;
				break;

			default:
				break;
		}
		return toReturn;
	}

	public static String getName(final int tokenID)
	{
		String toReturn = "";
		switch (tokenID)
		{
			case Tokens.DOG:
				toReturn = "Dog";
				break;

			case Tokens.BOOT:
				toReturn = "Boot";
				break;

			case Tokens.RACE_CAR:
				toReturn = "Car";
				break;

			case Tokens.THIMBLE:
				toReturn = "Thimble";
				break;

			case Tokens.TOP_HAT:
				toReturn = "Hat";
				break;

			case Tokens.WHEEL_BARROW:
				toReturn = "Barrow";
				break;

			case Tokens.BATTLE_SHIP:
				toReturn = "Ship";
				break;

			case Tokens.IRON:
				toReturn = "Iron";
				break;

			default:
				break;
		}
		return toReturn;
	}
}
