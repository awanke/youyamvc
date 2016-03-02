package com.magicalcoder.youyamvc.app.model.dict;

public class Dict {

	private Long id;
	private String dictKey;
	private String dictValue;
	private int dictType;
	private String dictDesc;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDictKey() {
		return dictKey;
	}
	public void setDictKey(String dictKey) {
		this.dictKey = dictKey;
	}
	public String getDictValue() {
		return dictValue;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public int getDictType() {
		return dictType;
	}
	public void setDictType(int dictType) {
		this.dictType = dictType;
	}
	public String getDictDesc() {
		return dictDesc;
	}
	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}
	
}
