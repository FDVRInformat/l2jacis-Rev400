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

import net.sf.l2j.gameserver.datatables.HennaTable;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.HennaEquipList;

/**
 * RequestHennaList
 * @author Tempy
 */
public final class RequestHennaList extends L2GameClientPacket
{
	@SuppressWarnings("unused")
	private int _unknown;
	
	@Override
	protected void readImpl()
	{
		_unknown = readD(); // ??
	}
	
	@Override
	protected void runImpl()
	{
		final Player activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		activeChar.sendPacket(new HennaEquipList(activeChar, HennaTable.getInstance().getAvailableHenna(activeChar.getClassId().getId())));
	}
}