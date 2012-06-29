package org.zkforge.zktodo2.jetty;

import static java.lang.System.out;

import java.io.IOException;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * A class to do a clean shutdown of a jetty server which has jmx management
 * interfaces enabled.
 * 
 * @author simbo
 */
public class StopJettyJmx {

	enum Paramaeters {
		NAME_JETTY_JMX("org.eclipse.jetty.jmx:name=rmiconnectorserver")
		, NAME_JETTY_SERVER("org.eclipse.jetty.server:type=server,id=0")
		, NAME_JETTY_MAVEN("org.mortbay.jetty.plugin:type=jettyserver,id=0");
		final String val;
		Paramaeters(String val){
			this.val = val;
		}
	}
	enum Commands {
		COMMAND_STOP("stop");
		final String val;
		Commands(String val){
			this.val = val;
		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			usage();
			System.exit(-1);
		}

		final String type = args[0];
		out.println("type: " + type);
		final String jmxUrl = args[1];
		out.println("jmxUrl: " + jmxUrl);

		try {
			JMXConnector jmxc = JMXConnectorFactory.connect(new JMXServiceURL(
					jmxUrl), null);
			MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

			if (type.equalsIgnoreCase(Paramaeters.NAME_JETTY_SERVER.name())) {
				invoke(mbsc, Paramaeters.NAME_JETTY_SERVER, Commands.COMMAND_STOP);
				invoke(mbsc, Paramaeters.NAME_JETTY_JMX, Commands.COMMAND_STOP);
			} else if( type.equalsIgnoreCase(Paramaeters.NAME_JETTY_MAVEN.name())){
				invoke(mbsc, Paramaeters.NAME_JETTY_MAVEN, Commands.COMMAND_STOP);
			} else {
				throw new IllegalArgumentException(String.format("I don't understand '%s' please use %s or %s", type, Paramaeters.NAME_JETTY_SERVER.name(), Paramaeters.NAME_JETTY_MAVEN.name()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	protected static void invoke(MBeanServerConnection mbsc, Paramaeters object,
			Commands command) throws MalformedObjectNameException,
			InstanceNotFoundException, MBeanException, ReflectionException,
			IOException {
		ObjectName jettyServer = new ObjectName(object.val);
		mbsc.invoke(jettyServer, command.val, null, null);
	}

	private static void usage() {
		out.println("usage:");
		out.println("\tjava " + StopJettyJmx.class.getCanonicalName()
				+ " [NAME_JETTY_SERVER|NAME_JETTY_MAVEN] JMX_URL");
		out.println("where JMX_URL is shown on the jetty startup log");
		out.println("e.g. java "
				+ StopJettyJmx.class.getCanonicalName()
				+ " NAME_JETTY_SERVER service:jmx:rmi://Simon-Masseys-Mac-mini-2.local:1099/jndi/rmi://localhost:1099/jmxrmi");
		out.println("N.B If you want to be able to make a remote connnection you need to start the server with -Dcom.sun.management.jmxremote.* options.");
	}
}
