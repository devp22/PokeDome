package edu.gatech.pokedome.battleTests;


import edu.gatech.pokedome.pokemon.Pokemon;
import edu.gatech.pokedome.pokemon.PokemonType;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class SkillStrategyTests {

    @Disabled("Only run manually")
    @Test
    public void testSkillStrategyAggressive() {

        Skill atk = new Skill("Atk");
        Skill def = new Skill("Def");

        ArrayList<Skill> atkSkills = new ArrayList<>();
        atkSkills.add(atk);
        ArrayList<Skill> defSkills = new ArrayList<>();
        defSkills.add(def);

        HitPointStrategy strategy = new HitPointStrategy(10, 1);

        Pokemon pokemon = new Pokemon("Pikachu", PokemonType.FIRE, strategy,
                new StatGroup(5,5,0), atkSkills, defSkills);
        pokemon.prepareForBattle(new Random().nextInt());

        for (int i = 0; i < 10; i++) {
            Skill chosen = pokemon.chooseSkill();
            System.out.println(chosen);
        }
    }

    @Disabled("Only run manually")
    @Test
    public void testSkillStrategyCautious() {
        Skill atk = new Skill("Atk");
        Skill def = new Skill("Def");

        ArrayList<Skill> atkSkills = new ArrayList<>();
        atkSkills.add(atk);
        ArrayList<Skill> defSkills = new ArrayList<>();
        defSkills.add(def);

        HitPointStrategy strategy = new HitPointStrategy(1, 10);

        Pokemon pokemon = new Pokemon("Pikachu", PokemonType.FIRE, strategy,
                new StatGroup(5,5,0), atkSkills, defSkills);
        pokemon.prepareForBattle(new Random().nextInt());

        for (int i = 0; i < 10; i++) {
            Skill chosen = pokemon.chooseSkill();
            System.out.println(chosen);
        }
    }
}
