package coffee.mort.steambly.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;

abstract public class PresserTileEntity extends SteamblyTileEntity {

	@Override
	public boolean shouldSyncToClient() {
		return true;
	}

	abstract public boolean onRightClick(
			EntityPlayer player,
			ItemStack heldItem,
			float hitX, float hitY, float hitZ);

	abstract public boolean onEmptyRightClick(
			EntityPlayer player,
			float hitX, float hitY, float hitZ);

	abstract public void onPress();
}
