package org.j2dmonopoly.game.board;

import org.j2dmonopoly.game.J2DMonopoly;
import org.j2dmonopoly.game.player.Player;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MAC on 7/10/2015.
 */
public class leftmover implements ActionListener {
    int t=1000;
    Timer taim;
    Player player;
    int initial=0;
    public void actionPerformed(ActionEvent e){
        if(t>0) player.setX(player.getX()-5);
        if(t>500) player.setheight(player.getheight()+1);
        if(t<500 && t>0) player.setheight(player.getheight()-1);
        t=t-20;
        if(t==0) player.setX(initial-236);
    }
    public leftmover(Player p){
        this.player=p;
        this.initial=player.getX();
        taim = new Timer(20, this);
        taim.start();
    }

}
