import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

class Frame extends Entity
{
	//initiate the first frame, which is the game background of demon rick
	public Frame()
	{
		//initiate goal and its position
		super("src/resources/SAYGOODBYE.jpeg", 0, 0);
	}

	////////////////////////
	//FILLER, DO NOT TOUCH//
	////////////////////////	
	public void move()
	{
		System.out.print("filler text");
	}
	public void move(Entity mario)
	{
		System.out.print("filler text");
	}
}