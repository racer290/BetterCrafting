package racer290.bettercrafting;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe;

public class BetterCraftingConfiguration {
	
	private static Configuration config;
	
	public static boolean ctEnable = true;
	public static boolean recipeEnable = true;
	
	public static void load(File file) {
		
		BetterCraftingConfiguration.config = new Configuration(file);
		
		Property pro;
		String category;
		String key;
		String comment;
		
		category = "Crafting";
		comment = "Settings in this category will affect the balance of the crafting mechanics.";
		
		BetterCraftingConfiguration.config.addCustomCategoryComment(category, comment);
		
		comment = "Controls the default duration for one crafting operation. Measured in ticks, must be positive.";
		key = "Default crafting duration";
		
		pro = BetterCraftingConfiguration.config.get(category, key, 200);
		pro.setComment(comment);
		pro.setRequiresMcRestart(true);
		BaseCubicCraftingRecipe.DEFAULT_TICKS_PER_OPERATION = pro.getInt();
		
		category = "Integration";
		comment = "Settings in this category will turn several mod integration plugins off/on.";
		
		BetterCraftingConfiguration.config.setCategoryComment(category, comment);
		
		comment = "Controls the activation of the CraftTweaker integration plugin. Must be either \"true\" or \"false\"";
		key = "CraftTweaker integration";
		
		pro = BetterCraftingConfiguration.config.get(category, key, true);
		pro.setComment(comment);
		pro.setRequiresMcRestart(true);
		ctEnable = pro.getBoolean();
		
		category = "Misc";
		comment = "Settings in this category will control several other features of the mod.";
		
		BetterCraftingConfiguration.config.addCustomCategoryComment(category, comment);
		
		comment = "Controls the activation of the builting recipes of the mod (these may include tweaking/removal of vanilla recipes). Must be either \"true\" or \"false\"";
		key = "Builtin recipes";
		
		pro = BetterCraftingConfiguration.config.get(category, key, true);
		pro.setComment(comment);
		pro.setRequiresMcRestart(true);
		recipeEnable = pro.getBoolean();
		
		BetterCraftingConfiguration.config.save();
		
	}
	
}
