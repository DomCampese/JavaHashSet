
public class MyHashSet implements HS_Interface  {
     
    private int numBuckets; 
    private Node[] buckets;
    private int size;
    private int threshold; // resize when size exceeds this value
 
    public MyHashSet(int numBuckets) {
        this.numBuckets = numBuckets;
        threshold = (int) Math.round(numBuckets * 0.70);
        buckets = new Node[numBuckets];
        size = 0;
    }
     
    public boolean add( String key ) {
        // Duplicates rejected and return false
        if (this.contains(key)) {
            return false;
        }
 
        if ((size() + 1) > threshold) {
            upsize();
        }
 
        int hash = hashOf(key);
        Node head = buckets[hash];
        if (head == null) {
            buckets[hash] = new Node(key);
            size++;
            return true;
        }
 
        Node.insertAtTail(head, key);
        size++;
        return true;
    } 
     
    public boolean remove( String key ) {
        // if not found return false
        if (!this.contains(key)) {
            return false;
        }
 
        // else remove & return true
        int hash = hashOf(key);
        Node.remove(buckets[hash], key);
        size--;
        return true;
    }
    
    public boolean contains( String key ) {
        if (size() == 0) {
            return false; 
        }
 
        Node head = buckets[hashOf(key)];
        return (Node.contains(head, key)); 
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return size;
    }
    
    public void clear() {
        // Create a new backing array
        buckets = new Node[numBuckets];
    }
 
    // Iterate over each element and rehash into an upsized backing array
    private void upsize() {
        numBuckets *= 2;
        threshold = (int) Math.round(numBuckets * 0.70);
        Node[] upsize = new Node[numBuckets];
        for (int i = 0; i < buckets.length; i++) {
            Node curr = buckets[i];
            while (curr != null) {
                // rehash curr.data and place in upsize
                int rehash = hashOf(curr.data);
                // insert at front
                upsize[rehash] = new Node(curr.data, upsize[rehash]);
                curr = curr.next;
            }
        }
        buckets = upsize;
    }
 
    private int hashOf(String str) {
        // hash function from project 7
        int result = 0;
        for (int i = 0; i < str.length(); i++) 
        {
            result ^= str.charAt(i);
            result *= 17;
        }
        result %= numBuckets; // scales result to (0, numBuckets - 1)
        return Math.abs(result);
    }
} //END MYHASHSET CLASS
 
 
// CODE BELOW USED FOR LINKED LIST
// Methods are insert at tail, remove, search, contains
class Node {
    String data;
    Node next;
 
    Node() {
        this(null, null);
    }
 
    Node(String data) {
        this(data, null);
    }
 
    Node(String data, Node next) {
        this.data = data;
        this.next = next;
    }
 
    static void insertAtTail(Node head, String data) {
        if (head == null) {
            head = new Node(data);
            return;
        }
        Node curr = head;
        // get reference to last node
        while (curr.next != null)
            curr = curr.next;
 
        curr.next = new Node(data);
    }

    static void insertAtFront(Node head, String data) {
        head = new Node(data, head); 
    }
 
   
    static boolean remove(Node head, String key) {
        // Base cases
        if (head == null)
            return false;
        if (head.data.equals(key)) {
            // remove head elem
            if (head.next == null) {
                head = null;
                return true;
            } 
            head = head.next;
            return true;
        }
 
        Node prev = null;
        Node curr = head;
        while (curr != null && !(curr.data.equals(key))) {
            prev = curr;
            curr = curr.next;
        }
        if (curr == null)
            return false;
 
        // link previous and next skipping deadnode
        prev.next = curr.next; 
        return true;
    }
 
    static boolean contains(Node head, String key) {
        return (search(head, key) != null);
    }
 
    static Node search(Node head, String key) {
        if (head == null) 
            return null;
        if (head.next == null)
            return head.data.equals(key) ? head : null; 
         
        Node curr = head;
        while (curr != null) {
            if (curr.data.equals(key))
                return curr;
            curr = curr.next;
        }
        return null;
    }
 
    public String toString() {
        return "" + data;
    } 
 
}