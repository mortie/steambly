package coffee.mort.steambly.proxy;

import coffee.mort.steambly.tileentity.ConveyorBeltTileEntity;
import coffee.mort.steambly.tileentity.PlatePresserTileEntity;
import coffee.mort.steambly.tileentity.CraftingPresserTileEntity;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerTESR() {
		ClientRegistry.bindTileEntitySpecialRenderer(
			ConveyorBeltTileEntity.class,
			new ConveyorBeltTileEntity.CustomRenderer());

		ClientRegistry.bindTileEntitySpecialRenderer(
			PlatePresserTileEntity.class,
			new PlatePresserTileEntity.CustomRenderer());

		ClientRegistry.bindTileEntitySpecialRenderer(
			CraftingPresserTileEntity.class,
			new CraftingPresserTileEntity.CustomRenderer());
	}
}
