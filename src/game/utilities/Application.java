package game.utilities;

import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.positions.World;
import game.actors.*;
import game.actors.boss.BedOfChaos;
import game.attributes.Status;
import game.behaviours.controllers.RandomNPCController;
import game.behaviours.controllers.StandardNPCController;
import game.conditions.Condition;
import game.conditions.SurroundCapabilitiesCondition;
import game.conditions.TickCondition;
import game.grounds.*;
import game.items.Talisman;
import game.miningSystem.BombTrap;
import game.miningSystem.MineItem;
import game.miningSystem.StaggerTrap;
import game.miningSystem.WoodTrap;
import game.offspring.producer.GoldenBeetleProducer;
import game.offspring.producer.OmenSheepProducer;
import game.offspring.producer.SpiritGoatProducer;
import game.phases.*;
import game.phases.managers.MerchantSleepManager;
import game.phases.managers.TimePhaseManager;
import game.plants.Bloodrose;
import game.plants.Inheritree;
import game.plants.Seed;
import game.rules.CycleIfConditionRule;
import game.phases.PhaseTransitionLogic;
import game.rules.TransitionRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * The main class to setup and run the game.
 * @author Adrian Kristanto
 * Modified by: hewjiajing
 */
public class Application {

    public static void main(String[] args) {

        World world = new World(new Display());

        FancyGroundFactory groundFactory = new FancyGroundFactory(new Blight(),
                new Wall(), new Floor(), new Soil());

        List<String> map = Arrays.asList(
                "xxxx...xxxxxxxxxxxxxxxxxxxxxxx........xx",
                "xxx.....xxxxxxx..xxxxxxxxxxxxx.........x",
                "..........xxxx....xxxxxxxxxxxxxx.......x",
                "....xxx...........xxxxxxxxxxxxxxx.....xx",
                "...xxxxx...........xxxxxxxxxxxxxx.....xx",
                "...xxxxxxxxxx.......xxxxxxxx...xx......x",
                "....xxxxxxxxxx........xxxxxx...xxx......",
                "....xxxxxxxxxxx.........xxx....xxxx.....",
                "....xxxxxxxxxxx................xxxx.....",
                "...xxxx...xxxxxx.....#####.....xxx......",
                "...xxx....xxxxxxx....#___#.....xx.......",
                "..xxxx...xxxxxxxxx...#___#....xx........",
                "xxxxx...xxxxxxxxxx...##_##...xxx.......x",
                "xxxxx..xxxxxxxxxxx.........xxxxx......xx",
                "xxxxx..xxxxxxxxxxxx.......xxxxxx......xx");

        GameMap gameMap = new GameMap("Valley of the Inheritree", groundFactory, map);
        world.addGameMap(gameMap);

        List<String> limveld = Arrays.asList(
                ".............xxxx",
                "..............xxx",
                "................x",
                ".................",
                "................x",
                "...............xx",
                "..............xxx",
                "..............xxx",
                "..............xxx",
                ".............xxxx",
                ".............xxxx",
                "....xxx.....xxxxx",
                "....xxxx...xxxxxx");

        GameMap limveldMap = new GameMap("Limveld", groundFactory, limveld);
        world.addGameMap(limveldMap);

        List<Location> existingGameMaps = new ArrayList<>();
        existingGameMaps.add(gameMap.at(35, 11));
        existingGameMaps.add(limveldMap.at(5, 6));

        limveldMap.at(5, 6).setGround(new TeleportationCircle(existingGameMaps));
        gameMap.at(35, 11).setGround(new TeleportationCircle(existingGameMaps));

        // BEHOLD, ELDEN THING!
        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        MerchantSleepManager sleepManager = new MerchantSleepManager();
        List<TimePhase> phases = List.of(
                new DawnveilPhase(sleepManager),
                new DuskfallPhase(sleepManager),
                new CrimsonEclipsePhase()
        );

        TreeMap<Integer, TransitionRule> rules = new TreeMap<>();
        rules.put(999, new CycleIfConditionRule(new TickCondition(1, 8)));

        PhaseTransitionLogic transitionLogic = new PhaseTransitionLogic(rules);
        TimePhaseManager timePhaseManager = new TimePhaseManager(phases, transitionLogic);

        WorldTrigger worldTrigger = new WorldTrigger(timePhaseManager);
        gameMap.at(0, 0).addActor(worldTrigger);

        limveldMap.at(10,10).addActor(new BedOfChaos(new StandardNPCController()));

        // the "Farmer" starts with 100 hit points (health) and 200 stamina.
        Player player = new Player("Farmer", '@', 100, 200);

        // the “Farmer” starts with one seed (*) of “Inheritree” and one seed of “Bloodrose” in their inventory
        player.addItemToInventory(new Seed(new Bloodrose()));
        player.addItemToInventory(new Seed(new Inheritree()));

        // add initial wallet balance to the player
        player.addBalance(5000);
        world.addPlayer(player, gameMap.at(35, 10));

        // game setup
        gameMap.at(24, 11).addItem(new Talisman());

        // initialize the SpiritGoat
        // Condition to produce when surround by Blessed
        Condition spiritGoatProduceCondition = new SurroundCapabilitiesCondition(Status.BLESSED);
        // SpiritGoat producer to produce a SpiritGoat
        SpiritGoatProducer spiritGoatProducer = new SpiritGoatProducer(spiritGoatProduceCondition, new StandardNPCController());
        SpiritGoat spiritGoat = new SpiritGoat(spiritGoatProduceCondition, spiritGoatProducer, new StandardNPCController());

        // initialize OmenSheep
        // Condition to produce after each 7 turns
        Condition omenSheepProduceCondition = new TickCondition(1, 7);
        // Condition for OmenSheep Egg to hatch (3 turns)
        Condition omenSheepEggHatchCondition = new TickCondition(1, 3);
        // the Producer which Egg of Sheep will hatch into
        OmenSheepProducer omenSheepProducer = new OmenSheepProducer(omenSheepProduceCondition, new StandardNPCController());
        OmenSheep omenSheep = new OmenSheep(omenSheepProduceCondition, omenSheepEggHatchCondition, omenSheepProducer, new StandardNPCController());

        // initialize GoldenBeetle
        Condition goldenBeetleProduceCondition = new TickCondition(1, 5);
        Condition goldenBeetleEggHatchCondition = new SurroundCapabilitiesCondition(Status.CURSED);
        GoldenBeetleProducer goldenBeetleProducer = new GoldenBeetleProducer(goldenBeetleProduceCondition, new StandardNPCController());
        GoldenBeetle goldenBeetle = new GoldenBeetle(goldenBeetleProduceCondition, goldenBeetleEggHatchCondition, goldenBeetleProducer, new StandardNPCController());

        Spriggle pet = new Spriggle(player, new StandardNPCController());  // assistant of player

        MineItem wood = new MineItem("Wood", new WoodTrap());
        MineItem explosivePowder = new MineItem("Explosive Powder", new BombTrap());
        MineItem magicalPotion = new MineItem("Magical Potion", new StaggerTrap());


        gameMap.at(15, 11).addActor(pet);
        gameMap.at(15, 12).addItem(wood);
        gameMap.at(18, 13).addItem(explosivePowder);
        gameMap.at(13, 13).addItem(magicalPotion);

        gameMap.at(24, 13).addActor(spiritGoat);
        gameMap.at(23, 12).addActor(omenSheep);
        gameMap.at(24, 11).addActor(goldenBeetle);

        gameMap.at(19, 10).addActor(new Guts(new StandardNPCController()));
        gameMap.at(19, 11).addActor(new Kale(new StandardNPCController()));
        gameMap.at(19, 12).addActor(new Sellen(new StandardNPCController()));

        OmenSheep omenSheepRandom = new OmenSheep(omenSheepProduceCondition, omenSheepEggHatchCondition, omenSheepProducer, new RandomNPCController());
        GoldenBeetle goldenBeetleRandom = new GoldenBeetle(goldenBeetleProduceCondition, goldenBeetleEggHatchCondition, goldenBeetleProducer, new RandomNPCController());

        limveldMap.at(5, 4).addActor(omenSheepRandom);
        limveldMap.at(5, 3).addActor(goldenBeetleRandom);
        world.run();
    }
}
