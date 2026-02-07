package me.alphamode.beta.proxy;

import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import me.alphamode.beta.proxy.config.Config;
import net.raphimc.netminecraft.netty.crypto.CryptUtil;

import java.net.Proxy;

public final class Main {
	static void main(final String[] args) {
		final HttpAuthenticationService authService = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
		new BrodernProxy(new Config(), CryptUtil.generateKeyPair(), authService.createMinecraftSessionService(), authService.createProfileRepository()).listen();
	}
}
