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
package net.sf.l2j.gameserver.network.clientpackets;

import net.sf.l2j.gameserver.datatables.ClanTable;
import net.sf.l2j.gameserver.model.L2Clan;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.SystemMessage;

public final class RequestSurrenderPledgeWar extends L2GameClientPacket
{
	private String _pledgeName;
	
	@Override
	protected void readImpl()
	{
		_pledgeName = readS();
	}
	
	@Override
	protected void runImpl()
	{
		final Player activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		final L2Clan playerClan = activeChar.getClan();
		if (playerClan == null)
			return;
		
		// Check if player who does the request has the correct rights to do it
		if ((activeChar.getClanPrivileges() & L2Clan.CP_CL_PLEDGE_WAR) != L2Clan.CP_CL_PLEDGE_WAR)
		{
			activeChar.sendPacket(SystemMessageId.YOU_ARE_NOT_AUTHORIZED_TO_DO_THAT);
			return;
		}
		
		final L2Clan clan = ClanTable.getInstance().getClanByName(_pledgeName);
		if (clan == null)
			return;
		
		if (!playerClan.isAtWarWith(clan.getClanId()))
		{
			activeChar.sendPacket(SystemMessageId.NOT_INVOLVED_IN_WAR);
			return;
		}
		
		activeChar.deathPenalty(false, false, false);
		activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.YOU_HAVE_SURRENDERED_TO_THE_S1_CLAN).addString(_pledgeName));
		ClanTable.getInstance().deleteClansWars(playerClan.getClanId(), clan.getClanId());
	}
}