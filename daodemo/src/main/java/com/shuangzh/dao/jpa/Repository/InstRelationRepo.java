package com.shuangzh.dao.jpa.Repository;

import com.shuangzh.dao.jpa.domain.InstRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/4/4.
 */
@Repository
public interface InstRelationRepo extends JpaRepository<InstRelation, Integer> {

}
