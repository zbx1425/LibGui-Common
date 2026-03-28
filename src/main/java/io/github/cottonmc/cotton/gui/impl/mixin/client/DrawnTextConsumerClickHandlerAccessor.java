package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.network.chat.Style;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Consumer;

@Mixin(ActiveTextCollector.ClickableStyleFinder.class)
public interface DrawnTextConsumerClickHandlerAccessor {
	@Accessor("styleScanner")
	@Mutable
	void libgui$setStyleScanner(Consumer<Style> setStyleCallback);

	@Accessor("result")
	void libgui$setResult(@Nullable Style style);
}
