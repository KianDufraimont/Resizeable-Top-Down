
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player{
    private final BufferedImage playerPic;
    private int tileIndex = 0;
    private int x, y, width, height;
    private final int rows, columns;
    
    public Player(BufferedImage pic, int startingTile, int row, int column){
        playerPic = pic;
        tileIndex = startingTile;
        rows = row;
        columns = column;
        //setDrawInfo(0, 0, 100, 100);
    }
    
    public void draw(Graphics g){
        g.drawImage(playerPic, x, y, width, height, null);
    }

    public int getTileIndex(){
        return tileIndex;
    }

    /*public void addTileCount(int count){
        tileIndex += count;
    }*/

    public void setDrawInfo(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public enum Direction{
        UP,
        DOWN,
        LEFT,
        RIGHT
    };

    public void movePlayer(Direction dir){
        switch(dir){
            case UP -> {if (tileIndex - rows >= 0) tileIndex-=rows;}
            case DOWN -> { if (tileIndex + rows < rows*columns) tileIndex += rows; }
            case LEFT -> {if (tileIndex % rows != 0) tileIndex -= 1;}
            case RIGHT -> {if ((tileIndex + 1) % rows != 0) tileIndex += 1;}
        }
    }
}