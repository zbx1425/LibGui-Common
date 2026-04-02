package io.github.cottonmc.cotton.gui.impl;

import net.minecraft.resources.Identifier;

public final class LibGuiCommon {
	public static final String MOD_ID = "libgui_common";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

}
