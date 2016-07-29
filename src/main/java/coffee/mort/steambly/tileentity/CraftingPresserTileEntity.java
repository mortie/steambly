package coffee.mort.steambly.tileentity;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class CraftingPresserTileEntity extends PresserTileEntity implements IInventory {
	public ItemStack[] recipe = new ItemStack[9];
	public ItemStack[] slots = new ItemStack[recipe.length];

	public final int maxSlotSize = 1;

	private int getSlot(float x, float y, float z) {
		float s = 0.375F;
		float e = 0.625F;

		if (
				(x <= 0.2 || x >= 0.8) &&
				(z <= 0.2 || z >= 0.8))
			return -1;

		// Not terribly pretty, but oh well.
		if (x <= s && z <= s)
			return 0;
		if (x >= s && x <= e && z <= s)
			return 1;
		if (x >= e && z <= s)
			return 2;

		if (x <= s && z >= s && z <= e)
			return 3;
		if (x >= s && x <= e && z >= s && z <= e)
			return 4;
		if (x >= e && z >= s && z <= e)
			return 5;

		if (x <= s && z >= e)
			return 6;
		if (x >= s && x <= e && z >= e)
			return 7;
		if (x >= e && z >= e)
			return 8;

		return -1;
	}

	@Override
	public boolean onRightClick(
			EntityPlayer player,
			ItemStack heldItem,
			float hitX, float hitY, float hitZ) {

		int slot = getSlot(hitX, hitY, hitZ);
		if (slot == -1)
			return false;

		if (recipe[slot] == null) {
			recipe[slot] = new ItemStack(heldItem.getItem());
		} else if (recipe[slot].getItem() == heldItem.getItem()) {
			ItemStack stack = slots[slot];
			if (stack == null) {
				slots[slot] = new ItemStack(heldItem.getItem());
			} else {
				if (stack.getItem() != heldItem.getItem())
					return true;
				if (stack.stackSize >= maxSlotSize)
					return true;

				stack.stackSize += 1;
			}
			if (!player.capabilities.isCreativeMode)
				heldItem.stackSize -= 1;
		} else {
			return true;
		}

		syncToClient();

		return true;
	}

	@Override
	public boolean onEmptyRightClick(
			EntityPlayer player,
			float hitX, float hitY, float hitZ) {

		int slot = getSlot(hitX, hitY, hitZ);
		if (slot == -1)
			return true;

		if (recipe[slot] == null)
			return true;

		ItemStack stack;
		if (slots[slot] != null) {
			stack = new ItemStack(slots[slot].getItem());
			slots[slot].stackSize -= 1;
			if (slots[slot].stackSize <= 0)
				slots[slot] = null;
		} else {
			stack = new ItemStack(recipe[slot].getItem());
			recipe[slot] = null;
			return true;
		}

		EntityItem dropped = new EntityItem(
			getWorld(),
			pos.getX() + 0.5D,
			pos.getY() + 1.5D,
			pos.getZ() + 0.5D,
			stack);

		if (!getWorld().isRemote)
			getWorld().spawnEntityInWorld(dropped);

		syncToClient();

		return true;
	}

	@Override
	public void onPress() {}

	public static class CustomRenderer extends TileEntitySpecialRenderer<CraftingPresserTileEntity> {

		@Override
		public void renderTileEntityAt(
				CraftingPresserTileEntity te,
				double tx, double ty, double tz,
				float partialTicks, int destroyStage) {

			for (int i = 0; i < te.recipe.length; ++i) {
				if (te.recipe[i] == null)
					continue;

				double x = tx + 0.5;
				double y = ty + 1.01;
				double z = tz + 0.5;

				x += ((i % 3) - 1) * 0.19;
				z += ((i / 3) - 1) * 0.19;

				int len = 1;
				if (te.slots[i] != null)
					len += te.slots[i].stackSize;

				for (int j = 0; j < len; ++j) {
					ItemStack s = te.recipe[i];

					GlStateManager.pushMatrix();

					if (j == 0)
						GlStateManager.translate(x, y, z - 0.056);
					else
						GlStateManager.translate(x, y, z);

					if (j == 0)
						GlStateManager.rotate(90F, 1F, 0F, 0F);

					GlStateManager.scale(0.3, 0.3, 0.3);
					Minecraft.getMinecraft().getRenderItem().renderItem(
						s, ItemCameraTransforms.TransformType.GROUND);
					GlStateManager.popMatrix();

					y += 0.08;
					if (j == 0) {
						y -= 0.025;
					}
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		clear();

		NBTTagList recipelist = nbt.getTagList("recipe", 10);
		NBTTagList slotslist = nbt.getTagList("slots", 10);

		for (int i = 0; i < recipelist.tagCount(); ++i) {
			NBTTagCompound c = recipelist.getCompoundTagAt(i);

			int j = c.getByte("slot") & 255;
			if (j >= 0 && j < recipe.length) {
				recipe[j] = ItemStack.loadItemStackFromNBT(c);
			}
		}

		for (int i = 0; i < slotslist.tagCount(); ++i) {
			NBTTagCompound c = slotslist.getCompoundTagAt(i);

			int j = c.getByte("slot") & 255;
			if (j >= 0 && j < slots.length) {
				slots[j] = ItemStack.loadItemStackFromNBT(c);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		NBTTagList recipelist = new NBTTagList();
		NBTTagList slotslist = new NBTTagList();

		for (int i = 0; i < recipe.length; ++i) {
			if (recipe[i] == null)
				continue;

			NBTTagCompound r = new NBTTagCompound();
			r.setByte("slot", (byte)i);
			recipe[i].writeToNBT(r);
			recipelist.appendTag(r);

			if (slots[i] == null)
				continue;

			NBTTagCompound s = new NBTTagCompound();
			s.setByte("slot", (byte)i);
			slots[i].writeToNBT(s);
			slotslist.appendTag(s);
		}

		nbt.setTag("slots", slotslist);
		nbt.setTag("recipe", recipelist);

		return nbt;
	}

	@Override
	public int getSizeInventory() {
		return recipe.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots[index];
	}

	@Override
	public @Nullable ItemStack decrStackSize(int index, int count) {
		if (slots[index] == null)
			return null;

		ItemStack stack = new ItemStack(slots[index].getItem());
		if (slots[index].stackSize == 1)
			slots[index] = null;
		else
			slots[index].stackSize -= 1;

		syncToClient();
		return stack;
	}

	@Override
	public @Nullable ItemStack removeStackFromSlot(int index) {
		ItemStack stack = slots[index];
		slots[index] = null;
		syncToClient();
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		slots[index] = stack;
		syncToClient();
	}

	@Override
	public int getInventoryStackLimit() {
		return maxSlotSize;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (recipe[index] == null || stack == null)
			return false;

		return recipe[index].getItem() == stack.getItem();
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i < recipe.length; ++i) {
			recipe[i] = null;
			slots[i] = null;
		}
	}

	@Override
	public String getName() {
		return "container.crafting_presser";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}
}
