//https://zetcode.com/javagames/basics/
//package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame
{
	//Main constructor that'll call a function to intialize the game
	public Main ()
	{
		//see description below for more info on what it initializes
		initUI();
	}

	/****************************************************************************************************
	 * function that is responsible for intializing the game
	 * setting up the application frame, size, name and other basic tasks 
	 ***************************************************************************************************/
	public void initUI()
	{
		//basic info for the game
		add(new Game());							//creates a game at the center of JFrame
		setSize(578, 457);							//set a definite border for the game
		setResizable(false);						//does not allow the game to resize its border
		setTitle("The Rick-magedon");					//setting the title of the application

		//stop run when the game is closed
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Passing null to the setLocationRelativeTo() method centers the window on the screen.
		setLocationRelativeTo(null);
	}

	//main method
	public static void main(String[] args)
	{
		//This is the entry point of the game. Main's constructor is called here.
		EventQueue.invokeLater(() -> {JFrame ex = new Main(); ex.setVisible(true);});
	}
}