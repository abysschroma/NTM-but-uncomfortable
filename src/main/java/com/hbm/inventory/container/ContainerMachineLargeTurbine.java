package com.hbm.inventory.container;

import com.hbm.inventory.SlotMachineOutput;
import com.hbm.tileentity.machine.TileEntityMachineLargeTurbine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMachineLargeTurbine extends Container {

	private TileEntityMachineLargeTurbine turbine;

	public ContainerMachineLargeTurbine(InventoryPlayer invPlayer, TileEntityMachineLargeTurbine tedf) {

		turbine = tedf;

		//Fluid ID
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 0, 8, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 1, 8, 53));
		//Input IO
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 2, 44, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 3, 44, 53));
		//Battery
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 4, 98, 53));
		//Output IO
		this.addSlotToContainer(new SlotItemHandler(tedf.inventory, 5, 152, 17));
		this.addSlotToContainer(new SlotMachineOutput(tedf.inventory, 6, 152, 53));

		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int par2)
    {
		ItemStack var3 = ItemStack.EMPTY;
		Slot var4 = (Slot) this.inventorySlots.get(par2);

		if (var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();

            if (par2 <= 6) {
				if (!this.mergeItemStack(var5, 7, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(var5, 4, 5, false))
			{
				if (!this.mergeItemStack(var5, 2, 3, false))
					if (!this.mergeItemStack(var5, 5, 6, false))
						if (!this.mergeItemStack(var5, 0, 1, false))
							return ItemStack.EMPTY;
			}

			if (var5.isEmpty())
			{
				var4.putStack(ItemStack.EMPTY);
			}
			else
			{
				var4.onSlotChanged();
			}
		}

		return var3;
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return turbine.isUseableByPlayer(player);
	}
}