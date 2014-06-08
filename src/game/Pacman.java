package game;

import game.utilities.Position;

import java.util.ArrayList;
import java.util.List;

import com.android.vissenspel.Monster;
import com.android.vissenspel.Strawberry;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.input.MotionSensor;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;
import android.util.Log;


public class Pacman extends Creature implements ICollision, IAlarm 
{
	private PacmanApplication app;
	private int speed, currentDirection, previousDirection, score, dotsEaten, lives;
	private double currentX, currentY;
	private boolean playerAction;
	private Alarm myAlarm;
	private Position position;
	private boolean hunter;

	 
	public Pacman(PacmanApplication app, int speed, int xCor, int yCor) 
	{
		this.app = app;
		this.speed = speed;
		
		position = new Position(xCor, yCor);
		myAlarm = new Alarm(2, 60, this);
		//myAlarm.startAlarm();
		
		setSprite("pacman_right_strip4", 4);
		setDirection(90);
		currentDirection = 90;
		dotsEaten = 0;
		score = -10;
		playerAction = false;
		lives = 3;
		hunter = false;
	}

	@Override
	public void update()
	{
		super.update();
		animatePacman();
		collisionHandler();
		setMovement();
	}

	private void collisionHandler() 
	{
		ArrayList<GameObject> collided = getCollidedObjects();
		if (collided != null)
		{
			for (GameObject gameObject : collided)
			{
				if (gameObject instanceof NormalPoint)
				{
					score = score + ((NormalPoint) gameObject).getPoints();
					app.deleteGameObject(gameObject);
					dotsEaten++;
				} 
				if (gameObject instanceof SpecialPoint)
				{
					score = score + ((SpecialPoint) gameObject).getPoints();
					app.deleteGameObject(gameObject);
				}
				if (gameObject instanceof Enemy)
				{
					if (lives > 0 && !app.resetting)
					{
						app.resetting = true;
						lives--;
						app.freezeMap();
					}
					else if (lives == 0)
					{
						app.restart();
					}
				}
				if (gameObject instanceof PowerUp)
				{
					score = score + ((PowerUp) gameObject).getPoints();
					app.deleteGameObject(gameObject);
					activateHunterMode();
				}
			}
		}
	}

	private void setMovement() 
	{
		boolean buttonPressed = false;
		if (OnScreenButtons.dPadUp || OnScreenButtons.dPadDown
				|| OnScreenButtons.dPadLeft || OnScreenButtons.dPadRight)
		{
			buttonPressed = true;
		}

		if (OnScreenButtons.dPadUp || (MotionSensor.tiltUp && !buttonPressed))
		{
			previousDirection = currentDirection;
			playerAction = true;
			currentDirection = Direction.UP.getValue();
			setDirectionSpeed(currentDirection, speed);
		}
		if (OnScreenButtons.dPadDown
				|| (MotionSensor.tiltDown && !buttonPressed))
		{
			previousDirection = currentDirection;
			playerAction = true;
			currentDirection = Direction.DOWN.getValue();
			setDirectionSpeed(currentDirection, speed);
		}
		if (OnScreenButtons.dPadRight
				|| (MotionSensor.tiltRight && !buttonPressed))
		{
			previousDirection = currentDirection;	
			playerAction = true;
			currentDirection = Direction.RIGHT.getValue();
			setDirectionSpeed(currentDirection, speed);						
			
		}
		if (OnScreenButtons.dPadLeft
				|| (MotionSensor.tiltLeft && !buttonPressed))
		{
			previousDirection = currentDirection;
			playerAction = true;
			currentDirection = Direction.LEFT.getValue();
			setDirectionSpeed(currentDirection, speed);
		}
	}
	
	int counter = 0;
	private void animatePacman()
	{
		if (counter == 4) counter = 0;
				
		if (currentDirection == Direction.UP.getValue())
		{
			setSprite("pacman_up_strip4", 4);
			setFrameNumber(counter);
		}
		else if (currentDirection == Direction.RIGHT.getValue())
		{
			setSprite("pacman_right_strip4", 4);
			setFrameNumber(counter);
		}
		else if (currentDirection == Direction.DOWN.getValue())
		{
			setSprite("pacman_down_strip4", 4);
			setFrameNumber(counter);
		}
		else if (currentDirection == Direction.LEFT.getValue())
		{
			setSprite("pacman_left_strip4", 4);
			setFrameNumber(counter);
		}
		
		counter++;
	}
	
	// handler for tile collisions.
	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) 
	{
		super.collisionOccurred(collidedTiles);
		
		for (TileCollision tc : collidedTiles)
		{
			int collisionSide = tc.collisionSide;
			boolean movingUpOrDown = previousDirection == 0 || previousDirection == 180;
			boolean movingRightOrLeft = previousDirection == 90 || previousDirection == 270;
			
			// If pacman hits a wall 
			if (tc.theTile.getTileType() != 11 && tc.theTile.getTileType() != 14
					&& tc.theTile.getTileType() != 15)
			{
				
				moveUpToTileSide(tc);
				if (movingUpOrDown && playerAction)
				{	
					
					if (collisionSide == 1 || collisionSide == 3)
					{
						
						currentDirection = previousDirection;
						setDirectionSpeed(previousDirection, speed);
					}
				}
				else if (movingRightOrLeft && playerAction)
				{
					if (collisionSide == 0 || collisionSide == 2)
					{
						currentDirection = previousDirection;
						setDirectionSpeed(previousDirection, speed);
					}
				}
				else 
				{
					setSpeed(0);
				}
			}
		}
		playerAction = false;
	}
	
	private void activateHunterMode()
	{
		myAlarm.restartAlarm();
		hunter = true;
	}
	
	public void triggerAlarm(int id) { // Pacman wordt slachtoffer
		Log.d("Pacman", "Alarm gaat af");
		hunter = false;
		myAlarm.pauseAlarm();
	    }
	
	public int getScore()
	{
		return score;
	}
	
	public int getDotsEaten()
	{
		return dotsEaten;
	}
	
	public int getLives() {
		return lives;
	}

	public int getXCor() {
		return position.getXCor();
	}
	
	public int getYCor() {
		return position.getYCor();
	}
	
	
}
