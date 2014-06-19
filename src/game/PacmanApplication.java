package game;

/**
 * Pacman
 * This is a recreation of the original pacman for android devices.
 * The player needs to collect all the white dots in other to advance
 * a level. He can collect extra points by collecting fruits (red dots)
 * or by eating ghosts after a power up. A power up gives the ability
 * to eat ghosts for a limited amount of time.
 * 
 * @author Martijn van de Wetering, Koen Moret
 */

import game.creatures.BlueEnemy;
import game.creatures.Enemy;
import game.creatures.GreenEnemy;
import game.creatures.OrangeEnemy;
import game.creatures.Pacman;
import game.creatures.RedEnemy;
import game.creatures.Creature.Direction;
import game.points.NormalPoint;
import game.points.PointController;
import game.points.SpecialPoint;
import game.utilities.LevelGenerator;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.Vector;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
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

public class PacmanApplication extends GameEngine implements IAlarm
{
	private Pacman pacman;
	private DashboardTextView scoreDisplay;
	private GameTiles gameTiles;
	private Alarm alarm;
	private boolean resetting;

	/**
	 * Contains all the gameobjects placed on the game field. This is needed
	 * cause the list with game objects in the GameEngine class gives errors
	 * when iterating over it to remove game objects.
	 */
	private ArrayList<GameObject> placedGameObjects;

	private PointController pointController;

	private int[][] tileMap;
	private int level;

	@Override
	protected void initialize() {

		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;

		level = 1;

		createTileEnvironment();

		placedGameObjects = new ArrayList<GameObject>();

		addPacman(100, 260);
		addGhosts();
		addPointsAndPowerUps();


		scoreDisplay = new DashboardTextView(this);
		scoreDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
		scoreDisplay.setTextColor(Color.BLACK);
		addToDashboard(scoreDisplay);

		createDashboard(scoreDisplay, 590, 5);

		resetting = false;
	}

	/**
	 * Places all the points (dots) and power ups on the game field.
	 */
	private void addPointsAndPowerUps() {
		pointController = new PointController(this);
		pointController.placeNormalPoints();
		pointController.placePowerUps();
	}

	private void addGhosts() {
		placedGameObjects.add(new RedEnemy(pacman, this, 280, 260, 2, 4, 100));
		placedGameObjects.add(new OrangeEnemy(pacman, this, 300, 260, 5, 4, 100));
		placedGameObjects.add(new GreenEnemy(pacman, this, 280, 280, 10, 4, 100));
		placedGameObjects.add(new BlueEnemy(pacman, this, 300, 280, 12, 4, 100));

		for (GameObject gameObject : placedGameObjects)
		{
			if (gameObject instanceof Enemy)
			{
				Enemy enemy = (Enemy)gameObject;
				addGameObject(enemy, enemy.getXcor(), enemy.getYcor(), 20);
			}
		}
	}

	/**
	 * Creates a dashboard to the right of the game field that keeps track
	 * of pacmans score and lives. 
	 * @param textView
	 * @param xCor
	 * @param yCor
	 */
	private void createDashboard(final DashboardTextView textView, int xCor, int yCor) 
	{
		textView.setWidgetHeight(60);
		textView.setWidgetBackgroundColor(Color.WHITE);
		textView.setWidgetX(xCor);
		textView.setWidgetY(yCor);
		textView.run(new Runnable() {
			public void run() {
				textView.setPadding(10, 10, 10, 10);
			}
		});
	}

	/**
	 * Responsible for adding pacman to the game.
	 * @param x: starting x position
	 * @param y: starting y position
	 */
	private void addPacman(int x, int y)
	{
		pacman = new Pacman(this, 4, x, y, 3);
		placedGameObjects.add(pacman);
		addGameObject(pacman, pacman.getXcor() , pacman.getYcor());
	}

	/**
	 * Responsible for creating the correct tile environment. Currently
	 * there are 2 different tile evironments in the game.
	 */
	private void createTileEnvironment() 
	{
		String[] tileImagesNames = LevelGenerator.createImagesNames();
		if(level == 0){tileMap = LevelGenerator.createMenu();}
		if(level == 1){tileMap = LevelGenerator.createLevel1();}
		if(level == 2){tileMap = LevelGenerator.createLevel2();}	
		gameTiles = new GameTiles(tileImagesNames, tileMap, 20);
		GameTiles myTiles = new GameTiles(tileImagesNames, tileMap, 20);
		setTileMap(myTiles);
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
			nextLevel();
		}
		
		// For testing next level
		/*if (15 == actualDotsEaten)
		{
			nextLevel();
		}*/
		
		if (actualDotsEaten == 10)
		{
			pointController.placeSpecialPoint(1);
		}
		if (actualDotsEaten == 15)
		{
			pointController.placeSpecialPoint(2);
		}

		scoreDisplay.setTextString(
				"Score: " + String.valueOf(pacman.getScore())
				+ " | Lives: " + String.valueOf(pacman.getLives()));
	}

	/**
	 * Responsible for restarting the game. This happens when pacman lost al
	 * of his lives. The method removes all game objects still in the game and
	 * then recreates the world with new game objects.
	 */
	public void restart() 
	{		
		for (GameObject gameObject : placedGameObjects)
		{
			deleteGameObject(gameObject);
		}
		
		addPacman(100, 260);
		addGhosts();
		addPointsAndPowerUps();
	}

	/**
	 * This method set a timer during which the game stands still. 
	 */
	public void freezeMap()
	{	
		alarm = new Alarm(1, 30, this);
		alarm.startAlarm();
	}

	/**
	 * Responsible for resetting the game field. All ghosts and pacman are set back
	 * to there starting positions. The dots eaten and points will stay the same.
	 */
	private void resetMap()
	{	
		for (GameObject gameObject : placedGameObjects )
		{
			if (gameObject instanceof Enemy) 
			{	
				Enemy go = (Enemy)gameObject;
				go.jumpToStartPosition();
				go.setDirection(Direction.UP.getValue());
			}
			if (gameObject instanceof Pacman)
			{
				Pacman go = (Pacman)gameObject;
				go.setPacmanActive(true);
				go.jumpToStartPosition();
				go.setDirection(Direction.RIGHT.getValue());
			}
		}
		resetting = false;
		this.deleteAlarm(alarm);
	}
	
	/**
	 * Responsible for removing all game objects and setting up the
	 * next level.
	 */
	public void nextLevel()
	{
		level++;
		if (level > 2) level = 1;
		
		for (GameObject gameObject : placedGameObjects)
		{
			deleteGameObject(gameObject);
		}
		
		createTileEnvironment();
		addPacman(100, 260);
		addGhosts();
		addPointsAndPowerUps();
	}

	public GameTiles getGameTiles()
	{
		return gameTiles;
	}

	@Override
	public void triggerAlarm(int alarmID) {
		resetMap();
	}
	
	public void setOnPlacedGameObjects(GameObject gameObject)
	{
		placedGameObjects.add(gameObject);
	}
	
	public void removeFromPlacedGameObjects(GameObject gameObject)
	{
		placedGameObjects.remove(gameObject);
	}

	public boolean getIsResetting()
	{
		return resetting;
	}

	public void setIsResetting(boolean value)
	{
		resetting = value;
	}
}

