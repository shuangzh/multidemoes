package com.shuangzh.dao.jpa.Repository;

import com.shuangzh.dao.jpa.domain.InstAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/4/4.
 */
@Repository
public interface InstAttributeRepo extends JpaRepository<InstAttribute, Integer> {
}
