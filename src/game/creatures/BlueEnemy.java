package game.creatures;

import game.PacmanApplication;
import game.creatures.Creature.Direction;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.TileCollision;

public class BlueEnemy extends Enemy 
{
	private boolean collided;
	private boolean isWall;
	private boolean inToCloseZone = false;

	private double posXPacman;
	private double posYPacman;
	private double posXBlueEnemy;
	private double posYBlueEnemy;

	public BlueEnemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, 
			int numberOfDotsToActivate, int speed, int points) 
	{
		super(pacman, app, xCor, yCor, numberOfDotsToActivate, speed, points);
		setSprite("blue_strip3", 3);
		setFrameNumber(0);
	}

	@Override
	public void move(List<TileCollision> collidedTiles) 
	{			  

		for (TileCollision tc : collidedTiles)
		{

			int tileType = tc.theTile.getTileType();

			isWall = false;
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

			// Only gets executed when pacman is in it normal state, and not in
			// hunter mode.
			if(!pacman.isHunter())
			{
			    if(isPacmanToClose() && !inToCloseZone)
			    {
			    	inToCloseZone = true;

			    	if (pacman.movesUp() || pacman.movesDown())
			    	{
			    		reverseVerticalDirection();
			    	}
			    	if (pacman.movesLeft() || pacman.movesRight())
			    	{
			    		reverseHorizontalDirection();			    
			    	}
				}
			    else 
				{
					inToCloseZone = false;
				}
			}


			// Only gets executed when pacman eat a power up and is the hunter.
			else if(pacman.isHunter())
		    {
			  if (isWall)
			  { 
					moveUpToTileSide(tc);
					if (getDirection() == Direction.UP.getValue())
					{
						if(collided)
						{							     
							setDirection(Direction.DOWN.getValue());					     
						}
						else
						{
							setDirection(Direction.RIGHT.getValue());
							collided = true;
						}
					}

					 else if (getDirection() == Direction.RIGHT.getValue())
					 {
			               if(collided)
			               {
			              	 setDirection(Direction.DOWN.getValue());			                	 
			               }
			               else
			               {
							 setDirection(Direction.UP.getValue()); 
							 collided = true;
			               }
					 }

					 else if (getDirection() == Direction.LEFT.getValue())
					 {
						     if(collided)
						     {
			              	 setDirection(Direction.RIGHT.getValue());
						     }
						     else
						     {
							 setDirection(Direction.UP.getValue()); 
							 collided = true;
						     }
					 }

					 else if (getDirection() == Direction.DOWN.getValue())
					 {
						     if(collided)
						     {
								 setDirection(Direction.LEFT.getValue()); 	 
						     }
					         else
					         {
							 setDirection(Direction.RIGHT.getValue()); 
							 collided = true;
					         } 
					 }						 
			   }            
			   else
			   {
			    collided = false;					  
			   }
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

	public boolean isPacmanToClose(){

		posXPacman = pacman.getX();
		posYPacman = pacman.getY();
		posXBlueEnemy = getX();
		posYBlueEnemy = getY();

		//pacman links van spook
		if(posYBlueEnemy == posYPacman && posXPacman < posXBlueEnemy && posYBlueEnemy > posXPacman + 60)
		{
			return true;
		}
		// pacman rechts van spook	
		else if(posYBlueEnemy == posYPacman && posXPacman > posXBlueEnemy && posXPacman < posXBlueEnemy + 60)
		{
			return true;
		}	
		//pacman boven spook
		else if(posXBlueEnemy == posXPacman && posYPacman < posYBlueEnemy && posYBlueEnemy >  posYPacman + 60)
		{
			return true;
		}
		//pacman onder spook
		else if(posXBlueEnemy == posXPacman && posYPacman > posYBlueEnemy  && posYPacman < posYBlueEnemy + 60)
		{
			return true;
		}
		else 
		{
			return false;
		}

	}


}
