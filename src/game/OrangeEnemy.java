package game;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.TileCollision;

public class OrangeEnemy extends Enemy 
{

	public OrangeEnemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, int numberOfDotsToActivate) 
	{
		super(pacman, app, xCor, yCor, numberOfDotsToActivate);
		setSprite("orange_strip3", 3);
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
				setDirection(randomDirection());
				return;
			}
		}
	}
	
	@Override
	public void update()
	{
		super.update();
	}
	
	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) 
	{
		move(collidedTiles);
	}
	
	private int randomDirection()
	{
		int[] directions = {0, 90, 180, 270};
		Random randomNumberGenerator = new Random();
		int randomDirection = randomNumberGenerator.nextInt(4);
		
		return directions[randomDirection];
	}
	
	@Override
	public void setDefaultSprite() {
		setSprite("orange_strip3", 3);
	}

}
