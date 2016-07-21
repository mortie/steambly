package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.ConveyorBeltTileEntity;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.tileentity.TileEntity;

public class ConveyorBeltBlock extends SteamBlock {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum TURNING =
		PropertyEnum.create("turning", TurningType.class);

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(
		0D, 0D, 0D,
		1D, 0.56D, 1D);

	public ConveyorBeltBlock() {
		super("conveyor_belt");
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(
			IBlockState state,
			IBlockAccess world,
			BlockPos pos) {

		return AABB;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new ConveyorBeltTileEntity();
	}

	@Override
	public IBlockState onBlockPlaced(
			World world,
			BlockPos pos,
			EnumFacing facing,
			float hitX, float hitY, float hitZ,
			int meta,
			EntityLivingBase placer) {

		EnumFacing playerFacing = placer.getHorizontalFacing();

		BlockPos pBack = pos.offset(playerFacing.getOpposite());
		IBlockState sBack = world.getBlockState(pBack);
		Block bBack = sBack.getBlock();
		boolean hasBack = false;
		if (bBack instanceof ConveyorBeltBlock) {
			hasBack = ((
					sBack.getValue(TURNING) == TurningType.STRAIGHT &&
					sBack.getValue(FACING) == playerFacing
				) || (
					sBack.getValue(TURNING) == TurningType.LEFT &&
					sBack.getValue(FACING) == playerFacing.rotateY()
				) || (
					sBack.getValue(TURNING) == TurningType.RIGHT &&
					sBack.getValue(FACING) == playerFacing.rotateY().getOpposite()
				));
		}

		if (hasBack) {
			return this.getDefaultState()
				.withProperty(FACING, playerFacing)
				.withProperty(TURNING, TurningType.STRAIGHT);
		}

		BlockPos pLeft = pos.offset(playerFacing.rotateY().getOpposite());
		IBlockState sLeft = world.getBlockState(pLeft);
		Block bLeft = sLeft.getBlock();
		boolean hasLeft = false;
		if (bLeft instanceof ConveyorBeltBlock) {
			hasLeft = ((
					sLeft.getValue(TURNING) == TurningType.STRAIGHT &&
					sLeft.getValue(FACING) == playerFacing.rotateY()
				) || (
					sLeft.getValue(TURNING) == TurningType.LEFT &&
					sLeft.getValue(FACING) == playerFacing.getOpposite()
				) || (
					sLeft.getValue(TURNING) == TurningType.RIGHT &&
					sLeft.getValue(FACING) == playerFacing
				));
		}

		BlockPos pRight = pos.offset(playerFacing.rotateY());
		IBlockState sRight = world.getBlockState(pRight);
		Block bRight = sRight.getBlock();
		boolean hasRight = false;
		if (bRight instanceof ConveyorBeltBlock) {
			hasRight = ((
					sRight.getValue(TURNING) == TurningType.STRAIGHT &&
					sRight.getValue(FACING) == playerFacing.rotateY().getOpposite()
				) || (
					sRight.getValue(TURNING) == TurningType.LEFT &&
					sRight.getValue(FACING) == playerFacing
				) || (
					sRight.getValue(TURNING) == TurningType.RIGHT &&
					sRight.getValue(FACING) == playerFacing.getOpposite()
				));
		}

		if ((!hasRight && !hasLeft) || (hasRight && hasLeft)) {
			return this.getDefaultState()
				.withProperty(FACING, playerFacing)
				.withProperty(TURNING, TurningType.STRAIGHT);
		} else if (hasLeft) {
			return this.getDefaultState()
				.withProperty(FACING, playerFacing.rotateY())
				.withProperty(TURNING, TurningType.LEFT);
		} else {
			return this.getDefaultState()
				.withProperty(FACING, playerFacing.rotateY().getOpposite())
				.withProperty(TURNING, TurningType.RIGHT);
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {
			FACING,
			TURNING
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();

		if (meta >= 0 && meta < 4)
			state = state.withProperty(FACING, EnumFacing.NORTH);
		else if (meta >= 4 && meta < 8)
			state = state.withProperty(FACING, EnumFacing.SOUTH);
		else if (meta >= 8 && meta < 12)
			state = state.withProperty(FACING, EnumFacing.WEST);
		else
			state = state.withProperty(FACING, EnumFacing.EAST);

		int mod = meta % 4;
		if (mod == 1)
			state = state.withProperty(TURNING, TurningType.LEFT);
		else if (mod == 2)
			state = state.withProperty(TURNING, TurningType.RIGHT);
		else
			state = state.withProperty(TURNING, TurningType.STRAIGHT);

		return state;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMetaFromState(IBlockState state) {

		int facing;
		if (state.getValue(FACING) == EnumFacing.NORTH)
			facing = 0;
		else if (state.getValue(FACING) == EnumFacing.SOUTH)
			facing = 4;
		else if (state.getValue(FACING) == EnumFacing.WEST)
			facing = 8;
		else
			facing = 12;

		int turning;
		if (state.getValue(TURNING) == TurningType.LEFT)
			turning = 1;
		else if (state.getValue(TURNING) == TurningType.RIGHT)
			turning = 2;
		else
			turning = 0;

		return facing + turning;
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

	public enum TurningType implements IStringSerializable {
		STRAIGHT("straight"),
		LEFT("left"),
		RIGHT("right");

		private final String name;

		private TurningType(String name) {
			this.name = name;
		}

		@Override
		public String getName() { return name; }
		@Override
		public String toString() { return name; }
	}
}
