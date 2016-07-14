package coffee.mort.steambly.block;

import coffee.mort.steambly.block.SteamPipe;
import coffee.mort.steambly.tileentity.BasicSteamPipeTileEntity;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;

public class BasicSteamPipe extends SteamPipe {
	public BasicSteamPipe() {
		super("basic_steam_pipe");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new BasicSteamPipeTileEntity();
	}
}
