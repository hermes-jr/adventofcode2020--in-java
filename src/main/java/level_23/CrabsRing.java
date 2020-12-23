package level_23;

import lombok.EqualsAndHashCode;

class CrabsRing {
    Node[] lookup = new Node[1_000_001];
    Node head = null;

    void add(Node n) {
        lookup[n.value] = n;
        if (head == null) {
            head = n;
        } else {
            Node hn = head.next;
            head.next = n;
            n.prev = head;
            hn.prev = n;
            n.next = hn;
        }
        head = n;
    }

    Node pick(Node n) {
        n.prev.next = n.next;
        n.next.prev = n.prev;
        n.next = null;
        n.prev = null;
        return n;
    }

    public void add(Node value, Integer destination) {
        head = lookup[destination];
        add(value);
    }

    @EqualsAndHashCode
    static class Node {
        Node prev;
        Node next;
        Integer value;

        public Node(Integer val) {
            this.value = val;
            next = this;
            prev = this;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = head;
        if (current == null) {
            return "- Empty -";
        }
        do {
            if (current == head) {
                sb.append("(").append(current).append(")");
            } else {
                sb.append(" ").append(current).append(" ");
            }
            current = current.next;
        } while (current != head);
        return sb.toString();
    }
}
