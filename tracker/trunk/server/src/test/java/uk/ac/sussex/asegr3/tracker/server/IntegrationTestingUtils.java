package uk.ac.sussex.asegr3.tracker.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import javax.servlet.ServletContextListener;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerWrapper;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.LifeCycle.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.yammer.dropwizard.AbstractService;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.ConfigurationException;
import com.yammer.dropwizard.config.ConfigurationFactory;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.ServerFactory;
import com.yammer.dropwizard.validation.Validator;

public class IntegrationTestingUtils {
	
	private final static Logger LOG = LoggerFactory.getLogger(IntegrationTestingUtils.class);

	public static <T extends Configuration> Server startEmbeddedJetty(AbstractService<T> service, ServletContextListener listener) throws Exception{
		
		int[] openPorts = getPossiblyOpenPorts(2);
	    int port = openPorts[0];
	    int adminPort = openPorts[1];

	    File temporaryConfigFile = createTemporaryConfigFile(port, adminPort);

	    ConfigurationFactory<T> factory = ConfigurationFactory.forClass(
	        service.getConfigurationClass(), new Validator(), service.getJacksonModules());
	    T configuration = factory.build(temporaryConfigFile);

	    Environment environment = new Environment(service, configuration);
	    environment.addServletListeners(listener);
	    service.initializeWithBundles(configuration, environment);

	    Server server = new ServerFactory(configuration.getHttpConfiguration(),
	        service.getName()).buildServer(environment);
	   
	    setHandlersToFailIfUnavailable(server.getHandler());

	    LOG.info("Starting embedded Jetty with configuration {}", configuration.toString());
	    server.start();
	    
	    return server;
	}
	
	private static void setHandlersToFailIfUnavailable(Handler handler){
		
		if (handler instanceof HandlerCollection){
			for (Handler childHandler: ((HandlerCollection)handler).getHandlers()){
				setHandlersToFailIfUnavailable(childHandler);
			}
		}
    	if (handler instanceof ServletContextHandler){
    		ServletContextHandler servletContextHandler = (ServletContextHandler)handler;
    		servletContextHandler.getServletHandler().setStartWithUnavailable(false); // force server to fail startup if anything is wrong with it.
    	}
    	if (handler instanceof HandlerWrapper){
    		setHandlersToFailIfUnavailable(((HandlerWrapper)handler).getHandler());
    	}
	}
	
	/**
	   * This method tries to find a set of open ports by creating a bunch of
	   * server sockets.
	   * <p/>
	   * Note: This procedure is susceptible to a race condition and some
	   * of the returned ports may be in use
	   */
	  private static int[] getPossiblyOpenPorts(int number) throws IOException {
	    ServerSocket[] serverSockets = new ServerSocket[number];
	    try {
	      int[] ports = new int[number];
	      for (int i = 0; i < number; i++) {
	        serverSockets[i] = new ServerSocket(0 /* bind to any open port */);
	        ports[i] = serverSockets[i].getLocalPort();
	      }
	      return ports;

	    } finally {
	      for (ServerSocket socket : serverSockets) {
	        socket.close();
	      }
	    }
	  }
	  
	  public static String createConfigurationAsYaml() throws IOException {
		    return Resources.toString(Resources.getResource("config/test-jetty-config.yml"), Charsets.UTF_8);
		  }

		  private static File createTemporaryConfigFile(int port, int adminPort) throws IOException {
		    File temporaryConfig = File.createTempFile("dropwizard", ".yml");
		    temporaryConfig.deleteOnExit();

		    Files.write(createConfigurationAsYaml() + "\n" +
		        "http:\n" +
		        "  port: " + port + "\n" +
		        "  adminPort: " + adminPort + "\n",
		        temporaryConfig, Charsets.UTF_8);

		    return temporaryConfig;
		  }
}
