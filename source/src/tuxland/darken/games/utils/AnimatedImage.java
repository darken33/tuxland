/*  
 *  Tuxland - Jeu de plateforme en Java (tm)
 *  Copright (c) 2005 Philippe Bousquet (Darken33@free.fr)
 * 
 *  ---------------------------------------------
 *  IMPORTANT : 
 *    Ce jeu est bas� sur le jeu Zlob 
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
package darken.games.utils;

import java.util.Vector;

/**
 * AnimatedImage - Gestion des images anim�es
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class AnimatedImage
{
	/** Liste des images */
    public Vector frames;
    /** Premi�re image de la boucle */
    public int whereToBoucle;
    /** Delaie entre chaque image en ms */
    public int delay;
    /** TimesTamp */
    private long timeStamp;

    /**
     * AnimatedImage - Constructeur pour une annimation
     * @param frames Liste des images de l'anim
     * @param whereToBoucle Premi�re image de la boucle 
     * @param delay Temps entre chaque image en ms
     */
    public AnimatedImage(Vector frames, int whereToBoucle, int delay)
    {
	 this.frames = frames;
	 this.whereToBoucle = whereToBoucle;
	 this.delay = delay;
	 touch();
    }

    /**
     * touch - Sauvegarde du timestamp 
     */
    private void touch()
    {
	  timeStamp = System.currentTimeMillis();
    }

    /**
     * next - Permet de calculer si l'on doit passer � l'image suivante 
     */
    public boolean next()
    {
	  if ((System.currentTimeMillis()-timeStamp) > delay )
	  {	    
	    touch();
	    return true;
	  }
	  else return false;
    }
}








