package game.level;

import game.Game;
import graphics.TextureAtlas;
import utils.Utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Level {

    public static final int TILE_SCALE = 16;
    public static final int TILE_IN_GAME_SCALE = 1;
    public static final int SCALED_TILE_SIZE = TILE_SCALE * TILE_IN_GAME_SCALE;
    public static final int TILE_IN_WIDth = Game.WIDTH / SCALED_TILE_SIZE;
    public static final int TILE_IN_HEIGHT = Game.HEIGHT / SCALED_TILE_SIZE;

    private int[][] tileMap;
    private Map<TileType, Tile> tiles;


    public Level(TextureAtlas atlas) {
        tiles = new HashMap<>();

        tiles.put(TileType.Empty, new Tile(atlas.cut(14 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.Empty));
        tiles.put(TileType.GROUND_BROWN, new Tile(atlas.cut(0 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BROWN));
        tiles.put(TileType.BRICK_BLOCK_BROWN, new Tile(atlas.cut(1 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BROWN));
        tiles.put(TileType.COIN_BLOCK, new Tile(atlas.cut(25 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.COIN_BLOCK));
        tiles.put(TileType.BUSH, new Tile(atlas.cut(12 * TILE_SCALE, 10 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE * 3), TILE_IN_GAME_SCALE, TileType.BUSH));
        tiles.put(TileType.CLOUD_RIGHT, new Tile(atlas.cut(0 * TILE_SCALE, 22 * TILE_SCALE, TILE_SCALE * 3, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_RIGHT));
        tiles.put(TileType.MOUNTAIN_LEFT, new Tile(atlas.cut(8 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE * 2), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_UP, new Tile(atlas.cut(9 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE * 2), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_RIGHT, new Tile(atlas.cut(10 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE * 2), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));


        tileMap = Utils.levelParser("res\\level.lvl");
    }


    public void update() {

    }


    public void render(Graphics2D g) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                if (tiles.get(TileType.fromNumeric(tileMap[i][j])) == null)
                    continue;
                tiles.get(TileType.fromNumeric(tileMap[i][j])).render(g, j * SCALED_TILE_SIZE, i * SCALED_TILE_SIZE);
            }
        }
    }
}
