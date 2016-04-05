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

import java.awt.Rectangle;

import darken.games.utils.Params;

/**
 * Item - Classe de l'objet ITEM 
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class Item 
{
    /** Nom de l'item */    
    private String name;
    /** Position et taille */
    private int x,y,w,h;
    /** Type d'item (cf ItemNames) */
    private int type;

    /**
     * Item - Constructeur 
     * @param name Nom de l'item (type_n)
     * @param x position X
     * @param y position Y
     * @param w Largeur en px
     * @param h Hauteur en px
     * @param type Type d'item (cf ItemNames)
     */
    public Item(String name, int x, int y, int w, int h, int type)
    {
	  this.name = name;
	  this.x = x;
	  this.y = y;
	  this.w = w;
	  this.h = h;
	  this.type = type;
    }
    
    /**
     * toString - Renvoie une représentation de l'item au format texte
     * @return String représentation de l'item
     */
    public String toString()
    {
 	  return "["+getName()+","+getX()+","+getY()+","+getWidth()+","+getHeight()+","+getType()+"]";
    }
    
    public void setName(String name){this.name = name;}
  
    public String getName(){return name;}
    
    public void setX(int x){this.x = x;}
    
    public int getX(){return x;}
    
    public void setY(int y){this.y = y;}
    
    public int getY(){return y;}
    
    public void setWidth(int w){this.w = w;}
    
    public int getWidth(){return w;}
    
    public void setHeight(int h){this.h = h;}
    
    public int getHeight(){return h;}
    
    public void setType(int type){this.type = type;}
    
    public int getType(){return type;}
    
    public int getXPixel(){return x*Params.SQUARE_SIZE;}
    
    public int getYPixel(){return y*Params.SQUARE_SIZE;}
    
    public Rectangle getBounds(){return new Rectangle(getXPixel(),getYPixel(),w,h);}
}







