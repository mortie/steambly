package coffee.mort.steambly.recipes;

import coffee.mort.steambly.Steambly;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SteamblyRecipes {
	public static void register() {
		GameRegistry.addSmelting(
			Steambly.blockChromiumOre,
			new ItemStack(Steambly.itemChromiumIngot),
			5);
		GameRegistry.addSmelting(
			Steambly.blockNickelOre,
			new ItemStack(Steambly.itemNickelIngot),
			5);
	}
}
