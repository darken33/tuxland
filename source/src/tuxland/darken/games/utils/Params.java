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
 * Params - Paramêtres du Jeu
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org)
 * @version 0.1.0 
 */ 
public interface Params{

	/** Version du jeu */
    public final static String VERSION = "0.1.1";

	/** Repertoires de images */
    public final static String IMG_DIR = "share/images";
	/** Repertoires de images status */
    public final static String STATUS_DIR = "status";
	/** Repertoires de images ecrans */
    public final static String SCREEN_DIR = "screen";
	/** Repertoires des sons et musiques */
    public final static String SND_DIR = "share/sounds";

    /** Balise pour le nom du level */
    public final static String LVL_NAME = "name";
    /** Balise pour la largeur du level */
    public final static String LVL_WIDTH = "width";
    /** Balise pour la hauteur du level */
    public final static String LVL_HEIGHT = "height";
    /** Balise pour l'image de fond du level */
    public final static String LVL_BGIMAGE = "bg";
    /** Balise pour l'image de premier plan du level */
    public final static String LVL_FGIMAGE = "fg";
    /** Balise pour la matrice du level */
    public final static String LVL_MATRIX = "matrix";
    /** Balise pour le hero du level */
    public final static String LVL_HERO = "hero"; // name:x:y:orientation 
    /** Balise pour les enemies du level */
    public final static String LVL_ENEMIES = "enemies"; // name:x:y:orientation,
    /** Balise pour le chronomètre du level */
    public final static String LVL_TIMER = "timer";
    /** Balise pour les items du level */    
    public final static String LVL_ITEM = "item"; // name_n:x:y:w:h
    /** Balise pour la musique du level */
    public final static String LVL_MUSIC = "music";

    /** Nombre maximum de Levels */ 
    public final static int LVL_MAX = 5;
    /** Taille d'un carre */
    public final static int SQUARE_SIZE = 40;
    /** Valeur de la Gravité */
    public final static double GRAVITY = 9.81;
    /** Gauche */
    public final static int LEFT = -1;
    /** Droite */
    public final static int RIGHT = 1;
    /** Centre  */
    public final static int CENTER = 0;
    /** Haut */
    public final static int UP = -1;
    /** Bas */
    public final static int DOWN = 1;
    /** Vitesse de marche */
    public final static int WALK_MAX = 3;
    /** Vitesse de course */
    public final static int RUN_MAX = 5;
    /** Pas de marche */
    public final static int WALK_STEP = 1;
    /** Pas de course */
    public final static int RUN_STEP = 2;
    /** Pas de saut */
    public final static int JUMP_STEP = -5;
    /** saut Maximum */
    public final static int JUMP_MAX = -50;
    /** Vitesse d'un ennemi */
    public final static int ENEMY_WALK_MAX = 1;
    /** Pas d'un ennemi */
    public final static int ENEMY_WALK_STEP = 1;
    /** Pourcentage pour savoir si on tombe d'une platefrome */
    public final static double STABILITY = 0.8; // if STABILITY percent of the sprite is on a platform, sprite stills under, otherwise sprite falldowns
    /** Pourcentage pour savoir si l'on attrape la platfrome en sautant */
    public final static double HEAD_PERCENTAGE = 0.8; // if HEAD_PERCENTAGE percent of the head is ceiling free lets jump!
    public final static double SIDE_PERCENTAGE = 0.85;//0.95
    public final static int REDUCE_FACTOR = (int)(SQUARE_SIZE*0.1);
    public final static int NAME_INDEX = 0;
    public final static int X_INDEX = 1;
    public final static int Y_INDEX = 2;
    public final static int O_INDEX = 3;

    /** Points pour avoir récolté un CDRom */
    public final static int CDROM_SCORE = 100;
    /** Points pour avoir récolté un Poisson */
    public final static int FISH_SCORE = 50;
    /** Points par seconde restantes */
    public final static int TIME_SCORE = 10;
    /** Nombre de lignes de HighScores */
    public final static int MAX_HS = 10;
    
    /** Extension pour le fichiers GIF */  
    public final static String GIF = ".gif";
}











