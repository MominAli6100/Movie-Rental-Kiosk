// Name:Ali Momin
// NetID:ASM180014
//package com.company;

public class BSTree<T extends Comparable<T>> {
    // creation of local variables to be used in the class
    private Node root;

    // default constructor that sets the variables to default values
    public BSTree()
    {
        root = null;
    }

    // overloaded constructor that takes the value the user entered and sets the local varaible to that value
    public BSTree(Node eRoot)
    {
        root = eRoot;
    }

    // setters and getters to access the local varaible outside of the class
    public void setRoot(Node eRoot)
    {
        root = eRoot;
    }
    public Node getRoot()
    {
        return root;
    }

    // Get parent function that takes a child and finds the parent for it
    public Node<T> BSTGetParent(Node<T> childItem)
    {
        // makes a call to the recursive getParentHelper function that takes two paremeters and returns the parent
        return BSTGetParentHelper(root, childItem);
    }

    // recursive getParent function that actually finds the parent, takes two parameters, a node and a child
    public Node<T> BSTGetParentHelper(Node<T> BSTtraversor, Node<T> childItem)
    {
        // checks to see if the node is empty
        if(BSTtraversor == null)
        {
            // if it is then return null
            return null;
        }
        // if not then check to see if either the left or right node to it is the child you have passed
        if(BSTtraversor.getLeft() == childItem || BSTtraversor.getRight() == childItem)
        {
            // if so then return the BSTtraversor node
            return BSTtraversor;
        }
        // set num to the value gotten from comparing the child and the BSTtraversor variable
        int num = ((DVD)childItem.getPayload()).getTitle().compareTo(((DVD)BSTtraversor.getPayload()).getTitle());

        // if num is less than 0
        if(num < 0)
        {
            // then call the getParentHelperFunction(itself) and pass it the node to the left of BSTTraversor and the child
            return BSTGetParentHelper(BSTtraversor.getLeft(), childItem);
        }
        // otherwise call the getParentHelperFunction(itself) and pass it the node to the right of BSTTraversor and the child
        return BSTGetParentHelper(BSTtraversor.getRight(), childItem);
    }

    // insert function that takes one parameter, the value you want to insert and inserts it into the BST
    public void BSTInsert(Node<T> itemToBeInserted)
    {
        // check to see if the root is null
        if(root == null)
        {
            // if so then set it equal to the value that is being inserted
            root = itemToBeInserted;
        }
        else
        {
            // otherwise call the recusrive helper function that will insert the values into the BST
            BSTInsertHelper(root, itemToBeInserted);
        }
    }

    // recursive insert function that takes two parameters, the parent and the node that you are trying to insert and
    // inserts it into the BST
    public void BSTInsertHelper(Node<T> parent, Node<T> itemToBeInserted)
    {
        // compare the node you are trying to insert with the parent node and store the numeric result inside of num
        int num = ((DVD)itemToBeInserted.getPayload()).getTitle().compareTo(((DVD)parent.getPayload()).getTitle());
        // if num is less than 0
        if(num < 0)
        {
            // check to see if the node to the left of the parent is null
            if(parent.getLeft() == null)
            {
                // if so then set the node you are trying to insert to the node that is to the left of the parent
                parent.setLeft(itemToBeInserted);
            }
            else
            {
                // otherwise call the same function, BSTInsertHelper and pass it the node to the left of the parent and
                // the node you are trying to insert
                BSTInsertHelper(parent.getLeft(), itemToBeInserted);
            }
        }
        // if the node is not less than 0
        else
        {
            // check to see if the node to the right of the parent is null
            if(parent.getRight() == null)
            {
                // if so then set the node to the right of the parent to the node you are trying to insert
                parent.setRight(itemToBeInserted);
            }
            else
            {
                // if the right side is not null then call itself, BSTInsertHelper and pass it the node to the right of the
                // parent and the node you are trying to insert
                BSTInsertHelper(parent.getRight(), itemToBeInserted);
            }
        }
    }

    // the BSTSearch function searches the BST and returns the node you are looking for, takes one paremeter which is the
    // node you are looking for
    public Node<T> BSTSearch(Node<T> itemToBeSearchedFor)
    {
        // calls the recursive helper functions for search and passes it the root and the item that you are searching for
        return BSTSearchHelper(root, itemToBeSearchedFor);
    }

    // recursive serach function that recurisvely searches through the BST and takes two paremeter, the current node and
    // node that you are looking for and returns the node that you are looking for
    public Node<T> BSTSearchHelper(Node<T> currentNode, Node<T> itemToBeSearchedFor)
    {
        // if the current node is not null
        if(currentNode != null)
        {
            // compare the node that has the item you are looking for and the currentNode and store the numeric value of
            // the comparison inside of num
            int num = ((DVD)itemToBeSearchedFor.getPayload()).getTitle().compareTo(((DVD)currentNode.getPayload()).getTitle());
            // if num equals 0
            if(num == 0)
            {
                // return the current node
                return currentNode;
            }
            // if it's less than 0
            else if(num < 0)
            {
                // return call itself, BSTSearchHelper, and pass it the node to the left of the current node and the item
                // that you are searching for
                return BSTSearchHelper(currentNode.getLeft(), itemToBeSearchedFor);
            }
            // otherwise
            else
            {
                // return call itself, BSTSearchHelper, and pass it the node to the right of the current node and the item
                // that you are searching for
                return BSTSearchHelper(currentNode.getRight(), itemToBeSearchedFor);
            }
        }
        // if the node is null then return null
        return null;
    }

    // remove function that takes a paremeter which is the value that you are trying to remove and removes it
    public void BSTRemove(Node<T> itemToBeRemoved)
    {
        // call the BSTSearch function and pass it the value that is being removed ands store the result inside of a node
        Node<T> removalNode = BSTSearch(itemToBeRemoved);
        // call the BSTGetParent function and pass it to the value you are trying to remove and store the result in a node
        Node<T> parentOfRemovalNode = BSTGetParent(itemToBeRemoved);
        // call the recursive deleter function and pass it the root, and the two nodes above
        BSTRemoveHelper(root, parentOfRemovalNode, removalNode);
    }

    // BSTRemoveHelper which is a recursive function that takes three paremeters, the parent, the root and the node that is
    // going to be removed and removes the node
    public boolean BSTRemoveHelper(Node<T> BSTTraversor, Node<T> parent, Node<T> itemToBeRemoved)
    {
        // creation of variables that will be used in the function
        Node<T> succNode = null;
        Node<T> successorParent = null;
        // check to see if the item that is going to be removed is null
        if(itemToBeRemoved == null)
        {
            return false;
        }
        // if not then check to see if it is a internal node with a left and right child
        if(itemToBeRemoved.getLeft() != null && itemToBeRemoved.getRight() != null)
        {
            // if so then set a node to the node that is to the right of the item to be removed
            succNode = itemToBeRemoved.getRight();
            // and set the itemTOBeREmoved to the successorParent
            successorParent = itemToBeRemoved;
            // check to see if succNode has any nodes to the left of it
            while(succNode.getLeft() != null)
            {
                // if so then keep moving in that direction
                successorParent = succNode;
                succNode = succNode.getLeft();
            }
            // copy all the values from the succNode into the itemToBeRemoved
            ((DVD)itemToBeRemoved.getPayload()).setTitle(((DVD)succNode.getPayload()).getTitle());
            ((DVD)itemToBeRemoved.getPayload()).setAvailable(((DVD)succNode.getPayload()).getAvailable());
            ((DVD)itemToBeRemoved.getPayload()).setRented(((DVD)succNode.getPayload()).getRented());

            // call itself, BSTRemovalHelper and pass it the root, the successorParent and the succNode
            BSTRemoveHelper(root, successorParent, succNode);
        }
        // if the itemToBeRemoved is the root
        else if(itemToBeRemoved == BSTTraversor)
        {
            // then check to see if there are any node to the left of it
            if(itemToBeRemoved.getLeft() != null)
            {
                // if so then set the root to that node
                root = itemToBeRemoved.getLeft();
            }
            else
            {
                // if not then set the root to the right node
                root = itemToBeRemoved.getRight();
            }
        }
        // if the node to be removed has a left child
        else if(itemToBeRemoved.getLeft() != null)
        {
            // check to see if the parent's left node is the itemToBeRemoved
            if(parent.getLeft() == itemToBeRemoved)
            {
                // if so then set the parent's left node to the itemToBeRemoved's left node
                parent.setLeft(itemToBeRemoved.getLeft());
            }
            else
            {
                // otherwise set the parent's right node to the itemToBeRemoved's Left node
                parent.setRight(itemToBeRemoved.getLeft());
            }
        }
        // if the itemToBeRemoved has a right child
        else
        {
            // check to see if the parent's left node is the same as the node to be deleted
            if(parent.getLeft() == itemToBeRemoved)
            {
                // if so then set the parent's left node to the itemToBeRemoved's right node
                parent.setLeft(itemToBeRemoved.getRight());
            }
            else
            {
                // otherwise set the parent's right node to the itemToBeRemoved's right node
                parent.setRight(itemToBeRemoved.getRight());
            }
        }
        // return true
        return true;
    }

}
