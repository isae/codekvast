/**
 * This class is generated by jOOQ
 */
package se.crisp.codekvast.server.codekvast_server.jooq.tables.pojos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.4.1" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrganisationMembers implements java.io.Serializable {

	private static final long serialVersionUID = 1732124619;

	private final java.lang.Long     organisationId;
	private final java.lang.Long     userId;
	private final java.lang.Boolean  primaryContact;
	private final java.sql.Timestamp createdAt;
	private final java.sql.Timestamp modifiedAt;

	public OrganisationMembers(
		java.lang.Long     organisationId,
		java.lang.Long     userId,
		java.lang.Boolean  primaryContact,
		java.sql.Timestamp createdAt,
		java.sql.Timestamp modifiedAt
	) {
		this.organisationId = organisationId;
		this.userId = userId;
		this.primaryContact = primaryContact;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public java.lang.Long getOrganisationId() {
		return this.organisationId;
	}

	public java.lang.Long getUserId() {
		return this.userId;
	}

	public java.lang.Boolean getPrimaryContact() {
		return this.primaryContact;
	}

	public java.sql.Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public java.sql.Timestamp getModifiedAt() {
		return this.modifiedAt;
	}
}
