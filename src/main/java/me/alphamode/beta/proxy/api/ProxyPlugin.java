package me.alphamode.beta.proxy.api;

import me.alphamode.beta.proxy.networking.ClientConnection;

// TODO
public interface ProxyPlugin {
	void onProxyStart();

	void onProxyConnection(final ClientConnection connection);

	void onProxyClose();
}
