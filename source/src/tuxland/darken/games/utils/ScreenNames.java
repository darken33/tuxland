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

import java.awt.Rectangle;

/**
 * SreenNames - Parametrage pour les ecrans du jeu
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org)
 * @version 0.1.0
 */
public interface ScreenNames
{
	/** Ecran BLANC */
    public final static int BLANK = -1;
	/** Ecran de TITRE */
    public final static int TITLE = 0;
	/** Ecran de CHARGEMENT */
    public final static int LOADING = 1;
	/** Ecran de JEU */
    public final static int PLAYING = 2;
	/** Ecran de CONTINUE */
    public final static int CONTINUE = 3;
	/** Ecran de GAMEOVER */
    public final static int GAMEOVER = 4;
	/** Ecran de FIN DE NIVEAU */
    public final static int WELLDONE = 5;
	/** Ecran de JEU EN PAUSE */
    public final static int PAUSED = 6;
	/** Ecran de PRE-CHARGEMENT */
    public final static int PRELOADING = 7;
	/** Ecran de PRE-CHARGEMENT */
    public final static int INTRO = 8;
	/** Ecran de PRE-CHARGEMENT */
    public final static int TOBECONTINUED = 9;
	/** Ecran d'Aide 1 */
    public final static int HELP1 = 10;
	/** Ecran d'Aide 2 */
    public final static int HELP2 = 11;
	/** Ecran d'Aide 3 */
    public final static int HELP3 = 12;
	/** Ecran d'Aide 4 */
    public final static int CREDITS = 13;

	/** Repertoire des images des écran du jeu */
    public final static String SCREEN_IMG_DIR = Params.IMG_DIR+java.io.File.separator+"screen"+java.io.File.separator;
	
	/** Image pour le Titre */
    public final static String TITLE_IMG = "title.gif";
	/** Image de Titre vierge*/
    public final static String TITLE_EMPTY_IMG = "titre_vierge.gif";
    /** Image d'Intro */
    public final static String INTRO_TITLE_IMG = "intro_title.jpg";   
    /** Image to be continued */
    public final static String TOBECONTINUED_IMG = "tobecontinued.jpg";   
    /** Image d'aide 1 */
    public final static String HELP1_IMG = "help1.gif";
    /** Image d'aide 2 */
    public final static String HELP2_IMG = "help2.gif";
    /** Image d'aide 3 */
    public final static String HELP3_IMG = "help3.gif";

    /** Image pour les Fontes */
   // public final static String FONT_IMG = "font.gif";
    
    /** Image de Pause */
    public final static String PAUSE_IMG = "pause.gif";
    
    public final static int PAUSE_ID = 0;
    public final static int FONT_ID = 1;
    public final static int TITLE_ID = 0;
    public final static int TITLE_EMPTY_ID = 2;
    public final static int INTRO_TITLE_ID = 3;
    public final static int TOBECONTINUED_ID = 4;
    public final static int HELP1_ID = 5;
    public final static int HELP2_ID = 6;
    public final static int HELP3_ID = 7;
    
    /** Definition Bouton Intro */
    public final static Rectangle INTRO_BTN = new Rectangle(8,118,66,29); 
    /** Definition Bouton Play */
    public final static Rectangle PLAY_BTN = new Rectangle(8,144,62,30); 
    /** Definition Bouton Credits */
    public final static Rectangle CREDITS_BTN = new Rectangle(8,172,83,26); 
    /** Definition Bouton Help */
    public final static Rectangle HELP_BTN = new Rectangle(8,199,68,27); 
    /** Definition Bouton Quit */
    public final static Rectangle QUIT_BTN = new Rectangle(8,226,61,25); 
    /** Definition Bouton Menu */
    public final static Rectangle BACK_TO_TITLE_BTN = new Rectangle(337,269,62,28); 
    /** Definition Bouton Next */
    public final static Rectangle GO_TO_NEXT_STAGE_BTN = new Rectangle(5,269,60,30);; 
    /** Definition Bouton Retry */
    public final static Rectangle TRY_AGAIN_BTN = new Rectangle(5,269,70,30);
}







