package com.elling.book.sys.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.elling.book.sys.common.entity.TreeNode;

public class TreeBuilder {
	
	//多重循环
	public static List buildTreeMap(List<Map> treeNode){
		List trees = new ArrayList();
		List tmpList = null;
		for(Map m:treeNode){
			if("0".equals(m.get("PID"))){
				trees.add(m);
			}
			
			for(Map map:treeNode){
				if(map.get("PID").equals(m.get("ID"))){
					if(m.get("children") == null){
						tmpList = new ArrayList();
						m.put("children", tmpList);
					}
					((List)m.get("children")).add(map);
				}
			}
		}
		return trees;
	}
	
	//多重循环
	public static List<TreeNode> buildTree(List<TreeNode> treeNode){
		List<TreeNode> trees = new ArrayList<TreeNode>();
		List tmpList = null;
		for(TreeNode node:treeNode){
			if("0".equals(node.getPid())){
				trees.add(node);
			}
			
			for(TreeNode n:treeNode){
				if(n.getPid().equals(node.getId())){
					if(node.getChildren() == null){
						tmpList = new ArrayList();
						node.setChildren(tmpList);
					}
					node.getChildren().add(n);
				}
			}
		}
		return trees;
	}
	
	/**
     * 使用递归方法建树
     * @param treeNodes
     * @return
     */
    public static List<TreeNode> buildByRecursive(List<TreeNode> treeNodes) {
        List<TreeNode> trees = new ArrayList<TreeNode>();
        for (TreeNode treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }
 
    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static TreeNode findChildren(TreeNode treeNode,List<TreeNode> treeNodes) {
        for (TreeNode it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<TreeNode>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }

}

