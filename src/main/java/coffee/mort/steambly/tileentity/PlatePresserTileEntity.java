package coffee.mort.steambly.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;

public class PlatePresserTileEntity extends PresserTileEntity {

	@Override
	public boolean onRightClick(
			EntityPlayer player,
			EnumFacing side,
			ItemStack heldItem,
			float hitX, float hitY, float hitZ) {

		return true;
	}

	@Override
	public boolean onShiftRightClick(
			EntityPlayer player,
			EnumFacing side,
			ItemStack heldItem,
			float hitX, float hitY, float hitZ) {

		return true;
	}

	@Override
	public void onPress() {}
}
