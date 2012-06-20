package org.grails.twitter.pages

import geb.Module

class UserSearchResult extends Module{
    
    static content = {
        realName { $('.real_name').text() }
        linkToFollow { $('a') }
    }
}
