package game.creatures;

import game.PacmanApplication;
import game.creatures.Creature.Direction;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.TileCollision;

public class BlueEnemy extends Enemy 
{
	public BlueEnemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, int numberOfDotsToActivate, int speed) 
	{
		super(pacman, app, xCor, yCor, numberOfDotsToActivate, speed);
		setSprite("blue_strip3", 3);
		setFrameNumber(0);
	}

	@Override
	public void move(List<TileCollision> collidedTiles) 
	{
		for (TileCollision tc : collidedTiles)
		{
			
			int tileType = tc.theTile.getTileType();
			
			boolean isWall = false;
			// 0 until 10 tile types are walls.
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
		setSprite("blue_strip3", 3);
	}

}
