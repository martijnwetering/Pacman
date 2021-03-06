package game.creatures;

import game.PacmanApplication;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public class OrangeEnemy extends Enemy implements ICollision
{

	public OrangeEnemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, 
			int numberOfDotsToActivate, int speed, int points) 
	{
		super(pacman, app, xCor, yCor, numberOfDotsToActivate, speed, points);
		setSprite("orange_strip3", 3);
		setFrameNumber(0);
	}

	/**
	 * This ghost always go's a random direction when it hits a wall.
	 */
	@Override
	public void move(List<TileCollision> collidedTiles) 
	{
		for (TileCollision tc : collidedTiles)
		{
			
			int tileType = tc.theTile.getTileType();
			
			boolean isWall = false;
			// 11 and 12 are the only tiles that are not walls.
			for (int i = 0; i < 11; i++)
			{
				if (tileType == i) isWall = true;
			}
			
			if (tileType == 13 && tc.collisionSide == 0)
			{
				moveUpToTileSide(tc);
				setDirection(Direction.RIGHT.getValue());
				return;
			}
			
			if (isWall)
			{
				moveUpToTileSide(tc);
				setDirection(randomDirection());
				return;
			}
		}
	}
	
	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) 
	{
		move(collidedTiles);
	}
		
	@Override
	public void setDefaultSprite() {
		setSprite("orange_strip3", 3);
	}

}
