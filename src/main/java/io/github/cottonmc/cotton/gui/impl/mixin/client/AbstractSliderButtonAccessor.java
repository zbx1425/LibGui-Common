package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.resources.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractSliderButton.class)
public interface AbstractSliderButtonAccessor {
	@Accessor("SLIDER_SPRITE")
	static Identifier libgui$getTexture() {
		throw new AssertionError();
	}

	@Accessor("SLIDER_HANDLE_SPRITE")
	static Identifier libgui$getHandleTexture() {
		throw new AssertionError();
	}

	@Accessor("SLIDER_HANDLE_HIGHLIGHTED_SPRITE")
	static Identifier libgui$getHandleHighlightedTexture() {
		throw new AssertionError();
	}
}
