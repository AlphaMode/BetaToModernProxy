package me.alphamode.beta.proxy.api;

import me.alphamode.beta.proxy.config.Config;
import net.lenni0451.mcstructs.text.TextComponent;

public interface ProxyAPI {

	void setBrand(String brand);

	String getBrand();

	void setMessage(TextComponent description);

	TextComponent getMessage();

	Config getConfig();
}
