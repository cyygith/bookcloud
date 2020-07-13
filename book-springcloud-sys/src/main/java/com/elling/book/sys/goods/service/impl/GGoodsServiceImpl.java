package com.elling.book.sys.goods.service.impl;

import com.elling.book.sys.goods.dao.mapper.GGoodsMapper;
import com.elling.book.sys.goods.model.GGoods;
import com.elling.book.sys.goods.service.GGoodsService;
import com.elling.book.sys.common.base.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * Created by cyy on 2019/08/01.
 */
@Service
public class GGoodsServiceImpl extends AbstractService<GGoods> implements GGoodsService {

    @Autowired
    private GGoodsMapper gGoodsMapper;

	@Override
	public int deleteByUuids(List<String> ids) {
		gGoodsMapper.deleteByUuids(ids);
		return 0;
	}

	@Override
	public GGoods getGoodsById(GGoods gGoods) {
		return gGoodsMapper.getGoodsById(gGoods);
	}
    
}
