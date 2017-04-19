package com.shuangzh.dao.jpa.Repository;

import com.shuangzh.dao.jpa.domain.CIDefinition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/4/2.
 */

@Repository
public interface CIDefinitionRepo extends CrudRepository<CIDefinition, Integer> {

}
