package com.simon.util;

import com.simon.domain.AppUser;
import com.simon.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by simon on 2017/3/5.
 */
public class TokenUtil {

    private static final Logger log = LoggerFactory.getLogger(TokenUtil.class);

    private static TokenUtil tokenUtil;

    private TokenUtil(){

    }

    public static TokenUtil getInstance(){
        if(null==tokenUtil){
            tokenUtil = new TokenUtil();
        }
        return tokenUtil;
    }

    public AppUser getAppUserByAccessToken(AppUserRepository appUserRepository, JdbcTemplate jdbcTemplate, String access_token){
        return appUserRepository.findByPhone(getPhoneByAccessToken(jdbcTemplate,access_token));
    }

    public String getAppUserIdByAccessToken(AppUserRepository appUserRepository, JdbcTemplate jdbcTemplate, String access_token){
        return appUserRepository.findByPhone(getPhoneByAccessToken(jdbcTemplate,access_token)).getId();
    }

    public String getPhoneByAccessToken(JdbcTemplate jdbcTemplate, String access_token){
        //PostgreSQL
        return jdbcTemplate.queryForObject("SELECT user_name FROM oauth_access_token" +
                " WHERE encode(token, 'escape') LIKE CONCAT('%', ?)", new Object[]{access_token}, String.class);
        //MySQL
        /*return jdbcTemplate.queryForObject("SELECT user_name FROM oauth_access_token" +
                " WHERE right(cast(token as char), 36)=?", new Object[]{access_token}, String.class);*/

    }
}
