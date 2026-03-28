package io.github.cottonmc.test;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.resources.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class LibGuiTest implements ModInitializer {
	public static final String MODID = "libgui-test";

	public static BlockEntityType<GuiBlockEntity> GUI_BLOCKENTITY_TYPE;
	public static MenuType<TestDescription> GUI_SCREEN_HANDLER_TYPE;
	public static MenuType<TestItemDescription> ITEM_SCREEN_HANDLER_TYPE;
	public static MenuType<ReallySimpleDescription> REALLY_SIMPLE_SCREEN_HANDLER_TYPE;

	@Override
	public void onInitialize() {
		TestBlocks.register();
		TestItems.register();
		GUI_BLOCKENTITY_TYPE = FabricBlockEntityTypeBuilder.create(GuiBlockEntity::new, TestBlocks.GUI).build();
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id("gui"), GUI_BLOCKENTITY_TYPE);
		
		GUI_SCREEN_HANDLER_TYPE = new MenuType<>((int syncId, Inventory inventory) -> {
			return new TestDescription(GUI_SCREEN_HANDLER_TYPE, syncId, inventory, ContainerLevelAccess.NULL);
		}, FeatureFlagSet.of(FeatureFlags.VANILLA));
		Registry.register(BuiltInRegistries.MENU, id("gui"), GUI_SCREEN_HANDLER_TYPE);
		ITEM_SCREEN_HANDLER_TYPE = new ExtendedScreenHandlerType<>((syncId, inventory, slot) -> {
			SlotAccess handStack = SlotAccess.forEquipmentSlot(inventory.player, slot);
			return new TestItemDescription(syncId, inventory, handStack);
		}, ByteBufCodecs.fromCodec(EquipmentSlot.CODEC).cast());
		Registry.register(BuiltInRegistries.MENU, id("item_gui"), ITEM_SCREEN_HANDLER_TYPE);

		REALLY_SIMPLE_SCREEN_HANDLER_TYPE = new MenuType<>(ReallySimpleDescription::new, FeatureFlagSet.of(FeatureFlags.VANILLA));
		Registry.register(BuiltInRegistries.MENU, id("really_simple"), REALLY_SIMPLE_SCREEN_HANDLER_TYPE);

		Optional<ModContainer> containerOpt = FabricLoader.getInstance().getModContainer("jankson");
		if (containerOpt.isPresent()) {
			ModContainer jankson = containerOpt.get();
			System.out.println("Jankson root path: "+jankson.getRootPath());
			try {
				Files.list(jankson.getRootPath()).forEach((path)->{
					path.getFileSystem().getFileStores().forEach((store)->{
						System.out.println("        Filestore: "+store.name());
					});
					System.out.println("    "+path.toAbsolutePath());
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			Path modJson = jankson.getPath("/fabric.mod.json");
			System.out.println("Jankson fabric.mod.json path: "+modJson);
			System.out.println(Files.exists(modJson) ? "Exists" : "Does Not Exist");
		} else {
			System.out.println("Container isn't present!");
		}
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}
}
