import Network.WCQueryClient;


public class WCClientApp {

	public static void main(String[] args) {
		WCQueryClient client = new WCQueryClient();
		if(client.sendFile("truc", "machin.txt", "192.168.1.1", 1)) {
			System.out.println("Connection Success");
			
		} else {
			System.out.println("Connection Failure");
		}
	}

}
