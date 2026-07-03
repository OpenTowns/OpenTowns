package xaos.panels;

import java.awt.Point;
import org.lwjgl.opengl.GL11;
import xaos.campaign.TutorialTrigger;
import xaos.data.CaravanData;
import xaos.main.Game;
import xaos.panels.menus.SmartMenu;
import xaos.tiles.Tile;
import xaos.utils.ColorGL;
import xaos.utils.Log;
import xaos.utils.Messages;
import xaos.utils.UtilFont;
import xaos.utils.UtilsGL;


public final class TradeUIPanel {

	public static Point MOUSE_TRADE_PANEL_BUTTONS_CARAVAN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_CARAVAN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_CARAVAN_UP_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_UP_CARAVAN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_CARAVAN_DOWN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_DOWN_CARAVAN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN_UP_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_UP_CARAVAN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN_DOWN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_DOWN_CARAVAN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_CONFIRM_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_CONFIRM, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TOWN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TOWN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TOWN_UP_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_UP_TOWN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TOWN_DOWN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_DOWN_TOWN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN_UP_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_UP_TOWN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN_DOWN_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_DOWN_TOWN, -1);

	public static Point MOUSE_TRADE_PANEL_BUTTONS_CLOSE_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_BUTTONS_CLOSE, -1);

	public static Point MOUSE_TRADE_PANEL_ICON_BUY_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_ICON_BUY, -1);

	public static Point MOUSE_TRADE_PANEL_ICON_SELL_POINT = new Point (UIPanel.MOUSE_TRADE_PANEL_ICON_SELL, -1);

	public static int TRADE_PANEL_WIDTH;

	public static int TRADE_PANEL_HEIGHT;

	public final static int TRADE_PANEL_BUTTON_WIDTH = 64;

	public final static int TRADE_PANEL_BUTTON_HEIGHT = 64;

	public static Tile[] tileTradePanel;

	public static Point tradePanelPoint = new Point (0, 0);

	public static Point tradePanelClosePoint = new Point (0, 0);

	public static TradePanel tradePanel;

	public static boolean tradePanelActive = false;

	private static boolean tradePanelActivePausedBefore = false;

	public static void renderTradePanel (int mouseX, int mouseY, int mousePanel) {
		Point pItem = isMouseOnTradeButtons (mouseX, mouseY);

		// Fondo
		// XAVI GL11.glColor4f (1, 1, 1, 1);
		int iCurrentTexture = tileTradePanel[0].getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UIPanel.renderBackground (tileTradePanel, tradePanelPoint, TRADE_PANEL_WIDTH, TRADE_PANEL_HEIGHT);

		// Close button
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileButtonClose, iCurrentTexture);
		if (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_CLOSE) {
			UIPanel.drawTile (UIPanel.tileButtonClose, tradePanelClosePoint);
		} else {
			UIPanel.drawTile (UIPanel.tileButtonCloseDisabled, tradePanelClosePoint);
		}

		UtilsGL.glEnd ();

		// Miramos si hay caravana activa
		CaravanData caravanData = Game.getWorld ().getCurrentCaravanData ();
		String sText = null;
		boolean bTrading = false;
		if (caravanData == null || caravanData.getStatus () == CaravanData.STATUS_NONE) {
			sText = Messages.getString ("UIPanel.17"); //$NON-NLS-1$
		} else if (caravanData.getStatus () == CaravanData.STATUS_COMING) {
			sText = Messages.getString ("UIPanel.18"); //$NON-NLS-1$
		} else if (caravanData.getStatus () == CaravanData.STATUS_IN_PLACE) {
			sText = null;
		} else if (caravanData.getStatus () == CaravanData.STATUS_TRADING) {
			sText = Messages.getString ("UIPanel.20"); //$NON-NLS-1$
			bTrading = true;
		} else if (caravanData.getStatus () == CaravanData.STATUS_LEAVING) {
			sText = Messages.getString ("UIPanel.21"); //$NON-NLS-1$
		} else {
			// Nunca debería llegar aquí
			Log.log (Log.LEVEL_ERROR, "Caravan status [" + caravanData.getStatus () + "]", "UIPanel"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			sText = null;
		}

		if (sText != null && !bTrading) {
			int iTextWidth = UtilFont.getWidth (sText);
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);
			UtilsGL.drawStringWithBorder (sText, tradePanelPoint.x + TRADE_PANEL_WIDTH / 2 - iTextWidth / 2, tradePanelPoint.y + TRADE_PANEL_HEIGHT / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.ORANGE, ColorGL.BLACK);
			UtilsGL.glEnd ();
			return;
		}

		// Si llega aquí es que la caravana está lista para tradear (o está tradeando)
		// if (!bTrading && tradePanel == null) {
		if (tradePanel == null) {
			// Acaba de entrar por primera vez, generamos el panel
			tradePanel = new TradePanel (caravanData, tradePanelPoint, TRADE_PANEL_WIDTH, TRADE_PANEL_HEIGHT);
		}

		// Renderizamos
		UtilsGL.glBegin (GL11.GL_QUADS);

		// Confirm button
		if (!bTrading) {
			iCurrentTexture = UtilsGL.setTexture (TradePanel.tileTradeConfirm, iCurrentTexture);
			if (tradePanel.isTransactionReady ()) {
				UIPanel.drawTile (TradePanel.tileTradeConfirm, tradePanel.getConfirmPoint (), TradePanel.tileTradeConfirm.getTileWidth (), TradePanel.tileTradeConfirm.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_CONFIRM));
			} else {
				UIPanel.drawTile (TradePanel.tileTradeConfirmDisabled, tradePanel.getConfirmPoint ());
			}
		}

		Point point;
		if (!bTrading) {
			// Caravan buttons scroll up
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUp, iCurrentTexture);
			point = tradePanel.getScrollUpCaravanPoint ();
			if (tradePanel.getIndexButtonsCaravan () > 0) {
				UIPanel.drawTile (UIPanel.tileScrollUp, point, UIPanel.tileScrollUp.getTileWidth (), UIPanel.tileScrollUp.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_UP_CARAVAN));
			} else {
				UIPanel.drawTile (UIPanel.tileScrollUpDisabled, point);
			}
		}
		// Caravan buttons scroll up to-buy
		point = tradePanel.getScrollUpCaravanToBuyPoint ();
		if (tradePanel.getIndexButtonsToBuyCaravan () > 0) {
			UIPanel.drawTile (UIPanel.tileScrollUp, point, UIPanel.tileScrollUp.getTileWidth (), UIPanel.tileScrollUp.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_UP_CARAVAN));
		} else {
			UIPanel.drawTile (UIPanel.tileScrollUpDisabled, point);
		}
		// Town buttons scroll up to-sell
		point = tradePanel.getScrollUpTownToSellPoint ();
		if (tradePanel.getIndexButtonsToSellTown () > 0) {
			UIPanel.drawTile (UIPanel.tileScrollUp, point, UIPanel.tileScrollUp.getTileWidth (), UIPanel.tileScrollUp.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_UP_TOWN));
		} else {
			UIPanel.drawTile (UIPanel.tileScrollUpDisabled, point);
		}

		if (!bTrading) {
			// Town buttons scroll up
			point = tradePanel.getScrollUpTownPoint ();
			if (tradePanel.getIndexButtonsTown () > 0) {
				UIPanel.drawTile (UIPanel.tileScrollUp, point, UIPanel.tileScrollUp.getTileWidth (), UIPanel.tileScrollUp.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_UP_TOWN));
			} else {
				UIPanel.drawTile (UIPanel.tileScrollUpDisabled, point);
			}
		}

		// Caravan buttons
		if (!bTrading) {
			iCurrentTexture = UtilsGL.setTexture (TradePanel.tileTradeButton, iCurrentTexture);
			for (int i = 0; i < tradePanel.getAlButtonPointsCaravan ().size (); i++) {
				point = tradePanel.getAlButtonPointsCaravan ().get (i);
				UIPanel.drawTile (TradePanel.tileTradeButton, point, TradePanel.tileTradeButton.getTileWidth (), TradePanel.tileTradeButton.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_CARAVAN && pItem.y == i));
			}
		}
		// Caravan buttons to-buy
		for (int i = 0; i < tradePanel.getAlButtonPointsCaravanToBuy ().size (); i++) {
			point = tradePanel.getAlButtonPointsCaravanToBuy ().get (i);
			UIPanel.drawTile (TradePanel.tileTradeButton, point, TradePanel.tileTradeButton.getTileWidth (), TradePanel.tileTradeButton.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN && pItem.y == i));
		}
		// Town buttons to-sell
		for (int i = 0; i < tradePanel.getAlButtonPointsTownToSell ().size (); i++) {
			point = tradePanel.getAlButtonPointsTownToSell ().get (i);
			UIPanel.drawTile (TradePanel.tileTradeButton, point, TradePanel.tileTradeButton.getTileWidth (), TradePanel.tileTradeButton.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN && pItem.y == i));
		}
		if (!bTrading) {
			// Town buttons
			for (int i = 0; i < tradePanel.getAlButtonPointsTown ().size (); i++) {
				point = tradePanel.getAlButtonPointsTown ().get (i);
				UIPanel.drawTile (TradePanel.tileTradeButton, point, TradePanel.tileTradeButton.getTileWidth (), TradePanel.tileTradeButton.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TOWN && pItem.y == i));
			}
		}

		if (!bTrading) {
			// Caravan buttons scroll down
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDown, iCurrentTexture);
			point = tradePanel.getScrollDownCaravanPoint ();
			if (tradePanel.getAlButtonPointsCaravan ().size () + tradePanel.getIndexButtonsCaravan () < tradePanel.getMenuCaravan ().getItems ().size ()) {
				UIPanel.drawTile (UIPanel.tileScrollDown, point, UIPanel.tileScrollDown.getTileWidth (), UIPanel.tileScrollDown.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_DOWN_CARAVAN));
			} else {
				UIPanel.drawTile (UIPanel.tileScrollDownDisabled, point);
			}
		}
		// Caravan buttons scroll down to-buy
		point = tradePanel.getScrollDownCaravanToBuyPoint ();
		if (tradePanel.getAlButtonPointsCaravanToBuy ().size () + tradePanel.getIndexButtonsToBuyCaravan () < caravanData.getMenuCaravanToBuy ().getItems ().size ()) {
			UIPanel.drawTile (UIPanel.tileScrollDown, point, UIPanel.tileScrollDown.getTileWidth (), UIPanel.tileScrollDown.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_DOWN_CARAVAN));
		} else {
			UIPanel.drawTile (UIPanel.tileScrollDownDisabled, point);
		}
		// Town buttons scroll down to-sell
		point = tradePanel.getScrollDownTownToSellPoint ();
		if (tradePanel.getAlButtonPointsTownToSell ().size () + tradePanel.getIndexButtonsToSellTown () < caravanData.getMenuTownToSell ().getItems ().size ()) {
			UIPanel.drawTile (UIPanel.tileScrollDown, point, UIPanel.tileScrollDown.getTileWidth (), UIPanel.tileScrollDown.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_DOWN_TOWN));
		} else {
			UIPanel.drawTile (UIPanel.tileScrollDownDisabled, point);
		}
		if (!bTrading) {
			// Town buttons scroll down
			point = tradePanel.getScrollDownTownPoint ();
			if (tradePanel.getAlButtonPointsTown ().size () + tradePanel.getIndexButtonsTown () < tradePanel.getMenuTown ().getItems ().size ()) {
				UIPanel.drawTile (UIPanel.tileScrollDown, point, UIPanel.tileScrollDown.getTileWidth (), UIPanel.tileScrollDown.getTileHeight (), (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_DOWN_TOWN));
			} else {
				UIPanel.drawTile (UIPanel.tileScrollDownDisabled, point);
			}
		}

		// Icons
		iCurrentTexture = UtilsGL.setTexture (TradePanel.tileTradeCaravanCoins, iCurrentTexture);
		UIPanel.drawTile (TradePanel.tileTradeCaravanCoins, tradePanel.getCaravanCoinsIconPoint ());
		iCurrentTexture = UtilsGL.setTexture (TradePanel.tileTradeTownCoins, iCurrentTexture);
		UIPanel.drawTile (TradePanel.tileTradeTownCoins, tradePanel.getTownCoinsIconPoint ());
		iCurrentTexture = UtilsGL.setTexture (TradePanel.tileTradeBuy, iCurrentTexture);
		UIPanel.drawTile (TradePanel.tileTradeBuy, tradePanel.getBuyIconPoint ());
		iCurrentTexture = UtilsGL.setTexture (TradePanel.tileTradeSell, iCurrentTexture);
		UIPanel.drawTile (TradePanel.tileTradeSell, tradePanel.getSellIconPoint ());
		iCurrentTexture = UtilsGL.setTexture (TradePanel.tileTradeCost, iCurrentTexture);
		UIPanel.drawTile (TradePanel.tileTradeCost, tradePanel.getCostPoint ());

		SmartMenu menu;
		int iIndex;

		if (!bTrading) {
			// Caravan Items
			menu = tradePanel.getMenuCaravan ();
			for (int i = 0; i < tradePanel.getAlButtonPointsCaravan ().size (); i++) {
				iIndex = i + tradePanel.getIndexButtonsCaravan ();
				if (menu.getItems ().size () <= iIndex) {
					break;
				}
				if (menu.getItems ().get (iIndex).getIcon () != null && menu.getItems ().get (iIndex).getIconType () == SmartMenu.ICON_TYPE_ITEM) {
					point = tradePanel.getAlButtonPointsCaravan ().get (i);
					iCurrentTexture = UtilsGL.setTexture (menu.getItems ().get (iIndex).getIcon (), iCurrentTexture);
					UIPanel.drawTile (menu.getItems ().get (iIndex).getIcon (), point, TRADE_PANEL_BUTTON_WIDTH, TRADE_PANEL_BUTTON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_CARAVAN && pItem.y == i));
				}
			}
		}
		// Caravan Items to-buy
		menu = caravanData.getMenuCaravanToBuy ();
		for (int i = 0; i < tradePanel.getAlButtonPointsCaravanToBuy ().size (); i++) {
			iIndex = i + tradePanel.getIndexButtonsToBuyCaravan ();
			if (menu.getItems ().size () <= iIndex) {
				break;
			}
			if (menu.getItems ().get (iIndex).getIcon () != null && menu.getItems ().get (iIndex).getIconType () == SmartMenu.ICON_TYPE_ITEM) {
				point = tradePanel.getAlButtonPointsCaravanToBuy ().get (i);
				iCurrentTexture = UtilsGL.setTexture (menu.getItems ().get (iIndex).getIcon (), iCurrentTexture);
				UIPanel.drawTile (menu.getItems ().get (iIndex).getIcon (), point, TRADE_PANEL_BUTTON_WIDTH, TRADE_PANEL_BUTTON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN && pItem.y == i));
			}
		}
		// Town Items to-sell
		menu = caravanData.getMenuTownToSell ();
		for (int i = 0; i < tradePanel.getAlButtonPointsTownToSell ().size (); i++) {
			iIndex = i + tradePanel.getIndexButtonsToSellTown ();
			if (menu.getItems ().size () <= iIndex) {
				break;
			}
			if (menu.getItems ().get (iIndex).getIcon () != null && menu.getItems ().get (iIndex).getIconType () == SmartMenu.ICON_TYPE_ITEM) {
				point = tradePanel.getAlButtonPointsTownToSell ().get (i);
				iCurrentTexture = UtilsGL.setTexture (menu.getItems ().get (iIndex).getIcon (), iCurrentTexture);
				UIPanel.drawTile (menu.getItems ().get (iIndex).getIcon (), point, TRADE_PANEL_BUTTON_WIDTH, TRADE_PANEL_BUTTON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN && pItem.y == i));
			}
		}
		if (!bTrading) {
			// Town Items
			menu = tradePanel.getMenuTown ();
			for (int i = 0; i < tradePanel.getAlButtonPointsTown ().size (); i++) {
				iIndex = i + tradePanel.getIndexButtonsTown ();
				if (menu.getItems ().size () <= iIndex) {
					break;
				}
				if (menu.getItems ().get (iIndex).getIcon () != null && menu.getItems ().get (iIndex).getIconType () == SmartMenu.ICON_TYPE_ITEM) {
					point = tradePanel.getAlButtonPointsTown ().get (i);
					iCurrentTexture = UtilsGL.setTexture (menu.getItems ().get (iIndex).getIcon (), iCurrentTexture);
					UIPanel.drawTile (menu.getItems ().get (iIndex).getIcon (), point, TRADE_PANEL_BUTTON_WIDTH, TRADE_PANEL_BUTTON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_TRADE_PANEL_BUTTONS_TOWN && pItem.y == i));
				}
			}
		}
		UtilsGL.glEnd ();

		// Números
		int iTextWidth;
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		if (!bTrading) {
			// Caravan
			menu = tradePanel.getMenuCaravan ();
			for (int i = 0; i < tradePanel.getAlButtonPointsCaravan ().size (); i++) {
				iIndex = i + tradePanel.getIndexButtonsCaravan ();
				if (menu.getItems ().size () <= iIndex) {
					break;
				}
				point = tradePanel.getAlButtonPointsCaravan ().get (i);
				sText = menu.getItems ().get (iIndex).getParameter2 ();
				iTextWidth = UtilFont.getWidth (sText); // Qtty
				UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y + TRADE_PANEL_BUTTON_HEIGHT - UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
				sText = Integer.toString (menu.getItems ().get (iIndex).getDirectCoordinates ().x);
				iTextWidth = UtilFont.getWidth (sText); // Price
				UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y, ColorGL.WHITE, ColorGL.BLACK);
			}
		}
		// Caravan to-buy
		menu = caravanData.getMenuCaravanToBuy ();
		for (int i = 0; i < tradePanel.getAlButtonPointsCaravanToBuy ().size (); i++) {
			iIndex = i + tradePanel.getIndexButtonsToBuyCaravan ();
			if (menu.getItems ().size () <= iIndex) {
				break;
			}
			point = tradePanel.getAlButtonPointsCaravanToBuy ().get (i);
			sText = menu.getItems ().get (iIndex).getParameter2 ();
			iTextWidth = UtilFont.getWidth (sText); // Qtty
			UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y + TRADE_PANEL_BUTTON_HEIGHT - UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
			sText = Integer.toString (menu.getItems ().get (iIndex).getDirectCoordinates ().x);
			iTextWidth = UtilFont.getWidth (sText); // Price
			UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y, ColorGL.WHITE, ColorGL.BLACK);
		}
		// Town to-sell
		menu = caravanData.getMenuTownToSell ();
		for (int i = 0; i < tradePanel.getAlButtonPointsTownToSell ().size (); i++) {
			iIndex = i + tradePanel.getIndexButtonsToSellTown ();
			if (menu.getItems ().size () <= iIndex) {
				break;
			}
			point = tradePanel.getAlButtonPointsTownToSell ().get (i);
			sText = menu.getItems ().get (iIndex).getParameter2 ();
			iTextWidth = UtilFont.getWidth (sText); // Qtty
			UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y + TRADE_PANEL_BUTTON_HEIGHT - UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
			sText = Integer.toString (menu.getItems ().get (iIndex).getDirectCoordinates ().x);
			iTextWidth = UtilFont.getWidth (sText); // Price
			UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y, ColorGL.WHITE, ColorGL.BLACK);
		}
		if (!bTrading) {
			// Town
			menu = tradePanel.getMenuTown ();
			for (int i = 0; i < tradePanel.getAlButtonPointsTown ().size (); i++) {
				iIndex = i + tradePanel.getIndexButtonsTown ();
				if (menu.getItems ().size () <= iIndex) {
					break;
				}
				point = tradePanel.getAlButtonPointsTown ().get (i);
				sText = menu.getItems ().get (iIndex).getParameter2 ();
				iTextWidth = UtilFont.getWidth (sText); // Qtty
				UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y + TRADE_PANEL_BUTTON_HEIGHT - UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
				sText = Integer.toString (menu.getItems ().get (iIndex).getDirectCoordinates ().x);
				iTextWidth = UtilFont.getWidth (sText); // Price
				UtilsGL.drawStringWithBorder (sText, point.x + TRADE_PANEL_BUTTON_WIDTH / 2 - iTextWidth / 2, point.y, ColorGL.WHITE, ColorGL.BLACK);
			}
		}

		// Caravan coins
		sText = Integer.toString (caravanData.getCoins ());
		UtilsGL.drawString (sText, tradePanel.getCaravanCoinsIconPoint ().x + TradePanel.tileTradeCaravanCoins.getTileWidth () / 2 - UtilFont.getWidth (sText) / 2, tradePanel.getCaravanCoinsIconPoint ().y + TradePanel.tileTradeCaravanCoins.getTileHeight (), ColorGL.BLACK);
		// Cost
		sText = Integer.toString (tradePanel.getCost ());
		UtilsGL.drawString (sText, tradePanel.getCostPoint ().x + TradePanel.tileTradeCost.getTileWidth () / 2 - UtilFont.getWidth (sText) / 2, tradePanel.getCostPoint ().y + TradePanel.tileTradeCost.getTileHeight (), tradePanel.getCost () >= 0 ? ColorGL.BLACK : ColorGL.RED);
		// Towns coins
		sText = Integer.toString (Game.getWorld ().getCoins ());
		UtilsGL.drawString (sText, tradePanel.getTownCoinsIconPoint ().x + TradePanel.tileTradeTownCoins.getTileWidth () / 2 - UtilFont.getWidth (sText) / 2, tradePanel.getTownCoinsIconPoint ().y + TradePanel.tileTradeTownCoins.getTileHeight (), ColorGL.BLACK);

		UtilsGL.glEnd ();
	}

	public static boolean isMouseOnTradePanel (int x, int y) {
		return ((y >= tradePanelPoint.y && y < (tradePanelPoint.y + TRADE_PANEL_HEIGHT)) && (x >= tradePanelPoint.x && x < (tradePanelPoint.x + TRADE_PANEL_WIDTH)));
	}

	public static Point isMouseOnTradeButtons (int x, int y) {
		if (UIPanel.typingPanel != null) {
			return null;
		}

		Point point;
		// Close button
		point = tradePanelClosePoint;
		if (x >= point.x && x < (point.x + UIPanel.tileButtonClose.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileButtonClose.getTileHeight ())) {
			if (!UIPanel.tileButtonCloseAlpha[x - point.x][y - point.y]) {
				return MOUSE_TRADE_PANEL_BUTTONS_CLOSE_POINT;
			}
		}

		if (tradePanel != null && y >= tradePanelPoint.y && y < (tradePanelPoint.y + TRADE_PANEL_HEIGHT) && x >= tradePanelPoint.x && x < (tradePanelPoint.x + TRADE_PANEL_WIDTH)) {
			// Scroll up caravan
			point = tradePanel.getScrollUpCaravanPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
				if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_CARAVAN_UP_POINT;
				}
			}
			// Scroll down caravan
			point = tradePanel.getScrollDownCaravanPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
				if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_CARAVAN_DOWN_POINT;
				}
			}

			// Scroll up caravan to-buy
			point = tradePanel.getScrollUpCaravanToBuyPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
				if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN_UP_POINT;
				}
			}
			// Scroll down caravan to-buy
			point = tradePanel.getScrollDownCaravanToBuyPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
				if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN_DOWN_POINT;
				}
			}

			// Scroll up town to-sell
			point = tradePanel.getScrollUpTownToSellPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
				if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN_UP_POINT;
				}
			}
			// Scroll down town to-sell
			point = tradePanel.getScrollDownTownToSellPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
				if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN_DOWN_POINT;
				}
			}

			// Scroll up town
			point = tradePanel.getScrollUpTownPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
				if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_TOWN_UP_POINT;
				}
			}
			// Scroll down town
			point = tradePanel.getScrollDownTownPoint ();
			if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
				if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_TOWN_DOWN_POINT;
				}
			}

			// Confirm
			point = tradePanel.getConfirmPoint ();
			if (x >= point.x && x < (point.x + TradePanel.tileTradeConfirm.getTileWidth ()) && y >= point.y && y < (point.y + TradePanel.tileTradeConfirm.getTileHeight ())) {
				if (!TradePanel.tileTradeConfirmAlpha[x - point.x][y - point.y]) {
					return MOUSE_TRADE_PANEL_BUTTONS_CONFIRM_POINT;
				}
			}

			// Caravan buttons
			for (int i = 0; i < tradePanel.getAlButtonPointsCaravan ().size (); i++) {
				point = tradePanel.getAlButtonPointsCaravan ().get (i);
				if (x >= point.x && x < (point.x + TRADE_PANEL_BUTTON_WIDTH) && y >= point.y && y < (point.y + TRADE_PANEL_BUTTON_HEIGHT)) {
					if (!TradePanel.tileTradeButtonAlpha[x - point.x][y - point.y]) {
						MOUSE_TRADE_PANEL_BUTTONS_CARAVAN_POINT.y = i;
						return MOUSE_TRADE_PANEL_BUTTONS_CARAVAN_POINT;
					}
				}
			}

			// Caravan buttons to-buy
			for (int i = 0; i < tradePanel.getAlButtonPointsCaravanToBuy ().size (); i++) {
				point = tradePanel.getAlButtonPointsCaravanToBuy ().get (i);
				if (x >= point.x && x < (point.x + TRADE_PANEL_BUTTON_WIDTH) && y >= point.y && y < (point.y + TRADE_PANEL_BUTTON_HEIGHT)) {
					if (!TradePanel.tileTradeButtonAlpha[x - point.x][y - point.y]) {
						MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN_POINT.y = i;
						return MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN_POINT;
					}
				}
			}

			// Town buttons to-sell
			for (int i = 0; i < tradePanel.getAlButtonPointsTownToSell ().size (); i++) {
				point = tradePanel.getAlButtonPointsTownToSell ().get (i);
				if (x >= point.x && x < (point.x + TRADE_PANEL_BUTTON_WIDTH) && y >= point.y && y < (point.y + TRADE_PANEL_BUTTON_HEIGHT)) {
					if (!TradePanel.tileTradeButtonAlpha[x - point.x][y - point.y]) {
						MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN_POINT.y = i;
						return MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN_POINT;
					}
				}
			}

			// Town buttons
			for (int i = 0; i < tradePanel.getAlButtonPointsTown ().size (); i++) {
				point = tradePanel.getAlButtonPointsTown ().get (i);
				if (x >= point.x && x < (point.x + TRADE_PANEL_BUTTON_WIDTH) && y >= point.y && y < (point.y + TRADE_PANEL_BUTTON_HEIGHT)) {
					if (!TradePanel.tileTradeButtonAlpha[x - point.x][y - point.y]) {
						MOUSE_TRADE_PANEL_BUTTONS_TOWN_POINT.y = i;
						return MOUSE_TRADE_PANEL_BUTTONS_TOWN_POINT;
					}
				}
			}

			// Buy icon
			point = tradePanel.getBuyIconPoint ();
			if (x >= point.x && x < (point.x + TradePanel.tileTradeBuy.getTileWidth ()) && y >= point.y && y < (point.y + TradePanel.tileTradeBuy.getTileHeight ())) {
				return MOUSE_TRADE_PANEL_ICON_BUY_POINT;
			}

			// Sell icon
			point = tradePanel.getSellIconPoint ();
			if (x >= point.x && x < (point.x + TradePanel.tileTradeSell.getTileWidth ()) && y >= point.y && y < (point.y + TradePanel.tileTradeSell.getTileHeight ())) {
				return MOUSE_TRADE_PANEL_ICON_SELL_POINT;
			}
		}

		return null;
	}

	public static void setTradePanelActive (boolean tradePanelActive) {
		if (!isTradePanelActive ()) {
			if (Game.getWorld ().getCurrentCaravanData () != null) {
				Game.getWorld ().getCurrentCaravanData ().updateCaravanStatus ();
			}
		}

		if (TradeUIPanel.tradePanelActive != tradePanelActive) {
			if (tradePanelActive) {
				// Se activa el panel
				tradePanelActivePausedBefore = Game.isPaused ();
				Game.pause (false);

				// Chequeamos los items en el mundo
				if (tradePanel != null) {
					tradePanel.createTownMenu ();
				}
				TradeUIPanel.tradePanelActive = tradePanelActive;


				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_TRADE, null);
			} else {
				TradeUIPanel.tradePanelActive = tradePanelActive; // Se pone primero pq sino el Game.resume no funcionará

				// Se desactiva el panel, quitamos la pausa si al activar el panel el juego no estaba pausado
				if (!tradePanelActivePausedBefore) {
					// Si antes no había pausa la quitamos
					Game.resume (false);
				}
			}

			if (tradePanelActive) {
				UIPanel.closePanels (true, false, true, true, true, true, true);
			}
		}
	}

	public static boolean isTradePanelActive () {
		return tradePanelActive;
	}

	public static void createTradePanel () {
		TRADE_PANEL_WIDTH = (UIPanel.renderWidth / 8) * 7;
		TRADE_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconNumCitizensBackgroundPoint.y + UIPanel.tileBottomItem.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () / 2;
		tradePanelPoint.setLocation (UIPanel.renderWidth / 8 - ((UIPanel.renderWidth / 8) / 2), UIPanel.iconNumCitizensBackgroundPoint.y + UIPanel.tileBottomItem.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 4);
		tradePanelClosePoint.setLocation (tradePanelPoint.x + TRADE_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), tradePanelPoint.y);

		TradePanel.loadStatics ();
		if (tradePanel != null && Game.getWorld () != null && Game.getWorld ().getCurrentCaravanData () != null) {
			tradePanel.resize (Game.getWorld ().getCurrentCaravanData ());
		}
	}

	public static void createTradePanelContent (CaravanData caravanData) {
		if (Game.isHeadless ()) {
			// Caravan sim opens its trade UI on arrival; nothing to open here
			return;
		}

		if (caravanData != null) {
			if (tradePanel == null) {
				tradePanel = new TradePanel (caravanData, tradePanelPoint, TRADE_PANEL_WIDTH, TRADE_PANEL_HEIGHT);
			} else {
				tradePanel.resize (caravanData);
			}
		}
	}

	public static void deleteTradePanel () {
		tradePanelActive = false;
		tradePanel = null;
	}
}
