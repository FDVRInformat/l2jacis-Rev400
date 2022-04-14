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
package net.sf.l2j.gameserver.model.actor.knownlist;

import net.sf.l2j.gameserver.model.L2Object;
import net.sf.l2j.gameserver.model.actor.L2Character;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.L2Vehicle;
import net.sf.l2j.gameserver.network.serverpackets.DeleteObject;
import net.sf.l2j.gameserver.network.serverpackets.SpawnItemPoly;

public class PcKnownList extends PlayableKnownList
{
	public PcKnownList(Player activeChar)
	{
		super(activeChar);
	}
	
	public void refreshInfos()
	{
		for (L2Object object : _knownObjects.values())
		{
			if (object instanceof Player && ((Player) object).inObserverMode())
				continue;
			
			sendInfoFrom(object);
		}
	}
	
	private void sendInfoFrom(L2Object object)
	{
		if (object.getPoly().isMorphed() && object.getPoly().getPolyType().equals("item"))
			getActiveChar().sendPacket(new SpawnItemPoly(object));
		else
		{
			object.sendInfo(getActiveChar());
			
			if (object instanceof L2Character)
			{
				// Update the state of the L2Character object client side by sending Server->Client packet MoveToPawn/MoveToLocation and AutoAttackStart to the Player
				L2Character obj = (L2Character) object;
				if (obj.hasAI())
					obj.getAI().describeStateToPlayer(getActiveChar());
			}
		}
	}
	
	/**
	 * Add a visible L2Object to Player _knownObjects and _knownPlayer (if necessary) and send Server-Client Packets needed to inform the Player of its state and actions in progress.<BR>
	 * <BR>
	 * <B><U> object is a ItemInstance </U> :</B><BR>
	 * <BR>
	 * <li>Send Server-Client Packet DropItem/SpawnItem to the Player</li><BR>
	 * <BR>
	 * <B><U> object is a L2DoorInstance </U> :</B><BR>
	 * <BR>
	 * <li>Send Server-Client Packets DoorInfo and DoorStatusUpdate to the Player</li> <li>Send Server->Client packet MoveToPawn/MoveToLocation and AutoAttackStart to the Player</li><BR>
	 * <BR>
	 * <B><U> object is a L2Npc </U> :</B><BR>
	 * <BR>
	 * <li>Send Server-Client Packet NpcInfo to the Player</li> <li>Send Server->Client packet MoveToPawn/MoveToLocation and AutoAttackStart to the Player</li><BR>
	 * <BR>
	 * <B><U> object is a L2Summon </U> :</B><BR>
	 * <BR>
	 * <li>Send Server-Client Packet NpcInfo/PetItemList (if the Player is the owner) to the Player</li> <li>Send Server->Client packet MoveToPawn/MoveToLocation and AutoAttackStart to the Player</li><BR>
	 * <BR>
	 * <B><U> object is a Player </U> :</B><BR>
	 * <BR>
	 * <li>Send Server-Client Packet CharInfo to the Player</li> <li>If the object has a private store, Send Server-Client Packet PrivateStoreMsgSell to the Player</li> <li>Send Server->Client packet MoveToPawn/MoveToLocation and AutoAttackStart to the Player</li><BR>
	 * <BR>
	 * @param object The L2Object to add to _knownObjects and _knownPlayer
	 */
	@Override
	public boolean addKnownObject(L2Object object)
	{
		if (!super.addKnownObject(object))
			return false;
		
		sendInfoFrom(object);
		return true;
	}
	
	/**
	 * Remove a L2Object from Player _knownObjects and _knownPlayer (if necessary) and send Server-Client Packet DeleteObject to the Player.<BR>
	 * <BR>
	 * @param object The L2Object to remove from _knownObjects and _knownPlayer
	 */
	@Override
	public boolean removeKnownObject(L2Object object)
	{
		if (!super.removeKnownObject(object))
			return false;
		
		// Send Server-Client Packet DeleteObject to the Player
		getActiveChar().sendPacket(new DeleteObject(object));
		return true;
	}
	
	@Override
	public final Player getActiveChar()
	{
		return (Player) super.getActiveChar();
	}
	
	@Override
	public int getDistanceToForgetObject(L2Object object)
	{
		return (int) Math.round(1.5 * getDistanceToWatchObject(object));
	}
	
	@Override
	public int getDistanceToWatchObject(L2Object object)
	{
		if (object instanceof L2Vehicle)
			return 8000;
		
		return Math.max(1800, 3600 - (_knownObjects.size() * 20));
	}
}