package com.ysk.urbandictionary.entry;

import com.ysk.urbandictionary.entry.dtos.EntrySubmitDto;
import com.ysk.urbandictionary.file.FileAttachment;
import com.ysk.urbandictionary.file.FileAttachmentRepository;
import com.ysk.urbandictionary.file.FileService;
import com.ysk.urbandictionary.user.User;
import com.ysk.urbandictionary.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EntryService {

    EntryRepository entryRepository;
    UserService userService;
    FileAttachmentRepository fileAttachmentRepository;

    FileService fileService;

    public EntryService(EntryRepository entryRepository, UserService userService, FileAttachmentRepository fileAttachmentRepository, FileService fileService) {
        this.entryRepository = entryRepository;
        this.userService = userService;
        this.fileAttachmentRepository = fileAttachmentRepository;
        this.fileService = fileService;
    }

    public void saveEntry(EntrySubmitDto entrySubmitDto, User user) {
        Entry entry = new Entry();
        entry.setWord(entrySubmitDto.getWord());
        entry.setDefinition(entrySubmitDto.getDefinition());
        entry.setSentence(entrySubmitDto.getSentence());
        entry.setTimestamp(new Date());  //db ye kaydetmeden önce şuanın zamanını girdik.
        entry.setUser(user);
        entryRepository.save(entry);
        if(entrySubmitDto.getAttachmentId() == null) {
            // elimizde attachmentId yoksa burdan dönebiliriz
            return;
        }
        Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(entrySubmitDto.getAttachmentId());
        if(optionalFileAttachment.isPresent()){  //eğer böyle birşey varsa.
            FileAttachment fileAttachment = optionalFileAttachment.get();
            fileAttachment.setEntry(entry); //attachment ile entry i ilişkilendirdim.
            fileAttachmentRepository.save(fileAttachment);
        }
    }

    public Page<Entry> getEntries(Pageable page) {
        return entryRepository.findAll(page);
    }

    public Page<Entry> getEntriesByUsername(String username, Pageable page) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.findByUser(inDatabase,page);
    }

    public Page<Entry> getOldEntries(Long id, String username, Pageable page) {
        Specification<Entry> specIdLessThan = idLessThan(id);
        if(username!=null){                                                                                                                 //elimizde id ve username var ise username i db den alalım ve gerekli sorguyu usera göre oluşturalım.
            User inDatabase = userService.getByUsername(username);
            Specification<Entry> specUserIs = userIs(inDatabase);
            Specification<Entry> specFindByIdLessThanAndUser = specIdLessThan.and(specUserIs);                                              //findByIdLessThanAndUser bu işlemi parça parça yaptık. Hem id si küçük olanlar olacak ve o usera ait olanlar getirilir.
            return entryRepository.findAll(specFindByIdLessThanAndUser,page);                                                               //entryRepository.findByIdLessThanAndUser(id,inDatabase,page);
        }
        return entryRepository.findAll(specIdLessThan,page);                                                                                //return entryRepository.findByIdLessThan(id,page);
    }

    public long getNewEntriesCount(Long id, String username) {
        Specification<Entry> specification = idGreaterThan(id);
        if(username != null){
            User inDatabase = userService.getByUsername(username);
            specification = specification.and(userIs(inDatabase));                                                                              //üstteki getOldEntries de yazdığımız metodun kısa yazılmış halidir aslında olay mantığı yine aynıdır.
        }
        return entryRepository.count(specification);                                                                                            //findall olduğu gibi count içinde specificationa özel özellik vardır.
    }

    public List<Entry> getNewEntries(Long id, String username, Sort sort) {
        Specification<Entry> specification = idGreaterThan(id);
        if(username != null){
            User inDatabase = userService.getByUsername(username);
            specification = specification.and(userIs(inDatabase));                                                                          //entryRepository.findByIdGreaterThanAndUser(id,inDatabase,sort);
        }
        return entryRepository.findAll(specification,sort);                                                                                 // entryRepository.findByIdGreaterThan(id,sort);
    }

    //birden fazla filtreleme senaryoları olduğunda specification kullanarak parçalama işlemi uygulayabiliriz yani dinamik parametreli queryler oluşturabiliriz. Diğer halde EntryRepository çok şişecekti yönetilemez hala gelebilirdi.
    Specification<Entry> idLessThan(Long id){                                                                                        //specification, yaratılan sorgunun içerisindeki parametrik kısımları oluşturan parçalardan biri. Sorguları parçalamamızı sağladı. Dinamik olarak query oluşturmamızı sağlar. Repository metodlarını EntryService içinde specification aracılığıyla dinamik olarak ürettik.
        return new Specification<Entry>() {
            @Override
            public Predicate toPredicate(Root<Entry> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.lessThan(root.get("id"),id);                                                                 //birinci parametre hangi sütünü referans alacağı. İkinci parametre bizim verdiğimiz id.
            }
        };
    }
    Specification<Entry> userIs(User user ){
        return (root,query,criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("user"),user); //bizim verdiğimiz user ile entry deki user objecti eşit mi onun sorgusu.
        };
    }

    Specification<Entry> idGreaterThan(Long id){
        return (root,query,criteriaBuilder) -> {
            return criteriaBuilder.greaterThan(root.get("id"),id);
        };
    }

    public void deleteEntry(Long id) {
        Entry inDatabase = entryRepository.getReferenceById(id);
        if(inDatabase.getFileAttachment() != null){
            String fileName = inDatabase.getFileAttachment().getName();
            fileService.deleteAttachmentFile(fileName);  //attachmenti da dosyadan siliyoruz.
        }
        entryRepository.deleteById(id);
    }
}




/*
   public Page<Entry> findByIdLessThanAndUser(Long id, String username, Pageable page) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.findByIdLessThanAndUser(id,inDatabase,page);
   }

   public long getNewEntriesCountOfUser(Long id, String username) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.countByIdGreaterThanAndUser(id,inDatabase);
    }

    public List<Entry> getNewEntriesOfUser(Long id, String username, Sort sort) {
        User inDatabase = userService.getByUsername(username);
        return entryRepository.findByIdGreaterThanAndUser(id,inDatabase,sort);
    }

    public List<Entry> getNewEntries(Long id, String username, Sort sort) {
        if(username != null){
            User inDatabase = userService.getByUsername(username);
            return entryRepository.findByIdGreaterThanAndUser(id,inDatabase,sort);
        }
        return entryRepository.findByIdGreaterThan(id,sort);
    }

    public List<Entry> getNewEntries(Long id, String username, Sort sort) {
        if(username != null){
            User inDatabase = userService.getByUsername(username);
            return entryRepository.findByIdGreaterThanAndUser(id,inDatabase,sort);
        }
        return entryRepository.findByIdGreaterThan(id,sort);
    }

*/