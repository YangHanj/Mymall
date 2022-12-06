package iee.yh.Mymall.member.service.impl;

import iee.yh.Mymall.member.vo.UserLoginVo;
import iee.yh.Mymall.member.vo.UserRegisterVo;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.member.dao.MemberDao;
import iee.yh.Mymall.member.entity.MemberEntity;
import iee.yh.Mymall.member.service.MemberService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional()
    public void register(UserRegisterVo registerVo) {
        MemberEntity member = new MemberEntity();
        // 设置默认等级
        member.setLevelId(1l);

        //  密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(registerVo.getPassword());
        member.setPassword(encode);
        // TODO 检查用户名和mail是否唯一
        member.setUsername(registerVo.getUsername());
        member.setEmail(registerVo.getMail());

        this.baseMapper.insert(member);

    }

    @Override
    public MemberEntity login(UserLoginVo userLoginVo) {
        String username = userLoginVo.getUsername();
        MemberEntity member = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>().eq("username", username)
                .or().eq("email",username));
        if (member == null) // 账户不存在
            return null;
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(userLoginVo.getPassword(), member.getPassword());
        if (matches)
            return member;
        else return null;  // 密码错误
    }

}