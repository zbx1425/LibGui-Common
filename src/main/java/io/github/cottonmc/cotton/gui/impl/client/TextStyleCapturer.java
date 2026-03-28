package io.github.cottonmc.cotton.gui.impl.client;

import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.Font;

import io.github.cottonmc.cotton.gui.impl.mixin.client.ActiveTextCollectorClickableStyleFinderAccessor;

public class TextStyleCapturer extends ActiveTextCollector.ClickableStyleFinder {
	public TextStyleCapturer(Font textRenderer, int clickX, int clickY) {
		super(textRenderer, clickX, clickY);
		var self = (ActiveTextCollectorClickableStyleFinderAccessor) this;
		self.libgui$setStyleScanner(self::libgui$setResult);
	}
}
