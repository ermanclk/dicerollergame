package org.familygame.service;

import java.util.Random;

public class DiceRollerMockImpl implements RollDiceServiceI{

    private final int MIN_DICE_VALUE = 1;
    private final int MAX_DICE_VALUE = 6;

    @Override
    public int callRemoteDiceRoller() {
        return MIN_DICE_VALUE + new Random().nextInt(MAX_DICE_VALUE);
    }
}
