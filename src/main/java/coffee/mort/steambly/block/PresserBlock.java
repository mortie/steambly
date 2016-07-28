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

public class PresserBlock extends SteamblyBlock {
	public PresserBlock(String name) {
		super(name);
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

		if (te == null) {
			System.out.println("Warning: right clicked null tile entity");
			System.out.println(pos);
			return false;
		}

		if (hand != EnumHand.MAIN_HAND)
			return false;

		if (player.isSneaking()) {
			return te.onShiftRightClick(player, side, heldItem, hitX, hitY, hitZ);
		} else {
			if (heldItem == null)
				return false;
			return te.onRightClick(player, side, heldItem, hitX, hitY, hitZ);
		}
	}
}
