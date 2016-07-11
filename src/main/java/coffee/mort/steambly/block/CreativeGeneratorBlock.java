package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.CreativeGeneratorTileEntity;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class CreativeGeneratorBlock extends SteamBlock {
	public CreativeGeneratorBlock() {
		super("creativeGenerator");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new CreativeGeneratorTileEntity();
	}
}
