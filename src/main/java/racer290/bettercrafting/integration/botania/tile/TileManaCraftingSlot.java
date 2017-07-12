package racer290.bettercrafting.integration.botania.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.ItemHandlerHelper;
import racer290.bettercrafting.tile.TileCraftingSlot;
import vazkii.botania.common.item.ModItems;

public class TileManaCraftingSlot extends TileCraftingSlot {
	
	public TileManaCraftingSlot() {
		
		super();
		
		this.itemHandler = new CustomItemHandler(new int[] { 1, 1 }, this);
		
	}
	
	@Override
	public void rightClick(EntityPlayer activator, EnumHand hand) {
		
		ItemStack cachedStack = activator.getHeldItem(hand);
		
		if (!cachedStack.isEmpty()) {
			
			if (cachedStack.getItem().equals(ModItems.rune) && this.getInventory().getStackInSlot(1).isEmpty()) {
				
				this.getInventory().setStackInSlot(1, ItemHandlerHelper.copyStackWithSize(cachedStack, 1));
				cachedStack.shrink(1);
				
			} else if (this.getInventory().getStackInSlot(0).isEmpty()) {
				
				this.getInventory().setStackInSlot(0, ItemHandlerHelper.copyStackWithSize(cachedStack, 1));
				cachedStack.shrink(1);
				
			}
			
			return;
			
		}
		
		if (!this.getInventory().getStackInSlot(0).isEmpty()) {
			
			ItemHandlerHelper.giveItemToPlayer(activator, this.getInventory().getStackInSlot(0), activator.inventory.currentItem);
			this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
			
			return;
			
		}
		
		if (!this.getInventory().getStackInSlot(1).isEmpty()) {
			
			ItemHandlerHelper.giveItemToPlayer(activator, this.getInventory().getStackInSlot(1), activator.inventory.currentItem);
			this.getInventory().setStackInSlot(1, ItemStack.EMPTY);
			
			return;
			
		}
		
	}
	
	@Override
	public ItemStack getStoredItem() {
		
		return this.getInventory().getStackInSlot(0);
		
	}
	
	public ItemStack getStoredRune() {
		
		return this.getInventory().getStackInSlot(1);
		
	}
	
	@Override
	public void clear() {
		
		this.getInventory().setStackInSlot(0, ItemStack.EMPTY);
		this.getInventory().setStackInSlot(1, ItemStack.EMPTY);
		
	}
	
}
