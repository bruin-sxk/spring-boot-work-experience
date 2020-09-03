package com.sxk.leetcode.day;

import java.util.Random;
import java.util.StringJoiner;

public class SkipList {

	private final static double PROMOTE_RATE = 0.5D;
	private Node head, tail;
	private int maxLevel;

	public SkipList() {
		head = new Node(Integer.MIN_VALUE);
		tail = new Node(Integer.MAX_VALUE);
		head.right = tail;
		tail.left = head;
	}

	public int get(int data) {
		Node search = search(data);
		return search == null ? 0 : search.data;
	}

	private Node search(int data) {
		Node node = find(data);
		if (node.data != data) {
			return null;
		}
		return node;
	}

	public void insert(int data) {
		Node preNode = find(data);
		if (preNode.data == data) {
			return;
		}
		Node addNode = new Node(data);
		appendNode(preNode, addNode);
		int currentLevel = 0;
		Random random = new Random();
		while (random.nextDouble() > PROMOTE_RATE) {
			if (currentLevel == maxLevel) {
				// 增加一层
				addLevel();
			}
			while (preNode.up == null) {
				preNode = preNode.left;
			}
			preNode = preNode.up;
			Node promotionNode = new Node(data);
			appendNode(preNode, promotionNode);
			addNode.up = promotionNode;
			promotionNode.down = addNode;
			// 防止多次晋升时出现问题
			addNode = promotionNode;
			currentLevel++;
		}

	}

	public boolean delete(int data) {
		Node removeNode = find(data);
		if (removeNode.data != data) {
			return false;
		}
		int currentLevel = 0;
		while (removeNode != null) {
			removeNode(removeNode);
			if (currentLevel != 0 && removeNode.right.data == Integer.MAX_VALUE
					&& removeNode.left.data == Integer.MIN_VALUE) {
				removeLevel(removeNode.right);
			}else {
				currentLevel++;
			}
			removeNode = removeNode.up;
		}
		return true;
	}

	public String printString(){
		Node node = head;
		while(node.down != null){
			node = node.down;
		}
		StringJoiner sj = new StringJoiner(",");
		while(node.right.data != Integer.MAX_VALUE){
			sj.add(String.valueOf(node.right.data));
			node = node.right;
		}
		return sj.toString();
	}


	private void removeNode(Node removeNode) {
		removeNode.right.left = removeNode.left;
		removeNode.left.right = removeNode.right;
	}

	private void removeLevel(Node node) {
		Node tmpNode = node.left;
		if (tmpNode.up == null) {
			head = tmpNode.down;
			tail = tmpNode.right.down;
			head.up = null;
			tmpNode.down = null;
			tail.up = null;
			tmpNode.right.down = null;
		} else {
			tmpNode.up.down = tmpNode.down;
			tmpNode.down.up = tmpNode.up;
			tmpNode.right.up.down = tmpNode.right.down;
			tmpNode.right.down.up = tmpNode.right.up;
		}
		maxLevel--;
	}


	private void addLevel() {
		maxLevel++;
		Node p1 = new Node(Integer.MIN_VALUE);
		Node p2 = new Node(Integer.MAX_VALUE);
		p1.right = p2;
		p2.left = p1;
		p1.down = head;
		head.up = p1;
		p2.down = tail;
		tail.up = p2;
		head = p1;
		tail = p2;
	}

	private void appendNode(Node pre, Node add) {
		pre.right.left = add;
		add.left = pre;
		add.right = pre.right;
		pre.right = add;
	}


	private Node find(int data) {
		Node node = head;
		while (true) {
			while (node.right.data != Integer.MAX_VALUE && node.right.data <= data) {
				node = node.right;
			}
			if (node.down == null) {
				break;
			}
			node = node.down;
		}
		return node;
	}


	public class Node {
		private int data;

		private Node up, down, right, left;

		public Node(int data) {
			this.data = data;
		}
	}

	public static void main(String[] args) {
		SkipList list = new SkipList();
		list.insert(50);
		list.insert(15);
		list.insert(13);
		list.insert(20);
		list.insert(100);
		list.insert(75);
		list.insert(99);
		list.insert(76);
		list.insert(83);
		list.insert(65);
		System.out.println(list.printString());
		list.get(50);
		list.delete(50);
		list.get(50);
		System.out.println(list.printString());
	}

}
