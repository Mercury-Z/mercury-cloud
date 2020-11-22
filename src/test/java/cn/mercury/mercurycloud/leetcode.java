package cn.mercury.mercurycloud;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class leetcode {
    public static void main(String[] args) {
        MaxQueue maxQueue = new MaxQueue();
        maxQueue.push_back(1);
        maxQueue.push_back(2);
        System.out.println(maxQueue.max_value());
        maxQueue.pop_front();
        System.out.println(maxQueue.max_value());

    }
    public static class MaxQueue {
        private Queue<Integer> queue;
        private Deque<Integer> deque;

        public MaxQueue() {
            queue = new LinkedList<Integer>();
            deque = new LinkedList<Integer>();
        }

        public int max_value() {
            //得到最大值
            if(queue.peek()!=null)
                return deque.peek();
            return -1;
        }

        public void push_back(int value) {
            queue.add(value);
       /* if(deque.peek()==null)
            deque.add(value);*/
            if(deque.peekLast()!=null&&value>deque.peekLast()){
                while(deque.peekLast()!=null&&value>deque.peekLast()){
                    deque.removeLast();
                }
            }
            deque.addLast(value);
        }

        public int pop_front() {
            if(queue.peek()!=null){
                if(queue.peek()==deque.peek())
                    deque.pollFirst();
                return queue.poll();
            }
            return -1;
        }
    }
}
