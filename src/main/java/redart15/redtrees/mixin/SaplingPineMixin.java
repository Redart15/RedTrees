package redart15.redtrees.mixin;


import net.minecraft.core.block.BlockLogicSaplingPine;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeTaigaBushy;
import net.minecraft.core.world.generate.feature.tree.WorldFeatureTreeTaigaTall;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import redart15.redtrees.helper.Point;

import java.util.*;

import static redart15.redtrees.helper.RedShape.*;


@Mixin(value = BlockLogicSaplingPine.class, remap = false)
public class SaplingPineMixin {


	@Inject(
		method = "growTree(Lnet/minecraft/core/world/World;IIILjava/util/Random;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	public void growTree(World world, int x, int y, int z, Random random, CallbackInfo ci) {
		WorldFeature treeBig = new WorldFeatureTreeTaigaTall(Blocks.LEAVES_PINE.id(), Blocks.LOG_PINE.id());
		WorldFeature treeSmall = new WorldFeatureTreeTaigaBushy(Blocks.LEAVES_PINE.id(), Blocks.LOG_PINE.id());

		int[] ids = {
			Blocks.GLASS.id(),
			Blocks.BLOCK_GOLD.id(),
			Blocks.STONE.id(),
			Blocks.PUMPKIN.id(),
			Blocks.BASALT.id(),
			Blocks.GRANITE.id(),
			Blocks.COBBLE_STONE.id(),
			Blocks.GLOWSTONE.id()
		};
		int id = ids[0];
		int c = 0;
		List<Point> points = firCone(x,y,z,12,12,19);
		for(int i = 0; i < points.size(); i++){
			if(i % 8 == 0)c = (++c % ids.length);
			world.setBlockWithNotify(points.get(i).getIntX(), points.get(i).getIntY(), points.get(i).getIntZ(), ids[c]);
		}
		world.setBlockWithNotify(x, y, z, Blocks.BLOCK_DIAMOND.id());

//		world.setBlock(x, y, z, 0);
//		if(!small.place(world,random,x,y,z)){
//			world.setBlock(x, y, z, ((BlockLogicSaplingPine)(Object)this).id());
//		}

//		if (!treeSmall.place(world, random, x, y, z) && !treeBig.place(world, random, x, y, z)) {
//			world.setBlock(x, y, z, ((BlockLogicSaplingPine)(Object)this).id());
//		}
		ci.cancel();
	}
}
