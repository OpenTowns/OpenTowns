package xaos.utils;

import java.awt.Point;

import xaos.main.World;

public final class UtilsGeometry {

    public static final int MAX_DISTANCE = World.MAP_WIDTH * World.MAP_HEIGHT * 512; // 512 por poner algo alto

    private UtilsGeometry() { /*static utility class*/ }

	// ************
    // * 2D UTILS *
    // ************
    /**
     * Returns a point in the Bézier curve
     *
     * @param pointSource Source point
     * @param pointDest Dstination point
     * @param pointControlA A-control point
     * @param pointControlB B-control point
     * @param dIteration Iteration from 0.0 (first point) to 1.0 (last point)
     * @return
     */
    public static Point getBezierPoint(Point pointSource, Point pointDest, Point pointControlA, Point pointControlB, double dIteration) {
        // Primeros puntos intermedios (3 puntos)
        double dS_A_X = pointSource.x + ((pointControlA.x - pointSource.x) * dIteration);
        double dS_A_Y = pointSource.y + ((pointControlA.y - pointSource.y) * dIteration);
        double dA_B_X = pointControlA.x + ((pointControlB.x - pointControlA.x) * dIteration);
        double dA_B_Y = pointControlA.y + ((pointControlB.y - pointControlA.y) * dIteration);
        double dB_D_X = pointControlB.x + ((pointDest.x - pointControlB.x) * dIteration);
        double dB_D_Y = pointControlB.y + ((pointDest.y - pointControlB.y) * dIteration);

        // Segundos puntos intermedios (2 puntos)
        double dSA_AB_X = dS_A_X + ((dA_B_X - dS_A_X) * dIteration);
        double dSA_AB_Y = dS_A_Y + ((dA_B_Y - dS_A_Y) * dIteration);
        double dAB_BD_X = dA_B_X + ((dB_D_X - dA_B_X) * dIteration);
        double dAB_BD_Y = dA_B_Y + ((dB_D_Y - dA_B_Y) * dIteration);

        // Punto final
        double dBezier_X = dSA_AB_X + ((dAB_BD_X - dSA_AB_X) * dIteration);
        double dBezier_Y = dSA_AB_Y + ((dAB_BD_Y - dSA_AB_Y) * dIteration);

        return new Point((int) dBezier_X, (int) dBezier_Y);
    }

    /**
     * Returns true if point it's inside 0-(width-1),0-(height-1)
     *
     * @param iX Point X
     * @param iY Point Y
     * @param iWidth Width
     * @param iHeight Height
     * @return true if point it's inside 0-(width-1),0-(height-1)
     */
    public static boolean isValidPoint(int iX, int iY, int iWidth, short iHeight) {
        return iX >= 0 && iX < iWidth && iY >= 0 && iY < iHeight;
    }

    /**
     * Indica si la coordenada pasada está dentro del mapa
     *
     * @param x
     * @param y
     * @param z
     * @return
     */
    public static boolean isInsideMap(int x, int y, int z) {
        return x >= 0 && x < World.MAP_WIDTH && y >= 0 && y < World.MAP_HEIGHT && z >= 0 && z < World.MAP_DEPTH;
    }

    /**
     * Indica si el punto (coordenada) pasado está dentro del mapa
     *
     * @param p3d
     * @return
     */
    public static boolean isInsideMap(Point3DShort p3d) {
        return p3d.x >= 0 && p3d.x < World.MAP_WIDTH && p3d.y >= 0 && p3d.y < World.MAP_HEIGHT && p3d.z >= 0 && p3d.z < World.MAP_DEPTH;
    }

    public static int getDistance(Point3DShort p3dSource, Point3DShort p3dDestination) {
        return getDistance(p3dSource.x, p3dSource.y, p3dSource.z, p3dDestination.x, p3dDestination.y, p3dDestination.z);
    }

    public static int getDistance(Point3DShort p3dSource, int xd, int yd, int zd) {
        return getDistance(p3dSource.x, p3dSource.y, p3dSource.z, xd, yd, zd);
    }

    public static int getDistance(int xs, int ys, int zs, Point3DShort p3dDestination) {
        return getDistance(xs, ys, zs, p3dDestination.x, p3dDestination.y, p3dDestination.z);
    }

    public static int getDistance(int xs, int ys, int zs, int xd, int yd, int zd) {
        return Math.abs(xs - xd) + Math.abs(ys - yd) + (Math.abs(zs - zd) * (32));
//		int h = Math.max (Math.abs (xd - xs), Math.abs (yd - ys));
//		if (zs >= (World.MAP_NUM_LEVELS_OUTSIDE - 1)) {
//			if (zd >= (World.MAP_NUM_LEVELS_OUTSIDE - 1)) {
//				// Los 2 puntos son underground
//				h += ((World.MAP_WIDTH / 4) * Math.abs (zd - zs)); // Método Manhattan, funciona mejor incluso permitiendo movimiento diagonal que la "Distancia Chebyshev" (20% mejor)
//			} else {
//				// El origen es underground pero el destino no
//				h += ((World.MAP_WIDTH / 4) * Math.abs (zs - (World.MAP_NUM_LEVELS_OUTSIDE - 1)) + 2 * (World.MAP_NUM_LEVELS_OUTSIDE - 1 - zd)); // Método Manhattan, funciona mejor incluso permitiendo movimiento diagonal que la "Distancia Chebyshev" (20% mejor)
//			}
//		} else {
//			// El origen es outside
//			if (zd >= (World.MAP_NUM_LEVELS_OUTSIDE - 1)) {
//				// El destino es underground (y origen outside)
//				h += ((World.MAP_WIDTH / 4) * Math.abs (zd - (World.MAP_NUM_LEVELS_OUTSIDE - 1)) + 2 * (World.MAP_NUM_LEVELS_OUTSIDE - 1 - zs)); // Método Manhattan, funciona mejor incluso permitiendo movimiento diagonal que la "Distancia Chebyshev" (20% mejor)
//			} else {
//				// El destino es outside, como el origen
//				h += (2 * Math.abs (zd - zs)); // Método Manhattan, funciona mejor incluso permitiendo movimiento diagonal que la "Distancia Chebyshev" (20% mejor)
//			}
//		}
//
//		return h;
    }

    /**
     * Fast integer SQRT
     *
     * @param number
     * @return
     */
    public static int sqrt(int number) {
        if (number >= 1 && number < 4) {
            return 1;
        }
        if (number >= 4 && number < 9) {
            return 2;
        }
        if (number >= 9 && number < 16) {
            return 3;
        }
        if (number >= 16 && number < 25) {
            return 4;
        }
        if (number >= 25 && number < 36) {
            return 5;
        }
        if (number >= 36 && number < 49) {
            return 6;
        }
        if (number >= 49 && number < 64) {
            return 7;
        }
        if (number >= 64 && number < 81) {
            return 8;
        }
        if (number >= 81 && number < 100) {
            return 9;
        }
        if (number >= 100 && number < 121) {
            return 10;
        }
        if (number >= 121 && number < 144) {
            return 11;
        }
        if (number >= 144 && number < 169) {
            return 12;
        }
        if (number >= 169 && number < 196) {
            return 13;
        }
        if (number >= 196 && number < 225) {
            return 14;
        }
        if (number >= 225) {
            return 15;
        }

        return 0;
    }

}
