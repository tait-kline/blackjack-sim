
public class RandIndexQueue<T>
{
    private int front;  // index of logical front of array
    private int back;   // index of logical back of array 
    private int count;  // logical size of array (number of objects in array)

    public T[] queue; // SET BACK TO PRIVATE AFTER TESTING!!!

    // constructor that sets fornt and back to -1, this value will indicate
    // that the array is empty
    public RandIndexQueue(int size)
    {
        queue = (T[]) new Object[size];
        count = 0;
        front = -1;
        back = -1;
    }


    public void enqueue(T newEntry)
    {
        // if array is empty, assign newEnrty at index 0
        // and incement all counter variables
        if (count == 0)
        {
            queue[0] = newEntry;

            front++;   // front & back are now both 0
            back++;    // becauase we only have 1 object in array    
            count ++;
        }

        // if array is full, resize
        else if (count == queue.length)
        {
            // resize();
            // queue[count - 1] = newEntry
        }

        // if there is no space at end of array, place new entry at beginning
        else if (back == queue.length - 1)
        {
            queue[0] = newEntry;
            back = 0;   // set back to 0
            count ++;
            
        }

        // else, just add at index back + 1
        else
        {
            queue[back + 1] = newEntry;
            back ++;
            count++;
        }
        
    }

    private void resize()
    {
        
    }

    // Remove and return object at front of array
    public T dequeue()
    {
        if (isEmpty())
        {
            throw new EmptyQueueException("Error: Queue is empty.");
        }

        else
        {
            T frontEntry = queue[front];
            queue[front] = null;

            // if front is at end of array & is not the last item,
            // move front to beginning
            if((front == queue.length - 1) & (front != back))
            {
                front = 0;
                count --;
            }

            // if we are removing last item in array,
            // set front & back to empty value of -1.
            else if(front == back)
            {
                front = -1;
                back = -1;
                count --;
            }

            else
            {
                front ++;
                count --;

            }
            
           

            return frontEntry;
        }

        
    
        
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public String toString()
    {
       String retString = "\nFront: " + front +
                    "\nBack: " + back +
                    "\nCount: " + count +
                    "\nQueue: ";

        for (T item : queue)
        {
            retString += (item + " ");
        }

        return retString;

        
    }



    
}
