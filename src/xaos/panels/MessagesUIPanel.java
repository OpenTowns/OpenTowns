package xaos.panels;

import java.awt.Point;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import xaos.main.Game;
import xaos.tiles.Tile;
import xaos.utils.ColorGL;
import xaos.utils.UtilFont;
import xaos.utils.UtilsGL;


public final class MessagesUIPanel {

	public static Point MOUSE_MESSAGES_PANEL_BUTTONS_CLOSE_POINT = new Point (UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_CLOSE, -1);

	public static Point MOUSE_MESSAGES_PANEL_BUTTONS_ANNOUNCEMENT_POINT = new Point (UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_ANNOUNCEMENT, -1);

	public static Point MOUSE_MESSAGES_PANEL_BUTTONS_COMBAT_POINT = new Point (UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_COMBAT, -1);

	public static Point MOUSE_MESSAGES_PANEL_BUTTONS_HEROES_POINT = new Point (UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_HEROES, -1);

	public static Point MOUSE_MESSAGES_PANEL_BUTTONS_SYSTEM_POINT = new Point (UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_SYSTEM, -1);

	public static Point MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_UP_POINT = new Point (UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_UP, -1);

	public static Point MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_DOWN_POINT = new Point (UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_DOWN, -1);

	public static int MESSAGES_PANEL_WIDTH;

	public static int MESSAGES_PANEL_HEIGHT;

	public static int MESSAGES_PANEL_SUBPANEL_WIDTH;

	public static int MESSAGES_PANEL_SUBPANEL_HEIGHT;

	// MESSAGES panel
	private static int messagesPanelActive = -1;

	public static Point[] messageIconPoints;

	private static Point[] messagePanelIconPoints;

	public static Tile[] messageTiles;

	public static Tile[] messageTilesON;

	public static ArrayList<boolean[][]> messageTilesAlpha;

	private static Tile[] messagePanelTiles;

	private static Tile[] messagePanelTilesON;

	private static ArrayList<boolean[][]> messagePanelTilesAlpha;

	public static Point messagesPanelPoint = new Point (0, 0);

	public static Point messagesPanelSubPanelPoint = new Point (0, 0);

	public static Tile[] tileMessagesPanel;

	public static Tile[] tileMessagesPanelSubPanel;

	public static Point messagesPanelClosePoint = new Point (0, 0);

	private static Point messagePanelIconScrollUpPoint = new Point (0, 0);

	private static Point messagePanelIconScrollDownPoint = new Point (0, 0);

	private static Point messagePanelPagesPositionPoint = new Point (0, 0);

	public static void renderMessagesPanel (int mouseX, int mouseY, int mousePanel) {
		Point pItem = isMouseOnMessagesButtons (mouseX, mouseY);

		// Fondo
		// XAVI GL11.glColor4f (1, 1, 1, 1);
		int iCurrentTexture = tileMessagesPanel[0].getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UIPanel.renderBackground (tileMessagesPanel, messagesPanelPoint, MESSAGES_PANEL_WIDTH, MESSAGES_PANEL_HEIGHT);

		// Close button
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileButtonClose, iCurrentTexture);
		if (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_CLOSE) {
			UIPanel.drawTile (UIPanel.tileButtonClose, messagesPanelClosePoint);
		} else {
			UIPanel.drawTile (UIPanel.tileButtonCloseDisabled, messagesPanelClosePoint);
		}

		// "Tabs"
		int iMessagesType = getMessagesPanelActive ();
		if (MessagesPanel.TYPE_ANNOUNCEMENT == iMessagesType) {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTilesON[MessagesPanel.TYPE_ANNOUNCEMENT], iCurrentTexture);
			UIPanel.drawTile (messagePanelTilesON[MessagesPanel.TYPE_ANNOUNCEMENT], messagePanelIconPoints[MessagesPanel.TYPE_ANNOUNCEMENT], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_ANNOUNCEMENT));
		} else {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTiles[MessagesPanel.TYPE_ANNOUNCEMENT], iCurrentTexture);
			UIPanel.drawTile (messagePanelTiles[MessagesPanel.TYPE_ANNOUNCEMENT], messagePanelIconPoints[MessagesPanel.TYPE_ANNOUNCEMENT], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_ANNOUNCEMENT));
		}
		if (MessagesPanel.TYPE_COMBAT == iMessagesType) {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTilesON[MessagesPanel.TYPE_COMBAT], iCurrentTexture);
			UIPanel.drawTile (messagePanelTilesON[MessagesPanel.TYPE_COMBAT], messagePanelIconPoints[MessagesPanel.TYPE_COMBAT], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_COMBAT));
		} else {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTiles[MessagesPanel.TYPE_COMBAT], iCurrentTexture);
			UIPanel.drawTile (messagePanelTiles[MessagesPanel.TYPE_COMBAT], messagePanelIconPoints[MessagesPanel.TYPE_COMBAT], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_COMBAT));
		}
		if (MessagesPanel.TYPE_HEROES == iMessagesType) {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTilesON[MessagesPanel.TYPE_HEROES], iCurrentTexture);
			UIPanel.drawTile (messagePanelTilesON[MessagesPanel.TYPE_HEROES], messagePanelIconPoints[MessagesPanel.TYPE_HEROES], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_HEROES));
		} else {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTiles[MessagesPanel.TYPE_HEROES], iCurrentTexture);
			UIPanel.drawTile (messagePanelTiles[MessagesPanel.TYPE_HEROES], messagePanelIconPoints[MessagesPanel.TYPE_HEROES], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_HEROES));
		}
		if (MessagesPanel.TYPE_SYSTEM == iMessagesType) {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTilesON[MessagesPanel.TYPE_SYSTEM], iCurrentTexture);
			UIPanel.drawTile (messagePanelTilesON[MessagesPanel.TYPE_SYSTEM], messagePanelIconPoints[MessagesPanel.TYPE_SYSTEM], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_SYSTEM));
		} else {
			iCurrentTexture = UtilsGL.setTexture (messagePanelTiles[MessagesPanel.TYPE_SYSTEM], iCurrentTexture);
			UIPanel.drawTile (messagePanelTiles[MessagesPanel.TYPE_SYSTEM], messagePanelIconPoints[MessagesPanel.TYPE_SYSTEM], (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_SYSTEM));
		}

		// Scrolls
		if (MessagesPanel.getPages (iMessagesType) > 1 && MessagesPanel.getPagesCurrent (iMessagesType) > 1) {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUp, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUp, messagePanelIconScrollUpPoint, (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_UP));
		} else {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUpDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUpDisabled, messagePanelIconScrollUpPoint);
		}
		if (MessagesPanel.getPages (iMessagesType) > 1 && MessagesPanel.getPagesCurrent (iMessagesType) < MessagesPanel.getPages (iMessagesType)) {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDown, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDown, messagePanelIconScrollDownPoint, (pItem != null && pItem.x == UIPanel.MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_DOWN));
		} else {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDownDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDownDisabled, messagePanelIconScrollDownPoint);
		}

		// Subpanel donde irá el texto
		iCurrentTexture = UtilsGL.setTexture (tileMessagesPanelSubPanel[0], iCurrentTexture);
		UIPanel.renderBackground (tileMessagesPanelSubPanel, messagesPanelSubPanelPoint, MESSAGES_PANEL_SUBPANEL_WIDTH, MESSAGES_PANEL_SUBPANEL_HEIGHT);

		UtilsGL.glEnd ();

		// Pages
		String sText = MessagesPanel.getPagesCurrent (iMessagesType) + " / " + MessagesPanel.getPages (iMessagesType); //$NON-NLS-1$
		// XAVI GL11.glColor4f (1, 1, 1, 1);
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UtilsGL.drawString (sText, messagePanelPagesPositionPoint.x - UtilFont.getWidth (sText) / 2, messagePanelPagesPositionPoint.y, ColorGL.BLACK);
		UtilsGL.glEnd ();

		// Mensajes
		MessagesPanel.render (mouseX, mouseY, getMessagesPanelActive (), messagesPanelSubPanelPoint.x + tileMessagesPanel[3].getTileWidth (), messagesPanelSubPanelPoint.y + tileMessagesPanel[1].getTileHeight ());
	}

	public static boolean isMouseOnMessagesPanel (int x, int y) {
		return ((y >= messagesPanelPoint.y && y < (messagesPanelPoint.y + MESSAGES_PANEL_HEIGHT)) && (x >= messagesPanelPoint.x && x < (messagesPanelPoint.x + MESSAGES_PANEL_WIDTH)));
	}

	public static Point isMouseOnMessagesButtons (int x, int y) {
		if (UIPanel.typingPanel != null) {
			return null;
		}

		Point point;
		// Close button
		point = messagesPanelClosePoint;
		if (x >= point.x && x < (point.x + UIPanel.tileButtonClose.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileButtonClose.getTileHeight ())) {
			if (!UIPanel.tileButtonCloseAlpha[x - point.x][y - point.y]) {
				return MOUSE_MESSAGES_PANEL_BUTTONS_CLOSE_POINT;
			}
		}

		// Types
		if (UIPanel.isMouseOnAnIcon (x, y, messagePanelIconPoints[MessagesPanel.TYPE_ANNOUNCEMENT], messagePanelTiles[MessagesPanel.TYPE_ANNOUNCEMENT], messagePanelTilesAlpha.get (MessagesPanel.TYPE_ANNOUNCEMENT))) {
			return MOUSE_MESSAGES_PANEL_BUTTONS_ANNOUNCEMENT_POINT;
		}
		if (UIPanel.isMouseOnAnIcon (x, y, messagePanelIconPoints[MessagesPanel.TYPE_COMBAT], messagePanelTiles[MessagesPanel.TYPE_COMBAT], messagePanelTilesAlpha.get (MessagesPanel.TYPE_COMBAT))) {
			return MOUSE_MESSAGES_PANEL_BUTTONS_COMBAT_POINT;
		}
		if (UIPanel.isMouseOnAnIcon (x, y, messagePanelIconPoints[MessagesPanel.TYPE_HEROES], messagePanelTiles[MessagesPanel.TYPE_HEROES], messagePanelTilesAlpha.get (MessagesPanel.TYPE_HEROES))) {
			return MOUSE_MESSAGES_PANEL_BUTTONS_HEROES_POINT;
		}
		if (UIPanel.isMouseOnAnIcon (x, y, messagePanelIconPoints[MessagesPanel.TYPE_SYSTEM], messagePanelTiles[MessagesPanel.TYPE_SYSTEM], messagePanelTilesAlpha.get (MessagesPanel.TYPE_SYSTEM))) {
			return MOUSE_MESSAGES_PANEL_BUTTONS_SYSTEM_POINT;
		}

		// Scrolls
		if (UIPanel.isMouseOnAnIcon (x, y, messagePanelIconScrollUpPoint, UIPanel.tileScrollUp, UIPanel.tileScrollUpButtonAlpha)) {
			return MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_UP_POINT;
		}
		if (UIPanel.isMouseOnAnIcon (x, y, messagePanelIconScrollDownPoint, UIPanel.tileScrollDown, UIPanel.tileScrollDownButtonAlpha)) {
			return MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_DOWN_POINT;
		}

		return null;
	}

	public static boolean isMessagesPanelActive () {
		return messagesPanelActive != -1;
	}

	public static int getMessagesPanelActive () {
		return messagesPanelActive;
	}

	public static void setMessagesPanelActive (int iMessageType) {
		messagesPanelActive = iMessageType;
		if (iMessageType != -1) {
			UIPanel.closePanels (true, true, false, true, true, true, true);
		}
	}

	public static void createMessagesPanel () {
		messagesPanelActive = -1;

		// Tamaño y close button
		MESSAGES_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7;
		MESSAGES_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconNumCitizensBackgroundPoint.y + UIPanel.tileBottomItem.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () / 2;
		messagesPanelPoint.setLocation (UIPanel.renderWidth / 8 - ((UIPanel.renderWidth / 8) / 2), UIPanel.iconNumCitizensBackgroundPoint.y + UIPanel.tileBottomItem.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 4);
		messagesPanelClosePoint.setLocation (messagesPanelPoint.x + MESSAGES_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), messagesPanelPoint.y);

		// Mini iconos y puntos (los que salen arriba a la izquierda de la pantalla, no los de dentro del panel)
		if (messageTiles == null) {
			messageTiles = new Tile [MessagesPanel.MAX_TYPES];
			messageTilesON = new Tile [MessagesPanel.MAX_TYPES];
			messageTilesAlpha = new ArrayList<boolean[][]> (MessagesPanel.MAX_TYPES);

			for (int i = 0; i < MessagesPanel.MAX_TYPES; i++) {
				messageTiles[i] = new Tile ("icon_messages" + i); //$NON-NLS-1$
				messageTilesON[i] = new Tile ("icon_messages" + i + "ON"); //$NON-NLS-1$ //$NON-NLS-2$
				messageTilesAlpha.add (UtilsGL.generateAlpha (messageTiles[i]));
			}
		}

		messageIconPoints = new Point [MessagesPanel.MAX_TYPES];
		for (int i = 0; i < MessagesPanel.MAX_TYPES; i++) {
			messageIconPoints[i] = new Point (UIPanel.PIXELS_TO_BORDER + i * (messageTiles[i].getTileWidth () + UIPanel.PIXELS_TO_BORDER), UIPanel.PIXELS_TO_BORDER);
		}

		// Iconos dentro del panel
		// Los "tabs" (iconos gordos arriba)
		if (messagePanelTiles == null) {
			messagePanelTiles = new Tile [MessagesPanel.MAX_TYPES];
			messagePanelTilesON = new Tile [MessagesPanel.MAX_TYPES];
			messagePanelTilesAlpha = new ArrayList<boolean[][]> (MessagesPanel.MAX_TYPES);

			for (int i = 0; i < MessagesPanel.MAX_TYPES; i++) {
				messagePanelTiles[i] = new Tile ("icon_big_messages" + i); //$NON-NLS-1$
				messagePanelTilesON[i] = new Tile ("icon_big_messages" + i + "ON"); //$NON-NLS-1$ //$NON-NLS-2$
				messagePanelTilesAlpha.add (UtilsGL.generateAlpha (messagePanelTiles[i]));
			}
		}

		// Scroll up/down
		messagePanelIconScrollUpPoint.setLocation (messagesPanelPoint.x + MESSAGES_PANEL_WIDTH - tileMessagesPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), messagesPanelPoint.y + tileMessagesPanel[1].getTileHeight () + messagePanelTiles[0].getTileHeight ());
		messagePanelIconScrollDownPoint.setLocation (messagesPanelPoint.x + MESSAGES_PANEL_WIDTH - tileMessagesPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), messagesPanelPoint.y + MESSAGES_PANEL_HEIGHT - UIPanel.tileScrollDown.getTileHeight () - tileMessagesPanel[1].getTileHeight ());

		// Pages
		messagePanelPagesPositionPoint.setLocation (messagePanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () / 2, messagePanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight () + (messagePanelIconScrollDownPoint.y - (messagePanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight ())) / 2 - UtilFont.MAX_HEIGHT / 2);

		// Subpanel
		MESSAGES_PANEL_SUBPANEL_WIDTH = MESSAGES_PANEL_WIDTH - (3 * tileMessagesPanel[3].getTileWidth () + UIPanel.tileScrollUp.getTileWidth ());
		MESSAGES_PANEL_SUBPANEL_HEIGHT = (messagePanelIconScrollDownPoint.y + UIPanel.tileScrollDown.getTileHeight ()) - messagePanelIconScrollUpPoint.y;
		messagesPanelSubPanelPoint.setLocation (messagesPanelPoint.x + tileMessagesPanel[3].getTileWidth (), messagePanelIconScrollUpPoint.y);

		// Posición de iconos (los 4 de arriba) dentro del panel (va aquí pq tienen que centrarse con el subpanel)
		messagePanelIconPoints = new Point [MessagesPanel.MAX_TYPES];
		int iSeparation = (MESSAGES_PANEL_SUBPANEL_WIDTH - (MessagesPanel.MAX_TYPES * messagePanelTiles[0].getTileWidth ())) / (MessagesPanel.MAX_TYPES + 1);
		for (int i = 0; i < MessagesPanel.MAX_TYPES; i++) {
			messagePanelIconPoints[i] = new Point (messagesPanelSubPanelPoint.x + iSeparation + (i * (messagePanelTiles[0].getTileWidth () + iSeparation)), messagesPanelPoint.y + tileMessagesPanel[1].getTileHeight ());
		}

		// Ésto es para que parta los messages render
		MessagesPanel.resize (MESSAGES_PANEL_WIDTH, MESSAGES_PANEL_HEIGHT);
	}
}
