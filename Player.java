
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player{
    private BufferedImage playerPic;
    private int tileIndex = 0;
    private int x, y, width, height;
    public Player(BufferedImage pic, int startingTile){
        playerPic = pic;
        tileIndex = startingTile;
        //setDrawInfo(0, 0, 100, 100);
    }
    
    public void draw(Graphics g){
        g.drawImage(playerPic, x, y, width, height, null);
    }

    public int getTileIndex(){
        return tileIndex;
    }

    public void addTileCount(int count){
        tileIndex += count;
    }

    public void setDrawInfo(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
}