package com.sxk.bruin.springbootdatasource;

import org.apache.commons.lang3.RandomUtils;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 最近最少使用
 * 
 * @author suxingkang
 */
public class LRUCacheTest {

	public static void main(String[] args) {
		LRUCache cache = new LRUCache(16);

		for (int i = 0; i < 16; i++) {
			cache.set(String.valueOf(i), "suxingkang" + i);
		}

		for (int i = 0; i < 500; i++) {
			cache.get(String.valueOf(RandomUtils.nextInt(0, 16)));
		}

		cache.set(String.valueOf(16), "zuihoutianjiayigewudidezhongzi" + 16);

		for (int i = 0; i < 500; i++) {
			cache.get(String.valueOf(RandomUtils.nextInt(0, 17)));
		}
		cache.set(String.valueOf(9000), "taiyuanxueyuandexiaoxuesheng" + 9000);
		System.out.println();
	}

	private static class LRUCache {

		private CacheNode[] capacity;
		private int capacitySize;
		private int currentNode = 0;

		LRUCache(int capacitySize) {
			this.capacity = new CacheNode[capacitySize];
			this.capacitySize = capacitySize;
		}

		String get(String cacheKey) {
			for (CacheNode cacheNode : capacity) {
				if (cacheNode.key.equals(cacheKey)) {
					cacheNode.count.incrementAndGet();
					return cacheNode.cacheValue;
				}
			}
			return null;
		}

		void set(String cacheKey, String value) {
			int length = 0;
			for (CacheNode cacheNode : capacity) {
				if (Objects.nonNull(cacheNode)) {
					length++;
				}
			}
			if (length < capacitySize) {
				capacity[currentNode++] = new CacheNode(cacheKey, value, new AtomicInteger());
			} else {
				boolean flag = false;
				for (int i = 0; i < capacity.length; i++) {
					if (flag)
						break;
					flag = true;
					for (int j = 0; j < capacity.length - i - 1; j++) {
						if (capacity[j].count.get() > capacity[j + 1].count.get()) {
							CacheNode tempNode = capacity[j];
							capacity[j] = capacity[j + 1];
							capacity[j + 1] = tempNode;
							flag = false;
						}
					}
				}
				capacity[0] = new CacheNode(cacheKey, value, new AtomicInteger());
			}
		}
	}

	private static class CacheNode {
		String key;
		String cacheValue;
		AtomicInteger count;

		CacheNode(String key, String cacheValue, AtomicInteger count) {
			this.key = key;
			this.cacheValue = cacheValue;
			this.count = count;
		}
	}
}
