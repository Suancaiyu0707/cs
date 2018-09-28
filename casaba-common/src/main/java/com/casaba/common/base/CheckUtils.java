package com.casaba.common.base;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * created by chaoyi on 2017/10/12
 */
public class CheckUtils {
    private static final Logger logger = LoggerFactory.getLogger(CheckUtils.class);

    /**
     * 判断指定obj是否为null或空集合，name为字段名，报错形式为“参数为空，***name”
     * 使用形式如CheckUtils.NotEmpty(promotion.getType,"promotion.type")
     *
     * @param obj
     * @param name
     */
    public static void notEmpty(Object obj, String name) {
        if (name == null)
            name = "";
        if (obj == null) {
            logger.error("参数为空: " + name);
            throw new IllegalArgumentException("参数为空: " + name);
        } else if (obj instanceof String) {
            if (StringUtils.isBlank((String) obj)) {
                logger.error("字符串参数为空: " + name);
                throw new IllegalArgumentException("字符串参数为空: " + name);
            }
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0) {
                logger.error("集合参数为空: " + name);
                throw new IllegalArgumentException("集合参数为空: " + name);
            }
        } else if (obj instanceof Map) {
            if (((Map) obj).isEmpty()) {
                logger.error("map参数为空: " + name);
                throw new IllegalArgumentException("Map参数为空: " + name);
            }
        }
    }

    /**
     * 判断所有指定对象是否为null或空集合，有字段报错即抛异常终止
     * 使用方式形如 CheckUtils.allNotEmpty(promotion, promotion.getPromotionType())
     *
     * @param objs
     */
    public static void allNotEmpty(Object... objs) {
        for (Object obj : objs) {
            notEmpty(obj);
        }
    }

    public static void notEmpty(Object obj) {
        notEmpty(obj, "");
    }

    public static void main(String[] args) {
        HashMap<Object, Object> map = Maps.newHashMap();
        // map.put(1, "de");
//        allNotEmpty(new ArrayList<>(), map);
        // notEmpty(map);
        Set set = Sets.newHashSet();
        CheckUtils.notEmpty(set);
    }

}
