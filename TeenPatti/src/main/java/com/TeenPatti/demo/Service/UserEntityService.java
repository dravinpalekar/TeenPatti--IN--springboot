package com.TeenPatti.demo.Service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;

import com.TeenPatti.demo.Entity.UserEntity;
import com.TeenPatti.demo.Repository.UserEntityRepository;

@Service
public class UserEntityService {
	
	private static final Logger log = LoggerFactory.getLogger(UserEntityService.class);
	
	@Autowired
    UserEntityRepository userRepository;
	
	@Autowired
    SendMailService mailService;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	public Map<String, String> RegisterUser(UserEntity userEntity){
		//set password and Store other information of new user's
		
        String encodedPassword = this.bCryptPasswordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);
//        userEntity.setUserOtherInformationEntity(userOtherInformationEntity);
        Map<String, String> RequestResponse = new HashMap<String, String>();
        try {
        	userRepository.save(userEntity);
        	       	
        	mailService.sendMail(userEntity);
        	
        }catch (DataIntegrityViolationException  e) {
			
        	log.error("Error in DataIntegrityViolationException= " + e);
        	RequestResponse.put("ErrorOK", "User already exists and user name is : " + userEntity.getUsername() + " OR email id is : " + userEntity.getEmail());
        	return RequestResponse;
		}
        catch (UnexpectedRollbackException  e) {
			
        	RequestResponse.put("ErrorOK", "Something is wrong UnexpectedRollbackException");
        	return RequestResponse;
		}
        catch (Exception e) {
			
//        	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        	log.error("Error in Exception= " + e);
        	RequestResponse.put("ErrorOK","Something is wrong!") ;
        	return RequestResponse;
		}
        log.error("Creating User " + userEntity.getUsername());
        RequestResponse.put("MessageOK","User created successfully and username is : "+userEntity.getUsername());
        return RequestResponse;
    }


}
