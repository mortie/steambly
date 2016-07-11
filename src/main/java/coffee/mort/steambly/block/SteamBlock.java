package coffee.mort.steambly.block;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

abstract public class SteamBlock extends SteamblyBlockContainer {
	SteamBlock(String name) {
		super(Material.IRON, name);
	}

	public static boolean canBlockConnect(
			IBlockAccess world,
			BlockPos pos,
			EnumFacing direction) {

		Block block = world.getBlockState(pos.offset(direction)).getBlock();
		return block instanceof SteamBlock;
	}

	public static List<EnumFacing> getConnectedSteamBlocks(
			IBlockAccess world,
			BlockPos pos) {

		List<EnumFacing> connected = new ArrayList<EnumFacing>();
		if (canBlockConnect(world, pos, EnumFacing.DOWN))
			connected.add(EnumFacing.DOWN);
		if (canBlockConnect(world, pos, EnumFacing.UP))
			connected.add(EnumFacing.UP);
		if (canBlockConnect(world, pos, EnumFacing.NORTH))
			connected.add(EnumFacing.NORTH);
		if (canBlockConnect(world, pos, EnumFacing.SOUTH))
			connected.add(EnumFacing.SOUTH);
		if (canBlockConnect(world, pos, EnumFacing.WEST))
			connected.add(EnumFacing.WEST);
		if (canBlockConnect(world, pos, EnumFacing.EAST))
			connected.add(EnumFacing.EAST);

		return connected;
	}
}
