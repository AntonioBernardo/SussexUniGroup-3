package uk.ac.sussex.asegr3.tracker.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;

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

	private final static Logger LOG = LoggerFactory
			.getLogger(IntegrationTestingUtils.class);

	public static <T extends Configuration> Server startEmbeddedJetty(AbstractService<T> service, ServletContextListener listener) throws Exception {
		return startEmbeddedJetty(service, listener, getClasspathConfigFile("/config/test-config.yml"));
	}

	public static <T extends Configuration> Server startEmbeddedJetty(AbstractService<T> service, ServletContextListener listener, File testConfigFile) throws Exception {

		ConfigurationFactory<T> factory = ConfigurationFactory.forClass(service.getConfigurationClass(), new Validator(),
				service.getJacksonModules());
		T configuration = factory.build(testConfigFile);

		Environment environment = new Environment(service, configuration);
		if (listener != null){
			environment.addServletListeners(listener);
		}
		service.initializeWithBundles(configuration, environment);

		Server server = new ServerFactory(configuration.getHttpConfiguration(),
				service.getName()).buildServer(environment);

		setHandlersToFailIfUnavailable(server.getHandler());

		LOG.info("Starting embedded Jetty with configuration {}", configuration.toString());
		server.start();

		return server;
	}

	private static void setHandlersToFailIfUnavailable(Handler handler) {

		if (handler instanceof HandlerCollection) {
			for (Handler childHandler : ((HandlerCollection) handler)
					.getHandlers()) {
				setHandlersToFailIfUnavailable(childHandler);
			}
		}
		if (handler instanceof ServletContextHandler) {
			ServletContextHandler servletContextHandler = (ServletContextHandler) handler;
			servletContextHandler.getServletHandler().setStartWithUnavailable(
					false); // force server to fail startup if anything is wrong
							// with it.
		}
		if (handler instanceof HandlerWrapper) {
			setHandlersToFailIfUnavailable(((HandlerWrapper) handler)
					.getHandler());
		}
	}

	public static String createConfigurationAsYaml() throws IOException {
		return Resources.toString(
				Resources.getResource("config/test-jetty-config.yml"),
				Charsets.UTF_8);
	}

	private static File getClasspathConfigFile(String configFileName)
			throws IOException, URISyntaxException {
		URI configURI = IntegrationTestingUtils.class.getResource(
				configFileName).toURI();
		
		LOG.info("using config file at: "+configURI);
		
		File testConfig = new File(configURI);

		return testConfig;
	}
}
