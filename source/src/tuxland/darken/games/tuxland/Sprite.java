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
import java.io.File;

import darken.games.utils.*;

/**
 * Sprite - Gestion unitaire des differents personnages du jeu 
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class Sprite extends Component implements Runnable, Action
{
    protected String name = null;
    protected int x = 0;  
    protected int y = 0; 
    protected int xPixel = x*Params.SQUARE_SIZE;
    protected int yPixel = y*Params.SQUARE_SIZE;
    protected int width = Params.SQUARE_SIZE;
    protected int height = Params.SQUARE_SIZE;
    protected int oldX = x;
    protected int oldY = y;
    protected int oldXPixel = xPixel;
    protected int oldYPixel = yPixel;
    protected int xOrientation;
    protected int yOrientation = Params.DOWN;
    protected int action = REST;
    protected Image img = null;
    protected int dX = 0;
    protected int dY = 0;
    protected Thread thread = null;
    protected int edge = Params.RUN_MAX;
    protected boolean suspension = false;
    protected double t = 0;
    protected int x0 = 0;
    protected int y0 = 0;
    protected int dX0 = 0;
    protected int dY0 = 0;
    protected int xModif = 0;
    protected int levelMatrix[][]=null;
    protected int levelWidth = 0;
    protected int levelHeight = 0;
    protected boolean inTunnel = false;
    protected int ox;
    protected int oy = Params.DOWN;
    protected int oxd;
    protected boolean jumpAbortedByCollision = false;
    protected boolean jumpAbortedBySideCollision = false;
    protected boolean wallCollision = false;
    protected boolean shiftDone = false;
    protected ImageDisplayer imageDisplayer = null;
    protected Rectangle oldR = null;
    private boolean active = false;
    private boolean paused = false;
    
    /**
     * Sprite - Constructeur
     */
    public Sprite()
    {
	  setSize(width, height);
	  setVisible(true);
	  thread = new Thread(this);
	  imageDisplayer = new ImageDisplayer();
    }

    /**
     * Sprite - Constructeur
     * @param _name Nom du perso
     * @param _x Position X
     * @param _y Position Y
     * @param _xOrientation Orientation
     */
    public Sprite(String _name, int _x, int _y, int _xOrientation)
    {
	  this();
	  name = _name;
	  x = _x;
	  oldX = x;
	  xPixel = _x*Params.SQUARE_SIZE;
	  oldXPixel = xPixel;
	  y = _y;
	  oldY = y;
	  yPixel = _y*Params.SQUARE_SIZE;
	  oldYPixel = yPixel;
	  xOrientation = _xOrientation;
	  ox = _xOrientation;
	  oxd = _xOrientation;
    }
    
    /**
     * setActive - Activer/Désactiver le perso
     * @param a true=activer, false=désactiver
     */
    public void setActive(boolean a)
    {
	  active = a;
    }
    public void setPaused(boolean a)
    {
	  paused = a;
    }

    /**
     * isActive - Le perso est il actif ?
     * @return boolean true=oui, false=non
     */
    public boolean isActive()
    {
	  return active;
    }
    public boolean isPaused()
    {
	  return paused;
    }
    
    /**
     * init - Réinitialisation d'un perso
     * @param _x position X
     * @param _y position Y
     * @param _o orientation
     */
    public void init(int _x, int _y, int _o)
    {
        x = _x;  
        y = _y; 
        xPixel = x*Params.SQUARE_SIZE;
        yPixel = y*Params.SQUARE_SIZE;
        oldX = x;
        oldY = y;
        oldXPixel = xPixel;
        oldYPixel = yPixel;
        xOrientation=_o;
        yOrientation = Params.DOWN;
        action = REST;
        dX = 0;
        dY = 0;
        suspension = true;
        t = 0;
        x0 = xPixel;
        y0 = yPixel;
        dX0 = 0;
        dY0 = 0;
        xModif = 0;
        inTunnel = false;
        ox=_o;
        oy = Params.DOWN;
        oxd=_o;
        jumpAbortedByCollision = false;
        jumpAbortedBySideCollision = false;
        wallCollision = false;
        shiftDone = false;
        oldR = null;
        active = false;
    }

    /**
     * reset - RAZ d'un perso
     */
    public void reset()
    {
	  name = null;
	  x = 0;  
	  y = 0; 
	  xPixel = x*Params.SQUARE_SIZE;
	  yPixel = y*Params.SQUARE_SIZE;
	  oldX = x;
	  oldY = y;
	  oldXPixel = xPixel;
	  oldYPixel = yPixel;
	  action = REST;
	  dX = 0;
	  dY = 0;
	  suspension = false;
	  t = 0;
	  x0 = 0;
	  y0 = 0;
	  dX0 = 0;
	  dY0 = 0;
	  xModif = 0;
	  levelWidth = 0;
	  levelHeight = 0;
	  inTunnel = false;
	  oy = Params.DOWN;
	  jumpAbortedByCollision = false;
	  jumpAbortedBySideCollision = false;
	  wallCollision = false;
	  shiftDone = false;
	  oldR = null;
	  active = false;
    }
   
    public void setName(String _name)
    {
	  name = _name;
    }

    public String getName()
    {
	  return name;
    }

    public void setX(int _x)
    {
	  x = _x;
  	  xPixel=x*Params.SQUARE_SIZE;
    }

    public int getX()
    {
	  return x;
    } 
    
    public void setY(int _y)
    {
	  y = _y;
	  yPixel = _y*Params.SQUARE_SIZE;
    }
    
    public int getY()
    {
	  return y;
    }

    public void setPosition(Point p)
    {
	  x = p.x;
	  y = p.y;
    }

    public Point getCenter()
    {
	  return new Point(getXPixel()+(getWidth()/2),getYPixel()+(getHeight()/2));
    }

    public int getMapX()
    {
	  if(getXOrientation()==Params.RIGHT) return getXPixel()/Params.SQUARE_SIZE; 
   	  return ((getXPixel()+getWidth())/Params.SQUARE_SIZE);
    }
    
    public int getMapY()
    {
	  return getYPixel()/Params.SQUARE_SIZE; 
    }
 
    public int getMapX(int _xTmp)
    {
	  ox = getXOrientation(_xTmp);
	  if(ox==Params.RIGHT) return getXPixel()/Params.SQUARE_SIZE; 
	  return (getXPixel()+getWidth())/Params.SQUARE_SIZE; 
    }
    
    public int getMapY(int _yTmp)
    {
	  oy = getYOrientation(_yTmp);
	  return getYPixel()/Params.SQUARE_SIZE; 
    }
 
    public Point getMapPosition()
    {
	  return new Point(getMapX(),getMapY());
    }
    
    public Point getMapPosition(int _xTmp, int _yTmp)
    {
	  return new Point(getMapX(_xTmp),getMapY(_yTmp));
    }

    public Point getPosition()
    {
	  return new Point(x,y);
    }

    public Point getLocation()
    {
	  return new Point(getXPixel(), getYPixel());
    }

    public void setXOrientation(int o)
    {
	  xOrientation = o;
    }
    
    public void setYOrientation(int o)
    {
	  yOrientation = o;
    }

    public int getXOrientation()
    {
      if (oldXPixel<getXPixel())
      { 
      	xOrientation = Params.RIGHT;
      	oxd=xOrientation;
      }
      else if (oldXPixel>getXPixel())
      {
      	xOrientation = Params.LEFT;
     	oxd=xOrientation;
      }
      else xOrientation = oxd;
      return xOrientation;
    }
    

    public int getYOrientation()
    {
	  if (oldYPixel<getYPixel()) yOrientation = Params.DOWN;
	  else if (oldYPixel>getYPixel()) yOrientation = Params.UP;
	  return yOrientation;
    }

    public Point getOrientation()
    {
	  return new Point(getXOrientation(), getYOrientation());
    }

    protected int getXOrientation( int x )
    {
	  if (oldXPixel<x) return Params.RIGHT;
	  else if (oldXPixel>x) return Params.LEFT;
	  else return oxd;
    }
    
    protected int getYOrientation( int y )
    {
	  if (oldYPixel<y) return Params.DOWN;
	  else if (oldYPixel>y) return Params.UP;
	  else return oy;
    }

    public void setAction(int _action)
    {
	  action = _action;
    }

    public int getAction()
    {
	  int act = REST+LEFT;
	  Point o = getOrientation();
	  // Si le perso est en l'air
	  if(suspension)
	  {
		// Il saute
	    if(o.y==Params.UP)
	    {
		  if(o.x==Params.LEFT) act = JUMP+LEFT;
		  else if (o.x==Params.RIGHT) act = JUMP+RIGHT;
	    }
	    // Ou il tombe
	    else if(o.y==Params.DOWN)
	    {
		  if(o.x==Params.LEFT) act = FALL+LEFT;
		  else if (o.x==Params.RIGHT) act = FALL+RIGHT;
	    }
	  }
	  // Sinon s'il se deplace sur X
	  else if(dX!=0)
	  {
		// Il court ?
	    if(action==RUN)
	    {
	      // vers la gauche
		  if(o.x==Params.LEFT) act = RUN+LEFT;
		  // vers la droite
		  else if (o.x==Params.RIGHT) act = RUN+RIGHT;
	    }
	    // Il marche
	    else 
	    {
	      // Vers la gauche	
		  if(o.x==Params.LEFT) act = WALK+LEFT;
	      // Vers la droite	
		  else if (o.x==Params.RIGHT) act = WALK+RIGHT;
	    }
	  }
	  else 
	  {
	  	// Il se protège
	    if(action==SQUAT)
	    {
		  if(o.x==Params.LEFT) act = SQUAT+LEFT;
		  else if (o.x==Params.RIGHT) act = SQUAT+RIGHT;
	    }
	    // Il ne fait rien
	    else
	    {
		  if(o.x==Params.LEFT) act = REST+LEFT;
		  else if (o.x==Params.RIGHT) act = REST+RIGHT;
	    } 
	  }
	  return act;
    }
    
    public int getXPixel(){	return xPixel; }

    public int getYPixel(){ return yPixel; }

    public int getWidth(){ return width; }

    public int getHeight(){	return height; }

    public Rectangle getLargeBounds()
    {
	  return new Rectangle(getXPixel()-edge,
			               getYPixel()-edge,
			               getWidth()+(edge*2), 
			               getHeight()+(edge*2));
    }

    public Rectangle getBounds()
    {
	  return new Rectangle(getXPixel(),getYPixel(),getWidth(), getHeight());
    }

    public Rectangle getOldBounds()
    {
	  return oldR;
    }

    public void setOldBounds(Rectangle r)
    {
	  oldR = r;
    }

    public void setLevel(Level l)
    {
	  levelWidth = l.getWidth();
	  levelHeight = l.getHeight();
	  levelMatrix = new int[levelWidth][levelHeight];
	  levelMatrix = l.getMatrix();
    }

    public void start()
    {
	  if(thread!=null && !isAlive() ) thread.start();
    }
    
    public void free() 
    {
    	if(thread!=null && isAlive()) thread.interrupt();	
    }

    public boolean isAlive()
    {
	  if(thread!=null && thread.isAlive()) return true;
	  else return false;
    }

    /**
     * run - boucle d'execution du thread
     */
    public void run()
    {
      // boucle infini
	  while(true)
	  {
	  	
	    if(levelMatrix!=null && isActive())
	    	{
	    	  updatePlacement();
	    	}
	    try
		{
		  Thread.sleep(20);
	    }
	    catch(InterruptedException ie)
		{
		  ie.printStackTrace();
		  break;
	    }
	  }
    }
    
    /**
     * walkLeft - Deplacer le sprite vers la gauche
     */
    public void walkLeft()
    {
	  oxd = Params.LEFT;
	  if(suspension) jumpLeft();
	  else 
	  {
	    if ( dX > -Params.WALK_MAX ) 
		dX-=Params.WALK_STEP;
	    else 
		dX = -Params.WALK_MAX;
	  }
    }
    
    /**
     * walkRight - Déplacer le sprite vers la droite
     */
    public void walkRight()
    {
	  oxd = Params.RIGHT;
	  if(suspension) jumpRight();
	  else 
	  {
	    if ( dX < Params.WALK_MAX ) 
		dX+=Params.WALK_STEP;
	    else 
		dX = Params.WALK_MAX;
	  }
    }

    /**
     * runLeft - Courrir vers la gauche
     */
    public void runLeft()
    {
	  oxd = Params.LEFT;
	  if(suspension) jumpLeft();
	  else 
	  {
	    if ( dX > -Params.RUN_MAX ) 
		dX-=Params.RUN_STEP;
	    else 
		dX = -Params.RUN_MAX;
	  }
    }

    /**
     * runRight - Courrir vers la droite
     */
    public void runRight()
    {
	  oxd = Params.RIGHT;
	  if(suspension) jumpRight();
	  else 
	  {
	    if ( dX < Params.RUN_MAX ) 
		dX+=Params.RUN_STEP;
	    else 
		dX = Params.RUN_MAX;
	  } 
    }
    
    /**
     * hold - S'arreter
     */
    public void hold()
    {
	  setAction(Action.REST);
	  if(dX>0) 
	  {
	    dX-=Params.WALK_STEP;
	    if (dX<0) dX=0;
	  }
	  else if(dX<0) 
	  {
	    dX+=Params.WALK_STEP;
	    if (dX>0) dX=0;
	  }
    }

    /**
     * jumpLeft - Sauter vers la gauche
     */
    public void jumpLeft()
    {
	  if(jumpAbortedByCollision || jumpAbortedBySideCollision) return;
	  xModif-=(Params.WALK_STEP)*2;
    }

    /**
     * jumpRight - Sauter vers la droite
     */
    public void jumpRight()
    {
	  if(jumpAbortedByCollision || jumpAbortedBySideCollision) return;
	  xModif+=(Params.WALK_STEP)*2;
    }

    /**
     * jump - Sauter
     */
    public void jump()
    {
	  if(suspension || inTunnel) return;
	  x0 = getXPixel();
	  y0 = getYPixel();
	  dX0 = dX*2;
	  dY0 = Params.JUMP_MAX;
	  t = 0;
	  xModif = 0;
	  suspension = true;
    }

    /**
     * endOfJump - Fin de saut;
     *
     */
    public void endOfJump()
    {
	  if ( getYOrientation()==Params.UP && suspension ) 
	  {
	    x0 = getXPixel();
	    y0 = getYPixel();
	    xModif=0;
	    dY0 = 0;
	    t = 0;
	  }
    }

    /**
     * fallDown - tomber
     */
    public void fallDown()
    {
	  if (suspension) return;
	  x0 = getXPixel();
	  y0 = getYPixel();
	  dX0 = dX;
	  dY0 = 0;
	  t = 0;
	  xModif = 0;
	  suspension = true;
    }
    
    /**
     * fallDown - tomber
     * @param _x0
     * @param _y0
     * @param _dX0
     * @param _dY0
     * @param _xModif
     */
    public void fallDown(int _x0, int _y0, int _dX0, int _dY0, int _xModif)
    {
	  if (suspension) return;
	  x0 = _x0;
	  y0 = _y0;
	  dX0 = _dX0;
	  dY0 = _dY0;
 	  t = 0;
	  xModif = _xModif;
	  suspension = true;
    }

    /**
     * abortJump - arreter un saut (lors d'un contact) 
     */
    public void abortJump()
    {
	  x0 = getXPixel();
	  y0 = getYPixel();
	  dY0 = 0;
	  t = 0;
	  suspension = true;
    }
    
    /**
     * abortJump - arreter un saut (lors d'un contact) 
     * @param _x0
     * @param _y0
     * @param _dX0
     * @param _dY0
     * @param _xModif
     */
    public void abortJump(int _x0, int _y0, int _dX0, int _dY0, int _xModif)
    {
	  x0 = _x0;
	  y0 = _y0;
	  dX0 = _dX0;
	  dY0 = _dY0;
	  xModif = _xModif;
	  t = 0;
	  suspension = true;
    }

    /**
     * updatePlacement - Mettre à jour le déplacement 
     */
    public void updatePlacement()
    {
	  oldXPixel = getXPixel();
	  oldYPixel = getYPixel();
	  int xTmp = getXPixel();
	  int yTmp = getYPixel();
	  // En suspension
	  if(suspension)
	  {
	  	// On prend en compte la gravité
	    t+=0.3;   
	    xTmp = (int)(dX0 * t + x0 + xModif);
	    yTmp = (int)((Params.GRAVITY/2 * t * t) + (dY0 * t) + y0); 
	  }
	  else
	  {
	    xTmp+=dX;
	    yTmp+=dY;
	  }
	  // Est ce qu'on percute un element du décord
	  testMapCollision(xTmp, yTmp);
    }
  
    public void testMapCollision(int xTmp, int yTmp) 
    {
	   Point p = getMapPosition(xTmp,yTmp);
	   Debug.println(DebugNames.NORMAL_PRIORITY,this,"==> xTmp="+xTmp+" yTmp="+yTmp+" p=("+p.x+","+p.y + ") xModif=" + xModif);
	   Debug.println(DebugNames.NORMAL_PRIORITY,this,"==> getOrientation=("+getXOrientation()+","+getYOrientation()+") inTunnel="+inTunnel + " suspension="+suspension);
	   Debug.println(DebugNames.NORMAL_PRIORITY,this,"==> ox="+ox +" oy="+oy );
	
	   // Un mur à coté
	   if ( isSolid(p.x+ox,p.y) )
	   {
	     Debug.println(DebugNames.LOW_PRIORITY,this,"dur a cote");
	     wallCollision = true;
	     if(suspension) 
	     {
		   if(ox==Params.LEFT&&(xModif<0)) xModif=0;
		   if(ox==Params.RIGHT&&(xModif>0)) xModif=0;
		   if ( !jumpAbortedBySideCollision )
		   {
		     x0 = xPixel;
		     dX0 = 0;
		     xModif = 0;
		     jumpAbortedBySideCollision = true;
		   }
	     }
	     else 
	     {
		   xTmp = p.x*Params.SQUARE_SIZE;
		   xPixel = xTmp;
	     }
	   }
	   else if ((( ox == Params.LEFT && isSolid(p.x-2,p.y) ) && ( xTmp <= (p.x-1)*Params.SQUARE_SIZE  )) || 
		        (( ox == Params.RIGHT && isSolid(p.x+2,p.y) ) && ( xTmp >= (p.x+1)*Params.SQUARE_SIZE ))) 
	   {
	     wallCollision = true;
	     Debug.println(DebugNames.LOW_PRIORITY,this,"dur a cote xTmp="+xTmp);
	     xTmp = (p.x+ox)*Params.SQUARE_SIZE-ox;
	     xPixel = xTmp;
	     if(suspension) 
	     {
		   if(ox==Params.LEFT&&(xModif<0)) xModif=0;
		   if(ox==Params.RIGHT&&(xModif>0)) xModif=0;
		   if ( !jumpAbortedBySideCollision )
		   {
		     x0 = xPixel;
		     dX0 = 0;
		     xModif = 0;
		     jumpAbortedBySideCollision = true;
		   }
	     }
	   }
	   else if( suspension && /*1*/
	 	        oy==Params.DOWN && /*2*/
		        ((( ox == Params.LEFT && isSolid(p.x-2,p.y+1) ) && ( xTmp <= (p.x-1)*Params.SQUARE_SIZE  )) || 
		         (( ox == Params.RIGHT && isSolid(p.x+2,p.y+1) ) && ( xTmp >= (p.x+1)*Params.SQUARE_SIZE ))) && /*3*/
		        (!isSolid(p.x+ox,p.y+1)) && /*4*/
		        (yTmp > (p.y+1-Params.SIDE_PERCENTAGE)*Params.SQUARE_SIZE))
	   {
	     // on ne peut pas passer un gouffre sans courrir ou presque
	     Debug.println(DebugNames.LOW_PRIORITY,this,"trou");
	     wallCollision = true;
	   }
	   else if( suspension && /*1*/
		        oy==Params.DOWN && /*2*/
		        isSolid(p.x+ox,p.y+1) && /*3*/
		        (yTmp > (p.y+1-Params.SIDE_PERCENTAGE)*Params.SQUARE_SIZE)/*4*/)
	   {
	     Debug.println(DebugNames.LOW_PRIORITY,this,"est ce que c'est mieux comme ca ?");
	     wallCollision = true;
	   }
	   /*on ne monte pas sur une case sans etre assez haut*/
	   else if( suspension && /*1*/
		        oy==Params.UP && /*2*/
		        isSolid(p.x+ox,p.y+1) && /*3*/
		        (yTmp>((p.y+1-Params.SIDE_PERCENTAGE)*Params.SQUARE_SIZE))/*4*/)
	   {
	     Debug.println(DebugNames.LOW_PRIORITY,this,"est ce que c'est mieux comme ca ?");
	     wallCollision = true;
	   }
	   else 
	   {
	     wallCollision = false;
	     jumpAbortedBySideCollision = false;
	     jumpAbortedByCollision = false;
	     Debug.println(DebugNames.LOW_PRIORITY,this,"la voie est libre a cote xTmp="+xTmp);
	     xPixel = xTmp;
	   }
	   // je monte
	   if ( yTmp<oldYPixel && suspension ) 
	   {
	     if(( isSolid(p.x,p.y-1) || isSolid(p.x+ox,p.y-1)) && 
		    ( yTmp < (p.y * Params.SQUARE_SIZE) ))
	     {
		   if (isSolid(p.x+ox,p.y-1) && !isSolid(p.x,p.y-1) && wallCollision)
		   {
		     Debug.println(DebugNames.LOW_PRIORITY,this,"une case seulement devant en montant");
		     xTmp = p.x*Params.SQUARE_SIZE;//-ox;
		     yPixel = yTmp;
		     jumpAbortedByCollision = true;
		   }
		   else 
		   {
		     if(((ox==Params.LEFT) && ( isSolid(p.x,p.y-1) && (!isSolid(p.x-1,p.y-1)) && (xTmp < ((p.x-Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)))) ||
		        ((ox==Params.RIGHT) && ( isSolid(p.x,p.y-1) && (!isSolid(p.x+1,p.y-1)) && ( xTmp > ((p.x+Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)))))
		     {
			   Debug.println(DebugNames.LOW_PRIORITY,this,"ca passe avant!!!");
			   yPixel = yTmp;
			   if(!shiftDone)
			   {
			     shiftDone=true;
			     Debug.println(DebugNames.LOW_PRIORITY,this,"*=*=*=*=*=> ca passe avant");
			     if(xTmp==yPixel)x0 = (p.x+ox)*Params.SQUARE_SIZE;//+ox;
			   }
			   inTunnel = false;
		     }
		     else if (((ox==Params.LEFT) && ( isSolid(p.x-1,p.y-1) && (!isSolid(p.x,p.y-1)) && (xTmp > ((p.x-1+Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)))) ||
			          ((ox==Params.RIGHT) && ( isSolid(p.x+1,p.y-1) && (!isSolid(p.x,p.y-1)) && (xTmp < ((p.x+1-Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)))))
		     {
			   Debug.println(DebugNames.LOW_PRIORITY,this,"ca passe arriere!!!");
			   yPixel = yTmp;
			   if(!shiftDone)
			   {
			     shiftDone=true;
			     Debug.println(DebugNames.LOW_PRIORITY,this,"*=*=*=*=*=> ca passe arriere");
			     x0 = p.x*Params.SQUARE_SIZE;//-ox;
			   }
		     }
		     else 
		     {
			   Debug.println(DebugNames.LOW_PRIORITY,this,"je touche le plafond en haut");
			   yPixel = yTmp;
			   if(yPixel<(p.y*Params.SQUARE_SIZE)) yPixel = p.y*Params.SQUARE_SIZE;
			   endOfJump();
		     }
		   }
	     }
	     else 
	     {
		   Debug.println(DebugNames.LOW_PRIORITY,this,"la voie est libre en haut");
		   yPixel = yTmp;
		   inTunnel = false;
	     }	    
	   } 
  	   // if je descend => test si j'atteris
	   else if (yTmp>=oldYPixel  && suspension) 
	   {
	     if (( isSolid(p.x,p.y+1) || isSolid(p.x+ox,p.y+1) ) ||
	     	 ( isPlatform(p.x,p.y+1) || isPlatform(p.x+ox,p.y+1) ))
	     {
		   if ( (isSolid(p.x+ox,p.y+1) || isPlatform(p.x+ox,p.y+1)) && !(isSolid(p.x,p.y+1) || isPlatform(p.x,p.y+1)) && wallCollision)
		   {
		     Debug.println(DebugNames.LOW_PRIORITY,this,"une case seulement devant en descendant");
		     xTmp = p.x*Params.SQUARE_SIZE-ox;
		     abortJump(xTmp,yPixel,0,0,-ox);
		     jumpAbortedByCollision = true;
		   }
		   else 
		   {
		     if(((ox==Params.LEFT) && ( (isSolid(p.x,p.y+1) || isPlatform(p.x,p.y+1)) && (!(isSolid(p.x-1,p.y+1) || isPlatform(p.x-1,p.y+1))) && (xTmp < ((p.x-Params.STABILITY)*Params.SQUARE_SIZE)))) ||
		        ((ox==Params.RIGHT) && ( (isSolid(p.x,p.y+1) || isPlatform(p.x,p.y+1)) && (!(isSolid(p.x+1,p.y+1) || isPlatform(p.x+1,p.y+1))) && (xTmp > ((p.x+Params.STABILITY)*Params.SQUARE_SIZE)))))
		     {
			   Debug.println(DebugNames.LOW_PRIORITY,this,"descendant: desequilibre et chute en avant");
			   yPixel = yTmp;
		     }  
		     else 
		     {
			   Debug.println(DebugNames.LOW_PRIORITY,this,"bas en descendant");
			   dX = 0;
			   yPixel = p.y*Params.SQUARE_SIZE;
			   landing();
  			   // tunnel
			   if ((isSolid(p.x,p.y-1) || isSolid(p.x+ox,p.y-1) ) && !(((ox==Params.LEFT) && ( isSolid(p.x,p.y-1) && (!isSolid(p.x+ox,p.y-1)) && (xTmp < ((p.x-Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) )) || ( (ox==Params.RIGHT) && ( isSolid(p.x,p.y-1) && (!isSolid(p.x+ox,p.y-1)) && (xTmp > ((p.x+Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) )) || ( (ox==Params.LEFT) && ( isSolid(p.x-1,p.y-1) && (!isSolid(p.x,p.y-1)) && (xTmp > ((p.x-1+Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) )) || ( (ox==Params.RIGHT) && ( isSolid(p.x+1,p.y-1) && (!isSolid(p.x,p.y-1)) && (xTmp < ((p.x+1-Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) )) )  )
			   {
			     Debug.println(DebugNames.LOW_PRIORITY,this,"tunnel en descendant");
			     inTunnel = true;
			   }
			   else 
			   {
			     inTunnel = false;
			   } 
		     }
		   }
	     }
	     // pour corriger le bug du decalage quand on touchait le sol
	     else if ((isSolid(p.x, p.y+2) || ( isSolid(p.x, p.y+2) &&  isSolid(p.x+ox, p.y+2) ) || ( isSolid(p.x+ox,p.y+2) && !isSolid(p.x,p.y+2) && !wallCollision ) ) && 
		          (yTmp > ((p.y+1)*Params.SQUARE_SIZE)))
	     {
		   Debug.println(DebugNames.LOW_PRIORITY,this,"***** ca chie **** yTmp = "+yTmp);
		   dX = 0;
		   yPixel = (p.y+1)*Params.SQUARE_SIZE;
		   landing();
	     }
	     else 
	     {
		   Debug.println(DebugNames.LOW_PRIORITY,this,"il n'y a toujours rien sous mes pieds");
		   yPixel = yTmp;
		   //fallDown();
	     }   
	   }
	   // si je marche => test sur il y a le vide sous mes pieds...
	   else if ( yTmp == oldYPixel && !suspension) 
	   {	    
	     if ((isSolid(p.x,p.y+1) || isSolid(p.x+ox,p.y+1)) ||
	   		 (isPlatform(p.x,p.y+1) || isPlatform(p.x+ox,p.y+1)))	
	     {
		   if(((ox==Params.LEFT) && ( (isSolid(p.x,p.y+1) || isPlatform(p.x,p.y+1)) && (!(isSolid(p.x+ox,p.y+1) || isPlatform(p.x+ox,p.y+1))) && (xTmp <= ((p.x-Params.STABILITY)*Params.SQUARE_SIZE)))) ||
		      ((ox==Params.RIGHT) && ( (isSolid(p.x,p.y+1) || isPlatform(p.x,p.y+1)) && (!(isSolid(p.x+ox,p.y+1) || isPlatform(p.x+ox,p.y+1))) && (xTmp >= ((p.x+Params.STABILITY)*Params.SQUARE_SIZE)))))
		   {
		     Debug.println(DebugNames.LOW_PRIORITY,this,"marchant: desequilibre et chute et p.y="+p.y);
		     yPixel = yTmp;
		     fallDown(xTmp, yTmp,dX,0,0);
		   }
		   else
		   {		    
		     Debug.println(DebugNames.LOW_PRIORITY,this,"bas en marchant");
		     landing();
		     setY(p.y);
		     // tunnel
		     if ( (isSolid(p.x,p.y-1) || isSolid(p.x+ox,p.y-1) ) && !( ( (ox==Params.LEFT) && ( isSolid(p.x,p.y-1) && (!isSolid(p.x+ox,p.y-1)) && (xTmp < ((p.x-Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) )) || ( (ox==Params.RIGHT) && ( isSolid(p.x,p.y-1) && (!isSolid(p.x+ox,p.y-1)) && (xTmp > ((p.x+Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) )) || ( (ox==Params.LEFT) && ( isSolid(p.x-1,p.y-1) && (!isSolid(p.x,p.y-1)) && (xTmp > ((p.x-1+Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) ))  || ( (ox==Params.RIGHT) && ( isSolid(p.x+1,p.y-1) && (!isSolid(p.x,p.y-1)) && (xTmp < ((p.x+1-Params.HEAD_PERCENTAGE)*Params.SQUARE_SIZE)) )) ) )
		     {
		 	   Debug.println(DebugNames.LOW_PRIORITY,this,"tunnel en marchant"); 
	 		   inTunnel = true;
		     }
		     else 
		     {
			   inTunnel = false;
		     } 
		   }
	     }
	     else 
	     {
		   Debug.println(DebugNames.LOW_PRIORITY,this,"vide sous mes pieds");
		   fallDown();
		   inTunnel = false;
	     }
	   }
    }
    
    /**
     * isSolid - Verifie si une case de la matrice est de type SOLID
     * @param a position X
     * @param b position Y
     * @return boolean true=oui, false=non
     */
    protected boolean isSolid(int a, int b)
    {
	  try
	  {
	    if ( levelMatrix[a][b] == MkLvlConst.SOLID ) return true;	    	
	    else return false;
	  }
	  catch(ArrayIndexOutOfBoundsException e)
	  {
	    if (a<0) Debug.println(DebugNames.LOW_PRIORITY,this,"*****left*****");
	    else if (a>=levelWidth) Debug.println(DebugNames.LOW_PRIORITY,this,"*****right*****");
	    else if (b<0) Debug.println(DebugNames.LOW_PRIORITY,this,"*****up*****");
	    else if (b>=levelHeight) Debug.println(DebugNames.LOW_PRIORITY,this,"*****down*****");
	    //System.exit(0);
	    return false;
	  }
    }

    /**
     * isPlatform - Verifie si une case de la matrice est de type PLATFORM
     * @param a position X
     * @param b position Y
     * @return boolean true=oui, false=non
     */
    protected boolean isPlatform(int a, int b)
    {
      try
	  {
    	if ( levelMatrix[a][b] == MkLvlConst.PLATFORM ) return true;
    	else return false;
      }
      catch(ArrayIndexOutOfBoundsException e)
	  {
    	if (a<0) Debug.println(DebugNames.LOW_PRIORITY,this,"*****left*****");
    	else if (a>=levelWidth) Debug.println(DebugNames.LOW_PRIORITY,this,"*****right*****");
    	else if (b<0) Debug.println(DebugNames.LOW_PRIORITY,this,"*****up*****");
    	else if (b>=levelHeight) Debug.println(DebugNames.LOW_PRIORITY,this,"*****down*****");
    	//System.exit(0);
        return false;
      }
    }

    /**
     * isEmpty - Verifie si une case de la matrice est de type Empty
     * @param a position X
     * @param b position Y
     * @return boolean true=oui, false=non
     */
    protected boolean isEmpty(int a, int b)
    {
      try
	  {
    	if ( levelMatrix[a][b] == MkLvlConst.EMPTY ) return true;
    	else return false;
      }
      catch(ArrayIndexOutOfBoundsException e)
	  {
    	if (a<0) Debug.println(DebugNames.LOW_PRIORITY,this,"*****left*****");
    	else if (a>=levelWidth) Debug.println(DebugNames.LOW_PRIORITY,this,"*****right*****");
    	else if (b<0) Debug.println(DebugNames.LOW_PRIORITY,this,"*****up*****");
    	else if (b>=levelHeight) Debug.println(DebugNames.LOW_PRIORITY,this,"*****down*****");
    	//System.exit(0);
    	return false;
      }
    }
  
    /**
     * landing - fait atterrir un sprite
     */
    protected void landing()
    {
	  shiftDone = false;
	  suspension = false;
	  xModif=0;
	  dY = 0;
	  jumpAbortedByCollision = false;
	  jumpAbortedBySideCollision = false;
    }

    /**
     * setAnimationImage - Permet de charger l'ensemble des images suivant le type de sprite
     * @param d Type de sprite (par ex : tux, xbill, ...)
     */
    protected void setAnimationImage(String d)
    {
	  String dir = Params.IMG_DIR+File.separator+d+File.separator;
	  for(int i=LEFT;i<=RIGHT;i++)
	  {
	    for(int _action=REST;_action<=DEAD;_action+=2)
	    {
		  int index = _action/2;
		  imageDisplayer.addLineOfImage(dir+ALL[index][IMG]+i+GIF,
					                    Integer.parseInt(ALL[index][NB]),
					                    (_action==SQUAT)?Integer.parseInt(ALL[index][NB])-1:0, 
					                    Integer.parseInt(ALL[index][DELAY]),
					                    Integer.parseInt(ALL[index][ID])+i);
	    }
	  }	
    }

    public void paint(Graphics g){}
    
    /**
     * display - Affiche un sprite
     * @param g Graphics sur lequl dessiner
     */
    public void display(Graphics g)
    {
	  imageDisplayer.displayNextImage(g,getXPixel(),getYPixel(),getAction());
    }
}

