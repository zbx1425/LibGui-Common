package io.github.cottonmc.cotton.gui.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.impl.VisualLogger;
import io.github.cottonmc.cotton.gui.impl.client.CottonScreenImpl;
import io.github.cottonmc.cotton.gui.impl.client.FocusElements;
import io.github.cottonmc.cotton.gui.impl.client.MouseInputHandler;
import io.github.cottonmc.cotton.gui.impl.client.NarrationHelper;
import io.github.cottonmc.cotton.gui.impl.mixin.client.ScreenAccessor;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WWidget;
import io.github.cottonmc.cotton.gui.widget.data.InputResult;
import org.jspecify.annotations.Nullable;

public class CottonClientScreen extends Screen implements CottonScreenImpl {
	protected final GuiDescription description;
	protected int left = 0;
	protected int top = 0;

	/**
	 * The X coordinate of the screen title.
	 * This is relative to the root panel's top-left corner.
	 *
	 * @since 2.0.0
	 */
	protected int titleX;

	/**
	 * The Y coordinate of the screen title.
	 * This is relative to the root panel's top-left corner.
	 *
	 * @since 2.0.0
	 */
	protected int titleY;

	protected @Nullable WWidget lastResponder = null;

	private final MouseInputHandler<CottonClientScreen> mouseInputHandler = new MouseInputHandler<>(this);

	public CottonClientScreen(GuiDescription description) {
		this(CommonComponents.EMPTY, description);
	}

	public CottonClientScreen(Component title, GuiDescription description) {
		super(title);
		this.description = description;
		description.getRootPanel().validate(description);
		description.addFocusChangeListener((from, to) -> {
			boolean fromTextInput = from != null && from.canFocusForTextInput();
			boolean toTextInput = to != null && to.canFocusForTextInput();

			if (from != to && (fromTextInput || toTextInput)) {
				minecraft.onTextInputFocusChange(this, toTextInput);
			}
		});
	}

	@Override
	public GuiDescription getDescription() {
		return description;
	}

	@Override
	public void init() {
		super.init();

		WPanel root = description.getRootPanel();
		root.addPainters();
		description.addPainters();
		reposition(width, height);

		GuiEventListener rootPanelElement = FocusElements.ofPanel(root);
		((ScreenAccessor) this).libgui$getChildren().add(rootPanelElement);
		setInitialFocus(rootPanelElement);
	}

	@Override
	public void removed() {
		super.removed();
		VisualLogger.reset();
	}

	@Override
	public @Nullable WWidget getLastResponder() {
		return lastResponder;
	}

	@Override
	public void setLastResponder(@Nullable WWidget lastResponder) {
		this.lastResponder = lastResponder;
	}

	/**
	 * Repositions the root panel.
	 *
	 * @param screenWidth  the width of the screen
	 * @param screenHeight the height of the screen
	 */
	protected void reposition(int screenWidth, int screenHeight) {
		WPanel root = description.getRootPanel();
		titleX = description.getTitlePos().x();
		titleY = description.getTitlePos().y();

		if (!description.isFullscreen()) {
			this.left = (screenWidth - root.getWidth()) / 2;
			this.top = (screenHeight - root.getHeight()) / 2;
		} else {
			this.left = 0;
			this.top = 0;

			root.setSize(screenWidth, screenHeight);
		}
	}

	private void paint(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
		WPanel root = description.getRootPanel();
		root.paint(context, left, top, mouseX-left, mouseY-top);

		if (description.isTitleVisible()) {
			int width = description.getRootPanel().getWidth();
			ScreenDrawing.drawString(context, getTitle().getVisualOrderText(), description.getTitleAlignment(), left + titleX, top + titleY, width - 2 * titleX, description.getTitleColor());
		}
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		paint(graphics, mouseX, mouseY, a);
		
		WPanel root = description.getRootPanel();
		WWidget hitChild = root.hit(mouseX-left, mouseY-top);
		if (hitChild!=null) hitChild.renderTooltip(graphics, left, top, mouseX-left, mouseY-top);

		VisualLogger.render(graphics);
	}

	@Override
	public void tick() {
		super.tick();
		WPanel root = description.getRootPanel();
		root.tick();
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
		super.mouseClicked(click, doubled);

		int containerX = (int) click.x() - left;
		int containerY = (int) click.y() - top;
		mouseInputHandler.checkFocus(containerX, containerY);
		if (containerX<0 || containerY<0 || containerX>=width || containerY>=height) return true;
		mouseInputHandler.onMouseDown(containerX, containerY, click, doubled);

		return true;
	}

	@Override
	public boolean mouseReleased(MouseButtonEvent click) {
		super.mouseReleased(click);

		int containerX = (int) click.x() - left;
		int containerY = (int) click.y() - top;
		mouseInputHandler.onMouseUp(containerX, containerY, click);

		return true;
	}

	@Override
	public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
		super.mouseDragged(click, offsetX, offsetY);

		int containerX = (int) click.x() - left;
		int containerY = (int) click.y() - top;
		mouseInputHandler.onMouseDrag(containerX, containerY, click, offsetX, offsetY);

		return true;
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);

		int containerX = (int)mouseX-left;
		int containerY = (int)mouseY-top;
		mouseInputHandler.onMouseScroll(containerX, containerY, horizontalAmount, verticalAmount);

		return true;
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		super.mouseMoved(mouseX, mouseY);

		int containerX = (int)mouseX-left;
		int containerY = (int)mouseY-top;
		mouseInputHandler.onMouseMove(containerX, containerY);
	}

	@Override
	public boolean charTyped(CharacterEvent input) {
		WWidget focus = description.getFocus();
		if (focus != null && focus.onCharTyped(input) == InputResult.PROCESSED) {
			return true;
		}

		return super.charTyped(input);
	}

	@Override
	public boolean keyPressed(KeyEvent input) {
		WWidget focus = description.getFocus();
		if (focus != null && focus.onKeyPressed(input) == InputResult.PROCESSED) {
			return true;
		}

		return super.keyPressed(input);
	}

	@Override
	public boolean keyReleased(KeyEvent input) {
		WWidget focus = description.getFocus();
		if (focus != null && focus.onKeyReleased(input) == InputResult.PROCESSED) {
			return true;
		}

		return super.keyReleased(input);
	}

	@Override
	public boolean preeditUpdated(@Nullable PreeditEvent event) {
		WWidget focus = description.getFocus();
		if (focus != null && focus.onPreeditUpdated(event) == InputResult.PROCESSED) {
			return true;
		}

		return super.preeditUpdated(event);
	}

	@Override
	protected void updateNarratedWidget(NarrationElementOutput builder) {
		NarrationHelper.addNarrations(description.getRootPanel(), builder);
	}
}
