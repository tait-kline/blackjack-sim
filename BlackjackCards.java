public class BlackjackCards extends RandIndexQueue<Card>
{
    public BlackjackCards(int size)
    {
        super(size);
    }

    public int getValue()
    {
        int totalValue = 0; // Total value of hand
        int numAces = 0;    // # of aces in hand

        for(int i = 0 ; i < size() ; i++)
        {
            Card curCard = get(i);
            totalValue += curCard.value();

            // if card is an ace
            if (curCard.value() != curCard.value2())
            {
                numAces ++; // increment aces
            }

        }

        // if we bust and have aces in hand
        if(totalValue > 21 && numAces > 0)
        {
            int i = 0;  // loop control variable
            // loop until we are under 21 or out of aces to change
            while(totalValue > 21 && i < numAces)
            {

                totalValue -= 10;   // subtract diference between ace values from total
                i++;
            }
            
            
        }

        return totalValue;
    }
}
