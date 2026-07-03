package xaos.panels;

import java.awt.Point;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import xaos.campaign.TutorialFlow;
import xaos.data.CitizenGroups;
import xaos.data.EquippedData;
import xaos.data.SoldierData;
import xaos.data.SoldierGroupData;
import xaos.data.SoldierGroups;
import xaos.main.Game;
import xaos.main.World;
import xaos.panels.menus.ContextMenu;
import xaos.panels.menus.SmartMenu;
import xaos.tiles.Tile;
import xaos.tiles.entities.items.Container;
import xaos.tiles.entities.items.Item;
import xaos.tiles.entities.items.ItemManager;
import xaos.tiles.entities.items.ItemManagerItem;
import xaos.tiles.entities.items.military.MilitaryItem;
import xaos.tiles.entities.living.Citizen;
import xaos.tiles.entities.living.LivingEntity;
import xaos.utils.ColorGL;
import xaos.utils.Messages;
import xaos.utils.UtilFont;
import xaos.utils.UtilsGL;


public final class LivingsUIPanel {

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_CLOSE_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_CLOSE, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_UP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_UP, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_DOWN_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_DOWN, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE, -1);

	public static Point MOUSE_LIVINGS_PANEL_SGROUP_NOGROUP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SGROUP_NOGROUP, -1);

	public static Point MOUSE_LIVINGS_PANEL_SGROUP_GROUP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SGROUP_GROUP, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_RENAME_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_RENAME, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_GUARD_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_GUARD, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_PATROL_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_PATROL, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_BOSS_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_BOSS, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_AUTOEQUIP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_AUTOEQUIP, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_DISBAND_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_DISBAND, -1);

	public static Point MOUSE_LIVINGS_PANEL_CGROUP_NOGROUP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_CGROUP_NOGROUP, -1);

	public static Point MOUSE_LIVINGS_PANEL_CGROUP_GROUP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_CGROUP_GROUP, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_RENAME_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_RENAME, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_AUTOEQUIP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_AUTOEQUIP, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_DISBAND_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_DISBAND, -1);

	public static Point MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_CHANGE_JOBS_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_CHANGE_JOBS, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP, -1);

	public static Point MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN_POINT = new Point (UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN, -1);

	public static int LIVINGS_PANEL_WIDTH;

	public static int LIVINGS_PANEL_HEIGHT;

	public static int LIVINGS_PANEL_MAX_ROWS = 1;

	public static int LIVINGS_PANEL_GROUPS_WIDTH;

	public static int LIVINGS_PANEL_GROUPS_HEIGHT;

	public static int LIVINGS_PANEL_SINGLE_GROUP_WIDTH;

	public static int LIVINGS_PANEL_SINGLE_GROUP_HEIGHT;

	public static final int LIVINGS_PANEL_TYPE_NONE = -1;

	public static final int LIVINGS_PANEL_TYPE_CITIZENS = 0;

	public static final int LIVINGS_PANEL_TYPE_SOLDIERS = 1;

	public static final int LIVINGS_PANEL_TYPE_HEROES = 2;

	// LIVINGS PANEL
	public static Tile[] tileLivingsPanel;

	private static Point livingsPanelPoint = new Point (0, 0);

	private static int livingsPanelActive = LIVINGS_PANEL_TYPE_NONE;

	public static int livingsPanelCitizensGroupActive = -1;

	public static int livingsPanelSoldiersGroupActive = -1;

	public static Point livingsPanelClosePoint = new Point (0, 0);

	private static Point livingsPanelIconScrollUpPoint = new Point (0, 0);

	private static Point livingsPanelIconScrollDownPoint = new Point (0, 0);

	private static Point livingsPanelPagesPoint = new Point (0, 0);

	private static Point[] livingsPanelRowPoints;

	private static Point[] livingsPanelRowHeadPoints;

	private static Point[] livingsPanelRowBodyPoints;

	private static Point[] livingsPanelRowLegsPoints;

	private static Point[] livingsPanelRowFeetPoints;

	private static Point[] livingsPanelRowWeaponPoints;

	public static Tile tileLivingsPanelRowNoHead;

	public static Tile tileLivingsPanelRowNoBody;

	public static Tile tileLivingsPanelRowNoLegs;

	public static Tile tileLivingsPanelRowNoFeet;

	public static Tile tileLivingsPanelRowNoWeapon;

	private static Point[] livingsPanelRowAutoequipPoints;

	public static Tile tileLivingsRowAutoequip;

	public static Tile tileLivingsRowAutoequipON;

	public static boolean tileLivingsRowAutoequipAlpha[][];

	private static Point[] livingsPanelRowProfessionPoints;

	private static Point[] livingsPanelRowJobsGroupsPoints;

	private static Point[] livingsPanelRowConvertCivilianSoldierPoints;

	private static Point[] livingsPanelRowConvertSoldierGuardPoints;

	private static Point[] livingsPanelRowConvertSoldierPatrolPoints;

	private static Point[] livingsPanelRowConvertSoldierBossPoints;

	public static Tile tileLivingsRowProfession;

	public static Tile tileLivingsRowJobsGroups;

	public static Tile tileLivingsRowProfessionON;

	public static Tile tileLivingsRowJobsGroupsON;

	public static Tile tileLivingsRowConvertSoldier;

	public static Tile tileLivingsRowConvertSoldierON;

	public static Tile tileLivingsRowConvertCivilian;

	public static Tile tileLivingsRowConvertCivilianON;

	public static Tile tileLivingsRowConvertSoldierGuard;

	public static Tile tileLivingsRowConvertSoldierGuardON;

	public static Tile tileLivingsRowConvertSoldierPatrol;

	public static Tile tileLivingsRowConvertSoldierPatrolON;

	public static Tile tileLivingsRowConvertSoldierBoss;

	public static Tile tileLivingsRowConvertSoldierBossON;

	public static boolean tileLivingsRowProfessionAlpha[][];

	public static boolean tileLivingsRowJobsGroupsAlpha[][];

	public static boolean tileLivingsRowConvertSoldierAlpha[][];

	public static boolean tileLivingsRowConvertCivilianAlpha[][];

	public static boolean tileLivingsRowConvertSoldierGuardAlpha[][];

	public static boolean tileLivingsRowConvertSoldierPatrolAlpha[][];

	public static boolean tileLivingsRowConvertSoldierBossAlpha[][];

	private static Point[] livingsPanelRowGroupPoints;

	public static Tile tileLivingsRowGroupAdd;

	public static Tile tileLivingsRowGroupAddON;

	public static boolean tileLivingsRowGroupAddAlpha[][];

	public static Tile tileLivingsRowGroupRemove;

	public static Tile tileLivingsRowGroupRemoveON;

	public static boolean tileLivingsRowGroupRemoveAlpha[][];

	public static Point livingsPanelIconRestrictUpPoint = new Point (0, 0);

	private static Point livingsPanelIconRestrictDownPoint = new Point (0, 0);

	// LIVINGS GROUP PANEL data
	public static Tile[] tileLivingsGroupPanel;

	private static Point livingsGroupPanelPoint = new Point (0, 0);

	private static Point livingsSingleGroupPanelPoint = new Point (0, 0);

	private static Point livingsGroupPanelFirstIconPoint = new Point (0, 0);

	private static int livingsGroupPanelIconsSeparation = Tile.TERRAIN_ICON_WIDTH; // Esto se cambiará seguro, no tiene nada que ver, es por si acaso

	public static Tile tileLivingsGroup;

	public static Tile tileLivingsGroupON;

	public static Tile tileLivingsGroupGreen;

	public static boolean tileLivingsGroupAlpha[][];

	public static Tile tileLivingsNoGroup;

	public static Tile tileLivingsNoGroupON;

	public static Tile tileLivingsNoGroupGreen;

	public static boolean tileLivingsNoGroupAlpha[][];

	public static Tile tileLivingsNoJobGroup;

	public static Tile tileLivingsNoJobGroupON;

	public static Tile tileLivingsNoJobGroupGreen;

	public static boolean tileLivingsNoJobGroupAlpha[][];

	public static Tile tileLivingsJobGroup;

	public static Tile tileLivingsJobGroupON;

	public static Tile tileLivingsJobGroupGreen;

	public static boolean tileLivingsJobGroupAlpha[][];

	private static Point livingsSingleGroupRenamePoint = new Point (0, 0);

	public static Tile tileLivingsSingleGroupRename;

	public static Tile tileLivingsSingleGroupRenameON;

	public static boolean tileLivingsSingleGroupRenameAlpha[][];

	public static Tile tileLivingsSingleJobGroupRename;

	public static Tile tileLivingsSingleJobGroupRenameON;

	public static boolean tileLivingsSingleJobGroupRenameAlpha[][];

	private static Point livingsSingleGroupGuardPoint = new Point (0, 0);

	public static Tile tileLivingsSingleGroupGuard;

	public static Tile tileLivingsSingleGroupGuardON;

	public static boolean tileLivingsSingleGroupGuardAlpha[][];

	private static Point livingsSingleGroupPatrolPoint = new Point (0, 0);

	public static Tile tileLivingsSingleGroupPatrol;

	public static Tile tileLivingsSingleGroupPatrolON;

	public static boolean tileLivingsSingleGroupPatrolAlpha[][];

	private static Point livingsSingleGroupBossPoint = new Point (0, 0);

	public static Tile tileLivingsSingleGroupBoss;

	public static Tile tileLivingsSingleGroupBossON;

	public static boolean tileLivingsSingleGroupBossAlpha[][];

	private static Point livingsSingleGroupDisbandPoint = new Point (0, 0);

	public static Tile tileLivingsSingleGroupDisband;

	public static Tile tileLivingsSingleGroupDisbandON;

	public static boolean tileLivingsSingleGroupDisbandAlpha[][];

	public static Tile tileLivingsSingleJobGroupDisband;

	public static Tile tileLivingsSingleJobGroupDisbandON;

	public static boolean tileLivingsSingleJobGroupDisbandAlpha[][];

	private static Point livingsSingleGroupAutoequipPoint = new Point (0, 0);

	private static Point livingsSingleGroupChangeJobsPoint = new Point (0, 0);

	public static Tile tileLivingsSingleGroupChangeJobs;

	public static Tile tileLivingsSingleGroupChangeJobsON;

	public static boolean tileLivingsSingleGroupChangeJobsAlpha[][];

	// LIVINGS PANEL data
	public static int[] livingsDataIndexPages;

	public static int[] livingsDataIndexPagesCitizenGroups;

	public static int[] livingsDataIndexPagesSoldierGroups;

	public static boolean tileIconLevelUpAlpha[][];

	public static boolean tileIconLevelDownAlpha[][];

	public static boolean checkGroupsPanelEnabled (int iLivingsPanelActive) {
		return (iLivingsPanelActive == LIVINGS_PANEL_TYPE_CITIZENS && livingsPanelCitizensGroupActive != -1) || (iLivingsPanelActive == LIVINGS_PANEL_TYPE_SOLDIERS && livingsPanelSoldiersGroupActive != -1);
	}

	public static void renderLivingsPanel (int mouseX, int mouseY, int mousePanel) {
		// Possible mini icon blinks?
		// Blink
		TutorialFlow tutorialFlow = null;
		if ((UIPanel.blinkTurns >= UIPanel.MAX_BLINK_TURNS / 2)) {
			if (Game.getCurrentMissionData () != null && ImagesPanel.getCurrentFlowIndex () >= 0 && ImagesPanel.getCurrentFlowIndex () < Game.getCurrentMissionData ().getTutorialFlows ().size ()) {
				tutorialFlow = Game.getCurrentMissionData ().getTutorialFlows ().get (ImagesPanel.getCurrentFlowIndex ());
			}
		}

		Point pItem = isMouseOnLivingsButtons (mouseX, mouseY);

		// XAVI GL11.glColor4f (1, 1, 1, 1);
		int iCurrentTexture = tileLivingsPanel[0].getTextureID ();
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, iCurrentTexture);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UIPanel.renderBackground (tileLivingsPanel, livingsPanelPoint, LIVINGS_PANEL_WIDTH, LIVINGS_PANEL_HEIGHT);

		// Close button
		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileButtonClose, iCurrentTexture);
		if (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_CLOSE) {
			UIPanel.drawTile (UIPanel.tileButtonClose, livingsPanelClosePoint);
		} else {
			UIPanel.drawTile (UIPanel.tileButtonCloseDisabled, livingsPanelClosePoint);
		}

		// Groups panel
		if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
			// Subpanel
			iCurrentTexture = UtilsGL.setTexture (tileLivingsGroupPanel[0], iCurrentTexture);
			UIPanel.renderBackground (tileLivingsGroupPanel, livingsGroupPanelPoint, LIVINGS_PANEL_GROUPS_WIDTH, LIVINGS_PANEL_GROUPS_HEIGHT);

			// No-group icon
			if (livingsPanelSoldiersGroupActive == -1 || mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SGROUP_NOGROUP) {
				iCurrentTexture = UtilsGL.setTexture (tileLivingsNoGroupON, iCurrentTexture);
				UIPanel.drawTile (tileLivingsNoGroupON, livingsGroupPanelFirstIconPoint);
			} else {
				// Miramos si el grupo tiene miembros
				if (Game.getWorld ().getSoldierGroups ().getSoldiersWithoutGroup ().size () > 0) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsNoGroupGreen, iCurrentTexture);
					UIPanel.drawTile (tileLivingsNoGroupGreen, livingsGroupPanelFirstIconPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsNoGroup, iCurrentTexture);
					UIPanel.drawTile (tileLivingsNoGroup, livingsGroupPanelFirstIconPoint);
				}
			}

			// Group icons
			for (int i = 0; i < SoldierGroups.MAX_GROUPS; i++) {
				if (livingsPanelSoldiersGroupActive == i || (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_SGROUP_GROUP && pItem.y == i)) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsGroupON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsGroupON, livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation, false);
				} else {
					// Miramos si el grupo tiene miembros
					if (Game.getWorld ().getSoldierGroups ().getGroup (i).getLivingIDs ().size () > 0) {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsGroupGreen, iCurrentTexture);
						UIPanel.drawTile (tileLivingsGroupGreen, livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation, false);
					} else {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsGroup, iCurrentTexture);
						UIPanel.drawTile (tileLivingsGroup, livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation, false);
					}
				}
			}

			// Single group subpanel
			if (livingsPanelSoldiersGroupActive != -1) {
				iCurrentTexture = UtilsGL.setTexture (tileLivingsGroupPanel[0], iCurrentTexture);
				UIPanel.renderBackground (tileLivingsGroupPanel, livingsSingleGroupPanelPoint, LIVINGS_PANEL_SINGLE_GROUP_WIDTH, LIVINGS_PANEL_SINGLE_GROUP_HEIGHT);

				int iGroupState = Game.getWorld ().getSoldierGroups ().getGroup (livingsPanelSoldiersGroupActive).getState ();
				// Botones
				if (mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_RENAME) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupRenameON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupRenameON, livingsSingleGroupRenamePoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupRename, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupRename, livingsSingleGroupRenamePoint);
				}
				if (iGroupState == SoldierGroupData.STATE_GUARD || mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_GUARD) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupGuardON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupGuardON, livingsSingleGroupGuardPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupGuard, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupGuard, livingsSingleGroupGuardPoint);
				}
				if (iGroupState == SoldierGroupData.STATE_PATROL || mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_PATROL) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupPatrolON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupPatrolON, livingsSingleGroupPatrolPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupPatrol, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupPatrol, livingsSingleGroupPatrolPoint);
				}
				if (iGroupState == SoldierGroupData.STATE_BOSS || mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_BOSS) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupBossON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupBossON, livingsSingleGroupBossPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupBoss, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupBoss, livingsSingleGroupBossPoint);
				}
				if (mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_DISBAND) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupDisbandON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupDisbandON, livingsSingleGroupDisbandPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupDisband, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupDisband, livingsSingleGroupDisbandPoint);
				}
				if (mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_AUTOEQUIP) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsRowAutoequipON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsRowAutoequipON, livingsSingleGroupAutoequipPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsRowAutoequip, iCurrentTexture);
					UIPanel.drawTile (tileLivingsRowAutoequip, livingsSingleGroupAutoequipPoint);
				}

				// Text
				UtilsGL.glEnd ();
				iCurrentTexture = Game.TEXTURE_FONT_ID;
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);

				// Group name
				String sText = Game.getWorld ().getSoldierGroups ().getGroup (livingsPanelSoldiersGroupActive).getName ();
				UtilsGL.drawStringWithBorder (sText, livingsSingleGroupPanelPoint.x + LIVINGS_PANEL_SINGLE_GROUP_WIDTH / 2 - UtilFont.getWidth (sText) / 2, livingsSingleGroupPanelPoint.y, ColorGL.WHITE, ColorGL.BLACK);
			}

			// Text
			if (iCurrentTexture != Game.TEXTURE_FONT_ID) {
				UtilsGL.glEnd ();
				iCurrentTexture = Game.TEXTURE_FONT_ID;
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);
			}

			// Group icons (1, 2, 3, 4, 5, 6, 7, 8)
			String sNumber;
			for (int i = 0; i < SoldierGroups.MAX_GROUPS; i++) {
				sNumber = Integer.toString (i + 1);
				UtilsGL.drawStringWithBorder (sNumber, livingsGroupPanelFirstIconPoint.x + tileLivingsGroup.getTileWidth () / 2 - UtilFont.getWidth (sNumber) / 2, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation + tileLivingsGroup.getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.WHITE, ColorGL.BLACK);
			}
		} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
			// Subpanel
			iCurrentTexture = UtilsGL.setTexture (tileLivingsGroupPanel[0], iCurrentTexture);
			UIPanel.renderBackground (tileLivingsGroupPanel, livingsGroupPanelPoint, LIVINGS_PANEL_GROUPS_WIDTH, LIVINGS_PANEL_GROUPS_HEIGHT);

			// No-group icon
			if (livingsPanelCitizensGroupActive == -1 || mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_CGROUP_NOGROUP) {
				iCurrentTexture = UtilsGL.setTexture (tileLivingsNoJobGroupON, iCurrentTexture);
				UIPanel.drawTile (tileLivingsNoJobGroupON, livingsGroupPanelFirstIconPoint);
			} else {
				// Miramos si el grupo tiene miembros
				if (Game.getWorld ().getCitizenGroups ().getCitizensWithoutGroup ().size () > 0) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsNoJobGroupGreen, iCurrentTexture);
					UIPanel.drawTile (tileLivingsNoJobGroupGreen, livingsGroupPanelFirstIconPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsNoJobGroup, iCurrentTexture);
					UIPanel.drawTile (tileLivingsNoJobGroup, livingsGroupPanelFirstIconPoint);
				}
			}

			// Group icons
			for (int i = 0; i < CitizenGroups.MAX_GROUPS; i++) {
				if (livingsPanelCitizensGroupActive == i || (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_CGROUP_GROUP && pItem.y == i)) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsJobGroupON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsJobGroupON, livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation, false);
				} else {
					// Miramos si el grupo tiene miembros
					if (Game.getWorld ().getCitizenGroups ().getGroup (i).getLivingIDs ().size () > 0) {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsJobGroupGreen, iCurrentTexture);
						UIPanel.drawTile (tileLivingsJobGroupGreen, livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation, false);
					} else {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsJobGroup, iCurrentTexture);
						UIPanel.drawTile (tileLivingsJobGroup, livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation, false);
					}
				}
			}

			// Single group subpanel
			if (livingsPanelCitizensGroupActive != -1) {
				iCurrentTexture = UtilsGL.setTexture (tileLivingsGroupPanel[0], iCurrentTexture);
				UIPanel.renderBackground (tileLivingsGroupPanel, livingsSingleGroupPanelPoint, LIVINGS_PANEL_SINGLE_GROUP_WIDTH, LIVINGS_PANEL_SINGLE_GROUP_HEIGHT);

				// Botones
				if (mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_RENAME) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleJobGroupRenameON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleJobGroupRenameON, livingsSingleGroupRenamePoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleJobGroupRename, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleJobGroupRename, livingsSingleGroupRenamePoint);
				}
				if (mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_CHANGE_JOBS) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupChangeJobsON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupChangeJobsON, livingsSingleGroupChangeJobsPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleGroupChangeJobs, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleGroupChangeJobs, livingsSingleGroupChangeJobsPoint);
				}
				if (mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_DISBAND) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleJobGroupDisbandON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleJobGroupDisbandON, livingsSingleGroupDisbandPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsSingleJobGroupDisband, iCurrentTexture);
					UIPanel.drawTile (tileLivingsSingleJobGroupDisband, livingsSingleGroupDisbandPoint);
				}
				if (mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_AUTOEQUIP) {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsRowAutoequipON, iCurrentTexture);
					UIPanel.drawTile (tileLivingsRowAutoequipON, livingsSingleGroupAutoequipPoint);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsRowAutoequip, iCurrentTexture);
					UIPanel.drawTile (tileLivingsRowAutoequip, livingsSingleGroupAutoequipPoint);
				}

				// Text
				UtilsGL.glEnd ();
				iCurrentTexture = Game.TEXTURE_FONT_ID;
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);

				// Group name
				String sText = Game.getWorld ().getCitizenGroups ().getGroup (livingsPanelCitizensGroupActive).getName ();
				UtilsGL.drawStringWithBorder (sText, livingsSingleGroupPanelPoint.x + LIVINGS_PANEL_SINGLE_GROUP_WIDTH / 2 - UtilFont.getWidth (sText) / 2, livingsSingleGroupPanelPoint.y, ColorGL.WHITE, ColorGL.BLACK);
			}

			// Text
			if (iCurrentTexture != Game.TEXTURE_FONT_ID) {
				UtilsGL.glEnd ();
				iCurrentTexture = Game.TEXTURE_FONT_ID;
				GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
				GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
				UtilsGL.glBegin (GL11.GL_QUADS);
			}

			// Group icons (1, 2, 3, 4, 5, 6, 7, 8)
			String sNumber;
			for (int i = 0; i < CitizenGroups.MAX_GROUPS; i++) {
				sNumber = Integer.toString (i + 1);
				UtilsGL.drawStringWithBorder (sNumber, livingsGroupPanelFirstIconPoint.x + tileLivingsGroup.getTileWidth () / 2 - UtilFont.getWidth (sNumber) / 2, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation + tileLivingsGroup.getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.WHITE, ColorGL.BLACK);
			}
		}

		// Restrict
		if ((getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS && livingsPanelCitizensGroupActive == -1) || (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_HEROES)) {
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsRestriction ()) {
				UtilsGL.setColorRed ();
			}
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileIconLevelUp, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileIconLevelUp, livingsPanelIconRestrictUpPoint, UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP);
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileIconLevelDown, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileIconLevelDown, livingsPanelIconRestrictDownPoint, UIPanel.ICON_WIDTH, UIPanel.ICON_HEIGHT, mousePanel == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsRestriction ()) {
				UtilsGL.unsetColor ();
			}
		}

		ArrayList<Integer> alLivingIDs = getLivings ();
		int iNumLivings;
		if (alLivingIDs != null) {
			iNumLivings = alLivingIDs.size ();
		} else {
			iNumLivings = 0;
		}
		if (iNumLivings == 0) {
			UtilsGL.glEnd ();

			String sText;
			if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
				sText = Messages.getString ("UIPanel.34"); //$NON-NLS-1$
			} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
				sText = Messages.getString ("UIPanel.37"); //$NON-NLS-1$
			} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_HEROES) {
				sText = Messages.getString ("UIPanel.38"); //$NON-NLS-1$
			} else {
				sText = Messages.getString ("UIPanel.39"); //$NON-NLS-1$
			}

			int iTextWidth = UtilFont.getWidth (sText);
			GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
			GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
			UtilsGL.glBegin (GL11.GL_QUADS);
			UtilsGL.drawStringWithBorder (sText, livingsPanelPoint.x + LIVINGS_PANEL_WIDTH / 2 - iTextWidth / 2, livingsPanelPoint.y + LIVINGS_PANEL_HEIGHT / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.ORANGE, ColorGL.BLACK);

			// Restrict
			if ((getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS && livingsPanelCitizensGroupActive == -1) || (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_HEROES)) {
				int iLevel;
				if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
					iLevel = Game.getWorld ().getRestrictHaulEquippingLevel ();
				} else {
					iLevel = Game.getWorld ().getRestrictExploringLevel ();
				}
				iLevel = World.MAP_NUM_LEVELS_OUTSIDE - iLevel;
				sText = Integer.toString (iLevel);
				UtilsGL.drawString (sText, (livingsPanelIconRestrictUpPoint.x + UIPanel.tileIconLevelUp.getTileWidth ()) + ((livingsPanelIconRestrictDownPoint.x) - (livingsPanelIconRestrictUpPoint.x + UIPanel.tileIconLevelUp.getTileWidth ())) / 2 - UtilFont.getWidth (sText) / 2, livingsPanelIconRestrictUpPoint.y + UIPanel.tileIconLevelUp.getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.BLACK);
			}

			UtilsGL.glEnd ();

			return;
		}

		// Num livigs > 0, comprobamos índices
		int iNumPages = (iNumLivings % LIVINGS_PANEL_MAX_ROWS == 0) ? iNumLivings / LIVINGS_PANEL_MAX_ROWS : (iNumLivings / LIVINGS_PANEL_MAX_ROWS) + 1;
		int iIndexPage;
		boolean bNoGroupsPanel = !checkGroupsPanelEnabled (getLivingsPanelActive ());
		if (bNoGroupsPanel) {
			iIndexPage = livingsDataIndexPages[getLivingsPanelActive ()];
		} else {
			if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
				iIndexPage = livingsDataIndexPagesCitizenGroups[livingsPanelCitizensGroupActive];
			} else {
				iIndexPage = livingsDataIndexPagesSoldierGroups[livingsPanelSoldiersGroupActive];
			}
		}
		if (iIndexPage > iNumPages) {
			if (bNoGroupsPanel) {
				livingsDataIndexPages[getLivingsPanelActive ()] = iNumPages;
			} else {
				if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
					livingsDataIndexPagesCitizenGroups[livingsPanelCitizensGroupActive] = iNumPages;
				} else {
					livingsDataIndexPagesSoldierGroups[livingsPanelSoldiersGroupActive] = iNumPages;
				}
			}
			iIndexPage = iNumPages;
		} else if (iIndexPage < 1) {
			if (bNoGroupsPanel) {
				livingsDataIndexPages[getLivingsPanelActive ()] = 1;
			} else {
				if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
					livingsDataIndexPagesCitizenGroups[livingsPanelCitizensGroupActive] = 1;
				} else {
					livingsDataIndexPagesSoldierGroups[livingsPanelSoldiersGroupActive] = 1;
				}
			}
			iIndexPage = 1;
		}

		// Scrolls
		if (iIndexPage > 1) {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUp, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUp, livingsPanelIconScrollUpPoint, (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_UP));
		} else {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollUpDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollUpDisabled, livingsPanelIconScrollUpPoint);
		}
		if (iIndexPage < iNumPages) {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDown, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDown, livingsPanelIconScrollDownPoint, (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_DOWN));
		} else {
			iCurrentTexture = UtilsGL.setTexture (UIPanel.tileScrollDownDisabled, iCurrentTexture);
			UIPanel.drawTile (UIPanel.tileScrollDownDisabled, livingsPanelIconScrollDownPoint);
		}

		// Rows + equipment
		// Livings pictures + military stuff + civ/soldier stuff
		int iMaxRows = Math.min (iNumLivings - ((iIndexPage - 1) * LIVINGS_PANEL_MAX_ROWS), livingsPanelRowPoints.length);
		iMaxRows = Math.min (iMaxRows, LIVINGS_PANEL_MAX_ROWS);

		iCurrentTexture = UtilsGL.setTexture (UIPanel.tileBottomItem, iCurrentTexture);
		for (int i = 0; i < iMaxRows; i++) {
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsLivings ()) {
				UtilsGL.setColorRed ();
			}
			UIPanel.drawTile (UIPanel.tileBottomItem, livingsPanelRowPoints[i]);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsLivings ()) {
				UtilsGL.unsetColor ();
			}
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsBody ()) {
				UtilsGL.setColorRed ();
			}
			UIPanel.drawTile (UIPanel.tileBottomItem, livingsPanelRowHeadPoints[i]);
			UIPanel.drawTile (UIPanel.tileBottomItem, livingsPanelRowBodyPoints[i]);
			UIPanel.drawTile (UIPanel.tileBottomItem, livingsPanelRowLegsPoints[i]);
			UIPanel.drawTile (UIPanel.tileBottomItem, livingsPanelRowFeetPoints[i]);
			UIPanel.drawTile (UIPanel.tileBottomItem, livingsPanelRowWeaponPoints[i]);
			if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsBody ()) {
				UtilsGL.unsetColor ();
			}
		}
		LivingEntity le;
		int iIndex;
		for (int i = 0; i < iMaxRows; i++) {
			iIndex = ((iIndexPage - 1) * LIVINGS_PANEL_MAX_ROWS) + i;
			if (iIndex >= 0 && iIndex < alLivingIDs.size ()) {
				// Living
				le = World.getLivingEntityByID (alLivingIDs.get (iIndex));

				iCurrentTexture = renderLiving (le, livingsPanelRowPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - le.getTileWidth () / 2, livingsPanelRowPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - le.getTileHeight () / 2, getLivingsPanelActive (), iCurrentTexture);

				EquippedData equippedData = le.getEquippedData ();
				// Head
				if (equippedData.isWearing (MilitaryItem.LOCATION_HEAD)) {
					MilitaryItem mi = equippedData.getHead ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UIPanel.drawTile (mi, livingsPanelRowHeadPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - mi.getTileWidth () / 2, livingsPanelRowHeadPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - mi.getTileHeight () / 2, false);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsPanelRowNoHead, iCurrentTexture);
					UIPanel.drawTile (tileLivingsPanelRowNoHead, livingsPanelRowHeadPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tileLivingsPanelRowNoHead.getTileWidth () / 2, livingsPanelRowHeadPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsPanelRowNoHead.getTileHeight () / 2, false);
				}
				// Body
				if (equippedData.isWearing (MilitaryItem.LOCATION_BODY)) {
					MilitaryItem mi = equippedData.getBody ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UIPanel.drawTile (mi, livingsPanelRowBodyPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - mi.getTileWidth () / 2, livingsPanelRowBodyPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - mi.getTileHeight () / 2, false);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsPanelRowNoBody, iCurrentTexture);
					UIPanel.drawTile (tileLivingsPanelRowNoBody, livingsPanelRowBodyPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tileLivingsPanelRowNoBody.getTileWidth () / 2, livingsPanelRowBodyPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsPanelRowNoBody.getTileHeight () / 2, false);
				}
				// Legs
				if (equippedData.isWearing (MilitaryItem.LOCATION_LEGS)) {
					MilitaryItem mi = equippedData.getLegs ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UIPanel.drawTile (mi, livingsPanelRowLegsPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - mi.getTileWidth () / 2, livingsPanelRowLegsPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - mi.getTileHeight () / 2, false);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsPanelRowNoLegs, iCurrentTexture);
					UIPanel.drawTile (tileLivingsPanelRowNoLegs, livingsPanelRowLegsPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tileLivingsPanelRowNoLegs.getTileWidth () / 2, livingsPanelRowLegsPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsPanelRowNoLegs.getTileHeight () / 2, false);
				}
				// Feet
				if (equippedData.isWearing (MilitaryItem.LOCATION_FEET)) {
					MilitaryItem mi = equippedData.getFeet ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UIPanel.drawTile (mi, livingsPanelRowFeetPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - mi.getTileWidth () / 2, livingsPanelRowFeetPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - mi.getTileHeight () / 2, false);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsPanelRowNoFeet, iCurrentTexture);
					UIPanel.drawTile (tileLivingsPanelRowNoFeet, livingsPanelRowFeetPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tileLivingsPanelRowNoFeet.getTileWidth () / 2, livingsPanelRowFeetPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsPanelRowNoFeet.getTileHeight () / 2, false);
				}
				// Weapon
				if (equippedData.isWearing (MilitaryItem.LOCATION_WEAPON)) {
					MilitaryItem mi = equippedData.getWeapon ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UIPanel.drawTile (mi, livingsPanelRowWeaponPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - mi.getTileWidth () / 2, livingsPanelRowWeaponPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - mi.getTileHeight () / 2, false);
				} else {
					iCurrentTexture = UtilsGL.setTexture (tileLivingsPanelRowNoWeapon, iCurrentTexture);
					UIPanel.drawTile (tileLivingsPanelRowNoWeapon, livingsPanelRowWeaponPoints[i].x + UIPanel.tileBottomItem.getTileWidth () / 2 - tileLivingsPanelRowNoWeapon.getTileWidth () / 2, livingsPanelRowWeaponPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsPanelRowNoWeapon.getTileHeight () / 2, false);
				}

				// Autoequip
				if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS || getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
					if (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP && pItem.y == i) {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowAutoequipON, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsAutoequip ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowAutoequipON, livingsPanelRowAutoequipPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsAutoequip ()) {
							UtilsGL.unsetColor ();
						}
					} else {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowAutoequip, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsAutoequip ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowAutoequip, livingsPanelRowAutoequipPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsAutoequip ()) {
							UtilsGL.unsetColor ();
						}
					}
				}

				// Civ/soldier stuff
				if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
					if (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER && pItem.y == i) {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldierON, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertSoldier ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowConvertSoldierON, livingsPanelRowConvertCivilianSoldierPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertSoldier ()) {
							UtilsGL.unsetColor ();
						}
					} else {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldier, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertSoldier ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowConvertSoldier, livingsPanelRowConvertCivilianSoldierPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertSoldier ()) {
							UtilsGL.unsetColor ();
						}
					}
					if (livingsPanelCitizensGroupActive == -1) {
						if (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS && pItem.y == i) {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowProfessionON, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsJobs ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowProfessionON, livingsPanelRowProfessionPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsJobs ()) {
								UtilsGL.unsetColor ();
							}
						} else {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowProfession, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsJobs ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowProfession, livingsPanelRowProfessionPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsJobs ()) {
								UtilsGL.unsetColor ();
							}
						}
					}
					if (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE && pItem.y == i) {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowJobsGroupsON, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGroup ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowJobsGroupsON, livingsPanelRowJobsGroupsPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGroup ()) {
							UtilsGL.unsetColor ();
						}
					} else {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowJobsGroups, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGroup ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowJobsGroups, livingsPanelRowJobsGroupsPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGroup ()) {
							UtilsGL.unsetColor ();
						}
					}
				} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
					if (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN && pItem.y == i) {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertCivilianON, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertCivilian ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowConvertCivilianON, livingsPanelRowConvertCivilianSoldierPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertCivilian ()) {
							UtilsGL.unsetColor ();
						}
					} else {
						iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertCivilian, iCurrentTexture);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertCivilian ()) {
							UtilsGL.setColorRed ();
						}
						UIPanel.drawTile (tileLivingsRowConvertCivilian, livingsPanelRowConvertCivilianSoldierPoints[i]);
						if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsConvertCivilian ()) {
							UtilsGL.unsetColor ();
						}
					}

					// Soldier type
					Citizen soldier = ((Citizen) le);
					int soldierState = soldier.getSoldierData ().getState ();
					if (livingsPanelSoldiersGroupActive == -1) {
						if (soldierState == SoldierData.STATE_GUARD || (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD && pItem.y == i)) {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldierGuardON, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGuard ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowConvertSoldierGuardON, livingsPanelRowConvertSoldierGuardPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGuard ()) {
								UtilsGL.unsetColor ();
							}
						} else {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldierGuard, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGuard ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowConvertSoldierGuard, livingsPanelRowConvertSoldierGuardPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsGuard ()) {
								UtilsGL.unsetColor ();
							}
						}
						if (soldierState == SoldierData.STATE_PATROL || (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL && pItem.y == i)) {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldierPatrolON, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsPatrol ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowConvertSoldierPatrolON, livingsPanelRowConvertSoldierPatrolPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsPatrol ()) {
								UtilsGL.unsetColor ();
							}
						} else {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldierPatrol, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsPatrol ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowConvertSoldierPatrol, livingsPanelRowConvertSoldierPatrolPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsPatrol ()) {
								UtilsGL.unsetColor ();
							}
						}
						if (soldierState == SoldierData.STATE_BOSS_AROUND || (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS && pItem.y == i)) {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldierBossON, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsBoss ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowConvertSoldierBossON, livingsPanelRowConvertSoldierBossPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsBoss ()) {
								UtilsGL.unsetColor ();
							}
						} else {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowConvertSoldierBoss, iCurrentTexture);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsBoss ()) {
								UtilsGL.setColorRed ();
							}
							UIPanel.drawTile (tileLivingsRowConvertSoldierBoss, livingsPanelRowConvertSoldierBossPoints[i]);
							if (tutorialFlow != null && tutorialFlow.isBlinkMiniLivingsBoss ()) {
								UtilsGL.unsetColor ();
							}
						}

						if (soldierState == SoldierData.STATE_IN_A_GROUP || (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD && pItem.y == i)) {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowGroupAddON, iCurrentTexture);
							UIPanel.drawTile (tileLivingsRowGroupAddON, livingsPanelRowGroupPoints[i]);
						} else {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowGroupAdd, iCurrentTexture);
							UIPanel.drawTile (tileLivingsRowGroupAdd, livingsPanelRowGroupPoints[i]);
						}
					} else {
						if (pItem != null && pItem.x == UIPanel.MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE && pItem.y == i) {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowGroupRemoveON, iCurrentTexture);
							UIPanel.drawTile (tileLivingsRowGroupRemoveON, livingsPanelRowGroupPoints[i]);
						} else {
							iCurrentTexture = UtilsGL.setTexture (tileLivingsRowGroupRemove, iCurrentTexture);
							UIPanel.drawTile (tileLivingsRowGroupRemove, livingsPanelRowGroupPoints[i]);
						}
					}
				}
			}
		}

		UtilsGL.glEnd ();

		// Text
		// Pages
		String sText = iIndexPage + " / " + iNumPages; //$NON-NLS-1$
		GL11.glBindTexture (GL11.GL_TEXTURE_2D, Game.TEXTURE_FONT_ID);
		GL11.glTexEnvf (GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		UtilsGL.glBegin (GL11.GL_QUADS);
		UtilsGL.drawString (sText, livingsPanelPagesPoint.x - UtilFont.getWidth (sText) / 2, livingsPanelPagesPoint.y, ColorGL.BLACK);

		// Restrict
		if ((getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS && livingsPanelCitizensGroupActive == -1) || (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_HEROES)) {
			int iLevel;
			if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
				iLevel = Game.getWorld ().getRestrictHaulEquippingLevel ();
			} else {
				iLevel = Game.getWorld ().getRestrictExploringLevel ();
			}
			iLevel = World.MAP_NUM_LEVELS_OUTSIDE - iLevel;
			sText = Integer.toString (iLevel);
			UtilsGL.drawString (sText, (livingsPanelIconRestrictUpPoint.x + UIPanel.tileIconLevelUp.getTileWidth ()) + ((livingsPanelIconRestrictDownPoint.x) - (livingsPanelIconRestrictUpPoint.x + UIPanel.tileIconLevelUp.getTileWidth ())) / 2 - UtilFont.getWidth (sText) / 2, livingsPanelIconRestrictUpPoint.y + UIPanel.tileIconLevelUp.getTileHeight () / 2 - UtilFont.MAX_HEIGHT / 2, ColorGL.BLACK);
		}

		UtilsGL.glEnd ();
	}

	private static int renderLiving (LivingEntity le, int x, int y, int iType, int iCurrentTexture) {
		// Render
		iCurrentTexture = UtilsGL.setTexture (le, iCurrentTexture);
		UtilsGL.drawTexture (x, y, x + le.getTileWidth (), y + le.getTileHeight (), le.getBaseTileSetTexX0 (), le.getBaseTileSetTexY0 (), le.getBaseTileSetTexX1 (), le.getBaseTileSetTexY1 ());

		// Comprobamos que no tenga un effect de graphicchange
		boolean bGraphiChanged = false;
		for (int e = 0; e < le.getLivingEntityData ().getEffects ().size (); e++) {
			if (le.getLivingEntityData ().getEffects ().get (e).isGraphicChange ()) {
				bGraphiChanged = true;
				break;
			}
		}

		if (!bGraphiChanged) {
			// Miramos si lleva algo equipado para dibujarlo
			if (iType == LIVINGS_PANEL_TYPE_CITIZENS || iType == LIVINGS_PANEL_TYPE_SOLDIERS) {
				EquippedData equippedData = le.getEquippedData ();
				if (equippedData.isWearing (MilitaryItem.LOCATION_BODY)) {
					MilitaryItem mi = equippedData.getBody ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UtilsGL.drawTexture (x + le.getOffset_body_x (), y + le.getOffset_body_y (), x + le.getOffset_body_x () + mi.getTileWidth (), y + le.getOffset_body_y () + mi.getTileHeight (), mi.getBaseTileSetTexX0 (), mi.getBaseTileSetTexY0 (), mi.getBaseTileSetTexX1 (), mi.getBaseTileSetTexY1 ());
				}
				if (equippedData.isWearing (MilitaryItem.LOCATION_HEAD)) {
					MilitaryItem mi = equippedData.getHead ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UtilsGL.drawTexture (x + le.getOffset_head_x (), y + le.getOffset_head_y (), x + le.getOffset_head_x () + mi.getTileWidth (), y + le.getOffset_head_y () + mi.getTileHeight (), mi.getBaseTileSetTexX0 (), mi.getBaseTileSetTexY0 (), mi.getBaseTileSetTexX1 (), mi.getBaseTileSetTexY1 ());
				}
				if (equippedData.isWearing (MilitaryItem.LOCATION_FEET)) {
					MilitaryItem mi = equippedData.getFeet ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UtilsGL.drawTexture (x + le.getOffset_feet_x (), y + le.getOffset_feet_y (), x + le.getOffset_feet_x () + mi.getTileWidth (), y + le.getOffset_feet_y () + mi.getTileHeight (), mi.getBaseTileSetTexX0 (), mi.getBaseTileSetTexY0 (), mi.getBaseTileSetTexX1 (), mi.getBaseTileSetTexY1 ());
				}
				if (equippedData.isWearing (MilitaryItem.LOCATION_LEGS)) {
					MilitaryItem mi = equippedData.getLegs ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UtilsGL.drawTexture (x + le.getOffset_legs_x (), y + le.getOffset_legs_y (), x + le.getOffset_legs_x () + mi.getTileWidth (), y + le.getOffset_legs_y () + mi.getTileHeight (), mi.getBaseTileSetTexX0 (), mi.getBaseTileSetTexY0 (), mi.getBaseTileSetTexX1 (), mi.getBaseTileSetTexY1 ());
				}
				if (equippedData.isWearing (MilitaryItem.LOCATION_WEAPON)) {
					MilitaryItem mi = equippedData.getWeapon ();
					iCurrentTexture = UtilsGL.setTexture (mi, iCurrentTexture);
					UtilsGL.drawTexture (x + le.getOffset_weapon_x (), y + le.getOffset_weapon_y (), x + le.getOffset_weapon_x () + mi.getTileWidth (), y + le.getOffset_weapon_y () + mi.getTileHeight (), mi.getBaseTileSetTexX0 (), mi.getBaseTileSetTexY0 (), mi.getBaseTileSetTexX1 (), mi.getBaseTileSetTexY1 ());
				}
			}
		}

		return iCurrentTexture;
	}

	public static boolean isMouseOnLivingsPanel (int x, int y) {
		return ((y >= livingsPanelPoint.y && y < (livingsPanelPoint.y + LIVINGS_PANEL_HEIGHT)) && (x >= livingsPanelPoint.x && x < (livingsPanelPoint.x + LIVINGS_PANEL_WIDTH)));
	}

	public static Point isMouseOnLivingsButtons (int x, int y) {
		if (UIPanel.typingPanel != null) {
			return null;
		}

		Point point;
		// Close button
		point = livingsPanelClosePoint;
		if (x >= point.x && x < (point.x + UIPanel.tileButtonClose.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileButtonClose.getTileHeight ())) {
			if (!UIPanel.tileButtonCloseAlpha[x - point.x][y - point.y]) {
				return MOUSE_LIVINGS_PANEL_BUTTONS_CLOSE_POINT;
			}
		}

		// Scrolls
		point = livingsPanelIconScrollUpPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollUp.getTileHeight ())) {
			if (!UIPanel.tileScrollUpButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_UP_POINT;
			}
		}
		point = livingsPanelIconScrollDownPoint;
		if (x >= point.x && x < (point.x + UIPanel.tileScrollDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileScrollDown.getTileHeight ())) {
			if (!UIPanel.tileScrollDownButtonAlpha[x - point.x][y - point.y]) {
				return MOUSE_LIVINGS_PANEL_BUTTONS_SCROLL_DOWN_POINT;
			}
		}

		if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
			// Groups subpanel
			point = livingsGroupPanelFirstIconPoint;
			if (x >= point.x && x < (point.x + tileLivingsNoGroup.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsNoGroup.getTileHeight ())) {
				if (!tileLivingsNoGroupAlpha[x - point.x][y - point.y]) {
					return MOUSE_LIVINGS_PANEL_SGROUP_NOGROUP_POINT;
				}
			}

			// Los 10 grupos
			for (int i = 0; i < SoldierGroups.MAX_GROUPS; i++) {
				point = new Point (livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation);
				if (x >= point.x && x < (point.x + tileLivingsGroup.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsGroup.getTileHeight ())) {
					if (!tileLivingsGroupAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_SGROUP_GROUP_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_SGROUP_GROUP_POINT;
					}
				}
			}
		} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
			// Groups subpanel
			point = livingsGroupPanelFirstIconPoint;
			if (x >= point.x && x < (point.x + tileLivingsNoJobGroup.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsNoJobGroup.getTileHeight ())) {
				if (!tileLivingsNoJobGroupAlpha[x - point.x][y - point.y]) {
					return MOUSE_LIVINGS_PANEL_CGROUP_NOGROUP_POINT;
				}
			}

			// Los 10 grupos
			for (int i = 0; i < SoldierGroups.MAX_GROUPS; i++) {
				point = new Point (livingsGroupPanelFirstIconPoint.x, livingsGroupPanelFirstIconPoint.y + (i + 1) * livingsGroupPanelIconsSeparation);
				if (x >= point.x && x < (point.x + tileLivingsJobGroup.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsJobGroup.getTileHeight ())) {
					if (!tileLivingsJobGroupAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_CGROUP_GROUP_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_CGROUP_GROUP_POINT;
					}
				}
			}
		}

		if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
			// Single group
			if (livingsPanelCitizensGroupActive != -1) {
				point = livingsSingleGroupRenamePoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleJobGroupRename.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleJobGroupRename.getTileHeight ())) {
					if (!tileLivingsSingleJobGroupRenameAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_RENAME_POINT;
					}
				}
				point = livingsSingleGroupAutoequipPoint;
				if (x >= point.x && x < (point.x + tileLivingsRowAutoequip.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowAutoequip.getTileHeight ())) {
					if (!tileLivingsRowAutoequipAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_AUTOEQUIP_POINT;
					}
				}
				point = livingsSingleGroupChangeJobsPoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleGroupChangeJobs.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleGroupChangeJobs.getTileHeight ())) {
					if (!tileLivingsSingleGroupChangeJobsAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_CHANGE_JOBS_POINT;
					}
				}
				point = livingsSingleGroupDisbandPoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleJobGroupDisband.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleJobGroupDisband.getTileHeight ())) {
					if (!tileLivingsSingleJobGroupDisbandAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_CGROUP_DISBAND_POINT;
					}
				}
			} else {
				// Restrict
				point = livingsPanelIconRestrictUpPoint;
				if (x >= point.x && x < (point.x + UIPanel.tileIconLevelUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileIconLevelUp.getTileHeight ())) {
					if (!tileIconLevelUpAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP_POINT;
					}
				}
				point = livingsPanelIconRestrictDownPoint;
				if (x >= point.x && x < (point.x + UIPanel.tileIconLevelDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileIconLevelDown.getTileHeight ())) {
					if (!tileIconLevelDownAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN_POINT;
					}
				}
			}
		} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
			// Single group
			if (livingsPanelSoldiersGroupActive != -1) {
				point = livingsSingleGroupRenamePoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleGroupRename.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleGroupRename.getTileHeight ())) {
					if (!tileLivingsSingleGroupRenameAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_RENAME_POINT;
					}
				}
				point = livingsSingleGroupGuardPoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleGroupGuard.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleGroupGuard.getTileHeight ())) {
					if (!tileLivingsSingleGroupGuardAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_GUARD_POINT;
					}
				}
				point = livingsSingleGroupPatrolPoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleGroupPatrol.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleGroupPatrol.getTileHeight ())) {
					if (!tileLivingsSingleGroupPatrolAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_PATROL_POINT;
					}
				}
				point = livingsSingleGroupBossPoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleGroupBoss.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleGroupBoss.getTileHeight ())) {
					if (!tileLivingsSingleGroupBossAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_BOSS_POINT;
					}
				}
				point = livingsSingleGroupAutoequipPoint;
				if (x >= point.x && x < (point.x + tileLivingsRowAutoequip.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowAutoequip.getTileHeight ())) {
					if (!tileLivingsRowAutoequipAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_AUTOEQUIP_POINT;
					}
				}
				point = livingsSingleGroupDisbandPoint;
				if (x >= point.x && x < (point.x + tileLivingsSingleGroupDisband.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsSingleGroupDisband.getTileHeight ())) {
					if (!tileLivingsSingleGroupDisbandAlpha[x - point.x][y - point.y]) {
						return MOUSE_LIVINGS_PANEL_SINGLE_SGROUP_DISBAND_POINT;
					}
				}
			}
		} else {
			// Heroes
			// Restrict
			point = livingsPanelIconRestrictUpPoint;
			if (x >= point.x && x < (point.x + UIPanel.tileIconLevelUp.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileIconLevelUp.getTileHeight ())) {
				if (!tileIconLevelUpAlpha[x - point.x][y - point.y]) {
					return MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_UP_POINT;
				}
			}
			point = livingsPanelIconRestrictDownPoint;
			if (x >= point.x && x < (point.x + UIPanel.tileIconLevelDown.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileIconLevelDown.getTileHeight ())) {
				if (!tileIconLevelDownAlpha[x - point.x][y - point.y]) {
					return MOUSE_LIVINGS_PANEL_BUTTONS_RESTRICT_DOWN_POINT;
				}
			}
		}

		// Living, equipment, civ/soldier converts, ...
		ArrayList<Integer> alLivings = getLivings ();
		int iNumLivings;
		if (alLivings != null) {
			iNumLivings = alLivings.size ();
		} else {
			iNumLivings = 0;
		}

		if (iNumLivings > 0) {
			int iNumPages = (iNumLivings % LIVINGS_PANEL_MAX_ROWS == 0) ? iNumLivings / LIVINGS_PANEL_MAX_ROWS : (iNumLivings / LIVINGS_PANEL_MAX_ROWS) + 1;
			int iIndexPage;
			boolean bNoGroupsPanel = !checkGroupsPanelEnabled (getLivingsPanelActive ());
			if (bNoGroupsPanel) {
				iIndexPage = livingsDataIndexPages[getLivingsPanelActive ()];
			} else {
				if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
					iIndexPage = livingsDataIndexPagesCitizenGroups[livingsPanelCitizensGroupActive];
				} else {
					iIndexPage = livingsDataIndexPagesSoldierGroups[livingsPanelSoldiersGroupActive];
				}
			}
			if (iIndexPage > iNumPages) {
				if (bNoGroupsPanel) {
					livingsDataIndexPages[getLivingsPanelActive ()] = iNumPages;
				} else {
					if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
						livingsDataIndexPagesCitizenGroups[livingsPanelCitizensGroupActive] = iNumPages;
					} else {
						livingsDataIndexPagesSoldierGroups[livingsPanelSoldiersGroupActive] = iNumPages;
					}
				}
				iIndexPage = iNumPages;
			} else if (iIndexPage < 1) {
				if (bNoGroupsPanel) {
					livingsDataIndexPages[getLivingsPanelActive ()] = 1;
				} else {
					if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
						livingsDataIndexPagesCitizenGroups[livingsPanelCitizensGroupActive] = 1;
					} else {
						livingsDataIndexPagesSoldierGroups[livingsPanelSoldiersGroupActive] = 1;
					}
				}
				iIndexPage = 1;
			}

			int iMaxRows = Math.min (iNumLivings - ((iIndexPage - 1) * LIVINGS_PANEL_MAX_ROWS), livingsPanelRowPoints.length);
			iMaxRows = Math.min (iMaxRows, LIVINGS_PANEL_MAX_ROWS);

			for (int i = 0; i < iMaxRows; i++) {
				// Living
				point = livingsPanelRowPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_POINT;
					}
				}

				// Equipment
				point = livingsPanelRowHeadPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_HEAD_POINT;
					}
				}
				point = livingsPanelRowBodyPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_BODY_POINT;
					}
				}
				point = livingsPanelRowLegsPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_LEGS_POINT;
					}
				}
				point = livingsPanelRowFeetPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_FEET_POINT;
					}
				}
				point = livingsPanelRowWeaponPoints[i];
				if (x >= point.x && x < (point.x + UIPanel.tileBottomItem.getTileWidth ()) && y >= point.y && y < (point.y + UIPanel.tileBottomItem.getTileHeight ())) {
					if (!UIPanel.tileBottomItemAlpha[x - point.x][y - point.y]) {
						MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON_POINT.y = i;
						return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_WEAPON_POINT;
					}
				}

				// Autoequip
				if (getLivingsPanelActive () != LIVINGS_PANEL_TYPE_HEROES) {
					point = livingsPanelRowAutoequipPoints[i];
					if (x >= point.x && x < (point.x + tileLivingsRowAutoequip.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowAutoequip.getTileHeight ())) {
						if (!tileLivingsRowAutoequipAlpha[x - point.x][y - point.y]) {
							MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP_POINT.y = i;
							return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_AUTOEQUIP_POINT;
						}
					}
				}

				// Civ/soldier converts
				if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
					point = livingsPanelRowConvertCivilianSoldierPoints[i];
					if (x >= point.x && x < (point.x + tileLivingsRowConvertSoldier.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowConvertSoldier.getTileHeight ())) {
						if (!tileLivingsRowConvertSoldierAlpha[x - point.x][y - point.y]) {
							MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_POINT.y = i;
							return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_POINT;
						}
					}
					if (livingsPanelCitizensGroupActive == -1) {
						point = livingsPanelRowProfessionPoints[i];
						if (x >= point.x && x < (point.x + tileLivingsRowProfession.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowProfession.getTileHeight ())) {
							if (!tileLivingsRowProfessionAlpha[x - point.x][y - point.y]) {
								MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS_POINT.y = i;
								return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_PROFESSIONS_POINT;
							}
						}
					}
					point = livingsPanelRowJobsGroupsPoints[i];
					if (x >= point.x && x < (point.x + tileLivingsRowJobsGroups.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowJobsGroups.getTileHeight ())) {
						if (!tileLivingsRowJobsGroupsAlpha[x - point.x][y - point.y]) {
							MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE_POINT.y = i;
							return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_JOBS_GROUPS_ADDREMOVE_POINT;
						}
					}
				} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
					point = livingsPanelRowConvertCivilianSoldierPoints[i];
					if (x >= point.x && x < (point.x + tileLivingsRowConvertCivilian.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowConvertCivilian.getTileHeight ())) {
						if (!tileLivingsRowConvertCivilianAlpha[x - point.x][y - point.y]) {
							MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN_POINT.y = i;
							return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_CIVILIAN_POINT;
						}
					}

					if (livingsPanelSoldiersGroupActive == -1) {
						point = livingsPanelRowConvertSoldierGuardPoints[i];
						if (x >= point.x && x < (point.x + tileLivingsRowConvertSoldierGuard.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowConvertSoldierGuard.getTileHeight ())) {
							if (!tileLivingsRowConvertSoldierGuardAlpha[x - point.x][y - point.y]) {
								MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD_POINT.y = i;
								return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_GUARD_POINT;
							}
						}
						point = livingsPanelRowConvertSoldierPatrolPoints[i];
						if (x >= point.x && x < (point.x + tileLivingsRowConvertSoldierPatrol.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowConvertSoldierPatrol.getTileHeight ())) {
							if (!tileLivingsRowConvertSoldierPatrolAlpha[x - point.x][y - point.y]) {
								MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL_POINT.y = i;
								return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_PATROL_POINT;
							}
						}
						point = livingsPanelRowConvertSoldierBossPoints[i];
						if (x >= point.x && x < (point.x + tileLivingsRowConvertSoldierBoss.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowConvertSoldierBoss.getTileHeight ())) {
							if (!tileLivingsRowConvertSoldierBossAlpha[x - point.x][y - point.y]) {
								MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS_POINT.y = i;
								return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_CONVERT_SOLDIER_BOSS_POINT;
							}
						}
						point = livingsPanelRowGroupPoints[i];
						if (x >= point.x && x < (point.x + tileLivingsRowGroupAdd.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowGroupAdd.getTileHeight ())) {
							if (!tileLivingsRowGroupAddAlpha[x - point.x][y - point.y]) {
								MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD_POINT.y = i;
								return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_ADD_POINT;
							}
						}
					} else {
						point = livingsPanelRowGroupPoints[i];
						if (x >= point.x && x < (point.x + tileLivingsRowGroupRemove.getTileWidth ()) && y >= point.y && y < (point.y + tileLivingsRowGroupRemove.getTileHeight ())) {
							if (!tileLivingsRowGroupRemoveAlpha[x - point.x][y - point.y]) {
								MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE_POINT.y = i;
								return MOUSE_LIVINGS_PANEL_BUTTONS_ROWS_SGROUP_REMOVE_POINT;
							}
						}
					}
				}
			}
		}

		return null;
	}

	public static ArrayList<Integer> getLivings () {
		if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
			if (livingsPanelCitizensGroupActive == -1) {
				// Citizens sin grupo
				return Game.getWorld ().getCitizenGroups ().getCitizensWithoutGroup ();
			} else {
				// Está mostrando un grupo, miramos los miembros que tiene
				if (livingsPanelCitizensGroupActive >= 0 && livingsPanelCitizensGroupActive < CitizenGroups.MAX_GROUPS) {
					return Game.getWorld ().getCitizenGroups ().getGroup (livingsPanelCitizensGroupActive).getLivingIDs ();
				}
			}

			return World.getCitizenIDs ();
		} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
			if (livingsPanelSoldiersGroupActive == -1) {
				// Soldiers sin grupo
				return Game.getWorld ().getSoldierGroups ().getSoldiersWithoutGroup ();
			} else {
				// Está mostrando un grupo, miramos los miembros que tiene
				if (livingsPanelSoldiersGroupActive >= 0 && livingsPanelSoldiersGroupActive < SoldierGroups.MAX_GROUPS) {
					return Game.getWorld ().getSoldierGroups ().getGroup (livingsPanelSoldiersGroupActive).getLivingIDs ();
				}
			}
		} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_HEROES) {
			return World.getHeroIDs ();
		}

		return null;
	}

	public static int getLivingsIndex () {
		if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_CITIZENS) {
			if (livingsPanelCitizensGroupActive == -1) {
				// Todos los citizens
				return (livingsDataIndexPages[LIVINGS_PANEL_TYPE_CITIZENS] - 1) * LIVINGS_PANEL_MAX_ROWS;
			} else {
				// Está mostrando un grupo, miramos los miembros que tiene
				return (livingsDataIndexPagesCitizenGroups[livingsPanelCitizensGroupActive] - 1) * LIVINGS_PANEL_MAX_ROWS;
			}
		} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_SOLDIERS) {
			if (livingsPanelSoldiersGroupActive == -1) {
				// Todos los soldiers
				return (livingsDataIndexPages[LIVINGS_PANEL_TYPE_SOLDIERS] - 1) * LIVINGS_PANEL_MAX_ROWS;
			} else {
				// Está mostrando un grupo, miramos los miembros que tiene
				return (livingsDataIndexPagesSoldierGroups[livingsPanelSoldiersGroupActive] - 1) * LIVINGS_PANEL_MAX_ROWS;
			}
		} else if (getLivingsPanelActive () == LIVINGS_PANEL_TYPE_HEROES) {
			return (livingsDataIndexPages[LIVINGS_PANEL_TYPE_HEROES] - 1) * LIVINGS_PANEL_MAX_ROWS;
		}

		return -1;
	}

	public static void createMilitaryContextMenu (SmartMenu smToAdd, int iLocation, LivingEntity le, int mouseX, int mouseY) {
		// Equipar, miramos si hay objetos militares en el mundo, de paso ya hacemos una lista para poner en el menú
		Integer[] aItems = World.getItems ().keySet ().toArray (new Integer [0]);
		ArrayList<MilitaryItem> alMilitaryItems = new ArrayList<MilitaryItem> ();

		int iASZID = World.getCell (le.getCoordinates ()).getAstarZoneID ();

		ItemManagerItem imi;
		Item mi;
		for (int i = 0; i < aItems.length; i++) {
			mi = World.getItems ().get (aItems[i]);
			if (mi != null && mi instanceof MilitaryItem) {
				if (World.getCell (mi.getCoordinates ()).getAstarZoneID () == iASZID) {
					imi = ItemManager.getItem (mi.getIniHeader ());
					if (imi.getLocation () == iLocation) {
						// Lo metemos en la posición correcta, ordenado por item level
						int iItemLevel = imi.getLevel ();
						int iIndexLevel = -1;
						for (int iL = 0; iL < alMilitaryItems.size (); iL++) {
							imi = ItemManager.getItem (alMilitaryItems.get (iL).getIniHeader ());
							if (imi.getLevel () <= iItemLevel) {
								// Bingo
								iIndexLevel = iL;
								break;
							}
						}

						if (iIndexLevel == -1) {
							alMilitaryItems.add ((MilitaryItem) mi);
						} else {
							alMilitaryItems.add (iIndexLevel, (MilitaryItem) mi);
						}
					}
				}
			}
		}

		// Containers
		ArrayList<Container> alContainers = Game.getWorld ().getContainers ();
		ArrayList<Item> alContainerItems;
		nextContainer: for (int i = 0; i < alContainers.size (); i++) {
			alContainerItems = alContainers.get (i).getItemsInside ();
			for (int j = 0; j < alContainerItems.size (); j++) {
				mi = alContainerItems.get (j);
				if (World.getCell (mi.getCoordinates ()).getAstarZoneID () != iASZID) {
					continue nextContainer;
				}

				if (mi != null && mi instanceof MilitaryItem) {
					imi = ItemManager.getItem (mi.getIniHeader ());
					if (imi.getLocation () == iLocation) {
						// Lo metemos en la posición correcta, ordenado por item level
						int iItemLevel = imi.getLevel ();
						int iIndexLevel = -1;
						for (int iL = 0; iL < alMilitaryItems.size (); iL++) {
							imi = ItemManager.getItem (alMilitaryItems.get (iL).getIniHeader ());
							if (imi.getLevel () <= iItemLevel) {
								// Bingo
								iIndexLevel = iL;
								break;
							}
						}

						if (iIndexLevel == -1) {
							alMilitaryItems.add ((MilitaryItem) mi);
						} else {
							alMilitaryItems.add (iIndexLevel, (MilitaryItem) mi);
						}
					}
				}
			}
		}

		if (Game.getCurrentState () == Game.STATE_CREATING_TASK) {
			Game.deleteCurrentTask ();
		}
		ContextMenu menuMilitary = new ContextMenu ();
		SmartMenu smMilitary = new SmartMenu ();

		if (alMilitaryItems.size () > 0 || smToAdd != null) {
			// Tenemos la lista con items que el aldeano puede equipar, creamos el menú
			if (smToAdd != null) {
				smMilitary.addItem (smToAdd);
				if (alMilitaryItems.size () > 0) {
					smMilitary.addItem (new SmartMenu (SmartMenu.TYPE_TEXT, null, null, null, null));
				}
			}

			// Ordenamos el menú por item level
			MilitaryItem militaryItem;
			for (int i = 0; i < alMilitaryItems.size (); i++) {
				militaryItem = alMilitaryItems.get (i);
				if (militaryItem.getZ () > Game.getWorld ().getRestrictHaulEquippingLevel ()) {
					smMilitary.addItem (new SmartMenu (SmartMenu.TYPE_ITEM, Messages.getString ("UIPanel.79") + militaryItem.getExtendedTilename (), null, CommandPanel.COMMAND_WEAR, Integer.toString (le.getID ()), Integer.toString (militaryItem.getID ()), militaryItem.getCoordinates ().toPoint3D (), militaryItem.getItemTextColor ())); //$NON-NLS-1$
				} else {
					smMilitary.addItem (new SmartMenu (SmartMenu.TYPE_ITEM, militaryItem.getExtendedTilename (), null, CommandPanel.COMMAND_WEAR, Integer.toString (le.getID ()), Integer.toString (militaryItem.getID ()), militaryItem.getCoordinates ().toPoint3D (), militaryItem.getItemTextColor ()));
				}
			}
		} else {
			smMilitary.addItem (new SmartMenu (SmartMenu.TYPE_TEXT, Messages.getString ("UIPanel.56"), null, null, null)); //$NON-NLS-1$
		}
		menuMilitary.setSmartMenu (smMilitary);
		menuMilitary.setX (mouseX + 16 + -menuMilitary.getWidth () / 2);
		menuMilitary.setY (mouseY + 32);
		menuMilitary.resize ();
		Game.setContextMenu (menuMilitary);
	}

	public static void setLivingsPanelActive (int iType, int iSoldiersGroup, int iCitizenGroup) {
		if (iType != LIVINGS_PANEL_TYPE_NONE) {
			createLivingsPanel (iType, iSoldiersGroup, iCitizenGroup);
		}
		LivingsUIPanel.livingsPanelActive = iType;
		LivingsUIPanel.livingsPanelCitizensGroupActive = iCitizenGroup;
		LivingsUIPanel.livingsPanelSoldiersGroupActive = iSoldiersGroup;

		if (iType != -1) {
			UIPanel.closePanels (true, true, true, true, false, true, true);
		}
	}

	public static boolean isLivingsPanelActive () {
		return livingsPanelActive != -1;
	}

	public static int getLivingsPanelActive () {
		return livingsPanelActive;
	}

	public static void createLivingsPanel (int iPanelTypeActive, int iSoldiersGroupActive, int iCitizensGroupActive) {
		livingsPanelActive = iPanelTypeActive;
		livingsPanelCitizensGroupActive = iCitizensGroupActive;
		livingsPanelSoldiersGroupActive = iSoldiersGroupActive;

		LIVINGS_PANEL_GROUPS_WIDTH = 2 * tileLivingsGroupPanel[3].getTileWidth () + 32;
		LIVINGS_PANEL_WIDTH = 2 * tileLivingsPanel[3].getTileWidth () + 7 * UIPanel.tileBottomItem.getTileWidth () + UIPanel.tileScrollUp.getTileWidth ();
		if (iPanelTypeActive == LIVINGS_PANEL_TYPE_CITIZENS) {
			LIVINGS_PANEL_WIDTH += (2 * UIPanel.tileBottomItem.getTileWidth () + tileLivingsRowAutoequip.getTileWidth () + tileLivingsRowConvertSoldier.getTileWidth () + tileLivingsRowProfession.getTileWidth () + tileLivingsRowJobsGroups.getTileWidth () + LIVINGS_PANEL_GROUPS_WIDTH);
		} else if (iPanelTypeActive == LIVINGS_PANEL_TYPE_SOLDIERS) {
			LIVINGS_PANEL_WIDTH += (2 * UIPanel.tileBottomItem.getTileWidth () + tileLivingsRowAutoequip.getTileWidth () + tileLivingsRowConvertCivilian.getTileWidth () + tileLivingsRowConvertSoldierGuard.getTileWidth () + tileLivingsRowGroupAdd.getTileWidth ()) + LIVINGS_PANEL_GROUPS_WIDTH;
		}

		LIVINGS_PANEL_HEIGHT = UIPanel.renderHeight - (UIPanel.iconNumCitizensBackgroundPoint.y + UIPanel.tileBottomItem.getTileHeight ()) - UIPanel.tileBottomItem.getTileHeight () / 2;
		LIVINGS_PANEL_GROUPS_HEIGHT = LIVINGS_PANEL_HEIGHT - 2 * tileLivingsPanel[1].getTileHeight ();

		livingsPanelPoint.setLocation (UIPanel.renderWidth / 2 - LIVINGS_PANEL_WIDTH / 2, UIPanel.iconNumCitizensBackgroundPoint.y + UIPanel.tileBottomItem.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 4);
		livingsPanelClosePoint.setLocation (livingsPanelPoint.x + LIVINGS_PANEL_WIDTH - UIPanel.tileButtonClose.getTileWidth (), livingsPanelPoint.y);

		if (iPanelTypeActive == LIVINGS_PANEL_TYPE_SOLDIERS || iPanelTypeActive == LIVINGS_PANEL_TYPE_CITIZENS) {
			// Sub-groups panel
			livingsGroupPanelPoint.setLocation (livingsPanelPoint.x + LIVINGS_PANEL_WIDTH - tileLivingsPanel[3].getTileWidth () - LIVINGS_PANEL_GROUPS_WIDTH, livingsPanelPoint.y + (((livingsPanelPoint.y + LIVINGS_PANEL_HEIGHT) - livingsPanelPoint.y) / 2) - LIVINGS_PANEL_GROUPS_HEIGHT / 2);

			// Primer icono del subpanel y la separación
			int iSeparation = (LIVINGS_PANEL_GROUPS_HEIGHT - 2 * tileLivingsGroupPanel[3].getTileHeight () - tileLivingsNoGroup.getTileHeight () - (SoldierGroups.MAX_GROUPS * tileLivingsGroup.getTileHeight ())) / (SoldierGroups.MAX_GROUPS + 2);
			livingsGroupPanelFirstIconPoint.setLocation (livingsGroupPanelPoint.x + LIVINGS_PANEL_GROUPS_WIDTH / 2 - tileLivingsNoGroup.getTileWidth () / 2, livingsGroupPanelPoint.y + tileLivingsGroupPanel[3].getTileHeight () + iSeparation);
			livingsGroupPanelIconsSeparation = iSeparation + tileLivingsNoGroup.getTileHeight ();
		}

		// Miramos cuantas livings caben
		int iMaxHeight;
		if (checkGroupsPanelEnabled (iPanelTypeActive)) {
			iMaxHeight = LIVINGS_PANEL_HEIGHT - 2 * tileLivingsPanel[1].getTileHeight () - UIPanel.tileBottomItem.getTileHeight () - UIPanel.tileBottomItem.getTileHeight () / 2;
		} else {
			iMaxHeight = LIVINGS_PANEL_HEIGHT - 2 * tileLivingsPanel[1].getTileHeight ();
		}
		LIVINGS_PANEL_MAX_ROWS = iMaxHeight / UIPanel.tileBottomItem.getTileHeight ();
		if (LIVINGS_PANEL_MAX_ROWS < 1) {
			LIVINGS_PANEL_MAX_ROWS = 1;
		}
		// Rows
		int iSeparation;
		if (LIVINGS_PANEL_MAX_ROWS > 1) {
			iSeparation = (iMaxHeight - LIVINGS_PANEL_MAX_ROWS * UIPanel.tileBottomItem.getTileHeight ()) / (LIVINGS_PANEL_MAX_ROWS - 1);
		} else {
			iSeparation = 0;
		}

		int iIniY = livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ();

		if (livingsPanelRowPoints == null || livingsPanelRowPoints.length < LIVINGS_PANEL_MAX_ROWS) {
			livingsPanelRowPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowHeadPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowBodyPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowLegsPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowFeetPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowWeaponPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowAutoequipPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowProfessionPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowJobsGroupsPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowConvertCivilianSoldierPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowConvertSoldierGuardPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowConvertSoldierPatrolPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowConvertSoldierBossPoints = new Point [LIVINGS_PANEL_MAX_ROWS];
			livingsPanelRowGroupPoints = new Point [LIVINGS_PANEL_MAX_ROWS];

			for (int i = 0; i < LIVINGS_PANEL_MAX_ROWS; i++) {
				livingsPanelRowPoints[i] = new Point (0, 0);
				livingsPanelRowHeadPoints[i] = new Point (0, 0);
				livingsPanelRowBodyPoints[i] = new Point (0, 0);
				livingsPanelRowLegsPoints[i] = new Point (0, 0);
				livingsPanelRowFeetPoints[i] = new Point (0, 0);
				livingsPanelRowWeaponPoints[i] = new Point (0, 0);

				livingsPanelRowAutoequipPoints[i] = new Point (0, 0);
				livingsPanelRowProfessionPoints[i] = new Point (0, 0);
				livingsPanelRowJobsGroupsPoints[i] = new Point (0, 0);
				livingsPanelRowConvertCivilianSoldierPoints[i] = new Point (0, 0);
			}

			if (livingsPanelRowConvertSoldierGuardPoints == null || iPanelTypeActive == LIVINGS_PANEL_TYPE_SOLDIERS) {
				for (int i = 0; i < LIVINGS_PANEL_MAX_ROWS; i++) {
					livingsPanelRowConvertSoldierGuardPoints[i] = new Point (0, 0);
					livingsPanelRowConvertSoldierPatrolPoints[i] = new Point (0, 0);
					livingsPanelRowConvertSoldierBossPoints[i] = new Point (0, 0);
					livingsPanelRowGroupPoints[i] = new Point (0, 0);
				}
			}
		}

		int iRowsOffsetY = 0;
		if (checkGroupsPanelEnabled (iPanelTypeActive)) {
			iRowsOffsetY = UIPanel.tileBottomItem.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 2;
		}

		for (int i = 0; i < LIVINGS_PANEL_MAX_ROWS; i++) {
			// Living
			livingsPanelRowPoints[i] = new Point (livingsPanelPoint.x + tileLivingsPanel[3].getTileWidth (), iIniY + (i * (UIPanel.tileBottomItem.getTileHeight () + iSeparation)) + iRowsOffsetY);

			// Equipment
			livingsPanelRowHeadPoints[i] = new Point (livingsPanelRowPoints[i].x + UIPanel.tileBottomItem.getTileWidth () + UIPanel.tileBottomItem.getTileWidth () / 2, livingsPanelRowPoints[i].y);
			livingsPanelRowBodyPoints[i] = new Point (livingsPanelRowHeadPoints[i].x + UIPanel.tileBottomItem.getTileWidth (), livingsPanelRowPoints[i].y);
			livingsPanelRowLegsPoints[i] = new Point (livingsPanelRowBodyPoints[i].x + UIPanel.tileBottomItem.getTileWidth (), livingsPanelRowPoints[i].y);
			livingsPanelRowFeetPoints[i] = new Point (livingsPanelRowLegsPoints[i].x + UIPanel.tileBottomItem.getTileWidth (), livingsPanelRowPoints[i].y);
			livingsPanelRowWeaponPoints[i] = new Point (livingsPanelRowFeetPoints[i].x + UIPanel.tileBottomItem.getTileWidth (), livingsPanelRowPoints[i].y);

			// Autoequip
			livingsPanelRowAutoequipPoints[i] = new Point (livingsPanelRowWeaponPoints[i].x + UIPanel.tileBottomItem.getTileWidth () + UIPanel.tileBottomItem.getTileWidth () / 2, livingsPanelRowWeaponPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsRowAutoequip.getTileHeight () / 2);

			// Convert to soldier / civilian
			livingsPanelRowConvertCivilianSoldierPoints[i] = new Point (livingsPanelRowAutoequipPoints[i].x + tileLivingsRowAutoequip.getTileWidth () + UIPanel.tileBottomItem.getTileWidth () / 2, livingsPanelRowWeaponPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsRowConvertSoldier.getTileHeight () / 2);

			// Profession
			livingsPanelRowProfessionPoints[i] = new Point (livingsPanelRowConvertCivilianSoldierPoints[i].x + tileLivingsRowConvertSoldier.getTileWidth (), livingsPanelRowConvertCivilianSoldierPoints[i].y + tileLivingsRowConvertSoldier.getTileHeight () / 2 - tileLivingsRowProfession.getTileHeight () / 2);

			// Jobs groups
			livingsPanelRowJobsGroupsPoints[i] = new Point (livingsPanelRowProfessionPoints[i].x + tileLivingsRowProfession.getTileWidth (), livingsPanelRowProfessionPoints[i].y + tileLivingsRowProfession.getTileHeight () / 2 - tileLivingsRowJobsGroups.getTileHeight () / 2);
		}
		if (iPanelTypeActive == LIVINGS_PANEL_TYPE_SOLDIERS) {
			if (livingsPanelSoldiersGroupActive == -1) {
				for (int i = 0; i < LIVINGS_PANEL_MAX_ROWS; i++) {
					// Soldier states
					livingsPanelRowConvertSoldierGuardPoints[i] = new Point (livingsPanelRowConvertCivilianSoldierPoints[i].x + tileLivingsRowConvertCivilian.getTileWidth () + UIPanel.tileBottomItem.getTileWidth () / 2, livingsPanelRowWeaponPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsRowConvertSoldierGuard.getTileHeight ());
					livingsPanelRowConvertSoldierPatrolPoints[i] = new Point (livingsPanelRowConvertSoldierGuardPoints[i].x + tileLivingsRowConvertSoldierGuard.getTileWidth (), livingsPanelRowConvertSoldierGuardPoints[i].y);
					livingsPanelRowConvertSoldierBossPoints[i] = new Point (livingsPanelRowConvertSoldierGuardPoints[i].x, livingsPanelRowConvertSoldierGuardPoints[i].y + tileLivingsRowConvertSoldierGuard.getTileHeight ());

					// Soldier add group
					livingsPanelRowGroupPoints[i] = new Point (livingsPanelRowConvertSoldierPatrolPoints[i].x, livingsPanelRowConvertSoldierBossPoints[i].y);
				}
			} else {
				for (int i = 0; i < LIVINGS_PANEL_MAX_ROWS; i++) {
					// Soldier remove group
					livingsPanelRowGroupPoints[i] = new Point (livingsPanelRowConvertCivilianSoldierPoints[i].x + tileLivingsRowConvertCivilian.getTileWidth () + UIPanel.tileBottomItem.getTileWidth () / 2, livingsPanelRowConvertCivilianSoldierPoints[i].y);
				}

				// Single group panel
				if (iPanelTypeActive == LIVINGS_PANEL_TYPE_SOLDIERS && livingsPanelSoldiersGroupActive != -1) {
					LIVINGS_PANEL_SINGLE_GROUP_WIDTH = livingsPanelRowGroupPoints[0].x + tileLivingsRowGroupRemove.getTileWidth () - livingsPanelRowPoints[0].x;
					LIVINGS_PANEL_SINGLE_GROUP_HEIGHT = UIPanel.tileBottomItem.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 2;
					livingsSingleGroupPanelPoint.setLocation (livingsPanelRowPoints[0].x, iIniY - tileLivingsGroupPanel[1].getTileHeight () / 2);

					int iSeparationSingleGroup = (LIVINGS_PANEL_SINGLE_GROUP_WIDTH - 2 * tileLivingsGroupPanel[3].getTileWidth () - tileLivingsSingleGroupRename.getTileWidth () - tileLivingsSingleGroupGuard.getTileWidth () - tileLivingsSingleGroupPatrol.getTileWidth () - tileLivingsSingleGroupBoss.getTileWidth () - tileLivingsRowAutoequip.getTileWidth () - tileLivingsSingleGroupDisband.getTileWidth ()) / 5;
					// Botones del single group panel
					int iFirstButton = livingsSingleGroupPanelPoint.x + tileLivingsGroupPanel[3].getTileWidth ();
					// Rename
					livingsSingleGroupRenamePoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupRename.getTileHeight () / 2);
					iFirstButton += tileLivingsSingleGroupRename.getTileWidth () + iSeparationSingleGroup;
					// Guard
					livingsSingleGroupGuardPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupGuard.getTileHeight () / 2);
					iFirstButton += tileLivingsSingleGroupGuard.getTileWidth () + iSeparationSingleGroup;
					// Patrol
					livingsSingleGroupPatrolPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupPatrol.getTileHeight () / 2);
					iFirstButton += tileLivingsSingleGroupPatrol.getTileWidth () + iSeparationSingleGroup;
					// Boss
					livingsSingleGroupBossPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupBoss.getTileHeight () / 2);
					iFirstButton += tileLivingsSingleGroupBoss.getTileWidth () + iSeparationSingleGroup;
					// Autoequip
					livingsSingleGroupAutoequipPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsRowAutoequip.getTileHeight () / 2);
					iFirstButton += tileLivingsRowAutoequip.getTileWidth () + iSeparationSingleGroup;
					// Disband
					livingsSingleGroupDisbandPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupDisband.getTileHeight () / 2);
				}
			}
		} else if (iPanelTypeActive == LIVINGS_PANEL_TYPE_CITIZENS) {
			if (livingsPanelCitizensGroupActive == -1) {
				for (int i = 0; i < LIVINGS_PANEL_MAX_ROWS; i++) {
					// Soldier states
					livingsPanelRowConvertSoldierGuardPoints[i] = new Point (livingsPanelRowConvertCivilianSoldierPoints[i].x + tileLivingsRowConvertCivilian.getTileWidth () + UIPanel.tileBottomItem.getTileWidth () / 2, livingsPanelRowWeaponPoints[i].y + UIPanel.tileBottomItem.getTileHeight () / 2 - tileLivingsRowConvertSoldierGuard.getTileHeight ());
					livingsPanelRowConvertSoldierPatrolPoints[i] = new Point (livingsPanelRowConvertSoldierGuardPoints[i].x + tileLivingsRowConvertSoldierGuard.getTileWidth (), livingsPanelRowConvertSoldierGuardPoints[i].y);
					livingsPanelRowConvertSoldierBossPoints[i] = new Point (livingsPanelRowConvertSoldierGuardPoints[i].x, livingsPanelRowConvertSoldierGuardPoints[i].y + tileLivingsRowConvertSoldierGuard.getTileHeight ());

					// Soldier add group
					livingsPanelRowGroupPoints[i] = new Point (livingsPanelRowConvertSoldierPatrolPoints[i].x, livingsPanelRowConvertSoldierBossPoints[i].y);
				}
			} else {
				for (int i = 0; i < LIVINGS_PANEL_MAX_ROWS; i++) {
					// Civilian??? remove group
					livingsPanelRowGroupPoints[i] = new Point (livingsPanelRowConvertCivilianSoldierPoints[i].x + tileLivingsRowConvertCivilian.getTileWidth () + UIPanel.tileBottomItem.getTileWidth () / 2, livingsPanelRowConvertCivilianSoldierPoints[i].y);
				}

				// Single group panel
				if (iPanelTypeActive == LIVINGS_PANEL_TYPE_CITIZENS && livingsPanelCitizensGroupActive != -1) {
					LIVINGS_PANEL_SINGLE_GROUP_WIDTH = livingsPanelRowGroupPoints[0].x + tileLivingsRowGroupRemove.getTileWidth () - livingsPanelRowPoints[0].x;
					LIVINGS_PANEL_SINGLE_GROUP_HEIGHT = UIPanel.tileBottomItem.getTileHeight () + UIPanel.tileBottomItem.getTileHeight () / 2;
					livingsSingleGroupPanelPoint.setLocation (livingsPanelRowPoints[0].x, iIniY - tileLivingsGroupPanel[1].getTileHeight () / 2);

					int iSeparationSingleGroup = (LIVINGS_PANEL_SINGLE_GROUP_WIDTH - 2 * tileLivingsGroupPanel[3].getTileWidth () - tileLivingsSingleGroupRename.getTileWidth () - tileLivingsRowAutoequip.getTileWidth () - tileLivingsSingleGroupDisband.getTileWidth () - tileLivingsSingleGroupChangeJobs.getTileWidth ()) / 3;
					// Botones del single group panel
					int iFirstButton = livingsSingleGroupPanelPoint.x + tileLivingsGroupPanel[3].getTileWidth ();
					// Rename
					livingsSingleGroupRenamePoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupRename.getTileHeight () / 2);
					iFirstButton += tileLivingsSingleGroupRename.getTileWidth () + iSeparationSingleGroup;
					// Autoequip
					livingsSingleGroupAutoequipPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsRowAutoequip.getTileHeight () / 2);
					iFirstButton += tileLivingsRowAutoequip.getTileWidth () + iSeparationSingleGroup;
					// Change jobs
					livingsSingleGroupChangeJobsPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupChangeJobs.getTileHeight () / 2);
					iFirstButton += tileLivingsSingleGroupChangeJobs.getTileWidth () + iSeparationSingleGroup;
					// Disband
					livingsSingleGroupDisbandPoint.setLocation (iFirstButton, livingsSingleGroupPanelPoint.y + LIVINGS_PANEL_SINGLE_GROUP_HEIGHT / 2 - tileLivingsSingleGroupDisband.getTileHeight () / 2);
				}
			}
		}

		// Scrolls
		if (iPanelTypeActive == LIVINGS_PANEL_TYPE_SOLDIERS) {
			if (livingsPanelSoldiersGroupActive != -1) {
				// Scroll up/down
				livingsPanelIconScrollUpPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollUp.getTileWidth (), livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ());
				livingsPanelIconScrollDownPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollDown.getTileWidth (), livingsPanelRowPoints[LIVINGS_PANEL_MAX_ROWS - 1].y + UIPanel.tileBottomItem.getTileWidth () - UIPanel.tileScrollDown.getTileHeight ());
			} else {
				// Scroll up/down
				livingsPanelIconScrollUpPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollUp.getTileWidth (), livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ());
				livingsPanelIconScrollDownPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollDown.getTileWidth (), livingsPanelPoint.y + LIVINGS_PANEL_HEIGHT - tileLivingsPanel[1].getTileHeight () - UIPanel.tileScrollDown.getTileHeight ());
			}
		} else if (iPanelTypeActive == LIVINGS_PANEL_TYPE_CITIZENS) {
			if (livingsPanelCitizensGroupActive != -1) {
				// Scroll up/down
				livingsPanelIconScrollUpPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollUp.getTileWidth (), livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ());
				livingsPanelIconScrollDownPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollDown.getTileWidth (), livingsPanelRowPoints[LIVINGS_PANEL_MAX_ROWS - 1].y + UIPanel.tileBottomItem.getTileWidth () - UIPanel.tileScrollDown.getTileHeight ());
			} else {
				// Scroll up/down
				livingsPanelIconScrollUpPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollUp.getTileWidth (), livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight () + 2 * UIPanel.tileIconLevelDown.getTileHeight ());
				livingsPanelIconScrollDownPoint.setLocation (livingsGroupPanelPoint.x - UIPanel.tileBottomItem.getTileWidth () / 2 - UIPanel.tileScrollDown.getTileWidth (), livingsPanelPoint.y + LIVINGS_PANEL_HEIGHT - tileLivingsPanel[1].getTileHeight () - UIPanel.tileScrollDown.getTileHeight ());

				// Restrict points
				livingsPanelIconRestrictUpPoint.setLocation (livingsPanelIconScrollUpPoint.x - (UIPanel.tileIconLevelUp.getTileWidth () / 4) * 3, livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ());
				livingsPanelIconRestrictDownPoint.setLocation (livingsPanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () - (UIPanel.tileIconLevelDown.getTileWidth () / 4), livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ());
			}
		} else { // Heroes
			// Scroll up/down
			livingsPanelIconScrollUpPoint.setLocation (livingsPanelPoint.x + LIVINGS_PANEL_WIDTH - tileLivingsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight () + 2 * UIPanel.tileIconLevelDown.getTileHeight ());
			livingsPanelIconScrollDownPoint.setLocation (livingsPanelPoint.x + LIVINGS_PANEL_WIDTH - tileLivingsPanel[3].getTileWidth () - UIPanel.tileScrollUp.getTileWidth (), livingsPanelPoint.y + LIVINGS_PANEL_HEIGHT - tileLivingsPanel[1].getTileHeight () - UIPanel.tileScrollDown.getTileHeight ());

			// Restrict points
			livingsPanelIconRestrictUpPoint.setLocation (livingsPanelIconScrollUpPoint.x - (UIPanel.tileIconLevelUp.getTileWidth () / 4) * 3, livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ());
			livingsPanelIconRestrictDownPoint.setLocation (livingsPanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () - (UIPanel.tileIconLevelDown.getTileWidth () / 4), livingsPanelPoint.y + tileLivingsPanel[1].getTileHeight ());
		}

		// Pages
		int iSeparationScroll = livingsPanelIconScrollDownPoint.y - (livingsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight ());
		livingsPanelPagesPoint.setLocation (livingsPanelIconScrollUpPoint.x + UIPanel.tileScrollUp.getTileWidth () / 2, livingsPanelIconScrollUpPoint.y + UIPanel.tileScrollUp.getTileHeight () + iSeparationScroll / 2 - UtilFont.MAX_HEIGHT / 2);
		// Pages data
		if (livingsDataIndexPages == null) {
			livingsDataIndexPages = new int [3];
			livingsDataIndexPages[LIVINGS_PANEL_TYPE_CITIZENS] = 1;
			livingsDataIndexPages[LIVINGS_PANEL_TYPE_SOLDIERS] = 1;
			livingsDataIndexPages[LIVINGS_PANEL_TYPE_HEROES] = 1;

			livingsDataIndexPagesCitizenGroups = new int [CitizenGroups.MAX_GROUPS];
			for (int i = 0; i < CitizenGroups.MAX_GROUPS; i++) {
				livingsDataIndexPagesCitizenGroups[i] = 1;
			}

			livingsDataIndexPagesSoldierGroups = new int [SoldierGroups.MAX_GROUPS];
			for (int i = 0; i < SoldierGroups.MAX_GROUPS; i++) {
				livingsDataIndexPagesSoldierGroups[i] = 1;
			}
		}
	}
}
