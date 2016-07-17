package coffee.mort.steambly;

import coffee.mort.steambly.block.SteamblyBlock;
import coffee.mort.steambly.item.SteamblyItem;
import coffee.mort.steambly.tileentity.SteamTileEntity;

// Blocks
import coffee.mort.steambly.block.CreativeGeneratorBlock;
import coffee.mort.steambly.block.ConveyorBeltBlock;
import coffee.mort.steambly.block.BasicSteamPipe;

// Items
import coffee.mort.steambly.item.ChromiumIngotItem;
import coffee.mort.steambly.item.HydrosteelIngotItem;
import coffee.mort.steambly.item.NichromeIngotItem;
import coffee.mort.steambly.item.NickelIngotItem;

// Tile Entities
import coffee.mort.steambly.tileentity.CreativeGeneratorTileEntity;
import coffee.mort.steambly.tileentity.ConveyorBeltTileEntity;
import coffee.mort.steambly.tileentity.BasicSteamPipeTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Steambly.MODID, version = Steambly.VERSION)
public class Steambly {
	public static final String MODID = "steambly";
	public static final String VERSION = "0.0.0";

	private static void addRenderer(RenderItem ri, SteamblyBlock block) {
		ri.getItemModelMesher().register(
			Item.getItemFromBlock(block), 0,
			new ModelResourceLocation(
				Steambly.MODID + ":" + block.getName(), "inventory"));
	}

	private static void addRenderer(RenderItem ri, SteamblyItem item) {
		ri.getItemModelMesher().register(
			item, 0,
			new ModelResourceLocation(
				Steambly.MODID + ":" + item.getName(), "inventory"));
	}

	// Blocks
	public static SteamblyBlock blockCreativeGenerator;
	public static SteamblyBlock blockConveyorBelt;
	public static SteamblyBlock blockBasicSteamPipe;

	// Items
	public static SteamblyItem itemChromiumIngot;
	public static SteamblyItem itemHydrosteelIngot;
	public static SteamblyItem itemNichromeIngot;
	public static SteamblyItem itemNickelIngot;

	// Creative tab
	public static final CreativeTabs creativeTab = new CreativeTabs(Steambly.MODID) {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.STICK;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// Register tile entities
		GameRegistry.registerTileEntity(
			CreativeGeneratorTileEntity.class, "creative_generator_te");
		GameRegistry.registerTileEntity(
			ConveyorBeltTileEntity.class, "conveyor_belt_te");
		GameRegistry.registerTileEntity(
			BasicSteamPipeTileEntity.class, "basic_steam_pipe_te");

		// Create block singletons
		blockCreativeGenerator = new CreativeGeneratorBlock();
		blockConveyorBelt = new ConveyorBeltBlock();
		blockBasicSteamPipe = new BasicSteamPipe();

		// Create item singletons
		itemChromiumIngot = new ChromiumIngotItem();
		itemHydrosteelIngot = new HydrosteelIngotItem();
		itemNichromeIngot = new NichromeIngotItem();
		itemNickelIngot = new NickelIngotItem();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		// Add renderers
		if (event.getSide() == Side.CLIENT) {
			RenderItem ri = Minecraft.getMinecraft().getRenderItem();

			// Blocks
			addRenderer(ri, blockCreativeGenerator);
			addRenderer(ri, blockConveyorBelt);
			addRenderer(ri, blockBasicSteamPipe);

			// Items
			addRenderer(ri, itemChromiumIngot);
			addRenderer(ri, itemHydrosteelIngot);
			addRenderer(ri, itemNichromeIngot);
			addRenderer(ri, itemNickelIngot);
		}
	}
}
