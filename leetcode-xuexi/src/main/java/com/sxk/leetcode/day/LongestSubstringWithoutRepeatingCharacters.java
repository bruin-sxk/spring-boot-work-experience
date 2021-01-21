package com.sxk.leetcode.day;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 无重复字符的最长子串
 */
public class LongestSubstringWithoutRepeatingCharacters {


	public static void main(String[] args) {
		String str = "abcabcbb";
		Solution solution = new Solution();
		int count = solution.lengthOfLongestSubstring(str);
		System.out.println(count);
	}

}


class Solution {
	public int lengthOfLongestSubstring(String s) {
		int temp = 0;
		List<Character> set = new ArrayList<>();
		char[] chars = s.toCharArray();
		for (char c : chars) {
			if (!set.contains(c)) {
				set.add(c);
			} else {
				temp = temp > set.size() ? temp : set.size();
				set = new ArrayList<>(set.subList(set.indexOf(c) + 1, set.size()));
				set.add(c);
			}
		}
		return temp > set.size() ? temp : set.size();
	}
}
