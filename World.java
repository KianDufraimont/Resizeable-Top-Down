
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class World extends JPanel{
    private Player player;

    public World(){
        super();
        this.setLayout(new GridLayout(8,8,1,1));
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("basictiles.png"));
            BufferedImage tile1 = loadFromSpriteSheet(spriteSheet, 16, 7, 1);
            BufferedImage tile2 = loadFromSpriteSheet(spriteSheet, 16, 7, 2);

            for (int i = 0; i < 8; i++){
                for (int j = 0; j < 8; j++){
                    Tile tile;
                    int index = i*8 + j;
                    if (i % 2 == 0 && j % 2 == 0) tile = new Tile(tile1, index);
                    else if (i % 2 == 1 && j % 2 == 0) tile = new Tile(tile2, index);
                    else if (i % 2 == 0 && j % 2 == 1) tile = new Tile(tile2, index);
                    else tile = new Tile(tile1, index);
                    this.add(tile);        
                }
            }

            BufferedImage sheet = ImageIO.read(new File("characters.png"));
            BufferedImage playerPic = sheet.getSubimage(16*1, 0, 16, 16);
            player = new Player(playerPic);
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e){
                    setPlayerLocation();
                }
            });
            this.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    switch(key){
                        case KeyEvent.VK_DOWN:
                        case KeyEvent.VK_S:
                            if (player.getTileIndex() + 8 < 64) player.addTileCount(8);
                            break;
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_W:
                            if (player.getTileIndex() - 8 >= 0) player.addTileCount(-8);
                            break;
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_A:
                            if (player.getTileIndex() - 1 >= 0) player.addTileCount(-1);
                            break;
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_D:
                            if (player.getTileIndex() + 1 < 64) player.addTileCount(1);
                            break;
                    }
                    setPlayerLocation();
                    repaint();
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNotify(){
        super.addNotify();
        requestFocus();
    }

    private void setPlayerLocation(){
        int tileID = player.getTileIndex();
        Component tile = getComponent(tileID);
        //System.out.println(((Tile)tile).getIndex());
        //System.out.println(String.format("X: %d, Y: %d, wid: %d, hei: %d", tile.getX(),tile.getY(),tile.getWidth(),tile.getHeight()));
        player.setDrawInfo(tile.getX(),tile.getY(),tile.getWidth(),tile.getHeight());
    }

    private BufferedImage loadFromSpriteSheet(BufferedImage spriteSheet, int width, int x, int y) throws IOException{
        return spriteSheet.getSubimage(width*x, width*y,width,width);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        player.draw(g);
    }
}
