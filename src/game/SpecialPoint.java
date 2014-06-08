package game;

public class SpecialPoint extends BasePoint 
{
	private int maxAge;

	public SpecialPoint(PacmanApplication app) 
	{
		super(app, 25);
		setSprite("special_point");
		maxAge = 210;
	}
	
	public int getMaxAge()
	{
		return maxAge;
	}

}
