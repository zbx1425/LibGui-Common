package io.github.cottonmc.test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GuiBlockEntity extends BlockEntity implements ImplementedInventory, MenuProvider {
	static final int INVENTORY_SIZE = 8;
	
	NonNullList<ItemStack> items =  NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
	
	public GuiBlockEntity(BlockPos pos, BlockState state) {
		super(LibGuiTest.GUI_BLOCKENTITY_TYPE, pos, state);
	}

	@Override
	public NonNullList<ItemStack> getItems() {
		return items;
	}
	
	@Override
	public boolean stillValid(Player player) {
		return worldPosition.closerThan(player.blockPosition(), 4.5);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("test title");
	}

	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
		return new TestDescription(LibGuiTest.GUI_SCREEN_HANDLER_TYPE, syncId, inv, ContainerLevelAccess.create(level, worldPosition));
	}
}
