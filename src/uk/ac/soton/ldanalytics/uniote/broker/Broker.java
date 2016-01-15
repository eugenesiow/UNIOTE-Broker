package uk.ac.soton.ldanalytics.uniote.broker;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Broker {
	public static void main(String[] a) {
		//  Prepare our context and sockets
        Context context = ZMQ.context(1);

        String clientAddress = "";
        try {
			clientAddress = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
        
        //  For publishers
        Socket frontend = context.socket(ZMQ.XSUB);
        frontend.bind("tcp://"+clientAddress+":5500");

        //  For subscribers
        Socket backend  = context.socket(ZMQ.XPUB);
        backend.bind("tcp://"+clientAddress+":5600");

        //  Run the proxy until the user interrupts us
        ZMQ.proxy (frontend, backend, null);

        frontend.close();
        backend.close();
        context.term();
	}
}
