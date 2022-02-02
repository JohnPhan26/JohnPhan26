import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

//literally just a sprite to represent the portal, needed for collision detection
public class Goal extends Entity
{
	//setting up the portal
	public Goal()
	{
		//initiate goal and its position
		super("src/resources/Portal.png", 270, 176);
	}
	
	//the portal does not move, i just added filler text
	public void move()
	{
		System.out.print("filler text");
	}
	//the portal does not move pt2
	public void move(Entity mario)
	{
		System.out.print("filler text");
	}
}