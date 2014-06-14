package game.points;

import game.PacmanApplication;
import android.gameengine.icadroids.objects.GameObject;

public class PowerUp extends GameObject implements IPoint  
{
	private int points;
	
	public PowerUp(int points) 
	{
		this.points = points;
		setSprite("power_up");
	}

	@Override
	public int getPoints() 
	{
		return points;
	}
}



