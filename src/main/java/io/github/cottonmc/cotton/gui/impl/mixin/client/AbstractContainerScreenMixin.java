package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
abstract class AbstractContainerScreenMixin {
	@Inject(
			method = "extractContents",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/screens/Screen;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V",
					shift = At.Shift.AFTER
			),
			allow = 1
	)
	private void onSuperRender(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta, CallbackInfo info) {
		if ((Object) this instanceof CottonInventoryScreen<?> cottonInventoryScreen) {
			cottonInventoryScreen.paintDescription(context, mouseX, mouseY, delta);
		}
	}
}
