/*
 * https://www.codesdope.com/course/data-structures-avl-trees/
 * */
class DataStructure {
    public Node root;
    private String basedON;
    private int minHp = 0;
    public DataStructure(String basedON) {
        setBasedON(basedON);
        this.root = null;
    }

    private double getPatientBasedON(Patient patient) {
        switch (basedON.toLowerCase()) {
            case "order":
                return patient.getOrder();
            case "health_point":
                return hashHp(patient.getHealthPoint(), patient.getPatientID());
            case "patient_id":
                return patient.getPatientID();
        }
        return -1;
    }

    private double hashHp(int hp, int id){
        int sign = hp < 0? -1 : 1;
        String hash = "0." + id;
        double doubleHp = hp * Math.pow(10, -6) * hp * 9 ;
        float newID = Float.parseFloat(hash);
        return sign * doubleHp * Math.pow(10, -3) / newID*newID ;
    }

    public void setBasedON(String basedON) {
        this.basedON = basedON;
    }


    private int height(Node node) {
        if (node == null)
            return -1;
        return node.height;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x; //new root of rotation
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    private int getBalanceFactor(Node node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    /**
     * Will call insertNode() and pass a copy of root
     */
    public void insert(Patient p) {
        root = insertNode(root, p);
    }

    private Node insertNode(Node node, Patient item) {
        if (node == null)
            return (new Node(item));
        if (getPatientBasedON(item) <= getPatientBasedON(node.data))
            node.left = insertNode(node.left, item);
        else if (getPatientBasedON(item) > getPatientBasedON(node.data))
            node.right = insertNode(node.right, item);
         else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balanceFactor = getBalanceFactor(node);
        if (balanceFactor > 1) {
            if (getPatientBasedON(item) <= getPatientBasedON(node.left.data)) {
                return rightRotate(node);
            } else if (getPatientBasedON(item) > getPatientBasedON(node.left.data)) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if (balanceFactor < -1) {
            if (getPatientBasedON(item) > getPatientBasedON(node.right.data)) {
                return leftRotate(node);
            } else if (getPatientBasedON(item) < getPatientBasedON(node.right.data)) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }


    /**
     * Will call searchByOrder() and pass a copy of root
     * @return Node
     */
    public Node search(Patient patient) {
        return searchByOrder(patient, root);
    }

    private Node searchByOrder(Patient patient, Node node) {
        while (node != null)
            if (getPatientBasedON(patient) < getPatientBasedON(node.data))
                node = node.left;
            else if (getPatientBasedON(patient) > getPatientBasedON(node.data))
                node = node.right;
            else
                return node;
        return null;
    }

    /**
     * @return Node
     */
    public Node minimumNode(Node node) {
        if (node == null)
            return null;
        Node current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    /**
     * Will call deleteNode() and pass a copy of root
     */
    public void delete(Patient patient) {
        this.root = deleteNode(this.root, patient);
    }

    private Node deleteNode(Node root, Patient item) {
        if (root == null){
            return root;
        }
        if (getPatientBasedON(item) < getPatientBasedON(root.data))
            root.left = deleteNode(root.left, item);
        else if (getPatientBasedON(item) > getPatientBasedON(root.data))
            root.right = deleteNode(root.right, item);

        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;
                if (temp == null) {
                    temp = root;
                    root = null;
                } else
                    root = temp;
            } else {
                Node temp = minimumNode(root.right);
                root.data = temp.data;
                root.right = deleteNode(root.right, temp.data);
            }
        }

        if (root == null)
            return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        int balanceFactor = getBalanceFactor(root);
        if (balanceFactor > 1) {
            if (getBalanceFactor(root.left) >= 0) {
                return rightRotate(root);
            } else {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(root.right) <= 0) {
                return leftRotate(root);
            } else {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }
        return root;
    }
}

class Node {
    Node left, right;
    Patient data;
    int height;

    public Node(Patient patient) {
        this.data = patient;
        this.left = null;
        this.right = null;
        this.height = 0;
    }
}
