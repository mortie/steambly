package coffee.mort.steambly.proxy;

import coffee.mort.steambly.tileentity.ConveyorBeltTileEntity;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerTESR() {
		ClientRegistry.bindTileEntitySpecialRenderer(
			ConveyorBeltTileEntity.class,
			new ConveyorBeltTileEntity.CustomRenderer());
	}
}
