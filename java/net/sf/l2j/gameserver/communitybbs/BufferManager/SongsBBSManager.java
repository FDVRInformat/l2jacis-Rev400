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
public class SongsBBSManager extends BaseBBSManager
{
	@Override
	public void parseCmd(String command, Player activeChar)
	{
		if (command.startsWith("_bbself"))
		{
			if (!checkAllowed(activeChar))
			{
				return;
			}
			
			String val2 = command.substring(8);
			StringTokenizer st2 = new StringTokenizer(val2, "_");
			
			String a2 = st2.nextToken();
			int id2 = Integer.parseInt(a2);
			String b2 = st2.nextToken();
			int lvl2 = Integer.parseInt(b2);
			
			L2Skill skill2 = SkillTable.getInstance().getInfo(id2, lvl2);
			if (skill2 != null)
			{
				skill2.getEffects(activeChar, activeChar);
				MagicSkillUse mgc = new MagicSkillUse(activeChar, activeChar, id2, lvl2, 1150, 0);
				activeChar.sendPacket(mgc);
				activeChar.broadcastPacket(mgc);
			}
			
			String filename = "data/html/CommunityBoard/top/songs.htm";
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
	
	public static SongsBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final SongsBBSManager INSTANCE = new SongsBBSManager();
	}
}
