package cellsociety_team16.Slime;

import cellsociety_team16.Util.Pos;
import cellsociety_team16.Util.RandUtil;

public class Amoeba {
	
	private Pos.Direction myDir;
	
	public Amoeba(Pos.Direction dir) {
		myDir = dir;
	}
	
	public void setDir(Pos.Direction dir) {
		myDir = dir;
	}
	
	public void randomlyAdjustDir() {
		myDir = RandUtil.getEitherAdjacentDirectionOf(myDir);
	}
	
	public Pos.Direction getDir() {
		return myDir;
	}

}
