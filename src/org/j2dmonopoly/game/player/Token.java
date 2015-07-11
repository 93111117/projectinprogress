package org.j2dmonopoly.game.player;

import java.awt.image.BufferedImage;

public class Token
{

	private final int type;
	private BufferedImage image;

	public Token(final int type)
	{
		this.type = type;
		switch (type)
		{
			case Tokens.DOG:
				image = Tokens.DOG_IMG;
				break;

			case Tokens.BOOT:
				image = Tokens.BOOT_IMG;
				break;

			case Tokens.RACE_CAR:
				image = Tokens.RACE_CAR_IMG;
				break;

			case Tokens.THIMBLE:
				image = Tokens.THIMBLE_IMG;
				break;

			case Tokens.TOP_HAT:
				image = Tokens.TOP_HAT_IMG;
				break;

			case Tokens.WHEEL_BARROW:
				image = Tokens.WHEEL_BARROW_IMG;
				break;

			case Tokens.BATTLE_SHIP:
				image = Tokens.BATTLE_SHIP_IMG;
				break;

			case Tokens.IRON:
				image = Tokens.RACE_CAR_IMG;
				break;

			default:
				break;
		}
	}

	public int getType()
	{
		return type;
	}

	public BufferedImage getImage()
	{
		return image;
	}
}
