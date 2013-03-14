package Network.FromServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

import Exceptions.InvalidResultFMException;
import Model.ResultFM;
import Network.Protocols.ProtocolResultFM;
import System.Config;
import System.Utils;

public class ResultAnalyzer {

	private Socket mSocket;

	public ResultAnalyzer(Socket client) {
		mSocket = client;
	}

	public ResultFM handleResult() {

		ResultFM result = null;
		String slaveQuery;
		HashMap<String, String> aMap = new HashMap<String, String>();

		try {
			BufferedReader mInput = new BufferedReader(new InputStreamReader(
					mSocket.getInputStream()));
			slaveQuery = mInput.readLine();

			if (slaveQuery != null
					&& slaveQuery.equals(ProtocolResultFM.MASTER_SEND_RESULT)) {
				boolean isSuccess = true;
				// Go through argument.
				for (int i = 0; i < ProtocolResultFM.MASTER_MAX_ARG_NUMBER; i++) {
					slaveQuery = mInput.readLine();
					System.out.println("## " + slaveQuery);
					if (slaveQuery != null && !slaveQuery.isEmpty()) {
						String[] line = slaveQuery.split("\\"
								+ ProtocolResultFM.COMMON_SEPARATOR);

						// If it doesn't fit the protocol.
						if (line.length != 2) {
							isSuccess = false;
							break;
						}

						// Otherwise Get the arguments.
						aMap.put(line[0], line[1]);
					} else {
						// Finish! Let's start to read the file. see below.
						break;
					}
				}

				if (isSuccess) {
					result = new ResultFM(aMap);
					if (result.getResultSize() != 0) {

						File f = Utils.CreateFile(result.getResultPath());
						if (f == null) {
							result = null;
						} else {
							int filesize = result.getResultSize() + 1;
							char[] content = new char[Config.NETWORK_BUFFER_SIZE];

							f.delete();

							BufferedWriter out = new BufferedWriter(
									new FileWriter(f));

							int count = 0;
							while ((count = mInput.read(content)) > 0
									&& filesize > 0) {
								out.write(content, 0, count);
								filesize -= count;
							}

							out.flush();
							out.close();
						}
					} else {
						result = null;
					}
				} else {
					result = null;
				}
			} else {
				result = null;
			}

			// Closing and quitting this client.
			mSocket.shutdownInput();

		} catch (IOException e) {
			// e.printStackTrace();
			result = null;
		} catch (InvalidResultFMException e) {
			// e.printStackTrace();
			result = null;
		}
		return result;
	}

}
