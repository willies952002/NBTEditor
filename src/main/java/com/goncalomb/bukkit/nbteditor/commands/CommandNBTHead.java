/*
 * Copyright (C) 2013-2016 Gonçalo Baltazar <me@goncalomb.com>
 *
 * This file is part of NBTEditor.
 *
 * NBTEditor is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NBTEditor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NBTEditor.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.goncalomb.bukkit.nbteditor.commands;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.goncalomb.bukkit.mylib.command.MyCommand;
import com.goncalomb.bukkit.mylib.command.MyCommandException;

public class CommandNBTHead extends MyCommand {

	public CommandNBTHead() {
		super("nbthead", "nbth");
	}

	@Command(args = "", type = CommandType.DEFAULT, minargs = 1, maxargs = 2, usage = "<skull-player-name> [player-name]")
	public boolean headCommand(CommandSender sender, String[] args) throws MyCommandException {
		Player other;
		if (args.length == 2) {
			other = CommandUtils.findPlayer(args[1]);
		} else if (sender instanceof Player) {
			other = (Player) sender;
		} else {
			sender.sendMessage("§cParameter 'player-name' missing!");
			return false;
		}
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		if (meta.setOwner(args[0])) {
			head.setItemMeta(meta);
			CommandUtils.giveItem(other, head);
			sender.sendMessage("§aEnjoy your new head.");
		} else {
			sender.sendMessage("§cPlayer name too long!");
		}
		return true;
	}

	@TabComplete(args = "")
	public List<String> tab(CommandSender sender, String[] args) {
		return CommandUtils.playerTabComplete(sender, args[args.length - 1]);
	}
}
