package com.hbm.inventory.container;

import com.hbm.tileentity.machine.TileEntityFireboxBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerFirebox extends Container {
	
	protected TileEntityFireboxBase firebox;
	
	public ContainerFirebox(InventoryPlayer invPlayer, TileEntityFireboxBase furnace) {
		this.firebox = furnace;
		this.firebox.playersUsing++;
		
		this.addSlotToContainer(new SlotItemHandler(furnace.inventory, 0, 44, 27));
		this.addSlotToContainer(new SlotItemHandler(furnace.inventory, 1, 62, 27));
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 144));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack()) {
			ItemStack originalStack = slot.getStack();
			stack = originalStack.copy();

			if(index <= 1) {
				if(!this.mergeItemStack(originalStack, 2, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
				
				slot.onSlotChange(originalStack, stack);
				
			} else if(!this.mergeItemStack(originalStack, 0, 2, false)) {
				return ItemStack.EMPTY;
			}

			if(originalStack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return stack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return firebox.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.firebox.playersUsing--;
	}
}
