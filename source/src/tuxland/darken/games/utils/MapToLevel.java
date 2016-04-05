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

import java.awt.*;
import java.awt.image.*;
import java.text.DateFormat;
import java.util.Date;
import java.io.*;

public class MapToLevel {
   
    private static void convert(String name){
	String file = name.substring(0,name.length()-3) + "lvl";
	convert(name,file);
    }

    private static void convert(String name,String file){
	System.out.println("MapToLevel.convert("+name+","+file+")");
	PrintWriter writer=null;
	
	try{
	    writer = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
	}catch(FileNotFoundException e){System.err.println("File not found");}
	
	Frame f = new Frame();
	MediaTracker mt = new MediaTracker(f);
	Image img = f.getToolkit().getImage(name);
	mt.addImage(img,0);
	try{
	    mt.waitForID(0);
	}catch(InterruptedException e){System.err.println("le loadage de l'image a echoue");}
	
	int w = img.getWidth((ImageObserver)f)/4;
	int h = img.getHeight((ImageObserver)f)/4;
	int i, j;
	PixelGrabber pg;
	int [] pixels = null;
	int k=0;
	DateFormat df = DateFormat.getDateInstance();
	writer.println("Toweld - "+ df.format(new Date()));
	writer.println();
	writer.println(" *** DO NOT EDIT *** ");
	writer.println();
	writer.println("name=");
	writer.println("width="+w);
	writer.println("height="+h);
	writer.println("bg=");
	writer.println("timer=");
	writer.println("matrix");
	for(i=0 ; i < h ; i++){
	    for(j=0 ; j < w ; j++){
		pg = new PixelGrabber(img, j*4+2, i*4+2, 1, 1, pixels, 0, 1);
		try{
		    while(! pg.grabPixels() ) ;
		}catch(InterruptedException ie){
		    ie.printStackTrace();
		}
		pixels = (int[]) pg.getPixels();
		if(pixels[0]==-1) writer.print("0");
		else writer.print("1");
		writer.print(",");
	    }
	    writer.println("");
	}  
	
	writer.println("hero=");
	writer.println("enemies=");
	writer.println("item=");
	writer.println("EOF.");
	
	writer.close();
	
	System.out.println("Operation completed.");
    }
       
    public static void main(String args[]){
	switch(args.length){
	case 1:  MapToLevel.convert(args[0]);
	    break;
	case 2:  MapToLevel.convert(args[0],args[1]); 
	    break;
	default: 
	    System.err.println("Syntaxe: MapToLevel <level.map> [<level.lvl>]");
	    System.exit(-1);
	    break;
	}
	
	System.exit(0);
    }

}
