// Tait Kline
// This class is an array based queue implementaion. The array is resizable but will
// not resize until the array is completely full.

import java.util.Random;
public class RandIndexQueue<T> implements MyQ<T>, Shufflable, Indexable<T>
{
    private int front;  // index of logical front of array
    private int back;   // index of logical back of array 
    private int count;  // logical size of array (number of objects in array)
    private int moves; // number of operations performed during enqueue() & dequeue()

    private T[] queue;

    // constructor that sets fornt and back to -1, this value will indicate
    // that the array is empty
    @SuppressWarnings("unchecked")
    public RandIndexQueue(int size)
    {
        
        queue = (T[]) new Object[size];
        count = 0;
        front = -1;
        back = -1;
    }

    // copy constructor
    @SuppressWarnings("unchecked")
    public RandIndexQueue (RandIndexQueue<T> old)
    {
        // create deep copy of array
        queue = (T[]) new Object[old.queue.length];

        for(int i = 0 ; i < queue.length ; i++)
        {
            queue[i] = old.queue[i];
        }

        count = old.count;
        front = old.front;
        back = old.back;

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
            moves ++;
        }

        // if array is full, resize
        else if (count == queue.length)
        {
            resize();
            queue[count] = newEntry;
            count ++;
            back ++;
            moves ++;
        }

        // if there is no space at end of array, place new entry at beginning
        else if (back == queue.length - 1)
        {
            queue[0] = newEntry;
            back = 0;   // set back to 0
            count ++;
            moves ++;
            
        }

        // else, just add at index back + 1
        else
        {
            queue[back + 1] = newEntry;
            back ++;
            count ++;
            moves ++;
        }
        
    }


    // Remove and return object at front of array
    public T dequeue()
    {
        if (isEmpty())
        {
            throw new EmptyQueueException("Queue is empty.");
        }

        else
        {
            T frontEntry = queue[front];
            queue[front] = null;
            moves ++;

            // if front is at end of array & is not the last item,
            // move front to beginning
            if((front == queue.length - 1) && (front != back))
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

    public T getFront()
    {
        if(isEmpty())
        {
            throw new EmptyQueueException("Queue is empty.");
        }

        else
        {
            return queue[front];
        }
    }

    @SuppressWarnings("unchecked")
    public void clear()
    {
        queue = (T[]) new Object[queue.length];

        // reset class variables
        count = 0;
        front = -1;
        back = -1;

    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public int size()
    {
        return count;
    }

    public int capacity()
    {
        return queue.length;
    }

    public int getMoves()
    {
        return moves;
    }

    public void setMoves(int moves)
    {
        this.moves = moves;
    }

    public T get(int i)
    {
        // if argument is greater than # of items in queue, throw exception
        if(i > count - 1)
        {
            throw new IndexOutOfBoundsException();
        }

        else
        {
            // if index + i wraps around to front of array,
            // subtract distance from front to end of array from i
            if(front + i > queue.length - 1)
            {
                return queue[i - (queue.length - front)];
            }

            // else, just return at front + i
            else
            {
                return queue[front + i];
            }

        }

    }

    public void set(int i, T item)
    {
        // if argument is greater than # of items in queue, throw exception
        if(i > count - 1)
        {
            throw new IndexOutOfBoundsException();
        }

        else
        {
            // if index + i wraps around to front of array,
            // subtract distance from front to end of array from i
            if(front + i > queue.length - 1)
            {
                queue[i - (queue.length - front)] = item;
            }

            // else, just set at front + i
            else
            {
                queue[front + i] = item;
            }

        }

    }
	
    @SuppressWarnings("unchecked")
    private void resize()
    {
        // Create an array 2x the size of current array
        T[] biggerQueue = (T[]) new Object[queue.length * 2];

        // Copy old values to new array
        for(int i = 0 ; i < queue.length ; i++)
        {
            if(front < back || (front + i) <= (queue.length - 1))
            {
                biggerQueue[i] = queue[front + i]; 
            }

            else
            {
                biggerQueue[i] = queue[i - (queue.length - front)];
            }
              
        }

        queue = biggerQueue;
        front = 0;
        back = count - 1;
    }

    public void shuffle()
    {
        Random rng = new Random();

        // for every item in the queue, randomly select and swap two items
        for(int i = 0 ; i < count  ; i++)
        {
            int randIndex1 = rng.nextInt(count);
            int randIndex2 = rng.nextInt(count);

            T temp = get(randIndex1);

            // swap indexes
            set(randIndex1, get(randIndex2));
            set(randIndex2, temp);
            
        }
    }

    public boolean equals(RandIndexQueue<T> rhs)
    {
        boolean equal = true;

        if(this.count != rhs.count)
        {
            equal = false;
        }

        else
        {
            for(int i = 0 ; i < count ; i++)
            {

                if(!this.get(i).equals(rhs.get(i)))
                {
                    equal = false;
                }
            }
        }

        return equal;
    }


    public String toString()
    {
        String retString = "Contents: ";
 
        for (int i = 0 ; i < count ; i++)
        {
            T item = get(i);
            retString += item + " ";
        }

        return retString;    
    }
   
}
