public class Main 
{
    public static void main(String[] args)
    {

        // initialize

        BlackjackCards shoe = new BlackjackCards(52); // multiply by Num of decks later!
        int dealerWins = 0;
        int playerWins = 0;
        int ties = 0;
        boolean gameOver = false;

        // Create cards for the shoe
        // LOOP THIS FOR # OF SHOES NEEDED!
        for (Card.Suits s: Card.Suits.values())
        {
            for (Card.Ranks r: Card.Ranks.values())
            {
                // Creating 2 cards for every suit and rank
                // and adding them to the shoe
                Card c1 = new Card(s, r);
		        Card c2 = new Card(s, r);

                shoe.enqueue(c1);
                shoe.enqueue(c2);


            }
        }

        shoe.shuffle();

        System.out.println(shoe);

        BlackjackCards playerHand = new BlackjackCards(5);
        BlackjackCards dealerHand = new BlackjackCards(5);

        ////////////////////////////////////////////////////////////////

        // initial deal//
        playerHand.enqueue(shoe.dequeue());
        dealerHand.enqueue(shoe.dequeue());
        playerHand.enqueue(shoe.dequeue());
        dealerHand.enqueue(shoe.dequeue());

        System.out.println(playerHand);
        System.out.println(dealerHand);

        //play loop//
        int playerValue = playerHand.getValue();
        int dealerValue = dealerHand.getValue();

        // someone has blackjack
        if(playerValue == 21 || dealerValue == 21)
        {
            if(playerValue > dealerValue)
            {
                System.out.println("Player Wins on initial blackjack!"); 
                playerWins++;
                gameOver = true;
            }

            else if(dealerValue > playerValue)
            {
                System.out.println("Dealer Wins on initial blackjack!"); 
                dealerWins++;
                gameOver = true;
            }

            else
            {
                System.out.println("Round ends in a draw on initial blackjack."); 
                ties++;
                gameOver = true;
            }
        }

        //play round//
        // player decides
        while(playerHand.getValue() < 17 && (!gameOver) )
        {
            //hit
            playerHand.enqueue(shoe.dequeue());
            System.out.println("Player hit.");
            System.out.println(playerHand);
            

            if((playerHand.getValue() >= 17) && (playerHand.getValue() < 22))
            {
                //stand
                System.out.println("Player stood.");
                break;
            }

            else if(playerHand.getValue() > 21)
            {
                //bust maybe do something to end round here?
                System.out.println("Player Busted");
                gameOver = true;
                break;
            }

        }

        // dealer decides
        if(!gameOver)
        {
            while(dealerHand.getValue() < 17)
            {
                //hit
                dealerHand.enqueue(shoe.dequeue());
                System.out.println("Dealer hit.");
                System.out.println(dealerHand);

                if((dealerHand.getValue() >= 17) && (dealerHand.getValue() < 22))
                {
                    //stand
                    System.out.println("Dealer stood.");
                    break;
                }

                else if(dealerHand.getValue() > 21)
                {
                    //bust maybe do something to end round here?
                    System.out.println("Dealer Busted");
                    gameOver = true;
                    break;
                }

            }

        }

        // finalize round//
        if((dealerHand.getValue() < 21) && (playerHand.getValue() < 21))
        {
            //find winner
            if(dealerHand.getValue() > playerHand.getValue())
            {
                System.out.println("Dealer wins round with a score of: " +
                "\nDealer: " + dealerHand.getValue() + "\nPlayer: " + playerHand.getValue());

                dealerWins++;
            }

            else if(playerHand.getValue() > dealerHand.getValue())
            {
                System.out.println("Player wins round with a score of: " +
                "\nDealer: " + dealerHand.getValue() + "\nPlayer: " + playerHand.getValue());

                playerWins++;
            }

            else
            {
                System.out.println("Round Tied." +
                "\nDealer: " + dealerHand.getValue() + "\nPlayer: " + playerHand.getValue());

                ties++;
            }
        }







    }


}
