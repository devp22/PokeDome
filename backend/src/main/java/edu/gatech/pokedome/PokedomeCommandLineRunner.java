package edu.gatech.pokedome;

import java.util.List;

import edu.gatech.pokedome.pokemon.PokemonType;
import org.springframework.boot.CommandLineRunner;

import edu.gatech.pokedome.pokemon.PokemonModel;
import edu.gatech.pokedome.pokemon.PokemonRepository;
import edu.gatech.pokedome.pokemon.effect.DamageEffect;
import edu.gatech.pokedome.pokemon.effect.DamageOverTimeEffect;
import edu.gatech.pokedome.pokemon.effect.Effect;
import edu.gatech.pokedome.pokemon.effect.CopyEffect;
import edu.gatech.pokedome.pokemon.effect.HealEffect;
import edu.gatech.pokedome.pokemon.effect.ParalysisEffect;
import edu.gatech.pokedome.pokemon.effect.StatChangeEffect;
import edu.gatech.pokedome.pokemon.skill.Skill;
import edu.gatech.pokedome.pokemon.statGroup.StatGroup;
import edu.gatech.pokedome.pokemon.strategy.HitPointStrategy;
import lombok.RequiredArgsConstructor;

/**
 * PokedomeCommandLineRunner is used to add template Pokemons to the database.
 */
@RequiredArgsConstructor
public class PokedomeCommandLineRunner implements CommandLineRunner {
    final PokemonRepository pokemonRepository;

    @Override
    public void run(final String... args) throws Exception {
        Effect damage10 = new DamageEffect(100, PokemonType.FIRE);
        Effect damage20 = new DamageEffect(200, PokemonType.FIRE);
        Effect defense10 = new StatChangeEffect(10, 0, 2);
        Effect defense20 = new StatChangeEffect(20, 0, 1);
        Effect dot5 = new DamageOverTimeEffect(5, 5);
        Effect paralysis3 = new ParalysisEffect(3);
        Effect attack10 = new StatChangeEffect(0, 10, 3);

        Skill flamethrower = new Skill("Flamethrower", List.of(damage20));
        Skill thunderbolt = new Skill("Thunderbolt", List.of(damage10));
        Skill block = new Skill("Block10", List.of(defense10));
        Skill protect = new Skill("Protect20", List.of(defense20));
        Skill toxic = new Skill("Toxic", List.of(dot5));
        Skill paralyze = new Skill("Paralyze", List.of(paralysis3));
        Skill increaseAttack = new Skill("Increase Attack", List.of(attack10));

        // Add some Pokemon to the database
        final PokemonModel raichu = PokemonModel.builder().nickname("Raichu").type("WATER").offensiveSkills(new Skill[]{thunderbolt, increaseAttack}).defensiveSkills(new Skill[]{block}).baseStats(new StatGroup(60, 55, 50)).hitPointStrategy(new HitPointStrategy(8, 2)).build();
        final PokemonModel treecko = PokemonModel.builder().nickname("Treecko").type("GRASS").offensiveSkills(new Skill[]{flamethrower, paralyze}).defensiveSkills(new Skill[]{protect}).baseStats(new StatGroup(80, 70, 80)).hitPointStrategy(new HitPointStrategy(5, 5)).build();
        final PokemonModel beedrill = PokemonModel.builder().nickname("Beedrill").type("FIRE").offensiveSkills(new Skill[]{paralyze, toxic}).defensiveSkills(new Skill[]{block}).baseStats(new StatGroup(40, 60, 40)).hitPointStrategy(new HitPointStrategy(1, 10)).build();

        pokemonRepository.save(raichu);
        pokemonRepository.save(treecko);
        pokemonRepository.save(beedrill);

        this.saveTestCasePokemon();
    }

    private void saveTestCasePokemon() {
        // Normal Damage Effects
        Effect damage0 = new DamageEffect(0, PokemonType.NORMAL);
        Effect damage5Norm = new DamageEffect(5, PokemonType.NORMAL);
        Effect damage10Norm = new DamageEffect(10, PokemonType.NORMAL);
        Effect damage15Norm = new DamageEffect(15, PokemonType.NORMAL);
        Effect damage20Norm = new DamageEffect(20, PokemonType.NORMAL);
        Effect damage25Norm = new DamageEffect(25, PokemonType.NORMAL);
        Effect damage30Norm = new DamageEffect(30, PokemonType.NORMAL);

        // Fire Damage Effects
        Effect damage5Fire = new DamageEffect(5, PokemonType.FIRE);
        Effect damage10Fire = new DamageEffect(10, PokemonType.FIRE);
        Effect damage15Fire = new DamageEffect(15, PokemonType.FIRE);
        Effect damage20Fire = new DamageEffect(20, PokemonType.FIRE);
        Effect damage25Fire = new DamageEffect(25, PokemonType.FIRE);
        Effect damage30Fire = new DamageEffect(30, PokemonType.FIRE);

        // Water Damage Effects
        Effect damage5Water = new DamageEffect(5, PokemonType.WATER);
        Effect damage10Water = new DamageEffect(10, PokemonType.WATER);
        Effect damage15Water = new DamageEffect(15, PokemonType.WATER);
        Effect damage20Water = new DamageEffect(20, PokemonType.WATER);
        Effect damage25Water = new DamageEffect(25, PokemonType.WATER);
        Effect damage30Water = new DamageEffect(30, PokemonType.WATER);

        // Grass Damage Effects
        Effect damage5Grass = new DamageEffect(5, PokemonType.GRASS);
        Effect damage10Grass = new DamageEffect(10, PokemonType.GRASS);
        Effect damage15Grass = new DamageEffect(15, PokemonType.GRASS);
        Effect damage20Grass = new DamageEffect(20, PokemonType.GRASS);
        Effect damage25Grass = new DamageEffect(25, PokemonType.GRASS);
        Effect damage30Grass = new DamageEffect(30, PokemonType.GRASS);

        // Stat Changing Effects
        Effect defense10 = new StatChangeEffect(10, 0, 4);
        Effect defense20 = new StatChangeEffect(20, 0, 4);
        Effect defenseMinus10 = new StatChangeEffect(-10, 0, 4);
        Effect defenseMinus20 = new StatChangeEffect(-20, 0, 4);
        Effect attack10 = new StatChangeEffect(0, 10, 4);
        Effect attack20 = new StatChangeEffect(0, 20, 4);
        Effect attackMinus10 = new StatChangeEffect(0, -10, 4);
        Effect attack5Defense5 = new StatChangeEffect(5, 5, 4);
        Effect attackMinus5DefenseMinus5 = new StatChangeEffect(-5, -5, 4);

        // Damage Over Time Effects
        Effect dotDmg5Turn3 = new DamageOverTimeEffect(5, 3);
        Effect dotDmg5Turn5 = new DamageOverTimeEffect(5, 5);
        Effect dotDmg10Turn3 = new DamageOverTimeEffect(10, 3);

        // Paralysis Effects
        Effect paralysis = new ParalysisEffect(4);

        // Heal Effects
        Effect heal15 = new HealEffect(15);
        Effect heal25 = new HealEffect(25);

        // Copy Effects
        Effect copy = new CopyEffect();

        // Offensive skills
        Skill megaPunch = new Skill("Mega Punch", List.of(damage15Norm));
        Skill megaKick = new Skill("Mega Kick", List.of(damage20Norm));
        Skill seismicToss = new Skill("Seismic Toss", List.of(damage15Norm));
        Skill psychic = new Skill("Psychic", List.of(damage25Norm));
        Skill tackle = new Skill("Tackle", List.of(damage10Norm));
        Skill vineWhip = new Skill("Vine Whip", List.of(damage10Grass));
        Skill razorLeaf = new Skill("Razor Leaf", List.of(damage15Grass));
        Skill magicalLeaf = new Skill("Magical Leaf", List.of(damage20Grass));
        Skill leafStorm = new Skill("Leaf Storm", List.of(damage25Grass, attackMinus5DefenseMinus5));
        Skill poisonPowder = new Skill("Poison Powder", List.of(dotDmg5Turn3));
        Skill gust = new Skill("Gust", List.of(damage10Norm));
        Skill whirlwind = new Skill("Whirlwind", List.of(damage5Norm));
        Skill solarBeam = new Skill("Solar Beam", List.of(damage30Grass, attackMinus10));
        Skill scratch = new Skill("Scratch", List.of(damage10Norm));
        Skill ember = new Skill("Ember", List.of(damage10Fire));
        Skill flamethrower = new Skill("Flamethrower", List.of(damage20Fire));
        Skill transform = new Skill("Transform", List.of(copy));
        Skill rockThrow = new Skill("Rock Throw", List.of(damage15Norm));
        Skill earthQuake = new Skill("Earth Quake", List.of(damage25Norm));
        Skill rockSlide = new Skill("Rock Slide", List.of(damage20Norm));
        Skill sharpie = new Skill("Sharpie", List.of(attackMinus5DefenseMinus5));
        Skill sing = new Skill("Sing", List.of(damage5Norm));
        Skill pound = new Skill("Pound", List.of(damage10Norm));
        Skill doubleSlap = new Skill("Double Slap", List.of(damage15Norm));
        Skill mist = new Skill("Mist", List.of(damage5Norm));
        Skill iceShard = new Skill("Ice Shard", List.of(damage10Norm));
        Skill iceBeam = new Skill("Ice Beam", List.of(damage20Norm));
        Skill sheerCold = new Skill("Sheer Cold", List.of(damage30Norm));
        Skill voltTackle = new Skill("Volt Tackle", List.of(damage20Norm, defenseMinus10));
        Skill thunderShock = new Skill("Thunder Shock", List.of(damage10Norm));
        Skill thunder = new Skill("Thunder", List.of(damage25Norm));
        Skill confusion = new Skill("Confusion", List.of(damage10Norm));
        Skill snore = new Skill("Snore", List.of(damage5Norm));
        Skill waterGun = new Skill("Water Gun", List.of(damage10Water));
        Skill hydroPump = new Skill("Hydro Pump", List.of(damage25Water));
        Skill waterPulse = new Skill("Water Pulse", List.of(damage15Water));
        Skill waterfall = new Skill("Waterfall", List.of(damage20Water));
        Skill hydroCannon = new Skill("Hydro Cannon", List.of(damage30Water, attackMinus10));
        Skill splash = new Skill("Splash", List.of(damage0));
        Skill bubble = new Skill("Bubble", List.of(damage5Water));
        Skill willOWisp =  new Skill("Will-O-Wisp", List.of(damage5Fire));
        Skill incinerate = new Skill("Incinerate", List.of(damage15Fire));
        Skill fireBlast = new Skill("Fire Blast", List.of(damage25Fire, dotDmg5Turn5));
        Skill blastBurn = new Skill("Blast Burn", List.of(damage30Fire, attackMinus10));
        Skill absorb = new Skill("Absorb", List.of(damage5Grass));
        Skill frenzyPlant = new Skill("Frenzy Plant", List.of(damage30Grass, attackMinus5DefenseMinus5));
        Skill thunderbolt = new Skill("Thunderbolt", List.of(damage20Norm));
        Skill fireSpin = new Skill("Fire Spin", List.of(dotDmg5Turn5));
        Skill leechSeed =  new Skill("Leech Seed", List.of(dotDmg5Turn5));
        Skill toxic = new Skill("Toxic", List.of(dotDmg10Turn3));
        Skill headbutt = new Skill("Headbutt", List.of(damage25Norm, defenseMinus20));
        Skill howl = new Skill("Howl", List.of(attack10));
        Skill swordsDance = new Skill("Swords Dance", List.of(attack20));
        Skill flameCharge = new Skill("Flame Charge", List.of(damage15Fire));
        Skill stomp = new Skill("Stomp", List.of(damage15Norm));
        Skill sunnyDay = new Skill("Sunny Day", List.of(attack10));
        Skill surf = new Skill("Surf", List.of(damage20Water));
        Skill scald = new Skill("Scald", List.of(damage15Water, dotDmg5Turn5));
        Skill whirlpool = new Skill("Whirlpool", List.of(dotDmg10Turn3));

        // Defensive skills
        Skill endure = new Skill("Endure", List.of(defense10));
        Skill block = new Skill("Block", List.of(defense10));
        Skill protect = new Skill("Protect", List.of(defense20));
        Skill healPulse = new Skill("Heal Pulse", List.of(heal15));
        Skill rest = new Skill("Rest", List.of(heal25));
        Skill curse = new Skill("Curse", List.of(attack5Defense5));
        Skill imprison = new Skill("Imprison", List.of(paralysis));
        Skill growl = new Skill("Growl", List.of(defense10));
        Skill thunderWave = new Skill("Thunder Wave", List.of(paralysis));
        Skill stunSpore = new Skill("Stun Spore", List.of(paralysis));

        // Stat groups
        StatGroup weakBalanced = new StatGroup(75, 20, 20);
        StatGroup strongBalanced = new StatGroup(100, 25, 25);
        StatGroup glassCannon = new StatGroup(75, 35, 15);
        StatGroup bulky = new StatGroup(125, 15, 35);

        // Hit point strategies
        HitPointStrategy strategyDefault = new HitPointStrategy(5, 5);

        // Skill lists
        Skill[] defenseSkillsDefault = new Skill[]{growl, block, protect};

        // Build pokemon
        final PokemonModel charizard = PokemonModel.builder().nickname("Charizard").type("FIRE").offensiveSkills(new Skill[]{blastBurn, fireBlast, seismicToss, fireSpin}).defensiveSkills(defenseSkillsDefault).baseStats(strongBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel blastoise = PokemonModel.builder().nickname("Blastoise").type("WATER").offensiveSkills(new Skill[]{hydroCannon, hydroPump, megaPunch, headbutt}).defensiveSkills(defenseSkillsDefault).baseStats(strongBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel venusaur = PokemonModel.builder().nickname("Venusaur").type("GRASS").offensiveSkills(new Skill[]{frenzyPlant, leafStorm, megaPunch, leechSeed}).defensiveSkills(defenseSkillsDefault).baseStats(strongBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel raichu = PokemonModel.builder().nickname("Raichu").type("NORMAL").offensiveSkills(new Skill[]{thunder, thunderbolt, megaKick, voltTackle}).defensiveSkills(new Skill[]{growl, block, protect, thunderWave}).baseStats(glassCannon).hitPointStrategy(strategyDefault).build();
        final PokemonModel gastly = PokemonModel.builder().nickname("Gastly").type("NORMAL").offensiveSkills(new Skill[]{incinerate, mist, willOWisp, poisonPowder}).defensiveSkills(new Skill[]{block, curse, imprison, growl}).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel gengar = PokemonModel.builder().nickname("Gengar").type("NORMAL").offensiveSkills(new Skill[]{psychic, flamethrower, thunderbolt, toxic}).defensiveSkills(new Skill[]{curse, imprison, protect}).baseStats(strongBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel gyarados = PokemonModel.builder().nickname("Gyarados").type("WATER").offensiveSkills(new Skill[]{waterfall, earthQuake, tackle, swordsDance}).defensiveSkills(defenseSkillsDefault).baseStats(glassCannon).hitPointStrategy(strategyDefault).build();
        final PokemonModel abra = PokemonModel.builder().nickname("Abra").type("NORMAL").offensiveSkills(new Skill[]{megaPunch, megaKick, seismicToss, psychic}).defensiveSkills(defenseSkillsDefault).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel bulbasaur = PokemonModel.builder().nickname("Bulbasaur").type("GRASS").offensiveSkills(new Skill[]{solarBeam, vineWhip, razorLeaf, leechSeed}).defensiveSkills(defenseSkillsDefault).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel butterfree = PokemonModel.builder().nickname("Butterfree").type("NORMAL").offensiveSkills(new Skill[]{poisonPowder, gust, whirlwind, solarBeam}).defensiveSkills(defenseSkillsDefault).baseStats(glassCannon).hitPointStrategy(strategyDefault).build();
        final PokemonModel charmander = PokemonModel.builder().nickname("Charmander").type("FIRE").offensiveSkills(new Skill[]{fireSpin, scratch, ember, flamethrower}).defensiveSkills(defenseSkillsDefault).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel ditto = PokemonModel.builder().nickname("Ditto").type("NORMAL").offensiveSkills(new Skill[]{transform}).defensiveSkills(defenseSkillsDefault).baseStats(bulky).hitPointStrategy(strategyDefault).build();
        final PokemonModel geodude = PokemonModel.builder().nickname("Geodude").type("NORMAL").offensiveSkills(new Skill[]{tackle, rockThrow, earthQuake, rockSlide}).defensiveSkills(defenseSkillsDefault).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel jigglypuff = PokemonModel.builder().nickname("Jigglypuff").type("NORMAL").offensiveSkills(new Skill[]{sharpie, sing, pound, doubleSlap}).defensiveSkills(defenseSkillsDefault).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel lapras = PokemonModel.builder().nickname("Lapras").type("NORMAL").offensiveSkills(new Skill[]{mist, iceShard, iceBeam, sheerCold}).defensiveSkills(defenseSkillsDefault).baseStats(bulky).hitPointStrategy(strategyDefault).build();
        final PokemonModel mew = PokemonModel.builder().nickname("Mew").type("NORMAL").offensiveSkills(new Skill[]{toxic, transform, pound, psychic}).defensiveSkills(new Skill[]{curse, healPulse, imprison, protect}).baseStats(strongBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel pikachu = PokemonModel.builder().nickname("Pikachu").type("NORMAL").offensiveSkills(new Skill[]{megaPunch, voltTackle, thunderShock, thunder}).defensiveSkills(new Skill[]{growl, block, protect, thunderWave}).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel slowpoke = PokemonModel.builder().nickname("Slowpoke").type("NORMAL").offensiveSkills(new Skill[]{tackle, curse, confusion, psychic}).defensiveSkills(new Skill[]{healPulse, endure, block, protect}).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel snorlax = PokemonModel.builder().nickname("Snorlax").type("NORMAL").offensiveSkills(new Skill[]{megaPunch, tackle, snore, howl}).defensiveSkills(new Skill[]{growl, block, protect, rest}).baseStats(bulky).hitPointStrategy(strategyDefault).build();
        final PokemonModel squirtle = PokemonModel.builder().nickname("Squirtle").type("WATER").offensiveSkills(new Skill[]{waterPulse, tackle, waterGun, hydroPump}).defensiveSkills(defenseSkillsDefault).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel magikarp =  PokemonModel.builder().nickname("Magikarp").type("WATER").offensiveSkills(new Skill[]{bubble, splash}).defensiveSkills(new Skill[]{endure}).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel oddish = PokemonModel.builder().nickname("Oddish").type("GRASS").offensiveSkills(new Skill[]{vineWhip, absorb, pound, poisonPowder}).defensiveSkills(defenseSkillsDefault).baseStats(weakBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel parasect = PokemonModel.builder().nickname("Parasect").type("GRASS").offensiveSkills(new Skill[]{magicalLeaf, poisonPowder, solarBeam, frenzyPlant}).defensiveSkills(new Skill[]{stunSpore, endure, block, protect}).baseStats(strongBalanced).hitPointStrategy(strategyDefault).build();
        final PokemonModel rapidash = PokemonModel.builder().nickname("Rapidash").type("FIRE").offensiveSkills(new Skill[]{fireBlast, flameCharge, stomp, sunnyDay}).defensiveSkills(defenseSkillsDefault).baseStats(glassCannon).hitPointStrategy(strategyDefault).build();
        final PokemonModel slowbro = PokemonModel.builder().nickname("Slowbro").type("WATER").offensiveSkills(new Skill[]{surf, confusion, scald, whirlpool}).defensiveSkills(new Skill[]{healPulse, endure, block, protect}).baseStats(glassCannon).hitPointStrategy(strategyDefault).build();

        pokemonRepository.save(charizard);
        pokemonRepository.save(blastoise);
        pokemonRepository.save(venusaur);
        pokemonRepository.save(raichu);
        pokemonRepository.save(gastly);
        pokemonRepository.save(gengar);
        pokemonRepository.save(gyarados);
        pokemonRepository.save(abra);
        pokemonRepository.save(bulbasaur);
        pokemonRepository.save(butterfree);
        pokemonRepository.save(charmander);
        pokemonRepository.save(ditto);
        pokemonRepository.save(geodude);
        pokemonRepository.save(jigglypuff);
        pokemonRepository.save(lapras);
        pokemonRepository.save(mew);
        pokemonRepository.save(pikachu);
        pokemonRepository.save(slowpoke);
        pokemonRepository.save(snorlax);
        pokemonRepository.save(squirtle);
        pokemonRepository.save(magikarp);
        pokemonRepository.save(oddish);
        pokemonRepository.save(parasect);
        pokemonRepository.save(rapidash);
        pokemonRepository.save(slowbro);
    }
}
