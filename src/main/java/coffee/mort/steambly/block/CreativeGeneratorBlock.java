package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.CreativeGeneratorTileEntity;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;

public class CreativeGeneratorBlock extends SteamBlock {
	public CreativeGeneratorBlock() {
		super("creative_generator");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new CreativeGeneratorTileEntity();
	}
}
