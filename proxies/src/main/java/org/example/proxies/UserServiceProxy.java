package org.example.proxies;

import org.example.domain.User;
import org.example.interfaces.UserServiceInterface;
import org.example.utils.Request;
import org.example.utils.Response;
import org.example.requests.user.GetUserRequest;
import org.example.responses.NotFoundResponse;
import org.example.responses.user.SuccessAuthenticationResponse;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class UserServiceProxy implements UserServiceInterface {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public UserServiceProxy(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException {
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    @Override
    public User getByUsername(String username) throws SQLException, IOException {
        GetUserRequest request = new GetUserRequest(username);
        this.sendRequest(request);
        Response response = this.getResponse();
        if (response instanceof SuccessAuthenticationResponse) {
            return ((SuccessAuthenticationResponse) response).getUser();
        }
        if (response instanceof NotFoundResponse) {
            return null;
        }
        throw new IOException("Error in getting user");
    }
    private void sendRequest(Request request){
        try{
            out.writeObject(request);
            out.flush();
        }
        catch (IOException e){
            System.out.println("Error in sending request");
        }
    }
    private Response getResponse(){
        Response response = null;
        try{
            response = (Response) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Error in reading response");
        }
        return response;
    }
}
