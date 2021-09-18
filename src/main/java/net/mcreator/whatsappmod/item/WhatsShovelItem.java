
package net.mcreator.whatsappmod.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;

import net.mcreator.whatsappmod.itemgroup.WhatsAppModItemGroup;
import net.mcreator.whatsappmod.WhatsappmodModElements;

@WhatsappmodModElements.ModElement.Tag
public class WhatsShovelItem extends WhatsappmodModElements.ModElement {
	@ObjectHolder("whatsappmod:whats_shovel")
	public static final Item block = null;
	public WhatsShovelItem(WhatsappmodModElements instance) {
		super(instance, 8);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ShovelItem(new IItemTier() {
			public int getMaxUses() {
				return 441;
			}

			public float getEfficiency() {
				return 8f;
			}

			public float getAttackDamage() {
				return 1f;
			}

			public int getHarvestLevel() {
				return 3;
			}

			public int getEnchantability() {
				return 21;
			}

			public Ingredient getRepairMaterial() {
				return Ingredient.fromStacks(new ItemStack(WhatsAppItem.block));
			}
		}, 1, -3f, new Item.Properties().group(WhatsAppModItemGroup.tab)) {
		}.setRegistryName("whats_shovel"));
	}
}
