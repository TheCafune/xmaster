package cn.edu.tju.bigdata.mapper;

import cn.edu.tju.bigdata.entity.RiskEvaluateDetailFormMap;
import cn.edu.tju.bigdata.entity.RiskEvaluateIndexFormMap;
import cn.edu.tju.bigdata.entity.RiskFormMap;
import cn.edu.tju.bigdata.mapper.base.BaseMapper;

import java.util.List;

/**
 * Created by lucas on 2016/10/5.
 */
public interface RiskEvaluateDetailMapper extends BaseMapper {

	List<RiskEvaluateDetailFormMap> findbyUserId(RiskEvaluateDetailFormMap riskEvaluateDetailFormMap);

}
