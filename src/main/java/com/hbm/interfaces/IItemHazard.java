package com.hbm.interfaces;

import com.hbm.modules.ItemHazardModule;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public interface IItemHazard {
	
	public ItemHazardModule getModule();
	
	public default IItemHazard addRadiation(float radiation) {
		this.getModule().addRadiation(radiation);
		return this;
	}

	public default IItemHazard addAlpha(float radiation) { this.getModule().radiation.setAlpha(radiation); return this; }
	public default IItemHazard addBeta(float radiation) { this.getModule().radiation.setBeta(radiation); return this; }
	public default IItemHazard addGamma(float radiation) { this.getModule().radiation.setGamma(radiation); return this; }
	public default IItemHazard addNeutrons(float radiation) { this.getModule().radiation.setNeutrons(radiation); return this; }
	public default IItemHazard addRadon(float radiation) { this.getModule().radiation.setRadon(radiation); return this; }
	
	public default IItemHazard addDigamma(float digamma) {
		this.getModule().addDigamma(digamma);
		return this;
	}
	
	public default IItemHazard addFire(int fire) {
		this.getModule().addFire(fire);
		return this;
	}

	public default IItemHazard addCryogenic(int fire) {
		this.getModule().addCryogenic(fire);
		return this;
	}

	public default IItemHazard addToxic(int fire) {
		this.getModule().addToxic(fire);
		return this;
	}
	
	public default IItemHazard addAsbestos(int asbestos) {
		this.getModule().addAsbestos(asbestos);
		return this;
	}
	
	public default IItemHazard addCoal(int coal) {
		this.getModule().addCoal(coal);
		return this;
	}
	
	public default IItemHazard addBlinding() {
		this.getModule().addBlinding();
		return this;
	}
	
	public default IItemHazard addHydroReactivity() {
		this.getModule().addHydroReactivity();
		return this;
	}

	public default IItemHazard addHydroReactivity(int period) {
		this.getModule().addAlkaline(period);
		return this;
	}
	
	public default IItemHazard addExplosive(float bang) {
		this.getModule().addExplosive(bang);
		return this;
	}

	public default IItemHazard addSharp(float sharpness) {
		this.getModule().addSharp(sharpness/100);
		return this;
	}

	public default IItemHazard addSharpUnscaled(float sharpness) {
		this.getModule().addSharp(sharpness);
		return this;
	}

	public default boolean isRadioactive(){
		return this.getModule().isRadioactive();
	}
	
	//the only ugly part of this entire system is the manual casting so that the rest of the daisychained setters work
	public default Item toItem() {
		return (Item)this;
	}
	
	public default Block toBlock() {
		return (Block)this;
	}
}