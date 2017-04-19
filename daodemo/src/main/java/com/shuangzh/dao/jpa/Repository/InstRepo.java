package com.shuangzh.dao.jpa.Repository;

import com.shuangzh.dao.jpa.domain.Inst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/4/4.
 */
@Repository
public interface InstRepo extends JpaRepository<Inst, Integer>{

}
