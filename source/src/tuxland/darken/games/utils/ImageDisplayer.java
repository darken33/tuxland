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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.Vector;

/**
 * @author Pascal "Toweld" Bourut (toweld@planetelibre.org)
 *  
 * Gère le découpage d'un gif en BufferedImage, ce qui permet d'acceder de facon 
 * instantanée à une image donnée. L'image a utilise est definie par l'action courante et 
 * l'image courante dans cette action
 */
public class ImageDisplayer {	
    
	/** Component qui recoit les evenements du MediaTracker */
    private Panel panel;
    /** Vecteur qui contient toutes les actions animations */
    private Vector imageVector;
    /** Image en cours d'utilisation */
    private int currentFrame = 0;
    /** Action en cours d'utilisation */
    private int currentAction = 0;

    /** 
     * ImageDisplayer - Constructeur : Initialisation des vecteurs  
     */
    public ImageDisplayer()
    {
	  panel = new Panel();
	  imageVector = new Vector();
	  imageVector.setSize(Action.DEAD+2);
    }
    
    /**
     * addLineOfImage - Ajoute une serie d'image au vecteur qui definissent une nouvelle action
     * @param imagePath Le fichier contenant les images
     * @param numberOfImage Le nombre d'images de l'anim
     * @param startBoucle La première image de la boucle
     * @param delay le temps entre chaque image en ms
     * @param action l'action representée par l'annimation
     * @return Dimension la taille d'une image
     */
    public Dimension addLineOfImage(String imagePath,int numberOfImage, int startBoucle, int delay, int action)
    {
      // On charge le fichier	
	  Image image = Toolkit.getDefaultToolkit().createImage(imagePath);
	  try 
	  {
	    MediaTracker tracker = new MediaTracker(panel);
	    tracker.addImage(image, 0);
	    tracker.waitForID(0);
	  }
	  catch ( Exception e ) {}
	
	  // On prepare la sépartion de chaque image
	  Vector actionVector = new Vector(numberOfImage);
	  int iw = image.getWidth(panel);
	  int ih = image.getHeight(panel);
	  int siw = iw/numberOfImage;
        
	  BufferedImage bimg = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_ARGB);
	  Graphics2D bimgGraphics = bimg.createGraphics();
	  bimgGraphics.drawImage(image,0,0,panel);
	
	  // On sépare chaque image 
	  for(int i = 0; i<numberOfImage ; i++)
	  {			    
	    BufferedImage bi = new BufferedImage(siw, ih, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D big = bi.createGraphics();
	    try 
		{
		  big.drawImage(bimg.getSubimage(i*siw,0,siw,ih),0,0,panel);
	    }
	    catch (RasterFormatException e){}
	    actionVector.add(bi);
	  }
	  
	  // On stocke l'annimation dans imageVector
	  imageVector.setElementAt(new AnimatedImage(actionVector,startBoucle,delay),action);
	  
	  return new Dimension(siw,ih);
    }
    
    
    /**
     * getNextImage - Renvoie l'image suivante dans l'action donnée 
     * @param action Action en cours
     * @return Image l'image à afficher
     */    
    public Image getNextImage(int action) 
    {
	  if(action != currentAction)
	  {
	    if(action<0 || action >= imageVector.size()) currentFrame = 0;
	    else initializeFrameAndAction(action);
	  }
	  return nextImage();
    }	
    
    /**
     * initializeFrameAndAction - Reinitialise l'action courante et l'image courante dans le cas ou l'on change d'action
     * @param action Action courante
     */
    private void initializeFrameAndAction(int action)
    {
	  currentFrame = 0;
	  currentAction = action;
    }
    
    /** 
     * nextImage - Appele par getNextImage pour renvoyer la bonne image
     * @return Image l'image à afficher
     */
    private Image nextImage()
    {
	  if(currentAction <= imageVector.size()-1)
	  {
	    AnimatedImage animatedImage = (AnimatedImage)imageVector.get(currentAction);
	    Vector actionImages = animatedImage.frames;
	    // Si on arrive en fin d'annimation, on reboucle
	    if((currentFrame > actionImages.size()-1) || (currentFrame < 0)) currentFrame = animatedImage.whereToBoucle;
	    
	    // Doit on passer à l'image suivante ?
	    if(animatedImage.next()) return (Image) actionImages.get(currentFrame++);
	    else  return (Image) actionImages.get(currentFrame);
	  }
	  else
	  { 
	    return (Image) ((AnimatedImage)imageVector.get(0)).frames.get(0);
	  }
    }		

    /** 
     * displayNextImage - Dessine l'image suivante dans le graphics a la bonne position
     * @param g graphics où l'on doit dessiner l'image
     * @param x position X
     * @param y position Y
     * @param action action en cours
     */
    public void displayNextImage(Graphics g,int x, int y, int action) 
    {
      // On recupère l'image suivante	
	  Image img = getNextImage(action);
	
	  // On dessine l'image
	  if(img!=null) g.drawImage(img,x,y,panel);
	  // Si nous n'avons pas d'image on dessine un carré noir
	  else 
	  {
	    g.setColor(Color.black);
	    g.drawRect(x,y,Params.SQUARE_SIZE,Params.SQUARE_SIZE);
	  }
    }
    
    /**
     * displayImageNum - Dessine une image specifique dans le graphics a la bonne position
     * @param g graphics où l'on doit dessiner l'image
     * @param x position X
     * @param y position Y
     * @param action action en cours
     * @param index numero de l'image à afficher
     */
    public void displayImageNum(Graphics g,int x, int y, int action, int index) 
    {
	  Image img = (Image) ((AnimatedImage)imageVector.get(action)).frames.get(index);
	  if(img!=null) g.drawImage(img,x,y,panel);
	  else 
	  {
	    g.setColor(Color.black);
	    g.drawRect(x,y,Params.SQUARE_SIZE,Params.SQUARE_SIZE);
	  }
    }
}


