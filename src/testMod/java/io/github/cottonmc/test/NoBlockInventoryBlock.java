package io.github.cottonmc.test;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;

public class NoBlockInventoryBlock extends Block implements MenuProvider {
	public NoBlockInventoryBlock(Properties settings) {
		super(settings);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		player.openMenu(this);
		return world.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
	}

	@Override
	public Component getDisplayName() {
		return getName();
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
		return new ReallySimpleDescription(syncId, inv);
	}
}
