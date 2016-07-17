package coffee.mort.steambly.item;

import coffee.mort.steambly.Steambly;

import net.minecraft.item.Item;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.BlockRenderLayer;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class SteamblyItem extends Item {
	private final String name;

	public SteamblyItem(String name, int maxStackSize) {
		super();

		this.name = name;

		setUnlocalizedName(Steambly.MODID+"."+name);
		setRegistryName(Steambly.MODID+":"+name);

		GameRegistry.register(this);

		setCreativeTab(Steambly.creativeTab);
		setMaxStackSize(maxStackSize);
	}

	public SteamblyItem(String name) {
		this(name, 64);
	}

	public String getName() { return name; }
}
