package xaos.panels;

import java.awt.Point;
import org.lwjgl.opengl.GL11;
import xaos.actions.ActionPriorityManager;
import xaos.main.Game;
import xaos.panels.menus.SmartMenu;
import xaos.tiles.Tile;
import xaos.tiles.entities.items.Item;
import xaos.utils.ColorGL;
import xaos.utils.Messages;
import xaos.utils.UtilFont;
import xaos.utils.UtilsGL;


public final class ProfessionsUIPanel {

	public static Point MOUSE_PROFESSIONS_PANEL_BUTTONS_CLOSE_POINT = new Point (UIPanel.MOUSE_PROFESSIONS_PANEL_BUTTONS_CLOSE, -1);

	public static Point MOUSE_PROFESSIONS_PANEL_BUTTONS_ITEMS_POINT = new Point (UIPanel.MOUSE_PROFESSIONS_PANEL_BUTTONS_ITEMS, -1);

	public static Point MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_UP_POINT = new Point (UIPanel.MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_UP, -1);

	public static Point MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_DOWN_POINT = new Point (UIPanel.MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_DOWN, -1);

	public static int PROFESSIONS_PANEL_WIDTH;

	public static int PROFESSIONS_PANEL_HEIGHT;

	public static int PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE;

	// PROFESSIONS panel
	private static Point professionsPanelPoint = new Point (0, 0);

	public static int professionsPanelCitizenOrGroupIDActive = -1;

	public static boolean professionsPanelIsCitizen = true;

	public static Point professionsPanelClosePoint = new Point (0, 0);

	private static Point professionsPanelIconScrollUpPoint = new Point (0, 0);

	private static Point professionsPanelIconScrollDownPoint = new Point (0, 0);

	private static Point[] professionsPanelItemPoints;

	private static Point professionsPanelPagesPositionPoint = new Point (0, 0);

	public static SmartMenu menuProfessions = null;

	public static int professionsPanelPageIndex = -1;

	public static int professionsPanelMaxPages = -1;

	public static void renderProfessionsPanel (int mouseX, int mouseY, int mousePanel) {
		Point pItem = isMouseOnProfessionsButtons (mouseX, mouseY);

		// XAVI GL11.glColor4f (1, 1, 1, 1);
		int iCurrentTexture = MatsUIPanel.tileMatsPanel[0].getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UIPanel.renderBackground (MatsUIPanel.tileMatsPanel, professionsPanelPoint, PROFESSIONS_PANEL_WIDTH, PROFESSIONS_PANEL_HEIGHT);

		// Close button
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileButtonClose, iCurrentTexture);
		if (pItem != null && pItem.x == UIPanel.MOUSE_PROFESSIONS_PANEL_BUTTONS_CLOSE) {
			UIPanel.drawTile (UIPanel.tileButtonClose, professionsPanelClosePoint);
		} else {
			UIPanel.drawTile (UIPanel.tileButtonCloseDisabled, professionsPanelClosePoint);
		}

		// Scroll up
		if (professionsPanelPageIndex > 0) {
			// Enabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUp, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUp, professionsPanelIconScrollUpPoint, (pItem != null && pItem.x == UIPanel.MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_UP));
		} else {
			// Disabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUpDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUpDisabled, professionsPanelIconScrollUpPoint);
		}
		// Scroll down
		if ((professionsPanelPageIndex + 1) < professionsPanelMaxPages) {
			// Enabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDown, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDown, professionsPanelIconScrollDownPoint, (pItem != null && pItem.x == UIPanel.MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_DOWN));
		} else {
			// Disabled
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDownDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDownDisabled, professionsPanelIconScrollDownPoint);
		}

		// Items
		if (menuProfessions != null) {
			int iFirstIndex = professionsPanelPageIndex * PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE;
			int iMaxItems = Math.min (menuProfessions.getItems ().size () - iFirstIndex, PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE);
			Tile tile;
			SmartMenu smAux;
			for (int i = 0; i < iMaxItems; i++) {
				smAux = menuProfessions.getItems ().get (i + iFirstIndex);
				if (smAux.getType () == SmartMenu.TYPE_MENU) {
					// Submenu
					iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItemSM, iCurrentTexture);
					UIPanel.drawTile (UIPanel.tileBottomItemSM, professionsPanelItemPoints[i].x + UIPanel.tileBottomItemSM.getTileWidth () / 2 - UIPanel.tileBottomItemSM.getTileWidth () / 2, professionsPanelItemPoints[i].y + UIPanel.tileBottomItemSM.getTileHeight () / 2 - UIPanel.tileBottomItemSM.getTileHeight () / 2, (pItem != null && pItem.y == i));
				} else {
					// Item
					iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
					UIPanel.drawTile (UIPanel.tileBottomItem, professionsPanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileBottomItem.getTileWidth () / 2, professionsPanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - UIPanel.tileBottomItem.getTileHeight () / 2, (pItem != null && pItem.y == i));
				}

				// Icono
				tile = smAux.getIcon ();
				if (tile != null) {
					iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
					UIPanel.drawTile (tile, professionsPanelItemPoints[i].x, professionsPanelItemPoints[i].y, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.y == i));
				}

				if (smAux.getCommand () != null && (smAux.getCommand ().equals (CommandPanel.COMMAND_PROFESSIONS_ENABLE_ITEM) || smAux.getCommand ().equals (CommandPanel.COMMAND_JOB_GROUP_ENABLE_ITEM))) {
					// Cruz roja
					tile = UIPanel.BIG_RED_CROSS_TILE;
					iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
					UIPanel.drawTile (tile, professionsPanelItemPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tile.getTileWidth () / 2, professionsPanelItemPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tile.getTileHeight () / 2, (pItem != null && pItem.y == i));
				}
			}
		}

		UtilsGL.glEnd ();

		// Pages
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);

		String sText = (professionsPanelPageIndex + 1) + " / " + professionsPanelMaxPages; //$NON-NLS-1$
		UtilsGL.drawString (sText, professionsPanelPagesPositionPoint.x - UtilFont.getWidth (sText) / 2, professionsPanelPagesPositionPoint.y, ColorGL.BLACK);

		// Title
		if (professionsPanelIsCitizen) {
			UtilsGL.drawStringWithBorder (Messages.getString ("UIPanel.63"), professionsPanelPoint.x + MatsUIPanel.tileMatsPanel[3].getTileWidth (), professionsPanelPoint.y + UtilFont.MAX_HEIGHT, ColorGL.ORANGE, ColorGL.BLACK); //$NON-NLS-1$
		} else {
			UtilsGL.drawStringWithBorder (Messages.getString ("UIPanel.67"), professionsPanelPoint.x + MatsUIPanel.tileMatsPanel[3].getTileWidth (), professionsPanelPoint.y + UtilFont.MAX_HEIGHT, ColorGL.ORANGE, ColorGL.BLACK); //$NON-NLS-1$
		}

		UtilsGL.glEnd ();
	}

	public static boolean isMouseOnProfessionsPanel (int x, int y) {
		return ((y >= professionsPanelPoint.y && y < (professionsPanelPoint.y + PROFESSIONS_PANEL_HEIGHT)) && (x >= professionsPanelPoint.x && x < (professionsPanelPoint.x + PROFESSIONS_PANEL_WIDTH)));
	}

	public static Point isMouseOnProfessionsButtons (int x, int y) {
		if (UIPanel.typingPanel != null) {
			return null;
		}

		Point point;
		// Close button
		point = professionsPanelClosePoint;
		if (x >= point.x && x < (point.x + UIPanel.tileButtonClose.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileButtonClose.getTileHeight ())) {
			if (!UIPanel.tileButtonCloseAlpha[x - point.x][y - point.y]) {
				return MOUSE_PROFESSIONS_PANEL_BUTTONS_CLOSE_POINT;
			}
		}

		// Scrolls
		point = professionsPanelIconScrollUpPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
			if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_UP_POINT;
			}
		}
		point = professionsPanelIconScrollDownPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
			if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_DOWN_POINT;
			}
		}

		// Items
		if (menuProfessions != null) {
			int iFirstIndex = professionsPanelPageIndex * PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE;
			int iMin = Math.min (menuProfessions.getItems ().size () - iFirstIndex, PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE);
			for (int i = 0; i < iMin; i++) {
				point = professionsPanelItemPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_PROFESSIONS_PANEL_BUTTONS_ITEMS_POINT.y = i + iFirstIndex;
						return MOUSE_PROFESSIONS_PANEL_BUTTONS_ITEMS_POINT;
					}
				}
			}
		}

		return null;
	}

	public static boolean isProfessionsPanelActive () {
		return professionsPanelCitizenOrGroupIDActive != -1;
	}

	public static void setProfessionsPanelActive (int iProfessionsCitizenOrGroupID, boolean isCitizen) {
		professionsPanelCitizenOrGroupIDActive = iProfessionsCitizenOrGroupID;
		professionsPanelIsCitizen = isCitizen;

		if (iProfessionsCitizenOrGroupID != -1) {
			UIPanel.closePanels (true, true, true, true, false, true, false);

			// Creamos el menú
			if (isCitizen) {
				menuProfessions = ActionPriorityManager.createProfessionsMenu (iProfessionsCitizenOrGroupID);
			} else {
				menuProfessions = ActionPriorityManager.createJobGroupPanelMenu (iProfessionsCitizenOrGroupID);
			}

			if (menuProfessions != null) {
				// Cambiamos el tamaño de los iconos
				UIPanel.resizeIcons (menuProfessions, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT);

				resizeProfessionsPanel (menuProfessions);

				// Miramos las pages
				recheckProfessionsPanelPages ();
			}
		} else {
			menuProfessions = null;
		}
	}

	public static void recheckProfessionsPanelPages () {
		if (menuProfessions != null) {
			professionsPanelPageIndex = 0;
			professionsPanelMaxPages = (menuProfessions.getItems ().size () / PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE) + 1;
			if ((menuProfessions.getItems ().size () % PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE) == 0) {
				professionsPanelMaxPages--;
			}
		}
	}

	public static void createProfessionsPanel () {
		PROFESSIONS_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7;
		PROFESSIONS_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () - UIPanel.tileBottomItem.getTileHeight () / 2;

		professionsPanelPoint.setLocation (UIPanel.renderWidth / 8 - ((UIPanel.renderWidth / 8) / 2), UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 4);
		professionsPanelClosePoint.setLocation (professionsPanelPoint.x + PROFESSIONS_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), professionsPanelPoint.y);

		professionsPanelCitizenOrGroupIDActive = -1;
		professionsPanelIsCitizen = false;

		// Scroll up/down
		professionsPanelIconScrollUpPoint.setLocation (professionsPanelPoint.x + PROFESSIONS_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), professionsPanelPoint.y + MatsUIPanel.tileMatsPanel[1].getTileHeight ());
		professionsPanelIconScrollDownPoint.setLocation (professionsPanelPoint.x + PROFESSIONS_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), professionsPanelPoint.y + PROFESSIONS_PANEL_HEIGHT - UIPanel.tileScrollDown.getTileHeight () - MatsUIPanel.tileMatsPanel[1].getTileHeight ());

		// Pages
		professionsPanelPagesPositionPoint.setLocation (professionsPanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () / 2, professionsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight () + (professionsPanelIconScrollDownPoint.y - (professionsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight ())) / 2 - UtilFont.MAX_HEIGHT / 2);

		// Miramos de cuantas filas y columnas disponemos y seteamos el array de posiciones
		int iMaxItemsWidth = (PROFESSIONS_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth ()) / (UIPanel.tileBottomItem.getTileWidth () + 8);
		int iMaxItemsHeight = (PROFESSIONS_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight ()) / (UIPanel.tileBottomItem.getTileHeight () + 8);

		PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE = iMaxItemsWidth * iMaxItemsHeight;
		professionsPanelItemPoints = new Point [PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE];
		int iSeparationW = (PROFESSIONS_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () - iMaxItemsWidth * UIPanel.tileBottomItem.getTileWidth ()) / (iMaxItemsWidth + 1);
		int iSeparationH = (PROFESSIONS_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight () - iMaxItemsHeight * UIPanel.tileBottomItem.getTileHeight ()) / (iMaxItemsHeight + 1);
		int iFirstWidth = professionsPanelPoint.x + MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () + iSeparationW;
		int iFirstHeight = professionsPanelPoint.y + MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight () + iSeparationH;
		// int x = iFirstWidth;
		// int y = iFirstHeight;
		int i = 0;
		for (int y = 0; y < iMaxItemsHeight; y++) {
			for (int x = 0; x < iMaxItemsWidth; x++) {
				professionsPanelItemPoints[i] = new Point (iFirstWidth + (x * (UIPanel.tileBottomItem.getTileWidth () + iSeparationW)), iFirstHeight + (y * (UIPanel.tileBottomItem.getTileHeight () + iSeparationH)));
				i++;
			}
		}
	}

	public static void resizeProfessionsPanel (SmartMenu menuProfessions) {
		if (menuProfessions == null) {
			return;
		}

		int iMaxItemsWidth = (PROFESSIONS_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth ()) / (UIPanel.tileBottomItem.getTileWidth () + 8);
		int iRows = (menuProfessions.getItems ().size () / iMaxItemsWidth) + 1;
		if (menuProfessions.getItems ().size () % iMaxItemsWidth == 0) {
			iRows--;
		}

		PROFESSIONS_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconMatsPoint.y + UIPanel.tileIconMats.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () - UIPanel.tileBottomItem.getTileHeight () / 2;
		int iMaxItemsHeight = (PROFESSIONS_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight ()) / (UIPanel.tileBottomItem.getTileHeight () + 8);
		int iSeparationH = (PROFESSIONS_PANEL_HEIGHT - 2 * MatsUIPanel.tileMatsPanelSubPanel[1].getTileHeight () - iMaxItemsHeight * UIPanel.tileBottomItem.getTileHeight ()) / (iMaxItemsHeight + 1);

		int iRowsToDelete = 0;
		if (iMaxItemsHeight <= iRows) {
			iRows = iMaxItemsHeight;
		} else {
			iRowsToDelete = (iMaxItemsHeight - iRows);
		}

		PROFESSIONS_PANEL_HEIGHT -= (iRowsToDelete * (UIPanel.tileBottomItem.getTileHeight () + iSeparationH));
		// PILE_PANEL_HEIGHT = renderHeight - (iconMatsPoint.y + tileIconMats.getTileHeight ()) - tileBottomItem.getTileHeight () - tileBottomItem.getTileHeight () / 2;

		PROFESSIONS_PANEL_MAX_ITEMS_PER_PAGE = iMaxItemsWidth * iRows;

		if (iRows <= 1) {
			// Lo hacemos pequeño por la derecha
			int iSeparationW = (PROFESSIONS_PANEL_WIDTH - UIPanel.tileScrollUp.getTileWidth () - 4 * MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () - iMaxItemsWidth * UIPanel.tileBottomItem.getTileWidth ()) / (iMaxItemsWidth + 1);
			int iFirstWidth = professionsPanelPoint.x + MatsUIPanel.tileMatsPanelSubPanel[3].getTileWidth () + iSeparationW;
			PROFESSIONS_PANEL_WIDTH = iFirstWidth + ((menuProfessions.getItems ().size () + 1) * (UIPanel.tileBottomItem.getTileWidth () + iSeparationW));
		} else {
			PROFESSIONS_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7;
		}

		professionsPanelClosePoint.setLocation (professionsPanelPoint.x + PROFESSIONS_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), professionsPanelPoint.y);

		// Scroll up/down
		professionsPanelIconScrollUpPoint.setLocation (professionsPanelPoint.x + PROFESSIONS_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), professionsPanelPoint.y + MatsUIPanel.tileMatsPanel[1].getTileHeight ());
		professionsPanelIconScrollDownPoint.setLocation (professionsPanelPoint.x + PROFESSIONS_PANEL_WIDTH - MatsUIPanel.tileMatsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), professionsPanelPoint.y + PROFESSIONS_PANEL_HEIGHT - UIPanel.tileScrollDown.getTileHeight () - MatsUIPanel.tileMatsPanel[1].getTileHeight ());

		// Pages
		professionsPanelPagesPositionPoint.setLocation (professionsPanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () / 2, professionsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight () + (professionsPanelIconScrollDownPoint.y - (professionsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight ())) / 2 - UtilFont.MAX_HEIGHT / 2);
	}
}
