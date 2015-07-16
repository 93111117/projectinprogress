package org.j2dmonopoly.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;

import javax.swing.JFrame;

import org.j2dmonopoly.game.J2DMonopoly;

public class GameWindow
{

	private final JFrame frame;
	private final J2DMonopoly game;

	public GameWindow(final J2DMonopoly game, final String string, final int i, final int j)
	{
		this.game = game;
		frame = new JFrame(string);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(i, j));
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().setBackground(Color.red);
		frame.getContentPane().add(game.getGameBoard(), BorderLayout.CENTER);
		game.getGameBoard().setFocusable(true);
		game.getGameBoard().addKeyListener(inputHandler);
		frame.pack();

		frame.setLocationRelativeTo(null);
		//frame.setAlwaysOnTop(true);
	}

	public KeyAdapter inputHandler = new KeyAdapter()
	{
		@Override
		public void keyPressed(final java.awt.event.KeyEvent arg0)
		{
			game.keyPressed(arg0.getKeyCode());
			return;
		}

		@Override
		public void keyReleased(final java.awt.event.KeyEvent arg0)
		{
			game.keyReleased(arg0.getKeyCode());
			return;
		}

		@Override
		public void keyTyped(final java.awt.event.KeyEvent arg0)
		{
			game.keyTyped(arg0.getKeyCode());
			return;
		}

	};

	public void show()
	{
		frame.setVisible(true);
	}

	public void hide()
	{
		frame.setVisible(false);
	}

	public void setResizable(final boolean value)
	{
		frame.setResizable(value);
	}

	public void setTitle(final String title)
	{
		frame.setTitle(title);
	}
}
