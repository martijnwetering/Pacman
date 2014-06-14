package game.points;

import android.gameengine.icadroids.objects.GameObject;
import game.PacmanApplication;

public class SpecialPoint extends GameObject implements IPoint
{
	private int points;
	private int maxAge;

	public SpecialPoint(int points) 
	{
		this.points = points;
		setSprite("special_point");
		maxAge = 250;
	}
	
	@Override
	public int getPoints() {
		return points;
	}
	
	public int getMaxAge() 
	{
		return maxAge;
	}

}
