package game;

import java.util.Random;

import android.gameengine.icadroids.alarms.Alarm;
import android.gameengine.icadroids.alarms.IAlarm;
import android.gameengine.icadroids.tiles.Tile;

public class PointController  implements IAlarm
{
	private PacmanApplication app;
	private Alarm alarm;
	

	public PointController(PacmanApplication app) 
	{
		this.app = app;
		alarm = new Alarm(1, 1, this);
		alarm.startAlarm();
	}

	void placeNormalPoints() 
	{
		NormalPoint normalPoint = new NormalPoint(app);
		int x = 20;
		int y = 20;
		app.addGameObject(normalPoint, x, y);	
	}
	
	void placeSpecialPoint()
	{
		SpecialPoint specialPoint = new SpecialPoint(app);
		
		Random randomNumberGenerator = new Random();
		int randomNumberOne = randomNumberGenerator.nextInt(20);
		int randomNumberTwo = randomNumberGenerator.nextInt(20);
		
		int x = 20 * randomNumberOne;
		int y = 20 * randomNumberTwo;
		
		//int x = 20 + (int) (570 * Math.random());
		//int y = 20 + (int) (370 * Math.random());
		app.addGameObject(specialPoint, x, y);
		alarm.setTime(10);
		alarm.restartAlarm();
	}

	@Override
	public void triggerAlarm(int alarmID) {
		placeSpecialPoint();	
	}
}
