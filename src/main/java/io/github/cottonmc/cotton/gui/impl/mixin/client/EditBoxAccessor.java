package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.WidgetSprites;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EditBox.class)
public interface EditBoxAccessor {
	@Accessor("SPRITES")
	static WidgetSprites libgui$getTextures() {
		throw new AssertionError();
	}
}
