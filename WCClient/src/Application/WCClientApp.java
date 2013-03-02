package Application;
import Network.ToMaster.WCQueryClient;


public class WCClientApp {

	public static void main(String[] args) {
		WCQueryClient client = new WCQueryClient();
		
		System.out.println("Starting Client");
		
		if(client.connect()) {
			System.out.println("Client is Connected to Master");
		} else {
			System.out.println("Unable to Connect");
		}
		
		/*
		if(client.checkAvailability()) {
			System.out.println("Master is Available");
			
		} else {
			System.out.println("Master is not Available");
		}
		*/
		
		if(client.sendFile("truc", "machin.txt", "192.168.1.1", 1)) {
			System.out.println("File Sent with Success");
			
		} else {
			System.out.println("File not sent");
		}
		
		client.disconnect();
		
		System.out.println("Closing Client");
		
	}

}