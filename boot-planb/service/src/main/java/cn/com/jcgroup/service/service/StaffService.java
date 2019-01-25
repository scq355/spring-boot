package cn.com.jcgroup.service.service;

import static cn.com.jcgroup.service.constant.ServiceIdentifier.DEFAULT_ADMIN_PAGE_SIZE;
import cn.com.jcgroup.service.domain.PbPersonnelData;
import cn.com.jcgroup.service.repositories.PbPersonnelDataRepository;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 员工相关
 *
 * @author LiuYong on 17/6/25 下午3:30.
 */
@Service
public class StaffService {

    private static final Logger LOG = LoggerFactory.getLogger(StaffService.class);
    
    @Autowired
    private PbPersonnelDataRepository pbPersonnelDataRepository;
    
    /**
     * 分页查询员工信息
     * 
     * @author LiuYong  
     */
    public JSONObject findStaffInfo(List<String> departmentList, int page){
        JSONObject result = null;
        if(departmentList !=null && !departmentList.isEmpty()){
            JSONArray jsonArray = null;
            Pageable pageable = new PageRequest(page-1,DEFAULT_ADMIN_PAGE_SIZE,new Sort(Sort.Direction.DESC,"entryTime"));
            Slice<PbPersonnelData> slice =pbPersonnelDataRepository.findStaffInfo(departmentList,pageable);
            if(slice != null && slice.hasContent()){
                int size = slice.getSize();
                jsonArray = new JSONArray(size);
                for(int i=0;i<size;i++){
                    jsonArray.add(slice.getContent().get(i));
                }
            }
            result = new JSONObject();
            result.put("hasNext",slice != null && slice.hasNext());
            result.put("data",jsonArray);
        }
        return result;
    }
    
    /**
     * 修改员工信息
     * 
     * @author LiuYong  
     */
    @Transactional
    public boolean updateStaffInfoByStaffCode(JSONObject jsonObject){
        if(jsonObject != null && !jsonObject.isEmpty()){
            PbPersonnelData pbPersonnelData = jsonObject.toJavaObject(PbPersonnelData.class);
            return pbPersonnelDataRepository.updateStaffInfoByStaffCode(pbPersonnelData) > 0;
        }
        return false;
    }
}
