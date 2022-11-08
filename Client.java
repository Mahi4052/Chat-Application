// package pack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.*;

class Client extends JFrame{
    BufferedReader br;
    PrintWriter pw;
    Socket socket;

    public JLabel heading= new JLabel("Client Area");
    public JTextArea messageArea= new JTextArea();
    public JTextField messageInput= new JTextField();
    public Font font = new Font("Roboto",Font.PLAIN,20);
    public Client() {
        try {
            System.out.println("Sending request to server");
            try {
                socket = new Socket("127.0.0.1", 1234);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Connection Done");

            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            try {
                pw = new PrintWriter(socket.getOutputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            createGUI();
            handleEvents();
            startReading();
            startWriting();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("key released:"+e.getKeyCode());
                if(e.getKeyCode()==10){
                    //System.out.println("Entered Pressed");
                    String contentToSend=messageInput.getText();
                    messageArea.append("Me: "+contentToSend+"\n");
                    pw.println(contentToSend);
                    pw.flush();
                    messageInput.setText("");
                }
            }
        });
    }

    private void createGUI(){
        //gui
        this.setTitle("Client Messanger Platform");
        this.setSize(550,550);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //component coding
        heading.setFont(font);
        messageArea.setFont(font);
        messageInput.setFont(font);


        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        //frame layout
        this.setLayout(new BorderLayout());

        //adding components to frame
        this.add(heading,BorderLayout.NORTH);
        this.add(messageArea,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);

    }
    public void startReading() {
        Runnable r1 = () -> {


                try {
                    while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit chat")) {
                        System.out.println("Server ended the chat");
                        JOptionPane.showMessageDialog(this, "Server Terminated");
                        messageInput.setEnabled(false);
                        break;
                    }
                    //System.out.println("Server: " + msg);
                    messageArea.append("Karthik: " + msg+"\n");
                }
                }catch (IOException e) {
                    e.printStackTrace();
                }

        };
        new Thread(r1).start();
    }

    public void startWriting() {
        Runnable r2 = () -> {

                try {
                    while (!socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    pw.println(content);
                    pw.flush();

                }
                }catch (Exception e) {
                    e.printStackTrace();
                }


        };
        new Thread(r2).start();
    }
    public static void main(String args[]){

        System.out.println("Client Online..");
        Client cn= new Client();

    }
}
