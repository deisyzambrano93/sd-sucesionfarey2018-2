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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Deisy Zambrano
 */
class sendServer extends Thread {

    MulticastSocket socket;
    DatagramPacket packet;
    byte data[] = new byte[100];
    String message = "Server_Deisy";
    InetAddress IP;
    boolean stop = true;

    public sendServer() {
        try {
            IP = InetAddress.getByName("255.255.255.255");
        } catch (UnknownHostException ex) {
            System.out.println("Error IP");
        }
        try {
            socket = new MulticastSocket();
        } catch (IOException ex) {
            System.out.println("Error Socket");
        }
    }

    @Override
    public void run() {
        while (stop) {
            data = message.getBytes();
            packet = new DatagramPacket(data, data.length, IP, 9500);
            try {
                socket.send(packet);
                System.out.println("Sending... " + message);
            } catch (IOException ex) {
                System.out.println("Error Send");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Error Sleep");
            }
        }
    }
}

class thread extends Thread {

    Socket client;
    int var = 0, nThread;
    String color;
    Server Server;
    DataInputStream input = null;
    DataOutputStream output = null;

    public thread(Socket Client, Server server) {
        try {
            client = Client;
            Server = server;
            input = new DataInputStream(client.getInputStream());
            output = new DataOutputStream(client.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(thread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @SuppressWarnings({"static-access", "null"})
    public void run() {
        try {
            System.out.println("sending..... ");
            output.writeUTF("testing connection");
        } catch (IOException ex) {
            Logger.getLogger(thread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

public class Server extends Frame {

    JTextField inputNumber, textfieldC;
    JTextArea assignmentArea, resultArea, ClientD, infoClients, assignmentTitle, resultTitle;
    JComboBox combobox;
    JLabel calculate, exit, start;
    JScrollPane assignmentScroll, resultScroll;
    String result = "", print = "", resultFinal = "", printFinal = "", resultE = "", printE = "";
    static int port = 4000;
    int quantity;
    ArrayList<thread> th = new ArrayList<>();

    public Server() {
        this.setLayout(null);
        this.setVisible(true);

        background.setIcon(backgroundIcon);

        URL calculateUrl = this.getClass().getResource("images/calculate.png");
        ImageIcon calculateIcon = new ImageIcon(calculateUrl);

        assignmentArea = new JTextArea();
        assignmentArea.setFont(new Font("Serif", Font.ITALIC, 12));
        assignmentArea.setLineWrap(true);
        assignmentArea.setEditable(false);
        assignmentArea.setWrapStyleWord(true);
        assignmentScroll = new JScrollPane(assignmentArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        assignmentScroll.setBounds(25, 70, 300, 250);
        assignmentScroll.setVisible(false);
        background.add(assignmentScroll);

        resultArea = new JTextArea();
        resultArea.setFont(new Font("Serif", Font.ITALIC, 12));
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setEditable(false);
        resultScroll = new JScrollPane(resultArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        resultScroll.setBounds(400, 70, 300, 250);
        resultScroll.setVisible(false);
        background.add(resultScroll);

        assignmentTitle = new JTextArea();
        assignmentTitle.setBounds(25, 20, 300, 40);
        assignmentTitle.setOpaque(false);
        assignmentTitle.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        assignmentTitle.setSelectedTextColor(Color.CYAN);
        assignmentTitle.setForeground(Color.BLACK);
        assignmentTitle.setText("Assignment");
        assignmentTitle.setEditable(false);
        assignmentTitle.setVisible(false);
        background.add(assignmentTitle);

        resultTitle = new JTextArea();
        resultTitle.setBounds(400, 20, 300, 40);
        resultTitle.setOpaque(false);
        resultTitle.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        resultTitle.setSelectedTextColor(Color.CYAN);
        resultTitle.setForeground(Color.BLACK);
        resultTitle.setText("Result");
        resultTitle.setEditable(false);
        resultTitle.setVisible(false);
        background.add(resultTitle);

        infoClients = new JTextArea();
        infoClients.setBounds(15, 15, 380, 40);
        infoClients.setOpaque(false);
        infoClients.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        infoClients.setForeground(Color.BLACK);
        infoClients.setText("Waiting For Clients...");
        infoClients.setEditable(false);
        infoClients.setVisible(true);
        background.add(infoClients);

        inputNumber = new JTextField(new Integer(100));
        inputNumber.setBounds(15, 180, 200, 20);
        inputNumber.setVisible(false);
        background.add(inputNumber);

        calculate = new JLabel("calcular");
        calculate.setBounds(625, 120, 96, 128);
        calculate.setVisible(false);
        background.add(calculate);
        calculate.setIcon(calculateIcon);

        calculate.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {

                if ((inputNumber.getText() != null) && (Integer.parseInt(inputNumber.getText()) > 0)) {
                    Integer value = Integer.parseInt(inputNumber.getText());

                    assignmentTitle.setVisible(true);
                    resultTitle.setVisible(true);
                    assignmentScroll.setVisible(true);
                    resultScroll.setVisible(true);
                    infoClients.setVisible(false);
                    inputNumber.setVisible(false);
                    calculate.setVisible(false);

                    ArrayList<Integer> results = new ArrayList<>();

                    int calc = quantity + 1;
                    int porc = value / calc;
                    System.out.println(porc);
                    if (value % calc != 0) {
                        System.out.println("excess " + value % calc);
                        results.add(value % calc + porc);
                        for (int i = 1; i < quantity + 1; i++) {
                            results.add(results.get(i - 1) + porc);
                        }
                    } else {
                        results.add(porc);
                        for (int i = 1; i < quantity + 1; i++) {
                            results.add(results.get(i - 1) + porc);
                        }
                    }
                    porc = porc / 2;
                    //50
                    //25
                    //1-50 +25
                    //51-100
                    for (int i = quantity - 1; i >= 0; i--) {
                        results.set(i, results.get(i) + porc);
                        System.out.println("Results " + i + ": " + results.get(i));
                    }

                    /*
                //para mirar los puntos de partida de cada parte
                for (int i = 0; i < cant+1; i++) {
                    System.out.println(valores.get(i));
                    
                }
                     */
                    calculate(1, results.get(0), 0);
                    System.out.println("Server start in 1 and finish in " + results.get(0));
                    assignmentArea.setText(printE);
                    resultFinal = result;
                    printFinal = print;

                    for (int i = 1; i < quantity + 1; i++) {
                        //calcular(valores.get(i-1)+1,valores.get(i),RFinal);
                        try {
                            System.out.println("Client " + i + " , start in  " + (results.get(i - 1) + 1) + " and finish in " + results.get(i));
                            if (th.get(i - 1) == null) {
                                System.out.println("Thread not found");
                            }
                            th.get(i - 1).output.writeUTF((results.get(i - 1) + 1) + ";" + results.get(i) + ";" + resultFinal + ";" + printFinal);
                            String aux = "";
                            while ("".equals(aux)) {
                                aux = th.get(i - 1).input.readUTF();
                                String[] data;
                                data = aux.split(";");
                                System.out.println("Responded the client " + i);
                                resultFinal = data[0];
                                printFinal = data[1];
                            }
                        } catch (IOException ex) {
                            System.out.println("Client " + i + " does not respond");
                            JOptionPane.showMessageDialog(null, "Client " + i + " does not respond, the server will do the calculation");
                            calculate(results.get(i - 1) + 1, results.get(i), i);
                            assignmentArea.setText(resultE);
                        }
                    }
                    resultArea.setText(printFinal);
                }
            }
        });

        exit = new JLabel("Exit");
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

    void calculate(int start, int end, int c) {
        result = resultFinal;
        print = printFinal;
        if (c == 0) {
            printE += "";
        } else {
            printE += "Calculate of the client " + c + "\n";
        }
        for (int i = start; i <= end; i++) {
            if (!result.contains("" + i)) {
                result += i;
                if (i % 10 == 0) {
                    print += i + "\n";
                    printE += i + "\n";
                } else {
                    print += i + "-";
                    printE += i + "-";
                }
            }
        }
        resultFinal = result;
        printFinal = print;
    }
}

class serverSocket implements Runnable {

    int clients;

    public serverSocket(int clients) {
        this.clients = clients;
    }

    Server server = new Server();

    @Override
    public void run() {
        server.quantity = clients;
        try {
            ServerSocket serverSocket;
            @SuppressWarnings("UnusedAssignment")
            Socket client = null;

            serverSocket = new ServerSocket(server.port);
            System.out.println("Server started");
            System.out.println("Waiting " + clients + " clients...");
            sendServer s = new sendServer();
            s.start();

            for (int i = 0; i < clients; i++) {
                client = serverSocket.accept();
                server.th.add(new thread(client, server));
                server.th.get(i).nThread = i;
                System.out.println("Accept the client " + i);
            }
            s.stop();

            System.out.println("finished");
            server.infoClients.setText("Limit Number");
            server.infoClients.setBounds(15, 140, 380, 40);
            server.inputNumber.setVisible(true);
            server.calculate.setVisible(true);
            for (int i = 0; i < clients; i++) {
                server.th.get(i).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
