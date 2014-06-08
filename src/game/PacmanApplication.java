package game;

import game.Creature.Direction;

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
	private Enemy redEnemy, orangeEnemy, greenEnemy, blueEnemy;
	private Alarm alarm;
	protected boolean resetting;
	
	private PointController pointController;
	private ArrayList<NormalPoint> normalPoints;
	private ArrayList<SpecialPoint> specialPoints;
	
	private LevelGenerator generator;
	private int[][] tileMap;
	private int level;
	 


	@Override
	protected void initialize() {

		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;
		
		level = 1;
		generator = new LevelGenerator();
		
		createTileEnvironment();
		
		normalPoints = new ArrayList<NormalPoint>();
		specialPoints = new ArrayList<SpecialPoint>();
		
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
		redEnemy = new RedEnemy(pacman, 280, 260);
		addGameObject(redEnemy, redEnemy.getXcor(), redEnemy.getYcor());
		
		orangeEnemy = new OrangeEnemy(pacman, 300, 260);
		addGameObject(orangeEnemy, orangeEnemy.getXcor(), orangeEnemy.getYcor());
		
		greenEnemy = new GreenEnemy(pacman, 280, 280);
		addGameObject(greenEnemy, greenEnemy.getXcor(), greenEnemy.getYcor());
		
		blueEnemy = new BlueEnemy(pacman, 300, 280);
		addGameObject(blueEnemy, blueEnemy.getXcor(), blueEnemy.getYcor());
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
		pacman = new Pacman(this, 4, x, y);
		addGameObject(pacman, pacman.getXCor(), pacman.getYCor());
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
		deleteGameObject(pacman);
		items.remove(pacman);
		this.pacman = null;
		deleteGameObject(redEnemy);
		deleteGameObject(blueEnemy);
		deleteGameObject(greenEnemy);
		deleteGameObject(orangeEnemy);
		
		addPacman(100, 260);
		addGhosts();
		addPointsAndPowerUps();
		
	}
	
	public void freezeMap()
	{	
		pacman.setSpeed(0);
		redEnemy.setSpeed(0);
		greenEnemy.setSpeed(0);
		blueEnemy.setSpeed(0);
		orangeEnemy.setSpeed(0);
		
		alarm = new Alarm(1, 30, this);
		alarm.startAlarm();
	}
	
	private void resetMap()
	{
		pacman.jumpToStartPosition();
		pacman.setDirection(Direction.RIGHT.getValue());
		redEnemy.jumpToStartPosition();
		redEnemy.setDirectionSpeed(Direction.UP.getValue(), 4);
		greenEnemy.jumpToStartPosition();
		greenEnemy.setDirectionSpeed(Direction.UP.getValue(), 4);
		blueEnemy.jumpToStartPosition();
		blueEnemy.setDirectionSpeed(Direction.UP.getValue(), 4);
		orangeEnemy.jumpToStartPosition();
		orangeEnemy.setDirectionSpeed(Direction.UP.getValue(), 4);
		
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

}
