package game.creatures;

import game.PacmanApplication;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public class GreenEnemy extends Enemy implements ICollision
{

	public GreenEnemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, 
			int numberOfDotsToActivate, int speed, int points) 
	{
		super(pacman, app, xCor, yCor, numberOfDotsToActivate, speed, points);
		setSprite("green_strip3", 3);
		setFrameNumber(0);
	}

	/**
	 * This ghost always moves a random direction, also on intersections
	 * where it does not hit a wall.
	 */
	@Override
	public void move(List<TileCollision> collidedTiles) 
	{
		for (TileCollision tc : collidedTiles)
		{
			
			int tileType = tc.theTile.getTileType();
			
			boolean isWall = false;
			boolean invisibleWall = false;
			// 11 and 12 are the only tiles that are not walls.
			for (int i = 0; i < 11; i++)
			{
				if (tileType == i) isWall = true;
			}
			if (tileType == 14) 
			{
				invisibleWall = true;
			}
			
			// Gate.
			if (tileType == 13 && tc.collisionSide == 0)
			{
				int direction = Direction.RIGHT.getValue();
				currentDirection = direction;
				moveUpToTileSide(tc);
				setDirection(direction);
			}
			
			else if (isWall)
			{
				int direction = randomDirection();
				currentDirection = direction;
				moveUpToTileSide(tc);
				setDirection(direction);
			}
			else if (invisibleWall)
			{
				int direction = randomDirection();
				setDirectionSpeed(direction, speed);
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
		setSprite("green_strip3", 3);
	}

}

