package game;

import java.util.List;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public class Creature extends MoveableGameObject implements ICollision
{
	
	
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
