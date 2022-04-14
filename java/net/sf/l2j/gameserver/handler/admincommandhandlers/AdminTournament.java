package net.sf.l2j.gameserver.handler.admincommandhandlers;

import java.util.logging.Logger;

import net.sf.l2j.gameserver.ThreadPool;
import net.sf.l2j.gameserver.handler.IAdminCommandHandler;
import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.entity.events.tournaments.properties.ArenaTask;


/**
 * @author Juvenil Walker
 *
 */
public class AdminTournament implements IAdminCommandHandler 
{
	
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_tour"

	};
	
	protected static final Logger _log = Logger.getLogger(AdminTournament.class.getName());
	public static boolean _arena_manual = false;

	@Override
	public boolean useAdminCommand(String command, Player activeChar)
	{

		if (command.equals("admin_tour"))
		{
			if (ArenaTask._started)
			{
				_log.info("----------------------------------------------------------------------------");
				_log.info("[Tournament]: Event Finished.");
				_log.info("----------------------------------------------------------------------------");
				ArenaTask._aborted = true;
				finishEventArena();
				_arena_manual = true;

				activeChar.sendMessage("SYS: Voce Finalizou o evento Tournament Manualmente..");
			}
			else
			{
				_log.info("----------------------------------------------------------------------------");
				_log.info("[Tournament]: Event Started.");
				_log.info("----------------------------------------------------------------------------");
				initEventArena();
				_arena_manual = true;
				activeChar.sendMessage("SYS: Voce ativou o evento Tournament Manualmente..");
			}
		}
		return true;
	}

	public static void initEventArena()
	{
		ThreadPool.schedule(new Runnable()
		{
			@Override
			public void run()
			{

				ArenaTask.SpawnEvent();
			}
		}, 10L);
	}

	private static void finishEventArena()
	{
		ThreadPool.schedule(new Runnable()
		{
			@Override
			public void run()
			{

				ArenaTask.finishEvent();
			}
		}, 10L);
	}


	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}

}
