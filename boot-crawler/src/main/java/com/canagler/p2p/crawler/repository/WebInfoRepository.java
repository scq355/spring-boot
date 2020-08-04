package com.canagler.p2p.crawler.repository;

import com.canagler.p2p.crawler.domain.WebInfo;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by scq on 2018-04-08 11:23:45
 */
@Repository
public interface WebInfoRepository extends MongoRepository<WebInfo, String> {

	List<WebInfo> findAllByIsShow(Integer isShow);

	List<WebInfo> findAllByProcessStatus(@NonNull Integer status);

	List<WebInfo> findAllByInfoTypeAndProcessStatusIsIn(@NonNull Integer infoType, @NotEmpty List<Integer> processStatusList);

}
