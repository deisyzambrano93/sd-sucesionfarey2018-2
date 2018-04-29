package sd.sucesionfarey2018.pkg2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.*;

public class Frame extends JFrame {

    URL backgroundUrl = this.getClass().getResource("images/background.png");
    URL exitUrl = this.getClass().getResource("images/exit.png");
    ImageIcon backgroundIcon = new ImageIcon(backgroundUrl);
    ImageIcon exitIcon = new ImageIcon(exitUrl);
    JLabel background = new JLabel();

    public Frame() {
        Dimension SizeWindows = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(SizeWindows.width / 3, SizeWindows.height / 10, 600, 400);
        this.add(background);
        background.setIcon(backgroundIcon);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
