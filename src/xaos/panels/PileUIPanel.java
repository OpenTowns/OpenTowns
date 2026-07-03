package xaos.panels;

import java.awt.Point;
import org.lwjgl.opengl.GL11;
import xaos.main.Game;
import xaos.main.World;
import xaos.panels.menus.SmartMenu;
import xaos.stockpiles.Stockpile;
import xaos.tiles.Tile;
import xaos.tiles.entities.items.Container;
import xaos.tiles.entities.items.Item;
import xaos.utils.ColorGL;
import xaos.utils.Messages;
import xaos.utils.UtilFont;
import xaos.utils.UtilsGL;
import xaos.utils.UtilsIniHeaders;


public final class PileUIPanel {

	public static Point MOUSE_PILE_PANEL_BUTTONS_CLOSE_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_CLOSE, -1);

	public static Point MOUSE_PILE_PANEL_BUTTONS_ITEMS_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_ITEMS, -1);

	public static Point MOUSE_PILE_PANEL_BUTTONS_SCROLL_UP_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_SCROLL_UP, -1);

	public static Point MOUSE_PILE_PANEL_BUTTONS_SCROLL_DOWN_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_SCROLL_DOWN, -1);

	public static Point MOUSE_PILE_PANEL_BUTTONS_CONFIG_COPY_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_COPY, -1);

	public static Point MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK, -1);

	public static Point MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_ALL_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_ALL, -1);

	public static Point MOUSE_PILE_PANEL_BUTTONS_CONFIG_UNLOCK_ALL_POINT = new Point (UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_UNLOCK_ALL, -1);

	public static int PILE_PANEL_WIDTH;

	public static int PILE_PANEL_HEIGHT;

	public static int PILE_PANEL_MAX_ITEMS_PER_PAGE;

	private static Point pilePanelPoint = new Point (0, 0);

	public static int pilePanelPileContainerIDActive = -1;

	public static boolean pilePanelIsContainer = false;

	public static boolean pilePanelIsLocked = false;

	public static Point pilePanelClosePoint = new Point (0, 0);

	private static Point pilePanelIconScrollUpPoint = new Point (0, 0);

	private static Point pilePanelIconScrollDownPoint = new Point (0, 0);

	private static Point pilePanelIconConfigCopyPoint = new Point (0, 0);

	private static Point pilePanelIconConfigLockPoint = new Point (0, 0);

	private static Point pilePanelIconConfigUnlockAllPoint = new Point (0, 0);

	private static Point pilePanelIconConfigLockAllPoint = new Point (0, 0);

	private static Point[] pilePanelItemPoints;

	private static Point pilePanelPagesPositionPoint = new Point (0, 0);

	public static SmartMenu menuPile = null;

	public static int pilePanelPageIndex = -1;

	public static int pilePanelMaxPages = -1;

	public static void renderPilePanel (int mouseX, int mouseY, int mousePanel) {
		Point pItem = isMouseOnPileButtons (mouseX, mouseY);

		// XAVI GL11.glColor4f (1, 1, 1, 1);
		int iCurrentTexture = MatsUIPanel.tileMatsPanel[0].getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UIPanel.renderBackground (MatsUIPanel.tileMatsPanel, pilePanelPoint, PILE_PANEL_WIDTH, PILE_PANEL_HEIGHT);

		// Close button
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileButtonClose, iCurrentTexture);
		if (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_CLOSE) {
			UIPanel.drawTile (UIPanel.tileButtonClose, pilePanelClosePoint);
		} else {
			UIPanel.drawTile (UIPanel.tileButtonCloseDisabled, pilePanelClosePoint);
		}

		// Scroll up
		if (pilePanelPageIndex > 0) {
			// Enabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUp, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUp, pilePanelIconScrollUpPoint, (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_SCROLL_UP));
		} else {
			// Disabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUpDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUpDisabled, pilePanelIconScrollUpPoint);
		}
		// Scroll down
		if ((pilePanelPageIndex + 1) < pilePanelMaxPages) {
			// Enabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDown, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDown, pilePanelIconScrollDownPoint, (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_SCROLL_DOWN));
		} else {
			// Disabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDownDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDownDisabled, pilePanelIconScrollDownPoint);
		}

		// Items
		if (menuPile != null) {
			int iFirstIndex = pilePanelPageIndex * PILE_PANEL_MAX_ITEMS_PER_PAGE;
			int iMaxItems = Math.min (menuPile.getItems ().size () - iFirstIndex, PILE_PANEL_MAX_ITEMS_PER_PAGE);
			Tile tile;
			SmartMenu smAux;
			for (int i = 0; i < iMaxItems; i++) {
				smAux = menuPile.getItems ().get (i + iFirstIndex);
				if (smAux.getType () == SmartMenu.TYPE_MENU) {
					// Submenu
					iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItemSM, iCurrentTexture);
					UIPanel.drawTile (UIPanel.tileBottomItemSM, pilePanelItemPoints[i].x + UIPanel.tileBottomItemSM.getTileWidth () / 2 - UIPanel.tileBottomItemSM.getTileWidth () / 2, pilePanelItemPoints[i].y + UIPanel.tileBottomItemSM.getTileHeight () / 2 - UIPanel.tileBottomItemSM.getTileHeight () / 2, (pItem != null && pItem.y == i));
				} else {
					// Item
					iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
					UIPanel.drawTile (UIPanel.tileBottomItem, pilePanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileBottomItem.getTileWidth () / 2, pilePanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - UIPanel.tileBottomItem.getTileHeight () / 2, (pItem != null && pItem.y == i));
				}

				// Icono
				tile = smAux.getIcon ();
				if (tile != null) {
					iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
					UIPanel.drawTile (tile, pilePanelItemPoints[i].x, pilePanelItemPoints[i].y, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.y == i));
				}

				if (smAux.getCommand () != null && (smAux.getCommand ().equals (CommandPanel.COMMAND_STOCKPILE_ENABLE_ITEM) || smAux.getCommand ().equals (CommandPanel.COMMAND_CONTAINER_ENABLE_ITEM))) {
					// Cruz roja
					tile = UIPanel.BIG_RED_CROSS_TILE; // World.getTileRedCross ();
					iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
					UIPanel.drawTile (tile, pilePanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tile.getTileWidth () / 2, pilePanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tile.getTileHeight () / 2, (pItem != null && pItem.y == i));
				}
			}
		}

		// Configuration buttons
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileConfigCopy, iCurrentTexture);
		UIPanel.drawTile (UIPanel.tileConfigCopy, pilePanelIconConfigCopyPoint.x, pilePanelIconConfigCopyPoint.y, (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_COPY));

		if (pilePanelIsLocked) {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileConfigLockLocked, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileConfigLockLocked, pilePanelIconConfigLockPoint.x, pilePanelIconConfigLockPoint.y, (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK));
		} else {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileConfigLock, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileConfigLock, pilePanelIconConfigLockPoint.x, pilePanelIconConfigLockPoint.y, (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK));
		}

		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileConfigLockAll, iCurrentTexture);
		UIPanel.drawTile (UIPanel.tileConfigLockAll, pilePanelIconConfigLockAllPoint.x, pilePanelIconConfigLockAllPoint.y, (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_ALL));

		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileConfigUnlockAll, iCurrentTexture);
		UIPanel.drawTile (UIPanel.tileConfigUnlockAll, pilePanelIconConfigUnlockAllPoint.x, pilePanelIconConfigUnlockAllPoint.y, (pItem != null && pItem.x == UIPanel.MOUSE_PILE_PANEL_BUTTONS_CONFIG_UNLOCK_ALL));
		UtilsGL.glEnd ();

		// Pages
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);

		String sText = (pilePanelPageIndex + 1) + " / " + pilePanelMaxPages; //$NON-NLS-1$
		UtilsGL.drawString (sText, pilePanelPagesPositionPoint.x - UtilFont.getWidth (sText) / 2, pilePanelPagesPositionPoint.y, ColorGL.BLACK);

		if (menuPile != null) {
			int iFirstIndex = pilePanelPageIndex * PILE_PANEL_MAX_ITEMS_PER_PAGE;
			int iMaxItems = Math.min (menuPile.getItems ().size () - iFirstIndex, PILE_PANEL_MAX_ITEMS_PER_PAGE);
			SmartMenu smAux;

			// Stock
			for (int i = 0; i < iMaxItems; i++) {
				smAux = menuPile.getItems ().get (i + iFirstIndex);
				if (smAux.getType () == SmartMenu.TYPE_ITEM) {
					if (smAux.getCommand ().equals (CommandPanel.COMMAND_STOCKPILE_ENABLE_ITEM) || smAux.getCommand ().equals (CommandPanel.COMMAND_STOCKPILE_DISABLE_ITEM)) {
						int iNumItems = Item.getNumItems (UtilsIniHeaders.getIntIniHeader (smAux.getParameter ()), false, World.MAP_DEPTH);
						sText = Integer.toString (iNumItems);
						UtilsGL.drawStringWithBorder (sText, pilePanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - UtilFont.getWidth (sText) / 2, pilePanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () - UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
					} else if (smAux.getCommand ().equals (CommandPanel.COMMAND_CONTAINER_ENABLE_ITEM) || smAux.getCommand ().equals (CommandPanel.COMMAND_CONTAINER_DISABLE_ITEM)) {
						int iNumItems = Item.getNumItems (UtilsIniHeaders.getIntIniHeader (smAux.getParameter2 ()), false, World.MAP_DEPTH);
						sText = Integer.toString (iNumItems);
						UtilsGL.drawStringWithBorder (sText, pilePanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - UtilFont.getWidth (sText) / 2, pilePanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () - UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
					}
				}
			}
		}

		// Title
		String sTitle;
		if (isPilePanelIsContainer ()) {
			sTitle = Messages.getString ("UIPanel.62"); //$NON-NLS-1$
		} else {
			sTitle = Messages.getString ("UIPanel.64"); //$NON-NLS-1$
		}
		UtilsGL.drawStringWithBorder (sTitle, pilePanelPoint.x + MatsUIPanel.tileMatsPanel[3].getTileWidth (), pilePanelPoint.y + UtilFont.MAX_HEIGHT, ColorGL.ORANGE, ColorGL.BLACK);

		UtilsGL.glEnd ();
	}

	public static boolean isMouseOnPilePanel (int x, int y) {
		return ((y >= pilePanelPoint.y && y < (pilePanelPoint.y + PILE_PANEL_HEIGHT)) && (x >= pilePanelPoint.x && x < (pilePanelPoint.x + PILE_PANEL_WIDTH)));
	}

	public static Point isMouseOnPileButtons (int x, int y) {
		if (UIPanel.typingPanel != null) {
			return null;
		}

		Point point;
		// Close button
		point = pilePanelClosePoint;
		if (x >= point.x && x < (point.x + UIPanel.tileButtonClose.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileButtonClose.getTileHeight ())) {
			if (!UIPanel.tileButtonCloseAlpha[x - point.x][y - point.y]) {
				return MOUSE_PILE_PANEL_BUTTONS_CLOSE_POINT;
			}
		}

		// Scrolls
		point = pilePanelIconScrollUpPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
			if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PILE_PANEL_BUTTONS_SCROLL_UP_POINT;
			}
		}
		point = pilePanelIconScrollDownPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
			if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PILE_PANEL_BUTTONS_SCROLL_DOWN_POINT;
			}
		}

		// Configuration buttons
		point = pilePanelIconConfigCopyPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileConfigCopy.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileConfigCopy.getTileHeight ())) {
			if (!UIPanel.tileConfigCopyButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PILE_PANEL_BUTTONS_CONFIG_COPY_POINT;
			}
		}
		point = pilePanelIconConfigLockPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileConfigLock.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileConfigLock.getTileHeight ())) {
			if (!UIPanel.tileConfigLockButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_POINT;
			}
		}
		point = pilePanelIconConfigLockAllPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileConfigLockAll.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileConfigLockAll.getTileHeight ())) {
			if (!UIPanel.tileConfigLockAllButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_ALL_POINT;
			}
		}
		point = pilePanelIconConfigUnlockAllPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileConfigUnlockAll.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileConfigUnlockAll.getTileHeight ())) {
			if (!UIPanel.tileConfigUnlockAllButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PILE_PANEL_BUTTONS_CONFIG_UNLOCK_ALL_POINT;
			}
		}
		
		// Items
		if (menuPile != null) {
			int iFirstIndex = pilePanelPageIndex * PILE_PANEL_MAX_ITEMS_PER_PAGE;
			int iMin = Math.min (menuPile.getItems ().size () - iFirstIndex, PILE_PANEL_MAX_ITEMS_PER_PAGE);
			for (int i = 0; i < iMin; i++) {
				point = pilePanelItemPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_PILE_PANEL_BUTTONS_ITEMS_POINT.y = i + iFirstIndex;
						return MOUSE_PILE_PANEL_BUTTONS_ITEMS_POINT;
					}
				}
			}
		}

		return null;
	}

	public static boolean isPilePanelActive () {
		return pilePanelPileContainerIDActive != -1;
	}

	public static void setPilePanelActive (int iPileContainerID, boolean isContainer) {
		pilePanelPileContainerIDActive = iPileContainerID;
		pilePanelIsContainer = isContainer;

		if (iPileContainerID != -1) {
			UIPanel.closePanels (true, true, true, true, true, false, true);

			// Creamos el menú
			if (isContainer) {
				menuPile = Container.createContainerMenu (iPileContainerID);

				if (menuPile != null) {
					Container container = Game.getWorld ().getContainer (iPileContainerID);
					pilePanelIsLocked = container.isLockedToCopy ();
				}
			} else {
				menuPile = Stockpile.createPilePanelMenu (iPileContainerID);

				if (menuPile != null) {
					Stockpile pile = Stockpile.getStockpile (iPileContainerID);
					pilePanelIsLocked = pile.isLockedToCopy ();
				}
			}

			if (menuPile != null) {
				// Cambiamos el tamaño de los iconos
				UIPanel.resizeIcons (menuPile, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT);

				resizePilePanel (menuPile);

				// Miramos las pages
				recheckPilePanelPages ();
			}
		} else {
			menuPile = null;
		}
	}

	public static void setPilePanelIsContainer (boolean pilePanelIsContainer) {
		PileUIPanel.pilePanelIsContainer = pilePanelIsContainer;
	}

	public static boolean isPilePanelIsContainer () {
		return pilePanelIsContainer;
	}

	public static void recheckPilePanelPages () {
		if (menuPile != null) {
			pilePanelPageIndex = 0;
			pilePanelMaxPages = (menuPile.getItems ().size () / PILE_PANEL_MAX_ITEMS_PER_PAGE) + 1;
			if ((menuPile.getItems ().size () % PILE_PANEL_MAX_ITEMS_PER_PAGE) == 0) {
				pilePanelMaxPages--;
			}
		}
	}

	public static void createPilePanel () {
		PILE_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7;
		PILE_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () - UIPanel.tileBottomItem.getTileHeight () / 2;

		pilePanelPoint.setLocation (UIPanel.renderWidth / 8 - ((UIPanel.renderWidth / 8) / 2), UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 4);
		pilePanelClosePoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), pilePanelPoint.y);

		pilePanelPileContainerIDActive = -1;
		pilePanelIsContainer = false;
		pilePanelIsLocked = false;

		// Scroll up/down
		pilePanelIconScrollUpPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), pilePanelPoint.y + MatsUIPanel.tileMatsPanel[1].getTileHeight ());
		pilePanelIconScrollDownPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileScrollDown.getTileHeight () - MatsUIPanel.tileMatsPanel[1].getTileHeight ());

		// Configuration buttons
		pilePanelIconConfigCopyPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 - 3 * UIPanel.tileConfigCopy.getTileWidth (), pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigCopy.getTileHeight () - UtilFont.MAX_HEIGHT / 2);
		pilePanelIconConfigLockPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 - UIPanel.tileConfigCopy.getTileWidth () - UIPanel.tileConfigCopy.getTileWidth () / 2, pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigLock.getTileHeight () - UtilFont.MAX_HEIGHT / 2);
		pilePanelIconConfigUnlockAllPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 + UIPanel.tileConfigCopy.getTileWidth () / 2, pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigLock.getTileHeight () - UtilFont.MAX_HEIGHT / 2);
		pilePanelIconConfigLockAllPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 + 2 * UIPanel.tileConfigCopy.getTileWidth (), pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigLock.getTileHeight () - UtilFont.MAX_HEIGHT / 2);
		
		// Pages
		pilePanelPagesPositionPoint.setLocation (pilePanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () / 2, pilePanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight () + (pilePanelIconScrollDownPoint.y - (pilePanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight ())) / 2 - UtilFont.MAX_HEIGHT / 2);

		// Miramos de cuantas filas y columnas disponemos y seteamos el array de posiciones
		int iMaxItemsWidth = (PILE_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth ()) / (UIPanel.tileBottomItem.getTileWidth () + 8);
		int iMaxItemsHeight = (PILE_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight ()) / (UIPanel.tileBottomItem.getTileHeight () + 8);

		PILE_PANEL_MAX_ITEMS_PER_PAGE = iMaxItemsWidth * iMaxItemsHeight;
		pilePanelItemPoints = new Point [PILE_PANEL_MAX_ITEMS_PER_PAGE];
		int iSeparationW = (PILE_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () - iMaxItemsWidth * UIPanel.tileBottomItem.getTileWidth ()) / (iMaxItemsWidth + 1);
		int iSeparationH = (PILE_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight () - iMaxItemsHeight * UIPanel.tileBottomItem.getTileHeight ()) / (iMaxItemsHeight + 1);
		int iFirstWidth = pilePanelPoint.x + MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () + iSeparationW;
		int iFirstHeight = pilePanelPoint.y + MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight () + iSeparationH;
		// int x = iFirstWidth;
		// int y = iFirstHeight;
		int i = 0;
		for (int y = 0; y < iMaxItemsHeight; y++) {
			for (int x = 0; x < iMaxItemsWidth; x++) {
				pilePanelItemPoints[i] = new Point (iFirstWidth + (x * (UIPanel.tileBottomItem.getTileWidth () + iSeparationW)), iFirstHeight + (y * (UIPanel.tileBottomItem.getTileHeight () + iSeparationH)));
				i++;
			}
		}
	}

	public static void resizePilePanel (SmartMenu menuPile) {
		if (menuPile == null) {
			return;
		}

		PILE_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7; // Copied from the createPilePanel method
		int iMaxItemsWidth = (PILE_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth ()) / (UIPanel.tileBottomItem.getTileWidth () + 8);
		int iRows = (menuPile.getItems ().size () / iMaxItemsWidth) + 1;
		if (menuPile.getItems ().size () % iMaxItemsWidth == 0) {
			iRows--;
		}

		PILE_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () - UIPanel.tileBottomItem.getTileHeight () / 2;
		int iMaxItemsHeight = (PILE_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight ()) / (UIPanel.tileBottomItem.getTileHeight () + 8);
		int iSeparationH = (PILE_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight () - iMaxItemsHeight * UIPanel.tileBottomItem.getTileHeight ()) / (iMaxItemsHeight + 1);

		int iRowsToDelete = 0;
		if (iMaxItemsHeight <= iRows) {
			iRows = iMaxItemsHeight;
		} else {
			iRowsToDelete = (iMaxItemsHeight - iRows);
		}

		PILE_PANEL_HEIGHT -= (iRowsToDelete * (UIPanel.tileBottomItem.getTileHeight () + iSeparationH));
		// PILE_PANEL_HEIGHT = renderHeight - (iconMatsPoint.y + tileIconMats.getTileHeight ()) - tileBottomItem.getTileHeight () - tileBottomItem.getTileHeight () / 2;

		PILE_PANEL_MAX_ITEMS_PER_PAGE = iMaxItemsWidth * iRows;

		if (iRows <= 1) {
			// Lo hacemos pequeño por la derecha
			int iSeparationW = (PILE_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () - iMaxItemsWidth * UIPanel.tileBottomItem.getTileWidth ()) / (iMaxItemsWidth + 1);
			int iFirstWidth = pilePanelPoint.x + MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () + iSeparationW;
			PILE_PANEL_WIDTH = iFirstWidth + ((menuPile.getItems ().size () + 1) * (UIPanel.tileBottomItem.getTileWidth () + iSeparationW));
		} else {
			PILE_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7;
		}

		pilePanelClosePoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), pilePanelPoint.y);

		// Scroll up/down
		pilePanelIconScrollUpPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), pilePanelPoint.y + MatsUIPanel.tileMatsPanel[1].getTileHeight ());
		pilePanelIconScrollDownPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileScrollDown.getTileHeight () - MatsUIPanel.tileMatsPanel[1].getTileHeight ());

		// Configuration buttons
		pilePanelIconConfigCopyPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 - 3 * UIPanel.tileConfigCopy.getTileWidth (), pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigCopy.getTileHeight () - UtilFont.MAX_HEIGHT / 2);
		pilePanelIconConfigLockPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 - UIPanel.tileConfigCopy.getTileWidth () - UIPanel.tileConfigCopy.getTileWidth () / 2, pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigLock.getTileHeight () - UtilFont.MAX_HEIGHT / 2);
		pilePanelIconConfigUnlockAllPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 + UIPanel.tileConfigCopy.getTileWidth () / 2, pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigLock.getTileHeight () - UtilFont.MAX_HEIGHT / 2);
		pilePanelIconConfigLockAllPoint.setLocation (pilePanelPoint.x + PILE_PANEL_WIDTH / 2 + 2 * UIPanel.tileConfigCopy.getTileWidth (), pilePanelPoint.y + PILE_PANEL_HEIGHT - UIPanel.tileConfigLock.getTileHeight () - UtilFont.MAX_HEIGHT / 2);

		// Pages
		pilePanelPagesPositionPoint.setLocation (pilePanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () / 2, pilePanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight () + (pilePanelIconScrollDownPoint.y - (pilePanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight ())) / 2 - UtilFont.MAX_HEIGHT / 2);
	}
}
