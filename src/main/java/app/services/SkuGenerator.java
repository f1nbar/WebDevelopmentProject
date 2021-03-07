package app.services;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

import app.entities.Product;

import java.io.Serializable;

public class SkuGenerator extends UUIDGenerator {


    public Serializable generate(SharedSessionContractImplementor session, Object obj, Product product) throws HibernateException {

        String uuid = super.generate(session, obj).toString();
        String initials = product.getProductName();
        char c;
        for (int i = 0; i < initials.length(); i++) {
            c = initials.charAt(i);
            initials += Character.isUpperCase(c) ? c + " " : "";
        }
        return initials + uuid;
    }
}
