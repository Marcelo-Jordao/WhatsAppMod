
package net.mcreator.whatsappmod.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.IItemTier;
import net.minecraft.item.AxeItem;

import net.mcreator.whatsappmod.itemgroup.WhatsAppModItemGroup;
import net.mcreator.whatsappmod.WhatsappmodModElements;

@WhatsappmodModElements.ModElement.Tag
public class WhatsAxeItem extends WhatsappmodModElements.ModElement {
	@ObjectHolder("whatsappmod:whats_axe")
	public static final Item block = null;
	public WhatsAxeItem(WhatsappmodModElements instance) {
		super(instance, 6);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new AxeItem(new IItemTier() {
			public int getMaxUses() {
				return 441;
			}

			public float getEfficiency() {
				return 8f;
			}

			public float getAttackDamage() {
				return 12f;
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
		}.setRegistryName("whats_axe"));
	}
}
