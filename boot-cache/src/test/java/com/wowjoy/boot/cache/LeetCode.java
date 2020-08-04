package com.wowjoy.boot.cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class LeetCode {



    public int[] twoSum(int[] nums, int target) {
        List<Integer> numList = new ArrayList<>();
        for (int num : nums) {
            numList.add(num);
        }
        int[] result = new int[2];
        for(int i = 0; i < numList.size(); i++) {
            int temp = target - numList.get(i);
            if(numList.contains(temp)) {
                result[0] = i;
                result[1] = numList.indexOf(temp);
            }
            break;
        }

        return result;
    }

    public int reverse(int x) {
        String strX = String.valueOf(x);
        char[] charX = strX.toCharArray();
        String reverseX = "";
        boolean flag = true;
        for (int i = charX.length - 1; i >= 0 ; i--) {
            if (charX[i] == '-') {
                flag = false;
            } else {
                reverseX += charX[i];
            }
        }
        reverseX = flag ? reverseX : ("-" + reverseX);
        if (Double.valueOf(reverseX) < Integer.MIN_VALUE || Double.valueOf(reverseX) > Integer.MAX_VALUE) {
            return 0;
        }
        return Integer.valueOf(reverseX);
    }

    public boolean isPalindrome(int x) {
        String strX = String.valueOf(x);
        char[] charX = strX.toCharArray();
        String reverseX = "";
        for (int i = charX.length - 1; i >= 0 ; i--) {
            reverseX += charX[i];
        }
        return reverseX.equals(String.valueOf(x));
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j] && j - i <= k) {
                    return true;
                }
            }
        }
        return false;
    }



    public int lengthOfLastWord(String s) {
        if (s.trim().length() == 0) {
            return 0;
        }
        String[] sList = s.split(" ");
        return sList[sList.length - 1].trim().length();
    }

    public int singleNumber(int[] nums) {
        int record;
        for (int i = 0; i < nums.length; i++) {
            record = 0;
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] == nums[j] && i != j) {
                    record = 2;
                    break;
                }
            }
            if (record == 0) {
                return nums[i];
            }
        }
        return 0;
    }

    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> resultList = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            List<Integer> rowList = new ArrayList<>();
            for (int k = 0; k < i + 1; k++) {
                rowList.add(0);
            }
            rowList.set(i, 1);
            rowList.set(0, 1);
            for (int j = 0; j < i; j++) {
                if (rowList.get(j) != 1) {
                    rowList.set(j, resultList.get(i - 1).get(j - 1) + resultList.get(i - 1).get(j));
                }
            }
            resultList.add(rowList);
        }
        return resultList;
    }

    public boolean isPalindrome(String s) {
        char[] chars = s.toLowerCase().trim().toCharArray();
        char[] resultChars = new char[s.length()];
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= '0' && chars[i] <= '9') || (chars[i] >= 'a' && chars[i] <= 'z')) {
                resultChars[i] = chars[i];
            } else {
                resultChars[i] = '*';
            }
        }
        String res = String.valueOf(resultChars);
        res = res.replace("*", "");
        chars = res.toCharArray();
        for (int j = 0; j < chars.length; j++) {
            if (chars[j] != chars[chars.length - j - 1]) {
                return false;
            }
        }
        return true;
    }


    public void merge(int[] nums1, int m, int[] nums2, int n) {
        List<Integer> resultList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            resultList.add(nums1[i]);
        }
        for (int i = 0; i < n; i++) {
            resultList.add(nums2[i]);
        }

        Collections.sort(resultList);
        for (int i = 0; i < resultList.size(); i++) {
            nums1[i] = resultList.get(i);
        }
    }


    public int mySqrt(int x) {
        return (int) Math.sqrt(x);
    }


    public int hammingWeight(Integer n) {
        int num1 = 0;
        while (n != 0) {
            if (n % 2 == 1) {
                num1++;
            }
            n = n / 2;
        }
        return num1;
    }

    public boolean isPowerOfTwo(int n) {
        while (n > 0) {
            n = n / 2;
        }
        return (n == 2) ? false : true;
    }

    @Test
    public void test000() {
//        System.out.println(reverse(-2147483648));

//        System.out.println(isPalindrome(-13));

//        int[] nums = new int[] {1,2,3,1};
//        int k = 3;
//        System.out.println(containsNearbyDuplicate(nums, k));

//        System.out.println(lengthOfLastWord(" "));

//        int[] nums = new int[] {2, 2, 1};
//        System.out.println(singleNumber(nums));

//        System.out.println(generate(5));
//        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));

//        int[] num1 = new int[] {2, 4, 0};
//        int[] num2 = new int[] {3, 5, 1};
//        merge(num1,3, num2, 3);

//        log.info(String.valueOf(Integer.MAX_VALUE) + "");
//      2147483648
//        log.info(hammingWeight(21474836) + "");
        log.info(isPowerOfTwo(8) + "");


    }


}
