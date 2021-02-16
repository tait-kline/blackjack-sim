// Tait Kline
// This is the class that holds the main function of my Blackjack program.
public class Blackjack
{
    static BlackjackCards shoe;
    static BlackjackCards discardPile;
    static BlackjackCards playerHand;
    static BlackjackCards dealerHand;

    static int dealerWins = 0;
    static int playerWins = 0;
    static int ties = 0;

    static StringBuilder traceString = new StringBuilder(); // Used to keep track of trace output


    // Initializes and returns shoe
    public static void initializeShoe(int numOfDecks)
    {
        shoe = new BlackjackCards(52 * numOfDecks);
        
         // Create cards for the shoe
         // This loop will repeat itereate for every deck needed
        for(int i = 0 ; i < numOfDecks ; i++)
        {
            for (Card.Suits s: Card.Suits.values())
            {
                for (Card.Ranks r: Card.Ranks.values())
                {
                    // Creating 2 cards for every suit and rank
                    // and adding them to the shoe
                    Card c1 = new Card(s, r);
                    
                    shoe.enqueue(c1);
                }
            }

        }

        shoe.shuffle();
    }

    public static void dealCards()
    {
        playerHand.enqueue(shoe.dequeue());
        dealerHand.enqueue(shoe.dequeue());
        playerHand.enqueue(shoe.dequeue());
        dealerHand.enqueue(shoe.dequeue());
    }

    // Determines player logic for the round.
    // Returns true if player busted.
    public static boolean playerPlays()
    {
        boolean busted = false;

        // if player takes initial stand
        if((playerHand.getValue() >= 17) && (playerHand.getValue() < 22))
        {
            traceString.append("\nPlayer Stands: " + playerHand + " : " + playerHand.getValue());
        }

        
        while(playerHand.getValue() < 17) // while player wants to hit
        {
            //hit
            Card hit = shoe.dequeue();
            playerHand.enqueue(hit);
            traceString.append("\nPlayer Hits: " + hit);
            
            // player stands after hit
            if((playerHand.getValue() >= 17) && (playerHand.getValue() < 22))
            {
                traceString.append("\nPlayer Stands: " + playerHand + " : " + playerHand.getValue());
            }

            // player busts
            else if(playerHand.getValue() > 21)
            {
                busted = true;
                traceString.append("\nPlayer Busts: " + playerHand + " : " + playerHand.getValue());
            }
        }

        return busted;
    }

    public static void dealerPlays()
    {
        // if dealer takes initial stand
        if((dealerHand.getValue() >= 17) && (dealerHand.getValue() < 22))
        {
            traceString.append("\nDealer Stands: " + dealerHand + " : " + dealerHand.getValue());
        }

        while(dealerHand.getValue() < 17)   // While dealer wants to hit
        {
            //hit
            Card hit = shoe.dequeue();
            dealerHand.enqueue(hit);
            traceString.append("\nDealer Hits: " + hit);

            // dealer stands after hit
            if((dealerHand.getValue() >= 17) && (dealerHand.getValue() < 22))
            {
                
                traceString.append("\nDealer Stands: " + dealerHand + " : " + dealerHand.getValue());
                break;
            }

            // delaer busts
            else if(dealerHand.getValue() > 21)
            {
                traceString.append("\nDealer Busts: " + dealerHand + " : " + dealerHand.getValue());
                break;
            }

        }
    }

    public static void determineWinner()
    {
        int dealerScore = dealerHand.getValue();
        int playerScore = playerHand.getValue();
        
        // If someone busted
        if(dealerScore > 21 || playerScore > 21)
        {
            // If delaer busted
            if(dealerScore > playerScore)
            {
                traceString.append("\nPlayer Wins!");

                playerWins++;
            }

            // Else player busted
            else
            {
                traceString.append("\nDealer Wins!");

                dealerWins++;
            }

           
        }

        // Else hand with highest score wins
        else
        {
            // If player wins
            if(playerScore > dealerScore)
            {
                traceString.append("\nPlayer Wins!");
                playerWins++;
            }

            // else if dealer wins
            else if(dealerScore > playerScore)
            {
                traceString.append("\nDealer Wins!");
                dealerWins++;
            }

            // Else score is tied
            else
            {  
                traceString.append("\nPush");

                ties++;

            }
        }
    }

    // Removes the cards in the hands that are passed in the argument
    // and puts them into the discard pile
    public static void discard(BlackjackCards hand)
    {
        while(0 != hand.size())
        {
            discardPile.enqueue(hand.dequeue());
           
        }

    }

    // checks if shoe needs replenished and does so if needed
    // also reshuffles
    // returns true if shoe is replenished and reshuffled
    public static boolean checkShoe()
    {
        boolean shuffled = false;

        if(discardPile.size() >= (shoe.capacity() * 0.75))
        {
            shuffled = true;
           
            // Add discarded cards into shoe
            while(0 != discardPile.size())
            {
                shoe.enqueue(discardPile.dequeue());
            }

            shoe.shuffle();
        }

        return shuffled;
    }

    public static String showResults()
    {
        String results = "\nDealer Wins: " + dealerWins +
                        " \nPlayer Wins: " + playerWins +
                        "\nPushes: " + ties;

        return results;
    }

    public static void main(String[] args)
    {
        // Initialize shoe and discard
        initializeShoe(Integer.parseInt(args[1]));
        discardPile = new BlackjackCards(shoe.capacity()/2); // Discard is half the size of shoe
       
        // Initializing hands
        playerHand = new BlackjackCards(10);
        dealerHand = new BlackjackCards(10);

        System.out.println("\nStarting Blackjack with " + args[0] + " rounds and " + args[1] + " decks in the shoe.");

        // round begins here, using for loop to count # of rounds
        for(int i = 0; i < Integer.parseInt(args[0]) ; i++)
        {
            traceString.append("\n\nRound " + (i+1) + " beginning.");

            dealCards();
            
            traceString.append("\nPlayer: " + playerHand + " : " + playerHand.getValue());
            traceString.append("\nDealer: " + dealerHand + " : " + dealerHand.getValue());

            // If someone has blackjack on initial deal, determine winner
            if(playerHand.getValue() == 21 || dealerHand.getValue() == 21)
            {
                determineWinner();
            }

            // else play continues
            else
            {
                // if player busts
                if(playerPlays())
                {
                    determineWinner();
                }

                // else player does not bust
                else
                {
                    dealerPlays();
                    determineWinner();
                }

            }

            discard(playerHand);
            discard(dealerHand);

          
            // If round is a trace round, print trace string
            if(Integer.parseInt(args[2]) > i)
            {
                System.out.println(traceString);
                traceString = new StringBuilder();
            }

              
            if(checkShoe())
            {
                System.out.println("\nReshuffling the shoe at the end of round " + (i+1));
            }
            
        }

        System.out.println("\nAfter " + args[0] + " rounds, here are the results: "
                            + showResults());

    }

}
