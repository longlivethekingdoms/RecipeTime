package com.recipetime.find.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recipetime.find.DAO.UserDAO;
import com.recipetime.find.Model.Users;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;

	@Override
	public String validateUser(Users users) {
		
		if (users.getUserid() == null || users.getUserid().isEmpty()) {
			return "ID�� �Է��ϼ���";
		}
		
		if (!users.getIsduplicateIDCheck()) {			
			return "ID �ߺ� üũ�� ���ֽʽÿ�.";
		}
		
		if (userDAO.duplicateIDCheck(users.getUserid())> 0) {
			return "�̹� ��� ���� ID�Դϴ�.";
		}
		
		if (users.getUserpw() == null || users.getUserpw().isEmpty()) {
			return "��й�ȣ�� �Է��ϼ���.";
		}
		
		if (users.getConfirmPw() == null || users.getConfirmPw().isEmpty()) {
			return "��й�ȣ Ȯ���� �Է��ϼ���.";
		}
		
		if (!users.getUserpw().equals(users.getConfirmPw())) {
            return "��й�ȣ�� ��ġ���� �ʽ��ϴ�.";
        }
		
		if (users.getNickname() == null || users.getNickname().isEmpty()) {
			return "�г����� �Է��ϼ���.";
		}
		
		if (!users.getIsduplicateNickname()) {
			return "�г��� �ߺ�üũ�� ���ֽʽÿ�.";
		}

        if (userDAO.duplicateNickname(users.getNickname())> 0) {
            return "�̹� ��� ���� �г����Դϴ�.";
        }
        
        if (users.getUseremail() == null || users.getUseremail().isEmpty()) {
        	return "�̸����� �Է��ϼ���.";
        }
        
        if (!users.getIsduplicateEmail()) {
        	return "�̸��� �ߺ�üũ�� ���ֽʽÿ�.";
        }
        
        if (userDAO.duplicateEmail(users.getUseremail()) > 0) {
            return "�̹� ��� ���� �̸����Դϴ�.";
        }
  
        if (users.getUserTel() == null || users.getUserTel().isEmpty()) {
            return "��ȭ��ȣ�� �Է��ϼ���.";
        }
        
        if (users.getUserBirth() == null) {
        	return "��������� �Է��ϼ���.";
        }
		
		return null;
	}

	@Override
	public void insertJoin(Users users) {
		userDAO.insertJoin(users);
	}

	@Override
	public void insertUserAdmin(Users users) {
		// TODO Auto-generated method stub
		
	}
}
