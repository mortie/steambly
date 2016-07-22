package coffee.mort.steambly.block;

import coffee.mort.steambly.Steambly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.BlockRenderLayer;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class SteamblyBlock extends Block {
	private final String name;

	public SteamblyBlock(Material m, String name) {
		super(m);

		this.name = name;

		setUnlocalizedName(Steambly.MODID+"."+name);
		setRegistryName(Steambly.MODID+":"+name);

		GameRegistry.register(this);
		GameRegistry.register(
			new ItemBlock(this),
			new ResourceLocation(Steambly.MODID+":"+name));

		setCreativeTab(Steambly.creativeTab);
	}
	public SteamblyBlock(String name) {
		this(Material.IRON, name);
	}

	public String getName() { return name; }
}
