package game.level;

public enum TileType {
    Empty(0),
    BRICK_BLOCK_BROWN(1),
    COIN_BLOCK(2),
    BUSH(3),
    MOUNTAIN_LEFT(4),
    MOUNTAIN_UP(5),
    MOUNTAIN_RIGHT(6),
    CLOUD(7),
    CLOUD_TRIPLE(8),
    PIPE_UP(9),
    PIPE_LEFT(10),
    PIPE_SMALL(11),
    GROUND_BROWN(12),
    GROUND_BLUE(13),
    CASTLE_RECT_HOLE(14),
    CASTLE_OVAL_HOLE(15),
    CASTLE_BRICK(16),
    GRANITE_BROWN(17),
    GRANITE_BLUE(18),
    COIN(19),
    PLATFORM_GRASS(20),
    BRICK_BLOCK_BLUE(21);


    private int n;


    TileType(int n) {
        this.n = n;
    }


    public static TileType fromNumeric(int n) {
        switch (n) {
            case 1:
                return BRICK_BLOCK_BROWN;
            case 2:
                return COIN_BLOCK;
            case 3:
                return BUSH;
            case 4:
                return MOUNTAIN_LEFT;
            case 5:
                return MOUNTAIN_UP;
            case 6:
                return MOUNTAIN_RIGHT;
            case 7:
                return CLOUD;
            case 8:
                return CLOUD_TRIPLE;
            case 9:
                return PIPE_UP;
            case 10:
                return PIPE_LEFT;
            case 11:
                return PIPE_SMALL;
            case 12:
                return GROUND_BROWN;
            case 13:
                return GROUND_BLUE;
            case 14:
                return CASTLE_RECT_HOLE;
            case 15:
                return CASTLE_OVAL_HOLE;
            case 16:
                return CASTLE_BRICK;
            case 17:
                return GRANITE_BROWN;
            case 18:
                return GRANITE_BLUE;
            case 19:
                return COIN;
            case 20:
                return PLATFORM_GRASS;
            case 21:
                return BRICK_BLOCK_BLUE;
            default:
                return Empty;


        }
    }


    public int numeric() {
        return n;
    }

}