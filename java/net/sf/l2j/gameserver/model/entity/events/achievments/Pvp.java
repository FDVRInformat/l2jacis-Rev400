package net.sf.l2j.gameserver.model.entity.events.achievments;

import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.entity.events.achievments.base.Condition;

/**
 * @author Matim
 * @version 1.0
 */
public class Pvp extends Condition
{
	public Pvp(Object value)
	{
		super(value);
		setName("PvP Count");
	}

	@Override
	public boolean meetConditionRequirements(Player player)
	{
		if (getValue() == null)
			return false;

		int val = Integer.parseInt(getValue().toString());

		if (player.getPvpKills() >= val)
			return true;

		return false;
	}
}