/**
 * This is the starting point of your game.
 * 
 * Please rename this class and the project to your own game name.
 * To rename the class and the project,
 * right click on the class or the project, and go to 'Refractor -> rename'
 * or select the project or class and press Alt+Shift+R
 * 
 * (you can delete this comment)
 */
package game;

import android.content.Intent;
import android.gameengine.icadroids.dashboard.DashboardTextView;
import android.gameengine.icadroids.engine.GameEngine;
import android.gameengine.icadroids.input.MotionSensor;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.input.TouchInput;
import android.gameengine.icadroids.tiles.GameTiles;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;


public class PackmanApplication extends GameEngine 
{
	private Pacman pacman;
	
	/**
	 * Initialize the game, create objects and level
	 * 
	 * @see android.gameengine.icadroids.engine.GameEngine#initialize()
	 */
	@Override
	protected void initialize() {
		System.out.println("running pacman");

		// Set up control mechanisms to use
		TouchInput.use = false;
		MotionSensor.use = false;
		OnScreenButtons.use = true;

		createTileEnvironment();
		
		pacman = new Pacman(this, 4);
		addGameObject(pacman, 100, 240);

//		vis = new Vis(this);
//		addGameObject(vis, 120, 240);
//
//		Monster engerd = new Monster(vis);
//		addGameObject(engerd, 480, 240);
//
//		@SuppressWarnings("unused")
//		StrawberryControler sc = new StrawberryControler(this);
//		
//		// Example of how to use the Viewport, properties and zooming
		/*
			// Switch it on
			Viewport.useViewport = true;
			// Zoom in, 2x
			setZoomFactor(2f);
			// Make viewport follow the Vis
			setPlayer(vis);
			// Vis will be center screen
			setPlayerPositionOnScreen(Viewport.PLAYER_CENTER, Viewport.PLAYER_CENTER);
			// Determines how quickly viewport moves (see API for details)
			setPlayerPositionTolerance(0.8, 0.5);
		 */
		
		// Example of how to add a Dashboard to a game
//		scoreDisplay = new DashboardTextView(this);
//		scoreDisplay.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
//		scoreDisplay.setTextColor(Color.BLACK);
//		addToDashboard(scoreDisplay);
//		
//		// Example of how to add an image to the dashboard.
//		/*
//			DashboardImageView imageDisplay = new DashboardImageView(this, "bg");
//			addToDashboard(imageDisplay);
//		*/
//		
//		createDashboard();
	}
	
	
//	private void createDashboard(){
//		
//		//this.scoreDisplay.setWidgetWidth(20);
//		this.scoreDisplay.setWidgetHeight(60);
//		this.scoreDisplay.setWidgetBackgroundColor(Color.WHITE);
//		this.scoreDisplay.setWidgetX(10);
//		this.scoreDisplay.setWidgetY(10);
//		// If you want to modify the layout of a dashboard widget,
//		// you need to so so using its run method.
//		this.scoreDisplay.run(new Runnable(){
//			public void run() {
//				scoreDisplay.setPadding(10, 10, 10, 10);
//			}
//		});
//	}

	/**
	 * Create background with tiles
	 */
	private void createTileEnvironment() {
		String[] tileImagesNames = { "frame_tile", "tile5" };
		// layout: better not let the Eclipse formatter get at this...
		int[][] tilemap = {
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, 0 },
				{ 0,  -1,  -1,  -1,  1,  -1,  -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1,  -1, 0 },
				{ 0, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1, -1, -1, -1, -1,  -1,  1,  -1,  1,  -1,  1,  -1,  1,  -1,  1,  -1,  1,  -1,  1, -1, -1, -1, -1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1, -1, -1, -1, -1,  1,  -1,  1,  -1,  1,  -1,  1,  -1,  1,  -1,  1,  -1,  1,  -1, -1, -1, -1, -1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, 0 },
				{ 0,  1,  1,  1,  -1,  1,  1,  1,  1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  1,  1,  1,  1,  1,  1,  1,  1, 0 },
				{ 0, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, 0 },
				{ 0, -1, -1, -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1,  -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1,  -1, -1, -1, -1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
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
//		this.scoreDisplay.setTextString(
//				"Score: " + String.valueOf(this.vis.getScore()));
	}

}
