package com.yif.store.test;

import java.util.ArrayList;

public class Trie {
	public void rotate(int[] nums, int k) {
        int[] a = new int[nums.length];
        for (int i = 0; i < a.length; i++) {
			a[(i + k) % nums.length] = nums[i];
		}
        for (int i = 0; i < nums.length; i++) {
			nums[i] = a[i];
		}
      
    }
	public static void main(String[] args) {
		Trie t = new Trie();
		t.rotate(new int[]{1,2,3,4,5,6,7}, 3);
	}
}
