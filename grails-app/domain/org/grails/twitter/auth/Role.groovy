package org.grails.twitter.auth

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Role implements Serializable {

	private static final long serialVersionUID = 1

	String authority

	@Override
	String toString() {
		authority
	}

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
