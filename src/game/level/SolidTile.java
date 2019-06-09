package game.level;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SolidTile extends Tile {
    private static final int borderThickness = 4;

    private Rectangle upR;
    private Rectangle rightR;
    private Rectangle leftR;
    private Rectangle downR;


    public SolidTile(BufferedImage image, int scale, TileType type) {
        super(image, scale, type);
    }


    public void update(int x, int y, int w, int h) {
        upR = new Rectangle(x + borderThickness, y, w - borderThickness*2, borderThickness*2);

        leftR = new Rectangle(x - borderThickness, y + borderThickness, borderThickness * 2, h - borderThickness * 2);

        rightR = new Rectangle(x + w - borderThickness, y + borderThickness, borderThickness * 2,
                h - borderThickness * 2);

        downR = new Rectangle(x + borderThickness * 2, y + h - borderThickness, w - borderThickness * 4,
                borderThickness * 2);
    }


    @Override
    protected void render(Graphics2D g, int x, int y) {
        super.render(g, x, y);
    }

    public void renderDebug(Graphics2D g){
        if (upR != null){
            g.setColor(Color.WHITE);
            g.draw(upR);
            g.draw(leftR);
            g.draw(rightR);
            g.draw(downR);
        }
    }

    public Rectangle[] getCollisionRect(){
        return new Rectangle[]{upR, leftR, downR, rightR};
    }

}
