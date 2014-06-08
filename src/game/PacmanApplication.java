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
	private DashboardTextView livesDisplay;
	private GameTiles gameTiles;
	private PointController pointController;
	private int level = 1;
	private Enemy redEnemy, orangeEnemy, greenEnemy, blueEnemy;
	private Alarm alarm;
	protected boolean resetting;
	private ArrayList<NormalPoint> normalPoints; 
	
	@Override
	protected void initialize() {
		System.out.println("running pacman");

		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;

		createTileEnvironment();
		
		normalPoints = new ArrayList<NormalPoint>();
		
		addPacman(100, 260);
		
		redEnemy = new RedEnemy(pacman, 280, 260);
		addGameObject(redEnemy, redEnemy.getXcor(), redEnemy.getYcor());
		
		orangeEnemy = new OrangeEnemy(pacman, 300, 260);
		addGameObject(orangeEnemy, orangeEnemy.getXcor(), orangeEnemy.getYcor());
		
		greenEnemy = new GreenEnemy(pacman, 280, 280);
		addGameObject(greenEnemy, greenEnemy.getXcor(), greenEnemy.getYcor());
		
		blueEnemy = new BlueEnemy(pacman, 300, 280);
		addGameObject(blueEnemy, blueEnemy.getXcor(), blueEnemy.getYcor());
		
		pointController = new PointController(this);
		pointController.placeNormalPoints();
		
		scoreDisplay = new DashboardTextView(this);
		scoreDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
		scoreDisplay.setTextColor(Color.BLACK);
		addToDashboard(scoreDisplay);
		
		createDashboard(scoreDisplay, 590, 5);
		
		resetting = false;
		
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
		String[] tileImagesNames = { "cornertopleft", "cornertopright", "cornerbottomleft", 
				"cornerbottomright", "vertical", "horizontal", "internleft", 
				"internright", "interntop", "internbottom", "wall_tile", 
				"background_tile", "no_gameobject", "gate", "invisible_wall" };
		int[][] tilemap;
		// layout: better not let the Eclipse formatter get at this...
		if(level == 1){
		tilemap = new int[][] {
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
				{10, 0, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 1, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  0,  5,  5,  1, 11,  0,  5,  5,  5,  1, 11,  4, 11,  0,  5,  5,  5,  1, 11,  0,  5,  5,  1, 12, 4, 10 },
				{10, 4,11,  4, 12, 12,  4, 11,  4, 12, 12, 12,  4, 11,  4, 11,  4, 12, 12, 12,  4, 11,  4, 12, 12,  4, 12, 4, 10 }, 
				{10, 4,11,  2,  5,  5,  3, 14,  2,  5,  5,  5,  3, 14,  9, 14,  2,  5,  5,  5,  3, 14,  2,  5,  5,  3, 12, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 4,14,  6,  5,  5,  7, 14,  8, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  8, 14,  6,  5,  5,  7, 14, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 2, 5,  5,  5,  5,  1, 11,  4,  5,  5,  5,  7, 11,  4, 11,  6,  5,  5,  5,  4, 11,  0,  5,  5,  5,  5, 3, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11, 11, 11, 11, 11, 14, 11, 11, 11, 11, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11,  0,  5,  5, 13, 13, 13,  5,  5,  1, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10, 5, 5,  5,  5,  5,  3, 14,  9, 14,  4, 12, 12, 13, 13, 13, 12, 12,  4, 14,  9, 14,  2,  5,  5,  5,  5, 5, 10 },
				{10,11,11, 11, 11, 11, 11, 11, 14, 11,  4, 12, 12, 12, 12, 12, 12, 12,  4, 11, 14, 11, 11, 11, 11, 11, 11,11, 10 },
				{10, 5, 5,  5,  5,  5,  1, 14,  8, 14,  4, 12, 12, 12, 12, 12, 12, 12,  4, 14,  8, 14,  0,  5,  5,  5,  5, 5, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11,  2,  5,  5,  5,  5,  5,  5,  5,  3, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10, 0, 5,  5,  5,  5,  3, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  9, 11,  2,  5,  5,  5,  5, 1, 10 },
				{10, 4,11, 11, 11, 14, 11, 11, 14, 11, 14, 11, 11, 11,  4, 11, 11, 11, 14, 11, 14, 11, 14, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  6,  5,  5,  1, 11,  6,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  7, 11,  0,  5,  5,  7, 11, 4, 10 },
				{10, 4,11, 11, 11, 11,  4, 11, 11, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 4, 10 },
				{10, 4, 5,  5,  7, 11,  9, 14,  8, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  8, 11,  9, 11,  6,  5,  5, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 14, 11, 14, 11, 11, 4, 10 },
				{10, 4,11,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, 11, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 14, 11, 14, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 2, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 3, 10 },
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
		};
		gameTiles = new GameTiles(tileImagesNames, tilemap, 20);
		setTileMap(gameTiles);
		Log.d("pacman", "GameTiles created");
		}
		if(level == 2){
		tilemap = new int[][] {
					{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
					{10, 0, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 1, 10 },
					{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 },
					{10, 4,-1,  6,  5,  5,  7, -1,  6,  5,  5,  5,  7, -1,  4, -1,  6,  5,  5,  5,  7, -1,  6,  5,  5,  7, -1, 4, 10 },
					{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 }, 
					{10, 4,-1,  0,  5,  5,  1, -1,  6,  5,  5,  5,  7, -1,  9, -1,  6,  5,  5,  5,  7, -1,  0,  5,  5,  1, -1, 4, 10 },
					{10, 4,-1,  4, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1,  4, -1, 4, 10 },
					{10, 4,-1,  2,  5,  5,  3, -1,  8, -1,  6,  5,  5,  7, -1,  6,  5,  5,  7, -1,  8, -1,  2,  5,  5,  3, -1, 4, 10 },
					{10, 4,-1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, 4, 10 },
					{10, 2, 5,  5,  5,  5,  1, -1,  4, -1,  6,  5,  5,  5,  5,  5,  5,  5,  7, -1,  4, -1,  0,  5,  5,  5,  5, 3, 10 },
					{10,-1,-1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
					{10,-1,-1, -1, -1, -1,  4, -1,  4, -1,  0,  5,  5,  5,  5,  5,  5,  5,  1, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
					{10, 5, 5,  5,  5,  5,  3, -1,  9, -1,  4, -1, -1, -1, -1, -1, -1, -1,  4, -1,  9, -1,  2,  5,  5,  5,  5, 5, 10 },
					{10,-1,-1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1,-1, 10 },
					{10, 5, 5,  5,  5,  5,  1, -1,  8, -1,  4, -1, -1, -1, -1, -1, -1, -1,  4, -1,  8, -1,  0,  5,  5,  5,  5, 5, 10 },
					{10,-1,-1, -1, -1, -1,  4, -1,  4, -1,  2,  5,  5,  5,  5,  5,  5,  5,  3, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
					{10,-1,-1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
					{10, 0, 5,  5,  5,  5,  3, -1,  9, -1,  6,  5,  5,  5,  5,  5,  5,  5,  7, -1,  9, -1,  2,  5,  5,  5,  5, 1, 10 },
					{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 },
					{10, 4,-1,  0,  5,  5,  7, -1,  6,  5,  5,  5,  7, -1,  9, -1,  6,  5,  5,  5,  7, -1,  0,  5,  5,  7, -1, 4, 10 },
					{10, 4,-1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, 4, 10 },
					{10, 4,-1,  9, -1,  8, -1, -1,  8, -1,  6,  5,  5,  5,  5,  5,  5,  5,  7, -1,  8, -1,  9, -1,  6,  5,  5, 4, 10 },
					{10, 4,-1, -1, -1,  4, -1, -1,  4, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, 4, 10 },
					{10, 4,-1,  6,  5,  3,  5,  5,  5,  5,  5,  5,  7, -1,  9, -1,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, -1, 4, 10 },
					{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 },
					{10, 2, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 3, 10 },
					{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
			};
		
		gameTiles = new GameTiles(tileImagesNames, tilemap, 20);
		setTileMap(gameTiles);
		Log.d("pacman", "GameTiles created");
		}
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
			this.deleteGameObject(gameObject);
		}
		deleteGameObject(pacman);
		deleteGameObject(redEnemy);
		deleteGameObject(blueEnemy);
		deleteGameObject(greenEnemy);
		deleteGameObject(orangeEnemy);
		
		this.initialize();
		
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

}
