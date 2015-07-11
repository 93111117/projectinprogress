package org.j2dmonopoly;

import java.util.Properties;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.j2dmonopoly.game.J2DMonopoly;

import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;

public class GameLauncher
{

	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				J2DMonopoly.print("GameLauncher -> Setting look and feel for game window...");
				setLookAndFeel();
				// GameBoard.loadTokens = false;
				// Create the game.
				J2DMonopoly.print("GameLauncher -> Creating game instance...");
				final J2DMonopoly game = new J2DMonopoly(true);
				game.start();
				game.getGameWindow().show();
			};
		}.start();

	}

	private static void setLookAndFeel()
	{
		// setup the look and feel properties
		final Properties props = new Properties();

		props.put("logoString", "");
		props.put("licenseKey", "");

		// set your theme
		GraphiteLookAndFeel.setCurrentTheme(props);
		// select the Look and Feel
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
		} catch (final ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
