import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

public class Astley extends Entity
{
	/*****************************************************************************************************
	 * Setting up variables and constructor
	 ****************************************************************************************************/
	//controls how fast Astley is
	private final int Astleyspeed = 7;

	//The constructor, which initializes the player.
	public Astley()
	{
		//create player object and set initial position
		super("src/resources/RICKKKK.png", 440, 100);
	}

	/*****************************************************************************************************
	 * Moving and updating the sprite
	 ****************************************************************************************************/	
	public void move()
	{
		System.out.print("filler text");
	}
	public void move(Entity player)
	{
		/**
		 * Make demon rick chase player. RUNNNNNNNN.
		 * He may alter his movement based on where the player is heading.
		 * Different movements has different priority (as decided by the if statement)
		 * Top priority: 	If the player is close to a portal, rush to the portal and centers himself,
		 * 					blocking the player
		 * Medium priority:	If the player is heading towards the margin, starts going to the other 
		 * 					direction to anticipate their attack
		 * Bottom priority: if the player is idling or is not in the two zones above, chase the player
		 * 					directly without altering the course.
		 */
		//The position rick should be to center himself is (230, 156) 
		//chase the player if they are near the portal
		if (closeToPortal(player))
		{
			//horizontal movement
			if (xPosition < 230)
				xPosition += this.Astleyspeed;
			else if (xPosition > 230)
				xPosition -= this.Astleyspeed;

			//vertical movement 
			if (yPosition < 156)
				yPosition += this.Astleyspeed;
			else if (yPosition > 156)
				yPosition -= this.Astleyspeed;
		}	
		//check if the player is near any margin, chase the other way to anticipate movements
		else
		{
			//Astley is above the player
			if (player.getY() > 357 && player.getYDirection() > 0)//player heading to bottom margin
				yPosition -= this.Astleyspeed;								//move Astley upwards
			else if (yPosition < (player.getY() - 30))			//if its near margin but no movement detected 
				yPosition += this.Astleyspeed;								//chase downwards

			//Astley is bellow the player 
			if (player.getY() < 75 && player.getYDirection() < 0)	//player heading to top margin
				yPosition += this.Astleyspeed;								//move Astley down wards to catch player tp
			else if (yPosition > (player.getY() - 30))			//if near top margin but no movement found
				yPosition -= this.Astleyspeed;								//chase upwards

			//Astley is to the left of the player
			if (player.getX() > 504 && player.getXDirection() > 0)//player is heading to the right margin
				xPosition -= this.Astleyspeed;								//Astley goes left to catch him tp
			else if (xPosition < (player.getX() - 30))			//no movement found
				xPosition += this.Astleyspeed;								//continue chasing right

			//Astley is to the right of the player
			if (player.getX() < 75 && player.getXDirection() < 0)	//player heading to left margin
				xPosition += this.Astleyspeed;								//predict warp and goes the opposite way
			else if (xPosition > (player.getX() - 30))			//no movement found
				xPosition -= this.Astleyspeed;								//continue chasing
		}
	}

	//if the player is near a portal, change priority of Rick's to secure the portal, blocking the players path
	private boolean closeToPortal(Entity player)
	{
		////check if player's x value is between near portal x and final x value
		if(player.getX() >= 200 && player.getX() <= 391)
		{
			//check if player's y value is between near portal y and final y value
			if (player.getY() >= 100 && player.getY() <= 326)
				return true;
		}

		//check if player bottomRight x value is between near portal x amd final x value
		else if ((player.getX() + player.getWidth()) >= 200 && (player.getX() + player.getWidth()) <= 391)
		{
			//check if a's bottom right y value is between near portal y and final y value
			if ((player.getY() + player.getHeight()) >= 100 && (player.getY() + player.getHeight()) <= 326)
				return true;
		}
		//all clear
		return false;
	}
}