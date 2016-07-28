package coffee.mort.steambly.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class CraftingPresserTileEntity extends PresserTileEntity {
	public ItemStack[] recipe = new ItemStack[9];
	public ItemStack[] slots = new ItemStack[recipe.length];

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

	public static class CustomRenderer extends TileEntitySpecialRenderer<CraftingPresserTileEntity> {
		public CustomRenderer() {}

		@Override
		public void renderTileEntityAt(
				CraftingPresserTileEntity te,
				double tx, double ty, double tz,
				float partialTicks, int destroyStage) {

			for (int i = 0; i < te.recipe.length; ++i) {
				ItemStack s = te.recipe[i];

				double x = tx + 0.5;
				double y = ty + 0.5;
				double z = tz + 0.5;

				x += ((i % 3) - 1) * 0.8;
				z += ((i / 3) - 1) * 0.8;

				GL11.glPushMatrix();
				GL11.glTranslated(x, y, z);
				Minecraft.getMinecraft().getRenderItem().renderItem(
					s, ItemCameraTransforms.TransformType.GROUND);
				GL11.glPopMatrix();
			}
		}
	}
}
