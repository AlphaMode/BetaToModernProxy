package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.config.Config;

public final class Main {

	static void main(String[] args) {
        Config config = new Config();
        config.load();

        Proxy proxy = new Proxy(config);

		proxy.listen();
	}
}
