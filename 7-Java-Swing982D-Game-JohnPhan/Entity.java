import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

abstract class Entity
{
	/*****************************************************************************************************
	 * Setting up variables and constructor
	 ****************************************************************************************************/
	//declare variables thatll be inherited
	//image for the sprite
	protected Image image;
	protected ImageIcon file;
	//delcare directions of player and how he'll move
	protected int yDirection;					
	protected int xDirection;					
	//player's Position
	protected int xPosition;
	protected int yPosition;
	//declare the width and height of player
	protected int width;
	protected int height;


	//constructors
	public Entity(String imageLink, int x, int y)
	{
		//load image for entity
		loadImage(imageLink);
		//set initial position for entity
		xPosition = x;
		yPosition = y;
	}

	//concrete function to load image
	public void loadImage(String imageLink)
	{
		file = new ImageIcon(imageLink);
		image = file.getImage();

		//get the w and h of the image
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}

	//changing the frames for the entity, will be used in game for sprite animation
	public void changeFile(String frameFile)
	{
		this.file = new ImageIcon(frameFile);
		this.image = file.getImage();
	}


	abstract void move();
	abstract void move(Entity player);
	
	//after the position is updated, player is redrawn in this new position1
	public void draw(Graphics2D g2d, Game game)
	{
		//Draws player in the program window, at its x and y location.
		g2d.drawImage(this.image, this.xPosition, this.yPosition, game);
	}
	
	
	/*************************************************************************************************
	 * A bunch of getter functions.
	 ************************************************************************************************/
	//positionArray, use to store coordinates
	public int[][] coordinate = {
							{this.xPosition, this.yPosition},						//Top left [0][0],[0][1]
						  	{(this.xPosition + width), this.yPosition},				//Top right	[1][0],[1][1]
						  	{this.xPosition, (this.yPosition + height)},			//Bottom left [2][0],[3][1]
						  	{(this.xPosition + width), (this.yPosition + height)}	//Bottom right [3][0],[3][1]
						 };	
	public int getX()
	{
		return this.xPosition;
	}
	public int getY()
	{
		return this.yPosition;
	}
	public int getWidth()
	{
		return this.width;
	}
	public int getHeight()
	{
		return this.height;
	}
	public Image getImage()
	{
		return this.image;
	}
	//special getter function for player, use in pursue
	public int getXDirection()
	{
		return xDirection;
	}
	public int getYDirection()
	{
		return yDirection;
	}

	/*****************************************************************************************************
	 * Detecting key presses/release and changing the direction inputs
	 * this is actually exclusively for player, but since im using polymorphism its
	 * to be in here or else repl wont run >:<
	 ****************************************************************************************************/

	//this event is triggered very frequently, whenever a key is pressed or released
	public void keyPressed(KeyEvent e) 
	{
		//get key signal and put it into a variable
        int key = e.getKeyCode();
		int speed = 15;
		//horizontal movements
        if (key == KeyEvent.VK_LEFT) 
           	xDirection = -speed;	//we want to split the game up to 7 lanes for player to tp						
        if (key == KeyEvent.VK_RIGHT) 
			xDirection = speed;	//move down one lane

		//vertical movements
        if (key == KeyEvent.VK_UP) 
           	yDirection = -speed;	//we want to split the game up to 7 lanes for player to tp						
        if (key == KeyEvent.VK_DOWN) 
			yDirection = speed;	//move down one lane
    }

	//use to reset the direction, stopping player from moving when the turn is already up
    public void keyReleased(KeyEvent e) 
	{
        //getting the key input into a variable 
        int key = e.getKeyCode();
        //reseting the key
		if (key == KeyEvent.VK_UP) {
            yDirection = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            yDirection = 0;
        }
		if (key == KeyEvent.VK_LEFT) {
            xDirection = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            xDirection = 0;
        }
    }
}