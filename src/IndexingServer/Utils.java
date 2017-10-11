package IndexingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
	
	public static void log (String TAG, String logText){
		System.out.println(TAG+": "+logText);
	}

	public static String systemRead() throws IOException{
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
		String commandString=inputStream.readLine();
		return commandString;
	}
}
