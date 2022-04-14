package net.sf.l2j.gameserver.communitybbs.Manager;

import java.util.StringTokenizer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.cache.HtmCache;
import net.sf.l2j.gameserver.communitybbs.BaseBBSManager;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.TeleportLocationTable;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2TeleportLocation;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.SystemMessageId;
import net.sf.l2j.gameserver.network.serverpackets.ConfirmDlg;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;

/**
 * @author Juvenil Walker
 *
 */
public class TeleportBBSManager extends BaseBBSManager
{	
	@Override
	public void parseCmd(String command, Player activeChar)
	{
		if (command.startsWith("_bbstele"))
		{
			if (!checkAllowed(activeChar))
			{
				return;
			}
			
			StringTokenizer st = new StringTokenizer(command, ";");
			st.nextToken();
			int id = Integer.parseInt(st.nextToken());
			L2TeleportLocation list = TeleportLocationTable.getInstance().getTemplate(id);
			int price = list.getPrice();
			
			ConfirmDlg confirm = new ConfirmDlg(SystemMessageId.TIME_REQUEST_TELEPORTER.getId());
			confirm.addString("Teleport?");
			confirm.addZoneName(list.getLocX(), list.getLocY(), list.getLocZ());
			confirm.addTime(15000);
			confirm.addRequesterId(activeChar.getObjectId());
			
			L2Skill skill = SkillTable.FrequentSkill.EFFECT_TELEPORT.getSkill();
			if (skill != null || activeChar.destroyItem("Teleport " + (list.getIsForNoble() ? " nobless" : ""), 57, price, null, true))
			{
				MagicSkillUse MSU = new MagicSkillUse(activeChar, activeChar, 8002, 1, 1, 0);
				activeChar.sendPacket(MSU);
				activeChar.broadcastPacket(MSU);
				activeChar.useMagic(skill, false, false);
				activeChar.sendPacket(confirm);
				int[] cords = { list.getLocX(), list.getLocY(), list.getLocZ() };
				activeChar.TimeTeleporterCoords(cords);
			}
			
			else if (Config.ALT_GAME_FREE_TELEPORT || activeChar.destroyItem("Teleport " + (list.getIsForNoble() ? " nobless" : ""), 57, price, null, true))
			{
				if (Config.DEBUG)
					_log.fine("Teleporting player " + activeChar.getName() + " to new location: " + list.getLocX() + ":" + list.getLocY() + ":" + list.getLocZ());
				
				activeChar.teleToLocation(list.getLocX(), list.getLocY(), list.getLocZ(), 20);
				
			}
			else
				_log.warning("Community Bord teleport destination with id:" + id);
			
			String filename = "data/html/communityboard/top/Teleport.htm";
			String content = HtmCache.getInstance().getHtm(filename);
			separateAndSend(content, activeChar);
		}
	}
	
	public boolean checkAllowed(Player activeChar)
	{
		String msg = null;
		
		if (activeChar.isSitting())
		{
			msg = "You can't use Community Teleport when you sit!";
		}
		else if (activeChar.isCastingNow())
		{
			msg = "You can't use Community Teleport when you cast!";
		}
		else if (activeChar.isDead())
		{
			msg = "You can't use Community Teleport when you dead!";
		}
		else if (activeChar.isInCombat())
		{
			msg = "You can't use Community Teleport when you in combat!";
		}
		
		if (msg != null)
		{
			activeChar.sendMessage(msg);
		}
		
		return msg == null;
	}
	
	public static TeleportBBSManager getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final TeleportBBSManager INSTANCE = new TeleportBBSManager();
	}
}
