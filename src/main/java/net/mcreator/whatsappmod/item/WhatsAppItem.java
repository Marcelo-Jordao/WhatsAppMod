
package net.mcreator.whatsappmod.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.whatsappmod.itemgroup.WhatsAppModItemGroup;
import net.mcreator.whatsappmod.WhatsappmodModElements;

@WhatsappmodModElements.ModElement.Tag
public class WhatsAppItem extends WhatsappmodModElements.ModElement {
	@ObjectHolder("whatsappmod:whats_app")
	public static final Item block = null;
	public WhatsAppItem(WhatsappmodModElements instance) {
		super(instance, 1);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(WhatsAppModItemGroup.tab).maxStackSize(17).rarity(Rarity.COMMON));
			setRegistryName("whats_app");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
