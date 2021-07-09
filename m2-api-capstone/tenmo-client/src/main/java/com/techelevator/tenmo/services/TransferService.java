package com.techelevator.tenmo.services;

import com.techelevator.tenmo.auth.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Random;

public class TransferService {

    private String BASE_URL;
    private AuthenticatedUser currentUser;
    private RestTemplate restTemplate = new RestTemplate();
    private ConsoleService console;

    public TransferService(AuthenticatedUser currentUser, String BASE_URL){
        this.currentUser = currentUser;
        this.BASE_URL = BASE_URL;
    }


    //I think where we went wrong was mainly having everything we need for the deserialization and
    //serialization of JSON. The body doesn't match what we are actually asking for.

    //Additionally, I was confused if we retrieve information from this, if we could update account
    //one, and account two. Or if these are three totally separate requests.

    //Do the parameters in this createTransfer need to match the parameters on the Server-side?
    public Transfer createTransfer(Transfer transfer){

        HttpHeaders header = new HttpHeaders();

        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(currentUser.getToken());

        HttpEntity<Transfer> entity = new HttpEntity<Transfer>(transfer, header);

        try{
            restTemplate.exchange(BASE_URL + "transfers", HttpMethod.POST, entity, Transfer.class).getBody();
        } catch (ResourceAccessException e){
            console.errorCannotConnect();
        } catch (RestClientResponseException e){
            System.out.println(e.getRawStatusCode() + " " + e.getStatusText());
        }
        return transfer;
    }

    public Transfer getATransferById(int id){
        Transfer transfer = null;
        String endpoint = BASE_URL + "transfers/" + id;
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(header);

        try{
            transfer = restTemplate.exchange(endpoint, HttpMethod.GET, entity, Transfer.class).getBody();
        } catch (ResourceAccessException e){
            //How would I print to the console without printing directly?
            console.errorCannotConnect();
        } catch (RestClientResponseException e){
            //in examples we used console.PrintError()
            System.out.println(e.getRawStatusCode() + " " + e.getStatusText());
        }
        return transfer;
    }

    //Stole the code below from elsewhere in the capstone. Went down a silly rabbit hold here.
    private Transfer makeTransfer(String CSV) {
        String[] parsed = CSV.split(",");

        // invalid input (
        if (parsed.length < 3 || parsed.length > 4) {
            return null;
        }

        // Add method does not include an id and only has 5 strings
        if (parsed.length == 3) {
            // Create a string version of the id and place into an array to be concatenated
            String[] withId = new String[6];
            String[] idArray = new String[] { new Random().nextInt(1000) + "" };
            // place the id into the first position of the data array
            System.arraycopy(idArray, 0, withId, 0, 1);
            System.arraycopy(parsed, 0, withId, 1, 3);
            parsed = withId;
        }

        return new Transfer(Integer.parseInt(parsed[0].trim()), Integer.parseInt(parsed[1].trim()), Double.parseDouble(parsed[2].trim()));
    }
}
