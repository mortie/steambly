package coffee.mort.steambly.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.IBlockAccess;

public class OreBlock extends SteamblyBlock {
	public OreBlock(String name) {
		super(Material.ROCK, name);
		setHardness(3F);
	}
}
