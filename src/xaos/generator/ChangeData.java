package xaos.generator;

import xaos.utils.UtilsString;

public class ChangeData extends ParentMapData {

    public String source = null;
    public String destination = null;
    public String terrain = null;
    public int pct = 0;
    public int radius = 1;
    public int iHeightMin = -1;
    public int iHeightMax = -1;

    public ChangeData(GeneratorItem item) {
        for (int i = 0; i < item.getList().size(); i++) {
            if (item.getList().get(i).getName().equalsIgnoreCase(MapGeneratorItem.ITEM_CHANGE_SOURCE)) {
                source = item.getList().get(i).getValue();
            } else if (item.getList().get(i).getName().equalsIgnoreCase(MapGeneratorItem.ITEM_CHANGE_DESTINATION)) {
                destination = item.getList().get(i).getValue();
            } else if (item.getList().get(i).getName().equalsIgnoreCase(MapGeneratorItem.ITEM_CHANGE_TERRAIN)) {
                terrain = item.getList().get(i).getValue();
            } else if (item.getList().get(i).getName().equalsIgnoreCase(MapGeneratorItem.ITEM_CHANGE_PCT)) {
                pct = UtilsString.getInteger(item.getList().get(i).getValue(), 0);
            } else if (item.getList().get(i).getName().equalsIgnoreCase(MapGeneratorItem.ITEM_CHANGE_RADIUS)) {
                radius = UtilsString.getInteger(item.getList().get(i).getValue(), 1);
            } else if (item.getList().get(i).getName().equalsIgnoreCase(MapGeneratorItem.ITEM_CHANGE_HEIGHT_MIN)) {
                iHeightMin = UtilsString.getInteger(item.getList().get(i).getValue(), -1);
            } else if (item.getList().get(i).getName().equalsIgnoreCase(MapGeneratorItem.ITEM_CHANGE_HEIGHT_MAX)) {
                iHeightMax = UtilsString.getInteger(item.getList().get(i).getValue(), -1);
            }
        }
    }
}
