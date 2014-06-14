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

	private ArrayList<GameObject> placedGameObjects;

	private PointController pointController;
	private ArrayList<NormalPoint> normalPoints;
	private ArrayList<SpecialPoint> specialPoints;

	private int[][] tileMap;
	private int level;

	@Override
	protected void initialize() {

		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;

		level = 1;

		createTileEnvironment();

		normalPoints = new ArrayList<NormalPoint>();
		specialPoints = new ArrayList<SpecialPoint>();

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

	private void addPointsAndPowerUps() {
		pointController = new PointController(this);
		pointController.placeNormalPoints();
		pointController.placePowerUps();
	}

	private void addGhosts() {
		//placedGameObjects.add(new RedEnemy(pacman, this, 280, 260, 0, 4, 100));
		//placedGameObjects.add(new OrangeEnemy(pacman, this, 300, 260, 5, 4, 100));
		//placedGameObjects.add(new GreenEnemy(pacman, this, 280, 280, 10, 4, 100));
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

	private void createDashboard(final DashboardTextView textView, int xCor, int yCor) 
	{

		// this.scoreDisplay.setWidgetWidth(20);
		textView.setWidgetHeight(60);
		textView.setWidgetBackgroundColor(Color.WHITE);
		textView.setWidgetX(xCor);
		textView.setWidgetY(yCor);

		// If you want to modify the layout of a dashboard widget,
		// you need to so so using its run method.
		textView.run(new Runnable() {
			public void run() {
				textView.setPadding(10, 10, 10, 10);
			}
		});
	}

	private void addPacman(int x, int y)
	{
		pacman = new Pacman(this, 4, x, y, 3);
		placedGameObjects.add(pacman);
		addGameObject(pacman, pacman.getXcor() , pacman.getYcor());
	}

	/**
	 * Create background with tiles
	 */
	private void createTileEnvironment() 
	{
		String[] tileImagesNames = LevelGenerator.createImagesNames();
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
			endGame();
		}
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
		for (GameObject gameObject : normalPoints)
		{
			deleteGameObject(gameObject);
		}
		if (!specialPoints.isEmpty())
		{
			for (GameObject gameObject : specialPoints)
			{
				deleteGameObject(gameObject);
			}
		}
//		deleteGameObject(pacman);
//		items.remove(pacman);
//		this.pacman = null;

		for (GameObject gameObject : placedGameObjects)
		{
			items.remove(gameObject);
			deleteGameObject(gameObject);
		}

//		deleteGameObject(redEnemy);
//		deleteGameObject(blueEnemy);
//		deleteGameObject(greenEnemy);
//		deleteGameObject(orangeEnemy);

		addPacman(100, 260);
		addGhosts();
		addPointsAndPowerUps();

	}

	/**
	 * This method stops all movable game objects and sets an alarm. This happens
	 * when pacman hits one of the ghosts. This is to put a pause between hitting a 
	 * ghost and resetting the game field. 
	 */
	// TODO move the setspeed to the objects themselves after collision.
	// TODO Pacman can still be controlled during the freeze, this shouldn't be allowed.
	public void freezeMap()
	{	
		pacman.setSpeed(0);
//		redEnemy.setSpeed(0);
//		greenEnemy.setSpeed(0);
//		blueEnemy.setSpeed(0);
//		orangeEnemy.setSpeed(0);

		alarm = new Alarm(1, 30, this);
		alarm.startAlarm();
	}

	/**
	 * Responsible for resetting the game field. Al ghosts and pacman are set back
	 * to there starting positions. The dots eaten and points will stay the same.
	 */
	private void resetMap()
	{
		pacman.jumpToStartPosition();
		pacman.setDirection(Direction.RIGHT.getValue());

		for (GameObject gameObject : placedGameObjects )
		{
			gameObject.jumpToStartPosition();

			if (gameObject instanceof Enemy) 
			{	
				((Enemy)gameObject).setDirection(Direction.UP.getValue());
			}
		}

//		redEnemy.jumpToStartPosition();
//		redEnemy.setDirection(Direction.UP.getValue());
//		greenEnemy.jumpToStartPosition();
//		greenEnemy.setDirection(Direction.UP.getValue());
//		blueEnemy.jumpToStartPosition();
//		blueEnemy.setDirection(Direction.UP.getValue());
//		orangeEnemy.jumpToStartPosition();
//		orangeEnemy.setDirection(Direction.UP.getValue());

		resetting = false;

		deleteAlarm(alarm);
	}

	public GameTiles getGameTiles()
	{
		return gameTiles;
	}

	@Override
	public void triggerAlarm(int alarmID) {
		resetMap();
	}

	public void setOnNormalPointList(NormalPoint point)
	{
		normalPoints.add(point);
	}

	public void setOnSpecialpointlist(SpecialPoint point) {
		specialPoints.add(point);
	}

	public void setOnPlacedGameObjects(GameObject gameObject)
	{
		placedGameObjects.add(gameObject);
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