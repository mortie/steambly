package coffee.mort.steambly.block;

import coffee.mort.steambly.block.SteamBlock;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

abstract public class SteamPipe extends SteamBlock {
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");

	public SteamPipe(String name) {
		super(name);

		this.setDefaultState(this.blockState.getBaseState()
			.withProperty(DOWN, false)
			.withProperty(UP, false)
			.withProperty(NORTH, false)
			.withProperty(SOUTH, false)
			.withProperty(WEST, false)
			.withProperty(EAST, false));
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getActualState(
			IBlockState state,
			IBlockAccess world,
			BlockPos pos) {

		boolean down = canBlockConnect(world, pos, EnumFacing.DOWN);
		boolean up = canBlockConnect(world, pos, EnumFacing.UP);
		boolean north = canBlockConnect(world, pos, EnumFacing.NORTH);
		boolean south = canBlockConnect(world, pos, EnumFacing.SOUTH);
		boolean west = canBlockConnect(world, pos, EnumFacing.WEST);
		boolean east = canBlockConnect(world, pos, EnumFacing.EAST);

		state = state
			.withProperty(DOWN, down)
			.withProperty(UP, up)
			.withProperty(NORTH, north)
			.withProperty(SOUTH, south)
			.withProperty(WEST, west)
			.withProperty(EAST, east);

		return state;
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(
			IBlockState state,
			IBlockAccess world, 
			BlockPos pos) {

		state = this.getActualState(state, world, pos);

		double minX = 0.4D;
		double minY = 0.4D;
		double minZ = 0.4D;
		double maxX = 0.6D;
		double maxY = 0.6D;
		double maxZ = 0.6D;

		if (state.getValue(UP))
			maxY = 1D;
		if (state.getValue(DOWN))
			minY = 0D;
		if (state.getValue(NORTH))
			minZ = 0D;
		if (state.getValue(SOUTH))
			maxZ = 1D;
		if (state.getValue(WEST))
			minX = 0D;
		if (state.getValue(EAST))
			maxX = 1D;

		return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {
			DOWN,
			UP,
			NORTH,
			SOUTH,
			EAST,
			WEST
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}
