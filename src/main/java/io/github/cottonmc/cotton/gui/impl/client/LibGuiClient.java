package io.github.cottonmc.cotton.gui.impl.client;

import io.github.cottonmc.cotton.gui.impl.Proxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LibGuiClient {
	public static final Logger logger = LogManager.getLogger();
	public static volatile LibGuiConfig config = new LibGuiConfig();

	static {
		Proxy.proxy = new ClientProxy();
	}

}
