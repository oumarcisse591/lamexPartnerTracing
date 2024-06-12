package com.slabtech.lamexPartnerTracing.service;


import com.slabtech.lamexPartnerTracing.dao.RoleDao;
import com.slabtech.lamexPartnerTracing.dao.UserDao;
import com.slabtech.lamexPartnerTracing.entity.Role;
import com.slabtech.lamexPartnerTracing.entity.User;
import com.slabtech.lamexPartnerTracing.repository.UserRepository;
import com.slabtech.lamexPartnerTracing.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private UserDao userDao;
    private RoleDao roleDao;

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, UserRepository userRepository){
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUserName(username);
        if (user==null){
            throw new UsernameNotFoundException("Identifiant ou mot de passe érroné");
        }
//        return new CustomUserDetails(user);
        return new CustomUserDetails(user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()), user.getName());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user != null) {
            return user.getId();
        } else {
            throw new UsernameNotFoundException("Utilisateur non trouvé pour le nom d'utilisateur: " + username);
        }
    }

}

