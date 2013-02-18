package Network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class QueryHandler implements Runnable {
	
	private WeakReference<BufferedReader> mInput;
	private WeakReference<DataOutputStream> mOutput;
	
	public QueryHandler(Socket client) throws IOException {
		if(client != null) {
		mInput = new WeakReference<BufferedReader>(new BufferedReader(
				new InputStreamReader(client.getInputStream())));
		mOutput = new WeakReference<DataOutputStream>(new DataOutputStream(client
				.getOutputStream()));
		}
	}

	@Override
	public void run() {
		
		if(mInput == null
				|| mInput.get() == null
				|| mOutput == null
				|| mOutput.get() == null) {
			return;
		}
			
		String clientQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();
		boolean isSuccess = true;

		try {
			clientQuery = mInput.get().readLine();

			if (clientQuery != null
					&& clientQuery.equals(Protocol.CLIENT_SEND_FILE)) {

				// TODO Stop Infinite Loop with Max Arguments...
				for (;;) {
					clientQuery = mInput.get().readLine();
					if (clientQuery != null && !clientQuery.isEmpty()) {
						String[] line = clientQuery.split("\\"
								+ Protocol.COMMON_SEPARATOR);
						if (line.length != 2) {
							isSuccess = false;
							break;
						}
						aMap.put(line[0], line[1]);
					} else {
						// Let's start to read the file.
						break;
					}
				}
			} else {
				isSuccess = false;
			}

			if (isSuccess) {

				// TODO Read the file!!

				for (Map.Entry<String, String> e : aMap.entrySet()) {
					System.out.println(e.getKey() + " --> " + e.getValue());
				}

				mOutput.get().writeBytes(Protocol.MASTER_OK
						+ Protocol.COMMON_END_LINE);
			} else {
				mOutput.get().writeBytes(Protocol.MASTER_KO
						+ Protocol.COMMON_END_LINE);
			}
			// Closing and quitting this client.
			mOutput.get().flush();
			mOutput.get().close();
			mInput.get().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
