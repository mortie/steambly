package coffee.mort.steambly.block;

import coffee.mort.steambly.block.SteamBlock;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
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

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState();
	}

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
}
