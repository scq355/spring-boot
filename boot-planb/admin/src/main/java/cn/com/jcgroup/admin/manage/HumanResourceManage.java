package cn.com.jcgroup.admin.manage;

import cn.com.jcgroup.service.service.PersonnelFlowService;
import cn.com.jcgroup.service.util.DateUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人力资源信息同步
 *
 * @author LiuYong on 17/6/18 下午2:44.
 */
@Service
public class HumanResourceManage {

    private static final Logger LOG = LoggerFactory.getLogger(HumanResourceManage.class);

    private static final int SIZE = 20;

    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PersonnelFlowService personnelFlowService;

    /**
     * 增量同步人员信息
     *
     * @author LiuYong
     */
    public void synHumanResource() {
        //查询当前数据库人员id
        int id = personnelFlowService.queryLastStaffId();
        //开始增量查询数据
        String sql = "SELECT a.ID as id, a.LASTNAME as  stuffName,a.NCCODE as stuffCode ,a.JOBLEVEL as positionLevel ,a.JOBTITLENAME as positions ,a.SEX  as sex," +
                "  a.ENTRYTIME as entryTime,a.WORKTIME as workTime,  a.SUBCOMPANYNAME as companyName,a.COMPANYID as department ,a.DEGREE as degree ,a.BIRTHDAY as birthday ,a.STATUS as status ,a.LOCATIONNAME as workSite " +
                "  from JC_V_CUSTINFO a   where a.ID > ? AND rownum<= ? order by a.ID ASC ";
        int page = 1;
        while (true) {
            List<Map<String,Object>> list = jdbcTemplate.queryForList(sql, new Object[]{id, SIZE});
            if (list != null && !list.isEmpty()) {
                int length = list.size();
                LOG.info("[人员信息]当前同步页数为page={},共{}条记录", page, length);
                //批量插入数据
                if(!personnelFlowService.batchAddStaff(list)){
                    LOG.error("[人员信息],数据插入失败,info={}", JSON.toJSONString(list));
                }
                if (length < SIZE) {
                    break;
                } else {
                    //修改id
                    BigDecimal tempId = (BigDecimal) list.get(SIZE - 1).get("ID");
                    id = tempId.intValue();
                    page++;
                }
            }else{
                break;
            }
        }
    }

    /**
     * 增量同步人员异动信息
     *
     * @author LiuYong
     */
    public void synPersonFlowInfo(){
        //查询上一次同步时间
        Date lastModifyTime = personnelFlowService.queryLastModifyTime();
        String sql = "select result.* from(select rownum as rn, a.BEGINDATE as beginTime,a.CLERKCODE as staffCode,a.ENDDATE as endTime,a.ENDFLAG as isEnd,a.TRNSEVENT as type," +
                "  a.TS as modifyTime,a.PK_PSNJOB as relationId  from JC_V_HI_PSNJOB a where rownum<=? ";
        if(lastModifyTime != null){
            String tempTime = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",lastModifyTime);
            sql += "  and a.TS>'"+tempTime+"'";
        }
        sql += " order by a.TS asc) result where result.rn>?";
        LOG.info("[人员异动流水]同步sql={}",sql);
        int page = 1;
        while (true){
            int start = (page - 1) * SIZE;
            int end = page * SIZE;
            List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,new Object[]{end,start});
            if (list != null && !list.isEmpty()) {
                int length = list.size();
                LOG.info("[人员人员异动流水]当前同步页数为page={},共{}条记录", page, length);
                //更新数据
                for(Map<String,Object> map : list){
                    if(personnelFlowService.queryRelationIdExist(String.valueOf(map.get("relationId")))){
                        //更新数据
                        personnelFlowService.updatePersonFlowInfo(map);
                    }else{
                        //增加数据
                        personnelFlowService.addPersonFlowInfo(map);
                    }    
                }
                if (length < SIZE) {
                    break;
                } else {
                    page++;
                }
            }else{
                break;
            }
        }
    }

}