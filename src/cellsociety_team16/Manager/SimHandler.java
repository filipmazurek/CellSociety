package cellsociety_team16.Manager;

import java.io.File;

import cellsociety_team16.Enum.EdgeType;
import cellsociety_team16.Enum.ShapeType;
import cellsociety_team16.Enum.SimType;
import cellsociety_team16.Util.Pos;

public interface SimHandler {
	void onChangeStateOf(Pos position);
	void onSetEdge(EdgeType edge);
	void onSetShape(ShapeType shape);
	void onSetSimulation(SimType sim);
	void onReset();
	void onStep();
	void onChangeDimension();
	void onLoadExistingXML(File file);
	void onCheckBox(Boolean checked);
	void onSubmitSpec(SimType simType);
}
