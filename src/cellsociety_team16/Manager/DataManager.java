package cellsociety_team16.Manager;

import cellsociety_team16.Config.FireConfig;
import cellsociety_team16.Config.LifeConfig;
import cellsociety_team16.Config.SegConfig;
import cellsociety_team16.Config.SimConfig;
import cellsociety_team16.Config.SlimeConfig;
import cellsociety_team16.Config.WatorConfig;

/**
 * A singleton DataManager that provides the DataMap parsed from XML
 * It is made a singleton because data should be consistent throughout
 * the entire simulation, with this being the only source of the data.
 * @author Jay
 *
 */
public class DataManager {
	private SimConfig mySimConfig;
	private SegConfig mySegConfig;
	private WatorConfig myWatorConfig;
	private FireConfig myFireConfig;
	private LifeConfig myLifeConfig;
	private SlimeConfig mySlimeConfig;
	
	private static DataManager instance = new DataManager();
	
	/**
	 * A private constructor to prevent new instances from being created.
	 */
	private DataManager() {
		mySimConfig = new SimConfig();
		mySegConfig = new SegConfig();
		myWatorConfig = new WatorConfig();
		myFireConfig = new FireConfig();
		myLifeConfig = new LifeConfig();
		mySlimeConfig = new SlimeConfig();
	};
	
	/**
	 * The only way to get an instance of DataManager.
	 * @return DataManager instance
	 */
	public static DataManager get() {
		return instance;
	}
	
	public SimConfig sim() {
		return mySimConfig;
	}
	
	public SegConfig seg() {
		return mySegConfig;
	}
	
	public WatorConfig wator() {
		return myWatorConfig;
	}
	
	public FireConfig fire() {
		return myFireConfig;
	}
	
	public LifeConfig life() {
		return myLifeConfig;
	}
	
	public SlimeConfig slime() {
		return mySlimeConfig;
	}
}
