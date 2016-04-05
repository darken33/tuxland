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

import darken.games.utils.MkLvlConst;
import darken.games.utils.Monsters;
import darken.games.utils.Params;

/**
 * Enemy - Gestion des ennemis
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class Enemy extends Sprite implements Monsters
{
    private boolean active = false;
    private String race = null;

    /**
     * Enemy - Constructeur
     */
    public Enemy()
    {
	  super();
    }
    
    /**
     * Enemy - Constructeur
     * @param name nom de l'ennemy (type_n) 
     * @param x position X de départ
     * @param y position Y de départ
     * @param orientation Orientation de départ
     */
    public Enemy(String name, int x, int y, int orientation)
    {
	  super(name,x,y,orientation);
	  race = name.substring(0,name.lastIndexOf("_"));
	  setOldBounds(getBounds());
	  setAnimationImage(race);
    }

    /**
     * toString - Genère une représentation d'un enemi sous forme de chaine
     * @return String l'enemi sous forme de chaine
     */
    public String toString()
    {
 	  return "["+getName()+","+getXPixel()+","+getYPixel()+","+getOrientation()+","+getRace()+"]";
    }
     
    /**
     * setActive - Activer/Désactiver un ennemi
     * @param b true=Actif, flase=Inactif
     */
    public void setActive(boolean b)
    {
	  active = b;
    }
    
    /**
     * isActive - Verifier si un ennemi est actif
     * @return boolean true=Actif, flase=Inactif
     */
    public boolean isActive()
    {
	  return active;
    }

    /**
     * getRace - Recupérer la race de l'ennemi
     * @return String la race de l'ennemi (Cf Monsters)
     */
    public String getRace()
    {
	  return race;
    }
    
    /**
     * setRace - Positionner la race de l'ennemi
     * @param r race de l'ennemy (Cf Monsters)
     */
    public void setRace(String r)
    {
	  race=r;
    }

    /**
     * run - Execution du thread
     */
    public void run()
    {
      // Boucle infinie
	  while(true)
	  {
	    try
		{ 
	      // Si la matrice du niveau existe et que l'ennemi est actif	
		  if(levelMatrix!=null && active && !isPaused())
		  {
		  	// Mettre à jour ses déplacements
		    updatePlacement();  
		    Thread.sleep(20);
		  }
		  else 
		  {
		    Thread.sleep(1000);
		  }
	    }
	    catch(InterruptedException ie)
		{
		  break;    
	    }
	  }
    }
   
    /**
     * autoMove - Deplacement automatique  
     */
    public void autoMove()
    {
	  hold();
    }

    /**
     * walkLeft - Se déplacer vers la Gauche
     */
    public void walkLeft()
    {
    	oxd = Params.LEFT;
    	if ( dX > -Params.ENEMY_WALK_MAX ) dX-=Params.ENEMY_WALK_STEP;
    	else dX = -Params.ENEMY_WALK_MAX;
    }
        
    /**
     * walkRight - Se déplacer vers la Droite
     */
   public void walkRight()
   {
    	oxd = Params.RIGHT;
    	if ( dX < Params.ENEMY_WALK_MAX ) dX+=Params.ENEMY_WALK_STEP;
    	else dX = Params.ENEMY_WALK_MAX;
   }

   /**
    * jumpLeft - Sauter vers la Gauche
    */
   public void jumpLeft()
   {
	  if(jumpAbortedByCollision || jumpAbortedBySideCollision) return;
	  xModif-=Params.ENEMY_WALK_STEP;
	  jump();
   }

   /**
    * jumpRight - Sauter vers la Droite
    */
   public void jumpRight()
   {
	 if(jumpAbortedByCollision || jumpAbortedBySideCollision) return;
	 xModif+=Params.ENEMY_WALK_STEP;
	 jump();
   }
    
   /**
    * isStopMonster - L'element dans la matrice [a,b] bloque t'il les ennemis
    * @return boolean true=oui, false=non
    */
   protected boolean isStopMonster(int a, int b)
   {
     try
	 {
       if ( levelMatrix[a][b] == MkLvlConst.STOPMONSTER ) return true;	    	
       else return false;
     }
     catch(ArrayIndexOutOfBoundsException e)
	 {
       return true;
     }
   }
}







