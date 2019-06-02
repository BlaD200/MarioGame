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


    public Level(TextureAtlas level, TextureAtlas object) {
        tiles = new HashMap<>();

        tiles.put(TileType.Empty, new Tile(level.cut(20 * TILE_SCALE, 25 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.Empty));
        tiles.put(TileType.GROUND_BROWN, new Tile(level.cut(0 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BROWN));
        tiles.put(TileType.GROUND_BLUE, new Tile(level.cut(0 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.GROUND_BLUE));
        tiles.put(TileType.GRANITE_BROWN, new Tile(level.cut(0 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.GRANITE_BROWN));
        tiles.put(TileType.GRANITE_BLUE, new Tile(level.cut(0 * TILE_SCALE, 3 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.GRANITE_BLUE));

        tiles.put(TileType.BRICK_BLOCK_BROWN, new Tile(level.cut(1 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BROWN));
        tiles.put(TileType.BRICK_BLOCK_BLUE, new Tile(level.cut(1 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BLUE));
        tiles.put(TileType.COIN_BLOCK, new Tile(level.cut(24 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.COIN_BLOCK));
        tiles.put(TileType.COIN, new Tile(level.cut(24 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.COIN));

        tiles.put(TileType.BUSH_CENTER, new Tile(level.cut(12 * TILE_SCALE, 11 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.BUSH_CENTER));
        tiles.put(TileType.BUSH_LEFT, new Tile(level.cut(11 * TILE_SCALE, 11 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.BUSH_LEFT));
        tiles.put(TileType.BUSH_RIGHT, new Tile(level.cut(13 * TILE_SCALE, 11 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.BUSH_RIGHT));

        tiles.put(TileType.PLATFORM_GRASS_LEFT, new Tile(level.cut(5 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_LEFT));
        tiles.put(TileType.PLATFORM_GRASS_CENTER, new Tile(level.cut(6 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_CENTER));
        tiles.put(TileType.PLATFORM_GRASS_RIGHT, new Tile(level.cut(7 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_RIGHT));

        tiles.put(TileType.CLOUD_UP_LEFT, new Tile(level.cut(0 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_UP_LEFT));
        tiles.put(TileType.CLOUD_UP_CENTER, new Tile(level.cut(1 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_UP_CENTER));
        tiles.put(TileType.CLOUD_UP_RIGHT, new Tile(level.cut(2 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_UP_RIGHT));
        tiles.put(TileType.CLOUD_LEFT, new Tile(level.cut(0 * TILE_SCALE, 21 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_LEFT));
        tiles.put(TileType.CLOUD_CENTER, new Tile(level.cut(1 * TILE_SCALE, 21 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_CENTER));
        tiles.put(TileType.CLOUD_RIGHT, new Tile(level.cut(2 * TILE_SCALE, 21 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_RIGHT));
        tiles.put(TileType.CLOUD_TRIPLE_LEFT, new Tile(level.cut(8 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_TRIPLE_LEFT));
        tiles.put(TileType.CLOUD_TRIPLE_CENTER, new Tile(level.cut(9 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_TRIPLE_CENTER));
        tiles.put(TileType.CLOUD_TRIPLE_RIGHT, new Tile(level.cut(10 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_TRIPLE_RIGHT));

        tiles.put(TileType.MOUNTAIN_LEFT, new Tile(level.cut(8 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_UP, new Tile(level.cut(9 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_RIGHT, new Tile(level.cut(10 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_CENTER, new Tile(level.cut(9 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_CENTER));
        tiles.put(TileType.MOUNTAIN_CENTER_STONE, new Tile(level.cut(8 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_CENTER_STONE));

        tiles.put(TileType.PIPE_CENTER_LEFT, new Tile(level.cut(0 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_CENTER_LEFT));
        tiles.put(TileType.PIPE_CENTER_RIGHT, new Tile(level.cut(1 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_CENTER_RIGHT));
        tiles.put(TileType.PIPE_UP_LEFT, new Tile(level.cut(0 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.PIPE_UP_LEFT));
        tiles.put(TileType.PIPE_UP_RIGHT, new Tile(level.cut(1 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.PIPE_UP_RIGHT));

        tiles.put(TileType.PIPE_LEFT_UP, new Tile(level.cut(2 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_UP));
        tiles.put(TileType.PIPE_LEFT_DOWN, new Tile(level.cut(2 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_DOWN));
        tiles.put(TileType.PIPE_LEFT_CENTER_UP, new Tile(level.cut(3 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_CENTER_UP));
        tiles.put(TileType.PIPE_LEFT_CENTER_DOWN, new Tile(level.cut(3 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_CENTER_DOWN));
        tiles.put(TileType.PIPE_SMALL_UP, new Tile(level.cut(4 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_SMALL_UP));
        tiles.put(TileType.PIPE_SMALL_DOWN, new Tile(level.cut(4 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_SMALL_DOWN));

        tiles.put(TileType.CASTLE_BRICK, new Tile(level.cut(11 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.CASTLE_BRICK));
        tiles.put(TileType.CASTLE_RECT_HOLE, new Tile(level.cut(22 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.CASTLE_RECT_HOLE));
        tiles.put(TileType.CASTLE_OVAL_HOLE, new Tile(level.cut(21 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.CASTLE_OVAL_HOLE));

        tiles.put(TileType.BAR, new Tile(level.cut(16 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BAR));
        tiles.put(TileType.BAR_TOP, new Tile(level.cut(16 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE ), TILE_IN_GAME_SCALE, TileType.BAR_TOP));
        tiles.put(TileType.FLAG, new Tile(object.cut(8 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.FLAG));


        tileMap = Utils.levelParser("res\\levels\\level.lvl");
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


    public Map<TileType, Tile> getTiles() {
        return tiles;
    }
}
