package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.PlatePresserTileEntity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PlatePresserBlock extends PresserBlock {
	public PlatePresserBlock() {
		super("plate_presser");
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new PlatePresserTileEntity();
	}
}
