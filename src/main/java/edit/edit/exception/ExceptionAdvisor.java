package edit.edit.exception;

import edit.edit.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionAdvisor {

    /**
     * Valid 예외 처리
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity signValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("] ");
            builder.append(fieldError.getDefaultMessage());
        }
        return new ResponseEntity(ResponseDto.setBadRequest(builder.toString()), HttpStatus.BAD_REQUEST);
    }

    /**
     *  예외
     */
    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity exceptionHandler(Exception exception) {
        String message = exception.getMessage();
        ResponseDto responseDto = ResponseDto.setBadRequest(message);
        return new ResponseEntity(responseDto, HttpStatus.BAD_REQUEST);
    }
}
