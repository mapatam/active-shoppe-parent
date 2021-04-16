package za.co.momentum.service.persistence;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * @author Lehlogonolo Sekgonyane<lehlogonolo.sekgonyane@altron.com>
 *
 * Table naming strategy that will prefix table names with &quot;tbl_&quot; and remove &quot;entity&quot; from
 * the end of the name. e.g. a class named &quot;PersonEntity&quot; will become a table named &quot;tbl_person&quot;.
 */
public class CustomPhysicalNamingStrategy extends SpringPhysicalNamingStrategy {

	public static final String ENTITY = "_entity";

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
		Identifier identifier = super.toPhysicalTableName(name, jdbcEnvironment);
		return Identifier.toIdentifier(classToTableName(identifier.getText()), identifier.isQuoted());
	}

	String classToTableName(String entityTableName) {
		String improvedTableName = entityTableName;
		int indexOf = entityTableName.lastIndexOf('_');
		if (indexOf > 0) {
			if (ENTITY.equals(entityTableName.substring(indexOf))) {
				improvedTableName = entityTableName.substring(0, indexOf);
			}
		}
		return "tbl_" + improvedTableName;
	}

}
