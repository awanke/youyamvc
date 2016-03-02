package com.magicalcoder.youyamvc.core.common.dto;

/**
 * qq:799374340
 *
 * @author hdy 2013-7-15下午5:18:58
 */
public class JsonData {

	private Integer code = 0;// 默认值
	private String message;
	private Object info;
	private String jsonp;

	public static class Builder{
		private Integer code = 0;// 默认值
		private String message;
		private Object info;
		private String jsonp;

		public Builder(Object info){
			this.info = info;
		}

		public Builder code(Integer code){
			this.code = code;
			return this;
		}
		public Builder message(String message){
			this.message = message;
			return this;
		}
		public Builder jsoup(String jsonp){
			this.jsonp = jsonp;
			return this;
		}

		public JsonData build(){
			return new JsonData(this);
		}
	}

	public JsonData(Builder builder) {
		this.code = builder.code;
		this.jsonp = builder.jsonp;
		this.message = builder.message;
		this.info = builder.info;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	public String getJsonp() {
		return jsonp;
	}

	public void setJsonp(String jsonp) {
		this.jsonp = jsonp;
	}
}
