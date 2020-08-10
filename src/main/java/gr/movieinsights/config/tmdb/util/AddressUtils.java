package gr.movieinsights.config.tmdb.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Predicate;

public class AddressUtils {
    private static Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static boolean isIPAddressConnectableToInternet(InetAddress inetAddress, String checkAccessibilitySite) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Duration.ofMillis(200));
        builder.socketFactory(new SocketFactory() {
            @Override
            public Socket createSocket() throws IOException {
                Socket socket = SocketFactory.getDefault().createSocket();
                socket.bind(new InetSocketAddress(inetAddress, 0));
                return socket;
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
        });
        OkHttpClient okHttpClient = builder.build();
        try {
            Call call = okHttpClient.newCall(new Request.Builder().url(checkAccessibilitySite).head().build());
            Response response = call.execute();
            boolean state = response.isSuccessful();
            if (response.body() != null) {
                response.body().close();
            }
            response.close();
            log.debug("Accessibility for Address: {} is {}", inetAddress, state);
            return state;
        } catch (IOException e) {
            log.debug("Accessibility for Address: {} is {}", inetAddress, e.getMessage());
            return false;
        }

    }

    public static List<InetAddress> getMachineIPAddresses() throws SocketException {
        return getMachineIPAddresses(null);
    }

    public static List<InetAddress> getPublicIPAddresses() throws SocketException {
        return getInterfaceIPAddresses(
            addr ->
                !addr.isLinkLocalAddress()
                    && !addr.isLoopbackAddress()
                    && !addr.isAnyLocalAddress()
                    && !addr.isSiteLocalAddress()
        );
    }

    public static List<InetAddress> getNetworkLocalIPAddress() throws SocketException {
        return getInterfaceIPAddresses(InetAddress::isSiteLocalAddress);
    }

    public static List<InetAddress> getInterfaceIPAddresses(Predicate<InetAddress> inetAddressPredicate) throws SocketException {
        List<InetAddress> addrList = new ArrayList<>();
        Enumeration<NetworkInterface> enumNI = NetworkInterface.getNetworkInterfaces();
        while (enumNI.hasMoreElements()) {
            NetworkInterface ifc = enumNI.nextElement();
            if (ifc.isUp()) {
                Enumeration<InetAddress> enumAdds = ifc.getInetAddresses();
                while (enumAdds.hasMoreElements()) {
                    InetAddress addr = enumAdds.nextElement();
                    if (inetAddressPredicate.test(addr)) {
                        addrList.add(addr);
                    }
                }
            }
        }
        return addrList;
    }


    public static List<InetAddress> getMachineIPAddresses(String checkAccessibilitySite) throws SocketException {


        List<InetAddress> addrList = new ArrayList<>();
        List<InetAddress> lAddrList = new ArrayList<>();
        Enumeration<NetworkInterface> enumNI = NetworkInterface.getNetworkInterfaces();
        while (enumNI.hasMoreElements()) {
            NetworkInterface ifc = enumNI.nextElement();
            if (ifc.isUp()) {
                Enumeration<InetAddress> enumAdds = ifc.getInetAddresses();
                while (enumAdds.hasMoreElements()) {
                    InetAddress addr = enumAdds.nextElement();
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && !addr.isAnyLocalAddress() && addr.isSiteLocalAddress()) {
                        if (checkAccessibilitySite == null || isIPAddressConnectableToInternet(addr, checkAccessibilitySite)) {
                            lAddrList.add(addr);
                        }
                    }
                    if (!addr.isLinkLocalAddress() && !addr.isLoopbackAddress() && !addr.isAnyLocalAddress() && !addr.isSiteLocalAddress()) {

                        addrList.add(addr);
                    }
                }
            }
        }
        if (addrList.size() == 0) {
            addrList.addAll(lAddrList);
        }
        return addrList.size() > 0 ? addrList : lAddrList;
    }
}
