package xaos.panels;

import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import xaos.actions.ActionPriorityManager;
import xaos.main.Game;
import xaos.tiles.Tile;
import xaos.utils.ColorGL;
import xaos.utils.UtilsGL;


public final class PrioritiesUIPanel {

	public static Point MOUSE_PRIORITIES_PANEL_ITEMS_POINT = new Point (UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS, -1);
	public static Point MOUSE_PRIORITIES_PANEL_ITEMS_UP_POINT = new Point (UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS_UP, -1);
	public static Point MOUSE_PRIORITIES_PANEL_ITEMS_DOWN_POINT = new Point (UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS_DOWN, -1);

	private final static int PRIORITIES_PANEL_ITEM_SIZE = 64;
	public static int PRIORITIES_PANEL_NUM_ITEMS;
	public static int PRIORITIES_PANEL_WIDTH;
	public static int PRIORITIES_PANEL_HEIGHT;

	// PRIORITIES panel
	public static Tile[] tilePrioritiesPanel;
	public static Point prioritiesPanelPoint = new Point (0, 0);
	public static boolean prioritiesPanelActive = false;

	private static ArrayList<Point> prioritiesPanelItemsPosition;
	public static Tile tilePrioritiesPanelUpIcon;
	public static boolean tilePrioritiesPanelUpIconAlpha[][];
	private static ArrayList<Point> prioritiesPanelItemsUpPosition;
	public static Tile tilePrioritiesPanelDownIcon;
	public static boolean tilePrioritiesPanelDownIconAlpha[][];
	private static ArrayList<Point> prioritiesPanelItemsDownPosition;

	public static void renderPrioritiesPanel (int mouseX, int mouseY, int mousePanel) {
		// XAVI GL11.glColor4f (1, 1, 1, 1);
		int iCurrentTexture = tilePrioritiesPanel[0].getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UIPanel.renderBackground (tilePrioritiesPanel, prioritiesPanelPoint, PRIORITIES_PANEL_WIDTH, PRIORITIES_PANEL_HEIGHT);

		// Items
		Point point;
		Point pItem;
		if (mousePanel == UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS || mousePanel == UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS_UP || mousePanel == UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS_DOWN) {
			pItem = isMouseOnPrioritiesItems (mouseX, mouseY);
		} else {
			pItem = null;
		}

		for (int i = 0; i < PRIORITIES_PANEL_NUM_ITEMS; i++) {
			point = prioritiesPanelItemsPosition.get (i);
			// Round button
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileBottomItem, point, PRIORITIES_PANEL_ITEM_SIZE, PRIORITIES_PANEL_ITEM_SIZE, (pItem != null && pItem.x == UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS && pItem.y == i));

			// Icono
			if (i < (PRIORITIES_PANEL_NUM_ITEMS - 1)) {
				// Icono de UI que toque
				Tile tile = ActionPriorityManager.getItem (ActionPriorityManager.getPrioritiesList ().get (i)).getIcon ();
				iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
				UIPanel.drawTile (tile, point, PRIORITIES_PANEL_ITEM_SIZE, PRIORITIES_PANEL_ITEM_SIZE, (pItem != null && pItem.x == UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS && pItem.y == i));
			} else {
				// Back
				iCurrentTexture = UtilsGL.setTexture (UIPanel.BACK_TILE, iCurrentTexture);
				UIPanel.drawTile (UIPanel.BACK_TILE, point, PRIORITIES_PANEL_ITEM_SIZE, PRIORITIES_PANEL_ITEM_SIZE, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS && pItem.y == i));
			}

			point = prioritiesPanelItemsUpPosition.get (i);
			if (point.x != -1) {
				// Up
				iCurrentTexture = UtilsGL.setTexture (tilePrioritiesPanelUpIcon, iCurrentTexture);
				UIPanel.drawTile (tilePrioritiesPanelUpIcon, point, UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS_UP && pItem.y == i));
			}
			point = prioritiesPanelItemsDownPosition.get (i);
			if (point.x != -1) {
				// Down
				iCurrentTexture = UtilsGL.setTexture (tilePrioritiesPanelDownIcon, iCurrentTexture);
				UIPanel.drawTile (tilePrioritiesPanelDownIcon, point, UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRIORITIES_PANEL_ITEMS_DOWN && pItem.y == i));
			}
		}
		UtilsGL.glEnd ();

		// Render priority numbers
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		for (int i = 0; i < PRIORITIES_PANEL_NUM_ITEMS - 1; i++) {
			point = prioritiesPanelItemsPosition.get (i);
			UtilsGL.drawStringWithBorder (Integer.toString ((i + 1)), point.x, point.y, ColorGL.WHITE, ColorGL.BLACK);
		}
		UtilsGL.glEnd ();
	}

	public static boolean isMouseOnPrioritiesPanel (int x, int y) {
		return ((y >= prioritiesPanelPoint.y && y < (prioritiesPanelPoint.y + PRIORITIES_PANEL_HEIGHT)) && (x >= prioritiesPanelPoint.x && x < (prioritiesPanelPoint.x + PRIORITIES_PANEL_WIDTH)));
	}

	public static Point isMouseOnPrioritiesItems (int x, int y) {
		if (UIPanel.typingPanel != null) {
			return null;
		}

		if (y >= prioritiesPanelPoint.y && y < (prioritiesPanelPoint.y + PRIORITIES_PANEL_HEIGHT) && x >= prioritiesPanelPoint.x && x < (prioritiesPanelPoint.x + PRIORITIES_PANEL_WIDTH)) {
			Point point;
			for (int i = 0; i < PRIORITIES_PANEL_NUM_ITEMS; i++) {
				point = prioritiesPanelItemsPosition.get (i);
				if (x >= point.x && x < (point.x + PRIORITIES_PANEL_ITEM_SIZE) && y >= point.y && y < (point.y + PRIORITIES_PANEL_ITEM_SIZE)) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_PRIORITIES_PANEL_ITEMS_POINT.y = i;
						return MOUSE_PRIORITIES_PANEL_ITEMS_POINT;
					}
				}
				point = prioritiesPanelItemsUpPosition.get (i);
				if (x >= point.x && x < (point.x + UIPanel.ICON_WIDTH) && y >= point.y && y < (point.y + UIPanel.ICON_HEIGHT)) {
					if (!tilePrioritiesPanelUpIconAlpha[x - point.x][y - point.y]) {
						MOUSE_PRIORITIES_PANEL_ITEMS_UP_POINT.y = i;
						return MOUSE_PRIORITIES_PANEL_ITEMS_UP_POINT;
					}
				}
				point = prioritiesPanelItemsDownPosition.get (i);
				if (x >= point.x && x < (point.x + UIPanel.ICON_WIDTH) && y >= point.y && y < (point.y + UIPanel.ICON_HEIGHT)) {
					if (!tilePrioritiesPanelDownIconAlpha[x - point.x][y - point.y]) {
						MOUSE_PRIORITIES_PANEL_ITEMS_DOWN_POINT.y = i;
						return MOUSE_PRIORITIES_PANEL_ITEMS_DOWN_POINT;
					}
				}
			}
		}

		return null;
	}

	public static void setPrioritiesPanelActive (boolean prioritiesPanelActive) {
		PrioritiesUIPanel.prioritiesPanelActive = prioritiesPanelActive;
		if (prioritiesPanelActive) {
			UIPanel.closePanels (false, true, true, true, true, true, true);
		}
	}


	public static boolean isPrioritiesPanelActive () {
		return prioritiesPanelActive;
	}

	public static void createPrioritiesPanel () {
		PRIORITIES_PANEL_NUM_ITEMS = ActionPriorityManager.getPrioritiesListSize () + 1; // +1 para el back

		// Miramos la separación entre items
		int iPixelsBetweenItems;
		if (PRIORITIES_PANEL_NUM_ITEMS > 1) {
			iPixelsBetweenItems = UIPanel.PIXELS_TO_BORDER;
		} else {
			iPixelsBetweenItems = 0;
		}

		// Tenemos el tamaño de los items
		PRIORITIES_PANEL_WIDTH = PRIORITIES_PANEL_ITEM_SIZE + 2 * tilePrioritiesPanelUpIcon.getTileWidth ();
		PRIORITIES_PANEL_HEIGHT = 2 * UIPanel.PIXELS_TO_BORDER + (PRIORITIES_PANEL_NUM_ITEMS * PRIORITIES_PANEL_ITEM_SIZE) + ((PRIORITIES_PANEL_NUM_ITEMS - 1) * iPixelsBetweenItems);

		// Número de columnas para que quepa
		int MAX_ITEMS_PER_COLUMN = PRIORITIES_PANEL_NUM_ITEMS;
		int iNumColumns;
		int iMaxHeight = (BottomMenuUIPanel.bottomPanelY - UIPanel.PIXELS_TO_BORDER) - (20 + 2 * UIPanel.PIXELS_TO_BORDER);

		if (PRIORITIES_PANEL_NUM_ITEMS > 1 && PRIORITIES_PANEL_HEIGHT > iMaxHeight) {
			if (iMaxHeight - 2 * UIPanel.PIXELS_TO_BORDER != 0) { // Check división por 0
				iNumColumns = PRIORITIES_PANEL_HEIGHT / (iMaxHeight - 2 * UIPanel.PIXELS_TO_BORDER); // Realmente no entiendo el 2*PIXELS en esta operación
				if (PRIORITIES_PANEL_HEIGHT % (iMaxHeight - 2 * UIPanel.PIXELS_TO_BORDER) != 0) {
					iNumColumns++;
				}
				if (iNumColumns < 1) {
					iNumColumns = 1;
				}
			} else {
				iNumColumns = PRIORITIES_PANEL_NUM_ITEMS;
			}

			MAX_ITEMS_PER_COLUMN = (PRIORITIES_PANEL_NUM_ITEMS / iNumColumns);
			if (PRIORITIES_PANEL_NUM_ITEMS % iNumColumns != 0) {
				MAX_ITEMS_PER_COLUMN++;
			}
			if (MAX_ITEMS_PER_COLUMN < 1) {
				MAX_ITEMS_PER_COLUMN = 1;
			}

			PRIORITIES_PANEL_WIDTH = iNumColumns * (PRIORITIES_PANEL_ITEM_SIZE + 2 * tilePrioritiesPanelUpIcon.getTileWidth () + UIPanel.PIXELS_TO_BORDER);
			PRIORITIES_PANEL_HEIGHT = 2 * UIPanel.PIXELS_TO_BORDER + (MAX_ITEMS_PER_COLUMN * PRIORITIES_PANEL_ITEM_SIZE) + ((MAX_ITEMS_PER_COLUMN - 1) * iPixelsBetweenItems);
		} else {
			iNumColumns = 1;
		}

		prioritiesPanelPoint.setLocation (UIPanel.renderWidth / 2 - PRIORITIES_PANEL_WIDTH / 2, UIPanel.renderHeight / 2 - PRIORITIES_PANEL_HEIGHT / 2);

		// Positions
		prioritiesPanelItemsPosition = new ArrayList<Point> ();
		prioritiesPanelItemsUpPosition = new ArrayList<Point> ();
		prioritiesPanelItemsDownPosition = new ArrayList<Point> ();
		Point p;
		int iColumnCounter = 0, iColumnIndex = 0;
		for (int i = 0; i < PRIORITIES_PANEL_NUM_ITEMS; i++) {
			p = new Point (prioritiesPanelPoint.x + tilePrioritiesPanelUpIcon.getTileWidth () + (iColumnIndex * (PRIORITIES_PANEL_ITEM_SIZE + 2 * UIPanel.ICON_WIDTH + iPixelsBetweenItems)), prioritiesPanelPoint.y + UIPanel.PIXELS_TO_BORDER + ((i - (iColumnIndex * MAX_ITEMS_PER_COLUMN)) * (PRIORITIES_PANEL_ITEM_SIZE + iPixelsBetweenItems)));
			prioritiesPanelItemsPosition.add (p);
			if (i != (PRIORITIES_PANEL_NUM_ITEMS - 1)) {
				if (i > 0) {
					prioritiesPanelItemsUpPosition.add (new Point (p.x - UIPanel.ICON_WIDTH, p.y + PRIORITIES_PANEL_ITEM_SIZE / 2 - UIPanel.ICON_HEIGHT / 2));
				} else {
					prioritiesPanelItemsUpPosition.add (new Point (-1, -1));
				}
				if (i < (PRIORITIES_PANEL_NUM_ITEMS - 2)) {
					prioritiesPanelItemsDownPosition.add (new Point (p.x + PRIORITIES_PANEL_ITEM_SIZE, p.y + PRIORITIES_PANEL_ITEM_SIZE / 2 - UIPanel.ICON_HEIGHT / 2));
				} else {
					prioritiesPanelItemsDownPosition.add (new Point (-1, -1));
				}
			} else {
				prioritiesPanelItemsUpPosition.add (new Point (-1, -1));
				prioritiesPanelItemsDownPosition.add (new Point (-1, -1));
			}

			iColumnCounter++;
			if (iColumnCounter == MAX_ITEMS_PER_COLUMN) {
				iColumnCounter = 0;
				iColumnIndex++;
			}
		}
	}
}
