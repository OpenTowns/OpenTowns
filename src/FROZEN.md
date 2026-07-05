# Frozen files: a mindfulness guide

The point of this list is to know which edits can shift simulation behavior, and which edits are in the free zone where you can work without worry.

See `src/README.md` for the package-by-package map and the testing section for how the pins and the headless harness work.

## Frozen: simulation core

Whole packages under `src/xaos/` (every file):

- `main/` (`World.java`, `Game.java`)
- `tiles/` (`Cell.java`, `Tile.java`, `terrain/**` including fluid `Water`/`Lava`, `entities/**`: livings, items, buildings, special)
- `tasks/`, `actions/`, `generator/`, `stockpiles/`, `zones/`
- `caravans/`, `dungeons/`, `effects/`, `events/`, `gods/`, `skills/`
- `campaign/` (mission selection drives worldgen)
- `data/` (the serialized + hashed data carriers)

Root files: `Towns.java`, `TownsProperties.java`.

Frozen subset of `src/xaos/utils/` (the rest of this package is free, see below):

`Utils`, `UtilsDice`, `UtilsGeometry`, `UtilsLineOfSight`, `UtilsSavegame`, `UtilsString`, `UtilsXML`, `UtilsIniHeaders`, `UtilsFiles`, `Names` (draws RNG for entity names), `Date`, `Point3D`, `Point3DShort`, `AStarQueue`, `AStarBinaryHeap`, `AStarNodo`, `AStarQueueItem`.

## Frozen: data inputs (not `.java`, but they define behavior)

The pins hash the *result* of loading these, so they are just as load-bearing:

- `src/data/**` : all game XML (`items`, `buildings`, `livingentities`, `terrain`, `actions`, `effects`, `events`, `gods`, `heroes`, `caravans`, `skills`), every `gen_*.xml`, `campaigns.xml`, and `data/languages/*.properties` (which must also stay ISO-8859-1).
- `src/graphics.ini` : **partly frozen.** A tile's `animationFrameDelay` sets the range of the `Tile` constructor's RNG draw, so changing frame delays shifts the stream. Atlas *coordinates* are render-only and free.
- `src/towns.ini` : the values that select folders, mods and campaign feed the sim; window/FPS/keybind values do not.

## Gray zone: mostly free, but watch one thing

- `src/xaos/panels/UIPanel.java`, `src/xaos/panels/MainPanel.java` : panel *behavior* is free, but each `static Tile ... = new Tile(...)` field draws an RNG number when the class first initializes (`UIPanel` inits mid-sim; `MainPanel` inits during scenario tests). Editing those field lists is a behavior change; everything else in these files is free.
- `src/xaos/property/**` : config plumbing that parses the `.ini` files; you will rarely touch it, but the parser feeds sim inputs.
- `src/xaos/TownsHeadless.java` : the harness is free to extend, but its two hash methods are the pin oracle and are frozen.

## Free zone: edit freely (QoL, graphics, camera, minimap, modding UX)

- `src/xaos/compat/**` (the platform layer, meant to be edited)
- `src/xaos/panels/**` behavior (UI, minimap, menus, info), minus the two `<clinit>` Tile lists noted above
- `src/xaos/setup/**` (first-run asset copy; not in the headless path)
- Free `src/xaos/utils/`: `UtilsGL`, `UtilsAL`, `UtilsKeyboard`, `UtilsServer`, `JNASteamAPI`, `Log`, `Messages`, `LanguageData`, `LocalResourceClassLoader`, `PropertiesWriter`, `UtilFont`, `CharDef`, `ImageData`, `TextureData`, `ColorGL`
- `test/**` (the guard itself)
- `src/audio.ini`, and atlas coordinates in `graphics.ini`
