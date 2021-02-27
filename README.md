# dicerollergame

Application reads player configuration from Console,
Accepts player number between 1-4
and for each player requires input player name from Console

After setting player count and player names, automatically applys game rules 
on each player, rolls dices in turns, and according ro rules calculates scores,
final user which gets to score 25 wons the game. 

Note: After first 6, If user rolls 4 then wont get negative point, but will go back to not started state,
but after started state, if user rolls 4's then it can go to negative values, as it is not specified in rules.

Rules:
The rules of the game are:
● There is amaximum of 4players.
● You can enter the actual amount players.
● You can name the different players.
● The first player to get a total sum of 25 is the winner.A player
does not have to get 25 exactly (>=25 is OK).
● To get started the player will need to get 6.If the player gets
1-5 they will then have to wait for their turn before having
another go.
● When finally hitting the number 6 the player will have to throw
again to determine the starting point. Getting a 6 on the first
try will give you 0.
● Each time a player hits number 4,he will get -4 from the total
score.
● If a player hits a 4 after hitting the first 6,they do not get a
     negative score but will have to roll another 6 before they start
accumulating points.
● Each time a player hits the number 6 he will then get one extra
throw.