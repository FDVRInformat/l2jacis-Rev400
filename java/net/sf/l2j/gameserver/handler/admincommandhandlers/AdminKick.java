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

import java.util.Collection;
import java.util.StringTokenizer;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.L2World;
import net.sf.l2j.gameserver.model.actor.Player;

public class AdminKick implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_character_disconnect",
		"admin_kick",
		"admin_kick_non_gm"
	};
	
	@Override
	public boolean useAdminCommand(String command, Player activeChar)
	{
		if (command.equals("admin_character_disconnect") || command.equals("admin_kick"))
			disconnectCharacter(activeChar);
		
		if (command.startsWith("admin_kick"))
		{
			StringTokenizer st = new StringTokenizer(command);
			if (st.countTokens() > 1)
			{
				st.nextToken();
				String player = st.nextToken();
				Player plyr = L2World.getInstance().getPlayer(player);
				if (plyr != null)
				{
					plyr.logout();
					activeChar.sendMessage(plyr.getName() + " have been kicked from server.");
				}
			}
		}
		
		if (command.startsWith("admin_kick_non_gm"))
		{
			int counter = 0;
			Collection<Player> pls = L2World.getInstance().getAllPlayers().values();
			
			for (Player player : pls)
			{
				if (!player.isGM())
				{
					counter++;
					player.logout();
				}
			}
			activeChar.sendMessage("A total of " + counter + " players have been kicked.");
		}
		return true;
	}
	
	private static void disconnectCharacter(Player activeChar)
	{
		L2Object target = activeChar.getTarget();
		Player player = null;
		
		if (target instanceof Player)
			player = (Player) target;
		else
			return;
		
		if (player == activeChar)
			activeChar.sendMessage("You cannot disconnect your own character.");
		else
		{
			activeChar.sendMessage(player.getName() + " have been kicked from server.");
			player.logout();
		}
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}