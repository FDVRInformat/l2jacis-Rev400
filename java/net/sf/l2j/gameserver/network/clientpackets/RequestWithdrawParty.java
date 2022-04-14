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

import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.L2Party.MessageType;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.entity.events.tournaments.Arena1x1;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoom;
import net.sf.l2j.gameserver.model.partymatching.PartyMatchRoomList;
import net.sf.l2j.gameserver.network.serverpackets.ExClosePartyRoom;
import net.sf.l2j.gameserver.network.serverpackets.ExPartyRoomMember;
import net.sf.l2j.gameserver.network.serverpackets.PartyMatchDetail;

public final class RequestWithdrawParty extends L2GameClientPacket
{
	@Override
	protected void readImpl()
	{
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getClient().getActiveChar();
		if (player == null)
			return;
		
		final L2Party party = player.getParty();
		if (party == null)
			return;
		
		if (player.isArenaProtection())
		{
			player.sendMessage("You can't exit party in Tournament Event.");
			return;
		}
		
		if (party.isInDimensionalRift() && !party.getDimensionalRift().getRevivedAtWaitingRoom().contains(player) || player.isInArenaEvent() || Arena1x1.getInstance().isRegistered(player))
			player.sendMessage("You can't exit party when you are in Tournament Event.");
		else
		{
			party.removePartyMember(player, MessageType.Left);
			
			if (player.isInPartyMatchRoom())
			{
				PartyMatchRoom _room = PartyMatchRoomList.getInstance().getPlayerRoom(player);
				if (_room != null)
				{
					player.sendPacket(new PartyMatchDetail(player, _room));
					player.sendPacket(new ExPartyRoomMember(player, _room, 0));
					player.sendPacket(ExClosePartyRoom.STATIC_PACKET);
					
					_room.deleteMember(player);
				}
				player.setPartyRoom(0);
				player.broadcastUserInfo();
			}
		}
	}
}