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
package net.sf.l2j.gameserver.network.serverpackets;

import net.sf.l2j.gameserver.model.actor.Player;

public class PrivateStoreMsgBuy extends L2GameServerPacket
{
	private final Player _activeChar;
	private String _storeMsg;
	
	public PrivateStoreMsgBuy(Player player)
	{
		_activeChar = player;
		if (_activeChar.getBuyList() != null)
			_storeMsg = _activeChar.getBuyList().getTitle();
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xb9);
		writeD(_activeChar.getObjectId());
		writeS(_storeMsg);
	}
}