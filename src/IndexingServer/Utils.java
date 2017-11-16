package IndexingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Utils {
	
	public static void log (String TAG, String logText){
		System.out.println(TAG+": "+logText);
	}

	public static String systemRead() throws IOException{
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(System.in));
		String commandString=inputStream.readLine();
		return commandString;
	}

	public static InetAddress inetAddress;

	public static NetworkInterface gettNetworkAdapter(String name) {
		String address = null;
		Enumeration<NetworkInterface> enumeration = null;
		NetworkInterface networkInterface;

		try {
			enumeration = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException se) {
			se.printStackTrace();
			return null;
		}

		while(enumeration.hasMoreElements()) {
			networkInterface = enumeration.nextElement();
			String interfaceName = networkInterface.getName();
			if(interfaceName.toUpperCase().startsWith(name.toUpperCase())) {
				Enumeration<InetAddress> enumIpAddr = networkInterface.getInetAddresses();
				while(enumIpAddr.hasMoreElements()) {
					inetAddress = enumIpAddr.nextElement();
					address = new String(inetAddress.getHostAddress().toString());
					log("ip: ", address);
					if(address != null & address.length() > 0 && inetAddress instanceof Inet4Address) {
						return networkInterface;
					}
				}
			}
		}
		return null;
	}

	public static NetworkInterface getWlanAdapter() {
		NetworkInterface networkInterface = gettNetworkAdapter("en0");
		if(null == networkInterface || (!networkInterface.getInetAddresses().hasMoreElements())) {
			return gettNetworkAdapter("wlan");
		} else {
			return networkInterface;
		}
	}

	public static InetAddress getLocalIp() {
		getWlanAdapter();
		return inetAddress;
	}
}
