/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

/**
 *
 * @author Stanislav
 */
public class UsernameDuplicationException extends Exception{
    public UsernameDuplicationException(String message) {
        super(message);
    }
}
