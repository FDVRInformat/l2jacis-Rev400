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
package net.sf.l2j.gameserver.communitybbs.BufferManager;

import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.BaseBBSManager;
import net.sf.l2j.gameserver.model.actor.L2Summon;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

/**
 * @author Juvenil Walker
 *
 */
public class OpitionsBufferBSSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, Player activeChar)
	{
		if (!checkAllowed(activeChar))
		{
			return;
		}
		
		if (command.equals("_bbscancelbuff"))
		{
			MagicSkillUse mgc = new MagicSkillUse(activeChar, activeChar, 1056, 1, 1150, 0);
			activeChar.sendPacket(mgc);
			activeChar.broadcastPacket(mgc);
			
			activeChar.stopAllEffects();
			
			final L2Summon summon = activeChar.getPet();
			if (summon != null)
			{
				summon.stopAllEffects();
			}
		}
		else if (command.equals("_bbsheal"))
		{
			MagicSkillUse mgc = new MagicSkillUse(activeChar, activeChar, 1217, 1, 1150, 0);
			activeChar.sendPacket(mgc);
			activeChar.broadcastPacket(mgc);
			
			activeChar.setCurrentHpMp(activeChar.getMaxHp(), activeChar.getMaxMp());
			activeChar.setCurrentCp(activeChar.getMaxCp());
			
			final L2Summon summon = activeChar.getPet();
			if (summon != null)
			{
				summon.setCurrentHpMp(summon.getMaxHp(), summon.getMaxMp());
			}
		}
		String filename = "data/html/CommunityBoard/top/Buffer.htm";
		String content = HtmCache.getInstance().getHtm(filename);
		
		separateAndSend(content, activeChar);
	}
	
	public boolean checkAllowed(Player activeChar)
	{
		String msg = null;
		
		if (activeChar.isSitting())
		{
			msg = "You can't use Community Buffer when you sit!";
		}
		else if (activeChar.isCastingNow())
		{
			msg = "You can't use Community Buffer when you cast!";
		}
		else if (activeChar.isDead())
		{
			msg = "You can't use Community Buffer when you dead!";
		}
		else if (activeChar.isInCombat())
		{
			msg = "You can't use Community Buffer when you in combat!";
		}
		
		if (msg != null)
		{
			activeChar.sendMessage(msg);
		}
		
		return msg == null;
	}
	public static OpitionsBufferBSSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final OpitionsBufferBSSManager INSTANCE = new OpitionsBufferBSSManager();
	}
}
