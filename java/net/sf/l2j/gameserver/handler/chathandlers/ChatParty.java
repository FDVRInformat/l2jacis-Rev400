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
package net.sf.l2j.gameserver.handler.chathandlers;

import net.sf.l2j.gameserver.handler.IChatHandler;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.CreatureSay;

/**
 * A chat handler
 * @author durgus
 */
public class ChatParty implements IChatHandler
{
	private static final int[] COMMAND_IDS =
	{
		3
	};
	
	/**
	 * Handle chat type 'party'
	 * @see net.sf.l2j.gameserver.handler.IChatHandler#handleChat(int, net.sf.l2j.gameserver.model.actor.Player, java.lang.String, java.lang.String)
	 */
	@Override
	public void handleChat(int type, Player activeChar, String target, String text)
	{
		if (activeChar.isInParty())
		{
			CreatureSay cs = new CreatureSay(activeChar.getObjectId(), type, activeChar.getName(), text);
			
			activeChar.getParty().broadcastToPartyMembers(cs);
		}
	}
	
	/**
	 * Returns the chat types registered to this handler
	 * @see net.sf.l2j.gameserver.handler.IChatHandler#getChatTypeList()
	 */
	@Override
	public int[] getChatTypeList()
	{
		return COMMAND_IDS;
	}
}
