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

public class PointController 
{
	final static int SPECIALPOINTONE = 1;
	final static int SPECIALPOINTTWO = 2;
	
	private PacmanApplication app;
	private Alarm alarm;
	private Tile[][] tiles;
	private int numberOfNormalPoints;
	private int firstSpecialPointPlaced;
	private int secondSpecialPointPlaced;
	
	private ArrayList<Position> specialPointsCords;
	
	
	
	public PointController(PacmanApplication app) 
	{
		this.app = app;
		tiles = app.getGameTiles().getTileArray();
		numberOfNormalPoints = 0;
		firstSpecialPointPlaced = 0;
		secondSpecialPointPlaced = 0;
	
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
				
				
					if (tileType == 11 || tileType == 14)
					{
						numberOfNormalPoints++;
						NormalPoint normalPoint = new NormalPoint(app);
						app.setOnNormalPointList(normalPoint);
						app.addGameObject(normalPoint, xCor, yCor);
				
					}
				}
			}
		}
	}
	
	/**
	 * This method will place place a maximum of two special points. It will
	 * only place the special point if it hasn't been placed before and it's
	 * triggered after pacman ate a certain number of normal points.
	 */
	public void placeSpecialPoint(int kindOfSpecialPoint)
	{
		if (kindOfSpecialPoint == SPECIALPOINTONE && firstSpecialPointPlaced == 0 
				|| kindOfSpecialPoint == SPECIALPOINTTWO && secondSpecialPointPlaced == 0)
		{
			SpecialPoint specialPoint = new SpecialPoint(app);
			
			Random randomNumberGenerator = new Random();
			int randomNumber = randomNumberGenerator.nextInt(9);
			
			Position point =  specialPointsCords.get(randomNumber);
			int xCor = point.getXCor();
			int yCor = point.getYCor();
			
			app.addGameObject(specialPoint, xCor, yCor);
			
			if (kindOfSpecialPoint == SPECIALPOINTONE) firstSpecialPointPlaced = 1;
			if (kindOfSpecialPoint == SPECIALPOINTTWO) secondSpecialPointPlaced = 1;
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
	
	private void initializeSpecialPointsCords()
	{
		int xCor = 200;
		int yCor = 320;
		
		specialPointsCords = new ArrayList<Position>();
		for (int i = 0; i < 9; i++)
		{
			Position point = new Position(xCor, yCor);
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
}


