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

import java.util.*;
import java.awt.*;

import darken.games.event.EndGameEvent;
import darken.games.utils.*;

/**
 * ItemGroup - Gestion des items 
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class ItemGroup extends Hashtable implements ItemNames
{
    /** Niveau actuel du jeu */
    private Level level = null;
    /** Banque d'images pour les CDROM */
    protected ImageDisplayer cdromDisplayer = null;
    /** Banque d'images pour les Bonus VIE */
    protected ImageDisplayer heartDisplayer = null;
    /** Banque d'images pour les Bonus POINTS */
    protected ImageDisplayer fishDisplayer = null;
    /** La barre de status */
    private StatusBar statusBar = null;
    
    /**
     * ItemGroup - Constructeur
     */
    public ItemGroup()
    {
	  super();
	  cdromDisplayer = new ImageDisplayer();
	  heartDisplayer = new ImageDisplayer();
	  fishDisplayer = new ImageDisplayer();
    }

    /**
     * ItemGroup - Constructeur
     * @param l Level en cours
     */
    public ItemGroup(Level l)
    {
	  super();
	  level = l;
	  cdromDisplayer = new ImageDisplayer();
	  heartDisplayer = new ImageDisplayer();
	  fishDisplayer = new ImageDisplayer();
    }

    /**
     * reset - RAZ
     */
    public void reset()
    {
	  clear();
    }

    /**
     * setLevel - permet de lier le niveau
     * @param level Niveau actuel
     */
    public void setLevel(Level level)
    {
	  this.level = level;
    }

    /**
     * setStatusBar - permet de lier la barre de status
     * @param statusBar Barre de status
     */
    public void setStatusBar(StatusBar statusBar)
    {
	  this.statusBar = statusBar;
    }

    /**
     * fill - permet d'initialiser la gestion des items
     * @return boolean true=OK, false=KO
     */
    public boolean fill()
    {
	  Item item = null;
	  int cdromCount = 0;
	  // On ajoute les images dans les différentes banque d'images
	  cdromDisplayer.addLineOfImage(ITEM_DIR+CDROM_IMG+GIF,CDROM_NB,0,CDROM_DELAY,0);
	  heartDisplayer.addLineOfImage(ITEM_DIR+HEART_IMG+GIF,HEART_NB,0,HEART_DELAY,0);
	  fishDisplayer.addLineOfImage(ITEM_DIR+FISH_IMG+GIF,FISH_NB,0,FISH_DELAY,0);
	  // On initialise à partir des infos du Level
	  for(Enumeration e = level.getItemList().elements(); e.hasMoreElements();)
	  {
	    item = (Item)e.nextElement();
	    // On incremente le nombre de CD à collecter
	    if(item.getType()==CDROM_ID) cdromCount++;
	    // On ajoute l'item
	    if(addItem(item)==false) return false;
	  }
	  // On met à jour la barre de status
	  statusBar.setCDRomCollected(0);
	  statusBar.setCDRomPercent(0);
	  statusBar.setCDRomTotal(cdromCount);
	  return true;
    }

    /**
     * addItem - Ajouter un item dans le gestionaire
     * @param item Item à ajouter
     * @return boolean true=l'item a été ajouté, false=l'item n'a pas été ajouté
     */
    public boolean addItem(Item item)
    {
	  if(containsKey(item.getName())) return false;
	  put(item.getName(), item);
	  return true;
    }
    
    /**
     * getItem - Permet de récupérer un item du gestionnaire
     * @param id identifiant de l'item (il s'agit de son nom)
     * @return Item l'item que l'on veut manipuler
     */
    public Item getItem(String id)
    {
	  if(containsKey(id)) return (Item)get(id);
	  else return null;
    }
    
    /**
     * removeItem - Permet d'enlever un item du gestionnaire (lorque tux l'a ramassé)
     * @param id identifiant de l'item (il s'agit de son nom)
     * @return boolean true=OK, false=KO
     */
    public boolean removeItem(String id)
    {
	  if(!containsKey(id)) return false;
	  if(remove(id)==null) return false;
	  return true;
    }

    /**
     * display - Gestion et affichage des items
     * @param g Graphics sur lequel il faut dessiner
     * @param screen Position et Taille de l'ecran
     * @param hero Informations sur le hero
     * @param jukebox Jukebox pour pouvoir jouer les sons
     * @throws EndGameEvent Evennement de fin de jeu lorsque tous les CD ont étés récoltés
     */
    public void display(Graphics g,Rectangle screen,Hero hero, SoundPlayer jukebox) throws EndGameEvent
	{
  	  Item item = null;
	  boolean dodisplay=true;
	  // Pour chaque ITM
	  for(Enumeration e = elements(); e.hasMoreElements();)
	  {
	    item = (Item)e.nextElement();
	    Rectangle r = item.getBounds();
	    // S'il est dans l'écran
	    if(screen.intersects(r))
	    {
	      dodisplay=true; 	
	      // S'il est ramassé par le heros
		  if(r.intersects(hero.getBounds()))
		  {
		  	// S'il s'agit d'un CD
			if (item.getType()==CDROM_ID)
			{
			  // On incremente le nombre de CD récoltés
  	          jukebox.play(SoundNames.SND_CDROM_ID);
		      statusBar.increaseCDRomCollected();
		      hero.increaseScore(Params.CDROM_SCORE);
  	          removeItem(item.getName());
  	          // On ne l'affichera pas
  		      dodisplay=false; 	
			}
			// S'il s'ajgit d'un poisson
			else if (item.getType()==FISH_ID)
			{
			  // On augmente le score du hero
	  	      jukebox.play(SoundNames.SND_ITEM_ID);
		      hero.increaseScore(Params.FISH_SCORE);
  	          removeItem(item.getName());
  	          // On ne l'affichera pas
  		      dodisplay=false; 	
			}
			// S'il s'ajgit d'un coeur
			else if (item.getType()==HEART_ID) 
			{
			  // Et que le hero n'a pas son plein de vie	
			  if (hero.getContinue()<4)
			  {
			    // On ajoute une vie  
   	            jukebox.play(SoundNames.SND_ITEM_ID);
			  	hero.setContinue(hero.getContinue()+1);
				statusBar.setLivesLid(4-hero.getContinue());
	  	        removeItem(item.getName());
	  	          // On ne l'affichera pas
    		    dodisplay=false; 	
			  }
		    }
		  }
		  // Si on doit afficher l'item
		  if (dodisplay)
		  {
		    switch (item.getType())
		    {
		      case CDROM_ID : cdromDisplayer.displayNextImage(g,item.getXPixel(),item.getYPixel(),0);
		                      break;
		      case HEART_ID : heartDisplayer.displayNextImage(g,item.getXPixel(),item.getYPixel(),0);
                              break;
		      case FISH_ID : fishDisplayer.displayNextImage(g,item.getXPixel(),item.getYPixel(),0);
                              break;
		    }
		  }  
  		  Debug.print(DebugNames.LOW_PRIORITY,".");
	    }
	  }
	  Debug.println(DebugNames.LOW_PRIORITY,"");
    }
}








