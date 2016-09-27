/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package netprojclienttest;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Ranit
 */
public class NetProjClientTest {
    
    static Socket socket;
    static DataInputStream in;
    static DataOutputStream out;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        System.out.println("COnnecting....");
        socket = new Socket("localhost", 7777);
        System.out.println("COnnection Success");
        in = new DataInputStream(socket.getInputStream());
        //System.out.println("Receiving Information...");
        //String test = in.readUTF();
        //System.out.println("Msg From Server: " + test);
        out = new DataOutputStream(socket.getOutputStream());
        Input input = new Input(in);
        Thread thread = new Thread(input);
        thread.start();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name and then press enter: ");
        String clientName = sc.nextLine();
        out.writeUTF(clientName);
        while(true) {
            String sendmsg = sc.nextLine();
            out.writeUTF(sendmsg);
        }
    }
    
}

class Input implements Runnable {
    
    DataInputStream in;
    
    public Input(DataInputStream in) {
        this.in = in;
    }
    public void run() {
        while(true) {
            try {
                String msg = in.readUTF();
                System.out.println(msg);
            } catch (IOException ex) {
                Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
