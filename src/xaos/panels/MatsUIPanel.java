package xaos.panels;

import java.awt.Point;
import org.lwjgl.opengl.GL11;
import xaos.main.Game;
import xaos.main.World;
import xaos.tiles.Tile;
import xaos.tiles.entities.items.Item;
import xaos.utils.ColorGL;
import xaos.utils.UtilFont;
import xaos.utils.UtilsGL;
import xaos.utils.UtilsIniHeaders;


public final class MatsUIPanel {

	public static Point MOUSE_MATS_PANEL_BUTTONS_CLOSE_POINT = new Point (UIPanel.MOUSE_MATS_PANEL_BUTTONS_CLOSE, -1);

	public static Point MOUSE_MATS_PANEL_BUTTONS_GROUPS_POINT = new Point (UIPanel.MOUSE_MATS_PANEL_BUTTONS_GROUPS, -1);

	public static Point MOUSE_MATS_PANEL_BUTTONS_ITEMS_POINT = new Point (UIPanel.MOUSE_MATS_PANEL_BUTTONS_ITEMS, -1);

	public static Point MOUSE_MATS_PANEL_BUTTONS_SCROLL_UP_POINT = new Point (UIPanel.MOUSE_MATS_PANEL_BUTTONS_SCROLL_UP, -1);

	public static Point MOUSE_MATS_PANEL_BUTTONS_SCROLL_DOWN_POINT = new Point (UIPanel.MOUSE_MATS_PANEL_BUTTONS_SCROLL_DOWN, -1);

	public static int MATS_PANEL_WIDTH;

	public static int MATS_PANEL_HEIGHT;

	public static int MATS_PANEL_SUBPANEL_WIDTH;

	public static int MATS_PANEL_SUBPANEL_HEIGHT;

	public static int MATS_PANEL_MAX_ITEMS_PER_PAGE;

	// MATS panel
	public static Tile[] tileMatsPanel;

	public static Tile[] tileMatsPanelSubPanel;

	private static Point matsPanelPoint = new Point (0, 0);

	private static int matsPanelActive = -1;

	public static Point matsPanelClosePoint = new Point (0, 0);

	private static Point matsPanelIconScrollUpPoint = new Point (0, 0);

	private static Point matsPanelIconScrollDownPoint = new Point (0, 0);

	private static Tile[] matsPanelTiles;

	private static Tile[] matsPanelTilesON;

	public static Point matsPanelSubPanelPoint = new Point (0, 0);

	private static Point[] matsPanelIconPoints;

	private static Point[] matsPanelItemPoints;

	private static Point matsPanelPagesPositionPoint = new Point (0, 0);

	public static int[] matsNumPages;

	public static int[] matsIndexPages;

	private static int matsLastGroup = -1;

	public static void renderMatsPanel (int mouseX, int mouseY, int mousePanel) {
		Point pItem = isMouseOnMatsButtons (mouseX, mouseY);

		// XAVI GL11.glColor4f (1, 1, 1, 1);
		int iCurrentTexture = tileMatsPanel[0].getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UIPanel.renderBackground (tileMatsPanel, matsPanelPoint, MATS_PANEL_WIDTH, MATS_PANEL_HEIGHT);

		// Close button
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileButtonClose, iCurrentTexture);
		if (pItem != null && pItem.x == UIPanel.MOUSE_MATS_PANEL_BUTTONS_CLOSE) {
			UIPanel.drawTile (UIPanel.tileButtonClose, matsPanelClosePoint);
		} else {
			UIPanel.drawTile (UIPanel.tileButtonCloseDisabled, matsPanelClosePoint);
		}

		// Subpanel donde irán los items
		iCurrentTexture = UtilsGL.setTexture (tileMatsPanelSubPanel[0], iCurrentTexture);
		UIPanel.renderBackground (tileMatsPanelSubPanel, matsPanelSubPanelPoint, MATS_PANEL_SUBPANEL_WIDTH, MATS_PANEL_SUBPANEL_HEIGHT);

		// "Tabs"
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
		for (int i = 0; i < MatsPanelData.numGroups; i++) {
			UIPanel.drawTile (UIPanel.tileBottomItem, matsPanelIconPoints[i], (pItem != null && pItem.x == UIPanel.MOUSE_MATS_PANEL_BUTTONS_GROUPS && pItem.y == i));
		}
		for (int i = 0; i < MatsPanelData.numGroups; i++) {
			if (i == getMatsPanelActive ()) {
				iCurrentTexture = UtilsGL.setTexture (matsPanelTilesON[i], iCurrentTexture);
				UIPanel.drawTile (matsPanelTilesON[i], matsPanelIconPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - matsPanelTilesON[i].getTileWidth () / 2, matsPanelIconPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - matsPanelTilesON[i].getTileHeight () / 2, (pItem != null && pItem.x == UIPanel.MOUSE_MATS_PANEL_BUTTONS_GROUPS && pItem.y == i));
			} else {
				iCurrentTexture = UtilsGL.setTexture (matsPanelTiles[i], iCurrentTexture);
				UIPanel.drawTile (matsPanelTiles[i], matsPanelIconPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - matsPanelTiles[i].getTileWidth () / 2, matsPanelIconPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - matsPanelTiles[i].getTileHeight () / 2, (pItem != null && pItem.x == UIPanel.MOUSE_MATS_PANEL_BUTTONS_GROUPS && pItem.y == i));
			}
		}

		// Scrolls
		if (matsIndexPages[getMatsPanelActive ()] > 0) {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUp, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUp, matsPanelIconScrollUpPoint, (pItem != null && pItem.x == UIPanel.MOUSE_MATS_PANEL_BUTTONS_SCROLL_UP));
		} else {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUpDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUpDisabled, matsPanelIconScrollUpPoint);
		}
		if (matsIndexPages[getMatsPanelActive ()] < (matsNumPages[getMatsPanelActive ()] - 1)) {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDown, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDown, matsPanelIconScrollDownPoint, (pItem != null && pItem.x == UIPanel.MOUSE_MATS_PANEL_BUTTONS_SCROLL_DOWN));
		} else {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDownDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDownDisabled, matsPanelIconScrollDownPoint);
		}

		// Icons
		int iIndex = matsIndexPages[getMatsPanelActive ()] * MATS_PANEL_MAX_ITEMS_PER_PAGE;
		int iMax = Math.min (MATS_PANEL_MAX_ITEMS_PER_PAGE, (MatsPanelData.tileGroups.get (getMatsPanelActive ()).size () - iIndex));
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
		for (int i = 0; i < iMax; i++) {
			UIPanel.drawTile (UIPanel.tileBottomItem, matsPanelItemPoints[i]);
		}
		Tile tile;
		for (int i = 0; i < iMax; i++) {
			if (MatsPanelData.tileGroups.get (getMatsPanelActive ()).size () > (i + iIndex)) {
				tile = MatsPanelData.tileGroups.get (getMatsPanelActive ()).get (i + iIndex);
				iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
				// drawTile (tile, matsPanelItemPoints [i].x + tileBottomItem.getTileWidth () / 2 - tile.getTileWidth () / 2, matsPanelItemPoints [i].y + tileBottomItem.getTileHeight () / 2 - tile.getTileHeight () / 2, false); //BOTTOM_ITEM_WIDTH, BOTTOM_ITEM_HEIGHT, false);
				UIPanel.drawTile (tile, matsPanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tile.getTileWidth () / 2, matsPanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - UIPanel.BOTTOM_ITEM_HEIGHT / 2, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, false); // BOTTOM_ITEM_WIDTH, BOTTOM_ITEM_HEIGHT, false);
			}
		}
		UtilsGL.glEnd ();

		// Numbers
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		String sText;
		for (int i = 0; i < iMax; i++) {
			if (MatsPanelData.tileGroups.get (getMatsPanelActive ()).size () > (i + iIndex)) {
				tile = MatsPanelData.tileGroups.get (getMatsPanelActive ()).get (i + iIndex);
				sText = Integer.toString (Item.getNumItems (UtilsIniHeaders.getIntIniHeader (tile.getIniHeader ()), false, World.MAP_DEPTH));
				UtilsGL.drawStringWithBorder (sText, matsPanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - UtilFont.getWidth (sText) / 2, matsPanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () - UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
			}
		}

		// Pages
		sText = (matsIndexPages[getMatsPanelActive ()] + 1) + " / " + matsNumPages[getMatsPanelActive ()]; //$NON-NLS-1$
		UtilsGL.drawString (sText, matsPanelPagesPositionPoint.x - UtilFont.getWidth (sText) / 2, matsPanelPagesPositionPoint.y, ColorGL.BLACK);
		UtilsGL.glEnd ();
	}

	public static boolean isMouseOnMatsPanel (int x, int y) {
		return ((y >= matsPanelPoint.y && y < (matsPanelPoint.y + MATS_PANEL_HEIGHT)) && (x >= matsPanelPoint.x && x < (matsPanelPoint.x + MATS_PANEL_WIDTH)));
	}

	public static Point isMouseOnMatsButtons (int x, int y) {
		if (UIPanel.typingPanel != null) {
			return null;
		}

		Point point;
		// Close button
		point = matsPanelClosePoint;
		if (x >= point.x && x < (point.x + UIPanel.tileButtonClose.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileButtonClose.getTileHeight ())) {
			if (!UIPanel.tileButtonCloseAlpha[x - point.x][y - point.y]) {
				return MOUSE_MATS_PANEL_BUTTONS_CLOSE_POINT;
			}
		}

		// Scrolls
		point = matsPanelIconScrollUpPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
			if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_MATS_PANEL_BUTTONS_SCROLL_UP_POINT;
			}
		}
		point = matsPanelIconScrollDownPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
			if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_MATS_PANEL_BUTTONS_SCROLL_DOWN_POINT;
			}
		}

		// Groups
		for (int i = 0; i < MatsPanelData.numGroups; i++) {
			point = matsPanelIconPoints[i];
			if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
				if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
					MOUSE_MATS_PANEL_BUTTONS_GROUPS_POINT.y = i;
					return MOUSE_MATS_PANEL_BUTTONS_GROUPS_POINT;
				}
			}
		}

		// Items
		if (getMatsPanelActive () != -1) {
			int iFirstIndex = matsIndexPages[getMatsPanelActive ()] * MATS_PANEL_MAX_ITEMS_PER_PAGE;
			int iMin = Math.min (MatsPanelData.tileGroups.get (getMatsPanelActive ()).size () - iFirstIndex, MATS_PANEL_MAX_ITEMS_PER_PAGE);
			for (int i = 0; i < iMin; i++) {
				point = matsPanelItemPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_MATS_PANEL_BUTTONS_ITEMS_POINT.y = i + iFirstIndex;
						return MOUSE_MATS_PANEL_BUTTONS_ITEMS_POINT;
					}
				}
			}
		}

		return null;
	}

	public static void setMatsPanelActive (boolean bActive) {
		if (bActive) {
			setMatsPanelActive (matsLastGroup);
		} else {
			setMatsPanelActive (-1);
		}
	}

	public static void setMatsPanelActive (int iGroup) {
		MatsUIPanel.matsPanelActive = iGroup;
		if (iGroup != -1) {
			UIPanel.closePanels (true, true, true, false, true, true, true);
			matsLastGroup = iGroup;
		}
	}

	public static int getMatsPanelActive () {
		return matsPanelActive;
	}

	public static boolean isMatsPanelActive () {
		return matsPanelActive != -1;
	}

	public static void createMatsPanel () {
		// Groups, si peta sale del juego
		MatsPanelData.loadGroups ();

		MATS_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7;
		MATS_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () - UIPanel.tileBottomItem.getTileHeight () / 2;

		matsPanelPoint.setLocation (UIPanel.renderWidth / 8 - ((UIPanel.renderWidth / 8) / 2), UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 4);
		matsPanelClosePoint.setLocation (matsPanelPoint.x + MATS_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), matsPanelPoint.y);

		// Iconos dentro del panel
		// Los "tabs" (iconos gordos arriba)
		if (matsPanelTiles == null) {
			matsPanelTiles = new Tile [MatsPanelData.numGroups];
			matsPanelTilesON = new Tile [MatsPanelData.numGroups];
			for (int i = 0; i < MatsPanelData.numGroups; i++) {
				matsPanelTiles[i] = new Tile (MatsPanelData.iconGroups.get (i));
				matsPanelTilesON[i] = new Tile (MatsPanelData.iconGroups.get (i) + "ON"); //$NON-NLS-1$
			}
		}

		matsLastGroup = 0;

		// Scroll up/down
		matsPanelIconScrollUpPoint.setLocation (matsPanelPoint.x + MATS_PANEL_WIDTH - tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), matsPanelPoint.y + tileMatsPanel[1].getTileHeight () + UIPanel.tileBottomItem.getTileHeight ());
		matsPanelIconScrollDownPoint.setLocation (matsPanelPoint.x + MATS_PANEL_WIDTH - tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), matsPanelPoint.y + MATS_PANEL_HEIGHT - UIPanel.tileScrollDown.getTileHeight () - tileMatsPanel[1].getTileHeight ());

		// Pages
		matsPanelPagesPositionPoint.setLocation (matsPanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () / 2, matsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight () + (matsPanelIconScrollDownPoint.y - (matsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight ())) / 2 - UtilFont.MAX_HEIGHT / 2);

		// Subpanel
		MATS_PANEL_SUBPANEL_WIDTH = MATS_PANEL_WIDTH - (3 * tileMatsPanelSubPanel[3].getTileWidth () + UIPanel.tileScrollUp.getTileWidth ());
		MATS_PANEL_SUBPANEL_HEIGHT = (matsPanelIconScrollDownPoint.y + UIPanel.tileScrollDown.getTileHeight ()) - matsPanelIconScrollUpPoint.y;
		matsPanelSubPanelPoint.setLocation (matsPanelPoint.x + tileMatsPanelSubPanel[3].getTileWidth (), matsPanelIconScrollUpPoint.y);

		// Posición de iconos (los X de arriba) dentro del panel (va aquí pq tienen que centrarse con el subpanel)
		matsPanelIconPoints = new Point [MatsPanelData.numGroups];
		int iSeparation = (MATS_PANEL_SUBPANEL_WIDTH - (MatsPanelData.numGroups * UIPanel.tileBottomItem.getTileWidth ())) / (MatsPanelData.numGroups + 1);
		for (int i = 0; i < MatsPanelData.numGroups; i++) {
			matsPanelIconPoints[i] = new Point (matsPanelSubPanelPoint.x + iSeparation + (i * (UIPanel.tileBottomItem.getTileWidth () + iSeparation)), matsPanelPoint.y + tileMatsPanel[1].getTileHeight ());
		}

		// Ahora miramos de cuantas filas y columnas disponemos y seteamos el array de posiciones
		int iMaxItemsWidth = (MATS_PANEL_SUBPANEL_WIDTH - 2 * tileMatsPanelSubPanel[3].getTileWidth ()) / (UIPanel.tileBottomItem.getTileWidth () + 8);
		int iMaxItemsHeight = (MATS_PANEL_SUBPANEL_HEIGHT - 2 * tileMatsPanelSubPanel[1].getTileHeight ()) / (UIPanel.tileBottomItem.getTileHeight () + 8);

		MATS_PANEL_MAX_ITEMS_PER_PAGE = iMaxItemsWidth * iMaxItemsHeight;
		matsPanelItemPoints = new Point [MATS_PANEL_MAX_ITEMS_PER_PAGE];
		int iSeparationW = (MATS_PANEL_SUBPANEL_WIDTH - 2 * tileMatsPanelSubPanel[3].getTileWidth () - iMaxItemsWidth * UIPanel.tileBottomItem.getTileWidth ()) / (iMaxItemsWidth + 1);
		int iSeparationH = (MATS_PANEL_SUBPANEL_HEIGHT - 2 * tileMatsPanelSubPanel[1].getTileHeight () - iMaxItemsHeight * UIPanel.tileBottomItem.getTileHeight ()) / (iMaxItemsHeight + 1);
		int iFirstWidth = matsPanelSubPanelPoint.x + tileMatsPanelSubPanel[3].getTileWidth () + iSeparationW;
		int iFirstHeight = matsPanelSubPanelPoint.y + tileMatsPanelSubPanel[1].getTileHeight () + iSeparationH;
		int x = iFirstWidth;
		int y = iFirstHeight;
		for (int i = 0; i < MATS_PANEL_MAX_ITEMS_PER_PAGE; i++) {
			matsPanelItemPoints[i] = new Point (x, y);
			x += (UIPanel.tileBottomItem.getTileWidth () + iSeparationW);
			if (x > (matsPanelSubPanelPoint.x + MATS_PANEL_SUBPANEL_WIDTH - tileMatsPanelSubPanel[3].getTileWidth () - UIPanel.tileBottomItem.getTileWidth () - 1)) {
				y += (UIPanel.tileBottomItem.getTileHeight () + iSeparationH);
				x = iFirstWidth;
			}
		}

		// Pages
		matsNumPages = new int [MatsPanelData.numGroups];
		matsIndexPages = new int [MatsPanelData.numGroups];
		for (int i = 0; i < MatsPanelData.numGroups; i++) {
			if (MatsPanelData.tileGroups.get (i).size () % MATS_PANEL_MAX_ITEMS_PER_PAGE == 0) {
				matsNumPages[i] = MatsPanelData.tileGroups.get (i).size () / MATS_PANEL_MAX_ITEMS_PER_PAGE;
			} else {
				matsNumPages[i] = (MatsPanelData.tileGroups.get (i).size () / MATS_PANEL_MAX_ITEMS_PER_PAGE) + 1;
			}

			matsIndexPages[i] = 0;
		}
	}
}
