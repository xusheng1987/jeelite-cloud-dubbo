/**
 * Copyright &copy; 2017-2018 <a href="https://github.com/xusheng1987/jeelite">jeelite</a> All rights reserved.
 */
package com.github.flying.jeelite.common.persistence;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Maps;

/**
 * Entity支持类
 * @author flying
 * @version 2014-05-16
 */
public abstract class BaseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 实体编号（唯一标识）
	 */
	@TableId(type=IdType.ASSIGN_ID)
	protected String id;

	/**
	 * 当前实体分页对象
	 */
	@TableField(exist=false)
	protected Page page;

	/**
	 * 自定义SQL（SQL标识，SQL内容）
	 */
	@TableField(exist=false)
	protected Map<String, String> sqlMap;

	public BaseEntity() {

	}

	public BaseEntity(String id) {
		this();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonIgnore
	@XmlTransient
	public Page getPage() {
		if (page == null){
			page = new Page();
		}
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	@JsonIgnore
	@XmlTransient
	public Map<String, String> getSqlMap() {
		if (sqlMap == null){
			sqlMap = Maps.newHashMap();
		}
		return sqlMap;
	}

	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	/**
	 * 获取数据库名称
	 */
	@JsonIgnore
	public String getDbName(){
		return "mysql";
	}

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
	
	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";

}