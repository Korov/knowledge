package org.leetcode;

/**
 * 给定一个含有n个正整数的数组和一个正整数s，找出该数组中满足其和 >= s
 * 的长度最小的连续子数组，并返回其长度。
 * 如果不存在符合条件的连续子数组，返回0。
 * <p>
 * 示例：
 * 输入：s = 7, nums = [2,3,1,2,4,3]
 * 输出：2
 * 解释：子数组 [4,3] 是该条件下的长度最小的连续子数组。
 */
public class MinArray {
    /**
     * 在方法一和方法二中，都是每次确定子数组的开始下标，然后得到长度最小的子数组，因此时间复杂度较高。为了降低时间复杂度，可以使用双指针的方法。
     * <p>
     * 定义两个指针 \textit{start}start 和 \textit{end}end 分别表示子数组的开始位置和结束位置，维护变量 \textit{sum}sum 存储子数组中的元素和（即从 \text{nums}[\textit{start}]nums[start] 到 \text{nums}[\textit{end}]nums[end] 的元素和）。
     * <p>
     * 初始状态下，\textit{start}start 和 \textit{end}end 都指向下标 00，\textit{sum}sum 的值为 00。
     * <p>
     * 每一轮迭代，将 \text{nums}[end]nums[end] 加到 \textit{sum}sum，如果 \textit{sum} \ge ssum≥s，则更新子数组的最小长度（此时子数组的长度是 \textit{end}-\textit{start}+1end−start+1），然后将 \text{nums}[start]nums[start] 从 \textit{sum}sum 中减去并将 \textit{start}start 右移，直到 \textit{sum} < ssum<s，在此过程中同样更新子数组的最小长度。在每一轮迭代的最后，将 \textit{end}end 右移。
     * <p>
     * 作者：LeetCode-Solution
     * 链接：https://leetcode-cn.com/problems/minimum-size-subarray-sum/solution/chang-du-zui-xiao-de-zi-shu-zu-by-leetcode-solutio/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param s
     * @param nums
     * @return
     */
    public static int method1(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int start = 0;
        int end = 0;
        int result = Integer.MAX_VALUE;
        int sum = 0;

        while (end < nums.length) {
            sum += nums[end];
            while (sum >= s) {
                result = Math.min(result, end - start + 1);
                sum -= nums[start];
                start++;
            }
            end++;
        }

        return result;
    }
}
