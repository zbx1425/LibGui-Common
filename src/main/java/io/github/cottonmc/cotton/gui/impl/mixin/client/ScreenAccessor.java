package io.github.cottonmc.cotton.gui.impl.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;

import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Screen.class)
public interface ScreenAccessor {
	@Accessor("children")
	List<GuiEventListener> libgui$getChildren();

	@Invoker("defaultHandleGameClickEvent")
	static void libgui$handleClickEvent(ClickEvent clickEvent, Minecraft client, @Nullable Screen screenAfterRun) {
		throw new AssertionError("Untransformed mixin");
	}
}
