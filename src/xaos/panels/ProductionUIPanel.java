package xaos.panels;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import org.lwjgl.opengl.GL11;
import xaos.actions.ActionManager;
import xaos.actions.ActionManagerItem;
import xaos.campaign.TutorialFlow;
import xaos.main.Game;
import xaos.main.World;
import xaos.panels.menus.SmartMenu;
import xaos.tiles.Tile;
import xaos.tiles.entities.items.Item;
import xaos.utils.ColorGL;
import xaos.utils.UtilFont;
import xaos.utils.UtilsGL;
import xaos.utils.UtilsIniHeaders;


public final class ProductionUIPanel {

	public static Point MOUSE_PRODUCTION_PANEL_ITEMS_POINT = new Point (UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS, -1);

	public static Point MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR_POINT = new Point (UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR, -1);

	public static Point MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR_POINT = new Point (UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR, -1);

	public static Point MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED_POINT = new Point (UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED, -1);

	public static Point MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED_POINT = new Point (UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED, -1);

	public final static int PRODUCTION_PANEL_ITEM_WIDTH = 64;

	public final static int PRODUCTION_PANEL_ITEM_HEIGHT = 64;

	private static int PRODUCTION_PANEL_NUM_ITEMS_X;

	private static int PRODUCTION_PANEL_NUM_ITEMS_Y;

	public static int PRODUCTION_PANEL_WIDTH;

	public static int PRODUCTION_PANEL_HEIGHT;

	// PRODUCTION panel
	public static Tile[] tileProductionPanel;

	public static Point productionPanelPoint = new Point (0, 0);

	public static boolean productionPanelActive = false;

	private static boolean productionPanelLocked = false;

	public static SmartMenu productionPanelMenu;

	private static ArrayList<Point> productionPanelItemsPosition = new ArrayList<Point> ();

	public static Tile tileProductionPanelPlusIcon;

	public static boolean tileProductionPanelPlusIconAlpha[][];

	private static ArrayList<Point> productionPanelItemsPlusRegularPosition = new ArrayList<Point> ();

	private static ArrayList<Point> productionPanelItemsPlusAutomatedPosition = new ArrayList<Point> ();

	public static Tile tileProductionPanelMinusIcon;

	public static boolean tileProductionPanelMinusIconAlpha[][];

	private static ArrayList<Point> productionPanelItemsMinusRegularPosition = new ArrayList<Point> ();

	private static ArrayList<Point> productionPanelItemsMinusAutomatedPosition = new ArrayList<Point> ();

	public static Tile tileOpenProductionPanel;

	public static Tile tileOpenProductionPanelON;

	public static Point tileOpenCloseProductionPanelPoint = new Point (0, 0);

	private static boolean checkBlinkProduction = false;

	public static void renderProductionPanel (int mouseX, int mouseY, int mousePanel) {
		checkBlinkProduction = (UIPanel.blinkTurns >= UIPanel.MAX_BLINK_TURNS / 2) && TutorialFlow.isBlinkProduction ();

		if (isProductionPanelActive ()) {
			int iCurrentTexture = tileProductionPanel[0].getTextureID ();
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);

			UIPanel.renderBackground (tileProductionPanel, productionPanelPoint, PRODUCTION_PANEL_WIDTH, PRODUCTION_PANEL_HEIGHT);

			// Items
			int iMenu;
			Point point;
			SmartMenu smItem;
			Point pItem;
			if (mousePanel == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS || mousePanel == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED || mousePanel == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR || mousePanel == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED || mousePanel == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR) {
				pItem = isMouseOnProductionItems (mouseX, mouseY);
			} else {
				pItem = null;
			}

			if (productionPanelMenu != null) {
				Tile tile;
				bucle1: for (int y = 0; y < PRODUCTION_PANEL_NUM_ITEMS_Y; y++) {
					for (int x = 0; x < PRODUCTION_PANEL_NUM_ITEMS_X; x++) {
						iMenu = (y * PRODUCTION_PANEL_NUM_ITEMS_X) + x;
						if (iMenu >= productionPanelMenu.getItems ().size ()) {
							break bucle1;
						}
						smItem = productionPanelMenu.getItems ().get (iMenu);

						point = productionPanelItemsPosition.get (iMenu);
						boolean bBlinkItem = checkBlinkProduction && TutorialFlow.currentBlinkProduction (productionPanelMenu.getItems ().get (iMenu).getID ());
						TutorialFlow tutFlow = null;
						if (bBlinkItem && Game.getCurrentMissionData () != null && ImagesPanel.getCurrentFlowIndex () >= 0 && ImagesPanel.getCurrentFlowIndex () < Game.getCurrentMissionData ().getTutorialFlows ().size ()) {
							tutFlow = Game.getCurrentMissionData ().getTutorialFlows ().get (ImagesPanel.getCurrentFlowIndex ());
						}

						// Round button
						if (productionPanelMenu.getItems ().get (iMenu).getType () == SmartMenu.TYPE_MENU) {
							iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItemSM, iCurrentTexture);
							if (bBlinkItem) {
								UtilsGL.setColorRed ();
								UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS && pItem.y == iMenu));
								UtilsGL.unsetColor ();
							} else {
								UIPanel.drawTile (UIPanel.tileBottomItemSM, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS && pItem.y == iMenu));
							}
						} else {
							iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
							if (bBlinkItem) {
								UtilsGL.setColorRed ();
								UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS && pItem.y == iMenu));
								UtilsGL.unsetColor ();
							} else {
								UIPanel.drawTile (UIPanel.tileBottomItem, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS && pItem.y == iMenu));
							}
						}

						// Icono
						tile = productionPanelMenu.getItems ().get (iMenu).getIcon ();
						if (tile != null && productionPanelMenu.getItems ().get (iMenu).getIconType () == SmartMenu.ICON_TYPE_UI) {
							iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
							UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS && pItem.y == iMenu));
						}

						point = productionPanelItemsPlusRegularPosition.get (iMenu);
						if (point.x != -1) {
							// Regular
							iCurrentTexture = UtilsGL.setTexture (tileProductionPanelPlusIcon, iCurrentTexture);
							if (tutFlow != null && tutFlow.isBlinkProductionRegularPlus ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileProductionPanelPlusIcon, point, UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR && pItem.y == iMenu));
							if (tutFlow != null && tutFlow.isBlinkProductionRegularPlus ()) {
								UtilsGL.unsetColor ();
							}

							// Automated
							if (tutFlow != null && tutFlow.isBlinkProductionAutomatedPlus ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileProductionPanelPlusIcon, productionPanelItemsPlusAutomatedPosition.get (iMenu), UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED && pItem.y == iMenu));
							if (tutFlow != null && tutFlow.isBlinkProductionAutomatedPlus ()) {
								UtilsGL.unsetColor ();
							}

							iCurrentTexture = UtilsGL.setTexture (tileProductionPanelMinusIcon, iCurrentTexture);

							// Regular
							if (tutFlow != null && tutFlow.isBlinkProductionRegularMinus ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileProductionPanelMinusIcon, productionPanelItemsMinusRegularPosition.get (iMenu), UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR && pItem.y == iMenu));
							if (tutFlow != null && tutFlow.isBlinkProductionRegularMinus ()) {
								UtilsGL.unsetColor ();
							}

							// Automated
							if (tutFlow != null && tutFlow.isBlinkProductionAutomatedMinus ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileProductionPanelMinusIcon, productionPanelItemsMinusAutomatedPosition.get (iMenu), UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED && pItem.y == iMenu));
							if (tutFlow != null && tutFlow.isBlinkProductionAutomatedMinus ()) {
								UtilsGL.unsetColor ();
							}
						}
					}
				}
			}
			UtilsGL.glEnd ();

			/*
			 * ITEMS TEXTURES
			 */
			if (productionPanelMenu != null) {
				iCurrentTexture = Game.TEXTURE_FONT_ID;
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);

				bucle1: for (int y = 0; y < PRODUCTION_PANEL_NUM_ITEMS_Y; y++) {
					for (int x = 0; x < PRODUCTION_PANEL_NUM_ITEMS_X; x++) {
						iMenu = (y * PRODUCTION_PANEL_NUM_ITEMS_X) + x;
						if (iMenu >= productionPanelMenu.getItems ().size ()) {
							break bucle1;
						}
						point = productionPanelItemsPosition.get (iMenu);
						// Icono
						Tile tile = productionPanelMenu.getItems ().get (iMenu).getIcon ();
						if (tile != null && productionPanelMenu.getItems ().get (iMenu).getIconType () == SmartMenu.ICON_TYPE_ITEM) {
							iCurrentTexture = UtilsGL.setTexture (tile, iCurrentTexture);
							UIPanel.drawTile (tile, point, UIPanel.BOTTOM_ITEM_WIDTH, UIPanel.BOTTOM_ITEM_HEIGHT, (pItem != null && pItem.x == UIPanel.MOUSE_PRODUCTION_PANEL_ITEMS && pItem.y == iMenu));
						}
					}
				}
				UtilsGL.glEnd ();
			}

			/*
			 * NUMBERS
			 */
			if (productionPanelMenu != null) {
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);

				String strValue;
				HashMap<String, Integer> hmItemsOnQueue = Game.getWorld ().getTaskManager ().getItemsOnRegularQueue ();
				Integer iItemQueue;
				bucle1: for (int y = 0; y < PRODUCTION_PANEL_NUM_ITEMS_Y; y++) {
					for (int x = 0; x < PRODUCTION_PANEL_NUM_ITEMS_X; x++) {
						iMenu = (y * PRODUCTION_PANEL_NUM_ITEMS_X) + x;
						if (iMenu >= productionPanelMenu.getItems ().size ()) {
							break bucle1;
						}
						smItem = productionPanelMenu.getItems ().get (iMenu);
						if (smItem.getType () == SmartMenu.TYPE_ITEM) {
							if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
								point = productionPanelItemsPosition.get (iMenu);
								iItemQueue = hmItemsOnQueue.get (smItem.getParameter ());
								if (iItemQueue == null) {
									strValue = "0"; //$NON-NLS-1$
								} else {
									strValue = Integer.toString (iItemQueue);
								}
								// Regular
								UtilsGL.drawStringWithBorder (strValue, point.x - UIPanel.ICON_WIDTH / 2 - (UtilFont.getWidth (strValue)) / 2, point.y + PRODUCTION_PANEL_ITEM_HEIGHT / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.WHITE, ColorGL.BLACK);

								// Automated
								strValue = Integer.toString (Game.getWorld ().getTaskManager ().getNumItemsOnAutomatedQueue (smItem.getParameter ()));
								UtilsGL.drawStringWithBorder (strValue, point.x + PRODUCTION_PANEL_ITEM_WIDTH + UIPanel.ICON_WIDTH / 2 - (UtilFont.getWidth (strValue)) / 2, point.y + PRODUCTION_PANEL_ITEM_HEIGHT / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.WHITE, ColorGL.BLACK);

								// Items in world
								ActionManagerItem ami = ActionManager.getItem (smItem.getParameter ());
								if (ami != null && ami.getGeneratedItem () != null) {
									int iNum = Item.getNumItems (UtilsIniHeaders.getIntIniHeader (ami.getGeneratedItem ()), false, World.MAP_DEPTH);
									if (iNum > 0) {
										strValue = Integer.toString (iNum);
										UtilsGL.drawStringWithBorder (strValue, point.x + PRODUCTION_PANEL_ITEM_WIDTH / 2 - (UtilFont.getWidth (strValue)) / 2, point.y + PRODUCTION_PANEL_ITEM_HEIGHT / 4 - UtilFont.MAX_HEIGHT / 2, ColorGL.WHITE, ColorGL.BLACK);
									}
								}
							}
						}
					}
				}

				UtilsGL.glEnd ();
			}
		}

		if (isProductionPanelLocked ()) {
			// Close icon
			// XAVI GL11.glColor4f (1, 1, 1, 1);
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, tileOpenProductionPanelON.getTextureID ());
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);
			UIPanel.drawTile (tileOpenProductionPanelON, tileOpenCloseProductionPanelPoint, tileOpenProductionPanelON.getTileWidth (), tileOpenProductionPanelON.getTileHeight (), mousePanel == UIPanel.MOUSE_PRODUCTION_OPENCLOSE);
			UtilsGL.glEnd ();
		} else {
			// Open icon
			// XAVI GL11.glColor4f (1, 1, 1, 1);
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, tileOpenProductionPanel.getTextureID ());
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);
			if (checkBlinkProduction) {
				UtilsGL.setColorRed ();
			}
			UIPanel.drawTile (tileOpenProductionPanel, tileOpenCloseProductionPanelPoint, tileOpenProductionPanel.getTileWidth (), tileOpenProductionPanel.getTileHeight (), mousePanel == UIPanel.MOUSE_PRODUCTION_OPENCLOSE);
			if (checkBlinkProduction) {
				UtilsGL.unsetColor ();
			}
			UtilsGL.glEnd ();
		}
	}

	public static boolean isMouseCloseToOpenCloseProductionIcon (int x, int y) {
		return UIPanel.isMouseCloseToIcon (x, y, tileOpenCloseProductionPanelPoint, tileOpenProductionPanel, UIPanel.CLOSE_PIXELS);
	}

	public static boolean isMouseOnProductionPanel (int x, int y) {
		return ((y >= productionPanelPoint.y && y < (productionPanelPoint.y + PRODUCTION_PANEL_HEIGHT)) && (x >= productionPanelPoint.x && x < (productionPanelPoint.x + PRODUCTION_PANEL_WIDTH)));
	}

	public static Point isMouseOnProductionItems (int x, int y) {
		if (y >= productionPanelPoint.y && y < (productionPanelPoint.y + PRODUCTION_PANEL_HEIGHT) && x >= productionPanelPoint.x && x < (productionPanelPoint.x + PRODUCTION_PANEL_WIDTH)) {
			Point point;
			bucle1: for (int y1 = 0; y1 < PRODUCTION_PANEL_NUM_ITEMS_Y; y1++) {
				for (int x1 = 0; x1 < PRODUCTION_PANEL_NUM_ITEMS_X; x1++) {
					int i = (y1 * PRODUCTION_PANEL_NUM_ITEMS_X) + x1;
					if (i >= productionPanelMenu.getItems ().size ()) {
						break bucle1;
					}
					point = productionPanelItemsPosition.get (i);
					if (x >= point.x && x < (point.x + PRODUCTION_PANEL_ITEM_WIDTH) && y >= point.y && y < (point.y + PRODUCTION_PANEL_ITEM_HEIGHT)) {
						if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
							MOUSE_PRODUCTION_PANEL_ITEMS_POINT.y = i;
							return MOUSE_PRODUCTION_PANEL_ITEMS_POINT;
						}
					}
					point = productionPanelItemsPlusRegularPosition.get (i);
					if (point.x != -1) {
						if (x >= point.x && x < (point.x + UIPanel.ICON_WIDTH) && y >= point.y && y < (point.y + UIPanel.ICON_HEIGHT)) {
							if (!tileProductionPanelPlusIconAlpha[x - point.x][y - point.y]) {
								MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR_POINT.y = i;
								return MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR_POINT;
							}
						}
						point = productionPanelItemsMinusRegularPosition.get (i);
						if (x >= point.x && x < (point.x + UIPanel.ICON_WIDTH) && y >= point.y && y < (point.y + UIPanel.ICON_HEIGHT)) {
							if (!tileProductionPanelMinusIconAlpha[x - point.x][y - point.y]) {
								MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR_POINT.y = i;
								return MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR_POINT;
							}
						}
						point = productionPanelItemsPlusAutomatedPosition.get (i);
						if (x >= point.x && x < (point.x + UIPanel.ICON_WIDTH) && y >= point.y && y < (point.y + UIPanel.ICON_HEIGHT)) {
							if (!tileProductionPanelPlusIconAlpha[x - point.x][y - point.y]) {
								MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED_POINT.y = i;
								return MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED_POINT;
							}
						}
						point = productionPanelItemsMinusAutomatedPosition.get (i);
						if (x >= point.x && x < (point.x + UIPanel.ICON_WIDTH) && y >= point.y && y < (point.y + UIPanel.ICON_HEIGHT)) {
							if (!tileProductionPanelMinusIconAlpha[x - point.x][y - point.y]) {
								MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED_POINT.y = i;
								return MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED_POINT;
							}
						}
					}
				}
			}
		}

		return null;
	}

	public static void setProductionPanelActive (boolean productionPanelActive) {
		ProductionUIPanel.productionPanelActive = productionPanelActive;

		ImagesPanel.resize (UIPanel.renderWidth, UIPanel.renderHeight);
	}

	public static boolean isProductionPanelActive () {
		return productionPanelActive;
	}

	public static void setProductionPanelLocked (boolean productionPanelLocked) {
		ProductionUIPanel.productionPanelLocked = productionPanelLocked;
	}

	public static boolean isProductionPanelLocked () {
		return productionPanelLocked;
	}

	public static void createProductionPanel (SmartMenu menu) {
		int iFirstY = UIPanel.iconNumCitizensBackgroundPoint.y + UIPanel.tileBottomItem.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 4;
		int iLastY;
		if (BottomMenuUIPanel.bottomSubPanelMenu != null && BottomMenuUIPanel.isBottomMenuPanelActive ()) {
			iLastY = BottomMenuUIPanel.bottomSubPanelPoint.y;
		} else {
			iLastY = BottomMenuUIPanel.bottomPanelY;
		}
		PRODUCTION_PANEL_HEIGHT = iLastY - iFirstY - UIPanel.PIXELS_TO_BORDER * 2;

		PRODUCTION_PANEL_WIDTH = RightMenuUIPanel.menuPanelPoint.x - UIPanel.PIXELS_TO_BORDER * 4;

		PRODUCTION_PANEL_NUM_ITEMS_Y = (PRODUCTION_PANEL_HEIGHT - UIPanel.PIXELS_TO_BORDER) / (PRODUCTION_PANEL_ITEM_HEIGHT + UIPanel.PIXELS_TO_BORDER);
		if (PRODUCTION_PANEL_NUM_ITEMS_Y < 1) {
			PRODUCTION_PANEL_NUM_ITEMS_Y = 1;
		}
		PRODUCTION_PANEL_HEIGHT = PRODUCTION_PANEL_NUM_ITEMS_Y * (PRODUCTION_PANEL_ITEM_HEIGHT + UIPanel.PIXELS_TO_BORDER) + UIPanel.PIXELS_TO_BORDER;

		int iMaxItems = menu.getItems ().size ();
		PRODUCTION_PANEL_NUM_ITEMS_X = (iMaxItems / PRODUCTION_PANEL_NUM_ITEMS_Y);
		if ((iMaxItems % PRODUCTION_PANEL_NUM_ITEMS_Y) != 0) {
			PRODUCTION_PANEL_NUM_ITEMS_X++;
		}
		PRODUCTION_PANEL_WIDTH = PRODUCTION_PANEL_NUM_ITEMS_X * (PRODUCTION_PANEL_ITEM_WIDTH + UIPanel.PIXELS_TO_BORDER + 2 * UIPanel.ICON_WIDTH) + UIPanel.PIXELS_TO_BORDER;

		while (((PRODUCTION_PANEL_NUM_ITEMS_Y - 1) * PRODUCTION_PANEL_NUM_ITEMS_X) >= iMaxItems) {
			PRODUCTION_PANEL_HEIGHT -= (PRODUCTION_PANEL_ITEM_HEIGHT + UIPanel.PIXELS_TO_BORDER);
			PRODUCTION_PANEL_NUM_ITEMS_Y--;
		}

		productionPanelPoint.setLocation (tileOpenProductionPanel.getTileWidth (), iFirstY + ((iLastY - iFirstY) / 2) - PRODUCTION_PANEL_HEIGHT / 2);

		// Positions
		productionPanelItemsPosition.clear ();
		productionPanelItemsPlusRegularPosition.clear ();
		productionPanelItemsMinusRegularPosition.clear ();
		productionPanelItemsPlusAutomatedPosition.clear ();
		productionPanelItemsMinusAutomatedPosition.clear ();
		Point p;
		SmartMenu smItem;
		int iMenu;
		bucle1: for (int y = 0; y < PRODUCTION_PANEL_NUM_ITEMS_Y; y++) {
			for (int x = 0; x < PRODUCTION_PANEL_NUM_ITEMS_X; x++) {
				iMenu = (y * PRODUCTION_PANEL_NUM_ITEMS_X) + x;
				if (iMenu >= productionPanelMenu.getItems ().size ()) {
					break bucle1;
				}
				smItem = productionPanelMenu.getItems ().get (iMenu);

				p = new Point (productionPanelPoint.x + UIPanel.PIXELS_TO_BORDER + UIPanel.ICON_WIDTH + (x * (PRODUCTION_PANEL_ITEM_WIDTH + UIPanel.PIXELS_TO_BORDER + 2 * UIPanel.ICON_WIDTH)), productionPanelPoint.y + UIPanel.PIXELS_TO_BORDER + (y * (PRODUCTION_PANEL_ITEM_HEIGHT + UIPanel.PIXELS_TO_BORDER)));
				productionPanelItemsPosition.add (p);
				if (smItem.getType () == SmartMenu.TYPE_ITEM && !smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
					productionPanelItemsPlusRegularPosition.add (new Point (p.x - UIPanel.ICON_WIDTH, p.y));
					productionPanelItemsMinusRegularPosition.add (new Point (p.x - UIPanel.ICON_WIDTH, p.y + PRODUCTION_PANEL_ITEM_HEIGHT - UIPanel.ICON_HEIGHT));
					productionPanelItemsPlusAutomatedPosition.add (new Point (p.x + PRODUCTION_PANEL_ITEM_WIDTH, p.y));
					productionPanelItemsMinusAutomatedPosition.add (new Point (p.x + PRODUCTION_PANEL_ITEM_WIDTH, p.y + PRODUCTION_PANEL_ITEM_HEIGHT - UIPanel.ICON_HEIGHT));
				} else {
					productionPanelItemsPlusRegularPosition.add (new Point (-1, -1));
					productionPanelItemsMinusRegularPosition.add (new Point (-1, -1));
					productionPanelItemsPlusAutomatedPosition.add (new Point (-1, -1));
					productionPanelItemsMinusAutomatedPosition.add (new Point (-1, -1));
				}
			}
		}

		// Minibotón para abrir/cerrar el menú de producción
		tileOpenCloseProductionPanelPoint.setLocation (0, UIPanel.renderHeight / 2 - tileOpenProductionPanel.getTileHeight () / 2);


		// Tutorial?
		ImagesPanel.resize (UIPanel.renderWidth, UIPanel.renderHeight);
	}
}
