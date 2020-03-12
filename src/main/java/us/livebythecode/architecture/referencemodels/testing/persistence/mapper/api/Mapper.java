/**
 * Mapper.java
 *
 * Copyright (c) 2014 - Old Dominion Freight Line
 *
 */
package us.livebythecode.architecture.referencemodels.testing.persistence.mapper.api;

import java.util.List;

/**
 * Enter type doc here.
 * 
 * $Header$
 *
 * @param <D> the generic type
 * @param <P> the generic type
 */
public interface Mapper<D, P> {

	/**
	 * Map to domain.
	 *
	 * @param arg the arg
	 * @return the d
	 */
	public D mapToDomain(P arg);

	/**
	 * Map list to domain.
	 *
	 * @param arg the arg
	 * @return the list
	 */
	public List<D> mapListToDomain(List<P> arg);

	/**
	 * Map from domain.
	 *
	 * @param arg the arg
	 * @return the p
	 */
	public P mapFromDomain(D arg);
}
