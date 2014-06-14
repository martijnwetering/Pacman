package game.creatures;

import game.PacmanApplication;
import game.points.NormalPoint;
import game.points.PowerUp;
import game.points.SpecialPoint;
import game.utilities.Position;
import game.points.IPoint;

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
	private int currentDirection, previousDirection, score, dotsEaten, dotsEatenOnTurn, lives;
	private boolean playerAction;
	private Alarm alarm;
	private boolean hunter;
	private boolean pacmanActive;

	public Pacman(PacmanApplication app, int speed, int xCor, int yCor, int lives) 
	{
		super(app, xCor, yCor, speed);
		
		this.app = app;
		this.lives = lives;
		alarm = new Alarm(2, 210, this);
		
		setSprite("pacman_right_strip4", 4);
		setDirection(90);
		currentDirection = 90;
		dotsEaten = 0;
		score = 0;
		playerAction = false;
		hunter = false;
		dotsEatenOnTurn = 0;
		pacmanActive = true;
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
				if (gameObject instanceof IPoint)
				{
					score = score + ((IPoint)gameObject).getPoints();
				}
				if (gameObject instanceof NormalPoint)
				{
					app.deleteGameObject(gameObject);
					dotsEaten++;
					dotsEatenOnTurn++;
				} 
				if (gameObject instanceof SpecialPoint)
				{
					app.deleteGameObject(gameObject);
				}
				if (gameObject instanceof Enemy)
				{
					if (!hunter && lives > 0 && !app.getIsResetting())
					{
						app.setIsResetting(true);
						setSpeed(0);
						pacmanActive = false;
						lives--;
						dotsEatenOnTurn = 0;
						app.freezeMap();
					}
					else if (lives == 0)
					{
						app.restart();
					}
				}
				if (gameObject instanceof PowerUp)
				{
					app.deleteGameObject(gameObject);
					activateHunterMode();
				}
			}
		}
	}

	private void setMovement() 
	{
		boolean buttonPressed = false;
		if (pacmanActive)
		{
			if (OnScreenButtons.dPadUp || OnScreenButtons.dPadDown
					|| OnScreenButtons.dPadLeft || OnScreenButtons.dPadRight)
			{
				buttonPressed = true;
			}
	
			if (OnScreenButtons.dPadUp 
					|| (MotionSensor.tiltUp && !buttonPressed))
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
	
	// Handler for tile collisions.
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
			if (tc.theTile.getTileType() != 11 && tc.theTile.getTileType() != 12 && tc.theTile.getTileType() != 14
					&& tc.theTile.getTileType() != 15)
			{
				moveUpToTileSide(tc);
				
				if(tc.theTile.getTileType() == 16)
				{
					setPosition(520,260);
					setDirectionSpeed(previousDirection, speed);
				}
				
				else if(tc.theTile.getTileType() == 17)
				{
					setPosition(20,260);
					setDirectionSpeed(previousDirection, speed);
				}
				
				
				else if (movingUpOrDown && playerAction)
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
		alarm.restartAlarm();
		hunter = true;
	}
	
	public void triggerAlarm(int id) 
	{ 
		hunter = false;
		alarm.pauseAlarm();
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

	public boolean isHunter() {
		return hunter;
	}
	
	public int getDotsEatenOnTurn() {
		return dotsEatenOnTurn;
	}
	
	public void setPacmanActive(boolean pacmanActive)
	{
		this.pacmanActive = pacmanActive;
	}
	
}
