/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html.
 */
package org.hibernate.sql.model;

import org.hibernate.jdbc.Expectation;
import org.hibernate.metamodel.mapping.TableDetails;

/**
 * Describes a table as far as Hibernate understands it from mapping details
 * <p/>
 * Includes {@linkplain TableDetails basic details}, in addition to details
 * about the table in relation to a particular {@link MutationTarget}
 *
 * @author Steve Ebersole
 */
public interface TableMapping extends TableDetails {
	/**
	 * The name of the mapped table
	 */
	String getTableName();

	/**
	 * The position of the table relative to others for the {@link MutationTarget}
	 */
	int getRelativePosition();

	/**
	 * Whether the table is mapped as optional
	 */
	boolean isOptional();

	/**
	 * Whether the table is mapped as inverse
	 */
	boolean isInverse();

	/**
	 * Whether this table holds the identifier for the {@link MutationTarget}
	 */
	boolean isIdentifierTable();

	/**
	 * Details for insertion into this table
	 */
	MutationDetails getInsertDetails();

	/**
	 * Details for updating this table
	 */
	MutationDetails getUpdateDetails();

	/**
	 * Whether deletions are cascaded to this table at the database level.
	 *
	 * @apiNote When {@code true}, {@link #isIdentifierTable()} will generally
	 * be {@code false}
	 *
	 * @see org.hibernate.annotations.OnDelete
	 */
	boolean isCascadeDeleteEnabled();

	/**
	 * Details for deleting from this table
	 */
	MutationDetails getDeleteDetails();


	/**
	 * Details for the {@linkplain MutationType mutation} of a table
	 */
	class MutationDetails {
		private final MutationType mutationType;
		private final Expectation expectation;
		private final String customSql;
		private final boolean callable;

		public MutationDetails(
				MutationType mutationType,
				Expectation expectation,
				String customSql,
				boolean callable) {
			this.mutationType = mutationType;
			this.expectation = expectation;
			this.customSql = customSql;
			this.callable = callable;
		}

		/**
		 * The type of mutation being detailed
		 */
		public MutationType getMutationType() {
			return mutationType;
		}

		/**
		 * The expectation for this mutation
		 */
		public Expectation getExpectation() {
			return expectation;
		}

		/**
		 * Custom, application-provided SQL for this mutation (if one).
		 * Will return {@code null} if no custom SQL was provided indicating
		 * Hibernate will generate the SQL based on the mapping
		 */
		public String getCustomSql() {
			return customSql;
		}

		/**
		 * Whether {@linkplain #getCustomSql() custom SQL} should be treated as callable (function / procedure)
		 */
		public boolean isCallable() {
			return callable;
		}
	}
}
