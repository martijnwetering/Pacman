package game;

import java.util.ArrayList;
import java.util.Random;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Rect;
import java.util.Vector;
import android.util.Log;
import game.utilities.*;

public class PointController implements IAlarm
{
	private PacmanApplication app;
	private Alarm alarm;
	private Tile[][] tiles;
	private int numberOfNormalPoints;
	
	private ArrayList<PointPosition> specialPointsCords;
	
	
	
	public PointController(PacmanApplication app) 
	{
		this.app = app;
		alarm = new Alarm(1, 30, this);
		alarm.startAlarm();
		tiles = app.getGameTiles().getTileArray();
		numberOfNormalPoints = 0;
	
		initializeSpecialPointsCords();
	}

	public void placeNormalPoints() 
	{
		for (int i = 0; i < tiles.length; i++ )
		{
			for (int j = 0; j < tiles[i].length; j++)
			{
				if (tiles[i][j] != null)
				{
					Tile tile = tiles[i][j];
					int xCor = tile.getTileX();
					int yCor = tile.getTileY();
					int tileType = tile.getTileType();
					
					Log.d("tileType", Integer.toString(tileType));				
				
					if (tileType == 11)
					{
						numberOfNormalPoints++;
						NormalPoint normalPoint = new NormalPoint(app);
						app.addGameObject(normalPoint, xCor, yCor);
					}
					/*if (tileType == 12)
					{
						Log.d("PowerUps", "placing powerUps");
						PowerUp powerUp = new PowerUp(app);
						app.addGameObject(powerUp, xCor, yCor);				
					}*/
				}
			}
		}
	}
	
	/**
	 * This method is called every time the alarm go's off. It checks
	 * if it can place a point on a random location, if the location is
	 * already occupied by a tile on which no points are allowed, the
	 * method will call itself again until it can place a point.
	 */
	public void placeSpecialPoint()
	{
		SpecialPoint specialPoint = new SpecialPoint(app);
		
		Random randomNumberGenerator = new Random();
		int randomNumber = randomNumberGenerator.nextInt(9);
		
		PointPosition point =  specialPointsCords.get(randomNumber);
		int xCor = point.getXCor();
		int yCor = point.getYCor();
		
		app.addGameObject(specialPoint, xCor, yCor);
				
		
	}
	
	public void placePowerUps() 
	{
		for (int i = 0; i < tiles.length; i++ )
		{
			for (int j = 0; j < tiles[i].length; j++)
			{
				if (tiles[i][j] != null)
				{
					Tile tile = tiles[i][j];
					int xCor = tile.getTileX();
					int yCor = tile.getTileY();
					int tileType = tile.getTileType();
					
					Log.d("tileType", Integer.toString(tileType));				
				    
					if (tileType == 12)
					{
						Log.d("PowerUps", "placing powerUps");
						PowerUp powerUp = new PowerUp(app);
						app.addGameObject(powerUp, xCor, yCor);				
					}
				}
			}
		}
	}
	
	/**
	 * Checks if the given x and y coordinates are already occupied
	 * by a wall tile.
	 * 
	 * @param xCor
	 * @param yCor
	 * @return
	 */
	public boolean checkIfPosIsWall(int xCor, int yCor)
	{
		for (int i = 0; i < tiles.length; i++ )
		{
			for (int j = 0; j < tiles[i].length; j++)
			{
				if (tiles[i][j] != null)
				{
					Tile tile = tiles[i][j];
					int tileX = tile.getTileX();
					int tileY = tile.getTileY();
					int tileType = tile.getTileType();
					
					if (tileType != 11 || tileType != 12)
					{ 
						if (xCor == tileX && yCor == tileY)
						{
							return true;
						}					  
					}
				}
			}
		}
		return false;
	}

	@Override
	public void triggerAlarm(int alarmID) {
		placeSpecialPoint();
		alarm.setTime(2);
		alarm.restartAlarm();
	}
	
	private void initializeSpecialPointsCords()
	{
		int xCor = 200;
		int yCor = 320;
		
		specialPointsCords = new ArrayList<PointPosition>();
		for (int i = 0; i < 9; i++)
		{
			PointPosition point = new PointPosition(xCor, yCor);
			specialPointsCords.add(point);
			xCor += 20;
		}
	}
	
	// Return the number of normal points the player needs to acquire 
	// to win.
	public int getNumberOfNormalPoints()
	{
		return numberOfNormalPoints;
	}
	
	public Alarm getAlarm()
	{
		return alarm;
	}
}


