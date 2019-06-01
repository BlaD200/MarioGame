package game.level;

public enum TileType {
    Empty(0),
    BRICK_BLOCK_BROWN(1),
    COIN_BLOCK(2),

    BUSH_CENTER(3),
    BUSH_LEFT(40),
    BUSH_RIGHT(41),

    MOUNTAIN_LEFT(4),
    MOUNTAIN_UP(5),
    MOUNTAIN_CENTER(22),
    MOUNTAIN_CENTER_STONE(23),
    MOUNTAIN_RIGHT(6),

    CLOUD_UP_LEFT(38),
    CLOUD_UP_CENTER(24),
    CLOUD_UP_RIGHT(39),
    CLOUD_LEFT(7),
    CLOUD_CENTER(25),
    CLOUD_RIGHT(26),

    PIPE_UP_RIGHT(9),
    PIPE_UP_LEFT(8),
    PIPE_CENTER_LEFT(30),
    PIPE_CENTER_RIGHT(31),

    PIPE_LEFT_UP(10),
    PIPE_LEFT_DOWN(32),
    PIPE_LEFT_CENTER_UP(33),
    PIPE_LEFT_CENTER_DOWN(34),
    PIPE_SMALL_UP(11),
    PIPE_SMALL_DOWN(35),

    GROUND_BROWN(12),
    GROUND_BLUE(13),
    CASTLE_RECT_HOLE(14),
    CASTLE_OVAL_HOLE(15),
    CASTLE_BRICK(16),
    GRANITE_BROWN(17),
    GRANITE_BLUE(18),
    COIN(19),

    PLATFORM_GRASS_LEFT(20),
    PLATFORM_GRASS_RIGHT(36),
    PLATFORM_GRASS_CENTER(37),

    BRICK_BLOCK_BLUE(21),

    CLOUD_TRIPLE_LEFT(27),
    CLOUD_TRIPLE_CENTER(28),
    CLOUD_TRIPLE_RIGHT(29);


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
                return BUSH_CENTER;
            case 4:
                return MOUNTAIN_LEFT;
            case 5:
                return MOUNTAIN_UP;
            case 6:
                return MOUNTAIN_RIGHT;
            case 7:
                return CLOUD_LEFT;
            case 8:
                return PIPE_UP_LEFT;
            case 9:
                return PIPE_UP_RIGHT;
            case 10:
                return PIPE_LEFT_UP;
            case 11:
                return PIPE_SMALL_UP;
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
                return PLATFORM_GRASS_LEFT;
            case 21:
                return BRICK_BLOCK_BLUE;
            case 22:
                return MOUNTAIN_CENTER;
            case 23:
                return MOUNTAIN_CENTER_STONE;
            case 24:
                return CLOUD_UP_CENTER;
            case 25:
                return CLOUD_CENTER;
            case 26:
                return CLOUD_RIGHT;
            case 27:
                return CLOUD_TRIPLE_LEFT;
            case 28:
                return CLOUD_TRIPLE_CENTER;
            case 29:
                return CLOUD_TRIPLE_RIGHT;
            case 30:
                return PIPE_CENTER_LEFT;
            case 31:
                return PIPE_CENTER_RIGHT;
            case 32:
                return PIPE_LEFT_DOWN;
            case 33:
                return PIPE_LEFT_CENTER_UP;
            case 34:
                return PIPE_LEFT_CENTER_DOWN;
            case 35:
                return PIPE_SMALL_DOWN;
            case 36:
                return PLATFORM_GRASS_RIGHT;
            case 37:
                return PLATFORM_GRASS_CENTER;
            case 38:
                return CLOUD_UP_LEFT;
            case 39:
                return CLOUD_UP_RIGHT;
            case 40:
                return BUSH_LEFT;
            case 41:
                return BUSH_RIGHT;
            default:
                return Empty;


        }
    }


    public int numeric() {
        return n;
    }

}