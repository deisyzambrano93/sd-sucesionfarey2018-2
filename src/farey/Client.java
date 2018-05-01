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
    JList listServers = new JList();
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

        background.add(listServers);
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

        listServers.setVisible(true);
        listServers.setBounds(200, 90, 400, 200);
        listServers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listServers.setBackground(Color.LIGHT_GRAY);

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
            listServers.setModel(model);
            listServers.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getSource() == listServers) {
                        selected = listServers.getSelectionMode();
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

    JTextArea result, title;
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

        result = new JTextArea("Waiting for assignment...");
        result.setFont(new Font("Serif", Font.ITALIC, 12));
        result.setLineWrap(true);
        result.setEditable(false);
        result.setWrapStyleWord(true);
        result.setBackground(Color.WHITE);
        scroll = new JScrollPane(result, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
    ArrayList<Farey> results = new ArrayList<>();

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
                if (data.length == 3) {
                    System.out.println("Receive-> Result: " + data[0] + " Task: " + data[1] + " Value: " + data[2]);
                    if(Integer.parseInt(data[1]) == 0){
                        tasks(Integer.parseInt(data[1]), Integer.parseInt(data[2]), 0, data[0]);
                    }
                    
                    win.result.setText(resultClient);
                    output.writeUTF(resultClient);
                } else {
                    System.out.println("Receive: " + aux + " Size: " + data.length);
                }

            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Connection error client: " + e);
            System.exit(-1);
        }
    }

    public void tasks(int number, int finish, int clientId, String result) {
        resultClient = result;

        switch (number) {
            case 0:
                agregateFractionInitial();
                break;
            case 1:
                calculateCombination(finish);
                break;
            case 2:
                agregateFractionFinal();
                break;
            case 3:
                orderByFractions();
                break;
        }
    }

    public void agregateFractionInitial() {
        Farey fStart = new Farey(0, 1);
        results.add(fStart);
        resultClient += 0 + "/" + 1 + ",";
    }

    public void calculateCombination(int finish) {
        for (int i = 1; i <= finish; i++) {
            for (int j = 1; j <= finish; j++) {
                if (i < j) {
                    Farey f = new Farey(i, j);
                    boolean flag = false;
                    for (int k = 0; k < results.size() - 1; k++) {
                        if (results.get(k).getValue() == f.getValue()) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        results.add(f);
                        resultClient += f.getNum() + "/" + f.getDen() + ",";
                    }
                }
            }
        }
    }

    public void agregateFractionFinal() {
        Farey fFinish = new Farey(1, 1);
        results.add(fFinish);
        resultClient += 1 + "/" + 1;
    }

    public void orderByFractions() {
        for (int i = 0; i < results.size() - 1; i++) {
            for (int j = i + 1; j < results.size() - 1; j++) {
                if (((double) results.get(i).getNum() / (double) results.get(i).getDen()) > ((double) results.get(j).getNum() / (double) results.get(j).getDen())) {
                    Farey tmp = results.get(j);
                    results.set(j, results.get(i));
                    results.set(i, tmp);
                }
            }
        }
        resultClient += "Final: " + results.toString();
    }
}
