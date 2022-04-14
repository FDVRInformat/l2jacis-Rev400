package phantom.ai.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phantom.FakePlayer;
import phantom.ai.CombatAI;
import phantom.helpers.FakeHelpers;
import phantom.model.HealingSpell;
import phantom.model.OffensiveSpell;
import phantom.model.SupportSpell;

import net.sf.l2j.gameserver.model.ShotType;

public class StormScreamerAI extends CombatAI
{
	public StormScreamerAI(FakePlayer character)
	{
		super(character);		
	}
	
	@Override
	public void thinkAndAct()
	{
		super.thinkAndAct();
		setBusyThinking(true);
		handleShots();
		tryTargetRandomCreatureByTypeInRadius(FakeHelpers.getTestTargetClass(), FakeHelpers.getTestTargetRange());
		tryAttackingUsingMageOffensiveSkill();
		setBusyThinking(false);
	}
	
	@Override
	protected ShotType getShotType()
	{
		return ShotType.BLESSED_SPIRITSHOT;
	}
	
	@Override
	protected List<OffensiveSpell> getOffensiveSpells()
	{
		List<OffensiveSpell> _offensiveSpells = new ArrayList<>();
		_offensiveSpells.add(new OffensiveSpell(1341, 1));
		_offensiveSpells.add(new OffensiveSpell(1343, 2));
		_offensiveSpells.add(new OffensiveSpell(1234, 3));
		_offensiveSpells.add(new OffensiveSpell(1239, 4));
		return _offensiveSpells; 
	}

	@Override
	protected List<HealingSpell> getHealingSpells()
	{		
		return Collections.emptyList();
	}
	
	@Override
	protected List<SupportSpell> getSelfSupportSpells() 
	{
		return Collections.emptyList();
	}
}
