package org.grails.twitter

import org.grails.twitter.auth.Person

import grails.plugin.springsecurity.annotation.Secured

@Secured('isAuthenticated()')
class PersonController {

    def statusService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [personInstanceCount: Person.count(), personInstanceList: Person.list(params)]
    }

    def unfollow(String userToUnfollow) {
        statusService.unfollow userToUnfollow
        redirect action: 'index'
    }

    def follow(String userToFollow) {
        statusService.follow userToFollow
        redirect action: 'index'
    }

    def show(Person person) {
        [person: person, messages: Status.findAllByAuthor(person, [max: 10, sort: "dateCreated", order: "desc" ] )]
    }
}
