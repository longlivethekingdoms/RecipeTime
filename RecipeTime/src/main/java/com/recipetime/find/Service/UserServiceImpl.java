package com.recipetime.find.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipetime.find.DAO.UserDAO;
import com.recipetime.find.Model.Users;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;

	@Transactional
	@Override
	public void insertUserAdmin(Users users) {
		//1.recipeuseradmin추가
		userDAO.insertUserAdmin(users);
		//2.userloginstate추가
		userDAO.insertUserLoginState(users.getUserid());
		
	}
	
	@Override
	public String validateUser(Users users) {
		
		if (users.getUserid() == null || users.getUserid().isEmpty()) {
			return "ID를 입력하세요";
		}
		
		if (!users.getIsduplicateIDCheck()) {			
			return "ID 중복 체크를 해주세요.";
		}
		
		if (userDAO.duplicateIDCheck(users.getUserid())> 0) {
			return "이미 사용 중인 ID입니다.";
		}
		
		if (users.getUserpw() == null || users.getUserpw().isEmpty()) {
			return "비밀번호를 입력하세요.";
		}
		
		if (users.getConfirmPw() == null || users.getConfirmPw().isEmpty()) {
			return "비밀번호 확인을 입력하세요.";
		}
		
		if (!users.getUserpw().equals(users.getConfirmPw())) {
            return "비밀번호가 일치하지 않습니다.";
        }
		
		if (users.getNickname() == null || users.getNickname().isEmpty()) {
			return "닉네임을 입력하세요.";
		}
		
		if (!users.getIsduplicateNickname()) {
			return "닉네임 중복체크를 해주십시오.";
		}

        if (userDAO.duplicateNickname(users.getNickname())> 0) {
            return "이미 사용 중인 닉네임입니다.";
        }
        
        if (users.getUseremail() == null || users.getUseremail().isEmpty()) {
        	return "이메일을 입력하세요.";
        }
        
        if (!users.getIsduplicateEmail()) {
        	return "이메일 중복체크를 해주십시오.";
        }
        
        if (userDAO.duplicateEmail(users.getUseremail()) > 0) {
            return "이미 사용 중인 이메일입니다.";
        }
  
        if (users.getUsertel() == null || users.getUsertel().isEmpty()) {
            return "전화번호를 입력하세요.";
        }
        
        if (users.getUserbirth() == null) {
        	return "생년월일을 입력하세요.";
        }
		
		return null;
	}

	@Override
	public void insertJoin(Users users) {
		userDAO.insertJoin(users);
		userDAO.insertUserLoginState(users.getUserid());
	}

	@Override
	public boolean duplicateID(String userid) {
		return userDAO.duplicateIDCheck(userid) > 0;
	}

	@Override
	public boolean duplicateNickname(String nickname) {
		return userDAO.duplicateNickname(nickname) > 0;
	}

	@Override
	public boolean duplicateEmail(String useremail) {
		return userDAO.duplicateEmail(useremail) > 0;
	}

	@Override
	public Users login(String userid, String userpw) {
		Users user = userDAO.findByUserid(userid);
		if(user != null && user.getUserpw().equals(userpw)) {
			return user;
		}
		return null;
	}

}
