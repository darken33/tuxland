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
package darken.games.tuxland;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import darken.games.utils.Debug;
import darken.games.utils.DebugNames;
import darken.games.utils.HighScore;
import darken.games.utils.ImageDisplayer;
import darken.games.utils.Params;
import darken.games.utils.ScreenNames;
import darken.games.utils.SoundNames;
import darken.games.utils.SoundPlayer;

/**
 * Tuxland - Jeu de plateforme en Java (tm)
 *  
 *  <br/><strong>Tuxland - Jeu de plateforme en Java (tm)</strong>
 *  <br/>Copright (c) 2005 Philippe Bousquet (Darken33@free.fr)
 *  <br/>
 *  <br/>---------------------------------------------
 *  <br/>IMPORTANT : 
 *  <br/> Ce jeu est basé sur le jeu Zlob 
 *  <br/> Copyright (C) 2001 Pascal "Toweld" Bourut (toweld@planetelibre.org)
 *  <br/>---------------------------------------------
 *  <br/> 
 *  <br/>This program is free software; you can redistribute it and/or modify
 *  <br/>it under the terms of the GNU General Public License as published by
 *  <br/>the Free Software Foundation; either version 2 of the License, or
 *  <br/>(at your option) any later version.
 *  <br/>
 *  <br/>This program is distributed in the hope that it will be useful,
 *  <br/>but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  <br/>MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  <br/>GNU General Public License for more details.
 *  <br/>
 *  <br/>You should have received a copy of the GNU General Public License
 *  <br/>along with this program; if not, write to the Free Software
 *  <br/>Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 * 
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) - Class Zlob.java
 * @version 0.1.0 
 */ 
public class Tuxland extends Frame implements Runnable 
{
    /** largeur de la fenetre */
	private int width = 400;
	/** hauteur de la fennetre */
    private int height = 300;
    /** hauteur da la barr de status */
    private int statusBarWidth = 40;
    /** Thread de l'application */  
    private Thread thread = null;
    /** Partie jouable de l'ecran */
    private GameZone gameZone = null;
    /** Partie status de l'ecran */
    private StatusBar statusBar = null;
    /** ID de l'écran en cours */
    private int screen;
    /** Outil de chargement des images */
    private MediaTracker mt = null;
    /** Outil de gestion des images */
    private ImageDisplayer displayer = null;
    /** L'appli est elle en mode pause ? */
    private boolean pause = false;  
    /** Level en cours */
    private int level = 1;
    /** Nombre de level maximum */
    private int maxLevel = Params.LVL_MAX;
    /** Liste des images */    
    private Image [] images;
    /** Le pré-chargement du jeu a t'il été effectué */
    private boolean preloadDone = false;
    /** La souris est au dessus du bouton "Play" */
    private boolean overPlay = false;
    /** La souris est au dessus du bouton "Intro" */
    private boolean overIntro = false;
    /** La souris est au dessus du bouton "Credits" */
    private boolean overCredits = false;
    /** La souris est au dessus du bouton "Help" */
    private boolean overHelp = false;
    /** La souris est au dessus du bouton "Quit" */
    private boolean overQuit = false;
    /** La souris est au dessus du bouton "Retry" */
    private boolean overTryAgain = false;
    /** La souris est au dessus du bouton "Menu" */
    private boolean overBackToTitle = false;
    /** La souris est au dessus du bouton "Next" */    
    private boolean overGoToNextStage = false;
    /** Image de l'ecran pour le double buffering */ 
    private Image offImage = null;
    /** La partie graphique de l'image */ 
    private Graphics offGraphics = null;
    /** Le jukebox pour les sons et musiques */
    private SoundPlayer jukebox = null;
    /** Le nom du joueur */ 
    private String user = null;
    /** La gestion des HighScores */
    private HighScore hscore = null;
    /** Le message "Game Over" est il affiché ? */   
    private boolean litGameOver;
    /** nb de ligne de highscores à afficher */  
    private int hsline;
    /** Level passé en parametre */
    private static int _level=1;
    private boolean music_play=false;
    /** Le joystick */
    public float J0X=0; 
    public float J0Y=0;
    int J0B=0;
    public boolean J0B1=false; 
    public boolean J0B2=false; 
    public boolean J0B3=false; 
	public boolean useJ0 = false;
	public String oldMusique = "";
    

    /**
     * Tuxland : Constructeur de la classe
     */
    public Tuxland()
    {
      // On prepare la fenetre 
	  super("Tuxland "+Params.VERSION+" by Darken33 (darken33@free.fr)");
	  setSize(width,height);
	  setResizable(false);
	  setBackground(Color.white);
	  setLocation(300, 200);
      music_play=false;

	  // Recuperer le nom de lutilisateur
	  user = System.getProperty("user.name");
	  
	  // On cree la partie HighScore
	  hscore = new HighScore();
	
	  // On prepare le gestionnaire d'images
	  mt = new MediaTracker(this);
	  displayer = new ImageDisplayer();
     
	  // On cree la barre de Status
	  statusBar = new StatusBar(this);
	  statusBar.setSize(width, statusBarWidth);
	  statusBar.setLocation(0,height-statusBarWidth);
	
	  // On creé la partie jouable du jeu
	  gameZone = new GameZone(this);
	  gameZone.setSize(width, height-statusBarWidth);
	  gameZone.setLocation(0,0);
	
	  setLayout(null);

	  // On ajoute les elements à la fenetre principale
	  add(gameZone);
	  add(statusBar);
      
	  // On ajoute le gestionnaire d'évennements sur la souris
	  addMouseListener(new MouseAdapter()
	                       {
		                     public void mouseClicked(MouseEvent e)
		                     {
		                       handleClick(e.getPoint());
		                       e.consume();
		                     }
	                       }
	  );
	  addMouseMotionListener(new MouseMotionAdapter()
	                             {
		                           public void mouseMoved(MouseEvent e)
		                           {
		                             handleMovement(e.getPoint());
		                             e.consume();
		                           }
	                             }
	  );
	  
	  // On ajoute le gestionnaire d'evennement sur la fenetre
	  addWindowListener(new WindowAdapter() 
	  		                { 
		                      public void windowClosing(WindowEvent e)
		                      { 
		                        //setAutoRepeat(true);
		                        System.exit(0);  
		                      }
		                      public void windowOpened(WindowEvent e)
		                      { 
		                        //setAutoRepeat(false);
		                      }
		                      public void windowActivate(WindowEvent e)
		                      { 
		                        //setAutoRepeat(false); 
		                      }
		                      public void windowDeactivate(WindowEvent e)
		                      { 
		                        //setAutoRepeat(true);
		                      }
		                      public void windowIconified(WindowEvent e)
		                      { 
		                        //setAutoRepeat(true);
		                      }
		                      public void windowDeiconified(WindowEvent e)
		                      { 
		                        //setAutoRepeat(false);
		                      }
	                        }
	  );
	  
	  try
	  {
	    useJ0=false;
        //J0 = Joystick.createInstance();
	    //useJ0=true;
	    //J0.addJoystickListener(this);
	  }
	  catch (Exception e)
	  {
	  	System.err.println("Pas de joystick détecté");
	  	useJ0=false;
	  }

	  // On execute le thread
	  thread = new Thread(this);
	  thread.start();
	  
	  // La fenetre est affichée 
	  setVisible(true);
	  onStage(false,false);
	  
	  // On charge les éléments du jeu
 	  preload();
	  
 	  // On affiche l'ecran titre
	  intro();
	
	  // On Met le niveau de Debug Minimum 
	  Debug.setDebugLevel(DebugNames.SHOW_NONE);
    }
    

    /**
     * setAutoRepeat : activer/désactiver l'autorepeat du clavier
     * @param  setOn true=activer, false=desactiver
     */
    private void setAutoRepeat(boolean setOn)
    {
      // On tente d'activer/désactiver l'autorepeat du clavier 
	  Runtime rt = Runtime.getRuntime();
	  String arg=setOn?"on":"off";  
      try
	  {
	    rt.exec("xset r "+arg); 
	  }catch(Exception e)
	  {
	    System.out.println("It's recommended to disable keyboard's autorepeat function to easily play to this game...\n");
	  }
    }

    /**
     * setAutoRepeat : activer/désactiver l'autorepeat du clavier
     * @return int - ID de l'écran
     */
    public int getCurrentScreen()
    {
	  return screen;
    }
   
    /**
     * run : Executer l'application
     */
    public void run()
    {
	  Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
	  while(true)
	  {
	    if(!gameZone.hasFocus()) gameZone.requestFocus();
	    if(offImage==null) offImage = createImage(width,height);
	    // on affiche l'ecran en cours
	    drawScreen();
	    // on fait une pause  pour eviter le blocage de l'utilisteur
	    try
		{ 
		  Thread.sleep(250); 
	    }catch ( InterruptedException e)
		{
		  e.printStackTrace();
	    } 
	  }
    }    
    
    /**
     * getHero : renvoi les infos du personnage Tux
     * @return Hero - Tux
     */
    public Hero getHero()
    {
      return gameZone.getHero();
    }

    /** 
     * loadImage : ajoute une image dans le gestionnaire d'images
     * @param img image à ajouter
     * @param frame nombre d'images pour une anim
     * @param id id de l'image    
     */
    private void loadImage(String img,int frame,int id)
    {
	  if(frame>1)
	  {
	  	// L'image est en fait une animation
	    displayer.addLineOfImage(ScreenNames.SCREEN_IMG_DIR+img,frame,0,200,id);
	  }
	  else
	  {
	    images[id] = Toolkit.getDefaultToolkit().createImage(ScreenNames.SCREEN_IMG_DIR+img);
	    mt.addImage(images[id],id);
	    try { mt.waitForID(id); }catch (Exception e) {;}
	  }
    } 

    /**
     * preload : Chargement des éléments du jeu
     */
    private void preload()
    {
      // Lecran actuel est BLANC	
	  screen = ScreenNames.BLANK;
	
      // On cree la liste des images	
	  images = new Image[16];
	  
	  // On affiche l'image LOADING
	  preloadDone = true;
	  screen = ScreenNames.PRELOADING;

	  // On charge le reste des images
	  loadImage(ScreenNames.INTRO_TITLE_IMG,1,ScreenNames.INTRO_TITLE_ID);
	  loadImage(ScreenNames.TITLE_IMG,1,ScreenNames.TITLE_ID);
	  loadImage(ScreenNames.PAUSE_IMG,2,ScreenNames.PAUSE_ID);
//	  loadImage(ScreenNames.FONT_IMG,36,ScreenNames.FONT_ID);
	  loadImage(ScreenNames.TITLE_EMPTY_IMG,1,ScreenNames.TITLE_EMPTY_ID);
	  loadImage(ScreenNames.INTRO_TITLE_IMG,1,ScreenNames.INTRO_TITLE_ID);
	  loadImage(ScreenNames.TOBECONTINUED_IMG,1,ScreenNames.TOBECONTINUED_ID);
	  loadImage(ScreenNames.HELP1_IMG,1,ScreenNames.HELP1_ID);
	  loadImage(ScreenNames.HELP2_IMG,1,ScreenNames.HELP2_ID);
	  loadImage(ScreenNames.HELP3_IMG,1,ScreenNames.HELP3_ID);
	
	  // On charge les musiques et sons
	  jukebox = new SoundPlayer();
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_CDROM,SoundNames.SND_CDROM_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_ITEM,SoundNames.SND_ITEM_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_MENU,SoundNames.SND_MENU_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_DIE,SoundNames.SND_DIE_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_WELLDONE,SoundNames.SND_WELLDONE_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_JUMP,SoundNames.SND_JUMP_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_POINT,SoundNames.SND_POINT_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.SND_TIMEOUT,SoundNames.SND_TIMEOUT_ID);
	  jukebox.addSound("file:./"+Params.SND_DIR+"/"+SoundNames.MUS_MENU,SoundNames.MUS_MENU_ID);
    }

    /**
     * getJukeBox : Donne acces au jukebox
     * @return SounPlayer - le jukebox sons/musiques
     */
    public SoundPlayer getJukeBox()
    {
    	return jukebox;
    }
    
    /**
     * setStatus : permet d'afficher un message dans la barre de status 
     * @param msg (message à afficher)
     */
    public void setStatus(String msg)
    {
	  statusBar.setText(msg);
    }

    /**
     * startStatusBar : Demarre la barre de Status
     */
    public void startStatusBar()
    {
	  statusBar.start();
    }

    /**
     * getStatusBar : donne acces à la barre de status
     * @return StatusBar - La barre status
     */
    public StatusBar getStatusBar()
    {
	  return statusBar;
    }

    /**
     * onStage : permet d'activer et rndre visible la barre status et la partie jouable 
     * @param v rendre visible/invisible
     * @param a rendre actif/inactif
     */
    public void onStage(boolean v,boolean a)
    { 
	  gameZone.setVisible(v);
	  statusBar.setVisible(v);
	  gameZone.setActive(a);
	  statusBar.setActive(a);
    }

    /**
     * freeGameZone : permet de liberer la memoire de la partie jouable
     */
    public void freeGameZone()
    {
    	gameZone.free();
    }
    
    /**
     * allCDromCollected : A executer lorsque Tux à terminé un level
     */
    public void allCDromCollected()
    {
      // On arrette le jeu	
	  onStage(false,false);
	  freeGameZone();
	  // On affiche l'ecran WELLDONE
	  overGoToNextStage = false;
	  overBackToTitle = false;
	  screen = ScreenNames.WELLDONE;
    }

    /**
     * stopPlaying : A executer lorsque Tux à raté un niveau 
     */
    public void stopPlaying()
    {
      // On arrette le jeu	
      onStage(false,false);
      
 	  overTryAgain = false;
	  overBackToTitle = false;
	  // Si tux possède encore des vies
	  if( gameZone.getHero().getContinue()>0 )
	  {
	  	// On affiche l'ecran CONTINUE
	  	screen = ScreenNames.CONTINUE;
	  }
	  else 
	  {
	  	// On affiche l'ecran GAMEOVER
		hscore.update(user,level,gameZone.getHero().getScore());
		litGameOver = true;
		hsline = 0;
	    screen = ScreenNames.GAMEOVER;
	  }
    }

    /**
     * title : afficher l'ecran de titre
     */
    public void title()
    {
      // On arrette le jeu	
	  onStage(false,false);
	  // On affiche l'ecran TITLE
	  overPlay = false;
	  overIntro = false;
	  overCredits = false;
	  overHelp = false;
	  overQuit = false;
	  screen = ScreenNames.TITLE;
    }

    /**
     * intro : afficher l'ecran d'intro
     */
    public void intro()
    {
      // On arrette le jeu	
	  onStage(false,false);
	  // On affiche l'ecran INTRO
	  overBackToTitle = false;
	  screen = ScreenNames.INTRO;
    }

    /**
     * intro : afficher l'ecran d'aide 1
     */
    public void help1()
    {
      // On arrette le jeu	
	  onStage(false,false);
	  // On affiche l'ecran HELP1
	  overBackToTitle = false;
	  overGoToNextStage = false;
	  screen = ScreenNames.HELP1;
    }

    /**
     * intro : afficher l'ecran de credits
     */
    public void credits()
    {
      // On arrette le jeu	
	  onStage(false,false);
	  // On affiche l'ecran CREDITS
	  overBackToTitle = false;
	  overGoToNextStage = false;
	  screen = ScreenNames.CREDITS;
    }

    /**
     * intro : afficher l'ecran d'aide 2
     */
    public void help2()
    {
      // On arrette le jeu	
	  onStage(false,false);
	  // On affiche l'ecran HELP1
	  overBackToTitle = false;
	  overGoToNextStage = false;
	  screen = ScreenNames.HELP2;
    }

    /**
     * intro : afficher l'ecran d'aide 3
     */
    public void help3()
    {
      // On arrette le jeu	
	  onStage(false,false);
	  // On affiche l'ecran HELP1
	  overBackToTitle = false;
	  screen = ScreenNames.HELP3;
    }

    /**
     * load : permet de charger un niveau
     * @param firstLoad rcharger/ne pas recharger le level
     */
    public void load(boolean firstLoad)
    {
      // On arrette le jeu	
      onStage(false,false);
      // On affiche l'ecran loading
	  screen = ScreenNames.LOADING;
	  drawScreen();
	  // On reinitialise Tux
	  getHero().initSafe();	
	  // On charge le level
	  gameZone.setLevelName("level"+level,firstLoad,(gameZone.getLevel()!=null?gameZone.getLevel().isMusicPlaying():false));
	  // On lance le jeu
	  if (!gameZone.started()) gameZone.start();
	  play();
    }
 
    /**
     * play : on lance la partie
     */
    public void play()
    {
	  onStage(true,true);
	  screen = ScreenNames.PLAYING;
    }
    
    /**
     * togglePause : on met/enleve la pause
     */
    public void togglePause()
    {
	  pause = !pause;
	  if(pause) 
	  {
	  	// On desactive Tux
	    gameZone.getHero().setActive(false);
	  	// On desactive la partie en cours
	    onStage(true,false);
	    // On affiche la Pause
	    screen = ScreenNames.PAUSED;
	  }
	  else 
	  {
	  	// On reactive la partie 
	    onStage(true,true);
	    screen = ScreenNames.PLAYING;
	  }
    }

    public int getScreen()
    {
    	return screen;
    }
    /**
     * goToNextLevel : on va au level suivant
     */
    private void goToNextLevel()
    {
	  level = level+1;
	  if (level<=maxLevel)
	  {
	  	load(true);
	  }
	  else screen = ScreenNames.TOBECONTINUED;
    }

    /**
     * handleClick : on clique sur la souris
     * @param p coordonnées du pointeur
     */
    public void handleClick(Point p)
    {
	  switch(screen)
	  {
	    // On est sur l'ecran INTRO ?     
	    case ScreenNames.INTRO:	    	
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) 
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
		       jukebox.play(SoundNames.SND_MENU_ID);
		       title();
		     }
	         break;    
 	    // On est sur l'ecran PLAYING ?     
	    case ScreenNames.PLAYING:
	    	 Debug.println(DebugNames.HIGH_PRIORITY,"/** PAUSE **/");
	         togglePause();
	         break;
  	    // On est sur l'ecran PAUSE ?     
	    case ScreenNames.PAUSED:
		     Debug.println(DebugNames.HIGH_PRIORITY,"/** PLAYING **/");
	         togglePause();
	         break;
 	    // On est sur l'ecran BLANK, PRELOADING ou LOADIN ?     
	    case ScreenNames.BLANK:
	    case ScreenNames.PRELOADING:
	    case ScreenNames.LOADING:
		     Debug.println(DebugNames.HIGH_PRIORITY,"/** LOADING **/");
	         break; 
	    // On est sur l'ecran TITLE ?     
	    case ScreenNames.TITLE:
	    	 // On a cliqué sur "Intro" ?
	         if(ScreenNames.INTRO_BTN.contains(p))
	         {
			   Debug.println(DebugNames.HIGH_PRIORITY,"/** INTRO **/");
		       // Jouer un "bip"
	    	   jukebox.play(SoundNames.SND_MENU_ID);
	    	   // afficher l'intro
	    	   intro();
	         }	
	    	 // On a cliqué sur "Credits" ?
	         if(ScreenNames.CREDITS_BTN.contains(p))
	         {
			   Debug.println(DebugNames.HIGH_PRIORITY,"/** CREDITS **/");
		       // Jouer un "bip"
	    	   jukebox.play(SoundNames.SND_MENU_ID);
	    	   // afficher l'intro
	    	   credits();
	         }	
	    	 // On a cliqué sur "Help" ?
	         if(ScreenNames.HELP_BTN.contains(p))
	         {
			   Debug.println(DebugNames.HIGH_PRIORITY,"/** HELP **/");
		       // Jouer un "bip"
	    	   jukebox.play(SoundNames.SND_MENU_ID);
	    	   // afficher l'intro
	    	   help1();
	         }	
	    	 // On a cliqué sur "Play" ?
	         else if(ScreenNames.PLAY_BTN.contains(p))
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** PLAY **/");
		       // Jouer un "bip"
	           jukebox.play(SoundNames.SND_MENU_ID);
		    	 if (music_play)
		    	 {
		    	 	jukebox.stop(SoundNames.MUS_MENU_ID);
		    	 	music_play=false;
		    	 }
	           // Initialiser le jeu 
	           level=_level;
		       getHero().setContinue(4);
		       getHero().setScore(0);
		       load(true); 
	         }
	    	 // On a cliqué sur "Quit" ?
	         else if(ScreenNames.QUIT_BTN.contains(p))
	         {
			   Debug.println(DebugNames.HIGH_PRIORITY,"/** QUIT **/");
		       // Jouer un "bip"
	    	   jukebox.play(SoundNames.SND_MENU_ID);
	    	   // Quitter
	    	   System.exit(0);
	         }	
	         break;
  	    // On est sur l'ecran CONTINUE ?     
	    case ScreenNames.CONTINUE:
	    	 // On clique sur le bouton "Retry" ? 
	         if (ScreenNames.TRY_AGAIN_BTN.contains(p))
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TRY AGAIN **/");
		       // Jouer un "bip"
    	       jukebox.play(SoundNames.SND_MENU_ID);
    	       // Lancer le jeu
     	 	   if (gameZone.getLevel().isMusicPlaying())
    	 	   {
     	   	     oldMusique=gameZone.getLevel().getMusic();
    	 	   }
     	 	   else oldMusique="";
		       load(true);
	         }
	         else if (ScreenNames.BACK_TO_TITLE_BTN.contains(p))
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
		       // Jouer un "bip"
	           jukebox.play(SoundNames.SND_MENU_ID);
	           // Afficher l'ecran TITLE
		       title();
	         }
	         break;
	    case ScreenNames.GAMEOVER:
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) 
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
		       jukebox.play(SoundNames.SND_MENU_ID);
		       title();
		     }
	         break;    
	    case ScreenNames.WELLDONE:
	         if (ScreenNames.GO_TO_NEXT_STAGE_BTN.contains(p) && statusBar.getTimer()==0)
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** NEXT LEVEL **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
	    	 	if (gameZone.getLevel().isMusicPlaying())
	    	 	{
    	   	      oldMusique=gameZone.getLevel().getMusic();
	    	 	  //jukebox.stop(SoundNames.MUS_LEVEL_ID);
	//    	 	  gameZone.getLevel().setMusicPlay(false);
	    	 	}
	    	 	else oldMusique="";
    	       goToNextLevel();
	         }
	         else if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) 
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
		       title();
	         }
	         else
	         {
	        	 getHero().increaseScore(Params.TIME_SCORE*getStatusBar().getTimer());
		         getStatusBar().setTimer(0);	         	
	         }
	         break;
	    case ScreenNames.HELP1:
	         if (ScreenNames.GO_TO_NEXT_STAGE_BTN.contains(p))
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** NEXT HELP **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
   	           help2();
	         }
	         else if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) 
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
		       title();
	         }
	         break;
	    case ScreenNames.HELP2:
	         if (ScreenNames.GO_TO_NEXT_STAGE_BTN.contains(p))
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** NEXT HELP **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
  	           help3();
	         }
	         else if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) 
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
		       title();
	         }
	         break;
	    case ScreenNames.HELP3:
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) 
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
		       title();
	         }
	         break;
	    case ScreenNames.CREDITS:
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) 
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** TITLE **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
		       title();
	         }
	         break;
	    case ScreenNames.TOBECONTINUED:
	         if (ScreenNames.GO_TO_NEXT_STAGE_BTN.contains(p) && statusBar.getTimer()==0)
	         {
		       Debug.println(DebugNames.HIGH_PRIORITY,"/** GAME OVER **/");
	           jukebox.play(SoundNames.SND_MENU_ID);
	   		   hscore.update(user,level,gameZone.getHero().getScore());
			   litGameOver = true;
			   hsline = 0;
		       screen = ScreenNames.GAMEOVER;
	         }
	         break;
	    default:
		     Debug.println(DebugNames.HIGH_PRIORITY,"/** ERROR **/");
	         title(); // logiquement on arrive jamais la...
	         break;	    
	  }
    }

    /**
     * handleMovement : on bouge la souris
     * @param p coordonnées du pointeur
     */
    private void handleMovement(Point p)
    {
	  switch(screen)
	  {
	    case ScreenNames.INTRO:
			 overBackToTitle = false;
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) overBackToTitle = true;
	         break;    
	    case ScreenNames.TITLE:
		     overIntro = false;
		     overPlay = false;
		     overCredits = false;
		     overHelp = false;
		     overQuit = false;
	         if (ScreenNames.INTRO_BTN.contains(p)) overIntro = true;
	         else if (ScreenNames.PLAY_BTN.contains(p)) overPlay = true;
	         else if (ScreenNames.CREDITS_BTN.contains(p)) overCredits = true;
	         else if (ScreenNames.HELP_BTN.contains(p)) overHelp = true;
	         else if (ScreenNames.QUIT_BTN.contains(p)) overQuit = true;
	         break;
	    case ScreenNames.CONTINUE:
			 overTryAgain = false;
		     overBackToTitle = false;
	         if (ScreenNames.TRY_AGAIN_BTN.contains(p))	overTryAgain = true;
	         else if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) overBackToTitle = true;
	         break;
	    case ScreenNames.GAMEOVER:
			 overBackToTitle = false;
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) overBackToTitle = true;
	         break;    
	    case ScreenNames.CREDITS:
			 overBackToTitle = false;
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) overBackToTitle = true;
	         break;    
	    case ScreenNames.WELLDONE:
			 overGoToNextStage = false;
		     overBackToTitle = false;
		     if (ScreenNames.GO_TO_NEXT_STAGE_BTN.contains(p) && statusBar.getTimer()==0) overGoToNextStage = true;
	         else if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) overBackToTitle = true;
	         break;
	    case ScreenNames.HELP1:
	    case ScreenNames.HELP2:	
			 overGoToNextStage = false;
		     overBackToTitle = false;
		     if (ScreenNames.GO_TO_NEXT_STAGE_BTN.contains(p)) overGoToNextStage = true;
	         else if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) overBackToTitle = true;
	         break;
	    case ScreenNames.HELP3:	
		     overBackToTitle = false;
	         if (ScreenNames.BACK_TO_TITLE_BTN.contains(p)) overBackToTitle = true;
	         break;
	    case ScreenNames.TOBECONTINUED:
			 overGoToNextStage = false;
		     if (ScreenNames.GO_TO_NEXT_STAGE_BTN.contains(p)) overGoToNextStage = true;
	         break;
	    default:
	         break;	    
	  }
    }

    /**
     * drawScreen : on dessine la fenetre
     */
    public void drawScreen()
    {
	  Graphics g = getGraphics();
	  Font f;
	  FontMetrics fm;
	  
	  if(g==null) return;
	  if(!preloadDone) return;

	  offGraphics = offImage.getGraphics();
	  switch(screen)
	  {
	    case ScreenNames.PLAYING:
	    	 if (gameZone.getLevel().getMusic()!=null)
	    	 {
	    	 	if (!gameZone.getLevel().getMusic().equals(oldMusique))
	    	 	{
		    	  jukebox.stop(SoundNames.MUS_LEVEL_ID);	    	 		
		    	  gameZone.getLevel().setMusicPlay(false);
		    	  oldMusique=gameZone.getLevel().getMusic();
	    	 	}
	    	 	if (!gameZone.getLevel().isMusicPlaying())
	    	 	{
		    	  gameZone.getLevel().setMusicPlay(true);
	    	 	  jukebox.loop(SoundNames.MUS_LEVEL_ID);
	    	 	}
	    	 }
 			 else if (gameZone.getLevel().isMusicPlaying())
	    	 {
		    	  jukebox.stop(SoundNames.MUS_LEVEL_ID);	    	 		
		    	  gameZone.getLevel().setMusicPlay(false);	    	 	
	    	 }
	         break;
	    case ScreenNames.PAUSED:
	         gameZone.getHero().setActive(false);
	         Point p = gameZone.getSnapshotPos();
	         offGraphics.drawImage(gameZone.getSnapshot(),p.x,p.y,this);
	         displayer.displayNextImage(offGraphics,(width-150)/2,(height-60)/2,ScreenNames.PAUSE_ID);
	         g.drawImage(offImage,0,0,this);
	         break; 
	    case ScreenNames.PRELOADING: break;
	    case ScreenNames.LOADING:
	         offGraphics.drawImage(images[ScreenNames.TITLE_EMPTY_ID],0,0,this);
	         if (gameZone.getLevelName()!=null)
	         {
	           f = new java.awt.Font("Arial", Font.BOLD, 20);
	           offGraphics.setFont(f);
	           fm = g.getFontMetrics();
	           int w=fm.stringWidth(gameZone.getLevelName())*2;
	           offGraphics.setColor(Color.BLACK);	
	           offGraphics.drawString(gameZone.getLevelName(),((width-w)/2)+3,53);
	           offGraphics.setColor(Color.CYAN);	
	           offGraphics.drawString(gameZone.getLevelName(),(width-w)/2,50);
	         }
	         f = new java.awt.Font("Arial", Font.BOLD, 20);
	         offGraphics.setFont(f);
	         offGraphics.setColor(Color.RED);	
	         offGraphics.drawString("Loading...",150,175);
	         
	         g.drawImage(offImage,0,0,this);
	         break; 
	    case ScreenNames.INTRO:
	    	 if (!music_play)
	    	 {
	    	 	jukebox.loop(SoundNames.MUS_MENU_ID);
	    	 	music_play=true;
	    	 }
	         offGraphics.drawImage(images[ScreenNames.INTRO_TITLE_ID],0,0,this);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
             if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
             else offGraphics.setColor(Color.RED);
             offGraphics.drawString("Menu",340,290);
	         g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.TITLE:
	    	 if (!music_play)
	    	 {
	    	 	jukebox.loop(SoundNames.MUS_MENU_ID);
	    	 	music_play=true;
	    	 }
	         offGraphics.drawImage(images[ScreenNames.TITLE_ID],0,0,this);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
             	    
             if (overIntro) offGraphics.setColor(Color.YELLOW);
             else offGraphics.setColor(Color.RED);
             offGraphics.drawString("Intro",15,139);
//             	offGraphics.drawImage(images[ScreenNames.INTRO_ID],(int) ScreenNames.INTRO_BTN.getMinX(),(int) ScreenNames.INTRO_BTN.getMinY(),this);
	         if (overPlay) offGraphics.setColor(Color.YELLOW);
             else offGraphics.setColor(Color.RED);
             offGraphics.drawString("Play",15,165);
//	         	offGraphics.drawImage(images[ScreenNames.PLAY_ID],(int) ScreenNames.PLAY_BTN.getMinX(),(int) ScreenNames.PLAY_BTN.getMinY(),this);
	         if (overCredits) offGraphics.setColor(Color.YELLOW);
             else offGraphics.setColor(Color.RED);
             offGraphics.drawString("Credits",15,193);
//	         	offGraphics.drawImage(images[ScreenNames.CREDITS_ID],(int) ScreenNames.CREDITS_BTN.getMinX(),(int) ScreenNames.CREDITS_BTN.getMinY(),this);
	         if (overHelp) offGraphics.setColor(Color.YELLOW);
             else offGraphics.setColor(Color.RED);
             offGraphics.drawString("Help",15,220);
//	         	offGraphics.drawImage(images[ScreenNames.HELP_ID],(int) ScreenNames.HELP_BTN.getMinX(),(int) ScreenNames.HELP_BTN.getMinY(),this);
	         if (overQuit) offGraphics.setColor(Color.YELLOW); 
             else offGraphics.setColor(Color.RED);
             offGraphics.drawString("Quit",15,247);
//	         	offGraphics.drawImage(images[ScreenNames.QUIT_ID],(int) ScreenNames.QUIT_BTN.getMinX(),(int) ScreenNames.QUIT_BTN.getMinY(),this);
	         Font font = new java.awt.Font("Arial", Font.PLAIN, 9);
	         offGraphics.setColor(Color.white);
	         offGraphics.setFont(font);
	         offGraphics.drawString("Copyright (c) 2006 Philippe Bousquet",230,292);
	         g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.HELP1:
	         offGraphics.drawImage(images[ScreenNames.HELP1_ID],0,0,this);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
	         if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Menu",340,290);
	         if(overGoToNextStage)  offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Next",8,290);
	         g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.HELP2:
	         offGraphics.drawImage(images[ScreenNames.HELP2_ID],0,0,this);
            f=new Font("Arial",Font.BOLD, 20);
            offGraphics.setFont(f);
	         if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Menu",340,290);
	         if(overGoToNextStage)  offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Next",8,290);
	         g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.HELP3:
	         offGraphics.drawImage(images[ScreenNames.HELP3_ID],0,0,this);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
	         if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Menu",340,290);
	         g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.CONTINUE:
	         offGraphics.drawImage(images[ScreenNames.TITLE_EMPTY_ID],0,0,this);
             offGraphics.setColor(Color.WHITE);
		     offGraphics.drawString(new Integer(gameZone.getHero().getScore()).toString(),10,35);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
             fm=offGraphics.getFontMetrics();
             int w=fm.stringWidth("TIME OVER");
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("TIME OVER",((width-w)/2)+3,53);
             offGraphics.setColor(Color.RED);
             offGraphics.drawString("TIME OVER",((width-w)/2),50);
             f=new Font("Arial",Font.BOLD, 15);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.RED);
             offGraphics.drawString("Continue left : "+new Integer(gameZone.getHero().getContinue()).toString(),140,175);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
	         if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Menu",340,290);
             if(overTryAgain)  offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Retry",8,290);
	         g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.GAMEOVER:
    	 	if (gameZone.getLevel().isMusicPlaying())
    	 	{
    	 	  jukebox.stop(SoundNames.MUS_LEVEL_ID);
    	 	  gameZone.getLevel().setMusicPlay(false);
    	 	}
	         offGraphics.drawImage(images[ScreenNames.TITLE_EMPTY_ID],0,0,this);	    
	         offGraphics.setColor(Color.white);
	         offGraphics.drawString(new Integer(gameZone.getHero().getScore()).toString(),10,35);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
             fm=offGraphics.getFontMetrics();
             w=fm.stringWidth("HIGH SCORES");
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("HIGH SCORES",((width-w)/2)+3,53);
             offGraphics.setColor(Color.RED);
             offGraphics.drawString("HIGH SCORES",((width-w)/2),50);
             f=new Font("Arial",Font.BOLD, 15);
             offGraphics.setFont(f);
             int h=80;
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("NAME",101,h+1);
             offGraphics.drawString("LVL",251,h+1);
             offGraphics.drawString("SCORE",301,h+1);
             offGraphics.setColor(Color.RED);
             offGraphics.drawString("NAME",100,h);
             offGraphics.drawString("LVL",250,h);
             offGraphics.drawString("SCORE",300,h);
             h=h+20;  
		     for (int i=0;i<hsline;i++)
		     {
	           offGraphics.setColor(Color.BLACK);
	           offGraphics.drawString((i+1)+"- ",51,h+1);
	           offGraphics.drawString(hscore.getUser(i),101,h+1);
	           if (hscore.getLevel(i)>Params.LVL_MAX)
	             offGraphics.drawString("END",251,h+1);
	           else
	             offGraphics.drawString(new Integer(hscore.getLevel(i)).toString(),251,h+1);
	           offGraphics.drawString(new Integer(hscore.getScore(i)).toString(),301,h+1);
	           if (i==hscore.getCurent()) offGraphics.setColor(Color.decode("0x00F0F0"));
	           else offGraphics.setColor(Color.decode("0x00B0B0"));
	           offGraphics.drawString((i+1)+"- ",50,h);
	           offGraphics.drawString(hscore.getUser(i),100,h);
	           if (hscore.getLevel(i)>Params.LVL_MAX)
		         offGraphics.drawString("END",250,h);
	           else
	             offGraphics.drawString(new Integer(hscore.getLevel(i)).toString(),250,h);
               offGraphics.drawString(new Integer(hscore.getScore(i)).toString(),300,h);
	           h=h+20;  
		     }
		     if (litGameOver)
		     {
               f=new Font("Arial",Font.BOLD, 20);
               offGraphics.setFont(f);
               offGraphics.setColor(Color.decode("0xF000F0"));
               offGraphics.drawString("Gamme Over",130,180);
		     }
             litGameOver=!litGameOver;
		     if (hsline<Params.MAX_HS) hsline++;
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
	         if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Menu",340,290);
	         g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.WELLDONE:
	         offGraphics.drawImage(images[ScreenNames.TITLE_EMPTY_ID],0,0,this);
             offGraphics.setColor(Color.WHITE);
	         offGraphics.drawString(new Integer(gameZone.getHero().getScore()).toString(),10,35);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
             fm=offGraphics.getFontMetrics();
             w=fm.stringWidth("LEVEL CLEARED");
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("LEVEL CLEARED",((width-w)/2)+3,53);
             offGraphics.setColor(Color.GREEN);
             offGraphics.drawString("LEVEL CLEARED",((width-w)/2),50);
             f=new Font("Arial",Font.BOLD, 15);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.RED);
             offGraphics.drawString("Time Bonus x "+Params.TIME_SCORE+" : "+statusBar.getTimer(),125,170);
             offGraphics.drawString("Score : "+gameZone.getHero().getScore(),150,185);
	         if (statusBar.getTimer()>0)
	         {
	           jukebox.play(SoundNames.SND_POINT_ID);
	    	   statusBar.setTimer(statusBar.getTimer()-1);
	    	   getHero().increaseScore(Params.TIME_SCORE);
	         }
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
	         if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Menu",340,290);
	         if (getStatusBar().getTimer()<=0)
	         {
	           if(overGoToNextStage)  offGraphics.setColor(Color.YELLOW);
	           else offGraphics.setColor(Color.RED);
	           offGraphics.drawString("Next",8,290);
	         }
	    	 g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.TOBECONTINUED:
    	 	if (gameZone.getLevel().isMusicPlaying())
    	 	{
    	 	  jukebox.stop(SoundNames.MUS_LEVEL_ID);
    	 	  gameZone.getLevel().setMusicPlay(false);
    	 	}
	         offGraphics.drawImage(images[ScreenNames.TOBECONTINUED_ID],0,0,this);
	    	 offGraphics.setColor(Color.WHITE);
             offGraphics.drawString(new Integer(gameZone.getHero().getScore()).toString(),10,35);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
             fm=offGraphics.getFontMetrics();
             w=fm.stringWidth("TO BE CONTINUED...");
             offGraphics.setColor(Color.RED);
             offGraphics.drawString("TO BE CONTINUED...",((width-w)/2),200);
             f=new Font("Arial",Font.ITALIC, 15);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.CYAN);
             offGraphics.drawString("Visit http://darken33.free.fr/",110,240);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
	         if(overGoToNextStage)  offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Next",8,290);
	    	 g.drawImage(offImage,0,0,this);
	         break;
	    case ScreenNames.CREDITS:
//	         offGraphics.drawImage(images[ScreenNames.TITLE_EMPTY_ID],0,0,this);
             offGraphics.setColor(Color.BLUE);
	         offGraphics.fillRect(0,0,width, height);
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
             fm=offGraphics.getFontMetrics();
             w=fm.stringWidth("CREDITS");
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("CREDITS",((width-w)/2)+3,53);
             offGraphics.setColor(Color.RED);
             offGraphics.drawString("CREDITS",((width-w)/2),50);
             h=80;
             w=20;
             // La Conception
             f=new Font("Arial",Font.ITALIC | Font.BOLD, 15);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("Conception :",w+1,h+1);
             offGraphics.setColor(Color.decode("0x00F0F0"));
             offGraphics.drawString("Conception :",w,h);
             h=h+20;  
             f=new Font("Arial",Font.BOLD, 12);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.decode("0xFFFFFF"));
             offGraphics.drawString("Philippe Bousquet (Darken33)",w+30,h);
             // Programmer
             h=h+30;  
             f=new Font("Arial",Font.ITALIC | Font.BOLD, 15);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("Programmer :",w+1,h+1);
             offGraphics.setColor(Color.decode("0x00F0F0"));
             offGraphics.drawString("Programmer :",w,h);
             h=h+20;  
             f=new Font("Arial",Font.BOLD, 12);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.decode("0xFFFFFF"));
             offGraphics.drawString("Philippe Bousquet (Darken33)",w+30,h);
             // Music / SFX
             h=h+30;  
             f=new Font("Arial",Font.ITALIC | Font.BOLD, 15);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("Music / SFX :",w+1,h+1);
             offGraphics.setColor(Color.decode("0x00F0F0"));
             offGraphics.drawString("Music / SFX :",w,h);
             h=h+20;  
             f=new Font("Arial",Font.BOLD, 12);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.decode("0xFFFFFF"));
             offGraphics.drawString("Thierry Bousquet (Akira)",w+30,h);
             // GFX / Anim
             h=h+30;  
             f=new Font("Arial",Font.ITALIC | Font.BOLD, 15);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.BLACK);
             offGraphics.drawString("GFX / Anim :",w+1,h+1);
             offGraphics.setColor(Color.decode("0x00F0F0"));
             offGraphics.drawString("GFX / Anim :",w,h);
             h=h+20;  
             f=new Font("Arial",Font.BOLD, 12);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.decode("0xFFFFFF"));
             offGraphics.drawString("André Bouaziz (Heder)",w+30,h);
             h=h+20;  
             f=new Font("Arial",Font.BOLD, 12);
             offGraphics.setFont(f);
             offGraphics.setColor(Color.decode("0xFFFFFF"));
             offGraphics.drawString("Philippe Bousquet (Darken33)",w+30,h);
             // Bouton Menu
             f=new Font("Arial",Font.BOLD, 20);
             offGraphics.setFont(f);
	         if(overBackToTitle) offGraphics.setColor(Color.YELLOW);
	         else offGraphics.setColor(Color.RED);
	         offGraphics.drawString("Menu",340,290);
	         g.drawImage(offImage,0,0,this);
	         break;
	    default:
	         break;
	  }
    }

    /**
     * drawInt : Permet d'aficher un Eniter sur l'ecran
     * @param g Ecran sur l'equel afficher l'entier
     * @param n Entier à afficher
     * @param x Position X
     * @param y Position Y
     * @param max Longueur maximale
     */
    private void drawInt(Graphics g, int n, int x, int y, int max)
    {
	  String s = ""+n;
	  if(s.length()>max)
	  { 
	    for(int i=0;i<s.length();i++) 
		displayer.displayImageNum(g,x+i*20,y,ScreenNames.FONT_ID,26+9);
	  }
	  else 
	  {
	    int align = (max-s.length())*20; // align = "right"
	    for(int i=0;i<s.length();i++) 
		displayer.displayImageNum(g,x+align+i*20,y,ScreenNames.FONT_ID,26+Integer.parseInt(s.substring(i,i+1)));
	  }
    } 

   
    public static void main(String args[]) {
    if (args != null && args.length>0)
    {
      try
	  {
    	_level=new Integer(args[0]).intValue();
	  }
      catch (Exception e)
	  {
      	_level=1;
	  }
    }
    else _level=1;
	new Tuxland();
    }


}







