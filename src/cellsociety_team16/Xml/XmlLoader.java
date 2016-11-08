package cellsociety_team16.Xml;

import java.io.File;
import java.util.HashMap;

import cellsociety_team16.Enum.SimType;

public class XmlLoader {
	
	public void loadXmlWithSimType(SimType simType) {
		String filename;
		switch (simType) {
		case Fire:
			filename = "fireNew.xml";
			break;
		case Life:
			filename = "lifeNew.xml";
			break;
		case Seg:
			filename = "segNew.xml";
			break;
		case Slime:
			filename = "slimeNew.xml";
			break;
		case Wator:	
			filename = "watorNew.xml";
			break;
		default:
			filename = "fireNew.xml";
		}
		loadXml("xml/" + filename);
	}
	
	public void loadXml(String filename) {
		XmlParser parser = new XmlParser();
		File f = new File(filename);
		try {
			HashMap<String, Object> xmlParams = (HashMap<String, Object>) parser.XMLparse(f);

			@SuppressWarnings("unchecked")
			HashMap<String, Object> rulesMap = (HashMap<String, Object>) xmlParams.get("rules");
			
			XmlContentSetter newSetter = new XmlContentSetter();
			newSetter.setSimConstants(rulesMap);

//			if (xmlParams.keySet().contains("reload")) {
//				XmlSetReload reloadSetter = new XmlSetReload();
//				/*Figure out where to put the newly made and populated 'grid'. Update the SimType.*/
//				reloadSetter.makeGrid(xmlParams.get("reload"));
//				// TODO: Make the grid and start running the simulation
//			}
//			else {
//				// TODO: start the simulation by populating the grid randomly, as usual.
//			}

		}
		catch(Exception e) { // TODO: Actually handle the exception, not just throwing it out.
			e.printStackTrace();
		}
	}
}
