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

import java.awt.Dimension;

/**
 * Matrix - Classe permettant de gérer la matrice d'un niveau
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class Matrix
{
    protected int height=0;
    protected int width=0;
    protected int[][] matrix;

    public Matrix(){}

    /**
     * Matrix - Constructeur
     * @param w Largeur du tableau
     * @param h hateur du tableau
     */
    public Matrix(int w, int h)
    {
	  height = h;
	  width = w;
	  matrix = new int[width][height];
    }

    /**
     * Matrix - Constructeur a partir d'une autre matrice
     * @param m matrice
     * @param w largeur
     * @param h hauteur
     */
    public Matrix(int [][] m, int w, int h)
    {
	  this(w,h);
	  matrix = m;
    }

    /**
     * getHeight - renvoyer la hauteur du tableau
     * @return int - hauteur de la matrice 
     */
    public int getHeight()
    {
	  return height;
    }
    
    /**
     * setHeight - allouer une nouvelle hauteur
     * @param h nouvelle hauteur
     */
    public void setHeight(int h)
    {
	  height = h;
    }

    /**
     * getWidth - renvoyer la largeur du tableau
     * @return int - largeur de la matrice
     */
    public int getWidth(){
	  return width;
    }

    /**
     * setWidth - Affecter une nouvelle largeur pour le niveau
     * @param w largeur du tableau
     */
    public void setWidth(int w)
    {
	  width = w;
    }

    /**
     * getDimension - renvoyer les dimensions du tableau
     * @return Dimension - Les dimension de la matrice
     */
    public Dimension getDimension()
    {
	  return new Dimension(width, height);
    }

    /**
     * setDimension - affecter de nouvelles dimensions au tableau
     * @param d Les dimensions de la matrice
     */
    public void setDimension(Dimension d)
    {
	  width = d.width;
	  height = d.height;
    }

    /**
     * setMatrix - Affecter à partir d'une autre matrice
     * @param m matrice
     * @param w largeur
     * @param h hauteur
     */
    public void setMatrix(int [][] m, int w, int h)
    {
	  width = w;
	  height = h;
	  matrix = m;
    }
    
    /**
     * getMatrix - Renvoie la matrice
     * @return int[][] La matrice
     */
    public int[][] getMatrix()
    {
	  return matrix;
    }

    /**
     * getValue - Renvoyer l'element de la matrice en pos X, Y
     * @param x position sur X
     * @param y position sur Y
     * @return int élement de la matrice (cf MkLvlConst)
     */
    public int getValue(int x, int y)
    {
	  return matrix[x][y];
    }

    /**
     * setValue - Affecter un nouvel element à la matrice en pos X,Y
     * @param x position sur X
     * @param y position sur y
     * @param value element (cf MkLvlConst)
     */
    public void setValue(int x, int y, int value)
    {
	  matrix[x][y] = value;
    }
}












