package org.familygame;

import org.familygame.domain.Player;
import org.familygame.exception.FamilyGameException;
import org.familygame.service.Game;
import org.familygame.service.RollDiceService;
import org.familygame.service.RollDiceServiceI;
import org.familygame.util.ConfigurationReader;

import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Application {

    /**
     * starting application
     * @param args
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        List<Player> players = readPlayerConfig();

        System.out.println("Starting game...");

        //initializing services by injecting dependencies
        RollDiceServiceI service = new RollDiceService(getHttpClient(), new ConfigurationReader());
        Player winner = new Game(service).start(players);

        System.out.println("Player " + winner.getName() + " won the game with " + winner.getScore() + " points!");

    }

    /**
     * read players configuration from console
     * @return
     */
    private static List<Player> readPlayerConfig() {

        System.out.println("You can enter the actual amount players.");

        Scanner in = new Scanner(System.in);
        int playerCount;

        try {
            playerCount = in.nextInt();
        } catch (InputMismatchException e) {
            throw new FamilyGameException("invalid input format, input for player count should be a number.",e);
        }

        if (playerCount > 4 || playerCount < 1) {
            throw new FamilyGameException("Game can be played with 1-4 players");
        }

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            System.out.println("Enter player " + i + " name:");
            String playerName = in.next();
            players.add(new Player(playerName));
        }
        return players;
    }

    /**
     * generates Http Client for injecting on required services.
     * @return
     */
    private static HttpClient getHttpClient(){
        return  HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

}
