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
package net.sf.l2j.gameserver.handler.admincommandhandlers;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.L2Playable;
import net.sf.l2j.gameserver.model.base.Experience;
import net.sf.l2j.gameserver.network.SystemMessageId;

public class AdminLevel implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_addlevel",
		"admin_setlevel"
	};
	
	@Override
	public boolean useAdminCommand(String command, Player activeChar)
	{
		if (activeChar == null)
			return false;
		
		L2Object targetChar = activeChar.getTarget();
		
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command
		
		String val = "";
		if (st.countTokens() >= 1)
			val = st.nextToken();
		
		if (actualCommand.equalsIgnoreCase("admin_addlevel"))
		{
			try
			{
				if (targetChar instanceof L2Playable)
					((L2Playable) targetChar).getStat().addLevel(Byte.parseByte(val));
			}
			catch (NumberFormatException e)
			{
				activeChar.sendMessage("Wrong number format.");
				return false;
			}
		}
		else if (actualCommand.equalsIgnoreCase("admin_setlevel"))
		{
			try
			{
				if (targetChar == null || !(targetChar instanceof Player))
				{
					activeChar.sendPacket(SystemMessageId.TARGET_IS_INCORRECT); // incorrect target!
					return false;
				}
				Player targetPlayer = (Player) targetChar;
				
				byte lvl = Byte.parseByte(val);
				if (lvl >= 1 && lvl <= Experience.MAX_LEVEL)
				{
					long pXp = targetPlayer.getExp();
					long tXp = Experience.LEVEL[lvl];
					
					if (pXp > tXp)
						targetPlayer.removeExpAndSp(pXp - tXp, 0);
					else if (pXp < tXp)
						targetPlayer.addExpAndSp(tXp - pXp, 0);
				}
				else
				{
					activeChar.sendMessage("You must specify level between 1 and " + Experience.MAX_LEVEL + ".");
					return false;
				}
			}
			catch (NumberFormatException e)
			{
				activeChar.sendMessage("You must specify level between 1 and " + Experience.MAX_LEVEL + ".");
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}