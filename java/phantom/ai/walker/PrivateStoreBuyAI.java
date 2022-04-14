package phantom.ai.walker;

import java.util.ArrayList;
import java.util.List;

import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.network.serverpackets.PrivateStoreMsgBuy;
import net.sf.l2j.util.Rnd;

import phantom.FakePlayer;
import phantom.FakePlayerConfig;
import phantom.ai.FakePlayerAI;

public class PrivateStoreBuyAI extends FakePlayerAI
{
	public PrivateStoreBuyAI(FakePlayer character)
	{
		super(character);
		setup();
	}
	
	@Override
	public void setup()
	{
		_fakePlayer.setIsRunning(true);
	}
	
	@Override
	public void thinkAndAct()
	{
		setBusyThinking(true);
		handleDeath();
		
		if ((FakePlayerConfig.PHANTOM_PRIVATE_STORE))
		{
			_fakePlayer.addItem(":", 57, 1000000000, _fakePlayer, false);
			_fakePlayer.getBuyList().addItemByItemId(getPrivateBuy(), 1, Rnd.get(1000, 24678));
			
			_fakePlayer.getBuyList().setTitle(getPrivateBuy_Title());
			_fakePlayer.sitDown();
			_fakePlayer.setPrivateStoreType(3);
			_fakePlayer.getPrivateStoreType();
			_fakePlayer.getBuyList().setPackaged(Player.StoreType.BUY == Player.StoreType.BUY_MANAGE);
			_fakePlayer.broadcastUserInfo();
			_fakePlayer.broadcastPacket(new PrivateStoreMsgBuy(_fakePlayer));
		}
	}
	
	static int getPrivateBuy()
	{
		return FakePlayerConfig.LIST_PRIVATE_BUY.get(Rnd.get(FakePlayerConfig.LIST_PRIVATE_BUY.size())).intValue();
	}
	
	static final List<String> list_msg_buy = new ArrayList<>();
	
	@SuppressWarnings("null")
	static String getPrivateBuy_Title()
	{
		String msg = null;
		if (msg == null)
		{
			msg = FakePlayerConfig.PHANTOM_PRIVATE_BUY_TITLE.get(Rnd.get(FakePlayerConfig.PHANTOM_PRIVATE_BUY_TITLE.size()));
		}
		if (list_msg_buy.contains(msg))
		{
			boolean gerar = true;
			while (gerar)
			{
				msg = FakePlayerConfig.PHANTOM_PRIVATE_BUY_TITLE.get(Rnd.get(FakePlayerConfig.PHANTOM_PRIVATE_BUY_TITLE.size()));
				if (!list_msg_buy.contains(msg))
				{
					list_msg_buy.add(msg);
					gerar = false;
					return msg;
				}
				if (list_msg_buy.size() == FakePlayerConfig.PHANTOM_PRIVATE_BUY_TITLE.size())
				{
					gerar = false;
					return "";
				}
			}
		}
		else if (!list_msg_buy.contains(msg))
		{
			list_msg_buy.add(msg);
			return msg;
		}
		return msg;
	}
}