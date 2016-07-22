package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.SteamTileEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

abstract public class SteamBlock extends SteamblyBlock {
	SteamBlock(String name) {
		super(name);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }

	public static boolean canBlockConnect(
			IBlockAccess world,
			BlockPos pos,
			EnumFacing direction) {

		Block block = world.getBlockState(pos.offset(direction)).getBlock();
		return block instanceof SteamBlock;
	}

	public static List<EnumFacing> getConnectedSteamBlocks(
			IBlockAccess world,
			BlockPos pos) {

		List<EnumFacing> connected = new ArrayList<EnumFacing>();
		if (canBlockConnect(world, pos, EnumFacing.DOWN))
			connected.add(EnumFacing.DOWN);
		if (canBlockConnect(world, pos, EnumFacing.UP))
			connected.add(EnumFacing.UP);
		if (canBlockConnect(world, pos, EnumFacing.NORTH))
			connected.add(EnumFacing.NORTH);
		if (canBlockConnect(world, pos, EnumFacing.SOUTH))
			connected.add(EnumFacing.SOUTH);
		if (canBlockConnect(world, pos, EnumFacing.WEST))
			connected.add(EnumFacing.WEST);
		if (canBlockConnect(world, pos, EnumFacing.EAST))
			connected.add(EnumFacing.EAST);

		return connected;
	}

	/*
	 * Print steam info if right clicked with an empty hand
	 */
	@Override
	public boolean onBlockActivated(
			World world,
			BlockPos pos,
			IBlockState state,
			EntityPlayer player,
			EnumHand hand,
			@Nullable ItemStack heldItem,
			EnumFacing side,
			float hitX, float hitY, float hitZ) {

		if (heldItem == null && player.isSneaking()) {
			SteamTileEntity te = (SteamTileEntity)world.getTileEntity(pos);
			if (te == null)
				return false;

			if (!world.isRemote) {
				player.addChatComponentMessage(new TextComponentString(
					"Volume: "+te.getSteamVolume()+", "+
					"Steam: "+te.getSteamAmount()+", "+
					"Pressure: "+te.getPressure()));
			}

			return true;
		}

		return false;
	}
}
