package game.points;

import android.gameengine.icadroids.objects.GameObject;
import game.PacmanApplication;

public class NormalPoint extends GameObject implements IPoint
{
	private int points;
	
	public NormalPoint(int points) 
	{
		this.points = points;
		setSprite("normal_point");
	}

	@Override
	public int getPoints() 
	{
		return points;
	}
}
