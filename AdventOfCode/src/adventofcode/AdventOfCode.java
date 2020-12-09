package adventofcode;

import adventofcode.challenge.ChallengeManager;
import adventofcode.challenge.days.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdventOfCode {
    public static final Logger LOGGER = Logger.getLogger(AdventOfCode.class.getName());

    public static void main(String[] args) {
        LOGGER.setLevel(Level.INFO);

        ClassLoader c1 = AdventOfCode.class.getClassLoader();

        ChallengeManager challengeManager = new ChallengeManager();
        challengeManager.addChallenge(new Day1_0("1.0", new File("data/1_0.txt")));

        if(args.length != 1) {
            LOGGER.log(Level.SEVERE, "No ID was given as an argument");
            System.exit(0);
        }

        challengeManager.runChallenge(args[0]);
    }
}
