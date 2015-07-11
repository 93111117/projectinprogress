package org.j2dmonopoly.game.bank;

public class Bill
{

	private int type;
	private int amount;

	public Bill(final int type, final int amount)
	{
		this.type = type;
		this.amount = amount;
	}

	public int getType()
	{
		return type;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setType(final int type)
	{
		this.type = type;
	}

	public void setAmount(final int amount)
	{
		this.amount = amount;
	}
}
