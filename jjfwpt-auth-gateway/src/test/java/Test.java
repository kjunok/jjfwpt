import org.springframework.security.crypto.factory.PasswordEncoderFactories;

public class Test {
    public static void main(String[] args) {
       String encodePassword = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456");
        System.out.println(encodePassword);
    }
}
