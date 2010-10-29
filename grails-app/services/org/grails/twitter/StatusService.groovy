package org.grails.twitter

import org.grails.twitter.auth.Person

class StatusService {

    static expose = ['jms']
    def twitterCache

    def onMessage(username) {
        log.debug "Message received. New status message posted by user <${username}>."
        def following = Person.withCriteria {
            projections {
                property 'username'
            }
            followed {
                eq 'username', username
            }
        }

        following.each { uname ->
            twitterCache.remove uname
        }
        return
    }

}
