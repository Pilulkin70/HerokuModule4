package ua.garmash.module4.dao;

import ua.garmash.module4.model.Detail;

public class DetailDao extends AbstractDao<Detail> {
    @Override
    protected void init() {
        aClass = Detail.class;
    }
}
