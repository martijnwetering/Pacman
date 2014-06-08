package game;

import game.utilities.Position;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.objects.collisions.TileCollision;

public abstract class Enemy extends Creature
{
	private Pacman pacman;
	private int value;
	private Position position;
	
	protected int speed;
	protected boolean collided;
	protected Random randomNumberGenerator = new Random();
	protected int currentDirection;
	

	public Enemy(Pacman pacman, int xCor, int yCor) 
	{
		this.pacman = pacman;
		position = new Position(xCor, yCor);
		value = 200;
		setDirection(Direction.UP.getValue());
		speed = 4;
		collided = false;
		
	}
	
	public abstract void move(List<TileCollision> collidedTiles);
	
	public int getXcor()
	{
		return position.getXCor();
	}
	
	public int getYcor()
	{
		return position.getYCor();
	}
	
	

}
