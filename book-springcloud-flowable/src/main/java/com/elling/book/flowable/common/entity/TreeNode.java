package com.elling.book.flowable.common.entity;

import java.util.List;
/**
 * .树实体类
 * @author Administrator
 *
 */
public class TreeNode {
	private String id;
	private String name;
	private String url;
	private String pid;
	private String icon;
	private String isLeaf;
	private List<TreeNode> children;
	
	public TreeNode() {
		super();
	}
	
	public TreeNode(String id, String name, String url, String pid, String icon,String isLeaf) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.pid = pid;
		this.icon = icon;
		this.isLeaf = isLeaf;
	}
	
	public TreeNode(String id, String name,String pid) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
	}
	
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "TreeNode [children=" + children + ", id=" + id + ", name="
				+ name + ", pid=" + pid + ", url=" + url + "]";
	}
	
	
}
