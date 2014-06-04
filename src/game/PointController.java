package game;

import java.util.Random;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.tiles.Tile;
import android.graphics.Rect;
import java.util.Vector;
import android.util.Log;

public class PointController implements IAlarm
{
	private PacmanApplication app;
	private Alarm alarm;
	private Tile[][] tiles;
	
	
	
	public PointController(PacmanApplication app) 
	{
		this.app = app;
		alarm = new Alarm(1, 30, this);
		alarm.startAlarm();
		tiles = app.getGameTiles().getTileArray();
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
						Log.d("Normal Point", "placing normal point");
						NormalPoint normalPoint = new NormalPoint(app);
						app.addGameObject(normalPoint, xCor, yCor);
				
					}
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
	void placeSpecialPoint()
	{
		SpecialPoint specialPoint = new SpecialPoint(app);
		
		Random randomNumberGenerator = new Random();
		int randomNumberOne = randomNumberGenerator.nextInt(20);
		int randomNumberTwo = randomNumberGenerator.nextInt(20);
		
		int x = 20 * randomNumberOne;
		int y = 20 * randomNumberTwo;
				
		if(checkIfPosIsWall(x, y) == false)
		{
			// Checks if there is already a normal point on the specified
			// coordinates. If there is one, it will be removed.
			Rect rectangle = new Rect(x, y, x + 20, y + 20);
			Vector<GameObject> gameObjectList = app.findItemAt(rectangle);
			if (gameObjectList.size() != 0)
			{
				GameObject gameObject = gameObjectList.firstElement();
				app.deleteGameObject(gameObject);
			}
			// Adds the gameObject to the world if the underlaying tile is a
			// background tile.
			app.addGameObject(specialPoint, x, y);
		}
		else
		{
			placeSpecialPoint();
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
					
					if (tileType != 11)
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
		alarm.setTime(200);
		alarm.restartAlarm();
	}
}
