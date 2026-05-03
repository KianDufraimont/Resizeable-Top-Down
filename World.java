
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class World extends JPanel{
    private Player player, player2;

    public World(){
        super();
        this.setLayout(new GridLayout(10,10,1,1));
        try {
            BufferedImage spriteSheet = ImageIO.read(new File("basictiles.png"));
            BufferedImage tile1 = loadFromSpriteSheet(spriteSheet, 16, 0, 1);
            BufferedImage tile2 = loadFromSpriteSheet(spriteSheet, 16, 1, 2);

            for (int i = 0; i < 10; i++){
                for (int j = 0; j < 10; j++){
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
            player = new Player(playerPic,0,10,10);
            playerPic = sheet.getSubimage(16*7, 0, 16, 16);
            player2 = new Player(playerPic,99,10,10);

            keyListener();
            
            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e){
                    setPlayerLocation();
                    revalidate();
                    repaint();
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

        tileID = player2.getTileIndex();
        tile = getComponent(tileID);
        player2.setDrawInfo(tile.getX(),tile.getY(),tile.getWidth(),tile.getHeight());        
    }

    private BufferedImage loadFromSpriteSheet(BufferedImage spriteSheet, int width, int x, int y) throws IOException{
        return spriteSheet.getSubimage(width*x, width*y,width,width);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        player.draw(g);
        player2.draw(g);
    }

    /**
     * Makes a key listener and attaches player movement to different keys
     */
    private void keyListener(){
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch(key){
                    case KeyEvent.VK_UP -> player2.movePlayer(Player.Direction.UP);
                    case KeyEvent.VK_DOWN -> player2.movePlayer(Player.Direction.DOWN);
                    case KeyEvent.VK_LEFT -> player2.movePlayer(Player.Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> player2.movePlayer(Player.Direction.RIGHT);

                    case KeyEvent.VK_W -> player.movePlayer(Player.Direction.UP);
                    case KeyEvent.VK_S -> player.movePlayer(Player.Direction.DOWN);                    
                    case KeyEvent.VK_A -> player.movePlayer(Player.Direction.LEFT);
                    case KeyEvent.VK_D -> player.movePlayer(Player.Direction.RIGHT);
                }
                setPlayerLocation();
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
            
        });
    }

    //Work in Progress just to compare the differences between key listener and key bindings
    private void keyBindings(){
        InputMap inputMap = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = this.getActionMap();
        addKeyToActionMap(inputMap, actionMap, player, KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), Player.Direction.DOWN);
        addKeyToActionMap(inputMap, actionMap, player, KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), Player.Direction.UP);
        addKeyToActionMap(inputMap, actionMap, player, KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), Player.Direction.LEFT);
        addKeyToActionMap(inputMap, actionMap, player, KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), Player.Direction.RIGHT);

        addKeyToActionMap(inputMap, actionMap, player2, KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), Player.Direction.DOWN);
        addKeyToActionMap(inputMap, actionMap, player2, KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), Player.Direction.UP);
        addKeyToActionMap(inputMap, actionMap, player2, KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), Player.Direction.LEFT);
        addKeyToActionMap(inputMap, actionMap, player2, KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), Player.Direction.RIGHT);

    }

    private void addKeyToActionMap(InputMap input, ActionMap action, Player pl, KeyStroke ks, Player.Direction dir){
        Action ac = new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                pl.movePlayer(dir);
                setPlayerLocation();
                repaint();
            }
            
        };
        System.out.println(ks.toString());
        input.put(ks, ks.toString());
        action.put(ks.toString(), ac);
    }
}
