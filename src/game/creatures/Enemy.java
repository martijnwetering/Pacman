package game.creatures;

import game.PacmanApplication;
import game.creatures.Creature.Direction;
import game.utilities.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public abstract class Enemy extends Creature
{	
	private int points, numberOfDotsToActivate;
	private Position position;
	
	protected Pacman pacman;
	protected int speed;
	protected boolean collided, active;
	protected Random randomNumberGenerator = new Random();
	protected int currentDirection;
	
	

	public Enemy(Pacman pacman, PacmanApplication app, int xCor, int yCor, int numberOfDotsToActivate) 
	{
		super(app);
		
		this.pacman = pacman;
		this.numberOfDotsToActivate = numberOfDotsToActivate;
		position = new Position(xCor, yCor);
		points = 200;
		setDirection(Direction.UP.getValue());
		speed = 4;
		collided = false;
		active = false;
		
	}
	
	public abstract void move(List<TileCollision> collidedTiles);
	public abstract void setDefaultSprite();
	
	public int getXcor()
	{
		return position.getXCor();
	}
	
	public int getYcor()
	{
		return position.getYCor();
	}
	
	@Override
	public void update()
	{
		super.update();
		activate();
		collisionHandler();
		
		if (pacman.isHunter())
		{
			setSprite("scared_strip3", 3);
		}
		else
		{
			setDefaultSprite();
		}
	}
	
	public void activate()
	{
		if (pacman.getDotsEatenOnTurn() == numberOfDotsToActivate && !active)
		{
			active = true;
			setDirectionSpeed(Direction.UP.getValue(), speed);
		}
	}
	
	private void collisionHandler() 
	{
		ArrayList<GameObject> collided = getCollidedObjects();
		if (collided != null)
		{
			for (GameObject gameObject : collided)
			{
				if (gameObject instanceof Pacman && !pacman.isHunter())
				{
					for (GameObject go : app.items)
					{
						if (go instanceof Enemy)
						{
							((Enemy) go).setSpeed(0);
							((Enemy) go).setActive(false);
						}
					}
				}
				if (gameObject instanceof Pacman && pacman.isHunter())
				{
					jumpToStartPosition();
					setDirection(Direction.UP.getValue());
				}
			}
		}
	}
	
	protected int randomDirection()
	{
		int[] directions = {0, 90, 180, 270};
		Random randomNumberGenerator = new Random();
		int randomDirection = randomNumberGenerator.nextInt(4);
		
		return directions[randomDirection];
	}
	
	protected int randomLeftOrRight()
	{
		int[] directions = {90, 270};
		int randomDirection = randomNumberGenerator.nextInt(2);
		
		return directions[randomDirection];
	}
	
	protected int randomUpOrDown()
	{
		int[] directions = {0, 180};
		int randomDirection = randomNumberGenerator.nextInt(2);
		
		return directions[randomDirection];
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void setActive(boolean value)
	{
		active = value;
	}
	
	
	
}
