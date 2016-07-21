package coffee.mort.steambly.tileentity;

import coffee.mort.steambly.block.ConveyorBeltBlock;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import org.lwjgl.opengl.GL11;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

public class ConveyorBeltTileEntity extends SteamTileEntity implements IInventory {
	private static final int moveItemTimeout = 4;

	private int moveItemCounter = moveItemTimeout;
	private ItemStack[] slots = new ItemStack[6];

	public ConveyorBeltTileEntity() {}

	@Override
	public boolean shouldSyncToClient() {
		return true;
	}

	public ItemStack[] getSlots() { return slots; }

	private BlockPos getPosInFront() {
		IBlockState state = getWorld().getBlockState(pos);

		EnumFacing facing = state.getValue(ConveyorBeltBlock.FACING);

		ConveyorBeltBlock.TurningType turning =
			(ConveyorBeltBlock.TurningType)state.getValue(ConveyorBeltBlock.TURNING);
		if (turning == ConveyorBeltBlock.TurningType.LEFT) {
			facing = facing.rotateY().getOpposite();
		} else if (turning == ConveyorBeltBlock.TurningType.RIGHT) {
			facing = facing.rotateY();
		}

		return pos.offset(facing);
	}

	public static class CustomRenderer extends TileEntitySpecialRenderer<ConveyorBeltTileEntity> {
		public CustomRenderer() {}

		@Override
		public void renderTileEntityAt(
				ConveyorBeltTileEntity te,
				double x, double y, double z,
				float partialTicks, int destroyStage) {

			ItemStack[] slots = te.getSlots();

			for (int i = 0; i < slots.length; ++i) {
				ItemStack s = slots[i];
				if (s == null)
					continue;

				GL11.glPushMatrix();
				GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
				Minecraft.getMinecraft().getRenderItem().renderItem(
					s, ItemCameraTransforms.TransformType.GROUND);
				GL11.glPopMatrix();
			}
		}
	}

	private void updateInventory() {
		boolean moved = false;
		for (int i = 1; i < slots.length; ++i) {
			if (slots[i] != null && slots[i - 1] == null) {
				moved = true;
				slots[i - 1] = slots[i];
				slots[i] = null;
			}
		}

		if (moved)
			syncToClient();
	}

	private void moveItems() {
		ItemStack stack = slots[0];
		slots[0] = null;

		if (stack == null)
			return;

		syncToClient();

		boolean success = false;

		// Put stack in inventory if it exists
		BlockPos frontpos = getPosInFront();
		TileEntity te = getWorld().getTileEntity(frontpos);
		if (te instanceof IInventory) {
			IInventory inv = (IInventory)te;
			int idx = -1;
			for (int i = inv.getSizeInventory() - 1; i >= 0; --i) {
				if (inv.isItemValidForSlot(i, stack)) {
					idx = i;
					break;
				}
			}

			if (idx != -1) {
				success = true;

				ItemStack nwstack = new ItemStack(
					stack.getItem(), stack.stackSize);
				ItemStack existing = inv.getStackInSlot(idx);

				if (existing != null) {
					nwstack.stackSize += existing.stackSize;
				}

				inv.setInventorySlotContents(idx, nwstack);
			}
		}

		// Drop item if it couldn't be placed in inventory
		if (!success) {
			EntityItem dropped = new EntityItem(
				getWorld(),
				frontpos.getX() + 0.5D,
				frontpos.getY() + 0.3D,
				frontpos.getZ() + 0.5D,
				stack);

			dropped.motionZ *= 0.5;
			dropped.motionY = 0;
			dropped.motionX *= 0.5;

			getWorld().spawnEntityInWorld(dropped);
		}
	}

	@Override
	public void update() {
		moveItemCounter -= 1;
		if (moveItemCounter <= 0 && !getWorld().isRemote) {
			moveItemCounter = moveItemTimeout;
			updateInventory();
			moveItems();
		}

		super.update();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		NBTTagList slotslist = nbt.getTagList("slots", 10);

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

		NBTTagList slotslist = new NBTTagList();

		for (int i = 0; i < slots.length; ++i) {
			if (slots[i] == null)
				continue;

			NBTTagCompound c = new NBTTagCompound();
			c.setByte("slot", (byte)i);
			slots[i].writeToNBT(c);
			slotslist.appendTag(c);
		}

		nbt.setTag("slots", slotslist);

		return nbt;
	}

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

	@Override
	public @Nullable ItemStack decrStackSize(int index, int count) {
		// Only one item per slot
		return removeStackFromSlot(index);
	}

	@Override
	public @Nullable ItemStack removeStackFromSlot(int index) {
		ItemStack s = slots[index];
		slots[index] = null;
		return s;
	}

	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		markDirty();
		slots[slots.length - 1] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {};

	@Override
	public void closeInventory(EntityPlayer player) {};

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return slots[slots.length - 1] == null;
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
		return "container.conveyor_belt";
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
