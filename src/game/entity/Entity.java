package game.entity;

import IO.Input;
import game.level.Level;
import game.level.SolidTile;
import game.level.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

import static game.level.Level.TILE_IN_GAME_SCALE;
import static game.level.Level.TILE_SCALE;

public abstract class Entity {

    public final EntityType type;

    protected float			x;
    protected float			y;

    protected Entity(EntityType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public abstract void update(Input input, Level level);

    public abstract void render(Graphics2D g);

}