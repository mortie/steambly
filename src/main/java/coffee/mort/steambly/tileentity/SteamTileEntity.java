package coffee.mort.steambly.tileentity;

import coffee.mort.steambly.block.SteamBlock;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.nbt.NBTTagCompound;

abstract public class SteamTileEntity extends SteamblyTileEntity implements ITickable {
	private int steamAmount;

	public SteamTileEntity() {
		steamAmount = getSteamVolume();
	}

	abstract public int getSteamVolume();

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		steamAmount = nbt.getInteger("steamAmount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("steamAmount", steamAmount);
		return nbt;
	}

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			onServerUpdate();
		}
	}

	public void onServerUpdate() {
		rebalance();
	}

	private void rebalance() {
		List<EnumFacing> connected = SteamBlock.getConnectedSteamBlocks(
			this.getWorld(),
			this.pos);

		for (EnumFacing direction: connected) {
			TileEntity te = this.getWorld().getTileEntity(this.pos.offset(direction));
			if (!(te instanceof SteamTileEntity))
				break;

			balance((SteamTileEntity)te);
		}
	}

	public int getSteamAmount() {
		return steamAmount;
	}

	public void addSteam(int amount) {
		steamAmount += amount;
	}
	public void removeSteam(int amount) {
		steamAmount -= amount;
	}

	public double getPressure() {
		return (double)steamAmount / (double)getSteamVolume();
	}

	private void balance(SteamTileEntity te) {
		double diff = getPressure() - te.getPressure();
		if (diff > 0) {
			removeSteam(1);
			te.addSteam(1);
		} else if (diff < 0) {
			te.removeSteam(1);
			addSteam(1);
		}
	}
}
