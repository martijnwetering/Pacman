package game.utilities;

public class LevelGenerator{

	private int[][] tilemap1;
	private int[][] tilemap2;
	private String[] tileImagesNames;
	 	
	public LevelGenerator(){
		tilemap1 = createLevel1();
		tilemap2 = createLevel2();
		tileImagesNames = createImagesNames();
	}
	
	private String[] createImagesNames(){
	
	String[] tileImagesNames = { "cornertopleft", "cornertopright", "cornerbottomleft", 
			"cornerbottomright", "vertical", "horizontal", "internleft", 
			"internright", "interntop", "internbottom", "wall_tile", 
			"background_tile", "no_gameobject", "gate", "invisible_wall",
			"power_up_tile"};
    return tileImagesNames;
	}
	
	
	private int[][] createLevel1() 
	{	
		int [][] tilemap = new int[][] {
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
				{10, 0, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 1, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  0,  5,  5,  1, 11,  0,  5,  5,  5,  1, 11,  4, 11,  0,  5,  5,  5,  1, 11,  0,  5,  5,  1, 12, 4, 10 },
				{10, 4,15,  4, 12, 12,  4, 11,  4, 12, 12, 12,  4, 11,  4, 11,  4, 12, 12, 12,  4, 11,  4, 12, 12,  4, 12, 4, 10 }, 
				{10, 4,11,  2,  5,  5,  3, 14,  2,  5,  5,  5,  3, 14,  9, 14,  2,  5,  5,  5,  3, 14,  2,  5,  5,  3, 12, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 15, 4, 10 },
				{10, 4,14,  6,  5,  5,  7, 14,  8, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  8, 14,  6,  5,  5,  7, 14, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 2, 5,  5,  5,  5,  1, 11,  4,  5,  5,  5,  7, 11,  4, 11,  6,  5,  5,  5,  4, 11,  0,  5,  5,  5,  5, 3, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11, 11, 11, 11, 11, 14, 11, 11, 11, 11, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11,  0,  5,  5, 13, 13, 13,  5,  5,  1, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10, 5, 5,  5,  5,  5,  3, 14,  9, 14,  4, 12, 12, 13, 13, 13, 12, 12,  4, 14,  9, 14,  2,  5,  5,  5,  5, 5, 10 },
				{10,11,11, 11, 11, 12, 11, 11, 14, 11,  4, 12, 12, 12, 12, 12, 12, 12,  4, 11, 14, 11, 11, 11, 11, 11, 11,11, 10 },
				{10, 5, 5,  5,  5,  5,  1, 14,  8, 14,  4, 12, 12, 12, 12, 12, 12, 12,  4, 14,  8, 14,  0,  5,  5,  5,  5, 5, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11,  2,  5,  5,  5,  5,  5,  5,  5,  3, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10,12,12, 12, 12, 12,  4, 11,  4, 11, 12, 12, 12, 12, 12, 12, 12, 12, 12, 11,  4, 11,  4, 12, 12, 12, 12,12, 10 },
				{10, 0, 5,  5,  5,  5,  3, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  9, 11,  2,  5,  5,  5,  5, 1, 10 },
				{10, 4,11, 11, 11, 14, 11, 11, 14, 11, 14, 11, 11, 11,  4, 11, 11, 11, 14, 11, 14, 11, 14, 11, 11, 11, 11, 4, 10 },
				{10, 4,11,  6,  5,  5,  1, 11,  6,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  7, 11,  0,  5,  5,  7, 11, 4, 10 },
				{10, 4,15, 11, 11, 11,  4, 11, 11, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 15, 4, 10 },
				{10, 4, 5,  5,  7, 11,  9, 14,  8, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  8, 11,  9, 11,  6,  5,  5, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11, 14, 11, 14, 11, 11, 4, 10 },
				{10, 4,11,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  5,  7, 11, 4, 10 },
				{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 14, 11, 14, 11, 14, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
				{10, 2, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 3, 10 },
				{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
		};
	    return tilemap;
	}
	

	private int[][] createLevel2() 
	{	
		int[][] tilemap = 
		{
		{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
		{10, 0, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 1, 10 },
		{10, 4,12, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 13, 12, 4, 10 },
		{10, 4,11,  6,  5,  5,  7, 11,  6,  5,  5,  5,  7, 11,  4, 11,  6,  5,  5,  5,  7, 11,  6,  5,  5,  7, 11, 4, 10 },
		{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 }, 
		{10, 4,11,  0,  5,  5,  1, 11,  6,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  7, 11,  0,  5,  5,  1, 11, 4, 10 },
		{10, 4,11,  4, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11,  4, 11, 4, 10 },
		{10, 4,11,  2,  5,  5,  3, 11,  8, 11,  6,  5,  5,  7, 11,  6,  5,  5,  7, 11,  8, 11,  2,  5,  5,  3, 11, 4, 10 },
		{10, 4,11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 4, 10 },
		{10, 2, 5,  5,  5,  5,  1, 11,  4, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  4, 11,  0,  5,  5,  5,  5, 3, 10 },
		{10,13,13, 13, 13, 13,  4, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11,  4, 11, 11, 11, 11,11, 10 },
		{10,13,13, 13, 13, 13,  4, 11,  4, 11,  0,  5,  5,  5,  5,  5,  5,  5,  1, 11,  4, 11,  4, 11, 11, 11, 11,11, 10 },
		{10, 5, 5,  5,  5,  5,  3, 11,  9, 11,  4, 13, 13, 13, 13, 13, 13, 13,  4, 11,  9, 11,  2,  5,  5,  5,  5, 5, 10 },
		{10,11,11, 11, 11, 11, 11, 11, 11, 11,  4, 13, 13, 13, 13, 13, 13, 13,  4, 11, 11, 11, 11, 11, 11, 11, 11,11, 10 },
		{10, 5, 5,  5,  5,  5,  1, 11,  8, 11,  4, 13, 13, 13, 13, 13, 13, 13,  4, 11,  8, 11,  0,  5,  5,  5,  5, 5, 10 },
		{10,13,13, 13, 13, 13,  4, 11,  4, 11,  2,  5,  5,  5,  5,  5,  5,  5,  3, 11,  4, 11,  4, 13, 13, 13, 13,13, 10 },
		{10,13,13, 13, 13, 13,  4, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11,  4, 13, 13, 13, 13,13, 10 },
		{10, 0, 5,  5,  5,  5,  3, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  9, 11,  2,  5,  5,  5,  5, 1, 10 },
		{10, 4,11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 4, 10 },
		{10, 4,11,  0,  5,  5,  7, 11,  6,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  5,  5,  5,  5,  5,  1, 11, 4, 10 },
		{10, 4,11,  4, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11,  4, 11, 4, 10 },
		{10, 4,11,  9, 11,  0,  1, 11,  8, 11,  6,  5,  5,  5,  5,  5,  5,  5,  7, 11,  8, 11,  0,  1, 11,  9, 11, 4, 10 },
		{10, 4,11, 11, 11,  4,  4, 11,  4, 11, 11, 11, 11, 11,  4, 11, 11, 11, 11, 11,  4, 11,  4,  4, 11, 11, 11, 4, 10 },
		{10, 4,11,  6,  5,  5,  3, 11,  2,  5,  5,  5,  7, 11,  9, 11,  6,  5,  5,  5,  3, 11,  2,  5,  5,  7, 11, 4, 10 },
		{10, 4,12, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 13, 12, 4, 10 },
		{10, 2, 5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5,  5, 3, 10 },
		{10,10,10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,10, 10 },
		};
		return tilemap;
}
	
	

	public int[][] getTilemap1() {
		return tilemap1;
	}
	
	public int[][] getTilemap2() {
		return tilemap2;
	}

	public String[] getTileImagesNames() {
		return tileImagesNames;
	}
	
}
	


