package org.j2dmonopoly.game.bank;

import java.util.ArrayList;

import org.j2dmonopoly.game.J2DMonopoly;
import org.j2dmonopoly.game.player.Player;
import org.j2dmonopoly.util.CashFormatter;

public class Bank
{
	// Quantities and denominations found in standard editions of the Monopoly game:
	// 30 $500.00 Bills
	// 30 $100.00 Bills
	// 30 $ 50.00 Bills
	// 30 $ 20.00 Bills
	// 30 $ 10.00 Bills
	// 30 $ 5.00 Bills
	// 30 $ 1.00 Bills

	public static final int STARTING_AMOUNT = 20580;
	private int cash;
	private final ArrayList<Bill> bankBills;

	public Bank(final Player[] players)
	{
		J2DMonopoly.print("Bank -> Setting up banks starting cash.");
		bankBills = startingBankBills();

		cash = STARTING_AMOUNT;

		J2DMonopoly.print("Bank -> Dealing players their starting cash.");

		final ArrayList<Bill> playerBills = startingPlayerBills();
		for (final Player player : players)
		{
			player.setStartingBills(this, playerBills);
		}

	}

	private ArrayList<Bill> startingBankBills()
	{
		final ArrayList<Bill> bills = new ArrayList<Bill>();
		bills.add(new Bill(BillType.ONE, 30));
		bills.add(new Bill(BillType.FIVE, 30));
		bills.add(new Bill(BillType.TEN, 30));
		bills.add(new Bill(BillType.TWENTY, 30));
		bills.add(new Bill(BillType.FIFTY, 30));
		bills.add(new Bill(BillType.HUNDRED, 30));
		bills.add(new Bill(BillType.FIVE_HUNDRED, 30));
		return bills;
	}

	public ArrayList<Bill> startingPlayerBills()
	{
		final ArrayList<Bill> bills = new ArrayList<Bill>();
		bills.add(new Bill(BillType.ONE, 5));
		bills.add(new Bill(BillType.FIVE, 5));
		bills.add(new Bill(BillType.TEN, 5));
		bills.add(new Bill(BillType.TWENTY, 6));
		bills.add(new Bill(BillType.FIFTY, 2));
		bills.add(new Bill(BillType.HUNDRED, 2));
		bills.add(new Bill(BillType.FIVE_HUNDRED, 2));
		return bills;
	}

	public void removeBill(final int type, final int amount)
	{

		for (final Bill bill : bankBills)
		{
			if (bill.getType() == type)
			{
				bill.setAmount(bill.getAmount() - amount);
				// J2DMonopoly.print("Bank -> Removing $" + type * amount + " from bank ($" + type + "x" + amount + ")");
				cash -= type * amount;
			}
		}

	}

	private void printBankBills()
	{
		// J2DMonopoly.print("Bank -> Total cash amount: $" + CashFormatter.format(cash));
		for (final Bill bill : bankBills)
		{
			J2DMonopoly.print("Bank -> Contains a (" + bill.getType() + ") bill (x" + bill.getAmount() + ")");
		}
	}

	private void printCash()
	{
		J2DMonopoly.print("Bank -> Total cash amount: $" + CashFormatter.format(cash));
	}

	public int getCash()
	{
		return cash;
	}

}
