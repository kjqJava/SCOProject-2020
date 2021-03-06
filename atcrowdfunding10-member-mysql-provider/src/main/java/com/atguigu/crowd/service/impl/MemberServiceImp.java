package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.po.MemberPOExample;
import com.atguigu.crowd.mapper.MemberPOMapper;
import com.atguigu.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 孔佳齐丶
 * @create 2020-08-27 14:32
 * @package com.atguigu.crowd.service.impl
 */
@Transactional(readOnly = true)
@Service
public class MemberServiceImp implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginAcct(String loginAcct) {

        //1.创建example对象,
        MemberPOExample example = new MemberPOExample();

        //2.创建Criteria对象
        MemberPOExample.Criteria criteria = example.createCriteria();

        //3.封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);

        //获取结果
        List<MemberPO> list =  memberPOMapper.selectByExample(example);

       if(list==null||list.size()==0){
           return null;
       }

        return list.get(0);
    }

    //rollbackFor 如果类加了这个注解，那么这个类里面的方法抛出异常，就会回滚，数据库里面的数据也会回滚。
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class,readOnly = false)
    @Override
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
    }
}
