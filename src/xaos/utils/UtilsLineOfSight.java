package xaos.utils;

import java.util.ArrayList;

import xaos.main.World;
import xaos.tiles.Cell;
import xaos.tiles.entities.items.Item;
import xaos.tiles.entities.items.ItemManager;
import xaos.tiles.entities.items.ItemManagerItem;
import xaos.tiles.entities.living.LivingEntity;

public final class UtilsLineOfSight {

    private UtilsLineOfSight() { /*static utility class*/ }

    public static ArrayList<Point3DShort> bresenhamLine(Point3DShort p0, Point3DShort p1, short zLevel) { //, int livingType) {
        return bresenhamLine(p0.x, p0.y, p1.x, p1.y, zLevel); //, livingType);
    }

    public static boolean bresenhamLineExists(int x0, int y0, int x1, int y1, int zLevel) { //, int livingType) {
        ArrayList<Point3DShort> alBresenham = bresenhamLine(x0, y0, x1, y1, zLevel); //, livingType);
        if (alBresenham == null) {
            return false;
        } else {
            // Limpiamos la lista
            Point3DShort.returnToPool(alBresenham);
            return true;
        }
    }

    /**
     * Returns an array of Point's representing a line between 2 points
     *
     * @param x0 Ini X
     * @param y0 Ini Y
     * @param x1 End X
     * @param y1 End Y
     * @param zLevel Z
     * @return an array of Point's representing a line between 2 points, null if
     * found a not allowed point
     */
    public static ArrayList<Point3DShort> bresenhamLine(int x0, int y0, int x1, int y1, int zLevel) { //, int livingType) {
        if (x0 == x1 && y0 == y1) {
            return null;
        }

        ArrayList<Point3DShort> alReturn = new ArrayList<Point3DShort>();
        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if (steep) {
            int iAux = x0;
            x0 = y0;
            y0 = iAux;
            iAux = x1;
            x1 = y1;
            y1 = iAux;
        }
        int deltax = Math.abs(x1 - x0);
        int deltay = Math.abs(y1 - y0);
        int error = deltax / 2;
        int y = y0;

        short inc = 1;
        if (x0 >= x1) {
            inc = -1;
        }

        short ystep = 1;
        if (y0 >= y1) {
            ystep = -1;
        }

        if (inc == 1) {
            for (int x = x0; x <= x1; x += inc) {
                if (steep) {
                    if (!LivingEntity.isCellAllowed(y, x, zLevel)) {
                        return null;
                    }
                    alReturn.add(Point3DShort.getPoolInstance(y, x, zLevel));
                } else {
                    if (!LivingEntity.isCellAllowed(x, y, zLevel)) {
                        return null;
                    }
                    alReturn.add(Point3DShort.getPoolInstance(x, y, zLevel));
                }
                error = error - deltay;
                if (error < 0) {
                    y += ystep;
                    error = error + deltax;
                }
            }
        } else {
            for (int x = x0; x >= x1; x += inc) {
                if (steep) {
                    if (!LivingEntity.isCellAllowed(y, x, zLevel)) {
                        return null;
                    }
                    alReturn.add(Point3DShort.getPoolInstance(y, x, zLevel));
                } else {
                    if (!LivingEntity.isCellAllowed(x, y, zLevel)) {
                        return null;
                    }
                    alReturn.add(Point3DShort.getPoolInstance(x, y, zLevel));
                }
                error = error - deltay;
                if (error < 0) {
                    y += ystep;
                    error = error + deltax;
                }
            }
        }

        return alReturn;
    }

    /**
     * Descubre las celdas de la linea hasta que encuentra un no-mined
     *
     * @param x0 Ini X
     * @param y0 Ini Y
     * @param x1 End X
     * @param y1 End Y
     * @param zLevel Z
     */
    public static void bresenhamLineDiscover(int x0, int y0, int x1, int y1, int zLevel) {
        if (x0 == x1 && y0 == y1) {
            return;
        }

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if (steep) {
            int iAux = x0;
            x0 = y0;
            y0 = iAux;
            iAux = x1;
            x1 = y1;
            y1 = iAux;
        }
        int deltax = Math.abs(x1 - x0);
        int deltay = Math.abs(y1 - y0);
        int error = deltax / 2;
        int y = y0;

        int inc = 1;
        if (x0 >= x1) {
            inc = -1;
        }

        int ystep = 1;
        if (y0 >= y1) {
            ystep = -1;
        }

        Cell cell;
        if (inc == 1) {
            for (int x = x0; x <= x1; x += inc) {
                if (steep) {
                    cell = World.getCell(y, x, zLevel);
                } else {
                    cell = World.getCell(x, y, zLevel);
                }
                cell.setDiscovered(true);
                if (!cell.isMined()) {
                    return;
                } else {
                    Item item = cell.getItem();
                    if (item != null) {
                        ItemManagerItem imi = ItemManager.getItem(item.getIniHeader());
                        if (imi.isWall() && imi.isBlocky()) {
                            return;
                        }
                    }
                }
                // Cell arriba
                if (zLevel > 0) {
                    if (steep) {
                        cell = World.getCell(y, x, zLevel - 1);
                    } else {
                        cell = World.getCell(x, y, zLevel - 1);
                    }
                    cell.setDiscovered(true);
                }
                error = error - deltay;
                if (error < 0) {
                    y = y + ystep;
                    error = error + deltax;
                }
            }
        } else {
            for (int x = x0; x >= x1; x += inc) {
                if (steep) {
                    cell = World.getCell(y, x, zLevel);
                } else {
                    cell = World.getCell(x, y, zLevel);
                }
                cell.setDiscovered(true);
                if (!cell.isMined()) {
                    return;
                } else {
                    Item item = cell.getItem();
                    if (item != null) {
                        ItemManagerItem imi = ItemManager.getItem(item.getIniHeader());
                        if (imi.isWall() && imi.isBlocky()) {
                            return;
                        }
                    }
                }
                // Cell arriba
                if (zLevel > 0) {
                    if (steep) {
                        cell = World.getCell(y, x, zLevel - 1);
                    } else {
                        cell = World.getCell(x, y, zLevel - 1);
                    }
                    cell.setDiscovered(true);
                }
                error = error - deltay;
                if (error < 0) {
                    y = y + ystep;
                    error = error + deltax;
                }
            }
        }
    }

    private static boolean isCellBlockingLight(Cell cell) {
        if (!cell.isMined()) {
            return true;
        } else {
            Item item = cell.getItem();
            if (item != null) {
                ItemManagerItem imi = ItemManager.getItem(item.getIniHeader());
                return isCellBlockingLightItem(imi, item);
            }
        }

        return false;
    }

    public static boolean isCellBlockingLightItem(ItemManagerItem imi, Item item) {
        if (imi == null || item == null) {
            return true;
        }

        if (imi.isTranslucent()) {
            return false;
        }

        if (imi.isWall()) {
            return true;
        }
        if (imi.isDoor() && !item.isDoorStatus(Item.FLAG_WALL_CONNECTOR_STATUS_UNLOCKED_AND_OPENED)) {
            return true;
        }

        return false;
    }

    /**
     * Mira y pone las luzes si hace falta
     *
     * @param bNonMinedOrWall
     * @param cell
     * @param imiSource
     * @param iDirection 0 = misma casilla, 1-NW 2-N 3-NE 4-W 5-E 6-SW 7-S 8-SE
     */
    private static void checkSetLights(boolean bNonMinedOrWall, Cell cell, ItemManagerItem imiSource, int iDirection) {
        if (!bNonMinedOrWall) {
            cell.setLights(imiSource);
        } else {
            // Wall o non-mined, miramos si hay que pintarlo o no
            if (iDirection != 7 && iDirection != 4 && iDirection != 6) {
                if (iDirection == 2 || iDirection == 5 || iDirection == 3 || iDirection == 0) {
                    cell.setLights(imiSource);
                } else if (iDirection == 8) {
                    // Hay que mirar la casilla oeste
                    if (cell.getCoordinates().x > 0) {
                        Cell cellTmp = World.getCell(cell.getCoordinates().x - 1, cell.getCoordinates().y, cell.getCoordinates().z);
                        if (!isCellBlockingLight(cellTmp)) {
                            cell.setLights(imiSource);
                        }
                    }
                } else if (iDirection == 1) {
                    // Hay que mirar la casilla sur
                    if (cell.getCoordinates().y < (World.MAP_HEIGHT - 1)) {
                        Cell cellTmp = World.getCell(cell.getCoordinates().x, cell.getCoordinates().y + 1, cell.getCoordinates().z);
                        if (!isCellBlockingLight(cellTmp)) {
                            cell.setLights(imiSource);
                        }
                    }
                }
            }
        }
    }

    /**
     * Mira y pone las luzes si hace falta
     *
     * @param bNonMinedOrWall
     * @param cell
     * @param imiSource
     * @param iDirection 0 = misma casilla, 1-NW 2-N 3-NE 4-W 5-E 6-SW 7-S 8-SE
     * @return true si la luz se ha puesto
     */
    private static boolean checkSetLightsUp(boolean bNonMinedOrWall, Cell cell, ItemManagerItem imiSource, int iDirection) {
        if (!bNonMinedOrWall) {
            cell.setLights(imiSource);
            return true;
        } else {
            // Wall o non-mined, miramos si hay que pintarlo o no
            if (iDirection != 7 && iDirection != 4 && iDirection != 6 && iDirection != 0) {
                if (iDirection == 2) {
                    // Hay que mirar la casilla sur
                    if (cell.getCoordinates().y < (World.MAP_HEIGHT - 1)) {
                        Cell cellTmp = World.getCell(cell.getCoordinates().x, cell.getCoordinates().y + 1, cell.getCoordinates().z);
                        if (!isCellBlockingLight(cellTmp)) {
                            cell.setLights(imiSource);
                            return true;
                        }
                    }
                } else if (iDirection == 5) {
                    // Hay que mirar la casilla oeste
                    if (cell.getCoordinates().x > 0) {
                        Cell cellTmp = World.getCell(cell.getCoordinates().x - 1, cell.getCoordinates().y, cell.getCoordinates().z);
                        if (!isCellBlockingLight(cellTmp)) {
                            cell.setLights(imiSource);
                            return true;
                        }
                    }
                } else if (iDirection == 3 || iDirection == 8) {
                    // Hay que mirar la casilla sur
                    if (cell.getCoordinates().y < (World.MAP_HEIGHT - 1)) {
                        Cell cellTmp = World.getCell(cell.getCoordinates().x, cell.getCoordinates().y + 1, cell.getCoordinates().z);
                        if (!isCellBlockingLight(cellTmp)) {
                            cell.setLights(imiSource);
                            return true;
                        } else {
                            // Hay que mirar la casilla oeste
                            if (cell.getCoordinates().x > 0) {
                                cellTmp = World.getCell(cell.getCoordinates().x - 1, cell.getCoordinates().y, cell.getCoordinates().z);
                                if (!isCellBlockingLight(cellTmp)) {
                                    cell.setLights(imiSource);
                                    return true;
                                }
                            }
                        }
                    }
                } else if (iDirection == 1) {
                    // Hay que mirar la casilla sur
                    if (cell.getCoordinates().y < (World.MAP_HEIGHT - 1)) {
                        Cell cellTmp = World.getCell(cell.getCoordinates().x, cell.getCoordinates().y + 1, cell.getCoordinates().z);
                        if (!isCellBlockingLight(cellTmp)) {
                            cell.setLights(imiSource);
                            return true;
                        } else {
                            // Hay que mirar la casilla este
                            if (cell.getCoordinates().x < (World.MAP_WIDTH - 1)) {
                                cellTmp = World.getCell(cell.getCoordinates().x + 1, cell.getCoordinates().y, cell.getCoordinates().z);
                                if (!isCellBlockingLight(cellTmp)) {
                                    cell.setLights(imiSource);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Pone luz a las celdas de la linea (y las de arriba y abajo teniendo en
     * cuenta el radio) hasta que encuentra un no-mined o un wall
     *
     * @param x0 Ini X
     * @param y0 Ini Y
     * @param x1 End X
     * @param y1 End Y
     * @param zLevel Z
     * @param iRadious Radio para poner luz a las casillas de arriba/abajo
     */
    public static void bresenhamLineLight(int x0, int y0, int x1, int y1, int zLevel, int iRadious, ItemManagerItem imiSource) {
        if (x0 == x1 && y0 == y1) {
            return;
        }

        // Miramos la dirección de la luz
        int iDirection; // 0 = misma casilla, 1-NW 2-N 3-NE 4-W 5-E 6-SW 7-S 8-SE
        if (x0 == x1) {
            if (y0 == y1) {
                iDirection = 0;
            } else if (y0 > y1) {
                // Norte
                iDirection = 2;
            } else {
                // Sur
                iDirection = 7;
            }
        } else {
            if (x0 < x1) {
                if (y0 == y1) {
                    // Este
                    iDirection = 5;
                } else if (y0 < y1) {
                    // Sureste
                    iDirection = 8;
                } else {
                    // Noreste
                    iDirection = 3;
                }
            } else {
                if (y0 == y1) {
                    // Oeste
                    iDirection = 4;
                } else if (y0 < y1) {
                    // Suroeste
                    iDirection = 6;
                } else {
                    // Noroeste
                    iDirection = 1;
                }
            }
        }

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);
        if (steep) {
            int iAux = x0;
            x0 = y0;
            y0 = iAux;
            iAux = x1;
            x1 = y1;
            y1 = iAux;
        }
        int deltax = Math.abs(x1 - x0);
        int deltay = Math.abs(y1 - y0);
        int error = deltax / 2;
        int y = y0;

        int inc = 1;
        if (x0 >= x1) {
            inc = -1;
        }

        int ystep = 1;
        if (y0 >= y1) {
            ystep = -1;
        }

        Cell cell, cellaux;
        int iZAux;
        boolean bNonMinedOrWallUpDown, bNonMinedOrWall;
        if (inc == 1) {
            bNonMinedOrWall = false;
            for (int x = x0; x <= x1; x += inc) {
                if (steep) {
                    cell = World.getCell(y, x, zLevel);
                } else {
                    cell = World.getCell(x, y, zLevel);
                }

                bNonMinedOrWall = isCellBlockingLight(cell);
                checkSetLights(bNonMinedOrWall, cell, imiSource, iDirection);

				// Arriba y abajo, teniendo en cuenta el radio
                // Arriba
                iZAux = zLevel - iRadious;
                if (iZAux < 0) {
                    iZAux = 0;
                }

                bNonMinedOrWallUpDown = false;
                for (int i = (zLevel - 1); i >= iZAux; i--) {
                    if (steep) {
                        cellaux = World.getCell(y, x, i);
                    } else {
                        cellaux = World.getCell(x, y, i);
                    }

                    bNonMinedOrWallUpDown = isCellBlockingLight(cellaux);
                    bNonMinedOrWallUpDown = checkSetLightsUp(bNonMinedOrWallUpDown, cellaux, imiSource, iDirection);

                    if (!bNonMinedOrWallUpDown) {
                        break;
                    }
                }

                // Abajo
                iZAux = zLevel + iRadious;
                if (iZAux >= World.MAP_DEPTH) {
                    iZAux = (World.MAP_DEPTH - 1);
                }

                bNonMinedOrWallUpDown = false;
                for (int i = (zLevel + 1); i <= iZAux; i++) {
                    if (steep) {
                        cellaux = World.getCell(y, x, i);
                    } else {
                        cellaux = World.getCell(x, y, i);
                    }

                    bNonMinedOrWallUpDown = isCellBlockingLight(cellaux);
                    //checkSetLights (bNonMinedOrWallUpDown, cellaux, imiSource, true, iPaintWalls);
                    cellaux.setLights(imiSource);

                    if (bNonMinedOrWallUpDown) {
                        break;
                    }
                }

                if (bNonMinedOrWall) {
                    return;
                }

                error = error - deltay;
                if (error < 0) {
                    y = y + ystep;
                    error = error + deltax;
                }
            }
        } else {
            bNonMinedOrWall = false;
            for (int x = x0; x >= x1; x += inc) {
                if (steep) {
                    cell = World.getCell(y, x, zLevel);
                } else {
                    cell = World.getCell(x, y, zLevel);
                }

                bNonMinedOrWall = isCellBlockingLight(cell);
                checkSetLights(bNonMinedOrWall, cell, imiSource, iDirection);

				// Arriba y abajo, teniendo en cuenta el radio
                // Arriba
                iZAux = zLevel - iRadious;
                if (iZAux < 0) {
                    iZAux = 0;
                }

                bNonMinedOrWallUpDown = false;
                for (int i = (zLevel - 1); i >= iZAux; i--) {
                    if (steep) {
                        cellaux = World.getCell(y, x, i);
                    } else {
                        cellaux = World.getCell(x, y, i);
                    }

                    bNonMinedOrWallUpDown = isCellBlockingLight(cellaux);
                    bNonMinedOrWallUpDown = checkSetLightsUp(bNonMinedOrWallUpDown, cellaux, imiSource, iDirection);

                    if (!bNonMinedOrWallUpDown) {
                        break;
                    }
                }

                // Abajo
                iZAux = zLevel + iRadious;
                if (iZAux >= World.MAP_DEPTH) {
                    iZAux = (World.MAP_DEPTH - 1);
                }

                bNonMinedOrWallUpDown = false;
                for (int i = (zLevel + 1); i <= iZAux; i++) {
                    if (steep) {
                        cellaux = World.getCell(y, x, i);
                    } else {
                        cellaux = World.getCell(x, y, i);
                    }

                    bNonMinedOrWallUpDown = isCellBlockingLight(cellaux);
//					checkSetLights (bNonMinedOrWallUpDown, cellaux, imiSource, true, iPaintWalls);
                    cellaux.setLights(imiSource);

                    if (bNonMinedOrWallUpDown) {
                        break;
                    }
                }

                if (bNonMinedOrWall) {
                    return;
                }

                error = error - deltay;
                if (error < 0) {
                    y = y + ystep;
                    error = error + deltax;
                }
            }
        }
    }

}
