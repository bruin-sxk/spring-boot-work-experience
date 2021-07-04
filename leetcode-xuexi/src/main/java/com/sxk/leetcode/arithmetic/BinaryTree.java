package com.sxk.leetcode.arithmetic;

import java.util.Stack;

public class BinaryTree {

    public static void main(String[] args) {
        final Node node = generateNodeByLength();
        pre(node);
        pos(node);
        pos1(node);
        in(node);
    }

    /**
     * 中序遍历
     * @param cur 头节点
     */
    private static void in(Node cur) {
        if (cur == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        while (!stack.isEmpty() || cur != null) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                System.out.print(cur.val);
                cur = cur.right;
            }

        }
        System.out.println();
    }


    private static void pos1(Node head) {
        if (head == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node c = null;
        while (!stack.isEmpty()) {
            c = stack.peek();
            if (c.left != null && head != c.left && head != c.right) {
                stack.push(c.left);
            } else if (c.right != null && head != c.right) {
                stack.push(c.right);
            } else {
                System.out.print(stack.pop().val);
                head = c;
            }
        }
        System.out.println();
    }

    /**
     * 后序遍历  两个栈空间
     *
     * @param head
     */
    private static void pos(Node head) {
        if (head == null) {
            return;
        }
        final Stack<Node> stack1 = new Stack<>();
        final Stack<Node> stack2 = new Stack<>();
        stack1.push(head);
        while (!stack1.isEmpty()) {
            final Node pop = stack1.pop();
            stack2.push(pop);
            if (pop.left != null) {
                stack1.push(pop.left);
            }
            if (pop.right != null) {
                stack1.push(pop.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.print(stack2.pop().val);
        }
        System.out.println();
    }

    /**
     * 非递归 先序遍历
     *
     * @param head 头节点
     */
    public static void pre(Node head) {
        if (head == null) {
            return;
        }
        final Stack<Node> stack = new Stack<>();
        stack.push(head);
        while (!stack.isEmpty()) {
            Node pop = stack.pop();
            System.out.print(pop.val);
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
        System.out.println();
    }


    private static Node generateNodeByLength() {
        Node n7 = new Node(7, null, null);
        Node n6 = new Node(6, null, null);
        Node n5 = new Node(5, null, null);
        Node n4 = new Node(4, n7, null);
        Node n3 = new Node(3, null, null);
        Node n2 = new Node(2, n5, n6);
        Node n1 = new Node(1, n3, n4);
        Node head = new Node(0, n1, n2);
        return head;
    }


    private static class Node {

        private int val;
        private Node left;
        private Node right;


        public Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}
