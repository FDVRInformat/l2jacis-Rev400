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

import java.util.logging.Logger;

import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.Player;

/**
 * This class handles following admin commands:
 * <ul>
 * <li>admin_unblockip</li>
 * </ul>
 */
public class AdminUnblockIp implements IAdminCommandHandler
{
	private static final Logger _log = Logger.getLogger(AdminUnblockIp.class.getName());
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_unblockip"
	};
	
	@Override
	public boolean useAdminCommand(String command, Player activeChar)
	{
		if (command.startsWith("admin_unblockip "))
		{
			try
			{
				String ipAddress = command.substring(16);
				if (unblockIp(ipAddress, activeChar))
					activeChar.sendMessage("Removed IP " + ipAddress + " from blocklist!");
			}
			catch (StringIndexOutOfBoundsException e)
			{
				// Send syntax to the user
				activeChar.sendMessage("Usage mode: //unblockip <ip>");
			}
		}
		
		return true;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
	
	private static boolean unblockIp(String ipAddress, Player activeChar)
	{
		_log.warning("IP removed by GM " + activeChar.getName());
		return true;
	}
}