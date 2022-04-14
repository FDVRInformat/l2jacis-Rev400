package net.sf.l2j.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ai.CtrlIntention;
import net.sf.l2j.gameserver.datatables.ItemTable;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.actor.template.NpcTemplate;
import net.sf.l2j.gameserver.network.serverpackets.ActionFailed;
import net.sf.l2j.gameserver.network.serverpackets.ExShowScreenMessage;
import net.sf.l2j.gameserver.network.serverpackets.MyTargetSelected;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;
import net.sf.l2j.gameserver.network.serverpackets.ValidateLocation;
import net.sf.l2j.util.Rnd;

import Dev.Dungeon.DungeonManager;

public class L2DungeonManagerInstance extends L2NpcInstance
{
	public L2DungeonManagerInstance(int objectId, NpcTemplate template)
	{
		super(objectId, template);
	}

	@Override
	public void onAction(Player player)
	{
		if (this != player.getTarget()) 
		{
			player.setTarget(this);
			player.sendPacket(new MyTargetSelected(getObjectId(), player.getLevel() - getLevel()));
			player.sendPacket(new ValidateLocation(this));
		}
		else if (isInsideRadius(player, INTERACTION_DISTANCE, false, false)) 
		{
			SocialAction sa = new SocialAction(this, Rnd.get(8));
			broadcastPacket(sa);
			player.setCurrentFolkNPC(this);
			showChatWindow(player);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
		else 
		{
			player.getAI().setIntention(CtrlIntention.INTERACT, this);
			player.sendPacket(ActionFailed.STATIC_PACKET);
		}
	}


	
	public static String getPlayerStatus(Player player, int dungeonId)
	{
		String s = "You can enter";
		
		String ip = player.getIP(); // Is ip or hwid?
		if (DungeonManager.getInstance().getPlayerData().containsKey(ip) && DungeonManager.getInstance().getPlayerData().get(ip)[dungeonId] > 0)
		{
			long total = (DungeonManager.getInstance().getPlayerData().get(ip)[dungeonId] + (1000*60*60*12)) - System.currentTimeMillis();
			
			if (total > 0)
			{
				int hours = (int) (total/1000/60/60);
				int minutes = (int) ((total/1000/60) - hours*60);
				int seconds = (int) ((total/1000) - (hours*60*60 + minutes*60));
				
				s = String.format("%02d:%02d:%02d", hours, minutes, seconds);
			}
		}
		
		return s;
	}

	@Override
	public void onBypassFeedback(Player player, String command)
	{
		if (command.startsWith("dungeon"))
		{

						
			if (DungeonManager.getInstance().isInDungeon(player) || player.isInOlympiadMode())
			{
				player.sendMessage("You are currently unable to enter a Dungeon. Please try again later.");
				return;
			}
			
			if (player.isAio())
			{
				player.sendMessage("AioBuffer: cannot leave You have been teleported to the nearest village.");
				return;
			}
			
			if (player.destroyItemByItemId("Donate Coin", Config.DUNGEON_COIN_ID, Config.CONT_DUNGEON_ITEM, null, true))
			{
				int dungeonId = Integer.parseInt(command.substring(8));
				
				if(dungeonId == 1 || dungeonId == 2)
				{
					DungeonManager.getInstance().enterDungeon(dungeonId, player);
				}
				
				else if (player.isOnline())
				{
					player.sendPacket(new ExShowScreenMessage("Your character Cont Item." +" "+ ItemTable.getInstance().getTemplate(Config.DUNGEON_COIN_ID).getName() + " " + Config.CONT_DUNGEON_ITEM , 6000, 2, true));

					mainHtml(player, 0);
				}
				
			}
			else
			{
				mainHtml(player, 0);
				player.sendPacket(new ExShowScreenMessage("Your character Cont Item." +" "+ ItemTable.getInstance().getTemplate(Config.DUNGEON_COIN_ID).getName() + " " + Config.CONT_DUNGEON_ITEM , 6000, 2, true));
				
			}
		}

	}

	public static void mainHtml(Player activeChar, int time)
	{
		
		if (activeChar.isOnline())
		{
			NpcHtmlMessage nhm = new NpcHtmlMessage(5);
			StringBuilder html1 = new StringBuilder("");
			html1.append("<html><head><title>Dungeon</title></head><body><center>");
			html1.append("<br>");
			html1.append("Your character Cont Item.");
			html1.append("</center>");
			html1.append("</body></html>");
			nhm.setHtml(html1.toString());
			activeChar.sendPacket(nhm);
		}
		
	}
	
	@Override
	public void showChatWindow(Player player, int val)
	{
		NpcHtmlMessage htm = new NpcHtmlMessage(getObjectId());
		htm.setFile("data/html/mods/Dungeon-L2JDev/"+getNpcId()+(val == 0 ? "" : "-"+val)+".htm");
		
		String[] s = htm.getHtml().split("%");
		for (int i = 0; i < s.length; i++)
		{
			if (i % 2 > 0 && s[i].contains("dung "))
			{
				StringTokenizer st = new StringTokenizer(s[i]);
				st.nextToken();
				htm.replace("%"+s[i]+"%", getPlayerStatus(player, Integer.parseInt(st.nextToken())));
			}
		}
		
		htm.replace("%objectId%", getObjectId()+"");
		
		player.sendPacket(htm);
	}
}