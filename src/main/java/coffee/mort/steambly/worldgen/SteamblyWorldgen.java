package coffee.mort.steambly.worldgen;

import coffee.mort.steambly.Steambly;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class SteamblyWorldgen implements IWorldGenerator {
	@Override
	public void generate(
			Random rand,
			int x, int z,
			World world,
			IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {

		switch (world.provider.getDimension()) {
		case -1: // Nether
			break;
		case 0: // Overworld
			generateOverworld(world, rand, x, z);
			break;
		case 1: // End
			break;
		}
	}

	private void generateOverworld(World world, Random rand, int x, int z) {
		genBlocks(rand, world,
			Steambly.blockChromiumOre,
			10, 16, // maxVein, n
			10, 30, // minY, maxY
			x, z);

		genBlocks(rand, world,
			Steambly.blockNickelOre,
			14, 16, // maxVein, n
			10, 60, // minY, maxY
			x, z);
	}

	private void genBlocks(
			Random rand,
			World world,
			Block block,
			int maxVein, int n,
			int minY, int maxY,
			int x, int z) {

		x *= 16;
		z *= 16;

		for (int i = 0; i < n; ++i) {
			int blockX = rand.nextInt(16) + x;
			int blockZ = rand.nextInt(16) + z;
			int blockY = rand.nextInt(maxY - minY) + maxY;

			BlockPos pos = new BlockPos(blockX, blockY, blockZ);
			WorldGenMinable worldgen = new WorldGenMinable(block.getDefaultState(), 10);
			worldgen.generate(world, rand, pos);
		}
	}
}
