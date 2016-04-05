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
import java.awt.event.*;

public class MakeLevel extends Frame implements ItemListener, ActionListener, MouseListener, WindowListener, MkLvlConst{
    private TextField levelNameField = new TextField(20);
    private Choice levelType = new Choice();
    private TextField levelWidthField = new TextField(3);
    private TextField levelHeightField = new TextField(3);
    private Panel leftPanel = new Panel();
    private Panel rightPanel = new Panel();
    private Panel bottomPanel = new Panel();
    private Panel topPanel = new Panel();
    private ScrollPane levelPanel = new ScrollPane();
    private java.awt.MenuBar menuBar = new java.awt.MenuBar();
    private Menu fileMenu = new Menu("File");
    private MenuItem newItem = new MenuItem("New...");
    private MenuItem loadItem = new MenuItem("Load...");
    private MenuItem saveItem = new MenuItem("Save");
    private MenuItem saveAsItem = new MenuItem("Save as...");
    private MenuItem exitItem = new MenuItem("Exit");
    
    private Menu addMenu = new Menu("Add");
    private MenuItem bgItem = new MenuItem("Background...");
    private MenuItem musicItem = new MenuItem("Music...");
    
    private CheckboxGroup blockChooser = new CheckboxGroup();
    private CheckboxGroup enemyChooser = new CheckboxGroup();
	
    public MakeLevel(){
	super("Make level");
	setSize(500,300);
	setResizable(false);
	setBackground(Color.white);
	setMenuBar(menuBar);
	fileMenu.add(newItem); newItem.addActionListener(this);
	fileMenu.add(loadItem);loadItem.addActionListener(this);
	fileMenu.add(saveItem);saveItem.addActionListener(this);
	fileMenu.add(saveAsItem);saveAsItem.addActionListener(this);
	fileMenu.addSeparator();
	fileMenu.add(exitItem);exitItem.addActionListener(this);
	menuBar.add(fileMenu);fileMenu.addActionListener(this);
       
	addMenu.add(bgItem);
	addMenu.add(musicItem); musicItem.addActionListener(this);
	menuBar.add(addMenu); addMenu.addActionListener(this);
	
	leftPanel.setLayout(new GridLayout(7,1));
	leftPanel.add(new Label("Blocks"));
	leftPanel.add(new Checkbox("floor", blockChooser,true));
	leftPanel.add(new Checkbox("platform left", blockChooser,false));
	leftPanel.add(new Checkbox("platform right", blockChooser,false));
	leftPanel.add(new Checkbox("platform center", blockChooser,false));
	leftPanel.add(new Checkbox("wall", blockChooser,false));
	leftPanel.add(new Checkbox("ceiling", blockChooser,false));
	
	rightPanel.add(new Label("Enemies"));
	//	rightPanel.add(enemyChooser);

	addWindowListener(this);
	leftPanel.addMouseListener(this);
	rightPanel.addMouseListener(this);
	levelPanel.addMouseListener(this);
	levelType.addItemListener(this);

	add("North", topPanel);
	add("West", leftPanel);
	add("Center", levelPanel);
	add("East", rightPanel);
	add("South", bottomPanel);
	
	setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
	Debug.println(DebugNames.NORMAL_PRIORITY,this,"Not yet implemented...");
    }

    public void itemStateChanged(ItemEvent e){;} 

    public void windowActivated(WindowEvent e){;}
    public void windowOpened(WindowEvent e){;}
    public void windowClosed(WindowEvent e){;}
    public void windowClosing(WindowEvent e){System.exit(0);}
    public void windowDeactivated(WindowEvent e){;}
    public void windowDeiconified(WindowEvent e){;} 
    public void windowIconified(WindowEvent e){;}
    
    public void mouseClicked(MouseEvent e) {;}
    public void mouseEntered(MouseEvent e) {;}
    public void mouseExited(MouseEvent e) {;}
    public void mousePressed(MouseEvent e) {;}
    public void mouseReleased(MouseEvent e) {;}
  
    public static void main(String args[]){
	new MakeLevel();
    }

}


