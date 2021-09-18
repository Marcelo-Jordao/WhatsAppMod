
package net.mcreator.whatsappmod.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.HoeItem;

import net.mcreator.whatsappmod.itemgroup.WhatsAppModItemGroup;
import net.mcreator.whatsappmod.WhatsappmodModElements;

@WhatsappmodModElements.ModElement.Tag
public class WhatsHoeItem extends WhatsappmodModElements.ModElement {
	@ObjectHolder("whatsappmod:whats_hoe")
	public static final Item block = null;
	public WhatsHoeItem(WhatsappmodModElements instance) {
		super(instance, 9);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new HoeItem(new IItemTier() {
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
		}, 0, -3f, new Item.Properties().group(WhatsAppModItemGroup.tab)) {
		}.setRegistryName("whats_hoe"));
	}
}
