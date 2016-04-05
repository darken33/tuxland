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
package darken.games.utils;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Vector;

/**
 * SoundPlayer - Classe permettant de jouer les sons et musiques du jeu
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class SoundPlayer 
{	
    /**
     * Vecteur conenant les sons et musiques du jeu
     */
    private static Vector sounds = new Vector();
   
    /**
     * addSound - Permet d'ajouter un son
     * @param soundPath Nom complet du fichier son
     * @param id Identifiant dans le vecteur
     */
    public void addSound(String soundPath,int id)
    {
	  if(sounds.size()!=SoundNames.NB_MAX) sounds.setSize(SoundNames.NB_MAX);
  	  AudioClip audio = null; 
	  try
	  {
	    audio = Applet.newAudioClip(new URL(soundPath));
	  } 
	  catch(Exception e)
	  {
		System.out.println("exception : "+e);
	  }
	  if (audio == null) System.out.println("Erreur de charcge ment du fichier "+soundPath);
      sounds.setElementAt(audio,id);
    }
    
    /**
     * play - permet de jouer le son désiré
     * @param id Identifiant du son dans le vecteur
     */
    public void play(int id)
    {
  	  try
	  {
 	    ((AudioClip)sounds.elementAt(id)).play();
	  }
	  catch(Exception e)
	  {
			System.out.println("exception : "+e);
	  }
    }

    /**
     * stop - permet d'arreter la lecture d'un son
     * @param id Identifiant du son dans le vecteur
     */
    public void stop(int id)
    {
      ((AudioClip)sounds.elementAt(id)).stop();
    }
    
    /**
     * loop - permet de jouer un son en boucle infini
     * @param id Identifiant du son dans le vecteur
     */
    public void loop(int id)
    {
	  ((AudioClip)sounds.elementAt(id)).loop();
    }
}


