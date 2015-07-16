package org.j2dmonopoly.game.board;

import org.j2dmonopoly.game.J2DMonopoly;
import org.j2dmonopoly.game.player.Player;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by MAC on 7/10/2015.
 */
public class mover implements ActionListener {
    int t=940;
    Timer taim;
    Player player;
    int initial=0;
    public void actionPerformed(ActionEvent e){
        if(t==940) play();
       if(t>0) player.setX(player.getX()+5);
       if(t>470) player.setheight(player.getheight()+1);
        if(t<470 && t>0) player.setheight(player.getheight()-1);
        t=t-20;
       // if(t==0) player.setX(initial+236);
    }
    public mover(Player p, gmover g){
        this.player=p;
     //   p.setposition((p.getposition()+1)%36);
        this.initial=player.getX();
        taim = new Timer(20, this);
        taim.setInitialDelay(1500*g.movebuf);
        taim.start();
        g.movebuf++;
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
}
