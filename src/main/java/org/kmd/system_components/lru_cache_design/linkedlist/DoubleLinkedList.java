package org.kmd.system_components.lru_cache_design.linkedlist;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Data
public class DoubleLinkedList<K, V> {

    private Node<K, V> HEAD;
    private Node<K, V> TAIL;

    private Map<K, Node<K, V>> nodeLocatorMap = new HashMap<>();

    public DoubleLinkedList() {
    }

    public void addOnFront(K key, V value) {
        Node<K, V> newNode= new Node<>(key, value);

        if(HEAD==null){
            HEAD=newNode;
            TAIL=newNode;
        }else{
            newNode.next=HEAD;
            HEAD.prev=newNode;
            HEAD=newNode;
        }

        nodeLocatorMap.put(key, newNode);

    }

    private void deleteNode(Node<K, V> node){         //add conditions
        node.prev.next=node.next;
        node.next.prev=node.prev;
        nodeLocatorMap.remove(node.key);
    }


    public void moveToHead(Node<K, V> node){
        deleteNode(node);
        addOnFront(node.key, node.value);
    }

    public void remoteTail(){

    }

    /* utility methods */

    public void iterateFromHead(){
        Node<K, V> current=HEAD;

        while(current != null){
            System.out.print(current.getValue()+"->");
            current=current.next;
        }
    }

    public void printNextPrev(K key){
        Node<K, V> vNode = nodeLocatorMap.get(key);
        System.out.println(vNode.value);
        System.out.println("next->"+vNode.next.value);
        System.out.println("prev->"+vNode.prev.value);

    }


    public static void main(String[] str){
        DoubleLinkedList<String, Integer> list= new DoubleLinkedList<>();

        list.addOnFront("a", 1);
        list.addOnFront("b", 2);
        list.addOnFront("c", 3);
        list.addOnFront("d", 4);
        list.addOnFront("e", 5);
        list.addOnFront("f", 6);
        list.iterateFromHead();

//        System.out.println();
//        list.moveOnHead(list.getHead().getNext().getNext());
//        list.iterateFromHead();

        System.out.println("NN");
//        list.printNextPrev(4);


    }
}





