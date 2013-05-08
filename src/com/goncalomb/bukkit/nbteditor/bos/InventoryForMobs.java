package com.goncalomb.bukkit.nbteditor.bos;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.goncalomb.bukkit.nbteditor.nbt.MobNBT;
import com.goncalomb.bukkit.betterplugin.Lang;

public final class InventoryForMobs extends IInventoryForBos {
	
	private static HashMap<Integer, ItemStack> _placeholders = new HashMap<Integer, ItemStack>();
	
	static {
		_placeholders.put(0, createPlaceholder(Material.PAPER, Lang._("nbt.bos.mob.pholder.head")));
		_placeholders.put(1, createPlaceholder(Material.PAPER, Lang._("nbt.bos.mob.pholder.chest")));
		_placeholders.put(2, createPlaceholder(Material.PAPER, Lang._("nbt.bos.mob.pholder.legs")));
		_placeholders.put(3, createPlaceholder(Material.PAPER, Lang._("nbt.bos.mob.pholder.feet")));
		_placeholders.put(4, createPlaceholder(Material.PAPER, Lang._("nbt.bos.mob.pholder.hand")));
		_placeholders.put(8, createPlaceholder(Material.GLASS_BOTTLE, Lang._("nbt.bos.mob.pholder.effects"), Lang._("nbt.bos.mob.pholder.effects-lore")));
	}
	
	private BookOfSouls _bos;
	
	public InventoryForMobs(BookOfSouls bos, Player owner) {
		super(owner, 9, Lang._("nbt.bos.mob.title") + " - " + ChatColor.BLACK + bos.getEntityNBT().getEntityType().getName(), _placeholders);
		_bos = bos;
		Inventory inv = getInventory();
		MobNBT mob = (MobNBT) _bos.getEntityNBT();
		ItemStack[] equip = mob.getEquipment();
		for (int i = 0; i < 5; ++i) {
			if (equip[i] != null && equip[i].getType() != Material.AIR) {
				inv.setItem(4 - i, equip[i]);
			}
		}
		inv.setItem(5, _itemFiller);
		inv.setItem(6, _itemFiller);
		inv.setItem(7, _itemFiller);
		ItemStack potion = mob.getEffectsAsPotion();
		if (potion != null) {
			inv.setItem(8, potion);
		}
	}

	@Override
	protected void inventoryClick(InventoryClickEvent event) {
		super.inventoryClick(event);
		if (event.getRawSlot() > 4 && event.getRawSlot() < 8) {
			event.setCancelled(true);
		}
		
		int slot = event.getRawSlot();
		boolean isShift = event.isShiftClick();
		ItemStack itemToCheck = null;
		if (isShift && slot > 8 && event.getInventory().firstEmpty() == 8) {
			itemToCheck = event.getCurrentItem();
		} else if (slot == 8 && !isShift && event.getCursor().getType() != Material.AIR) {
			itemToCheck = event.getCursor();
		}
		if (itemToCheck != null) {
			if (itemToCheck.getType() != Material.POTION) {
				((Player)event.getWhoClicked()).sendMessage(Lang._("nbt.bos.mob.potion"));
				event.setCancelled(true);
			}
		}
	}

	@Override
	protected void inventoryClose(InventoryCloseEvent event) {
		MobNBT mob = (MobNBT) _bos.getEntityNBT();
		ItemStack[] items = getContents();
		mob.setEquipment(items[4], items[3], items[2], items[1], items[0]);
		mob.setEffectsFromPotion(items[8]);
		_bos.saveBook();
		((Player)event.getPlayer()).sendMessage(Lang._("nbt.bos.mob.done"));
	}

}
