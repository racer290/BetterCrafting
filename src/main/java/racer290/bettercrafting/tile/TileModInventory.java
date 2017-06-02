package racer290.bettercrafting.tile;

import java.util.Arrays;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import racer290.bettercrafting.BetterCrafting;

/**
 * This file is very similar to <a href=
 * "https://github.com/Vazkii/Botania/blob/1.11/src/main/java/vazkii/botania/common/block/tile/TileSimpleInventory.java">{@code TileSimpleInventory}</a>.
 * by Vazkii. It is based on it and really helped me out. Thanks Vazkii!
 */
public class TileModInventory extends TileEntity {
	
	protected CustomItemHandler itemHandler;
	
	public TileModInventory() {
		
		this.itemHandler = new CustomItemHandler(this);
		
	}
	
	public TileModInventory(int size) {
		
		this.itemHandler = new CustomItemHandler(size, this);
		
	}
	
	public CustomItemHandler getInventory() {
		
		return this.itemHandler;
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		
		super.readFromNBT(compound);
		
		this.itemHandler = new CustomItemHandler(this);
		this.itemHandler.deserializeNBT(compound.hasKey("inventory") ? compound.getCompoundTag("inventory") : new NBTTagCompound());
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		
		super.writeToNBT(compound);
		
		compound.setTag("inventory", this.itemHandler.serializeNBT());
		
		return compound;
		
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		
		super.onDataPacket(net, pkt);
		this.readFromNBT(pkt.getNbtCompound());
		
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		
		return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), this.getUpdateTag());
		
	}
	
	@Override
	public NBTTagCompound getUpdateTag() {
		
		return this.writeToNBT(new NBTTagCompound());
		
	}
	
	@Override
	public boolean hasCapability(@Nonnull Capability<?> cap, EnumFacing side) {
		
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(cap, side);
		
	}
	
	@Override
	public <T> T getCapability(@Nonnull Capability<T> cap, EnumFacing side) {
		
		return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.itemHandler) : super.getCapability(cap, side);
		
	}
	
	public class CustomItemHandler extends ItemStackHandler {
		
		private boolean changeable;
		private int[] stackLimits;
		
		TileModInventory tile;
		
		public CustomItemHandler(TileModInventory tile) {
			
			this(1, tile);
			
		}
		
		public CustomItemHandler(int size, TileModInventory tile) {
			
			this(IntStream.generate(() -> 64).limit(size).toArray(), tile);
			
		}
		
		public CustomItemHandler(int[] slotLimits, TileModInventory tile) {
			
			this(NonNullList.withSize(slotLimits.length, ItemStack.EMPTY), true, slotLimits, tile);
			
		}
		
		public CustomItemHandler(NonNullList<ItemStack> inventory, boolean changeable, int[] slotLimits, TileModInventory tile) {
			
			super(inventory);
			
			if (slotLimits.length != inventory.size()) {
				
				this.stackLimits = Arrays.stream(Arrays.copyOf(slotLimits, inventory.size())).map(i -> i == 0 ? 64 : Math.min(i, 1)).toArray();
				
			} else {
				this.stackLimits = slotLimits;
			}
			
			this.changeable = changeable;
			
			this.tile = tile;
			
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			
			BetterCrafting.LOGGER.info("Item: " + stack.getDisplayName() + ", metadata: " + stack.getMetadata());
			
			return this.changeable ? stack : super.insertItem(slot, stack, simulate);
			
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			
			return this.changeable ? ItemStack.EMPTY : super.extractItem(slot, amount, simulate);
			
		}
		
		@Override
		public ItemStack getStackInSlot(int slot) {
			
			return this.stacks.get(slot).copy();
			
		}
		
		@Override
		public int getSlotLimit(int slot) {
			
			return this.stackLimits[slot];
			
		}
		
		public void setSlotLimit(int slot, int limit) {
			
			this.validateSlotIndex(slot);
			
			this.stackLimits[slot] = limit;
			
		}
		
		public void setChangeable(boolean changeable) {
			
			this.changeable = changeable;
			
		}
		
		public boolean getChangeable() {
			
			return this.changeable;
			
		}
		
		@Override
		public NBTTagCompound serializeNBT() {
			
			NBTTagCompound compound = super.serializeNBT();
			
			compound.setBoolean("canChange", this.changeable);
			compound.setIntArray("limits", this.stackLimits);
			
			return compound;
			
		}
		
		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			
			super.deserializeNBT(nbt);
			
			this.changeable = nbt.hasKey("canChange") ? nbt.getBoolean("canChange") : true;
			
			this.stackLimits = new int[this.getSlots()];
			Arrays.fill(this.stackLimits, 64);
			
			if (!nbt.hasKey("limits", NBT.TAG_INT_ARRAY)) return;
			
			int[] nbtarr = nbt.getIntArray("limits");
			
			for (int slot = 0; slot < nbt.getIntArray("limits").length; slot++) {
				
				this.stackLimits[slot] = nbtarr[slot];
				
			}
			
		}
		
		@Override
		public void onContentsChanged(int slot) {
			
			if (this.tile.getWorld() instanceof WorldServer) {
				
				PlayerChunkMapEntry chunkie = ((WorldServer) this.tile.getWorld()).getPlayerChunkMap().getEntry(this.tile.getPos().getX() / 16, this.tile.getPos().getZ() / 16);
				
				if (chunkie != null) {
					
					chunkie.sendPacket(this.tile.getUpdatePacket());
					
				}
				
			}
			
			this.tile.markDirty();
			
		}
		
	}
	
}
