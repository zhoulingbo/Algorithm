package pers.zhoulingbo.algorithm.tree;

public class TreeNode
{
    int val;

    TreeNode parent;
    TreeNode left;
    TreeNode right;

    TreeNode(int val, TreeNode parent, TreeNode left, TreeNode right)
    {
        this.val = val;
        this.parent = parent;
        this.left = left;
        this.right = right;
    }

}
