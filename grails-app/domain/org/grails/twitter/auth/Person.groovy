package org.grails.twitter.auth

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = 'userName')
class Person implements Serializable {

    private static final long serialVersionUID = 1

    transient springSecurityService

    String firstName
    String lastName
    String userName
    String email

    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static hasMany = [followed:Person]
    static transients = ['displayName']

    static constraints = {
        userName blank: false, unique: true
        email nullable: true
    }

    String getDisplayName() {
        "$firstName $lastName"
    }

    Set<Role> getAuthorities() {
        PersonRole.findAllByPerson(this)*.role
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }
}
