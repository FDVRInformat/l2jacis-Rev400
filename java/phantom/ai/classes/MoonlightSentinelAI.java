package phantom.ai.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phantom.FakePlayer;
import phantom.ai.CombatAI;
import phantom.ai.addon.IConsumableSpender;
import phantom.helpers.FakeHelpers;
import phantom.model.HealingSpell;
import phantom.model.OffensiveSpell;
import phantom.model.SupportSpell;

import net.sf.l2j.gameserver.model.ShotType;

public class MoonlightSentinelAI extends CombatAI implements IConsumableSpender
{
	public MoonlightSentinelAI(FakePlayer character)
	{
		super(character);
	}
	
	@Override
	public void thinkAndAct()
	{
		super.thinkAndAct();
		setBusyThinking(true);
		
		handleConsumable(_fakePlayer, getArrowId());
		selfSupportBuffs();
		handleShots();		
		tryTargetRandomCreatureByTypeInRadius(FakeHelpers.getTestTargetClass(), FakeHelpers.getTestTargetRange());		
		tryAttackingUsingFighterOffensiveSkill();
		setBusyThinking(false);
	}
	
	@Override
	protected double changeOfUsingSkill()
	{
		return 0.15;
	}
	
	@Override
	protected ShotType getShotType()
	{
		return ShotType.SOULSHOT;
	}
	
	@Override
	protected List<OffensiveSpell> getOffensiveSpells()
	{
		List<OffensiveSpell> _offensiveSpells = new ArrayList<>();
		_offensiveSpells.add(new OffensiveSpell(101, 1));
		_offensiveSpells.add(new OffensiveSpell(343, 1));
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
		List<SupportSpell> _selfSupportSpells = new ArrayList<>();
		_selfSupportSpells.add(new SupportSpell(99, 1));
		return _selfSupportSpells;
	}
}