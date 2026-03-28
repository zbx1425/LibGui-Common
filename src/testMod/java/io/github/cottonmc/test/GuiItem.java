package io.github.cottonmc.test;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public class GuiItem extends Item {
	public GuiItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResult use(Level world, Player player, InteractionHand hand) {
		player.openMenu(createScreenHandlerFactory(player, hand));
		return InteractionResult.SUCCESS;
	}

	private MenuProvider createScreenHandlerFactory(Player player, InteractionHand hand) {
		EquipmentSlot slot = switch (hand) {
			case MAIN_HAND -> EquipmentSlot.MAINHAND;
			case OFF_HAND -> EquipmentSlot.OFFHAND;
		};
		ItemStack stack = player.getItemInHand(hand);
		return new ExtendedScreenHandlerFactory<EquipmentSlot>() {
			@Override
			public Component getDisplayName() {
				return stack.getHoverName();
			}

			@Override
			public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
				return new TestItemDescription(syncId, playerInventory, SlotAccess.forEquipmentSlot(player, slot));
			}

			@Override
			public EquipmentSlot getScreenOpeningData(ServerPlayer player) {
				return slot;
			}
		};
	}
}
