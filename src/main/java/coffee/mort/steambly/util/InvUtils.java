package coffee.mort.steambly.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InvUtils {
	public static ItemStack insertInto(ItemStack stack, IInventory inv) {
		if (stack == null)
			return null;

		for (int i = 0; i < inv.getSizeInventory(); ++i) {
			stack = insertInto(stack, i, inv);
			if (stack == null)
				return null;
		}

		return stack;
	}

	public static ItemStack insertInto(ItemStack stack, int index, IInventory inv) {
		if (stack == null)
			return null;

		if (!inv.isItemValidForSlot(index, stack))
			return stack;

		ItemStack existing = inv.getStackInSlot(index);

		if (existing == null) {
			inv.setInventorySlotContents(index, stack);
			return null;
		}

		if (existing.getItem() != stack.getItem())
			return stack;

		int diff = inv.getInventoryStackLimit() - existing.stackSize;
		if (diff <= 0)
			return stack;
		if (diff > stack.stackSize) {
			diff = stack.stackSize;
		}

		existing.stackSize += diff;
		stack.stackSize -= diff;
		inv.setInventorySlotContents(index, existing);

		if (stack.stackSize <= 0)
			return null;
		else
			return stack;
	}
}
