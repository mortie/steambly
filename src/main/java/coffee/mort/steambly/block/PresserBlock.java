package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.PresserTileEntity;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

abstract public class PresserBlock extends SteamblyBlock {
	public PresserBlock(String name) {
		super(name);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
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
}
