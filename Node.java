// Avery Lanier, ATL230000

public class Node <AnyType> {
    // Members
    private Node<AnyType> next;
    private Node<AnyType> down;
    private Node<AnyType> prev;
    private Object payload;

    // Constructors
    public Node()
    {
        next = null;
        down = null;
        prev = null;
        payload = null;
    }
    public Node(Node<AnyType> newNext, Node<AnyType> newDown, Node<AnyType> newPrev, Object newPayload)
    {
        next = newNext;
        down = newDown;
        prev = newPrev;
        payload = newPayload;
    }

    // Mutators
    public void setNext(Node<AnyType> newNext)
    {
        next = newNext;
    }
    public void setDown(Node<AnyType> newDown)
    {
        down = newDown;
    }
    public void setPrev(Node<AnyType> newPrev)
    {
        prev = newPrev;
    }
    public void setPayload(Object newPayload)
    {
        payload = newPayload;
    }

    // Accessors
    public Node<AnyType> getNext()
    {
        return next;
    }
    public Node<AnyType> getDown()
    {
        return down;
    }
    public Node<AnyType> getPrev()
    {
        return prev;
    }
    public Object getPayload()
    {
        return payload;
    }

}