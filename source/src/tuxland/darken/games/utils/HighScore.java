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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * HighScore - Gestion du tableau des highscore
 * @author Philippe Bousquet (Darken33@free.fr)
 * @version 0.1.0
 */
public class HighScore 
{
	private String users[];
	private int levels[];
    private int scores[];
    private int curent;
   
    /**
     * HighScore - Constructeur
     */
    public HighScore()
    {
    	users=new String[Params.MAX_HS];
    	scores=new int[Params.MAX_HS];
    	levels=new int[Params.MAX_HS];
    	curent=-1;
    	for (int i=0; (i<Params.MAX_HS);i++)
    	{
    		users[i]="";
    		scores[i]=0;
    		levels[i]=0;
    	}
    	load();
    }
    
    /**
     * load - Permet de charger le fichier des highscore
     * @return boolean true si ok, false si le fichier n'a pas été chargé
     */
    public boolean load()
    {
    	String fileName = "scores.sc";  
        BufferedReader reader;
        int i=0;
    	try
		{
    	    reader = new BufferedReader(new FileReader(fileName));
    	    String line;
    	    while ((line = reader.readLine()) != null && i<Params.MAX_HS) 
    	    {
    	      String [] t = line.split(":::");
    	      users[i]= t[0]; 
    	      levels[i]= (new Integer(t[1])).intValue(); 
    	      scores[i]= (new Integer(t[2])).intValue();
    	      i++;
    	    }
    	    reader.close();
		}
    	catch (Exception e)
		{
    		return false;
		}
    	return true;
    }

    /**
     * save - Permet de sauvegarder le fichier des highscores 
     * @return boolean true si ok, false si le fichier n'a pas été enregistré
     */
    public boolean save()
    {
    	String fileName = "scores.sc";
    	PrintWriter writer;
    	try
		{
    	    writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(fileName)));
    	    for (int i=0; i<Params.MAX_HS;i++)
    	    {
    	      writer.println(getUser(i)+":::"+getLevel(i)+":::"+getScore(i));
    	    }
    	    writer.close();
		}
    	catch (Exception e)
		{
    		return false;
		}
    	return true;
    }

    /**
     * update - permet de mettre à jour le tableau des highscores
     * @param user Code utilisateur du joueur
     * @param lvl Niveau qu'il a atteint
     * @param score Score du joueur
     * @return int Son rang dans le tableau des highscores (-1 s'il n'entre pas dans le tableau)
     */
    public int update(String user, int lvl, int score)
    {
    	int i=0;
    	for (i=0; (i<Params.MAX_HS) && (scores[i]>=score);i++);
    	if (i<Params.MAX_HS)
    	{
    		for (int j=Params.MAX_HS-1; j>i; j--) 
    		{
    		  users[j]=users[j-1];
    		  levels[j]=levels[j-1];
    		  scores[j]=scores[j-1];
    		}
    		users[i]=user;
    		levels[i]=lvl;
    		scores[i]=score;
    		save();
        	curent=i;
    		return i;
    	}
    	curent=-1;
    	return -1;
    }

    /**
     * getCurent - Récupère le rang du joueur dans le tableau 
     * @return int Rang du joueur
     */
    public int getCurent()
    {
    	return curent;
    }

    /**
     * getUser - Récupère le nom du joueur pour un rang donné
     * @param i Rang à consulter
     * @return String Nom du joueur
     */
    public String getUser(int i)
    {
    	return users[i];
    }

    /**
     * getLevel - Récupère le niveau atteint par le joueur pour un rang donné
     * @param i Rang à consulter
     * @return int Niveau atteint par le joueur
     */
    public int getLevel(int i)
    {
    	return levels[i];
    }

    /**
     * getScore - Récupère le score du joueur pour un rang donné 
     * @param i Rang à consulter
     * @return int Score du joueur
     */
    public int getScore(int i)
    {
    	return scores[i];
    }
}
