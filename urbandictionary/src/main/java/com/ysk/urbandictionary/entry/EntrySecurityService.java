package com.ysk.urbandictionary.entry;


import com.ysk.urbandictionary.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "entrySecurity")
public class EntrySecurityService {

    @Autowired
    EntryRepository entryRepository;

    public boolean isAllowedToDelete(Long id, User loggedInUser){
        Optional<Entry> optionalEntry = entryRepository.findById(id);
        if(!optionalEntry.isPresent()){  //eğer o entry yoksa.
            return false;
        }
        Entry entry = optionalEntry.get();
        if(entry.getUser().getId() != loggedInUser.getId()){
            return false;
        }
        return true;
    }

}
