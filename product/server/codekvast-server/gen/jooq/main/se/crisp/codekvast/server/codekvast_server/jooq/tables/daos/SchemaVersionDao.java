/**
 * This class is generated by jOOQ
 */
package se.crisp.codekvast.server.codekvast_server.jooq.tables.daos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SchemaVersionDao extends org.jooq.impl.DAOImpl<se.crisp.codekvast.server.codekvast_server.jooq.tables.records.SchemaVersionRecord, se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion, java.lang.String> {

	/**
	 * Create a new SchemaVersionDao without any configuration
	 */
	public SchemaVersionDao() {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION, se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion.class);
	}

	/**
	 * Create a new SchemaVersionDao with an attached configuration
	 */
	public SchemaVersionDao(org.jooq.Configuration configuration) {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION, se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected java.lang.String getId(se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion object) {
		return object.getVersion();
	}

	/**
	 * Fetch records that have <code>version_rank IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByVersionRank(java.lang.Integer... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.VERSION_RANK, values);
	}

	/**
	 * Fetch records that have <code>installed_rank IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByInstalledRank(java.lang.Integer... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.INSTALLED_RANK, values);
	}

	/**
	 * Fetch records that have <code>version IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByVersion(java.lang.String... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.VERSION, values);
	}

	/**
	 * Fetch a unique record that has <code>version = value</code>
	 */
	public se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion fetchOneByVersion(java.lang.String value) {
		return fetchOne(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.VERSION, value);
	}

	/**
	 * Fetch records that have <code>description IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByDescription(java.lang.String... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.DESCRIPTION, values);
	}

	/**
	 * Fetch records that have <code>type IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByType(java.lang.String... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.TYPE, values);
	}

	/**
	 * Fetch records that have <code>script IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByScript(java.lang.String... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.SCRIPT, values);
	}

	/**
	 * Fetch records that have <code>checksum IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByChecksum(java.lang.Integer... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.CHECKSUM, values);
	}

	/**
	 * Fetch records that have <code>installed_by IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByInstalledBy(java.lang.String... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.INSTALLED_BY, values);
	}

	/**
	 * Fetch records that have <code>installed_on IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByInstalledOn(java.sql.Timestamp... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.INSTALLED_ON, values);
	}

	/**
	 * Fetch records that have <code>execution_time IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchByExecutionTime(java.lang.Integer... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.EXECUTION_TIME, values);
	}

	/**
	 * Fetch records that have <code>success IN (values)</code>
	 */
	public java.util.List<se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos.SchemaVersion> fetchBySuccess(java.lang.Boolean... values) {
		return fetch(se.crisp.codekvast.server.codekvast_server.jooq.tables.SchemaVersion.SCHEMA_VERSION.SUCCESS, values);
	}
}
