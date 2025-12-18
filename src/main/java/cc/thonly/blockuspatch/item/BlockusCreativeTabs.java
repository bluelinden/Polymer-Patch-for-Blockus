package cc.thonly.blockuspatch.item;

import com.brand.blockus.itemgroups.BlockusItemGroups;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.ColoredTilesBundle;
import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

public class BlockusCreativeTabs extends BlockusItemGroups {
	public static void bootstrap() {
		PolymerItemGroupUtils.registerPolymerItemGroup(BLOCKUS_BUILDING_BLOCKS,
				FabricItemGroup.builder()
						.displayName(Text.translatable("itemGroup.blockus_building_blocks").append(BLOCKUS_GROUPS_TEXT))
						.icon(() -> new ItemStack(BlockusBlocks.BLAZE_BRICKS.block())).build());
		PolymerItemGroupUtils.registerPolymerItemGroup(BLOCKUS_COLORED_BLOCKS,
				FabricItemGroup.builder()
						.displayName(Text.translatable("itemGroup.blockus_colored").append(BLOCKUS_GROUPS_TEXT))
						.icon(() -> new ItemStack(BlockusBlocks.ASPHALT.colorMap().get(DyeColor.LIME).block())).build());
		PolymerItemGroupUtils.registerPolymerItemGroup(BLOCKUS_COLORED_TILES, FabricItemGroup.builder()
				.displayName(Text.translatable("itemGroup.blockus_colored_tiles").append(BLOCKUS_GROUPS_TEXT))
				.icon(() -> new ItemStack(ColoredTilesBundle.get(Blocks.RED_CONCRETE, Blocks.BLUE_CONCRETE).block())).build());
		PolymerItemGroupUtils.registerPolymerItemGroup(BLOCKUS_NATURAL,
				FabricItemGroup.builder()
						.displayName(Text.translatable("itemGroup.blockus_natural").append(BLOCKUS_GROUPS_TEXT))
						.icon(() -> new ItemStack(BlockusBlocks.WHITE_OAK_SAPLING)).build());
		PolymerItemGroupUtils.registerPolymerItemGroup(BLOCKUS_FUNCTIONAL_BLOCKS,
				FabricItemGroup.builder()
						.displayName(Text.translatable("itemGroup.blockus_functional").append(BLOCKUS_GROUPS_TEXT))
						.icon(() -> new ItemStack(BlockusBlocks.REDSTONE_LANTERN)).build());
		PolymerItemGroupUtils.registerPolymerItemGroup(BLOCKUS_LEGACY_BLOCKS,
				FabricItemGroup.builder().displayName(Text.translatable("itemGroup.blockus_legacy").append(BLOCKUS_GROUPS_TEXT))
						.icon(() -> new ItemStack(BlockusBlocks.LEGACY_BRICKS)).build());
	}
}
