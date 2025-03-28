package tn.esprit.profile_service.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import tn.esprit.profile_service.Dto.ProfileResponse;
import tn.esprit.profile_service.Dto.ProfileResquest;

import tn.esprit.profile_service.service.ProfileService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class ProfileController {
   ProfileService profileService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<?> add(
           @Valid @RequestBody ProfileResquest profile,
            @PathVariable String userId
    ){
        System.out.println("Reçu: " + profile);
        return ResponseEntity.ok(profileService.addProfile(profile,userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateProfile(
         @Valid   @RequestBody  ProfileResquest request,
            @PathVariable String userId
    ){
        return ResponseEntity.ok(profileService.updateProfile(request,userId));

    }

    @GetMapping("/find-id/{userId}")
    public ResponseEntity<?> getProfileByIdUser(
          @PathVariable  String userId
    ){
         return ResponseEntity.ok(profileService.getProfileByIdUser(userId)) ;
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProfile(String userId){
        boolean checkDelete = profileService.deleteProfile(userId);
        if(checkDelete) return ResponseEntity.ok("deleted with success");
        return ResponseEntity.notFound().build();
    }


    @PostMapping(value = "/picture/{userId}",consumes = "multipart/form-data")
    public ResponseEntity<?> uploadProfilePicture(
         @RequestPart("file")     MultipartFile file,
          @PathVariable  String userId
    ){
        profileService.uploadProfilePicture(file,userId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllProfile(){
        return ResponseEntity.ok(profileService.getAllProfile());
    }
}
