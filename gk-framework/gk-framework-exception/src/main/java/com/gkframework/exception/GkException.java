
package com.gkframework.exception;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author moueimei
 * @version 1.0
 */
public class GkException extends RuntimeException {

	private static final long serialVersionUID = -4379801359412979859L;

	@XmlAttribute
    protected String code;

    @XmlElement
    protected String message;

    @XmlElement
    protected String solution;


    public GkException() {
    }

    public GkException(Throwable throwable) {
    	super(throwable);
    }

    public GkException(String message, Throwable throwable) {
    	super(message, throwable);
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

}

