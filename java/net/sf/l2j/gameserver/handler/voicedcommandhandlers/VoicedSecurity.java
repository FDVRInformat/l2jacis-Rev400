package net.sf.l2j.gameserver.handler.voicedcommandhandlers;

import net.sf.l2j.gameserver.handler.IVoicedCommandHandler;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.NpcHtmlMessage;

public class VoicedSecurity implements IVoicedCommandHandler
{
	private static final String[] VOICED_COMMANDS = 
	{
		"security"
	};

	@Override
	public boolean useVoicedCommand(String command, Player activeChar, String target)
	{
		if (activeChar.getPincheck())
			sendHtml(activeChar);
		else
			activeChar.sendMessage("You have already submitted a Pin code");

		return true;
	}
	
	private static void sendHtml(Player activeChar)
	{
		String htmFile = "data/html/mods/menu/Security_Pin.htm";

		NpcHtmlMessage msg = new NpcHtmlMessage(5);
		msg.setFile(htmFile);
		activeChar.sendPacket(msg);
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return VOICED_COMMANDS;
	}
}