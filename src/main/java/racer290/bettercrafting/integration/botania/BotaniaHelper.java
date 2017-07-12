package racer290.bettercrafting.integration.botania;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.Level;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.block.ModBlock;
import racer290.bettercrafting.crafting.BaseCubicCraftingRecipe.Ingredient;
import racer290.bettercrafting.integration.botania.block.BlockManaCraftingResult;
import racer290.bettercrafting.integration.botania.block.BlockManaCraftingSlot;
import racer290.bettercrafting.integration.botania.crafting.BaseManaCubicCraftingRecipe.RecipeRuneConstellation;
import racer290.bettercrafting.integration.botania.crafting.ManaCubicCraftingManager;
import racer290.bettercrafting.integration.botania.crafting.ShapedManaCubicCraftingRecipe;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;
import vazkii.botania.common.item.ModItems;

public class BotaniaHelper {
	
	public static BlockManaCraftingSlot manaCraftingSlot;
	public static BlockManaCraftingResult manaCraftingResult;
	
	public static ManaCubicCraftingManager craftingManager = new ManaCubicCraftingManager();
	
	public static void registerBlocks() {
		
		manaCraftingSlot = new BlockManaCraftingSlot();
		manaCraftingResult = new BlockManaCraftingResult();
		
	}
	
	public static void registerRecipes() {
		
		try {
			
			CubicMatrix3x3<RecipeRuneConstellation> in = ShapedManaCubicCraftingRecipe.nullMatrix();
			
			in.set(0, 0, 0, new RecipeRuneConstellation(new Ingredient(Items.SLIME_BALL), 0));
			in.set(0, 0, 2, new RecipeRuneConstellation(new Ingredient(Items.COOKIE), 1));
			in.set(2, 0, 0, new RecipeRuneConstellation(new Ingredient(Items.CLAY_BALL), 2));
			in.set(2, 0, 2, new RecipeRuneConstellation(new Ingredient(Items.FEATHER), 3));
			in.set(1, 1, 1, new RecipeRuneConstellation(new Ingredient(ModItems.manaBottle), 8));
			in.set(0, 2, 0, new RecipeRuneConstellation(new Ingredient(Items.MELON), 5));
			in.set(0, 2, 2, new RecipeRuneConstellation(new Ingredient(Items.WHEAT_SEEDS), 4));
			in.set(2, 2, 0, new RecipeRuneConstellation(new Ingredient(Blocks.LEAVES), 6));
			in.set(2, 2, 2, new RecipeRuneConstellation(new Ingredient(Items.SNOWBALL), 7));
			
			ShapedManaCubicCraftingRecipe recipe = new ShapedManaCubicCraftingRecipe(in, new ItemStack(Items.APPLE), 100, 200, true);
			
			craftingManager.registerRecipe(recipe);
			
		} catch (IllegalArgumentException ex) {
			
			if (!ex.getMessage().equals("Tried to construct invalid recipe")) throw ex;
			
			BetterCrafting.LOGGER.error("Something went wrong during construction of the botania compat recipes! Registration aborted.");
			
			for (StackTraceElement ste : ex.getStackTrace()) {
				
				BetterCrafting.LOGGER.error(ste.toString());
				
			}
			
		}
		
	}
	
	public static void registerItemBlocks() throws IllegalArgumentException, IllegalAccessException {
		
		for (Field f : BotaniaHelper.class.getFields()) {
			
			Object hold = f.get(null);
			
			if (hold instanceof ModBlock) {
				
				ModBlock block = (ModBlock) hold;
				
				if (block.getRegistryName() != null && block.shouldItemBlockBeRegistered()) {
					
					GameRegistry.register(new ItemBlock(block), block.getRegistryName());
					
				} else {
					
					BetterCrafting.LOGGER.debug("Skipping field " + f.getName() + " during ItemBlock registering, this block will be missing a corresponding inventory variant.");
					
				}
				
			}
			
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerBlockModels() throws IllegalArgumentException, IllegalAccessException {
		
		for (Field f : BotaniaHelper.class.getDeclaredFields()) {
			
			try {
				
				if (f.get(null) instanceof ModBlock) {
					f.get(null).getClass().getMethod("initVisuals", new Class<?>[] {}).invoke(f.get(null), new Object[] {});
				}
				
			} catch (NoSuchMethodException ex) {
				
				BetterCrafting.LOGGER.info("Visuals for field " + f.getName() + " in BlockHelper class not initialized. The field may be of type that does not declare such a method.");
				
			} catch (InvocationTargetException ex) {
				
				BetterCrafting.LOGGER.error("Exception caught while initialiation of visuals for " + f.get(null).getClass().getName() + "! Stack trace:");
				BetterCrafting.LOGGER.catching(Level.ERROR, ex);
				
			}
			
		}
		
	}
	
}
