/**
 * This class is generated by jOOQ
 */
package se.crisp.codekvast.server.codekvast_server.jooq.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EnvironmentHostnamesRecord extends org.jooq.impl.UpdatableRecordImpl<se.crisp.codekvast.server.codekvast_server.jooq.tables.records.EnvironmentHostnamesRecord> implements org.jooq.Record4<java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String> {

	private static final long serialVersionUID = 943340594;

	/**
	 * Setter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.ID</code>.
	 */
	public EnvironmentHostnamesRecord setId(java.lang.Long value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.ID</code>.
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.ORGANISATION_ID</code>.
	 */
	public EnvironmentHostnamesRecord setOrganisationId(java.lang.Long value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.ORGANISATION_ID</code>.
	 */
	public java.lang.Long getOrganisationId() {
		return (java.lang.Long) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.ENVIRONMENT_ID</code>.
	 */
	public EnvironmentHostnamesRecord setEnvironmentId(java.lang.Long value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.ENVIRONMENT_ID</code>.
	 */
	public java.lang.Long getEnvironmentId() {
		return (java.lang.Long) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.HOST_NAME</code>.
	 */
	public EnvironmentHostnamesRecord setHostName(java.lang.String value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ENVIRONMENT_HOSTNAMES.HOST_NAME</code>.
	 */
	public java.lang.String getHostName() {
		return (java.lang.String) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Long> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.Long, java.lang.Long, java.lang.String> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.EnvironmentHostnames.ENVIRONMENT_HOSTNAMES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field2() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.EnvironmentHostnames.ENVIRONMENT_HOSTNAMES.ORGANISATION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field3() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.EnvironmentHostnames.ENVIRONMENT_HOSTNAMES.ENVIRONMENT_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.EnvironmentHostnames.ENVIRONMENT_HOSTNAMES.HOST_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value2() {
		return getOrganisationId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value3() {
		return getEnvironmentId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getHostName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentHostnamesRecord value1(java.lang.Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentHostnamesRecord value2(java.lang.Long value) {
		setOrganisationId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentHostnamesRecord value3(java.lang.Long value) {
		setEnvironmentId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentHostnamesRecord value4(java.lang.String value) {
		setHostName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnvironmentHostnamesRecord values(java.lang.Long value1, java.lang.Long value2, java.lang.Long value3, java.lang.String value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached EnvironmentHostnamesRecord
	 */
	public EnvironmentHostnamesRecord() {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.EnvironmentHostnames.ENVIRONMENT_HOSTNAMES);
	}

	/**
	 * Create a detached, initialised EnvironmentHostnamesRecord
	 */
	public EnvironmentHostnamesRecord(java.lang.Long id, java.lang.Long organisationId, java.lang.Long environmentId, java.lang.String hostName) {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.EnvironmentHostnames.ENVIRONMENT_HOSTNAMES);

		setValue(0, id);
		setValue(1, organisationId);
		setValue(2, environmentId);
		setValue(3, hostName);
	}
}
