package sd.sucesionfarey2018.pkg2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author deisy
 */
public class Menu extends Frame {

    private JLabel server, client, exit;
    String name;
    URL serverUrl = this.getClass().getResource("images/server.png");
    ImageIcon serverIcon = new ImageIcon(serverUrl);
    URL clientUrl = this.getClass().getResource("images/client.png");
    ImageIcon clientIcon = new ImageIcon(clientUrl);

    public Menu() {
        this.setLayout(null);
        this.setVisible(true);

        server = new JLabel("server");
        server.setBounds(120, 60, 340, 130);
        server.setVisible(true);
        background.add(server);
        server.setIcon(serverIcon);

        server.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent ma) {
                name = "";
                name = JOptionPane.showInputDialog("Cantidad de clientes: ", "1");
                if (name != null) {
                    setVisible(false);
                    Server s = new Server(Integer.parseInt(name));
                    Thread t = new Thread(s);
                    t.start();
                }
            }
        });

        client = new JLabel("client");
        client.setBounds(120, 200, 340, 130);
        client.setVisible(true);
        background.add(client);
        client.setIcon(clientIcon);

        client.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent ma) {
                setVisible(false);
                receiveC c = new receiveC();
                Thread h = new Thread(c);
                h.start();
            }
        });

        exit = new JLabel("exit");
        exit.setBounds(530, 330, 65, 65);
        background.add(exit);
        exit.setIcon(exitIcon);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                System.exit(0);

            }
        });

    }
}
