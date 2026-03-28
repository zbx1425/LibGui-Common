package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.AbstractButton;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractButton.class)
public interface AbstractButtonAccessor {
	@Accessor("SPRITES")
	static WidgetSprites libgui$getTextures() {
		throw new AssertionError();
	}
}
