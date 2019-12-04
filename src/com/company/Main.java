package com.company;

import com.company.MyList.ListNode;
import com.company.MyList.ListRand;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    public static ListNode addNode(ListNode prev) {
        ListNode result = new ListNode();
        result.Prev = prev;
        result.Next = null;
        Integer i = (int) (Math.random() * 100);
        result.Data = i.toString();
        prev.Next = result;
        return result;
    }

    public static ListNode randomNode(ListNode _head, int _length) {
        int k = (int) (Math.random() * _length);
        int i = 0;
        ListNode result = _head;
        while (i < k) {
            result = result.Next;
            i++;
        }
        return result;
    }

    public static void main(String[] args) {

        int length = 7;

        //первый node
        ListNode head = new ListNode();
        ListNode tail;
        ListNode temp;
        Integer integer = (int) (Math.random() * 1000);
        head.Data = integer.toString();

        tail = head;

        for (int i = 1; i < length; i++)
            tail = addNode(tail);

        temp = head;

        //добавляем ссылку на random node
        for (int i = 0; i < length; i++) {
            temp.Rand = randomNode(head, length);
            temp = temp.Next;
        }

        //объявляем первый лист
        ListRand first = new ListRand();
        first.Head = head;
        first.Tail = tail;
        first.Count = length;

        //сериализуем
        try {
            FileOutputStream fs = new FileOutputStream("Save.ser");
            first.serialize(fs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        //десереализуем во второй лист
        ListRand second = new ListRand();
        try {
            FileInputStream fs = new FileInputStream("Save.ser");
            second.deserialize(fs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
