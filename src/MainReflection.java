import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume = new Resume("uuid01", "Иванов Иван Иванович");
        Class<? extends Resume> aClass = resume.getClass();
        Method toString = aClass.getMethod("toString");
        Object result = toString.invoke(resume);
        System.out.println(result);
    }
}
