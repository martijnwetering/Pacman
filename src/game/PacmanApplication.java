package game;

import android.gameengine.icadroids.dashboard.DashboardTextView;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.input.MotionSensor;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.input.TouchInput;
import android.gameengine.icadroids.tiles.GameTiles;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;


public class PacmanApplication extends GameEngine 
{
	private Pacman pacman;
	private DashboardTextView scoreDisplay;
	private GameTiles gameTiles;
	
	@Override
	protected void initialize() {
		System.out.println("running pacman");

		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;


		createTileEnvironment();
		
		pacman = new Pacman(this, 4);
		addGameObject(pacman, 100, 260);
		
		PointController pointController = new PointController(this);
		pointController.placeNormalPoints();
		
		scoreDisplay = new DashboardTextView(this);
		scoreDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
		scoreDisplay.setTextColor(Color.BLACK);
		addToDashboard(scoreDisplay);

		createDashboard();		
	}
	
	private void createDashboard() {

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
	
	


	/**
	 * Create background with tiles
	 */
	private void createTileEnvironment() {
		String[] tileImagesNames = { "cornertopleft", "cornertopright", "cornerbottomleft", "cornerbottomright", "vertical", "horizontal", "internleft", "internright", "interntop", "internbottom", "wall_tile", "background_tile" };
		// layout: better not let the Eclipse formatter get at this...
		int[][] testMap = {
				{  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
				{  0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2,  1,  1,  1,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2,  0 },
				{  0, 2,  1,  1, 2,  1,  1,  1, 2,  1, 2,  1, 2, 2, 2, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2,  0 },
				{  0, 2,  1,  1, 2,  1,  1,  1, 2,  1, 2,  1,  1, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2,  0 },
				{  0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0, 2,  1,  1, 2,  1, 2,  1,  1,  1,  1,  1,  1, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2,  0 },
				{  0, 2, 2, 2, 2,  1, 2,  1,  1,  1,  1,  1,  1, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2,  0 },
				{  0,  1,  1,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2,  0 },
				{  0, 2, 2,  1, 2,  1,  1,  1, 2,  1,  1,  1,  1, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2,  0 },
				{  0, 2, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  1,  1, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0,  1,  1,  1, 2,  1, 2,  1,  1,  1,  1, 2, 2,  1,  1,  1,  1, 2,  1, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0, 2, 2, 2, 2,  1, 2,  1,  1, 2, 2, 2, 2, 2, 2,  1,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0,  1,  1,  1, 2, 2, 2,  1,  1, 2, 2, 2, 2, 2, 2,  1,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0, 2, 2,  1, 2,  1, 2,  1,  1, 2, 2, 2, 2, 2, 2,  1,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0, 2, 2,  1, 2,  1, 2,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0,  1,  1,  1, 2,  1, 2,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 2, 2, 2, 2,  1,  1,  1,  1,  1,  1,  1,  1,  0 },
				{  0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0,  1,  1,  1, 2,  1,  1,  1, 2,  1,  1,  1, 2,  1,  1,  1,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0, 2, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,  0 },
				{  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0 },
		};
		int[][] tilemap = {
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
				{10, 0, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 1, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  0,  5,  5,  1, 11,  0,  5,  5,  5,  1, 11,  4, 11,  0,  5,  5,  5,  1, 11,  0,  5,  5,  1, 11, 4, 10 },
				{10, 4,11,  4, 11, 11,  4, 11,  4, 11, 11, 11,  4, 11,  4, 11,  4, 11, 11, 11,  4, 11,  4, 11, 11,  4, 11, 4, 10 }, 
				{10, 4,11,  2,  5,  5,  3, 11,  2,  5,  5,  5,  3, 11,  9, 11,  2,  5,  5,  5,  3, 11,  2,  5,  5,  3, 11, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  6,  5,  5,  7, 11,  8, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  8, 11,  6,  5,  5,  7, 11, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 2, 5,  5,  5,  5,  1, 11,  4,  5,  5,  5,  7, 11,  4, 11,  6,  5,  5,  5,  4, 11,  0,  5,  5,  5,  5, 3, 10 },
				{10,11,11, 11, 11, 11,  4, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11,  4, 11, 11, 11, 11,11, 10 },
				{10,11,11, 11, 11, 11,  4, 11,  4, 11,  0,  5,  5,  5,  5,  5,  5,  5,  1, 11,  4, 11,  4, 11, 11, 11, 11,11, 10 },
				{10, 5, 5,  5,  5,  5,  3, 11,  9, 11,  4, 11, 11, 11, 11, 11, 11, 11,  4, 11,  9, 11,  2,  5,  5,  5,  5, 5, 10 },
				{10,11,11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11,11, 10 },
				{10, 5, 5,  5,  5,  5,  1, 11,  8, 11,  4, 11, 11, 11, 11, 11, 11, 11,  4, 11,  8, 11,  0,  5,  5,  5,  5, 5, 10 },
				{10,11,11, 11, 11, 11,  4, 11,  4, 11,  2,  5,  5,  5,  5,  5,  5,  5,  3, 11,  4, 11,  4, 11, 11, 11, 11,11, 10 },
				{10,11,11, 11, 11, 11,  4, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11,  4, 11, 11, 11, 11,11, 10 },
				{10, 0, 5,  5,  5,  5,  3, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  9, 11,  2,  5,  5,  5,  5, 1, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  6,  5,  5,  1, 11,  6,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  7, 11,  0,  5,  5,  7, 11, 4, 10 },
				{10, 4,11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 4, 10 },
				{10, 4, 5,  5,  7, 11,  9, 11,  8, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  8, 11,  9, 11,  6,  5,  5, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, 11, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 2, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 3, 10 },
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
		};
		gameTiles = new GameTiles(tileImagesNames, tilemap, 20);
		setTileMap(gameTiles);
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
		this.scoreDisplay.setTextString(
				"Score: " + String.valueOf(this.pacman.getScore()));
	}
	
	public GameTiles getGameTiles()
	{
		return gameTiles;
	}

}
