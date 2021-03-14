package app.services;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class SkuGenerator extends SequenceStyleGenerator{

    public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
    public static final String VALUE_PREFIX_DEFAULT = "";
    private String valuePrefix;

    
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        Connection connection = session.connection();
        try {

            PreparedStatement ps = connection
                    .prepareStatement("SELECT MAX(id) as value from product");

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String sku = rs.getString("value");
                String separator ="-";
                int sepPos = sku.indexOf(separator);
                if (sepPos == -1) {
                    System.out.println("");
                }
                String idSub = sku.substring(sepPos + separator.length());
                int idNum = Integer.parseInt(idSub) + 1;
                System.out.println("Generated Stock Code: BASICS-" + idNum);
                return "BASICS-" + idNum;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void configure(Type type, Properties params,
                          ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER,
                params, VALUE_PREFIX_DEFAULT);
    }
}