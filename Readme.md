# Chat-Application
This project focuses on developing a client-server chat application that leverages the Swing and AWT frameworks for the graphical user interface (GUI) and utilizes socket programming for seamless communication between the client and server.

# Client:

The client is a Java application represented by the Client class.
It connects to a server using a socket on IP address "127.0.0.1" and port 1234.
The client has a GUI interface using Swing, which includes a message input field, a message display area, and a label for the heading.
Messages are sent to the server when the user presses "Enter" in the message input field. The client also displays its own messages.
The client can receive messages from the server and displays them in the message display area.
It can exit the chat by typing "exit chat."

# Server:

The server is represented by the Server class.
It listens on port 1234 and waits for client connections.
Once a client connects, it can exchange messages with the client.
The server can also exit the chat by typing "exit chat."
The server and client communicate using sockets for both input and output.
