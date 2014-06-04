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
	private int level = 2;
	
	@Override
	protected void initialize() {
		System.out.println("running pacman");

		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;
		
		
		
		createTileEnvironment();
		
		pacman = new Pacman(this, 2);
		addGameObject(pacman, 40, 40);
		
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
		this.scoreDisplay.setWidgetX(0);
		this.scoreDisplay.setWidgetY(0);
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
		String[] tileImagesNames = { "cornertopleft", "cornertopright", "cornerbottomleft", "cornerbottomright", "vertical", "horizontal", "internleft", "internright", "interntop", "internbottom", "wall_tile" };
		// layout: better not let the Eclipse formatter get at this...
		
		int[][] tilemap = {
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
				{10, 0, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 1, 10 },
				{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 },
				{10, 4,-1,  0,  5,  5,  1, -1,  0,  5,  5,  5,  1, -1,  4, -1,  0,  5,  5,  5,  1, -1,  0,  5,  5,  1, -1, 4, 10 },
				{10, 4,-1,  4, -1, -1,  4, -1,  4, -1, -1, -1,  4, -1,  4, -1,  4, -1, -1, -1,  4, -1,  4, -1, -1,  4, -1, 4, 10 }, 
				{10, 4,-1,  2,  5,  5,  3, -1,  2,  5,  5,  5,  3, -1,  9, -1,  2,  5,  5,  5,  3, -1,  2,  5,  5,  3, -1, 4, 10 },
				{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 },
				{10, 4,-1,  6,  5,  5,  7, -1,  8, -1,  6,  5,  5,  5,  5,  5,  5,  5,  7, -1,  8, -1,  6,  5,  5,  7, -1, 4, 10 },
				{10, 4,-1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, 4, 10 },
				{10, 2, 5,  5,  5,  5,  1, -1,  4,  5,  5,  5,  7, -1,  4, -1,  6,  5,  5,  5,  4, -1,  0,  5,  5,  5,  5, 3, 10 },
				{10,-1,-1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
				{10,-1,-1, -1, -1, -1,  4, -1,  4, -1,  0,  5,  5,  5,  5,  5,  5,  5,  1, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
				{10, 5, 5,  5,  5,  5,  3, -1,  9, -1,  4, -1, -1, -1, -1, -1, -1, -1,  4, -1,  9, -1,  2,  5,  5,  5,  5, 5, 10 },
				{10,-1,-1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1,-1, 10 },
				{10, 5, 5,  5,  5,  5,  1, -1,  8, -1,  4, -1, -1, -1, -1, -1, -1, -1,  4, -1,  8, -1,  0,  5,  5,  5,  5, 5, 10 },
				{10,-1,-1, -1, -1, -1,  4, -1,  4, -1,  2,  5,  5,  5,  5,  5,  5,  5,  3, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
				{10,-1,-1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1,  4, -1, -1, -1, -1,-1, 10 },
				{10, 0, 5,  5,  5,  5,  3, -1,  9, -1,  6,  5,  5,  5,  5,  5,  5,  5,  7, -1,  9, -1,  2,  5,  5,  5,  5, 1, 10 },
				{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 },
				{10, 4,-1,  6,  5,  5,  1, -1,  6,  5,  5,  5,  7, -1,  9, -1,  6,  5,  5,  5,  7, -1,  0,  5,  5,  7, -1, 4, 10 },
				{10, 4,-1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, 4, 10 },
				{10, 4, 5,  5,  7, -1,  9, -1,  8, -1,  6,  5,  5,  5,  5,  5,  5,  5,  7, -1,  8, -1,  9, -1,  6,  5,  5, 4, 10 },
				{10, 4,-1, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1,  4, -1, -1, -1, -1, -1, -1, 4, 10 },
				{10, 4,-1,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, -1,  9, -1,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, -1, 4, 10 },
				{10, 4,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 10 },
				{10, 2, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 3, 10 },
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
		};
		
		
		
		GameTiles myTiles = new GameTiles(tileImagesNames, tilemap, 20);
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
		this.scoreDisplay.setTextString(
				"Score: " + String.valueOf(this.pacman.getScore()));
	}

}
