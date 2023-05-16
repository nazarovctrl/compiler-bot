package uz.katkit.service;


import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import uz.katkit.entity.ProfileEntity;
import uz.katkit.repository.ProfileRepository;

import java.util.List;

@Service
public class ProfileService {

    private final ProfileRepository repository;

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }




}
