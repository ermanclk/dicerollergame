package org.familygame.service;

import org.familygame.domain.Player;

import java.util.List;


public class Game {

    private static final int WINNER_SCORE = 25;
    private static final int STARTER_DICE = 6;
    private static final int DOUBLE_ROLL_DICE = 6;
    private static final int NEGATIVE_SCORE_DICE = 4;

    private RollDiceServiceI rollDiceService;

    public Game(RollDiceServiceI rollDiceService) {
        this.rollDiceService = rollDiceService;
    }

    public Player start(List<Player> players) throws InterruptedException {
        Player winner;

        do {
            winner = playNextTurn(players);
        } while (winner == null);

        return winner;
    }

    private Player playNextTurn(List<Player> players) throws InterruptedException {
        Player winner = null;
        for (Player player : players) {
            play(player);

            if (player.getScore() >= WINNER_SCORE) {
                winner = player;
                break;
            }
        }
        return winner;
    }

    private void play(Player player) throws InterruptedException {
        int dice = rollTheDice(player);

        if (player.isStarted()) {
            applyRulesForStartedPlayer(player, dice);
        } else {
            applyRulesForPlayerOnStart(player, dice);
        }

    }

    /**
     * Already started player:
     *  - rolls again if rolls 6
     *  - goes NEGATIVE_SCORE_DICE points back if rolls NEGATIVE_SCORE_DICE
     *  - adds new score if not NEGATIVE_SCORE_DICE
     * @param player
     * @param dice
     */
    private void applyRulesForStartedPlayer(Player player, int dice) throws InterruptedException {
        while (dice == DOUBLE_ROLL_DICE && player.getScore() < WINNER_SCORE) {
            player.setScore(player.getScore() + dice);
            dice = rollTheDice(player);
        }
        if (dice == NEGATIVE_SCORE_DICE) {
            player.setScore(player.getScore() - NEGATIVE_SCORE_DICE);
        } else {
            player.setScore(player.getScore() + dice);
        }
    }

    /**
     * Player that is not started yet:
     *  - can only start if dice is equals to STARTER_DICE
     *  - if dice is STARTER_DICE and next dice is NEGATIVE_SCORE_DICE, then cannot start.
     *  - rolls again if rolls DOUBLE_ROLL_DICE
     *  - After throwing 6 and 6 and after that 4, as rule says only apply return back penalty after first 6, then after second 6
     *  only extract penalty points, do not return back to start.
     * @param player
     * @param dice
     */
    private void applyRulesForPlayerOnStart(Player player, int dice) throws InterruptedException {
        if (dice != STARTER_DICE) {
            return;
        }

        player.start();
        dice = rollTheDice(player);
        if (dice == NEGATIVE_SCORE_DICE) {
            player.backToInitialState();
            return;
        }

        while (dice == DOUBLE_ROLL_DICE && player.getScore() + dice < WINNER_SCORE) {
            player.setScore(player.getScore() + dice);
            dice = rollTheDice(player);
        }

        //If user rolls 4 after second 6, then do not go back to start, just decrease score
        if (dice == NEGATIVE_SCORE_DICE) {
            player.setScore(player.getScore() - NEGATIVE_SCORE_DICE);
        }else{
            player.setScore(player.getScore() + dice);
        }
    }

    public int rollTheDice(Player player) throws InterruptedException {
        int dice = rollDiceService.callRemoteDiceRoller();
        System.out.println("Player name:" + player.getName() + ", Total Score:" + player.getScore() + ", Current Value of Dice:" + dice + " ");
        return dice;
    }

}
