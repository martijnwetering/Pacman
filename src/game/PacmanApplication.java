package game;


import java.util.Vector;

import android.gameengine.icadroids.dashboard.DashboardImageView;
import android.gameengine.icadroids.dashboard.DashboardTextView;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.input.MotionSensor;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.input.TouchInput;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.tiles.GameTiles;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;


public class PacmanApplication extends GameEngine 
{
	private Pacman pacman;
	private DashboardTextView scoreDisplay;
	private DashboardTextView Display;
	private DashboardImageView DisplayImage; 
	private GameTiles gameTiles;
	private PointController pointController;
	private LevelGenerator generator;
	private int[][] tileMap;
	private int level;
	private boolean start = false;
	
	 


	@Override
	protected void initialize() {
		System.out.println("running pacman");

		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;
		
		level = 1;
		generator = new LevelGenerator();
		
		
		
		createTileEnvironment();
		
		addPacman(120, 260);
		
		
		pointController = new PointController(this);
		pointController.placeNormalPoints();
		pointController.placePowerUps();
		
		DisplayImage = new DashboardImageView(this, "pacman2");
		addToDashboard(DisplayImage);
		
		//Display = new DashboardTextView(this);		
		
		
		//addToDashboard(Display);
		
		
		scoreDisplay = new DashboardTextView(this);
		scoreDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
		scoreDisplay.setTextColor(Color.BLACK);
		addToDashboard(scoreDisplay);

		createDashboard();
	
	}
	
	private void createDashboard() 
	{
		if(!start){/*
		this.Display.setPadding(140, 10, 10, 10);
		this.Display.setWidgetHeight(800);
		this.Display.setWidgetWidth(1200);
		this.Display.setWidgetBackgroundColor(Color.BLACK);
		this.Display.setTextColor(Color.WHITE);
		this.Display.setWidgetX(0);
		this.Display.setWidgetY(0);
		this.Display.setTextSize(40);
		this.Display.setTextScaleX(5);
		this.Display.setTextString("PacMan");
		//this.setResourceName(String resourceName)
		*/
		this.DisplayImage.setWidgetX(-115);
		this.DisplayImage.setWidgetY(0);
		this.DisplayImage.setWidgetWidth(1240);
		this.DisplayImage.setWidgetHeight(600);
		//this.DisplayImage.setBackgroundColor(Color.WHITE);
		if (OnScreenButtons.start)
		{
			System.out.println("start!!!!!!!!!");
			//buttonPressed = true;
			start = true;
		}
		}
		if(start){
			System.out.println("start!!!!!!!!!");
		// this.scoreDisplay.setWidgetWidth(20);
	    OnScreenButtons.use = true;
		this.scoreDisplay.setWidgetHeight(60);
		this.scoreDisplay.setWidgetBackgroundColor(Color.WHITE);
		this.scoreDisplay.setWidgetX(590);
		this.scoreDisplay.setWidgetY(5);

		// If you want to modify the layout of a dashboard widget,
		// you need to so so using its run method.
		this.scoreDisplay.run(new Runnable() {
			public void run() {
				scoreDisplay.setPadding(10, 10, 10, 10);
			}
		});
		}
	}
	
	private void addPacman(int x, int y)
	{
		pacman = new Pacman(this, 4);
		addGameObject(pacman, x, y);
	}
	
	


	/**
	 * Create background with tiles
	 */
	private void createTileEnvironment() 
	{
		String[] tileImagesNames = generator.getTileImagesNames();
		if(level == 1){tileMap = generator.getTilemap1();}
		if(level == 2){tileMap = generator.getTilemap2();}	
		gameTiles = new GameTiles(tileImagesNames, tileMap, 20);
		GameTiles myTiles = new GameTiles(tileImagesNames, tileMap, 20);
		setTileMap(myTiles);
		Log.d("pacman", "GameTiles created");
	}


	/**
	 * Update the game. At this moment, we only need to update the Dashboard.
	 * Note: the Dashboard settings will be adjusted!!!
	 * 
	 * @see android.gameengine.icadroids.engine.GameEngine#update()
	 */
	@Override
	public void update() {
		super.update();
		
		int goalDotsEaten = pointController.getNumberOfNormalPoints();
		int actualDotsEaten = pacman.getDotsEaten();
		if (goalDotsEaten == actualDotsEaten)
		{
			endGame();
		}
		if(OnScreenButtons.start){
			start = true;
		}
		this.scoreDisplay.setTextString(
				"Score: " + String.valueOf(this.pacman.getScore()));
	}
	
	public GameTiles getGameTiles()
	{
		return gameTiles;
	}

}
