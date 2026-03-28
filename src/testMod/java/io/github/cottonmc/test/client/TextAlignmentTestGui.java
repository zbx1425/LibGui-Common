package io.github.cottonmc.test.client;

import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WLabeledSlider;
import io.github.cottonmc.cotton.gui.widget.WTabPanel;
import io.github.cottonmc.cotton.gui.widget.WText;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.VerticalAlignment;

import java.util.function.Consumer;
import java.util.stream.IntStream;

public final class TextAlignmentTestGui extends LightweightGuiDescription {
	public TextAlignmentTestGui() {
		WTabPanel tabPanel = new WTabPanel();

		WGridPanel labelPanel = new WGridPanel();
		labelPanel.setInsets(Insets.ROOT_PANEL);
		labelPanel.setGaps(0, 1);
		Component labelStyled = Component.literal("world")
				.withStyle(style -> style.withHoverEvent(new HoverEvent.ShowText(Component.literal("test"))));
		Component labelText = Component.literal("hello, ").append(labelStyled);
		WLabel label = new WLabel(labelText);
		WLabeledSlider labelSliderH = forEnum(HorizontalAlignment.class, label::setHorizontalAlignment);
		WLabeledSlider labelSliderV = forEnum(VerticalAlignment.class, label::setVerticalAlignment);
		labelPanel.add(label, 0, 0, 5, 3);
		labelPanel.add(labelSliderH, 0, 3, 5, 1);
		labelPanel.add(labelSliderV, 0, 4, 5, 1);

		WGridPanel textPanel = new WGridPanel();
		textPanel.setInsets(Insets.ROOT_PANEL);
		textPanel.setGaps(0, 1);
		Component textText = IntStream.rangeClosed(1, 3)
				.mapToObj(line -> {
					Component textStyled = Component.literal("world").withStyle(style -> style
							.withHoverEvent(new HoverEvent.ShowText(Component.literal("test")))
							.withColor(ChatFormatting.values()[line + 9])
					);
					return Component.literal("hell" + "o".repeat(line * 3) + ", ").append(textStyled).append("\n");
				})
				.reduce(Component.empty(), MutableComponent::append);
		WText text = new WText(textText);
		WLabeledSlider textSliderH = forEnum(HorizontalAlignment.class, text::setHorizontalAlignment);
		WLabeledSlider textSliderV = forEnum(VerticalAlignment.class, text::setVerticalAlignment);
		textPanel.add(text, 0, 0, 5, 4);
		textPanel.add(textSliderH, 0, 4, 5, 1);
		textPanel.add(textSliderV, 0, 5, 5, 1);

		tabPanel.add(labelPanel, builder -> builder.title(Component.literal("WLabel")));
		tabPanel.add(textPanel, builder -> builder.title(Component.literal("WText")));
		setRootPanel(tabPanel);
		setUseDefaultRootBackground(false);
		tabPanel.validate(this);
	}

	private static <E extends Enum<E>> WLabeledSlider forEnum(Class<E> type, Consumer<E> consumer) {
		E[] values = type.getEnumConstants();
		var slider = new WLabeledSlider(1, values.length);
		slider.setLabel(Component.literal(type.getSimpleName() + ": " + values[0]));
		slider.setLabelUpdater(value -> Component.literal(type.getSimpleName() + ": " + values[value - 1]));
		slider.setValueChangeListener(value -> consumer.accept(values[value - 1]));
		return slider;
	}
}
