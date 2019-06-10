package game.entity;

import IO.Input;
import game.Game;
import game.level.Level;
import graphics.Sprite;

import java.awt.*;

public class Enemy extends Walker {
    private Sprite sprite;
    private float offset;
    private boolean directionLeft = true;

    public Enemy(EntityType type, Sprite sprite, float x, float y, float w, float h, float speed, float gravity) {
        super(type, x, y, w, h, speed);
        this.sprite = sprite;
        this.gravity = gravity;
        this.gravityEnabled = true;
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
        if (x > -50 && x < Game.width) {
            float newX = x;
            float newY = y;

            float speed = this.speed;

            if (!leftClear || x < 0) {
                directionLeft = false;
            } else if (!rightClear) {
                directionLeft = true;
            }

            if (directionLeft)
                newX -= speed;
            else
                newX += speed;

            if (gravityEnabled)
                newY += gravity;

            x = newX;
            y = newY;
        }

    }


    public void render(Graphics2D g) {
        super.render(g);
        sprite.render(g, x, y);
    }
}
