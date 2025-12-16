package io.github.cottonmc.cotton.gui.impl.client;

import net.minecraft.client.font.DrawnTextConsumer;
import net.minecraft.client.font.TextRenderer;

import io.github.cottonmc.cotton.gui.impl.mixin.client.DrawnTextConsumerClickHandlerAccessor;

public class TextStyleCapturer extends DrawnTextConsumer.ClickHandler {
	public TextStyleCapturer(TextRenderer textRenderer, int clickX, int clickY) {
		super(textRenderer, clickX, clickY);
		var self = (DrawnTextConsumerClickHandlerAccessor) this;
		self.libgui$setSetStyleCallback(self::libgui$setStyle);
	}
}
