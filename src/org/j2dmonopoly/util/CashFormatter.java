package org.j2dmonopoly.util;

import java.text.DecimalFormat;

public class CashFormatter
{
	private static DecimalFormat formatter = new DecimalFormat("##,###");

	public static String format(final int startingAmount)
	{
		return formatter.format(startingAmount);
	}
}
