package com.example.projectappchat.service.userDetails;


import com.example.projectappchat.entity.User;
import com.example.projectappchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userAccount) throws UsernameNotFoundException {

        User user = this.userRepository.findUserAccount(userAccount);

        if (user == null) {
            throw new UsernameNotFoundException("User " + userAccount
                    + " was not found in the database");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
// Thay thế kiểm tra roleNames != null bằng cách trực tiếp tạo quyền (authority) mặc định nếu không sử dụng roleRepository
        GrantedAuthority defaultAuthority = new SimpleGrantedAuthority("DEFAULT_ROLE");
        grantedAuthorityList.add(defaultAuthority);

        UserDetails userDetails = (UserDetails) new
                org.springframework.security.core.userdetails.User(user.getUserAccount(),
                user.getUserPassword(), grantedAuthorityList);

        return userDetails;
    }
}
