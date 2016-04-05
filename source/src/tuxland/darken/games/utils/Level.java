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

import java.awt.Dimension;
import java.util.Vector;

/**
 * Level - Bean représentant un tableau du jeu
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class Level extends Matrix{

    private String name;
    private String bgImage;
    private String fgImage;
    private String music;
    private boolean music_play;
    private Vector heroInfo;
    private Vector enemiesList;
    private Vector itemList;
    private int timer;

    public Level(){}

    /**
     * Level - Constructeur
     * @param name Nom du niveau
     * @param bgImage Image de fond du niveau
     * @param fgImage Image de premier plan du niveau
     * @param music Musqieu à jouer pour le niveau
     */
    public Level(String name, String bgImage, String fgImage, String music, boolean mp)
    {
	  this.name = name;
	  this.bgImage = bgImage;
	  this.fgImage = fgImage;
	  if (music!=null) this.music = "file:./"+Params.SND_DIR+"/"+music;
	  this.music_play=mp;
    }

    /**
     * getName - renvoie le nom du niveau
     * @return String Nom du level
     */
    public String getName(){
	return name;
    }

    /**
     * getName - renvoie le nom du niveau
     * @return String Nom du level
     */
    public String getMusic(){
    	return music;
    }

    /**
     * isMusicPlaying - renvoie true si la musique est en train de jouer
     * @return boolean etat du player de musique
     */
    public boolean isMusicPlaying()
    {
    	return music_play;
    }

    /**
     * setMusicPlay - position l'etat du player
     * @param p Etat du player
     */
    public void setMusicPlay(boolean p)
    {
    	this.music_play=p;
    }
    
    /**
     * setName - Specifier le nom du level
     * @param name Le nom du level
     */
    public void setName(String name)
    {
	  this.name = name;
    }

    /**
     * getBgImage - Renvoi le nom de fichier de l'image de fond
     * @return String Le nom du fichier
     */
    public String getBgImage()
    {
	  return bgImage;
    }

    /**
     * getFgImage - Renvoi le nom de fichier de l'image de premier plan
     * @return String Le nom du fichier
     */
    public String getFgImage()
    {
    	return fgImage;
    }

    /**
     * setBgImage - Definir le nom de fichier de l'image de fond
     * @param bgImage Nom de fichier de l'image
     */
    public void setBgImage(String bgImage)
    {
	  this.bgImage = bgImage;
    }

    /**
     * setFgImage - Definir le nom de fichier de l'image de premier plan
     * @param fgImage Nom de fichier de l'image
     */
    public void setFgImage(String fgImage)
    {
      this.fgImage = fgImage;
    }

    /**
     * getPixelWidth - Renvoi la largeur du niveau en pixel
     * @return int Largeur en pixel du niveau
     */
    public int getPixelWidth()
    {
	  return getWidth()*Params.SQUARE_SIZE;
    }
    
    /**
     * getPixelHeight - Renvoi la hauteur du niveau en pixel
     * @return int Hauteur en pixel du niveau
     */
    public int getPixelHeight()
    {
	  return getHeight()*Params.SQUARE_SIZE;
    }
    
    /**
     * getPixelDimension - Renvoi les dimensions du niveau en pixel
     * @return Dimension Dimensions (W,H) du niveau
     */
    public Dimension getPixelDimension()
    {
	  return new Dimension(getPixelWidth(),getPixelHeight());
    }

    /**
     * setEnnemiesList - Mise en place de la liste des ennemies du niveau
     * @param v Vecteur d'ennemies
     */
    public void setEnemiesList(Vector v)
    {
	  enemiesList = v;
    }

    /**
     * setItemList - Mise en place de la liste des items du niveau
     * @param v Vecteur d'item
     */
    public void setItemList(Vector v)
    {
	  itemList = v;
    }
   
    /**
     * setHeroInfo - Mise en place des infos du hero
     * @param v Vecteur infos du hero 
     */
    public void setHeroInfo(Vector v)
    {
	  heroInfo = v;
    }
    
    /**
     * getEnnemiesList - recuperation de la liste des ennemis
     * @return Vector liste des ennemis
     */
    public Vector getEnemiesList()
    {
	  return enemiesList;
    }

    /**
     * getItemList - recuperation de la liste des items
     * @return Vector liste des items
     */
    public Vector getItemList()
    {
	  return itemList;
    }

    /**
     * getHeroInfo - recuperation des infosrmation du joueur
     * @return Vector informations du joueur
     */
    public Vector getHeroInfo()
    {
	  return heroInfo;
    }

    /**
     * setTimer - initialiser le temps du timer
     * @param i Nombre de secondes
     */
    public void setTimer(int i)
    {
	  timer = i;
    }

    /**
     * getTimer - recupérer la valeur tinitiale du timer
     * @return int Nombre de secondes
     */
    public int getTimer()
    {
	  return timer;
    }
}