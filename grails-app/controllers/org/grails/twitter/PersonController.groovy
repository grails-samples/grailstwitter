package org.grails.twitter

import org.grails.twitter.auth.Person

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
}
