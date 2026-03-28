package io.github.cottonmc.test;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

public final class TestBlocks {
	public static final GuiBlock GUI = new GuiBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).setId(Keys.GUI));
	public static final Block NO_BLOCK_INVENTORY = new NoBlockInventoryBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(Keys.NO_BLOCK_INVENTORY));

	public static void register() {
		Registry.register(BuiltInRegistries.BLOCK, Keys.GUI, GUI);
		Registry.register(BuiltInRegistries.BLOCK, Keys.NO_BLOCK_INVENTORY, NO_BLOCK_INVENTORY);
	}

	public static final class Keys {
		public static final ResourceKey<Block> GUI = of("gui");
		public static final ResourceKey<Block> NO_BLOCK_INVENTORY = of("no_block_inventory");

		private static ResourceKey<Block> of(String id) {
			return ResourceKey.create(Registries.BLOCK, LibGuiTest.id(id));
		}
	}
}
