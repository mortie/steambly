package coffee.mort.steambly;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Steambly.MODID, version = Steambly.VERSION)
public class Steambly {
	public static final String MODID = "steambly";
	public static final String VERSION = "0.0.0";

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@EventHandler
	public void init(FMLInitializationEvent evnt)
	{
	}
}
