package ir.ac.aut.rghasemi.udpserver;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;


/**
 * Created by Roozbeh Ghasemi on 12/13/2018.
 */

public class Server {
	private static DatagramSocket dsocket;
	public static void main(String[] args) {
		try {
			dsocket = new DatagramSocket(3000);
			byte[] buffer = null;
			DatagramPacket packet_receive = null;
			DatagramPacket packet_send = null;
			DatagramPacket send_time = null;
			while(true) {
				buffer = new byte[65535];
				packet_receive = new DatagramPacket(buffer, buffer.length);
				dsocket.receive(packet_receive);
				String string_receive = new String(buffer, 0, buffer.length);
				string_receive = string_receive.trim();
				System.out.println("Equação recebida: " + string_receive);
				if(string_receive.equals("exit")) {
					System.out.println("Servidor encerrado !!!");
					break;
				}
				double result = 0;
				StringTokenizer tokens = new StringTokenizer(string_receive);
				String operation = tokens.nextToken();
				int first_operand = Integer.parseInt(tokens.nextToken());
				int second_operand = Integer.parseInt(tokens.nextToken());
				double radians = Math.toRadians(first_operand);
				switch (operation){
					case "+" :
						result = first_operand + second_operand;
						break;
					case "-" :
						result = first_operand - second_operand;
						break;
					case "*" :
						result = first_operand * second_operand;
						break;
					case "/" :
						result = first_operand / second_operand;
						break;
					case "tan" :
						double tanValue = Math.tan(radians);
						result =  tanValue;
						break;
					case "sin" :
						double sinValue = Math.sin(radians);
						result = sinValue;
						break;
					case "cot" :
						double cosValue1 = Math.cos(radians);
						double sinValue1 = Math.sin(radians);
						result = (cosValue1 / sinValue1);
						break;
					case "cos" :
						double cosValue = Math.cos(radians);
						result = cosValue;
						break;
				}
				System.out.println("Calculation Response:");
				String res = Double.toString(result);
				buffer = res.getBytes();
				int port = packet_receive.getPort();
				packet_send = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), port);
				dsocket.send(packet_send);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
