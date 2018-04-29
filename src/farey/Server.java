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
import javax.swing.JFormattedTextField;
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

    JTextField textfield, textfieldC;
    JTextArea textArea, textArea2, ClientD, End, Tit1, Tit2;
    JComboBox combobox;
    JLabel calculate, exit, start;
    JScrollPane scroll, scroll2;
    String result= "", print = "", resultFinal = "", printFinal = "", resultE = "", printE = "";
    static int port = 4000;
    int quantity;
    ArrayList<thread> th = new ArrayList<>();

    public Server() {
        this.setLayout(null);
        this.setVisible(true);

        URL urlN = this.getClass().getResource("imagenes/fondo2.jpg");
        ImageIcon iconoN = new ImageIcon(urlN);
        background.setIcon(iconoN);

        URL urlS = this.getClass().getResource("imagenes/boton-verde.gif");
        ImageIcon iconBoton = new ImageIcon(urlS);

        URL urlC = this.getClass().getResource("imagenes/boton-naranja.gif");
        ImageIcon iconBotonC = new ImageIcon(urlC);

        textArea = new JTextArea();
        textArea.setFont(new Font("Serif", Font.ITALIC, 12));
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(10, 60, 580, 100);
        scroll.setVisible(false);
        background.add(scroll);

        textArea2 = new JTextArea();
        textArea2.setFont(new Font("Serif", Font.ITALIC, 12));
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        textArea2.setEditable(false);
        scroll2 = new JScrollPane(textArea2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.setBounds(10, 220, 580, 100);
        scroll2.setVisible(false);
        background.add(scroll2);

        Tit1 = new JTextArea();
        Tit1.setBounds(10, 10, 300, 40);
        Tit1.setOpaque(false);
        Tit1.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        Tit1.setSelectedTextColor(Color.CYAN);
        Tit1.setForeground(Color.WHITE);
        Tit1.setText("Calculo Interno");
        Tit1.setEditable(false);
        Tit1.setVisible(false);
        background.add(Tit1);

        Tit2 = new JTextArea();
        Tit2.setBounds(10, 180, 300, 40);
        Tit2.setOpaque(false);
        Tit2.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        Tit2.setSelectedTextColor(Color.CYAN);
        Tit2.setForeground(Color.WHITE);
        Tit2.setText("Resultado Final");
        Tit2.setEditable(false);
        Tit2.setVisible(false);
        background.add(Tit2);

        End = new JTextArea();
        End.setBounds(10, 10, 300, 40);
        End.setOpaque(false);
        End.setFont(new Font("Serif", Font.PLAIN + Font.BOLD, 30));
        End.setSelectedTextColor(Color.CYAN);
        End.setForeground(Color.WHITE);
        End.setText("Esperando.......");
        End.setEditable(false);
        End.setVisible(true);
        background.add(End);

        textfield = new JTextField(new Integer(100));
        textfield.setBounds(10, 60, 200, 20);
        textfield.setVisible(false);
        background.add(textfield);

        calculate = new JLabel("calculate");
        calculate.setBounds(120, 180, 340, 130);
        calculate.setVisible(false);
        background.add(calculate);
        calculate.setIcon(iconBoton);

        calculate.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                Integer valor = Integer.parseInt(textfield.getText());
                Tit1.setVisible(true);
                Tit2.setVisible(true);
                scroll.setVisible(true);
                scroll2.setVisible(true);
                End.setVisible(false);
                textfield.setVisible(false);
                calculate.setVisible(false);

                ArrayList<Integer> valores = new ArrayList<>();

                int calc = quantity + 1;
                int porc = valor / calc;
                System.out.println(porc);
                if (valor % calc != 0) {
                    System.out.println("sobra " + valor % calc);
                    valores.add(valor % calc + porc);
                    for (int i = 1; i < quantity + 1; i++) {
                        valores.add(valores.get(i - 1) + porc);
                    }
                } else {
                    valores.add(porc);
                    for (int i = 1; i < quantity + 1; i++) {
                        valores.add(valores.get(i - 1) + porc);
                    }
                }
                porc = porc / 2;
                //50
                //25
                //1-50 +25
                //51-100
                for (int i = quantity - 1; i >= 0; i--) {
                    valores.set(i, valores.get(i) + porc);
                    System.out.println("Valores " + i + ": " + valores.get(i));
                }

                /*
                //para mirar los puntos de partida de cada parte
                for (int i = 0; i < cant+1; i++) {
                    System.out.println(valores.get(i));
                    
                }
                 */
                calcular(1, valores.get(0), 0);
                System.out.println("Servidor le toca iniciar en  1 y terminar en " + valores.get(0));
                textArea.setText(printE);
                resultFinal = result;
                printFinal = print;

                for (int i = 1; i < quantity + 1; i++) {
                    //calcular(valores.get(i-1)+1,valores.get(i),RFinal);
                    try {
                        System.out.println("Pc " + i + " le toca iniciar en  " + (valores.get(i - 1) + 1) + " y terminar en " + valores.get(i));
                        if (th.get(i - 1) == null) {
                            System.out.println("hilo perdido");
                        }
                        th.get(i - 1).output.writeUTF((valores.get(i - 1) + 1) + ";" + valores.get(i) + ";" + resultFinal + ";" + printFinal);
                        String aux = "";
                        while ("".equals(aux)) {
                            aux = th.get(i - 1).input.readUTF();
                            String[] data;
                            data = aux.split(";");
                            System.out.println("Respondio la Pc " + i);
                            resultFinal = data[0];
                            printFinal = data[1];
                        }
                    } catch (IOException ex) {
                        System.out.println("Cliente " + i + " no responde");
                        JOptionPane.showMessageDialog(null, "Cliente " + i + " no responde se hara el calculo del mismo");
                        calcular(valores.get(i - 1) + 1, valores.get(i), i);
                        textArea.setText(resultE);
                    }
                }
                textArea2.setText(printFinal);
            }
        });

        exit = new JLabel("Exit");
        exit.setBounds(530, 330, 65, 65);
        exit.setVisible(true);
        background.add(exit);
        //exit.setIcon(exit);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                System.exit(0);
            }
        });
    }

    void calcular(int inicio, int fin, int c) {
        result = resultFinal;
        print = printFinal;
        if (c == 0) {
            printE += "Calculo del servidor\n";
        } else {
            printE += "Calculo correspondiente al cliente " + c + "\n";
        }
        for (int i = inicio; i <= fin; i++) {
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

    Server m = new Server();

    @Override
    public void run() {
        m.quantity = clients;
        try {
            ServerSocket serverSocket;
            @SuppressWarnings("UnusedAssignment")
            Socket client = null;

            serverSocket = new ServerSocket(m.port);
            System.out.println("servidor listo");
            System.out.println("Esperando " + clients + " clientes....");
            sendServer s = new sendServer();
            s.start();

            for (int i = 0; i < clients; i++) {
                client = serverSocket.accept();
                m.th.add(new thread(client, m));
                m.th.get(i).nThread = i;
                System.out.println("acepto el cliente " + i);
            }
            s.stop();

            System.out.println("termino");
            m.End.setText("¿ Calcular hasta ?");
            m.textfield.setVisible(true);
            m.calculate.setVisible(true);
            for (int i = 0; i < clients; i++) {
                m.th.get(i).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
