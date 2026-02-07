package me.alphamode.beta.proxy.fabric;

import me.alphamode.beta.proxy.api.ProxyPlugin;
import me.alphamode.beta.proxy.networking.ClientConnection;

public class FabricPlugin implements ProxyPlugin {
    @Override
    public void onProxyStart() {
        IO.println("Fabric Proxy Plugin Started");
    }

    @Override
    public void onProxyConnection(ClientConnection connection) {
        IO.println("Fabric Proxy New Connection");
    }

    @Override
    public void onProxyClose() {
        IO.println("Fabric Proxy Plugin Closed");
    }
}
