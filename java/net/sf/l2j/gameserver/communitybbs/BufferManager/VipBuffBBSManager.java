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

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.BaseBBSManager;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

/**
 * @author Juvenil Walker
 *
 */
public class VipBuffBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, Player activeChar)
	{
		if (command.startsWith("_bbsvip"))
		{
			if (!checkAllowed(activeChar))
			{
				return;
			}
			String val = command.substring(8);
			StringTokenizer st = new StringTokenizer(val, "_");
			
			String a = st.nextToken();
			int id = Integer.parseInt(a);
			String b = st.nextToken();
			int lvl = Integer.parseInt(b);
			
			if (activeChar.isVip())
			{
				
				L2Skill skill = SkillTable.getInstance().getInfo(id, lvl);
				if (skill != null)
				{
					skill.getEffects(activeChar, activeChar);
					MagicSkillUse mgc = new MagicSkillUse(activeChar, activeChar, id, lvl, 1150, 0);
					activeChar.sendPacket(mgc);
					activeChar.broadcastPacket(mgc);
				}
				
				String filename = "data/html/CommunityBoard/top/VipBuffer.htm";
				String content = HtmCache.getInstance().getHtm(filename);
				
				separateAndSend(content, activeChar);
			}
			else
			activeChar.sendMessage("Buy premium, tho use service. EN");
			activeChar.sendMessage("Compre premium, use service. PT");
			String filename = "data/html/CommunityBoard/top/VipBuffer.htm";
			String content = HtmCache.getInstance().getHtm(filename);
			separateAndSend(content, activeChar);
		}
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
	
	public static VipBuffBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final VipBuffBBSManager INSTANCE = new VipBuffBBSManager();
	}	
}
