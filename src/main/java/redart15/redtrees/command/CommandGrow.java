package redart15.redtrees.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilderLiteral;
import com.mojang.brigadier.builder.ArgumentBuilderRequired;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogic;
import net.minecraft.core.block.BlockLogicSaplingBirch;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.command.CommandManager;
import net.minecraft.core.net.command.CommandSource;
import net.minecraft.core.net.command.arguments.ArgumentTypeBiome;
import net.minecraft.core.net.command.arguments.ArgumentTypeBlock;
import net.minecraft.core.net.command.arguments.ArgumentTypeEntity;
import net.minecraft.core.net.command.arguments.ArgumentTypeIntegerCoordinates;
import net.minecraft.core.net.command.helpers.BlockInput;
import net.minecraft.core.net.command.helpers.EntitySelector;
import net.minecraft.core.net.command.helpers.IntegerCoordinates;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.Chunk;
import net.minecraft.core.world.chunk.ChunkPosition;
import net.minecraft.core.world.chunk.provider.IChunkProvider;
import net.minecraft.core.world.data.SynchedEntityData;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.Sys;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("ALL") //cause this drives me nuts
public class CommandGrow implements CommandManager.CommandRegistry {

	public void register(CommandDispatcher<CommandSource> dispatcher) {
//		dispatcher.register(
//			(ArgumentBuilderLiteral) ArgumentBuilderLiteral.literal("grow")
//				.then(ArgumentBuilderRequired.argument("blocks", ArgumentTypeIntegerCoordinates.intCoordinates())
//					.executes(context -> {
//						CommandSource source = (CommandSource)context.getSource();
//						Player player = source.getSender();
//						IntegerCoordinates coordinates = context.getArgument("blocks", IntegerCoordinates.class);
//						World world = source.getWorld();
//						Block block = world.getBlock(coordinates.getX(source),coordinates.getY(source, true),coordinates.getZ(source));
//						if(block.getLogic() instanceof BlockLogicSaplingBirch){
//							WorldFeature treeSmall = new WorldFeatureTree(Blocks.LEAVES_BIRCH.id(), Blocks.LOG_BIRCH.id(), 5);
//							treeSmall.place(world, new Random(),coordinates.getX(source),coordinates.getY(source, true),coordinates.getZ(source));
//						}
//						return 0;
//					})));
	}
}

