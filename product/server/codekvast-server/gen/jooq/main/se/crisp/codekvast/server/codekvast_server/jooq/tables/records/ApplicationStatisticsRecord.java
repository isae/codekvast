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
public class ApplicationStatisticsRecord extends org.jooq.impl.TableRecordImpl<se.crisp.codekvast.server.codekvast_server.jooq.tables.records.ApplicationStatisticsRecord> implements org.jooq.Record15<java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> {

	private static final long serialVersionUID = -1616805203;

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.APPLICATION_ID</code>.
	 */
	public ApplicationStatisticsRecord setApplicationId(java.lang.Long value) {
		setValue(0, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.APPLICATION_ID</code>.
	 */
	public java.lang.Long getApplicationId() {
		return (java.lang.Long) getValue(0);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.APPLICATION_VERSION</code>.
	 */
	public ApplicationStatisticsRecord setApplicationVersion(java.lang.String value) {
		setValue(1, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.APPLICATION_VERSION</code>.
	 */
	public java.lang.String getApplicationVersion() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_HOST_NAMES</code>. The number of distinct host names in which this application is executing
	 */
	public ApplicationStatisticsRecord setNumHostNames(java.lang.Integer value) {
		setValue(2, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_HOST_NAMES</code>. The number of distinct host names in which this application is executing
	 */
	public java.lang.Integer getNumHostNames() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_SIGNATURES</code>. The total number of signatures in this application, invoked or not
	 */
	public ApplicationStatisticsRecord setNumSignatures(java.lang.Integer value) {
		setValue(3, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_SIGNATURES</code>. The total number of signatures in this application, invoked or not
	 */
	public java.lang.Integer getNumSignatures() {
		return (java.lang.Integer) getValue(3);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_NOT_INVOKED_SIGNATURES</code>. The number of signatures that never have been invoked
	 */
	public ApplicationStatisticsRecord setNumNotInvokedSignatures(java.lang.Integer value) {
		setValue(4, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_NOT_INVOKED_SIGNATURES</code>. The number of signatures that never have been invoked
	 */
	public java.lang.Integer getNumNotInvokedSignatures() {
		return (java.lang.Integer) getValue(4);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_INVOKED_SIGNATURES</code>. The number of invoked signatures in this application
	 */
	public ApplicationStatisticsRecord setNumInvokedSignatures(java.lang.Integer value) {
		setValue(5, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_INVOKED_SIGNATURES</code>. The number of invoked signatures in this application
	 */
	public java.lang.Integer getNumInvokedSignatures() {
		return (java.lang.Integer) getValue(5);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_BOOTSTRAP_SIGNATURES</code>. The number of signatures that are only invoked within a short time after the application starts
	 */
	public ApplicationStatisticsRecord setNumBootstrapSignatures(java.lang.Integer value) {
		setValue(6, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_BOOTSTRAP_SIGNATURES</code>. The number of signatures that are only invoked within a short time after the application starts
	 */
	public java.lang.Integer getNumBootstrapSignatures() {
		return (java.lang.Integer) getValue(6);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_POSSIBLY_DEAD_SIGNATURES</code>. The number of probably dead signatures in the application, i.e., only invoked before the latest
   full usage cycle
	 */
	public ApplicationStatisticsRecord setNumPossiblyDeadSignatures(java.lang.Integer value) {
		setValue(7, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.NUM_POSSIBLY_DEAD_SIGNATURES</code>. The number of probably dead signatures in the application, i.e., only invoked before the latest
   full usage cycle
	 */
	public java.lang.Integer getNumPossiblyDeadSignatures() {
		return (java.lang.Integer) getValue(7);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.SUM_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (sum over all instances)?
	 */
	public ApplicationStatisticsRecord setSumUpTimeMillis(java.lang.Long value) {
		setValue(8, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.SUM_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (sum over all instances)?
	 */
	public java.lang.Long getSumUpTimeMillis() {
		return (java.lang.Long) getValue(8);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.AVG_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (average over all instances)?
	 */
	public ApplicationStatisticsRecord setAvgUpTimeMillis(java.lang.Long value) {
		setValue(9, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.AVG_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (average over all instances)?
	 */
	public java.lang.Long getAvgUpTimeMillis() {
		return (java.lang.Long) getValue(9);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.MIN_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (minimum over all instances)?
	 */
	public ApplicationStatisticsRecord setMinUpTimeMillis(java.lang.Long value) {
		setValue(10, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.MIN_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (minimum over all instances)?
	 */
	public java.lang.Long getMinUpTimeMillis() {
		return (java.lang.Long) getValue(10);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.MAX_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (maximum over all instances)?
	 */
	public ApplicationStatisticsRecord setMaxUpTimeMillis(java.lang.Long value) {
		setValue(11, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.MAX_UP_TIME_MILLIS</code>. How many millis has this application version been running in total (maximum over all instances)?
	 */
	public java.lang.Long getMaxUpTimeMillis() {
		return (java.lang.Long) getValue(11);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.FIRST_STARTED_AT_MILLIS</code>. When was this application version first started?
	 */
	public ApplicationStatisticsRecord setFirstStartedAtMillis(java.lang.Long value) {
		setValue(12, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.FIRST_STARTED_AT_MILLIS</code>. When was this application version first started?
	 */
	public java.lang.Long getFirstStartedAtMillis() {
		return (java.lang.Long) getValue(12);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.MAX_STARTED_AT_MILLIS</code>. When was the last time this application was started?
	 */
	public ApplicationStatisticsRecord setMaxStartedAtMillis(java.lang.Long value) {
		setValue(13, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.MAX_STARTED_AT_MILLIS</code>. When was the last time this application was started?
	 */
	public java.lang.Long getMaxStartedAtMillis() {
		return (java.lang.Long) getValue(13);
	}

	/**
	 * Setter for <code>PUBLIC.APPLICATION_STATISTICS.LAST_REPORTED_AT_MILLIS</code>. When was the last time data was received from this application version?
	 */
	public ApplicationStatisticsRecord setLastReportedAtMillis(java.lang.Long value) {
		setValue(14, value);
		return this;
	}

	/**
	 * Getter for <code>PUBLIC.APPLICATION_STATISTICS.LAST_REPORTED_AT_MILLIS</code>. When was the last time data was received from this application version?
	 */
	public java.lang.Long getLastReportedAtMillis() {
		return (java.lang.Long) getValue(14);
	}

	// -------------------------------------------------------------------------
	// Record15 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row15<java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> fieldsRow() {
		return (org.jooq.Row15) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row15<java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long> valuesRow() {
		return (org.jooq.Row15) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field1() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.APPLICATION_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.APPLICATION_VERSION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.NUM_HOST_NAMES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field4() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.NUM_SIGNATURES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field5() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.NUM_NOT_INVOKED_SIGNATURES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field6() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.NUM_INVOKED_SIGNATURES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field7() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.NUM_BOOTSTRAP_SIGNATURES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field8() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.NUM_POSSIBLY_DEAD_SIGNATURES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field9() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.SUM_UP_TIME_MILLIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field10() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.AVG_UP_TIME_MILLIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field11() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.MIN_UP_TIME_MILLIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field12() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.MAX_UP_TIME_MILLIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field13() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.FIRST_STARTED_AT_MILLIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field14() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.MAX_STARTED_AT_MILLIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Long> field15() {
		return se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS.LAST_REPORTED_AT_MILLIS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value1() {
		return getApplicationId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getApplicationVersion();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getNumHostNames();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value4() {
		return getNumSignatures();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value5() {
		return getNumNotInvokedSignatures();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value6() {
		return getNumInvokedSignatures();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value7() {
		return getNumBootstrapSignatures();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value8() {
		return getNumPossiblyDeadSignatures();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value9() {
		return getSumUpTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value10() {
		return getAvgUpTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value11() {
		return getMinUpTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value12() {
		return getMaxUpTimeMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value13() {
		return getFirstStartedAtMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value14() {
		return getMaxStartedAtMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Long value15() {
		return getLastReportedAtMillis();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value1(java.lang.Long value) {
		setApplicationId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value2(java.lang.String value) {
		setApplicationVersion(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value3(java.lang.Integer value) {
		setNumHostNames(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value4(java.lang.Integer value) {
		setNumSignatures(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value5(java.lang.Integer value) {
		setNumNotInvokedSignatures(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value6(java.lang.Integer value) {
		setNumInvokedSignatures(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value7(java.lang.Integer value) {
		setNumBootstrapSignatures(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value8(java.lang.Integer value) {
		setNumPossiblyDeadSignatures(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value9(java.lang.Long value) {
		setSumUpTimeMillis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value10(java.lang.Long value) {
		setAvgUpTimeMillis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value11(java.lang.Long value) {
		setMinUpTimeMillis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value12(java.lang.Long value) {
		setMaxUpTimeMillis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value13(java.lang.Long value) {
		setFirstStartedAtMillis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value14(java.lang.Long value) {
		setMaxStartedAtMillis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord value15(java.lang.Long value) {
		setLastReportedAtMillis(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationStatisticsRecord values(java.lang.Long value1, java.lang.String value2, java.lang.Integer value3, java.lang.Integer value4, java.lang.Integer value5, java.lang.Integer value6, java.lang.Integer value7, java.lang.Integer value8, java.lang.Long value9, java.lang.Long value10, java.lang.Long value11, java.lang.Long value12, java.lang.Long value13, java.lang.Long value14, java.lang.Long value15) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ApplicationStatisticsRecord
	 */
	public ApplicationStatisticsRecord() {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS);
	}

	/**
	 * Create a detached, initialised ApplicationStatisticsRecord
	 */
	public ApplicationStatisticsRecord(java.lang.Long applicationId, java.lang.String applicationVersion, java.lang.Integer numHostNames, java.lang.Integer numSignatures, java.lang.Integer numNotInvokedSignatures, java.lang.Integer numInvokedSignatures, java.lang.Integer numBootstrapSignatures, java.lang.Integer numPossiblyDeadSignatures, java.lang.Long sumUpTimeMillis, java.lang.Long avgUpTimeMillis, java.lang.Long minUpTimeMillis, java.lang.Long maxUpTimeMillis, java.lang.Long firstStartedAtMillis, java.lang.Long maxStartedAtMillis, java.lang.Long lastReportedAtMillis) {
		super(se.crisp.codekvast.server.codekvast_server.jooq.tables.ApplicationStatistics.APPLICATION_STATISTICS);

		setValue(0, applicationId);
		setValue(1, applicationVersion);
		setValue(2, numHostNames);
		setValue(3, numSignatures);
		setValue(4, numNotInvokedSignatures);
		setValue(5, numInvokedSignatures);
		setValue(6, numBootstrapSignatures);
		setValue(7, numPossiblyDeadSignatures);
		setValue(8, sumUpTimeMillis);
		setValue(9, avgUpTimeMillis);
		setValue(10, minUpTimeMillis);
		setValue(11, maxUpTimeMillis);
		setValue(12, firstStartedAtMillis);
		setValue(13, maxStartedAtMillis);
		setValue(14, lastReportedAtMillis);
	}
}
