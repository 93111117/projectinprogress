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
public class upmover implements ActionListener {
    int t=880;
    Timer taim;
    Player player;
    int initial=0;
    public void actionPerformed(ActionEvent e){
        if(t==880) play();
        if(t>0) player.setY(player.getY() + 3);
        if(t>440) player.setheight(player.getheight()+1);
        if(t<440 && t>0) player.setheight(player.getheight()-1);
        t=t-20;
      //  if(t==0) player.setY(initial + 132);
    }
    public upmover(Player p, gmover g){
        this.player=p;
       // p.setposition((p.getposition()+1) % 36);
        this.initial=player.getY();
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
