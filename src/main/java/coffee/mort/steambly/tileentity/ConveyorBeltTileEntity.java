package coffee.mort.steambly.tileentity;

import coffee.mort.steambly.block.ConveyorBeltBlock;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ConveyorBeltTileEntity extends SteamTileEntity implements IInventory {
	private static final int moveItemTimeout = 20;

	private int moveItemCounter = moveItemTimeout;
	private ItemStack[] slots = new ItemStack[6];
	private VisualizedItem[] visualItems = new VisualizedItem[slots.length];

	public ConveyorBeltTileEntity() {}

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

	private void visualizeItems() {
		for (int i = 0; i < slots.length; ++i) {
			VisualizedItem e = visualItems[i];

			if (e != null)
				e.setDead();

			visualItems[i] = null;
		}

		for (int i = 0; i < slots.length; ++i) {
			ItemStack stack = slots[i];
			if (stack == null)
				continue;

			VisualizedItem e = new VisualizedItem(
				getWorld(),
				pos,
				stack);

			visualItems[i] = e;
			getWorld().spawnEntityInWorld(e);
		}
	}

	private static class VisualizedItem extends EntityHanging {
		private static final DataParameter<Optional<ItemStack>> ITEM =
			EntityDataManager.<Optional<ItemStack>>createKey(
				VisualizedItem.class,
				DataSerializers.OPTIONAL_ITEM_STACK);
		private ItemStack stack;

		public VisualizedItem(World world, BlockPos pos, @Nullable ItemStack stack) {
			super(world, pos);

			stack = stack.copy();
			stack.stackSize = 1;
			this.stack = stack;

			System.out.println("making thing");
			System.out.println(stack);
			this.getDataManager().set(ITEM, Optional.fromNullable(stack));
		}

		@Override
		protected void entityInit() {
			this.getDataManager().register(ITEM, Optional.<ItemStack>absent());
		}

		@Override
		public int getHeightPixels() {
			return 8;
		}
		@Override
		public int getWidthPixels() {
			return 8;
		}

		@Override
		public void writeEntityToNBT(NBTTagCompound compound) {}
		@Override
		public void readEntityFromNBT(NBTTagCompound compound) {}
		@Override
		public void playPlaceSound() {}
		@Override
		public void onBroken(Entity e) {}
	}

	private void updateInventory() {
		for (int i = 1; i < slots.length; ++i) {
			if (slots[i] != null && slots[i - 1] == null) {
				slots[i - 1] = slots[i];
				slots[i] = null;
			}
		}
	}

	private void moveItems() {
		ItemStack stack = slots[0];
		slots[0] = null;

		if (stack == null)
			return;

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

	/*
	private static class VisualizedItem extends EntityItem {
		private double x, y, z;

		VisualizedItem(
				World world,
				double x,
				double y,
				double z,
				ItemStack stack) {

			super(world, x, y, z, stack);
			this.x = x;
			this.y = y;
			this.z = z;

			this.hoverStart = 0F;
			this.rotationYaw = 32F;
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
			this.setInfinitePickupDelay();
			this.lifespan = moveItemTimeout;
		}

		@Override
		protected void entityInit() {
			this.hoverStart = 0F;
			this.rotationYaw = 32F;
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
			this.setInfinitePickupDelay();
			this.lifespan = moveItemTimeout;
			super.entityInit();
		}

		@Override
		public void moveEntity(double x, double y, double z) {}

		@Override
		public void addVelocity(double x, double y, double z) {}

		@Override
		public void onUpdate() {
			super.onUpdate();
			this.motionY = 0F;
			this.posX = x;
			this.prevPosX = x;
			this.posY = y;
			this.prevPosY = y;
			this.posZ = z;
			this.prevPosZ = z;
			this.rotationYaw = 32F;
		}
	}
	*/

	@Override
	public void update() {
		moveItemCounter -= 1;
		if (moveItemCounter <= 0) {
			moveItemCounter = moveItemTimeout;
			updateInventory();
			visualizeItems();
			moveItems();
		}

		super.update();
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
