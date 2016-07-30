package coffee.mort.steambly;

import coffee.mort.steambly.block.SteamblyBlock;
import coffee.mort.steambly.item.SteamblyItem;
import coffee.mort.steambly.tileentity.SteamTileEntity;
import coffee.mort.steambly.proxy.CommonProxy;
import coffee.mort.steambly.worldgen.SteamblyWorldgen;
import coffee.mort.steambly.recipes.SteamblyRecipes;

// Blocks
import coffee.mort.steambly.block.CreativeGeneratorBlock;
import coffee.mort.steambly.block.ConveyorBeltBlock;
import coffee.mort.steambly.block.BasicSteamPipe;
import coffee.mort.steambly.block.ChromiumBlock;
import coffee.mort.steambly.block.HydrosteelBlock;
import coffee.mort.steambly.block.NichromeBlock;
import coffee.mort.steambly.block.NickelBlock;
import coffee.mort.steambly.block.PlatePresserBlock;
import coffee.mort.steambly.block.CraftingPresserBlock;
import coffee.mort.steambly.block.ChromiumOreBlock;
import coffee.mort.steambly.block.NickelOreBlock;

// Items
import coffee.mort.steambly.item.ChromiumIngotItem;
import coffee.mort.steambly.item.HydrosteelIngotItem;
import coffee.mort.steambly.item.NichromeIngotItem;
import coffee.mort.steambly.item.NickelIngotItem;
import coffee.mort.steambly.item.ChromiumPlateItem;
import coffee.mort.steambly.item.HydrosteelPlateItem;
import coffee.mort.steambly.item.NichromePlateItem;
import coffee.mort.steambly.item.NickelPlateItem;

// Tile Entities
import coffee.mort.steambly.tileentity.CreativeGeneratorTileEntity;
import coffee.mort.steambly.tileentity.ConveyorBeltTileEntity;
import coffee.mort.steambly.tileentity.BasicSteamPipeTileEntity;
import coffee.mort.steambly.tileentity.PlatePresserTileEntity;
import coffee.mort.steambly.tileentity.CraftingPresserTileEntity;

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
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.client.registry.ClientRegistry;

@Mod(modid = Steambly.MODID, version = Steambly.VERSION)
public class Steambly {
	public static final String MODID = "steambly";
	public static final String VERSION = "0.0.0";

	@SidedProxy(clientSide="coffee.mort.steambly.proxy.ClientProxy", serverSide="coffee.mort.steambly.proxy.CommonProxy")
	public static CommonProxy proxy;

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
	public static SteamblyBlock blockChromium;
	public static SteamblyBlock blockHydrosteel;
	public static SteamblyBlock blockNichrome;
	public static SteamblyBlock blockNickel;
	public static SteamblyBlock blockPlatePresser;
	public static SteamblyBlock blockCraftingPresser;
	public static SteamblyBlock blockChromiumOre;
	public static SteamblyBlock blockNickelOre;

	// Items
	public static SteamblyItem itemChromiumIngot;
	public static SteamblyItem itemHydrosteelIngot;
	public static SteamblyItem itemNichromeIngot;
	public static SteamblyItem itemNickelIngot;
	public static SteamblyItem itemChromiumPlate;
	public static SteamblyItem itemHydrosteelPlate;
	public static SteamblyItem itemNichromePlate;
	public static SteamblyItem itemNickelPlate;

	// Creative tab
	public static final CreativeTabs creativeTab = new CreativeTabs(Steambly.MODID) {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return Items.STICK;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		// Register worldgen
		GameRegistry.registerWorldGenerator(new SteamblyWorldgen(), 2);

		// Register tile entities
		GameRegistry.registerTileEntity(
			CreativeGeneratorTileEntity.class, "creative_generator_te");
		GameRegistry.registerTileEntity(
			ConveyorBeltTileEntity.class, "conveyor_belt_te");
		GameRegistry.registerTileEntity(
			BasicSteamPipeTileEntity.class, "basic_steam_pipe_te");
		GameRegistry.registerTileEntity(
			PlatePresserTileEntity.class, "plate_presser_te");
		GameRegistry.registerTileEntity(
			CraftingPresserTileEntity.class, "crafting_presser_te");

		// Create block singletons
		blockCreativeGenerator = new CreativeGeneratorBlock();
		blockConveyorBelt = new ConveyorBeltBlock();
		blockBasicSteamPipe = new BasicSteamPipe();
		blockChromium = new ChromiumBlock();
		blockHydrosteel = new HydrosteelBlock();
		blockNichrome = new NichromeBlock();
		blockNickel = new NickelBlock();
		blockPlatePresser = new PlatePresserBlock();
		blockCraftingPresser = new CraftingPresserBlock();
		blockChromiumOre = new ChromiumOreBlock();
		blockNickelOre = new NickelOreBlock();

		// Create item singletons
		itemChromiumIngot = new ChromiumIngotItem();
		itemHydrosteelIngot = new HydrosteelIngotItem();
		itemNichromeIngot = new NichromeIngotItem();
		itemNickelIngot = new NickelIngotItem();
		itemChromiumPlate = new ChromiumPlateItem();
		itemHydrosteelPlate = new HydrosteelPlateItem();
		itemNichromePlate = new NichromePlateItem();
		itemNickelPlate = new NickelPlateItem();

		// Register tile entity special renderers
		proxy.registerTESR();

		// Register recipes
		SteamblyRecipes.register();
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
			addRenderer(ri, blockChromium);
			addRenderer(ri, blockHydrosteel);
			addRenderer(ri, blockNichrome);
			addRenderer(ri, blockNickel);
			addRenderer(ri, blockPlatePresser);
			addRenderer(ri, blockCraftingPresser);
			addRenderer(ri, blockChromiumOre);
			addRenderer(ri, blockNickelOre);

			// Items
			addRenderer(ri, itemChromiumIngot);
			addRenderer(ri, itemHydrosteelIngot);
			addRenderer(ri, itemNichromeIngot);
			addRenderer(ri, itemNickelIngot);
			addRenderer(ri, itemChromiumPlate);
			addRenderer(ri, itemHydrosteelPlate);
			addRenderer(ri, itemNichromePlate);
			addRenderer(ri, itemNickelPlate);
		}
	}
}
