package farey;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Deisy Zambrano
 */
public class Menu extends Frame {

    private JLabel server, client, exit;
    String cantClients;
    URL serverUrl = this.getClass().getResource("images/server.png");
    ImageIcon serverIcon = new ImageIcon(serverUrl);
    URL clientUrl = this.getClass().getResource("images/client.png");
    ImageIcon clientIcon = new ImageIcon(clientUrl);

    public Menu() {
        this.setLayout(null);
        this.setVisible(true);

        server = new JLabel("server");
        server.setBounds(75, 120, 96, 128);
        server.setVisible(true);
        background.add(server);
        server.setIcon(serverIcon);

        server.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent ma) {
                cantClients = "";
                cantClients = JOptionPane.showInputDialog(
                    server, 
                    "Ingrese la cantidad de clientes:", 
                    "Cantidad de clientes", 
                    JOptionPane.QUESTION_MESSAGE
                );
                if (cantClients != null) {
                    setVisible(false);
                    /*Server s = new Server(Integer.parseInt(name));
                    Thread t = new Thread(s);
                    t.start();*/
                }
            }
        });

        client = new JLabel("client");
        client.setBounds(625, 120, 96, 128);
        client.setVisible(true);
        background.add(client);
        client.setIcon(clientIcon);

        client.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent ma) {
                setVisible(false);
                /*receiveC c = new receiveC();
                Thread h = new Thread(c);
                h.start();*/
            }
        });

        exit = new JLabel("exit");
        exit.setBounds(720, 15, 64, 64);
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