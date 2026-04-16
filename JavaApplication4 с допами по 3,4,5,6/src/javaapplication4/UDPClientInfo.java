package javaapplication4;

import java.net.InetAddress;
import java.util.Objects;

public class UDPClientInfo {
    private final InetAddress address;
    private final int port;

    public UDPClientInfo(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return address.getHostAddress() + ":" + port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UDPClientInfo)) return false;
        UDPClientInfo that = (UDPClientInfo) o;
        return port == that.port && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port);
    }
}