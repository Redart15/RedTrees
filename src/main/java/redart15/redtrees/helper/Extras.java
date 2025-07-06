//package redart15.redtrees.helper;
//
//import net.minecraft.core.block.Blocks;
//import net.minecraft.core.world.World;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class Extras {
//
//	private void plotCircleScanlines(int cx, int cz, int x, int z, int cy, World world, Set<String> filled) {
//		// These are the symmetrical horizontal spans for each row
//		drawLine(world, cx - x, cx + x, cy, cz + z, filled); // top half
//		drawLine(world, cx - x, cx + x, cy, cz - z, filled); // bottom half
//		drawLine(world, cx - z, cx + z, cy, cz + x, filled); // right half
//		drawLine(world, cx - z, cx + z, cy, cz - x, filled); // left half
//	}
//
//	private void drawLine(World world, int x1, int x2, int y, int z, Set<String> filled) {
//		for (int x = x1; x <= x2; x++) {
//			String key = x + "," + y + "," + z;
//			if (!filled.contains(key)) {
//				filled.add(key);
//				world.setBlock(x, y, z, Blocks.BLOCK_GOLD.id()); // Replace 'yourBlockType' with actual block
//			}
//		}
//	}
//
//
//
//
//
//	private static boolean filled(double x, double y, double radius, double ratio) {
//		y = y * ratio;
//		return y * y + x * x <= radius;
//	}
//
//	private static boolean isFilled(double x, double y, double width, double height) {
//		double dx = -0.5 * (width - 2 * (x * 0.5));
//		double dy = -0.5 * (height - 2 * (y * 0.5));
//		return filled(dx, dy, width / 2, width / height);
//	}
//
//
//	public static List<Point> drawCircle_donat(int radius){
//		return drawCircle(radius, radius);
//	}
//
//	public static List<Point> drawCircle_donat(int width, int height){
//		return drawCircle(width, height);
//	}
//
//	public static List<Point> drawCircle_donat(double width, double height){
//		return drawCircle(width, height);
//	}
//
//	public static List<Point> drawCircle(double width, double height) {
//		List<Point> Points = new ArrayList<>();
//		double cx = width / 2, cy = height / 2;
//		for(double y = 0; y < height; y++){
//			for(double x = 0; x < width; x++){
//				boolean filled = isFilled(x,y,width, height);
//				double midX =  (width / 2) - 0.5;
//				double midY =  (width / 2) - 0.5;
//				if (!filled ) { // || x == midX || y == midY
//					continue;
//				}
//				Points.add(new Point((int)x,(int)y));
//
//			}
//		}
//		return Points;
//	}
//
//	private static void setBlock(World world, int x, int y, int z) {
//		world.setBlockWithNotify(x, y, z, Blocks.BLOCK_GOLD.id());
//	}
//
//	//		int radius = 7;
////		int sq_radius = radius * radius;
////		for (int ix = x - radius; ix <= x + radius; ix++) {
////			for (int iz = z - radius; iz <= z + radius; iz++) {
////				int dx = Math.abs(ix - x);
////				int dz = Math.abs(iz - z);
////				if (dx * dx + dz * dz < sq_radius) {
////					world.setBlockWithNotify(ix, y, iz, Blocks.BLOCK_GOLD.id());
////				}
////			}
////		}
////		world.setBlockWithNotify(x, y, z, Blocks.BLOCK_DIAMOND.id());
//
//}
