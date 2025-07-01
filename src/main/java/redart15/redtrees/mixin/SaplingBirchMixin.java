package redart15.redtrees.mixin;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockLogicSaplingBase;
import net.minecraft.core.block.BlockLogicSaplingBirch;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTree;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeTall;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = BlockLogicSaplingBirch.class,remap = false)
public abstract class SaplingBirchMixin extends BlockLogicSaplingBase {

	public SaplingBirchMixin(Block<?> block) {
		super(block);
	}

	@Inject(
		method = "growTree(Lnet/minecraft/core/world/World;IIILjava/util/Random;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	public void betterGrowTree(World world, int x, int y, int z, Random random, CallbackInfo ci){
		WorldFeature treeTall = new WorldFeatureTreeTall(Blocks.LEAVES_BIRCH.id(), Blocks.LOG_BIRCH.id());
		WorldFeature treeSmall = new WorldFeatureTree(Blocks.LEAVES_BIRCH.id(), Blocks.LOG_BIRCH.id(), 5);
		world.setBlock(x, y, z, 0);
		if (!treeSmall.place(world, random, x, y, z) && !treeTall.place(world, random, x, y, z)) {
			world.setBlock(x, y, z, this.id());
		}
		ci.cancel();
	}
}
