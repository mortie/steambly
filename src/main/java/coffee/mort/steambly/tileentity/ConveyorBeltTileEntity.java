package coffee.mort.steambly.tileentity;

import javax.annotation.Nullable;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ConveyorBeltTileEntity extends SteamTileEntity implements IInventory {

	private ItemStack[] slots = new ItemStack[6];
	private String customName = null;

	public ConveyorBeltTileEntity() {}

	@Override
	public int getSteamVolume() {
		return 20;
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots[index];
	}

	/*
	 * TODO
	 */

	@Override
	public @Nullable ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public @Nullable ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stace) {
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {};

	@Override
	public void closeInventory(EntityPlayer player) {};

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
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
		for (int i = 0; i < slots.length; ++i) {
			slots[i] = null;
		}
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.conveyor_belt";
	}

	@Override
	public boolean hasCustomName() {
		return customName != null;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}
}
