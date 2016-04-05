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

import java.awt.*;

import darken.games.utils.*;

/**
 * Virus - Ennemi de Tux se déplacant de gauche à droite dans les airs
 * @author Philippe Bousquet (Darken33@free.fr)
 * @version 0.1.0
 */
public class Virus extends Enemy 
{
    
    /**
     * Virus - Constructeur
     * @param name nom/id de l'ennemi
     * @param x position X
     * @param y position Y
     * @param orientation Orientation
     */
    public Virus(String name, int x, int y, int orientation)
    {
	  super(name,x,y,orientation);
    } 
    
    /**
     * autoMove : Déplacer automatiquement Virus
     */
    public void autoMove()
    {
	  Point p = getMapPosition();
	  int direction = getXOrientation();    
	  if(isSolid(p.x+2*direction,p.y) || isPlatform(p.x+2*direction,p.y) || isStopMonster(p.x+2*direction,p.y)) direction=-direction; 
	  if(direction == Params.LEFT) walkLeft();
	  else walkRight();
    } 

    /**
     * autoMove : Mettre à jour les coordonées de Virus sans tenir compte de la gravité
     */
    public void updatePlacement()
    {

    	oldXPixel = getXPixel();
    	oldYPixel = getYPixel();
    	
    	int xTmp = getXPixel();
    	int yTmp = getYPixel();
    	
    	xTmp+=dX;
    	yTmp+=dY;
    	
    	testMapCollision(xTmp, yTmp);
    }

    /**
     * walkLeft : deplacer Virus vers la gauche
     */    
    public void walkLeft()
    {
    	oxd = Params.LEFT;
   	    if ( dX > (-Params.WALK_MAX)*2 ) dX-=(Params.WALK_STEP)*2;
    	else dX = (-Params.WALK_MAX)*2;
    }
        
    /**
     * walkRight : deplacer Virus vers la droite
     */    
    public void walkRight()
    {
    	oxd = Params.RIGHT;
        if ( dX < (Params.WALK_MAX)*2 ) dX+=(Params.WALK_STEP)*2;
    	else dX = (Params.WALK_MAX)*2;
    }      
}

