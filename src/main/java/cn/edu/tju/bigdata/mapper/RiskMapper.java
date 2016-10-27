package cn.edu.tju.bigdata.mapper;

import cn.edu.tju.bigdata.entity.RiskFormMap;
import cn.edu.tju.bigdata.mapper.base.BaseMapper;

import java.util.List;

/**
 * Created by lucas on 2016/10/5.
 */
public interface RiskMapper extends BaseMapper {
    public List<RiskFormMap> findRiskPage(RiskFormMap riskFormMap);
    public List<RiskFormMap> findAllRisk();
}
