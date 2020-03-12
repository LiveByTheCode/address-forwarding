package us.livebythecode.architecture.referencemodels.testing.exception;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ApiError implements Serializable{
	 
	private static final long serialVersionUID = 3153715598829476288L;
	private String timestamp;
    private int status;
    private String error;
    private String message;
 
    public ApiError(String timestamp, int status, String error, String message) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }
}