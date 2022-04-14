package net.sf.l2j.gameserver.handler.voicedcommandhandlers;

import Dev.Config.ConfigDev;

import net.sf.l2j.gameserver.ThreadPool;
import net.sf.l2j.gameserver.datatables.MultisellData;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.MagicSkillUse;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;
import net.sf.l2j.gameserver.network.serverpackets.SocialAction;
import net.sf.l2j.gameserver.network.serverpackets.SpecialCamera;

public class VoicedDonate implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS = 
	{
		"donate",
		"infoDonate",
		"visualHtml",
		"visualTest",
		"multisell"
	};

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String target)
	{
		if (command.startsWith("donate"))
			showDonateHtml(activeChar);       

		else if (command.startsWith("infoDonate"))
			showInfoDonateHtml(activeChar);   
		
		else if (command.startsWith("visualTest"))
		{
			if(ConfigDev.ENABLE_VISUAL_SET)
			{
				if (activeChar.getVisualTest() > 0)
				{
					activeChar.sendMessage("You are already trying on a uniform, please wait till it finishes.");
					return true;
				}
				
				if (ConfigDev.ENABLE_VISUAL_EFFECT)
				{
					L2Skill skill = SkillTable.FrequentSkill.MAGIC_VISUAL.getSkill();
					if (skill != null)
					{
						MagicSkillUse MSU = new MagicSkillUse(activeChar, activeChar, ConfigDev.ID_VISUAL_EFFECT, 1, 1, 0);
						activeChar.sendPacket(MSU);
						activeChar.broadcastPacket(MSU);
						activeChar.useMagic(skill, false, false);
						int uniform = Integer.parseInt(command.substring(11));
						activeChar.setVisualTest(uniform);
						activeChar.broadcastPacket(new SocialAction(activeChar, ConfigDev.ID_VISUAL_SOCIAL_ACTION));
						ThreadPool.schedule(() -> activeChar.setVisualTest(0), 1000 * ConfigDev.VISUAL_TEST_SEGUNDS);
						sendVisualTime(activeChar);
						VisualHtml(activeChar);
						if (ConfigDev.CAMERA_ENABLED)
						{
							activeChar.sendPacket(new SpecialCamera(activeChar.getObjectId(), ConfigDev.CAMERA_DISTANCE, ConfigDev.CAMERA_POV, ConfigDev.CAMERA_ANGLE, ConfigDev.CAMERA_SPEED, ConfigDev.CAMERA_DELAY));
						}else {
							
						}
					}
				}else{
					int uniform = Integer.parseInt(command.substring(11));
					activeChar.setVisualTest(uniform);
					ThreadPool.schedule(() -> activeChar.setVisualTest(0), 1000 * ConfigDev.VISUAL_TEST_SEGUNDS);
					sendVisualTime(activeChar);
					VisualHtml(activeChar);
				}
			}else
				activeChar.sendMessage("Sorry, admin has disabled skins.");	
		}
		
		else if (command.startsWith("visualHtml"))
		{
			
			VisualHtml(activeChar);
			
		}
		
		else if (command.startsWith("multisell"))
		{
			try
			{
				activeChar.setIsUsingCMultisell(true);
				MultisellData.getInstance().separateAndSend(command.substring(9).trim(), activeChar, false, 0);
			}
			catch(Exception e)
			{
				activeChar.sendMessage("This list does not exist.");
			}
		}
		return true;
	}

	private static void showDonateHtml(Player activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/mods/menu/Donate.htm");  
		activeChar.sendPacket(html);
	}

	private static void showInfoDonateHtml(Player activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/mods/menu/Info_Donate.htm");  
		activeChar.sendPacket(html);
	}
	
	public static void VisualHtml(Player activeChar)
	{
		NpcHtmlMessage html = new NpcHtmlMessage(0);
		html.setFile("data/html/mods/menu/Visual.htm");
		activeChar.sendPacket(html);
	}
	
	private static void sendVisualTime(Player activeChar)
	{
		activeChar.sendMessage("Visual Time: " + ConfigDev.VISUAL_TEST_SEGUNDS + " seconds");
		VisualHtml(activeChar);
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}