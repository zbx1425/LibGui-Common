package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.ClickEvent;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Screen.class)
public interface ScreenAccessor {
	@Accessor("children")
	List<Element> libgui$getChildren();

	@Invoker("handleClickEvent")
	static void libgui$handleClickEvent(ClickEvent clickEvent, MinecraftClient client, @Nullable Screen screenAfterRun) {
		throw new AssertionError("Untransformed mixin");
	}
}
