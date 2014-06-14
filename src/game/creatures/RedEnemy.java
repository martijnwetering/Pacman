package game.creatures;

import game.PacmanApplication;
import game.creatures.Creature.Direction;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.TileCollision;

public class RedEnemy extends Enemy 
{

	public RedEnemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, int numberOfDotsToActivate, int speed) 
	{
		super(pacman, app, xCor, yCor, numberOfDotsToActivate, speed);
		setSprite("red_strip3", 3);
		setFrameNumber(0);
	}

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
				// If the previous collision also was a wall it sets a random direction.
				// This is to make sure the ghost doesn't keep making circles in certain
				// area's of the game field.
				collided = true;
				if (getDirection() == Direction.UP.getValue()) 
				{
					if (collided == true)
					{
						setDirection(randomDirection());
					}
					else 
					{
						setDirection(Direction.RIGHT.getValue());
				
					}
				}
				else if (getDirection() == Direction.RIGHT.getValue()) 
				{
					if (collided == true)
					{
						setDirection(randomDirection());
					}
					else 
					{
						setDirection(Direction.DOWN.getValue());
				
					}
				}
				
				else if (getDirection() == Direction.DOWN.getValue()) 
				{
					if (collided == true)
					{
						setDirection(randomDirection());
					}
					else 
					{
						setDirection(Direction.LEFT.getValue());
					}
				}
				else if (getDirection() == Direction.LEFT.getValue())
				{
					if (collided == true)
					{
						setDirection(randomDirection());
					}
					else 
					{
						setDirection(Direction.RIGHT.getValue());
				
					}
				}
				
				collided = true;
				return;
			}
			else
			{
				collided = false;
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
		setSprite("red_strip3", 3);
	}

}
