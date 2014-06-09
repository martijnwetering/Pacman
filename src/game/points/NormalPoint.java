package game.points;

import game.PacmanApplication;

public class NormalPoint extends BasePoint 
{
	public NormalPoint(PacmanApplication app) 
	{
		super(app, 10);
		setSprite("normal_point");
	}

}
