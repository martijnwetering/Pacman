package game.creatures;

import game.PacmanApplication;
import game.utilities.Position;

import java.util.List;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public abstract class Creature extends MoveableGameObject
{
	private Position position;
	
	protected PacmanApplication app; 
	protected int speed;
	
	public Creature(PacmanApplication app, int xCor, int yCor, int speed)
	{
		this.app = app;
		position = new Position(xCor, yCor);
		this.speed = speed;
	}
	
	/**
	 * Can be used to set the direction of a movable game object.
	 */
	public enum Direction
	{
		UP(0), RIGHT(90), DOWN(180), LEFT(270);
		
		private final int value;
		
		private Direction(int value) 
		{
            this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	
	public int getXcor()
	{
		return position.getXCor();
	}
	
	public int getYcor()
	{
		return position.getYCor();
	}

}
