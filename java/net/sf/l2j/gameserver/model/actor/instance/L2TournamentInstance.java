/*
 * Copyright (C) 2004-2013 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.gameserver.model.L2Effect;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.model.entity.events.teamvsteam.TvTEvent;
import net.sf.l2j.gameserver.model.entity.events.tournaments.Arena1x1;
import net.sf.l2j.gameserver.model.entity.events.tournaments.properties.ArenaConfig;
import net.sf.l2j.gameserver.model.olympiad.OlympiadManager;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

import phantom.FakePlayer;
import phantom.FakePlayerManager;

public class L2TournamentInstance extends L2NpcInstance
{
	public L2TournamentInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}
	
	@Override
	public void showChatWindow(Player player, int val)
	{
		
		player.sendPacket(ActionFailed.STATIC_PACKET);
		NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile("data/html/mods/tournament/" + getNpcId() + (val == 0 ? "" : "-" + val) + ".htm");
		html.replace("%name%", player.getName());
		html.replace("%objectId%", getObjectId() + "");
		player.sendPacket(html);
	}
	
	@Override
	public void onBypassFeedback(Player player, String command)
	{
		
		if (command.startsWith("1_Player_1_FakePlayer"))
		{
			FakePlayer nukerFake = FakePlayerManager.spawntour(82248, 148600, -3464);
			nukerFake.assignDefaultAI();
			if (Arena1x1.getInstance().register(player))
			{
				player.sendMessage("Tournament: Your participation has been approved.");
				player.setArenaProtection(true);
				player.setArena1x1(true);
				showChatWindow(player);
				
			}
			else
			{
				player.sendMessage("Tournament: You or your member does not have the necessary requirements.");
			}
			
		}
		else if (command.startsWith("1x1"))
		{
			
			if (player.isArena1x1() || player.isArenaProtection())
			{
				player.sendMessage("Tournament: You already registered!");
				return;
			}
			
			if (player.isCursedWeaponEquipped() || player.isInStoreMode() || player.getKarma() > 0)
			{
				player.sendMessage("Tournament: You or your member does not have the necessary requirements.");
				
				return;
			}
			
			if (OlympiadManager.getInstance().isRegistered(player))
			{
				player.sendMessage("Tournament: You or your member is registered in the Olympiad.");
				return;
			}
			
			if (TvTEvent.isPlayerParticipant(player.getObjectId()))
			{
				player.sendMessage("Tournament: You already participated in another event!");
				
				return;
			}
			
			if (ArenaConfig.ARENA_SKILL_PROTECT)
			{
				for (L2Effect effect : player.getAllEffects())
				{
					if (ArenaConfig.ARENA_STOP_SKILL_LIST.contains(effect.getSkill().getId()))
						player.stopSkillEffects(effect.getSkill().getId());
				}
				
				for (L2Effect effect : player.getAllEffects())
				{
					if (ArenaConfig.ARENA_DISABLE_SKILL_LIST_PERM.contains(effect.getSkill().getId()))
						player.stopSkillEffects(effect.getSkill().getId());
				}
				
			}
			
			if (Arena1x1.getInstance().register(player))
			{
				player.sendMessage("Tournament: Your participation has been approved.");
				player.setArenaProtection(true);
				player.setArena1x1(true);
				showChatWindow(player);
				
			}
			else
				player.sendMessage("Tournament: You or your member does not have the necessary requirements.");
			
		}
		
		else if (command.startsWith("remove1x1"))
		{
			if (player.isArena1x1())
				Arena1x1.getInstance().remove(player);
		}
		
		else if (command.startsWith("observe_list"))
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			String filename = "data/html/mods/tournament/10006-11.htm";
			
			NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
			html.setFile(filename);
			html.replace("%objectId%", String.valueOf(getObjectId()));
			player.sendPacket(html);
		}
		else if (command.startsWith("observe_back"))
			showChatWindow(player);
		else if (command.startsWith("tournament_observe"))
		{
			
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			
			player.setArenaObserv(true);
			player.enterObserverMode(x, y, z);
		}
		else
			super.onBypassFeedback(player, command);
	}
	
}