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

package com.goncalomb.bukkit.customitems.api;

import org.bukkit.Material;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.material.MaterialData;


public abstract class CustomBow extends CustomItem {

	protected CustomBow(String slug, String name) {
		super(slug, name, new MaterialData(Material.BOW));
	}

	public void onShootBow(EntityShootBowEvent event, DelayedPlayerDetails details) { }

	public void onProjectileHit(ProjectileHitEvent event, DelayedPlayerDetails details) { }

	public void onProjectileDamageEntity(EntityDamageByEntityEvent event, DelayedPlayerDetails details) { }
}
