package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.config.Config;

public final class Main {
	static void main(String[] args) {
		new BrodernProxy(new Config()).listen();
	}
}
