import java.util.Arrays;

/**
 * This class is used to perform the game's necessary functions.
 *
 * @author Ng Kong Pang
 * @version 1.0
 */
public class Game {
    private int[] deckOfCards = new int[52];

    /**
     * This constructor will create a deck of card in order.
     *
     */
    public Game(){
        int i = 0;

        for (int cardType = 1; cardType < 5; cardType++){
            for (int cardNum = 1; cardNum < 14; cardNum++){
                deckOfCards[i] = Integer.parseInt(Integer.toString(cardType) + Integer.toString(cardNum));
                i++;
            }
        }
    }

    /**
     * This method will shuffle the deck by exchange two cards (randomly chosen each time) in the deck for 100 times.
     *
     */
    public void shuffleDeck(){
        int shuffleCard1, shuffleCard2, temp;
        for(int i = 0; i < 100; i++){
            shuffleCard1 = (int) (Math.random() * 52);
            shuffleCard2 = (int) (Math.random() * 52);

            temp = deckOfCards[shuffleCard2];
            deckOfCards[shuffleCard2] = deckOfCards[shuffleCard1];
            deckOfCards[shuffleCard1] = temp;
        }
    }

    /**
     * This method draws a card from the top of the deck.
     *
     * @return The value of the card as a int
     */
    public int draw(){
        int returnCard = deckOfCards[0];
        for (int i = 0; i < 51; i++){
            deckOfCards[i] = deckOfCards[i+1];
        }
        deckOfCards[51] = 0;
        return returnCard;
    }

    /**
     * This method put a card back to the bottom of the deck.
     *
     * @param card The value of the card needs to be put back into the deck as a int
     */
    public void putBackCard(int card){
        for (int i = 0; i < 52; i++){
            if(deckOfCards[i] == 0){
                deckOfCards[i] = card;
                return;
            }
        }
    }

    /**
     * This method check if the player won the game or not.
     *
     * @param cards array of int length=6, that represent the 6 cards
     * @return True when player wins, False when player losses
     */
    public boolean checkResult(int[] cards){
        int dealerSpecialCards = 0;
        int playerSpecialCards = 0;
        int dealerPoints = 0;
        int playerPoints = 0;

        for (int i = 0; i < 6; i++){
            if(cards[i] > 100){
                cards[i] = cards[i]%100;
            }else{
                cards[i] = cards[i]%10;
            }
        }

        for (int i = 0; i < 3; i++){
            if(cards[i] >= 11){
                dealerSpecialCards++;
            }else {
                dealerPoints = dealerPoints + cards[i];
            }
        }
        for (int i = 3; i < 6; i++){
            if(cards[i] >= 11){
                playerSpecialCards++;
            }else {
                playerPoints = playerPoints + cards[i];
            }
        }
        playerPoints = playerPoints % 10;
        dealerPoints = dealerPoints % 10;

        if(playerSpecialCards > dealerSpecialCards){
            return true;
        }else if(dealerSpecialCards > playerSpecialCards){
            return false;
        }else if(playerPoints > dealerPoints){
            return true;
        }else {
            return false;
        }
    }

    /**
     * This method check if player's bet is a positive int.
     *
     * @param userInput Player's input of a bet as a String
     * @return True if player's bet is valid, otherwise False.
     */
    public boolean checkBet(String userInput){
        try {
            Integer.parseInt(userInput);
        } catch (NumberFormatException error){
            return false;
        }

        int inputAmount = Integer.parseInt(userInput);

        if(inputAmount > 0){
            return true;
        }else {
            return false;
        }
    }

}
