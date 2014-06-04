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

public class Pacman extends Creature implements ICollision
{
	private PacmanApplication app;
	private int speed, currentDirection, previousDirection, score, count, i;
	double currentX, currentY;
	 
	public Pacman(PacmanApplication app, int speed) {
		this.app = app;
		this.speed = speed;
		this.currentX = getPrevCenterX();
		this.currentY = getPrevCenterY();
		
		setSprite("pacman_right_strip4", 4);
		setDirection(90);
		currentDirection = 90;
		score = 0;
		count = 0;
		i = 0;
	}
	
	@Override
	public void update()
	{
		super.update();
		count++;
		if(count == 5){
			i++;
			count = 0;
			if(i == 3){
				i = 0;
			}
		}

		
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
			setSprite("pacman_up_strip4", 4);
			previousDirection = currentDirection;
			currentDirection = 0;
			setDirectionSpeed(0, speed);
			setFrameNumber(i);
		}
		if (OnScreenButtons.dPadDown
				|| (MotionSensor.tiltDown && !buttonPressed))
		{
			setSprite("pacman_down_strip4", 4);
			previousDirection = currentDirection;			
			currentDirection = 180;
			setDirectionSpeed(180, speed);
			setFrameNumber(i);
		}
		if (OnScreenButtons.dPadRight
				|| (MotionSensor.tiltRight && !buttonPressed))
		{
			setSprite("orange_strip3", 3);
			previousDirection = currentDirection;		
			currentDirection = 90;
			setDirectionSpeed(90, speed);						
			setFrameNumber(i);
			
		}
		if (OnScreenButtons.dPadLeft
				|| (MotionSensor.tiltLeft && !buttonPressed))
		{
			setSprite("pacman_left_strip4", 4);
			previousDirection = currentDirection;			
			currentDirection = 270;
			setDirectionSpeed(270, speed);
			setFrameNumber(i);
		}
	}
	
	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) 
	{
		super.collisionOccurred(collidedTiles);
		for (TileCollision tc : collidedTiles)
		{
			if (tc.theTile.getTileType() != 11)
			{
				undoMove();
				currentDirection = previousDirection;
				setDirectionSpeed(previousDirection, speed);
				return; 
			}
		}
	}
	
	public int getScore()
	{
		return score;
	}
	
	

}
