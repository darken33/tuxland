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
 * ItemNames - Parametrage de la classe Item
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public interface ItemNames
{
	/** Répertoire ou l'on trouve les images d'items */
    public final static String ITEM_DIR = Params.IMG_DIR+java.io.File.separator+"item"+java.io.File.separator;
    /** Extension des fichiers images */
    public final static String GIF = ".gif";

    /** Item non défini */
    public final static int UNDEFINED = -1;

    /** Item de type CDROM (Objectif) */
    public final static int CDROM_DELAY = 150; 
    public final static int CDROM_ID = 0;
    public final static String CDROM_IMG = "cdrom";
    public final static int CDROM_NB = 6;

    /** Item de type HEART (Bonus vie) */
    public final static int HEART_DELAY = 150;     
    public final static int HEART_ID = 1;
    public final static String HEART_IMG = "heart";
    public final static int HEART_NB = 4;

    /** Item de type FISH (Bonus point) */
    public final static int FISH_DELAY = 150; 
    public final static int FISH_ID = 2;
    public final static String FISH_IMG = "fish";
    public final static int FISH_NB = 1;
}







