package xaos.panels;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import xaos.compat.input.Keyboard;
import xaos.compat.input.Mouse;
import org.lwjgl.opengl.GL11;

import xaos.TownsProperties;
import xaos.actions.ActionManager;
import xaos.actions.ActionManagerItem;
import xaos.actions.ActionPriorityManager;
import xaos.campaign.TutorialFlow;
import xaos.campaign.TutorialTrigger;
import xaos.data.CaravanData;
import xaos.data.CitizenGroupData;
import xaos.data.CitizenGroups;
import xaos.data.EffectData;
import xaos.data.EquippedData;
import xaos.data.EventData;
import xaos.data.GlobalEventData;
import xaos.data.HeroData;
import xaos.data.SoldierData;
import xaos.data.SoldierGroupData;
import xaos.data.SoldierGroups;
import xaos.effects.EffectManager;
import xaos.events.EventManager;
import xaos.events.EventManagerItem;
import xaos.main.Game;
import xaos.main.World;
import xaos.panels.menus.ContextMenu;
import xaos.panels.menus.SmartMenu;
import xaos.stockpiles.Stockpile;
import xaos.tasks.Task;
import xaos.tiles.Tile;
import xaos.tiles.entities.items.Container;
import xaos.tiles.entities.items.ItemManager;
import xaos.tiles.entities.items.ItemManagerItem;
import xaos.tiles.entities.items.military.MilitaryItem;
import xaos.tiles.entities.living.Citizen;
import xaos.tiles.entities.living.LivingEntity;
import xaos.tiles.entities.living.heroes.Hero;
import xaos.utils.ColorGL;
import xaos.utils.Messages;
import xaos.utils.Point3D;
import xaos.utils.UtilFont;
import xaos.utils.UtilsAL;
import xaos.utils.UtilsGL;
import xaos.utils.UtilsKeyboard;


public final class UIPanel {

	public static int PIXELS_TO_BORDER = 16;
	public static final int CLOSE_PIXELS = 20;
	public static final int MAX_BLINK_TURNS = Game.FPS_INGAME;

	public static Tile BACK_TILE = new Tile ("ui_back"); //$NON-NLS-1$
	public static Tile BLACK_TILE = new Tile ("ui_black"); //$NON-NLS-1$
	public static Tile BIG_RED_CROSS_TILE = new Tile ("iconredcross"); //$NON-NLS-1$
	public static Tile ENABLE_ALL_TILE = new Tile ("iconenableall"); //$NON-NLS-1$
	public static Tile DISABLE_ALL_TILE = new Tile ("icondisableall"); //$NON-NLS-1$

	public final static int MOUSE_NONE = 0;
	public final static int MOUSE_BOTTOM_PANEL = 1;
	public final static int MOUSE_BOTTOM_LEFT_SCROLL = 2;
	public final static int MOUSE_BOTTOM_RIGHT_SCROLL = 3;
	public final static int MOUSE_BOTTOM_ITEMS = 4;
	public final static int MOUSE_BOTTOM_SUBPANEL = 5;
	public final static int MOUSE_BOTTOM_SUBITEMS = 6;
	public final static int MOUSE_BOTTOM_OPENCLOSE = 7;
	public final static int MOUSE_MINIMAP = 10;
	public final static int MOUSE_MESSAGES_PANEL = 20;
	public final static int MOUSE_MESSAGES_ICON_COMBAT = 21;
	public final static int MOUSE_MESSAGES_ICON_SYSTEM = 22;
	public final static int MOUSE_MESSAGES_ICON_ANNOUNCEMENT = 23;
	public final static int MOUSE_MESSAGES_ICON_HEROES = 24;
	public final static int MOUSE_MESSAGES_PANEL_BUTTONS_CLOSE = 25;
	public final static int MOUSE_MESSAGES_PANEL_BUTTONS_ANNOUNCEMENT = 26;
	public final static int MOUSE_MESSAGES_PANEL_BUTTONS_COMBAT = 27;
	public final static int MOUSE_MESSAGES_PANEL_BUTTONS_HEROES = 28;
	public final static int MOUSE_MESSAGES_PANEL_BUTTONS_SYSTEM = 29;
	public final static int MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_UP = 30;
	public final static int MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_DOWN = 31;
	public final static int MOUSE_MENU_PANEL = 35;
	public final static int MOUSE_MENU_PANEL_ITEMS = 36;
	public final static int MOUSE_MENU_OPENCLOSE = 37;
	public final static int MOUSE_ICON_LEVEL_UP = 40;
	public final static int MOUSE_ICON_LEVEL_DOWN = 41;
	public final static int MOUSE_ICON_CITIZEN_NEXT = 42;
	public final static int MOUSE_ICON_CITIZEN_PREVIOUS = 43;
	public final static int MOUSE_ICON_SOLDIER_NEXT = 44;
	public final static int MOUSE_ICON_SOLDIER_PREVIOUS = 45;
	public final static int MOUSE_ICON_LEVEL = 46;
	public final static int MOUSE_ICON_HERO_NEXT = 47;
	public final static int MOUSE_ICON_HERO_PREVIOUS = 48;
	// public final static int MOUSE_INFO_CURRENT_LEVEL = 50;
	public final static int MOUSE_INFO_NUM_CITIZENS = 51;
	public final static int MOUSE_INFO_NUM_SOLDIERS = 52;
	public final static int MOUSE_INFO_NUM_HEROES = 53;
	public final static int MOUSE_INFO_CARAVAN = 54;
	public final static int MOUSE_INFOPANEL = 60;
	public final static int MOUSE_DATEPANEL = 61;
	public final static int MOUSE_PRODUCTION_PANEL = 65;
	public final static int MOUSE_PRODUCTION_PANEL_ITEMS = 66;
	// public final static int MOUSE_PRODUCTION_PANEL_ICON = 67;
	public final static int MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR = 68;
	public final static int MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR = 69;
	public final static int MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED = 70;
	public final static int MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED = 71;
	public final static int MOUSE_PRODUCTION_OPENCLOSE = 72;
	public final static int MOUSE_ICON_PRIORITIES = 77;
	public final static int MOUSE_ICON_PAUSE_RESUME = 78;
	public final static int MOUSE_ICON_SETTINGS = 79;
	public final static int MOUSE_ICON_GRID = 80;
	public final static int MOUSE_PRIORITIES_PANEL = 81;
	public final static int MOUSE_PRIORITIES_PANEL_ITEMS = 82;
	public final static int MOUSE_PRIORITIES_PANEL_ITEMS_UP = 83;
	public final static int MOUSE_PRIORITIES_PANEL_ITEMS_DOWN = 84;
	public final static int MOUSE_ICON_INCREASE_SPEED = 85;
	public final static int MOUSE_ICON_LOWER_SPEED = 86;
	public final static int MOUSE_ICON_MINIBLOCKS = 87;
	public final static int MOUSE_ICON_FLATMOUSE = 88;
	public final static int MOUSE_ICON_3DMOUSE = 89;
	public final static int MOUSE_TRADE_PANEL = 90;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_CARAVAN = 91;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_DOWN_CARAVAN = 92;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_UP_CARAVAN = 93;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN = 94;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_DOWN_CARAVAN = 95;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_UP_CARAVAN = 96;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_CONFIRM = 97;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_TOWN = 98;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_DOWN_TOWN = 99;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_UP_TOWN = 100;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN = 101;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_DOWN_TOWN = 102;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_UP_TOWN = 103;
	public final static int MOUSE_TRADE_PANEL_BUTTONS_CLOSE = 104;
	public final static int MOUSE_TRADE_PANEL_ICON_BUY = 105;
	public final static int MOUSE_TRADE_PANEL_ICON_SELL = 106;

	public final static int MOUSE_EVENTS_ICON = 107;
	// public final static int MOUSE_GODS_ICON = 108;
	public final static int MOUSE_TUTORIAL_ICON = 109;

	public final static int MOUSE_ICON_MATS = 120;
	public final static int MOUSE_MATS_PANEL = 121;
	public final static int MOUSE_MATS_PANEL_BUTTONS_CLOSE = 122;
	public final static int MOUSE_MATS_PANEL_BUTTONS_GROUPS = 123;
	public final static int MOUSE_MATS_PANEL_BUTTONS_ITEMS = 124;
	public final static int MOUSE_MATS_PANEL_BUTTONS_SCROLL_UP = 125;
	public final static int MOUSE_MATS_PANEL_BUTTONS_SCROLL_DOWN = 126;

	public final static int MOUSE_LIVINGS_PANEL = 140;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_CLOSE = 141;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS = 142;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_UP = 143;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_DOWN = 144;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD = 145;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY = 146;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS = 147;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET = 148;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON = 149;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP = 150;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER = 151;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN = 152;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD = 153;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL = 154;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS = 155;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD = 156;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE = 157;
	public final static int MOUSE_LIVINGS_PANEL_SGROUP_NOGROUP = 158;
	public final static int MOUSE_LIVINGS_PANEL_SGROUP_GROUP = 159;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_RENAME = 160;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_GUARD = 161;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_PATROL = 162;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_BOSS = 163;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_AUTOEQUIP = 164;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_DISBAND = 165;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS = 166;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE = 167;
	public final static int MOUSE_LIVINGS_PANEL_CGROUP_NOGROUP = 168;
	public final static int MOUSE_LIVINGS_PANEL_CGROUP_GROUP = 169;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_RENAME = 170;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_AUTOEQUIP = 171;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_DISBAND = 172;
	public final static int MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_CHANGE_JOBS = 173;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP = 174;
	public final static int MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN = 175;

	public final static int MOUSE_TYPING_PANEL = 180;
	public final static int MOUSE_TYPING_PANEL_CLOSE = 181;
	public final static int MOUSE_TYPING_PANEL_CONFIRM = 182;

	public final static int MOUSE_PILE_PANEL = 185;
	public final static int MOUSE_PILE_PANEL_BUTTONS_CLOSE = 186;
	public final static int MOUSE_PILE_PANEL_BUTTONS_ITEMS = 187;
	public final static int MOUSE_PILE_PANEL_BUTTONS_SCROLL_UP = 188;
	public final static int MOUSE_PILE_PANEL_BUTTONS_SCROLL_DOWN = 189;
	public final static int MOUSE_PILE_PANEL_BUTTONS_CONFIG_COPY = 190;
	public final static int MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK = 191;
	public final static int MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_ALL = 192;
	public final static int MOUSE_PILE_PANEL_BUTTONS_CONFIG_UNLOCK_ALL = 193;

	public final static int MOUSE_PROFESSIONS_PANEL = 195;
	public final static int MOUSE_PROFESSIONS_PANEL_BUTTONS_CLOSE = 196;
	public final static int MOUSE_PROFESSIONS_PANEL_BUTTONS_ITEMS = 197;
	public final static int MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_UP = 198;
	public final static int MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_DOWN = 199;

	public final static int MOUSE_IMAGES_PANEL = 200;
	public final static int MOUSE_IMAGES_PANEL_CLOSE = 201;
	public final static int MOUSE_IMAGES_PANEL_PREVIOUS = 202;
	public final static int MOUSE_IMAGES_PANEL_NEXT = 203;
	public final static int MOUSE_IMAGES_PANEL_NEXT_MISSION = 204;

	/*
	 * BOTTOM PANEL
	 */
	public final static int BOTTOM_ITEM_WIDTH = 64;
	public final static int BOTTOM_ITEM_HEIGHT = 64;

	/*
	 * PRODUCTION PANEL
	 */

	/*
	 * TRADE PANEL
	 */

	/*
	 * PRIORITIES PANEL
	 */
	public static int PRIORITIES_PANEL_ICON_WIDTH;
	public static int PRIORITIES_PANEL_ICON_HEIGHT;

	/*
	 * MATS PANEL
	 */

	/*
	 * PILE PANEL
	 */

	/*
	 * PROFESSIONS PANEL
	 */

	/*
	 * LIVINGS PANEL
	 */

	/*
	 * MINIMAP
	 */
	private static int MINIMAP_PANEL_WIDTH = World.MAP_WIDTH;
	private static int MINIMAP_PANEL_HEIGHT = World.MAP_HEIGHT;

	/*
	 * MESSAGES PANEL
	 */

	/*
	 * MENU PANEL
	 */

	/*
	 * MINI-ICONS
	 */
	public final static int ICON_WIDTH = 32;
	public final static int ICON_HEIGHT = 32;

	private static int delayTime;
	public static int blinkTurns;

	public static int renderWidth;
	public static int renderHeight;

	public static Tile tileBottomItem;
	public static Tile tileBottomItemSM;
	public static boolean tileBottomItemAlpha[][];

	// Open/close bottom
	private static boolean tileOpenBottomMenuAlpha[][];
	private static Tile tileOpenBottomMenuON;
	private static boolean tileOpenBottomMenuONAlpha[][];

	// BOTTOM subpanel
	private static Tile tileBottomSubItem;

	// MINIMAP panel
	private int minimapPanelX;
	private int minimapPanelY;

	private static Tile tileMinimapPanel;
	private static boolean tileMinimapPanelAlpha[][];

	// Open/close production
	private static boolean tileOpenProductionPanelAlpha[][];
	private static boolean tileOpenProductionPanelONAlpha[][];

	// TRADE panel
	public static Tile tileScrollUp = new Tile ("scrollup"); //$NON-NLS-1$
	public static Tile tileScrollUpDisabled = new Tile ("scrollup_disabled"); //$NON-NLS-1$
	public static final boolean tileScrollUpButtonAlpha[][] = UtilsGL.generateAlpha (tileScrollUp);
	public static Tile tileScrollDown = new Tile ("scrolldown"); //$NON-NLS-1$
	public static Tile tileScrollDownDisabled = new Tile ("scrolldown_disabled"); //$NON-NLS-1$
	public static final boolean tileScrollDownButtonAlpha[][] = UtilsGL.generateAlpha (tileScrollDown);

	// PILE panel
	public static Tile tileConfigCopy = new Tile ("configcopy"); //$NON-NLS-1$
	public static final boolean tileConfigCopyButtonAlpha[][] = UtilsGL.generateAlpha (tileConfigCopy);
	public static Tile tileConfigLock = new Tile ("configlock"); //$NON-NLS-1$
	public static final boolean tileConfigLockButtonAlpha[][] = UtilsGL.generateAlpha (tileConfigLock);
	public static Tile tileConfigLockLocked = new Tile ("configlocklocked"); //$NON-NLS-1$
	public static final boolean tileConfigLockLockedButtonAlpha[][] = UtilsGL.generateAlpha (tileConfigLockLocked);
	public static Tile tileConfigLockAll = new Tile ("configlockall"); //$NON-NLS-1$
	public static final boolean tileConfigLockAllButtonAlpha[][] = UtilsGL.generateAlpha (tileConfigLockAll);
	public static Tile tileConfigUnlockAll = new Tile ("configunlockall"); //$NON-NLS-1$
	public static final boolean tileConfigUnlockAllButtonAlpha[][] = UtilsGL.generateAlpha (tileConfigUnlockAll);

	// Open/close right menu
	private static boolean tileOpenRightMenuAlpha[][];
	private static boolean tileOpenRightMenuONAlpha[][];

	// Close panels
	public static Tile tileButtonClose = new Tile ("panel_close"); //$NON-NLS-1$
	public static Tile tileButtonCloseDisabled = new Tile ("panel_close_disabled"); //$NON-NLS-1$
	public static final boolean tileButtonCloseAlpha[][] = UtilsGL.generateAlpha (tileButtonClose);

	// MINI-ICONS
	private static boolean tileIconNextMiniAlpha[][];
	private static boolean tileIconPreviousMiniAlpha[][];
	private static Point iconLevelUpPoint = new Point (0, 0);
	public static Tile tileIconLevelUp;
	private static Point iconLevelDownPoint = new Point (0, 0);
	public static Tile tileIconLevelDown;
	private static Point iconLevelPoint = new Point (0, 0);
	private static Tile tileIconLevel;
	private static boolean tileIconLevelAlpha[][];

	// ICONS
	private static Tile tileIconPriorities;
	private static Tile tileIconPrioritiesON;
	private static boolean tileIconPrioritiesAlpha[][];
	private static Point iconPrioritiesPoint = new Point (0, 0);
	public static Tile tileIconMats;
	private static Tile tileIconMatsON;
	private static boolean tileIconMatsAlpha[][];
	public static Point iconMatsPoint = new Point (0, 0);
	private static Tile tileIconPause;
	private static boolean tileIconPauseResumeAlpha[][];
	private static Tile tileIconResume;
	private static Point iconPauseResumePoint = new Point (0, 0);
	private static Tile tileIconIncreaseSpeed;
	private static Tile tileIconIncreaseSpeedON;
	private static boolean tileIconIncreaseSpeedAlpha[][];
	private static Point iconIncreaseSpeedPoint = new Point (0, 0);
	private static Tile tileIconLowerSpeed;
	private static Tile tileIconLowerSpeedON;
	private static boolean tileIconLowerSpeedAlpha[][];
	private static Point iconLowerSpeedPoint = new Point (0, 0);
	private static Tile tileIconSettings;
	private static boolean tileIconSettingsAlpha[][];
	private static Point iconSettingsPoint = new Point (0, 0);
	private static Tile tileIconGrid;
	private static Tile tileIconGridON;
	private static boolean tileIconGridAlpha[][];
	private static Point iconGridPoint = new Point (0, 0);
	private static Tile tileIconMiniblocks;
	private static Tile tileIconMiniblocksON;
	private static boolean tileIconMiniblocksAlpha[][];
	private static Point iconMiniblocksPoint = new Point (0, 0);
	private static Tile tileIconFlatMouse;
	private static Tile tileIconFlatMouseON;
	private static boolean tileIconFlatMouseAlpha[][];
	private static Point iconFlatMousePoint = new Point (0, 0);
	private static Tile tileIcon3DMouse;
	private static Tile tileIcon3DMouseON;
	private static boolean tileIcon3DMouseAlpha[][];
	private static Point icon3DMousePoint = new Point (0, 0);

	private static Point iconEventsPoint = new Point (0, 0);
	// private static Point iconGodsPoint = new Point (0, 0);
	// private static Tile tileIconGods;
	private static Point iconTutorialPoint = new Point (0, 0);
	public static Tile tileIconTutorial;

	// INFO
	private static Tile tileIconNumCitizens;
	public static Point iconNumCitizensBackgroundPoint = new Point (0, 0);
	private static Point iconNumCitizensPoint = new Point (0, 0);
	private static Point iconCitizenNextPoint = new Point (0, 0);
	private static Tile tileIconCitizenNext;
	private static Tile tileIconCitizenNextON;
	private static Point iconCitizenPreviousPoint = new Point (0, 0);
	private static Tile tileIconCitizenPrevious;
	private static Tile tileIconCitizenPreviousON;

	private static Tile tileIconNumSoldiers;
	private static Point iconNumSoldiersBackgroundPoint = new Point (0, 0);
	private static Point iconNumSoldiersPoint = new Point (0, 0);
	private static Point iconSoldierNextPoint = new Point (0, 0);
	private static Tile tileIconSoldierNext;
	private static Tile tileIconSoldierNextON;
	private static Point iconSoldierPreviousPoint = new Point (0, 0);
	private static Tile tileIconSoldierPrevious;
	private static Tile tileIconSoldierPreviousON;

	private static Tile tileIconNumHeroes;
	private static Point iconNumHeroesBackgroundPoint = new Point (0, 0);
	private static Point iconNumHeroesPoint = new Point (0, 0);
	private static Point iconHeroNextPoint = new Point (0, 0);
	private static Tile tileIconHeroNext;
	private static Tile tileIconHeroNextON;
	private static Point iconHeroPreviousPoint = new Point (0, 0);
	private static Tile tileIconHeroPrevious;
	private static Tile tileIconHeroPreviousON;

	private static Tile tileIconCaravan;
	private static Tile tileIconCaravanON;
	private static Point iconCaravanBackgroundPoint = new Point (0, 0);
	private static Point iconCaravanPoint = new Point (0, 0);

	private static Tile tileInfoPanel;
	private static boolean tileInfoPanelAlpha[][];
	private static Point infoPanelPoint = new Point (0, 0);

	private static Tile tileDatePanel;
	private static boolean tileDatePanelAlpha[][];
	private static Point datePanelPoint = new Point (0, 0);

	private static Tile tileIconCoins;
	private static Point tileIconCoinsPoint = new Point (0, 0);

	/*
	 * TOOLTIP
	 */
	public static Tile tileTooltipBackground;

	/*
	 * Typing panel
	 */
	public static TypingPanel typingPanel = null;

	/*
	 * Images panel
	 */
	public static ImagesPanel imagesPanel = null;

	
	public UIPanel () {
		if (Game.getWorld () != null) {
			resize (UtilsGL.getWidth (), UtilsGL.getHeight (), Game.getWorld ().getCampaignID (), Game.getWorld ().getMissionID (), false);
		} else {
			resize (UtilsGL.getWidth (), UtilsGL.getHeight (), null, null, false);
		}
	}

	private void loadMenus (String sCampaignID, String sMissionID) {
		BottomMenuUIPanel.currentMenu = new SmartMenu ();
		SmartMenu.readXMLMenu (BottomMenuUIPanel.currentMenu, "menu.xml", sCampaignID, sMissionID); //$NON-NLS-1$

		RightMenuUIPanel.menuPanelMenu = new SmartMenu ();
		SmartMenu.readXMLMenu (RightMenuUIPanel.menuPanelMenu, "menu_right.xml", sCampaignID, sMissionID); //$NON-NLS-1$

		ProductionUIPanel.productionPanelMenu = new SmartMenu ();
		SmartMenu.readXMLMenu (ProductionUIPanel.productionPanelMenu, "menu_production.xml", sCampaignID, sMissionID); //$NON-NLS-1$

		// Vamos a setear los tamaños de los iconos de los menús para que sea proporcional al botón de menú
		resizeIcons (BottomMenuUIPanel.currentMenu, BOTTOM_ITEM_WIDTH, BOTTOM_ITEM_HEIGHT);
		resizeIcons (RightMenuUIPanel.menuPanelMenu, RightMenuUIPanel.MENU_ITEM_WIDTH, RightMenuUIPanel.MENU_ITEM_HEIGHT);
		resizeIcons (ProductionUIPanel.productionPanelMenu, ProductionUIPanel.PRODUCTION_PANEL_ITEM_WIDTH, ProductionUIPanel.PRODUCTION_PANEL_ITEM_HEIGHT);
	}

	public static void resizeIcons (SmartMenu menu, int width, int height) {
		if (menu.getItems () != null) {
			for (int i = 0; i < menu.getItems ().size (); i++) {
				resizeIcons (menu.getItems ().get (i), width, height);
			}

			if (menu.getIcon () != null) {
				Tile tile = menu.getIcon ();
				if (tile.getTileWidth () > width || tile.getTileHeight () > height) {
					float relation = (float) tile.getTileWidth () / (float) tile.getTileHeight ();

					if (tile.getTileWidth () > tile.getTileHeight ()) {
						tile.setTileWidth (width);
						tile.setTileHeight ((int) (width / relation));
					} else {
						tile.setTileHeight (height);
						tile.setTileWidth ((int) (height * relation));
					}
				}
			}
		}
	}

	public void resize (int renderW, int renderH, String sCampaignID, String sMissionID, boolean bLoadMenus) {
		renderWidth = renderW;
		renderHeight = renderH;

		initialize (sCampaignID, sMissionID, bLoadMenus);
	}


	public SmartMenu getCurrentMenu () {
		return BottomMenuUIPanel.currentMenu;
	}

	/**
	 * Genera los tiles de la UI con su alpha y tal
	 */
	private static void generateTiles () {
		/*
		 * BOTTOM
		 */
		tileBottomItem = new Tile ("bottom_item"); //$NON-NLS-1$
		tileBottomItemSM = new Tile ("bottom_item_sm"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomScrollLeft = new Tile ("bottom_scr_left"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomScrollLeftON = new Tile ("bottom_scr_leftON"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomScrollRight = new Tile ("bottom_scr_right"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomScrollRightON = new Tile ("bottom_scr_rightON"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomPanel = new Tile ("bottom_panel"); //$NON-NLS-1$

		tileBottomItemAlpha = UtilsGL.generateAlpha (tileBottomItem);
		BottomMenuUIPanel.tileBottomScrollLeftAlpha = UtilsGL.generateAlpha (BottomMenuUIPanel.tileBottomScrollLeft);
		BottomMenuUIPanel.tileBottomScrollRightAlpha = UtilsGL.generateAlpha (BottomMenuUIPanel.tileBottomScrollRight);
		BottomMenuUIPanel.tileBottomPanelAlpha = UtilsGL.generateAlpha (BottomMenuUIPanel.tileBottomPanel, BottomMenuUIPanel.BOTTOM_PANEL_WIDTH, BottomMenuUIPanel.BOTTOM_PANEL_HEIGHT);

		tileBottomSubItem = new Tile ("bottom_subitem"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		BottomMenuUIPanel.tileBottomSubPanel[0] = new Tile ("bottom_subpanel"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[1] = new Tile ("bottom_subpanel_N"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[2] = new Tile ("bottom_subpanel_S"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[3] = new Tile ("bottom_subpanel_E"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[4] = new Tile ("bottom_subpanel_W"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[5] = new Tile ("bottom_subpanel_NE"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[6] = new Tile ("bottom_subpanel_NW"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[7] = new Tile ("bottom_subpanel_SE"); //$NON-NLS-1$
		BottomMenuUIPanel.tileBottomSubPanel[8] = new Tile ("bottom_subpanel_SW"); //$NON-NLS-1$

		BottomMenuUIPanel.tileBottomSubItemAlpha = UtilsGL.generateAlpha (tileBottomSubItem);

		BottomMenuUIPanel.tileOpenBottomMenu = new Tile ("icon_openBottom"); //$NON-NLS-1$
		tileOpenBottomMenuAlpha = UtilsGL.generateAlpha (BottomMenuUIPanel.tileOpenBottomMenu);
		tileOpenBottomMenuON = new Tile ("icon_openBottomON"); //$NON-NLS-1$
		tileOpenBottomMenuONAlpha = UtilsGL.generateAlpha (tileOpenBottomMenuON);

		/*
		 * MINIMAP
		 */
		tileMinimapPanel = new Tile ("minimap_panel"); //$NON-NLS-1$
		tileMinimapPanelAlpha = UtilsGL.generateAlpha (tileMinimapPanel);

		/*
		 * MESSAGES
		 */
		MessagesUIPanel.tileMessagesPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		MessagesUIPanel.tileMessagesPanel[0] = new Tile ("messages_panel"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[1] = new Tile ("messages_panel_N"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[2] = new Tile ("messages_panel_S"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[3] = new Tile ("messages_panel_E"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[4] = new Tile ("messages_panel_W"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[5] = new Tile ("messages_panel_NE"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[6] = new Tile ("messages_panel_NW"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[7] = new Tile ("messages_panel_SE"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanel[8] = new Tile ("messages_panel_SW"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		MessagesUIPanel.tileMessagesPanelSubPanel[0] = new Tile ("messages_subpanel"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[1] = new Tile ("messages_subpanel_N"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[2] = new Tile ("messages_subpanel_S"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[3] = new Tile ("messages_subpanel_E"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[4] = new Tile ("messages_subpanel_W"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[5] = new Tile ("messages_subpanel_NE"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[6] = new Tile ("messages_subpanel_NW"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[7] = new Tile ("messages_subpanel_SE"); //$NON-NLS-1$
		MessagesUIPanel.tileMessagesPanelSubPanel[8] = new Tile ("messages_subpanel_SW"); //$NON-NLS-1$

		/*
		 * PRODUCTION PANEL
		 */
		ProductionUIPanel.tileProductionPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		ProductionUIPanel.tileProductionPanel[0] = new Tile ("production_panel"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[1] = new Tile ("production_panel_N"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[2] = new Tile ("production_panel_S"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[3] = new Tile ("production_panel_E"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[4] = new Tile ("production_panel_W"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[5] = new Tile ("production_panel_NE"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[6] = new Tile ("production_panel_NW"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[7] = new Tile ("production_panel_SE"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanel[8] = new Tile ("production_panel_SW"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanelPlusIcon = new Tile ("production_panel_plus_icon"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanelPlusIconAlpha = UtilsGL.generateAlpha (ProductionUIPanel.tileProductionPanelPlusIcon);
		ProductionUIPanel.tileProductionPanelMinusIcon = new Tile ("production_panel_minus_icon"); //$NON-NLS-1$
		ProductionUIPanel.tileProductionPanelMinusIconAlpha = UtilsGL.generateAlpha (ProductionUIPanel.tileProductionPanelMinusIcon);

		ProductionUIPanel.tileOpenProductionPanel = new Tile ("icon_openLeft"); //$NON-NLS-1$
		tileOpenProductionPanelAlpha = UtilsGL.generateAlpha (ProductionUIPanel.tileOpenProductionPanel);
		ProductionUIPanel.tileOpenProductionPanelON = new Tile ("icon_openLeftON"); //$NON-NLS-1$
		tileOpenProductionPanelONAlpha = UtilsGL.generateAlpha (ProductionUIPanel.tileOpenProductionPanelON);

		/*
		 * TRADE PANEL
		 */
		TradeUIPanel.tileTradePanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		TradeUIPanel.tileTradePanel[0] = new Tile ("trade_panel"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[1] = new Tile ("trade_panel_N"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[2] = new Tile ("trade_panel_S"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[3] = new Tile ("trade_panel_E"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[4] = new Tile ("trade_panel_W"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[5] = new Tile ("trade_panel_NE"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[6] = new Tile ("trade_panel_NW"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[7] = new Tile ("trade_panel_SE"); //$NON-NLS-1$
		TradeUIPanel.tileTradePanel[8] = new Tile ("trade_panel_SW"); //$NON-NLS-1$

		/*
		 * PRIORITIES PANEL
		 */
		PrioritiesUIPanel.tilePrioritiesPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		PrioritiesUIPanel.tilePrioritiesPanel[0] = new Tile ("priorities_panel"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[1] = new Tile ("priorities_panel_N"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[2] = new Tile ("priorities_panel_S"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[3] = new Tile ("priorities_panel_E"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[4] = new Tile ("priorities_panel_W"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[5] = new Tile ("priorities_panel_NE"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[6] = new Tile ("priorities_panel_NW"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[7] = new Tile ("priorities_panel_SE"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanel[8] = new Tile ("priorities_panel_SW"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanelUpIcon = new Tile ("priorities_up"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanelUpIconAlpha = UtilsGL.generateAlpha (PrioritiesUIPanel.tilePrioritiesPanelUpIcon);
		PrioritiesUIPanel.tilePrioritiesPanelDownIcon = new Tile ("priorities_down"); //$NON-NLS-1$
		PrioritiesUIPanel.tilePrioritiesPanelDownIconAlpha = UtilsGL.generateAlpha (PrioritiesUIPanel.tilePrioritiesPanelDownIcon);

		/*
		 * MATS PANEL
		 */
		MatsUIPanel.tileMatsPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		MatsUIPanel.tileMatsPanel[0] = new Tile ("mats_panel"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[1] = new Tile ("mats_panel_N"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[2] = new Tile ("mats_panel_S"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[3] = new Tile ("mats_panel_E"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[4] = new Tile ("mats_panel_W"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[5] = new Tile ("mats_panel_NE"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[6] = new Tile ("mats_panel_NW"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[7] = new Tile ("mats_panel_SE"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanel[8] = new Tile ("mats_panel_SW"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		MatsUIPanel.tileMatsPanelSubPanel[0] = new Tile ("mats_subpanel"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[1] = new Tile ("mats_subpanel_N"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[2] = new Tile ("mats_subpanel_S"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[3] = new Tile ("mats_subpanel_E"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[4] = new Tile ("mats_subpanel_W"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[5] = new Tile ("mats_subpanel_NE"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[6] = new Tile ("mats_subpanel_NW"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[7] = new Tile ("mats_subpanel_SE"); //$NON-NLS-1$
		MatsUIPanel.tileMatsPanelSubPanel[8] = new Tile ("mats_subpanel_SW"); //$NON-NLS-1$

		/*
		 * LIVINGS PANEL
		 */
		LivingsUIPanel.tileLivingsPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		LivingsUIPanel.tileLivingsPanel[0] = new Tile ("livings_panel"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[1] = new Tile ("livings_panel_N"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[2] = new Tile ("livings_panel_S"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[3] = new Tile ("livings_panel_E"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[4] = new Tile ("livings_panel_W"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[5] = new Tile ("livings_panel_NE"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[6] = new Tile ("livings_panel_NW"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[7] = new Tile ("livings_panel_SE"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanel[8] = new Tile ("livings_panel_SW"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		LivingsUIPanel.tileLivingsGroupPanel[0] = new Tile ("livings_group_panel"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[1] = new Tile ("livings_group_panel_N"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[2] = new Tile ("livings_group_panel_S"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[3] = new Tile ("livings_group_panel_E"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[4] = new Tile ("livings_group_panel_W"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[5] = new Tile ("livings_group_panel_NE"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[6] = new Tile ("livings_group_panel_NW"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[7] = new Tile ("livings_group_panel_SE"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupPanel[8] = new Tile ("livings_group_panel_SW"); //$NON-NLS-1$

		LivingsUIPanel.tileLivingsPanelRowNoHead = new Tile ("livings_nohead"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanelRowNoBody = new Tile ("livings_nobody"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanelRowNoLegs = new Tile ("livings_nolegs"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanelRowNoFeet = new Tile ("livings_nofeet"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsPanelRowNoWeapon = new Tile ("livings_noweapon"); //$NON-NLS-1$

		LivingsUIPanel.tileLivingsRowAutoequip = new Tile ("livings_autoequip"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowAutoequipON = new Tile ("livings_autoequipON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowAutoequipAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowAutoequip);

		LivingsUIPanel.tileLivingsRowProfession = new Tile ("livings_professions"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowProfessionON = new Tile ("livings_professionsON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowProfessionAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowProfession);
		LivingsUIPanel.tileLivingsRowJobsGroups = new Tile ("livings_jobgroup_change"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowJobsGroupsON = new Tile ("livings_jobgroup_changeON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowJobsGroupsAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowJobsGroups);
		LivingsUIPanel.tileLivingsRowConvertSoldier = new Tile ("livings_convert_soldier"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierON = new Tile ("livings_convert_soldierON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowConvertSoldier);
		LivingsUIPanel.tileLivingsRowConvertCivilian = new Tile ("livings_convert_civilian"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertCivilianON = new Tile ("livings_convert_civilianON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertCivilianAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowConvertCivilian);
		LivingsUIPanel.tileLivingsRowConvertSoldierGuard = new Tile ("livings_convert_soldier_guard"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierGuardON = new Tile ("livings_convert_soldier_guardON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierGuardAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowConvertSoldierGuard);
		LivingsUIPanel.tileLivingsRowConvertSoldierPatrol = new Tile ("livings_convert_soldier_patrol"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierPatrolON = new Tile ("livings_convert_soldier_patrolON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierPatrolAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowConvertSoldierPatrol);
		LivingsUIPanel.tileLivingsRowConvertSoldierBoss = new Tile ("livings_convert_soldier_boss"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierBossON = new Tile ("livings_convert_soldier_bossON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowConvertSoldierBossAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowConvertSoldierBoss);

		LivingsUIPanel.tileLivingsRowGroupAdd = new Tile ("livings_group_add"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowGroupAddON = new Tile ("livings_group_addON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowGroupAddAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowGroupAdd);
		LivingsUIPanel.tileLivingsRowGroupRemove = new Tile ("livings_group_remove"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowGroupRemoveON = new Tile ("livings_group_removeON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsRowGroupRemoveAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsRowGroupRemove);

		LivingsUIPanel.tileLivingsGroup = new Tile ("livings_group"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupON = new Tile ("livings_groupON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupGreen = new Tile ("livings_group_green"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsGroupAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsGroup);
		LivingsUIPanel.tileLivingsNoGroup = new Tile ("livings_nogroup"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsNoGroupON = new Tile ("livings_nogroupON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsNoGroupGreen = new Tile ("livings_nogroup_green"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsNoGroupAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsNoGroup);

		LivingsUIPanel.tileLivingsJobGroup = new Tile ("livings_jobgroup"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsJobGroupON = new Tile ("livings_jobgroupON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsJobGroupGreen = new Tile ("livings_jobgroup_green"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsJobGroupAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsJobGroup);
		LivingsUIPanel.tileLivingsNoJobGroup = new Tile ("livings_nojobgroup"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsNoJobGroupON = new Tile ("livings_nojobgroupON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsNoJobGroupGreen = new Tile ("livings_nojobgroup_green"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsNoJobGroupAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsNoJobGroup);

		LivingsUIPanel.tileLivingsSingleGroupRename = new Tile ("livings_group_rename"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupRenameON = new Tile ("livings_group_renameON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupRenameAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleGroupRename);
		LivingsUIPanel.tileLivingsSingleJobGroupRename = new Tile ("livings_jobgroup_rename"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleJobGroupRenameON = new Tile ("livings_jobgroup_renameON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleJobGroupRenameAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleJobGroupRename);
		LivingsUIPanel.tileLivingsSingleGroupGuard = new Tile ("livings_group_guard"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupGuardON = new Tile ("livings_group_guardON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupGuardAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleGroupGuard);
		LivingsUIPanel.tileLivingsSingleGroupPatrol = new Tile ("livings_group_patrol"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupPatrolON = new Tile ("livings_group_patrolON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupPatrolAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleGroupPatrol);
		LivingsUIPanel.tileLivingsSingleGroupBoss = new Tile ("livings_group_boss"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupBossON = new Tile ("livings_group_bossON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupBossAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleGroupBoss);
		LivingsUIPanel.tileLivingsSingleGroupDisband = new Tile ("livings_group_disband"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupDisbandON = new Tile ("livings_group_disbandON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupDisbandAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleGroupDisband);
		LivingsUIPanel.tileLivingsSingleJobGroupDisband = new Tile ("livings_jobgroup_disband"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleJobGroupDisbandON = new Tile ("livings_jobgroup_disbandON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleJobGroupDisbandAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleJobGroupDisband);

		LivingsUIPanel.tileLivingsSingleGroupChangeJobs = new Tile ("livings_jobgroup_changejob"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupChangeJobsON = new Tile ("livings_jobgroup_changejobON"); //$NON-NLS-1$
		LivingsUIPanel.tileLivingsSingleGroupChangeJobsAlpha = UtilsGL.generateAlpha (LivingsUIPanel.tileLivingsSingleGroupChangeJobs);

		/*
		 * MENU (right)
		 */
		RightMenuUIPanel.tileMenuPanel = new Tile [9]; // Background/N/S/E/W/NE,NW,SE,SW
		RightMenuUIPanel.tileMenuPanel[0] = new Tile ("menu_panel"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[1] = new Tile ("menu_panel_N"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[2] = new Tile ("menu_panel_S"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[3] = new Tile ("menu_panel_E"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[4] = new Tile ("menu_panel_W"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[5] = new Tile ("menu_panel_NE"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[6] = new Tile ("menu_panel_NW"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[7] = new Tile ("menu_panel_SE"); //$NON-NLS-1$
		RightMenuUIPanel.tileMenuPanel[8] = new Tile ("menu_panel_SW"); //$NON-NLS-1$
		RightMenuUIPanel.tileOpenRightMenu = new Tile ("icon_openRight"); //$NON-NLS-1$
		tileOpenRightMenuAlpha = UtilsGL.generateAlpha (RightMenuUIPanel.tileOpenRightMenu);
		RightMenuUIPanel.tileOpenRightMenuON = new Tile ("icon_openRightON"); //$NON-NLS-1$
		tileOpenRightMenuONAlpha = UtilsGL.generateAlpha (RightMenuUIPanel.tileOpenRightMenuON);

		/*
		 * Mini icons
		 */
		tileIconCitizenNext = new Tile ("icon_citizennext"); //$NON-NLS-1$
		tileIconCitizenNextON = new Tile ("icon_citizennextON"); //$NON-NLS-1$
		tileIconCitizenPrevious = new Tile ("icon_citizenprevious"); //$NON-NLS-1$
		tileIconCitizenPreviousON = new Tile ("icon_citizenpreviousON"); //$NON-NLS-1$
		tileIconSoldierNext = new Tile ("icon_soldiernext"); //$NON-NLS-1$
		tileIconSoldierNextON = new Tile ("icon_soldiernextON"); //$NON-NLS-1$
		tileIconSoldierPrevious = new Tile ("icon_soldierprevious"); //$NON-NLS-1$
		tileIconSoldierPreviousON = new Tile ("icon_soldierpreviousON"); //$NON-NLS-1$
		tileIconHeroNext = new Tile ("icon_heronext"); //$NON-NLS-1$
		tileIconHeroNextON = new Tile ("icon_heronextON"); //$NON-NLS-1$
		tileIconHeroPrevious = new Tile ("icon_heroprevious"); //$NON-NLS-1$
		tileIconHeroPreviousON = new Tile ("icon_heropreviousON"); //$NON-NLS-1$

		tileIconNextMiniAlpha = UtilsGL.generateAlpha (tileIconCitizenNext);
		tileIconPreviousMiniAlpha = UtilsGL.generateAlpha (tileIconCitizenPrevious);

		tileIconLevelDown = new Tile ("icon_leveldown"); //$NON-NLS-1$
		LivingsUIPanel.tileIconLevelDownAlpha = UtilsGL.generateAlpha (tileIconLevelDown);
		tileIconLevel = new Tile ("icon_level"); //$NON-NLS-1$
		tileIconLevelAlpha = UtilsGL.generateAlpha (tileIconLevel);
		tileIconLevelUp = new Tile ("icon_levelup"); //$NON-NLS-1$
		LivingsUIPanel.tileIconLevelUpAlpha = UtilsGL.generateAlpha (tileIconLevelUp);

		/*
		 * Icons
		 */
		tileIconPriorities = new Tile ("icon_priorities"); //$NON-NLS-1$
		tileIconPrioritiesON = new Tile ("icon_prioritiesON"); //$NON-NLS-1$
		tileIconPrioritiesAlpha = UtilsGL.generateAlpha (tileIconPriorities);
		tileIconMats = new Tile ("icon_mats"); //$NON-NLS-1$
		tileIconMatsON = new Tile ("icon_matsON"); //$NON-NLS-1$
		tileIconMatsAlpha = UtilsGL.generateAlpha (tileIconMats);
		tileIconSettings = new Tile ("icon_settings"); //$NON-NLS-1$
		tileIconSettingsAlpha = UtilsGL.generateAlpha (tileIconSettings);
		tileIconGrid = new Tile ("icon_grid"); //$NON-NLS-1$
		tileIconGridON = new Tile ("icon_gridON"); //$NON-NLS-1$
		tileIconGridAlpha = UtilsGL.generateAlpha (tileIconGrid);
		tileIconMiniblocks = new Tile ("icon_miniblock"); //$NON-NLS-1$
		tileIconMiniblocksON = new Tile ("icon_miniblockON"); //$NON-NLS-1$
		tileIconMiniblocksAlpha = UtilsGL.generateAlpha (tileIconMiniblocks);
		tileIconFlatMouse = new Tile ("icon_flatmouse"); //$NON-NLS-1$
		tileIconFlatMouseON = new Tile ("icon_flatmouseON"); //$NON-NLS-1$
		tileIconFlatMouseAlpha = UtilsGL.generateAlpha (tileIconFlatMouse);
		tileIcon3DMouse = new Tile ("icon_3d_mouse"); //$NON-NLS-1$
		tileIcon3DMouseON = new Tile ("icon_3d_mouseON"); //$NON-NLS-1$
		tileIcon3DMouseAlpha = UtilsGL.generateAlpha (tileIcon3DMouse);
		tileIconPause = new Tile ("icon_pause"); //$NON-NLS-1$
		tileIconResume = new Tile ("icon_resume"); //$NON-NLS-1$
		tileIconPauseResumeAlpha = UtilsGL.generateAlpha (tileIconPause);
		tileIconIncreaseSpeed = new Tile ("icon_increase_speed"); //$NON-NLS-1$
		tileIconIncreaseSpeedON = new Tile ("icon_increase_speedON"); //$NON-NLS-1$
		tileIconIncreaseSpeedAlpha = UtilsGL.generateAlpha (tileIconIncreaseSpeed);
		tileIconLowerSpeed = new Tile ("icon_lower_speed"); //$NON-NLS-1$
		tileIconLowerSpeedON = new Tile ("icon_lower_speedON"); //$NON-NLS-1$
		tileIconLowerSpeedAlpha = UtilsGL.generateAlpha (tileIconLowerSpeed);

		/*
		 * Info
		 */
		tileIconNumCitizens = new Tile ("icon_numcitizens"); //$NON-NLS-1$
		tileIconNumSoldiers = new Tile ("icon_numsoldiers"); //$NON-NLS-1$
		tileIconNumHeroes = new Tile ("icon_numheroes"); //$NON-NLS-1$
		tileIconCaravan = new Tile ("icon_caravan"); //$NON-NLS-1$
		tileIconCaravanON = new Tile ("icon_caravanON"); //$NON-NLS-1$

		tileInfoPanel = new Tile ("info_panel"); //$NON-NLS-1$
		tileInfoPanelAlpha = UtilsGL.generateAlpha (tileInfoPanel);
		tileDatePanel = new Tile ("date_panel"); //$NON-NLS-1$
		tileDatePanelAlpha = UtilsGL.generateAlpha (tileDatePanel);

		tileIconCoins = new Tile ("icon_coins"); //$NON-NLS-1$

		tileIconTutorial = new Tile ("icon_tutorial"); //$NON-NLS-1$

		// tileIconGods = new Tile ("icon_gods");

		/*
		 * Tooltip
		 */
		tileTooltipBackground = new Tile ("tooltip_background"); //$NON-NLS-1$
	}

	public void initialize (String sCampaignID, String sMissionID, boolean bLoadMenus) {
		if (BottomMenuUIPanel.currentMenu == null && bLoadMenus) {
			loadMenus (sCampaignID, sMissionID);
		}

		if (tileBottomItem == null) {
			generateTiles ();
		}

		PIXELS_TO_BORDER = renderWidth / 80;

		// MINIMAP
		MINIMAP_PANEL_WIDTH = tileMinimapPanel.getTileWidth ();
		MINIMAP_PANEL_HEIGHT = tileMinimapPanel.getTileHeight ();

		minimapPanelX = renderWidth - MINIMAP_PANEL_WIDTH - PIXELS_TO_BORDER;
		minimapPanelY = PIXELS_TO_BORDER;
		MiniMapPanel.initialize (minimapPanelX, minimapPanelY, MINIMAP_PANEL_WIDTH, MINIMAP_PANEL_HEIGHT);

		/*
		 * BOTTOM panel
		 */
		if (BottomMenuUIPanel.bottomPanelItemsPosition == null) {
			BottomMenuUIPanel.bottomPanelItemsPosition = new ArrayList<Point> (BottomMenuUIPanel.BOTTOM_PANEL_NUM_ITEMS);
		}

		// Centramos el panel
		BottomMenuUIPanel.bottomPanelX = renderWidth / 2 - BottomMenuUIPanel.BOTTOM_PANEL_WIDTH / 2;
		BottomMenuUIPanel.bottomPanelY = renderHeight - BottomMenuUIPanel.BOTTOM_PANEL_HEIGHT - BottomMenuUIPanel.tileOpenBottomMenu.getTileHeight ();
		// Calculamos la posición de los minipaneles de scroll
		BottomMenuUIPanel.bottomPanelLeftScrollX = BottomMenuUIPanel.bottomPanelX - BottomMenuUIPanel.BOTTOM_PANEL_SCROLL_WIDTH;
		BottomMenuUIPanel.bottomPanelRightScrollX = BottomMenuUIPanel.bottomPanelX + BottomMenuUIPanel.BOTTOM_PANEL_WIDTH;

		BottomMenuUIPanel.bottomPanelItemIndex = 0;

		// Subpanel
		BottomMenuUIPanel.bottomSubPanelMenu = null;

		// Cargamos las posiciones
		BottomMenuUIPanel.bottomPanelItemsPosition.clear ();
		int spaceBetweenItems = (BottomMenuUIPanel.BOTTOM_PANEL_WIDTH - (BOTTOM_ITEM_WIDTH * BottomMenuUIPanel.BOTTOM_PANEL_NUM_ITEMS)) / (BottomMenuUIPanel.BOTTOM_PANEL_NUM_ITEMS + 1);
		for (int i = 0; i < BottomMenuUIPanel.BOTTOM_PANEL_NUM_ITEMS; i++) {
			BottomMenuUIPanel.bottomPanelItemsPosition.add (new Point (BottomMenuUIPanel.bottomPanelX + spaceBetweenItems + (i * (BOTTOM_ITEM_WIDTH + spaceBetweenItems)), BottomMenuUIPanel.bottomPanelY + (BottomMenuUIPanel.BOTTOM_PANEL_HEIGHT / 2) - (BOTTOM_ITEM_HEIGHT / 2)));
		}

		// Minibotón para abrir/cerrar el panel de abajo
		BottomMenuUIPanel.tileOpenCloseBottomMenuPoint.setLocation (renderWidth / 2 - BottomMenuUIPanel.tileOpenBottomMenu.getTileWidth () / 2, renderHeight - BottomMenuUIPanel.tileOpenBottomMenu.getTileHeight ());

		/*
		 * Date panel
		 */
		datePanelPoint.setLocation (renderWidth / 2 - tileDatePanel.getTileWidth () / 2, tileIconCoins.getTileHeight ());

		/*
		 * Coins icon point
		 */
		tileIconCoinsPoint.setLocation (renderWidth / 2, 0 + tileIconCoins.getTileHeightOffset ());

		/*
		 * Info panel
		 */
		infoPanelPoint.setLocation (renderWidth / 2 - tileInfoPanel.getTileWidth () / 2 + tileInfoPanel.getTileWidthOffset (), 0);

		int iSeparation = datePanelPoint.x - infoPanelPoint.x;
		iSeparation = iSeparation - 2 * tileBottomItem.getTileWidth ();
		iSeparation /= 3;
		// Citizens
		iconNumCitizensBackgroundPoint.setLocation (infoPanelPoint.x + iSeparation, infoPanelPoint.y + tileIconNumCitizens.getTileHeightOffset ());
		iconNumCitizensPoint.setLocation (iconNumCitizensBackgroundPoint.x + tileIconNumCitizens.getTileWidthOffset (), iconNumCitizensBackgroundPoint.y + tileIconNumCitizens.getTileHeightOffset ());
		iconCitizenPreviousPoint.setLocation (iconNumCitizensBackgroundPoint.x + tileIconCitizenPrevious.getTileWidthOffset (), iconNumCitizensBackgroundPoint.y + tileIconCitizenPrevious.getTileHeightOffset ());
		iconCitizenNextPoint.setLocation (iconNumCitizensBackgroundPoint.x + tileIconCitizenNext.getTileWidthOffset (), iconNumCitizensBackgroundPoint.y + tileIconCitizenNext.getTileHeightOffset ());

		// Soldiers
		iconNumSoldiersBackgroundPoint.setLocation (infoPanelPoint.x + 2 * iSeparation + tileBottomItem.getTileWidth (), infoPanelPoint.y + tileIconNumSoldiers.getTileHeightOffset ());
		iconNumSoldiersPoint.setLocation (iconNumSoldiersBackgroundPoint.x + tileIconNumSoldiers.getTileWidthOffset (), iconNumSoldiersBackgroundPoint.y + tileIconNumSoldiers.getTileHeightOffset ());
		iconSoldierPreviousPoint.setLocation (iconNumSoldiersBackgroundPoint.x + tileIconSoldierPrevious.getTileWidthOffset (), iconNumSoldiersBackgroundPoint.y + tileIconSoldierPrevious.getTileHeightOffset ());
		iconSoldierNextPoint.setLocation (iconNumSoldiersBackgroundPoint.x + tileIconSoldierNext.getTileWidthOffset (), iconNumSoldiersBackgroundPoint.y + tileIconSoldierNext.getTileHeightOffset ());

		iSeparation = infoPanelPoint.x + tileInfoPanel.getTileWidth () - (datePanelPoint.x + tileDatePanel.getTileWidth ());
		iSeparation = iSeparation - 2 * tileBottomItem.getTileWidth ();
		iSeparation /= 3;

		// Heroes
		iconNumHeroesBackgroundPoint.setLocation (datePanelPoint.x + tileDatePanel.getTileWidth () + iSeparation, infoPanelPoint.y + tileIconNumHeroes.getTileHeightOffset ());
		iconNumHeroesPoint.setLocation (iconNumHeroesBackgroundPoint.x + tileIconNumHeroes.getTileWidthOffset (), iconNumHeroesBackgroundPoint.y + tileIconNumHeroes.getTileHeightOffset ());
		iconHeroPreviousPoint.setLocation (iconNumHeroesBackgroundPoint.x + tileIconHeroPrevious.getTileWidthOffset (), iconNumHeroesBackgroundPoint.y + tileIconHeroPrevious.getTileHeightOffset ());
		iconHeroNextPoint.setLocation (iconNumHeroesBackgroundPoint.x + tileIconHeroNext.getTileWidthOffset (), iconNumHeroesBackgroundPoint.y + tileIconHeroNext.getTileHeightOffset ());

		// Caravan
		iconCaravanBackgroundPoint.setLocation (datePanelPoint.x + tileDatePanel.getTileWidth () + 2 * iSeparation + tileBottomItem.getTileWidth (), infoPanelPoint.y + tileIconCaravan.getTileHeightOffset ());
		iconCaravanPoint.setLocation (iconCaravanBackgroundPoint.x + tileIconCaravan.getTileWidthOffset (), iconCaravanBackgroundPoint.y + tileBottomItem.getTileHeight () / 2 - tileIconCaravan.getTileHeight () / 2);

		if (bLoadMenus) {
			/*
			 * Menu panel (menú de la derecha)
			 */
			createMenuPanel (RightMenuUIPanel.menuPanelMenu);

			/*
			 * Production panel
			 */
			ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);

			/*
			 * Trade panel
			 */
			TradeUIPanel.createTradePanel ();

			/*
			 * Mats panel
			 */
			MatsUIPanel.createMatsPanel ();

			/*
			 * Stockpile panel (piles + containers)
			 */
			PileUIPanel.createPilePanel ();

			/*
			 * Professions panel
			 */
			ProfessionsUIPanel.createProfessionsPanel ();

			/*
			 * Livings panel
			 */
			LivingsUIPanel.createLivingsPanel (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, -1, -1);

			/*
			 * Priorities panel
			 */
			PrioritiesUIPanel.createPrioritiesPanel ();

			/*
			 * Images panel
			 */
			ImagesPanel.resize (renderWidth, renderHeight);
		}

		/*
		 * Messages panel
		 */
		MessagesUIPanel.createMessagesPanel ();

		// Images button location
		iconTutorialPoint.setLocation (MessagesUIPanel.messageIconPoints[0].x, MessagesUIPanel.messageIconPoints[0].y + MessagesUIPanel.messageTiles[0].getTileHeight () + PIXELS_TO_BORDER + BOTTOM_ITEM_HEIGHT + BOTTOM_ITEM_HEIGHT / 4);

		// Events + gods icons
		int iStartingX = MessagesUIPanel.messageIconPoints[MessagesUIPanel.messageIconPoints.length - 1].x + MessagesUIPanel.messageTiles[MessagesUIPanel.messageTiles.length - 1].getTileWidth ();
		int iAvailableWidth = infoPanelPoint.x - iStartingX;

		iconEventsPoint.setLocation (iStartingX + iAvailableWidth / 4 - ICON_WIDTH / 2 + 3, MessagesUIPanel.messageIconPoints[MessagesUIPanel.messageIconPoints.length - 1].y + MessagesUIPanel.messageTiles[MessagesUIPanel.messageTiles.length - 1].getTileHeight () / 2 - GlobalEventData.getIcon ().getTileHeight () / 2);

		// Gods icon
		// iconGodsPoint.setLocation (iStartingX + (iAvailableWidth / 4) * 3 - ICON_WIDTH / 2 + 3, iconEventsPoint.y + GlobalEventData.getIcon ().getTileHeight () / 2 - tileIconGods.getTileHeight () / 2);

		/*
		 * Mini icons
		 */
		iconLevelUpPoint.setLocation (minimapPanelX + tileIconLevelUp.getTileWidthOffset (), minimapPanelY + tileIconLevelUp.getTileHeightOffset ());
		iconLevelPoint.setLocation (minimapPanelX + tileIconLevel.getTileWidthOffset (), minimapPanelY + tileIconLevel.getTileHeightOffset ());
		iconLevelDownPoint.setLocation (minimapPanelX + tileIconLevelDown.getTileWidthOffset (), minimapPanelY + tileIconLevelDown.getTileHeightOffset ());

		// Debajo del date metemos 2 iconos de panel (de momento priorities y mats)
		int iPanels = (tileDatePanel.getTileWidth () - tileIconPriorities.getTileWidth () - tileIconMats.getTileWidth ()) / 3;
		iconMatsPoint.setLocation (datePanelPoint.x + iPanels, datePanelPoint.y + tileDatePanel.getTileHeight () + tileIconMats.getTileHeightOffset ());
		iconPrioritiesPoint.setLocation (datePanelPoint.x + iPanels + tileIconMats.getTileWidth () + iPanels, datePanelPoint.y + tileDatePanel.getTileHeight () + tileIconPriorities.getTileHeightOffset ());

		// Miniblocks, grid, settings, flat mouse, 3D mouse
		iconMiniblocksPoint.setLocation (minimapPanelX + tileIconMiniblocks.getTileWidthOffset (), minimapPanelY + tileIconMiniblocks.getTileHeightOffset ());
		iconGridPoint.setLocation (minimapPanelX + tileIconGrid.getTileWidthOffset (), minimapPanelY + tileIconGrid.getTileHeightOffset ());
		iconSettingsPoint.setLocation (minimapPanelX + tileIconSettings.getTileWidthOffset (), minimapPanelY + tileIconSettings.getTileHeightOffset ());
		iconFlatMousePoint.setLocation (minimapPanelX + tileIconFlatMouse.getTileWidthOffset (), minimapPanelY + tileIconFlatMouse.getTileHeightOffset ());
		icon3DMousePoint.setLocation (minimapPanelX + tileIcon3DMouse.getTileWidthOffset (), minimapPanelY + tileIcon3DMouse.getTileHeightOffset ());

		// Lower speed, pause/resume, increase speed
		iconLowerSpeedPoint.setLocation (minimapPanelX + tileIconLowerSpeedON.getTileWidthOffset (), minimapPanelY + tileIconLowerSpeedON.getTileHeightOffset ());
		iconPauseResumePoint.setLocation (minimapPanelX + tileIconPause.getTileWidthOffset (), minimapPanelY + tileIconPause.getTileHeightOffset ());
		iconIncreaseSpeedPoint.setLocation (minimapPanelX + tileIconIncreaseSpeedON.getTileWidthOffset (), minimapPanelY + tileIconIncreaseSpeedON.getTileHeightOffset ());

		// Edge menus
		if (ProductionUIPanel.isProductionPanelLocked ()) {
			ProductionUIPanel.setProductionPanelActive (true);
		}

		if (RightMenuUIPanel.isMenuPanelLocked ()) {
			RightMenuUIPanel.setMenuPanelActive (true);
		}

		if (BottomMenuUIPanel.isBottomMenuPanelLocked ()) {
			BottomMenuUIPanel.setBottomMenuPanelActive (true, true);
		}
	}

	public void render () {
		if (MainPanel.bHideUION) {
			return;
		}

		int mouseX = Mouse.getX ();
		int mouseY = renderHeight - Mouse.getY () - 1;
		delayTime++;
		blinkTurns++;
		if (blinkTurns >= MAX_BLINK_TURNS) {
			blinkTurns = 0;
		}

		int mousePanel = isMouseOnAPanel (mouseX, mouseY, true);

		/*
		 * BOTTOM menu panel
		 */
		int iCurrentTexture = BottomMenuUIPanel.tileBottomScrollLeft.getTextureID (); // Esta textura es la primera quese usa en el bottom menu
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		GL11.glColor4f (1, 1, 1, 1);
		UtilsGL.glBegin (GL11.GL_QUADS);

		BottomMenuUIPanel.checkBlinkBottom = (blinkTurns >= MAX_BLINK_TURNS / 2) && TutorialFlow.isBlinkBottom ();
		if (BottomMenuUIPanel.isBottomMenuPanelActive ()) {
			iCurrentTexture = BottomMenuUIPanel.renderBottomMenuPanel (mouseX, mouseY, mousePanel, iCurrentTexture);
		}

		// Rendereamos el botoncito para hacer visible/invisible el bottom panel
		if (BottomMenuUIPanel.isBottomMenuPanelLocked ()) {
			iCurrentTexture = UtilsGL.setTexture (tileOpenBottomMenuON, iCurrentTexture);
			drawTile (tileOpenBottomMenuON, BottomMenuUIPanel.tileOpenCloseBottomMenuPoint, tileOpenBottomMenuON.getTileWidth (), tileOpenBottomMenuON.getTileHeight (), mousePanel == MOUSE_BOTTOM_OPENCLOSE);
		} else {
			iCurrentTexture = UtilsGL.setTexture (BottomMenuUIPanel.tileOpenBottomMenu, iCurrentTexture);
			if (BottomMenuUIPanel.checkBlinkBottom) {
				UtilsGL.setColorRed ();
			}
			drawTile (BottomMenuUIPanel.tileOpenBottomMenu, BottomMenuUIPanel.tileOpenCloseBottomMenuPoint, BottomMenuUIPanel.tileOpenBottomMenu.getTileWidth (), BottomMenuUIPanel.tileOpenBottomMenu.getTileHeight (), mousePanel == MOUSE_BOTTOM_OPENCLOSE);
			if (BottomMenuUIPanel.checkBlinkBottom) {
				UtilsGL.unsetColor ();
			}
		}

		/*
		 * MINIMAP (textures)
		 */
		// Minimap background
		iCurrentTexture = UtilsGL.setTexture (tileMinimapPanel, iCurrentTexture);
		UtilsGL.drawTexture (minimapPanelX, minimapPanelY, minimapPanelX + MINIMAP_PANEL_WIDTH, minimapPanelY + MINIMAP_PANEL_HEIGHT, tileMinimapPanel.getTileSetTexX0 (), tileMinimapPanel.getTileSetTexY0 (), tileMinimapPanel.getTileSetTexX1 (), tileMinimapPanel.getTileSetTexY1 ());

		UtilsGL.glEnd ();

		// Minimap content
		MiniMapPanel.render ();

		/*
		 * MENU (right)
		 */
		RightMenuUIPanel.renderMenuPanel (mouseX, mouseY, mousePanel);

		// Possible mini icon blinks?
		// Blink
		TutorialFlow tutorialFlow = null;
		if ((blinkTurns >= MAX_BLINK_TURNS / 2)) {
			if (Game.getCurrentMissionData () != null && ImagesPanel.getCurrentFlowIndex () >= 0 && ImagesPanel.getCurrentFlowIndex () < Game.getCurrentMissionData ().getTutorialFlows ().size ()) {
				tutorialFlow = Game.getCurrentMissionData ().getTutorialFlows ().get (ImagesPanel.getCurrentFlowIndex ());
			}
		}

		/*
		 * Info
		 */
		iCurrentTexture = tileInfoPanel.getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		drawTile (tileInfoPanel, infoPanelPoint);
		iCurrentTexture = UtilsGL.setTexture (tileDatePanel, iCurrentTexture);
		drawTile (tileDatePanel, datePanelPoint);

		// Level up/down
		iCurrentTexture = UtilsGL.setTexture (tileIconLevelUp, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniLevelUp ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (tileIconLevelUp, iconLevelUpPoint, ICON_WIDTH, ICON_HEIGHT, mousePanel == MOUSE_ICON_LEVEL_UP);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniLevelUp ()) {
			UtilsGL.unsetColor ();
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconLevelDown, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniLevelDown ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (tileIconLevelDown, iconLevelDownPoint, ICON_WIDTH, ICON_HEIGHT, mousePanel == MOUSE_ICON_LEVEL_DOWN);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniLevelDown ()) {
			UtilsGL.unsetColor ();
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconLevel, iCurrentTexture);
		drawTile (tileIconLevel, iconLevelPoint);

		// Num citizens / soldiers / heroes / caravan
		iCurrentTexture = UtilsGL.setTexture (tileBottomItem, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniCitizens ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (tileBottomItem, iconNumCitizensBackgroundPoint);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniCitizens ()) {
			UtilsGL.unsetColor ();
		}
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniSoldiers ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (tileBottomItem, iconNumSoldiersBackgroundPoint);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniSoldiers ()) {
			UtilsGL.unsetColor ();
		}
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniHeroes ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (tileBottomItem, iconNumHeroesBackgroundPoint);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniHeroes ()) {
			UtilsGL.unsetColor ();
		}
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniTrade ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (tileBottomItem, iconCaravanBackgroundPoint);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniTrade ()) {
			UtilsGL.unsetColor ();
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconNumCitizens, iCurrentTexture);
		drawTile (tileIconNumCitizens, iconNumCitizensPoint, (mousePanel == MOUSE_INFO_NUM_CITIZENS));
		iCurrentTexture = UtilsGL.setTexture (tileIconNumSoldiers, iCurrentTexture);
		drawTile (tileIconNumSoldiers, iconNumSoldiersPoint, (mousePanel == MOUSE_INFO_NUM_SOLDIERS));
		iCurrentTexture = UtilsGL.setTexture (tileIconNumHeroes, iCurrentTexture);
		drawTile (tileIconNumHeroes, iconNumHeroesPoint, (mousePanel == MOUSE_INFO_NUM_HEROES));
		if (Game.getWorld ().getCurrentCaravanData () != null) {
			iCurrentTexture = UtilsGL.setTexture (tileIconCaravanON, iCurrentTexture);
			drawTile (tileIconCaravanON, iconCaravanPoint, (mousePanel == MOUSE_INFO_CARAVAN));
		} else {
			iCurrentTexture = UtilsGL.setTexture (tileIconCaravan, iCurrentTexture);
			drawTile (tileIconCaravan, iconCaravanPoint, (mousePanel == MOUSE_INFO_CARAVAN));
		}

		// Previous/next citizen/soldiers/heroes
		iCurrentTexture = UtilsGL.setTexture (tileIconCitizenPrevious, iCurrentTexture);
		if (mousePanel == MOUSE_ICON_CITIZEN_PREVIOUS) {
			drawTile (tileIconCitizenPreviousON, iconCitizenPreviousPoint);
		} else {
			drawTile (tileIconCitizenPrevious, iconCitizenPreviousPoint);
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconCitizenNext, iCurrentTexture);
		if (mousePanel == MOUSE_ICON_CITIZEN_NEXT) {
			drawTile (tileIconCitizenNextON, iconCitizenNextPoint);
		} else {
			drawTile (tileIconCitizenNext, iconCitizenNextPoint);
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconSoldierPrevious, iCurrentTexture);
		if (mousePanel == MOUSE_ICON_SOLDIER_PREVIOUS) {
			drawTile (tileIconSoldierPreviousON, iconSoldierPreviousPoint);
		} else {
			drawTile (tileIconSoldierPrevious, iconSoldierPreviousPoint);
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconSoldierNext, iCurrentTexture);
		if (mousePanel == MOUSE_ICON_SOLDIER_NEXT) {
			drawTile (tileIconSoldierNextON, iconSoldierNextPoint);
		} else {
			drawTile (tileIconSoldierNext, iconSoldierNextPoint);
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconHeroPrevious, iCurrentTexture);
		if (mousePanel == MOUSE_ICON_HERO_PREVIOUS) {
			drawTile (tileIconHeroPreviousON, iconHeroPreviousPoint);
		} else {
			drawTile (tileIconHeroPrevious, iconHeroPreviousPoint);
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconHeroNext, iCurrentTexture);
		if (mousePanel == MOUSE_ICON_HERO_NEXT) {
			drawTile (tileIconHeroNextON, iconHeroNextPoint);
		} else {
			drawTile (tileIconHeroNext, iconHeroNextPoint);
		}

		// Panel icons (priorities, mats)
		iCurrentTexture = UtilsGL.setTexture (MatsUIPanel.isMatsPanelActive () ? tileIconMatsON : tileIconMats, iCurrentTexture);
		drawTile (MatsUIPanel.isMatsPanelActive () ? tileIconMatsON : tileIconMats, iconMatsPoint, (mousePanel == MOUSE_ICON_MATS));
		iCurrentTexture = UtilsGL.setTexture (PrioritiesUIPanel.isPrioritiesPanelActive () ? tileIconPrioritiesON : tileIconPriorities, iCurrentTexture);
		drawTile (PrioritiesUIPanel.isPrioritiesPanelActive () ? tileIconPrioritiesON : tileIconPriorities, iconPrioritiesPoint, (mousePanel == MOUSE_ICON_PRIORITIES));

		// Coins
		String sTownCoins = Game.getWorld ().getCoinsString ();
		int iTownsCoinsWidth = UtilFont.getWidth (sTownCoins);
		iCurrentTexture = UtilsGL.setTexture (tileIconCoins, iCurrentTexture);
		drawTile (tileIconCoins, tileIconCoinsPoint.x - iTownsCoinsWidth / 2 - tileIconCoins.getTileWidth () / 2, tileIconCoinsPoint.y, false);

		// Icons (miniblock + grid + settings + flat mouse + 3d mouse)
		iCurrentTexture = UtilsGL.setTexture (MainPanel.bMiniBlocksON ? tileIconMiniblocksON : tileIconMiniblocks, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniFlat ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (MainPanel.bMiniBlocksON ? tileIconMiniblocksON : tileIconMiniblocks, iconMiniblocksPoint, (mousePanel == MOUSE_ICON_MINIBLOCKS));
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniFlat ()) {
			UtilsGL.unsetColor ();
		}
		iCurrentTexture = UtilsGL.setTexture (MainPanel.gridON ? tileIconGridON : tileIconGrid, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniGrid ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (MainPanel.gridON ? tileIconGridON : tileIconGrid, iconGridPoint, (mousePanel == MOUSE_ICON_GRID));
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniGrid ()) {
			UtilsGL.unsetColor ();
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconSettings, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniSettings ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (tileIconSettings, iconSettingsPoint, (mousePanel == MOUSE_ICON_SETTINGS));
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniSettings ()) {
			UtilsGL.unsetColor ();
		}
		iCurrentTexture = UtilsGL.setTexture (tileIconFlatMouse, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniFlatCursor ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (MainPanel.flatMouseON ? tileIconFlatMouseON : tileIconFlatMouse, iconFlatMousePoint, (mousePanel == MOUSE_ICON_FLATMOUSE));
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniFlatCursor ()) {
			UtilsGL.unsetColor ();
		}
		iCurrentTexture = UtilsGL.setTexture (tileIcon3DMouse, iCurrentTexture);
		if (tutorialFlow != null && tutorialFlow.isBlinkMini3DMouse ()) {
			UtilsGL.setColorRed ();
		}
		drawTile (MainPanel.tDMouseON ? tileIcon3DMouseON : tileIcon3DMouse, icon3DMousePoint, (mousePanel == MOUSE_ICON_3DMOUSE));
		if (tutorialFlow != null && tutorialFlow.isBlinkMini3DMouse ()) {
			UtilsGL.unsetColor ();
		}

		// Message icons
		if (MessagesUIPanel.getMessagesPanelActive () == MessagesPanel.TYPE_ANNOUNCEMENT || (MessagesPanel.getBlink ()[MessagesPanel.TYPE_ANNOUNCEMENT] && blinkTurns >= MAX_BLINK_TURNS / 2)) {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTilesON[0], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTilesON[0], MessagesUIPanel.messageIconPoints[0], (mousePanel == MOUSE_MESSAGES_ICON_ANNOUNCEMENT));
		} else {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTiles[0], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTiles[0], MessagesUIPanel.messageIconPoints[0], (mousePanel == MOUSE_MESSAGES_ICON_ANNOUNCEMENT));
		}
		if (MessagesUIPanel.getMessagesPanelActive () == MessagesPanel.TYPE_COMBAT || (MessagesPanel.getBlink ()[MessagesPanel.TYPE_COMBAT] && blinkTurns >= MAX_BLINK_TURNS / 2)) {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTilesON[1], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTilesON[1], MessagesUIPanel.messageIconPoints[1], (mousePanel == MOUSE_MESSAGES_ICON_COMBAT));
		} else {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTiles[1], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTiles[1], MessagesUIPanel.messageIconPoints[1], (mousePanel == MOUSE_MESSAGES_ICON_COMBAT));
		}
		if (MessagesUIPanel.getMessagesPanelActive () == MessagesPanel.TYPE_HEROES || (MessagesPanel.getBlink ()[MessagesPanel.TYPE_HEROES] && blinkTurns >= MAX_BLINK_TURNS / 2)) {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTilesON[2], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTilesON[2], MessagesUIPanel.messageIconPoints[2], (mousePanel == MOUSE_MESSAGES_ICON_HEROES));
		} else {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTiles[2], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTiles[2], MessagesUIPanel.messageIconPoints[2], (mousePanel == MOUSE_MESSAGES_ICON_HEROES));
		}
		if (MessagesUIPanel.getMessagesPanelActive () == MessagesPanel.TYPE_SYSTEM || (MessagesPanel.getBlink ()[MessagesPanel.TYPE_SYSTEM] && blinkTurns >= MAX_BLINK_TURNS / 2)) {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTilesON[3], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTilesON[3], MessagesUIPanel.messageIconPoints[3], (mousePanel == MOUSE_MESSAGES_ICON_SYSTEM));
		} else {
			iCurrentTexture = UtilsGL.setTexture (MessagesUIPanel.messageTiles[3], iCurrentTexture);
			drawTile (MessagesUIPanel.messageTiles[3], MessagesUIPanel.messageIconPoints[3], (mousePanel == MOUSE_MESSAGES_ICON_SYSTEM));
		}

		// Events icon
		iCurrentTexture = UtilsGL.setTexture (GlobalEventData.getIcon (), iCurrentTexture);
		drawTile (GlobalEventData.getIcon (), iconEventsPoint, false);

		// Gods icon
		// if (TownsProperties.GODS_ACTIVATED) {
		// iCurrentTexture = UtilsGL.setTexture (tileIconGods, iCurrentTexture);
		// drawTile (tileIconGods, iconGodsPoint, false);
		// }

		// (speed down, pause/play, speed up)
		if (World.SPEED > 1) {
			iCurrentTexture = UtilsGL.setTexture (tileIconLowerSpeedON, iCurrentTexture);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniSpeedDown ()) {
				UtilsGL.setColorRed ();
			}
			drawTile (tileIconLowerSpeedON, iconLowerSpeedPoint, (mousePanel == MOUSE_ICON_LOWER_SPEED));
		} else {
			iCurrentTexture = UtilsGL.setTexture (tileIconLowerSpeed, iCurrentTexture);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniSpeedDown ()) {
				UtilsGL.setColorRed ();
			}
			drawTile (tileIconLowerSpeed, iconLowerSpeedPoint, false);
		}
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniSpeedDown ()) {
			UtilsGL.unsetColor ();
		}
		if (Game.isPaused ()) {
			iCurrentTexture = UtilsGL.setTexture (tileIconResume, iCurrentTexture);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniPause ()) {
				UtilsGL.setColorRed ();
			}
			drawTile (tileIconResume, iconPauseResumePoint, (mousePanel == MOUSE_ICON_PAUSE_RESUME));
		} else {
			iCurrentTexture = UtilsGL.setTexture (tileIconPause, iCurrentTexture);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniPause ()) {
				UtilsGL.setColorRed ();
			}
			drawTile (tileIconPause, iconPauseResumePoint, (mousePanel == MOUSE_ICON_PAUSE_RESUME));
		}
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniPause ()) {
			UtilsGL.unsetColor ();
		}
		if (World.SPEED < World.SPEED_MAX) {
			iCurrentTexture = UtilsGL.setTexture (tileIconIncreaseSpeedON, iCurrentTexture);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniSpeedUp ()) {
				UtilsGL.setColorRed ();
			}
			drawTile (tileIconIncreaseSpeedON, iconIncreaseSpeedPoint, (mousePanel == MOUSE_ICON_INCREASE_SPEED));
		} else {
			iCurrentTexture = UtilsGL.setTexture (tileIconIncreaseSpeed, iCurrentTexture);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniSpeedUp ()) {
				UtilsGL.setColorRed ();
			}
			drawTile (tileIconIncreaseSpeed, iconIncreaseSpeedPoint, false);
		}
		if (tutorialFlow != null && tutorialFlow.isBlinkMiniSpeedUp ()) {
			UtilsGL.unsetColor ();
		}

		UtilsGL.glEnd ();

		// Date
		String sDate = Game.getWorld ().getDate ().toString ();
		int dateW = UtilFont.getWidth (sDate);
		int iLevel = World.MAP_NUM_LEVELS_OUTSIDE - Game.getWorld ().getView ().z;
		String sLevel = Integer.toString (iLevel);
		int sLevelW = UtilFont.getWidth (sLevel);
		String sNumCitizens = Integer.toString (World.getNumCitizens ());
		int numCitizensW = UtilFont.getWidth (sNumCitizens);
		String sNumSoldiers = Integer.toString (World.getNumSoldiers ());
		int numSoldiersW = UtilFont.getWidth (sNumSoldiers);
		String sNumHeroes = Integer.toString (World.getNumHeroes ());
		int numHeroesW = UtilFont.getWidth (sNumHeroes);

		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UtilsGL.drawString (sLevel, iconLevelPoint.x + tileIconLevel.getTileWidth () / 2 - sLevelW / 2, iconLevelPoint.y + tileIconLevel.getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.BLACK);
		UtilsGL.drawString (sNumCitizens, iconNumCitizensPoint.x + tileIconNumCitizens.getTileWidth () / 2 - numCitizensW / 2, iconNumCitizensPoint.y + tileIconNumCitizens.getTileHeight (), ColorGL.BLACK);
		UtilsGL.drawString (sNumSoldiers, iconNumSoldiersPoint.x + tileIconNumSoldiers.getTileWidth () / 2 - numSoldiersW / 2, iconNumSoldiersPoint.y + tileIconNumSoldiers.getTileHeight (), ColorGL.BLACK);
		UtilsGL.drawString (sNumHeroes, iconNumHeroesPoint.x + tileIconNumHeroes.getTileWidth () / 2 - numHeroesW / 2, iconNumHeroesPoint.y + tileIconNumHeroes.getTileHeight (), ColorGL.BLACK);
		UtilsGL.drawString (sDate, datePanelPoint.x + tileDatePanel.getTileWidth () / 2 - dateW / 2, datePanelPoint.y + tileDatePanel.getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.BLACK);
		UtilsGL.drawString (sTownCoins, tileIconCoinsPoint.x - iTownsCoinsWidth / 2 + tileIconCoins.getTileWidth () / 2, tileIconCoinsPoint.y + tileIconCoins.getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.WHITE);

		if (TownsProperties.DEBUG_MODE) {
			// Global events
			GlobalEventData ged = Game.getWorld ().getGlobalEvents ();
			UtilsGL.drawStringWithBorder ("Shadows " + ged.isShadows (), 2, 3 * UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK); //$NON-NLS-1$
			UtilsGL.drawStringWithBorder ("Half shadows " + ged.isHalfShadows (), 2, 4 * UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK); //$NON-NLS-1$
			UtilsGL.drawStringWithBorder ("RGB " + ged.getRed () + "," + ged.getGreen () + "," + ged.getBlue (), 2, 5 * UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			UtilsGL.drawStringWithBorder ("waitPCT " + ged.getWaitPCT (), 2, 6 * UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK); //$NON-NLS-1$
			UtilsGL.drawStringWithBorder ("walkSpeedPCT " + ged.getWalkSpeedPCT (), 2, 7 * UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK); //$NON-NLS-1$

			// Events
			StringBuffer sbEvents = new StringBuffer ("Events: "); //$NON-NLS-1$
			for (int e = 0; e < Game.getWorld ().getEvents ().size (); e++) {
				sbEvents.append (Game.getWorld ().getEvents ().get (e).getEventID ());
				sbEvents.append (" ("); //$NON-NLS-1$
				sbEvents.append (Game.getWorld ().getEvents ().get (e).getTurns ());
				sbEvents.append (")"); //$NON-NLS-1$
				sbEvents.append (", "); //$NON-NLS-1$
			}

			UtilsGL.drawStringWithBorder (sbEvents.toString (), 2, 2 + 9 * UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);

			// Gods
			//			sbEvents = new StringBuffer ("Gods: "); //$NON-NLS-1$
			// for (int e = 0; e < Game.getWorld ().getGods ().size (); e++) {
			// sbEvents.append (Game.getWorld ().getGods ().get (e).getGodID ());
			//				sbEvents.append (" ("); //$NON-NLS-1$
			// sbEvents.append (Game.getWorld ().getGods ().get (e).getStatus ());
			//				sbEvents.append (")"); //$NON-NLS-1$
			//				sbEvents.append (", "); //$NON-NLS-1$
			// }
			//
			// UtilsGL.drawStringWithBorder (sbEvents.toString (), 2, 2 + 10 * UtilFont.MAX_HEIGHT, ColorGL.WHITE, ColorGL.BLACK);
		}

		UtilsGL.glEnd ();

		// Task
		renderTask ();

		// Tutorial button
		renderTutorialButton (mousePanel, blinkTurns >= MAX_BLINK_TURNS / 2);

		/*
		 * PANELS, PRODUCTION PANEL, PRIORITIES PANEL, TRADE_PANEL (este va encima de todo siempre)
		 */
		ProductionUIPanel.renderProductionPanel (mouseX, mouseY, mousePanel);

		if (PileUIPanel.isPilePanelActive ()) {
			PileUIPanel.renderPilePanel (mouseX, mouseY, mousePanel);
		}
		if (LivingsUIPanel.isLivingsPanelActive ()) {
			LivingsUIPanel.renderLivingsPanel (mouseX, mouseY, mousePanel);
		}
		if (ProfessionsUIPanel.isProfessionsPanelActive ()) {
			ProfessionsUIPanel.renderProfessionsPanel (mouseX, mouseY, mousePanel);
		}
		if (MatsUIPanel.isMatsPanelActive ()) {
			MatsUIPanel.renderMatsPanel (mouseX, mouseY, mousePanel);
		}
		if (PrioritiesUIPanel.isPrioritiesPanelActive ()) {
			PrioritiesUIPanel.renderPrioritiesPanel (mouseX, mouseY, mousePanel);
		}
		if (TradeUIPanel.isTradePanelActive ()) {
			TradeUIPanel.renderTradePanel (mouseX, mouseY, mousePanel);
		}
		if (MessagesUIPanel.isMessagesPanelActive ()) {
			MessagesUIPanel.renderMessagesPanel (mouseX, mouseY, mousePanel);
		}

		// Al final de todo el Typing panel si hace falta
		if (typingPanel != null) {
			TypingPanel.render (mousePanel);
		}

		// Al final de todo el Images panel si hace falta
		if (imagesPanel != null && ImagesPanel.isVisible ()) {
			ImagesPanel.render (mousePanel);
		}

		// Tooltip
		renderTooltip (mouseX, mouseY, mousePanel);
	}

	/**
	 * Renderiza el background con los 8 tiles de los lados y esquinas 0: background 1: N 2: S 3: E 4: W 5: NE 6: NW 7: SE 8: SW
	 * 
	 * @param tiles
	 */
	public static void renderBackground (Tile[] tiles, Point point, int width, int height) {
		int iEdgeWidth = tiles[6].getTileWidth ();
		int iEdgeHeight = tiles[6].getTileHeight ();

		// Background
		Tile tile = tiles[0];
		UtilsGL.drawTexture (point.x + iEdgeWidth, point.y + iEdgeHeight, point.x + width - iEdgeWidth, point.y + height - iEdgeHeight, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// N
		tile = tiles[1];
		UtilsGL.drawTexture (point.x + iEdgeWidth, point.y, point.x + width - iEdgeWidth, point.y + iEdgeHeight, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// S
		tile = tiles[2];
		UtilsGL.drawTexture (point.x + iEdgeWidth, point.y + height - iEdgeHeight, point.x + width - iEdgeWidth, point.y + height, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// E
		tile = tiles[3];
		UtilsGL.drawTexture (point.x + width - iEdgeWidth, point.y + iEdgeHeight, point.x + width, point.y + height - iEdgeHeight, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// W
		tile = tiles[4];
		UtilsGL.drawTexture (point.x, point.y + iEdgeHeight, point.x + iEdgeWidth, point.y + height - iEdgeHeight, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// NE
		tile = tiles[5];
		UtilsGL.drawTexture (point.x + width - iEdgeWidth, point.y, point.x + width, point.y + iEdgeHeight, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// NW
		tile = tiles[6];
		UtilsGL.drawTexture (point.x, point.y, point.x + iEdgeWidth, point.y + iEdgeHeight, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// SE
		tile = tiles[7];
		UtilsGL.drawTexture (point.x + width - iEdgeWidth, point.y + height - iEdgeHeight, point.x + width, point.y + height, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());

		// SW
		tile = tiles[8];
		UtilsGL.drawTexture (point.x, point.y + height - iEdgeHeight, point.x + iEdgeWidth, point.y + height, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());
	}

	/**
	 * Draws the current task
	 * 
	 * @param x
	 * @param y
	 * @param mousePanel
	 */
	private void renderTask () {
		if (Game.getCurrentState () != Game.STATE_CREATING_TASK) {
			return;
		}

		Task task = Game.getCurrentTask ();
		if (task == null) {
			return;
		}

		String taskString = task.toString ();
		int taskX = MessagesUIPanel.messageIconPoints[0].x;
		int taskY = MessagesUIPanel.messageIconPoints[0].y + MessagesUIPanel.messageTiles[0].getTileHeight () + PIXELS_TO_BORDER;
		int taskWidth = UtilFont.getWidth (taskString);
		int taskHeight = UtilFont.MAX_HEIGHT;

		// Render del icono de la tarea
		if (Game.getCurrentState () == Game.STATE_CREATING_TASK) {
			Tile tile = Game.getCurrentTask ().getTile ();
			if (tile != null) {
				// XAVI GL11.glColor4f (1, 1, 1, 1);

				// Round button
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, tileBottomItem.getTextureID ());
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);
				drawTile (tileBottomItem, taskX, taskY, BOTTOM_ITEM_WIDTH, BOTTOM_ITEM_HEIGHT, false);
				UtilsGL.glEnd ();

				// Icon
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, tile.getTextureID ());
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);
				drawTile (tile, taskX, taskY, BOTTOM_ITEM_WIDTH, BOTTOM_ITEM_HEIGHT, false);
				UtilsGL.glEnd ();

				taskX += (BOTTOM_ITEM_WIDTH + PIXELS_TO_BORDER);
				taskY += (BOTTOM_ITEM_HEIGHT / 4);
			}
		}

		GL11.glBindTexture (GL11.GL_TEXTURE_2D, UIPanel.BLACK_TILE.getTextureID ());
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UtilsGL.drawTexture (taskX, taskY, taskX + taskWidth + 2, taskY + taskHeight + 2, UIPanel.BLACK_TILE.getTileSetTexX0 (), UIPanel.BLACK_TILE.getTileSetTexY0 (), UIPanel.BLACK_TILE.getTileSetTexX1 (), UIPanel.BLACK_TILE.getTileSetTexY1 ());
		UtilsGL.glEnd ();

		// Texto
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UtilsGL.drawString (taskString, taskX + 1, taskY + 1);
		UtilsGL.glEnd ();
	}

	/**
	 * Draws the tutorial button if it is enabled
	 * 
	 * @param mousePanel
	 */
	private void renderTutorialButton (int mousePanel, boolean bCheckBlink) {
		if (Game.getCurrentMissionData () == null || Game.getCurrentMissionData ().getTutorialFlows ().size () == 0) {
			return;
		}

		// Render del icono del tutorial
		// Round button
		int iCurrentTexture = tileBottomItem.getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		if (bCheckBlink) {
			if (imagesPanel == null || (ImagesPanel.getCurrentFlowIndex () == 0 && !ImagesPanel.isVisible ())) {
				UtilsGL.setColorRed ();
			}
		}
		drawTile (tileBottomItem, iconTutorialPoint.x, iconTutorialPoint.y, BOTTOM_ITEM_WIDTH, BOTTOM_ITEM_HEIGHT, (mousePanel == MOUSE_TUTORIAL_ICON));
		if (bCheckBlink) {
			if (imagesPanel == null || (ImagesPanel.getCurrentFlowIndex () == 0 && !ImagesPanel.isVisible ())) {
				UtilsGL.unsetColor ();
			}
		}

		// Icon
		iCurrentTexture = UtilsGL.setTexture (iCurrentTexture, tileIconTutorial.getTextureID ());
		drawTile (tileIconTutorial, iconTutorialPoint.x, iconTutorialPoint.y, BOTTOM_ITEM_WIDTH, BOTTOM_ITEM_HEIGHT, (mousePanel == MOUSE_TUTORIAL_ICON));

		UtilsGL.glEnd ();
	}

	/**
	 * Draws the tooltip
	 * 
	 * @param x
	 * @param y
	 * @param mousePanel
	 */
	private void renderTooltip (int x, int y, int mousePanel) {
		if (mousePanel == MOUSE_NONE || mousePanel == MOUSE_PRODUCTION_PANEL || mousePanel == MOUSE_PRIORITIES_PANEL) {
			return;
		}

		String tooltip = null;
		int tooltipX = 0, tooltipY = 0;

		if (typingPanel != null) {
			// TYPING PANEL

		} else {
			// TYPING PANEL NO ACTIVO

			// Bottom
			if (BottomMenuUIPanel.isBottomMenuPanelActive ()) {
				if (mousePanel == MOUSE_BOTTOM_ITEMS) {
					int iItem = BottomMenuUIPanel.isMouseOnBottomItems (x, y);
					if (iItem != -1) {
						SmartMenu item = BottomMenuUIPanel.currentMenu.getItems ().get (iItem + BottomMenuUIPanel.bottomPanelItemIndex);
						tooltip = item.getName ();
						if (item.getType () == SmartMenu.TYPE_ITEM && (iItem + BottomMenuUIPanel.bottomPanelItemIndex) >= 0 && (iItem + BottomMenuUIPanel.bottomPanelItemIndex) <= 9) {
							switch (iItem + BottomMenuUIPanel.bottomPanelItemIndex) {
								case 0:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_1);
									break;
								case 1:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_2);
									break;
								case 2:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_3);
									break;
								case 3:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_4);
									break;
								case 4:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_5);
									break;
								case 5:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_6);
									break;
								case 6:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_7);
									break;
								case 7:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_8);
									break;
								case 8:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_9);
									break;
								case 9:
									tooltip += UtilsKeyboard.getTooltip (UtilsKeyboard.FN_BOT_10);
									break;
							}
						}
						if (tooltip != null) {
							tooltipX = x - (UtilFont.getWidth (tooltip)) / 2;
							tooltipY = BottomMenuUIPanel.bottomPanelY - (2 * UtilFont.MAX_HEIGHT);
						}
					}
				} else if (mousePanel == MOUSE_BOTTOM_SUBITEMS) {
					int iItem = BottomMenuUIPanel.isMouseOnBottomSubItems (x, y);
					if (iItem != -1) {
						SmartMenu item = BottomMenuUIPanel.bottomSubPanelMenu.getItems ().get (iItem);
						if (item.getPrerequisites () != null && item.getPrerequisites ().size () > 0) {
							MainPanel.renderMessages (x, BottomMenuUIPanel.bottomSubPanelPoint.y - (item.getPrerequisites ().size () * (UtilFont.MAX_HEIGHT + 5)), renderWidth, renderHeight, Tile.TERRAIN_ICON_WIDTH / 2, item.getPrerequisites (), item.getPrerequisitesColor ());
						} else {
							tooltip = item.getName ();
							if (tooltip != null) {
								tooltipX = x - (UtilFont.getWidth (tooltip)) / 2;
								tooltipY = BottomMenuUIPanel.bottomSubPanelPoint.y - (2 * UtilFont.MAX_HEIGHT);
							}
						}
					}
				}
			}

			// Right menu
			if (tooltip == null && RightMenuUIPanel.isMenuPanelActive ()) {
				if (mousePanel == MOUSE_MENU_PANEL_ITEMS) {
					int iItem = RightMenuUIPanel.isMouseOnMenuItems (x, y);
					if (iItem != -1) {
						SmartMenu item = RightMenuUIPanel.menuPanelMenu.getItems ().get (iItem);
						if (item.getPrerequisites () != null && item.getPrerequisites ().size () > 0) {
							MainPanel.renderMessages (RightMenuUIPanel.menuPanelPoint.x, y, renderWidth, renderHeight, Tile.TERRAIN_ICON_WIDTH / 2, item.getPrerequisites (), item.getPrerequisitesColor ());
						} else {
							tooltip = item.getName ();
							if (tooltip != null) {
								tooltipX = RightMenuUIPanel.menuPanelPoint.x - (UtilFont.getWidth (tooltip));
								tooltipY = y;
							}
						}
					}
				}
			}

			// Production
			if (tooltip == null && ProductionUIPanel.isProductionPanelActive ()) {
				if (mousePanel == MOUSE_PRODUCTION_PANEL_ITEMS || mousePanel == MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED || mousePanel == MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED) {
					Point p = ProductionUIPanel.isMouseOnProductionItems (x, y);
					if (p != null) {
						if (p.x == MOUSE_PRODUCTION_PANEL_ITEMS) {
							SmartMenu item = ProductionUIPanel.productionPanelMenu.getItems ().get (p.y);
							if (item.getPrerequisites () != null && item.getPrerequisites ().size () > 0) {
								if ((ProductionUIPanel.productionPanelPoint.x + ProductionUIPanel.PRODUCTION_PANEL_WIDTH) > renderWidth) {
									MainPanel.renderMessages (renderWidth, y, renderWidth, renderHeight, 0, item.getPrerequisites (), item.getPrerequisitesColor ());
								} else {
									MainPanel.renderMessages (ProductionUIPanel.productionPanelPoint.x + ProductionUIPanel.PRODUCTION_PANEL_WIDTH, y, renderWidth, renderHeight, 0, item.getPrerequisites (), item.getPrerequisitesColor ());
								}
							} else {
								tooltip = item.getName ();
								if (tooltip != null) {
									tooltipX = ProductionUIPanel.productionPanelPoint.x + ProductionUIPanel.PRODUCTION_PANEL_WIDTH;
									tooltipY = y;
								}
							}
						} else if (p.x == MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED || p.x == MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED) {
							SmartMenu item = ProductionUIPanel.productionPanelMenu.getItems ().get (p.y);
							if (item.getCommand ().equals (CommandPanel.COMMAND_QUEUE)) {
								String sParam = item.getParameter ();
								ActionManagerItem ami = ActionManager.getItem (sParam);
								if (ami != null) {
									if (ami.isInverted ()) {
										tooltip = Messages.getString ("UIPanel.68"); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("UIPanel.72"); //$NON-NLS-1$
									}
									tooltipX = x + 32;
									tooltipY = y;
								}
							}
						}
					}
				}
			}

			// Priorities
			if (tooltip == null && PrioritiesUIPanel.isPrioritiesPanelActive ()) {
				if (mousePanel == MOUSE_PRIORITIES_PANEL_ITEMS) {
					Point p = PrioritiesUIPanel.isMouseOnPrioritiesItems (x, y);
					if (p != null && p.x == MOUSE_PRIORITIES_PANEL_ITEMS) {
						if (p.y == (PrioritiesUIPanel.PRIORITIES_PANEL_NUM_ITEMS - 1)) {
							// Back
							tooltip = Messages.getString ("UIPanel.13"); //$NON-NLS-1$
						} else {
							tooltip = ActionPriorityManager.getItem (ActionPriorityManager.getPrioritiesList ().get (p.y)).getName ();
						}
						tooltipX = PrioritiesUIPanel.prioritiesPanelPoint.x + PrioritiesUIPanel.PRIORITIES_PANEL_WIDTH;
						tooltipY = y;
					}
				}
			}

			// Trade
			if (tooltip == null && TradeUIPanel.isTradePanelActive ()) {
				CaravanData caravanData = Game.getWorld ().getCurrentCaravanData ();
				boolean bTrading = (caravanData != null && caravanData.getStatus () == CaravanData.STATUS_TRADING);

				if (!bTrading && mousePanel == MOUSE_TRADE_PANEL_BUTTONS_CARAVAN) {
					Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
					if (p != null && p.x == MOUSE_TRADE_PANEL_BUTTONS_CARAVAN && (p.y + TradeUIPanel.tradePanel.getIndexButtonsCaravan ()) < TradeUIPanel.tradePanel.getMenuCaravan ().getItems ().size ()) {
						tooltip = TradeUIPanel.tradePanel.getMenuCaravan ().getItems ().get (p.y + TradeUIPanel.tradePanel.getIndexButtonsCaravan ()).getName ();
						tooltipX = x + 32;
						tooltipY = y;
					}
				} else if (mousePanel == MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN) {
					if (caravanData != null) {
						Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
						if (p != null && p.x == MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN && (p.y + TradeUIPanel.tradePanel.getIndexButtonsToBuyCaravan ()) < caravanData.getMenuCaravanToBuy ().getItems ().size ()) {
							tooltip = caravanData.getMenuCaravanToBuy ().getItems ().get (p.y + TradeUIPanel.tradePanel.getIndexButtonsToBuyCaravan ()).getName ();
							tooltipX = x + 32;
							tooltipY = y;
						}
					}
				} else if (!bTrading && mousePanel == MOUSE_TRADE_PANEL_BUTTONS_TOWN) {
					Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
					if (p != null && p.x == MOUSE_TRADE_PANEL_BUTTONS_TOWN && (p.y + TradeUIPanel.tradePanel.getIndexButtonsTown ()) < TradeUIPanel.tradePanel.getMenuTown ().getItems ().size ()) {
						tooltip = TradeUIPanel.tradePanel.getMenuTown ().getItems ().get (p.y + TradeUIPanel.tradePanel.getIndexButtonsTown ()).getName ();
						tooltipX = x + 32;
						tooltipY = y;
					}
				} else if (mousePanel == MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN) {
					if (caravanData != null) {
						Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
						if (p != null && p.x == MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN && (p.y + TradeUIPanel.tradePanel.getIndexButtonsToSellTown ()) < caravanData.getMenuTownToSell ().getItems ().size ()) {
							tooltip = caravanData.getMenuTownToSell ().getItems ().get (p.y + TradeUIPanel.tradePanel.getIndexButtonsToSellTown ()).getName ();
							tooltipX = x + 32;
							tooltipY = y;
						}
					}
				} else if (mousePanel == MOUSE_TRADE_PANEL_BUTTONS_CLOSE) {
					tooltip = Messages.getString ("UIPanel.19"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_TRADE_PANEL_ICON_BUY) {
					tooltip = Messages.getString ("UIPanel.33"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_TRADE_PANEL_ICON_SELL) {
					tooltip = Messages.getString ("UIPanel.35"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (!bTrading && mousePanel == MOUSE_TRADE_PANEL_BUTTONS_CONFIRM) {
					tooltip = Messages.getString ("UIPanel.36"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				}
			}

			// Professions
			if (tooltip == null && ProfessionsUIPanel.isProfessionsPanelActive ()) {
				if (mousePanel == MOUSE_PROFESSIONS_PANEL_BUTTONS_ITEMS) {
					Point p = ProfessionsUIPanel.isMouseOnProfessionsButtons (x, y);
					if (p != null && p.y > -1 && p.y < ProfessionsUIPanel.menuProfessions.getItems ().size ()) {
						String sName = ProfessionsUIPanel.menuProfessions.getItems ().get (p.y).getName ();
						if (sName != null) {
							tooltip = sName;
							tooltipX = x;
							tooltipY = y + UtilFont.MAX_HEIGHT * 2;
						}
					}
				} else if (mousePanel == MOUSE_PROFESSIONS_PANEL_BUTTONS_CLOSE) {
					tooltip = Messages.getString ("UIPanel.19"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				}
			}

			// Pile
			if (tooltip == null && PileUIPanel.isPilePanelActive ()) {
				if (mousePanel == MOUSE_PILE_PANEL_BUTTONS_ITEMS) {
					Point p = PileUIPanel.isMouseOnPileButtons (x, y);
					if (p != null && p.y > -1 && p.y < PileUIPanel.menuPile.getItems ().size ()) {
						String sName = PileUIPanel.menuPile.getItems ().get (p.y).getName ();
						if (sName != null) {
							tooltip = sName;
							tooltipX = x;
							tooltipY = y + UtilFont.MAX_HEIGHT * 2;
						}
					}
				} else if (mousePanel == MOUSE_PILE_PANEL_BUTTONS_CLOSE) {
					tooltip = Messages.getString ("UIPanel.19"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_COPY) {
					if (PileUIPanel.pilePanelIsContainer) {
						tooltip = Messages.getString("UIPanel.80"); //$NON-NLS-1$
					} else {
						tooltip = Messages.getString("UIPanel.82"); //$NON-NLS-1$
					}
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK) {
					if (PileUIPanel.pilePanelIsLocked) {
						tooltip = Messages.getString("UIPanel.86"); //$NON-NLS-1$
					} else {
						tooltip = Messages.getString("UIPanel.85"); //$NON-NLS-1$
					}
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_ALL) {
					tooltip = Messages.getString("UIPanel.87"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_UNLOCK_ALL) {
					tooltip = Messages.getString("UIPanel.88"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				}
			}

			// Mats
			if (tooltip == null && MatsUIPanel.isMatsPanelActive ()) {
				if (mousePanel == MOUSE_MATS_PANEL_BUTTONS_GROUPS) {
					Point p = MatsUIPanel.isMouseOnMatsButtons (x, y);
					if (p != null && p.y > -1 && p.y < MatsPanelData.nameGroups.size ()) {
						tooltip = MatsPanelData.nameGroups.get (p.y);
						tooltipX = x + 32;
						tooltipY = y;
					}
				} else if (mousePanel == MOUSE_MATS_PANEL_BUTTONS_ITEMS) {
					Point p = MatsUIPanel.isMouseOnMatsButtons (x, y);
					if (p != null && p.y > -1 && p.y < MatsPanelData.tileGroups.get (MatsUIPanel.getMatsPanelActive ()).size ()) {
						String sIniHeader = MatsPanelData.tileGroups.get (MatsUIPanel.getMatsPanelActive ()).get (p.y).getIniHeader ();
						if (sIniHeader != null) {
							ItemManagerItem imi = ItemManager.getItem (sIniHeader);
							if (imi != null && imi.getName () != null) {
								tooltip = imi.getName ();
								tooltipX = x + 32;
								tooltipY = y;
							}
						}
					}
				} else if (mousePanel == MOUSE_MATS_PANEL_BUTTONS_CLOSE) {
					tooltip = Messages.getString ("UIPanel.19"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				}
			}

			// Livings
			if (tooltip == null && LivingsUIPanel.isLivingsPanelActive ()) {
				if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_CLOSE) {
					tooltip = Messages.getString ("UIPanel.19"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if ((mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP || mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN) && LivingsUIPanel.livingsPanelCitizensGroupActive == -1 && LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS) {
					tooltip = Messages.getString ("UIPanel.73"); //$NON-NLS-1$
					tooltipX = x - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = LivingsUIPanel.livingsPanelIconRestrictUpPoint.y + tileIconLevelUp.getTileHeight ();
				} else if ((mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP || mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN) && LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_HEROES) {
					tooltip = Messages.getString ("UIPanel.74"); //$NON-NLS-1$
					tooltipX = x - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = LivingsUIPanel.livingsPanelIconRestrictUpPoint.y + tileIconLevelUp.getTileHeight ();
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS && LivingsUIPanel.livingsPanelCitizensGroupActive == -1) {
					tooltip = Messages.getString ("UIPanel.63"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE) {
					tooltip = Messages.getString ("UIPanel.65"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER) {
					tooltip = Messages.getString ("Citizen.27"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN) {
					tooltip = Messages.getString ("Citizen.26"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD) {
					tooltip = Messages.getString ("Citizen.32"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL) {
					tooltip = Messages.getString ("Citizen.34"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS) {
					tooltip = Messages.getString ("Citizen.35"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP) {
					tooltip = Messages.getString ("UIPanel.43"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_CGROUP_NOGROUP) {
					tooltip = Messages.getString ("UIPanel.66") + " (" + Game.getWorld ().getCitizenGroups ().getCitizensWithoutGroup ().size () + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					tooltipX = x - UtilFont.getWidth (tooltip) / 2;
					tooltipY = y - UtilFont.MAX_HEIGHT - 2;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_CGROUP_GROUP) {
					Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
					if (p != null && p.y >= 0 && p.y < CitizenGroups.MAX_GROUPS) {
						CitizenGroupData cgd = Game.getWorld ().getCitizenGroups ().getGroup (p.y);
						if (cgd != null) {
							tooltip = cgd.getName () + " (" + cgd.getLivingIDs ().size () + ")"; //$NON-NLS-1$ //$NON-NLS-2$
							tooltipX = x - UtilFont.getWidth (tooltip) / 2;
							tooltipY = y - UtilFont.MAX_HEIGHT - 2;
						}
					}
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_RENAME) {
					tooltip = Messages.getString ("UIPanel.54"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_AUTOEQUIP) {
					tooltip = Messages.getString ("UIPanel.59"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_DISBAND) {
					tooltip = Messages.getString ("UIPanel.60"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_CHANGE_JOBS) {
					tooltip = Messages.getString ("UIPanel.69"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD) {
					tooltip = Messages.getString ("UIPanel.47"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE) {
					tooltip = Messages.getString ("UIPanel.51"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SGROUP_NOGROUP) {
					tooltip = Messages.getString ("UIPanel.53") + " (" + Game.getWorld ().getSoldierGroups ().getSoldiersWithoutGroup ().size () + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					tooltipX = x - UtilFont.getWidth (tooltip) / 2;
					tooltipY = y - UtilFont.MAX_HEIGHT - 2;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SGROUP_GROUP) {
					Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
					if (p != null && p.y >= 0 && p.y < SoldierGroups.MAX_GROUPS) {
						SoldierGroupData sgd = Game.getWorld ().getSoldierGroups ().getGroup (p.y);
						if (sgd != null) {
							tooltip = sgd.getName () + " (" + sgd.getLivingIDs ().size () + ")"; //$NON-NLS-1$ //$NON-NLS-2$
							tooltipX = x - UtilFont.getWidth (tooltip) / 2;
							tooltipY = y - UtilFont.MAX_HEIGHT - 2;
						}
					}
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_RENAME) {
					tooltip = Messages.getString ("UIPanel.54"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_GUARD) {
					tooltip = Messages.getString ("UIPanel.55"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_PATROL) {
					tooltip = Messages.getString ("UIPanel.57"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_BOSS) {
					tooltip = Messages.getString ("UIPanel.58"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_AUTOEQUIP) {
					tooltip = Messages.getString ("UIPanel.59"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_DISBAND) {
					tooltip = Messages.getString ("UIPanel.60"); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS || mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD || mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY || mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS || mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET || mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON) {
					Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
					int iIndex = LivingsUIPanel.getLivingsIndex ();
					ArrayList<Integer> alLivings = LivingsUIPanel.getLivings ();
					if (alLivings != null && p != null && (p.y + iIndex) >= 0 && (p.y + iIndex) < alLivings.size ()) {
						LivingEntity le = World.getLivingEntityByID (alLivings.get ((p.y + iIndex)));
						if (le != null) {
							if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS) {
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									Citizen citizen = (Citizen) le;
									int iNumEffects = le.getLivingEntityData ().getEffects ().size ();
									ArrayList<String> alMessages = new ArrayList<String> (6 + iNumEffects);
									ArrayList<ColorGL> alColors = new ArrayList<ColorGL> (6 + iNumEffects);

									alMessages.add (citizen.getCitizenData ().getFullName ());
									alColors.add (ColorGL.YELLOW);
									alMessages.add (citizen.getLivingEntityData ().toString ());
									alColors.add (ColorGL.WHITE);
									if (citizen.getCurrentTask () != null) {
										alMessages.add (Messages.getString ("Citizen.7") + citizen.getCurrentTask ()); //$NON-NLS-1$
										alColors.add (ColorGL.WHITE);
									}

									// Level / Xp
									if (citizen.getSoldierData ().isSoldier ()) {
										alMessages.add (Messages.getString ("Hero.4") + citizen.getSoldierData ().getLevel () + " (" + citizen.getSoldierData ().getXp () + Messages.getString ("Hero.5") + citizen.getSoldierData ().getXpPCT () + "%)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
										alColors.add (ColorGL.WHITE);
									}

									alMessages.add (Messages.getString ("UIPanel.40") + citizen.getCitizenData ().getHappiness () + " / 100"); //$NON-NLS-1$ //$NON-NLS-2$
									alColors.add (ColorGL.WHITE);
									alMessages.add (Messages.getString ("UIPanel.49") + citizen.getCitizenData ().getHungry () + " / " + citizen.getCitizenData ().getMaxHungry ()); //$NON-NLS-1$ //$NON-NLS-2$
									alColors.add (ColorGL.WHITE);
									alMessages.add (Messages.getString ("UIPanel.52") + citizen.getCitizenData ().getSleep () + " / " + citizen.getCitizenData ().getMaxSleep ()); //$NON-NLS-1$ //$NON-NLS-2$
									alColors.add (ColorGL.WHITE);

									// Effects
									EffectData eData;
									for (int e = 0; e < iNumEffects; e++) {
										eData = le.getLivingEntityData ().getEffects ().get (e);
										alMessages.add (EffectManager.getItem (eData.getEffectID ()).getName ());
										alColors.add (ColorGL.ORANGE);
									}

									MainPanel.renderMessages (x + 32, y, MainPanel.renderWidth, MainPanel.renderHeight, 2, alMessages, alColors);
									return;
								} else if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_HEROES) {
									Hero hero = (Hero) le;
									int iNumEffects = le.getLivingEntityData ().getEffects ().size ();
									ArrayList<String> alMessages = new ArrayList<String> (3 + iNumEffects);
									ArrayList<ColorGL> alColors = new ArrayList<ColorGL> (3 + iNumEffects);

									alMessages.add (hero.getCitizenData ().getFullName ());
									alMessages.add (hero.getLivingEntityData ().toString ());
									alMessages.add (Messages.getString ("Hero.4") + hero.getHeroData ().getLevel () + " (" + hero.getHeroData ().getXp () + Messages.getString ("Hero.5") + hero.getHeroData ().getXpPCT () + "%)"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
									alColors.add (ColorGL.YELLOW);
									alColors.add (ColorGL.WHITE);
									alColors.add (ColorGL.ORANGE);

									// Friendship
									String sHeroFriends = HeroData.getFriendshipString (hero);
									if (sHeroFriends != null) {
										alMessages.add (sHeroFriends);
										alColors.add (ColorGL.WHITE);
									}

									// Effects
									EffectData eData;
									for (int e = 0; e < iNumEffects; e++) {
										eData = le.getLivingEntityData ().getEffects ().get (e);
										alMessages.add (EffectManager.getItem (eData.getEffectID ()).getName ());
										alColors.add (ColorGL.ORANGE);
									}

									MainPanel.renderMessages (x + 32, y, MainPanel.renderWidth, MainPanel.renderHeight, 2, alMessages, alColors);
									return;
								}
							} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD) {
								// Head
								EquippedData equippedData = le.getEquippedData ();
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									if (equippedData.isWearing (MilitaryItem.LOCATION_HEAD)) {
										tooltip = Messages.getString ("UIPanel.41") + equippedData.getHead ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("UIPanel.42"); //$NON-NLS-1$
									}
								} else {
									if (equippedData.isWearing (MilitaryItem.LOCATION_HEAD)) {
										tooltip = Messages.getString ("Citizen.21") + equippedData.getHead ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("Citizen.14"); //$NON-NLS-1$
									}
								}
								tooltipX = x + 32;
								tooltipY = y;
							} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY) {
								// Body
								EquippedData equippedData = le.getEquippedData ();
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									if (equippedData.isWearing (MilitaryItem.LOCATION_BODY)) {
										tooltip = Messages.getString ("UIPanel.41") + equippedData.getBody ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("UIPanel.44"); //$NON-NLS-1$
									}
								} else {
									if (equippedData.isWearing (MilitaryItem.LOCATION_BODY)) {
										tooltip = Messages.getString ("Citizen.22") + equippedData.getBody ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("Citizen.15"); //$NON-NLS-1$
									}
								}
								tooltipX = x + 32;
								tooltipY = y;
							} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS) {
								// Legs
								EquippedData equippedData = le.getEquippedData ();
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									if (equippedData.isWearing (MilitaryItem.LOCATION_LEGS)) {
										tooltip = Messages.getString ("UIPanel.41") + equippedData.getLegs ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("UIPanel.46"); //$NON-NLS-1$
									}
								} else {
									if (equippedData.isWearing (MilitaryItem.LOCATION_LEGS)) {
										tooltip = Messages.getString ("Citizen.23") + equippedData.getLegs ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("Citizen.16"); //$NON-NLS-1$
									}
								}
								tooltipX = x + 32;
								tooltipY = y;
							} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET) {
								// Feet
								EquippedData equippedData = le.getEquippedData ();
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									if (equippedData.isWearing (MilitaryItem.LOCATION_FEET)) {
										tooltip = Messages.getString ("UIPanel.41") + equippedData.getFeet ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("UIPanel.48"); //$NON-NLS-1$
									}
								} else {
									if (equippedData.isWearing (MilitaryItem.LOCATION_FEET)) {
										tooltip = Messages.getString ("Citizen.24") + equippedData.getFeet ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("Citizen.17"); //$NON-NLS-1$
									}
								}
								tooltipX = x + 32;
								tooltipY = y;
							} else if (mousePanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON) {
								// Weapon
								EquippedData equippedData = le.getEquippedData ();
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									if (equippedData.isWearing (MilitaryItem.LOCATION_WEAPON)) {
										tooltip = Messages.getString ("UIPanel.41") + equippedData.getWeapon ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("UIPanel.50"); //$NON-NLS-1$
									}
								} else {
									if (equippedData.isWearing (MilitaryItem.LOCATION_WEAPON)) {
										tooltip = Messages.getString ("Citizen.25") + equippedData.getWeapon ().getExtendedTilename (); //$NON-NLS-1$
									} else {
										tooltip = Messages.getString ("Citizen.18"); //$NON-NLS-1$
									}
								}
								tooltipX = x + 32;
								tooltipY = y;
							}
						}
					}
				}
			}

			if (tooltip == null) {
				if (mousePanel == MOUSE_DATEPANEL) {
					tooltip = Messages.getString ("UIPanel.29"); //$NON-NLS-1$
					tooltipX = datePanelPoint.x + tileDatePanel.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = datePanelPoint.y + tileDatePanel.getTileHeight ();
				} else if (mousePanel == MOUSE_ICON_LEVEL_UP) {
					tooltip = Messages.getString ("UIPanel.0") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_LEVEL_UP); //$NON-NLS-1$
					tooltipX = iconLevelUpPoint.x + tileIconLevelUp.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconLevelUpPoint.y - UtilFont.MAX_HEIGHT;
				} else if (mousePanel == MOUSE_ICON_LEVEL_DOWN) {
					tooltip = Messages.getString ("UIPanel.2") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_LEVEL_DOWN); //$NON-NLS-1$
					tooltipX = iconLevelDownPoint.x + tileIconLevelDown.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconLevelDownPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_LEVEL) {
					tooltip = Messages.getString ("UIPanel.30"); //$NON-NLS-1$
					tooltipX = iconLevelPoint.x + tileIconLevel.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconLevelPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_CITIZEN_PREVIOUS) {
					tooltip = Messages.getString ("UIPanel.3") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_PREVIOUS_CITIZEN); //$NON-NLS-1$
					tooltipX = iconCitizenPreviousPoint.x + tileIconCitizenPrevious.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconCitizenPreviousPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_ICON_CITIZEN_NEXT) {
					tooltip = Messages.getString ("UIPanel.4") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_NEXT_CITIZEN); //$NON-NLS-1$
					tooltipX = iconCitizenNextPoint.x + tileIconCitizenNext.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconCitizenNextPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_ICON_SOLDIER_PREVIOUS) {
					tooltip = Messages.getString ("UIPanel.5") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_PREVIOUS_SOLDIER); //$NON-NLS-1$
					tooltipX = iconSoldierPreviousPoint.x + tileIconSoldierPrevious.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconSoldierPreviousPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_ICON_SOLDIER_NEXT) {
					tooltip = Messages.getString ("UIPanel.6") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_NEXT_SOLDIER); //$NON-NLS-1$
					tooltipX = iconSoldierNextPoint.x + tileIconSoldierNext.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconSoldierNextPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_ICON_HERO_PREVIOUS) {
					tooltip = Messages.getString ("UIPanel.22") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_PREVIOUS_HERO); //$NON-NLS-1$
					tooltipX = iconHeroPreviousPoint.x + tileIconHeroPrevious.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconHeroPreviousPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_ICON_HERO_NEXT) {
					tooltip = Messages.getString ("UIPanel.23") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_NEXT_HERO); //$NON-NLS-1$
					tooltipX = iconHeroNextPoint.x + tileIconHeroNext.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconHeroNextPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_INFO_NUM_CITIZENS) {
					int happinessMin = (World.getCitizenIDs ().size () + World.getSoldierIDs ().size ()) * 2;
					if (happinessMin < 20) {
						happinessMin = 20;
					} else if (happinessMin > 80) {
						happinessMin = 80;
					}
					tooltip = Messages.getString ("UIPanel.8") + " (" + Messages.getString ("UIPanel.76") + ": " + World.getHappinessAverage () + " " + Messages.getString ("UIPanel.81") + ": " + happinessMin + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
					tooltipX = iconNumCitizensBackgroundPoint.x + tileBottomItem.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconNumCitizensBackgroundPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_INFO_NUM_SOLDIERS) {
					tooltip = Messages.getString ("UIPanel.9"); //$NON-NLS-1$
					tooltipX = iconNumSoldiersBackgroundPoint.x + tileBottomItem.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconNumSoldiersBackgroundPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_INFO_NUM_HEROES) {
					tooltip = Messages.getString ("UIPanel.24"); //$NON-NLS-1$
					tooltipX = iconNumHeroesBackgroundPoint.x + tileBottomItem.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconNumHeroesBackgroundPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_INFO_CARAVAN) {
					tooltip = Messages.getString ("UIPanel.25") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_SHOW_TRADE); //$NON-NLS-1$
					tooltipX = iconCaravanBackgroundPoint.x + tileBottomItem.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconCaravanBackgroundPoint.y + tileBottomItem.getTileHeight ();
				} else if (mousePanel == MOUSE_ICON_PRIORITIES) {
					tooltip = Messages.getString ("UIPanel.14") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_SHOW_PRIORITIES); //$NON-NLS-1$
					tooltipX = iconPrioritiesPoint.x + tileIconPriorities.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconPrioritiesPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_MATS) {
					tooltip = Messages.getString ("UIPanel.32") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_SHOW_STOCK); //$NON-NLS-1$
					tooltipX = iconMatsPoint.x + tileIconMats.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconMatsPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_GRID) {
					tooltip = Messages.getString ("UIPanel.12") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_TOGGLE_GRID); //$NON-NLS-1$
					tooltipX = iconGridPoint.x + tileIconGrid.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconGridPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_MINIBLOCKS) {
					tooltip = Messages.getString ("UIPanel.16") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_TOGGLE_MINIBLOCKS); //$NON-NLS-1$
					tooltipX = iconMiniblocksPoint.x + tileIconMiniblocks.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconMiniblocksPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_FLATMOUSE) {
					tooltip = Messages.getString ("UIPanel.45") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_TOGGLE_FLAT_MOUSE); //$NON-NLS-1$
					tooltipX = iconFlatMousePoint.x + tileIconFlatMouse.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconFlatMousePoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_3DMOUSE) {
					tooltip = Messages.getString ("UtilsKeyboard.16") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_TOGGLE_3D_MOUSE); //$NON-NLS-1$
					tooltipX = icon3DMousePoint.x + tileIcon3DMouse.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = icon3DMousePoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_PAUSE_RESUME) {
					tooltip = Messages.getString ("UIPanel.10") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_PAUSE); //$NON-NLS-1$
					tooltipX = iconPauseResumePoint.x + tileIconPause.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconPauseResumePoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_SETTINGS) {
					tooltip = Messages.getString ("UIPanel.11"); //$NON-NLS-1$
					tooltipX = iconSettingsPoint.x + tileIconSettings.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconSettingsPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_LOWER_SPEED) {
					tooltip = Messages.getString ("UIPanel.1") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_SPEED_DOWN); //$NON-NLS-1$
					tooltipX = iconLowerSpeedPoint.x + tileIconLowerSpeed.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconLowerSpeedPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_ICON_INCREASE_SPEED) {
					tooltip = Messages.getString ("UIPanel.15") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_SPEED_UP); //$NON-NLS-1$
					tooltipX = iconIncreaseSpeedPoint.x + tileIconIncreaseSpeed.getTileWidth () / 2 - (UtilFont.getWidth (tooltip) / 2);
					tooltipY = iconIncreaseSpeedPoint.y + UtilFont.MAX_HEIGHT * 2;
				} else if (mousePanel == MOUSE_TUTORIAL_ICON) {
					tooltip = Messages.getString ("UIPanel.75") + UtilsKeyboard.getTooltip (UtilsKeyboard.FN_SHOW_MISSION); //$NON-NLS-1$
					tooltipX = x + 32;
					tooltipY = y;
				} else if (mousePanel == MOUSE_MESSAGES_ICON_ANNOUNCEMENT) {
					ArrayList<String> alMessages = new ArrayList<String> (4);
					ArrayList<ColorGL> alColors = new ArrayList<ColorGL> (4);
					alMessages.add (Messages.getString ("UIPanel.26")); //$NON-NLS-1$
					alColors.add (ColorGL.WHITE);

					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_ANNOUNCEMENT, 2);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_ANNOUNCEMENT, 2));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_ANNOUNCEMENT, 1);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_ANNOUNCEMENT, 1));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_ANNOUNCEMENT, 0);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_ANNOUNCEMENT, 0));
					}

					tooltipX = MessagesUIPanel.messageIconPoints[0].x;
					tooltipY = MessagesUIPanel.messageIconPoints[0].y + UtilFont.MAX_HEIGHT * 2;
					MainPanel.renderMessages (tooltipX, tooltipY, MainPanel.renderWidth, MainPanel.renderHeight, 2, alMessages, alColors);
					return;
				} else if (mousePanel == MOUSE_MESSAGES_ICON_COMBAT) {
					ArrayList<String> alMessages = new ArrayList<String> (4);
					ArrayList<ColorGL> alColors = new ArrayList<ColorGL> (4);
					alMessages.add (Messages.getString ("UIPanel.27")); //$NON-NLS-1$
					alColors.add (ColorGL.WHITE);

					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_COMBAT, 2);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_COMBAT, 2));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_COMBAT, 1);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_COMBAT, 1));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_COMBAT, 0);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_COMBAT, 0));
					}

					tooltipX = MessagesUIPanel.messageIconPoints[1].x;
					tooltipY = MessagesUIPanel.messageIconPoints[1].y + UtilFont.MAX_HEIGHT * 2;
					MainPanel.renderMessages (tooltipX, tooltipY, MainPanel.renderWidth, MainPanel.renderHeight, 2, alMessages, alColors);
					return;
				} else if (mousePanel == MOUSE_MESSAGES_ICON_HEROES) {
					ArrayList<String> alMessages = new ArrayList<String> (4);
					ArrayList<ColorGL> alColors = new ArrayList<ColorGL> (4);
					alMessages.add (Messages.getString ("UIPanel.28")); //$NON-NLS-1$
					alColors.add (ColorGL.WHITE);

					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_HEROES, 2);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_HEROES, 2));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_HEROES, 1);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_HEROES, 1));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_HEROES, 0);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_HEROES, 0));
					}

					tooltipX = MessagesUIPanel.messageIconPoints[2].x;
					tooltipY = MessagesUIPanel.messageIconPoints[2].y + UtilFont.MAX_HEIGHT * 2;
					MainPanel.renderMessages (tooltipX, tooltipY, MainPanel.renderWidth, MainPanel.renderHeight, 2, alMessages, alColors);
					return;
				} else if (mousePanel == MOUSE_MESSAGES_ICON_SYSTEM) {
					ArrayList<String> alMessages = new ArrayList<String> (4);
					ArrayList<ColorGL> alColors = new ArrayList<ColorGL> (4);
					alMessages.add (Messages.getString ("UIPanel.31")); //$NON-NLS-1$
					alColors.add (ColorGL.WHITE);

					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_SYSTEM, 2);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_SYSTEM, 2));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_SYSTEM, 1);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_SYSTEM, 1));
					}
					tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_SYSTEM, 0);
					if (tooltip != null) {
						alMessages.add (tooltip);
						alColors.add (MessagesPanel.getLastestMessageColor (MessagesPanel.TYPE_SYSTEM, 0));
					}

					tooltipX = MessagesUIPanel.messageIconPoints[3].x;
					tooltipY = MessagesUIPanel.messageIconPoints[3].y + UtilFont.MAX_HEIGHT * 2;
					MainPanel.renderMessages (tooltipX, tooltipY, MainPanel.renderWidth, MainPanel.renderHeight, 2, alMessages, alColors);
					return;
				}
			}
		}

		if (tooltip != null) {
			UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
		}

		if (typingPanel == null) {
			// Multi-lineas tooltip
			if (mousePanel == MOUSE_MESSAGES_ICON_ANNOUNCEMENT) {
				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_ANNOUNCEMENT, 2);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_ANNOUNCEMENT, 1);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_ANNOUNCEMENT, 0);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}
			} else if (mousePanel == MOUSE_MESSAGES_ICON_COMBAT) {
				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_COMBAT, 2);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_COMBAT, 1);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_COMBAT, 0);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}
			} else if (mousePanel == MOUSE_MESSAGES_ICON_HEROES) {
				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_HEROES, 2);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_HEROES, 1);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_HEROES, 0);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}
			} else if (mousePanel == MOUSE_MESSAGES_ICON_SYSTEM) {
				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_SYSTEM, 2);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_SYSTEM, 1);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}

				tooltip = MessagesPanel.getLastestMessage (MessagesPanel.TYPE_SYSTEM, 0);
				if (tooltip != null) {
					tooltipY += UtilFont.MAX_HEIGHT;
					UtilsGL.drawTooltip (tooltip, tooltipX, tooltipY, renderWidth, renderHeight);
				}
			} else if (mousePanel == MOUSE_EVENTS_ICON) {
				ArrayList<EventData> alEvents = Game.getWorld ().getEvents ();
				if (alEvents.size () == 0) {
					tooltip = Messages.getString ("UIPanel.83"); //$NON-NLS-1$
					UtilsGL.drawTooltip (tooltip, iconEventsPoint.x + GlobalEventData.getIcon ().getTileWidth () / 2 - UtilFont.getWidth (tooltip) / 2, iconEventsPoint.y + GlobalEventData.getIcon ().getTileHeight (), renderWidth, renderHeight);
				} else {
					// Obtenemos el tamaño del tooltip
					tooltip = Messages.getString ("UIPanel.84"); //$NON-NLS-1$
					int tooltipWidth = UtilFont.getWidth (tooltip);
					int tooltipHeight = UtilFont.MAX_HEIGHT; // Título

					EventData ed;
					EventManagerItem emi;
					int iAux;
					for (int i = 0; i < alEvents.size (); i++) {
						ed = alEvents.get (i);
						emi = EventManager.getItem (ed.getEventID ());
						if (emi != null) {
							// Alto
							if (emi.getIcon () != null) {
								tooltipHeight += emi.getIcon ().getTileHeight () + 2;

								// Ancho
								iAux = UtilFont.getWidth (emi.getName ()) + emi.getIcon ().getTileWidth ();
								if (iAux > tooltipWidth) {
									tooltipWidth = iAux;
								}
							} else {
								tooltipHeight += UtilFont.MAX_HEIGHT + 2;

								// Ancho
								iAux = UtilFont.getWidth (emi.getName ());
								if (iAux > tooltipWidth) {
									tooltipWidth = iAux;
								}
							}
						}
					}
					tooltipX = iconEventsPoint.x + GlobalEventData.getIcon ().getTileWidth () / 2 - tooltipWidth / 2;
					tooltipY = iconEventsPoint.y + GlobalEventData.getIcon ().getTileHeight ();

					// Renderizamos
					// Fondo
					int iCurrentTexture = UIPanel.tileTooltipBackground.getTextureID ();
					GL11.glColor4f (1, 1, 1, 1);
					GL11.glBindTexture (GL11.GL_TEXTURE_2D, UIPanel.tileTooltipBackground.getTextureID ());
					UtilsGL.glBegin (GL11.GL_QUADS);
					UtilsGL.drawTexture (tooltipX, tooltipY - 4, tooltipX + tooltipWidth + 8, tooltipY + tooltipHeight + 4, UIPanel.tileTooltipBackground.getTileSetTexX0 (), UIPanel.tileTooltipBackground.getTileSetTexY0 (), UIPanel.tileTooltipBackground.getTileSetTexX1 (), UIPanel.tileTooltipBackground.getTileSetTexY1 ());

					// Iconos
					int iCurrentHeight = tooltipY + UtilFont.MAX_HEIGHT + 2;
					for (int i = 0; i < alEvents.size (); i++) {
						ed = alEvents.get (i);
						emi = EventManager.getItem (ed.getEventID ());
						if (emi != null) {
							// Alto
							if (emi.getIcon () != null) {
								iCurrentTexture = UtilsGL.setTexture (emi.getIcon (), iCurrentTexture);
								drawTile (emi.getIcon (), tooltipX, iCurrentHeight, false);
								iCurrentHeight += emi.getIcon ().getTileHeight () + 2;
							} else {
								iCurrentHeight += UtilFont.MAX_HEIGHT + 2;
							}
						}
					}
					UtilsGL.glEnd ();

					// Textos
					GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
					UtilsGL.glBegin (GL11.GL_QUADS);
					iCurrentHeight = tooltipY;
					UtilsGL.drawString (tooltip, tooltipX, iCurrentHeight);
					iCurrentHeight += UtilFont.MAX_HEIGHT + 2;

					for (int i = 0; i < alEvents.size (); i++) {
						ed = alEvents.get (i);
						emi = EventManager.getItem (ed.getEventID ());
						if (emi != null) {
							// Alto
							if (emi.getIcon () != null) {
								UtilsGL.drawString (emi.getName (), tooltipX + emi.getIcon ().getTileWidth () + 4, iCurrentHeight + emi.getIcon ().getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2);
								iCurrentHeight += emi.getIcon ().getTileHeight () + 2;
							} else {
								UtilsGL.drawString (emi.getName (), tooltipX, iCurrentHeight);
								iCurrentHeight += UtilFont.MAX_HEIGHT + 2;
							}
						}
					}

					UtilsGL.glEnd ();
				}
				// } else if (mousePanel == MOUSE_GODS_ICON) {
				// ArrayList<GodData> alGods = Game.getWorld ().getGods ();
				// int iNonHidden = 0;
				// for (int i = 0; i < alGods.size (); i++) {
				// if (!alGods.get (i).isHidden ()) {
				// iNonHidden++;
				// }
				// }
				// if (iNonHidden == 0) {
				//					tooltip = Messages.getString("UIPanel.77"); //$NON-NLS-1$
				// UtilsGL.drawTooltip (tooltip, iconGodsPoint.x + tileIconGods.getTileWidth () / 2 - UtilFont.getWidth (tooltip) / 2, iconGodsPoint.y + tileIconGods.getTileHeight (), renderWidth, renderHeight);
				// } else {
				// // Obtenemos el tamaño del tooltip
				//					tooltip = Messages.getString("UIPanel.78"); //$NON-NLS-1$
				// int tooltipWidth = UtilFont.getWidth (tooltip);
				// int tooltipHeight = UtilFont.MAX_HEIGHT; // Título
				//
				// GodData gd;
				// int iAux;
				// // Alto
				// tooltipHeight += iNonHidden * UtilFont.MAX_HEIGHT + 2;
				// for (int i = 0; i < alGods.size (); i++) {
				// gd = alGods.get (i);
				// if (!gd.isHidden ()) {
				// // Ancho
				// if (Game.DEBUG_MODE) {
				//								iAux = UtilFont.getWidth (gd.getFullName () + " (" + gd.getStatus () + ")"); //$NON-NLS-1$ //$NON-NLS-2$
				// } else {
				// iAux = UtilFont.getWidth (gd.getFullName ());
				// }
				//
				// if (iAux > tooltipWidth) {
				// tooltipWidth = iAux;
				// }
				// }
				// }
				//
				// tooltipX = iconGodsPoint.x + tileIconGods.getTileWidth () / 2 - tooltipWidth / 2;
				// tooltipY = iconGodsPoint.y + tileIconGods.getTileHeight ();
				//
				// // Renderizamos
				// // Fondo
				// GL11.glColor4f (1, 1, 1, 1);
				// GL11.glBindTexture (GL11.GL_TEXTURE_2D, UIPanel.tileTooltipBackground.getTextureID ());
				// UtilsGL.glBegin (GL11.GL_QUADS);
				// UtilsGL.drawTexture (tooltipX, tooltipY - 4, tooltipX + tooltipWidth + 8, tooltipY + tooltipHeight + 4, UIPanel.tileTooltipBackground.getTileSetTexX0 (), UIPanel.tileTooltipBackground.getTileSetTexY0 (), UIPanel.tileTooltipBackground.getTileSetTexX1 (), UIPanel.tileTooltipBackground.getTileSetTexY1 ());
				// UtilsGL.glEnd ();
				//
				// // Textos
				// GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
				// UtilsGL.glBegin (GL11.GL_QUADS);
				// int iCurrentHeight = tooltipY;
				// UtilsGL.drawString (tooltip, tooltipX, iCurrentHeight);
				// iCurrentHeight += UtilFont.MAX_HEIGHT + 2;
				//
				// for (int i = 0; i < alGods.size (); i++) {
				// gd = alGods.get (i);
				//
				// if (Game.DEBUG_MODE) {
				//							UtilsGL.drawString (gd.getFullName () + " (" + gd.getStatus () + ")", tooltipX, iCurrentHeight); //$NON-NLS-1$ //$NON-NLS-2$
				// } else {
				// UtilsGL.drawString (gd.getFullName (), tooltipX, iCurrentHeight);
				// }
				// iCurrentHeight += UtilFont.MAX_HEIGHT + 2;
				// }
				//
				// UtilsGL.glEnd ();
				// }
			}
		}
	}

	public static void drawTile (Tile tile, Point point, boolean bigger) {
		drawTile (tile, point, tile.getTileWidth (), tile.getTileHeight (), bigger);
	}

	/**
	 * Draws a tile
	 * 
	 * @param tile Tile
	 * @param point Coordinates
	 * @param width Base width
	 * @param height Base height
	 * @param bigger Make it bigger?
	 */
	public static void drawTile (Tile tile, Point point, int width, int height, boolean bigger) {
		int iTemp = (width - tile.getTileWidth ()) / 2;
		int iTemp2 = (height - tile.getTileHeight ()) / 2;

		if (bigger) {
			UtilsGL.drawTexture (point.x - (tile.getTileWidth () / 4) + iTemp, point.y - (tile.getTileHeight () / 4) + iTemp2, point.x + tile.getTileWidth () + (tile.getTileWidth () / 4) + iTemp, point.y + tile.getTileHeight () + (tile.getTileHeight () / 4) + iTemp2, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());
		} else {
			UtilsGL.drawTexture (point.x + iTemp, point.y + iTemp2, point.x + tile.getTileWidth () + iTemp, point.y + tile.getTileHeight () + iTemp2, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());
		}
	}

	public static void drawTile (Tile tile, int pointX, int pointY, int width, int height, boolean bigger) {
		int iTemp = (width - tile.getTileWidth ()) / 2;
		int iTemp2 = (height - tile.getTileHeight ()) / 2;

		if (bigger) {
			UtilsGL.drawTexture (pointX - (tile.getTileWidth () / 4) + iTemp, pointY - (tile.getTileHeight () / 4) + iTemp2, pointX + tile.getTileWidth () + (tile.getTileWidth () / 4) + iTemp, pointY + tile.getTileHeight () + (tile.getTileHeight () / 4) + iTemp2, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());
		} else {
			UtilsGL.drawTexture (pointX + iTemp, pointY + iTemp2, pointX + tile.getTileWidth () + iTemp, pointY + tile.getTileHeight () + iTemp2, tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());
		}
	}

	public static void drawTile (Tile tile, Point point) {
		UtilsGL.drawTexture (point.x, point.y, point.x + tile.getTileWidth (), point.y + tile.getTileHeight (), tile.getTileSetTexX0 (), tile.getTileSetTexY0 (), tile.getTileSetTexX1 (), tile.getTileSetTexY1 ());
	}

	public static void drawTile (Tile tile, int x, int y, boolean bigger) {
		drawTile (tile, x, y, tile.getTileWidth (), tile.getTileHeight (), bigger);
	}

	/**
	 * Cierra los menús que no están locked indicados
	 * 
	 * @param bottom
	 * @param right
	 * @param production
	 */
	private void closeNonLockedMenus (boolean bottom, boolean right, boolean production) {
		if (bottom && !BottomMenuUIPanel.isBottomMenuPanelLocked ()) {
			BottomMenuUIPanel.setBottomMenuPanelActive (false);
		}
		if (right && !RightMenuUIPanel.isMenuPanelLocked ()) {
			RightMenuUIPanel.setMenuPanelActive (false);
		}
		if (production && !ProductionUIPanel.isProductionPanelLocked ()) {
			ProductionUIPanel.setProductionPanelActive (false);
		}
	}

	public int isMouseOnAPanel (int x, int y) {
		return isMouseOnAPanel (x, y, false);
	}

	/**
	 * Indica si el ratón está en algún panel. Retorna un código según el panel
	 * 
	 * @param x
	 * @param y
	 * @param doEdgeMenusStuff. Setea el delay a 0 si el mouse está en uno de los paneles laterales, también abre/cierra menus y tal
	 * @return
	 */
	public int isMouseOnAPanel (int x, int y, boolean doEdgeMenusStuff) {
		/*
		 * TYPING PANEL (Si está activo ya no miraremos nada más)
		 */
		if (typingPanel != null) {
			if (isMouseOnTypingPanel (x, y)) {
				if (isMouseOnAnIcon (x, y, TypingPanel.getCloseButtonPoint (), tileButtonClose, tileButtonCloseAlpha)) {
					return MOUSE_TYPING_PANEL_CLOSE;
				} else if (isMouseOnAnIcon (x, y, TypingPanel.getConfirmPoint (), TypingPanel.getTileConfirm (), TypingPanel.getTileConfirmAlpha ())) {
					return MOUSE_TYPING_PANEL_CONFIRM;
				}

				return MOUSE_TYPING_PANEL;
			}

			return MOUSE_NONE;
		}

		/*
		 * IMAGES PANEL
		 */
		if (imagesPanel != null && ImagesPanel.isVisible ()) {
			if (isMouseOnImagesPanel (x, y)) {
				if (isMouseOnAnIcon (x, y, ImagesPanel.getCloseButtonPoint (), tileButtonClose, tileButtonCloseAlpha)) {
					return MOUSE_IMAGES_PANEL_CLOSE;
				} else if (isMouseOnAnIcon (x, y, ImagesPanel.getPreviousImagePoint (), ImagesPanel.getTilePrevious ())) {
					return MOUSE_IMAGES_PANEL_PREVIOUS;
				} else if (isMouseOnAnIcon (x, y, ImagesPanel.getNextImagePoint (), ImagesPanel.getTileNext ())) {
					return MOUSE_IMAGES_PANEL_NEXT;
				} else if (isMouseOnAnIcon (x, y, ImagesPanel.getNextMissionPoint (), ImagesPanel.getTileNextMission ())) {
					return MOUSE_IMAGES_PANEL_NEXT_MISSION;
				}

				return MOUSE_IMAGES_PANEL;
			}

			// Miramos también el botón (para hacer toggle)
//			if (isMouseOnAnIcon (x, y, iconTutorialPoint, tileBottomItem, tileBottomItemAlpha)) {
//				return MOUSE_TUTORIAL_ICON;
//			}
//
//			return MOUSE_NONE;
		}

		/*
		 * PROFESSIONS PANEL
		 */
		if (ProfessionsUIPanel.isProfessionsPanelActive ()) {
			if (ProfessionsUIPanel.isMouseOnProfessionsPanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, true);
				}

				Point p = ProfessionsUIPanel.isMouseOnProfessionsButtons (x, y);
				if (p != null) {
					return p.x;
				}

				return MOUSE_PROFESSIONS_PANEL;
			}
		}

		/*
		 * PILE PANEL
		 */
		if (PileUIPanel.isPilePanelActive ()) {
			if (PileUIPanel.isMouseOnPilePanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, true);
				}

				Point p = PileUIPanel.isMouseOnPileButtons (x, y);
				if (p != null) {
					return p.x;
				}

				return MOUSE_PILE_PANEL;
			}
		}

		/*
		 * MESSAGES PANEL
		 */
		if (MessagesUIPanel.isMessagesPanelActive ()) {
			if (MessagesUIPanel.isMouseOnMessagesPanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, true);
				}

				Point p = MessagesUIPanel.isMouseOnMessagesButtons (x, y);
				if (p != null) {
					return p.x;
				}

				return MOUSE_MESSAGES_PANEL;
			}
		}

		/*
		 * MATS PANEL
		 */
		if (MatsUIPanel.isMatsPanelActive ()) {
			if (MatsUIPanel.isMouseOnMatsPanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, true);
				}

				Point p = MatsUIPanel.isMouseOnMatsButtons (x, y);
				if (p != null) {
					return p.x;
				}

				return MOUSE_MATS_PANEL;
			}
		}

		/*
		 * LIVINGS PANEL
		 */
		if (LivingsUIPanel.isLivingsPanelActive ()) {
			if (LivingsUIPanel.isMouseOnLivingsPanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, true);
				}

				Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
				if (p != null) {
					return p.x;
				}

				return MOUSE_LIVINGS_PANEL;
			}
		}

		/*
		 * TRADE PANEL
		 */
		if (TradeUIPanel.isTradePanelActive ()) {
			if (TradeUIPanel.isMouseOnTradePanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, true);
				}

				Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
				if (p != null) {
					return p.x;
				}

				return MOUSE_TRADE_PANEL;
			}
		}

		/*
		 * PRIORITIES PANEL
		 */
		if (PrioritiesUIPanel.isPrioritiesPanelActive ()) {
			if (PrioritiesUIPanel.isMouseOnPrioritiesPanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, true);
				}

				Point p = PrioritiesUIPanel.isMouseOnPrioritiesItems (x, y);
				if (p != null) {
					return p.x;
				}
				return MOUSE_PRIORITIES_PANEL;
			}
		}

		/*
		 * PRODUCTION PANEL
		 */
		if (ProductionUIPanel.isProductionPanelActive ()) {
			if (isMouseOnAnIcon (x, y, ProductionUIPanel.tileOpenCloseProductionPanelPoint, ProductionUIPanel.tileOpenProductionPanelON, tileOpenProductionPanelONAlpha)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, false);
					delayTime = 0;
				}
				return MOUSE_PRODUCTION_OPENCLOSE;
			}
			if (ProductionUIPanel.isMouseOnProductionPanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, false);
					delayTime = 0;
				}

				Point p = ProductionUIPanel.isMouseOnProductionItems (x, y);
				if (p != null) {
					return p.x;
				}

				return MOUSE_PRODUCTION_PANEL;
			}
			if (doEdgeMenusStuff) {
				if (delayTime > (Game.FPS_INGAME / 8) * 6) {
					if (!ProductionUIPanel.isProductionPanelLocked () && !isMouseOnAnIcon (x, y, ProductionUIPanel.tileOpenCloseProductionPanelPoint, ProductionUIPanel.tileOpenProductionPanel, tileOpenProductionPanelAlpha)) {
						delayTime = 0;
						ProductionUIPanel.setProductionPanelActive (false);
					}
				}
			}
		} else {
			if (doEdgeMenusStuff) {
				if (isMouseOnAnIcon (x, y, ProductionUIPanel.tileOpenCloseProductionPanelPoint, ProductionUIPanel.tileOpenProductionPanel, tileOpenProductionPanelAlpha)) {
					ProductionUIPanel.setProductionPanelActive (true);

					// Cerramos los menús no locked
					closeNonLockedMenus (true, true, false);
					delayTime = 0;
					return MOUSE_PRODUCTION_OPENCLOSE;
				}
			}
		}

		// BOTTOM
		if (BottomMenuUIPanel.isBottomMenuPanelActive ()) {
			if (BottomMenuUIPanel.isMouseOnBottomLeftScroll (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (false, true, true);
					delayTime = 0;
				}
				return MOUSE_BOTTOM_LEFT_SCROLL;
			}
			if (BottomMenuUIPanel.isMouseOnBottomRightScroll (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (false, true, true);
					delayTime = 0;
				}
				return MOUSE_BOTTOM_RIGHT_SCROLL;
			}
			if (BottomMenuUIPanel.isMouseOnBottomItems (x, y) != -1) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (false, true, true);
					delayTime = 0;
				}
				return MOUSE_BOTTOM_ITEMS;
			}
			if (BottomMenuUIPanel.isMouseOnBottomPanel (x, y)) { // Este check tiene que ir detrás de los items, ya que los items están encima
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (false, true, true);
					delayTime = 0;
				}
				return MOUSE_BOTTOM_PANEL;
			}
			if (isMouseOnAnIcon (x, y, BottomMenuUIPanel.tileOpenCloseBottomMenuPoint, tileOpenBottomMenuON, tileOpenBottomMenuONAlpha)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (false, true, true);
					delayTime = 0;
				}
				return MOUSE_BOTTOM_OPENCLOSE;
			}

			if (BottomMenuUIPanel.bottomSubPanelMenu != null) {
				// BOTTOM SUBPANEL
				if (BottomMenuUIPanel.isMouseOnBottomSubItems (x, y) != -1) {
					if (doEdgeMenusStuff) {
						// Cerramos los menús no locked
						closeNonLockedMenus (false, true, true);
						delayTime = 0;
					}
					return MOUSE_BOTTOM_SUBITEMS;
				}
				if (BottomMenuUIPanel.isMouseOnBottomSubPanel (x, y)) {
					if (doEdgeMenusStuff) {
						// Cerramos los menús no locked
						closeNonLockedMenus (false, true, true);
						delayTime = 0;
					}
					return MOUSE_BOTTOM_SUBPANEL;
				}
			}

			if (doEdgeMenusStuff) {
				if (delayTime > (Game.FPS_INGAME / 8) * 6) {
					if (!BottomMenuUIPanel.isBottomMenuPanelLocked () && !isMouseOnAnIcon (x, y, BottomMenuUIPanel.tileOpenCloseBottomMenuPoint, BottomMenuUIPanel.tileOpenBottomMenu, tileOpenBottomMenuAlpha)) {
						delayTime = 0;
						BottomMenuUIPanel.setBottomMenuPanelActive (false);
					}
				}
			}
		} else {
			if (doEdgeMenusStuff) {
				if (isMouseOnAnIcon (x, y, BottomMenuUIPanel.tileOpenCloseBottomMenuPoint, BottomMenuUIPanel.tileOpenBottomMenu, tileOpenBottomMenuAlpha)) {
					BottomMenuUIPanel.setBottomMenuPanelActive (true);

					// Cerramos los menús no locked
					closeNonLockedMenus (false, true, true);
					delayTime = 0;
					return MOUSE_BOTTOM_OPENCLOSE;
				}
			}
		}

		// MENU (right)
		if (RightMenuUIPanel.isMenuPanelActive ()) {
			if (RightMenuUIPanel.isMouseOnMenuItems (x, y) != -1) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, false, true);
					delayTime = 0;
				}
				return MOUSE_MENU_PANEL_ITEMS;
			}
			if (RightMenuUIPanel.isMouseOnMenuPanel (x, y)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, false, true);
					delayTime = 0;
				}
				return MOUSE_MENU_PANEL;
			}
			if (isMouseOnAnIcon (x, y, RightMenuUIPanel.tileOpenCloseRightMenuPoint, RightMenuUIPanel.tileOpenRightMenuON, tileOpenRightMenuONAlpha)) {
				if (doEdgeMenusStuff) {
					// Cerramos los menús no locked
					closeNonLockedMenus (true, false, true);
					delayTime = 0;
				}
				return MOUSE_MENU_OPENCLOSE;
			}

			if (doEdgeMenusStuff) {
				if (delayTime > (Game.FPS_INGAME / 8) * 6) {
					if (!RightMenuUIPanel.isMenuPanelLocked () && !isMouseOnAnIcon (x, y, RightMenuUIPanel.tileOpenCloseRightMenuPoint, RightMenuUIPanel.tileOpenRightMenu, tileOpenRightMenuAlpha)) {
						delayTime = 0;
						RightMenuUIPanel.setMenuPanelActive (false);
					}
				}
			}
		} else {
			if (doEdgeMenusStuff) {
				if (isMouseOnAnIcon (x, y, RightMenuUIPanel.tileOpenCloseRightMenuPoint, RightMenuUIPanel.tileOpenRightMenu, tileOpenRightMenuAlpha)) {
					RightMenuUIPanel.setMenuPanelActive (true);

					// Cerramos los menús no locked
					closeNonLockedMenus (true, false, true);
					delayTime = 0;
					return MOUSE_MENU_OPENCLOSE;
				}
			}
		}

		// MINI ICONS
		if (isMouseOnAnIcon (x, y, iconLevelUpPoint, tileIconLevelUp, LivingsUIPanel.tileIconLevelUpAlpha)) {
			return MOUSE_ICON_LEVEL_UP;
		}
		if (isMouseOnAnIcon (x, y, iconLevelPoint, tileIconLevel, tileIconLevelAlpha)) {
			return MOUSE_ICON_LEVEL;
		}
		if (isMouseOnAnIcon (x, y, iconLevelDownPoint, tileIconLevelDown, LivingsUIPanel.tileIconLevelDownAlpha)) {
			return MOUSE_ICON_LEVEL_DOWN;
		}
		if (isMouseOnAnIcon (x, y, iconCitizenPreviousPoint, tileIconCitizenPrevious, tileIconPreviousMiniAlpha)) {
			return MOUSE_ICON_CITIZEN_PREVIOUS;
		}
		if (isMouseOnAnIcon (x, y, iconCitizenNextPoint, tileIconCitizenNext, tileIconNextMiniAlpha)) {
			return MOUSE_ICON_CITIZEN_NEXT;
		}
		if (isMouseOnAnIcon (x, y, iconSoldierPreviousPoint, tileIconSoldierPrevious, tileIconPreviousMiniAlpha)) {
			return MOUSE_ICON_SOLDIER_PREVIOUS;
		}
		if (isMouseOnAnIcon (x, y, iconSoldierNextPoint, tileIconSoldierNext, tileIconNextMiniAlpha)) {
			return MOUSE_ICON_SOLDIER_NEXT;
		}
		if (isMouseOnAnIcon (x, y, iconHeroPreviousPoint, tileIconHeroPrevious, tileIconPreviousMiniAlpha)) {
			return MOUSE_ICON_HERO_PREVIOUS;
		}
		if (isMouseOnAnIcon (x, y, iconHeroNextPoint, tileIconHeroNext, tileIconNextMiniAlpha)) {
			return MOUSE_ICON_HERO_NEXT;
		}

		// Messages
		if (isMouseOnAnIcon (x, y, MessagesUIPanel.messageIconPoints[0], MessagesUIPanel.messageTiles[0], MessagesUIPanel.messageTilesAlpha.get (0))) {
			return MOUSE_MESSAGES_ICON_ANNOUNCEMENT;
		}
		if (isMouseOnAnIcon (x, y, MessagesUIPanel.messageIconPoints[1], MessagesUIPanel.messageTiles[1], MessagesUIPanel.messageTilesAlpha.get (1))) {
			return MOUSE_MESSAGES_ICON_COMBAT;
		}
		if (isMouseOnAnIcon (x, y, MessagesUIPanel.messageIconPoints[2], MessagesUIPanel.messageTiles[2], MessagesUIPanel.messageTilesAlpha.get (2))) {
			return MOUSE_MESSAGES_ICON_HEROES;
		}
		if (isMouseOnAnIcon (x, y, MessagesUIPanel.messageIconPoints[3], MessagesUIPanel.messageTiles[3], MessagesUIPanel.messageTilesAlpha.get (3))) {
			return MOUSE_MESSAGES_ICON_SYSTEM;
		}

		// Events
		if (isMouseOnAnIcon (x, y, iconEventsPoint, GlobalEventData.getIcon ())) {
			return MOUSE_EVENTS_ICON;
		}

		// Tutorial
		if (isMouseOnAnIcon (x, y, iconTutorialPoint, tileIconTutorial)) {
			return MOUSE_TUTORIAL_ICON;
		}

		// Gods
		// if (TownsProperties.GODS_ACTIVATED) {
		// if (isMouseOnAnIcon (x, y, iconGodsPoint, tileIconGods)) {
		// return MOUSE_GODS_ICON;
		// }
		// }

		// Backgrounds
		if (isMouseOnAnIcon (x, y, iconNumCitizensBackgroundPoint, tileBottomItem, tileBottomItemAlpha)) {
			return MOUSE_INFO_NUM_CITIZENS;
		}
		if (isMouseOnAnIcon (x, y, iconNumSoldiersBackgroundPoint, tileBottomItem, tileBottomItemAlpha)) {
			return MOUSE_INFO_NUM_SOLDIERS;
		}
		if (isMouseOnAnIcon (x, y, iconNumHeroesBackgroundPoint, tileBottomItem, tileBottomItemAlpha)) {
			return MOUSE_INFO_NUM_HEROES;
		}
		if (isMouseOnAnIcon (x, y, iconCaravanBackgroundPoint, tileBottomItem, tileBottomItemAlpha)) {
			return MOUSE_INFO_CARAVAN;
		}

		// ICONS
		if (isMouseOnAnIcon (x, y, iconPrioritiesPoint, tileIconPriorities, tileIconPrioritiesAlpha)) {
			return MOUSE_ICON_PRIORITIES;
		}
		if (isMouseOnAnIcon (x, y, iconMatsPoint, tileIconMats, tileIconMatsAlpha)) {
			return MOUSE_ICON_MATS;
		}
		if (isMouseOnAnIcon (x, y, iconMiniblocksPoint, tileIconMiniblocks, tileIconMiniblocksAlpha)) {
			return MOUSE_ICON_MINIBLOCKS;
		}
		if (isMouseOnAnIcon (x, y, iconFlatMousePoint, tileIconFlatMouse, tileIconFlatMouseAlpha)) {
			return MOUSE_ICON_FLATMOUSE;
		}
		if (isMouseOnAnIcon (x, y, icon3DMousePoint, tileIcon3DMouse, tileIcon3DMouseAlpha)) {
			return MOUSE_ICON_3DMOUSE;
		}
		if (isMouseOnAnIcon (x, y, iconGridPoint, tileIconGrid, tileIconGridAlpha)) {
			return MOUSE_ICON_GRID;
		}
		if (isMouseOnAnIcon (x, y, iconSettingsPoint, tileIconSettings, tileIconSettingsAlpha)) {
			return MOUSE_ICON_SETTINGS;
		}
		if (isMouseOnAnIcon (x, y, iconPauseResumePoint, tileIconPause, tileIconPauseResumeAlpha)) {
			return MOUSE_ICON_PAUSE_RESUME;
		}
		if (isMouseOnAnIcon (x, y, iconLowerSpeedPoint, tileIconLowerSpeed, tileIconLowerSpeedAlpha)) {
			return MOUSE_ICON_LOWER_SPEED;
		}
		if (isMouseOnAnIcon (x, y, iconIncreaseSpeedPoint, tileIconIncreaseSpeed, tileIconIncreaseSpeedAlpha)) {
			return MOUSE_ICON_INCREASE_SPEED;
		}
		if (Game.getCurrentMissionData () != null && Game.getCurrentMissionData ().getTutorialFlows ().size () > 0) {
			if (isMouseOnAnIcon (x, y, iconTutorialPoint, tileBottomItem, tileBottomItemAlpha)) {
				return MOUSE_TUTORIAL_ICON;
			}
		}

		// DATE
		if (isMouseOnDatePanel (x, y)) {
			return MOUSE_DATEPANEL;
		}

		// INFO
		if (isMouseOnInfoPanel (x, y)) {
			return MOUSE_INFOPANEL;
		}

		// MINIMAP
		if (isMouseOnMinimap (x, y)) {
			return MOUSE_MINIMAP;
		}

		return MOUSE_NONE;
	}

	public static boolean isMouseOnAnIcon (int x, int y, Point point, Tile tile) {
		if ((y >= point.y && y < (point.y + tile.getTileHeight ())) && (x >= point.x && x < (point.x + tile.getTileWidth ()))) {
			return true;
		}

		return false;
	}

	public static boolean isMouseOnAnIcon (int x, int y, Point point, Tile tile, boolean[][] alpha) {
		if ((y >= point.y && y < (point.y + tile.getTileHeight ())) && (x >= point.x && x < (point.x + tile.getTileWidth ()))) {
			return !alpha[x - point.x][y - point.y];
		}

		return false;
	}

	public static boolean isMouseCloseToIcon (int x, int y, Point point, Tile tile, int closeFactor) {
		if ((y >= (point.y - closeFactor) && y < (point.y + tile.getTileHeight () + closeFactor)) && (x >= (point.x - closeFactor) && x < (point.x + tile.getTileWidth () + closeFactor))) {
			return true;
		}

		return false;
	}

	/**
	 * Indica si el mouse está en un item, devuelve el número del mismo o -1 en caso de no estar
	 * 
	 * @param x
	 * @param y
	 * @return devuelve el número del item o -1 en caso de no estar
	 */



	/**
	 * Indica si el mouse está en un item del submenu de abajo, devuelve el número del mismo o -1 en caso de no estar
	 * 
	 * @param x
	 * @param y
	 * @return devuelve el número del item o -1 en caso de no estar
	 */


	private boolean isMouseOnDatePanel (int x, int y) {
		if ((y >= datePanelPoint.y && y < (datePanelPoint.y + tileDatePanel.getTileHeight ())) && (x >= datePanelPoint.x && x < (datePanelPoint.x + tileDatePanel.getTileWidth ()))) {
			return !tileDatePanelAlpha[x - datePanelPoint.x][y - datePanelPoint.y];
		}

		return false;
	}

	private boolean isMouseOnInfoPanel (int x, int y) {
		if ((y >= infoPanelPoint.y && y < (infoPanelPoint.y + tileInfoPanel.getTileHeight ())) && (x >= infoPanelPoint.x && x < (infoPanelPoint.x + tileInfoPanel.getTileWidth ()))) {
			return !tileInfoPanelAlpha[x - infoPanelPoint.x][y - infoPanelPoint.y];
		}

		return false;
	}

	private boolean isMouseOnImagesPanel (int x, int y) {
		return ((y >= ImagesPanel.getPanelPoint ().y && y < (ImagesPanel.getPanelPoint ().y + ImagesPanel.HEIGHT)) && (x >= ImagesPanel.getPanelPoint ().x && x < (ImagesPanel.getPanelPoint ().x + ImagesPanel.WIDTH)));
	}

	private boolean isMouseOnTypingPanel (int x, int y) {
		return ((y >= TypingPanel.getPanelPoint ().y && y < (TypingPanel.getPanelPoint ().y + TypingPanel.HEIGHT)) && (x >= TypingPanel.getPanelPoint ().x && x < (TypingPanel.getPanelPoint ().x + TypingPanel.WIDTH)));
	}

	private boolean isMouseOnMinimap (int x, int y) {
		if (y >= minimapPanelY && y < (minimapPanelY + MINIMAP_PANEL_HEIGHT) && x >= minimapPanelX && x < (minimapPanelX + MINIMAP_PANEL_WIDTH)) {
			if (MiniMapPanel.isMouseOver (x - minimapPanelX, y - minimapPanelY)) {
				return true;
			}

			return !tileMinimapPanelAlpha[x - minimapPanelX][y - minimapPanelY];
		}

		return false;
	}

	/**
	 * Indica si el mouse está en un item (o en los +/-) del panel de producción
	 * 
	 * @param x
	 * @param y
	 * @return Un punto, X es el MOUSE_ID y Y indica la posición del item en el array correspondiente
	 */


	/**
	 * Indica si el mouse está en un item (o en los up/down) del panel de prioridades
	 * 
	 * @param x
	 * @param y
	 * @return Un punto, X es el MOUSE_ID y Y indica la posición del item en el array correspondiente
	 */



	/**
	 * Indica si el mouse está en un item (o en los up/down) del panel de trade
	 * 
	 * @param x
	 * @param y
	 * @return Un punto, X es el MOUSE_ID y Y indica la posición del item en el array correspondiente
	 */


	/**
	 * Indica si el mouse está en un item (o en los up/down) del panel de pila
	 * 
	 * @param x
	 * @param y
	 * @return Un punto, X es el MOUSE_ID y Y indica la posición del item en el array correspondiente
	 */


	/**
	 * Indica si el mouse está en un item (o en los up/down) del panel de profesiones
	 * 
	 * @param x
	 * @param y
	 * @return Un punto, X es el MOUSE_ID y Y indica la posición del item en el array correspondiente
	 */


	/**
	 * Indica si el mouse está en un item (o en los up/down) del panel de trade
	 * 
	 * @param x
	 * @param y
	 * @return Un punto, X es el MOUSE_ID y Y indica la posición del item en el array correspondiente
	 */



	/**
	 * Retorna una lista de IDs de livings a partir de lo que esté mostrando el living panel
	 * 
	 * @return
	 */


	/**
	 * Retorna la primera posición de la página que esté mostrando el living panel
	 * 
	 * @return
	 */


	/**
	 * Mouse pressed
	 * 
	 * @param x
	 * @param y
	 * @param mouseButton
	 */
	public void mousePressed (int x, int y, int mouseButton) {
		int iPanel = isMouseOnAPanel (x, y);

		if (iPanel == MOUSE_NONE) {
			return;
		}

		/*
		 * TYPING PANEL (Si está activo ya no miraremos nada más)
		 */
		if (typingPanel != null) {
			if (iPanel == MOUSE_TYPING_PANEL_CLOSE || (mouseButton == 1 && iPanel == MOUSE_TYPING_PANEL)) {
				typingPanel = null;
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_TYPING_PANEL_CONFIRM) {
				closeTypingPanel ();
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			}
			return;
		}

		/*
		 * IMAGES PANEL
		 */
		if (imagesPanel != null && ImagesPanel.isVisible ()) {
			if (iPanel == MOUSE_IMAGES_PANEL_CLOSE || (mouseButton == 1 && iPanel == MOUSE_IMAGES_PANEL)) {
				ImagesPanel.setVisible (false);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_IMAGES_PANEL_NEXT) {
				if (ImagesPanel.nextFlow ()) {
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_IMAGES_PANEL_PREVIOUS) {
				if (ImagesPanel.previousFlow ()) {
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_IMAGES_PANEL_NEXT_MISSION) {
				if (ImagesPanel.nextMission ()) {
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_TUTORIAL_ICON) {
				// Miramos también el botón (para hacer toggle)
				toggleTutorialPanel (false);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_IMAGES_PANEL) {
				return;
			}
		}

		/*
		 * PROFESSIONS PANEL
		 */
		if (ProfessionsUIPanel.isProfessionsPanelActive ()) {
			if (iPanel == MOUSE_PROFESSIONS_PANEL_BUTTONS_CLOSE) {
				ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (ProfessionsUIPanel.professionsPanelMaxPages > 1 && iPanel == MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_UP) {
				if (ProfessionsUIPanel.professionsPanelPageIndex > 0) {
					ProfessionsUIPanel.professionsPanelPageIndex--;
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (ProfessionsUIPanel.professionsPanelMaxPages > 1 && iPanel == MOUSE_PROFESSIONS_PANEL_BUTTONS_SCROLL_DOWN) {
				if ((ProfessionsUIPanel.professionsPanelPageIndex + 1) < ProfessionsUIPanel.professionsPanelMaxPages) {
					ProfessionsUIPanel.professionsPanelPageIndex++;
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_PROFESSIONS_PANEL_BUTTONS_ITEMS) {
				// Ha clicado en un item, vamos a ver qué pasa
				if (ProfessionsUIPanel.menuProfessions == null) {
					return;
				}

				if (mouseButton == 1) { // Botón derecho (back al menú)
					if (ProfessionsUIPanel.menuProfessions.getParent () != null) {
						ProfessionsUIPanel.menuProfessions = ProfessionsUIPanel.menuProfessions.getParent ();
						ProfessionsUIPanel.resizeProfessionsPanel (ProfessionsUIPanel.menuProfessions);
						ProfessionsUIPanel.recheckProfessionsPanelPages ();
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					} else {
						ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
					}
					return;
				}

				// Botón izquierdo
				Point p = ProfessionsUIPanel.isMouseOnProfessionsButtons (x, y);
				if (p != null && p.y < ProfessionsUIPanel.menuProfessions.getItems ().size ()) {
					SmartMenu menuAux = ProfessionsUIPanel.menuProfessions.getItems ().get (p.y);
					if (menuAux.getType () == SmartMenu.TYPE_MENU) {
						ProfessionsUIPanel.menuProfessions = menuAux;
						ProfessionsUIPanel.resizeProfessionsPanel (ProfessionsUIPanel.menuProfessions);
						ProfessionsUIPanel.recheckProfessionsPanelPages ();
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
					if (menuAux.getCommand ().equals (CommandPanel.COMMAND_BACK)) {
						// Back
						if (ProfessionsUIPanel.menuProfessions.getParent () != null) {
							ProfessionsUIPanel.menuProfessions = ProfessionsUIPanel.menuProfessions.getParent ();
							ProfessionsUIPanel.resizeProfessionsPanel (ProfessionsUIPanel.menuProfessions);
							ProfessionsUIPanel.recheckProfessionsPanelPages ();
							UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						} else {
							ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
						}
					} else {
						CommandPanel.executeCommand (menuAux.getCommand (), menuAux.getParameter (), menuAux.getParameter2 (), menuAux.getDirectCoordinates (), null, SmartMenu.ICON_TYPE_ITEM);
						// Regeneramos el menu
						if (ProfessionsUIPanel.professionsPanelIsCitizen) {
							if (!ActionPriorityManager.regenerateProfessionsPanelMenu (ProfessionsUIPanel.menuProfessions, ProfessionsUIPanel.professionsPanelCitizenOrGroupIDActive)) {
								ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
							}
						} else {
							if (!ActionPriorityManager.regenerateJobGroupPanelMenu (ProfessionsUIPanel.menuProfessions, ProfessionsUIPanel.professionsPanelCitizenOrGroupIDActive)) {
								ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
							}
						}

						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					}

					return;
				}
			}

			if (iPanel == MOUSE_PROFESSIONS_PANEL) {
				if (mouseButton == 1) { // Botón derecho (cerramos o tiramos 1 atrás el menú)
					if (ProfessionsUIPanel.menuProfessions != null) {
						if (ProfessionsUIPanel.menuProfessions.getParent () != null) {
							ProfessionsUIPanel.menuProfessions = ProfessionsUIPanel.menuProfessions.getParent ();
							PileUIPanel.resizePilePanel (ProfessionsUIPanel.menuProfessions);
							ProfessionsUIPanel.recheckProfessionsPanelPages ();
						} else {
							ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
						}
					} else {
						ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
					}
				}
				return;
			}
		}

		/*
		 * PILE PANEL
		 */
		if (PileUIPanel.isPilePanelActive ()) {
			if (iPanel == MOUSE_PILE_PANEL_BUTTONS_CLOSE) {
				PileUIPanel.setPilePanelActive (-1, false);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (PileUIPanel.pilePanelMaxPages > 1 && iPanel == MOUSE_PILE_PANEL_BUTTONS_SCROLL_UP) {
				if (PileUIPanel.pilePanelPageIndex > 0) {
					PileUIPanel.pilePanelPageIndex--;
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (PileUIPanel.pilePanelMaxPages > 1 && iPanel == MOUSE_PILE_PANEL_BUTTONS_SCROLL_DOWN) {
				if ((PileUIPanel.pilePanelPageIndex + 1) < PileUIPanel.pilePanelMaxPages) {
					PileUIPanel.pilePanelPageIndex++;
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_COPY) {
				if (PileUIPanel.menuPile == null) {
					return;
				}

				if (PileUIPanel.pilePanelIsContainer) {
					CommandPanel.executeCommand (CommandPanel.COMMAND_CONTAINER_COPY_TO_ALL, PileUIPanel.menuPile.getParameter (), null, null, null, -1);
				} else {
					CommandPanel.executeCommand (CommandPanel.COMMAND_STOCKPILE_COPY_TO_ALL, PileUIPanel.menuPile.getParameter (), null, null, null, -1);
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK) {
				if (PileUIPanel.menuPile == null) {
					return;
				}

				if (PileUIPanel.pilePanelIsContainer) {
					Container container = Game.getWorld ().getContainer (Integer.parseInt (PileUIPanel.menuPile.getParameter ()));
					if (container != null) {
						container.setLockedToCopy (!container.isLockedToCopy ());
						PileUIPanel.pilePanelIsLocked = container.isLockedToCopy ();
					}
				} else {
					Stockpile pile = Stockpile.getStockpile (PileUIPanel.menuPile.getParameter ());
					if (pile != null) {
						pile.setLockedToCopy (!pile.isLockedToCopy ());
						PileUIPanel.pilePanelIsLocked = pile.isLockedToCopy ();
					}
				}

				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_LOCK_ALL) {
				if (PileUIPanel.menuPile == null) {
					return;
				}

				if (PileUIPanel.pilePanelIsContainer) {
					Container.lockUnlockAllConfigurations (true);

					Container container = Game.getWorld ().getContainer (Integer.parseInt (PileUIPanel.menuPile.getParameter ()));
					if (container != null) {
						PileUIPanel.pilePanelIsLocked = container.isLockedToCopy ();
					}
				} else {
					Stockpile.lockUnlockAllConfigurations (true);

					Stockpile pile = Stockpile.getStockpile (PileUIPanel.menuPile.getParameter ());
					if (pile != null) {
						PileUIPanel.pilePanelIsLocked = pile.isLockedToCopy ();
					}
				}

				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_PILE_PANEL_BUTTONS_CONFIG_UNLOCK_ALL) {
				if (PileUIPanel.menuPile == null) {
					return;
				}

				if (PileUIPanel.pilePanelIsContainer) {
					Container.lockUnlockAllConfigurations (false);

					Container container = Game.getWorld ().getContainer (Integer.parseInt (PileUIPanel.menuPile.getParameter ()));
					if (container != null) {
						PileUIPanel.pilePanelIsLocked = container.isLockedToCopy ();
					}
				} else {
					Stockpile.lockUnlockAllConfigurations (false);

					Stockpile pile = Stockpile.getStockpile (PileUIPanel.menuPile.getParameter ());
					if (pile != null) {
						PileUIPanel.pilePanelIsLocked = pile.isLockedToCopy ();
					}
				}

				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_PILE_PANEL_BUTTONS_ITEMS) {
				// Ha clicado en un item, vamos a ver qué pasa
				if (PileUIPanel.menuPile == null) {
					return;
				}

				if (mouseButton == 1) { // Botón derecho (back al menú)
					if (PileUIPanel.menuPile.getParent () != null) {
						PileUIPanel.menuPile = PileUIPanel.menuPile.getParent ();
						PileUIPanel.resizePilePanel (PileUIPanel.menuPile);
						PileUIPanel.recheckPilePanelPages ();
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					} else {
						PileUIPanel.setPilePanelActive (-1, false);
					}
					return;
				}

				// Botón izquierdo
				Point p = PileUIPanel.isMouseOnPileButtons (x, y);
				if (p != null && p.y < PileUIPanel.menuPile.getItems ().size ()) {
					SmartMenu menuAux = PileUIPanel.menuPile.getItems ().get (p.y);
					if (menuAux.getType () == SmartMenu.TYPE_MENU) {
						PileUIPanel.menuPile = menuAux;
						PileUIPanel.resizePilePanel (PileUIPanel.menuPile);
						PileUIPanel.recheckPilePanelPages ();
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
					if (menuAux.getCommand ().equals (CommandPanel.COMMAND_BACK)) {
						// Back
						if (PileUIPanel.menuPile.getParent () != null) {
							PileUIPanel.menuPile = PileUIPanel.menuPile.getParent ();
							PileUIPanel.resizePilePanel (PileUIPanel.menuPile);
							PileUIPanel.recheckPilePanelPages ();
							UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						} else {
							PileUIPanel.setPilePanelActive (-1, false);
						}
					} else {
						CommandPanel.executeCommand (menuAux.getCommand (), menuAux.getParameter (), menuAux.getParameter2 (), menuAux.getDirectCoordinates (), null, SmartMenu.ICON_TYPE_ITEM);
						// Regeneramos el menu
						if (PileUIPanel.isPilePanelIsContainer ()) {
							// Container
							if (!Container.regenerateContainerPanelMenu (PileUIPanel.pilePanelPileContainerIDActive, PileUIPanel.menuPile)) {
								PileUIPanel.setPilePanelActive (-1, false);
							}
						} else {
							// Pila
							if (!Stockpile.regeneratePilePanelMenu (PileUIPanel.pilePanelPileContainerIDActive, PileUIPanel.menuPile)) {
								PileUIPanel.setPilePanelActive (-1, false);
							}
						}

						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					}

					return;
				}
			}

			if (iPanel == MOUSE_PILE_PANEL) {
				if (mouseButton == 1) { // Botón derecho (cerramos o tiramos 1 atrás el menú)
					if (PileUIPanel.menuPile != null) {
						if (PileUIPanel.menuPile.getParent () != null) {
							PileUIPanel.menuPile = PileUIPanel.menuPile.getParent ();
							PileUIPanel.resizePilePanel (PileUIPanel.menuPile);
							PileUIPanel.recheckPilePanelPages ();
						} else {
							PileUIPanel.setPilePanelActive (-1, false);
						}
					} else {
						PileUIPanel.setPilePanelActive (-1, false);
					}
				}
				return;
			}
		}

		/*
		 * MESSAGES PANEL
		 */
		if (MessagesUIPanel.isMessagesPanelActive ()) {
			int iMessagesType = MessagesUIPanel.getMessagesPanelActive ();
			if (iPanel == MOUSE_MESSAGES_PANEL_BUTTONS_CLOSE) {
				MessagesUIPanel.setMessagesPanelActive (-1);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_MESSAGES_PANEL_BUTTONS_ANNOUNCEMENT) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_ANNOUNCEMENT);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_MESSAGES_PANEL_BUTTONS_COMBAT) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_COMBAT);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_MESSAGES_PANEL_BUTTONS_HEROES) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_HEROES);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_MESSAGES_PANEL_BUTTONS_SYSTEM) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_SYSTEM);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_UP) {
				if (MessagesPanel.getPages (iMessagesType) > 1 && MessagesPanel.getPagesCurrent (iMessagesType) > 1) {
					MessagesPanel.pageUp (iMessagesType);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_MESSAGES_PANEL_BUTTONS_SCROLL_DOWN) {
				if (MessagesPanel.getPages (iMessagesType) > 1 && MessagesPanel.getPagesCurrent (iMessagesType) < MessagesPanel.getPages (iMessagesType)) {
					MessagesPanel.pageDown (iMessagesType);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_MESSAGES_PANEL) {
				if (mouseButton == 1) { // Botón derecho (cerramos)
					MessagesUIPanel.setMessagesPanelActive (-1);
				} else {
					if (MessagesPanel.mousePressed (x, y, MessagesUIPanel.getMessagesPanelActive (), MessagesUIPanel.messagesPanelSubPanelPoint.x + MessagesUIPanel.tileMessagesPanel[3].getTileWidth (), MessagesUIPanel.messagesPanelSubPanelPoint.y + MessagesUIPanel.tileMessagesPanel[1].getTileHeight ())) {
						MessagesUIPanel.setMessagesPanelActive (-1);
					}
				}
				return;
			}
		}

		/*
		 * MATS PANEL
		 */
		if (MatsUIPanel.isMatsPanelActive ()) {
			if (iPanel == MOUSE_MATS_PANEL_BUTTONS_CLOSE) {
				MatsUIPanel.setMatsPanelActive (false);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_MATS_PANEL_BUTTONS_GROUPS) {
				Point p = MatsUIPanel.isMouseOnMatsButtons (x, y);
				if (p != null) {
					MatsUIPanel.setMatsPanelActive (p.y);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_MATS_PANEL_BUTTONS_SCROLL_UP) {
				if (MatsUIPanel.matsIndexPages[MatsUIPanel.getMatsPanelActive ()] > 0) {
					MatsUIPanel.matsIndexPages[MatsUIPanel.getMatsPanelActive ()]--;
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_MATS_PANEL_BUTTONS_SCROLL_DOWN) {
				if (MatsUIPanel.matsIndexPages[MatsUIPanel.getMatsPanelActive ()] < (MatsUIPanel.matsNumPages[MatsUIPanel.getMatsPanelActive ()] - 1)) {
					MatsUIPanel.matsIndexPages[MatsUIPanel.getMatsPanelActive ()]++;
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			}

			if (iPanel == MOUSE_MATS_PANEL) {
				if (mouseButton == 1) { // Botón derecho (cerramos)
					MatsUIPanel.setMatsPanelActive (false);
				}
				return;
			}
		}

		/*
		 * LIVINGS PANEL
		 */
		if (LivingsUIPanel.isLivingsPanelActive ()) {
			if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_CLOSE) {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_UP) {
				if (!LivingsUIPanel.checkGroupsPanelEnabled (LivingsUIPanel.getLivingsPanelActive ())) {
					if (LivingsUIPanel.livingsDataIndexPages[LivingsUIPanel.getLivingsPanelActive ()] > 1) {
						LivingsUIPanel.livingsDataIndexPages[LivingsUIPanel.getLivingsPanelActive ()]--;
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
				} else {
					if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS) {
						if (LivingsUIPanel.livingsDataIndexPagesCitizenGroups[LivingsUIPanel.livingsPanelCitizensGroupActive] > 1) {
							LivingsUIPanel.livingsDataIndexPagesCitizenGroups[LivingsUIPanel.livingsPanelCitizensGroupActive]--;
							UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
							return;
						}
					} else {
						if (LivingsUIPanel.livingsDataIndexPagesSoldierGroups[LivingsUIPanel.livingsPanelSoldiersGroupActive] > 1) {
							LivingsUIPanel.livingsDataIndexPagesSoldierGroups[LivingsUIPanel.livingsPanelSoldiersGroupActive]--;
							UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
							return;
						}
					}
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_DOWN) {
				int iNumLivings;
				ArrayList<Integer> alLivings = LivingsUIPanel.getLivings ();
				if (alLivings != null) {
					iNumLivings = alLivings.size ();
				} else {
					iNumLivings = 0;
				}
				if (iNumLivings > 0) {
					int iNumPages = (iNumLivings % LivingsUIPanel.LIVINGS_PANEL_MAX_ROWS == 0) ? iNumLivings / LivingsUIPanel.LIVINGS_PANEL_MAX_ROWS : (iNumLivings / LivingsUIPanel.LIVINGS_PANEL_MAX_ROWS) + 1;

					// Scrolls
					if (!LivingsUIPanel.checkGroupsPanelEnabled (LivingsUIPanel.getLivingsPanelActive ())) {
						if (LivingsUIPanel.livingsDataIndexPages[LivingsUIPanel.getLivingsPanelActive ()] < iNumPages) {
							LivingsUIPanel.livingsDataIndexPages[LivingsUIPanel.getLivingsPanelActive ()]++;
							UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
							return;
						}
					} else {
						if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS) {
							if (LivingsUIPanel.livingsDataIndexPagesCitizenGroups[LivingsUIPanel.livingsPanelCitizensGroupActive] < iNumPages) {
								LivingsUIPanel.livingsDataIndexPagesCitizenGroups[LivingsUIPanel.livingsPanelCitizensGroupActive]++;
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								return;
							}
						} else {
							if (LivingsUIPanel.livingsDataIndexPagesSoldierGroups[LivingsUIPanel.livingsPanelSoldiersGroupActive] < iNumPages) {
								LivingsUIPanel.livingsDataIndexPagesSoldierGroups[LivingsUIPanel.livingsPanelSoldiersGroupActive]++;
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								return;
							}
						}
					}
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP && LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS && LivingsUIPanel.livingsPanelCitizensGroupActive == -1) {
				Game.getWorld ().substractRestrictHaulEquippingLevel ();
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				// Tutorial flow
				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_RESTRICTION, null);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN && LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS && LivingsUIPanel.livingsPanelCitizensGroupActive == -1) {
				Game.getWorld ().addRestrictHaulEquippingLevel ();
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				// Tutorial flow
				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_RESTRICTION, null);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP && LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_HEROES) {
				Game.getWorld ().substractRestrictExploringLevel ();
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				// Tutorial flow
				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_RESTRICTION, null);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN && LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_HEROES) {
				Game.getWorld ().addRestrictExploringLevel ();
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				// Tutorial flow
				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_RESTRICTION, null);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE) {
				if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS && LivingsUIPanel.livingsPanelSoldiersGroupActive != -1) {
					Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
					if (p != null) {
						ArrayList<Integer> alLivings = LivingsUIPanel.getLivings ();
						int iIndex = LivingsUIPanel.getLivingsIndex ();
						if (alLivings != null && p != null && (p.y + iIndex) >= 0 && (p.y + iIndex) < alLivings.size ()) {
							LivingEntity le = World.getLivingEntityByID (alLivings.get ((p.y + iIndex)));
							if (le != null) {
								Citizen soldier = (Citizen) le;
								soldier.getSoldierData ().setState (SoldierData.STATE_GUARD, -1, le.getID ());
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								return;
							}
						}
					}
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD) {
				if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS && LivingsUIPanel.livingsPanelSoldiersGroupActive == -1) {
					Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
					if (p != null) {
						ArrayList<Integer> alLivings = LivingsUIPanel.getLivings ();
						int iIndex = LivingsUIPanel.getLivingsIndex ();
						if (alLivings != null && p != null && (p.y + iIndex) >= 0 && (p.y + iIndex) < alLivings.size ()) {
							LivingEntity le = World.getLivingEntityByID (alLivings.get ((p.y + iIndex)));
							if (le != null) {
								Citizen soldier = (Citizen) le;
								if (Game.getCurrentState () == Game.STATE_CREATING_TASK) {
									Game.deleteCurrentTask ();
								}
								ContextMenu menu = new ContextMenu ();
								SmartMenu sm = new SmartMenu ();

								SoldierGroupData sgd;
								for (int g = 0; g < SoldierGroups.MAX_GROUPS; g++) {
									// Añadir a grupos existentes
									sgd = Game.getWorld ().getSoldierGroups ().getGroup (g);
									if (soldier.getSoldierData ().getState () != SoldierData.STATE_IN_A_GROUP || soldier.getSoldierData ().getGroup () != sgd.getId ()) {
										sm.addItem (new SmartMenu (SmartMenu.TYPE_ITEM, sgd.getName (), null, CommandPanel.COMMAND_SOLDIER_SET_STATE, Integer.toString (soldier.getID ()), Integer.toString (SoldierData.STATE_IN_A_GROUP), new Point3D (sgd.getId (), -1, -1)));
									} else {
										sm.addItem (new SmartMenu (SmartMenu.TYPE_TEXT, sgd.getName (), null, null, null, null, null, Color.GRAY));
									}
								}

								menu.setSmartMenu (sm);
								menu.setX (x + 16 + -menu.getWidth () / 2);
								menu.setY (y + 32);
								menu.resize ();
								Game.setContextMenu (menu);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_GROUPS, null);
								return;
							}
						}
					}
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE) {
				if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS) {
					Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
					if (p != null) {
						ArrayList<Integer> alLivings = LivingsUIPanel.getLivings ();
						int iIndex = LivingsUIPanel.getLivingsIndex ();
						if (alLivings != null && p != null && (p.y + iIndex) >= 0 && (p.y + iIndex) < alLivings.size ()) {
							LivingEntity le = World.getLivingEntityByID (alLivings.get ((p.y + iIndex)));
							if (le != null) {
								Citizen citizen = (Citizen) le;
								if (Game.getCurrentState () == Game.STATE_CREATING_TASK) {
									Game.deleteCurrentTask ();
								}
								ContextMenu menu = new ContextMenu ();
								SmartMenu sm = new SmartMenu ();

								CitizenGroupData cgd;
								for (int g = 0; g < CitizenGroups.MAX_GROUPS; g++) {
									// Añadir a grupos existentes
									cgd = Game.getWorld ().getCitizenGroups ().getGroup (g);
									if (citizen.getCitizenData ().getGroupID () != g) {
										sm.addItem (new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("UIPanel.70") + cgd.getName (), null, CommandPanel.COMMAND_CITIZEN_SET_JOB_GROUP, Integer.toString (citizen.getID ()), Integer.toString (g), null, Color.GREEN)); //$NON-NLS-1$
									} else {
										sm.addItem (new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("UIPanel.71") + cgd.getName (), null, CommandPanel.COMMAND_CITIZEN_SET_JOB_GROUP, Integer.toString (citizen.getID ()), Integer.toString (-1), null, Color.ORANGE)); //$NON-NLS-1$
									}
								}

								menu.setSmartMenu (sm);
								menu.setX (x + 16 + -menu.getWidth () / 2);
								menu.setY (y + 32);
								menu.resize ();
								Game.setContextMenu (menu);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_GROUPS, null);
								return;
							}
						}
					}
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SGROUP_NOGROUP) {
				LivingsUIPanel.livingsPanelSoldiersGroupActive = -1;
				LivingsUIPanel.createLivingsPanel (LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS, -1, LivingsUIPanel.livingsPanelCitizensGroupActive);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SGROUP_GROUP) {
				Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
				if (p != null && p.y >= 0 && p.y < SoldierGroups.MAX_GROUPS) {
					LivingsUIPanel.livingsPanelSoldiersGroupActive = p.y;
					LivingsUIPanel.createLivingsPanel (LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS, p.y, LivingsUIPanel.livingsPanelCitizensGroupActive);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_CGROUP_NOGROUP) {
				LivingsUIPanel.livingsPanelCitizensGroupActive = -1;
				LivingsUIPanel.createLivingsPanel (LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS, LivingsUIPanel.livingsPanelSoldiersGroupActive, -1);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_CGROUP_GROUP) {
				Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
				if (p != null && p.y >= 0 && p.y < CitizenGroups.MAX_GROUPS) {
					LivingsUIPanel.livingsPanelCitizensGroupActive = p.y;
					LivingsUIPanel.createLivingsPanel (LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS, LivingsUIPanel.livingsPanelSoldiersGroupActive, p.y);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_RENAME) {
				if (LivingsUIPanel.livingsPanelSoldiersGroupActive != -1) {
					typingPanel = new TypingPanel (renderWidth, renderHeight, Messages.getString ("UIPanel.61"), Game.getWorld ().getSoldierGroups ().getGroup (LivingsUIPanel.livingsPanelSoldiersGroupActive).getName (), TypingPanel.TYPE_RENAME_GROUP, LivingsUIPanel.livingsPanelSoldiersGroupActive); //$NON-NLS-1$
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_RENAME) {
				if (LivingsUIPanel.livingsPanelCitizensGroupActive != -1) {
					typingPanel = new TypingPanel (renderWidth, renderHeight, Messages.getString ("UIPanel.61"), Game.getWorld ().getCitizenGroups ().getGroup (LivingsUIPanel.livingsPanelCitizensGroupActive).getName (), TypingPanel.TYPE_RENAME_JOB_GROUP, LivingsUIPanel.livingsPanelCitizensGroupActive); //$NON-NLS-1$
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_GUARD) {
				if (LivingsUIPanel.livingsPanelSoldiersGroupActive != -1) {
					Game.getWorld ().getSoldierGroups ().getGroup (LivingsUIPanel.livingsPanelSoldiersGroupActive).setState (SoldierGroupData.STATE_GUARD);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_PATROL) {
				if (LivingsUIPanel.livingsPanelSoldiersGroupActive != -1) {
					Game.getWorld ().getSoldierGroups ().getGroup (LivingsUIPanel.livingsPanelSoldiersGroupActive).setState (SoldierGroupData.STATE_PATROL);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_BOSS) {
				if (LivingsUIPanel.livingsPanelSoldiersGroupActive != -1) {
					Game.getWorld ().getSoldierGroups ().getGroup (LivingsUIPanel.livingsPanelSoldiersGroupActive).setState (SoldierGroupData.STATE_BOSS);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_AUTOEQUIP) {
				if (LivingsUIPanel.livingsPanelSoldiersGroupActive != -1) {
					ArrayList<Integer> alSoldiers = Game.getWorld ().getSoldierGroups ().getGroup (LivingsUIPanel.livingsPanelSoldiersGroupActive).getLivingIDs ();
					LivingEntity le;
					for (int s = 0; s < alSoldiers.size (); s++) {
						le = World.getLivingEntityByID (alSoldiers.get (s));
						if (le != null) {
							CommandPanel.executeCommand (CommandPanel.COMMAND_AUTOEQUIP, Integer.toString (le.getID ()), null, null, null, SmartMenu.ICON_TYPE_ITEM);
						}
					}
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_AUTOEQUIP) {
				ArrayList<Integer> alCitizens;
				if (LivingsUIPanel.livingsPanelCitizensGroupActive != -1) {
					alCitizens = Game.getWorld ().getCitizenGroups ().getGroup (LivingsUIPanel.livingsPanelCitizensGroupActive).getLivingIDs ();
				} else {
					alCitizens = Game.getWorld ().getCitizenGroups ().getCitizensWithoutGroup ();
				}
				LivingEntity le;
				for (int s = 0; s < alCitizens.size (); s++) {
					le = World.getLivingEntityByID (alCitizens.get (s));
					if (le != null) {
						CommandPanel.executeCommand (CommandPanel.COMMAND_AUTOEQUIP, Integer.toString (le.getID ()), null, null, null, SmartMenu.ICON_TYPE_ITEM);
					}
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_DISBAND) {
				if (LivingsUIPanel.livingsPanelSoldiersGroupActive != -1) {
					ArrayList<Integer> alSoldiers = Game.getWorld ().getSoldierGroups ().getGroup (LivingsUIPanel.livingsPanelSoldiersGroupActive).getLivingIDs ();
					LivingEntity le;
					int iSize = alSoldiers.size ();
					for (int s = 0; s < iSize; s++) {
						le = World.getLivingEntityByID (alSoldiers.get (0));
						if (le != null) {
							((Citizen) le).getSoldierData ().setState (SoldierData.STATE_GUARD, -1, le.getID ());
						}
					}
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_DISBAND) {
				ArrayList<Integer> alCitizens;
				if (LivingsUIPanel.livingsPanelCitizensGroupActive != -1) {
					alCitizens = Game.getWorld ().getCitizenGroups ().getGroup (LivingsUIPanel.livingsPanelCitizensGroupActive).getLivingIDs ();
				} else {
					alCitizens = Game.getWorld ().getCitizenGroups ().getCitizensWithoutGroup ();
				}

				LivingEntity le;
				for (int s = (alCitizens.size () - 1); s >= 0; s--) {
					le = World.getLivingEntityByID (alCitizens.get (s));
					if (le != null) {
						CommandPanel.executeCommand (CommandPanel.COMMAND_CITIZEN_SET_JOB_GROUP, Integer.toString (le.getID ()), Integer.toString (-1), null, null, SmartMenu.ICON_TYPE_ITEM);
					}
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_CHANGE_JOBS) {
				ProfessionsUIPanel.setProfessionsPanelActive (LivingsUIPanel.livingsPanelCitizensGroupActive, false);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN
					|| iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS || iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP) {
				Point p = LivingsUIPanel.isMouseOnLivingsButtons (x, y);
				if (p != null) {
					ArrayList<Integer> alLivings = LivingsUIPanel.getLivings ();
					int iIndex = LivingsUIPanel.getLivingsIndex ();
					if (alLivings != null && p != null && (p.y + iIndex) >= 0 && (p.y + iIndex) < alLivings.size ()) {
						LivingEntity le = World.getLivingEntityByID (alLivings.get ((p.y + iIndex)));
						if (le != null) {
							if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS) {
								Game.getWorld ().setView (le.getCoordinates ());
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);

								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_LIVINGS, null);
								return;
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS) {
								ProfessionsUIPanel.setProfessionsPanelActive (le.getID (), true);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_JOBS, null);
								return;
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER) {
								CommandPanel.executeCommand (CommandPanel.COMMAND_CONVERT_TO_SOLDIER, Integer.toString (le.getID ()), null, null, null, SmartMenu.ICON_TYPE_ITEM);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_CONVERTSOLDIER, null);
								return;
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN) {
								CommandPanel.executeCommand (CommandPanel.COMMAND_CONVERT_TO_CIVILIAN, Integer.toString (le.getID ()), null, null, null, SmartMenu.ICON_TYPE_ITEM);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_CONVERTCIVILIAN, null);
								return;
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD) {
								CommandPanel.executeCommand (CommandPanel.COMMAND_SOLDIER_SET_STATE, Integer.toString (le.getID ()), Integer.toString (SoldierData.STATE_GUARD), new Point3D (-1, -1, -1), null, SmartMenu.ICON_TYPE_ITEM);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								return;
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL) {
								CommandPanel.executeCommand (CommandPanel.COMMAND_SOLDIER_SET_STATE, Integer.toString (le.getID ()), Integer.toString (SoldierData.STATE_PATROL), new Point3D (-1, -1, -1), null, SmartMenu.ICON_TYPE_ITEM);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								return;
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS) {
								CommandPanel.executeCommand (CommandPanel.COMMAND_SOLDIER_SET_STATE, Integer.toString (le.getID ()), Integer.toString (SoldierData.STATE_BOSS_AROUND), new Point3D (-1, -1, -1), null, SmartMenu.ICON_TYPE_ITEM);
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
								return;
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD) {
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									MilitaryItem mi = le.getEquippedData ().getHead ();
									SmartMenu smUnequip = null;
									if (mi != null) {
										smUnequip = new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("Citizen.20") + le.getEquippedData ().getHead ().getExtendedTilename (), null, CommandPanel.COMMAND_WEAR_OFF, null, null, new Point3D (le.getID (), MilitaryItem.LOCATION_HEAD, -1), le.getEquippedData ().getHead ().getItemTextColor ()); //$NON-NLS-1$
									}
									LivingsUIPanel.createMilitaryContextMenu (smUnequip, MilitaryItem.LOCATION_HEAD, le, x, y);
									UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
									// Tutorial flow
									Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_BODY, null);
									return;
								}
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY) {
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									MilitaryItem mi = le.getEquippedData ().getBody ();
									SmartMenu smUnequip = null;
									if (mi != null) {
										smUnequip = new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("Citizen.20") + le.getEquippedData ().getBody ().getExtendedTilename (), null, CommandPanel.COMMAND_WEAR_OFF, null, null, new Point3D (le.getID (), MilitaryItem.LOCATION_BODY, -1), le.getEquippedData ().getBody ().getItemTextColor ()); //$NON-NLS-1$
									}
									LivingsUIPanel.createMilitaryContextMenu (smUnequip, MilitaryItem.LOCATION_BODY, le, x, y);
									UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
									// Tutorial flow
									Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_BODY, null);
									return;
								}
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS) {
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									MilitaryItem mi = le.getEquippedData ().getLegs ();
									SmartMenu smUnequip = null;
									if (mi != null) {
										smUnequip = new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("Citizen.20") + le.getEquippedData ().getLegs ().getExtendedTilename (), null, CommandPanel.COMMAND_WEAR_OFF, null, null, new Point3D (le.getID (), MilitaryItem.LOCATION_LEGS, -1), le.getEquippedData ().getLegs ().getItemTextColor ()); //$NON-NLS-1$
									}
									LivingsUIPanel.createMilitaryContextMenu (smUnequip, MilitaryItem.LOCATION_LEGS, le, x, y);
									UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
									// Tutorial flow
									Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_BODY, null);
									return;
								}
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET) {
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									MilitaryItem mi = le.getEquippedData ().getFeet ();
									SmartMenu smUnequip = null;
									if (mi != null) {
										smUnequip = new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("Citizen.20") + le.getEquippedData ().getFeet ().getExtendedTilename (), null, CommandPanel.COMMAND_WEAR_OFF, null, null, new Point3D (le.getID (), MilitaryItem.LOCATION_FEET, -1), le.getEquippedData ().getFeet ().getItemTextColor ()); //$NON-NLS-1$
									}
									LivingsUIPanel.createMilitaryContextMenu (smUnequip, MilitaryItem.LOCATION_FEET, le, x, y);
									UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
									// Tutorial flow
									Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_BODY, null);
									return;
								}
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON) {
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									MilitaryItem mi = le.getEquippedData ().getWeapon ();
									SmartMenu smUnequip = null;
									if (mi != null) {
										smUnequip = new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("Citizen.20") + le.getEquippedData ().getWeapon ().getExtendedTilename (), null, CommandPanel.COMMAND_WEAR_OFF, null, null, new Point3D (le.getID (), MilitaryItem.LOCATION_WEAPON, -1), le.getEquippedData ().getWeapon ().getItemTextColor ()); //$NON-NLS-1$
									}
									LivingsUIPanel.createMilitaryContextMenu (smUnequip, MilitaryItem.LOCATION_WEAPON, le, x, y);
									UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
									// Tutorial flow
									Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_BODY, null);
									return;
								}
							} else if (iPanel == MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP) {
								if (LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS || LivingsUIPanel.getLivingsPanelActive () == LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
									CommandPanel.executeCommand (CommandPanel.COMMAND_AUTOEQUIP, Integer.toString (le.getID ()), null, null, null, SmartMenu.ICON_TYPE_ITEM);
									UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
									// Tutorial flow
									Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_LIVINGS_AUTOEQUIP, null);
									return;
								}
							}
						}
					}
				}
			}

			if (iPanel == MOUSE_LIVINGS_PANEL) {
				if (mouseButton == 1) { // Botón derecho (cerramos)
					LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);
				}
				return;
			}
		}

		/*
		 * TRADE PANEL
		 */
		if (TradeUIPanel.isTradePanelActive ()) {
			if (iPanel == MOUSE_TRADE_PANEL_BUTTONS_CLOSE) {
				TradeUIPanel.setTradePanelActive (false);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				return;
			}

			if (TradeUIPanel.tradePanel != null) {
				CaravanData caravanData = Game.getWorld ().getCurrentCaravanData ();
				boolean bTrading = (caravanData != null && caravanData.getStatus () == CaravanData.STATUS_TRADING);

				if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_DOWN_CARAVAN) {
					TradeUIPanel.tradePanel.scrollDownCaravan ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_UP_CARAVAN) {
					TradeUIPanel.tradePanel.scrollUpCaravan ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (iPanel == MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_DOWN_CARAVAN) {
					TradeUIPanel.tradePanel.scrollDownToBuyCaravan ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (iPanel == MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_UP_CARAVAN) {
					TradeUIPanel.tradePanel.scrollUpToBuyCaravan ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (iPanel == MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_DOWN_TOWN) {
					TradeUIPanel.tradePanel.scrollDownToSellTown ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (iPanel == MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_UP_TOWN) {
					TradeUIPanel.tradePanel.scrollUpToSellTown ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_DOWN_TOWN) {
					TradeUIPanel.tradePanel.scrollDownTown ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_UP_TOWN) {
					TradeUIPanel.tradePanel.scrollUpTown ();
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_CARAVAN) {
					Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
					if (p != null) {
						TradeUIPanel.tradePanel.selectItemToBuy (p.y + TradeUIPanel.tradePanel.getIndexButtonsCaravan ());
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_TO_BUY_CARAVAN) {
					Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
					if (p != null) {
						TradeUIPanel.tradePanel.selectItemToNonBuy (p.y + TradeUIPanel.tradePanel.getIndexButtonsToBuyCaravan ());
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_TOWN) {
					Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
					if (p != null) {
						TradeUIPanel.tradePanel.selectItemToSell (p.y + TradeUIPanel.tradePanel.getIndexButtonsTown ());
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_TO_SELL_TOWN) {
					Point p = TradeUIPanel.isMouseOnTradeButtons (x, y);
					if (p != null) {
						TradeUIPanel.tradePanel.selectItemToNonSell (p.y + TradeUIPanel.tradePanel.getIndexButtonsToSellTown ());
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
				} else if (!bTrading && iPanel == MOUSE_TRADE_PANEL_BUTTONS_CONFIRM) {
					if (TradeUIPanel.tradePanel.isTransactionReady ()) {
						if (Game.getWorld ().getCurrentCaravanData () != null) {
							Game.getWorld ().getCurrentCaravanData ().confirmTrade ();
							UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
							return;
						}
					}
				}
			}
			if (iPanel == MOUSE_TRADE_PANEL) {
				if (mouseButton == 1) { // Botón derecho (cerramos)
					TradeUIPanel.setTradePanelActive (false);
				}
				return;
			}
		}

		/*
		 * PRIORITIES PANEL
		 */
		if (PrioritiesUIPanel.isPrioritiesPanelActive ()) {
			if (iPanel == MOUSE_PRIORITIES_PANEL_ITEMS_DOWN) {
				Point p = PrioritiesUIPanel.isMouseOnPrioritiesItems (x, y);
				if (p != null) {
					if (p.x == MOUSE_PRIORITIES_PANEL_ITEMS_DOWN) {
						ActionPriorityManager.swapPriorities (p.y, p.y + 1);
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
				}
			} else if (iPanel == MOUSE_PRIORITIES_PANEL_ITEMS_UP) {
				Point p = PrioritiesUIPanel.isMouseOnPrioritiesItems (x, y);
				if (p != null) {
					if (p.x == MOUSE_PRIORITIES_PANEL_ITEMS_UP) {
						ActionPriorityManager.swapPriorities (p.y, p.y - 1);
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
						return;
					}
				}
			} else if (iPanel == MOUSE_PRIORITIES_PANEL_ITEMS) {
				Point p = PrioritiesUIPanel.isMouseOnPrioritiesItems (x, y);
				if (p != null && p.y == (PrioritiesUIPanel.PRIORITIES_PANEL_NUM_ITEMS - 1)) {
					PrioritiesUIPanel.setPrioritiesPanelActive (false);
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					return;
				}
			} else if (iPanel == MOUSE_PRIORITIES_PANEL) {
				if (mouseButton == 1) { // Botón derecho (cerramos)
					PrioritiesUIPanel.setPrioritiesPanelActive (false);
				}
				return;
			}
		}

		/*
		 * PRODUCTION PANEL
		 */
		if (iPanel == MOUSE_PRODUCTION_OPENCLOSE) {
			ProductionUIPanel.setProductionPanelLocked (!ProductionUIPanel.isProductionPanelLocked ());
			// setProductionPanelActive (!isProductionPanelActive ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (ProductionUIPanel.isProductionPanelActive ()) {
			if (iPanel == MOUSE_PRODUCTION_PANEL_ITEMS) {
				if (mouseButton == 1) { // Botón derecho (back al menú)
					if (ProductionUIPanel.productionPanelMenu.getParent () != null) {
						ProductionUIPanel.productionPanelMenu = ProductionUIPanel.productionPanelMenu.getParent ();
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					} else {
						ProductionUIPanel.setProductionPanelLocked (false);
					}
					return;
				}
				Point p = ProductionUIPanel.isMouseOnProductionItems (x, y);
				if (p != null) {
					if (p.x == MOUSE_PRODUCTION_PANEL_ITEMS) {
						SmartMenu smItem = ProductionUIPanel.productionPanelMenu.getItems ().get (p.y);
						if (smItem.getType () == SmartMenu.TYPE_MENU) {
							ProductionUIPanel.productionPanelMenu = smItem;
							ProductionUIPanel.createProductionPanel (smItem);
						} else if (smItem.getType () == SmartMenu.TYPE_ITEM) {
							if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
								CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());

								if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_RCONTROL)) {
									for (int rep = 0; rep < 99; rep++) {
										CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									}
								} else if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
								}
							} else {
								if (ProductionUIPanel.productionPanelMenu.getParent () != null) {
									ProductionUIPanel.productionPanelMenu = ProductionUIPanel.productionPanelMenu.getParent ();
									ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
								} else {
									ProductionUIPanel.setProductionPanelActive (false);
								}
							}
						}
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					}
				}
				return;
			} else if (iPanel == MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR) {
				Point p = ProductionUIPanel.isMouseOnProductionItems (x, y);
				if (p != null) {
					if (p.x == MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_REGULAR) {
						SmartMenu smItem = ProductionUIPanel.productionPanelMenu.getItems ().get (p.y);
						if (smItem.getType () == SmartMenu.TYPE_ITEM) {
							if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
								CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_REGULAR_PLUS, null, smItem.getParameter ());

								if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_RCONTROL)) {
									for (int rep = 0; rep < 99; rep++) {
										CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									}
								} else if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
									CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
								}
							}
						}
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					}
				}
			} else if (iPanel == MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR) {
				Point p = ProductionUIPanel.isMouseOnProductionItems (x, y);
				if (p != null) {
					if (p.x == MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_REGULAR) {
						SmartMenu smItem = ProductionUIPanel.productionPanelMenu.getItems ().get (p.y);
						if (smItem.getType () == SmartMenu.TYPE_ITEM) {
							if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
								Game.getWorld ().getTaskManager ().removeFromQueue (smItem.getParameter ());
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_REGULAR_MINUS, null, smItem.getParameter ());

								if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_RCONTROL)) {
									for (int rep = 0; rep < 99; rep++) {
										Game.getWorld ().getTaskManager ().removeFromQueue (smItem.getParameter ());
									}
								} else if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
									Game.getWorld ().getTaskManager ().removeFromQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().removeFromQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().removeFromQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().removeFromQueue (smItem.getParameter ());
								}
							}
						}
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					}
				}
			} else if (iPanel == MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED) {
				Point p = ProductionUIPanel.isMouseOnProductionItems (x, y);
				if (p != null) {
					if (p.x == MOUSE_PRODUCTION_PANEL_ITEMS_PLUS_AUTOMATED) {
						SmartMenu smItem = ProductionUIPanel.productionPanelMenu.getItems ().get (p.y);
						if (smItem.getType () == SmartMenu.TYPE_ITEM) {
							if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
								Game.getWorld ().getTaskManager ().addItemOnAutomatedQueue (smItem.getParameter ());
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_AUTOMATED_PLUS, null, smItem.getParameter ());

								if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_RCONTROL)) {
									for (int rep = 0; rep < 99; rep++) {
										Game.getWorld ().getTaskManager ().addItemOnAutomatedQueue (smItem.getParameter ());
									}
								} else if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
									Game.getWorld ().getTaskManager ().addItemOnAutomatedQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().addItemOnAutomatedQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().addItemOnAutomatedQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().addItemOnAutomatedQueue (smItem.getParameter ());
								}
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
							}
						}
					}
				}
			} else if (iPanel == MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED) {
				Point p = ProductionUIPanel.isMouseOnProductionItems (x, y);
				if (p != null) {
					if (p.x == MOUSE_PRODUCTION_PANEL_ITEMS_MINUS_AUTOMATED) {
						SmartMenu smItem = ProductionUIPanel.productionPanelMenu.getItems ().get (p.y);
						if (smItem.getType () == SmartMenu.TYPE_ITEM) {
							if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
								Game.getWorld ().getTaskManager ().removeItemOnAutomatedQueue (smItem.getParameter ());
								// Tutorial flow
								Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_AUTOMATED_MINUS, null, smItem.getParameter ());

								if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_RCONTROL)) {
									for (int rep = 0; rep < 99; rep++) {
										Game.getWorld ().getTaskManager ().removeItemOnAutomatedQueue (smItem.getParameter ());
									}
								} else if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
									Game.getWorld ().getTaskManager ().removeItemOnAutomatedQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().removeItemOnAutomatedQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().removeItemOnAutomatedQueue (smItem.getParameter ());
									Game.getWorld ().getTaskManager ().removeItemOnAutomatedQueue (smItem.getParameter ());
								}
								UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
							}
						}
					}
				}
			}
			if (iPanel == MOUSE_PRODUCTION_PANEL) {
				if (mouseButton == 1) { // Botón derecho (back al menú)
					if (ProductionUIPanel.productionPanelMenu.getParent () != null) {
						ProductionUIPanel.productionPanelMenu = ProductionUIPanel.productionPanelMenu.getParent ();
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					} else {
						ProductionUIPanel.setProductionPanelLocked (false);
					}
				}
				return;
			}
		}

		// BOTTOM
		if (iPanel == MOUSE_BOTTOM_OPENCLOSE) {
			BottomMenuUIPanel.setBottomMenuPanelLocked (!BottomMenuUIPanel.isBottomMenuPanelLocked ());
			// setProductionPanelActive (!isProductionPanelActive ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (BottomMenuUIPanel.isBottomMenuPanelActive ()) {
			if (iPanel == MOUSE_BOTTOM_LEFT_SCROLL) {
				if (BottomMenuUIPanel.bottomPanelItemIndex > 0) {
					BottomMenuUIPanel.bottomPanelItemIndex--;
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				}
				return;
			}
			if (iPanel == MOUSE_BOTTOM_RIGHT_SCROLL) {
				if ((BottomMenuUIPanel.bottomPanelItemIndex + BottomMenuUIPanel.BOTTOM_PANEL_NUM_ITEMS) < BottomMenuUIPanel.currentMenu.getItems ().size ()) {
					BottomMenuUIPanel.bottomPanelItemIndex++;
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				}
				return;
			}
			if (iPanel == MOUSE_BOTTOM_ITEMS) {
				int iItem = BottomMenuUIPanel.isMouseOnBottomItems (x, y);
				if (iItem != -1) {
					iItem = iItem + BottomMenuUIPanel.bottomPanelItemIndex;
					SmartMenu smItem = BottomMenuUIPanel.currentMenu.getItems ().get (iItem);
					if (smItem.getType () == SmartMenu.TYPE_MENU && smItem.getItems () != null && smItem.getItems ().size () > 0) {
						// Activamos el subpanel de abajo
						BottomMenuUIPanel.bottomSubPanelMenu = smItem;
						BottomMenuUIPanel.createBottomSubPanel (smItem);
					} else if (smItem.getType () == SmartMenu.TYPE_ITEM) {
						CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
					}
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				}
				return;
			}
		}

		if (iPanel == MOUSE_BOTTOM_OPENCLOSE) {
			BottomMenuUIPanel.setBottomMenuPanelActive (!BottomMenuUIPanel.isBottomMenuPanelActive ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
		}

		// MENU
		if (iPanel == MOUSE_MENU_OPENCLOSE) {
			RightMenuUIPanel.setMenuPanelLocked (!RightMenuUIPanel.isMenuPanelLocked ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (RightMenuUIPanel.isMenuPanelActive ()) {
			if (iPanel == MOUSE_MENU_PANEL_ITEMS) {
				if (mouseButton == 1) { // Botón derecho (back al menú)
					if (RightMenuUIPanel.menuPanelMenu.getParent () != null) {
						RightMenuUIPanel.menuPanelMenu = RightMenuUIPanel.menuPanelMenu.getParent ();
						createMenuPanel (RightMenuUIPanel.menuPanelMenu);
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					} else {
						RightMenuUIPanel.setMenuPanelLocked (false);
					}
					return;
				}
				int iItem = RightMenuUIPanel.isMouseOnMenuItems (x, y);
				if (iItem != -1) {
					SmartMenu smItem = RightMenuUIPanel.menuPanelMenu.getItems ().get (iItem);
					if (smItem.getType () == SmartMenu.TYPE_MENU) {
						createMenuPanel (smItem);
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
						RightMenuUIPanel.menuPanelMenu = smItem;
					} else if (smItem.getType () == SmartMenu.TYPE_ITEM) {
						if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
							CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
						} else {
							if (RightMenuUIPanel.menuPanelMenu.getParent () != null) {
								RightMenuUIPanel.menuPanelMenu = RightMenuUIPanel.menuPanelMenu.getParent ();
								createMenuPanel (RightMenuUIPanel.menuPanelMenu);
								ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
							}
						}
					}
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				}
				return;
			}
			if (iPanel == MOUSE_MENU_PANEL) {
				if (mouseButton == 1) { // Botón derecho (back al menú)
					if (RightMenuUIPanel.menuPanelMenu.getParent () != null) {
						RightMenuUIPanel.menuPanelMenu = RightMenuUIPanel.menuPanelMenu.getParent ();
						createMenuPanel (RightMenuUIPanel.menuPanelMenu);
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
						UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
					} else {
						RightMenuUIPanel.setMenuPanelLocked (false);
					}
				}
				return;
			}
		}

		if (iPanel == MOUSE_MENU_OPENCLOSE) {
			RightMenuUIPanel.setMenuPanelActive (!RightMenuUIPanel.isMenuPanelActive ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
		}

		// BOTTOM submenu
		if (BottomMenuUIPanel.bottomSubPanelMenu != null && iPanel == MOUSE_BOTTOM_SUBITEMS) {
			// BOTTOM SUBPANEL
			if (mouseButton == 1) { // Botón derecho (back al menú)
				BottomMenuUIPanel.bottomSubPanelMenu = BottomMenuUIPanel.bottomSubPanelMenu.getParent ();
				if (BottomMenuUIPanel.bottomSubPanelMenu != null) {
					if (BottomMenuUIPanel.bottomSubPanelMenu.getParent () == null) {
						BottomMenuUIPanel.bottomSubPanelMenu = null;
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
					} else {
						BottomMenuUIPanel.createBottomSubPanel (BottomMenuUIPanel.bottomSubPanelMenu);
					}
					UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
				}
				return;
			}

			int iItem = BottomMenuUIPanel.isMouseOnBottomSubItems (x, y);
			if (iItem != -1) {
				SmartMenu smItem = BottomMenuUIPanel.bottomSubPanelMenu.getItems ().get (iItem);
				if (smItem.getType () == SmartMenu.TYPE_MENU && smItem.getItems () != null && smItem.getItems ().size () > 0) {
					// Activamos el subpanel de abajo
					BottomMenuUIPanel.bottomSubPanelMenu = smItem;
					BottomMenuUIPanel.createBottomSubPanel (smItem);
				} else if (smItem.getType () == SmartMenu.TYPE_ITEM) {
					if (!smItem.getCommand ().equalsIgnoreCase (CommandPanel.COMMAND_BACK)) {
						CommandPanel.executeCommand (smItem.getCommand (), smItem.getParameter (), smItem.getParameter2 (), smItem.getDirectCoordinates (), smItem.getIcon (), smItem.getIconType ());
						BottomMenuUIPanel.bottomSubPanelMenu = null;
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
					} else {
						BottomMenuUIPanel.bottomSubPanelMenu = BottomMenuUIPanel.bottomSubPanelMenu.getParent ();
						if (BottomMenuUIPanel.bottomSubPanelMenu != null) {
							if (BottomMenuUIPanel.bottomSubPanelMenu.getParent () == null) {
								BottomMenuUIPanel.bottomSubPanelMenu = null;
								ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
							} else {
								BottomMenuUIPanel.createBottomSubPanel (BottomMenuUIPanel.bottomSubPanelMenu);
							}
						}
					}
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			}
			return;
		}

		if (BottomMenuUIPanel.bottomSubPanelMenu != null && iPanel == MOUSE_BOTTOM_SUBPANEL) {
			// BOTTOM SUBPANEL
			if (mouseButton == 1) { // Botón derecho (back al menú)
				BottomMenuUIPanel.bottomSubPanelMenu = BottomMenuUIPanel.bottomSubPanelMenu.getParent ();
				if (BottomMenuUIPanel.bottomSubPanelMenu != null) {
					if (BottomMenuUIPanel.bottomSubPanelMenu.getParent () == null) {
						BottomMenuUIPanel.bottomSubPanelMenu = null;
						ProductionUIPanel.createProductionPanel (ProductionUIPanel.productionPanelMenu);
					} else {
						BottomMenuUIPanel.createBottomSubPanel (BottomMenuUIPanel.bottomSubPanelMenu);
					}
				}
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			}
			return;
		}

		// ICONS
		if (iPanel == MOUSE_ICON_LEVEL_UP) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_UP, null, null, null, null, 0);
			if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_RCONTROL)) {
				for (int rep = 0; rep < 99; rep++) {
					CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_UP, null, null, null, null, 0);
				}
			} else if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_UP, null, null, null, null, 0);
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_UP, null, null, null, null, 0);
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_UP, null, null, null, null, 0);
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_UP, null, null, null, null, 0);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_LEVEL_DOWN) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_DOWN, null, null, null, null, 0);
			if (Keyboard.isKeyDown (Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown (Keyboard.KEY_RCONTROL)) {
				for (int rep = 0; rep < 99; rep++) {
					CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_DOWN, null, null, null, null, 0);
				}
			} else if (Keyboard.isKeyDown (Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown (Keyboard.KEY_RSHIFT)) {
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_DOWN, null, null, null, null, 0);
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_DOWN, null, null, null, null, 0);
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_DOWN, null, null, null, null, 0);
				CommandPanel.executeCommand (CommandPanel.COMMAND_LEVEL_DOWN, null, null, null, null, 0);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_CITIZEN_PREVIOUS) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_PREVIOUS_CITIZEN, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_CITIZEN_NEXT) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_NEXT_CITIZEN, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_INFO_NUM_CITIZENS) {
			if (LivingsUIPanel.getLivingsPanelActive () != LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS) {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_CITIZENS, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);

				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_CITIZENS, null);
			} else {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_SOLDIER_PREVIOUS) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_PREVIOUS_SOLDIER, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_SOLDIER_NEXT) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_NEXT_SOLDIER, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_INFO_NUM_SOLDIERS) {
			if (LivingsUIPanel.getLivingsPanelActive () != LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS) {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_SOLDIERS, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);

				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_SOLDIERS, null);
			} else {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_HERO_PREVIOUS) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_PREVIOUS_HERO, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_HERO_NEXT) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_NEXT_HERO, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_INFO_NUM_HEROES) {
			if (LivingsUIPanel.getLivingsPanelActive () != LivingsUIPanel.LIVINGS_PANEL_TYPE_HEROES) {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_HEROES, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);

				Game.updateTutorialFlow (TutorialTrigger.TYPE_INT_ICONHIT, TutorialTrigger.ICON_INT_HEROES, null);
			} else {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_INFO_CARAVAN) {
			TradeUIPanel.setTradePanelActive (!TradeUIPanel.isTradePanelActive ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_PRIORITIES) {
			PrioritiesUIPanel.setPrioritiesPanelActive (!PrioritiesUIPanel.isPrioritiesPanelActive ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_MATS) {
			MatsUIPanel.setMatsPanelActive (!MatsUIPanel.isMatsPanelActive ());
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_MINIBLOCKS) {
			MainPanel.toggleMiniBlocks ();
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_FLATMOUSE) {
			MainPanel.toggleFlatMouse ();
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_3DMOUSE) {
			MainPanel.toggle3DMouse ();
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_GRID) {
			MainPanel.toggleGrid ();
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_SETTINGS) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_EXIT_TO_MAIN_MENU, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_PAUSE_RESUME) {
			CommandPanel.executeCommand (CommandPanel.COMMAND_PAUSE, null, null, null, null, 0);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}
		if (iPanel == MOUSE_ICON_LOWER_SPEED) {
			if (World.SPEED > 1) {
				CommandPanel.executeCommand (CommandPanel.COMMAND_LOWER_SPEED, null, null, null, null, 0);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			}
			return;
		}
		if (iPanel == MOUSE_ICON_INCREASE_SPEED) {
			if (World.SPEED < World.SPEED_MAX) {
				CommandPanel.executeCommand (CommandPanel.COMMAND_INCREASE_SPEED, null, null, null, null, 0);
				UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			}
			return;
		}
		if (iPanel == MOUSE_TUTORIAL_ICON) {
			toggleTutorialPanel (false);
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}

		if (iPanel == MOUSE_MESSAGES_ICON_ANNOUNCEMENT) {
			if (MessagesUIPanel.getMessagesPanelActive () != MessagesPanel.TYPE_ANNOUNCEMENT) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_ANNOUNCEMENT);
			} else {
				MessagesUIPanel.setMessagesPanelActive (-1);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}

		if (iPanel == MOUSE_MESSAGES_ICON_COMBAT) {
			if (MessagesUIPanel.getMessagesPanelActive () != MessagesPanel.TYPE_COMBAT) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_COMBAT);
			} else {
				MessagesUIPanel.setMessagesPanelActive (-1);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}

		if (iPanel == MOUSE_MESSAGES_ICON_HEROES) {
			if (MessagesUIPanel.getMessagesPanelActive () != MessagesPanel.TYPE_HEROES) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_HEROES);
			} else {
				MessagesUIPanel.setMessagesPanelActive (-1);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}

		if (iPanel == MOUSE_MESSAGES_ICON_SYSTEM) {
			if (MessagesUIPanel.getMessagesPanelActive () != MessagesPanel.TYPE_SYSTEM) {
				MessagesUIPanel.setMessagesPanelActive (MessagesPanel.TYPE_SYSTEM);
			} else {
				MessagesUIPanel.setMessagesPanelActive (-1);
			}
			UtilsAL.play (UtilsAL.SOURCE_FX_CLICK);
			return;
		}

		// MINIMAP
		if (iPanel == MOUSE_MINIMAP) {
			MiniMapPanel.mousePressed (x - minimapPanelX, y - minimapPanelY, mouseButton);
			return;
		}
	}

	/**
	 * Key pressed. Mostly because the ESC key to close the panels
	 * 
	 * @param tecla
	 * @return true if something is done
	 */
	public static boolean keyPressed (int tecla) {
		if (tecla == Keyboard.KEY_ESCAPE) {
			if (imagesPanel != null && ImagesPanel.isVisible ()) {
				ImagesPanel.setVisible (false);
				return true;
			} else if (PrioritiesUIPanel.isPrioritiesPanelActive ()) {
				PrioritiesUIPanel.setPrioritiesPanelActive (false);
				return true;
			} else if (TradeUIPanel.isTradePanelActive ()) {
				TradeUIPanel.setTradePanelActive (false);
				return true;
			} else if (ProfessionsUIPanel.isProfessionsPanelActive ()) {
				ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
				return true;
			} else if (LivingsUIPanel.isLivingsPanelActive ()) {
				LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);
				return true;
			} else if (MatsUIPanel.isMatsPanelActive ()) {
				MatsUIPanel.setMatsPanelActive (false);
				return true;
			} else if (MessagesUIPanel.isMessagesPanelActive ()) {
				MessagesUIPanel.setMessagesPanelActive (-1);
				return true;
			} else if (PileUIPanel.isPilePanelActive ()) {
				PileUIPanel.setPilePanelActive (-1, false);
				return true;
			}
		}

		return false;
	}

	public static int getImagesPanelOffset () {
		if (ProductionUIPanel.isProductionPanelActive ()) {
			return ProductionUIPanel.productionPanelPoint.x + ProductionUIPanel.PRODUCTION_PANEL_WIDTH;
		}

		return 0;
	}

	public static void toggleTutorialPanel (boolean bNewGame) {
		if (Game.getCurrentMissionData () != null && Game.getCurrentMissionData ().getTutorialFlows ().size () > 0) {
			if (imagesPanel == null) {
				imagesPanel = new ImagesPanel (MainPanel.renderWidth, MainPanel.renderHeight, Game.getCurrentMissionData ());
				if (bNewGame) {
					ImagesPanel.setCurrentFlowIndex (0);
				} else {
					ImagesPanel.setCurrentFlowIndex (Game.getCurrentMissionData ().getTutorialFlowIndex ());
				}
				ImagesPanel.setMaxFlowIndex (Game.getCurrentMissionData ().getTutorialFlowIndex ());
				ImagesPanel.setVisible (true);
			} else {
				ImagesPanel.setVisible (!ImagesPanel.isVisible ());
			}
		}
	}

	public static void closePanels (boolean bPriorities, boolean bTrade, boolean bMessages, boolean bMats, boolean bLivings, boolean bPile, boolean bProfessions) {
		if (bPriorities) {
			PrioritiesUIPanel.setPrioritiesPanelActive (false);
		}
		if (bTrade) {
			TradeUIPanel.setTradePanelActive (false);
		}
		if (bMessages) {
			MessagesUIPanel.setMessagesPanelActive (-1);
		}
		if (bMats) {
			MatsUIPanel.setMatsPanelActive (false);
		}
		if (bLivings) {
			LivingsUIPanel.setLivingsPanelActive (LivingsUIPanel.LIVINGS_PANEL_TYPE_NONE, LivingsUIPanel.livingsPanelSoldiersGroupActive, LivingsUIPanel.livingsPanelCitizensGroupActive);
		}
		if (bPile) {
			PileUIPanel.setPilePanelActive (-1, false);
		}
		if (bProfessions) {
			ProfessionsUIPanel.setProfessionsPanelActive (-1, false);
		}
	}

	private void createMenuPanel (SmartMenu menu) {
		RightMenuUIPanel.MENU_PANEL_HEIGHT = renderHeight - (minimapPanelY + MINIMAP_PANEL_HEIGHT + 2 * PIXELS_TO_BORDER) - BottomMenuUIPanel.BOTTOM_PANEL_HEIGHT - 2 * PIXELS_TO_BORDER;
		RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y = (RightMenuUIPanel.MENU_PANEL_HEIGHT - PIXELS_TO_BORDER) / (RightMenuUIPanel.MENU_ITEM_HEIGHT + PIXELS_TO_BORDER);
		if (RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y < 1) {
			RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y = 1;
		}
		RightMenuUIPanel.MENU_PANEL_HEIGHT = RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y * (RightMenuUIPanel.MENU_ITEM_HEIGHT + PIXELS_TO_BORDER) + PIXELS_TO_BORDER;

		int iMaxItems = menu.getItems ().size ();
		RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_X = (iMaxItems / RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y);
		if ((iMaxItems % RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y) != 0) {
			RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_X++;
		}
		RightMenuUIPanel.MENU_PANEL_WIDTH = RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_X * (RightMenuUIPanel.MENU_ITEM_WIDTH + PIXELS_TO_BORDER) + PIXELS_TO_BORDER;

		while (((RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y - 1) * RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_X) >= iMaxItems) {
			RightMenuUIPanel.MENU_PANEL_HEIGHT -= (RightMenuUIPanel.MENU_ITEM_HEIGHT + PIXELS_TO_BORDER);
			RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y--;
		}

		RightMenuUIPanel.menuPanelPoint.setLocation (renderWidth - RightMenuUIPanel.MENU_PANEL_WIDTH - RightMenuUIPanel.tileOpenRightMenu.getTileWidth (), minimapPanelY + MINIMAP_PANEL_HEIGHT + 2 * PIXELS_TO_BORDER);

		// Positions
		RightMenuUIPanel.menuPanelItemsPosition = new ArrayList<Point> ();
		for (int y = 0; y < RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_Y; y++) {
			for (int x = 0; x < RightMenuUIPanel.MENU_PANEL_NUM_ITEMS_X; x++) {
				RightMenuUIPanel.menuPanelItemsPosition.add (new Point (RightMenuUIPanel.menuPanelPoint.x + PIXELS_TO_BORDER + (x * (RightMenuUIPanel.MENU_ITEM_WIDTH + PIXELS_TO_BORDER)), RightMenuUIPanel.menuPanelPoint.y + PIXELS_TO_BORDER + (y * (RightMenuUIPanel.MENU_ITEM_HEIGHT + PIXELS_TO_BORDER))));
			}
		}

		// Minibotón para abrir/cerrar el menú
		RightMenuUIPanel.tileOpenCloseRightMenuPoint.setLocation (renderWidth - RightMenuUIPanel.tileOpenRightMenu.getTileWidth (), renderHeight / 2 - RightMenuUIPanel.tileOpenRightMenu.getTileHeight () / 2);
	}

	public static void closeTypingPanel () {
		if (typingPanel != null) {
			if (TypingPanel.TYPING_TYPE == TypingPanel.TYPE_RENAME_GROUP) {
				int iGroup = TypingPanel.TYPING_PARAMETER;
				if (iGroup >= 0 && iGroup < SoldierGroups.MAX_GROUPS) {
					Game.getWorld ().getSoldierGroups ().getGroup (iGroup).setName (TypingPanel.getNewText ());
				}
			} else if (TypingPanel.TYPING_TYPE == TypingPanel.TYPE_RENAME_JOB_GROUP) {
				int iGroup = TypingPanel.TYPING_PARAMETER;
				if (iGroup >= 0 && iGroup < CitizenGroups.MAX_GROUPS) {
					Game.getWorld ().getCitizenGroups ().getGroup (iGroup).setName (TypingPanel.getNewText ());
				}
			} else if (TypingPanel.TYPING_TYPE == TypingPanel.TYPE_ADD_TEXT_TO_ITEM) {
				// Miramos si el item existe
				Integer iItemID = Integer.valueOf (TypingPanel.TYPING_PARAMETER);
				if (World.getItems ().containsKey (iItemID)) {
					// Existe
					ArrayList<String> alTexts = World.getItemsText ().get (iItemID);
					if (alTexts == null) {
						alTexts = new ArrayList<String> ();
					}
					alTexts.add (TypingPanel.getNewText ());
					World.getItemsText ().put (iItemID, alTexts);
				}
			}

			typingPanel = null;
		}
	}

	/**
	 * Limpia todos los datos (se usa cuando se sale de la partida y se va al menú principal)
	 */
	public static void clear () {
		BottomMenuUIPanel.currentMenu = null;
		BottomMenuUIPanel.bottomSubPanelMenu = null;
		RightMenuUIPanel.menuPanelMenu = null;

		ProductionUIPanel.productionPanelActive = false;
		PrioritiesUIPanel.prioritiesPanelActive = false;
		TradeUIPanel.tradePanelActive = false;
		TradeUIPanel.tradePanel = null;

		ImagesPanel.clear ();
		imagesPanel = null;
	}
}
