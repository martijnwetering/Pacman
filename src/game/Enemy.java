package game;

import game.utilities.Position;

import java.util.List;
import java.util.Random;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public abstract class Enemy extends Creature  
{
	private static Pacman pacman;
	private int value;
	private Position position;
	
	protected int speed;
	protected boolean collided;
	protected Random randomNumberGenerator = new Random();
	protected int currentDirection;
	
	protected static boolean victum;
	protected static boolean eaten;
	

	public Enemy(Pacman pacman, int xCor, int yCor) 
	{
		this.pacman = pacman;
		position = new Position(xCor, yCor);
		value = 200;
		setDirection(Direction.UP.getValue());
		speed = 4;
		collided = false;
		victum = false;
		eaten = false;
	}
	
	public abstract void move(List<TileCollision> collidedTiles);
	
	//wanneer victum = true kan pacman de spookjes opeten.
	public static void bepaalStatusEnemey(){
		victum = pacman.getHunter();
	}
	
	public int getXcor()
	{
		return position.getXCor();
	}
	
	public int getYcor()
	{
		return position.getYCor();
	}
	
	

}
