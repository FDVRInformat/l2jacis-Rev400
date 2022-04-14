/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.model.actor.instance;

import net.sf.l2j.gameserver.ThreadPool;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;

import Dev.Dungeon.Dungeon;

/**
 * @author Juvenil Walker
 *
 */


public class L2DungeonMobInstance extends L2MonsterInstance
{
	private Dungeon dungeon;
	
	public L2DungeonMobInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public boolean doDie(L2Character killer)
	{
		if (!super.doDie(killer))
			return false;
		
		if(dungeon != null) // It will be dungeon == null when mob is spawned from admin and not from dungeon
			ThreadPool.schedule(() -> dungeon.onMobKill(this), 1000*2);
		
		return true;
	}
	
	public void setDungeon(Dungeon dungeon)
	{
		this.dungeon = dungeon;
	}
}
