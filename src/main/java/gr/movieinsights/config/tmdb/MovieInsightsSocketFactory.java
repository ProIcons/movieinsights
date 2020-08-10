package gr.movieinsights.config.tmdb;

import gr.movieinsights.config.tmdb.util.AddressUtils;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieInsightsSocketFactory extends SocketFactory {

    private static MovieInsightsSocketFactory movieInsightsSocketFactory = new MovieInsightsSocketFactory();

    public static MovieInsightsSocketFactory getInstance() {
        return movieInsightsSocketFactory;
    }

    private final List<InetAddress> inetAddresses;
    private AtomicInteger inetAddressPointer = new AtomicInteger(0);

    public MovieInsightsSocketFactory(InetAddress inetAddress) {
        inetAddresses = new LinkedList<>();
        inetAddresses.add(inetAddress);
    }

    MovieInsightsSocketFactory() {
        List<InetAddress> inetAddresses;
        try {
            inetAddresses = AddressUtils.getPublicIPAddresses();
            if (inetAddresses.size() == 0) {
                inetAddresses = AddressUtils.getNetworkLocalIPAddress();
            }
        } catch (SocketException ignored) {
            inetAddresses = new ArrayList<>();
        }
        this.inetAddresses = inetAddresses;
    }


    public List<InetAddress> getAvailableAddresses() {
        return new ArrayList<>(inetAddresses);
    }

    @Override
    public Socket createSocket() throws IOException {
        synchronized (this) {
            if (inetAddressPointer.get() >= inetAddresses.size()) {
                inetAddressPointer.set(0);
            }
            Socket sock = getDefault().createSocket();
            sock.bind(new InetSocketAddress(inetAddresses.get(inetAddressPointer.getAndIncrement()), 0));
            return sock;
        }
    }

    @Override
    public Socket createSocket(String s, int i) throws IOException, UnknownHostException {
        return SocketFactory.getDefault().createSocket(s, i);
    }

    @Override
    public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException, UnknownHostException {
        return SocketFactory.getDefault().createSocket(s, i, inetAddress, i1);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return SocketFactory.getDefault().createSocket(inetAddress, i);
    }

    @Override
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
        return SocketFactory.getDefault().createSocket(inetAddress, i, inetAddress1, i1);
    }
}
