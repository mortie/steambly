package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.PneumaticPressTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class PneumaticPressBaseBlock extends SteamBlock {
	public static final PropertyBool EXTENDED = PropertyBool.create("extended");

	public PneumaticPressBaseBlock() {
		super("pneumatic_press_base");

		this.setDefaultState(this.blockState.getBaseState()
			.withProperty(EXTENDED, false));
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new PneumaticPressTileEntity();
	}

	@Override
	public void onBlockPlacedBy(
			World world,
			BlockPos pos,
			IBlockState state,
			EntityLivingBase player,
			ItemStack stack) {

		if (!world.isRemote)
			checkForMove(world, pos, state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block) {
		if (!world.isRemote)
			checkForMove(world, pos, state);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote)
			checkForMove(world, pos, state);
	}

	private void checkForMove(World world, BlockPos pos, IBlockState state) {
		boolean shouldExtend = shouldBeExtended(world, pos);
		if (shouldExtend == state.getValue(EXTENDED))
			return;

		if (shouldExtend && !state.getValue(EXTENDED)) {
			if (canExtend(world, pos))
				doMove(world, pos, true);
		} else {
			doMove(world, pos, false);
		}
	}

	private boolean shouldBeExtended(World world, BlockPos pos) {
		EnumFacing[] faces = {
			EnumFacing.UP,
			EnumFacing.NORTH,
			EnumFacing.SOUTH,
			EnumFacing.WEST,
			EnumFacing.EAST
		};

		for (EnumFacing face: faces) {
			BlockPos p = pos.offset(face);
			if (world.isSidePowered(p, face))
				return true;
		}

		return false;
	}

	private boolean canExtend(World world, BlockPos pos) {
		if (world.isAirBlock(pos))
			return true;

		return false;
	}

	private void doMove(World world, BlockPos pos, boolean extend) {
		PneumaticPressTileEntity te =
			(PneumaticPressTileEntity)world.getTileEntity(pos);
		te.doMove(extend);

		if (extend == false) {
			world.setBlockState(
				pos,
				this.getDefaultState().withProperty(EXTENDED, true),
				1 | 2);
		}
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
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {
			EXTENDED
		});
	}
}
