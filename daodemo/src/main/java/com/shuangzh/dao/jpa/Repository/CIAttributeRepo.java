package com.shuangzh.dao.jpa.Repository;

import com.shuangzh.dao.jpa.domain.CIAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/4/3.
 */

@Repository
public interface CIAttributeRepo extends JpaRepository<CIAttribute, Integer> {

}
