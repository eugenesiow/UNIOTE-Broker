package uk.ac.soton.ldanalytics.uniote.broker;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

public class Broker {
	public static void main(String[] a) {
		//  Prepare our context and sockets
        Context context = ZMQ.context(1);

        //  For publishers
        Socket frontend =  context.socket(ZMQ.XSUB);
        frontend.bind("tcp://localhost:5500");

        //  For subscribers
        Socket backend  = context.socket(ZMQ.XPUB);
        backend.bind("tcp://localhost:5600");

        //  Run the proxy until the user interrupts us
        ZMQ.proxy (frontend, backend, null);

        frontend.close();
        backend.close();
        context.term();
	}
}
