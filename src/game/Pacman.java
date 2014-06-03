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
	private int speed, currentDirection, previousDirection;
	
	public Pacman(PacmanApplication app, int speed) {
		this.app = app;
		this.speed = speed;
		
		setSprite("pacman");
		setDirection(90);
		currentDirection = 90;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		ArrayList<GameObject> collided = getCollidedObjects();
		if (collided != null)
		{
			for (GameObject gameObject : collided)
			{
				if (gameObject instanceof NormalPoint)
				{
					//score = score + ((Strawberry) g).getPoints();
					// Log.d("hapje!!!", "score is nu " + score);
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
			currentDirection = 0;
			setDirectionSpeed(0, speed);
			setFrameNumber(0);
		}
		if (OnScreenButtons.dPadDown
				|| (MotionSensor.tiltDown && !buttonPressed))
		{
			previousDirection = currentDirection;
			
			currentDirection = 180;
			setDirectionSpeed(180, speed);
			setFrameNumber(0);
		}
		if (OnScreenButtons.dPadRight
				|| (MotionSensor.tiltRight && !buttonPressed))
		{
			previousDirection = currentDirection;
			
			currentDirection = 90;
			setDirectionSpeed(90, speed);
			setFrameNumber(0);
		}
		if (OnScreenButtons.dPadLeft
				|| (MotionSensor.tiltLeft && !buttonPressed))
		{
			previousDirection = currentDirection;
			
			currentDirection = 270;
			setDirectionSpeed(270, speed);
			setFrameNumber(0);
		}
	}
	
	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) 
	{
		super.collisionOccurred(collidedTiles);
		for (TileCollision tc : collidedTiles)
		{
			if (tc.theTile.getTileType() == 1)
			{
				undoMove();
				currentDirection = previousDirection;
				setDirectionSpeed(previousDirection, speed);
				return; 
			}
		}
	}
	
	

}
