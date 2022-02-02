/*********************************************************************************************************
 * class Game, responsible for creating the game and running it. Intiializing the sprites as well
 * as the background/animations.
 ********************************************************************************************************/
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

public class Player extends Entity
{
	/*****************************************************************************************************
	 * Setting up variables and constructor
	 ****************************************************************************************************/

	//The constructor, which initializes the player.
	public Player()
	{
		//create player object and set initial position
		super("src/resources/Player.png", 30, 40);
	}

	/*****************************************************************************************************
	 * Moving and updating the sprite
	 ****************************************************************************************************/	
	public void move()
	{
		//a function that changes the position of Player depending on the direction inputed
		//the key input function is actually in the Entity class
		this.yPosition += this.yDirection;					//move player vertically
		this.xPosition += this.xDirection;					//move player horizontally 

		//warper if statements
		if (this.yPosition > 372)							
			this.yPosition = 0;								//warps to top if goes down through bottom
		if (this.yPosition < 0)
			this.yPosition = 372;							//warps to bottom if goes through top

		//warper if statements for left and right
		if (this.xPosition > 519)							
			this.xPosition = 0;							//warps to top if goes down through bottom
		if (this.xPosition < 0)
			this.xPosition = 519;							//warps to bottom if goes through top
	}
	public void move(Entity player)
	{
		System.out.print("filler text");
	}
}

