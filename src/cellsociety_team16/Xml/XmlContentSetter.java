package cellsociety_team16.Xml;

import java.util.HashMap;
import java.util.Map;

import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Manager.DataManager;;

public class XmlContentSetter {
	public void setSimConstants(Map<String, Object> rulesMap) {
		if(rulesMap.containsKey("wator")) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> watorMap = (HashMap<String, Object>) rulesMap.get("wator");

			setSim(watorMap, SimType.Wator);

			DataManager.get().wator().setPercentEmpty(Integer.parseInt(getTextValue(watorMap.get("percent_empty"))));

			DataManager.get().wator().setPercentShark(Integer.parseInt(getTextValue(watorMap.get("percent_shark"))));
			DataManager.get().wator().setSharkBreed(Integer.parseInt(getTextValue(watorMap.get("shark_breed"))));
			DataManager.get().wator().setSharkStarve(Integer.parseInt(getTextValue(watorMap.get("shark_starve"))));

			DataManager.get().wator().setFishBreed(Integer.parseInt(getTextValue(watorMap.get("fish_breed"))));
		}

		if(rulesMap.containsKey("fire")) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> fireMap = (HashMap<String, Object>) rulesMap.get("fire");

			setSim(fireMap, SimType.Fire);

			DataManager.get().fire().setNumBurn(Integer.parseInt(getTextValue(fireMap.get("num_burn"))));
			DataManager.get().fire().setProbCatch(Integer.parseInt(getTextValue(fireMap.get("prob_catch"))));
		}

		if(rulesMap.containsKey("segregation")) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> segMap = (HashMap<String, Object>) rulesMap.get("segregation");

			setSim(segMap, SimType.Seg);

			DataManager.get().seg().setPercentA(Integer.parseInt(getTextValue(segMap.get("percent_a"))));
			DataManager.get().seg().setPercentEmpty(Integer.parseInt(getTextValue(segMap.get("percent_empty"))));
			DataManager.get().seg().setPercentSatisfy(Integer.parseInt(getTextValue(segMap.get("percent_satsify"))));
		}

		if(rulesMap.containsKey("life")) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> lifeMap = (HashMap<String, Object>) rulesMap.get("life");

			setSim(lifeMap, SimType.Life);

			DataManager.get().life().setNumAlive(Integer.parseInt(getTextValue(lifeMap.get("num_alive"))));
		}

		if(rulesMap.containsKey("slime")) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> slimeMap = (HashMap<String, Object>) rulesMap.get("slime");

			setSim(slimeMap, SimType.Slime);

			DataManager.get().slime().setNumAmoeba(Integer.parseInt(getTextValue(slimeMap.get("num_amoeba"))));
			DataManager.get().slime().setSniffThreshold(Integer.parseInt(getTextValue(slimeMap.get("sniff"))));
			DataManager.get().slime().setReleaseValue(Integer.parseInt(getTextValue(slimeMap.get("release"))));
		}
	}

	private void setSim(HashMap<String, Object> gameMap, SimType gameType) {
		DataManager.get().sim().setSimType(gameType);
		
		DataManager.get().sim().setColorMap(makeColorMap(gameMap));

		@SuppressWarnings("unchecked")
		HashMap<String, Object> gridMap = (HashMap<String, Object>) gameMap.get("grid");
		
		DataManager.get().sim().setNumCol(Integer.parseInt(getTextValue(gridMap.get("col"))));
		DataManager.get().sim().setNumRow(Integer.parseInt(getTextValue(gridMap.get("row"))));
	}
	
	private Map<Integer, String> makeColorMap(HashMap<String, Object> gameMap) {

		@SuppressWarnings("unchecked")
		HashMap<String, Object> preMap = (HashMap<String, Object>) gameMap.get("color_map");
		
		HashMap<Integer, String> colorMap = new HashMap<Integer, String>();
		
//		System.out.println(preMap);
		
		Boolean firstPass = true;
		for(String key : preMap.keySet()) {
			if(firstPass) {
				firstPass = false;
				continue;
			}
			try {
				colorMap.put(Integer.parseInt(key.substring(1)), getTextValue(preMap.get(key)));
			} catch (Exception e) {
				
			}
		}
		
		return colorMap;
	}

	@SuppressWarnings("unchecked")
	private String getTextValue(Object stringMap) {
		return ((Map<String, String>) stringMap).get("#text");
	}
}
