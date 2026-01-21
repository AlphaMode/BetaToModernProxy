package me.alphamode.beta.proxy.util;

public record Identifier(String namespace, String path) {
	public Identifier(String input) throws Exception {
		String namespace;
		String path;
		if (!input.contains(":")) {
			namespace = "minecraft";
			path = input;
		} else {
			final String[] parts = input.split(":", 1);
			if (parts.length < 2) {
				throw new Exception("Invalid identifier");
			} else {
				namespace = parts[0];
				path = parts[1];
			}
		}

		this(namespace, path);
	}

	public static Identifier vanilla(final String path) {
		return new Identifier("minecraft", path);
	}

	public static Identifier ofNullable(final String input) {
		try {
			return new Identifier(input);
		} catch (Exception exception) {
			return null;
		}
	}
}
