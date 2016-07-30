package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.PresserTileEntity;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

abstract public class PresserBlock extends SteamblyBlock {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public PresserBlock(String name) {
		super(name);
	}

	@Override
	public IBlockState onBlockPlaced(
			World world,
			BlockPos pos,
			EnumFacing facing,
			float hitX, float hitY, float hitZ,
			int meta, EntityLivingBase placer) {

		EnumFacing playerFacing = placer.getHorizontalFacing();
		return this.getDefaultState()
			.withProperty(FACING, playerFacing);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {
			FACING
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();

		if (meta == 0)
			return state.withProperty(FACING, EnumFacing.NORTH);
		else if (meta == 1)
			return state.withProperty(FACING, EnumFacing.SOUTH);
		else if (meta == 2)
			return state.withProperty(FACING, EnumFacing.WEST);
		else if (meta == 3)
			return state.withProperty(FACING, EnumFacing.EAST);
		else
			return state.withProperty(FACING, EnumFacing.NORTH);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getMetaFromState(IBlockState state) {
		if (state.getValue(FACING) == EnumFacing.NORTH)
			return 0;
		else if (state.getValue(FACING) == EnumFacing.SOUTH)
			return 1;
		else if (state.getValue(FACING) == EnumFacing.WEST)
			return 2;
		else if (state.getValue(FACING) == EnumFacing.EAST)
			return 3;
		else
			return 0;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public boolean onBlockActivated(
			World world,
			BlockPos pos, 
			IBlockState state,
			EntityPlayer player,
			EnumHand hand,
			@Nullable ItemStack heldItem,
			EnumFacing side,
			float hitX, float hitY, float hitZ) {

		PresserTileEntity te =
			(PresserTileEntity)world.getTileEntity(pos);

		if (hand != EnumHand.MAIN_HAND)
			return false;
		if (side != EnumFacing.UP)
			return false;

		if (heldItem == null)
			return te.onEmptyRightClick(player, hitX, hitY, hitZ);
		else
			return te.onRightClick(player, heldItem, hitX, hitY, hitZ);
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
