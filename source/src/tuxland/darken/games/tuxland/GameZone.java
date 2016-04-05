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

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import darken.games.event.*;
import darken.games.utils.*;

/**
 * GameZone - Gestion de la zone de jeu
 * @author Philippe Bousquet (Darken33@free.fr)
 *         <br/><strong>Previous :</strong> 
 *         Pascal "Toweld" Bourut (toweld@planetelibre.org) 
 * @version 0.1.0
 */
public class GameZone extends Component implements Runnable, KeyListener 
{

	/** Le frame Parent */
    private Tuxland mainFrame = null;
	/** Les actions en cours tapées au clavier  */
    private boolean jump, down, left, right, run;
	/** Le thread */  
    private Thread thread = null;
    /** Le Joueur */
    private Hero tux = null;
	/** Le nom du niveau */    
    private String levelName = null;
    /** Le niveau */
    private static Level level = null;
    private int levelWidth = 0;
    private int levelHeight = 0;
    /** Les dimensions du niveau */
    private Dimension levelDimension = null;
    /** Les dimensions de l'ecran */
    private Dimension screenDimension = null;
    /** Image de fond */
    private Image bgImage = null;
    /** Image de premier plan */
    private Image fgImage = null;
    private MediaTracker mt = null;
    /** Image pour le double buffering */
    private Image offImage = null;
    /** Graphics pour le double buffering */
    private Graphics offGraphics = null; 
    private Rectangle r = null;
    /** Zone à mettre à jour */
    private Rectangle updateZone = null;

    private int xImage = 0;
    private int yImage = 0;
    private int oldXImage = 0;
    private int oldYImage = 0;

    private boolean scrolling = false;
    private boolean active = false;

    /** Liste des ennemies */
    private SpriteGroup enemies = null;
    /** Liste des items */
    private ItemGroup items = null;

    /**
     * GameZone - Constructeur
     * @param mainFrame Fenetre parente
     */
    public GameZone(Tuxland mainFrame)
    {  
	  this.mainFrame = mainFrame;
	  setActive(false);
	  addKeyListener(this);
	  // Creer le joueur
	  tux = new Hero();
	  tux.setMainFrame(mainFrame);
	  // Creer le groupe d'items
	  items = new ItemGroup();
	  items.setStatusBar(mainFrame.getStatusBar());
	  mt = new MediaTracker(this);
	  thread = new Thread(this);
    }

    /**
     * start - Demarrer le thread
     */
    public void start()
    {
	  thread.start();
    }

    /**
     * started - Le thread est il démarré ?
     * @return boolean true=oui, false=non
     */
    public boolean started()
    {
	  return thread.isAlive();
    }
    
    /**
     * setActive - Activer/Désactiver la zone de jeu
     * @param a true=activer, false=désactiver
     */
    public void setActive(boolean a)
    {
	  active = a;
    }
    
    /**
     * isActive - La zone de jeu est elle active ?
     * @return boolean true=oui, false=non
     */
    public boolean isActive()
    {
	  return active;
    }

    /**
     * getLevelName - récuperer le nom du niveau
     * @return String Le nom du niveau
     */
    public String getLevelName()
    {
    	try
		{
    	  String name = level.getName();
    	  return name;
		}
    	catch (Exception e)
		{
          return null;
		}
    }
    
    /**
     * setLevelName - Affecter un nouveau nom de level et recharger
     * @param levelName Nom du niveau
     * @param load true=Recharger le niveau, false=Ne pas recharger
     */
    public void setLevelName(String levelName,boolean load, boolean mp)
    {
	  this.levelName = levelName;
	  // Charger le niveau
	  if(load) 
	  {
	    level = null;
	    prepareLevel(mp);
	  }
	  // Initialiser les infos du joueur
	  Vector heroInfo = level.getHeroInfo();
	  tux.reset();
	  tux.setName((String)heroInfo.get(Params.NAME_INDEX));
	  tux.setX(Integer.parseInt((String)heroInfo.get(Params.X_INDEX)));
	  tux.setY(Integer.parseInt((String)heroInfo.get(Params.Y_INDEX)));
	  tux.setXOrientation(Integer.parseInt((String)heroInfo.get(Params.O_INDEX)));
	  tux.setLevel(level);
	  down=false;
	  left=false;
	  right=false;
	  jump=false;
	  run=false;
	  // Initialiser les ennemis
	  enemies = new SpriteGroup();
	  enemies.setLevel(level);
	  enemies.fill();
	  // Initialiser les items
	  items.reset();
	  items.setLevel(level);
	  items.fill();
	  // Initialiser la barre de status
	  mainFrame.getStatusBar().setTimer(level.getTimer());
	  mainFrame.getStatusBar().setLivesLid(4-tux.getContinue());
    }

    /**
     * setSize - positionner la dimension de la zone de jeu
     * @param w Largeur
     * @param h Hauteur
     */
    public void setSize(int w,int h)
    {
	  super.setSize(w,h);
	  screenDimension = getSize();
    }

    /**
     * getHero - Recuperer les infos du joueur
     * @return Hero Le joueur
     */
    public Hero getHero()
    {
	  return tux;
    }

    /**
     * getLevel - Récuperer les infos du niveau
     * @return Level le niveau
     */
    public Level getLevel(boolean mp)
    {
      // Charger le niveau	
	  if( level == null ) level = FileHandler.loadLevel(levelName, mp);
	  return level;
    }
    public Level getLevel()
    {
      // Charger le niveau	
	  return level;
    }

    /**
     * prepareLevel - Initialiser le niveau
     */
    public void prepareLevel(boolean mp)
    {
      // Charger le niveau	
	  if(level==null) this.getLevel(mp);
	  // Les dimensions du niveau
	  levelWidth = level.getPixelWidth();
	  levelHeight = level.getPixelHeight();
	  levelDimension = new Dimension(levelWidth, levelHeight);
	  // Récupérer l'image de fond
	  bgImage = getToolkit().getImage(level.getBgImage());
	  mt.addImage(bgImage,0);
	  // Récupérer l'image de premier plan s'il y en a une
	  if (level.getFgImage()!=null)
	  {
	    fgImage = getToolkit().getImage(level.getFgImage());
	    mt.addImage(fgImage,1);
	  }
	  try
	  {
	    mt.waitForID(0);
	  }
	  catch(InterruptedException e)
	  {
	    e.printStackTrace();
	  }
	  // Preparer le double buffering
	  offImage = null;
	  while(offImage==null ) 
	  {
	    offImage = createImage(levelWidth, levelHeight);
	    Debug.print(DebugNames.NORMAL_PRIORITY,".");
	    try
		{ 
		  Thread.sleep(200); 
	    }
	    catch ( InterruptedException e)
		{
		  e.printStackTrace();
		  break;
	    } 
 	  }
	  // Récupérer la musique du niveau s'il y en a une
	  if (level.getMusic() != null && !level.getMusic().equals(mainFrame.oldMusique)) mainFrame.getJukeBox().addSound(level.getMusic(),SoundNames.MUS_LEVEL_ID);
    }

    /**
     * keyPressed - Fonction appelée lorsqu'une touche est pressée
     */
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_P ) mainFrame.handleClick(new Point(0,0));
	    else updateSpriteMoves(e, true);
	    e.consume();
    }
    
    /**
     * keyReleased - Fonction appelée lorsqu'une touche est relachée
     */
    public void keyReleased(KeyEvent e)
    {
	  updateSpriteMoves(e, false);
	  e.consume();
    }

    /**
     * keyTyped - Fonction appelée lorsqu'une touche est tapée
     */
    public void keyTyped(KeyEvent e)
    {
      e.consume();
    }

    /**
     * updateSpriteMoves - mettre à jour le deplacement du joueur
     * @param e Touche
     * @param b true=la touche est pressée, false=la touche est relachée
     */
    public void updateSpriteMoves(KeyEvent e, boolean b)
    {
	  switch(e.getKeyCode())
	  {
	    case KeyEvent.VK_UP : if (jump && jump!=b)tux.endOfJump();  jump = b ;  if(jump) down=false; break;
	    case KeyEvent.VK_DOWN : down = b ; if(down) jump=false; break;
	    case KeyEvent.VK_LEFT : left = b ; if(left) right=false; break;
	    case KeyEvent.VK_RIGHT : right = b ; if(right) left=false; break;
	    case KeyEvent.VK_SHIFT : run = b ; if(b)tux.setAction(Action.RUN);else tux.setAction(Action.WALK);break;
	  }
    }

    /**
     * moveHero - Déplacer le joueur
     */
    public void moveHero()
    {
	  if(left)
	  {
	    if(run) tux.runLeft();
	    else tux.walkLeft();
	  }	
	  else if(right)
	  {
	    if(run) tux.runRight();
	    else tux.walkRight();
	  }
	  if(jump) 
	  {
	  	mainFrame.getJukeBox().play(SoundNames.SND_JUMP_ID);
	  	tux.jump();
	  }
	  else if(down) tux.setAction(Action.SQUAT);
	  else if(!left && !right) tux.hold();
    }

    /**
     * run - Execution du thread
     */
    public void run()
    {
	  Debug.print(DebugNames.NORMAL_PRIORITY,"Loading...");
	  // Préparer le double buffering
	  while(offImage==null ) 
	  {
	    offImage = createImage(levelWidth, levelHeight);
	    Debug.print(DebugNames.NORMAL_PRIORITY,".");
	    try
		{ 
		  Thread.sleep(200); 
	    }
	    catch ( InterruptedException e)
		{
		  e.printStackTrace();
		  break;
	    } 
	  }
	  offGraphics = offImage.getGraphics();
	  // Demarrer le timer
	  mainFrame.startStatusBar();
	  // Demarrer le thread du joueur
	  tux.setOldBounds(new Rectangle(0, 0, getWidth(), getHeight()));
	  tux.start();
	  // Boucle infinie
	  while(true)
	  {
	  	// Afficher le nombre de vies
		mainFrame.getStatusBar().setLivesLid(4-tux.getContinue());
		// Si la zone de jeu n'est pas active, on effectue une pause d'1s et on reboucle 
	    if(!isActive() || mainFrame.getScreen()==ScreenNames.PAUSED) 
	    {
		  try
		  {
		  	enemies.setPause(true);
		  	Thread.sleep(1000); 
		  }
		  catch ( InterruptedException e)
		  {
		    e.printStackTrace();
		    break;
		  } 
		  continue;
	    }
	    else
	    {	// Si le hero n'est pas actif, on reinitialise les entrées claviers 
	  	enemies.setPause(false);
	    if(!tux.isActive()) 
	    {
	      down=false;
	      left=false;
	      right=false;
	      jump=false;
	      run=false;
	    }			
	    // Deplace le joueur
	    moveHero();
	    // Adapter l'affichage (c'est le fond qui se déplace)
	    adaptViewport();
        // Mettre à jour les ennemis	
	    try
		{
	      enemies.update(-xImage,-yImage,screenDimension.width,screenDimension.height, tux);
	    } 
	    // En cas de colision avec le hero
	    catch(CollisionEvent e)
		{	
  	      Debug.println(DebugNames.HIGH_PRIORITY,this,"Collision with "+((Sprite)e.getSource()).getName() );
  	      // Si le hero ne se protège pas ou n'est pas en période d'invincibilité
  	      if ((tux.getAction()!=Action.SQUAT) && (tux.getAction()!=11) && !tux.isSafe())
  	      {
  	      	 // Le joueur perd une vie
  	  		 tux.setFlags(true,false);
  	         if (tux.getContinue()<=0) free();
  	      }
	    }
	    // Afficher à l'écran
	    display();	    
	    getToolkit().sync();
	    // Faire une pause 19ms sur le thread
	    try
		{ 
		  Thread.sleep(19); 
	    } 
	    catch ( InterruptedException e)
		{
		  e.printStackTrace();
		  break;
	    } 
	  }
	  }
    }

    /**
     * free - Libérer la mémoire
     */
    public void free()
    {
      enemies.free();
    }

    /**
     * adaptViewPort - Adapter l'affichage suivant la position du joueur
     */
    public void adaptViewport()
    {
	  Point heroPosition = tux.getCenter();
	  scrolling = false;
	  oldXImage = xImage;
	  oldYImage = yImage;
	  Debug.println(DebugNames.LOW_PRIORITY,this,"tux location "+ heroPosition);
	  // On deplace le fond sur X si le joueur depasse la moitié de l'ecran 
	  if ( heroPosition.x >= (screenDimension.width*1/2) && heroPosition.x <= (levelDimension.width-(screenDimension.width*1/2)))
	  {
	    xImage = (screenDimension.width*1/2) - heroPosition.x;
	  }
 	  else if ( heroPosition.x < (screenDimension.width*1/2) )
 	  {
	    xImage = 0;
	  }
	  else if ( heroPosition.x <= (levelDimension.width-(screenDimension.width*1/2)) )
	  {
	    xImage = screenDimension.width - levelDimension.width;
	  }
	  // On deplace le fond sur Y si le joueur depasse la moitié de l'ecran 
	  if ( heroPosition.y >= (screenDimension.height*1/2) && heroPosition.y <= (levelDimension.height-(screenDimension.height*1/2))) 
	  {
	    yImage = (screenDimension.height*1/2) - heroPosition.y;
	  }
	  else if ( heroPosition.y < (screenDimension.height*1/2) ) 
	  {
	    yImage = 0;
	  }
	  else if ( heroPosition.y > (levelDimension.height-(screenDimension.height*1/2) ) )
	  {
	    yImage = screenDimension.height - levelDimension.height;
	  } 
	  scrolling = (oldXImage != xImage) || (oldYImage != yImage);
	  Debug.println(DebugNames.LOW_PRIORITY,this,"xImage=" + xImage + " et yImage="+yImage );
    }
    
    /**
     * myPaint - dessiner les éléments dans la zone de jeu
     * @param g Graphics sur lequel on doit dessiner
     */
    public void myPaint(Graphics g)
    { 
      // Si rien n'a été modifié on sort	
	  if(updateZone==null)return;
	  // On affiche le fond
	  g.drawImage(bgImage,0,0,this);
	  // On affiche les ennemis
	  enemies.display(g);
	  // On affiche les items
	  try
	  {
	    items.display(g,updateZone,tux,mainFrame.getJukeBox());
	  }
	  // En cas de reception d'un evenement EndGameEvent
	  catch(EndGameEvent a)
	  {
		if (a.successFull())
		{
		  // On termine le niveau (on a gagné)	
		  tux.setAction(Action.REST);
		  jump=false;
		  down=false;
		  left=false;
		  right=false;
		  tux.setActive(false);
		  while (!mainFrame.getStatusBar().getEndAnimCD())
		  {
			  tux.display(g);
			  try
			  { 
			  	Thread.sleep(20); 
			  }
			  catch ( InterruptedException e)
			  {
			    e.printStackTrace();
			    break;
			  } 		  	
		  }
		  mainFrame.getJukeBox().stop(SoundNames.SND_TIMEOUT_ID);
	      mainFrame.allCDromCollected();
		}
	  }
	  // Afficher Le Joueur
	  tux.display(g);
    }
    
    /**
     * display - Afficher la zonne de jeu
     */
    public void display()
    {
	  r = tux.getBounds();
	  scrolling = true;
	  if(scrolling)
	  {
	    updateZone = new Rectangle(-xImage,-yImage,screenDimension.width,screenDimension.height);
	  }
	  else 
	  {
	    updateZone = r.union(tux.getOldBounds());
	    Rectangle enemiesUpdateZone = enemies.getUpdateZone();
	    if(enemiesUpdateZone!=null) updateZone = updateZone.union(enemiesUpdateZone);
	  }
	  Debug.println(DebugNames.LOW_PRIORITY,this,"updateZone " + updateZone);
	  tux.setOldBounds(r);
	  offGraphics = offImage.getGraphics(); 
	  offGraphics.clipRect(updateZone.x,updateZone.y, updateZone.width,updateZone.height);
	  myPaint(offGraphics);
	  if (level.getFgImage()!=null)
	  {
		offGraphics.clipRect(updateZone.x,updateZone.y, updateZone.width,updateZone.height);
		offGraphics.drawImage(fgImage,0,0,this);
	  }
	  offGraphics.setColor(Color.white);
	  offGraphics.drawString(new Integer(tux.getScore()).toString(),-xImage+10,-yImage+35);
	  Graphics g=getGraphics();
	  g.clipRect(0,0,screenDimension.width,screenDimension.height);
	  g.drawImage(offImage, xImage, yImage, this);	
	  // Affichage du Score
	  tux.setActive(true);
    }

    /**
     * getSnapshot - Faire une capture d'ecran
     * @return Image La capture d'ecran
     */
    public Image getSnapshot()
    {
	  return offImage;
    }
    
    /**
     * getSnapshotPos - recupérer la position de la capture d'ecran
     * @return Image La capture d'ecran
     */
    public Point getSnapshotPos()
    {
	  return new Point(xImage,yImage);
    }
}
















