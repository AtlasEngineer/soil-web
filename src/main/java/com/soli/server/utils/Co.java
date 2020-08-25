package com.atlas.server.utils;

import com.jfinal.json.Json;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;

import java.util.Map;

/**
 * @author: xuzhonghao
 * @create: 2019-09-25 19:52
 */
public class Co extends Ret {

    private static final String CODE = "code";
    private static final Integer CODE_OK = 200;
    private static final Integer CODE_FAIL = 400;
    private static final Integer CODE_EXISTS = 402;


    public Co(){}

    public static Co by(Object key, Object value) {
        return new Co().set(key, value);
    }

    public static Co ok() {
        return new Co().setOk();
    }

    public static Co ok(Object key, Object value) {
        return ok().set(key, value);
    }

    public static Co fail() {
        return new Co().setFail();
    }

    public static Co fail(Object key, Object value) {
        return fail().set(key, value);
    }
    public static Co exists() {
        return new Co().setFail();
    }

    public static Co exists(Object key, Object value) {
        return fail().set(key, value);
    }

    public Co setOk() {
        super.put(CODE, CODE_OK);
        return this;
    }

    public Co setFail() {
        super.put(CODE, CODE_FAIL);
        return this;
    }

    public Co setExists() {
        super.put(CODE, CODE_EXISTS);
        return this;
    }

    public Co set(Object key, Object value) {
        super.put(key, value);
        return this;
    }

    public Co setIfNotBlank(Object key, String value) {
        if (StrKit.notBlank(value)) {
            set(key, value);
        }
        return this;
    }

    public Co setIfNotNull(Object key, Object value) {
        if (value != null) {
            set(key, value);
        }
        return this;
    }

    public Co set(Map map) {
        super.putAll(map);
        return this;
    }

    public Co set(Ret ret) {
        super.putAll(ret);
        return this;
    }

    public Co delete(Object key) {
        super.remove(key);
        return this;
    }

    public <T> T getAs(Object key) {
        return (T)get(key);
    }

    public String getStr(Object key) {
        Object s = get(key);
        return s != null ? s.toString() : null;
    }

    public Integer getInt(Object key) {
        Number n = (Number)get(key);
        return n != null ? n.intValue() : null;
    }

    public Long getLong(Object key) {
        Number n = (Number)get(key);
        return n != null ? n.longValue() : null;
    }

    public Number getNumber(Object key) {
        return (Number)get(key);
    }

    public Boolean getBoolean(Object key) {
        return (Boolean)get(key);
    }

    /**
     * key 存在，并且 value 不为 null
     */
    public boolean notNull(Object key) {
        return get(key) != null;
    }

    /**
     * key 不存在，或者 key 存在但 value 为null
     */
    public boolean isNull(Object key) {
        return get(key) == null;
    }

    /**
     * key 存在，并且 value 为 true，则返回 true
     */
    public boolean isTrue(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean)value == true));
    }

    /**
     * key 存在，并且 value 为 false，则返回 true
     */
    public boolean isFalse(Object key) {
        Object value = get(key);
        return (value instanceof Boolean && ((Boolean)value == false));
    }

    public String toJson() {
        return Json.getJson().toJson(this);
    }

    public boolean equals(Object co) {
        return co instanceof Co && super.equals(co);
    }
}
