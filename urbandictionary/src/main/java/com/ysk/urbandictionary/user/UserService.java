package com.ysk.urbandictionary.user;

import com.ysk.urbandictionary.error.NotFoundException;
import com.ysk.urbandictionary.file.FileService;
import com.ysk.urbandictionary.user.dtos.UserUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    FileService fileService;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,FileService fileService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileService=fileService;
    }

    public void save(User user) {
        String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

    }
    public Page<User> getAllUsers(Pageable page, User user) {                                   //sayfalamayla çağırıcaz mesela hangi sayfa ve ve o sayfada kaç item olacak. pageable page yapısını application.yaml da kontrol edicez. sayfa sınırlarını springe bıraktık. page ve size diyerek url içinde kullanarak ayarını yapıcaz.
        if (user!= null){
            return userRepository.findByUsernameNot(user.getUsername(),page);                   //eğer login olunmuşsa o login oluncan userin olmadığı bir liste getirsin istedik.
        }
        return userRepository.findAll(page);
    }

    public User getByUsername(String username) {
        User inDatabase= userRepository.findByUsername(username);
        if(inDatabase==null){
            throw new NotFoundException();
        }
        return inDatabase;
    }

    public User updateUser(String username, UserUpdateDto updatedUser) {
        User inDatabase= getByUsername(username);
        inDatabase.setDisplayName(updatedUser.getDisplayName());
        if(updatedUser.getImage() != null){
            String oldImageName = inDatabase.getImage(); //eski image i silmek için.
            try {
                String storedFileName = fileService.writeBase64EncodedStringToFile(updatedUser.getImage());
                inDatabase.setImage(storedFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            fileService.deleteFile(oldImageName);
        }
        return userRepository.save(inDatabase);
    }

}





















   /* public Page<User> getAllUsers(int currentPage,int pageSize) {
        Pageable page=PageRequest.of(currentPage,pageSize);                //hangi sayfa ve o sayfada kaç item olacak.
        return userRepository.findAll(page);
    }*/