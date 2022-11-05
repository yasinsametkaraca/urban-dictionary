package com.ysk.urbandictionary.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(User user) {
        String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

    }
    public Page<UserProjection> getAllUsers(Pageable page) {                                   //sayfalamayla çağırıcaz mesela hangi sayfa ve ve o sayfada kaç item olacak. pageable page yapısını application.yaml da kontrol edicez. sayfa sınırlarını springe bıraktık. page ve size diyerek url içinde kullanarak ayarını yapıcaz.
        return userRepository.getAllUsersProjection(page);
    }


}


   /* public Page<User> getAllUsers(int currentPage,int pageSize) {
        Pageable page=PageRequest.of(currentPage,pageSize);                //hangi sayfa ve o sayfada kaç item olacak.
        return userRepository.findAll(page);
    }*/