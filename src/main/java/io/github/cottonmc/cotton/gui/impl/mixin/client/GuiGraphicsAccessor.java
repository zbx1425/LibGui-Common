package io.github.cottonmc.cotton.gui.impl.mixin.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessor {
	@Invoker("innerBlit")
	void libgui$callInnerBlit(RenderPipeline pipeline, Identifier sprite, int x1, int x2, int y1, int y2, float u1, float u2, float v1, float v2, int color);
}
