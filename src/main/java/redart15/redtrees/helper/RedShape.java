package redart15.redtrees.helper;

import java.util.ArrayList;
import java.util.List;

public class RedShape {

	public static List<Point> firCone(int cx, int cy, int cz, int width, int length, int height) {
		List<Point> PointList = new ArrayList<>();
		int w = width, l = length;
		for (int h = 0; h < height; h+=3, w-=1, l-=1) {
//			double yRadius = h - 0.5;
			double minDimension = Math.min(w, l);
			double minRadius = minDimension / 2;
			for (double y = 0; y <= 2; y++) {
				double percent = 1 - (y / height);
				double circleRadius = percent * minRadius;
				PointList.addAll(voxelCircle(w, l, circleRadius, h + y));
			}
		}
		return addCenterPoint(PointList, cx, cy, cz);
	}


	/**
	 * Generates a list of {@code Point} representing a voxel cone
	 * centered at the point of origin.
	 * <p>The algorithm is based on voxel shapes by Raymond Minge:<br>
	 * https://oranj.io/blog/VoxelSphereGenerator
	 *
	 * @param height height of the cone
	 * @param radius radius of the circle
	 * @param cx     x coordinate of center block
	 * @param cy     y coordinate of center block
	 * @param cz     z coordinate of center block
	 * @return List of {@code Point} that lie within the circle
	 */
	public static List<Point> voxelCone(int cx, int cy, int cz, int radius, int height) {
		// width == length
		int width = 2 * radius;
		return addCenterPoint(voxelCone(height, width, width), cx, cy, cz);
	}

	/**
	 * Generates a list of {@code Point} representing a voxel cone
	 * centered at the point of origin.
	 * <p>The algorithm is based on voxel shapes by Raymond Minge:<br>
	 * https://oranj.io/blog/VoxelSphereGenerator
	 *
	 * @param width  width of the area
	 * @param length length of the area
	 * @param height height of the cone
	 * @param cx     x coordinate of center block
	 * @param cy     y coordinate of center block
	 * @param cz     z coordinate of center block
	 * @return List of {@code Point} that lie within the circle
	 */
	public static List<Point> voxelCone(int cx, int cy, int cz, int width, int length, int height) {
		return addCenterPoint(voxelCone(height, width, length), cx, cy, cz);
	}

	protected static List<Point> voxelCone(int height, int width, int length) {
		List<Point> PointList = new ArrayList<>();
		double yRadius = height - 0.5;
		double minDimension = Math.min(width, length);
		double minRadius = minDimension / 2;

		for (double y = 0; y <= yRadius; y++) {
			double percent = 1 - (y / height);
			double circleRadius = percent * minRadius;
			PointList.addAll(voxelCircle(width, length, circleRadius, y));
		}
		return PointList;
	}


	/**
	 * Generates a list of {@code Point} representing a voxel circle
	 * centered at the point of origin.
	 * <p>The algorithm is based on voxel shapes by Raymond Minge:<br>
	 * https://oranj.io/blog/VoxelSphereGenerator
	 *
	 * @param radius radius of the circle
	 * @param cx     x coordinate of center block
	 * @param cy     y coordinate of center block
	 * @param cz     z coordinate of center block
	 * @return List of {@code Point} that lie within the circle
	 */
	public static List<Point> voxelCircle(int cx, int cy, int cz, int radius) {
		// width == length
		int width = 2 * radius;
		return addCenterPoint(voxelCircle(width, width, radius, 0), cx, cy, cz);
	}

	/**
	 * Generates a list of {@code Point} representing a voxel circle/oval
	 * that fits in the width and length bounds and is centered at the point of origin.
	 * <p>The algorithm is based on voxel shapes by Raymond Minge:<br>
	 * https://oranj.io/blog/VoxelSphereGenerator
	 *
	 * @param width  width of the area
	 * @param length length of the area
	 * @param cx     x coordinate of center block
	 * @param cy     y coordinate of center block
	 * @param cz     z coordinate of center block
	 * @return List of {@code Point} that lie within the circle
	 * @implNote <p>width == length creates a circle<br>
	 * width != length creates an oval
	 */
	public static List<Point> voxelCircle(int cx, int cy, int cz, int width, int length) {
		double minDim = Math.min(width, length);
		double radius = (minDim - 1) / 2 + 0.5;
		return addCenterPoint(voxelCircle(width, length, radius, 0), cx, cy, cz);
	}

	protected static List<Point> voxelCircle(int width, int length, double radius, double y) {
		List<Point> PointList = new ArrayList<>();
		// ratios
		double xRadius = (width - 1.0) / 2;
		double zRadius = (length - 1.0) / 2;
		double minRadius = Math.min(xRadius, zRadius);
		double xRatio = xRadius / minRadius;
		double zRatio = zRadius / minRadius;

		for (double z = -zRadius; z <= zRadius; z++) {
			for (double x = -xRadius; x <= xRadius; x++) {
				double dx = x / xRatio;
				double dy = z / zRatio;
				if (dx * dx + dy * dy < radius * radius) {
					float fx = (float) (Math.ceil(x));
					float fy = (float) (Math.ceil(y));
					float fz = (float) (Math.ceil(z));
					PointList.add(new Point(fx, fy, fz));
				}
			}
		}
		return PointList;
	}

	/**
	 * Generates a list of {@code Point} representing a voxel circle
	 * centered at the point of origin.
	 * <p>The algorithm is based on the youtube video by NoBS Code:<br>
	 * https://www.youtube.com/watch?v=hpiILbMkF9w
	 *
	 * @param radius the radius of the sphere
	 * @param cx     x coordinate of center block
	 * @param cy     y coordinate of center block
	 * @param cz     z coordinate of center block
	 * @return List of {@code Point} that lie within the circle
	 */
	public static List<Point> midPointCircle(int cx, int cy, int cz, int radius) {
		return addCenterPoint(midPointCircle(radius,0), cx, cy, cz);
	}

	protected static List<Point> midPointCircle(int radius, int y) {
		List<Point> PointList = new ArrayList<>();
		int x = 0;
		int z = radius;
		int P = 5 - 4 * radius;
		while (x <= z) {
			PointList.addAll(getLinePoints(-z, z, x, y));
			if (P > 0) {
				PointList.addAll(getLinePoints(-x, x, z,y));
				z--;
				P -= 8 * z;
			}
			x++;
			P += 8 * x + 4;
		}
		return PointList;
	}

	protected static List<Point> getLinePoints(int startX, int endX, int z, int y) {
		List<Point> PointList = new ArrayList<>();
		for (int x = startX; x < endX; x++) {
			PointList.add(new Point(x, y, -z));
			PointList.add(new Point(x, y, z));
		}
		return PointList;
	}

	/**
	 * Generates a list of {@code Point} representing a voxel sphere
	 * centered at the point of origin.
	 * <p>The algorithm is based on the "Voxel Sphere" by Neil Fraser:<br>
	 * https://neil.fraser.name/news/2006/11/17/
	 *
	 * @param radius the radius of the sphere
	 * @param cx     x coordinate of center block
	 * @param cy     y coordinate of center block
	 * @param cz     z coordinate of center block
	 * @return List of {@code Point} that lie within the sphere
	 * @implNote the sphere center lies in the center of a 2x2 square
	 * where the center block is the north easter corner
	 */
	public static List<Point> legoSphere(int cx, int cy, int cz, int radius) {
		return addCenterPoint(legoSphere(radius), cx, cy, cz);
	}


	/**
	 * Generates a list of {@code Point} representing a voxel circle
	 * centered at the point of origin.
	 * <p>The algorithm is based on the "Voxel Sphere" by Neil Fraser:<br>
	 * https://neil.fraser.name/news/2006/11/17/
	 *
	 * @param radius the radius of the sphere
	 * @param cx     x coordinate of center block
	 * @param cy     y coordinate of center block
	 * @param cz     z coordinate of center block
	 * @return List of {@code Point} that lie within the circle
	 * @implNote the circle center lies in the center of a 2x2 square
	 * where the center block is the north easter corner
	 */
	public static List<Point> legoCircle(int cx, int cy, int cz, int radius) {
		return addCenterPoint(process_slice(radius, 0), cx, cy, cz);
	}

	protected static List<Point> legoSphere(double radius) {
		double maxBlockZ = radius * 2 + 1;
		List<Point> PointList = new ArrayList<>(process_slice(radius, 0));
		for (double y = -maxBlockZ / 2; y <= maxBlockZ / 2; y++) {
			PointList.addAll(process_slice(Math.sqrt(radius * radius - y * y), y));
		}
		return PointList;
	}

	protected static List<Point> process_slice(double radius, double y) {
		List<Point> PointList = new ArrayList<>();
		double maxBlocks = radius * 2 + 1;
		for (double z = -maxBlocks / 2; z <= maxBlocks / 2; z++) {
			for (double x = -maxBlocks / 2; x <= maxBlocks / 2; x++) {
				if (!(z * z + x * x > radius * radius)) {
					float fx = (float) (Math.ceil(x));
					float fz = (float) (Math.ceil(y));
					float fy = (float) (Math.ceil(z));
					PointList.add(new Point(fx, fy, fz));
				}
			}
		}
		return PointList;
	}

	protected static List<Point> addCenterPoint(List<Point> PointList, float cx, float cy, float cz) {
		for (Point point : PointList) {
			point.setX(point.getFloatX() + cx);
			point.setY(point.getFloatY() + cy);
			point.setZ(point.getFloatZ() + cz);
		}
		return PointList;
	}
}
