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

/**
 * StatusNames - Noms et IDs des images utilisées pour la barre de Status
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public interface StatusNames
{
	/** Paramètres pour l'image de fond */
    public final static int BG_ID = 0;
    public final static String BG_IMG = "bg";
    public final static int BG_NB = 1;
    public final static int BG_DELAY = 0; 
    public final static int BG_X = 0;
    public final static int BG_Y = 0;    

	/** Paramètres pour les images de vies */
    public final static int LIVES_ID = 1;
    public final static String LIVES_IMG = "lives";
    public final static int LIVES_NB = 3;
    public final static int LIVES_DELAY = 0; 
    public final static int LIVES_X = 154;
    public final static int LIVES_Y = 9;  

	/** Paramètres pour les chiffres de CD récoltés */
    public final static int CDROM_ID = 2;
    public final static String CDROM_IMG = "anim_cdrom";
    public final static int CDROM_NB = 11;
    public final static int CDROM_DELAY = 0; 
    public final static int CD_COLLECTED_X = 325;
    public final static int CD_COLLECTED_Y = 0;  
    public final static int CD_TOTAL_X = 364;
    public final static int CD_TOTAL_Y = 13;
    
	/** Paramètres pour l'animation de la BOMBE */
    public final static int BOMB_ID = 3;
    public final static String BOMB_IMG = "bomb";
    public final static int BOMB_NB = 31;
    public final static int BOMB_DELAY = 120; 
    public final static int BOMB_X = 0;
    public final static int BOMB_Y = 5;

	/** Paramètres pour le texte "Timer" */
    public final static int TIME_ID = 4;
    public final static String TIME_IMG = "time";
    public final static int TIME_NB = 1;
    public final static int TIME_DELAY = 0; 
    public final static int TIME_X = 2;
    public final static int TIME_Y = 14;

	/** Paramètres pour les chiffres du Timer */
    public final static int DIGIT_ID = 5;
    public final static String DIGIT_IMG = "digits_red";
    public final static int DIGIT_NB = 10;
    public final static int DIGIT_DELAY = 0; 
    public final static int DIGIT1_X = 73;
    public final static int DIGIT1_Y = 13;  
    public final static int DIGIT2_X = 93;
    public final static int DIGIT2_Y = 13;  
    public final static int DIGIT3_X = 113;
    public final static int DIGIT3_Y = 13;  
}