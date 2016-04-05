/*  
 *  Tuxland - Jeu de plateforme en Java (tm)
 *  Copright (c) 2005 Philippe Bousquet (Darken33@free.fr)
 * 
 *  ---------------------------------------------
 *  IMPORTANT : 
 *    Ce jeu est basé sur le jeu Zlob 
 *    Copyright (C) 2001 Pascal "Toweld" Bourut (toweld@planetelibre.org)
 *  ---------------------------------------------
 * 
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package darken.games.tuxland;

import java.awt.Graphics;
import java.awt.Point;

import darken.games.utils.MkLvlConst;
import darken.games.utils.ScreenNames;
import darken.games.utils.SoundNames;

/**
 * Hero - Gestion du joueur
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class Hero extends Sprite 
{
    private Tuxland mainFrame = null;
    private int life = 4;
    private int score = 0;
    private boolean safe = false;
    private int time = 5;
    private int chrono = 65535;
    private boolean dodisplay = true;
    private int safeX =0;
    private int safeY =0;
    private int safeO =0;
    boolean reposition = false;
    boolean flag4die = false;
    
    /**
     * Hero - Constructeur
     */
    public Hero()
    {
	  super();
	  setAnimationImage("tux");
    }
    
    /**
     * Hero - Constructeur
     * @param name Nom du hero (tux)
     * @param x position X de départ
     * @param y position Y de départ
     * @param orientation Orentation de départ
     */
    public Hero(String name, int x, int y, int orientation)
    {
	  super(name,x,y,orientation);
	  setAnimationImage("tux");
    }
    
    /**
     * setXOrientation - Changer d'orientation
     * @param o Orientation
     */
    public void setXOrientation(int o)
    {
      xOrientation = o;
    }

    /**
     * setScore - Affecter un Score
     * @param s Score
     */
    public void setScore(int s)
    {
	  score = s;
    }

    /**
     * getScore - Recupérer le score
     * @return int Score
     */
    public int getScore()
    {
	  return score;
    }

    /**
     * increaseScore - Augmenter le score
     * @param addon Nombre de points à ajouter
     */
    public void increaseScore(int addon)
    {
	  score += addon;
    }

    /**
     * setContinue - Affecter un nombre de vie
     * @param i Nombre de vie
     */
    public void setContinue(int i)
    {
	  life = i;
    }

    /**
     * getContinue - Récuperer le nombre de vies 
     * @return int nombre de vies
     */
    public int getContinue()
    {
	  return life;
    }

    /**
     * decreaseContinue - Enlever une vie
     */
    public void decreaseContinue()
    {
	  life--;
    }

    /**
     * setMainFrame - permet de lier la fenetre principale
     * @param mainFrame fenetre principale
     */
    public void setMainFrame(Tuxland mainFrame)
    {
	  this.mainFrame = mainFrame;
    }
    
    /**
     * setFlags - permet de postionner les drapeaux perte d'une vie et reposition
     * @param die true=le hero perd une vie, false=le heros ne perd pas de vie
     * @param reposition true=on doit repositioner le hero, false=on ne repositionne pas
     */
    public void setFlags(boolean die, boolean reposition)
    {
      this.flag4die = die;
      this.reposition = reposition;
    }
    
    /**
     * getFlag4Die - Le heros doit il perdre une vie ?
     * @return boolean true=oui, false=non
     */
    public boolean getFlag4Die()
    {
      return flag4die;	
    }
    
    /**
     * die - Le heros perd une vie
     */
    public void die()
    {
      // On doit être en train de jouer
	  if (mainFrame.getCurrentScreen()==ScreenNames.PLAYING)
	  { 
		flag4die = false;
		// On joue un son
		mainFrame.getJukeBox().play(SoundNames.SND_DIE_ID);
		// On rend le hero invicible pendant un temps
		safe=true;
		chrono=mainFrame.getStatusBar().getTimer();
		dodisplay = false;
		// On revient dans une position sure
		if (reposition)
		{
 		  reposition=false;
		  reset();
		  setX(safeX);
		  setY(safeY);
		  setXOrientation(safeO);
		}
		// Soit on enlève une vie
		if (getContinue()>0) decreaseContinue();
		// Soit on arrête la parti
		if (getContinue()<=0) mainFrame.stopPlaying();
	  }
    }

    /**
     * intiSafe - On initialise l'invincibilité du Hero
     */
    public void initSafe()
    {
        safe = false;
        time = 5;
        chrono = 65535;
        dodisplay = true;
        safeX =0;
        safeY =0;
        safeO =0;
    }
    
    /**
     * doDisplay - Doit on afficher le hero (lorsqu'il est invincible, celui ci clignote)
     * @return boolean true=oui, false=non
     */
    public boolean doDisplay()
	{
    	boolean r=true;
    	if (chrono-mainFrame.getStatusBar().getTimer()>=time && safe) safe=false;
    	if (safe)
    	{
    	  r=dodisplay;
    	  dodisplay=!dodisplay;
    	}
    	return r;
	}
    
    /**
     * 
     * @return boolean true=oui, false=non
     */
    public boolean isSafe()
    {
    	return safe;
    }

    /**
     * isSolid - Verifie si une case de la matrice est de type SOLID
     * @param a position X
     * @param b position Y
     * @return boolean true=oui, false=non
     */
    protected boolean isSolid(int a, int b) 
    {
      if (isActive() && mainFrame.getScreen()==ScreenNames.PLAYING) 	
	  try
	  {
		if ( levelMatrix[a][b] == MkLvlConst.SOLID ) return true;
	    else return false;
	  }
	  catch(ArrayIndexOutOfBoundsException e)
	  {
	  	// Si tux sort de l'ecran par le bas
	  	if (b>levelMatrix[0].length+1)
	  	{
	  	  // Il perd une vie
		  setActive(false);
		  setFlags(true,true);
	  	}
	  }
      return false;
    }

    /**
     * isPlatform - Verifie si une case de la matrice est de type PLATFORM
     * @param a position X
     * @param b position Y
     * @return boolean true=oui, false=non
     */
    protected boolean isPlatform(int a, int b) 
    {
      if (isActive() && mainFrame.getScreen()==ScreenNames.PLAYING) 	
	  try
	  {
	    if ( levelMatrix[a][b] == MkLvlConst.PLATFORM ) return true;
	    else return false;
	  }
	  catch(ArrayIndexOutOfBoundsException e)
	  {
	  	// Si tux sort de l'ecran par le bas
	  	if (b>levelMatrix[0].length+1)
	  	{
	  	  // Il perd une vie
		  setActive(false);
		  setFlags(true,true);
	  	}
	  }
      return false;
    }
        
    /**
     * diplay - Affiche le hero à l'ecran
     * @param g Graphics sur lequel il faut dessiner
     */
    public void display(Graphics g)
    {
    	Point p=getMapPosition();
    	if (isSolid(p.x,p.y+1) || isPlatform(p.x,p.y+1)) 
    	{
    		// on sauvegarde une position sure
    		safeX = p.x;
    		safeY = p.y;
    		safeO = getXOrientation();    		
    	}
    	// Tux doit il perdre une vie ?
    	if (getFlag4Die()) die();
    	// Afficher Tux
    	if (doDisplay()) imageDisplayer.displayNextImage(g,getXPixel(),getYPixel(),getAction());
    }    
}