package src;

// ********************************************************
// Array-based implementation of the ADT list.
// *********************************************************
public class ListArrayBased implements ListInterface
{
    private static final int MAX_LIST = 26;
    private Object[] items;  // an array of list items
    private int numItems;  // number of items in list

    public ListArrayBased()
    {
        items = new Object[MAX_LIST];
        numItems = 0;
    }

    public boolean isEmpty()
    {
        return (numItems == 0);
    }

    public int size()
    {
        return numItems;
    }

    public void removeAll()
    {
        // Creates a new array; marks old array for
        // garbage collection.
        items = new Object[MAX_LIST];
        numItems = 0;
    }

    public void add(int index, Object item) throws ListIndexOutOfBoundsException
    {
        index = translate(index);

        if (numItems > MAX_LIST)
        {
            throw new ListException("ListException on add");
        }
        if (index >= 0 && index <= numItems)
        {
            // make room for new element by shifting all items at
            // positions >= index toward the end of the
            // list (no shift if index == numItems+1)
            for (int pos = translate(numItems); pos >= index+1; pos--)
            {
                items[pos+1] = items[pos];
            }
            // insert new item
            items[index] = item;
            numItems++;
        }
        else
        {
            throw new ListIndexOutOfBoundsException(
                    "ListIndexOutOfBoundsException on add");
        }
    }

    public Object get(int index) throws ListIndexOutOfBoundsException
    {
        if (index >= 1 && index <= numItems)
        {
            return items[translate(index)];
        }
        else
        {  // index out of range
            throw new ListIndexOutOfBoundsException(
                    "ListIndexOutOfBoundsException on get");
        }
    }

    public void remove(int index) throws ListIndexOutOfBoundsException
    {
        if (index >= 1 && index <= numItems) {
            // delete item by shifting all items at
            // positions > index toward the beginning of the list
            // (no shift if index == size)
            for (int pos = index; pos < size(); pos++)
            {
                items[pos-1] = items[pos];
            }
            numItems--;
        }
        else
        {
            throw new ListIndexOutOfBoundsException(
                    "ListIndexOutOfBoundsException on remove");
        }
    }

    /**
     * Sort method based on bubble sort.
     *<br/>
     * This method requires that the objects in the list implement the {@link Comparable} interface.
     */
    @SuppressWarnings("unchecked")
    public void sort() {
        int lastUnsortedIndex = numItems, i;
        Object temp;

        while (lastUnsortedIndex > 0) {
            for (i = 0; i < lastUnsortedIndex - 1; i++) {
                if (((Comparable<Object>)items[i]).compareTo(items[i + 1]) > 0) {
                    temp = items[i];
                    items[i] = items[i + 1];
                    items[i + 1] = temp;
                }
            }
            lastUnsortedIndex--;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int i;

        for (i = 0; i < numItems - 1; i++) {
            builder.append(items[i].toString());
            builder.append(", ");
        }
        builder.append(items[i].toString());
        return builder.toString();
    }

    private int translate(int position)
    {
        return position - 1;
    }
}
