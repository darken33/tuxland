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

import java.io.*;
import java.util.Vector;
import java.util.Date;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.text.DateFormat;

import darken.games.tuxland.*;

/**
 * FileHandler - Classe permettant le chargement d'un niveau
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class FileHandler 
{   
    static BufferedReader reader = null;
    static PrintWriter writer = null;
    static String fileName = null;
    static String levelName = null;
    static int levelWidth = 0;
    static int levelHeight = 0;
    static String levelBgImage = null;
    static String levelFgImage = null;
    static String levelMusic = null;
    static int [][] levelMatrix = null;
    static Vector heroInfo = null;
    static Vector enemiesList = null;
    static Vector itemList = null;
    static int timer = 0;
    static boolean levelNameOk = false;
    static boolean levelWidthOk = false;
    static boolean levelHeightOk = false;
    static boolean levelBgImageOk = false;
    static boolean levelFgImageOk = false;
    static boolean levelMatrixOk = false;
    static boolean heroInfoOk = false;
    static boolean enemiesListOk = false;
    static boolean itemListOk = false;
    static boolean timerOk = false;
    static boolean levelMusicOk = false;
    
    /**
     * loadLevel - Chargement d'un niveau du jeu 
     * @param name Nom du level (levelN ou N est numerique)
     * @return Level Le level chargé
     */
    public static Level loadLevel(String name, boolean mp)
    {
	  reset();
	  fileName = "share/levels/" + name + ".lvl";  

	  Debug.println(DebugNames.HIGH_PRIORITY,"-> loadLevel "+fileName);

	  try
	  {
	    reader = new BufferedReader(new FileReader(fileName));
	  }
	  catch(FileNotFoundException fnfe)
	  {
	    fnfe.printStackTrace();
	  }
	
	  try
	  {
	    levelNameOk = false;
	    levelWidthOk = false;
	    levelHeightOk = false;
	    levelBgImageOk = false;
	    levelFgImageOk = false;
	    levelMatrixOk = false;
	    heroInfoOk = false;
	    enemiesListOk = false;
	    itemListOk = false;
	    timerOk = false;
	    levelMusicOk = false;
	    String line;
	    while ((line = reader.readLine()) != null ) 
	    {
	      // On recupère le nom du level	
		  if(line.startsWith(Params.LVL_NAME) ) 
		  {
		    levelName = line.substring(line.indexOf("=") + 1);
		    levelNameOk = true;
		    Debug.println(DebugNames.LOW_PRIORITY,"   levelName "+levelName);
		  }
		  // On recupère la largeur du level
		  else if(line.startsWith(Params.LVL_WIDTH) ) 
		  {
		    levelWidth = Integer.parseInt(line.substring(line.indexOf("=")+1));
		    levelWidthOk = true;
		    Debug.println(DebugNames.LOW_PRIORITY,"   levelWidth "+levelWidth);
		  }
		  // On recupère la hauteur du level
		  else if(line.startsWith(Params.LVL_HEIGHT) ) 
		  {
		    levelHeight = Integer.parseInt(line.substring(line.indexOf("=")+1));
		    levelHeightOk = true;
		    Debug.println(DebugNames.LOW_PRIORITY,"   levelHeight "+levelHeight);
		  }
		  // On recupère l'image de fond du level
		  else if(line.startsWith(Params.LVL_BGIMAGE) ) 
		  {
		    levelBgImage = line.substring(line.indexOf("=") + 1);
		    levelBgImageOk = true;
		    Debug.println(DebugNames.LOW_PRIORITY,"   bgimage "+levelBgImage);
		  }
		  // On recupère l'image de premier plan du level
		  else if(line.startsWith(Params.LVL_FGIMAGE) ) 
		  {
		    levelFgImage = line.substring(line.indexOf("=") + 1);
		    levelFgImageOk = true;
		    Debug.println(DebugNames.LOW_PRIORITY,"   fgimage "+levelFgImage);
		  }
		  // On recupère le timer du level
		  else if(line.startsWith(Params.LVL_TIMER) ) 
		  {
		    timer = Integer.parseInt(line.substring(line.indexOf("=")+1));
		    timerOk = true;
		    Debug.println(DebugNames.LOW_PRIORITY,"   timer "+timer);
		  }
		  // On recupère la matrice du level
		  else if(line.startsWith(Params.LVL_MATRIX) )
		  {
		    if(levelWidthOk && levelHeightOk) 
		    {
			  levelMatrix = new int[levelWidth][levelHeight];
			  for(int i = 0; i < levelHeight ; i++ )
			  {
			    line = reader.readLine();
			    StringTokenizer st = new StringTokenizer(line, ",");
			    for (int j = 0 ; j < levelWidth ; j++) 
			    {
				  levelMatrix[j][i] = Integer.parseInt(st.nextToken());
			    }
			  }
			  levelMatrixOk = true;
		    }  
		  }
		  // On recupère les infos du hero
		  else if(line.startsWith(Params.LVL_HERO) )
		  {
		    line = line.substring(line.indexOf("=") + 1);
		    StringTokenizer st = new StringTokenizer(line, ":");
		    int cpt=0;
		    heroInfo = new Vector(4);
		    heroInfo.setSize(4);
		    while(st.hasMoreTokens()) 
		    {
			  heroInfo.setElementAt(st.nextToken(),cpt);
			  cpt++;
		    }
		    heroInfoOk = true;
		  }
		  // On recupère la liste des ennemies
		  else if(line.startsWith(Params.LVL_ENEMIES) )
		  {
		    line = line.substring(line.indexOf("=") + 1);
		    StringTokenizer st = new StringTokenizer(line, ",");
		    enemiesList = new Vector();
		    while(st.hasMoreTokens()) 
		    {
			  String tmp = st.nextToken();
			  StringTokenizer stmp = new StringTokenizer(tmp, ":");
			  String n = stmp.nextToken();
			  int x = Integer.parseInt(stmp.nextToken());
			  int y = Integer.parseInt(stmp.nextToken());
			  int o = Integer.parseInt(stmp.nextToken());
			  enemiesList.add(morphByRace(n,x,y,o));
		    }
		    enemiesListOk = true;
		  }
		  // On recupère la liste des items
		  else if(line.startsWith(Params.LVL_ITEM) )
		  {
		    line = line.substring(line.indexOf("=") + 1);
		    StringTokenizer st = new StringTokenizer(line, ",");
		    itemList = new Vector();
		    while(st.hasMoreTokens()) 
		    {
			  String tmp = st.nextToken();
			  StringTokenizer stmp = new StringTokenizer(tmp, ":");
			  String n = stmp.nextToken();
			  int x = Integer.parseInt(stmp.nextToken());
			  int y = Integer.parseInt(stmp.nextToken());
			  int w = Integer.parseInt(stmp.nextToken());
			  int h = Integer.parseInt(stmp.nextToken());
			  itemList.add(new Item(n,x,y,w,h,extractType(n)));
		    }
		    itemListOk = true;
		  }
		  // On recupère la musique à jouer
		  else if(line.startsWith(Params.LVL_MUSIC) )
		  {
		    levelMusic = line.substring(line.indexOf("=") + 1);
		    levelMusicOk = true;
		    Debug.println(DebugNames.LOW_PRIORITY,"   music "+levelMusic);
		  }
	    }
	    reader.close();
	  }
	  catch(IOException ioe)
	  {
	    ioe.printStackTrace();
	  }

	  // On a récupéré toutes les info, on tente de creer le niveau
	  if(levelNameOk && levelBgImageOk && levelMatrixOk && heroInfoOk && enemiesListOk && itemListOk && timerOk) 
	  {
 	    Level level = new Level(levelName, levelBgImage, (levelFgImageOk? levelFgImage:null), (levelMusicOk? levelMusic:null), mp);
	    level.setMatrix(levelMatrix, levelWidth, levelHeight);
	    level.setHeroInfo(heroInfo);
	    level.setEnemiesList(enemiesList);
	    level.setItemList(itemList);
	    level.setTimer(timer);
	    Debug.println(DebugNames.HIGH_PRIORITY,"<- loadLevel ok");
	    return level;
	  }	
	  Debug.println(DebugNames.HIGH_PRIORITY,"<- loadLevel failed");
	  return null;
    }

    /**
     * storeLevel - Permet d'enregistrer un level
     * @param level Niveau à enregistrer
     * @param name Nom du level (levelN ou n est numerique)
     * @return boolean true si OK, false sinon
     */
    public static boolean storeLevel(Level level, String name)
    {
	  levelName = level.getName();
	  levelWidth = level.getWidth();
	  levelHeight = level.getHeight();
	  levelBgImage = level.getBgImage();
	  levelMatrix = level.getMatrix();
	  heroInfo = level.getHeroInfo();
	  enemiesList = level.getEnemiesList();
	  itemList = level.getItemList();
	
	  fileName = "levels/" + name + ".lvl";  
	  try
	  {
	    writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fileName)));
	    DateFormat df = DateFormat.getDateInstance();
	    writer.println("Toweld - "+ df.format(new Date()));
	    writer.println();
	    writer.println(" *** DO NOT EDIT *** ");
	    writer.println();
	    writer.println(Params.LVL_NAME+"="+levelName);
	    levelNameOk = true;
	    writer.println(Params.LVL_WIDTH+"="+levelWidth);
	    levelWidthOk = true;
	    writer.println(Params.LVL_HEIGHT+"="+levelHeight);
	    levelHeightOk = true;
	    writer.println(Params.LVL_BGIMAGE+"="+levelBgImage);
	    levelBgImageOk = true;
	    writer.println(Params.LVL_MATRIX);
	    int i = 0, j = 0;
	    for(i = 0 ; i < levelHeight ; i++)
	    {
		  for(j = 0 ; j < levelWidth ; j++)
		  {
		    writer.print(levelMatrix[j][i] + ",");
		  }	
		  writer.println();
	    }  
	    levelMatrixOk = true;
	    writer.print(Params.LVL_HERO+"=");
	    for(int k=0;k<4;k++) writer.print((String)heroInfo.get(k)+":");
	    writer.println();
	    heroInfoOk = true;
	    writer.print(Params.LVL_ENEMIES+"=");
	    Enemy enemy = null;
	    for(Enumeration e=enemiesList.elements();e.hasMoreElements();)
	    {
		  enemy = (Enemy)e.nextElement();
		  writer.print(enemy.getName()+":");
		  writer.print(enemy.getX()+":");
		  writer.print(enemy.getY()+":");
		  writer.print(enemy.getOrientation()+",");
	    } 
	    writer.println();
	    enemiesListOk = true;
	    writer.println("EOF.");
	    writer.close();
	  }catch(IOException ioe)
	  {
	    ioe.printStackTrace();
	  }
	  if(levelNameOk && levelWidthOk && levelHeightOk && levelBgImageOk && levelMatrixOk ) return true;
	  return false;
    }

    /**
     * extractType - Détermination du type d'item
     * @param s Item au format chaine de caractère "item_n:x:y:w:h"
     * @return int Le type d'item (Cf ItemNames)
     */
    private static int extractType(String s)
    {
	  String type = s.substring(0,s.lastIndexOf("_"));
	  if(type.equals(ItemNames.CDROM_IMG)) return ItemNames.CDROM_ID;
	  else if (type.equals(ItemNames.HEART_IMG)) return ItemNames.HEART_ID;
	  else if (type.equals(ItemNames.FISH_IMG)) return ItemNames.FISH_ID;
	  return ItemNames.UNDEFINED;
    }

    /**
     * morphByRace - Creation d'un Objet Ennemy suivant la race
     * @param n Nom de l'ennemy
     * @param x Position X
     * @param y Position Y
     * @param o Orientation
     * @return Enemy l'objet Ennemi
     */
    private static Enemy morphByRace(String n,int x,int y,int o)
    {
	  String race = n.substring(0,n.lastIndexOf("_"));
	  if ( race.equals(Monsters.XBILL) ) return new XBill(n,x,y,o);
	  else if ( race.equals(Monsters.WINPC) ) return new Winpc(n,x,y,o);
	  else if ( race.equals(Monsters.VIRUS) ) return new Virus(n,x,y,o);
	  else return new Enemy(n,x,y,o);
    }

    /**
     * reset - remise à zero pour initailiser un nouveau chargement
     */
    private static void reset()
    {
	  reader = null;
	  writer = null;
	  fileName = null;
	  levelName = null;
	  levelWidth = 0;
	  levelHeight = 0;
	  levelBgImage = null;
	  levelFgImage = null;
	  levelMatrix = null;
	  levelMusic = null;
	  heroInfo = null;
	  enemiesList = null;
	  itemList = null;
	  timer = 0;
	  levelNameOk = false;
	  levelWidthOk = false;
	  levelHeightOk = false;
	  levelBgImageOk = false;
	  levelMatrixOk = false;
	  levelMusicOk = false;
	  heroInfoOk = false;
	  enemiesListOk = false;
	  itemListOk = false;
	  timerOk = false;
    }
}



