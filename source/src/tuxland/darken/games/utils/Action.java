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
 * Action - Definitions des différents actions des personnages
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public interface Action
{
	/** Action REPOS */
    public final static int REST = 0;
	/** Action MARCHER */
    public final static int WALK = 2;
	/** Action COURRIR */
    public final static int RUN = 4;
	/** Action SAUTER */
    public final static int JUMP = 6;
	/** Action TOMBER */
    public final static int FALL = 8;
	/** Action SE PROTEGER */
    public final static int SQUAT = 10;
	/** Action MOURIR */
    public final static int DEAD = 12;
    
	/** Action VERS LA GAUCHE */
    public final static int LEFT = 0;
	/** Action VERS LA DROITE */
    public final static int RIGHT = 1;
    
	/** Action REPOS : Nb Images */
    public final static int REST_NB = 12;
	/** Action MARCHER : Nb Images */
    public final static int WALK_NB = 5;
	/** Action COURRIR : Nb Images */
    public final static int RUN_NB = 5;
	/** Action SAUTER : Nb Images */
    public final static int JUMP_NB = 5;
	/** Action TOMBER : Nb Images */
    public final static int FALL_NB = 6;
	/** Action SE PROTEGER : Nb Images */
    public final static int SQUAT_NB = 8;
	/** Action MOURIR : Nb Images */
    public final static int DEAD_NB = 10;
  
	/** Action REPOS : Images */
    public final static String REST_IMG = "rest";  
	/** Action MARCHER : Images */
    public final static String WALK_IMG = "walk";
	/** Action COURRIR : Images */
    public final static String RUN_IMG = "run";
	/** Action SAUTER : Images */
    public final static String JUMP_IMG = "jump";
	/** Action TOMBER : Images */
    public final static String FALL_IMG = "fall";
	/** Action SE PROTEGER : Images */
    public final static String SQUAT_IMG = "squat";
	/** Action MOURIR : Images */
    public final static String DEAD_IMG = "dead";
    
	/** Action REPOS : Delai entre chaque Image */
    public final static int REST_DELAY = 75; 
	/** Action MARCHER : Delai entre chaque Image */
    public final static int WALK_DELAY = 100;
	/** Action COURRIR : Delai entre chaque Image */
    public final static int RUN_DELAY = 50;
	/** Action SAUTER : Delai entre chaque Image */
    public final static int JUMP_DELAY = 75;
	/** Action TOMBER : Delai entre chaque Image */
    public final static int FALL_DELAY = 100;
	/** Action SE PROTEGER : Delai entre chaque Image */
    public final static int SQUAT_DELAY = 100;
	/** Action MOURIR : Delai entre chaque Image */
    public final static int DEAD_DELAY = 250;

	/** Extension pour les images */
    public final static String GIF = ".gif";
    
	/** Definition de toutes les actions */
    public final static String[][] ALL = {
	{ String.valueOf(REST),  REST_IMG,  String.valueOf(REST_NB),  String.valueOf(REST_DELAY)  },
	{ String.valueOf(WALK),  WALK_IMG,  String.valueOf(WALK_NB),  String.valueOf(WALK_DELAY)  },
	{ String.valueOf(RUN),   RUN_IMG,   String.valueOf(RUN_NB),   String.valueOf(RUN_DELAY)   },
	{ String.valueOf(JUMP),  JUMP_IMG,  String.valueOf(JUMP_NB),  String.valueOf(JUMP_DELAY)  },
	{ String.valueOf(FALL),  FALL_IMG,  String.valueOf(FALL_NB),  String.valueOf(FALL_DELAY)  },
	{ String.valueOf(SQUAT), SQUAT_IMG, String.valueOf(SQUAT_NB), String.valueOf(SQUAT_DELAY) },
	{ String.valueOf(DEAD),  DEAD_IMG,  String.valueOf(DEAD_NB),  String.valueOf(DEAD_DELAY)  }
    };
    
    public final static int ID = 0;
    public final static int IMG = 1;
    public final static int NB = 2;
    public final static int DELAY = 3;
}

