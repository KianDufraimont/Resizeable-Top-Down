
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Tile extends JLabel{
    private BufferedImage image;
    private int number;
    public Tile(BufferedImage pic, int index){
        super(new ImageIcon(pic));
        number = index;
        image = pic;
        this.setPreferredSize(new Dimension(80,80));
        Tile temp = this;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                temp.setIcon(new ImageIcon(image.getScaledInstance(temp.getWidth(), temp.getHeight(), Image.SCALE_SMOOTH)));
                super.componentResized(e);
            }
        });
    }

    public int getIndex(){
        return number;
    }
    
}
