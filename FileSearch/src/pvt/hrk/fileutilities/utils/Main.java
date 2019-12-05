package pvt.hrk.fileutilities.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	public static void main(String[] args) {
		String left = "manpreet";
		String right = "ken";
		List<Token> leftList = new ArrayList<>(left.length());
		List<Token> rightList = new ArrayList<>(right.length());
		int left_len = left.length();
		int right_len = right.length();
		int smallerLen = left_len > right_len ? right_len : left_len;

		Queue<Character> common = new LinkedList<>();
		Queue<Character> ldiff = new LinkedList<>();
		Queue<Character> rdiff = new LinkedList<>();

		int i = 0;

		while (i < smallerLen) {
			char lc = left.charAt(i);
			char rc = right.charAt(i);

			if (lc == rc) {
				if (!ldiff.isEmpty()) {
					char[] diff_arr = new char[ldiff.size()];
					int index = 0;
					while (!ldiff.isEmpty()) {
						diff_arr[index++] = ldiff.poll();
					}
					leftList.add(new Token(new String(diff_arr), "diff"));
				}
				if (!rdiff.isEmpty()) {
					char[] diff_arr = new char[rdiff.size()];
					int index = 0;
					while (!rdiff.isEmpty()) {
						diff_arr[index++] = rdiff.poll();
					}
					rightList.add(new Token(new String(diff_arr), "diff"));
				}
				common.add(lc);
			} else {
				if (!common.isEmpty()) {
					char[] diff_arr = new char[common.size()];
					int index = 0;
					while (!common.isEmpty()) {
						diff_arr[index++] = common.poll();
					}
					leftList.add(new Token(new String(diff_arr), "same"));
					rightList.add(new Token(new String(diff_arr), "same"));
				}

				ldiff.add(lc);
				rdiff.add(rc);
			}
			i++;
		}
		if (!common.isEmpty()) {
			char[] diff_arr = new char[common.size()];
			int index = 0;
			while (!common.isEmpty()) {
				diff_arr[index++] = common.poll();
			}
			leftList.add(new Token(new String(diff_arr), "same"));
			rightList.add(new Token(new String(diff_arr), "same"));
		}
		if (!ldiff.isEmpty()) {
			char[] diff_arr = new char[ldiff.size()];
			int index = 0;
			while (!ldiff.isEmpty()) {
				diff_arr[index++] = ldiff.poll();
			}
			leftList.add(new Token(new String(diff_arr), "diff"));
		}
		if (!rdiff.isEmpty()) {
			char[] diff_arr = new char[rdiff.size()];
			int index = 0;
			while (!rdiff.isEmpty()) {
				diff_arr[index++] = rdiff.poll();
			}
			rightList.add(new Token(new String(diff_arr), "diff"));
		}
		if (i < left_len) {
			leftList.add(new Token(left.substring(i, left_len), "diff"));
		}

		if (i < right_len) {
			rightList.add(new Token(right.substring(i, right_len), "diff"));
		}

		System.out.println(left + " | " + right);
		final String[] output = { "" };
		leftList.stream().forEach(token -> output[0] = output[0] + token);
		output[0] = output[0] + " | ";
		rightList.stream().forEach(token -> output[0] = output[0] + token);
		System.out.println(output[0]);
	}

}
