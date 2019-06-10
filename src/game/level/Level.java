package game.level;

import IO.Input;
import game.Game;
import game.entity.Enemy;
import game.entity.Entity;
import graphics.TextureAtlas;
import utils.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {

    public static final int TILE_SCALE = 16;
    public static final int TILE_IN_GAME_SCALE = 2;
    public static final int SCALED_TILE_SIZE = TILE_SCALE * TILE_IN_GAME_SCALE;
    private static float offsetX = 0;
    private static String level = "Level_1.lvl";
    private TextureAtlas levelAtlas;
    private TextureAtlas object;
    private int[][] tileMap;
    private Map<TileType, Tile> tiles;
    private Map<TileType, SolidTile> solidTiles;
    private Input input;
    private ArrayList<Entity> entities = new ArrayList<>();


    public Level(TextureAtlas levelAtlas, TextureAtlas object, Input input) {
        this.levelAtlas = levelAtlas;
        this.object = object;
        this.input = input;

        tiles = new HashMap<>();
        solidTiles = new HashMap<>();
        createTilesMap();
        createSolidTilesMap();

        tileMap = Utils.levelParser("res\\levels\\" + level);

        Game.setSize(new Dimension(Game.width, tileMap.length * 16 * TILE_IN_GAME_SCALE));
    }


    public static float getOffsetX() {
        return Level.offsetX;
    }


    public static void setOffsetX(float offsetX) {
        Level.offsetX = offsetX;
    }


    public static void setLevel(String level) {
        Level.level = level;
    }


    public void update() {
        for (Entity entity : entities) {
            entity.update(input, this);
        }
    }


    public void render(Graphics2D g) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                if (j * TILE_SCALE * TILE_IN_GAME_SCALE < offsetX)
                    continue;
                if (j * TILE_SCALE * TILE_IN_GAME_SCALE + offsetX > Game.width)
                    break;
                if (solidTiles.get(TileType.fromNumeric(tileMap[i][j])) != null) {
                    solidTiles.get(TileType.fromNumeric(tileMap[i][j])).render(g, (int) (j * SCALED_TILE_SIZE + offsetX),
                            i * SCALED_TILE_SIZE);
                }
                if (tiles.get(TileType.fromNumeric(tileMap[i][j])) == null && !Game.IS_DEBUG) {
                    continue;
                }
                if (TileType.fromNumeric(tileMap[i][j]) == TileType.Empty && !Game.IS_DEBUG) {
                    continue;
                }
                if (tiles.get(TileType.fromNumeric(tileMap[i][j])) != null) {
                    tiles.get(TileType.fromNumeric(tileMap[i][j])).render(g, (int) (j * SCALED_TILE_SIZE + offsetX), i * SCALED_TILE_SIZE);
                }
                int x = (int) (j * TILE_SCALE * TILE_IN_GAME_SCALE + offsetX);
                int y = i * TILE_SCALE * TILE_IN_GAME_SCALE;
                int width = TILE_SCALE * TILE_IN_GAME_SCALE;
                int height = TILE_SCALE * TILE_IN_GAME_SCALE;

                if (Game.IS_DEBUG) {
                    g.setColor(Color.BLACK);
                    g.drawLine(x, y, x + width, y);
                    g.drawLine(x, y, x, y + height);
                    g.setColor(Color.YELLOW);
                    g.drawString(j + ":" + i, x, y + 10);
                }
            }
        }

        for (Entity entity : entities) {
            entity.render(g);
        }
    }


    public Map<TileType, Tile> getTiles() {
        return tiles;
    }


    public Map<TileType, SolidTile> getSolidTiles() {
        return solidTiles;
    }


    public int[][] getTileMap() {
        return tileMap;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }


    private void createSolidTilesMap() {
        solidTiles.put(TileType.GROUND_BROWN, new SolidTile(levelAtlas.cut(0 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BROWN));
        solidTiles.put(TileType.GROUND_BLUE, new SolidTile(levelAtlas.cut(0 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.GROUND_BLUE));
        solidTiles.put(TileType.GRANITE_BROWN, new SolidTile(levelAtlas.cut(0 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.GRANITE_BROWN));
        solidTiles.put(TileType.GRANITE_BLUE, new SolidTile(levelAtlas.cut(0 * TILE_SCALE, 3 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.GRANITE_BLUE));

        solidTiles.put(TileType.BRICK_BLOCK_BROWN, new SolidTile(levelAtlas.cut(1 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BROWN));
        solidTiles.put(TileType.BRICK_BLOCK_BLUE, new SolidTile(levelAtlas.cut(1 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BRICK_BLOCK_BLUE));
        solidTiles.put(TileType.COIN_BLOCK, new SolidTile(levelAtlas.cut(24 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.COIN_BLOCK));

        solidTiles.put(TileType.PIPE_CENTER_LEFT, new SolidTile(levelAtlas.cut(0 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_CENTER_LEFT));
        solidTiles.put(TileType.PIPE_CENTER_RIGHT, new SolidTile(levelAtlas.cut(1 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_CENTER_RIGHT));
        solidTiles.put(TileType.PIPE_UP_LEFT, new SolidTile(levelAtlas.cut(0 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_UP_LEFT));
        solidTiles.put(TileType.PIPE_UP_RIGHT, new SolidTile(levelAtlas.cut(1 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_UP_RIGHT));

        solidTiles.put(TileType.PIPE_LEFT_UP, new SolidTile(levelAtlas.cut(2 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_UP));
        solidTiles.put(TileType.PIPE_LEFT_DOWN, new SolidTile(levelAtlas.cut(2 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_DOWN));
        solidTiles.put(TileType.PIPE_LEFT_CENTER_UP, new SolidTile(levelAtlas.cut(3 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_CENTER_UP));
        solidTiles.put(TileType.PIPE_LEFT_CENTER_DOWN, new SolidTile(levelAtlas.cut(3 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_CENTER_DOWN));
        solidTiles.put(TileType.PIPE_SMALL_UP, new SolidTile(levelAtlas.cut(4 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_SMALL_UP));
        solidTiles.put(TileType.PIPE_SMALL_DOWN, new SolidTile(levelAtlas.cut(4 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_SMALL_DOWN));

        solidTiles.put(TileType.PLATFORM_GRASS_LEFT, new SolidTile(levelAtlas.cut(5 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_LEFT));
        solidTiles.put(TileType.PLATFORM_GRASS_CENTER, new SolidTile(levelAtlas.cut(6 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_CENTER));
        solidTiles.put(TileType.PLATFORM_GRASS_RIGHT, new SolidTile(levelAtlas.cut(7 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_RIGHT));
    }


    private void createTilesMap() {
        tiles.put(TileType.Empty, new Tile(levelAtlas.cut(20 * TILE_SCALE, 25 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.Empty));

        tiles.put(TileType.COIN, new Tile(levelAtlas.cut(24 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.COIN));

        tiles.put(TileType.BUSH_CENTER, new Tile(levelAtlas.cut(12 * TILE_SCALE, 11 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BUSH_CENTER));
        tiles.put(TileType.BUSH_LEFT, new Tile(levelAtlas.cut(11 * TILE_SCALE, 11 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BUSH_LEFT));
        tiles.put(TileType.BUSH_RIGHT, new Tile(levelAtlas.cut(13 * TILE_SCALE, 11 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BUSH_RIGHT));

        tiles.put(TileType.CLOUD_UP_LEFT, new Tile(levelAtlas.cut(0 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_UP_LEFT));
        tiles.put(TileType.CLOUD_UP_CENTER, new Tile(levelAtlas.cut(1 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_UP_CENTER));
        tiles.put(TileType.CLOUD_UP_RIGHT, new Tile(levelAtlas.cut(2 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_UP_RIGHT));
        tiles.put(TileType.CLOUD_LEFT, new Tile(levelAtlas.cut(0 * TILE_SCALE, 21 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_LEFT));
        tiles.put(TileType.CLOUD_CENTER, new Tile(levelAtlas.cut(1 * TILE_SCALE, 21 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_CENTER));
        tiles.put(TileType.CLOUD_RIGHT, new Tile(levelAtlas.cut(2 * TILE_SCALE, 21 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_RIGHT));
        tiles.put(TileType.CLOUD_TRIPLE_LEFT, new Tile(levelAtlas.cut(8 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_TRIPLE_LEFT));
        tiles.put(TileType.CLOUD_TRIPLE_CENTER, new Tile(levelAtlas.cut(9 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_TRIPLE_CENTER));
        tiles.put(TileType.CLOUD_TRIPLE_RIGHT, new Tile(levelAtlas.cut(10 * TILE_SCALE, 20 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.CLOUD_TRIPLE_RIGHT));

        tiles.put(TileType.MOUNTAIN_LEFT, new Tile(levelAtlas.cut(8 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_UP, new Tile(levelAtlas.cut(9 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_RIGHT, new Tile(levelAtlas.cut(10 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_LEFT));
        tiles.put(TileType.MOUNTAIN_CENTER, new Tile(levelAtlas.cut(9 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_CENTER));
        tiles.put(TileType.MOUNTAIN_CENTER_STONE, new Tile(levelAtlas.cut(8 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.MOUNTAIN_CENTER_STONE));

        tiles.put(TileType.CASTLE_BRICK, new Tile(levelAtlas.cut(11 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.CASTLE_BRICK));
        tiles.put(TileType.CASTLE_RECT_HOLE, new Tile(levelAtlas.cut(22 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.CASTLE_RECT_HOLE));
        tiles.put(TileType.CASTLE_OVAL_HOLE, new Tile(levelAtlas.cut(21 * TILE_SCALE, 1 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.CASTLE_OVAL_HOLE));

        tiles.put(TileType.BAR, new Tile(levelAtlas.cut(16 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BAR));
        tiles.put(TileType.BAR_TOP, new Tile(levelAtlas.cut(16 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.BAR_TOP));
        tiles.put(TileType.FLAG, new Tile(object.cut(8 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.FLAG));
    }

    public static String getLevel() {
        return level;
    }
}
