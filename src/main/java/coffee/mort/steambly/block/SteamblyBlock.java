package coffee.mort.steambly.block;

import coffee.mort.steambly.Steambly;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class SteamblyBlock extends Block {
	private final String name;

	public SteamblyBlock(Material m, String name) {
		super(m);

		this.name = name;
		setHardness(2F);

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

	public String getName() {
		return name;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}
}
