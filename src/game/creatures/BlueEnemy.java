package game.creatures;

import game.PacmanApplication;
import game.creatures.Creature.Direction;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.TileCollision;

public class BlueEnemy extends Enemy 
{
	private boolean collided1;
	private boolean collided2;
	
	public BlueEnemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, int numberOfDotsToActivate) 
	{
		super(pacman, app, xCor, yCor, numberOfDotsToActivate);
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
			if(!pacman.isHunter())
			{
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
		  //pacman is hunter
		  //spook gaat naar de rechter bovenhoek
			if(pacman.isHunter())
			{	
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
				
				////////////////////////////
				if (isWall)
				 { //Begin
				 moveUpToTileSide(tc);
				 if (getDirection() == Direction.UP.getValue())
				 {
					    if(collided1)
					    {
						     System.out.println("test1: ");
						     System.out.println("bocht : naar beneden");
						     setDirection(Direction.DOWN.getValue());
						     collided2 = true;
					    }
					    else
					    {
					    System.out.println("bocht : naar rechts");
						setDirection(Direction.RIGHT.getValue());
				        collided1 = true;
					    }
				 }
				 
				 else if (getDirection() == Direction.RIGHT.getValue())
				 {
		                 if(collided1)
		                 {
		                	 System.out.println("test2: ");
		                	 System.out.println("bocht : naar beneden");
		                	 setDirection(Direction.DOWN.getValue());
		                	 collided2 = true;
		                 }
		                 else
		                 {
		                 System.out.println("bocht : naar boven");
						 setDirection(Direction.UP.getValue()); 
						 collided1 = true;
		                 }
				 }
				
				 else if (getDirection() == Direction.LEFT.getValue())
				 {
					     if(collided1)
					     {
					    	 System.out.println("test2: ");
		                	 System.out.println("bocht : RIGHT");
		                	 setDirection(Direction.RIGHT.getValue());
		                	 collided2 = true; 
					     }
					     else
					     {
					     System.out.println("bocht : naar boven");
						 setDirection(Direction.UP.getValue()); 
						 collided1 = true;
					     }
				 }
				 
				 else if (getDirection() == Direction.DOWN.getValue())
				 {
					     if(collided1)
					     {
						     System.out.println("bocht : naar rechts");
							 setDirection(Direction.LEFT.getValue()); 
							 collided2 = true;
					     }
				         else
				         {
					     System.out.println("bocht : naar rechts");
						 setDirection(Direction.RIGHT.getValue()); 
						 collided1 = true;
				         } 
				 }
				 
				 return;
				 }//Einde
				else
				 {
				  System.out.println("niets aan de hand!!!!!");
				  collided1 = false;
				  collided2 = false;
				 }
				//////////////////////////
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
		setSprite("blue_strip3", 3);
	}

}
