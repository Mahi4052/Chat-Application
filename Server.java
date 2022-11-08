// package pack;
import java.net.*;
import java.io.*;
class Server {
    BufferedReader br;
    PrintWriter pw;

    ServerSocket server;//Server
    Socket socket;//Client

    public Server(){ //Constructor
        try{
            server = new ServerSocket(1234);
            System.out.println("server is ready to accept client connnection");
            socket=server.accept();//Server accepted the client request

            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));

            pw= new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void startReading() {
        Runnable r1 = () -> {

            try {
                while (true) {
                    String msg = br.readLine();
                    if (msg.equals("exit chat")) {
                        System.out.println("Client ended the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Mahesh: " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        };
        new Thread(r1).start();
    };


    public void startWriting(){
        Runnable r2=()->{

            try {
                while (!socket.isClosed()) {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();

                    pw.println(content);
                    pw.flush();
                    if (content.equals("exit chat")) {
                        System.out.println("Client ended the chat");
                        socket.close();
                        break;
                    }

                }
            }
                catch (Exception e){
                    e.printStackTrace();
                }
            };
            new Thread(r2).start();
        };

    public static void main(String[] args) {
        System.out.println("Server Online..");
        Server s=new Server();

    }
}
