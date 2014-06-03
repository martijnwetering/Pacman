package game;

import java.util.List;

import android.gameengine.icadroids.input.MotionSensor;
import android.gameengine.icadroids.input.OnScreenButtons;
import android.gameengine.icadroids.objects.MoveableGameObject;
import android.gameengine.icadroids.objects.collisions.ICollision;
import android.gameengine.icadroids.objects.collisions.TileCollision;

public class Pacman extends MoveableGameObject implements ICollision
{
	private PackmanApplication app;
	private int speed;
	
	public Pacman(PackmanApplication app, int speed) {
		this.app = app;
		this.speed = speed;
		
		setSprite("pacman");
		setDirection(90);
	}
	
	@Override
	public void update()
	{
		super.update();
		
		boolean buttonPressed = false;
		if (OnScreenButtons.dPadUp || OnScreenButtons.dPadDown
				|| OnScreenButtons.dPadLeft || OnScreenButtons.dPadRight)
		{
			buttonPressed = true;
		}

		if (OnScreenButtons.dPadUp || (MotionSensor.tiltUp && !buttonPressed))
		{
			setDirectionSpeed(0, speed);
			setFrameNumber(0);
		}
		if (OnScreenButtons.dPadDown
				|| (MotionSensor.tiltDown && !buttonPressed))
		{
			setDirectionSpeed(180, speed);
			setFrameNumber(0);
		}
		if (OnScreenButtons.dPadRight
				|| (MotionSensor.tiltRight && !buttonPressed))
		{
			setDirectionSpeed(90, speed);
			setFrameNumber(0);
		}
		if (OnScreenButtons.dPadLeft
				|| (MotionSensor.tiltLeft && !buttonPressed))
		{
			setDirectionSpeed(270, speed);
			setFrameNumber(0);
		}
	}

	@Override
	public void collisionOccurred(List<TileCollision> collidedTiles) {
		for (TileCollision tc : collidedTiles)
		{
			if (tc.theTile.getTileType() == 1)
			{
				moveUpToTileSide(tc);
				setSpeed(0);
				return; // might be considered ugly by some colleagues...
			}
			
			if (tc.theTile.getTileType() == 0)
			{
				moveUpToTileSide(tc);
				setSpeed(0);
				return;
			}
		}
		
	}

}
