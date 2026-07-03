package xaos.events;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xaos.Towns;
import xaos.effects.EffectManager;
import xaos.main.Game;
import xaos.tiles.entities.items.ItemManager;
import xaos.tiles.entities.living.LivingEntityManager;
import xaos.utils.Log;
import xaos.utils.Messages;
import xaos.utils.Utils;
import xaos.utils.UtilsString;
import xaos.utils.UtilsXML;

public class EventManager {

    private static HashMap<String, EventManagerItem> eventsList;

    public static void loadItems() {
        eventsList = new HashMap<String, EventManagerItem>();

        // Cargar de fichero
        loadXMLEvents(Towns.getPropertiesString("DATA_FOLDER") + "events.xml", true); //$NON-NLS-1$ //$NON-NLS-2$

        // Mods
        File fUserFolder = new File(Game.getUserFolder());
        if (!fUserFolder.exists() || !fUserFolder.isDirectory()) {
            return;
        }

        ArrayList<String> alMods = Game.getModsLoaded();
        if (alMods != null && alMods.size() > 0) {
            for (int i = 0; i < alMods.size(); i++) {
                String sModActionsPath = fUserFolder.getAbsolutePath() + System.getProperty("file.separator") + Game.MODS_FOLDER1 + System.getProperty("file.separator") + alMods.get(i) + System.getProperty("file.separator") + Towns.getPropertiesString("DATA_FOLDER") + "events.xml"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
                File fIni = new File(sModActionsPath);
                if (fIni.exists()) {
                    loadXMLEvents(sModActionsPath, false);
                }
            }
        }

        // Miramos que los items, itemSpawnLivings, siegeLivings, afterEvents, prerequisites, eventsInmune, effects, effectsAfterEat y effectsAfterSleep existan
        Iterator<EventManagerItem> itEffects = eventsList.values().iterator();
        EventManagerItem emi;
        while (itEffects.hasNext()) {
            emi = itEffects.next();

            // items
            if (emi.getItems() != null && emi.getItems().size() > 0) {
                for (int e = 0; e < emi.getItems().size(); e++) {
                    if (ItemManager.getItem(emi.getItems().get(e)) == null) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.0") + " [" + emi.getItems().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // itemsSpawnLiving
            if (emi.getItemsSpawnLiving() != null && emi.getItemsSpawnLiving().size() > 0) {
                for (int e = 0; e < emi.getItemsSpawnLiving().size(); e++) {
                    if (LivingEntityManager.getItem(emi.getItemsSpawnLiving().get(e)) == null) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.2") + " [" + emi.getItemsSpawnLiving().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // siegeLivings
            if (emi.getSiegeLivings() != null && emi.getSiegeLivings().size() > 0) {
                for (int e = 0; e < emi.getSiegeLivings().size(); e++) {
                    if (LivingEntityManager.getItem(emi.getSiegeLivings().get(e)) == null) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.3") + " [" + emi.getSiegeLivings().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // afterEvents
            if (emi.getAfterEvents() != null && emi.getAfterEvents().size() > 0) {
                for (int e = 0; e < emi.getAfterEvents().size(); e++) {
                    if (!eventsList.containsKey(emi.getAfterEvents().get(e))) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.4") + " [" + emi.getAfterEvents().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // prerequisites
            if (emi.getPrerequisites() != null && emi.getPrerequisites().size() > 0) {
                for (int e = 0; e < emi.getPrerequisites().size(); e++) {
                    if (!eventsList.containsKey(emi.getPrerequisites().get(e))) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.8") + " [" + emi.getPrerequisites().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // eventsImmune
            if (emi.getEventsImmune() != null && emi.getEventsImmune().size() > 0) {
                for (int e = 0; e < emi.getEventsImmune().size(); e++) {
                    if (!eventsList.containsKey(emi.getEventsImmune().get(e))) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.12") + " [" + emi.getEventsImmune().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // effects
            if (emi.getEffects() != null && emi.getEffects().size() > 0) {
                for (int e = 0; e < emi.getEffects().size(); e++) {
                    if (EffectManager.getItem(emi.getEffects().get(e)) == null) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.16") + " [" + emi.getEffects().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // effectsAfterEat
            if (emi.getEffectsAfterEat() != null && emi.getEffectsAfterEat().size() > 0) {
                for (int e = 0; e < emi.getEffectsAfterEat().size(); e++) {
                    if (EffectManager.getItem(emi.getEffectsAfterEat().get(e)) == null) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.1") + " [" + emi.getEffectsAfterEat().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }

            // effectsAfterSleep
            if (emi.getEffectsAfterSleep() != null && emi.getEffectsAfterSleep().size() > 0) {
                for (int e = 0; e < emi.getEffectsAfterSleep().size(); e++) {
                    if (EffectManager.getItem(emi.getEffectsAfterSleep().get(e)) == null) {
                        Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.24") + " [" + emi.getEffectsAfterSleep().get(e) + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
                        Game.exit();
                    }
                }
            }
        }
    }

    public static EventManagerItem getItem(String sIniHeader) {
        if (sIniHeader == null) {
            return null;
        }

        if (eventsList == null) {
            loadItems();
        }

        return eventsList.get(sIniHeader);
    }

    public static EventManagerItem getRandomItem() {
        if (eventsList == null) {
            loadItems();
        }

        ArrayList<String> alRandomEvents = new ArrayList<String>();

        Iterator<EventManagerItem> itEffects = eventsList.values().iterator();
        EventManagerItem emi;
        while (itEffects.hasNext()) {
            emi = itEffects.next();
            if (emi.isSpawnAtRandom()) {
                alRandomEvents.add(emi.getId());
            }
        }

        if (alRandomEvents.size() == 0) {
            return null;
        }

        return getItem(alRandomEvents.get(Utils.getRandomBetween(0, (alRandomEvents.size() - 1))));
    }

    private static void loadXMLEvents(String sXMLName, boolean bLoadingMain) {

        try {
            Document doc = UtilsXML.loadXMLFile(sXMLName); //$NON-NLS-1$

			// Tenemos el documento XML parseado
            // Lo recorremos entero y vamos añadiendo las skills (SkillManagerItem) a la hash
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            Node node;
            EventManagerItem emi;
            String sIniHeader;
            for (int i = 0; i < nodeList.getLength(); i++) {
                node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String sName = node.getNodeName();
                    if (sName != null && sName.equalsIgnoreCase("DELETE")) { //$NON-NLS-1$
                        // Miramos que ID quiere borrar
                        if (node.getAttributes() != null && node.getAttributes().getNamedItem("id") != null) { //$NON-NLS-1$
                            String sIDToDelete = node.getAttributes().getNamedItem("id").getNodeValue(); //$NON-NLS-1$
                            if (sIDToDelete != null) {
                                eventsList.remove(sIDToDelete);
                            }
                        }
                        continue;
                    }

                    sIniHeader = UtilsXML.getChildValue(node.getChildNodes(), "id"); //$NON-NLS-1$
                    if (sIniHeader == null || sIniHeader.length() == 0) {
                        throw new Exception(Messages.getString("EventManager.31")); //$NON-NLS-1$
                    }

                    boolean bExists = eventsList.containsKey(sIniHeader);

                    // Mod cambiando valores de un item que ya existe?
                    boolean bModChangingValues = (bExists && !bLoadingMain);

                    if (bModChangingValues) {
                        emi = eventsList.get(sIniHeader);
                    } else {
                        emi = new EventManagerItem();
                        emi.setId(sIniHeader);
                    }

                    if (bModChangingValues) {
                        String sAux = UtilsXML.getChildValue(node.getChildNodes(), "name"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setName(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "turns"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setTurns(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "icon"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setIcon(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "order"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setOrder(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "minPopulation"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setMinPopulation(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "spawnAtRandom"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setSpawnAtRandom(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "eventCooldown"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setEventCooldown(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "afterEvents"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setAfterEvents(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "shadows"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setShadows(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "halfShadows"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setHalfShadows(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "red"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setRed(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "green"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setGreen(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "blue"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setBlue(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "prerequisites"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setPrerequisites(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "eventsImmune"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setEventsImmune(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "waitPCT"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setWaitPCT(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "walkSpeedPCT"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setWalkSpeedPCT(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "happinessPCT"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setHappinessPCT(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "siege"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setSiege(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "siegeUnderground"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setSiegeUnderground(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "siegeLivings"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setSiegeLivings(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "siegeSize"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setSiegeSize(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "targets"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setTargets(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "targetsPCT"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setTargetsPCT(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "targetsRandomCell"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setTargetsRandomCell(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "targetsOpenCell"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setTargetsOpenCell(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "effects"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setEffects(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "effectsAfterEat"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setEffectsAfterEat(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "effectsAfterSleep"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setEffectsAfterSleep(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "items"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setItems(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "itemsMaxAgePCT"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setItemsMaxAgePCT(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "itemsDeletePCT"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setItemsDeletePCT(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "itemsSpawnLiving"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setItemsSpawnLiving(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "itemsSpawnLivingSize"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setItemsSpawnLivingSize(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "fxBeforeCooldown"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setFxBeforeCooldown(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "fxAfterCooldown"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setFxAfterCooldown(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "fxRunning"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setFxRunning(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "fxRunningTurns"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setFxRunningTurns(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "fxFinish"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setFxFinish(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "injectActions"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setInjectActions(UtilsString.getArray(sAux));
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "useFile"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setUseFile(sAux);
                        }
                        sAux = UtilsXML.getChildValue(node.getChildNodes(), "useFileIDs"); //$NON-NLS-1$
                        if (sAux != null) {
                            emi.setUseFileIDs(UtilsString.getArray(sAux));
                        }
                        emi.loadUseFile(sXMLName);
                    } else {
                        emi.setName(UtilsXML.getChildValue(node.getChildNodes(), "name")); //$NON-NLS-1$
                        emi.setTurns(UtilsXML.getChildValue(node.getChildNodes(), "turns")); //$NON-NLS-1$
                        emi.setIcon(UtilsXML.getChildValue(node.getChildNodes(), "icon")); //$NON-NLS-1$
                        emi.setOrder(UtilsXML.getChildValue(node.getChildNodes(), "order")); //$NON-NLS-1$
                        emi.setMinPopulation(UtilsXML.getChildValue(node.getChildNodes(), "minPopulation")); //$NON-NLS-1$
                        emi.setSpawnAtRandom(UtilsXML.getChildValue(node.getChildNodes(), "spawnAtRandom")); //$NON-NLS-1$
                        emi.setEventCooldown(UtilsXML.getChildValue(node.getChildNodes(), "eventCooldown")); //$NON-NLS-1$
                        emi.setAfterEvents(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "afterEvents"))); //$NON-NLS-1$
                        emi.setShadows(UtilsXML.getChildValue(node.getChildNodes(), "shadows")); //$NON-NLS-1$
                        emi.setHalfShadows(UtilsXML.getChildValue(node.getChildNodes(), "halfShadows")); //$NON-NLS-1$
                        emi.setRed(UtilsXML.getChildValue(node.getChildNodes(), "red")); //$NON-NLS-1$
                        emi.setGreen(UtilsXML.getChildValue(node.getChildNodes(), "green")); //$NON-NLS-1$
                        emi.setBlue(UtilsXML.getChildValue(node.getChildNodes(), "blue")); //$NON-NLS-1$
                        emi.setPrerequisites(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "prerequisites"))); //$NON-NLS-1$
                        emi.setEventsImmune(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "eventsImmune"))); //$NON-NLS-1$
                        emi.setWaitPCT(UtilsXML.getChildValue(node.getChildNodes(), "waitPCT")); //$NON-NLS-1$
                        emi.setWalkSpeedPCT(UtilsXML.getChildValue(node.getChildNodes(), "walkSpeedPCT")); //$NON-NLS-1$
                        emi.setHappinessPCT(UtilsXML.getChildValue(node.getChildNodes(), "happinessPCT")); //$NON-NLS-1$
                        emi.setSiege(UtilsXML.getChildValue(node.getChildNodes(), "siege")); //$NON-NLS-1$
                        emi.setSiegeUnderground(UtilsXML.getChildValue(node.getChildNodes(), "siegeUnderground")); //$NON-NLS-1$
                        emi.setSiegeLivings(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "siegeLivings"))); //$NON-NLS-1$
                        emi.setSiegeSize(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "siegeSize"))); //$NON-NLS-1$
                        emi.setTargets(UtilsXML.getChildValue(node.getChildNodes(), "targets")); //$NON-NLS-1$
                        emi.setTargetsPCT(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "targetsPCT"))); //$NON-NLS-1$
                        emi.setTargetsRandomCell(UtilsXML.getChildValue(node.getChildNodes(), "targetsRandomCell")); //$NON-NLS-1$
                        emi.setTargetsOpenCell(UtilsXML.getChildValue(node.getChildNodes(), "targetsOpenCell")); //$NON-NLS-1$
                        emi.setEffects(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "effects"))); //$NON-NLS-1$
                        emi.setEffectsAfterEat(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "effectsAfterEat"))); //$NON-NLS-1$
                        emi.setEffectsAfterSleep(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "effectsAfterSleep"))); //$NON-NLS-1$
                        emi.setItems(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "items"))); //$NON-NLS-1$
                        emi.setItemsMaxAgePCT(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "itemsMaxAgePCT"))); //$NON-NLS-1$
                        emi.setItemsDeletePCT(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "itemsDeletePCT"))); //$NON-NLS-1$
                        emi.setItemsSpawnLiving(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "itemsSpawnLiving"))); //$NON-NLS-1$
                        emi.setItemsSpawnLivingSize(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "itemsSpawnLivingSize"))); //$NON-NLS-1$
                        emi.setFxBeforeCooldown(UtilsXML.getChildValue(node.getChildNodes(), "fxBeforeCooldown")); //$NON-NLS-1$
                        emi.setFxAfterCooldown(UtilsXML.getChildValue(node.getChildNodes(), "fxAfterCooldown")); //$NON-NLS-1$
                        emi.setFxRunning(UtilsXML.getChildValue(node.getChildNodes(), "fxRunning")); //$NON-NLS-1$
                        emi.setFxRunningTurns(UtilsXML.getChildValue(node.getChildNodes(), "fxRunningTurns")); //$NON-NLS-1$
                        emi.setFxFinish(UtilsXML.getChildValue(node.getChildNodes(), "fxFinish")); //$NON-NLS-1$
                        emi.setInjectActions(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "injectActions"))); //$NON-NLS-1$
                        emi.setUseFile(UtilsXML.getChildValue(node.getChildNodes(), "useFile")); //$NON-NLS-1$
                        emi.setUseFileIDs(UtilsString.getArray(UtilsXML.getChildValue(node.getChildNodes(), "useFileIDs"))); //$NON-NLS-1$
                        emi.loadUseFile(sXMLName);
                    }

                    // Lo añadimos a la hash
                    eventsList.put(sIniHeader, emi);
                }
            }
        } catch (Exception e) {
            Log.log(Log.LEVEL_ERROR, Messages.getString("EventManager.32") + " [" + e.getMessage() + "]", "EventManager"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            Game.exit();
        }
    }

    public static HashMap<String, EventManagerItem> getAllItems() {
        if (eventsList == null) {
            loadItems();
        }

        return eventsList;
    }

    public static void clear() {
        eventsList = null;
    }
}
