package net.sf.l2j.gameserver.model.entity.events.partyfarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ThreadPool;

public class InitialPartyFarm
{
	private static InitialPartyFarm _instance = null;
	protected static final Logger _log = Logger.getLogger(InitialPartyFarm.class.getName());
	private Calendar NextEvent;
	private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	
	public static InitialPartyFarm getInstance()
	{
		if (_instance == null)
			_instance = new InitialPartyFarm();
		return _instance;
	}
	
	public String getNextTime()
	{
		if (NextEvent.getTime() != null)
			return format.format(NextEvent.getTime());
		return "Erro";
	}
	
	private InitialPartyFarm()
	{
	}
	
	public void StartCalculationOfNextEventTime()
	{
		try
		{
			Calendar currentTime = Calendar.getInstance();
			Calendar testStartTime = null;
			long flush2 = 0, timeL = 0;
			int count = 0;
			
			for (String timeOfDay : Config.EVENT_BEST_FARM_INTERVAL_BY_TIME_OF_DAY)
			{
				testStartTime = Calendar.getInstance();
				testStartTime.setLenient(true);
				String[] splitTimeOfDay = timeOfDay.split(":");
				testStartTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(splitTimeOfDay[0]));
				testStartTime.set(Calendar.MINUTE, Integer.parseInt(splitTimeOfDay[1]));
				testStartTime.set(Calendar.SECOND, 00);
				if (testStartTime.getTimeInMillis() < currentTime.getTimeInMillis())
				{
					testStartTime.add(Calendar.DAY_OF_MONTH, 1);
				}
				
				timeL = testStartTime.getTimeInMillis() - currentTime.getTimeInMillis();
				
				if (count == 0)
				{
					flush2 = timeL;
					NextEvent = testStartTime;
				}
				
				if (timeL < flush2)
				{
					flush2 = timeL;
					NextEvent = testStartTime;
				}
				count++;
			}
			_log.info("Party Farm: Next Event " + NextEvent.getTime().toString());
			ThreadPool.schedule(new StartEventTask(), flush2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Party Farm: " + e);
		}
	}
	
	class StartEventTask implements Runnable
	{
		@Override
		public void run()
		{
			InitialPartyFarm._log.info("----------------------------------------------------------------------------");
			InitialPartyFarm._log.info("Party Farm: Event Started.");
			InitialPartyFarm._log.info("----------------------------------------------------------------------------");
			PartyFarm.bossSpawnMonster();
		}
	}
}
