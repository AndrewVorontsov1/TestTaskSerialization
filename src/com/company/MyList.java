package com.company;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyList {
    static class ListNode
    {
        public ListNode Prev;
        public ListNode Next;
        public ListNode Rand; // произвольный элемент внутри списка
        public String Data;
    }
    static class ListRand
    {
        public ListNode Head;
        public ListNode Tail;
        public int Count;

        public void serialize(FileOutputStream fileOutputStream) {
            Map<ListNode, Integer> map = new HashMap<>();
            ListNode node = Head;

            // присваиваем индексы всем ListNode
            int index = 0;
            do {
                map.put(node, index);
                node = node.Next;
                index++;

            } while (node != null);

            // перебираем map, записываем данные и случайный индекс fileOutputStream
            for (Map.Entry<ListNode, Integer> entry : map.entrySet()) {

                ListNode key = entry.getKey();
                ListNode rand = key.Rand;

                String outputString = (rand == null) ?
                        key.Data + " " + "-1" + "\n" :
                        key.Data + " " + map.get(rand) + "\n";

                try {
                    fileOutputStream.write(outputString.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        public ListRand deserialize(FileInputStream fileInputStream)
        {
            ListRand result = new ListRand();
            ListNode head = new ListNode();
            result.Head = head;

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;

            ArrayList<ListNode> nodes = new ArrayList<>();
            ArrayList<Integer> indexes = new ArrayList<>();
            ListNode current = head;

            try {

                // считываем данные и создаем структуру двусвязного списка
                while ((line = reader.readLine()) != null) {
                    String[] dataArray = line.trim().split(" ");
                    String data = dataArray[0];
                    Integer randomIndex = Integer.parseInt(dataArray[1]);

                    ListNode next = new ListNode();
                    current.Next = next;
                    current.Data = data;

                    next.Prev = current;

                    nodes.add(current);
                    current = next;

                    indexes.add(randomIndex);
                }

                // присваиваем null следующему элементу последнего узла
                ListNode lastNode = nodes.get(nodes.size() - 1);
                lastNode.Next = null;

                // перебираем map, записываем данные и случайный индекс в fileOutputStream
                for (int i = 0; i < nodes.size(); i++) {

                    // get by index - O(1) complexity
                    ListNode node = nodes.get(i);
                    Integer randIndex = indexes.get(i);
                    node.Rand = (randIndex == -1) ? null : nodes.get(randIndex);
                }

                result.Count = nodes.size();
                result.Tail = nodes.get(nodes.size() - 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

    }
}
