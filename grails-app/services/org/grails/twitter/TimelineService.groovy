package org.grails.twitter

import org.grails.twitter.auth.Person

class TimelineService {

    static transactional = false

    def twitterSecurityService

    void clearTimelineCacheForUser(String username) { }

    def getTimelineForUser(String username = twitterSecurityService.currentUsername) {
        def person = Person.findByUserName(username)
        if (person) {
            def query = Status.whereAny {
                author == person
                author in person.followed
            }
            query.list(max: 10, sort: 'dateCreated', order: 'desc')
        }
    }
}
