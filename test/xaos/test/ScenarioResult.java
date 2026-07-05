package xaos.test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The hashes a {@link ScenarioRunner} recorded over a run: the terrain and
 * full-state hash at each checkpoint tick, plus the final values. Checkpoint
 * hashes let a divergence be localized in time instead of only showing up as a
 * single end-of-run mismatch.
 *
 * The hash definitions come from TownsHeadless.computeTerrainHash /
 * computeStateHash and are frozen along with the golden pins.
 */
final class ScenarioResult {

    private final Map<Long, String> terrainHashes = new LinkedHashMap<>();
    private final Map<Long, String> stateHashes = new LinkedHashMap<>();
    private String finalTerrainHash;
    private String finalStateHash;

    void checkpoint(long tick, long terrainHash, long stateHash) {
        terrainHashes.put(tick, Long.toHexString(terrainHash));
        stateHashes.put(tick, Long.toHexString(stateHash));
    }

    void finish(long terrainHash, long stateHash) {
        finalTerrainHash = Long.toHexString(terrainHash);
        finalStateHash = Long.toHexString(stateHash);
    }

    String finalTerrainHash() {
        return finalTerrainHash;
    }

    String finalStateHash() {
        return finalStateHash;
    }

    String terrainHashAt(long tick) {
        return terrainHashes.get(tick);
    }

    String stateHashAt(long tick) {
        return stateHashes.get(tick);
    }

    /** Human-readable checkpoint dump for reading a run or recording new pins. */
    String describe() {
        StringBuilder sb = new StringBuilder();
        for (Long tick : terrainHashes.keySet()) {
            sb.append("  tick ").append(tick)
              .append(": terrain=").append(terrainHashes.get(tick))
              .append(" state=").append(stateHashes.get(tick))
              .append('\n');
        }
        sb.append("  final: terrain=").append(finalTerrainHash)
          .append(" state=").append(finalStateHash);
        return sb.toString();
    }
}
