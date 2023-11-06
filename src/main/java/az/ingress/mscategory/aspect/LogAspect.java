package az.ingress.mscategory.aspect;

import az.ingress.mscategory.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    @Around(value = "within(@az.ingress.mscategory.annotation.Log *)")
    public Object logAspect(ProceedingJoinPoint point) throws Throwable {
        var signature = (MethodSignature) point.getSignature();
        var logger = LoggerFactory.getLogger(point.getTarget().getClass());
        var args = point.getArgs();
        var argsSB = buildArgs(signature, args);
        log(logger, signature, argsSB);
        Object response;
        try {
            response = point.proceed();
        }
        catch (Throwable throwable) {
            log(logger, signature, argsSB, throwable);
            throw throwable;
        }
        log(logger, signature, argsSB, response);
        return response;
    }

    private StringBuilder buildArgs(MethodSignature signature, Object[] args) {
        var sb = new StringBuilder();
        var params = signature.getMethod().getParameters();
        for (var i = 0; i < params.length; i++) {
            var param = params[i];
            if (param.isAnnotationPresent(Log.Exclude.class)) continue;
            sb
                    .append(" | ")
                    .append(param.getName())
                    .append(" - ")
                    .append(objectToString(args[i]));
        }
        return sb;
    }

    private String objectToString(Object arg) {
        return arg.toString();
    }

    private void log(Logger logger, MethodSignature signature, StringBuilder args) {
        logger.info("ActionLog.{}.start{}", signature.getName(), args);
    }

    private void log(Logger logger, MethodSignature signature, StringBuilder args, Throwable throwable) {
        logger.error("ActionLog.{}.error{} -> {} - {}", signature.getName(), args, throwable.getClass().getName(), throwable.getMessage());
    }

    private void log(Logger logger, MethodSignature signature, StringBuilder args, Object response) {
        if (void.class.equals(signature.getReturnType())) logger.info("ActionLog.{}.end{}", signature.getName(), args);
        else {
            try {
                logger.info("ActionLog.{}.end{} -> {}", signature.getName(), args, response);
            } catch (Exception e) {
                logger.info("ActionLog.{}.end{}", signature.getName(), args);
            }
        }
    }
}