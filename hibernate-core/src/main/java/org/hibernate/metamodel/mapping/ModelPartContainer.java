/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.metamodel.mapping;

import java.util.function.Consumer;

import org.hibernate.internal.util.IndexedConsumer;

/**
 * Access to a group of ModelPart by name or for iteration.
 *
 * @author Steve Ebersole
 */
public interface ModelPartContainer extends ModelPart {
	ModelPart findSubPart(String name, EntityMappingType treatTargetType);

	default void forEachSubPart(IndexedConsumer<ModelPart> consumer) {
		forEachSubPart( consumer, null );
	}

	void forEachSubPart(IndexedConsumer<ModelPart> consumer, EntityMappingType treatTarget);

	void visitSubParts(Consumer<ModelPart> consumer, EntityMappingType treatTargetType);

	default ModelPart findByPath(String path) {
		int nextStart = 0;
		int dotIndex;
		ModelPartContainer modelPartContainer = this;
		while ( ( dotIndex = path.indexOf( '.', nextStart ) ) != -1 ) {
			modelPartContainer = (ModelPartContainer) modelPartContainer.findSubPart(
					path.substring( nextStart, dotIndex ),
					null
			);
			nextStart = dotIndex + 1;
		}
		return modelPartContainer.findSubPart( path.substring( nextStart ), null );
	}
}
