package studia.articles.bot.searcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import studia.articles.bot.controller.ConnectionException;

public abstract class IeeConnector {

	private InetSocketAddress addr;
	private Proxy proxy;

	private boolean throughProxy = false;
	public IeeConnector(String socksAddress, int socksPort, int throughPort) {
		addr = new InetSocketAddress(socksAddress, socksPort).createUnresolved(
				"localhost", throughPort);
		proxy = new Proxy(Proxy.Type.SOCKS, addr);
		CookieHandler.setDefault(new CookieManager());
		throughProxy = true;
	}
	
	public IeeConnector(){
		throughProxy = false;
		CookieHandler.setDefault(new CookieManager());
	}

	protected InputStream getInputStream(String urlStr, boolean throughProxy)
			throws ConnectionException {
		try {
			URL url = new URL(urlStr);
			URLConnection conn = null;
			if (throughProxy && this.throughProxy)
				conn = url.openConnection(proxy);
			else
				conn = url.openConnection();
			conn.setConnectTimeout(100000);
			conn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
			System.out.println(url);
			conn.connect();
			return conn.getInputStream();
		} catch (IOException e) {
			throw new ConnectionException(e);
		}
	}
}
