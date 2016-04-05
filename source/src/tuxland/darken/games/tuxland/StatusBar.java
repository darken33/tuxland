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
import java.awt.Image;
import java.awt.Panel;
import java.io.File;
import java.text.NumberFormat;

import darken.games.event.EndGameEvent;
import darken.games.utils.Debug;
import darken.games.utils.DebugNames;
import darken.games.utils.ImageDisplayer;
import darken.games.utils.Params;
import darken.games.utils.SoundNames;
import darken.games.utils.StatusNames;

/**
 * StatusBar - Gestion de la barre de status 
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class StatusBar extends Panel implements Runnable,StatusNames
{
    private Thread thread = null;
    private Image offImage = null;
    private Graphics offGraphics = null; 
    private int width = 0;
    private int height = 0;
    private ImageDisplayer imageDisplayer = null;
    private NumberFormat nf = null;

    private int cdromCollected = 0;
    private int cdromTotal = 0;
    private int cdromPourcent = 0;
    private int imgCD = 0;
    private int livesLid = 0;
    private int timer = 0;
    private String timerStr = null;
    private int digit1,digit2,digit3 = 0;
    private boolean bombAnim = false;
    private int bombFrame = 0;
    private Tuxland parent = null;
    private boolean active = false;

    /**
     * StatusBar - Constructeur
     * @param parent Fenettre principale du jeu
     */
    public StatusBar(Tuxland parent)
    {
	  this.parent = parent;
	  setActive(false);
	  thread = new Thread(this);
	  imageDisplayer = new ImageDisplayer();
	  String dir = Params.IMG_DIR+File.separator+Params.STATUS_DIR+File.separator;
	  imageDisplayer.addLineOfImage(dir+BG_IMG+Params.GIF,BG_NB,0,BG_DELAY,BG_ID);
	  imageDisplayer.addLineOfImage(dir+LIVES_IMG+Params.GIF,LIVES_NB,0,LIVES_DELAY,LIVES_ID);
	  imageDisplayer.addLineOfImage(dir+CDROM_IMG+Params.GIF,CDROM_NB,0,CDROM_DELAY,CDROM_ID);
	  imageDisplayer.addLineOfImage(dir+TIME_IMG+Params.GIF,TIME_NB,0,TIME_DELAY,TIME_ID);
	  imageDisplayer.addLineOfImage(dir+BOMB_IMG+Params.GIF,BOMB_NB,0,BOMB_DELAY,BOMB_ID);
	  imageDisplayer.addLineOfImage(dir+DIGIT_IMG+Params.GIF,DIGIT_NB,0,DIGIT_DELAY,DIGIT_ID);
	  nf = NumberFormat.getInstance();
	  nf.setMinimumIntegerDigits(3);
    }

    public void start()
    {
	  thread.start();
    }

    public void setActive(boolean a)
    {
	  active = a;
    }

    public boolean isActive()
    {
	  return active;
    }

    public void setCDRomCollected(int i)
    {
	  cdromCollected = i;
    }

    public int getCDRomCollected()
    {
	  return cdromCollected;
    }
    
    public void setCDRomTotal(int i)
    {
      cdromTotal = i;
    }
    public void setCDRomPercent(int i)
    {
      cdromPourcent = i;
      imgCD=0;
    }

    public int getCDRomTotal(){
	  return cdromTotal;
    }

    public void increaseCDRomCollected() throws EndGameEvent
	{
	  cdromCollected++;
	  if(cdromCollected==cdromTotal) throw new EndGameEvent(true);
    }
    
    public void setLivesLid(int i)
    {
	  livesLid = i;
    }

    public int getLivessLid()
    {
	  return livesLid;
    }
    
    public void setTimer(int i)
    {
	  bombFrame = 0;
	  bombAnim = false;
	  timer = i;
    }

    public int getPercent()
    {
    	cdromPourcent = (Math.round(cdromCollected*10/cdromTotal)*10);
    	return cdromPourcent;
    }
    public boolean getEndAnimCD()
    {
    	return (imgCD==10);
    }
    
    public int getTimer()
    {
	  return timer;
    }

    /**
     * run - Boucle dexecution du thread
     */
    public void run()
    {
	  /// pour l'affichage
	  width = getWidth();
	  height = getHeight();
	  while(offImage==null) 
	  {
	    offImage = createImage(width,height);
	    Debug.print(DebugNames.NORMAL_PRIORITY,".");
	    try
		{ 
		  Thread.sleep(200); 
	    }
	    catch (InterruptedException e)
		{
		  e.printStackTrace();
	   	  break;
	    } 
	  }
  	  offGraphics = offImage.getGraphics();
	  // fin pour l'affichage
	  long startTime = System.currentTimeMillis();
	  // Boucle infinie
	  while(true)
	  {
	  	// Pause si la barre est inactive
	    if(!isActive()) 
	    {
		  try{ 
		    display();
		  	Thread.sleep(1000); 
		  }
		  catch ( InterruptedException e) { e.printStackTrace();break; } 
		  continue;
	    }
	    // Si le chrono est en marche on le met à jour
	    long now = System.currentTimeMillis();
	    if (getPercent()!=100)
	    {
	      if ((!bombAnim) && ((now-startTime) > 1000) ) 
	      {
		    timer--; 
		    if(timer<1 && !bombAnim) 
		    {
		  	  bombAnim=true;
		      parent.getJukeBox().play(SoundNames.SND_TIMEOUT_ID);					
		    }
		    startTime = now; 
	      }
	      // Le timer est à zero, la bombe se declenche (il reste 5 sec)
	      if (bombAnim)
	      {
		    if((++bombFrame) >= BOMB_NB ) 
		    {
	  		  parent.getHero().decreaseContinue();
			  parent.stopPlaying();
			  parent.freeGameZone();
		    } 
	      }
	    }  
	    // Afficher la barre de status
	    display();
	    try { Thread.sleep(150); }
	    catch (InterruptedException e){ e.printStackTrace(); } 
	  }
    }

    /**
     * display - Affichage de la barre de status
     */
    public void display()
    {
	  offGraphics = offImage.getGraphics(); 
	  myPaint(offGraphics);
	  Graphics g = getGraphics();
	  g.drawImage(offImage,0,0, this);
    }

    /**
     * myPaint - dessiner la barre de status
     * @param g Graphics sur lequel on doit dessiner
     */
    public void myPaint(Graphics g)
    {
      // Afficher le fond
	  imageDisplayer.displayImageNum(g, BG_X, BG_Y, BG_ID, 0);
	  // Afficher le nombre de vies
	  if(livesLid>0 && livesLid<4) imageDisplayer.displayImageNum(g, LIVES_X, LIVES_Y, LIVES_ID, livesLid-1);
	  // Aficher le pourcentage
	  if (imgCD*10<getPercent()) imgCD++;
	  imageDisplayer.displayImageNum(g, CD_COLLECTED_X, CD_COLLECTED_Y, CDROM_ID, imgCD);
	  //g.setColor(Color.WHITE);
	  //g.drawString(new Integer(cdromPourcent).toString(),CD_COLLECTED_X, CD_COLLECTED_Y);
	  // Afficher le nombre de CD collectés
	  //imageDisplayer.displayImageNum(g, CD_COLLECTED_X, CD_COLLECTED_Y, CDROM_ID, cdromCollected);
	  // Afficher le nombre de CD total
	  //imageDisplayer.displayImageNum(g, CD_TOTAL_X, CD_TOTAL_Y, CDROM_ID, cdromTotal);
	  // Afficher l'animation de la bombe
	  if(bombAnim)
	  {
	    imageDisplayer.displayNextImage(g, BOMB_X, BOMB_Y, BOMB_ID);
	  }
	  // Affiche le chrono
	  else
	  {
	    timerStr = nf.format(timer);
	    if(timer>=100) 
		digit1 = Integer.parseInt(timerStr.substring(0,1));
	    else digit1 = 0;
	    if(timer>=10) 
		digit2 = Integer.parseInt(timerStr.substring(1,2));
	    else digit2 = 0;
	    if(timer>=1) 
		digit3 = Integer.parseInt(timerStr.substring(2,3));
	    else digit3 = 0;
	    imageDisplayer.displayImageNum(g, TIME_X, TIME_Y, TIME_ID, 0);
	    imageDisplayer.displayImageNum(g, DIGIT1_X, DIGIT1_Y, DIGIT_ID, digit1);
	    imageDisplayer.displayImageNum(g, DIGIT2_X, DIGIT2_Y, DIGIT_ID, digit2);
	    imageDisplayer.displayImageNum(g, DIGIT3_X, DIGIT3_Y, DIGIT_ID, digit3);
	  }
    }

    public void setText(String s)
    {
	  Graphics g = getGraphics();
	  g.clearRect(0, 0, getWidth(), getHeight());
	  g.drawString(s,getWidth()/2,getHeight());
    }
}
