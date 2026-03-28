package io.github.cottonmc.test;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public final class TestItems {
	public static final Item CLIENT_GUI = new GuiItem(new Item.Properties().setId(Keys.CLIENT_GUI));
	public static final Item GUI = new BlockItem(TestBlocks.GUI, new Item.Properties().setId(Keys.GUI).useBlockDescriptionPrefix());
	public static final Item NO_BLOCK_INVENTORY = new BlockItem(TestBlocks.NO_BLOCK_INVENTORY, new Item.Properties().setId(Keys.NO_BLOCK_INVENTORY).useBlockDescriptionPrefix());

	public static void register() {
		Registry.register(BuiltInRegistries.ITEM, Keys.CLIENT_GUI, CLIENT_GUI);
		Registry.register(BuiltInRegistries.ITEM, Keys.GUI, GUI);
		Registry.register(BuiltInRegistries.ITEM, Keys.NO_BLOCK_INVENTORY, NO_BLOCK_INVENTORY);
	}

	public static final class Keys {
		public static final ResourceKey<Item> GUI = of("gui");
		public static final ResourceKey<Item> NO_BLOCK_INVENTORY = of("no_block_inventory");
		public static final ResourceKey<Item> CLIENT_GUI = of("client_gui");

		private static ResourceKey<Item> of(String id) {
			return ResourceKey.create(Registries.ITEM, LibGuiTest.id(id));
		}
	}
}
