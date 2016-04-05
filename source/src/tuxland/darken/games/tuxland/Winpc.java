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

import java.awt.Point;

/**
 * Winpc - Ennemi de Tux sautant sur place
 * @author Philippe Bousquet (Darken33@free.fr)
 * @version 0.1.0
 */
public class Winpc extends Enemy {    
    
    /**
     * Winpc - Constructeur
     * @param name nom/id de l'ennemi
     * @param x position X
     * @param y position Y
     * @param orientation Orientation
     */
    public Winpc(String name, int x, int y, int orientation)
    {
	  super(name,x,y,orientation);
    }
    
    /**
     * autoMove : Déplacer automatiquement Winpc
     */
    public void autoMove()
    {
      Point p = getMapPosition();
      int direction = getXOrientation();
      jump();
    } 

}

