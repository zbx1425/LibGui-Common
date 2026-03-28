package io.github.cottonmc.cotton.gui.impl.mixin;

import net.minecraft.world.inventory.AbstractContainerMenu;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerMenu.class)
public interface AbstractContainerMenuAccessor {
	@Accessor("suppressRemoteUpdates")
	boolean libgui$getSuppressRemoteUpdates();
}
