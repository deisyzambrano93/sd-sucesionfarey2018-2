package farey;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Deisy Zambrano
 */
class ReceiveClient extends Frame implements Runnable {

    MulticastSocket socket;
    JLabel exit;
    JTextArea title;
    DatagramPacket packet;
    byte data[] = new byte[100];
    String message;
    boolean stop = true;
    JList list = new JList();
    ArrayList array = new ArrayList();
    DefaultListModel model = new DefaultListModel();
    ArrayList address = new ArrayList();
    InetAddress IP;
    int enter, selected;

    public ReceiveClient() {
        try {
            socket = new MulticastSocket(9500);
        } catch (IOException ex) {
            System.out.println("Error Socket");
        }

        background.add(list);
        try {
            IP = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException ex) {
            System.out.println("Error IP");
        }

        title = new JTextArea();
        title.setBounds(15, 30, 300, 40);
        title.setOpaque(false);
        title.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setText("List of Servers: ");
        title.setEditable(false);
        title.setVisible(true);
        background.add(title);

        list.setVisible(true);
        list.setBounds(200, 90, 400, 200);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setBackground(Color.LIGHT_GRAY);

        exit = new JLabel("exit");
        exit.setBounds(720, 15, 64, 64);
        exit.setVisible(true);
        background.add(exit);
        exit.setIcon(exitIcon);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                System.exit(0);
            }
        });
    }

    @Override
    public void run() {
        while (stop) {
            data = new byte[100];
            packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                System.out.println("Error Receiving...");
            }
            message = new String(packet.getData());
            for (int i = 0; i < model.getSize(); i++) {
                if (model.get(i).equals(message)) {
                    enter = 1;
                }
            }
            if (enter == 1) {
                enter = 0;
            } else {
                model.addElement(message);
                String ip = "" + packet.getAddress();
                String[] separate = ip.split("/");
                array.add(separate[1]);

            }
            list.setModel(model);
            list.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getSource() == list) {
                        selected = list.getSelectionMode();
                        enter = 2;
                    }
                }
            });
            if (enter == 2) {
                stop = false;
                setVisible(false);
                System.out.println(array.get(selected));
                Client c = new Client("" + array.get(selected));

            }
            System.out.println("Receiving... " + message);
        }
    }
}

class Window extends Frame {

    JTextArea textArea, title;
    JScrollPane scroll;
    JLabel exit;

    public Window() {

        background.setIcon(backgroundIcon);

        title = new JTextArea();
        title.setBounds(15, 30, 300, 40);
        title.setOpaque(false);
        title.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setText("Calculate:");
        title.setEditable(false);
        title.setVisible(true);
        background.add(title);

        textArea = new JTextArea("Waiting for assignment...");
        textArea.setFont(new Font("Serif", Font.ITALIC, 12));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(200, 90, 400, 200);
        scroll.setVisible(true);
        background.add(scroll);

        exit = new JLabel("exit");
        exit.setBounds(720, 15, 64, 64);
        exit.setVisible(true);
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

public class Client {

    final DataInputStream input = null;
    final DataOutputStream output = null;
    String resultClient = "", printClient = "", printG = "";

    void calculate(int start, int end, String result, String print) {
        resultClient = result;
        printClient += print;
        for (int i = start; i <= end; i++) {
            if (!resultClient.contains("" + i)) {
                resultClient += i;
                if (i % 10 == 0) {
                    printClient += i + "\n";
                    printG += i + "\n";
                } else {
                    printClient += i + "-";
                    printG += i + "-";
                }
            }
        }
    }

    public Client(String ip) {
        Socket client;
        client = null;
        try {
            client = new Socket(ip, 4000);
            final DataInputStream input = new DataInputStream(client.getInputStream());
            final DataOutputStream output = new DataOutputStream(client.getOutputStream());
            Window win = new Window();
            String aux;
            while (true) {
                aux = input.readUTF();
                String[] data;
                data = aux.split(";");
                if (data.length == 4) {
                    System.out.println("arrive-> Start: " + data[0] + " End: " + data[1]);
                    calculate(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], data[3]);
                    win.textArea.setText(printG);
                    output.writeUTF(resultClient + ";" + printClient);
                } else {
                    System.out.println("arrive: " + aux + " size: " + data.length);
                }

            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Connection error client: " + e);
            System.exit(-1);
        }
    }
}
