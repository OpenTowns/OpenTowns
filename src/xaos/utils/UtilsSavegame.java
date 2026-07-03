package xaos.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import xaos.TownsProperties;
import xaos.actions.ActionPriorityManager;
import xaos.campaign.MissionData;
import xaos.data.BuryData;
import xaos.main.Game;
import xaos.main.World;
import xaos.panels.MessagesPanel;
import xaos.stockpiles.Stockpile;
import xaos.tiles.Cell;
import xaos.tiles.entities.items.Container;
import xaos.tiles.entities.items.Item;
import xaos.tiles.entities.items.ItemManager;
import xaos.tiles.entities.items.ItemManagerItem;
import xaos.tiles.entities.living.LivingEntity;
import xaos.tiles.entities.living.LivingEntityManager;

public final class UtilsSavegame {

    private UtilsSavegame() { /*static utility class*/ }

	// **************
    // * DISK UTILS *
    // **************
    /**
     * Save the game
     */
    public static void save (boolean bSaveMissionData) throws Exception {
        // Pausamos el thread de pathfinding
        AStarQueue.pause();
        while (!AStarQueue.isPauseOK()) {
            try {
                // Espera X milisegundos
                Thread.sleep(256);
            } catch (InterruptedException e) {
            }
        }

        Game.SAVE_MISSION = bSaveMissionData;

        String sSaveFolder = Game.getUserFolder() + Game.getFileSeparator() + Game.SAVE_FOLDER1 + Game.getFileSeparator();
        String sDestFileName = Game.getSavegameName() + ".zip"; //$NON-NLS-1$

        String sTemporaryFileName = Long.toString(System.currentTimeMillis()) + ".zip"; //$NON-NLS-1$
        File fTmp = new File(sSaveFolder + sTemporaryFileName);
        while (fTmp.exists()) {
            sTemporaryFileName = Long.toString(System.currentTimeMillis()) + ".zip"; //$NON-NLS-1$
            fTmp = new File(sSaveFolder + sTemporaryFileName);
        }
        try {
            Game.iError = 12000;
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(sSaveFolder + sTemporaryFileName));
            Game.iError = 12001;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            Game.iError = 12002;

            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry("world1.twn")); //$NON-NLS-1$
            Game.iError = 12003;

            // Version
            oos.writeInt(Game.SAVEGAME_VERSION);

            //POPOoos.writeObject (Game.getWorld ());
            Game.getWorld().writeExternal(oos);
            Game.iError = 12004;

            // Depth
            int iDepth = World.cells[0][0].length;
            Game.iError = 12005;
            oos.writeInt(iDepth);

            // Guardamos en el .zip
            oos.flush();
            baos.flush();
            out.write(baos.toByteArray());
            out.closeEntry();

            // Celdas
            Game.iError = 12006;
            for (int x = 0; x < World.MAP_WIDTH; x++) {
                oos.close();
                baos.close();
                Game.iError = 13000 + (x * 100);
                baos = new ByteArrayOutputStream();
                Game.iError = Game.iError + 1;
                oos = new ObjectOutputStream(baos);
                Game.iError = Game.iError + 1;
                out.putNextEntry(new ZipEntry("row" + x)); //$NON-NLS-1$
                Game.iError = Game.iError + 1;
                //POPOoos.writeObject (World.cells [x]);
                for (int y = 0; y < World.MAP_HEIGHT; y++) {
                    for (int z = 0; z < iDepth; z++) {
                        World.cells[x][y][z].writeExternal(oos);
                    }
                }
                oos.flush();
                Game.iError = Game.iError + 1;
                out.flush();
                Game.iError = Game.iError + 1;
                baos.flush();
                Game.iError = Game.iError + 1;
                out.write(baos.toByteArray());
                Game.iError = Game.iError + 1;
                out.closeEntry();
                Game.iError = Game.iError + 1;
            }

            // El resto
            oos.close();
            baos.close();
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            out.putNextEntry(new ZipEntry("world2.twn")); //$NON-NLS-1$
            oos.writeInt(World.getCurrentEntityID());
            Game.iError = 12007;

            // Town value
            oos.writeInt(World.getTownValue());
            Game.iError = 12008;

            // DEPTHS
            oos.writeShort(World.MAP_DEPTH);
            Game.iError = 12009;
            oos.writeShort(World.MAP_NUM_LEVELS_OUTSIDE);
            Game.iError = 12010;
            oos.writeShort(World.MAP_NUM_LEVELS_UNDERGROUND);
            Game.iError = 12011;

            // Mensajes
            MessagesPanel.writeExternal(oos);

            // Priorities panel
            ActionPriorityManager.save(oos);
            Game.iError = 12017;

            // Pausa
            oos.writeBoolean(Game.isPaused());

            // Mission data
           	oos.writeObject (Game.getCurrentMissionData ());

            Game.iError = 12018;
            oos.flush();
            Game.iError = 12019;
            baos.flush();
            Game.iError = 12020;

            // Guardamos en el .zip
            out.write(baos.toByteArray());
            Game.iError = 12021;

            // Complete the entry
            out.closeEntry();
            Game.iError = 12022;

            // Complete the ZIP file
            out.flush();
            out.close();
            Game.iError = 12032;
            oos.close();
            Game.iError = 12033;

            // All ok, copy to the real file
            File fFinal = new File(sSaveFolder + sDestFileName);
            if (fFinal.exists()) {
                fFinal.delete();
            }
            File fTemporary = new File(sSaveFolder + sTemporaryFileName);
            if (fTemporary.exists()) { // Debería, siempre
                fTemporary.renameTo(fFinal);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            Game.SAVE_MISSION = true;
            AStarQueue.resume();
        }
    }

    /**
     * Load a game
     *
     * @param sFolder
     * @param sZipFilename
     */
    public static void load(String sFolder, String sZipFilename) throws Exception {
        try {
            // Lo descomprimimos
            ZipFile zipFile = new ZipFile(new File(sFolder + sZipFilename));
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(new File(sFolder + sZipFilename))));
            ZipEntry zipEntry = zis.getNextEntry();
            InputStream is = zipFile.getInputStream(zipEntry);

            // Cargamos
            ObjectInputStream ois = new ObjectInputStream(is);
            Game.SAVEGAME_LOADING_VERSION = ois.readInt();
            if (TownsProperties.DEBUG_MODE) {
                System.out.println ("Loading version: " + Game.SAVEGAME_LOADING_VERSION); //$NON-NLS-1$
            }

            World world = new World();
            world.readExternal(ois);
            Game.setWorld(world);
			//POPOGame.setWorld ((World) ois.readObject ());

            // Celdas
            int iDepth = ois.readInt();
            World.cells = new Cell[World.MAP_WIDTH][World.MAP_HEIGHT][iDepth];

            ois.close();
            is.close();

            // Celdas
            for (int x = 0; x < World.MAP_WIDTH; x++) {
                zipEntry = zis.getNextEntry();
                is = zipFile.getInputStream(zipEntry);
                ois = new ObjectInputStream(is);
                //POPOWorld.cells [x] = (Cell [][]) ois.readObject ();
                for (int y = 0; y < World.MAP_HEIGHT; y++) {
                    for (int z = 0; z < iDepth; z++) {
                        World.cells[x][y][z] = new Cell();
                        World.cells[x][y][z].readExternal(ois);
                    }
                }
                ois.close();
                is.close();
            }

            zipEntry = zis.getNextEntry();
            is = zipFile.getInputStream(zipEntry);
            ois = new ObjectInputStream(is);

            // El resto
            World.setCurrentEntityID(ois.readInt());

            // Town value
            World.setTownValue(ois.readInt());

            // DEPTHS
            World.MAP_DEPTH = ois.readShort();
            World.MAP_NUM_LEVELS_OUTSIDE = ois.readShort();
            World.MAP_NUM_LEVELS_UNDERGROUND = ois.readShort();

            if (Game.SAVEGAME_LOADING_VERSION < Game.SAVEGAME_V12) {
                world.setRestrictHaulEquippingLevel(World.MAP_DEPTH - 1);
                world.setRestrictExploringLevel(World.MAP_DEPTH - 1);
            }

            // Mensajes
            MessagesPanel.clear();
            MessagesPanel.readExternal(ois);

            // Priorities panel
            ActionPriorityManager.load(ois);

            // Pause
            Game.setPaused(ois.readBoolean());

            // SavegameName
            Game.setSavegameName(removeExtension(sZipFilename));

            // Mission data
            if (Game.SAVEGAME_LOADING_VERSION >= Game.SAVEGAME_V14d) {
            	Game.setCurrentMissionData ((MissionData) ois.readObject ());
            }

            ois.close();
            is.close();
            zis.close();
            zipFile.close();

            // Comprobamos que items y livings y types de containers y pilas
            Item item;
            ArrayList<LivingEntity> alLivings;
            for (short x = 0; x < World.MAP_WIDTH; x++) {
                for (short y = 0; y < World.MAP_HEIGHT; y++) {
                    for (short z = 0; z < World.MAP_DEPTH; z++) {
                        // Items
                        item = World.getCell(x, y, z).getItem();
                        if (item != null) {
                            // Miramos que exista
                            if (ItemManager.getItem(item.getIniHeader()) == null) {
                                throw new Exception(Messages.getString("Item.10") + " [" + item.getIniHeader() + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                            }
                        }

                        // Livings
                        alLivings = World.getCell(x, y, z).getLivings();
                        if (alLivings != null) {
                            for (int i = 0; i < alLivings.size(); i++) {
                                if (LivingEntityManager.getItem(alLivings.get(i).getIniHeader()) == null) {
                                    throw new Exception(Messages.getString("LivingEntity.10") + " [" + alLivings.get(i).getIniHeader() + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                                }
                            }
                        }
                    }
                }
            }

            // Containers y pilas
            ArrayList<Container> alContainers = Game.getWorld().getContainers();
            ArrayList<Item> alContainerItems;
            Container container;
            for (int i = 0; i < alContainers.size(); i++) {
                container = alContainers.get(i);

                int t = container.getType().getElements().size() - 1;
                while (t >= 0) {
                    if (ItemManager.getItem(container.getType().getElements().get(t)) == null) {
                        container.getType().removeElement(container.getType().getElements().get(t));
                    }

                    t--;
                }

                alContainerItems = container.getItemsInside();
                for (int j = 0; j < alContainerItems.size(); j++) {
                    item = alContainerItems.get(j);
                    if (item != null) {
                        if (ItemManager.getItem(item.getIniHeader()) == null) {
                            throw new Exception(Messages.getString("Utils.21") + " [" + item.getIniHeader() + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                        }
                    }
                }
            }

            ArrayList<Stockpile> alPiles = Game.getWorld().getStockpiles();
            Stockpile pile;
            for (int i = 0; i < alPiles.size(); i++) {
                pile = alPiles.get(i);

                int t = pile.getType().getElements().size() - 1;
                while (t >= 0) {
                    if (ItemManager.getItem(pile.getType().getElements().get(t)) == null) {
                        pile.getType().removeElement(pile.getType().getElements().get(t));
                    }

                    t--;
                }
            }

            Game.getWorld().refreshTransients();
        } catch (Exception e) {
            e.printStackTrace();
            Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.23") + e.toString() + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            if (e.getMessage() != null) {
                throw new Exception(Messages.getString("Utils.23") + e.getMessage() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                throw new Exception(Messages.getString("Utils.23") + e.toString() + "]"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
    }

    /**
     * Bury and save a town
     */
    public static void saveBury() throws Exception {
        // Pausamos el thread de pathfinding
        AStarQueue.pause();
        while (!AStarQueue.isPauseOK()) {
            try {
                // Espera X milisegundos
                Thread.sleep(256);
            } catch (InterruptedException e) {
            }
        }

        String sBuryFolder = Game.getUserFolder() + Game.getFileSeparator() + Game.BURY_FOLDER1 + Game.getFileSeparator();
        String sDestFileName = "b_" + Game.getSavegameName() + ".zip"; //$NON-NLS-1$ //$NON-NLS-2$

        String sTemporaryFileName = Long.toString(System.currentTimeMillis()) + ".zip"; //$NON-NLS-1$
        File fTmp = new File(sBuryFolder + sTemporaryFileName);
        while (fTmp.exists()) {
            sTemporaryFileName = Long.toString(System.currentTimeMillis()) + ".zip"; //$NON-NLS-1$
            fTmp = new File(sBuryFolder + sTemporaryFileName);
        }

        try {
            Game.iError = 22000;
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(sBuryFolder + sTemporaryFileName));
            Game.iError = 22001;

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            Game.iError = 22002;

            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry("bury.dat")); //$NON-NLS-1$
            Game.iError = 22003;

            // Data
            BuryData buryData = BuryData.generate(sDestFileName);
            buryData.writeExternal(oos);

            // Guardamos en el .zip
            oos.flush();
            baos.flush();
            out.write(baos.toByteArray());
            out.closeEntry();

            // Complete the ZIP file
            out.close();
            Game.iError = 22032;
            oos.close();
            Game.iError = 22033;

            // All ok, copy to the real file
            File fFinal = new File(sBuryFolder + sDestFileName);
            if (fFinal.exists()) {
                fFinal.delete();
            }
            File fTemporary = new File(sBuryFolder + sTemporaryFileName);
            if (fTemporary.exists()) { // Debería, siempre
                fTemporary.renameTo(fFinal);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            AStarQueue.resume();
        }
    }

    /**
     * Devuelve un random bury, nunca devuelve null, en todo caso un bury vacío
     *
     * @return
     */
    public static BuryData getRandomBuryData(String sServerName) {
        BuryData bd = new BuryData();
        String sBuryFolder = Game.getUserFolder() + Game.getFileSeparator() + Game.BURY_FOLDER1 + Game.getFileSeparator();
        File fBuryFolder = new File(sBuryFolder);
        if (!fBuryFolder.exists()) {
            return bd;
        }

        // Si le pasamos un servidor nos bajaremos un bury y lo cargaremos
        String sBuryFile = null;
        if (sServerName != null) {
            sBuryFile = UtilsServer.getBuriedTown(sServerName, sBuryFolder);
        }

        // Buscamos un .zip a random
        try {
            File fBuryZip = null;
            if (sBuryFile == null) {
                String[] alFiles = fBuryFolder.list();
                if (alFiles != null && alFiles.length > 0) {
                    sBuryFile = alFiles[UtilsDice.getRandomBetween(0, (alFiles.length - 1))];
                    fBuryZip = new File(sBuryFolder + sBuryFile);
                }
            } else {
                // Fichero fijo, bajado del servidor
                fBuryZip = new File(sBuryFolder + sBuryFile);
            }

            if (fBuryZip != null && fBuryZip.exists()) {
                // Lo descomprimimos
                ZipFile zipFile = new ZipFile(fBuryZip);
                ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(fBuryZip)));
                ZipEntry zipEntry = zis.getNextEntry();
                InputStream is = zipFile.getInputStream(zipEntry);

                // Cargamos
                ObjectInputStream ois = new ObjectInputStream(is);
                bd.readExternal(ois);

                ois.close();
                is.close();
                zis.close();
                zipFile.close();
            } else {
                Log.log(Log.LEVEL_ERROR, Messages.getString("Utils.17"), "Utils"); //$NON-NLS-1$ //$NON-NLS-2$
            }
        } catch (Exception e) {
            Log.log(Log.LEVEL_ERROR, Messages.getString("BuryData.1") + " [" + e.toString() + "]", "Utils"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        }

        return bd;
    }

    public static String removeExtension(String sFilename) {
        if (sFilename == null || sFilename.length() == 0) {
            return sFilename;
        }

        int iIndex = sFilename.lastIndexOf("."); //$NON-NLS-1$
        if (iIndex > 0) {
            return sFilename.substring(0, iIndex);
        }
        return sFilename;
    }

    public static boolean existsSavegame(String sName) {
        try {
            String sSaveFolder = Game.getUserFolder() + Game.getFileSeparator() + Game.SAVE_FOLDER1 + Game.getFileSeparator();
            File fAux = new File(sSaveFolder + sName + ".zip"); //$NON-NLS-1$
            return fAux.exists();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retorna una lista con todos los savegame names o null si no hay
     *
     * @return una lista con todos los savegame names o null si no hay
     */
    public static ArrayList<File> getSaveFiles() {
        String sSaveFolder = Game.getUserFolder() + Game.getFileSeparator() + Game.SAVE_FOLDER1 + Game.getFileSeparator();
        File fFolder = new File(sSaveFolder);
        File[] aFiles = fFolder.listFiles();

        if (aFiles.length == 0) {
            return null;
        }

        ArrayList<File> alFiles = new ArrayList<File>(aFiles.length);
        for (int i = 0; i < aFiles.length; i++) {
            if (aFiles[i] != null && aFiles[i].getName() != null && aFiles[i].isFile() && aFiles[i].getName().endsWith(".zip")) { //$NON-NLS-1$
                // Bingo
                alFiles.add(aFiles[i]);
            }
        }

        if (alFiles.size() == 0) {
            return null;
        }

        // Ordenamos por fecha
        for (int i = 0; i < (alFiles.size() - 1); i++) {
            for (int j = i; j < alFiles.size(); j++) {
                if (alFiles.get(i).lastModified() < alFiles.get(j).lastModified()) {
                    // Intercambiamos
                    File fAux = alFiles.get(i);
                    alFiles.set(i, alFiles.get(j));
                    alFiles.set(j, fAux);
                }
            }
        }

        return alFiles;
    }

    public static void deleteSavegame(String sName) {
        String sSaveFolder = Game.getUserFolder() + Game.getFileSeparator() + Game.SAVE_FOLDER1 + Game.getFileSeparator();
        File f = new File(sSaveFolder + sName);
        if (f.exists()) {
            f.delete();
        }
    }

}
