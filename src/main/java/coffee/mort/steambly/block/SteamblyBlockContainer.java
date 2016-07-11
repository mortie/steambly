package coffee.mort.steambly.block;

import coffee.mort.steambly.Steambly;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class SteamblyBlockContainer extends BlockContainer {
	private final String name;

	public SteamblyBlockContainer(Material m, String name) {
		super(m);
		this.name = name;

		setCreativeTab(Steambly.creativeTab);

		setUnlocalizedName(Steambly.MODID+"."+name);
		GameRegistry.registerBlock(this, name);
	}

	public String getName() {
		return name;
	}
}
