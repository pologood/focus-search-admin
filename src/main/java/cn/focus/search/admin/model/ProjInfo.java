package cn.focus.search.admin.model;


/**
 * 
 * @author xuemingtang
 *
 */
public class ProjInfo {
	
	private long groupId;
	
	private String projName;
	//楼盘名分词
	private String projNameWords;
	//校正名
	private String projNameOther;
	//校正名分词
	private String projNameOtherWords;
	//楼盘别名
	private String projNoLink;
	//别名分词
	private String projNoLinkWords;
	
	private String projAddress;
	
	private String projAddressWords;
	
	private String kfsName;
	
	private String kfsNameWords;
	//人工干预分词
	private String manualWords;

	
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	public String getProjName() {
		return projName;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getProjNameWords() {
		return projNameWords;
	}
	public void setProjNameWords(String projNameWords) {
		this.projNameWords = projNameWords;
	}
	public String getProjNameOther() {
		return projNameOther;
	}
	public void setProjNameOther(String projNameOther) {
		this.projNameOther = projNameOther;
	}
	public String getProjNameOtherWords() {
		return projNameOtherWords;
	}
	public void setProjNameOtherWord(String projNameOtherWords) {
		this.projNameOtherWords = projNameOtherWords;
	}
	public String getProjNoLink() {
		return projNoLink;
	}
	public void setProjNoLink(String projNoLink) {
		this.projNoLink = projNoLink;
	}
	public String getProjNoLinkWords() {
		return projNoLinkWords;
	}
	public void setProjNoLinkWords(String projNoLinkWords) {
		this.projNoLinkWords = projNoLinkWords;
	}
	public String getProjAddress() {
		return projAddress;
	}
	public void setProjAddress(String projAddress) {
		this.projAddress = projAddress;
	}
	public String getProjAddressWords() {
		return projAddressWords;
	}
	public void setProjAddressWords(String projAddressWords) {
		this.projAddressWords = projAddressWords;
	}
	public String getKfsName() {
		return kfsName;
	}
	public void setKfsName(String kfsName) {
		this.kfsName = kfsName;
	}
	public String getKfsNameWords() {
		return kfsNameWords;
	}
	public void setKfsNameWords(String kfsNameWords) {
		this.kfsNameWords = kfsNameWords;
	}
	public String getManualWords() {
		return manualWords;
	}
	public void setManualWords(String manualWords) {
		this.manualWords = manualWords;
	}
	public void setProjNameOtherWords(String projNameOtherWords) {
		this.projNameOtherWords = projNameOtherWords;
	}

}
