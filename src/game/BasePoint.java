package game;

import android.gameengine.icadroids.objects.GameObject;

public abstract class BasePoint extends GameObject 
{
	private PacmanApplication app;
	private int points;
	
	public BasePoint(PacmanApplication app ,int points) 
	{
		this.app = app;
		this.points = points;
	}
	
	public int getPoints()
	{
		return points;
	}
}
