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

/**
 * ddddd
 */
public class RecipeShopItemInfo extends L2GameServerPacket
{
	private final Player _player;
	private final int _recipeId;
	
	public RecipeShopItemInfo(Player player, int recipeId)
	{
		_player = player;
		_recipeId = recipeId;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xda);
		writeD(_player.getObjectId());
		writeD(_recipeId);
		writeD((int) _player.getCurrentMp());
		writeD(_player.getMaxMp());
		writeD(0xffffffff);
	}
}