package coffee.mort.steambly.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class PlatePresserTileEntity extends PresserTileEntity {

	@Override
	public boolean onRightClick(
			EntityPlayer player,
			ItemStack heldItem,
			float hitX, float hitY, float hitZ) {

		return true;
	}

	@Override
	public boolean onEmptyRightClick(
			EntityPlayer player,
			float hitX, float hitY, float hitZ) {

		return true;
	}

	@Override
	public void onPress() {}

	public static class CustomRenderer extends TileEntitySpecialRenderer<PlatePresserTileEntity> {

		@Override
		public void renderTileEntityAt(
				PlatePresserTileEntity te,
				double tx, double ty, double tz,
				float partialTicks, int destroyStage) {
		}
	}
}
