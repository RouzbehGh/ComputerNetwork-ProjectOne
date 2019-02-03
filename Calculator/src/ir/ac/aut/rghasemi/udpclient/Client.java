package ir.ac.aut.rghasemi.udpclient;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Roozbeh Ghasemi on 12/13/2018.
 */

public class Client {
	private static DatagramSocket dsocket;
	private static Scanner scanner;
	public static void main(String[] args) {
		scanner = new Scanner(System.in);		
		try {
			dsocket = new DatagramSocket();
			InetAddress ip_local = InetAddress.getLocalHost();			
			byte buffer[] = null;
			while(true) {
				System.out.println("Digits input format: 'operator operand1 operand2'");
				String input = scanner.nextLine();
				buffer = new byte[65535];
				buffer = input.getBytes();
				DatagramPacket packet_send = new DatagramPacket(buffer, buffer.length, ip_local, 3000);
				long t1 = System.currentTimeMillis();
				dsocket.send(packet_send);
				StringTokenizer token = new StringTokenizer(input.trim());
				int i = 0;
				String aux_input = null;
				while(i <= 2) {
					if(i == 2) {
						aux_input = token.nextToken();
						break;
					}
					token.nextToken();
					i++;
				}
				if(input.equals("exit")) {
					System.out.println("Bye Bye");
					break;
				}
				buffer = new byte[65535];
				DatagramPacket packet_receive = new DatagramPacket(buffer, buffer.length);
				dsocket.receive(packet_receive);
				long t2 = System.currentTimeMillis();
				long t3 = t2 - t1;
				System.out.println("Calculation time "+ t3 +" (ms)"+ " Result: " + new String(buffer, 0, buffer.length)+"\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
