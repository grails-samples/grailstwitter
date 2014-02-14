package org.grails.twitter

import org.grails.twitter.auth.Person

class TimelineService {

	static transactional = false

    void clearTimelineCacheForUser(String username) {}

    def getTimelineForUser(String username) {
        def per = Person.findByUsername(username)
        def query = Status.whereAny {
            author { username == per.username }
            if(per.followed) author in per.followed
        }.order 'dateCreated', 'desc'
        def messages = query.list(max: 10)
        messages
    }
}
