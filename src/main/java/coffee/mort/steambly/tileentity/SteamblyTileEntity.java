package coffee.mort.steambly.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;

abstract public class SteamblyTileEntity extends TileEntity {
	public SteamblyTileEntity() {}

	// Returning true will make the TE sync to the client once
	// syncToClient() has been called.
	public boolean shouldSyncToClient() {
		return false;
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		if (!shouldSyncToClient())
			return super.getUpdateTag();

		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		if (!shouldSyncToClient())
			return super.getUpdatePacket();

		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new SPacketUpdateTileEntity(getPos(), 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		this.readFromNBT(packet.getNbtCompound());
	}

	public void syncToClient() {
		markDirty();
		IBlockState state = getWorld().getBlockState(getPos());
		getWorld().notifyBlockUpdate(getPos(), state, state, 3);
	}
}

