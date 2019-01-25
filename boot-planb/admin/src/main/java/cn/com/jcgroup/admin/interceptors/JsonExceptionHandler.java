package cn.com.jcgroup.admin.interceptors;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.constant.ResCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 异常拦截器，作用于所有jc.it.rest包下的Controller
 * 覆盖@ExceptionHandler默认返回的错误信息
 *
 * @author LiuYong
 */
@RestControllerAdvice(basePackages = "cn.com.jcgroup.admin")
public class JsonExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(JsonExceptionHandler.class);

    //是否返回前端详细错误信息
    @Value("${response.error.debug}")
    private boolean responseErrorDebug;

    /**
     * 参数校验
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResult> handleValidationFailure(HttpServletRequest request, ConstraintViolationException ex) throws Exception {
        StringBuilder errorMsg = new StringBuilder();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            String msg = String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                    constraintViolation.getInvalidValue(), constraintViolation.getMessage());
            errorMsg.append(msg + ";");
        }
        LOG.error("[参数校验非法]请求路径:{}，错误信息为:{}", request.getRequestURI(), errorMsg.toString());
        ApiResult apiResult;
        if (responseErrorDebug) {
            apiResult = new ApiResult(ResCodeEnum.INVALID_PARAM, errorMsg.toString());
        } else {
            apiResult = new ApiResult(ResCodeEnum.INVALID_PARAM, null);
        }
        return new ResponseEntity<ApiResult>(apiResult, HttpStatus.OK);
    }

    /**
     * org.springframework.web.bind.MethodArgumentNotValidException
     * 参数绑定校验
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> errorList = bindingResult.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError:errorList) {
            String resultMsg = String.format("参数名:%s , 错误信息:%s ;", fieldError.getField(), fieldError.getDefaultMessage());
            sb.append(resultMsg);
        }
        LOG.error("[参数绑定校验错误]请求路径:{},错误信息:{}", request.getContextPath(), sb.toString());
        ApiResult apiResult;
        if (responseErrorDebug) {
            apiResult = new ApiResult(ResCodeEnum.INVALID_PARAM, sb.toString());
        } else {
            apiResult = new ApiResult(ResCodeEnum.INVALID_PARAM, null);
        }
        return new ResponseEntity<Object>(apiResult, HttpStatus.OK);
    }

    /**
     * 异常通用处理
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResult> handleException(Exception ex) throws Exception {
        LOG.error("[异常处理]错误信息为:", ex);
        return new ResponseEntity<ApiResult>(new ApiResult(ResCodeEnum.SERVER_ERROR, null), HttpStatus.OK);
    }

}
