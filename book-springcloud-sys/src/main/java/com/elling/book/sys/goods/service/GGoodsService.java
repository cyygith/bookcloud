package com.elling.book.sys.goods.service;
import java.util.List;

import com.elling.book.sys.common.base.Service;
import com.elling.book.sys.goods.model.GGoods;

/**
 *
 * Created by cyy on 2019/08/01.
 */
public interface GGoodsService extends Service<GGoods> {
	/**
	 * 根据uuid批量删除
	 * @param ids
	 * @return
	 */
	int deleteByUuids(List<String> ids);
	
	/**
	 * 根据其他条件查询Goods
	 * @param gGoods
	 * @return
	 */
	public GGoods getGoodsById(GGoods gGoods);
}
