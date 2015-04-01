package cn.focus.search.admin.model;

import java.util.Date;

public class ManualParticiple {
	
	private long id;
	
	private long groupId;
	
	private String manualWords;
	// 编辑人员
	private String editor;
	
	private Date createTime;
	
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getManualWords() {
		return manualWords;
	}

	public void setManualWords(String manualWords) {
		this.manualWords = manualWords;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

}
