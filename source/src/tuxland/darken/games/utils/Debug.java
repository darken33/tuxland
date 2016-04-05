/*  
 *  Tuxland - Jeu de plateforme en Java (tm)
 *  Copright (c) 2005 Philippe Bousquet (Darken33@free.fr)
 * 
 *  ---------------------------------------------
 *  IMPORTANT : 
 *    Ce jeu est bas� sur le jeu Zlob 
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
 * Debug - Classe permettant de g�rer l'ecriture de messages de debugs
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class Debug implements DebugNames
{
    /** Niveau de DEBUG (Cf DebugNames) */	
    private static int debugLevel = SHOW_ALL;
    
    /**
     * setDebugLevel - Cahnger le niveau de debug
     * @param l Nouveau niveau (Cf DebugNames)
     */
    public static void setDebugLevel(int l)
    {
	  debugLevel = l;
    }
    
    /**
     * println - permet d'afficher un message de faible priorit�
     * @param s Message � afficher
     */
    public static void println(String s)
    {
  	   if ( debugLevel == SHOW_ALL ) System.out.println(s);
    }

    /**
     * println - permet d'afficher un message de faible priorit�
     * @param o Classe ratach� au message
     * @param s Message � afficher
     */
    public static void println(Object o ,String s)
    {
	  if ( debugLevel == SHOW_ALL ) System.out.println(o.getClass().getName()+": "+s);
    }

    /**
     * println - permet d'afficher un message en pr�cisant sa priorit�
     * @param priority Priorit� du message
     * @param s Message � afficher
     */
    public static void println(int priority,String s)
    {
	  if ( priority < debugLevel ) System.out.println(s);
    }

    /**
     * println - permet d'afficher un message en y donnant sa priorit�
     * @param priority Priorit� du message (Cf DebugNames)
     * @param o Classe ratach� au message
     * @param s Message � afficher
     */
    public static void println(int priority, Object o ,String s)
    {
	  if ( priority < debugLevel ) System.out.println(o.getClass().getName()+": "+s);
    }

    /**
     * println - permet d'afficher une nouvelle ligne de faible priorit�
     */
    public static void println(){System.out.println();}

    /**
     * print - permet d'afficher un message de faible priorit� sans retour � la ligne
     * @param s Message � afficher
     */
    public static void print(String s)
    {
	  if ( debugLevel == SHOW_ALL ) System.out.print(s);
    }
    
    /**
     * print - permet d'afficher un message de faible priorit� sans retour � la ligne
     * @param o Classe ratach� au message
     * @param s Message � afficher
     */
    public static void print(Object o ,String s)
    {
	  if ( debugLevel == SHOW_ALL ) System.out.print(o.getClass().getName()+": "+s);
    }
    
    /**
     * print - permet d'afficher un message en pr�cisant sa priorit� sans retour � la ligne
     * @param priority Priorit� du message
     * @param s Message � afficher
     */
    public static void print(int priority,String s)
    {
	  if ( priority < debugLevel ) System.out.print(s);
    }
    
    /**
     * print - permet d'afficher un message en y donnant sa priorit� sans retour � la ligne
     * @param priority Priorit� du message (Cf DebugNames)
     * @param o Classe ratach� au message
     * @param s Message � afficher
     */
    public static void print(int priority, Object o ,String s)
    {
	  if ( priority < debugLevel ) System.out.print(o.getClass().getName()+": "+s);
    }
}
