/* 
    Zlob: Jeu de plate-forme en Java (A Java Platform game)

    Copyright (C) 2001 Pascal "Toweld" Bourut (toweld@planetelibre.org)

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
*/

package darken.games.tuxland;

import java.awt.Point;

import darken.games.utils.*;

public class Rags extends Enemy {

    private int direction = Params.LEFT;
 
    public Rags(String name, int x, int y, int orientation){
	super(name,x,y,orientation);
    } 

    public void autoMove(){
	Point p = getMapPosition();
	int direction = getXOrientation();
       
	if(isSolid(p.x+2*direction,p.y)) direction=-direction; 
	if(direction == Params.LEFT) runLeft();
	else runRight();
    } 

}
