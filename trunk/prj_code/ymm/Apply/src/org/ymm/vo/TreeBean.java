package org.ymm.vo;

import java.util.List;

public class TreeBean {
	private String id;
	private String text;
	private boolean leaf;
	private String cls = "folder";
	private String funName;
	private List<TreeBean> children;

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public List<TreeBean> getChildren() {
		this.leaf = this.children == null || this.children.size() <= 0 ? true
				: false;
		return children;
	}

	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}
}
