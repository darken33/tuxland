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

import darken.games.event.*;
import darken.games.utils.*;

/**
 * Sprite - Gestion globale des enemies du jeu 
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class SpriteGroup extends Hashtable
{
    private int reduceCoef = darken.games.utils.Params.REDUCE_FACTOR;
    private Level level = null;
    private Rectangle updateZone = null;

    /**
     * SpriteGroup - Constructeur
     */
    public SpriteGroup()
    {
	  super();
    }

    /**
     * SpriteGroup - Constructeur
     * @param l Level en cours
     */
    public SpriteGroup(Level l)
    {
	  super();
	  level = l;
    }

    /**
     * setLevel - Affecter un level au gestionnaire
     * @param level Level en cours
     */
    public void setLevel(Level level)
    {
	  this.level = level;
    }

    /**
     * fill - Remplit le gestionnaire des ennemies avec les infos du Level
     * @return boolean true=OK, false=KO
     */
    public boolean fill()
    {
 	  Enemy enemy = null;
	  for (Enumeration e = level.getEnemiesList().elements(); e.hasMoreElements();)
	  {
	    enemy = (Enemy)e.nextElement();
	    enemy.setLevel(level);
	    if(addSprite(enemy)==false) return false;
	  }
	  return true;
    }

    /**
     * addSprite - Ajouter un ennemi augestionnaire
     * @param sprite Ennemi à ajouter
     * @return boolean true=OK, false=KO
     */
    public boolean addSprite(Sprite sprite)
    {
	  if(containsKey(sprite.getName())) return false;
	  put(sprite.getName(), sprite);
	  return true;
    }

    /**
     * getSprite - récupérer un ennemi dans le gestionnaire
     * @param id nom de l'ennemi
     * @return sprite l'objet Sprite désiré
     */
    public Sprite getSprite(String id)
    {
	  if(containsKey(id)) return (Sprite)get(id);
	  else return null;
    }
    
    /**
     * removeSprite - retirer un Ennemi
     * @param id nom de l'ennemi (ex: xbill_0)
     * @return boolean true=OK, false=KO
     */
    public boolean removeSprite(String id)
    {
	  if(!containsKey(id)) return false;
	  if(remove(id)==null) return false;
	  return true;
    }

    /**
     * display - Afficher l'ensemble des ennemy
     * @param g Graphics sur lequel dessiner
     */
    public void display(Graphics g)
    {
	  Enemy enemy = null;
	  for(Enumeration e = elements(); e.hasMoreElements();)
	  {
	    enemy = (Enemy)e.nextElement();
	    if(enemy.isActive()) enemy.display(g);
	  }
    }

    public Rectangle getUpdateZone()
    {
	  return updateZone;
    }

    public void start()
    {
	  Enemy enemy = null;
	  for(Enumeration e = elements(); e.hasMoreElements();)
	  {
	    enemy = (Enemy)e.nextElement();
	    enemy.start();
	  }
    }

    private void move(Enemy enemy){
	enemy.autoMove();
    }
    
    private void detectCollision(Enemy enemy,Hero tux) throws CollisionEvent{
	Rectangle testR = reduceRect(tux.getBounds());
	Rectangle enemyR = enemy.getBounds();
	
	if((testR.intersects(enemyR) || testR.contains(enemyR)) && tux.isActive()){
		tux.setActive(false);
	    throw new CollisionEvent(enemy);
	}
    }
    
    private Rectangle reduceRect(Rectangle r){
	return new Rectangle(r.x+reduceCoef,r.y+reduceCoef,r.width-(reduceCoef*2),r.height-(reduceCoef*2));
    }

    private void updateActivity(Enemy enemy, Rectangle screen){
	if( screen.contains(enemy.getBounds()) || screen.intersects(enemy.getBounds()) ){
	    if(!enemy.isAlive()) enemy.start();
	    enemy.setActive(true);
	}
	else 
	    enemy.setActive(false);
    }
    
    private Rectangle computeUpdateZone(Enemy enemy, Rectangle zone){
	Rectangle r = enemy.getBounds().union(enemy.getOldBounds());
	enemy.setOldBounds(enemy.getBounds());
	if(zone==null) zone = r;
	else zone = zone.union(r);
	return zone;
    }

    public void update(int x, int y, int w, int h,Hero tux) throws CollisionEvent{
	Enemy enemy = null;
	Rectangle screen = new Rectangle(x-w/2,y-h/2,w*2,h*2);
	updateZone = null;
	int c=0;
	for(Enumeration e = elements(); e.hasMoreElements();){
	    enemy = (Enemy)e.nextElement();
	    updateActivity(enemy,screen);
	    if(enemy.isActive()){
		move(enemy);
		detectCollision(enemy,tux);
		updateZone = computeUpdateZone(enemy,updateZone);
	    }
	}
    }
    
    public void free() 
    {
    	Enemy enemy = null;
    	for(Enumeration e = elements(); e.hasMoreElements();)
    	{
    	    enemy = (Enemy)e.nextElement();
    	    if(enemy.isAlive()) enemy.free();
        }
    }
}








