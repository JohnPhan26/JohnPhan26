/*********************************************************************************************************
 * class Game, responsible for creating the game and running it. Intiializing the sprites as well
 * as the background/animations.
 ********************************************************************************************************/

//import for background printing
import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;

//import for sprites 
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener
{
	/***********************************************************************************************
	 * Declaring variable
	 **********************************************************************************************/
	
	private Image sayGoodbye;								//Astley pic
	private Entity[] entity = new Entity[4];				//declare Entity array	
	private Timer timer;									//timer variable, use to control frame rates
	private final int DELAY = 100;							//set a delay to be 500ms 
	private boolean winGame = false;						//boolean variable responsible for win/lose
	private boolean runGame = true;							//let functions know if the game is still running
	private int counter = 0;								//counter variablevalueOf
	//declare variables for animation
	ImageIcon frameFile;
	Image frame;	

	/***********************************************************************************************
	 * Constructs/initializes the game's graphics and game engines
	 **********************************************************************************************/

	//aka create objects, sprites, import keyboard/mouse detection 
	public Game()
	{
		initGame();
	}

	//initGame will set up player in his position, as well as the ActionListener etc
	private void initGame()
	{
		addKeyListener(new TAdapter());			//set up keyListenier 
		setFocusable(true);						

		//declare all entities
        entity[0] = new Player();				//declare player Mario
		entity[1] = new Astley();				//declare enemy Astley
		entity[2] = new Goal();					//declare Goal 
		entity[3] = new Frame();				//declare the new Frame for BAD ENDING

        timer = new Timer(DELAY, this);			//set the timer delay to be 10
        timer.start();							//start the timer, and the game movement itself
		
	}	

	//All game graphics are drawn inside the paintComponent() method.
	@Override
	public void paintComponent(Graphics g)
	{
		//Draws JPanel window
		super.paintComponent(g);	
		//Paint the intial position of the sprites and background
        doDrawing(g);							
		//engine stuff dont touch
        Toolkit.getDefaultToolkit().sync();
	}

	//Draw the initial position of all the sprites
	private void doDrawing(Graphics g) 
	{
        //create a 2DGraphics object first
        Graphics2D g2d = (Graphics2D) g;
		//use the object to draw the background, player, astley, and goal
		g2d.drawImage(entity[3].getImage(), entity[3].getX(), entity[3].getY(), this);
        g2d.drawImage(entity[0].getImage(), entity[0].getX(), entity[0].getY(), this);
		g2d.drawImage(entity[1].getImage(), entity[1].getX(), entity[1].getY(), this);	
		g2d.drawImage(entity[2].getImage(), entity[2].getX(), entity[2].getY(), this);
    }

	/********************************************************************************************************************
	 * Timer and Ending
	 * Responsible for controlling what happens at each ticks and also some
	 * of the class used for post game processing
	 *******************************************************************************************************************/
	
	//this is called once every 100ms, use for running the game as well as post game processing
	//also responsible for stopping the timer and ending the game.
	@Override
	public void actionPerformed(ActionEvent e)
	{
        step();								//call step function
    }

	//paint a win screen, use in good ending :3
	private void paintGoodEnding()
	{
		//update the new frame before drawing
		entity[3].changeFile("src/resources/THEGOODENDING.png");
		//paint the first frame of the animation
		repaint(entity[3].getX()-1, entity[3].getY()-1, entity[3].getWidth()+1, entity[3].getHeight()+1);
		counter++;
	}

	//animate a rick roll, is use in bad endings.  >:3
	private void paintRickRoll()
	{
		//update the new frame before drawing
		entity[3].changeFile("src/resources/Frames/Frame"+String.valueOf(counter+1)+".png");
		//paint the first frame of the animation
		repaint(entity[3].getX()-1, entity[3].getY()-1, entity[3].getWidth()+1, entity[3].getHeight()+1);
		counter += 1;					//update the counter
	}

	//turn sprites invisible
	private void turnInvis()
	{
		//change player, astley, portal source image to that of invisible png
		entity[0].changeFile("MarioInvis.png");
		entity[1].changeFile("RickInvis.png");
		entity[2].changeFile("PortalInvis.png");

		//update the game one last time before rick roll to turn sprite invisible
		repaint(0, 0, 580, 460);
	}
	/********************************************************************************************************************
	 * Step and analysis
	 * Responsible for updating the sprites and checking if they colided
	 * Also includes setting up key inputs and what happen when the player input a key
	 * paintRickRoll(), and paintGoodEnding() class
	 * also responsible for stopping the timer and ending the game.
	 *******************************************************************************************************************/

	//update everything in the game, from movement to repainting sprites, to check runGame
	private void step() 
	{
		//game should be running normally
		if (runGame)
		{
			//call the function move, which updates the location of Mario and Rick Astley
			entity[0].move();				//Mario's move
			entity[1].move(entity[0]);		//Rick's move

			//repaint entities in a small specific location
			//repaint the whole screen, after astley and mario was updated
			repaint(0, 0, 580, 460); 
			
			//check if the game can still be run, will break the cycle if game finished
			runGame = checkRunGame(); 
		}
		//the player won 
		else if (!runGame && winGame && counter < 1)
		{
			turnInvis();						//repaint the sprites so theyre invisible
			paintGoodEnding();					//print out good ending 
		}
		//The player lost, proceed to rick roll them hehe
		else if (!runGame && !winGame && counter < 43)
		{
			turnInvis();						//repaint the sprites so theyre invisible
			paintRickRoll();					//paint the rickroll
		}
		else
			timer.stop();						//stop the timer from running
    }    


	//check if the game can still run, return false if a collision is found to stop the game
	private boolean checkRunGame()
	{
		//collision detection code here
		//test Astley vs Player, runs if true;
		if (DetectCollision(entity[0], entity[1]))
		{
			winGame = false;							//declare player has lost
			return false;								//set off chain reaction to stop the game 
		}
		//test Player vs Goal
		else if (DetectCollision(entity[0], entity[2]))
		{
			winGame = true;								//declare player has won
			return false;								//set off chain reaction to stop the game 
		}
		else
			return true;
	}

	//Find if there are any collision between two entity, returns true if collision is detected
	private boolean DetectCollision (Entity a, Entity b)
	{
		//Check Top Left
		if(a.getX() >= b.getX() && a.getX() <= (b.getX() + b.getWidth()) && a.getY() >= b.getY() && a.getY() <= (b.getY() + b.getHeight()))
			return true;
		//Check Bottom Right
		else if ((a.getX() + a.getWidth()) >= b.getX() && (a.getX() + a.getWidth()) <= (b.getX() + b.getWidth()) && (a.getY() + a.getHeight()) >= b.getY() && (a.getY() + a.getHeight()) <= (b.getY() + b.getHeight()))
			return true;
		//Check Bottom Left
		else if ((a.getX()) >= b.getX() && (a.getX()) <= (b.getX() + b.getWidth()) && (a.getY() + a.getHeight()) >= b.getY() && (a.getY() + a.getHeight()) <= (b.getY() + b.getHeight()))
			return true;
		//Check Top Right
		else if ((a.getX() + a.getWidth()) >= b.getX() && (a.getX() + a.getWidth()) <= (b.getX() + b.getWidth()) && a.getY() >= b.getY() && a.getY() <= (b.getY() + b.getHeight()))
			return true;
		//all clear
		return false;
	}

	//this blockcs pick up keyboard input and calls the following functions
	//mainly use for player to control player
	private class TAdapter extends KeyAdapter 
	{
        @Override
		//activates when a keyboard release is found
        public void keyReleased(KeyEvent e) 
		{	
            entity[0].keyReleased(e);				//call the keyreleased() function in player
        }

		//same but for key pressed
        @Override
        public void keyPressed(KeyEvent e) 
		{
            entity[0].keyPressed(e);
        }
    }
}