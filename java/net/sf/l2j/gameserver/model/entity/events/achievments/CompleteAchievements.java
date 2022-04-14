package net.sf.l2j.gameserver.model.entity.events.achievments;

import net.sf.l2j.gameserver.model.actor.Player;
import net.sf.l2j.gameserver.model.entity.events.achievments.base.Condition;

/**
 * @author Matim
 * @version 1.0
 */
public class CompleteAchievements extends Condition
{
	public CompleteAchievements(Object value)
	{
		super(value);
		setName("Complete Achievements");
	}

	@Override
	public boolean meetConditionRequirements(Player player)
	{
		if (getValue() == null)
			return false;

		int val = Integer.parseInt(getValue().toString());

		if (player.getCompletedAchievements().size() >= val)
			return true;

		return false;
	}
}