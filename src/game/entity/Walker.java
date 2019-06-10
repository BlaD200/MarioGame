package game.entity;

import IO.Input;
import display.Display;
import game.Game;
import game.level.Level;
import game.level.SolidTile;
import game.level.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import static game.level.Level.TILE_IN_GAME_SCALE;
import static game.level.Level.TILE_SCALE;

public abstract class Walker extends Entity {

    protected float speed;
    protected float gravity;
    protected float width;
    protected float height;

    protected boolean gravityEnabled;
    protected boolean rightClear = true;
    protected boolean leftClear = true;
    protected boolean upClear = true;

    ArrayList<SolidTile> solidTilesPhysics = new ArrayList<>();
    Rectangle thisUpLeft;
    Rectangle thisDownRight;


    protected Walker(EntityType type, float x, float y, float w, float h, float speed) {
        super(type, x, y);
        this.speed = speed;
        this.width = w;
        this.height = h;
    }


    public void setGravityEnabled(boolean enabled) {
        this.gravityEnabled = enabled;
    }


    public void setRightClear(boolean rightClear) {
        this.rightClear = rightClear;
    }


    public void setLeftClear(boolean leftClear) {
        this.leftClear = leftClear;
    }


    public void setUpClear(boolean upClear) {
        this.upClear = upClear;
    }


    public float getWidth() {
        return width;
    }


    public float getHeight() {
        return height;
    }


    @Override
    public void update(Input input, Level level) {
        physicsUpdate(level);
    }


    private void physicsUpdate(Level level) {
        solidTilesPhysics.clear();
        Map<TileType, SolidTile> solidTiles = level.getSolidTiles();
        int[][] tileMap = level.getTileMap();

        float offsetX = Level.getOffsetX();
        float x = getX() - Level.getOffsetX();
        float y = getY();

        int tileX = ((int) (x / (TILE_SCALE * TILE_IN_GAME_SCALE)) * TILE_SCALE * TILE_IN_GAME_SCALE);
        int tileY = (int) ((y) / (TILE_SCALE * TILE_IN_GAME_SCALE)) * TILE_SCALE * TILE_IN_GAME_SCALE;
        int tileW = TILE_SCALE * TILE_IN_GAME_SCALE;
        int tileH = TILE_SCALE * TILE_IN_GAME_SCALE;

        int scale = TILE_IN_GAME_SCALE * TILE_SCALE;
        int xIndex = tileX / scale;
        int yIndex = tileY / scale;

        for (int i = xIndex - 1; i <= xIndex + 2; i++) {
            for (int j = yIndex - 1; j <= yIndex + 2; j++) {
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

        xIndex = (int) getX();
        yIndex = (int) getY();
        int w = (int) getWidth();
        int h = (int) getHeight();

        thisUpLeft = new Rectangle(xIndex, yIndex, w, h);
        thisDownRight = new Rectangle(xIndex, yIndex + 2, w, h);

        boolean bottomClear = true;
        boolean leftClear = true;
        boolean upClear = true;
        boolean rightClear = true;

        for (SolidTile solidTilesPhysic : solidTilesPhysics) {
            Rectangle[] rectangles = solidTilesPhysic.getCollisionRect();

            if (thisDownRight.intersects(rectangles[0]) && bottomClear) {
                bottomClear = false;
                setGravityEnabled(false);
            }
            if (thisUpLeft.intersects(rectangles[1]) && rightClear) {
                if (solidTilesPhysic.getType() == TileType.BAR || solidTilesPhysic.getType() == TileType.PIPE_LEFT_DOWN) {
                    Display.victory();
                    Game.sleep(3000);
                    Display.dispose();
                    Display.gameStop();

                }
                rightClear = false;
                setRightClear(false);
            }
            if (thisUpLeft.intersects(rectangles[2]) && upClear) {
                if (solidTilesPhysic.getType() == TileType.COIN_BLOCK) {
                    System.out.println("Coin");
                }
                upClear = false;
                setUpClear(false);
            }
            if (thisUpLeft.intersects(rectangles[3]) && leftClear) {
                leftClear = false;
                setLeftClear(false);
            }

        }

        if (bottomClear)
            setGravityEnabled(true);
        if (rightClear)
            setRightClear(true);
        if (upClear)
            setUpClear(true);
        if (leftClear)
            setLeftClear(true);
    }


    @Override
    public void render(Graphics2D g) {
        if (Game.IS_DEBUG) {
            for (SolidTile solidTilesPhysic : solidTilesPhysics) {
                solidTilesPhysic.renderDebug(g);
            }
            if (thisDownRight != null) {
                g.setColor(Color.RED);
                g.draw(thisDownRight);
                g.draw(thisUpLeft);
                g.setColor(Color.WHITE);
            }
        }
    }
}
