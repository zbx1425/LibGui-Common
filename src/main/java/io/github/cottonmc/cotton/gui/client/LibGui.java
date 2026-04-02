package io.github.cottonmc.cotton.gui.client;

import io.github.cottonmc.cotton.gui.impl.client.LibGuiClient;
import io.github.cottonmc.cotton.gui.widget.WWidget;

/**
 * This class provides access to LibGui configuration and other global data.
 *
 * @since 4.0.0
 */
public final class LibGui {
	private LibGui() {
	}

	/**
	 * Returns whether LibGui is running in dark mode and widgets should use dark theming.
	 *
	 * @return true if widgets should use dark theming, false otherwise
	 */
	public static boolean isDarkMode() {
		return LibGuiClient.config.darkMode;
	}

	public static void tick() {
		for (WWidget widget : WidgetHudElement.tickingWidgets) {
			widget.tick();
		}
	}
}
