package com.techelevator.tenmo.services;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.auth.models.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserService {

    private String BASE_URL;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();

    public UserService(AuthenticatedUser currentUser , String  BASE_URL){
        this.currentUser = currentUser;
        this.BASE_URL = BASE_URL;
    }

    public List<User> getAllUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(headers);
        User[] users = restTemplate.exchange(BASE_URL + "users" , HttpMethod.GET , entity ,  User[].class).getBody();

        return Arrays.asList(users);
    }
}
