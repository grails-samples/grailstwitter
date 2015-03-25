package org.grails.twitter.auth

class Person {

    String firstName
    String lastName
	String userName

    static hasMany = [followed:Person]
    static transients = ['displayName']

	static constraints = {
		userName blank: false, unique: true
	}

    String getDisplayName() {
        "$firstName $lastName"
    }
}
