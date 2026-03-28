package io.github.cottonmc.cotton.gui.client;

import net.minecraft.world.inventory.ContainerData;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.ValidatedSlot;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.Vec2i;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A GuiDescription without any associated Minecraft classes
 */
public class LightweightGuiDescription implements GuiDescription {
	protected WPanel rootPanel = new WGridPanel().setInsets(Insets.ROOT_PANEL);
	protected @Nullable ContainerData propertyDelegate;
	protected @Nullable WWidget focus;

	protected int titleColor = WLabel.DEFAULT_TEXT_COLOR;
	protected int darkmodeTitleColor = WLabel.DEFAULT_DARKMODE_TEXT_COLOR;
	protected boolean fullscreen = false;
	protected boolean titleVisible = true;
	protected HorizontalAlignment titleAlignment = HorizontalAlignment.LEFT;
	private Vec2i titlePos = new Vec2i(8, 6);
	private boolean useDefaultRootBackground = true;
	private final List<FocusChangeListener> focusChangeListeners = new ArrayList<>();
	
	@Override
	public WPanel getRootPanel() {
		return rootPanel;
	}

	@Override
	public int getTitleColor() {
		return isDarkMode().orElse(LibGui.isDarkMode()) ? darkmodeTitleColor : titleColor;
	}

	@Override
	public GuiDescription setRootPanel(WPanel panel) {
		this.rootPanel = panel;
		return this;
	}

	@Override
	public GuiDescription setTitleColor(int color) {
		this.titleColor = color;
		this.darkmodeTitleColor = (color == WLabel.DEFAULT_TEXT_COLOR) ? WLabel.DEFAULT_DARKMODE_TEXT_COLOR : color;
		return this;
	}

	@Override
	public GuiDescription setTitleColor(int lightColor, int darkColor) {
		this.titleColor = lightColor;
		this.darkmodeTitleColor = darkColor;
		return this;
	}

	@Override
	public void addPainters() {
		if (!fullscreen && getUseDefaultRootBackground()) {
			this.rootPanel.setBackgroundPainter(BackgroundPainter.VANILLA);
		}
	}

	@Override
	public boolean getUseDefaultRootBackground() {
		return useDefaultRootBackground;
	}

	@Override
	public void setUseDefaultRootBackground(boolean useDefaultRootBackground) {
		this.useDefaultRootBackground = useDefaultRootBackground;
	}

	@Override
	public void addSlotPeer(ValidatedSlot slot) {
		//NO-OP
	}

	@Override
	public @Nullable ContainerData getContainerData() {
		return propertyDelegate;
	}

	@Override
	public GuiDescription setContainerData(ContainerData data) {
		this.propertyDelegate = data;
		return this;
	}

	@Override
	public boolean isFocused(WWidget widget) {
		return widget == focus;
	}

	@Override
	public @Nullable WWidget getFocus() {
		return focus;
	}

	@Override
	public void requestFocus(WWidget widget) {
		//TODO: Are there circumstances where focus can't be stolen?
		if (focus==widget) return; //Nothing happens if we're already focused
		if (!widget.canFocus()) return; //This is kind of a gotcha but needs to happen
		if (focus!=null) focus.onFocusLost();
		var oldFocus = focus;
		focus = widget;
		focus.onFocusGained();

		for (FocusChangeListener listener : focusChangeListeners) {
			listener.onFocusChanged(oldFocus, focus);
		}
	}

	@Override
	public void releaseFocus(WWidget widget) {
		if (focus==widget) {
			focus = null;
			widget.onFocusLost();

			for (FocusChangeListener listener : focusChangeListeners) {
				listener.onFocusChanged(widget, null);
			}
		}
	}

	@Override
	public boolean isFullscreen() {
		return fullscreen;
	}

	@Override
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	@Override
	public boolean isTitleVisible() {
		return titleVisible;
	}

	@Override
	public void setTitleVisible(boolean titleVisible) {
		this.titleVisible = titleVisible;
	}

	@Override
	public HorizontalAlignment getTitleAlignment() {
		return titleAlignment;
	}

	@Override
	public void setTitleAlignment(HorizontalAlignment titleAlignment) {
		this.titleAlignment = titleAlignment;
	}

	@Override
	public Vec2i getTitlePos() {
		return titlePos;
	}

	@Override
	public void setTitlePos(Vec2i titlePos) {
		this.titlePos = titlePos;
	}

	@Override
	public void addFocusChangeListener(FocusChangeListener listener) {
		focusChangeListeners.add(Objects.requireNonNull(listener, "Listener cannot be null"));
	}
}
