package pers.zhoulingbo.algorithm.tree;

/**
 * 
 * 二叉搜索树
 * 
 * @version v1.0.0 @author zhoulingbo 2019-2-21 新建与整理
 */
public abstract class BinarySearchTree
{

    private TreeNode root;

    public BinarySearchTree(TreeNode root)
    {
        this.root = root;
    }

    /**
     * 中序遍历
     */
    public void inOrder()
    {
        inOrder(root);
    }

    /**
     * 中序遍历
     * @param node
     */
    protected void inOrder(TreeNode node)
    {
        if (node == null)
            return;

        inOrder(node.left);
        travel(node.val);
        inOrder(node.right);
    }

    /**
     * 前序遍历
     */
    public void preOrder()
    {
        preOrder(root);
    }

    /**
     * 前序遍历
     * @param node
     */
    protected void preOrder(TreeNode node)
    {
        if (node == null)
            return;

        travel(node.val);
        preOrder(node.left);
        preOrder(node.right);
    }

    public void postOrder()
    {
        postOrder(root);
    }

    /**
     * 后序遍历
     * @param node
     */
    protected void postOrder(TreeNode node)
    {
        if (node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        travel(node.val);
    }

    protected abstract void travel(int val);

    /**
     * 插入节点
     * @param key
     */
    public void insert(int key)
    {
        if (root == null)
        {
            root = new TreeNode(key, null, null, null);
            return;
        }

        TreeNode node = root;
        while (node != null)
        {
            if (node.val == key)
                return;

            if (node.val < key)
            {
                if (node.right == null)
                {
                    node.right = new TreeNode(key, node, null, null);
                    return;
                }
                node = node.right;
            }
            else
            {
                if (node.left == null)
                {
                    node.left = new TreeNode(key, node, null, null);
                    return;
                }
                node = node.left;
            }
        }
    }

    /**
     * 删除节点
     * @param key
     */
    public void delete(int key)
    {
        TreeNode item = search(key);
        if (item == null)
            return;

        // 左右子节点都存在
        if (item.left != null && item.right != null)
        {
            TreeNode s = searchSuccessor(item);
            item.val = s.val;
            item = s;
        }

        // 左右节点至少有一个节点不存在
        TreeNode child = null;
        if (item.left != null)
            child = item.left;
        else
            child = item.right;

        if (child != null)
            child.parent = item.parent;

        if (item.parent == null)
            root = child;
        else if (item.parent.left == item)
            item.parent.left = child;
        else
            item.parent.right = child;
    }

    /**
     * 查找节点
     * @param key
     * @return
     */
    public TreeNode search(int key)
    {
        TreeNode node = root;
        while (node != null)
        {
            if (node.val == key)
                return node;

            if (node.val < key)
                node = node.right;
            else
                node = node.left;
        }
        return null;
    }

    /**
     * 查找node的前驱节点(小于该节点的最大节点)
     * @param node
     * @return
     */
    public TreeNode searchPredecessor(TreeNode node)
    {
        if (node.left != null)
            return max(node.left);

        TreeNode p = node.parent;
        while ((p != null) && (p.left == node))
        {
            node = p;
            p = node.parent;
        }

        return p;
    }

    public TreeNode searchPredecessor(int key)
    {
        TreeNode node = search(key);
        if (node == null)
            return null;
        return searchPredecessor(node);
    }

    /**
     * 查找node的后继节点(大于该节点的最小节点)
     * @param node
     * @return
     */
    public TreeNode searchSuccessor(TreeNode node)
    {
        if (node.right != null)
            return min(node.right);

        TreeNode p = node.parent;
        while ((p != null) && (p.right == node))
        {
            node = p;
            p = node.parent;
        }

        return p;
    }

    public TreeNode searchSuccessor(int key)
    {
        TreeNode node = search(key);
        if (node == null)
            return null;
        return searchSuccessor(node);
    }

    /**
     * 是否存在节点
     * @param key
     * @return
     */
    public boolean exist(int key)
    {
        TreeNode node = root;
        while (node != null)
        {
            if (node.val == key)
                return true;

            if (node.val < key)
                node = node.right;
            else
                node = node.left;
        }
        return false;
    }

    /**
     * 最大值
     * @return
     */
    public int max()
    {
        TreeNode node = max(root);
        if (node != null)
            return node.val;

        return -1;
    }

    /**
     * 最大值
     * @param node
     * @return
     */
    protected TreeNode max(TreeNode node)
    {
        if (node == null)
            return null;

        while (node.right != null)
            node = node.right;
        return node;
    }

    /**
     * 最小值
     * @return
     */
    public int min()
    {
        TreeNode node = min(root);
        if (node != null)
            return node.val;

        return -1;
    }

    /**
     * 最小值
     * @param node
     * @return
     */
    protected TreeNode min(TreeNode node)
    {
        if (node == null)
            return null;

        while (node.left != null)
            node = node.left;
        return node;
    }

}
