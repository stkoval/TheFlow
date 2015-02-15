/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

/**
 *
 * @author Stas
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

    public EmailExistsException(String message) {
        super(message);
    }
}
