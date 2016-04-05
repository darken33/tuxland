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
 * SoundNames - Noms et IDs des Sons et Musiques du jeu
 * @author Philippe Bousquet (Darken33@free.fr)
 * @version 0.1.0
 */
public interface SoundNames
{

	/** Nombre maximum de sons et de musiques du jukebox */ 
	public final int NB_MAX = 10;
	
	/** Son à jouer lorsque tux attrape un CD */ 
    public final static String SND_CDROM = "blop.wav";
    /** Son à jouer lorsque tux attrape un Item */ 
    public final static String SND_ITEM = "item.wav";
    /** Son à jouer lorsque le joueur clique sur un bouton */ 
    public final static String SND_MENU = "beep.wav";
    /** Son à jouer lorsque tux perd une vie */     
    public final static String SND_DIE = "die.wav";   
    /** Son à jouer lorsque tux fini un niveau */     
    public final static String SND_WELLDONE = "gong.wav";
    /** Son à jouer lorsque tux attrape un Item */ 
    public final static String SND_JUMP = "jump.wav";
    /** Son à jouer lorsque tux attrape un Item */ 
    public final static String SND_POINT = "point.wav";
    /** Son à jouer lorsque le timer tombe à zero */ 
    public final static String SND_TIMEOUT = "time_out.wav";

    public final static int SND_CDROM_ID = 0;
    public final static int SND_ITEM_ID = 1;
    public final static int SND_MENU_ID = 2;
    public final static int SND_DIE_ID = 3;
    public final static int SND_WELLDONE_ID = 4;
    public final static int SND_JUMP_ID = 5;
    public final static int SND_POINT_ID = 6;
    public final static int SND_TIMEOUT_ID = 7;

    /** Musique à jouer lorsque l'on se trouve dans les menus */ 
    public final static String MUS_MENU = "menu.wav";
    public final static int MUS_MENU_ID = 8;
    
    /** Identifiant de la musique à jouer pendant un niveau */
    public final static int MUS_LEVEL_ID = 9;
}







