package com.wenping.yizhi.yizhiapp.cache;

import com.wenping.yizhi.yizhiapp.model.bean.douban.movie.child.SubjectsBean;
import com.wenping.yizhi.yizhiapp.utils.SpUtils;

import java.util.List;

/**
 * Created by WenPing on 12/27/2017.
 *
 */

public class Cache {

    /**
     * 获取豆瓣电影 hot cache
     * @return
     */
    public static List<SubjectsBean> getHotMovieCache() {
        return SpUtils.getDataList("hot_movie_cache", SubjectsBean.class);
    }

    /**
     * 保存豆瓣电影 hot cache
     * @param list
     */
    public static void saveHotMovieCache(List<SubjectsBean> list) {
        SpUtils.setDataList("hot_movie_cache",list);
    }
}
