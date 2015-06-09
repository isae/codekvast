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
public class UserRolesRecord extends org.jooq.impl.TableRecordImpl<se.crisp.codekvast.server.codekvast_server.jooq.tables.records.UserRolesRecord> implements org.jooq.Record4<java.lang.Long, java.lang.String, java.sql.Timestamp, java.sql.Timestamp> {

	private static final long serialVersionUID = 1797075956;

	/**
	 * Setter for <code>PUBLIC.USER_ROLES.USER_ID</code>.
	 */
	public UserRolesRecord setUserId(java.lang.Long value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.USER_ROLES.USER_ID</code>.
	 */
	public java.lang.Long getUserId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.USER_ROLES.ROLE</code>.
	 */
	public UserRolesRecord setRole(java.lang.String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.USER_ROLES.ROLE</code>.
	 */
	public java.lang.String getRole() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.USER_ROLES.CREATED_AT</code>.
	 */
	public UserRolesRecord setCreatedAt(java.sql.Timestamp value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.USER_ROLES.CREATED_AT</code>.
	 */
	public java.sql.Timestamp getCreatedAt() {
		return (java.sql.Timestamp) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.USER_ROLES.MODIFIED_AT</code>.
	 */
	public UserRolesRecord setModifiedAt(java.sql.Timestamp value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.USER_ROLES.MODIFIED_AT</code>.
	 */
	public java.sql.Timestamp getModifiedAt() {
		return (java.sql.Timestamp) getValue(3);
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
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.UserRoles.USER_ROLES.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.UserRoles.USER_ROLES.ROLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field3() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.UserRoles.USER_ROLES.CREATED_AT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.sql.Timestamp> field4() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.UserRoles.USER_ROLES.MODIFIED_AT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getUserId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getRole();
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
	public UserRolesRecord value1(java.lang.Long value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRolesRecord value2(java.lang.String value) {
		setRole(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRolesRecord value3(java.sql.Timestamp value) {
		setCreatedAt(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRolesRecord value4(java.sql.Timestamp value) {
		setModifiedAt(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRolesRecord values(java.lang.Long value1, java.lang.String value2, java.sql.Timestamp value3, java.sql.Timestamp value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserRolesRecord
	 */
	public UserRolesRecord() {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.UserRoles.USER_ROLES);
	}

	/**
	 * Create a detached, initialised UserRolesRecord
	 */
	public UserRolesRecord(java.lang.Long userId, java.lang.String role, java.sql.Timestamp createdAt, java.sql.Timestamp modifiedAt) {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.UserRoles.USER_ROLES);

		setValue(0, userId);
		setValue(1, role);
		setValue(2, createdAt);
		setValue(3, modifiedAt);
	}
}
