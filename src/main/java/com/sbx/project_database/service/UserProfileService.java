package com.sbx.project_database.service;

import com.sbx.project_database.models.UserProfile;
import com.sbx.project_database.repo.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfRep;

    public byte[] getImageByUserId(int id){ //todo I want this to return null when that column does not exist
        System.out.println(" We are inside get image by User id, inside the service layer ");
        System.out.println("User id = " + id);
        try{
            UserProfile prof =   userProfRep.findById(id).get();
            return prof.getPhoto();

        }catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

}
