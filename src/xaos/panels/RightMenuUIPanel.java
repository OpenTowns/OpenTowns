package xaos.panels;

import java.awt.Point;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import xaos.campaign.TutorialFlow;
import xaos.panels.menus.SmartMenu;
import xaos.tiles.Tile;
import xaos.utils.UtilsGL;


public final class RightMenuUIPanel {

	public final static int MENU_ITEM_WIDTH = 64;

	public final static int MENU_ITEM_HEIGHT = 64;

	public static int MENU_PANEL_NUM_ITEMS_X;

	public static int MENU_PANEL_NUM_ITEMS_Y;

	public static int MENU_PANEL_WIDTH;

	public static int MENU_PANEL_HEIGHT;

	// MENU panel (right)
	public static Point menuPanelPoint = new Point (0, 0);

	public static ArrayList<Point> menuPanelItemsPosition;

	public static Tile[] tileMenuPanel;

	public static SmartMenu menuPanelMenu = null;

	private static boolean menuPanelActive = false;

	private static boolean menuPanelLocked = false;

	public static Tile tileOpenRightMenu;

	public static Tile tileOpenRightMenuON;

	public static Point tileOpenCloseRightMenuPoint = new Point (0, 0);

	private static boolean checkBlinkRight = false;

	public static void renderMenuPanel (int mouseX, int mouseY, int mousePanel) {
		checkBlinkRight = (UIPanel.blinkTurns >= UIPanel.MAX_BLINK_TURNS / 2) && TutorialFlow.isBlinkRight ();

		if (isMenuPanelActive ()) {
			// XAVI GL11.glColor4f (1, 1, 1, 1);
			int iCurrentTexture = tileMenuPanel[0].getTextureID ();
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);
			UIPanel.renderBackground (tileMenuPanel, menuPanelPoint, MENU_PANEL_WIDTH, MENU_PANEL_HEIGHT);

			int iItemMenu;
			if (mousePanel == UIPanel.MOUSE_MENU_PANEL_ITEMS) {
				iItemMenu = isMouseOnMenuItems (mouseX, mouseY);
			} else {
				iItemMenu = -1;
			}

			// Items
			if (menuPanelMenu != null) {
				int iMenu;
				Point point;
				bucle1: for (int y = 0; y < MENU_PANEL_NUM_ITEMS_Y; y++) {
					for (int x = 0; x < MENU_PANEL_NUM_ITEMS_X; x++) {
						iMenu = (y * MENU_PANEL_NUM_ITEMS_X) + x;
						if (iMenu >= menuPanelMenu.getItems ().size ()) {
							break bucle1;
						}
						point = menuPanelItemsPosition.get (iMenu);

						// Round button
						if (menuPanelMenu.getItems ().get (iMenu).getType () == SmartMenu.TYPE_MENU) {
							iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItemSM, iCurrentTexture);
							if (checkBlinkRight && TutorialFlow.currentBlinkRight (menuPanelMenu.getItems ().get (iMenu).getID ())) {
								UtilsGL.setColorRed ();
								UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemMenu == iMenu));
								UtilsGL.unsetColor ();
							} else {
								UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemMenu == iMenu));
							}
						} else {
							iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);

							if (checkBlinkRight && TutorialFlow.currentBlinkRight (menuPanelMenu.getItems ().get (iMenu).getID ())) {
								UtilsGL.setColorRed ();
								UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemMenu == iMenu));
								UtilsGL.unsetColor ();
							} else {
								UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemMenu == iMenu));
							}
						}

						// Icono
						Tile tile = menuPanelMenu.getItems ().get (iMenu).getIcon ();
						if (tile != null && menuPanelMenu.getItems ().get (iMenu).getIconType () == SmartMenu.ICON_TYPE_UI) { // MENU
							iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
							UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemMenu == iMenu));
						}
					}
				}
			}

			// MENU
			if (menuPanelMenu != null) {
				int iMenu;
				Tile tile;
				Point point;
				bucle1: for (int y = 0; y < MENU_PANEL_NUM_ITEMS_Y; y++) {
					for (int x = 0; x < MENU_PANEL_NUM_ITEMS_X; x++) {
						iMenu = (y * MENU_PANEL_NUM_ITEMS_X) + x;
						if (iMenu >= menuPanelMenu.getItems ().size ()) {
							break bucle1;
						}
						point = menuPanelItemsPosition.get (iMenu);
						// Icono
						tile = menuPanelMenu.getItems ().get (iMenu).getIcon ();
						if (tile != null && menuPanelMenu.getItems ().get (iMenu).getIconType () == SmartMenu.ICON_TYPE_ITEM) { // ICONO
							iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
							UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (iItemMenu == iMenu));
						}
					}
				}
			}

			UtilsGL.glEnd ();
		}

		// Botoncito open/close
		if (isMenuPanelLocked ()) {
			// Close menu icon
			// XAVI GL11.glColor4f (1, 1, 1, 1);
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, tileOpenRightMenuON.getTextureID ());
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);
			UIPanel.drawTile (tileOpenRightMenuON, tileOpenCloseRightMenuPoint, tileOpenRightMenuON.getTileWidth (), tileOpenRightMenuON.getTileHeight (), mousePanel == UIPanel.MOUSE_MENU_OPENCLOSE);
			UtilsGL.glEnd ();
		} else {
			// XAVI GL11.glColor4f (1, 1, 1, 1);
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, tileOpenRightMenu.getTextureID ());
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);
			if (checkBlinkRight) {
				UtilsGL.setColorRed ();
			}
			UIPanel.drawTile (tileOpenRightMenu, tileOpenCloseRightMenuPoint, tileOpenRightMenu.getTileWidth (), tileOpenRightMenu.getTileHeight (), mousePanel == UIPanel.MOUSE_MENU_OPENCLOSE);
			if (checkBlinkRight) {
				UtilsGL.unsetColor ();
			}
			UtilsGL.glEnd ();
		}
	}

	public static boolean isMouseCloseToOpenCloseMenuIcon (int x, int y) {
		return UIPanel.isMouseCloseToIcon (x, y, tileOpenCloseRightMenuPoint, tileOpenRightMenu, UIPanel.CLOSE_PIXELS);
	}

	public static boolean isMouseOnMenuPanel (int x, int y) {
		if (x >= menuPanelPoint.x && x < (menuPanelPoint.x + MENU_PANEL_WIDTH) && y >= menuPanelPoint.y && y < (menuPanelPoint.y + MENU_PANEL_HEIGHT)) {
			return true;
		}

		return false;
	}

	public static int isMouseOnMenuItems (int x, int y) {
		if (y >= menuPanelPoint.y && y < (menuPanelPoint.y + MENU_PANEL_HEIGHT) && x >= menuPanelPoint.x && x < (menuPanelPoint.x + MENU_PANEL_WIDTH)) {
			Point point;
			bucle1: for (int y1 = 0; y1 < MENU_PANEL_NUM_ITEMS_Y; y1++) {
				for (int x1 = 0; x1 < MENU_PANEL_NUM_ITEMS_X; x1++) {
					int i = (y1 * MENU_PANEL_NUM_ITEMS_X) + x1;
					if (i >= menuPanelMenu.getItems ().size ()) {
						break bucle1;
					}
					point = menuPanelItemsPosition.get (i);
					if (x >= point.x && x < (point.x + MENU_ITEM_WIDTH) && y >= point.y && y < (point.y + MENU_ITEM_HEIGHT)) {
						if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
							return i;
						}
					}
				}
			}
		}

		return -1;
	}

	public static boolean isMenuPanelActive () {
		return RightMenuUIPanel.menuPanelActive;
	}

	public static void setMenuPanelActive (boolean menuPanelActive) {
		RightMenuUIPanel.menuPanelActive = menuPanelActive;
	}

	public static void setMenuPanelLocked (boolean menuPanelLocked) {
		RightMenuUIPanel.menuPanelLocked = menuPanelLocked;
	}

	public static boolean isMenuPanelLocked () {
		return menuPanelLocked;
	}
}
