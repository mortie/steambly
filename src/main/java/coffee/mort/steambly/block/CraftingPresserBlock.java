package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.CraftingPresserTileEntity;

import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;

public class CraftingPresserBlock extends PresserBlock {
	public CraftingPresserBlock() {
		super("crafting_presser");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new CraftingPresserTileEntity();
	}
}
