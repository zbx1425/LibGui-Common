package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.font.DrawnTextConsumer;
import net.minecraft.text.Style;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Consumer;

@Mixin(DrawnTextConsumer.ClickHandler.class)
public interface DrawnTextConsumerClickHandlerAccessor {
	@Accessor("setStyleCallback")
	@Mutable
	void libgui$setSetStyleCallback(Consumer<Style> setStyleCallback);

	@Accessor("style")
	void libgui$setStyle(@Nullable Style style);
}
