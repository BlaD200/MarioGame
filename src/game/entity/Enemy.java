package game.entity;

import IO.Input;
import game.level.Level;
import graphics.Sprite;

import java.awt.*;

public class Enemy extends Walker {
    private Sprite sprite;
    private float offset;

    public Enemy(EntityType type, Sprite sprite, float x, float y, float w, float h, float speed) {
        super(type, x, y, w, h, speed);
        this.sprite = sprite;
    }


    @Override
    public void update(Input input, Level level) {
        super.update(input, level);
        if (offset == 0f)
            offset = Level.getOffsetX();

        float offset = Level.getOffsetX();
        if (offset != this.offset) {
            x = x - this.offset + offset;
            this.offset = offset;
        }
    }


    public void render(Graphics2D g) {
        super.render(g);
        sprite.render(g, x, y);
    }
}
