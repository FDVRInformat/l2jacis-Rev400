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
package net.sf.l2j.gameserver.model.partymatching;

import java.util.HashMap;
import java.util.Map;

import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ExClosePartyRoom;

/**
 * @author Gnacik
 */
public class PartyMatchRoomList
{
	private int _maxid = 1;
	private final Map<Integer, PartyMatchRoom> _rooms;
	
	protected PartyMatchRoomList()
	{
		_rooms = new HashMap<>();
	}
	
	public synchronized void addPartyMatchRoom(int id, PartyMatchRoom room)
	{
		_rooms.put(id, room);
		_maxid++;
	}
	
	public void deleteRoom(int id)
	{
		for (Player _member : getRoom(id).getPartyMembers())
		{
			if (_member == null)
				continue;
			
			_member.sendPacket(ExClosePartyRoom.STATIC_PACKET);
			_member.sendPacket(SystemMessageId.PARTY_ROOM_DISBANDED);
			
			_member.setPartyRoom(0);
			_member.broadcastUserInfo();
		}
		_rooms.remove(id);
	}
	
	public PartyMatchRoom getRoom(int id)
	{
		return _rooms.get(id);
	}
	
	public PartyMatchRoom[] getRooms()
	{
		return _rooms.values().toArray(new PartyMatchRoom[_rooms.size()]);
	}
	
	public int getPartyMatchRoomCount()
	{
		return _rooms.size();
	}
	
	public int getMaxId()
	{
		return _maxid;
	}
	
	public PartyMatchRoom getPlayerRoom(Player player)
	{
		for (PartyMatchRoom _room : _rooms.values())
			for (Player member : _room.getPartyMembers())
				if (member.equals(player))
					return _room;
		
		return null;
	}
	
	public int getPlayerRoomId(Player player)
	{
		for (PartyMatchRoom _room : _rooms.values())
			for (Player member : _room.getPartyMembers())
				if (member.equals(player))
					return _room.getId();
		
		return -1;
	}
	
	public static PartyMatchRoomList getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static class SingletonHolder
	{
		protected static final PartyMatchRoomList _instance = new PartyMatchRoomList();
	}
}