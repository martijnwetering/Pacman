package game;


import java.util.Vector;

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
	private GameTiles gameTiles;
	private PointController pointController;
	private LevelGenerator generator;
	private int[][] tileMap;
	private int level;
	 


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
		
		
		scoreDisplay = new DashboardTextView(this);
		scoreDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
		scoreDisplay.setTextColor(Color.BLACK);
		addToDashboard(scoreDisplay);

		createDashboard();		
	}
	
	private void createDashboard() 
	{

		// this.scoreDisplay.setWidgetWidth(20);
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
		
		this.scoreDisplay.setTextString(
				"Score: " + String.valueOf(this.pacman.getScore()));
	}
	
	public GameTiles getGameTiles()
	{
		return gameTiles;
	}

}
