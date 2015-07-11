//Created by zavaraki & mirzaei

package org.j2dmonopoly.game.board;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.*;
import org.j2dmonopoly.game.J2DMonopoly;
import org.j2dmonopoly.game.player.Dice;
import org.j2dmonopoly.game.player.Player;
import org.j2dmonopoly.game.player.Tokens;
import org.j2dmonopoly.util.CashFormatter;
import org.j2dmonopoly.util.Transparency;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.*;
public class GameBoard extends JPanel
{

    long elapsedHours;
    long elapsedMinutes;
    long elapsedSeconds;
    public long time;
    public long starttime;
    public long dif;
    private static final long serialVersionUID = 1L;
    // IMAGES
    private ImageIcon board; // board image.
    private ImageIcon mainBackground; // background image.
    private ImageIcon mrMonopolyLeft; // monopoly guy left.
    private ImageIcon mrMonopolyRight; // monopoly guy right.
    private BufferedImage logo; // logo in center of board.
    private BufferedImage[] blueDice; // array[] of dice textures.
    public ImageIcon[] money; // array[] of money bills.

    // VARIABLES RELATING TO DICE
    private Color flashingDiceColor = Color.white; // current color of flashing text to roll.
    private long flashingDiceTextStart = -1; // start time for flashing text.
    private final long flashingDiceTextDelay = 500; // interval at with flashing text's updated.
    private int leftDiceFrame, rightDiceFrame; // the current dice frame. (left/right)
    private Dice leftDice, rightDice; // left/right dice.
    private final long diceTextureChangeDelay = 200; // delay between texture changes.
    private long diceTextureChangeStart = -1; // start time for dice texture change.
    public long diceRollStart = -1; // start time for a dice roll.
    private final long diceRollDuration = 1400; // dice will roll for this time in MS.

    // OTHER GAME VARIABLES
    private final J2DMonopoly game; // game instance.
    public static boolean loadTokens = true; // load tokens? (dev option, saves loading time)
    private int testDeltaX; // variable for testing movement with delta.
    private double delta; // delta for game loop.

    public GameBoard(final J2DMonopoly game)
    {
        this.game = game;
        starttime=System.nanoTime();
        J2DMonopoly.print("GameBoard -> Loading dice...");
        loadDice();
        // playbkg();
        J2DMonopoly.print("GameBoard -> Loading sprites...");
        loadSprites();
    }

    private void loadDice()
    {
        leftDice = new Dice(0, 5);
        rightDice = new Dice(0, 5);
    }

    public boolean diceRolling = false;
    private int leftDiceNumber; // final roll # for left dice.
    private int rightDiceNumber; // final roll # for right dice.

    public void update()
    {
        // J2DMonopoly.print(0.0001 * delta + "");
        testDeltaX += 0.0001 * delta;
        // TODO Auto-generated method stub
        updateFlashingDiceText();

        updateDiceRolling();

    }

    public void render(final double delta)
    {
        this.delta = delta;
        repaint();
        game.getTOOLKIT().sync();
    }

    @Override
    public void paint(final Graphics g)
    {
        renderBackground(g);
        renderBoard(g);
        renderSidePanels(g);

    }

    private void updateDiceRolling()
    {

        // if the dice are currently requesting to be rolled.
        if (diceRolling)
        {
            // start off our timer for changing dice textures.
            if (diceTextureChangeStart == -1)
            {
                diceTextureChangeStart = System.currentTimeMillis();
            }

            // start off our timer to control the roll time.
            if (diceRollStart == -1)
            {
                diceRollStart = System.currentTimeMillis();
            }

            final boolean rollFinished = System.currentTimeMillis() - diceRollStart >= diceRollDuration;
            // if it is time for our texture to change.
            if (System.currentTimeMillis() - diceTextureChangeStart >= diceTextureChangeDelay && !rollFinished)
            {

                J2DMonopoly.print("GameBoard -> Rolling dice.");
                // roll the dice's to get their texture ID.
                leftDice.roll();
                rightDice.roll();

                // change dice textures ^__^
                leftDiceFrame = leftDice.getNumber();
                rightDiceFrame = rightDice.getNumber();

                // reset our timer for changing dice textures.
                diceTextureChangeStart = System.currentTimeMillis();
            } else if (rollFinished)
            {
                leftDiceNumber = leftDiceFrame + 1;
                rightDiceNumber = rightDiceFrame + 1;
                if(leftDiceNumber>1){
                    movetest(game.getPlayers()[2]);
                    // playbkg();
                }
                diceRolling = false;
                flashingDiceTextStart = System.currentTimeMillis();
                flashingDiceColor = Color.black;
                J2DMonopoly.print("GameBoard -> Finished rolling dice, numbers rolled: " + leftDiceNumber + " & " + rightDiceNumber);
            }
        }
    }

    private void updateFlashingDiceText()
    {

        // only bother to update the flashing text if !diceRolling
        if (!diceRolling)
        {
            // start off our timer to control flashing dice text.
            if (flashingDiceTextStart == -1)
            {
                flashingDiceTextStart = System.currentTimeMillis();
            }

            // 500ms has passed. change to black.
            if (System.currentTimeMillis() - flashingDiceTextStart >= 0
                    && System.currentTimeMillis() - flashingDiceTextStart < flashingDiceTextDelay)
            {
                flashingDiceColor = Color.black;

                // 1000ms has passed. change to white.
            } else if (System.currentTimeMillis() - flashingDiceTextStart >= flashingDiceTextDelay
                    && System.currentTimeMillis() - flashingDiceTextStart < flashingDiceTextDelay * 2)
            {
                flashingDiceColor = Color.white;

                // 1500ms has passed, restart and change to black.
            } else if (System.currentTimeMillis() - flashingDiceTextStart >= flashingDiceTextDelay * 2)
            {
                flashingDiceTextStart = System.currentTimeMillis();
                flashingDiceColor = Color.black;
            }
        }

    }

    private void loadSprites()
    {

        if (loadTokens)
        {
            Tokens.loadTokens();
        } else
        {
            J2DMonopoly.print("GameBoard -> loadTokens set to false, skipping loading.");
        }
        J2DMonopoly.print("GameBoard -> Loading board sprite...");
        board = new ImageIcon("assets/sprites/board/board.png");

        J2DMonopoly.print("GameBoard -> Loading main background sprite...");
        mainBackground = new ImageIcon("assets/sprites/background/main_background.png");

        J2DMonopoly.print("GameBoard -> Loading mr monopoly left sprite...");
        mrMonopolyLeft = new ImageIcon("assets/sprites/board/mr_monopoly_left.png");

        J2DMonopoly.print("GameBoard -> Loading mr monopoly right sprite...");
        mrMonopolyRight = new ImageIcon("assets/sprites/board/mr_monopoly_right.png");

        J2DMonopoly.print("GameBoard -> Loading money sprites...");
        final String moneyDir = "assets/sprites/board/money/";
        money = new ImageIcon[] { new ImageIcon(moneyDir + "one.png"), new ImageIcon(moneyDir + "five.png"),
                new ImageIcon(moneyDir + "ten.png"), new ImageIcon(moneyDir + "twenty.png"), new ImageIcon(moneyDir + "fifty.png"),
                new ImageIcon(moneyDir + "hunded.png"), new ImageIcon(moneyDir + "five_hundred.png") };
        try
        {
            J2DMonopoly.print("GameBoard -> Loading board logo sprite...");
            logo = Transparency.makeTransparent("assets/sprites/board/logo.png");

            J2DMonopoly.print("GameBoard -> Loading dice sprites...");
            final String diceDir = "assets/sprites/board/dice/";
            blueDice = new BufferedImage[] { Transparency.makeTransparent(diceDir + "blue_01.png"),
                    Transparency.makeTransparent(diceDir + "blue_02.png"), Transparency.makeTransparent(diceDir + "blue_03.png"),
                    Transparency.makeTransparent(diceDir + "blue_04.png"), Transparency.makeTransparent(diceDir + "blue_05.png"),
                    Transparency.makeTransparent(diceDir + "blue_06.png") };
        } catch (final IOException e)
        {
            e.printStackTrace();
        }

    }

    private void renderSidePanels(final Graphics g)
    {
        final int panelWidth = 200; // panel width.
        final int panelHeight = 580; // panel height.
        renderLeftPanel(g, panelWidth, panelHeight);
        //renderRightPanel(g, panelWidth, panelHeight);
    }

    private void renderRightPanel(final Graphics g, final int panelWidth, final int panelHeight)
    {
        // Right side panel.
        final int panelX = 1045;
        final int panelY = 40;
        g.setColor(Color.black);
        g.fillRect(panelX, panelY, panelWidth, panelHeight);
        g.setColor(Color.white);
        g.drawRect(panelX, panelY, panelWidth, panelHeight);
        // g.drawImage(mrMonopolyRight.getImage(), 1100, 350, mrMonopolyRight.getIconWidth() / 6, mrMonopolyRight.getIconHeight() / 6, null);

        // Render player cash on right panel.
        for (final Player p : game.getPlayers())
        {
            // Render money.
            g.setColor(Color.yellow);
            g.setFont(new Font("Serif", Font.ROMAN_BASELINE | Font.BOLD, 20));
            final int moneyX = 1060;
            final int moneyIMGY = 55;
            final int moneyTextY = moneyIMGY + 60;
            final int gapBetween = 20;
            final int billHeight = 62;
            for (int i = 0; i < game.getGameBoard().money.length; i++)
            {
                final ImageIcon bill = game.getGameBoard().money[i];
                g.drawImage(bill.getImage(), moneyX, moneyIMGY + billHeight * i + gapBetween * i, null);
                // String amountText = "$" + bills.get(i).getType()*bills.get(i).getAmount();
                // g.drawString(amountText, moneyX + 135, moneyTextY + (62 * i) + gapBetween * i);
                g.drawString("x" + p.getBills().get(i).getAmount(), moneyX + 135, moneyTextY + billHeight * i + gapBetween * i);
            }
        }
    }

    private void renderLeftPanel(final Graphics g, final int panelWidth, final int panelHeight)
    {

        time=System.nanoTime();
        dif=time-starttime;
        long secondsInMilli = 1000000000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;


        elapsedHours = dif / hoursInMilli;
        dif = dif % hoursInMilli;

        elapsedMinutes = dif/ minutesInMilli;
        dif = dif % minutesInMilli;

        elapsedSeconds = dif / secondsInMilli;
        // RENDER LEFT SIDE PANEL
        final int panelX = 25;
        final int panelY = 40;
        g.setColor(Color.black);
        g.fillRect(panelX, panelY, panelWidth, panelHeight);
        g.setColor(Color.white);
        g.drawRect(panelX, panelY, panelWidth, panelHeight);

        // RENDER MR MONOPOLY
        g.drawImage(mrMonopolyLeft.getImage(), 70, 440, mrMonopolyLeft.getIconWidth() / 6, mrMonopolyLeft.getIconHeight() / 6, null);

        // RENDER FLASHING DICE TEXT
        g.setFont(new Font("Serif", Font.ROMAN_BASELINE | Font.BOLD, 16));

        if (!diceRolling)
        {
            g.setColor(Color.yellow);
            g.drawString("Players turn: TEST", 40, 400);
            g.setColor(flashingDiceColor);
            g.drawString("-SPACEBAR TO ROLL-", 40, 420);
        } else
        {
            g.setColor(Color.yellow);
            g.drawString("TEST is rolling the dice!", 40, 400);
            g.setColor(Color.gray);
            g.drawString("-ROLL IN PROGRESS-", 40, 420);
        }
        // RENDER DETAILS ABOUT GAME (BANK/POT/# PLAYERS)
        final int hudX = 35;
        g.setColor(Color.green.darker());
        g.setFont(new Font("Serif", Font.ROMAN_BASELINE, 20));
        g.drawString("Total Players: " + game.getPlayers().length, hudX, 60);
        g.drawString("Bank: $" + CashFormatter.format(game.getBank().getCash()), hudX, 80);
        String tim;
        tim=String.format("%02d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
        g.drawString(tim, hudX, 100);

        // RENDER PLAYER CASH AMOUNTS
        for (int i = 0; i < game.getPlayers().length; i++)
        {
            final int textY = 160 + 20 * i;
            g.drawString(
                    game.getPlayers()[i].getName() + ": $" + CashFormatter.format(game.getPlayers()[i].getCash()) + " - "
                            + game.getPlayers()[i].getTokenName(), hudX, textY);
        }

        // RENDER DICE
        g.drawImage(blueDice[leftDiceFrame], 40, 300, null);
        g.drawImage(blueDice[rightDiceFrame], 140, 300, null);
    }

    private void renderBoard(final Graphics g)
    {
        // g.setColor(Color.red);
        // g.fillRect(0, 0, 1200, 768);
        // Render board.
        g.drawImage(board.getImage(), 245, 15, board.getIconWidth() - 20, board.getIconHeight() - 165, null);

        // Render logo.
        g.drawImage(logo, 460*2+300, 235*2+100, logo.getWidth(null) / 3, logo.getHeight(null) / 3, null);

        // Render player tokens.
        final int width = 30;
        final int height = 30;
        for (final Player p : game.getPlayers())
        {
            g.setColor(Color.black);
            g.fillRect(p.getX(), p.getY(), width, height);
            g.setColor(Color.yellow);
            g.drawRect(p.getX(), p.getY(), width, height);
            g.drawImage(p.getToken(), p.getX(), p.getY(),p.getheight(), p.getheight(), null);

        }

    }

    private void renderBackground(final Graphics g)
    {
        // Render background.
        g.setColor(Color.red);
        g.fillRect(0, 0, super.getWidth(), super.getHeight());
        g.drawImage(mainBackground.getImage(), 0, 0, super.getWidth(), super.getHeight(), null);

    }
    public void movetest(Player player){
        //player.setX(player.getX()+100);
       new mover(player);
        play();
    }
    public static void play(){
        AudioPlayer MGP=AudioPlayer.player;


        AudioStream loop=null;
        try {


            loop=new AudioStream(new FileInputStream("bo.wav"));
        }catch(IOException error){

        }
        MGP.start(loop);

    }
    public static void playbkg(){
        AudioPlayer MGP=AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;
        ContinuousAudioDataStream loop=null;
        try {
            BGM = new AudioStream(new FileInputStream("s.wav"));
            MD=BGM.getData();
            loop=new ContinuousAudioDataStream(MD);
        }catch(IOException error){

        }
        MGP.start(loop);

    }

    }





