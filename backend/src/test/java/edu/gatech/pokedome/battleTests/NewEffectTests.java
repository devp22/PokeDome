package edu.gatech.pokedome.battleTests;

import edu.gatech.pokedome.battle.Battle;
import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.pokemon.PokemonType;
import edu.gatech.pokedome.pokemon.effect.*;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class NewEffectTests {

    @Disabled("Only run manually")
    @Test
    public void testEffects() {

        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 5 ,5);

        ArrayList<Effect> tWaveEffects = new ArrayList<>();
        tWaveEffects.add(new ParalysisEffect(4));
        Skill thunderWave = new Skill("Thunder Wave", tWaveEffects);

        ArrayList<Effect> tackleEffects = new ArrayList<>();
        tackleEffects.add(new DamageEffect(10, PokemonType.FIRE));
        Skill tackle = new Skill("Tackle", tackleEffects);

        ArrayList<Effect> fireSpinEffects = new ArrayList<>();
        fireSpinEffects.add(new DamageOverTimeEffect(5, 4));
        Skill fireSpin = new Skill("Fire Spin", fireSpinEffects);

        ArrayList<Effect> blockDefEffects = new ArrayList<>();
        blockDefEffects.add(new StatChangeEffect(5, 5, 4));
        Skill block = new Skill("Block", blockDefEffects);

        ArrayList<Effect> healPulseEffects = new ArrayList<>();
        healPulseEffects.add(new HealEffect(15));
        Skill healPulse = new Skill("Heal Pulse", healPulseEffects);

        ArrayList<Effect> transformEffects = new ArrayList<>();
        transformEffects.add(new CopyEffect());
        Skill transform = new Skill("Transform", transformEffects);

        ArrayList<Skill> atkSkills = new ArrayList<>();
        atkSkills.add(thunderWave);
        atkSkills.add(tackle);
        atkSkills.add(fireSpin);
        ArrayList<Skill> defSkills = new ArrayList<>();
        defSkills.add(block);
        defSkills.add(healPulse);
        atkSkills.add(transform);


        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, atkSkills, defSkills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, atkSkills, defSkills);
        Battle battle = new Battle(5, x, y);

        StringBuffer sb = new StringBuffer();

        battle.startBattle(sb);
        System.out.println(sb.toString());
    }

    @Test
    public void testDmgMultiplier() {
        DamageEffect dmg = new DamageEffect(10, PokemonType.FIRE);

        Assertions.assertEquals(0.5, dmg.getMultiplier(PokemonType.FIRE));
        Assertions.assertEquals(2.0, dmg.getMultiplier(PokemonType.GRASS));
        Assertions.assertEquals(0.5, dmg.getMultiplier(PokemonType.WATER));
        Assertions.assertEquals(1.0, dmg.getMultiplier(PokemonType.NORMAL));

        dmg = new DamageEffect(10, PokemonType.WATER);
        Assertions.assertEquals(2.0, dmg.getMultiplier(PokemonType.FIRE));
        Assertions.assertEquals(0.5, dmg.getMultiplier(PokemonType.GRASS));
        Assertions.assertEquals(0.5, dmg.getMultiplier(PokemonType.WATER));
        Assertions.assertEquals(1.0, dmg.getMultiplier(PokemonType.NORMAL));

        dmg = new DamageEffect(10, PokemonType.GRASS);
        Assertions.assertEquals(0.5, dmg.getMultiplier(PokemonType.FIRE));
        Assertions.assertEquals(0.5, dmg.getMultiplier(PokemonType.GRASS));
        Assertions.assertEquals(2.0, dmg.getMultiplier(PokemonType.WATER));
        Assertions.assertEquals(1.0, dmg.getMultiplier(PokemonType.NORMAL));

        dmg = new DamageEffect(10, PokemonType.NORMAL);
        Assertions.assertEquals(1.0, dmg.getMultiplier(PokemonType.FIRE));
        Assertions.assertEquals(1.0, dmg.getMultiplier(PokemonType.GRASS));
        Assertions.assertEquals(1.0, dmg.getMultiplier(PokemonType.WATER));
        Assertions.assertEquals(1.0, dmg.getMultiplier(PokemonType.NORMAL));
    }

    @Test
    public void testDmgCalc() {
        DamageEffect dmg = new DamageEffect(10, PokemonType.FIRE);
        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 10 ,10);

        ArrayList<Skill> skills = new ArrayList<>();

        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, skills, skills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, skills, skills);

        x.prepareForBattle(5);
        y.prepareForBattle(6);

        StringBuffer sb = new StringBuffer();

        dmg.apply(x, y, sb);

        Assertions.assertEquals(80, y.getBattleStat().getHitPoints());

        dmg.apply(y, x, sb);

        Assertions.assertEquals(95, x.getBattleStat().getHitPoints());

        dmg = new DamageEffect(10, PokemonType.WATER);
        dmg.apply(y, x, sb);

        Assertions.assertEquals(75, x.getBattleStat().getHitPoints());

        y.getBattleStat().updateAttack(20);

        dmg.apply(y, x, sb);
        dmg.apply(x, y, sb);

        Assertions.assertEquals(15, x.getBattleStat().getHitPoints());
        Assertions.assertEquals(75, y.getBattleStat().getHitPoints());

        dmg = new DamageEffect(40, PokemonType.GRASS);
        x.getBattleStat().updateDefense(50);
        dmg.apply(y, x, sb);
        dmg.apply(x, y, sb);

        Assertions.assertEquals(5, x.getBattleStat().getHitPoints());
        Assertions.assertEquals(55, y.getBattleStat().getHitPoints());
    }

    @Test
    public void testDmgCalc2() {
        DamageEffect dmg = new DamageEffect(10, PokemonType.WATER);
        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 10 ,10);

        ArrayList<Skill> skills = new ArrayList<>();

        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, skills, skills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, skills, skills);

        x.prepareForBattle(5);
        y.prepareForBattle(6);

        StringBuffer sb = new StringBuffer();

        dmg.apply(x, y, sb);
        dmg.apply(y, x, sb);

        Assertions.assertEquals(95, y.getBattleStat().getHitPoints());
        Assertions.assertEquals(80, x.getBattleStat().getHitPoints());
    }

    @Test
    public void testDmgCalc3() {
        DamageEffect dmg = new DamageEffect(10, PokemonType.WATER);
        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 50 ,10);

        ArrayList<Skill> skills = new ArrayList<>();

        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, skills, skills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, skills, skills);

        x.prepareForBattle(5);
        y.prepareForBattle(6);

        StringBuffer sb = new StringBuffer();

        dmg.apply(x, y, sb);
        dmg.apply(y, x, sb);

        Assertions.assertEquals(75, y.getBattleStat().getHitPoints());
        Assertions.assertEquals(0, x.getBattleStat().getHitPoints());
    }

    @Test
    public void testDmgCalc4() {
        DamageEffect dmg = new DamageEffect(20, PokemonType.FIRE);
        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 10 ,25);

        ArrayList<Skill> skills = new ArrayList<>();

        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, skills, skills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, skills, skills);

        x.prepareForBattle(5);
        y.prepareForBattle(6);

        StringBuffer sb = new StringBuffer();

        dmg.apply(x, y, sb);
        dmg.apply(y, x, sb);

        Assertions.assertEquals(84, y.getBattleStat().getHitPoints());
        Assertions.assertEquals(96, x.getBattleStat().getHitPoints());
    }

    @Test
    public void testDmgCalc5() {
        DamageEffect dmg = new DamageEffect(20, PokemonType.NORMAL);
        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 10 ,10);

        ArrayList<Skill> skills = new ArrayList<>();

        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, skills, skills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, skills, skills);

        x.prepareForBattle(5);
        y.prepareForBattle(6);

        StringBuffer sb = new StringBuffer();

        dmg.apply(x, y, sb);
        dmg.apply(y, x, sb);

        Assertions.assertEquals(80, y.getBattleStat().getHitPoints());
        Assertions.assertEquals(80, x.getBattleStat().getHitPoints());
    }

    @Test
    public void testHeal() {
        HealEffect heal = new HealEffect(10);

        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 10 ,10);

        ArrayList<Skill> skills = new ArrayList<>();

        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, skills, skills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, skills, skills);

        x.prepareForBattle(5);
        y.prepareForBattle(6);

        StringBuffer sb = new StringBuffer();

        heal.apply(x, y, sb);
        Assertions.assertEquals(100, x.getBattleStat().getHitPoints());

        x.getBattleStat().updateHitPoints(-5);

        heal.apply(x, y, sb);
        Assertions.assertEquals(100, x.getBattleStat().getHitPoints());

        x.getBattleStat().updateHitPoints(-20);

        heal.apply(x, y, sb);
        Assertions.assertEquals(90, x.getBattleStat().getHitPoints());
    }

    @Test
    public void testCopy() {
        HitPointStrategy strategy = new HitPointStrategy(1, 1);
        StatGroup stats = new StatGroup(100, 10 ,10);

        ArrayList<Skill> skills = new ArrayList<>();

        ArrayList<Effect> tackleEffects = new ArrayList<>();
        tackleEffects.add(new DamageEffect(10, PokemonType.FIRE));
        Skill tackle = new Skill("Tackle", tackleEffects);

        ArrayList<Effect> transformEffects = new ArrayList<>();
        transformEffects.add(new CopyEffect());
        Skill transform = new Skill("Transform", transformEffects);

        ArrayList<Effect> tWaveEffects = new ArrayList<>();
        tWaveEffects.add(new ParalysisEffect(4));
        Skill thunderWave = new Skill("Thunder Wave", tWaveEffects);

        ArrayList<Effect> fireSpinEffects = new ArrayList<>();
        fireSpinEffects.add(new DamageOverTimeEffect(5, 4));
        Skill fireSpin = new Skill("Fire Spin", fireSpinEffects);

        ArrayList<Effect> blockDefEffects = new ArrayList<>();
        blockDefEffects.add(new StatChangeEffect(5, 5, 4));
        Skill block = new Skill("Block", blockDefEffects);


        Pokemon x = new Pokemon("Charmander", PokemonType.FIRE, strategy, stats, skills, skills);
        Pokemon y = new Pokemon("Bulbasaur", PokemonType.GRASS, strategy, stats, skills, skills);

        x.prepareForBattle(5);
        y.prepareForBattle(6);

        StringBuffer sb = new StringBuffer();

        y.prepareForTurn(sb);
        transform.useSkill(y, x, sb);

        Assertions.assertEquals(90, x.getBattleStat().getHitPoints());

        x.setLastUsedSkill(tackle);
        transform.useSkill(y, x, sb);

        Assertions.assertEquals(85, x.getBattleStat().getHitPoints());

        x.prepareForTurn(sb);
        transform.useSkill(y, x, sb);

        Assertions.assertEquals(75, x.getBattleStat().getHitPoints());

        x.setLastUsedSkill(thunderWave);
        transform.useSkill(y, x, sb);

        Assertions.assertEquals(ParalysisEffect.class, x.getActiveEffects().getFirst().getClass());

        x.getActiveEffects().removeFirst();
        x.setLastUsedSkill(fireSpin);
        transform.useSkill(y, x, sb);
        x.endTurn(sb);

        Assertions.assertEquals(70, x.getBattleStat().getHitPoints());
        Assertions.assertEquals(DamageOverTimeEffect.class, x.getActiveEffects().getFirst().getClass());

        x.getActiveEffects().removeFirst();
        x.setLastUsedSkill(block);
        transform.useSkill(y, x, sb);

        Assertions.assertEquals(15, y.getBattleStat().getAttack());
        Assertions.assertEquals(15, y.getBattleStat().getDefense());
        Assertions.assertEquals(StatChangeEffect.class, y.getActiveEffects().getFirst().getClass());

        x.setLastUsedSkill(transform);
        transform.useSkill(y, x, sb);

        Assertions.assertEquals(60, x.getBattleStat().getHitPoints());
    }
}
