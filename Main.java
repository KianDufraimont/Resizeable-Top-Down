
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JPanel border = new JPanel();
        JPanel main = new World();
        border.add(main);
        border.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e){
                int min = border.getWidth() > border.getHeight() ? border.getHeight() : border.getWidth();
                main.setPreferredSize(new Dimension(min, min));
                border.revalidate();
                border.repaint();
            }
        });

        frame.add(border);
        frame.pack();
    }
}
