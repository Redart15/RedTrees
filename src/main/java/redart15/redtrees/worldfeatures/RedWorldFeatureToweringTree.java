package redart15.redtrees.worldfeatures;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
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
	int offsetX;
	int offsetZ;
	int x;
	int y;
	int z;

	public static int[][] offsets = {{0, 0}, {0, -1}, {-1, -1}, {-1, 0}};
	public static int[] leaveIDs = {
		Blocks.LEAVES_BIRCH.id(),
		Blocks.LEAVES_CACAO.id(),
		Blocks.LEAVES_CHERRY.id(),
		Blocks.LEAVES_CHERRY_FLOWERING.id(),
		Blocks.LEAVES_EUCALYPTUS.id(),
		Blocks.LEAVES_PALM.id(),
		Blocks.LEAVES_PINE.id(),
		Blocks.LEAVES_OAK.id(),
		Blocks.LEAVES_OAK_RETRO.id(),
		Blocks.LEAVES_SHRUB.id(),
		Blocks.LEAVES_THORN.id(),
	};


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
		this.x = x;
		this.y = y;
		this.z = z;

		if (y < 1 || y + treeHeight > world.getHeightBlocks()) {
			return false;
		}

		// find the square of saplings
		if (!getBlockSquareOffset()) {
			return false;
		}

		// temporary remove the saplings
		place2x2Area(offsetX, y, offsetZ, 0);
		if (!canPlaceTree()) {
			// if tree cannot be places put them back
			place2x2Area(offsetX, y, offsetZ, saplingID);
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

	public void place2x2AreaWithNotify(int x, int y, int z, int blockID) {
		world.setBlockWithNotify(x, y, z, blockID);
		world.setBlockWithNotify(x, y, z + 1, blockID);
		world.setBlockWithNotify(x + 1, y, z, blockID);
		world.setBlockWithNotify(x + 1, y, z + 1, blockID);
	}

	public void onTreeGrown() {
		Block<?>[] dirts = new Block<?>[4];
		dirts[0] = getDirtForGrass(world.getBlockId(offsetX, y - 1, offsetZ));
		dirts[1] = getDirtForGrass(world.getBlockId(offsetX, y - 1, offsetZ + 1));
		dirts[2] = getDirtForGrass(world.getBlockId(offsetX + 1, y - 1, offsetZ));
		dirts[3] = getDirtForGrass(world.getBlockId(offsetX + 1, y - 1, offsetZ + 1));
		for (Block<?> dirt : dirts) {
			if (dirt != null) {
				world.setBlockWithNotify(x, y - 1, z, dirt.id());
			}
		}
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
			int indexSide = random.nextInt(4);
			if (indexSide == prevIndex) {
				indexSide =  (indexSide + 3) % 4;
			}
			int[] side = sides[indexSide];
			int x = offsetX + side[0];
			int y = this.y + height;
			int z = offsetZ + side[1];
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
		if (this.offsetX == x && this.offsetZ == z) {
			return new int[]{-1, -1};
		}
		if (this.offsetX == x) {
			return new int[]{-1, 1};
		}
		if (this.offsetZ == z) {
			return new int[]{1, -1};
		}
		return new int[]{1, 1};
	}

	public void placeTrunk() {
		int idBelow = world.getBlockId(x, y - 1, z);
		if (!Blocks.hasTag(idBelow, BlockTags.GROWS_TREES)) {
			return;
		}
		onTreeGrown();
		for (int height = 0; height <= trunkHeight; height++) {
			world.setBlockWithNotify(offsetX, y + height, offsetZ, logID);
			world.setBlockWithNotify(offsetX, y + height, offsetZ + 1, logID);
			world.setBlockWithNotify(offsetX + 1, y + height, offsetZ, logID);
			world.setBlockWithNotify(offsetX + 1, y + height, offsetZ + 1, logID);
		}
	}

	public boolean canPlaceTrunk() {
		boolean canPlace = false;
		for (int height = this.y + 1; height <= this.y + trunkHeight; height++) {
			canPlace = canPlace | check2x2Area(offsetX, height, offsetZ, 0);
			for (int leaveID : leaveIDs) {
				canPlace = canPlace | check2x2Area(offsetX, height, offsetZ, leaveID);
			}
			if (!canPlace) {
				return false;
			}
		}
		return canPlace;
	}

	public void place2x2Area(int x, int y, int z, int blockID) {
		this.world.setBlock(x, y, z, blockID);
		this.world.setBlock(x, y, z + 1, blockID);
		this.world.setBlock(x + 1, y, z, blockID);
		this.world.setBlock(x + 1, y, z + 1, blockID);
	}

	public boolean check2x2Area(int x, int y, int z, int blockID) {
		return this.world.getBlockId(x, y, z) == blockID
			&& this.world.getBlockId(x, y, z + 1) == blockID
			&& this.world.getBlockId(x + 1, y, z) == blockID
			&& this.world.getBlockId(x + 1, y, z + 1) == blockID;
	}

	public boolean getBlockSquareOffset() {
		for (int[] offset : offsets) {
			int offsetX = offset[0];
			int offsetZ = offset[1];

			int adjX = offsetX + this.x;
			int adjZ = offsetZ + this.z;

			if (check2x2Area(adjX, this.y, adjZ, saplingID)) {
				this.offsetX = x + offsetX;
				this.offsetZ = z + offsetZ;
				return true;
			}
		}
		return false;
	}
}
