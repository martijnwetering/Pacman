package game.points;

import game.PacmanApplication;

public class PowerUp extends BasePoint  
{	 
	
	public PowerUp(PacmanApplication app) 
	{
		super(app, 25);
		setSprite("power_up");
	}
}



