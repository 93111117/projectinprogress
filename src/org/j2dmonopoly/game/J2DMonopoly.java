package org.j2dmonopoly.game;

import java.awt.Toolkit;

import org.j2dmonopoly.game.bank.Bank;
import org.j2dmonopoly.game.board.GameBoard;
import org.j2dmonopoly.game.player.Player;
import org.j2dmonopoly.game.player.Tokens;
import org.j2dmonopoly.gui.GameWindow;

public class J2DMonopoly
{

	private static final String GAME_WINDOW_TITLE = "Java2D Monopoly V0.0.0.1";
	private final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
	private Player[] players;
	private final Bank bank;
	private final GameBoard gameBoard;
	private final GameWindow gameWindow;
	private final Thread gameThread;
	protected boolean gameThreadRunning;
	public int framesPerSecond;
	public int updatesPerSecond;

	public J2DMonopoly(final boolean useDefaults)
	{
		print("Setting up a new monopoly game...");

		print("Setting up gameBoard...");
		gameBoard = new GameBoard(this);

		print("Setting up players...");
		if (useDefaults)
		{

			players = new Player[] { new Player(this, "Jimmy", Tokens.DOG), new Player(this, "John", Tokens.BATTLE_SHIP),
					new Player(this, "Joe", Tokens.BOOT), new Player(this, "Josh", Tokens.IRON), new Player(this, "Jean", Tokens.THIMBLE),
					new Player(this, "Julia", Tokens.TOP_HAT) };

			print("Setting player 0 to controller...");
			players[0].setController(true);

			print("Setting players position on board...");
			// Note current positions are not correct due to
			// implementing a new game board sprite.
			for (int i = 0; i < players.length; i++)
			{
				if (i < 3)
				{
					players[i].setX(300 + 35 * i);
					players[i].setY(1300);
				} else if (i > 2)
				{
					players[i].setX(195 + 35 * i);
					players[i].setY(1245);
				}
			}
		}

		print("Setting up bank system...");
		bank = new Bank(players);

		print("Setting up game window...");
		gameWindow = new GameWindow(this, GAME_WINDOW_TITLE + " (FPS: 0 | UPS: 0)", 1280*2, 700*2);

		print("Setting up game thread...");
		gameThread = new Thread("J2DMonopoly")
		{
			@Override
			public void run()
			{
				J2DMonopoly.this.run(60, 60, 1);
			}
		};

	}

	public void run(final int desiredUps, final int desiredFps, final int frameSkip)
	{
		long now = System.nanoTime();
		long logicTime = System.nanoTime();
		long renderTime = System.nanoTime();

		long fpsStart = System.currentTimeMillis();
		long upsStart = System.currentTimeMillis();
		final long frameLogicTime = 1000000000 / desiredUps;
		final long frameRenderTime = 1000000000 / desiredFps;

		int skippedFrames = 0;
		int frames = 0;
		int ups = 0;

		while (gameThreadRunning)
		{

			now = System.nanoTime();
			skippedFrames = 0;

			while (logicTime + frameLogicTime < now && skippedFrames < frameSkip)
			{
				update();
				// UPS counter implementation.
				ups++;
				if (System.currentTimeMillis() - upsStart >= 1000)
				{
					updatesPerSecond = ups;
					ups = 0;
					upsStart = System.currentTimeMillis();
				}
				skippedFrames++;
				logicTime += frameLogicTime;

			}

			while (renderTime + frameRenderTime < now)
			{

				render(frameLogicTime / 1000);
				// FPS counter implementation.
				frames++;
				if (System.currentTimeMillis() - fpsStart >= 1000)
				{
					framesPerSecond = frames;
					frames = 0;
					fpsStart = System.currentTimeMillis();
				}
				renderTime += frameRenderTime;

			}
		}
	}

	protected void render(final double delta)
	{
		gameBoard.render(delta);

	}

	protected void update()
	{
		gameWindow.setTitle(GAME_WINDOW_TITLE + " (FPS: " + framesPerSecond + " | UPS: " + updatesPerSecond + ")");
		gameBoard.update();
		for (final Player p : players)
		{
			p.update();
		}
	}

	public void start()
	{
		print("Starting game thread...");
		gameThreadRunning = true;
		gameThread.start();
	}

	public void keyPressed(final int keyCode)
	{
		for (final Player p : players)
		{
			if (p.isController())
			{
				p.keyPressed(keyCode);
			}
		}
	}

	public void keyReleased(final int keyCode)
	{
		for (final Player p : players)
		{
			if (p.isController())
			{
				p.keyReleased(keyCode);
			}
		}
	}

	public void keyTyped(final int keyCode)
	{
		for (final Player p : players)
		{
			if (p.isController())
			{
				p.keyTyped(keyCode);
			}
		}
	}

	public Player[] getPlayers()
	{
		return players;
	}

	public Bank getBank()
	{
		return bank;
	}

	public GameBoard getGameBoard()
	{
		return gameBoard;
	}

	public Thread getGameThread()
	{
		return gameThread;
	}

	public GameWindow getGameWindow()
	{
		return gameWindow;
	}

	public Toolkit getTOOLKIT()
	{
		return TOOLKIT;
	}

	public int getFramesPerSecond()
	{
		return framesPerSecond;
	}

	public int getUpdatesPerSecond()
	{
		return updatesPerSecond;
	}

	public static String getGameWindowTitle()
	{
		return GAME_WINDOW_TITLE;
	}

	public static void print(final String string)
	{
		System.out.println("J2DMonopoly -> " + string);
	}

}
