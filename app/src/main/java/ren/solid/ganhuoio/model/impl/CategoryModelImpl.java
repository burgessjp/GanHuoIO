package ren.solid.ganhuoio.model.impl;

import java.util.List;

import ren.solid.ganhuoio.constant.Apis;
import ren.solid.ganhuoio.model.ICategoryModel;

/**
 * Created by _SOLID
 * Date:2016/5/17
 * Time:10:31
 */
public class CategoryModelImpl implements ICategoryModel {
    @Override
    public List<String> loadCateGory() {
        return Apis.getGanHuoCateGory();
    }
}
