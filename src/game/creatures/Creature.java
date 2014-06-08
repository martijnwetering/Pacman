package game.creatures;

import game.PacmanApplication;

import java.util.List;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public class Creature extends MoveableGameObject implements ICollision
{
	protected PacmanApplication app;
	
	public Creature(PacmanApplication app)
	{
		this.app = app;
	}
	
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
	
	
	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) {
		for (TileCollision tc : collidedTiles)
		{
			if (tc.theTile.getTileType() == 0)
			{
				moveUpToTileSide(tc);
				setSpeed(0);
				return;
			}
		}
	}

}