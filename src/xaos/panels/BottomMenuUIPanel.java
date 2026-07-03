package xaos.panels;

import java.awt.Point;
import java.util.ArrayList;
import xaos.campaign.TutorialFlow;
import xaos.panels.menus.SmartMenu;
import xaos.tiles.Tile;
import xaos.utils.UtilsGL;


public final class BottomMenuUIPanel {

	public final static int BOTTOM_PANEL_SCROLL_WIDTH = 32;

	public final static int BOTTOM_PANEL_WIDTH = 1024 - (BOTTOM_PANEL_SCROLL_WIDTH * 2);

	public final static int BOTTOM_PANEL_HEIGHT = 64;

	public final static int BOTTOM_PANEL_NUM_ITEMS = 10;

	private static int BOTTOM_SUBPANEL_WIDTH;

	private static int BOTTOM_SUBPANEL_HEIGHT;

	private static int BOTTOM_SUBPANEL_NUM_ITEMS_X;

	private static int BOTTOM_SUBPANEL_NUM_ITEMS_Y;

	private static int BOTTOM_SUBITEM_WIDTH = 64;

	private static int BOTTOM_SUBITEM_HEIGHT = 64;

	public static SmartMenu currentMenu;

	private static boolean bottomMenuPanelActive = true;

	private static boolean bottomMenuPanelLocked = true;

	// BOTTOM panel
	public static ArrayList<Point> bottomPanelItemsPosition; // Array de sólo BOTTOM_PANEL_NUM_ITEMS posiciones (9) con las coordenadas de los items que caben

	public static int bottomPanelItemIndex;

	public static int bottomPanelX;

	public static int bottomPanelY;

	public static int bottomPanelLeftScrollX;

	public static int bottomPanelRightScrollX;

	public static Tile tileBottomScrollLeft;

	public static Tile tileBottomScrollLeftON;

	public static Tile tileBottomScrollRight;

	public static Tile tileBottomScrollRightON;

	public static Tile tileBottomPanel;

	public static boolean tileBottomScrollLeftAlpha[][];

	public static boolean tileBottomScrollRightAlpha[][];

	public static boolean tileBottomPanelAlpha[][];

	public static Tile tileOpenBottomMenu;

	public static Point tileOpenCloseBottomMenuPoint = new Point (0, 0);

	private static ArrayList<Point> bottomSubPanelItemsPosition; // Array de BOTTOM_SUBPANEL_NUM_ITEMS_X x BOTTOM_SUBPANEL_NUM_ITEMS_Y posiciones con las coordenadas de los subitems

	public static Point bottomSubPanelPoint = new Point (0, 0);

	public static Tile[] tileBottomSubPanel;

	public static SmartMenu bottomSubPanelMenu;

	public static boolean tileBottomSubItemAlpha[][];

	// Menu Blinks
	public static boolean checkBlinkBottom = false;

	public static int renderBottomMenuPanel (int mouseX, int mouseY, int mousePanel, int iCurrentTexture) {
		/*
		 * BOTTOM PANEL
		 */
		// Left scroll
		if (mousePanel == UIPanel.MOUSE_BOTTOM_LEFT_SCROLL && bottomPanelItemIndex > 0) {
			UtilsGL.drawTexture (bottomPanelLeftScrollX, bottomPanelY, bottomPanelLeftScrollX + BOTTOM_PANEL_SCROLL_WIDTH, bottomPanelY + BOTTOM_PANEL_HEIGHT, tileBottomScrollLeftON.getTileSetTexX0 (), tileBottomScrollLeftON.getTileSetTexY0 (), tileBottomScrollLeftON.getTileSetTexX1 (), tileBottomScrollLeftON.getTileSetTexY1 ());
		} else {
			UtilsGL.drawTexture (bottomPanelLeftScrollX, bottomPanelY, bottomPanelLeftScrollX + BOTTOM_PANEL_SCROLL_WIDTH, bottomPanelY + BOTTOM_PANEL_HEIGHT, tileBottomScrollLeft.getTileSetTexX0 (), tileBottomScrollLeft.getTileSetTexY0 (), tileBottomScrollLeft.getTileSetTexX1 (), tileBottomScrollLeft.getTileSetTexY1 ());
		}

		// Right scroll
		iCurrentTexture = UtilsGL.setTexture (tileBottomScrollRight, iCurrentTexture);
		if (mousePanel == UIPanel.MOUSE_BOTTOM_RIGHT_SCROLL && (bottomPanelItemIndex + BOTTOM_PANEL_NUM_ITEMS) < currentMenu.getItems ().size ()) {
			UtilsGL.drawTexture (bottomPanelRightScrollX, bottomPanelY, bottomPanelRightScrollX + BOTTOM_PANEL_SCROLL_WIDTH, bottomPanelY + BOTTOM_PANEL_HEIGHT, tileBottomScrollRightON.getTileSetTexX0 (), tileBottomScrollRightON.getTileSetTexY0 (), tileBottomScrollRightON.getTileSetTexX1 (), tileBottomScrollRightON.getTileSetTexY1 ());
		} else {
			UtilsGL.drawTexture (bottomPanelRightScrollX, bottomPanelY, bottomPanelRightScrollX + BOTTOM_PANEL_SCROLL_WIDTH, bottomPanelY + BOTTOM_PANEL_HEIGHT, tileBottomScrollRight.getTileSetTexX0 (), tileBottomScrollRight.getTileSetTexY0 (), tileBottomScrollRight.getTileSetTexX1 (), tileBottomScrollRight.getTileSetTexY1 ());
		}

		// Panel itself
		iCurrentTexture = UtilsGL.setTexture (tileBottomPanel, iCurrentTexture);
		UtilsGL.drawTexture (bottomPanelX, bottomPanelY, bottomPanelX + BOTTOM_PANEL_WIDTH, bottomPanelY + BOTTOM_PANEL_HEIGHT, tileBottomPanel.getTileSetTexX0 (), tileBottomPanel.getTileSetTexY0 (), tileBottomPanel.getTileSetTexX1 (), tileBottomPanel.getTileSetTexY1 ());

		// BOTTOM PANEL Items
		int iItemBottomPanel;
		if (mousePanel == UIPanel.MOUSE_BOTTOM_ITEMS) {
			iItemBottomPanel = isMouseOnBottomItems (mouseX, mouseY);
		} else {
			iItemBottomPanel = -1;
		}

		// UI TEXTURE bottom panel
		Point point;
		for (int i = bottomPanelItemIndex; i < bottomPanelItemIndex + BOTTOM_PANEL_NUM_ITEMS; i++) {
			if (i > currentMenu.getItems ().size ()) {
				break;
			}

			point = bottomPanelItemsPosition.get (i - bottomPanelItemIndex);

			// Round button
			if (currentMenu.getItems ().get (i).getType () == SmartMenu.TYPE_MENU) {
				iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItemSM, iCurrentTexture);
				if (checkBlinkBottom && TutorialFlow.currentBlinkBottom (currentMenu.getItems ().get (i).getID ())) {
					UtilsGL.setColorRed ();
					UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomPanel == (i - bottomPanelItemIndex)));
					UtilsGL.unsetColor ();
				} else {
					UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomPanel == (i - bottomPanelItemIndex)));
				}
			} else {
				iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
				if (checkBlinkBottom && TutorialFlow.currentBlinkBottom (currentMenu.getItems ().get (i).getID ())) {
					UtilsGL.setColorRed ();
					UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomPanel == (i - bottomPanelItemIndex)));
					UtilsGL.unsetColor ();
				} else {
					UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomPanel == (i - bottomPanelItemIndex)));
				}
			}

			// Icono
			Tile tile = currentMenu.getItems ().get (i).getIcon ();
			if (tile != null && currentMenu.getItems ().get (i).getIconType () == SmartMenu.ICON_TYPE_UI) {
				iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
				UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomPanel == (i - bottomPanelItemIndex)));
			}
		}

		/*
		 * BOTTOM SUBPANEL
		 */
		int iItemBottomSubPanel;
		if (mousePanel == UIPanel.MOUSE_BOTTOM_SUBITEMS) {
			iItemBottomSubPanel = isMouseOnBottomSubItems (mouseX, mouseY);
		} else {
			iItemBottomSubPanel = -1;
		}
		if (bottomSubPanelMenu != null) {
			// Pintamos el panel
			iCurrentTexture = UtilsGL.setTexture (tileBottomSubPanel[0], iCurrentTexture);
			UIPanel.renderBackground (tileBottomSubPanel, bottomSubPanelPoint, BOTTOM_SUBPANEL_WIDTH, BOTTOM_SUBPANEL_HEIGHT);
			// UtilsGL.drawTexture (bottomSubPanelX, bottomSubPanelY, bottomSubPanelX + BOTTOM_SUBPANEL_WIDTH, bottomSubPanelY + BOTTOM_SUBPANEL_HEIGHT, tileBottomSubPanel.getTileSetTexX0 (), tileBottomSubPanel.getTileSetTexY0 (), tileBottomSubPanel.getTileSetTexX1 (), tileBottomSubPanel.getTileSetTexY1 ());

			// Pintamos los items
			int iMenu;
			bucle1: for (int y = 0; y < BOTTOM_SUBPANEL_NUM_ITEMS_Y; y++) {
				for (int x = 0; x < BOTTOM_SUBPANEL_NUM_ITEMS_X; x++) {
					iMenu = (y * BOTTOM_SUBPANEL_NUM_ITEMS_X) + x;
					if (iMenu >= bottomSubPanelMenu.getItems ().size ()) {
						break bucle1;
					}

					point = bottomSubPanelItemsPosition.get (iMenu);
					// Round button
					if (bottomSubPanelMenu.getItems ().get (iMenu).getType () == SmartMenu.TYPE_MENU) {
						iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItemSM, iCurrentTexture);
						if (checkBlinkBottom && TutorialFlow.currentBlinkBottom (bottomSubPanelMenu.getItems ().get (iMenu).getID ())) {
							UtilsGL.setColorRed ();
							UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomSubPanel == iMenu));
							UtilsGL.unsetColor ();
						} else {
							UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomSubPanel == iMenu));
						}
					} else {
						iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
						if (checkBlinkBottom && TutorialFlow.currentBlinkBottom (bottomSubPanelMenu.getItems ().get (iMenu).getID ())) {
							UtilsGL.setColorRed ();
							UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomSubPanel == iMenu));
							UtilsGL.unsetColor ();
						} else {
							UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomSubPanel == iMenu));
						}
					}

					// Icono
					Tile tile = bottomSubPanelMenu.getItems ().get (iMenu).getIcon ();
					if (tile != null && bottomSubPanelMenu.getItems ().get (iMenu).getIconType () == SmartMenu.ICON_TYPE_UI) {
						iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
						UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomSubPanel == iMenu));
					}
				}
			}
		}

		/*
		 * ITEMS
		 */
		// BOTTOM PANEL
		for (int i = bottomPanelItemIndex; i < bottomPanelItemIndex + BOTTOM_PANEL_NUM_ITEMS; i++) {
			if (i > currentMenu.getItems ().size ()) {
				break;
			}

			point = bottomPanelItemsPosition.get (i - bottomPanelItemIndex);
			// Icono
			Tile tile = currentMenu.getItems ().get (i).getIcon ();
			if (tile != null && currentMenu.getItems ().get (i).getIconType () == SmartMenu.ICON_TYPE_ITEM) {
				iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
				UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomPanel == (i - bottomPanelItemIndex)));
			}
		}

		// BOTTOM SUBPANEL
		if (bottomSubPanelMenu != null) {
			// Ahora los items
			int iMenu;
			Tile tile;
			bucle1: for (int y = 0; y < BOTTOM_SUBPANEL_NUM_ITEMS_Y; y++) {
				for (int x = 0; x < BOTTOM_SUBPANEL_NUM_ITEMS_X; x++) {
					iMenu = (y * BOTTOM_SUBPANEL_NUM_ITEMS_X) + x;
					if (iMenu >= bottomSubPanelMenu.getItems ().size ()) {
						break bucle1;
					}

					point = bottomSubPanelItemsPosition.get (iMenu);
					// Icono
					tile = bottomSubPanelMenu.getItems ().get (iMenu).getIcon ();
					if (tile != null && bottomSubPanelMenu.getItems ().get (iMenu).getIconType () == SmartMenu.ICON_TYPE_ITEM) {
						iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
						UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemBottomSubPanel == iMenu));
					}
				}
			}
		}

		return iCurrentTexture;
	}

	public static boolean isMouseCloseToOpenCloseBottomIcon (int x, int y) {
		return UIPanel.isMouseCloseToIcon (x, y, tileOpenCloseBottomMenuPoint, tileOpenBottomMenu, UIPanel.CLOSE_PIXELS);
	}

	public static boolean isMouseOnBottomPanel (int x, int y) {
		if (y >= bottomPanelY && y < (bottomPanelY + BOTTOM_PANEL_HEIGHT)) {
			// Dentro del panel "virtual", miramos los paneles internos con sus transparencias

			if (x >= bottomPanelX && x < (bottomPanelX + BOTTOM_PANEL_WIDTH)) {
				return (!tileBottomPanelAlpha[x - bottomPanelX][y - bottomPanelY]);
			}
		}

		return false;
	}

	public static boolean isMouseOnBottomLeftScroll (int x, int y) {
		if ((y >= bottomPanelY && y < (bottomPanelY + BOTTOM_PANEL_HEIGHT)) && (x >= bottomPanelLeftScrollX && x < (bottomPanelLeftScrollX + BOTTOM_PANEL_SCROLL_WIDTH))) {
			return !tileBottomScrollLeftAlpha[x - bottomPanelLeftScrollX][y - bottomPanelY];
		}

		return false;
	}

	public static boolean isMouseOnBottomRightScroll (int x, int y) {
		if ((y >= bottomPanelY && y < (bottomPanelY + BOTTOM_PANEL_HEIGHT)) && (x >= bottomPanelRightScrollX && x < (bottomPanelRightScrollX + BOTTOM_PANEL_SCROLL_WIDTH))) {
			return !tileBottomScrollRightAlpha[x - bottomPanelRightScrollX][y - bottomPanelY];
		}

		return false;
	}

	public static int isMouseOnBottomItems (int x, int y) {
		if (y >= bottomPanelY && y < (bottomPanelY + BOTTOM_PANEL_HEIGHT)) {
			Point point;
			for (int i = 0; i < BOTTOM_PANEL_NUM_ITEMS; i++) {
				point = bottomPanelItemsPosition.get (i);
				if (x >= point.x && x < (point.x + UIPanel.BOTTOM_ITEM_WIDTH)) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						return i;
					}
				}
			}
		}

		return -1;
	}

	public static boolean isMouseOnBottomSubPanel (int x, int y) {
		if (x >= bottomSubPanelPoint.x && x < (bottomSubPanelPoint.x + BOTTOM_SUBPANEL_WIDTH) && y >= bottomSubPanelPoint.y && y < (bottomSubPanelPoint.y + BOTTOM_SUBPANEL_HEIGHT)) {
			return true;
		}

		return false;
	}

	public static int isMouseOnBottomSubItems (int x, int y) {
		if (bottomSubPanelMenu != null && y >= bottomSubPanelPoint.y && y < (bottomSubPanelPoint.y + BOTTOM_SUBPANEL_HEIGHT) && x >= bottomSubPanelPoint.x && x < (bottomSubPanelPoint.x + BOTTOM_SUBPANEL_WIDTH)) {
			Point point;
			bucle1: for (int y1 = 0; y1 < BOTTOM_SUBPANEL_NUM_ITEMS_Y; y1++) {
				for (int x1 = 0; x1 < BOTTOM_SUBPANEL_NUM_ITEMS_X; x1++) {
					int i = (y1 * BOTTOM_SUBPANEL_NUM_ITEMS_X) + x1;
					if (i >= bottomSubPanelMenu.getItems ().size ()) {
						break bucle1;
					}
					point = bottomSubPanelItemsPosition.get (i);
					if (x >= point.x && x < (point.x + BOTTOM_SUBITEM_WIDTH) && y >= point.y && y < (point.y + BOTTOM_SUBITEM_HEIGHT)) {
						if (!tileBottomSubItemAlpha[x - point.x][y - point.y]) {
							return i;
						}
					}
				}
			}
		}

		return -1;
	}

	public static void createBottomSubPanel (SmartMenu smItem) {
		int iMaxItems = smItem.getItems ().size ();
		BOTTOM_SUBPANEL_WIDTH = (RightMenuUIPanel.menuPanelPoint.x - bottomPanelX) - 2 * UIPanel.PIXELS_TO_BORDER;

		BOTTOM_SUBPANEL_NUM_ITEMS_X = (BOTTOM_SUBPANEL_WIDTH - UIPanel.PIXELS_TO_BORDER) / (BOTTOM_SUBITEM_WIDTH + UIPanel.PIXELS_TO_BORDER);
		if (BOTTOM_SUBPANEL_NUM_ITEMS_X < 1) {
			BOTTOM_SUBPANEL_NUM_ITEMS_X = 1;
		} else if (BOTTOM_SUBPANEL_NUM_ITEMS_X > iMaxItems) {
			BOTTOM_SUBPANEL_NUM_ITEMS_X = iMaxItems;
		}
		BOTTOM_SUBPANEL_WIDTH = BOTTOM_SUBPANEL_NUM_ITEMS_X * (BOTTOM_SUBITEM_WIDTH + UIPanel.PIXELS_TO_BORDER) + UIPanel.PIXELS_TO_BORDER;

		BOTTOM_SUBPANEL_NUM_ITEMS_Y = iMaxItems / BOTTOM_SUBPANEL_NUM_ITEMS_X;
		if (iMaxItems % BOTTOM_SUBPANEL_NUM_ITEMS_X != 0) {
			BOTTOM_SUBPANEL_NUM_ITEMS_Y++;
		}
		BOTTOM_SUBPANEL_HEIGHT = BOTTOM_SUBPANEL_NUM_ITEMS_Y * (BOTTOM_SUBITEM_HEIGHT + UIPanel.PIXELS_TO_BORDER) + UIPanel.PIXELS_TO_BORDER;

		bottomSubPanelPoint.setLocation (bottomPanelX, bottomPanelY - UIPanel.PIXELS_TO_BORDER - BOTTOM_SUBPANEL_HEIGHT);
		bottomSubPanelItemsPosition = new ArrayList<Point> ();
		bucle1: for (int y1 = 0; y1 < BOTTOM_SUBPANEL_NUM_ITEMS_Y; y1++) {
			for (int x1 = 0; x1 < BOTTOM_SUBPANEL_NUM_ITEMS_X; x1++) {
				if ((y1 * BOTTOM_SUBPANEL_NUM_ITEMS_X + x1) < smItem.getItems ().size ()) {
					bottomSubPanelItemsPosition.add (new Point (bottomSubPanelPoint.x + UIPanel.PIXELS_TO_BORDER + (x1 * (BOTTOM_SUBITEM_WIDTH + UIPanel.PIXELS_TO_BORDER)), bottomSubPanelPoint.y + UIPanel.PIXELS_TO_BORDER + (y1 * (BOTTOM_SUBITEM_HEIGHT + UIPanel.PIXELS_TO_BORDER))));
				} else {
					break bucle1;
				}
			}
		}

		ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
	}

	public static boolean isBottomMenuPanelActive () {
		return BottomMenuUIPanel.bottomMenuPanelActive;
	}

	public static void setBottomMenuPanelActive (boolean bottomMenuPanelActive) {
		setBottomMenuPanelActive (bottomMenuPanelActive, false);
	}

	public static void setBottomMenuPanelActive (boolean bottomMenuPanelActive, boolean bInitializing) {
		BottomMenuUIPanel.bottomMenuPanelActive = bottomMenuPanelActive;
		if (!bInitializing) {
			ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
		}
	}

	public static void setBottomMenuPanelLocked (boolean bottomMenuPanelLocked) {
		BottomMenuUIPanel.bottomMenuPanelLocked = bottomMenuPanelLocked;
	}

	public static boolean isBottomMenuPanelLocked () {
		return bottomMenuPanelLocked;
	}
}
