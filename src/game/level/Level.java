package game.level;

import IO.Input;
import game.Game;
import game.Player;
import graphics.TextureAtlas;
import utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static game.Game.PLAYER_TEXTURES_ATLAS_FILE_NAME;

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
    private Player player;

    // temp
    private Rectangle thisUpLeft;
    private Rectangle thisDownRight;
    private ArrayList<SolidTile> solidTilesPhysics = new ArrayList<>();
    // temp end


    public Level(TextureAtlas levelAtlas, TextureAtlas object, Input input) {
        this.levelAtlas = levelAtlas;
        this.object = object;
        this.input = input;

        tiles = new HashMap<>();
        solidTiles = new HashMap<>();
        createTilesMap();
        createSolidTilesMap();

        tileMap = Utils.levelParser("res\\levels\\" + level);

        TextureAtlas playerAtlas = new TextureAtlas(PLAYER_TEXTURES_ATLAS_FILE_NAME);
        player = new Player(50, 400, 1.9f, 3, 1.5f, 5, 7, playerAtlas);

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
        player.update(input);

        float x = player.getX() - offsetX;
        float y = player.getY();

        int tileX = ((int) (x / (TILE_SCALE * TILE_IN_GAME_SCALE)) * TILE_SCALE * TILE_IN_GAME_SCALE);
        int tileY = (int) ((y) / (TILE_SCALE * TILE_IN_GAME_SCALE)) * TILE_SCALE * TILE_IN_GAME_SCALE;
        int tileW = TILE_SCALE * TILE_IN_GAME_SCALE;
        int tileH = TILE_SCALE * TILE_IN_GAME_SCALE;

        physicsUpdate(tileX, tileY, tileW, tileH);
    }


    private void physicsUpdate(int tileX, int tileY, int tileW, int tileH) {
        int scale = TILE_IN_GAME_SCALE * TILE_SCALE;
        int x = tileX / scale;
        int y = tileY / scale;

        // TODO Auto-generated method stub
        for (int i = x - 1; i <= x + 2; i++) {
            for (int j = y - 1; j <= y + 2; j++) {
                try {
                    if (solidTiles.get(TileType.fromNumeric(tileMap[j][i])) != null) {
                        SolidTile tile = new SolidTile(new BufferedImage(tileW, tileH, BufferedImage.TYPE_INT_ARGB),
                                TILE_IN_GAME_SCALE, TileType.fromNumeric(tileMap[j][i]));
                        tile.update((int) (i * scale + offsetX), j * scale, scale, scale);
                        solidTilesPhysics.add(tile);
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }

        x = (int) player.getX();
        y = (int) player.getY();
        int w = (int) player.getWidth();
        int h = (int) player.getHeight();

        thisUpLeft = new Rectangle(x, y, w, h);
        thisDownRight = new Rectangle(x, y + 2, w, h);

        boolean bottomClear = true;
        boolean leftClear = true;
        boolean upClear = true;
        boolean rightClear = true;

        for (SolidTile solidTilesPhysic : solidTilesPhysics) {
            Rectangle[] rectangles = solidTilesPhysic.getCollisionRect();

            if (thisDownRight.intersects(rectangles[0]) && bottomClear) {
                bottomClear = false;
                player.setGravityEnabled(false);
                System.out.println("down");
            }
            if (thisUpLeft.intersects(rectangles[1]) && rightClear) {
                rightClear = false;
                player.setRightClear(false);
                System.out.println("right");
            }

            if (thisUpLeft.intersects(rectangles[2]) && upClear) {
                upClear = false;
                player.setUpClear(false);
                System.out.println("up");
            }

            if (thisUpLeft.intersects(rectangles[3]) && leftClear) {
                leftClear = false;
                player.setLeftClear(false);
                System.out.println("left");
            }
        }

        if (bottomClear)
            player.setGravityEnabled(true);
        if (rightClear)
            player.setRightClear(true);
        if (upClear)
            player.setUpClear(true);
        if (leftClear)
            player.setLeftClear(true);
    }


    public void render(Graphics2D g) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                if (solidTiles.get(TileType.fromNumeric(tileMap[i][j])) != null) {
                    solidTiles.get(TileType.fromNumeric(tileMap[i][j])).render(g, (int) (j * SCALED_TILE_SIZE + offsetX),
                            i * SCALED_TILE_SIZE);
                }
                if (tiles.get(TileType.fromNumeric(tileMap[i][j])) == null) {
                    continue;
                }
                if (TileType.fromNumeric(tileMap[i][j]) == TileType.Empty) {
                    continue;
                }
                if (tiles.get(TileType.fromNumeric(tileMap[i][j])) != null) {
                    tiles.get(TileType.fromNumeric(tileMap[i][j])).render(g, (int) (j * SCALED_TILE_SIZE + offsetX), i * SCALED_TILE_SIZE);
                }
                int x = (int) (j * TILE_SCALE * TILE_IN_GAME_SCALE + offsetX);
                int y = i * TILE_SCALE * TILE_IN_GAME_SCALE;
                int width = TILE_SCALE * TILE_IN_GAME_SCALE;
                int height = TILE_SCALE * TILE_IN_GAME_SCALE;

//                g.setColor(Color.BLACK);
//                g.drawLine(x, y, x + width, y);
//                g.drawLine(x, y, x, y + height);
//                g.setColor(Color.YELLOW);
//                g.drawString(j + ":" + i, x, y + 10);
//
//                g.setColor(Color.RED);
//                g.draw(thisDownRight);
//                g.draw(thisUpLeft);
//                g.setColor(Color.WHITE);
            }
        }
        player.render(g);

        for (int i = 0; i < solidTilesPhysics.size(); i++) {
            SolidTile solidTilesPhysic = solidTilesPhysics.get(i);
            solidTilesPhysic.renderDebug(g);
            solidTilesPhysics.remove(solidTilesPhysic);
        }
    }


    public Map<TileType, Tile> getTiles() {
        return tiles;
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

        tiles.put(TileType.PLATFORM_GRASS_LEFT, new Tile(levelAtlas.cut(5 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_LEFT));
        tiles.put(TileType.PLATFORM_GRASS_CENTER, new Tile(levelAtlas.cut(6 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_CENTER));
        tiles.put(TileType.PLATFORM_GRASS_RIGHT, new Tile(levelAtlas.cut(7 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PLATFORM_GRASS_RIGHT));

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

        tiles.put(TileType.PIPE_CENTER_LEFT, new Tile(levelAtlas.cut(0 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_CENTER_LEFT));
        tiles.put(TileType.PIPE_CENTER_RIGHT, new Tile(levelAtlas.cut(1 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_CENTER_RIGHT));
        tiles.put(TileType.PIPE_UP_LEFT, new Tile(levelAtlas.cut(0 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_UP_LEFT));
        tiles.put(TileType.PIPE_UP_RIGHT, new Tile(levelAtlas.cut(1 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_UP_RIGHT));

        tiles.put(TileType.PIPE_LEFT_UP, new Tile(levelAtlas.cut(2 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_UP));
        tiles.put(TileType.PIPE_LEFT_DOWN, new Tile(levelAtlas.cut(2 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_DOWN));
        tiles.put(TileType.PIPE_LEFT_CENTER_UP, new Tile(levelAtlas.cut(3 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_CENTER_UP));
        tiles.put(TileType.PIPE_LEFT_CENTER_DOWN, new Tile(levelAtlas.cut(3 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_LEFT_CENTER_DOWN));
        tiles.put(TileType.PIPE_SMALL_UP, new Tile(levelAtlas.cut(4 * TILE_SCALE, 8 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_SMALL_UP));
        tiles.put(TileType.PIPE_SMALL_DOWN, new Tile(levelAtlas.cut(4 * TILE_SCALE, 9 * TILE_SCALE, TILE_SCALE,
                TILE_SCALE), TILE_IN_GAME_SCALE, TileType.PIPE_SMALL_DOWN));

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
}
