package coffee.mort.steambly.block;

import coffee.mort.steambly.tileentity.ConveyorBeltTileEntity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;
import net.minecraft.tileentity.TileEntity;

public class ConveyorBeltBlock extends SteamBlock {
	public ConveyorBeltBlock() {
		super("conveyor_belt");
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(
			IBlockState state,
			IBlockAccess world,
			BlockPos pos) {

		return new AxisAlignedBB(
			0D, 0D, 0D,
			1D, 0.5D, 1D);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new ConveyorBeltTileEntity();
	}
}
