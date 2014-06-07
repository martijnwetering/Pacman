package game;

import java.util.ArrayList;
import java.util.List;

import com.android.vissenspel.Monster;
import com.android.vissenspel.Strawberry;

import android.gameengine.icadroids.input.MotionSensor;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.objects.GameObject;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;
import android.util.Log;


public class Pacman extends Creature implements ICollision
{
	private PacmanApplication app;
	private int speed, currentDirection, previousDirection, score, count, i;
	//zorgt ervoor of pacman jager is of slachtoffer
    private boolean hunter;
	
	public enum Direction
	{
		UP(0), RIGHT(90), DOWN(180), LEFT(270);
		
		private final int value;
		
		private Direction(int value) 
		{
            this.value = value;
		}
		
		public int getValue()
		{
			return value;
		}
	}
	
	 
	public Pacman(PacmanApplication app, int speed) {
		this.app = app;
		this.speed = speed;
		
		
		setSprite("pacman_right_strip4", 4);
		setDirection(90);
		currentDirection = 90;
		score = -10;
		hunter = false;
	}
	
	@Override
	public void update()
	{
		super.update();
				
		animatePacman();

		ArrayList<GameObject> collided = getCollidedObjects();
		if (collided != null)
		{
			for (GameObject gameObject : collided)
			{
				if (gameObject instanceof NormalPoint)
				{
					score = score + ((NormalPoint) gameObject).getPoints();
					app.deleteGameObject(gameObject);
				} 
				if (gameObject instanceof SpecialPoint)
				{
					score = score + ((SpecialPoint) gameObject).getPoints();
					app.deleteGameObject(gameObject);
				}
				if (gameObject instanceof PowerUp)
				{
					score = score + ((PowerUp) gameObject).getPoints();
					app.deleteGameObject(gameObject);
				}
			}
		}
		
		boolean buttonPressed = false;
		if (OnScreenButtons.dPadUp || OnScreenButtons.dPadDown
				|| OnScreenButtons.dPadLeft || OnScreenButtons.dPadRight)
		{
			buttonPressed = true;
		}

		if (OnScreenButtons.dPadUp || (MotionSensor.tiltUp && !buttonPressed))
		{
			previousDirection = currentDirection;
			currentDirection = Direction.UP.getValue();
			setDirectionSpeed(Direction.UP.getValue(), speed);
		}
		if (OnScreenButtons.dPadDown
				|| (MotionSensor.tiltDown && !buttonPressed))
		{
			previousDirection = currentDirection;			
			currentDirection = Direction.DOWN.getValue();
			setDirectionSpeed(Direction.DOWN.getValue(), speed);
		}
		if (OnScreenButtons.dPadRight
				|| (MotionSensor.tiltRight && !buttonPressed))
		{
			previousDirection = currentDirection;		
			currentDirection = Direction.RIGHT.getValue();
			setDirectionSpeed(Direction.RIGHT.getValue(), speed);						
			
		}
		if (OnScreenButtons.dPadLeft
				|| (MotionSensor.tiltLeft && !buttonPressed))
		{
			previousDirection = currentDirection;			
			currentDirection = Direction.LEFT.getValue();
			setDirectionSpeed(Direction.LEFT.getValue(), speed);
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
	
	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) 
	{
		super.collisionOccurred(collidedTiles);
		for (TileCollision tc : collidedTiles)
		{
			if (tc.theTile.getTileType() != 11)
			{ 
				if (tc.theTile.getTileType() != 13)
				{
				undoMove();
				currentDirection = previousDirection;
				setDirectionSpeed(previousDirection, speed);
				return;
				}
				else 
				{
					undoMove();
					currentDirection = previousDirection;
					setDirectionSpeed(previousDirection, speed);
					return;	
				}
			}
			
		}
	}
	
	public int getScore()
	{
		return score;
	}
	
	

}
