package org.familygame.service;

import org.familygame.domain.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    RollDiceServiceI rollDiceService;
    Player player1 ;
    Player player2 ;
    List players = new ArrayList<>();
    Game classUnderTest;

    @Before
    public void setUp() {
        player1 = new Player("player1");
        player2 = new Player("player2");
        players.add(player1);
        players.add(player2);
        rollDiceService = new DiceRollerMockImpl();

        classUnderTest= spy(new Game(rollDiceService));

    }

    @Test
    public void whenGameStartShouldReturnWinner() throws InterruptedException {

        Player winner = classUnderTest.start(players);

        assertThat( winner, is( notNullValue() ) );
    }

    /**
     *   player 1  rolls 5 after start, player 2 rolls 1 after start
     */
    @Test
    public void givenPlayer1RollsGoodScoreThenPlayer1ShouldWin() throws InterruptedException {

        when(classUnderTest.rollTheDice(player1))
                .thenReturn(6)
                .thenReturn(5);

        when(classUnderTest.rollTheDice(player2))
                .thenReturn(1);

        Player winner = classUnderTest.start(players);

        assertThat( winner, is( player1 ) );
        assertThat( winner.getScore(), is( equalTo(25) ) );
    }

    @Test
    public void givenPlayer1CannotRoll_6_Player2ShouldWin() throws InterruptedException {

        when(classUnderTest.rollTheDice(player1))
                .thenReturn(5);
        when(classUnderTest.rollTheDice(player2))
                .thenReturn(6)
                .thenReturn(1);

        Player winner = classUnderTest.start(players);

        assertThat( winner, is( player2 ) );
        assertThat( player1.getScore(), is( 0 ) );
    }

    /**
     * Player 1 rolls 4 after getting to 24, final player 1 score should be 20
     */
    @Test
    public void penaltyValuesShouldBeCalculatedOnScore() throws InterruptedException {

        when(classUnderTest.rollTheDice(player1)).thenReturn(6)
                .thenReturn(6)
                .thenReturn(6)
                .thenReturn(6)
                .thenReturn(6)
                .thenReturn(4);

        when(classUnderTest.rollTheDice(player2))
                .thenReturn(6);

        Player winner = classUnderTest.start(players);

        assertThat( winner, is( player2 ) );
        assertThat( player1.getScore(), is( 20 ) );
        assertThat( player2.getScore(), is( 30 ) );
    }

    /**
     *  User rolling 6 should roll again, test by checking final score,
     *  test 2 is unable to roll as player 1 always rolls 6
     */
    @Test
    public void whenDiceIs6PlayerRollsAgain() throws InterruptedException {

        when(classUnderTest.rollTheDice(player1)).thenReturn(6);
        Player winner = classUnderTest.start(players);
        assertThat( winner, is( player1 ) );
        assertThat( player1.getScore(), is( 30 ) );
        assertThat( player2.getScore(), is( 0 ) );
    }


}