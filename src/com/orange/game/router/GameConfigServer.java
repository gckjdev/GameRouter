package com.orange.game.router;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlConfiguration;

public class GameConfigServer {

	public static void main(String[] args) throws Exception {
		Resource fileserver_xml = Resource.newResource("etc/jetty.xml");
		XmlConfiguration configuration = new XmlConfiguration(
				fileserver_xml.getInputStream());
		Server server = (Server) configuration.configure();
		ContextHandlerCollection handlers = (ContextHandlerCollection) server
				.getHandler();
		ContextHandler apiHandler = new ContextHandler("/api");
		apiHandler.setHandler(new GameRouter());
		handlers.addHandler(apiHandler);
		server.start();
		server.join();
	}
}
