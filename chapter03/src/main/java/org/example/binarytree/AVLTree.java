package org.example.binarytree;

/**
 * AVL树
 */
public class AVLTree {

    static class AVLNode {
        // 节点的key
        int key;
        // 节点的value
        Object value;
        AVLNode root;
        // 节点的左孩子
        AVLNode left;
        // 节点的右孩子
        AVLNode right;
        // 节点的高度（默认新创建节点的高度为1，而不是0，这是力扣的约定）
        int height = 1;
        public AVLNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }
        public AVLNode(int key) {
            this.key = key;
        }
        public AVLNode(int key, Object value, AVLNode left, AVLNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        // 获取节点高度
        private int height(AVLNode node) {
            return node == null ? 0 : node.height;
        }
        // 更新节点高度(新增，删除，旋转)
        private void updateHeight(AVLNode node) {
            node.height = Integer.max(height(node.left), height(node.right)) + 1;
        }

        // 平衡因子（banlance factor）= 左子树高度 - 右子树高度
        // 0, 1, -1 平衡，否则不平衡
        // bf > 1 左边高
        // bf < -1 右边高
        private int bf(AVLNode node) {
            return height(node.left) - height(node.right);
        }
        // 右旋转
        // 参数：要旋转的节点，返回值：新的根节点
        private AVLNode rightRotate(AVLNode node) {
            AVLNode leftNode = node.left;
            AVLNode rightNode = leftNode.right;
            leftNode.right = node;
            node.left = rightNode;
            updateHeight(node);
            updateHeight(leftNode);
            return leftNode;
        }

        // 左旋转
        private AVLNode leftRotate(AVLNode node) {
            AVLNode rightNode = node.right;
            AVLNode leftNode = rightNode.left;
            rightNode.left = node;
            node.right = leftNode;
            updateHeight(node);
            updateHeight(rightNode);
            return rightNode;
        }

        // 先左旋左子树，再右旋根节点(处理LR)
        private AVLNode leftRightRotate(AVLNode node) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // 先右旋右子树，再左旋根节点(处理RL)
        private AVLNode rightLeftRotate(AVLNode node) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // 检查节点是否失衡，若失衡，则重新平衡代码
        private AVLNode balance(AVLNode node) {
            if (node == null) {
                return null;
            }
            int bf = bf(node);
            if (bf > 1 && bf(node.left) >= 0) {  // LL
                return rightRotate(node);
            }else if (bf > 1 && bf(node.left) < 0) {  // LR
                return leftRightRotate(node);
            }else if (bf < -1 && bf(node.right) > 0) {  // RL
                return rightLeftRotate(node);
            }else if (bf < -1 && bf(node.right) <= 0) {  // RR
                return leftRotate(node);
            }
            return node;
        }

        public void put(int key, Object value) {
            root = doPut(root, key, value);
        }

        public AVLNode doPut(AVLNode node, int key, Object value) {
            // 1. 找到空位，创建新节点
            if (node == null) {
                return new AVLNode(key, value);
            }
            // 2. key 已存在，更新
            if (key == node.key) {
                node.value = value;
                return node;
            }
            // 3. 继续查找
            if (key < node.key) {
                node.left = doPut(node.left, key, value);  // 向左
            } else {
                node.right = doPut(node.right, key, value);  // 向右
            }
            // 更新高度
            updateHeight(node);
            // 平衡节点
            return balance(node);
        }

        public void remove(int key) {
            root = doRemove(root, key);
        }
        private AVLNode doRemove(AVLNode node, int key) {
            // 1. node == null
            if (node == null) {
                return null;
            }
            // 2. 没找到 key
            if (key < node.key) {
                node.left = doRemove(node.left, key);
            } else if (node.key < key) {
                node.right = doRemove(node.right, key);
            } else {
                // 3. 找到 key 1）没有孩子
                if (node.left == null && node.right == null) {
                    return null;
                } else if (node.left == null) {
                    //2）只有一个右孩子
                    node = node.right;
                } else if (node.right == null) {
                    //3）只有一个左孩子
                    node = node.left;
                } else {
                    //4）有两个孩子
                    AVLNode s = node.right; // 后继节点
                    while (s.left != null) {
                        s = s.left;
                    }
                    s.right = doRemove(node.right, s.key);
                    s.left = node.left;
                    node = s;
                }
            }
            // 4. 更新高度
            updateHeight(node);
            // 5. 检查是否失衡
            return balance(node);
        }

    }
}
