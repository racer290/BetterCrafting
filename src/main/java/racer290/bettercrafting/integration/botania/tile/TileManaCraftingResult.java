package racer290.bettercrafting.integration.botania.tile;

import java.util.Map.Entry;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemHandlerHelper;
import racer290.bettercrafting.BetterCrafting;
import racer290.bettercrafting.integration.botania.BotaniaHelper;
import racer290.bettercrafting.integration.botania.crafting.BaseManaCubicCraftingRecipe;
import racer290.bettercrafting.integration.botania.crafting.BaseManaCubicCraftingRecipe.RealRuneConstellation;
import racer290.bettercrafting.tile.TileModInventory;
import racer290.bettercrafting.util.BetterMathHelper.CubicMatrix3x3;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.api.state.enums.PylonVariant;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.block.ModFluffBlocks;
import vazkii.botania.common.block.tile.mana.TilePool;

public class TileManaCraftingResult extends TileModInventory implements ITickable {
	
	public static ImmutableMap<BlockPos, IBlockState> MULTIBLOCK_MAPPINGS;
	
	private BaseManaCubicCraftingRecipe recipe;
	private int ticksCrafting;
	
	@Override
	public void update() {
		
		if (this.world.isRemote) return;
		
		if (!this.getInventory().getStackInSlot(0).isEmpty()) return;
		
		for (Entry<BlockPos, IBlockState> current : MULTIBLOCK_MAPPINGS.entrySet()) {
			
			IBlockState state = this.world.getBlockState(current.getKey().add(this.getPos()));
			
			if (state.getBlock() != current.getValue().getBlock()) return;
			if (state.getBlock() == ModBlocks.pylon && !state.equals(current.getValue())) return;
			
		}
		
		CubicMatrix3x3<RealRuneConstellation> cachedMatrix = this.getMatrixAndRunes();
		
		for (int angle = 0; angle < 4; angle++) {
			
			if (this.recipe == null) {
				this.recipe = BotaniaHelper.craftingManager.getRecipeForMatrix(cachedMatrix.rotateY(angle, new RealRuneConstellation(ItemStack.EMPTY, ItemStack.EMPTY)));
			}
			
		}
		
		if (this.recipe == null) return;
		
		BetterCrafting.LOGGER.info("recipe found for: " + this.recipe.getOutput().getDisplayName());
		
		BetterCrafting.LOGGER.info("mana: " + this.getCurrentMana());
		
		if (this.getCurrentMana() < this.recipe.getRequiredMana()) return;
		
		this.ticksCrafting++;
		
		if (this.ticksCrafting < this.recipe.getTicks()) return;
		
		int manaLeft = this.getCurrentMana();
		int poolsLeft = 4;
		
		for (BlockPos current : MULTIBLOCK_MAPPINGS.keySet()) {
			
			if (this.world.getBlockState(this.pos.add(current)).getBlock() == BotaniaHelper.manaCraftingSlot) {
				
				((TileManaCraftingSlot) this.getWorld().getTileEntity(this.getPos().add(current))).clear();
				
			} else if (this.world.getBlockState(this.pos.add(current)).getBlock() == ModBlocks.pool) {
				
				TilePool pool = (TilePool) this.getWorld().getTileEntity(this.getPos().add(current));
				
				int reduce = manaLeft / poolsLeft;
				pool.recieveMana(-reduce);
				
				manaLeft -= reduce;
				poolsLeft--;
				
			}
			
		}
		
		BetterCrafting.LOGGER.info("recipe finished");
		
		this.getInventory().setStackInSlot(0, this.recipe.getOutput().copy());
		
		this.ticksCrafting = 0;
		this.recipe = null;
		
	}
	
	public CubicMatrix3x3<RealRuneConstellation> getMatrixAndRunes() {
		
		CubicMatrix3x3<RealRuneConstellation> ret = new CubicMatrix3x3<>(new RealRuneConstellation(ItemStack.EMPTY, ItemStack.EMPTY));
		
		for (Entry<BlockPos, IBlockState> e : MULTIBLOCK_MAPPINGS.entrySet()) {
			
			if (e.getValue().getBlock() == BotaniaHelper.manaCraftingSlot) {
				
				if (!(this.getWorld().getTileEntity(e.getKey().add(this.getPos())) instanceof TileManaCraftingSlot)) return null;
				
				TileManaCraftingSlot tile = (TileManaCraftingSlot) this.getWorld().getTileEntity(this.getPos().add(e.getKey()));
				
				int x = e.getKey().getX() + 1;
				int y = e.getKey().getY() + 5;
				int z = e.getKey().getZ() + 1;
				
				ret.set(y, z, x, new RealRuneConstellation(tile.getStoredItem(), tile.getStoredRune()));
				
			}
			
		}
		
		return ret;
		
	}
	
	public int getCurrentMana() {
		
		int mana = 0;
		
		for (Entry<BlockPos, IBlockState> current : MULTIBLOCK_MAPPINGS.entrySet()) {
			
			if (current.getValue().getBlock().equals(ModBlocks.pool) && this.getWorld().getTileEntity(this.getPos().add(current.getKey())) instanceof TilePool) {
				
				mana += ((TilePool) this.getWorld().getTileEntity(this.getPos().add(current.getKey()))).getCurrentMana();
				
			}
			
		}
		
		return mana;
		
	}
	
	public ItemStack getStoredItem() {
		
		return this.getInventory().getStackInSlot(0);
		
	}
	
	public void rightClick(EntityPlayer player, EnumHand hand) {
		
		ItemHandlerHelper.giveItemToPlayer(player, this.getInventory().getStackInSlot(0), player.inventory.currentItem);
		
		this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
		
	}
	
	public TileManaCraftingResult() {
		
		super();
		
		this.getInventory().setSize(1);
		this.getInventory().setSlotLimit(0, 1);
		
		this.ticksCrafting = 0;
		this.recipe = null;
		
	}
	
	static {
		
		MULTIBLOCK_MAPPINGS = ImmutableMap.<BlockPos, IBlockState> builder().put(new BlockPos(-2, -6, -2), ModBlocks.livingrock.getDefaultState()).put(new BlockPos(-1, -6, -2), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(0, -6, -2), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(1, -6, -2), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(2, -6, -2), ModBlocks.livingrock.getDefaultState()).put(new BlockPos(-2, -6, -1), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(2, -6, -1), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(-2, -6, 0), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(2, -6, 0), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(-2, -6, 1), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(2, -6, 1), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(-2, -6, 2), ModBlocks.livingrock.getDefaultState()).put(new BlockPos(-1, -6, 2), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(0, -6, 2), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(1, -6, 2), ModFluffBlocks.livingrockSlab.getDefaultState()).put(new BlockPos(2, -6, 2), ModBlocks.livingrock.getDefaultState()).put(new BlockPos(-2, -5, -2), ModBlocks.pool.getDefaultState()).put(new BlockPos(2, -5, -2), ModBlocks.pool.getDefaultState()).put(new BlockPos(-2, -5, 2), ModBlocks.pool.getDefaultState()).put(new BlockPos(2, -5, 2), ModBlocks.pool.getDefaultState()).put(new BlockPos(-2, -2, -2), ModBlocks.pylon.getDefaultState().withProperty(BotaniaStateProps.PYLON_VARIANT, PylonVariant.NATURA)).put(new BlockPos(2, -2, -2), ModBlocks.pylon.getDefaultState().withProperty(BotaniaStateProps.PYLON_VARIANT, PylonVariant.NATURA)).put(new BlockPos(-2, -2, 2), ModBlocks.pylon.getDefaultState().withProperty(BotaniaStateProps.PYLON_VARIANT, PylonVariant.NATURA)).put(new BlockPos(2, -2, 2), ModBlocks.pylon.getDefaultState().withProperty(BotaniaStateProps.PYLON_VARIANT, PylonVariant.NATURA)).put(new BlockPos(-1, -5, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -5, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -5, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -5, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -5, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -5, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -5, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -5, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -5, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -4, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -4, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -4, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -4, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -4, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -4, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -4, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -4, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -4, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -3, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -3, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -3, -1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -3, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -3, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -3, 0), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(-1, -3, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(0, -3, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).put(new BlockPos(1, -3, 1), BotaniaHelper.manaCraftingSlot.getDefaultState()).build();
		
	}
	
}
