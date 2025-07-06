package redart15.redtrees.worldfeatures;

import net.minecraft.core.block.*;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.world.World;
import net.minecraft.core.world.chunk.ChunkPosition;
import net.minecraft.core.world.generate.feature.MethodParametersAnnotation;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

import static net.minecraft.core.world.generate.feature.tree.WorldFeatureTree.getDirtForGrass;

public class RedWorldFeatureToweringTree extends WorldFeature {
	World world;
	Random random = new Random();
	public int leaveID;
	public int logID;
	public int saplingID;
	public int treeHeight;
	public int trunkHeight;
	public ChunkPosition position;

//	int x;
//	int z;
//	int y;

	public static int[][] offsets = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};

	@MethodParametersAnnotation(
		names = {"leaveID", "logID", "saplingID"}
	)
	public RedWorldFeatureToweringTree(int leaveID, int logID, int saplingID) {
		this.leaveID = leaveID;
		this.logID = logID;
		this.saplingID = saplingID;
	}

	@Override
	public boolean place(World world, Random random, int x, int y, int z) {
		this.treeHeight = random.nextInt(10) + 22;
		this.trunkHeight = treeHeight - 2;
		this.world = world;

		if (y < 1 || y + treeHeight > world.getHeightBlocks()) {
			return false;
		}

		// check if there is a 2x2 cluster of saplings
		ChunkPosition position = findValid2x2SaplingCluster(world, x, y, z, saplingID);
		if (position == null) {
			return false;
		}
		this.position = position;
		// temporary remove the saplings
		place2x2Area(position.x, position.y, position.z, 0);
		if (!canPlaceTree()) {
			// if tree cannot be places put them back
			place2x2Area(position.x, position.y, position.z, saplingID);
		}
		placeTree();
		return true;
	}

	public void placeTree() {
		placeTrunk();
		texturingTrunk();
		placeFoliage();
	}

	// TODO: Partialy done thurther down
	public void placeFoliage() {
	}

	public boolean canPlaceTree() {
		return canPlaceTrunk() && canPlaceFoliage();
	}

	// TODO: Partialy done thurther down
	public boolean canPlaceFoliage() {
		return true;
	}

	/*TODO
	 *  Collect all the points
	 *  Designate some as branches
	 *  Check if branch with leaves can be placed
	 * */

	public void texturingTrunk() {
		int[][] sides = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
		int prevIndex = 0;
		for (int height = 0; height <= trunkHeight; height++) {
			if (height < 6) {
				continue;
			}
			int indexSide = random.nextInt(4);
			if (indexSide == prevIndex) {
				indexSide = (indexSide + 3) % 4;
			}
			int[] side = sides[indexSide];
			int x = position.x + side[0];
			int y = position.y + height;
			int z = position.z + side[1];
			int[] vector = detVector(x, z);
			for (int i = 2; i > 0; i--) {
				world.setBlockWithNotify(x + vector[0], y, z, leaveID);
				world.setBlockWithNotify(x, y, z + vector[1], leaveID);
				world.setBlockWithNotify(x + vector[0], y, z + vector[1], leaveID);
				x = x + vector[0];
				z = z + vector[1];
			}
			prevIndex = indexSide;
		}
	}

	private int[] detVector(int x, int z) {
		if (position.x == x && position.z == z) {
			return new int[]{-1, -1};
		}
		if (position.x == x) {
			return new int[]{-1, 1};
		}
		if (position.z == z) {
			return new int[]{1, -1};
		}
		return new int[]{1, 1};
	}

	public void onTreeGrown() {
		Block<?> dirt;
		for (int[] offset : offsets) {
			int x = position.x + offset[0];
			int z = position.z + offset[1];
			dirt = getDirtForGrass(world.getBlockId(x, position.y - 1, z));
			if (dirt != null) {
				world.setBlockWithNotify(x, position.y - 1, z, dirt.id());
			}
		}
	}

	public boolean canGrowOn() {
		for (int[] offset : offsets) {
			int idBelow = world.getBlockId(position.x + offset[0], position.y - 1, position.z + offset[1]);
			if (!Blocks.hasTag(idBelow, BlockTags.GROWS_TREES)) {
				return false;
			}
		}
		return true;
	}

	public void placeTrunk() {
		if (!canGrowOn()) {
			return;
		}
		onTreeGrown();
		for (int height = 0; height <= trunkHeight; height++) {
			for (int[] offset : offsets) {
				world.setBlockWithNotify(position.x + offset[0], position.y + height, position.z + offset[1], logID);
			}
		}
	}

	public boolean canPlaceTrunk() {
		boolean canPlace = false;
		for (int currentHeight = position.y + 1; currentHeight <= position.y + trunkHeight; currentHeight++) {
			if (!is2x2AreaClear(position.x, currentHeight, position.z)) {
				return false;
			}
		}
		return canPlace;
	}

	public void place2x2Area(int x, int y, int z, int blockID) {
		for (int[] offset : offsets) {
			int offsetX = x + offset[0];
			int offsetZ = z + offset[1];
			world.setBlock(offsetX, y, offsetZ, blockID);
		}
	}

	public boolean is2x2AreaClear(int x, int y, int z) {
		for (int[] offset : offsets) {
			int offsetX = x + offset[0];
			int offsetZ = z + offset[1];
			Block<?> block = world.getBlock(offsetX, y, offsetZ);
			if (!isClear(block)) {
				return false;
			}
		}
		return true;
	}

	private boolean isClear(Block<?> block) {
		if (block == null || block.id() == 0) {
			return true;
		} else {
			return Block.hasLogicClass(block, BlockLogicLog.class) || Block.hasLogicClass(block, BlockLogicLeavesBase.class) || Block.hasLogicClass(block, BlockLogicFlower.class);
		}
	}

	public static ChunkPosition findValid2x2SaplingCluster(World world, int x, int y, int z, int saplingID) {
		int[][] originPoints = {{0, 0}, {0, -1}, {-1, -1}, {-1, 0}};
		for (int[] originPoint : originPoints) {
			int originX = x + originPoint[0];
			int originZ = z + originPoint[1];
			boolean check = true;
			for (int[] offset : originPoints) {
				int offsetX = originX - offset[0];
				int offsetZ = originZ - offset[1];
				check = check & world.getBlockId(offsetX, y, offsetZ) == saplingID;
			}
			if (check) {
				return new ChunkPosition(originX, y, originZ);
			}
		}
		return null;
	}
}
