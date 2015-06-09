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
public class OrganisationsRecord extends org.jooq.impl.UpdatableRecordImpl<se.crisp.codekvast.server.codekvast_server.jooq.tables.records.OrganisationsRecord> implements org.jooq.Record4<java.lang.Long, java.lang.String, java.sql.Timestamp, java.sql.Timestamp> {

	private static final long serialVersionUID = 44753519;

	/**
	 * Setter for <code>PUBLIC.ORGANISATIONS.ID</code>.
	 */
	public OrganisationsRecord setId(java.lang.Long value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ORGANISATIONS.ID</code>.
	 */
	public java.lang.Long getId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.ORGANISATIONS.NAME</code>.
	 */
	public OrganisationsRecord setName(java.lang.String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ORGANISATIONS.NAME</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.ORGANISATIONS.CREATED_AT</code>.
	 */
	public OrganisationsRecord setCreatedAt(java.sql.Timestamp value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ORGANISATIONS.CREATED_AT</code>.
	 */
	public java.sql.Timestamp getCreatedAt() {
		return (java.sql.Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.ORGANISATIONS.MODIFIED_AT</code>.
	 */
	public OrganisationsRecord setModifiedAt(java.sql.Timestamp value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.ORGANISATIONS.MODIFIED_AT</code>.
	 */
	public java.sql.Timestamp getModifiedAt() {
		return (java.sql.Timestamp) getValue(3);
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
	public org.jooq.Row4<java.lang.Long, java.lang.String, java.sql.Timestamp, java.sql.Timestamp> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Long, java.lang.String, java.sql.Timestamp, java.sql.Timestamp> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.Organisations.ORGANISATIONS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.Organisations.ORGANISATIONS.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.Organisations.ORGANISATIONS.CREATED_AT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field4() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.Organisations.ORGANISATIONS.MODIFIED_AT;
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
	public java.lang.String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value3() {
		return getCreatedAt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.sql.Timestamp value4() {
		return getModifiedAt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrganisationsRecord value1(java.lang.Long value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrganisationsRecord value2(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrganisationsRecord value3(java.sql.Timestamp value) {
		setCreatedAt(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrganisationsRecord value4(java.sql.Timestamp value) {
		setModifiedAt(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrganisationsRecord values(java.lang.Long value1, java.lang.String value2, java.sql.Timestamp value3, java.sql.Timestamp value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached OrganisationsRecord
	 */
	public OrganisationsRecord() {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.Organisations.ORGANISATIONS);
	}

	/**
	 * Create a detached, initialised OrganisationsRecord
	 */
	public OrganisationsRecord(java.lang.Long id, java.lang.String name, java.sql.Timestamp createdAt, java.sql.Timestamp modifiedAt) {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.Organisations.ORGANISATIONS);

		setValue(0, id);
		setValue(1, name);
		setValue(2, createdAt);
		setValue(3, modifiedAt);
	}
}
